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
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.HistoricoParecerVO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoParecer;
import br.gov.caixa.siiac.persistence.dao.IServicoVerificacaoProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoParecerDAO;

/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class VerificacaoContratoParecerDAO extends GenericDAO<VerificacaoContratoParecer> implements IVerificacaoContratoParecerDAO {

	private static final String ESTADO_CONFORME = "CO";
	private static final String ESTADO_INCONFORME = "IC";
	private IServicoVerificacaoProdutoDAO serVerifProdDAO;
	
	@Autowired
	public void setSerVerifProdDAO(IServicoVerificacaoProdutoDAO serVerifProdDAO) {
		this.serVerifProdDAO = serVerifProdDAO;
	}
	
	public VerificacaoContratoParecerDAO() {
		super(VerificacaoContratoParecer.class);
	}

	public List<VerificacaoContratoVO> listVerificacaoByContrato(Contrato contrato)
			throws DAOException {
	
		Criteria c = getCriteria();
		c.add(Restrictions.eq("nuContrato", contrato.getNuContrato()));
		c.add(Restrictions.eq("icUltimaHierarquia", true));
		c.createAlias("verificacaoContratoPai", "verificacaoContratoPai", CriteriaSpecification.LEFT_JOIN);

		c.addOrder(Order.desc("icEstadoVerificacao"));
		c.addOrder(Order.asc("dtLimiteVerificacao"));
		c.addOrder(Order.asc("nuServicoVerificacaoProduto"));
		
		ProjectionList proj = Projections.projectionList();
		proj.add(Projections.property("nuVerificacaoContrato"))
		.add(Projections.property("nuServicoVerificacaoProduto"))
		.add(Projections.property("icEstadoVerificacao"))
		.add(Projections.property("dtLimiteVerificacao"))
		.add(Projections.property("dtVerificacao"))
		.add(Projections.property("coResponsavelVerificacao"))
		.add(Projections.property("nuChecklistServicoVerificacaoProduto"))
		.add(Projections.property("nuServicoVerificacaoProduto"))
		.add(Projections.property("verificacaoContratoPai.nuVerificacaoContrato"));
		
		List<Object[]> verificacoes = c.setProjection(proj).list();
		List<VerificacaoContratoVO> listVO = new ArrayList<VerificacaoContratoVO>();
		
		for(Object[] verificacao : verificacoes) {
			VerificacaoContratoVO vo = new VerificacaoContratoVO();
					
			vo.setNuVerificacaoContrato((Integer) verificacao[0]);
			ServicoVerificacaoProduto svp = serVerifProdDAO.findById(verificacao[1]);
			if(svp != null) {
				if(svp.getServicoVerificacao() != null) {
					vo.setNoServicoVerificacao(svp.getServicoVerificacao().getNoServicoVerificacao());
				}
			}
			vo.setIcEstadoVerificacao((String) verificacao[2]);
			vo.setDtLimiteVerificacao((Date) verificacao[3]);
			vo.setDtVerificacao((Date) verificacao[4]);
			vo.setCoRspnlVerificacao((String) verificacao[5]);
			vo.setNuChecklistServicoVerificacaoProduto((Integer)verificacao[6]);
			vo.setNuServicoVerificacaoProduto((Integer)verificacao[7]);
			vo.setNuVerificacaoContratoPai((Integer)verificacao[8]);
			listVO.add(vo);
		}
		
		return listVO;
	}
	
	public int getQtdVerificacaoApontamentoConforme(Integer nuContrato) {
		Criteria c = getCriteria();
		
		c.add(Restrictions.eq("nuContrato", nuContrato));
		c.add(Restrictions.ilike("icEstadoVerificacao", ESTADO_CONFORME, MatchMode.ANYWHERE));
		
		return (Integer)c.setProjection(Projections.rowCount()).uniqueResult();
	}
	
	public int getQtdVerificacaoApontamentoInconforme(Integer nuContrato) {
		Criteria c = getCriteria();
		
		c.add(Restrictions.eq("nuContrato", nuContrato));
		c.add(Restrictions.ilike("icEstadoVerificacao", ESTADO_INCONFORME, MatchMode.ANYWHERE));
		
		return (Integer)c.setProjection(Projections.rowCount()).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoParecerDAO#getListHistoricoParecer(java.lang.Integer, java.lang.Integer)
	 */
	public List<HistoricoParecerVO> getListHistoricoParecer(Integer nuContrato, Integer nuServicoVerificacaoProduto) {
		String sql = 
				"	SELECT verificacao.nu_verificacao_contrato " +
				"	      ,verificacao.nu_verificacao_contrato_pai " +
				"	      ,verificacao.dt_verificacao " +
				"	      ,verificacao.ic_estado_verificacao " +
				"	      ,verificacao.co_rspnl_verificacao" +
				"	      ,parecer.nu_parecer" +
				"	      ,parecer.aa_parecer" +
				"	      ,contrato.co_contrato_conta" +
				"	      ,parecer.nu_unidade" +
				" 	FROM iacsm001.iactb055_vrfco_cntro_prcr verificacao" +
				" 	INNER JOIN iacsm001.iactb003_servico_vrfco_produto svp ON svp.nu_servico_verificacao_produto = verificacao.nu_servico_verificacao_produto " +
				"	INNER JOIN iacsm001.iactb016_parecer parecer ON parecer.nu_verificacao_contrato = verificacao.nu_verificacao_contrato" +
				"	INNER JOIN iacsm001.iactb004_contrato contrato ON contrato.nu_contrato = verificacao.nu_contrato" +
				"	WHERE verificacao.nu_servico_verificacao_produto = :nu_servico_verificacao_produto " +
				"	AND verificacao.nu_contrato = :nu_contrato " +
				"   ORDER BY parecer.dt_parecer DESC";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_servico_verificacao_produto", nuServicoVerificacaoProduto);
		query.setParameter("nu_contrato", nuContrato);
		
		query.addEntity(HistoricoParecerVO.class);
		return query.list();
	}
	
	public VerificacaoContratoVO verificacaoByContrato(Contrato contrato) {
		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT this_.nu_verificacao_contrato        AS nuVerificacaoContrato, ");
		var1.append("       sv2_.no_servico_verificacao          AS noServicoVerificacao, ");
		var1.append("       this_.ic_estado_verificacao          AS icEstadoVerificacao, ");
		var1.append("       this_.dt_limite_verificacao          AS dtLimiteVerificacao, ");
		var1.append("       this_.dt_verificacao                 AS dtVerificacao, ");
		var1.append("       this_.co_rspnl_verificacao           AS coRspnlVerificacao, ");
		var1.append("       svp1_.nu_servico_verificacao_produto AS ");
		var1.append("       nuServicoVerificacaoProduto, ");
		var1.append("       this_.nu_verificacao_contrato_pai    AS nuVerificacaoContratoPai, ");
		var1.append("       chk3_.nu_chklst_srvco_vrfco_produto  AS nuChecklistServicoVerificacaoProduto ");
		var1.append("FROM   iacsm001.iactb055_vrfco_cntro_prcr this_ ");
		var1.append("       inner join iacsm001.iactb003_servico_vrfco_produto svp1_ ");
		var1.append("               ON ");
		var1.append("this_.nu_servico_verificacao_produto = svp1_.nu_servico_verificacao_produto ");
		var1.append("inner join iacsm001.iactb002_servico_verificacao sv2_ ");
		var1.append("        ON svp1_.nu_servico_verificacao = sv2_.nu_servico_verificacao ");
		var1.append("inner join iacsm001.iactb004_contrato c4_ ");
		var1.append("        ON this_.nu_contrato = c4_.nu_contrato ");
		var1.append("left join iacsm001.iactb005_chklst_srvco_produto chk3_ ");
		var1.append("        ON ");
		var1.append("this_.nu_chklst_srvco_vrfco_produto = chk3_.nu_chklst_srvco_vrfco_produto ");
		var1.append("WHERE  c4_.nu_contrato = :nuContrato AND ic_ultima_hierarquia = true ");
		var1.append("ORDER  BY icestadoverificacao ASC, ");
		var1.append("          svp1_.nu_servico_verificacao_produto ASC ");
		
		SQLQuery query = getSession().createSQLQuery(var1.toString());
		query.setParameter("nuContrato", contrato.getNuContrato());
		query.addEntity(VerificacaoContratoVO.class);
		
		return (VerificacaoContratoVO) query.uniqueResult();
	}
	
}