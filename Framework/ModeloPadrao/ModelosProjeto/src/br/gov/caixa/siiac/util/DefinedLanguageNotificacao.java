/**
 * Copyright (c) 2009-2011 Caixa Econômica Federal. Todos os direitos
 * reservados.
 * 
 * Caixa Econômica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade
 * 
 * Este programa de computador foi desenvolvido sob demanda da CAIXA e está
 * protegido por leis de direitos autorais e tratados internacionais. As
 * condições de cópia e utilização do todo ou partes dependem de autoriza��o da
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
package br.gov.caixa.siiac.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IGerenteUnidadeBO;
import br.gov.caixa.siiac.bo.IParecerBO;
import br.gov.caixa.siiac.model.domain.TemplateNotificacao;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;
import br.gov.caixa.siiac.persistence.dao.IContratoDAO;
import br.gov.caixa.siiac.persistence.dao.IUnidadeDAO;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.view.mb.TemplateNotificacaoMB;
import br.gov.caixa.util.Utilities;

/**
 *
 * Arquivo que recupera as tags que podem ser utilizadas no template de notificação, lendo do arquivo tagdefinitionNotificacao.lng, de acordo com a segunda coluna de cada linha.
 * Ex: 
 * 	Ano do parecer;<ano_parecer>;2012
 * 	
 *  - A primeira coluna representa o label que aparecerá no combobox.
 *  - A segunda coluna representa o valor que será mostrado no modelo parecer e também como é o método que retornará essa informação. 
 *  	No caso deste exemplo, em que a tag é <ano_parecer>, o método a retornar essa informação deve ter a assinatura "public String getAnoParecer()"; 
 *	- A terceira coluna é apenas para visualização de exemplo do modelo.
 *
 * @author GIS Consult
 *
 */
@Service()
@Scope("prototype")
@Transactional
public class DefinedLanguageNotificacao 
{
    /** 
     * Mascara de Data 
     */  
	private static String SDATE_FORMAT_MASK = "dd/MM/yyyy";
	private static String SLOCALE_PORTUGUESE_LOWER = "pt";
	private static String SLOCALE_BRASILIAN_UPPER = "BR";

	
	private IUnidadeDAO unidadeDAO;
	private IContratoDAO contratoDAO;
	private IParecerBO parecerBO;
	private IGerenteUnidadeBO gerenteUnidadeBO;
	
	private static final String GERENTE = "GERENTE";
	private static final Short SURET = 5032;
	
	/**
	 * Formata uma Data
	 * @param value A Data a ser formatada
	 * @return A Data formatada
	 */
	private String localFormatDate(Date value) {
		if(value == null) {
			return "";
		}
		try {
			return new SimpleDateFormat(SDATE_FORMAT_MASK, new Locale(SLOCALE_PORTUGUESE_LOWER, SLOCALE_BRASILIAN_UPPER)).format(value);
		} catch (Exception e) {
			return "";
		}
	}
	
	@Autowired
	public void setUnidadeDAO(IUnidadeDAO unidadeDAO){
		this.unidadeDAO = unidadeDAO;
	}

	@Autowired
	public void setParecerBO(IParecerBO parecerBO){
		this.parecerBO = parecerBO;
	}
	
	@Autowired
	public void setContratoDAO(IContratoDAO contratoDAO) {
		this.contratoDAO = contratoDAO;
	}

	@Autowired
	public void setGerenteUnidadeBO(IGerenteUnidadeBO gerenteUnidadeBO){
		this.gerenteUnidadeBO = gerenteUnidadeBO;
	}
	
	private TemplateNotificacao notificacao;
	private IUsuario usuario;
	private VerificacaoContrato verificacaoContrato;
	private Integer matrGestorAssinatura;

	public TemplateNotificacao getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(TemplateNotificacao notificacao) {
		this.notificacao = notificacao;
	}

	public void setUsuario(IUsuario usuario) {
		this.usuario = usuario;
	}
	
	public void setMatrGestorAssinatura(Integer matrGestorAssinatura) {
		this.matrGestorAssinatura = matrGestorAssinatura;
	}
	
