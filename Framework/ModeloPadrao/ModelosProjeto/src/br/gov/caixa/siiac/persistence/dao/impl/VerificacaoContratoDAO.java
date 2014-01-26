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

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.SituacaoVerificacaoVO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoDAO;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class VerificacaoContratoDAO extends GenericDAO<VerificacaoContrato> implements IVerificacaoContratoDAO {

	private static final String ESTADO_NAO_VERIFICADA = "NV";
	
	public VerificacaoContratoDAO() {
		super(VerificacaoContrato.class);
	}

	public ChecklistServicoVerificacaoProduto checklistByNuServico(Integer nuServico) {

			Query q  = getSession().createQuery("FROM ChecklistServicoVerificacaoProduto " +
					"as chk WHERE chk.icSituacao = 'PUBLICADO' AND chk.icRevogado = false AND chk.dataInicio <= :dtIni" +  
					" AND chk.servicoVerificacaoProduto = :nuServ");
		
			q.setParameter("nuServ", nuServico);
			q.setParameter("dtIni", new Date());
			
			List list = q.list();
			
			if(list != null && list.size() > 0) {
				return (ChecklistServicoVerificacaoProduto) list.get(0);
			} else {
				return null;
			}

	}
	
	public boolean existeChecklistByNuServico(Integer nuServico) {

		Query q  = getSession().createQuery("SELECT COUNT(chk) FROM ChecklistServicoVerificacaoProduto " +
				"as chk WHERE chk.icSituacao = 'PUBLICADO' AND chk.icRevogado = false AND chk.dataInicio <= :dtIni" +  
				" AND chk.servicoVerificacaoProduto = :nuServ");
	
		q.setParameter("nuServ", nuServico);
		q.setParameter("dtIni", new Date());
		
		Long count = (Long) q.uniqueResult();
			
		return count > 0;

}
	
	public List<VerificacaoContratoVO> listVerificacaoByContrato(Contrato contrato) {
		Criteria c = getCriteria();
		ProjectionList proj = Projections.projectionList();
		
		c.createAlias("servicoVerificacaoProduto", "svp");
		c.createAlias("svp.servicoVerificacao", "sv");
		c.createAlias("checklist", "chk", Criteria.LEFT_JOIN);
		c.add(Restrictions.eq("contrato", contrato));
		
		c.addOrder(Order.asc("dtLimiteVerificacao"));
		c.addOrder(Order.asc("svp.nuServicoVerificacaoProduto"));
		
		proj.add(Projections.property("nuVerificacaoContrato").as("nuVerificacaoContrato"))
		.add(Projections.property("sv.noServicoVerificacao").as("noServicoVerificacao"))
		.add(Projections.property("icEstadoVerificacao").as("icEstadoVerificacao"))
		.add(Projections.property("dtLimiteVerificacao").as("dtLimiteVerificacao"))
		.add(Projections.property("dtVerificacao").as("dtVerificacao"))
		.add(Projections.property("coRspnlVerificacao").as("coRspnlVerificacao"))
		.add(Projections.property("svp.nuServicoVerificacaoProduto").as("nuServicoVerificacaoProduto"))
		.add(Projections.property("nuVerificacaoContratoPai").as("nuVerificacaoContratoPai"))
		.add(Projections.property("chk.nuChecklistServicoVerificacaoProduto").as("nuChecklistServicoVerificacaoProduto"));
		
		c.setProjection(proj);
		c.setResultTransformer(new AliasToBeanResultTransformer(VerificacaoContratoVO.class));
		
		return c.list();
	}
	
	public VerificacaoContratoVO verificacaoByContrato(Contrato contrato) {
		Criteria c = getCriteria();
		ProjectionList proj = Projections.projectionList();
		
		c.createAlias("servicoVerificacaoProduto", "svp");
		c.createAlias("svp.servicoVerificacao", "sv");
		c.createAlias("checklist", "chk", Criteria.LEFT_JOIN);
		c.createAlias("contrato", "c");
		c.add(Restrictions.eq("c.nuContrato", contrato.getNuContrato()));
		
		c.addOrder(Order.asc("dtLimiteVerificacao"));
		c.addOrder(Order.asc("svp.nuServicoVerificacaoProduto"));
		
		proj.add(Projections.property("nuVerificacaoContrato").as("nuVerificacaoContrato"))
		.add(Projections.property("sv.noServicoVerificacao").as("noServicoVerificacao"))
		.add(Projections.property("icEstadoVerificacao").as("icEstadoVerificacao"))
		.add(Projections.property("dtLimiteVerificacao").as("dtLimiteVerificacao"))
		.add(Projections.property("dtVerificacao").as("dtVerificacao"))
		.add(Projections.property("coRspnlVerificacao").as("coRspnlVerificacao"))
		.add(Projections.property("svp.nuServicoVerificacaoProduto").as("nuServicoVerificacaoProduto"))
		.add(Projections.property("nuVerificacaoContratoPai").as("nuVerificacaoContratoPai"))
		.add(Projections.property("chk.nuChecklistServicoVerificacaoProduto").as("nuChecklistServicoVerificacaoProduto"));
		
		c.setProjection(proj);
		c.setResultTransformer(new AliasToBeanResultTransformer(VerificacaoContratoVO.class));
		
		return (VerificacaoContratoVO) c.uniqueResult();
	}
	
	public int getQtdVerificacaoApontamentoNaoVerificada(Integer nuContrato) {
		Criteria c = getCriteria();
		c.createAlias("contrato", "c");
		c.add(Restrictions.eq("c.nuContrato", nuContrato));
		c.add(Restrictions.ilike("icEstadoVerificacao", ESTADO_NAO_VERIFICADA, MatchMode.EXACT));
		
		return (Integer)c.setProjection(Projections.rowCount()).uniqueResult();
	}
	
	public String getSituacaoContrato(Integer nuContrato)
	{
		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT CASE ");
		var1.append("         WHEN ( ");
		/* SITUAÇÃO A VERIFICAR - sem ícone */
		var1.append("              SELECT Count(*) ");
		var1.append("               FROM   iacsm001.iactb004_contrato c ");
		var1.append("               WHERE  nu_contrato = :nuContrato ");
		var1.append("                      AND EXISTS (SELECT nu_contrato ");
		var1.append("                                  FROM   iacsm001.iactb018_verificacao_contrato ");
		var1.append("                                  WHERE  nu_contrato = c.nu_contrato ");
		var1.append("                                         AND ic_estado_verificacao = 'NV' ");
		var1.append("                                         AND ic_ultima_hierarquia = true) ");
		var1.append("                      AND NOT EXISTS (SELECT * ");
		var1.append("                                      FROM   iacsm001.iactb055_vrfco_cntro_prcr ");
		var1.append("                                      WHERE  nu_contrato = c.nu_contrato ");
		var1.append("                                             AND ic_ultima_hierarquia = true)) > ");
		var1.append("              0 THEN ");
		var1.append("         'NV' ");
		var1.append("         WHEN ( ");
		
		/* SITUAÇÃO VERIFICAÇÃO PARCIAL - ícone placa amarela com ? no meio */
		var1.append("              SELECT Count(*) ");
		var1.append("               FROM   iacsm001.iactb004_contrato c ");
		var1.append("               WHERE  nu_contrato = :nuContrato ");
		var1.append("                      AND EXISTS (SELECT nu_contrato ");
		var1.append("                                  FROM   iacsm001.iactb018_verificacao_contrato ");
		var1.append("                                  WHERE  nu_contrato = c.nu_contrato ");
		var1.append("                                         AND ic_estado_verificacao = 'NV' ");
		var1.append("                                         AND ic_ultima_hierarquia = true) ");
		var1.append("                      AND EXISTS (SELECT * ");
		var1.append("                                  FROM   iacsm001.iactb055_vrfco_cntro_prcr ");
		var1.append("                                  WHERE  nu_contrato = c.nu_contrato ");
		var1.append("                                         AND ic_ultima_hierarquia = true)) > 0 ");
		var1.append("       THEN ");
		var1.append("         'VP' ");
		var1.append("         WHEN ( ");
		
		/* SITUAÇÃO CONFORME - ícone check verde */ 
		var1.append("              SELECT Count(*) ");
		var1.append("               FROM   iacsm001.iactb004_contrato c ");
		var1.append("               WHERE  nu_contrato = :nuContrato ");
		var1.append("                      AND EXISTS (SELECT * ");
		var1.append("                                  FROM   iacsm001.iactb055_vrfco_cntro_prcr ");
		var1.append("                                  WHERE  nu_contrato = c.nu_contrato ");
		var1.append("                                         AND ic_ultima_hierarquia = true ");
		var1.append("                                         AND ic_estado_verificacao = 'CO') ");
		var1.append("                      AND NOT EXISTS (SELECT * ");
		var1.append("                                      FROM   iacsm001.iactb055_vrfco_cntro_prcr ");
		var1.append("                                      WHERE  nu_contrato = c.nu_contrato ");
		var1.append("                                             AND ic_ultima_hierarquia = true ");
		var1.append("                                             AND ic_estado_verificacao = 'IC') ");
		var1.append("                      AND NOT EXISTS ");
		var1.append("                          (SELECT * ");
		var1.append("                           FROM   iacsm001.iactb018_verificacao_contrato ");
		var1.append("                           WHERE  nu_contrato = c.nu_contrato ");
		var1.append("                                  AND ic_ultima_hierarquia = true)) > 0 THEN ");
		var1.append("         'CO' ");
		var1.append("         WHEN ( ");
		
		/* SITUAÇÃO INCONFORME - ícone check vermelho */ 
		var1.append("              SELECT Count(*) ");
		var1.append("               FROM   iacsm001.iactb004_contrato c ");
		var1.append("               WHERE  nu_contrato = :nuContrato ");
		var1.append("                      AND EXISTS (SELECT * ");
		var1.append("                                  FROM   iacsm001.iactb055_vrfco_cntro_prcr ");
		var1.append("                                  WHERE  nu_contrato = c.nu_contrato ");
		var1.append("                                         AND ic_ultima_hierarquia = true ");
		var1.append("                                         AND ic_estado_verificacao = 'IC') ");
		var1.append("                      AND NOT EXISTS ");
		var1.append("                          (SELECT * ");
		var1.append("                           FROM   iacsm001.iactb018_verificacao_contrato ");
		var1.append("                           WHERE  nu_contrato = c.nu_contrato ");
		var1.append("                                  AND ic_ultima_hierarquia = true)) > 0 THEN ");
		var1.append("         'IC' ");
		var1.append("         ELSE '' ");
		var1.append("       END AS TIPO ");
		
		SQLQuery q = getSession().createSQLQuery(var1.toString());
		q.setParameter("nuContrato", nuContrato);
		
		return (String) q.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoDAO#getQtdVerificacaoSituacao(java.lang.Integer)
	 */
	public SituacaoVerificacaoVO getQtdVerificacaoSituacaoParecer(Integer nuContrato) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT ");
		sql.append(" COALESCE(SUM(CASE WHEN tb013.ic_resultado_aptmnto_chklst = '0' THEN 1 ELSE 0 END), 0) nv, ");
		sql.append(" COALESCE(SUM(CASE WHEN tb013.ic_resultado_aptmnto_chklst = '1' THEN 1 ELSE 0 END), 0) co, ");
		sql.append(" COALESCE(SUM(CASE WHEN tb013.ic_resultado_aptmnto_chklst = '2' THEN 1 ELSE 0 END), 0) ic, ");
		sql.append(" (SELECT COUNT(*) FROM iacsm001.iactb055_vrfco_cntro_prcr WHERE nu_contrato = :nuContrato AND ic_estado_verificacao = 'CO' AND ic_ultima_hierarquia = true) as coparecer, ");
		sql.append(" (SELECT COUNT(*) FROM iacsm001.iactb055_vrfco_cntro_prcr WHERE nu_contrato = :nuContrato AND ic_estado_verificacao = 'IC' AND ic_ultima_hierarquia = true) as icparecer ");

		sql.append(" FROM iacsm001.iactb013_rslto_aptmnto_produto tb013 ");
		sql.append(" INNER JOIN iacsm001.iactb018_verificacao_contrato tb018 ON tb018.nu_verificacao_contrato = tb013.nu_verificacao_contrato ");
		sql.append(" INNER JOIN iacsm001.iactb012_aptmnto_chklst_produto tb012 ON tb013.nu_aptmnto_chklst_produto = tb012.nu_aptmnto_chklst_produto ");
		sql.append(" INNER JOIN iacsm001.iactb011_item_vrfco_cklst_prdto tb011 ON tb011.nu_item_chklst_srvco_vrfco_prdt = tb012.nu_item_chklst_srvco_vrfco_prdt ");
		sql.append(" LEFT JOIN iacsm001.iactb056_vrfco_cntro_obsro tb056_bloco ");
		sql.append("     ON tb056_bloco.nu_blco_chklst_srvco_vrfco_prdt = ");
		sql.append("        tb011.nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("     AND tb056_bloco.nu_verificacao_contrato = ");
		sql.append("         tb013.nu_verificacao_contrato ");
		sql.append("     AND tb056_bloco.ic_fonte = 'B' ");
		sql.append(" LEFT JOIN iacsm001.iactb056_vrfco_cntro_obsro tb056_item ");
		sql.append("     ON tb056_item.nu_item_chklst_srvco_vrfco_prdt = ");
		sql.append("        tb011.nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("     AND tb056_item.nu_verificacao_contrato = ");
		sql.append("         tb013.nu_verificacao_contrato ");
		sql.append("     AND tb056_item.ic_fonte = 'I' ");
		sql.append(" WHERE tb018.nu_contrato = :nuContrato ");
		sql.append("       AND ( tb056_bloco.ic_desabilitado IS NULL OR tb056_bloco.ic_desabilitado = false ) ");
		sql.append("       AND ( tb056_item.ic_desabilitado IS NULL OR tb056_item.ic_desabilitado = false ) ");
		
		SQLQuery q = getSession().createSQLQuery(sql.toString());
		q.setParameter("nuContrato", nuContrato);
		
		Object[] obj = (Object[]) q.uniqueResult();
		
		SituacaoVerificacaoVO situacao = new SituacaoVerificacaoVO();
		
		if (Utilities.notEmpty(obj[0]))
			situacao.setNaoVerificado(Integer.parseInt(obj[0].toString()));
		else
			situacao.setNaoVerificado(0);
		
		if (Utilities.notEmpty(obj[1]))
			situacao.setConforme(Integer.parseInt(obj[1].toString()));
		else
			situacao.setConforme(0);
		
		if (Utilities.notEmpty(obj[2]))
			situacao.setInconforme(Integer.parseInt(obj[2].toString()));
		else
			situacao.setInconforme(0);
		
		if (Utilities.notEmpty(obj[3]))
			situacao.setConformeParecer(Integer.parseInt(obj[3].toString()));
		else
			situacao.setConformeParecer(0);
		
		if (Utilities.notEmpty(obj[4]))
			situacao.setInconformeParecer(Integer.parseInt(obj[4].toString()));
		else
			situacao.setInconformeParecer(0);
		
		return situacao;
	}
	
	public void delete (Integer nuContrato)
	{
		//Deletando as Observações
		String sqlObs = "DELETE FROM iacsm001.iactb056_vrfco_cntro_obsro WHERE nu_verificacao_contrato in (SELECT nu_verificacao_contrato FROM iacsm001.iactb018_verificacao_contrato WHERE nu_contrato = :nuContrato)";
		SQLQuery queryObs = getSession().createSQLQuery(sqlObs);
		queryObs.setParameter("nuContrato", nuContrato);
		
		queryObs.executeUpdate();
		
		//Deletando as informações de Verificação Contrato
		String sql = "FROM VerificacaoContrato v where v.contrato.nuContrato = :nuContrato";
			
		Query query = getSession().createQuery(sql);
		query.setParameter("nuContrato", nuContrato);
		
		for (VerificacaoContrato vc : (List<VerificacaoContrato>) query.list()) {
			delete(vc);
		}
	}
}