/**
 * Copyright (c) 2009-2011 Caixa Econômica Federal. Todos os direitos
 * reservados.
 * 
 * Caixa Econômica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade
 * 
 * Este programa de computador foi desenvolvido sob demanda da CAIXA e está
 * protegido por leis de direitos autorais e tratados internacionais. As
 * condições de cópia e utilização do todo ou partes dependem de autorização da
 * empresa. Cópias não são permitidas sem expressa autorização. Não pode ser
 * comercializado ou utilizado para propósitos particulares.
 * 
 * Uso exclusivo da Caixa Econômica Federal. A reprodução ou distribuição não
 * autorizada deste programa ou de parte dele, resultará em punições civis e
 * criminais e os infratores incorrem em sanções previstas na legislação em
 * vigor.
 * 
 * Histórico do Subversion:
 * 
 * LastChangedRevision:  
 * LastChangedBy:  
 * LastChangedDate: 
 * 
 * HeadURL: 
 * 
 */
package br.gov.caixa.siiac.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.format.FormatUitils;
import br.gov.caixa.siiac.bo.ListaPerfil;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.persistence.dao.IMatrizAbrangenciaDAO;
import br.gov.caixa.siiac.persistence.dao.IUnidadeDAO;
import br.gov.caixa.siiac.util.LogCEF;

/**
 * @author GisConsult
 *
 */
@Repository
@Scope("prototype")
public class UnidadeDAO extends GenericDAO<Unidade> implements IUnidadeDAO {
	private static final String ERROR_UNIDADE_INEXISTENTE_MASK = "A Unidade %d não foi encontrada";
	private static final String ERROR_NUMERO_NATURAL_MASK = "Erro ao buscar NuNatural : %s";
	
	private static final Log LOG = LogFactory.getLog(UnidadeDAO.class);
	private IMatrizAbrangenciaDAO matrizAbrangenciaDAO;
	
	/**
	 * UnidadeAtivaHibernateDAO
	 * @throws DAOException
	 */
	public UnidadeDAO() throws SIIACException {
		super(Unidade.class);
	}
	
	public void setMatrizAcessoDAO(IMatrizAbrangenciaDAO matrizAbrangenciaDAO) {
		this.matrizAbrangenciaDAO = matrizAbrangenciaDAO;
	}

	public boolean existUnidade(String nuUnidade) {
		try {
			Criteria crit = getCriteria().add(Restrictions.eq("id.nuUnidade", Short.valueOf(nuUnidade))).setProjection(Projections.rowCount());
			return ((Integer) crit.uniqueResult()).intValue() > 0;
			
		} catch (HibernateException e) {
			LogCEF.error(String.format(ERROR_NUMERO_NATURAL_MASK, e.getMessage()));
		}
		return false;
	}
	
	public List<Unidade> getUnidadesFilhas(Short idUnidadeVinculadora, Short perfil) {
		Criteria crit = getCriteria();
		crit.add(Restrictions.sqlRestriction("this_.nu_unidade in (SELECT nu_unidade FROM icosm001.iacvw004_vinculacao_unidade WHERE " + "nu_unde_vnclra_u24 = " + idUnidadeVinculadora + getFiltroVinculacaoByPerfil(perfil) + ")"));
		
		return findByCriteria(crit);
	}
	
	private String getFiltroVinculacaoByPerfil(Short perfil) {
		if (ListaPerfil.PERFIL_GESTOR_SISTEMA.equals(perfil)) {
			return getFiltroVinculacaoGestorSistema();
		} else if (ListaPerfil.PERFIL_REGIONAL_CONFORMIDADE.equals(perfil)) {
			return getFiltroVinculacaoRegionalConformidade();
		}
		return "";
	}
	
	private String getFiltroVinculacaoGestorSistema() {
		return "";
	}
	
	private String getFiltroVinculacaoRegionalConformidade() {
		return " AND (nu_tp_vinculo_u22 = 1) OR ((nu_tp_vinculo_u22 = 4) and (nu_processo_u27 in (8703, 8737, 8768)))";
	}
	
