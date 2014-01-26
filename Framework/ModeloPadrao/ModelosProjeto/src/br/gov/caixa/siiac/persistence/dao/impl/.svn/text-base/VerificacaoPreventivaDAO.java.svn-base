/**
 * Copyright (c) 2009-2011 Caixa Econ?mica Federal. Todos os direitos
 * reservados.
 * 
 * Caixa Econ?mica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade
 * 
 * Este programa de computador foi desenvolvido sob demanda da CAIXA e est?
 * protegido por leis de direitos autorais e tratados internacionais. As
 * condi??es de c?pia e utiliza??o do todo ou partes dependem de autoriza??o da
 * empresa. C?pias n?o s?o permitidas sem expressa autoriza??o. N?o pode ser
 * comercializado ou utilizado para prop?sitos particulares.
 * 
 * Uso exclusivo da Caixa Econ?mica Federal. A reprodu??o ou distribui??o n?o
 * autorizada deste programa ou de parte dele, resultar? em puni??es civis e
 * criminais e os infratores incorrem em san??es previstas na legisla??o em
 * vigor.
 * 
 * Hist?rico do Subversion:
 * 
 * LastChangedRevision:  
 * LastChangedBy:  
 * LastChangedDate: 
 * 
 * HeadURL: 
 * 
 */
package br.gov.caixa.siiac.persistence.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.bo.impl.VerificacaoContratoBO;
import br.gov.caixa.siiac.model.ContratoVO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoPreventivaDAO;
import br.gov.caixa.siiac.view.mb.pagination.ParamPagination;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */

@Repository
@Scope("prototype")
public class VerificacaoPreventivaDAO extends GenericDAO<Contrato> implements IVerificacaoPreventivaDAO 
{
	public VerificacaoPreventivaDAO() {
		super(Contrato.class);
	}

	
	public Connection getConnection() {
		return getSession().connection();
	}	
	
	public List<Contrato> filtra(ContratoVO contratoVO, List<String> produtos, List<String> servicos, List<String> listProdutosAbrangentes, ParamPagination paramPagination){
		if(listProdutosAbrangentes != null && listProdutosAbrangentes.isEmpty()){
			return new ArrayList<Contrato>();
		}
		return getQueryFiltra(contratoVO, produtos, servicos, listProdutosAbrangentes, paramPagination, false).list();
	}
	
	public Query getQueryFiltra(ContratoVO contratoVO, List<String> produtos, List<String> servicos, List<String> listProdutosAbrangentes, ParamPagination paramPagination, boolean count){
		StringBuffer hql = new StringBuffer();
		if(count){
			hql.append("SELECT COUNT(c) ");
		}else{
			hql.append("SELECT c ");
		}
		hql.append("FROM Contrato c, Produto _p where c.produto = _p ");
		Map<String, Object> parametros = new HashMap<String, Object>();
		hql.append(" AND c.icAgendaGerada = :icAgendaGerada ");
		parametros.put("icAgendaGerada", Contrato.AGENDA_GERADA_ID_GERACAO_OK);
						
		if(Utilities.notEmpty(contratoVO.getUnidades())) {
			hql.append(" AND c.nuUnidade IN (:unidades) ");
			parametros.put("unidades", contratoVO.getUnidades());
		}
		

		if(contratoVO.getProdutosPreferenciais()){
			if(produtos != null && !produtos.isEmpty()){
				hql.append(" AND c.produto.coProduto IN (:produtos) ");
				parametros.put("produtos", produtos);
			}
			if(servicos != null && !servicos.isEmpty()){
				hql.append(" AND c.nuContrato IN (:servicos) ");
				parametros.put("servicos", servicos);
			}
			
		}else{
			if(listProdutosAbrangentes != null ){
				hql.append(" AND c.produto.coProduto IN (:produtosAbrangentes) ");
				parametros.put("produtosAbrangentes", listProdutosAbrangentes);
			}
		}
		if(Utilities.notEmpty(contratoVO.getNuUnidade())) {
			hql.append(" AND c.nuUnidade = :nuUnidade");
			parametros.put("nuUnidade", contratoVO.getNuUnidade());
		}
		
		if (contratoVO.isSimpleFilter()) {
			this.simpleFilter(hql, parametros, contratoVO);
		} else {
			this.advancedFilter(hql, parametros, contratoVO);
		}
		if (paramPagination != null && paramPagination.getOrder() != null && !count) {
			for (String s : paramPagination.getOrder().keySet()) {
				String order = paramPagination.getOrder().get(s);
				if (order != null) {
					if (s.equals("unidade")) {
						hql.append(" ORDER BY c.nuUnidade " + order);
					}
					if (s.equals("produto")) {
						hql.append(" ORDER BY c.produto.coProduto " + order);
					}
					if (s.equals("contrato")) {
						hql.append(" ORDER BY c.coContrato " + order);
					}
					if (s.equals("cliente")) {
						hql.append(" ORDER BY c.noCliente " + order);
					}
					if (s.equals("identificador")) {
						hql.append(" ORDER BY c.nuIdentificadorCliente " + order);
					}
					if (s.equals("dtInclusao")) {
						hql.append(" ORDER BY c.dtContrato " + order);
					}
					if (s.equals("valor")) {
						hql.append(" ORDER BY c.vrNominalContrato " + order);
					}
					if (s.equals("situacao")) {
						hql.append(" ORDER BY c.icSituacaoContaContrato " + order);
					}
					
					hql.append(", c.nuContrato " + order);
					
					break;
				}
			}
		}
		
		Session session = getSession();
		Query query = session.createQuery(hql.toString());
		
		query.setProperties(parametros);
		if (!count && paramPagination != null) {
			query.setFirstResult(paramPagination.getFirstResult());
			query.setMaxResults(paramPagination.getMaxResults());
		}
		return query;
	}
	