	public VerificacaoContrato getVerificacaoContrato() {
		return verificacaoContrato;
	}

	public void setVerificacaoContrato(VerificacaoContrato verificacaoContrato) {
		this.verificacaoContrato = verificacaoContrato;
	}

	//Tags
	
	//Assunto da notificação
	public String getAssuntoNotificacao()
	{
		try {
			if(Utilities.empty(notificacao) || Utilities.empty(notificacao.getNoAssuntoNotificacao())){
				LogCEF.debug("getNoAssuntoNotificacao(): A notificação ou o noAssuntoNotificacao estão em branco.");
				return "";
			}
			String result = this.notificacao.getNoAssuntoNotificacao();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Código do Cliente
	public String getCodigoCliente(){
		try {
			if(Utilities.empty(verificacaoContrato.getContrato()) || Utilities.empty(verificacaoContrato.getContrato().getCoCliente())){
				LogCEF.debug("getCodigoCliente(): O contrato ou getCoCliente estão em branco.");
				return "";
			}
			String result = this.verificacaoContrato.getContrato().getCoCliente(); 
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	//Código do Contrato
	public String getCodigoContrato(){
		try {
			if(Utilities.empty(verificacaoContrato.getContrato()) || Utilities.empty(verificacaoContrato.getContrato().getCoContrato())){
				LogCEF.debug("getCodigoContrato(): O contrato está em branco.");
				return "";
			}
			String result = this.verificacaoContrato.getContrato().getCoContrato(); 
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Código do Tomador
	public String getCodigoTomador(){
		try {
			if(Utilities.empty(verificacaoContrato.getContrato()) || Utilities.empty(verificacaoContrato.getContrato().getCoCliente())){
				LogCEF.debug("getCodigoCliente(): O contrato ou getCoCliente estão em branco.");
				return "";
			}
			String result = this.verificacaoContrato.getContrato().getCoCliente(); 
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Data da notificação
	public String getDataNotificacao(){
		try {
			String result = localFormatDate(new Date());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Data Envio AV Gestão
	public String getDataEnvioAvGestao()
	{
		try {
			if(Utilities.empty(verificacaoContrato) || Utilities.empty(verificacaoContrato.getDtInclusaoVerificacao()) 
					|| Utilities.empty(verificacaoContrato.getServicoVerificacaoProduto()) || Utilities.empty(verificacaoContrato.getServicoVerificacaoProduto().getPzAvgestao())){
				LogCEF.debug("getDtInclusaoVerificacao(): A verificacaoContrato ou getDtInclusaoVerificacao ou getPzAvgestao estão em branco.");
				return "";
			}
			Utilities.addDays(verificacaoContrato.getDtInclusaoVerificacao(), 
					verificacaoContrato.getServicoVerificacaoProduto().getPzAvgestao());	
			String result = localFormatDate(verificacaoContrato.getDtInclusaoVerificacao());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}		
	}
	
	//Data Envio AV SURET
	public String getDataEnvioAvSuret()
	{		
		try {
			if(Utilities.empty(verificacaoContrato) || Utilities.empty(verificacaoContrato.getDtInclusaoVerificacao()) 
					|| Utilities.empty(verificacaoContrato.getServicoVerificacaoProduto()) || Utilities.empty(verificacaoContrato.getServicoVerificacaoProduto().getPzAvsuret())){
				LogCEF.debug("getDtInclusaoVerificacao(): A verificacaoContrato ou getDtInclusaoVerificacao ou getPzAvsuret estão em branco.");
				return "";
			}
			Utilities.addDays(verificacaoContrato.getDtInclusaoVerificacao(), 
					verificacaoContrato.getServicoVerificacaoProduto().getPzAvsuret());		
			String result = localFormatDate(verificacaoContrato.getDtInclusaoVerificacao()); 
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}		
	}
	
	//Data Limite de Verificação
	public String getDataLimiteVerificacao()
	{	
		try {
			if(Utilities.empty(verificacaoContrato) || Utilities.empty(verificacaoContrato.getDtLimiteVerificacao())){
				LogCEF.debug("getNomeCliente(): A verificacaoContrato ou getDtLimiteVerificacao estão em branco.");
				return "";
			}		
			String result = localFormatDate(verificacaoContrato.getDtLimiteVerificacao());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}		
	}
	
	//Nome da função do gerente da unidade remetente
	public String getNomeFuncaoGerenteUnidadeRemetente()
	{
		try {
			String result = this.unidadeDAO.getNomeFuncaoResponsavelByUnidade(SURET);
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Nome da função do responsável da unidade destinatária
	public String getNomeFuncaoResponsavelUnidadeDestinataria()
	{
		try {
			if (Utilities.empty(verificacaoContrato) || Utilities.empty(verificacaoContrato.getNuUnidadeResponsavelVerificacao()))
			{
				LogCEF.debug("getNomeFuncaoResponsavelUnidadeDestinataria(): A verificacaoContrato ou o nuUnidadeResponsavel estão em branco.");
				return "";
			}		 
			String result = this.unidadeDAO.getNomeFuncaoResponsavelByUnidade(verificacaoContrato.getNuUnidadeResponsavelVerificacao());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Nome da Unidade responsável pela verificação 
	public String getNomeUnidadeResponsavelVerificacao()
	{
		try {
			if(Utilities.empty(verificacaoContrato.getContrato()) || Utilities.empty(verificacaoContrato.getContrato().getNuUnidade())){
				LogCEF.debug("getNuUnidade(): O contrato ou getNuUnidade estão em branco.");
				return "";
			}
			String result = this.unidadeDAO.getNomeUnidadeByUnidade(verificacaoContrato.getContrato().getNuUnidade());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Nome da Unidade superior hierárquica de 1º nível da unidade responsável pela verificação
	public String getNomeUnidadeSuperiorPrimeiroNivel()
	{
		try {
			if (Utilities.empty(verificacaoContrato) || Utilities.empty(verificacaoContrato.getNuUnidadeResponsavelVerificacao()))
			{
				LogCEF.debug("getNomeUnidadeSuperiorPrimeiroNivel(): A verificacaoContrato ou o nuUnidadeResponsavel estão em branco.");
				return "";
			}		
			String result = unidadeDAO.getNomeUnidadeSuperiorPrimeiroNivel(verificacaoContrato.getNuUnidadeResponsavelVerificacao());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Nome da Unidade superior hierárquica de 2º nível da unidade responsável pela verificação
	public String getNomeUnidadeSuperiorSegundoNivel()
	{
		try {
			if (Utilities.empty(verificacaoContrato) || Utilities.empty(verificacaoContrato.getNuUnidadeResponsavelVerificacao()))
			{
				LogCEF.debug("getNomeUnidadeSuperiorSegundoNivel(): A verificacaoContrato ou o nuUnidadeResponsavel estão em branco.");
				return "";
			}		
			String result = unidadeDAO.getNomeUnidadeSuperiorSegundoNivel(verificacaoContrato.getNuUnidadeResponsavelVerificacao());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
		
	//Nome do Cliente
	public String getNomeCliente()
	{
		try {
			if(Utilities.empty(verificacaoContrato.getContrato()) || Utilities.empty(verificacaoContrato.getContrato().getNoCliente())){
				LogCEF.debug("getNomeCliente(): O contrato ou getNoCliente estão em branco.");
				return "";
			}
			String result = this.verificacaoContrato.getContrato().getNoCliente();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	//Nome do gerente da unidade remetente
	public String getNomeGerenteUnidadeRemetente()
	{
		try {
			String result = this.unidadeDAO.getNomeResponsavelSURET(SURET);
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Nome do produto
	public String getNomeProduto() {
		try {
			if(Utilities.empty(verificacaoContrato.getContrato()) || Utilities.empty(verificacaoContrato.getContrato().getProduto())){
				LogCEF.debug("getNomeProduto(): O contrato ou o produto estão em branco.");
				return "";
			}
			String result = this.verificacaoContrato.getContrato().getProduto().getCodigoFormatado() + " " + this.verificacaoContrato.getContrato().getProduto().getNoProduto();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Nome do tomador
	public String getNomeTomador()
	{
		try {
			if(Utilities.empty(verificacaoContrato.getContrato()) || Utilities.empty(verificacaoContrato.getContrato().getNoCliente())){
				LogCEF.debug("getNomeCliente(): O contrato ou getNoCliente estão em branco.");
				return "";
			}
			String result = this.verificacaoContrato.getContrato().getNoCliente();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	//Número da avaliação SIRIC 
	public String getNumeroAvaliacaoSiric(){
		try {
			if(Utilities.empty(verificacaoContrato.getContrato())){
				LogCEF.debug("getNumeroAvaliacaoSiric(): O contrato está em branco.");
				return "";
			}
			String result = this.verificacaoContrato.getContrato().getCoAvaliacaoSiric();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	//Número da avaliação SIRIC do tomador
	public String getNumeroAvaliacaoSiricTomador(){
		try {
			if(Utilities.empty(verificacaoContrato.getContrato())){
				LogCEF.debug("getNumeroAvaliacaoSiricTomador(): O contrato está em branco.");
				return "";
			}
			String result = this.verificacaoContrato.getContrato().getCoAvaliacaoSiricTomador();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Quantidade de Verificações que sensibilizarão o AV Gestão no dia, por categoria de produto 
	public String getQuantidadeVerificacoesAvGestaoCategoriaProduto() throws ParseException
	{
		try {
			if (Utilities.empty(verificacaoContrato)
					|| Utilities.empty(verificacaoContrato.getDtInclusaoVerificacao())
					|| Utilities.empty(verificacaoContrato.getContrato())
					|| Utilities.empty(verificacaoContrato.getContrato().getProduto())
					|| Utilities.empty(verificacaoContrato.getContrato().getProduto().getCategoriaProduto().getNuCategoriaProduto())
					
			){
				LogCEF.debug("getQuantidadeVerificacoesAvGestaoCategoriaProduto(): A verificacaoContrato ou getDtInclusaoVerificacao ou getPzAvsuret estão em branco.");
				return null;
			}		
			String result = contratoDAO.getQuantidadeVerificacoesAvGestaoSuretCategoriaProduto(verificacaoContrato.getContrato().getProduto().getCategoriaProduto().getNuCategoriaProduto(), new SimpleDateFormat("dd/MM/yyyy").parse(this.getDataEnvioAvGestao()));
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Quantidade de Verificações que sensibilizarão o AV SURET no dia, por categoria de produto 
	public String getQuantidadeVerificacoesAvSuretCategoriaProduto() throws ParseException
	{
		try {
			if (Utilities.empty(verificacaoContrato)
					|| Utilities.empty(verificacaoContrato.getDtInclusaoVerificacao())
					|| Utilities.empty(verificacaoContrato.getContrato())
					|| Utilities.empty(verificacaoContrato.getContrato().getProduto())
					|| Utilities.empty(verificacaoContrato.getContrato().getProduto().getCategoriaProduto().getNuCategoriaProduto())
					
			){
				LogCEF.debug("getQuantidadeVerificacoesAvSuretCategoriaProduto(): A verificacaoContrato ou getDtInclusaoVerificacao ou getPzAvsuret estão em branco.");
				return null;
			}		
			String result = contratoDAO.getQuantidadeVerificacoesAvGestaoSuretCategoriaProduto(verificacaoContrato.getContrato().getProduto().getCategoriaProduto().getNuCategoriaProduto(), new SimpleDateFormat("dd/MM/yyyy").parse(this.getDataEnvioAvSuret()));
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Quantidade de Verificações vencendo no dia, por categoria de produto 
	public String getQuantidadeVerificacoesVencendoCategoriaProduto() throws ParseException
	{
		try {
			if (Utilities.empty(verificacaoContrato)
					|| Utilities.empty(verificacaoContrato.getDtInclusaoVerificacao())
					|| Utilities.empty(verificacaoContrato.getContrato())
					|| Utilities.empty(verificacaoContrato.getContrato().getProduto())
					|| Utilities.empty(verificacaoContrato.getContrato().getProduto().getCategoriaProduto().getNuCategoriaProduto())
					
			){
				LogCEF.debug("getQuantidadeVerificacoesVencendoCategoriaProduto(): A verificacaoContrato ou getDtInclusaoVerificacao ou getPzAvsuret estão em branco.");
				return null;
			}		
			String result = contratoDAO.getQuantidadeVerificacoesVencendoCategoriaProduto(verificacaoContrato.getContrato().getProduto().getCategoriaProduto().getNuCategoriaProduto());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Referência da notificação
	public String getReferenciaNotificacao()
	{
		try {
			if(Utilities.empty(notificacao) || Utilities.empty(notificacao.getNoReferenciaNotificacao())){
				LogCEF.debug("getNoReferenciaNotificacao(): A notificação ou o getNoReferenciaNotificacao estão em branco.");
				return "";
			}
			String result = this.notificacao.getNoReferenciaNotificacao();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Sigla do tipo de unidade da unidade destinatária
	public String getSiglaTipoUnidadeUnidadeDestinataria()
	{
		try {
			if (Utilities.empty(verificacaoContrato) || Utilities.empty(verificacaoContrato.getNuUnidadeResponsavelVerificacao()))
			{
				LogCEF.debug("getSiglaTipoUnidadeUnidadeDestinataria(): A verificacaoContrato ou o nuUnidadeResponsavel estão em branco.");
				return "";
			}		
			String result = this.unidadeDAO.getSiglaUnidadeByUnidade(verificacaoContrato.getNuUnidadeResponsavelVerificacao());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Sigla do tipo de unidade da unidade remetente
	public String getSiglaTipoUnidadeUnidadeRemetente()
	{
		try {
			if (Utilities.empty(verificacaoContrato) || Utilities.empty(verificacaoContrato.getNuUnidadeResponsavelVerificacao()))
			{
				LogCEF.debug("getSiglaTipoUnidadeUnidadeRemetente(): A verificacaoContrato ou o nuUnidadeResponsavel estão em branco.");
				return "";
			}		
			String result = this.unidadeDAO.getSiglaUnidadeByUnidade(verificacaoContrato.getNuUnidadeResponsavelVerificacao());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//Tipo de Notificação
	public String getTipoNotificacao()
	{
		try {
			if(Utilities.empty(notificacao) || Utilities.empty(notificacao.getIcTipoNotificacao())){
				LogCEF.debug("getIcTipoNotificacao(): A notificação ou o getIcTipoNotificacao estão em branco.");
				return "";
			}		
			String result = ""; 
			if (notificacao.getIcTipoNotificacao().equals(TemplateNotificacaoMB.TIPO_NOTIFICACAO_ID_ALERTA_PRAZO))
				result = TemplateNotificacaoMB.TIPO_NOTIFICACAO_LABEL_ALERTA_PRAZO;
			else if (notificacao.getIcTipoNotificacao().equals(TemplateNotificacaoMB.TIPO_NOTIFICACAO_ID_AVISO_ABERTURA))
				result = TemplateNotificacaoMB.TIPO_NOTIFICACAO_LABEL_AVISO_ABERTURA;
			else if (notificacao.getIcTipoNotificacao().equals(TemplateNotificacaoMB.TIPO_NOTIFICACAO_ID_ENVIO_AV_GESTAO))
				result = TemplateNotificacaoMB.TIPO_NOTIFICACAO_LABEL_ENVIO_AV_GESTAO;
			else if (notificacao.getIcTipoNotificacao().equals(TemplateNotificacaoMB.TIPO_NOTIFICACAO_ID_ENVIO_AV_SURET))
				result = TemplateNotificacaoMB.TIPO_NOTIFICACAO_LABEL_ENVIO_AV_SURET;
			else
				result = TemplateNotificacaoMB.TIPO_NOTIFICACAO_LABEL_VENCIMENTO_PRAZO;
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
}