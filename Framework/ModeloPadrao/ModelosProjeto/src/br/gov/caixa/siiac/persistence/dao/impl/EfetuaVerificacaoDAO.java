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

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.model.ChecklistVO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.ResultadoApontamentoProduto;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoParecer;
import br.gov.caixa.siiac.persistence.dao.IEfetuaVerificacaoDAO;

@Repository
@Scope("prototype")
public class EfetuaVerificacaoDAO extends GenericDAO<VerificacaoContrato> implements IEfetuaVerificacaoDAO {
	
	public EfetuaVerificacaoDAO() {
		super(VerificacaoContrato.class);
	}
	
	public ChecklistServicoVerificacaoProduto getMontaArvoreChecklist(VerificacaoContratoVO verificacaoContratoVO) {
		String sql = "";
		if (verificacaoContratoVO.getIcEstadoVerificacao().equals(VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_INCONFORME)) {
			sql = "SELECT clsvp FROM ChecklistServicoVerificacaoProduto clsvp" +
			" WHERE clsvp.nuChecklistServicoVerificacaoProduto = " + verificacaoContratoVO.getNuChecklistServicoVerificacaoProduto();
		}else {
			sql = "SELECT clsvp FROM ChecklistServicoVerificacaoProduto clsvp left join clsvp.verificacaoContratoList vc " +
			" WHERE vc.nuVerificacaoContrato = " + verificacaoContratoVO.getNuVerificacaoContrato() +
			" AND clsvp.nuChecklistServicoVerificacaoProduto = " + verificacaoContratoVO.getNuChecklistServicoVerificacaoProduto();
		}
		Query q = getSession().createQuery(sql);
		return (ChecklistServicoVerificacaoProduto) q.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IEfetuaVerificacaoDAO#validaParaParecer(java.lang.Integer)
	 */
	public Object[] validaParaParecer(Integer nuVerificacaoContrato) {
		String sql = ""
			+ "WITH datasource "
			+ "     AS (SELECT nu_rslto_aptmnto_produto, ic_resultado_aptmnto_chklst, tb007.ic_data_validade, tb056_item.dt_validade "
			+ "         FROM   iacsm001.iactb013_rslto_aptmnto_produto tb013 "
			+ "                LEFT JOIN iacsm001.iactb012_aptmnto_chklst_produto tb012 "
			+ "                       ON tb012.nu_aptmnto_chklst_produto = tb013.nu_aptmnto_chklst_produto "
			+ "                LEFT JOIN iacsm001.iactb011_item_vrfco_cklst_prdto tb011 "
			+ "                       ON tb011.nu_item_chklst_srvco_vrfco_prdt = tb012.nu_item_chklst_srvco_vrfco_prdt "
			+ "                LEFT JOIN iacsm001.iactb010_bloco_chklst_produto tb010 "
			+ "                       ON tb010.nu_blco_chklst_srvco_vrfco_prdt = tb011.nu_blco_chklst_srvco_vrfco_prdt "
			+ "                LEFT JOIN iacsm001.iactb007_item_verificacao tb007 "
			+ "                       ON tb007.nu_item_vrfco_checklist = tb011.nu_item_vrfco_checklist "
			+ " 			   LEFT JOIN iacsm001.iactb056_vrfco_cntro_obsro tb056_bloco "
			+ "						  ON tb056_bloco.nu_blco_chklst_srvco_vrfco_prdt = tb011.nu_blco_chklst_srvco_vrfco_prdt "
			+ "						  AND tb056_bloco.nu_verificacao_contrato = tb013.nu_verificacao_contrato "
			+ "						  AND tb056_bloco.ic_fonte = 'B' "
			+ " 			   LEFT JOIN iacsm001.iactb056_vrfco_cntro_obsro tb056_item "
			+ "						  ON tb056_item.nu_item_chklst_srvco_vrfco_prdt = tb011.nu_item_chklst_srvco_vrfco_prdt "
			+ "						  AND tb056_item.nu_verificacao_contrato = tb013.nu_verificacao_contrato "
			+ "						  AND tb056_item.ic_fonte = 'I' "
			+ "         WHERE  tb013.nu_verificacao_contrato = :nu_verificacao_contrato "
	     	+ "  		AND (tb056_bloco.ic_desabilitado IS NULL OR tb056_bloco.ic_desabilitado = FALSE) " 
	    	+ "			AND (tb056_item.ic_desabilitado IS NULL OR tb056_item.ic_desabilitado = FALSE)) "
			+ "SELECT (SELECT Count(*) "
			+ "        FROM   datasource "
			+ "        WHERE  ic_data_validade = true "
			+ "               AND dt_validade IS NULL)           AS itemObrigatorio, "
			+ "       (SELECT Count(*) "
			+ "        FROM   datasource "
			+ "        WHERE  ic_resultado_aptmnto_chklst = '0') AS apontamentoNaoVerificado , "
			+ "		  (SELECT COUNT(*) FROM iacsm001.iactb019_template_parecer template "
			+ " 		           INNER JOIN iacsm001.iactb018_verificacao_contrato verificacao ON verificacao.nu_servico_verificacao_produto = template.nu_servico_verificacao_produto "
			+ "                    WHERE ic_tipo_parecer = TRUE AND verificacao.nu_verificacao_contrato = :nu_verificacao_contrato) AS template_conforme, "
			+ " 	  (SELECT COUNT(*) FROM iacsm001.iactb019_template_parecer template "
			+ "					   INNER JOIN iacsm001.iactb018_verificacao_contrato verificacao ON verificacao.nu_servico_verificacao_produto = template.nu_servico_verificacao_produto "
			+ "					   WHERE ic_tipo_parecer = FALSE AND verificacao.nu_verificacao_contrato = :nu_verificacao_contrato) AS template_inconforme";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_verificacao_contrato", nuVerificacaoContrato);
		return (Object[]) query.uniqueResult();
	}
	
	public Integer validaDataItemImportaVerificacao(Integer nuVerificacaoContrato) {
		String sql = "SELECT COUNT(*) "
				+ " FROM iacsm001.iactb058_vrfco_cntro_obsro_prcr"
				+ " WHERE ic_fonte = 'I'"
				+ " AND nu_verificacao_contrato = :nu_verificacao_contrato"
				+ " AND (dt_validade IS NOT NULL AND dt_validade < current_date)";

		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_verificacao_contrato", nuVerificacaoContrato);
		
		return Integer.parseInt(String.valueOf(query.uniqueResult()));
	}
	
	public Integer validaCheckListImportaVerificacao(Integer nuVerificacaoProduto) {
		String sql = "SELECT nu_chklst_srvco_vrfco_produto FROM "
				+ " iacsm001.iactb005_chklst_srvco_produto"
				+ " where nu_servico_verificacao_produto = :nu_servico_verificacao_produto "
				+ " and ic_situacao_checklist Ilike :ic_situacao_checklist"
				+ " and ic_revogado is FALSE";

		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_servico_verificacao_produto", nuVerificacaoProduto);
		query.setString("ic_situacao_checklist",  "%" + ChecklistVO.CHKLST_SITUACAO_PUBLICADO + "%");
		
		
		if (query.uniqueResult() != null)
			return Integer.parseInt(String.valueOf(query.uniqueResult()));
				
		return 0;
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IEfetuaVerificacaoDAO#isConforme(java.lang.Integer)
	 */
	public Boolean isConforme(Integer nuVerificacaoContrato) {
		String sql = "SELECT COUNT(*) " +
				"FROM iacsm001.iactb013_rslto_aptmnto_produto tb013 " +
				"INNER JOIN iacsm001.iactb012_aptmnto_chklst_produto tb012 ON tb012.nu_aptmnto_chklst_produto = tb013.nu_aptmnto_chklst_produto " +
				"INNER JOIN iacsm001.iactb011_item_vrfco_cklst_prdto tb011 ON tb011.nu_item_chklst_srvco_vrfco_prdt = tb012.nu_item_chklst_srvco_vrfco_prdt " +
				"LEFT JOIN iacsm001.iactb056_vrfco_cntro_obsro tb056_bloco ON tb056_bloco.nu_blco_chklst_srvco_vrfco_prdt = tb011.nu_blco_chklst_srvco_vrfco_prdt " +
				"			AND tb056_bloco.nu_verificacao_contrato = tb013.nu_verificacao_contrato " +
				"   		AND tb056_bloco.ic_fonte = 'B' " +
				"LEFT JOIN iacsm001.iactb056_vrfco_cntro_obsro tb056_item ON tb056_item.nu_item_chklst_srvco_vrfco_prdt = tb011.nu_item_chklst_srvco_vrfco_prdt " +
				"			AND tb056_item.nu_verificacao_contrato = tb013.nu_verificacao_contrato " +
				"	    	AND tb056_item.ic_fonte = 'I' " +
				"WHERE tb013.ic_resultado_aptmnto_chklst = '2' " +
				"AND (tb056_bloco.ic_desabilitado IS NULL OR tb056_bloco.ic_desabilitado = FALSE) " +
				"AND (tb056_item.ic_desabilitado IS NULL OR tb056_item.ic_desabilitado = FALSE) " +
				"AND tb013.nu_verificacao_contrato = :nu_verificacao_contrato";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_verificacao_contrato", nuVerificacaoContrato);
		return query.uniqueResult() != null && Integer.parseInt(query.uniqueResult().toString()) <= 0;
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IEfetuaVerificacaoDAO#setNaoVerificadoToConforme(java.lang.Integer)
	 */
	public void setApontamentoNaoVerificadoToConforme(Integer nuVerificacaoContrato) {
		String sql = "UPDATE iacsm001.iactb013_rslto_aptmnto_produto " +
				"SET ic_resultado_aptmnto_chklst= '1' " +
				"WHERE nu_rslto_aptmnto_produto IN " +
				"( " +
				"SELECT nu_rslto_aptmnto_produto " +
				"FROM iacsm001.iactb013_rslto_aptmnto_produto tb013 " +
				"INNER JOIN iacsm001.iactb012_aptmnto_chklst_produto tb012 ON tb012.nu_aptmnto_chklst_produto = tb013.nu_aptmnto_chklst_produto " +
				"INNER JOIN iacsm001.iactb011_item_vrfco_cklst_prdto tb011 ON tb011.nu_item_chklst_srvco_vrfco_prdt = tb012.nu_item_chklst_srvco_vrfco_prdt " +
				"LEFT JOIN iacsm001.iactb056_vrfco_cntro_obsro tb056_bloco ON tb056_bloco.nu_blco_chklst_srvco_vrfco_prdt = tb011.nu_blco_chklst_srvco_vrfco_prdt " +
				"			  AND tb056_bloco.nu_verificacao_contrato = tb013.nu_verificacao_contrato " +
				"			  AND tb056_bloco.ic_fonte = 'B' " +
				"LEFT JOIN iacsm001.iactb056_vrfco_cntro_obsro tb056_item ON tb056_item.nu_item_chklst_srvco_vrfco_prdt = tb011.nu_item_chklst_srvco_vrfco_prdt " +
				"   		  AND tb056_item.nu_verificacao_contrato = tb013.nu_verificacao_contrato " +
				" 			  AND tb056_item.ic_fonte = 'I' " +
				"WHERE tb013.ic_resultado_aptmnto_chklst = '0' " +
				"AND (tb056_bloco.ic_desabilitado IS NULL OR tb056_bloco.ic_desabilitado = FALSE) " +
				"AND (tb056_item.ic_desabilitado IS NULL OR tb056_item.ic_desabilitado = FALSE) " +
				"AND tb013.nu_verificacao_contrato = :nu_verificacao_contrato " +
				")";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_verificacao_contrato", nuVerificacaoContrato);
		query.executeUpdate();
		getSession().flush();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IEfetuaVerificacaoDAO#setNaoVerificadoToConforme(java.lang.Integer)
	 */
	public void insertItemConformeTabelaObservacoes(Integer nuVerificacaoContrato) {

		StringBuffer  sql = new StringBuffer();
		sql.append("INSERT INTO iacsm001.iactb056_vrfco_cntro_obsro ");
		sql.append("            (nu_verificacao_contrato, ");
		sql.append("             nu_blco_chklst_srvco_vrfco_prdt, ");
		sql.append("             nu_item_chklst_srvco_vrfco_prdt, ");
		sql.append("             ic_fonte, ");
		sql.append("             de_observacao, ");
		sql.append("             ic_conforme, ");
		sql.append("             ic_desabilitado, ");
		sql.append("             nu_vrfco_cntro_obsro, ");
		sql.append("             dt_validade) ");
		sql.append("SELECT nu_verificacao_contrato, ");
		sql.append("       NULL, ");
		sql.append("       nu_item_chklst_srvco_vrfco_prdt, ");
		sql.append("       'I', ");
		sql.append("       NULL, ");
		sql.append("       true, ");
		sql.append("       false, ");
		sql.append("       Nextval('iacsm001.iacsq022_vrfco_cntro_obsro'), ");
		sql.append("       NULL ");
		sql.append("FROM   (SELECT DISTINCT ON(tb018.nu_verificacao_contrato, ");
		sql.append("       tb011.nu_item_chklst_srvco_vrfco_prdt) tb018.nu_verificacao_contrato, ");
		sql.append("       tb011.nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("        FROM   iacsm001.iactb011_item_vrfco_cklst_prdto tb011 ");
		sql.append("               INNER JOIN iacsm001.iactb010_bloco_chklst_produto tb010 ");
		sql.append("                       ON tb010.nu_blco_chklst_srvco_vrfco_prdt = ");
		sql.append("                          tb011.nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("               INNER JOIN iacsm001.iactb005_chklst_srvco_produto tb005 ");
		sql.append("                       ON tb005.nu_chklst_srvco_vrfco_produto = ");
		sql.append("                          tb010.nu_chklst_srvco_vrfco_produto ");
		sql.append("               INNER JOIN iacsm001.iactb018_verificacao_contrato tb018 ");
		sql.append("                       ON tb018.nu_chklst_srvco_vrfco_produto = ");
		sql.append("                          tb005.nu_chklst_srvco_vrfco_produto ");
		sql.append("        WHERE  tb018.nu_verificacao_contrato = :nu_verificacao_contrato ");
		sql.append("               AND tb011.nu_item_chklst_srvco_vrfco_prdt NOT IN ");
		sql.append("                       (SELECT nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("                                                                 FROM ");
		sql.append("                       iacsm001.iactb056_vrfco_cntro_obsro ");
		sql.append("                                                                 WHERE  ic_fonte ");
		sql.append("                       = 'I' ");
		sql.append("                                                                        AND ");
		sql.append("                       nu_verificacao_contrato = tb018.nu_verificacao_contrato) ");
		sql.append("               AND tb011.nu_blco_chklst_srvco_vrfco_prdt NOT IN ");
		sql.append("                       (SELECT nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("                                                                 FROM ");
		sql.append("                       iacsm001.iactb056_vrfco_cntro_obsro ");
		sql.append("                                                                 WHERE  ic_fonte ");
		sql.append("                       = 'B' ");
		sql.append("                                                                        AND ");
		sql.append("                       nu_verificacao_contrato = tb018.nu_verificacao_contrato ");
		sql.append("                                                                        AND ");
		sql.append("                       ic_desabilitado = true) ");
		sql.append("               AND tb011.nu_item_chklst_srvco_vrfco_prdt NOT IN ");
		sql.append("                       (SELECT nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("                                                                 FROM ");
		sql.append("                       iacsm001.iactb012_aptmnto_chklst_produto tb012 ");
		sql.append("                       INNER JOIN iacsm001.iactb013_rslto_aptmnto_produto tb013 ");
		sql.append("                               ON tb013.nu_aptmnto_chklst_produto = ");
		sql.append("                                  tb012.nu_aptmnto_chklst_produto ");
		sql.append("                                                                 WHERE ");
		sql.append("					   tb013.nu_verificacao_contrato = tb018.nu_verificacao_contrato AND ");
		sql.append("                       tb013.ic_resultado_aptmnto_chklst = '2')) AS datasource ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter("nu_verificacao_contrato", nuVerificacaoContrato);
		query.executeUpdate();
		getSession().flush();
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IEfetuaVerificacaoDAO#setNaoVerificadoToConforme(java.lang.Integer)
	 */
	public void updateItemConformeTabelaObservacoes(Integer nuVerificacaoContrato) {
		StringBuffer  sql = new StringBuffer();
		sql.append("UPDATE iacsm001.iactb056_vrfco_cntro_obsro ");
		sql.append("SET    ic_conforme = TRUE ");
		sql.append("WHERE  ( nu_verificacao_contrato, nu_item_chklst_srvco_vrfco_prdt ) IN ");
		sql.append("       (SELECT tb056.nu_verificacao_contrato, ");
		sql.append("       tb056.nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("       FROM   iacsm001.iactb056_vrfco_cntro_obsro tb056 ");
		sql.append("       inner join iacsm001.iactb011_item_vrfco_cklst_prdto tb011 ");
		sql.append("               ON tb056.nu_item_chklst_srvco_vrfco_prdt = ");
		sql.append("                  tb011.nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("       inner join iacsm001.iactb010_bloco_chklst_produto tb010 ");
		sql.append("               ON tb010.nu_blco_chklst_srvco_vrfco_prdt = ");
		sql.append("                  tb011.nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("       inner join iacsm001.iactb005_chklst_srvco_produto tb005 ");
		sql.append("               ON tb005.nu_chklst_srvco_vrfco_produto = ");
		sql.append("                  tb010.nu_chklst_srvco_vrfco_produto ");
		sql.append("       inner join iacsm001.iactb018_verificacao_contrato tb018 ");
		sql.append("               ON tb018.nu_chklst_srvco_vrfco_produto = ");
		sql.append("                  tb005.nu_chklst_srvco_vrfco_produto ");
		sql.append("                  AND tb056.nu_verificacao_contrato = ");
		sql.append("                      tb018.nu_verificacao_contrato ");
		sql.append("       WHERE  tb018.nu_verificacao_contrato = :nu_verificacao_contrato ");
		sql.append("       AND tb056.ic_fonte = 'I' ");
		sql.append("       AND ( tb056.ic_desabilitado = FALSE ");
		sql.append("              OR tb056.ic_desabilitado IS NULL ) ");
		sql.append("       AND ( tb056.ic_conforme = FALSE ");
		sql.append("              OR tb056.ic_conforme IS NULL ) ");
		sql.append("       AND tb011.nu_blco_chklst_srvco_vrfco_prdt NOT IN ");
		sql.append("               (SELECT nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("                                                         FROM ");
		sql.append("               iacsm001.iactb056_vrfco_cntro_obsro ");
		sql.append("                                                         WHERE  ic_fonte = 'B' ");
		sql.append("                                                                AND ");
		sql.append("               nu_verificacao_contrato = tb018.nu_verificacao_contrato ");
		sql.append("                                                                AND ");
		sql.append("               ic_desabilitado = TRUE) ");
		sql.append("       AND tb011.nu_item_chklst_srvco_vrfco_prdt NOT IN ");
		sql.append("               (SELECT nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("                                                         FROM ");
		sql.append("               iacsm001.iactb012_aptmnto_chklst_produto tb012 ");
		sql.append("               inner join iacsm001.iactb013_rslto_aptmnto_produto tb013 ");
		sql.append("                       ON tb013.nu_aptmnto_chklst_produto = tb012.nu_aptmnto_chklst_produto ");
		sql.append("                                                         WHERE ");
		sql.append("					   tb013.nu_verificacao_contrato = tb018.nu_verificacao_contrato AND ");
		sql.append("               		   tb013.ic_resultado_aptmnto_chklst = '2')) ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter("nu_verificacao_contrato", nuVerificacaoContrato);
		query.executeUpdate();
		getSession().flush();
	}

	@Transactional
	public List validaDataObrigatoria(Integer nuVerificacaoContrato) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("WITH datasource AS ( " );
		sbSQL.append("  SELECT" );
		sbSQL.append("    tb010.nu_blco_chklst_srvco_vrfco_prdt " );
		sbSQL.append("    , tb011.nu_item_chklst_srvco_vrfco_prdt " );
		sbSQL.append("    , ic_resultado_aptmnto_chklst " );
		sbSQL.append("    , tb007.ic_data_validade " );
		sbSQL.append("    , tb056_item.dt_validade " );
		sbSQL.append("  FROM" );
		sbSQL.append("    iacsm001.iactb013_rslto_aptmnto_produto tb013 " );
		sbSQL.append("    LEFT " );
		sbSQL.append("    " );
		sbSQL.append("    JOIN" );
		sbSQL.append("    iacsm001.iactb012_aptmnto_chklst_produto tb012 " );
		sbSQL.append("  ON tb012.nu_aptmnto_chklst_produto = tb013.nu_aptmnto_chklst_produto " );
		sbSQL.append("    LEFT " );
		sbSQL.append("    " );
		sbSQL.append("    JOIN" );
		sbSQL.append("    iacsm001.iactb011_item_vrfco_cklst_prdto tb011 " );
		sbSQL.append("  ON tb011.nu_item_chklst_srvco_vrfco_prdt = tb012.nu_item_chklst_srvco_vrfco_prdt " );
		sbSQL.append("    LEFT " );
		sbSQL.append("    " );
		sbSQL.append("    JOIN" );
		sbSQL.append("    iacsm001.iactb010_bloco_chklst_produto tb010 " );
		sbSQL.append("  ON tb010.nu_blco_chklst_srvco_vrfco_prdt = tb011.nu_blco_chklst_srvco_vrfco_prdt " );
		sbSQL.append("    LEFT " );
		sbSQL.append("    " );
		sbSQL.append("    JOIN" );
		sbSQL.append("    iacsm001.iactb007_item_verificacao tb007 " );
		sbSQL.append("  ON tb007.nu_item_vrfco_checklist = tb011.nu_item_vrfco_checklist " );
		sbSQL.append("    LEFT " );
		sbSQL.append("    " );
		sbSQL.append("    JOIN" );
		sbSQL.append("    iacsm001.iactb056_vrfco_cntro_obsro tb056_bloco " );
		sbSQL.append("  ON tb056_bloco.nu_blco_chklst_srvco_vrfco_prdt = tb011.nu_blco_chklst_srvco_vrfco_prdt " );
		sbSQL.append("    AND tb056_bloco.nu_verificacao_contrato = tb013.nu_verificacao_contrato " );
		sbSQL.append("    AND tb056_bloco.ic_fonte = 'B' " );
		sbSQL.append("    LEFT " );
		sbSQL.append("    " );
		sbSQL.append("    JOIN" );
		sbSQL.append("    iacsm001.iactb056_vrfco_cntro_obsro tb056_item " );
		sbSQL.append("  ON tb056_item.nu_item_chklst_srvco_vrfco_prdt = tb011.nu_item_chklst_srvco_vrfco_prdt " );
		sbSQL.append("    AND tb056_item.nu_verificacao_contrato = tb013.nu_verificacao_contrato " );
		sbSQL.append("    AND tb056_item.ic_fonte = 'I' " );
		sbSQL.append("  WHERE tb013.nu_verificacao_contrato = :nu_verificacao_contrato " );
		sbSQL.append("    AND " );
		sbSQL.append("    (" );
		sbSQL.append("      tb056_bloco.ic_desabilitado IS" );
		sbSQL.append("      NULL " );
		sbSQL.append("    OR tb056_bloco.ic_desabilitado = FALSE " );
		sbSQL.append("    ) " );
		sbSQL.append("    AND " );
		sbSQL.append("    (" );
		sbSQL.append("      tb056_item.ic_desabilitado IS" );
		sbSQL.append("      NULL " );
		sbSQL.append("    OR tb056_item.ic_desabilitado = FALSE " );
		sbSQL.append("    ) " );
		sbSQL.append("    AND tb007.ic_data_validade = TRUE " );
		sbSQL.append("    AND tb056_item.dt_validade IS" );
		sbSQL.append("    NULL " );
		sbSQL.append("  GROUP BY" );
		sbSQL.append("    tb010.nu_blco_chklst_srvco_vrfco_prdt" );
		sbSQL.append("    ,tb011.nu_item_chklst_srvco_vrfco_prdt" );
		sbSQL.append("    , tb007.ic_data_validade" );
		sbSQL.append("    , tb013.ic_resultado_aptmnto_chklst" );
		sbSQL.append("    , tb056_item.dt_validade " );
		sbSQL.append("  ) " );
		sbSQL.append("SELECT" );
		sbSQL.append("  nu_blco_chklst_srvco_vrfco_prdt, nu_item_chklst_srvco_vrfco_prdt " );
		sbSQL.append("FROM" );
		sbSQL.append("  datasource " );
		sbSQL.append("WHERE nu_item_chklst_srvco_vrfco_prdt NOT IN (" );
		sbSQL.append("  SELECT" );
		sbSQL.append("    nu_item_chklst_srvco_vrfco_prdt " );
		sbSQL.append("  FROM" );
		sbSQL.append("    datasource " );
		sbSQL.append("  WHERE ic_resultado_aptmnto_chklst = '2'" );
		sbSQL.append("  ) order by nu_item_chklst_srvco_vrfco_prdt" );

		SQLQuery query = getSession().createSQLQuery(sbSQL.toString());
		query.setParameter("nu_verificacao_contrato", nuVerificacaoContrato);
		return query.list();
	}

	public boolean isVerificacaoPrejudicada(Integer nuBlocoChecklistProduto, Integer nuVerificacaoContrato) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT" );
		sbSQL.append("  count(*) " );
		sbSQL.append("FROM" );
		sbSQL.append("  iacsm001.iactb012_aptmnto_chklst_produto" );
		sbSQL.append("  , iacsm001.iactb013_rslto_aptmnto_produto" );
		sbSQL.append("  , iacsm001.iactb011_item_vrfco_cklst_prdto" );
		sbSQL.append("  , iacsm001.iactb010_bloco_chklst_produto " );
		sbSQL.append("WHERE iactb012_aptmnto_chklst_produto.nu_item_chklst_srvco_vrfco_prdt = iactb011_item_vrfco_cklst_prdto.nu_item_chklst_srvco_vrfco_prdt " );
		sbSQL.append("  AND iactb013_rslto_aptmnto_produto.nu_aptmnto_chklst_produto = iactb012_aptmnto_chklst_produto.nu_aptmnto_chklst_produto " );
		sbSQL.append("  AND iactb011_item_vrfco_cklst_prdto.nu_blco_chklst_srvco_vrfco_prdt = iactb010_bloco_chklst_produto.nu_blco_chklst_srvco_vrfco_prdt " );
		sbSQL.append("  AND iactb010_bloco_chklst_produto.nu_blco_chklst_srvco_vrfco_prdt = " + nuBlocoChecklistProduto );
		sbSQL.append("  AND iactb013_rslto_aptmnto_produto.nu_verificacao_contrato = " + nuVerificacaoContrato );
		sbSQL.append("  AND iactb013_rslto_aptmnto_produto.ic_resultado_aptmnto_chklst = '" + ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_INCONFORME + "'");
		
		SQLQuery query = getSession().createSQLQuery(sbSQL.toString());
		return query.uniqueResult() != null && Integer.parseInt(query.uniqueResult().toString()) > 0;
	}
	
	public boolean isVerificacaoParecerPrejudicada(Integer nuVerificacaoContrato) {
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT Count(*) ");
		sql.append("FROM   iacsm001.iactb057_rslto_aptmnto_prdto_pr tb057 ");
		sql.append("       INNER JOIN iacsm001.iactb012_aptmnto_chklst_produto tb012 ");
		sql.append("               ON tb012.nu_aptmnto_chklst_produto = ");
		sql.append("                  tb057.nu_aptmnto_chklst_produto ");
		sql.append("       INNER JOIN iacsm001.iactb011_item_vrfco_cklst_prdto tb011 ");
		sql.append("               ON tb011.nu_item_chklst_srvco_vrfco_prdt = ");
		sql.append("                  tb012.nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("       INNER JOIN iacsm001.iactb010_bloco_chklst_produto tb010 ");
		sql.append("               ON tb010.nu_blco_chklst_srvco_vrfco_prdt = ");
		sql.append("                  tb011.nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("       LEFT JOIN iacsm001.iactb058_vrfco_cntro_obsro_prcr tb058_item ");
		sql.append("              ON tb058_item.nu_item_chklst_srvco_vrfco_prdt = ");
		sql.append("                           tb011.nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("                 AND tb058_item.nu_verificacao_contrato = ");
		sql.append("                     tb057.nu_verificacao_contrato ");
		sql.append("                 AND tb058_item.ic_fonte = 'I' ");
		sql.append("       LEFT JOIN iacsm001.iactb058_vrfco_cntro_obsro_prcr tb058_bloco ");
		sql.append("              ON tb058_bloco.nu_blco_chklst_srvco_vrfco_prdt = ");
		sql.append("                           tb010.nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("                 AND tb058_bloco.nu_verificacao_contrato = ");
		sql.append("                     tb057.nu_verificacao_contrato ");
		sql.append("                 AND tb058_bloco.ic_fonte = 'B' ");
		sql.append("WHERE  tb010.ic_bloco_analise_prejudicada = true ");
		sql.append("       AND ( tb058_bloco.ic_desabilitado IS NULL ");
		sql.append("              OR tb058_bloco.ic_desabilitado = false ) ");
		sql.append("       AND ( tb058_item.ic_desabilitado IS NULL ");
		sql.append("              OR tb058_item.ic_desabilitado = false ) ");
		sql.append("       AND tb057.ic_resultado_aptmnto_chklst = :ic_resultado_aptmnto_chklst ");
		sql.append("       AND tb057.nu_verificacao_contrato = :nu_verificacao_contrato ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setCharacter("ic_resultado_aptmnto_chklst", ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_INCONFORME);
		query.setInteger("nu_verificacao_contrato", nuVerificacaoContrato);
		
		return Integer.parseInt(query.uniqueResult().toString()) > 0;
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IEfetuaVerificacaoDAO#buscaPrimeiroBlocoPrejudicado(java.lang.Integer)
	 */
	public Integer buscaPrimeiroBlocoPrejudicado(Integer nuVerificacaoContrato) {
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT bloco.nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("FROM iacsm001.iactb010_bloco_chklst_produto bloco ");
		sql.append("INNER JOIN iacsm001.iactb011_item_vrfco_cklst_prdto item ON item.nu_blco_chklst_srvco_vrfco_prdt = bloco.nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("INNER JOIN iacsm001.iactb012_aptmnto_chklst_produto apontamento ON apontamento.nu_item_chklst_srvco_vrfco_prdt = item.nu_item_chklst_srvco_vrfco_prdt ");

		sql.append("WHERE bloco.ic_bloco_analise_prejudicada = TRUE ");
		sql.append("AND EXISTS (SELECT nu_rslto_aptmnto_produto  ");
		sql.append("			FROM iacsm001.iactb013_rslto_aptmnto_produto resultado "); 
		sql.append("			WHERE resultado.nu_aptmnto_chklst_produto = apontamento.nu_aptmnto_chklst_produto ");
		sql.append("			AND resultado.ic_resultado_aptmnto_chklst = '2' ");
		sql.append("			AND resultado.nu_verificacao_contrato = :nu_verificacao_contrato ");
		sql.append("		) ");
		sql.append("GROUP BY bloco.nu_blco_chklst_srvco_vrfco_prdt, bloco.nu_ordem_bloco_chklst ");
		sql.append("ORDER BY bloco.nu_ordem_bloco_chklst ASC ");
		sql.append("LIMIT 1 ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setInteger("nu_verificacao_contrato", nuVerificacaoContrato);
		
		return (Integer) query.uniqueResult();
	}
	
}