	public List<Unidade> getLikeNuUnidade(String nuUnidade) {
		
		Query q = getSession().createQuery("FROM Unidade u WHERE text(u.id.nuUnidade) like :nuUnidade");
		q.setParameter("nuUnidade", nuUnidade.trim() + "%");
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Unidade> getLikeNuUnidade(String nuUnidade, List<Short> unidadesPermitidas) {
		StringBuilder sbHQL = new StringBuilder("FROM Unidade u WHERE text(u.id.nuUnidade) like :nuUnidade ");
		Map<String, Object> param = new HashMap<String, Object>();
		if(unidadesPermitidas != null){
			sbHQL.append(" AND u.id.nuUnidade IN (:unidades)");
			param.put("unidades", unidadesPermitidas);
		}
		Query q = getSession().createQuery(sbHQL.toString());
		param.put("nuUnidade", nuUnidade + "%");
		q.setProperties(param);
		return q.list();
	}
	
	public Unidade findByNuUnidade(Short nuUnidade) {
		Criteria c = getCriteria();
		c.add(Restrictions.eq("id.nuUnidade", nuUnidade));
		List<Unidade> unidades = c.list();
		if(unidades.size() == 1) {
			return unidades.get(0);
		}
		
		return null;
	}
	
	public Integer getNuNaturalUnidade(Short nuUnidade) {
		Query q = getSession().createQuery("FROM Unidade u WHERE u.id.nuUnidade = :nuUnidade AND u.id.nuNatural " +
				"IN(SELECT MIN(us.id.nuNatural) FROM Unidade us WHERE us.id.nuUnidade = :nuUnidade)");
		q.setParameter("nuUnidade", nuUnidade);
		List<Unidade> unidades = q.list();
		if(unidades != null && unidades.size() == 1) {
			Unidade u = unidades.get(0);
			return u.getId().getNuNatural();
		}
		
		return null;
	}
	
	public Unidade getUnidadeByEmpregado(String coResponsavel) {
		String sql = "SELECT unidade.* " +
				"FROM icosm001.iacvw003_unidade unidade " +
				"LEFT JOIN icosm001.iacw002_empregado_caixa empregado ON empregado.nu_unidade = unidade.nu_unidade " +
				"WHERE unidade.nu_natural = (SELECT min(nu_natural) FROM icosm001.iacvw003_unidade WHERE nu_unidade = unidade.nu_unidade) " +
				"AND nu_matricula = :nu_matricula";
		SQLQuery query = getSession().createSQLQuery(sql);
		
	    Matcher matcher = Pattern.compile("[^0-9]").matcher(coResponsavel);
		query.setParameter("nu_matricula", Integer.valueOf(matcher.replaceAll("")));
		query.addEntity(Unidade.class);
		return (Unidade) query.uniqueResult();
	}
	
	public String getNomeEmpregado(String coResponsavel) {
		String sql = "SELECT no_pessoa " +
				"FROM icosm001.iacw002_empregado_caixa empregado " +
				"WHERE nu_matricula= :nu_matricula";
		SQLQuery query = getSession().createSQLQuery(sql);
		
	    Matcher matcher = Pattern.compile("[^0-9]").matcher(coResponsavel);
		query.setParameter("nu_matricula", Integer.valueOf(matcher.replaceAll("")));
		return (String) query.uniqueResult();
	}
	
	public String getFuncaoEmpregado(String coResponsavel) {
		String sql = "SELECT no_funcao " +
				"FROM icosm001.iacw002_empregado_caixa empregado " +
				"WHERE empregado.nu_matricula = :nu_matricula AND nu_funcao IS NOT NULL";
		SQLQuery query = getSession().createSQLQuery(sql);
		
	    Matcher matcher = Pattern.compile("[^0-9]").matcher(coResponsavel);
		query.setParameter("nu_matricula", Integer.valueOf(matcher.replaceAll("")));
		return (String) query.uniqueResult();
	}
	
	public String getCargoEmpregado(String coResponsavel) {
		String sql = "SELECT no_cargo " +
				"FROM icosm001.iacw002_empregado_caixa empregado " +
				"WHERE empregado.nu_matricula = :nu_matricula AND co_cargo IS NOT NULL";
		SQLQuery query = getSession().createSQLQuery(sql);
		
	    Matcher matcher = Pattern.compile("[^0-9]").matcher(coResponsavel);
		query.setParameter("nu_matricula", Integer.valueOf(matcher.replaceAll("")));
		return (String) query.uniqueResult();
	}
	
	public String getFuncaoResponsavelUnidade(Short nuUnidade) {
		
		String sql = "SELECT no_funcao " +
				"FROM icosm001.iacw002_empregado_caixa empregado " +
				"INNER JOIN icosm001.iacvw001_rspnl_unidade responsavel ON responsavel.nu_matricula_h01 = empregado.nu_matricula " +
				"WHERE responsavel.nu_unidade = :nu_unidade GROUP BY responsavel.ic_eventual, no_funcao LIMIT 1";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_unidade", nuUnidade);
		
		return (String) query.uniqueResult();		
	}
	

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IUnidadeDAO#getLocalizacao(java.lang.Short)
	 */
	public String getLocalidade(Short nuUnidade) {
		String sql = "SELECT no_localidade FROM icosm001.iacvw003_unidade WHERE nu_unidade = :nu_unidade";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_unidade", nuUnidade);
		return (String) query.uniqueResult();		
	}
	
	public String getNomeFuncaoResponsavelByUnidade(Short nuUnidade) {
		String sql = "Select f.no_funcao from icosm001.icotbh03_funcao as f inner join icosm001.icotbh01_empro_cxa as e "
				+ " on f.nu_funcao = e.nu_funcao_h03 "
				+ " inner join icosm001.icotbh02_prdo_rspe as r "
				+ " on e.nu_matricula = r.nu_matricula_h01 "
				+ " where r.nu_unidade_u24 = :nuUnidade  limit 1";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuUnidade", nuUnidade);
		return (String) query.uniqueResult();		
	}
	
	public String getNomeUnidadeByUnidade(Short nuUnidade) {
		String sql = "select CAST(no_unidade as VARCHAR(255)), CAST(sg_tipo_unidade as varchar(5)) from icosm001.iacvw003_unidade where nu_unidade = :nuUnidade";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuUnidade", nuUnidade);
		List list = query.list();
		String nome = "", sigla = "";
		if(!list.isEmpty()){
			Object[] obj = (Object[]) list.get(0);
			nome = obj[0] == null ? "" : String.valueOf(obj[0]);
			sigla = obj[1] == null ? "" : String.valueOf(obj[1]);
		}
		return 	FormatUitils.getNameForUnidadeComplete(sigla, nome);	
	}
	
	public String getEstadoByUnidade(Short nuUnidade) {
		String sql = "select CAST(sg_localizacao as VARCHAR(10)) from icosm001.iacvw003_unidade where nu_unidade = :nuUnidade";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuUnidade", nuUnidade);
		return (String) query.uniqueResult();		
	}
	
	public String getNomeUnidadeAlienacaoBensImoveisByUnidade(Short nuUnidade) {
		String sql = "select CAST(u.no_unidade as varchar(255)), CAST(u.sg_tipo_unidade as varchar(5)) from icosm001.iacvw004_vinculacao_unidade as v "
				+ " INNER JOIN icosm001.iacvw003_unidade as u on u.nu_unidade = v.nu_unidade "
				+ " where v.nu_tp_vinculo_u22 = 4 "
				+ " AND u.nu_tp_unidade_u21 in (14,49) "
				+ " and v.nu_unidade = :nuUnidade "
				+ " and v.nu_processo_u27 in (87,8724) LIMIT 1";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuUnidade", nuUnidade);
		List list = query.list();
		String nome = "", sigla = "";
		if(!list.isEmpty()){
			Object[] obj = (Object[]) list.get(0);
			nome = obj[0] == null ? "" : String.valueOf(obj[0]);
			sigla = obj[1] == null ? "" : String.valueOf(obj[1]);
		}
		return 	FormatUitils.getNameForUnidadeComplete(sigla, nome);		
	}
	
	public String getSiglaUnidadeAlienacaoBensImoveisByUnidade(Short nuUnidade) {
		String sql = "select CAST(u.sg_tipo_unidade as varchar(255)) from icosm001.iacvw004_vinculacao_unidade as v "
				+ " INNER JOIN icosm001.iacvw003_unidade as u on u.nu_unidade = v.nu_unidade "
				+ " where v.nu_tp_vinculo_u22 = 4 "
				+ " AND u.nu_tp_unidade_u21 in (14,49) "
				+ " and v.nu_unidade = :nuUnidade "
				+ " and v.nu_processo_u27 in (87,8724) LIMIT 1";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuUnidade", nuUnidade);
		return (String) query.uniqueResult();		
	}
	
	public String getNomeResponsavelSURET(Short nuUnidade) {
		String sql = "select e.no_pessoa from icosm001.icotbh01_empro_cxa as e inner join icosm001.icotbh03_funcao as f "
				+ " on e.nu_funcao_h03 = f.nu_funcao "
				+ " inner join icosm001.icotbh02_prdo_rspe as r "
				+ " on e.nu_matricula = r.nu_matricula_h01 "
				+ " where r.nu_unidade_u24 = :nuUnidade";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuUnidade", nuUnidade);
		return (String) query.uniqueResult();		
	}
	
	public String getSiglaUnidadeByUnidade(Short nuUnidade)
	{
		String sql = "select CAST(u.sg_tipo_unidade as varchar(50)) from icosm001.iacvw003_unidade as u "
				+ " where u.nu_unidade = :nuUnidade";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuUnidade", nuUnidade);
		return (String) query.uniqueResult();	
	}
	
	public String getNomeUnidadeSuperiorPrimeiroNivel(Short nuUnidade)
	{
		String sql = "Select "
				+ " vinUnid.nu_unde_vnclra_u24 as numeroUnidadeSuperior "
				+ " from icosm001.iacvw004_vinculacao_unidade vinUnid "
				+ " where (nu_unidade = :nuUnidade) and nu_tp_vinculo_u22 = 1 limit 1";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuUnidade", nuUnidade);
		
		Short unidade = (Short) query.uniqueResult();
		
		return getNomeUnidadeByUnidade(unidade);
	}
	
	public String getNomeUnidadeSuperiorSegundoNivel(Short nuUnidade)
	{
		String sql = "Select "
				+ " vinUnid.nu_unde_vnclra_u24 as numeroUnidadeSuperior "
				+ " from icosm001.iacvw004_vinculacao_unidade vinUnid "
				+ " where nu_tp_vinculo_u22 = 1 "
				+ " and ("
				+ "		nu_unidade = "
				+ "			(select vinUnid.nu_unde_vnclra_u24 from icosm001.iacvw004_vinculacao_unidade vinUnid "
				+ "				where (nu_unidade = :nuUnidade)	and nu_tp_vinculo_u22 = 1"
				+ "			)" + "	  ) limit 1";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuUnidade", nuUnidade);
		
		Short unidade = (Short) query.uniqueResult();
		
		return getNomeUnidadeByUnidade(unidade);
	}
	
	public String getSiglaUnidadeSuperiorPrimeiroNivel(Short nuUnidade)
	{
		String sql = "Select "
				+ " vinUnid.nu_unde_vnclra_u24 as numeroUnidadeSuperior "
				+ " from icosm001.iacvw004_vinculacao_unidade vinUnid "
				+ " where (nu_unidade = :nuUnidade) and nu_tp_vinculo_u22 = 1 limit 1";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuUnidade", nuUnidade);
		
		Short unidade = (Short) query.uniqueResult();
		
		return getSiglaUnidadeByUnidade(unidade);
	}
	
	public String getSiglaNomeUnidadeByEmpregado(Integer nuMatricula)
	{
		String sql = "SELECT CAST(u.sg_unidade AS VARCHAR(50)) FROM icosm001.iacw002_empregado_caixa e "
				+ "INNER JOIN icosm001.iacvw003_unidade as u on u.nu_unidade = e.nu_unidade "
				+ "WHERE e.nu_matricula = :nuMatricula";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuMatricula", nuMatricula);
		
		return (String) query.uniqueResult();
		
	}
	
	public List<Unidade> getAllOrderByNuUnidade(){
		Criteria c = getCriteria();
		c.addOrder(Order.asc("id.nuUnidade"));
		return c.list();
	}

	public String getCodigoUnidadeAlienacaoBensImoveisByUnidade(Short nuUnidade) {
		String sql = "select u.nu_unidade from icosm001.iacvw004_vinculacao_unidade as v "
				+ " INNER JOIN icosm001.iacvw003_unidade as u on u.nu_unidade = v.nu_unidade "
				+ " where v.nu_tp_vinculo_u22 = 4 "
				+ " AND u.nu_tp_unidade_u21 in (14,49) "
				+ " and v.nu_unidade = :nuUnidade "
				+ " and v.nu_processo_u27 in (87,8724) LIMIT 1";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuUnidade", nuUnidade);
		
		Short unidade = (Short) query.uniqueResult();
		if (unidade != null && unidade > 0)
			return 	unidade.toString();
		else
			return "";
	}

	public String getCodigoUnidadeSuperiorPrimeiroNivel(Short nuUnidade)
	{
		String sql = "Select "
				+ " vinUnid.nu_unde_vnclra_u24 as numeroUnidadeSuperior "
				+ " from icosm001.iacvw004_vinculacao_unidade vinUnid "
				+ " where (nu_unidade = :nuUnidade) and nu_tp_vinculo_u22 = 1 limit 1";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuUnidade", nuUnidade);
		
		Short unidade = (Short) query.uniqueResult();
		
		if (unidade != null && unidade > 0)		
			return unidade.toString();
		else
			return "";
	}
}
