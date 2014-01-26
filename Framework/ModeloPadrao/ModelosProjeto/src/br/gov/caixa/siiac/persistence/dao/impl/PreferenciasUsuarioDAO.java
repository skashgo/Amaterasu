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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.GerenteUnidade;
import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioServico;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuario;
import br.gov.caixa.siiac.persistence.dao.IPreferenciasUsuarioDAO;
import br.gov.caixa.util.Utilities;

/**
 * @author GisConsult
 *
 */
@Repository
@Scope("prototype")
public class PreferenciasUsuarioDAO extends GenericDAO<PreferenciasUsuario> implements IPreferenciasUsuarioDAO{
	
	/**
	 * @param persistenceClass
	 */
	public PreferenciasUsuarioDAO() {
		super(PreferenciasUsuario.class);
	}
	
	public void deleteAll(String matricula, List<PreferenciasUsuario> produtos) {
		
		Criteria crit = getCriteria()
							.add(Restrictions.eq("id.coUsuario", matricula));
		
		for(PreferenciasUsuario pu : findByCriteria(crit)){
			boolean contain = false;
			for(PreferenciasUsuario p : produtos){
				if(p.getId().getCoProduto().equals(pu.getProduto().getCoProduto())){
					contain = true;
					break;
				}
			}
			
			if(!contain){
				for(PreferenciasUsuario prefUsuario : findByCriteria(getCriteria().add(Restrictions.eq("produto", pu.getProduto())).add(Restrictions.eq("usuario", pu.getUsuario())))){
					delete(prefUsuario);
				}
			}
		}
		
		getSession().flush();
	}

	@SuppressWarnings("unchecked")
	public void saveAll(String matricula, List<PreferenciasUsuario> produtos) {

		List<String> l = getCriteria()
							.add(Restrictions.eq("id.coUsuario", matricula))
							.setProjection(Projections.property("produto.coProduto"))
							.list();
				
		for(PreferenciasUsuario p : produtos){
			boolean contain = false;
			for(String nuProdutoUsuario : l){
				if(p.getId().getCoProduto().equals(nuProdutoUsuario)){
					contain = true;
					break;
				}
			}
			
			if(!contain){
				merge(p);
			}
		}
		
		getSession().flush();
	}
	
	/**
	 * Verifica se determinada matrícula possui ou não produto preferencial
	 * @param matricula
	 * @return
	 */
	public boolean hasPreferencial(String matricula){
		Criteria c = getCriteria()
		.add(Restrictions.eq("usuario.coUsuario", matricula))
		.setProjection(Projections.rowCount());
		return ((Integer) c.uniqueResult()).intValue() > 0;
	}

