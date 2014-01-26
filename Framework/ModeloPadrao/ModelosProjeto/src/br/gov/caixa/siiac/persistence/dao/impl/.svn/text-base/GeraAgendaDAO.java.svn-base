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
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.ChecklistVO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.ResultadoApontamentoProdutoParecer;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoParecer;
import br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO;
import br.gov.caixa.siiac.view.mb.ServicoProdutoMB;
import br.gov.caixa.util.Utilities;

@Repository
@Scope("prototype")
public class GeraAgendaDAO extends GenericDAO<Contrato> implements IGeraAgendaDAO {
	
	public GeraAgendaDAO() {
		super(Contrato.class);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#getContratoGerarAgenda(Integer nuContrato)
	 */
	public Object[] getContratoGerarAgenda(Integer nuContrato) throws Exception {
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT nu_contrato, ");
		sql.append("       ic_agenda_gerada, ");
		sql.append("       ic_tipo_verificacao_contrato, ");
		sql.append("       nu_unidade_rspnl_contrato, ");
		sql.append("       nu_natural_undde_rspnl_contrato, ");
		sql.append("       dt_contrato, ");
		sql.append("       co_produto ");
		sql.append("FROM   iacsm001.iactb004_contrato ");
		sql.append("WHERE  ic_agenda_gerada IN ( '1', '2', '3', '4' ) ");
		sql.append("AND    nu_contrato = :nu_contrato ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter("nu_contrato", nuContrato);
		return (Object[]) query.uniqueResult();
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#getContratosAGerarAgenda()
	 */
	public List<Object[]> getContratosAGerarAgenda() throws Exception {
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT nu_contrato, ");
		sql.append("       ic_agenda_gerada, ");
		sql.append("       ic_tipo_verificacao_contrato, ");
		sql.append("       nu_unidade_rspnl_contrato, ");
		sql.append("       nu_natural_undde_rspnl_contrato, ");
		sql.append("       dt_contrato, ");
		sql.append("       co_produto ");
		sql.append("FROM   iacsm001.iactb004_contrato ");
		sql.append("WHERE  ic_agenda_gerada IN ( '1', '2', '3', '4' ) ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		return query.list();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#getServicosByContratoInclusao(br.gov.caixa.siiac.model.domain.Contrato)
	 */
	@SuppressWarnings("unchecked")
	public Object[] getServicoVerificacaoProdutoGerarAgenda(Integer nuServicoVerificacaoProduto) throws SIIACException, Exception {
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT svp.nu_servico_verificacao_produto AS svp, ");
		sql.append("       null AS nu_checklist, ");
		sql.append("       svp.pz_verificacao AS pz_verificacao ");
		sql.append("FROM   iacsm001.iactb003_servico_vrfco_produto svp ");
		sql.append("WHERE  svp.nu_servico_verificacao_produto = :nu_servico_verificacao_produto ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setInteger("nu_servico_verificacao_produto", nuServicoVerificacaoProduto);
		//query.setDate("dt_inicio", dtContrato);


		query.addScalar("svp", Hibernate.INTEGER);
		query.addScalar("nu_checklist", Hibernate.INTEGER);
		query.addScalar("pz_verificacao", Hibernate.INTEGER);
		
		return (Object[]) query.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#getServicosByContratoInclusao(br.gov.caixa.siiac.model.domain.Contrato)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getServicosByContratoInclusao(String coProduto, Date dtContrato) throws SIIACException, Exception {
		StringBuffer  sql = new StringBuffer();
		sql.append("WITH datasource AS (");
		sql.append("SELECT svp.nu_servico_verificacao_produto AS svp, ");
		sql.append("       (SELECT checklist.nu_chklst_srvco_vrfco_produto"); 
		sql.append("       FROM iacsm001.iactb005_chklst_srvco_produto checklist ");
		sql.append("       WHERE checklist.dt_inicio_checklist IS NOT NULL ");
		sql.append("       AND checklist.dt_inicio_checklist <= :dt_inicio ");
		sql.append("       AND checklist.ic_situacao_checklist ILIKE '%");
		sql.append(ChecklistVO.CHKLST_SITUACAO_PUBLICADO);
		sql.append("%' ");
		sql.append("       AND checklist.nu_servico_verificacao_produto = svp.nu_servico_verificacao_produto ");
		sql.append("       ORDER BY checklist.dt_inicio_checklist DESC ");
		sql.append("       LIMIT 1 ");
		sql.append("       ) nu_checklist, ");
		sql.append("       svp.pz_verificacao AS pz_verificacao ");
		sql.append("FROM   iacsm001.iactb003_servico_vrfco_produto svp ");
		sql.append("WHERE  svp.co_produto = :co_produto ");
		sql.append("       AND svp.ic_tipo_acao_servico IN ( '");
		sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO_ALTERACAO_DADOS_SITUACAO);
		sql.append("', '");
		sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO_ALTERACAO_DADOS);
		sql.append("', '");
		sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO);
		sql.append("' ) ");
		sql.append(") ");
		sql.append("SELECT * FROM datasource WHERE nu_checklist IS NOT NULL");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setString("co_produto", coProduto);
		query.setDate("dt_inicio", dtContrato);

		query.addScalar("svp", Hibernate.INTEGER);
		query.addScalar("nu_checklist", Hibernate.INTEGER);
		query.addScalar("pz_verificacao", Hibernate.INTEGER);
		
		return query.list();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#getServicosByContratoInclusao(br.gov.caixa.siiac.model.domain.Contrato)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getServicosByContratoVerificacaoPreventiva(String coProduto, Date dtContrato) throws SIIACException, Exception {
		StringBuffer  sql = new StringBuffer();
		sql.append("WITH datasource AS (");
		sql.append("SELECT svp.nu_servico_verificacao_produto AS svp, ");
		sql.append("       null nu_checklist, ");
		sql.append("       svp.pz_verificacao AS pz_verificacao ");
		sql.append("FROM   iacsm001.iactb003_servico_vrfco_produto svp ");
		sql.append("WHERE  svp.co_produto = :co_produto ");
		sql.append("       AND svp.ic_tipo_acao_servico IN ( '");
		sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_VERIFICACAO_PREVENTIVA);
		sql.append("' ) ");
		sql.append(") ");
		sql.append("SELECT * FROM datasource");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setString("co_produto", coProduto);

		query.addScalar("svp", Hibernate.INTEGER);
		query.addScalar("nu_checklist", Hibernate.INTEGER);
		query.addScalar("pz_verificacao", Hibernate.INTEGER);
		
		return query.list();
	}
	

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#getServicosSemVerificacaoByContratoAlteracao(br.gov.caixa.siiac.model.domain.Contrato)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getServicosSemVerificacaoByContratoAlteracao(Integer nuContrato, String coProduto, Date dtContrato, Character icAgendaGerada) throws SIIACException, Exception {
		if(Utilities.empty(icAgendaGerada)){
			throw new SIIACException("IcAgendaGerada não pode ser nulo."); 
		}
		
		StringBuffer  sql = new StringBuffer();
		sql.append("WITH datasource AS (");
		sql.append("SELECT svp.nu_servico_verificacao_produto AS svp, ");
		sql.append("       (SELECT checklist.nu_chklst_srvco_vrfco_produto"); 
		sql.append("       FROM iacsm001.iactb005_chklst_srvco_produto checklist ");
		sql.append("       WHERE checklist.dt_inicio_checklist IS NOT NULL ");
		sql.append("       AND checklist.dt_inicio_checklist <= :dt_inicio ");
		sql.append("       AND checklist.ic_situacao_checklist ILIKE '%");
		sql.append(ChecklistVO.CHKLST_SITUACAO_PUBLICADO);
		sql.append("%' ");
		sql.append("       AND checklist.nu_servico_verificacao_produto = svp.nu_servico_verificacao_produto ");
		sql.append("       ORDER BY checklist.dt_inicio_checklist DESC ");
		sql.append("       LIMIT 1 ");
		sql.append("       ) nu_checklist, ");
		sql.append("       svp.pz_verificacao AS pz_verificacao ");
		sql.append("FROM   iacsm001.iactb003_servico_vrfco_produto svp ");
		sql.append("WHERE  svp.co_produto = :co_produto ");
		
		
		if(icAgendaGerada.equals(Contrato.AGENDA_GERADA_ID_DADOS_ALTERADOS)){
			sql.append("       AND svp.ic_tipo_acao_servico IN ( '");
			sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO_ALTERACAO_DADOS);
			sql.append("', '");
			sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_ALTERACAO_DADOS);
			sql.append("' ) ");
		}else if(icAgendaGerada.equals(Contrato.AGENDA_GERADA_ID_SITUACAO_ALTERADA)){
			sql.append("       AND svp.ic_tipo_acao_servico IN ( '");
			sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_ALTERACAO_SITUACAO);
			sql.append("' ) ");
		}else{
			sql.append("       AND svp.ic_tipo_acao_servico IN ( '");
			sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO_ALTERACAO_DADOS_SITUACAO);
			sql.append("' ) ");
		}
		
		sql.append("  AND svp.nu_servico_verificacao_produto NOT IN ");
		sql.append("    (SELECT nu_servico_verificacao_produto ");
		sql.append("     FROM iacsm001.iactb055_vrfco_cntro_prcr ");
		sql.append("     WHERE nu_contrato = :nu_contrato ");
		sql.append("     AND ic_ultima_hierarquia = TRUE ");
		sql.append("     AND ic_estado_verificacao IN ('");
		sql.append(VerificacaoContratoVO.VERIFICACAO_CONFORME);
		sql.append("', '");
		sql.append(VerificacaoContratoVO.VERIFICACAO_INCONFORME);
		sql.append("') ");
		
		sql.append("     UNION ALL ");
		
		sql.append("     SELECT nu_servico_verificacao_produto ");
		sql.append("     FROM iacsm001.iactb018_verificacao_contrato "); 
		sql.append("     WHERE nu_contrato = :nu_contrato ");
		sql.append("     AND ic_ultima_hierarquia = TRUE ");
		sql.append("     AND ic_estado_verificacao IN ('");
		sql.append(VerificacaoContratoVO.VERIFICACAO_NAO_VERIFICADO);
		sql.append("') ");
		sql.append("  )");
		
		
		sql.append(") ");
		sql.append("SELECT * FROM datasource WHERE nu_checklist IS NOT NULL");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setInteger("nu_contrato", nuContrato);
		query.setString("co_produto", coProduto);
		query.setDate("dt_inicio", dtContrato);
		
		query.addScalar("svp", Hibernate.INTEGER);
		query.addScalar("nu_checklist", Hibernate.INTEGER);
		query.addScalar("pz_verificacao", Hibernate.INTEGER);
		
		return query.list();
	}

	public List<Object[]> getServicosVerificacaoSemParecerByContratoAlteracao(Integer nuContrato, String coProduto, Character icAgendaGerada) throws SIIACException, Exception {
		if(Utilities.empty(icAgendaGerada)){
			throw new SIIACException("IcAgendaGerada não pode ser nulo."); 
		}
		
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT svp.nu_servico_verificacao_produto AS svp, ");
		sql.append("       NULL AS nu_checklist, ");
		sql.append("       svp.pz_verificacao AS pz_verificacao ");
		sql.append("FROM   iacsm001.iactb003_servico_vrfco_produto svp ");
		sql.append("WHERE  svp.co_produto = :co_produto ");
		
		
		
		if(icAgendaGerada.equals(Contrato.AGENDA_GERADA_ID_DADOS_ALTERADOS)){
			sql.append("       AND svp.ic_tipo_acao_servico IN ( '");
			sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO_ALTERACAO_DADOS);
			sql.append("', '");
			sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_ALTERACAO_DADOS);
			sql.append("' ) ");
		}else if(icAgendaGerada.equals(Contrato.AGENDA_GERADA_ID_SITUACAO_ALTERADA)){
			sql.append("       AND svp.ic_tipo_acao_servico IN ( '");
			sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_ALTERACAO_SITUACAO);
			sql.append("' ) ");
		}else{
			sql.append("       AND svp.ic_tipo_acao_servico IN ( '");
			sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO_ALTERACAO_DADOS_SITUACAO);
			sql.append("' ) ");
		}

		sql.append("  AND svp.nu_servico_verificacao_produto IN ");
		sql.append("    (SELECT nu_servico_verificacao_produto ");
		sql.append("     FROM iacsm001.iactb018_verificacao_contrato "); 
		sql.append("     WHERE nu_contrato = :nu_contrato ");
		sql.append("     AND ic_ultima_hierarquia = TRUE ");
		sql.append("     AND ic_estado_verificacao IN ('");
		sql.append(VerificacaoContratoVO.VERIFICACAO_NAO_VERIFICADO);
		sql.append("') ");
		sql.append("  )");

		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setInteger("nu_contrato", nuContrato);
		query.setString("co_produto", coProduto);
		
		query.addScalar("svp", Hibernate.INTEGER);
		query.addScalar("nu_checklist", Hibernate.INTEGER);
		query.addScalar("pz_verificacao", Hibernate.INTEGER);
		
		return query.list();
		
	}
	
	public List<Object[]> getServicosVerificacaoComParecerByContratoAlteracao(Integer nuContrato, String coProduto, Character icAgendaGerada) throws SIIACException, Exception {
		if(Utilities.empty(icAgendaGerada)){
			throw new SIIACException("IcAgendaGerada não pode ser nulo."); 
		}
		
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT svp.nu_servico_verificacao_produto AS svp, ");
		sql.append("       NULL AS nu_checklist, ");
		sql.append("       svp.pz_verificacao AS pz_verificacao ");
		sql.append("FROM   iacsm001.iactb003_servico_vrfco_produto svp ");
		sql.append("WHERE  svp.co_produto = :co_produto ");

		
		if(icAgendaGerada.equals(Contrato.AGENDA_GERADA_ID_DADOS_ALTERADOS)){
			sql.append("       AND svp.ic_tipo_acao_servico IN ( '");
			sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO_ALTERACAO_DADOS);
			sql.append("', '");
			sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_ALTERACAO_DADOS);
			sql.append("' ) ");
		}else if(icAgendaGerada.equals(Contrato.AGENDA_GERADA_ID_SITUACAO_ALTERADA)){
			sql.append("       AND svp.ic_tipo_acao_servico IN ( '");
			sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_ALTERACAO_SITUACAO);
			sql.append("' ) ");
		}else{
			sql.append("       AND svp.ic_tipo_acao_servico IN ( '");
			sql.append(ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO_ALTERACAO_DADOS_SITUACAO);
			sql.append("' ) ");
		}


		sql.append("  AND svp.nu_servico_verificacao_produto IN ");
		sql.append("    (SELECT nu_servico_verificacao_produto ");
		sql.append("     FROM iacsm001.iactb055_vrfco_cntro_prcr "); 
		sql.append("     WHERE nu_contrato = :nu_contrato ");
		sql.append("     AND ic_ultima_hierarquia = TRUE ");
		sql.append("     AND ic_estado_verificacao IN ('");
		sql.append(VerificacaoContratoVO.VERIFICACAO_CONFORME);
		sql.append("', '");
		sql.append(VerificacaoContratoVO.VERIFICACAO_INCONFORME);
		sql.append("') ");
		sql.append("  ) ");
		

		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setInteger("nu_contrato", nuContrato);
		query.setString("co_produto", coProduto);
		
		query.addScalar("svp", Hibernate.INTEGER);
		query.addScalar("nu_checklist", Hibernate.INTEGER);
		query.addScalar("pz_verificacao", Hibernate.INTEGER);
		
		return query.list();
		
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#marcaVerificacaoComoNaoVerificada(br.gov.caixa.siiac.model.domain.VerificacaoContrato)
	 */
	public void marcaVerificacaoComoNaoVerificada(Integer nuContrato, Integer nuServicoVerificacaoProduto) {
		String sql = "UPDATE iacsm001.IACTB018_VERIFICACAO_CONTRATO " +
				"SET dt_verificacao = NULL " +
				", co_rspnl_verificacao = '' " +
				", ic_estado_verificacao = 'NV' " +
				"WHERE nu_contrato = :nu_contrato " +
				"AND nu_servico_verificacao_produto = :nu_servico_verificacao_produto";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_contrato", nuContrato);
		query.setParameter("nu_servico_verificacao_produto", nuServicoVerificacaoProduto);
		query.executeUpdate();
		getSession().flush();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#marcaApontamentoVerificacaoComoNaoVerificado(br.gov.caixa.siiac.model.domain.VerificacaoContrato)
	 */
	public void marcaApontamentoVerificacaoComoNaoVerificado(Integer nuContrato, Integer nuServicoVerificacaoProduto) {
		String sql = "UPDATE iacsm001.IACTB013_RSLTO_APTMNTO_PRODUTO " +
				"SET ic_resultado_aptmnto_chklst = '0'" +
				", de_observacao = NULL " +
				", co_rspnl_resultado = '' " +
				"WHERE nu_verificacao_contrato = " +
				"    (SELECT nu_verificacao_contrato " +
				"	  FROM iacsm001.iactb018_verificacao_contrato " +
				"	  WHERE nu_contrato = :nu_contrato " +
				"     AND nu_servico_verificacao_produto = :nu_servico_verificacao_produto ) ";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_contrato", nuContrato);
		query.setParameter("nu_servico_verificacao_produto", nuServicoVerificacaoProduto);
		query.executeUpdate();
		getSession().flush();
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#marcaApontamentoVerificacaoComoNaoVerificado(br.gov.caixa.siiac.model.domain.VerificacaoContrato)
	 */
	public void apagaObservacoesVerificacao(Integer nuContrato, Integer nuServicoVerificacaoProduto) {
		String sql = "DELETE FROM iacsm001.IACTB056_VRFCO_CNTRO_OBSRO " +
			 	"WHERE nu_verificacao_contrato = " +
				"    (SELECT nu_verificacao_contrato " +
				"	  FROM iacsm001.iactb018_verificacao_contrato " +
				"	  WHERE nu_contrato = :nu_contrato " +
				"     AND nu_servico_verificacao_produto = :nu_servico_verificacao_produto ) ";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_contrato", nuContrato);
		query.setParameter("nu_servico_verificacao_produto", nuServicoVerificacaoProduto);
		query.executeUpdate();
		getSession().flush();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#atualizaUltimaHierarquiaVerificacaoAnterior(br.gov.caixa.siiac.model.domain.VerificacaoContratoParecer)
	 */
	public void atualizaUltimaHierarquiaVerificacaoAnterior(Integer nuContrato, Integer nuServicoVerificacaoProduto) {
		String sql = "UPDATE iacsm001.iactb055_vrfco_cntro_prcr " +
				"SET ic_ultima_hierarquia = FALSE  " +
			 	"WHERE nu_contrato = :nu_contrato " +
			 	"AND nu_servico_verificacao_produto = :nu_servico_verificacao_produto " +
			 	"AND ic_ultima_hierarquia = TRUE";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_contrato", nuContrato);
		query.setParameter("nu_servico_verificacao_produto", nuServicoVerificacaoProduto);
		query.executeUpdate();
		getSession().flush();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#updateContratoAgendaGerada(br.gov.caixa.siiac.model.domain.Contrato)
	 */
	public void updateContratoAgendaGerada(Integer nuContrato) {
		String sql = "UPDATE iacsm001.iactb004_contrato " +
				"SET ic_agenda_gerada = :agenda_gerada_id  " +
				", ic_prazo_verificacao = :ic_prazo_verificacao_AV  " +
			 	"WHERE nu_contrato = :nu_contrato";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nu_contrato", nuContrato);
		query.setParameter("agenda_gerada_id", Contrato.AGENDA_GERADA_ID_GERACAO_OK);
		query.setParameter("ic_prazo_verificacao_AV", Contrato.PRAZO_VERIFICACAO_ID_CONTRATO_A_VERIFICAR);
		query.executeUpdate();
		getSession().flush();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#insereResultadoApontamentoTeste(br.gov.caixa.siiac.model.domain.VerificacaoContrato, br.gov.caixa.siiac.model.domain.Contrato)
	 */
	public void insereResultadoApontamento(Integer nuVerificacaoContrato, Integer nuContrato, Integer nuChecklist) {
		this.flush();
		StringBuffer  sql = new StringBuffer();
		sql.append("INSERT INTO iacsm001.iactb013_rslto_aptmnto_produto ");
		sql.append("            (nu_rslto_aptmnto_produto, ");
		sql.append("             nu_contrato, ");
		sql.append("             nu_verificacao_contrato, ");
		sql.append("             nu_aptmnto_chklst_produto, ");
		sql.append("             ic_resultado_aptmnto_chklst, ");
		sql.append("             co_rspnl_resultado) ");
		sql.append("SELECT Nextval('iacsm001.iacsq021_rslto_aptmnto_produto'), ");
		sql.append("       :nu_contrato, ");
		sql.append("       :nu_verificacao_contrato, ");
		sql.append("       apontamento.nu_aptmnto_chklst_produto, ");
		sql.append("       '0', ");
		sql.append("       '' ");
		sql.append("FROM   iacsm001.iactb012_aptmnto_chklst_produto apontamento ");
		sql.append("       INNER JOIN iacsm001.iactb011_item_vrfco_cklst_prdto item ");
		sql.append("               ON item.nu_item_chklst_srvco_vrfco_prdt = ");
		sql.append("                  apontamento.nu_item_chklst_srvco_vrfco_prdt ");
		sql.append("       INNER JOIN iacsm001.iactb010_bloco_chklst_produto bloco ");
		sql.append("               ON bloco.nu_blco_chklst_srvco_vrfco_prdt = ");
		sql.append("                  item.nu_blco_chklst_srvco_vrfco_prdt ");
		sql.append("WHERE  bloco.nu_chklst_srvco_vrfco_produto = :nu_checklist ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter("nu_contrato", nuContrato);
		query.setParameter("nu_verificacao_contrato", nuVerificacaoContrato);
		query.setParameter("nu_checklist", nuChecklist);
		
		query.executeUpdate();
		this.flush();
		
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#insereVerificacao(java.lang.Integer, java.lang.Integer, java.lang.Short, java.lang.Integer, java.util.Date, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	public Integer insereVerificacao(Integer nuContrato,
			Integer nuServicoVerificacaoProduto, Short nuUnidade,
			Integer nuNatural, Date dtInclusao, Date dtLimiteVerificacao,
			String icEstadoVerificacao, Integer nuVerificacaoContratoPai,
			Integer nuChecklist) {
		
		Integer nuVerificacaoContrato = (Integer) getSession().createSQLQuery("SELECT cast(nextval('iacsm001.iacsq020_verificacao_contrato') AS INTEGER)").uniqueResult();
		
		try {
			
			StringBuffer  sql = new StringBuffer();
			sql.append("INSERT INTO iacsm001.iactb018_verificacao_contrato ");
			sql.append("            (nu_verificacao_contrato, ");
			sql.append("             nu_contrato, ");
			sql.append("             nu_servico_verificacao_produto, ");
			sql.append("             nu_unidade_rspnl_verificacao, ");
			sql.append("             nu_natural_undde_rspnl_vrfccao, ");
			sql.append("             dt_inclusao_verificacao, ");
			sql.append("             dt_limite_verificacao, ");
			sql.append("             ic_estado_verificacao, ");
			sql.append("             ic_suspensa, ");
			sql.append("             ic_ultima_hierarquia, ");
			sql.append("             nu_chklst_srvco_vrfco_produto) ");
			sql.append("VALUES      (:nu_verificacao_contrato, ");
			sql.append("             :nu_contrato, ");
			sql.append("             :nu_servico_verificacao_produto, ");
			sql.append("             :nu_unidade_rspnl_verificacao, ");
			sql.append("             :nu_natural_undde_rspnl_vrfccao, ");
			sql.append("             :dt_inclusao_verificacao, ");
			sql.append("             :dt_limite_verificacao, ");
			sql.append("             :ic_estado_verificacao, ");
			sql.append("             false, ");
			sql.append("             true, ");
			sql.append("             null)");

			SQLQuery query = getSession().createSQLQuery(sql.toString());
			query.setInteger("nu_verificacao_contrato", nuVerificacaoContrato);
			query.setInteger("nu_contrato", nuContrato);
			query.setInteger("nu_servico_verificacao_produto", nuServicoVerificacaoProduto);
			query.setShort("nu_unidade_rspnl_verificacao", nuUnidade);
			query.setInteger("nu_natural_undde_rspnl_vrfccao", nuNatural);
			query.setDate("dt_inclusao_verificacao", dtInclusao);
			query.setDate("dt_limite_verificacao", dtLimiteVerificacao);
			query.setString("ic_estado_verificacao", icEstadoVerificacao);
			
//			if(nuChecklist != null) {
//				query.setInteger("nu_chklst_srvco_vrfco_produto", nuChecklist);
//			} else {
//				query.setInteger("nu_chklst_srvco_vrfco_produto", null);
//			}
			
			query.executeUpdate();
			getSession().flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return nuVerificacaoContrato;	
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#insereVerificacaoReplica(java.lang.Integer, java.lang.Integer, java.util.Date, java.util.Date)
	 */
	public Integer insereVerificacaoReplica(Integer nuContrato,
			Integer nuServicoVerificacao, Date dtInclusao,
			Date dtLimiteVerificacao, String icEstadoVerificacao) {
		
		
		Integer nuVerificacaoContrato = (Integer) getSession().createSQLQuery("SELECT cast(nextval('iacsm001.iacsq020_verificacao_contrato') AS INTEGER)").uniqueResult();
		
		StringBuffer  sql = new StringBuffer();
		sql.append("INSERT INTO iacsm001.iactb018_verificacao_contrato ");
		sql.append("            (nu_verificacao_contrato, ");
		sql.append("             nu_contrato, ");
		sql.append("             nu_servico_verificacao_produto, ");
		sql.append("             nu_unidade_rspnl_verificacao, ");
		sql.append("             nu_natural_undde_rspnl_vrfccao, ");
		sql.append("             dt_inclusao_verificacao, ");
		sql.append("             dt_verificacao, ");
		sql.append("             dt_limite_verificacao, ");
		sql.append("             co_rspnl_verificacao, ");
		sql.append("             ic_estado_verificacao, ");
		sql.append("             ic_suspensa, ");
		sql.append("             nu_verificacao_contrato_pai, ");
		sql.append("             ic_ultima_hierarquia, ");
		sql.append("             nu_chklst_srvco_vrfco_produto) ");
		sql.append("SELECT :nu_verificacao_contrato, ");
		sql.append("       nu_contrato, ");
		sql.append("       nu_servico_verificacao_produto, ");
		sql.append("       nu_unidade_rspnl_verificacao, ");
		sql.append("       nu_natural_undde_rspnl_vrfccao, ");
		sql.append("       :dt_inclusao_verificacao, ");
		sql.append("       NULL, ");
		sql.append("       :dt_limite_verificacao, ");
		sql.append("       NULL, ");
		sql.append("       :ic_estado_verificacao, ");
		sql.append("       ic_suspensa, ");
		sql.append("       nu_verificacao_contrato, ");
		sql.append("       true, ");
		sql.append("       nu_chklst_srvco_vrfco_produto ");
		sql.append("FROM   iacsm001.iactb055_vrfco_cntro_prcr ");
		sql.append("WHERE  nu_contrato = :nu_contrato ");
		sql.append("       AND nu_servico_verificacao_produto = :nu_servico_verificacao_produto ");
		sql.append("       AND ic_ultima_hierarquia = true ");
		

		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setInteger("nu_verificacao_contrato", nuVerificacaoContrato);
		query.setInteger("nu_contrato", nuContrato);
		query.setDate("dt_inclusao_verificacao", dtInclusao);
		query.setDate("dt_limite_verificacao", dtLimiteVerificacao);
		query.setInteger("nu_servico_verificacao_produto", nuServicoVerificacao);
		query.setString("ic_estado_verificacao", icEstadoVerificacao);
		
		query.executeUpdate();
		getSession().flush();
		
		return nuVerificacaoContrato;
		
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO#insereResultadoApontamentoReplica(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public void insereResultadoApontamentoReplica(
			Integer nuVerificacaoContrato, Integer nuContrato,
			Integer nuServicoVerificacaoProduto) {
		this.flush();
		StringBuffer  sql = new StringBuffer();
		sql.append("INSERT INTO iacsm001.iactb013_rslto_aptmnto_produto ");
		sql.append("            (nu_rslto_aptmnto_produto, ");
		sql.append("             nu_contrato, ");
		sql.append("             nu_verificacao_contrato, ");
		sql.append("             nu_aptmnto_chklst_produto, ");
		sql.append("             ic_resultado_aptmnto_chklst, ");
		sql.append("             co_rspnl_resultado) ");
		sql.append("SELECT Nextval('iacsm001.iacsq021_rslto_aptmnto_produto'), ");
		sql.append("       tb055.nu_contrato, ");
		sql.append("       :nu_verificacao_contrato, ");
		sql.append("       tb057.nu_aptmnto_chklst_produto, ");
		sql.append("       '0', ");
		sql.append("       '' ");
		sql.append("FROM   iacsm001.iactb057_rslto_aptmnto_prdto_pr tb057 ");
		sql.append("INNER JOIN iacsm001.iactb055_vrfco_cntro_prcr tb055 ON tb055.nu_verificacao_contrato = tb057.nu_verificacao_contrato ");
		sql.append("WHERE  tb055.nu_contrato = :nu_contrato ");
		sql.append("AND    tb055.nu_servico_verificacao_produto = :nu_servico_verificacao_produto ");
		sql.append("AND    tb055.ic_ultima_hierarquia = true ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter("nu_contrato", nuContrato);
		query.setParameter("nu_verificacao_contrato", nuVerificacaoContrato);
		query.setParameter("nu_servico_verificacao_produto", nuServicoVerificacaoProduto);
		
		query.executeUpdate();
		this.flush();
	}

	public Integer getMaiorPrazoServicoVerificacao() {
		SQLQuery query = getSession().createSQLQuery("SELECT CAST(MAX(pz_verificacao) AS integer) FROM iacsm001.iactb003_servico_vrfco_produto");
		return (Integer) query.uniqueResult();
	}
	
}