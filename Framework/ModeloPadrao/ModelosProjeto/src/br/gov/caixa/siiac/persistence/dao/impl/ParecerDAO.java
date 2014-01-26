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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.ResumoParecerVO;
import br.gov.caixa.siiac.model.TagListaItemDataValidadeVO;
import br.gov.caixa.siiac.model.domain.ControleNumeroParecer;
import br.gov.caixa.siiac.model.domain.Parecer;
import br.gov.caixa.siiac.model.domain.ResultadoApontamentoProduto;
import br.gov.caixa.siiac.persistence.dao.IParecerDAO;

@Repository
@Scope("prototype")
public class ParecerDAO extends GenericDAO<Parecer> implements IParecerDAO {

	public ParecerDAO() {
		super(Parecer.class);
	}
	
	/**
	 * * @see br.gov.caixa.siiac.persistence.dao.IParecerDAO#getResumoAntesParecerVOList(Integer nuVerificacaoContrato, Boolean conformes)
     */
	public List<ResumoParecerVO> getResumoAntesParecerVOList(Integer nuVerificacaoContrato, Boolean conformes) {
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT tb010.nu_blco_chklst_srvco_vrfco_prdt id_bloco, ");
		sql.append("       tb011.nu_item_chklst_srvco_vrfco_prdt id_item, ");
		sql.append("       tb012.nu_aptmnto_chklst_produto       id_apontamento, ");
		sql.append(" 	   CASE ");
		sql.append(" 		WHEN tb006.no_resumido_bloco_checklist <> '' THEN tb006.no_resumido_bloco_checklist ");
		sql.append(" 		ELSE  tb006.no_bloco_checklist ");
		sql.append(" 		END no_bloco, ");
		sql.append(" 	   CASE ");
		sql.append(" 		WHEN tb007.no_rsmdo_item_vrfco_checklist <> '' THEN tb007.no_rsmdo_item_vrfco_checklist ");
		sql.append(" 		ELSE  tb007.no_item_vrfco_checklist ");
		sql.append(" 		END no_item, ");
		sql.append("       tb008.no_aptmnto_checklist            no_apontamento, ");
		sql.append("       tb056_bloco.de_observacao             obs_bloco, ");
		sql.append("       tb056_item.de_observacao              obs_item, ");
		sql.append("       tb013.de_observacao                   obs_apontamento ");
		sql.append("FROM   iacsm001.iactb013_rslto_aptmnto_produto tb013 ");
		sql.append("       INNER JOIN iacsm001.iactb012_aptmnto_chklst_produto tb012 ");
		sql.append("               ON tb012.nu_aptmnto_chklst_produto = ");
		sql.append("                  tb013.nu_aptmnto_chklst_produto ");
		sql.append("       INNER JOIN iacsm001.iactb008_apontamento_checklist tb008 ");
		sql.append("               ON tb008.nu_aptmnto_checklist = tb012.nu_aptmnto_checklist ");
		sql.append("       INNER JOIN iacsm001.iactb011_item_vrfco_cklst_prdto tb011 ");
		sql.append("               ON tb011.nu_item_chklst_srvco_vrfco_prdt = ");
		sql.append("                  tb012.nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("       INNER JOIN iacsm001.iactb007_item_verificacao tb007 ");
		sql.append("               ON tb007.nu_item_vrfco_checklist = tb011.nu_item_vrfco_checklist ");
		sql.append("       INNER JOIN iacsm001.iactb010_bloco_chklst_produto tb010 ");
		sql.append("               ON tb010.nu_blco_chklst_srvco_vrfco_prdt = ");
		sql.append("                  tb011.nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("       INNER JOIN iacsm001.iactb006_bloco_checklist tb006 ");
		sql.append("               ON tb006.nu_bloco_checklist = tb010.nu_bloco_checklist ");
		sql.append("       LEFT JOIN iacsm001.iactb056_vrfco_cntro_obsro tb056_bloco ");
		sql.append("              ON tb056_bloco.nu_blco_chklst_srvco_vrfco_prdt = ");
		sql.append("                           tb011.nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("                 AND tb056_bloco.nu_verificacao_contrato = ");
		sql.append("                     tb013.nu_verificacao_contrato ");
		sql.append("                 AND tb056_bloco.ic_fonte = 'B' ");
		sql.append("       LEFT JOIN iacsm001.iactb056_vrfco_cntro_obsro tb056_item ");
		sql.append("              ON tb056_item.nu_item_chklst_srvco_vrfco_prdt = ");
		sql.append("                           tb011.nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("                 AND tb056_item.nu_verificacao_contrato = ");
		sql.append("                     tb013.nu_verificacao_contrato ");
		sql.append("                 AND tb056_item.ic_fonte = 'I' ");
		sql.append("WHERE  ");
		
		// Verifica se está trabalhando apenas com os conformes ou inconformes
		if(conformes){
			sql.append(" tb013.ic_resultado_aptmnto_chklst = '" + ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_CONFORME + "' "); 
		}else{
			sql.append(" tb013.ic_resultado_aptmnto_chklst = '" + ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_INCONFORME + "' ");
		}
		
		sql.append("       AND ( tb056_bloco.ic_desabilitado IS NULL ");
		sql.append("              OR tb056_bloco.ic_desabilitado = false ) ");
		sql.append("       AND ( tb056_item.ic_desabilitado IS NULL ");
		sql.append("              OR tb056_item.ic_desabilitado = false ) ");

		// Se estiver trabalhando com resultados conformes, só devem ser listados os que tiverem observação no bloco/item/apontamento. 
		if(conformes){
			sql.append("       AND ( ( tb056_bloco.de_observacao IS NOT NULL ");
			sql.append("               AND tb056_bloco.de_observacao != '' ) ");
			sql.append("              OR ( tb056_item.de_observacao IS NOT NULL ");
			sql.append("                   AND tb056_item.de_observacao != '' ) ");
			sql.append("              OR ( tb013.de_observacao IS NOT NULL ");
			sql.append("                   AND tb013.de_observacao != '' ) ) ");
		}
		
		sql.append("       AND tb013.nu_verificacao_contrato = :nu_verificacao_contrato ");
		sql.append("ORDER  BY tb010.nu_ordem_bloco_chklst, ");
		sql.append("          id_bloco, ");
		sql.append("          tb011.nu_ordem_item_vrfco_chklst, ");
		sql.append("          id_item, ");
		sql.append("          tb012.nu_ordem_aptmnto_chklst, ");
		sql.append("          id_apontamento ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter("nu_verificacao_contrato", nuVerificacaoContrato);
		query.addEntity(ResumoParecerVO.class);
		return query.list();
	}

	public List<TagListaItemDataValidadeVO> getListaItensComDataValidade(Integer nuVerificacaoContrato)
	{
		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT DISTINCT ON (tb010.nu_ordem_bloco_chklst, id_bloco, ");
		var1.append("tb011.nu_ordem_item_vrfco_chklst, id_item) tb010.nu_blco_chklst_srvco_vrfco_prdt id_bloco, ");
		var1.append("tb011.nu_item_chklst_srvco_vrfco_prdt id_item, ");
		var1.append(" 	   CASE ");
		var1.append(" 		WHEN tb006.no_resumido_bloco_checklist <> '' THEN tb006.no_resumido_bloco_checklist ");
		var1.append(" 		ELSE  tb006.no_bloco_checklist ");
		var1.append(" 		END no_bloco, ");
		var1.append(" 	   CASE ");
		var1.append(" 		WHEN tb007.no_rsmdo_item_vrfco_checklist <> '' THEN tb007.no_rsmdo_item_vrfco_checklist ");
		var1.append(" 		ELSE  tb007.no_item_vrfco_checklist ");
		var1.append(" 		END no_item, ");
		var1.append(" tb058_bloco.de_observacao             obs_bloco, ");
		var1.append(" tb058_item.de_observacao              obs_item, ");
		var1.append(" tb058_item.dt_validade                dt_validade ");
		var1.append(" FROM   iacsm001.iactb057_rslto_aptmnto_prdto_pr tb057 ");
		var1.append("       INNER JOIN iacsm001.iactb012_aptmnto_chklst_produto tb012 ");
		var1.append("               ON tb012.nu_aptmnto_chklst_produto = ");
		var1.append("                  tb057.nu_aptmnto_chklst_produto ");
		var1.append("       INNER JOIN iacsm001.iactb011_item_vrfco_cklst_prdto tb011 ");
		var1.append("               ON tb011.nu_item_chklst_srvco_vrfco_prdt = ");
		var1.append("                  tb012.nu_item_chklst_srvco_vrfco_prdt ");
		var1.append("       INNER JOIN iacsm001.iactb007_item_verificacao tb007 ");
		var1.append("               ON tb007.nu_item_vrfco_checklist = tb011.nu_item_vrfco_checklist ");
		var1.append("       INNER JOIN iacsm001.iactb010_bloco_chklst_produto tb010 ");
		var1.append("               ON tb010.nu_blco_chklst_srvco_vrfco_prdt = ");
		var1.append("                  tb011.nu_blco_chklst_srvco_vrfco_prdt ");
		var1.append("       INNER JOIN iacsm001.iactb006_bloco_checklist tb006 ");
		var1.append("               ON tb006.nu_bloco_checklist = tb010.nu_bloco_checklist ");
		var1.append("       LEFT JOIN iacsm001.iactb058_vrfco_cntro_obsro_prcr tb058_bloco ");
		var1.append("              ON tb058_bloco.nu_blco_chklst_srvco_vrfco_prdt = ");
		var1.append("                           tb011.nu_blco_chklst_srvco_vrfco_prdt ");
		var1.append("                 AND tb058_bloco.nu_verificacao_contrato = ");
		var1.append("                     tb057.nu_verificacao_contrato ");
		var1.append("                 AND tb058_bloco.ic_fonte = 'B' ");
		var1.append("       LEFT JOIN iacsm001.iactb058_vrfco_cntro_obsro_prcr tb058_item ");
		var1.append("              ON tb058_item.nu_item_chklst_srvco_vrfco_prdt = ");
		var1.append("                           tb011.nu_item_chklst_srvco_vrfco_prdt ");
		var1.append("                 AND tb058_item.nu_verificacao_contrato = ");
		var1.append("                     tb057.nu_verificacao_contrato ");
		var1.append("                 AND tb058_item.ic_fonte = 'I' ");
		var1.append("WHERE  tb057.nu_verificacao_contrato = :nuVerificacaoContrato ");
		var1.append("       AND ( tb058_bloco.ic_desabilitado IS NULL ");
		var1.append("              OR tb058_bloco.ic_desabilitado = false ) ");
		var1.append("       AND ( tb058_item.ic_desabilitado IS NULL ");
		var1.append("              OR tb058_item.ic_desabilitado = false ) ");
		var1.append("       AND tb058_item.dt_validade IS NOT NULL ");
		var1.append("ORDER  BY tb010.nu_ordem_bloco_chklst, ");
		var1.append("          id_bloco, ");
		var1.append("          tb011.nu_ordem_item_vrfco_chklst, ");
		var1.append("          id_item ");
		
		
		SQLQuery query = getSession().createSQLQuery(var1.toString());
		query.setParameter("nuVerificacaoContrato", nuVerificacaoContrato);
		query.addEntity(TagListaItemDataValidadeVO.class);
		return query.list();
	}
	
	/**
	 * * @see br.gov.caixa.siiac.persistence.dao.IParecerDAO#getResumoDepoisParecerVOList(Integer nuVerificacaoContrato, Boolean conformes)
     */
	public List<ResumoParecerVO> getResumoDepoisParecerVOList(Integer nuVerificacaoContrato, Boolean conformes) {
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT tb010.nu_blco_chklst_srvco_vrfco_prdt id_bloco, ");
		sql.append("       tb011.nu_item_chklst_srvco_vrfco_prdt id_item, ");
		sql.append("       tb012.nu_aptmnto_chklst_produto       id_apontamento, ");
		sql.append(" 	   CASE ");
		sql.append(" 		WHEN tb006.no_resumido_bloco_checklist <> '' THEN tb006.no_resumido_bloco_checklist ");
		sql.append(" 		ELSE  tb006.no_bloco_checklist ");
		sql.append(" 		END no_bloco, ");
		sql.append(" 	   CASE ");
		sql.append(" 		WHEN tb007.no_rsmdo_item_vrfco_checklist <> '' THEN tb007.no_rsmdo_item_vrfco_checklist ");
		sql.append(" 		ELSE  tb007.no_item_vrfco_checklist ");
		sql.append(" 		END no_item, ");
		sql.append("       tb008.no_aptmnto_checklist            no_apontamento, ");
		sql.append("       tb058_bloco.de_observacao             obs_bloco, ");
		sql.append("       tb058_item.de_observacao              obs_item, ");
		sql.append("       tb057.de_observacao                   obs_apontamento ");
		sql.append("FROM   iacsm001.iactb057_rslto_aptmnto_prdto_pr tb057 ");
		sql.append("       INNER JOIN iacsm001.iactb012_aptmnto_chklst_produto tb012 ");
		sql.append("               ON tb012.nu_aptmnto_chklst_produto = tb057.nu_aptmnto_chklst_produto ");
		sql.append("       INNER JOIN iacsm001.iactb008_apontamento_checklist tb008 ");
		sql.append("               ON tb008.nu_aptmnto_checklist = tb012.nu_aptmnto_checklist ");
		sql.append("       INNER JOIN iacsm001.iactb011_item_vrfco_cklst_prdto tb011 ");
		sql.append("               ON tb011.nu_item_chklst_srvco_vrfco_prdt = tb012.nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("       INNER JOIN iacsm001.iactb007_item_verificacao tb007 ");
		sql.append("               ON tb007.nu_item_vrfco_checklist = tb011.nu_item_vrfco_checklist ");
		sql.append("       INNER JOIN iacsm001.iactb010_bloco_chklst_produto tb010 ");
		sql.append("               ON tb010.nu_blco_chklst_srvco_vrfco_prdt = tb011.nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("       INNER JOIN iacsm001.iactb006_bloco_checklist tb006 ");
		sql.append("               ON tb006.nu_bloco_checklist = tb010.nu_bloco_checklist ");
		sql.append("       LEFT JOIN iacsm001.iactb058_vrfco_cntro_obsro_prcr tb058_bloco ");
		sql.append("              ON tb058_bloco.nu_blco_chklst_srvco_vrfco_prdt = tb011.nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("                 AND tb058_bloco.nu_verificacao_contrato = tb057.nu_verificacao_contrato ");
		sql.append("                 AND tb058_bloco.ic_fonte = 'B' ");
		sql.append("       LEFT JOIN iacsm001.iactb058_vrfco_cntro_obsro_prcr tb058_item ");
		sql.append("              ON tb058_item.nu_item_chklst_srvco_vrfco_prdt = tb011.nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("                 AND tb058_item.nu_verificacao_contrato = tb057.nu_verificacao_contrato ");
		sql.append("                 AND tb058_item.ic_fonte = 'I' ");
		sql.append("WHERE  ");
		
		// Verifica se está trabalhando apenas com os conformes ou inconformes
		if(conformes){
			sql.append(" tb057.ic_resultado_aptmnto_chklst = '" + ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_CONFORME + "' "); 
		}else{
			sql.append(" tb057.ic_resultado_aptmnto_chklst = '" + ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_INCONFORME + "' ");
		}
		
		sql.append("       AND ( tb058_bloco.ic_desabilitado IS NULL ");
		sql.append("              OR tb058_bloco.ic_desabilitado = false ) ");
		sql.append("       AND ( tb058_item.ic_desabilitado IS NULL ");
		sql.append("              OR tb058_item.ic_desabilitado = false ) ");

		// Se estiver trabalhando com resultados conformes, só devem ser listados os que tiverem observação no bloco/item/apontamento. 
		if(conformes){
			sql.append("       AND ( ( tb058_bloco.de_observacao IS NOT NULL ");
			sql.append("               AND tb058_bloco.de_observacao != '' ) ");
			sql.append("              OR ( tb058_item.de_observacao IS NOT NULL ");
			sql.append("                   AND tb058_item.de_observacao != '' ) ");
			sql.append("              OR ( tb057.de_observacao IS NOT NULL ");
			sql.append("                   AND tb057.de_observacao != '' ) ) ");
		}
		
		sql.append("       AND tb057.nu_verificacao_contrato = :nu_verificacao_contrato ");
		sql.append("ORDER  BY tb010.nu_ordem_bloco_chklst, ");
		sql.append("          id_bloco, ");
		sql.append("          tb011.nu_ordem_item_vrfco_chklst, ");
		sql.append("          id_item, ");
		sql.append("          tb012.nu_ordem_aptmnto_chklst, ");
		sql.append("          id_apontamento ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter("nu_verificacao_contrato", nuVerificacaoContrato);
		query.addEntity(ResumoParecerVO.class);
		return query.list();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IParecerDAO#getLastId(java.lang.Integer, java.lang.Short, java.lang.Integer)
	 */
	public Integer getLastId(Short aaParecer, Short nuUnidade, Integer nuNatural) {
		Criteria crit = getSession().createCriteria(ControleNumeroParecer.class)
						.add(Restrictions.eq("id.aaParecer", aaParecer))
						.add(Restrictions.eq("id.nuUnidade", nuUnidade))
						.add(Restrictions.eq("id.nuNatural", nuNatural))
						.setProjection(Projections.max("id.nuParecer"));
		return (Integer) crit.uniqueResult();
	}
}