	/**
	 * Verifica se determinada matrícula possui ou não servico preferencial
	 * @param matricula
	 * @return
	 */
	public boolean hasServicoPreferencial(String matricula){
		Criteria c = getSession().createCriteria(PreferenciaUsuarioServico.class)
		.add(Restrictions.eq("usuario.coUsuario", matricula))
		.setProjection(Projections.rowCount());
		return ((Integer) c.uniqueResult()).intValue() > 0;
	}
	
	
	public List<String> listProdutoPreferencial(String matricula){
		//Buscando as categorias preferenciais
		Query cat = getSession().createQuery("SELECT p.categoriaProduto.nuCategoriaProduto FROM PreferenciasUsuarioCategoriaProduto p JOIN p.categoriaProduto as c JOIN p.usuario as u WHERE p.usuario.coUsuario = :matricula");
		cat.setParameter("matricula", matricula);
		List<Short> lista = cat.list();
		
		List<String> retorno = new ArrayList<String>();		
		
		//Verificar se o usuário possui todos os produtos preferênciais.		
		if (getAll(matricula, "").isEmpty())
		{
			/*
			 * Possui todos os produtos preferênciais, portanto
			 * será feita uma busca em todos os produtos cadastrados passando como parâmetro as categorias
			 * resultantes do select acima
			 */ 
			getSession().flush();
			
			/*
			 * Verificando se o usuário possui registros na tabela de ProdutoUsuario, caso possua,
			 * fazer a busca nela.
			 * Caso não possua nenhuma registro, significa que o usuário possúi todos os produtos, 
			 * portanto, a busca deverá ser feita na tabela de produtos
			 */
			
			/*
			 * Caso prod.list() for vazia, será retornado uma lista de String com 1 elemento de valor 0
			 * Solução encontrada para que não buscar nenhum contrato já que quando a lista é vazia
			 * significa que o usuário possui todos os produtos
			 */
			
			String prodUsuario = "SELECT COUNT(*) FROM ProdutoUsuario p JOIN p.usuario u WHERE u.coUsuario = :usuario ";
			Query prodUsuarioQuery = getSession().createQuery(prodUsuario);
			prodUsuarioQuery.setParameter("usuario", matricula);
			
			Long count = (Long) prodUsuarioQuery.uniqueResult();
			
			if (count.intValue() > 0)
			{
				/*
				 * Nesse caso, o usuário não possui todos os produtos.
				 */
			
				String prodQuery = "SELECT p.coProduto FROM ProdutoUsuario as u JOIN u.produto as p "
						+ " JOIN u.usuario as m ";
							
				if (lista != null && !lista.isEmpty())
				{
					prodQuery += " JOIN p.categoriaProduto c WHERE m.coUsuario = :usuario "
							+ " AND c.nuCategoriaProduto in (:list) ";
				}
				
				Query prod = getSession().createQuery(prodQuery);
				if (lista != null && !lista.isEmpty())
				{
					prod.setParameter("usuario", matricula);
					prod.setParameterList("list", lista);
				}
				
				retorno = prod.list();
			} else {
				String prodQuery = "SELECT p.coProduto FROM Produto as p ";
				
				if (lista != null && !lista.isEmpty())
				{
					prodQuery += " JOIN p.categoriaProduto c WHERE c.nuCategoriaProduto in (:list) ";
				}
				
				Query prod = getSession().createQuery(prodQuery);
				if (lista != null && !lista.isEmpty())
				{
					prod.setParameterList("list", lista);
				}
				
				retorno = prod.list();
			}
		
			if (retorno.isEmpty())
			{
				retorno.add("0");
			}
			
			return retorno;
			
		} else {
			/* 
			 * Não possui todos os produtos preferênciais, portanto será feita uma busca
			 * entre os produtos preferênciais do usuário passando como parâmetro as categorias
			 * resultantes do select acima
			 */ 
			
			String sql = "SELECT o.produto.coProduto FROM PreferenciasUsuario o JOIN o.produto as produto WHERE o.usuario.coUsuario =:matricula ";
			
			if (lista != null && !lista.isEmpty())
			{
				sql += " AND produto.categoriaProduto.nuCategoriaProduto in (:list) ";
			}
			Query q = getSession().createQuery(sql);
			q.setParameter("matricula", matricula);
			

			if (lista != null && !lista.isEmpty())
			{
				q.setParameterList("list", lista);
			}
			
			
			/* Caso q.list() for vazia, será retornado uma lista de String com 1 elemento de valor 0
			 * Solução encontrada para que não buscar nenhum contrato já que quando a lista é vazia
			 * significa que o usuário possui todos os produtos
			*/
			
			retorno = q.list();
			
			if (retorno.isEmpty())
			{
				retorno.add("0");
			}
			
			return retorno;			
		}
		
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IPreferenciasUsuarioDAO#getAll(java.lang.String, java.lang.String)
	 */
	public List<PreferenciasUsuario> getAll(String matricula, String categorias) {
		
		String sql = "select pr.* from iacsm001.iactb035_preferencias_usuario as pr " +
				" left join iacsm001.iactb001_produto as p on pr.co_produto = p.co_produto " +
				" where pr.co_usuario = :coUsuario ";
	
		if (Utilities.notEmpty(categorias))
		{
			sql += " AND p.nu_categoria_produto in (:categorias)";
		}
		
		SQLQuery query = getSession().createSQLQuery(sql);
		
		if (Utilities.notEmpty(categorias))
			query.setParameter("categorias", categorias);
		
		query.setParameter("coUsuario", matricula);
		
		query.addEntity(PreferenciasUsuario.class);
		
		return query.list();
	}

	public List<String> listServicoPreferencial(String matricula) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT" );
		sbSQL.append("  tbVerCon.nu_contrato " );
		sbSQL.append("FROM" );
		sbSQL.append("  iacsm001.iactb018_verificacao_contrato tbVerCon INNER JOIN" );
		sbSQL.append("  iacsm001.iactb003_servico_vrfco_produto tvSerVerCon " );
		sbSQL.append("ON tbVerCon.nu_servico_verificacao_produto = tvSerVerCon.nu_servico_verificacao_produto INNER JOIN" );
		sbSQL.append("  iacsm001.iactb002_servico_verificacao tbSerVer " );
		sbSQL.append("ON tbSerVer.nu_servico_verificacao = tvSerVerCon.nu_servico_verificacao INNER JOIN" );
		sbSQL.append("  iacsm001.iactb041_preferencia_usro_srvco tbPrefSerVerUsu " );
		sbSQL.append("ON tbPrefSerVerUsu.nu_servico_verificacao = tvSerVerCon.nu_servico_verificacao " );
		sbSQL.append("WHERE tbPrefSerVerUsu.co_usuario = '"+matricula+"' " );
		sbSQL.append("UNION ( " );
		sbSQL.append("  SELECT" );
		sbSQL.append("    tbVerCon.nu_contrato " );
		sbSQL.append("  FROM" );
		sbSQL.append("    iacsm001.iactb055_vrfco_cntro_prcr tbVerCon INNER JOIN" );
		sbSQL.append("    iacsm001.iactb003_servico_vrfco_produto tvSerVerCon " );
		sbSQL.append("  ON tbVerCon.nu_servico_verificacao_produto = tvSerVerCon.nu_servico_verificacao_produto INNER JOIN" );
		sbSQL.append("    iacsm001.iactb002_servico_verificacao tbSerVer " );
		sbSQL.append("  ON tbSerVer.nu_servico_verificacao = tvSerVerCon.nu_servico_verificacao INNER JOIN" );
		sbSQL.append("    iacsm001.iactb041_preferencia_usro_srvco tbPrefSerVerUsu " );
		sbSQL.append("  ON tbPrefSerVerUsu.nu_servico_verificacao = tvSerVerCon.nu_servico_verificacao " );
		sbSQL.append("  WHERE tbPrefSerVerUsu.co_usuario = '"+matricula+"' " );
		sbSQL.append("  )" );

		
		Query q = getSession().createSQLQuery(sbSQL.toString());
		return q.list();
	}
	
	public List<GerenteUnidade> getListGerentesUnidade(Short nuUnidade) {
		SQLQuery sql = getSession().createSQLQuery("select " +
				"	nu_unidade " +
				"	, nu_matricula " +
				"	, no_pessoa " +
				"	, nu_undde_alcco_u24 " +
				"	, no_funcao " +
				"	, nu_tipo_funcao_h04 " +
				"	, (CASE WHEN (rep.nu_unidade, rep.nu_matricula) IN (SELECT nu_unidade, nu_matricula_h01 AS resultado " +
				"         FROM   icosm001.iacvw001_rspnl_unidade " +
				"         WHERE  ic_eventual = 'S') THEN true " +
				"				                    ELSE false " + 
                "   END) AS eventual  " +
				"from icosm001.iacvw10_gerente_undde rep " +
				"where nu_unidade = :unidade");
		sql.setParameter("unidade", nuUnidade);
		
		sql.addEntity(GerenteUnidade.class);
		return sql.list();
	}
}