	private void advancedFilter (StringBuffer hql, Map<String, Object> parametros, ContratoVO contratoVO) {
		BigDecimal valorInicial = null;
		BigDecimal valorFinal = null;

		if (Utilities.notEmpty(contratoVO.getFaixa()) && contratoVO.getFaixa().size() == 2) {
			valorInicial = contratoVO.getFaixa().get(0);
			valorFinal = contratoVO.getFaixa().get(1);
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre1()))
		{
			hql.append(" AND c.detalhesContrato.noCampoLivre1 = :numeroDamp ");
			parametros.put("numeroDamp", contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre1());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre2()))
		{
			hql.append(" AND c.detalhesContrato.noCampoLivre2 = :dataDamp ");
			parametros.put("dataDamp", contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre2());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre3()))
		{
			hql.append(" AND c.detalhesContrato.noCampoLivre3 = :valorRessarcimento ");
			parametros.put("valorRessarcimento", contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre3());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre4()))
		{
			hql.append(" AND c.detalhesContrato.noCampoLivre4 ILIKE :nomePrimeiroCoObrigado ");
			parametros.put("nomePrimeiroCoObrigado", contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre4());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre5()))
		{
			hql.append(" AND c.detalhesContrato.noCampoLivre5 = :codPrimeiroCoObrigado ");
			parametros.put("codPrimeiroCoObrigado", contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre5());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre6()))
		{
			hql.append(" AND c.detalhesContrato.noCampoLivre6 ILIKE :nomeSegundoCoObrigado ");
			parametros.put("nomeSegundoCoObrigado", contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre6());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre7()))
		{
			hql.append(" AND c.detalhesContrato.noCampoLivre7 = :codSegundoCoObrigado ");
			parametros.put("codSegundoCoObrigado", contratoVO.getContrato().getDetalhesContrato().getNoCampoLivre7());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNuIdentificadorEmpreendimento()))
		{
			hql.append(" AND c.detalhesContrato.nuIdentificadorEmpreendimento = :nuIdentificadorEmpreendimento ");
			parametros.put("nuIdentificadorEmpreendimento", contratoVO.getContrato().getDetalhesContrato().getNuIdentificadorEmpreendimento());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNoEmpreendimento()))
		{
			hql.append(" AND c.detalhesContrato.noEmpreendimento ILIKE :noEmpreendimento ");
			parametros.put("noEmpreendimento", "%" + contratoVO.getContrato().getDetalhesContrato().getNoEmpreendimento() + "%");
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNuIdentificadorVendedor()))
		{
			hql.append(" AND c.detalhesContrato.nuIdentificadorVendedor = :nuIdentificadorVendedor ");
			parametros.put("nuIdentificadorVendedor", contratoVO.getContrato().getDetalhesContrato().getNuIdentificadorVendedor());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNoVendedor()))
		{
			hql.append(" AND c.detalhesContrato.noVendedor ILIKE :noVendedor ");
			parametros.put("noVendedor", "%" + contratoVO.getContrato().getDetalhesContrato().getNoVendedor() + "%");
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNuConveniado())
				|| (Utilities.notEmpty(contratoVO.getNuConveniados()) && !contratoVO.getNuConveniados().isEmpty()))
		{
			
			if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNuConveniado()))
			{
				contratoVO.getNuConveniados().add(contratoVO.getContrato().getDetalhesContrato().getNuConveniado());
			}
			
			hql.append(" AND c.detalhesContrato.nuConveniado in (:nuConveniado) ");
			parametros.put("nuConveniado", contratoVO.getNuConveniados());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getCoAvaliacaoSiric()))
		{
			hql.append(" AND c.coAvaliacaoSiric = :coAvaliacaoSiric ");
			parametros.put("coAvaliacaoSiric", contratoVO.getContrato().getCoAvaliacaoSiric());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getNuIdentificadorCliente()))
		{
			hql.append(" AND c.nuIdentificadorCliente = :nuIdentificadorCliente ");
			parametros.put("nuIdentificadorCliente", contratoVO.getContrato().getNuIdentificadorCliente());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getCoCliente()))
		{
			hql.append(" AND c.coCliente = :coCliente ");
			parametros.put("coCliente", contratoVO.getContrato().getCoCliente());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getNoCliente()))
		{
			hql.append(" AND c.noCliente ILIKE :noCliente ");
			parametros.put("noCliente", "%" + contratoVO.getContrato().getNoCliente() + "%");
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getIcSituacaoContaContrato()))
		{
			hql.append(" AND c.icSituacaoContaContrato = :icSituacaoContaContrato ");
			parametros.put("icSituacaoContaContrato", contratoVO.getContrato().getIcSituacaoContaContrato());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getNuOperacao()))
		{
			hql.append(" AND c.nuOperacao = :nuOperacao ");
			parametros.put("nuOperacao", contratoVO.getContrato().getNuOperacao());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getNuCanalComercializacao()))
		{
			hql.append(" AND c.nuCanalComercializacao = :nuCanalComercializacao ");
			parametros.put("nuCanalComercializacao", contratoVO.getContrato().getNuCanalComercializacao());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getCoContrato()))
		{
			hql.append(" AND c.coContrato = :coContrato ");
			parametros.put("coContrato", contratoVO.getContrato().getCoContrato());
		}
		
		
		if (Utilities.notEmpty(contratoVO.getIcSituacaoContrato()))
		{
			//Caso o filtro seja por Não verificadas
			if (contratoVO.getIcSituacaoContrato().equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_NAO_VERIFICADA_SIGLA))
			{
				if (Utilities.notEmpty(contratoVO.getCoUsuario()) || Utilities.notEmpty(contratoVO.getDataVerificacaoInicio()) ||
						Utilities.notEmpty(contratoVO.getDataVerificacaoFim()) || Utilities.notEmpty(contratoVO.getNuServicoVerificacao()) 
						|| Utilities.notEmpty(contratoVO.getIcSituacaoContrato()))
				{
					hql.append(" AND ( EXISTS (select vc.nuVerificacaoContrato from ");
					hql.append(" VerificacaoContrato AS vc, ");
					hql.append(" ServicoVerificacaoProduto AS s, ");
					hql.append(" ServicoVerificacao AS sv ");
					hql.append(" WHERE vc.servicoVerificacaoProduto.nuServicoVerificacaoProduto = s.nuServicoVerificacaoProduto ");
					hql.append(" AND sv.nuServicoVerificacao = s.servicoVerificacao.nuServicoVerificacao");
					hql.append(" AND c.nuContrato = vc.contrato.nuContrato ");
					
					
//					//Parâmetro Somente Suspensas
					if (Utilities.notEmpty(contratoVO.getIcSuspensa())){
						hql.append(" AND vc.icSuspensa = :icSuspensa ");
						parametros.put("icSuspensa", contratoVO.getIcSuspensa());
					}
					
					//Parâmetro Número do Serviço de Verificação
					if (Utilities.notEmpty(contratoVO.getNuServicoVerificacao()))
					{
						hql.append(" AND sv.nuServicoVerificacao = :nuServicoVerificacao ");
						parametros.put("nuServicoVerificacao", contratoVO.getNuServicoVerificacao());
					}
					
					//Parâmetro Usuário Responsável
					if (Utilities.notEmpty(contratoVO.getCoUsuario()))
					{
						hql.append(" AND vc.coRspnlVerificacao = :coRspnlVerificacao ");
						parametros.put("coRspnlVerificacao", contratoVO.getCoUsuario());
					}
					
					//Parâmetros Período da Verificação
					if (contratoVO.getDataVerificacaoInicio() != null && contratoVO.getDataVerificacaoFim() != null)
					{
						hql.append(" AND vc.dtVerificacao BETWEEN :dtVerificacaoIni AND :dtVerificacaoFim ");
						parametros.put("dtVerificacaoIni", contratoVO.getDataVerificacaoInicio());
						parametros.put("dtVerificacaoFim", contratoVO.getDataVerificacaoFim());
					}
					
					hql.append(" AND vc.icEstadoVerificacao = 'NV'  ");
					
					hql.append(") AND NOT EXISTS (select vp.nuVerificacaoContrato from ");
					hql.append(" VerificacaoContratoParecer AS vp, ");
					hql.append(" ServicoVerificacaoProduto AS s, ");
					hql.append(" ServicoVerificacao AS sv ");
					hql.append(" WHERE vp.nuServicoVerificacaoProduto = s.nuServicoVerificacaoProduto ");
					hql.append(" AND sv.nuServicoVerificacao = s.servicoVerificacao.nuServicoVerificacao");
					hql.append(" AND c.nuContrato = vp.nuContrato ");
					
//					//Parâmetro Somente Suspensas
					if (Utilities.notEmpty(contratoVO.getIcSuspensa())){
						hql.append(" AND vp.icSuspensa = :icSuspensa ");
						parametros.put("icSuspensa", contratoVO.getIcSuspensa());
					}
					
					//Parâmetro Número do Serviço de Verificação
					if (Utilities.notEmpty(contratoVO.getNuServicoVerificacao()))
					{
						hql.append(" AND sv.nuServicoVerificacao = :nuServicoVerificacao ");
						parametros.put("nuServicoVerificacao", contratoVO.getNuServicoVerificacao());
					}
					
					//Parâmetro Usuário Responsável
					if (Utilities.notEmpty(contratoVO.getCoUsuario()))
					{
						hql.append(" AND vp.coResponsavelVerificacao = :coRspnlVerificacaoParecer ");
						parametros.put("coRspnlVerificacaoParecer", contratoVO.getCoUsuario());
					}
					
					//Parâmetros Período da Verificação
					if (contratoVO.getDataVerificacaoInicio() != null && contratoVO.getDataVerificacaoFim() != null)
					{
						hql.append(" AND vp.dtVerificacao BETWEEN :dtVerificacaoIni AND :dtVerificacaoFim ");
						parametros.put("dtVerificacaoIni", contratoVO.getDataVerificacaoInicio());
						parametros.put("dtVerificacaoFim", contratoVO.getDataVerificacaoFim());
					}
					
					hql.append(" AND vp.icUltimaHierarquia = :icUltimaHierarquia ) ) ");
					parametros.put("icUltimaHierarquia", Boolean.TRUE);
					
				}
			} else if (contratoVO.getIcSituacaoContrato().equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_VERIFICACAO_PARCIAL_SIGLA))
			{
				if (Utilities.notEmpty(contratoVO.getCoUsuario()) || Utilities.notEmpty(contratoVO.getDataVerificacaoInicio()) ||
						Utilities.notEmpty(contratoVO.getDataVerificacaoFim()) || Utilities.notEmpty(contratoVO.getNuServicoVerificacao()) ||
						Utilities.notEmpty(contratoVO.getIcSituacaoContrato()))
				{
					hql.append(" AND ( EXISTS (select vc.nuVerificacaoContrato from ");
					hql.append(" VerificacaoContrato AS vc, ");
					hql.append(" ServicoVerificacaoProduto AS s, ");
					hql.append(" ServicoVerificacao AS sv ");
					hql.append(" WHERE vc.servicoVerificacaoProduto.nuServicoVerificacaoProduto = s.nuServicoVerificacaoProduto ");
					hql.append(" AND sv.nuServicoVerificacao = s.servicoVerificacao.nuServicoVerificacao");
					hql.append(" AND c.nuContrato = vc.contrato.nuContrato ");
					
					
//					//Parâmetro Somente Suspensas
					if (Utilities.notEmpty(contratoVO.getIcSuspensa())){
						hql.append(" AND vc.icSuspensa = :icSuspensa ");
						parametros.put("icSuspensa", contratoVO.getIcSuspensa());
					}
//					
					//Parâmetro Número do Serviço de Verificação
					if (Utilities.notEmpty(contratoVO.getNuServicoVerificacao()))
					{
						hql.append(" AND sv.nuServicoVerificacao = :nuServicoVerificacao ");
						parametros.put("nuServicoVerificacao", contratoVO.getNuServicoVerificacao());
					}
					
					//Parâmetro Usuário Responsável
					if (Utilities.notEmpty(contratoVO.getCoUsuario()))
					{
						hql.append(" AND vc.coRspnlVerificacao = :coRspnlVerificacao ");
						parametros.put("coRspnlVerificacao", contratoVO.getCoUsuario());
					}
					
					//Parâmetros Período da Verificação
					if (contratoVO.getDataVerificacaoInicio() != null && contratoVO.getDataVerificacaoFim() != null)
					{
						hql.append(" AND vc.dtVerificacao BETWEEN :dtVerificacaoIni AND :dtVerificacaoFim ");
						parametros.put("dtVerificacaoIni", contratoVO.getDataVerificacaoInicio());
						parametros.put("dtVerificacaoFim", contratoVO.getDataVerificacaoFim());
					}
					
					hql.append(" AND vc.icEstadoVerificacao = 'NV'  ");
					
					hql.append(") AND EXISTS (select vp.nuVerificacaoContrato from ");
					hql.append(" VerificacaoContratoParecer AS vp, ");
					hql.append(" ServicoVerificacaoProduto AS s, ");
					hql.append(" ServicoVerificacao AS sv ");
					hql.append(" WHERE vp.nuServicoVerificacaoProduto = s.nuServicoVerificacaoProduto ");
					hql.append(" AND sv.nuServicoVerificacao = s.servicoVerificacao.nuServicoVerificacao");
					hql.append(" AND c.nuContrato = vp.nuContrato ");
					
//					//Parâmetro Somente Suspensas
					if (Utilities.notEmpty(contratoVO.getIcSuspensa())){
						hql.append(" AND vp.icSuspensa = :icSuspensa ");
						parametros.put("icSuspensa", contratoVO.getIcSuspensa());
					}
					
					//Parâmetro Número do Serviço de Verificação
					if (Utilities.notEmpty(contratoVO.getNuServicoVerificacao()))
					{
						hql.append(" AND sv.nuServicoVerificacao = :nuServicoVerificacao ");
						parametros.put("nuServicoVerificacao", contratoVO.getNuServicoVerificacao());
					}
					
					//Parâmetro Usuário Responsável
					if (Utilities.notEmpty(contratoVO.getCoUsuario()))
					{
						hql.append(" AND vp.coResponsavelVerificacao = :coRspnlVerificacaoParecer ");
						parametros.put("coRspnlVerificacaoParecer", contratoVO.getCoUsuario());
					}
					
					//Parâmetros Período da Verificação
					if (contratoVO.getDataVerificacaoInicio() != null && contratoVO.getDataVerificacaoFim() != null)
					{
						hql.append(" AND vp.dtVerificacao BETWEEN :dtVerificacaoIni AND :dtVerificacaoFim ");
						parametros.put("dtVerificacaoIni", contratoVO.getDataVerificacaoInicio());
						parametros.put("dtVerificacaoFim", contratoVO.getDataVerificacaoFim());
					}
					
					hql.append(" AND vp.icUltimaHierarquia = :icUltimaHierarquia ) ) ");
					parametros.put("icUltimaHierarquia", Boolean.TRUE);
					
				}
			} else if (contratoVO.getIcSituacaoContrato().equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_CONFORME_SIGLA)) {
				if (Utilities.notEmpty(contratoVO.getCoUsuario()) || Utilities.notEmpty(contratoVO.getDataVerificacaoInicio()) ||
						Utilities.notEmpty(contratoVO.getDataVerificacaoFim()) || Utilities.notEmpty(contratoVO.getNuServicoVerificacao()) ||
						Utilities.notEmpty(contratoVO.getIcSituacaoContrato()))
				{
					hql.append(" AND (EXISTS (select vp.nuVerificacaoContrato from ");
					hql.append(" VerificacaoContratoParecer AS vp, ");
					hql.append(" ServicoVerificacaoProduto AS s, ");
					hql.append(" ServicoVerificacao AS sv ");
					hql.append(" WHERE vp.nuServicoVerificacaoProduto = s.nuServicoVerificacaoProduto ");
					hql.append(" AND sv.nuServicoVerificacao = s.servicoVerificacao.nuServicoVerificacao");
					hql.append(" AND c.nuContrato = vp.nuContrato ");
					
//					//Parâmetro Somente Suspensas
					if (Utilities.notEmpty(contratoVO.getIcSuspensa())){
						hql.append(" AND vp.icSuspensa = :icSuspensa ");
						parametros.put("icSuspensa", contratoVO.getIcSuspensa());
					}
					
					//Parâmetro Número do Serviço de Verificação
					if (Utilities.notEmpty(contratoVO.getNuServicoVerificacao()))
					{
						hql.append(" AND sv.nuServicoVerificacao = :nuServicoVerificacao ");
						parametros.put("nuServicoVerificacaoProduto", contratoVO.getNuServicoVerificacao());
					}
					
					//Parâmetro Usuário Responsável
					if (Utilities.notEmpty(contratoVO.getCoUsuario()))
					{
						hql.append(" AND vp.coResponsavelVerificacao = :coRspnlVerificacaoParecer ");
						parametros.put("coRspnlVerificacaoParecer", contratoVO.getCoUsuario());
					}
					
					//Parâmetros Período da Verificação
					if (contratoVO.getDataVerificacaoInicio() != null && contratoVO.getDataVerificacaoFim() != null)
					{
						hql.append(" AND vp.dtVerificacao BETWEEN :dtVerificacaoIni AND :dtVerificacaoFim ");
						parametros.put("dtVerificacaoIni", contratoVO.getDataVerificacaoInicio());
						parametros.put("dtVerificacaoFim", contratoVO.getDataVerificacaoFim());
					}
					
					hql.append(" AND vp.icUltimaHierarquia = :icUltimaHierarquia ");
					parametros.put("icUltimaHierarquia", Boolean.TRUE);
					
					hql.append(" AND vp.icEstadoVerificacao = 'CO' ");
					
					hql.append(") AND NOT EXISTS (select vp.nuVerificacaoContrato from ");
					hql.append(" VerificacaoContratoParecer AS vp, ");
					hql.append(" ServicoVerificacaoProduto AS s, ");
					hql.append(" ServicoVerificacao AS sv ");
					hql.append(" WHERE vp.nuServicoVerificacaoProduto = s.nuServicoVerificacaoProduto ");
					hql.append(" AND sv.nuServicoVerificacao = s.servicoVerificacao.nuServicoVerificacao");
					hql.append(" AND c.nuContrato = vp.nuContrato ");
					
//					//Parâmetro Somente Suspensas
					if (Utilities.notEmpty(contratoVO.getIcSuspensa())){
						hql.append(" AND vp.icSuspensa = :icSuspensa ");
						parametros.put("icSuspensa", contratoVO.getIcSuspensa());
					}
					
					//Parâmetro Número do Serviço de Verificação
					if (Utilities.notEmpty(contratoVO.getNuServicoVerificacao()))
					{
						hql.append(" AND sv.nuServicoVerificacao = :nuServicoVerificacao ");
						parametros.put("nuServicoVerificacao", contratoVO.getNuServicoVerificacao());
					}
					
					//Parâmetro Usuário Responsável
					if (Utilities.notEmpty(contratoVO.getCoUsuario()))
					{
						hql.append(" AND vp.coResponsavelVerificacao = :coRspnlVerificacaoParecer ");
						parametros.put("coRspnlVerificacaoParecer", contratoVO.getCoUsuario());
					}
					
					//Parâmetros Período da Verificação
					if (contratoVO.getDataVerificacaoInicio() != null && contratoVO.getDataVerificacaoFim() != null)
					{
						hql.append(" AND vp.dtVerificacao BETWEEN :dtVerificacaoIni AND :dtVerificacaoFim ");
						parametros.put("dtVerificacaoIni", contratoVO.getDataVerificacaoInicio());
						parametros.put("dtVerificacaoFim", contratoVO.getDataVerificacaoFim());
					}
					
					hql.append(" AND vp.icUltimaHierarquia = :icUltimaHierarquia ");
					parametros.put("icUltimaHierarquia", Boolean.TRUE);
					
					hql.append(" AND vp.icEstadoVerificacao = 'IC' ");
					
					hql.append(") AND NOT EXISTS (select vc.nuVerificacaoContrato from ");
					hql.append(" VerificacaoContrato AS vc, ");
					hql.append(" ServicoVerificacaoProduto AS s, ");
					hql.append(" ServicoVerificacao AS sv ");
					hql.append(" WHERE vc.servicoVerificacaoProduto.nuServicoVerificacaoProduto = s.nuServicoVerificacaoProduto ");
					hql.append(" AND sv.nuServicoVerificacao = s.servicoVerificacao.nuServicoVerificacao");
					hql.append(" AND c.nuContrato = vc.contrato.nuContrato ");
					
					
//					//Parâmetro Somente Suspensas
					if (Utilities.notEmpty(contratoVO.getIcSuspensa())){
						hql.append(" AND vc.icSuspensa = :icSuspensa ");
						parametros.put("icSuspensa", contratoVO.getIcSuspensa());
					}
//					
					//Parâmetro Número do Serviço de Verificação
					if (Utilities.notEmpty(contratoVO.getNuServicoVerificacao()))
					{
						hql.append(" AND sv.nuServicoVerificacao = :nuServicoVerificacao ");
						parametros.put("nuServicoVerificacao", contratoVO.getNuServicoVerificacao());
					}
					
					//Parâmetro Usuário Responsável
					if (Utilities.notEmpty(contratoVO.getCoUsuario()))
					{
						hql.append(" AND vc.coRspnlVerificacao = :coRspnlVerificacao ");
						parametros.put("coRspnlVerificacao", contratoVO.getCoUsuario());
					}
					
					//Parâmetros Período da Verificação
					if (contratoVO.getDataVerificacaoInicio() != null && contratoVO.getDataVerificacaoFim() != null)
					{
						hql.append(" AND vc.dtVerificacao BETWEEN :dtVerificacaoIni AND :dtVerificacaoFim ");
						parametros.put("dtVerificacaoIni", contratoVO.getDataVerificacaoInicio());
						parametros.put("dtVerificacaoFim", contratoVO.getDataVerificacaoFim());
					}
					
					hql.append(" AND vc.icEstadoVerificacao = 'NV' ) ) ");
				}
			} else if (contratoVO.getIcSituacaoContrato().equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_INCONFORME_SIGLA)) {
				if (Utilities.notEmpty(contratoVO.getCoUsuario()) || Utilities.notEmpty(contratoVO.getDataVerificacaoInicio()) ||
						Utilities.notEmpty(contratoVO.getDataVerificacaoFim()) || Utilities.notEmpty(contratoVO.getNuServicoVerificacao()) ||
						Utilities.notEmpty(contratoVO.getIcSituacaoContrato()))
				{
					hql.append(" AND (EXISTS (select vp.nuVerificacaoContrato from ");
					hql.append(" VerificacaoContratoParecer AS vp, ");
					hql.append(" ServicoVerificacaoProduto AS s, ");
					hql.append(" ServicoVerificacao AS sv ");
					hql.append(" WHERE vp.nuServicoVerificacaoProduto = s.nuServicoVerificacaoProduto ");
					hql.append(" AND sv.nuServicoVerificacao = s.servicoVerificacao.nuServicoVerificacao");
					hql.append(" AND c.nuContrato = vp.nuContrato ");
					
//					//Parâmetro Somente Suspensas
					if (Utilities.notEmpty(contratoVO.getIcSuspensa())){
						hql.append(" AND vp.icSuspensa = :icSuspensa ");
						parametros.put("icSuspensa", contratoVO.getIcSuspensa());
					}
					
					//Parâmetro Número do Serviço de Verificação
					if (Utilities.notEmpty(contratoVO.getNuServicoVerificacao()))
					{
						hql.append(" AND sv.nuServicoVerificacao = :nuServicoVerificacao ");
						parametros.put("nuServicoVerificacao", contratoVO.getNuServicoVerificacao());
					}
					
					//Parâmetro Usuário Responsável
					if (Utilities.notEmpty(contratoVO.getCoUsuario()))
					{
						hql.append(" AND vp.coResponsavelVerificacao = :coRspnlVerificacaoParecer ");
						parametros.put("coRspnlVerificacaoParecer", contratoVO.getCoUsuario());
					}
					
					//Parâmetros Período da Verificação
					if (contratoVO.getDataVerificacaoInicio() != null && contratoVO.getDataVerificacaoFim() != null)
					{
						hql.append(" AND vp.dtVerificacao BETWEEN :dtVerificacaoIni AND :dtVerificacaoFim ");
						parametros.put("dtVerificacaoIni", contratoVO.getDataVerificacaoInicio());
						parametros.put("dtVerificacaoFim", contratoVO.getDataVerificacaoFim());
					}
					
					hql.append(" AND vp.icUltimaHierarquia = :icUltimaHierarquia ");
					parametros.put("icUltimaHierarquia", Boolean.TRUE);
					
					hql.append(" AND vp.icEstadoVerificacao = 'IC' ");
										
					hql.append(") AND NOT EXISTS (select vc.nuVerificacaoContrato from ");
					hql.append(" VerificacaoContrato AS vc, ");
					hql.append(" ServicoVerificacaoProduto AS s, ");
					hql.append(" ServicoVerificacao AS sv ");
					hql.append(" WHERE vc.servicoVerificacaoProduto.nuServicoVerificacaoProduto = s.nuServicoVerificacaoProduto ");
					hql.append(" AND sv.nuServicoVerificacao = s.servicoVerificacao.nuServicoVerificacao");
					hql.append(" AND c.nuContrato = vc.contrato.nuContrato ");
					
					
//					//Parâmetro Somente Suspensas
					if (Utilities.notEmpty(contratoVO.getIcSuspensa())){
						hql.append(" AND vc.icSuspensa = :icSuspensa ");
						parametros.put("icSuspensa", contratoVO.getIcSuspensa());
					}
					
					//Parâmetro Número do Serviço de Verificação
					if (Utilities.notEmpty(contratoVO.getNuServicoVerificacao()))
					{
						hql.append(" AND sv.nuServicoVerificacao = :nuServicoVerificacao ");
						parametros.put("nuServicoVerificacao", contratoVO.getNuServicoVerificacao());
					}
					
					//Parâmetro Usuário Responsável
					if (Utilities.notEmpty(contratoVO.getCoUsuario()))
					{
						hql.append(" AND vc.coRspnlVerificacao = :coRspnlVerificacao ");
						parametros.put("coRspnlVerificacao", contratoVO.getCoUsuario());
					}
					
					//Parâmetros Período da Verificação
					if (contratoVO.getDataVerificacaoInicio() != null && contratoVO.getDataVerificacaoFim() != null)
					{
						hql.append(" AND vc.dtVerificacao BETWEEN :dtVerificacaoIni AND :dtVerificacaoFim ");
						parametros.put("dtVerificacaoIni", contratoVO.getDataVerificacaoInicio());
						parametros.put("dtVerificacaoFim", contratoVO.getDataVerificacaoFim());
					}
					
					hql.append(" AND vc.icEstadoVerificacao = 'NV' ) ) ");
				}
			}
			
			
			
		} else {
			if (Utilities.notEmpty(contratoVO.getCoUsuario()) || Utilities.notEmpty(contratoVO.getDataVerificacaoInicio()) ||
					Utilities.notEmpty(contratoVO.getDataVerificacaoFim()) || Utilities.notEmpty(contratoVO.getNuServicoVerificacao()) ||
					Utilities.notEmpty(contratoVO.getIcSuspensa()))
			{
				hql.append(" AND (EXISTS (select vc.nuVerificacaoContrato from ");
				hql.append(" VerificacaoContrato AS vc, ");
				hql.append(" ServicoVerificacaoProduto AS s, ");
				hql.append(" ServicoVerificacao AS sv ");
				hql.append(" WHERE vc.servicoVerificacaoProduto.nuServicoVerificacaoProduto = s.nuServicoVerificacaoProduto ");
				hql.append(" AND sv.nuServicoVerificacao = s.servicoVerificacao.nuServicoVerificacao");
				hql.append(" AND c.nuContrato = vc.contrato.nuContrato ");
				
				
//				//Parâmetro Somente Suspensas
				if (Utilities.notEmpty(contratoVO.getIcSuspensa())){
					hql.append(" AND vc.icSuspensa = :icSuspensa ");
					parametros.put("icSuspensa", contratoVO.getIcSuspensa());
				}
				
				//Parâmetro Número do Serviço de Verificação
				if (Utilities.notEmpty(contratoVO.getNuServicoVerificacao()))
				{
					hql.append(" AND sv.nuServicoVerificacao = :nuServicoVerificacao ");
					parametros.put("nuServicoVerificacao", contratoVO.getNuServicoVerificacao());
				}
				
				//Parâmetro Usuário Responsável
				if (Utilities.notEmpty(contratoVO.getCoUsuario()))
				{
					hql.append(" AND vc.coRspnlVerificacao = :coRspnlVerificacao ");
					parametros.put("coRspnlVerificacao", contratoVO.getCoUsuario());
				}
				
				//Parâmetros Período da Verificação
				if (contratoVO.getDataVerificacaoInicio() != null && contratoVO.getDataVerificacaoFim() != null)
				{
					hql.append(" AND vc.dtVerificacao BETWEEN :dtVerificacaoIni AND :dtVerificacaoFim ");
					parametros.put("dtVerificacaoIni", contratoVO.getDataVerificacaoInicio());
					parametros.put("dtVerificacaoFim", contratoVO.getDataVerificacaoFim());
				}
							
				hql.append(") OR EXISTS (select vp.nuVerificacaoContrato from ");
				hql.append(" VerificacaoContratoParecer AS vp, ");
				hql.append(" ServicoVerificacaoProduto AS s, ");
				hql.append(" ServicoVerificacao AS sv ");
				hql.append(" WHERE vp.nuServicoVerificacaoProduto = s.nuServicoVerificacaoProduto ");
				hql.append(" AND sv.nuServicoVerificacao = s.servicoVerificacao.nuServicoVerificacao");
				hql.append(" AND c.nuContrato = vp.nuContrato ");
				
//				//Parâmetro Somente Suspensas
				if (Utilities.notEmpty(contratoVO.getIcSuspensa())){
					hql.append(" AND vp.icSuspensa = :icSuspensa ");
					parametros.put("icSuspensa", contratoVO.getIcSuspensa());
				}
				
				//Parâmetro Número do Serviço de Verificação
				if (Utilities.notEmpty(contratoVO.getNuServicoVerificacao()))
				{
					hql.append(" AND sv.nuServicoVerificacao = :nuServicoVerificacao ");
					parametros.put("nuServicoVerificacao", contratoVO.getNuServicoVerificacao());
				}
				
				//Parâmetro Usuário Responsável
				if (Utilities.notEmpty(contratoVO.getCoUsuario()))
				{
					hql.append(" AND vp.coResponsavelVerificacao = :coRspnlVerificacaoParecer ");
					parametros.put("coRspnlVerificacaoParecer", contratoVO.getCoUsuario());
				}
				
				//Parâmetros Período da Verificação
				if (contratoVO.getDataVerificacaoInicio() != null && contratoVO.getDataVerificacaoFim() != null)
				{
					hql.append(" AND vp.dtVerificacao BETWEEN :dtVerificacaoIni AND :dtVerificacaoFim ");
					parametros.put("dtVerificacaoIni", contratoVO.getDataVerificacaoInicio());
					parametros.put("dtVerificacaoFim", contratoVO.getDataVerificacaoFim());
				}
				
				hql.append(" AND vp.icUltimaHierarquia = :icUltimaHierarquia ) ) ");
				parametros.put("icUltimaHierarquia", Boolean.TRUE);
			}
		}
		
		//Campo Unidade
		if (contratoVO.getContrato().getNuUnidade() != null	&& !contratoVO.getContrato().getNuUnidade().toString().equals("")) {
			hql.append(" AND c.nuUnidade = :nuUnidade");
			parametros.put("nuUnidade", contratoVO.getContrato().getNuUnidade());
		}
		
		//Campo Produto
		if (contratoVO.getContrato().getProduto().getCoProduto() != null && !contratoVO.getContrato().getProduto().getCoProduto().equals("")) {
			hql.append(" AND c.produto.coProduto = :coProduto");
			parametros.put("coProduto", contratoVO.getContrato().getProduto().getCoProduto());
		}
		
		//Campo Contrato
		if (contratoVO.getContrato().getCoContrato() != null && !contratoVO.getContrato().getCoContrato().equals("")) {
			hql.append(" AND c.coContrato like :coContrato");
			parametros.put("coContrato", contratoVO.getContrato().getCoContrato());
		}
				
		//Avaliação Cliente
		if (contratoVO.getContrato().getCoAvaliacaoSiricTomador() != null	&& !contratoVO.getContrato().getCoAvaliacaoSiricTomador().equals("")) {
			hql.append(" AND c.coAvaliacaoSiricTomador ilike :coAvaliacaoSiricTomador");
			parametros.put("coAvaliacaoSiricTomador", contratoVO.getContrato().getCoAvaliacaoSiricTomador());
		}
		
		//Identificador Cliente
		if (contratoVO.getContrato().getNuIdentificadorCliente() != null	&& !contratoVO.getContrato().getNuIdentificadorCliente().equals("")) {
			hql.append(" AND c.nuIdentificadorCliente ilike :nuIdentificadorCliente");
			parametros.put("nuIdentificadorCliente", contratoVO.getContrato().getNuIdentificadorCliente());
		}
		
		//Avaliação Siric
		if (Utilities.notEmpty(contratoVO.getContrato().getCoAvaliacaoSiric())) {
			hql.append(" AND c.coAvaliacaoSiric = :coAvaliacaoSiric");
			parametros.put("coAvaliacaoSiric", contratoVO.getContrato().getCoAvaliacaoSiric());
		}
		
		//Situação do Contrato / Conta
		if (contratoVO.getContrato().getIcSituacaoContaContrato() != null
				&& !contratoVO.getContrato().getIcSituacaoContaContrato().equals("")) {
			hql.append(" AND c.icSituacaoContaContrato = :icSituacaoContaContrato");
			parametros.put("icSituacaoContaContrato", contratoVO.getContrato().getIcSituacaoContaContrato());
		}
		
		//Data Inclusão
		if (contratoVO.getDataInclusaoInicio() != null && contratoVO.getDataInclusaoFim() != null)
		{
			hql.append(" AND c.dtContrato BETWEEN :dtInclusaoIni AND :dtInclusaoFim");
			parametros.put("dtInclusaoIni", contratoVO.getDataInclusaoInicio());
			parametros.put("dtInclusaoFim", contratoVO.getDataInclusaoFim());
		}
		
	}
	
	private void simpleFilter(StringBuffer hql, Map<String, Object> parametros, ContratoVO contratoVO) {
		boolean isOR = false;
		if(Utilities.notEmpty(contratoVO.getPesquisaSimples())){

			hql.append("AND (");
			
			
			// Unidade
			if(Pattern.compile("[0-9]{1,4}").matcher(contratoVO.getPesquisaSimples()).matches()){
				hql.append(" c.nuUnidade = :nuUnidade");
				parametros.put("nuUnidade", Short.parseShort(contratoVO.getPesquisaSimples()));
				isOR = true;
			}
			
			// Identificador do Cliente
			String pesquisaIdentificadorCliente = contratoVO.getPesquisaSimples()
													.replace(".", "")
													.replace(" ", "")
													.replace(",", "")
													.replace("_", "")
													.replace("-", "");
			if(pesquisaIdentificadorCliente.length() <=  15){
				if(isOR){
					hql.append(" OR");
				}
				hql.append(" c.nuIdentificadorCliente = :nuIdentificadorCliente");
				parametros.put("nuIdentificadorCliente", pesquisaIdentificadorCliente);
				isOR = true;
			}
			
			if(isOR){
				hql.append(" OR");
			}
			hql.append(" c.noCliente ILIKE :noCliente");
			parametros.put("noCliente", "%" + contratoVO.getPesquisaSimples() + "%");
			
			
			// Produto
			hql.append(" OR");
			hql.append(" _p.noProduto ILIKE :noProduto");
			parametros.put("noProduto", "%"+contratoVO.getPesquisaSimples()+"%");
			if(Pattern.compile("[0-9]{4}[-]?[0-9]{3}").matcher(contratoVO.getPesquisaSimples()).matches()){
				hql.append(" OR c.produto.coProduto = :noProduto");
				parametros.put("noProduto", contratoVO.getPesquisaSimples().replace("-", ""));
			}
			
			if (contratoVO.getPesquisaSimples().length() <= 11)
			{
				hql.append(" OR c.coAvaliacaoSiric = :coAvaliacaoSiric");
				parametros.put("coAvaliacaoSiric", contratoVO.getPesquisaSimples());
			}
			
			hql.append(" ) ");
			
		}
	}


	/**
	 * @param usuarioLogado
	 * @param short1
	 * @param pesquisaString
	 * @param produtosPreferenciais
	 * @param param
	 * @return
	 */
	public int filtraCount(ContratoVO contratoVO, List<String> produtos, List<String> servicos, List<String> listProdutosAbrangentes, ParamPagination paramPagination){
		Long result = (Long) getQueryFiltra(contratoVO, produtos, servicos, listProdutosAbrangentes, paramPagination, true).uniqueResult();
		return result.intValue();
	}
	
	/**
	 * Método responsável por buscar o código dos conveniados, 
	 * que possuam uma parte do nome, igual ao parâmetro passado
	 * 
	 * @param Nome do Conveniao
	 * @return Lista com os códigos dos conveniados (Tipo Integer)
	 */
	public List<Integer> getListNuConveniadosByNome(String nomeConveniado)
	{
		String sql = "SELECT caixa.nu_convenio_c23 AS nuConvenio FROM icosm001.iacvw009_caixa_aqui caixa WHERE no_empresa ILIKE :nomeConveniado OR no_fantasia ILIKE :nomeConveniado";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		
		query.setParameter("nomeConveniado", "%" + nomeConveniado + "%");

		return (List<Integer>) query.list();
	}

	public boolean permitirExclusao(Integer nuContrato) {
		
		StringBuffer hql = new StringBuffer();
		
		hql.append("SELECT COUNT(c) ");
		
		hql.append("FROM Contrato c ");
		hql.append(" WHERE c.nuContrato = :nuContrato ");
		hql.append(" AND c.icTipoVerificacaoContrato = :icTipoVerificacao ");
		
		hql.append(" AND ( EXISTS (select vc.contrato.nuContrato from ");
		hql.append(" VerificacaoContrato AS vc ");
		hql.append(" WHERE c.nuContrato = vc.contrato.nuContrato ");
		
		hql.append(" AND vc.icModoCriacao = :icModoCriacao ");
		hql.append(" AND vc.icEstadoVerificacao = 'NV' ) ");
		
		hql.append(" AND NOT EXISTS (select vp.nuContrato from ");
		hql.append(" VerificacaoContratoParecer AS vp ");
		hql.append(" WHERE c.nuContrato = vp.nuContrato ) )");
			
		Session session = getSession();
		Query query = session.createQuery(hql.toString());
		
		query.setParameter("nuContrato", nuContrato);
		query.setParameter("icTipoVerificacao", Contrato.TIPO_VERIFICACAO_CONTRATO_ID_VERIFICACAO_PREVENTIVA);
		query.setParameter("icModoCriacao", VerificacaoContratoVO.INSERE_MANUAL);
				
		return (Long) query.uniqueResult() > 0;
	}
}
