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
package br.gov.caixa.siiac.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.IGerenteUnidadeBO;
import br.gov.caixa.siiac.bo.IResumoParecerBO;
import br.gov.caixa.siiac.model.domain.Parecer;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.model.domain.UnidadeId;
import br.gov.caixa.siiac.persistence.dao.IDetalhesContratoDAO;
import br.gov.caixa.siiac.persistence.dao.IUnidadeDAO;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.view.mb.ContratoMB;
import br.gov.caixa.util.Utilities;

/**
 *
 * Arquivo que recupera as tags que podem ser utilizadas no parecer, lendo do arquivo tagdefinition.lng, de acordo com a segunda coluna de cada linha.
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
public class DefinedLanguage 
{
	private IUnidadeDAO unidadeDAO;
	private IDetalhesContratoDAO detalhesContratoDAO;
	private IResumoParecerBO resumoParecerBO;
	private IGerenteUnidadeBO gerenteUnidadeBO;
		
	private static final String GERENTE = "GERENTE";
	
    /** 
     * Mascara de Data 
     */  
	private static String SDATE_FORMAT_MASK = "dd/MM/yyyy";
	private static String SLOCALE_PORTUGUESE_LOWER = "pt";
	private static String SLOCALE_BRASILIAN_UPPER = "BR";
	
    /** 
     * Mascara de dinheiro para Real Brasileiro 
     */  
	private static String SCURRENCY_PREFIX = "R$";
	private static final DecimalFormat SCURRENCY_MASK = new DecimalFormat("###,###,##0.00");

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
	/**
	 * Formata um valor Moeda
	 * @param value O valor a ser formatado
	 * @return O valor formatado
	 */
	private String localFormatCurrency(Double value) {
		if(value == null) {
			return "";
		}
		try {
			return String.format("%s %s", SCURRENCY_PREFIX, SCURRENCY_MASK.format(value)); 
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	@Autowired
	public void setUnidadeDAO(IUnidadeDAO unidadeDAO){
		this.unidadeDAO = unidadeDAO;
	}

	@Autowired
	public void setResumoParecerBO(IResumoParecerBO resumoParecerBO){
		this.resumoParecerBO = resumoParecerBO;
	}
	
	@Autowired
	public void setDetalhesContratoDAO(IDetalhesContratoDAO detalhesContratoDAO) {
		this.detalhesContratoDAO = detalhesContratoDAO;
	}

	@Autowired
	public void setGerenteUnidadeBO(IGerenteUnidadeBO gerenteUnidadeBO){
		this.gerenteUnidadeBO = gerenteUnidadeBO;
	}
	
	private Parecer parecer;
	private IUsuario usuario;
	
	
	public void setParecer(Parecer parecer) {
		this.parecer = parecer;
	}
	
	public void setUsuario(IUsuario usuario) {
		this.usuario = usuario;
	}

	public String getSiglaTipoUnidadeResponsavelParecer(){
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getCoResponsavelParecer())){
				LogCEF.debug("getSiglaTipoUnidadeResponsavelParecer(): O parecer ou o coResponsavelParecer estão em branco.");
				return "";
			}
	//		Unidade unidade = this.unidadeDAO.getUnidadeByEmpregado(this.parecer.getCoResponsavelParecer());
			Unidade unidade = this.unidadeDAO.findByNuUnidade(usuario.getUnidade());
			if(Utilities.empty(unidade) || Utilities.empty(unidade.getSgTipoUnidade())){
				LogCEF.debug("getSiglaTipoUnidadeResponsavelParecer(): A unidade ou a sgTipoUnidade estão em branco.");
				return "";
			}
			String result = unidade.getSgTipoUnidade();
			return (Utilities.empty(result)) ? "" : result;
		}
		catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public String getNomeUnidadeResponsavelParecer() {
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getCoResponsavelParecer())){
				LogCEF.debug("getNomeUnidadeResponsavelParecer(): O parecer ou o coResponsavelParecer estão em branco.");
				return "";
			}			
//			return this.unidadeDAO.getUnidadeByEmpregado(this.parecer.getCoResponsavelParecer()).getNomeAbreviado();
			String result = this.unidadeDAO.getNomeUnidadeByUnidade(this.usuario.getUnidade());
			return (Utilities.empty(result)) ? "" : result;
		}
		catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCodigoUnidadeResponsavelParecer() {
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getCoResponsavelParecer())){
				LogCEF.debug("getCodigoUnidadeResponsavelParecer(): O parecer ou o coResponsavelParecer estão em branco.");
				return "";
			}			
//			Short nuUnidade = this.unidadeDAO.getUnidadeByEmpregado(this.parecer.getCoResponsavelParecer()).getId().getNuUnidade();
			Short nuUnidade = this.unidadeDAO.findByNuUnidade(this.usuario.getUnidade()).getId().getNuUnidade();
			if (nuUnidade != null && nuUnidade > 0) {
				String result = nuUnidade.toString();
				return (Utilities.empty(result)) ? "" : result;
			}
			else
				return "";
		}
		catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getNumeroParecer() {
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getParecerId()) || Utilities.empty(parecer.getParecerId().getNuParecer())){
				LogCEF.debug("getNumeroParecer(): O parecer ou o parecerId estão em branco.");
				return "";
			}			
			String result = ConvertUtils.padZerosLeft(String.valueOf(this.parecer.getParecerId().getNuParecer()), 5);
			return (Utilities.empty(result)) ? "" : result;
		}
		catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getAnoParecer() {
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getParecerId()) || Utilities.empty(parecer.getParecerId().getAaParecer())){
				LogCEF.debug("getAnoParecer(): O parecer ou o parecerId estão em branco.");
				return "";
			}			
			String result = String.valueOf(this.parecer.getParecerId().getAaParecer());
			return (Utilities.empty(result)) ? "" : result;
		}
		catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeCidadeUnidadeResponsavelParecer() {
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getCoResponsavelParecer())
					|| Utilities.empty(this.usuario)
			) {
				LogCEF.debug("getNomeCidadeUnidadeResponsavelParecer(): O parecer, o coResponsavelParecer ou o Usuário logado é inválido.");
				return "";
			}
			String result = this.unidadeDAO.getLocalidade(this.usuario.getUnidade());
			return (Utilities.empty(result)) ? "" : result;			
		}
		catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getDataParecer(){
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getDtParecer())){
				LogCEF.debug("getDataParecer(): O parecer ou o dtParecer estão em branco.");
				return "";
			}
			String result = localFormatDate(parecer.getDtParecer());
			return (Utilities.empty(result)) ? "" : result;
		}
		catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getSiglaTipoUnidadeResponsavelContrato(){
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getNuUnidade()) || Utilities.empty(parecer.getContrato().getNuNatural()) ){
				LogCEF.debug("getSiglaTipoUnidadeResponsavelContrato(): O parecer ou o contrato estão em branco.");
				return "";
			}
			String result = this.unidadeDAO.findById(new UnidadeId(parecer.getContrato().getNuUnidade(), parecer.getContrato().getNuNatural())).getSgTipoUnidade();
			return (Utilities.empty(result)) ? "" : result;
		}
		catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeUnidadeDestinataria(){
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getParecerId().getNuUnidade()) || Utilities.empty(parecer.getParecerId().getNuNatural()) ){
				LogCEF.debug("getNomeUnidadeDestinataria(): O parecer ou o parecerId estão em branco.");
				return "";
			}
			String result = this.unidadeDAO.findById(
						new UnidadeId(
									parecer.getParecerId().getNuUnidade(), 
									Integer.parseInt(parecer.getParecerId().getNuNatural().toString())
									)
						).getNomeCompleto();
			return (Utilities.empty(result)) ? "" : result;
		}
		catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getAssuntoParecer(){
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getNoParecer())){
				LogCEF.debug("getAssuntoParecer(): O parecer ou o noParecer estão em branco.");
				return "";
			}
			String result = this.parecer.getNoParecer();
			return (Utilities.empty(result)) ? "" : result;
		}
		catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getReferenciaParecer(){
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getTemplateParecer()) 
					|| Utilities.empty(parecer.getTemplateParecer().getNoReferenciaParecer())){
				LogCEF.debug("getReferenciaParecer(): O parecer ou o templateParecer estão em branco.");
				return "";
			}
			String result = parecer.getTemplateParecer().getNoReferenciaParecer();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getDataContratacao()
	{
		try {
			if (Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getDtContrato()))
			{
				LogCEF.debug("getDataContratacao(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = localFormatDate(parecer.getContrato().getDtContrato()); 
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getValorContrato()
	{
		try {
			if (Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getVrNominalContrato()))
			{
				LogCEF.debug("getValorContrato(): O parecer ou o contrato estão em branco.");
				return "";
			}					
			String result = "R$ " + SCURRENCY_MASK.format(parecer.getContrato().getVrNominalContrato());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeFuncaoResponsavelUnidadeDestinataria(){
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getParecerId().getNuUnidade())){
				LogCEF.debug("getNomeFuncaoResponsavelUnidadeDestinataria(): O parecer ou o parecerId estão em branco.");
				return "";
			}		
			String noFuncao = this.unidadeDAO.getFuncaoResponsavelUnidade(parecer.getParecerId().getNuUnidade());
			if (Utilities.notEmpty(noFuncao))
				return noFuncao;
			else
			{
				LogCEF.debug("getNomeFuncaoResponsavelUnidadeDestinataria(): Não foi encontrado função para o a unidade: " + parecer.getParecerId().getNuUnidade()
						+ ". Retorno padrão será: GERENTE");
				// Retorno da palavra Gerente é relacionado ao caso 2188
				return GERENTE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeProduto() {
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato()) 
					|| Utilities.empty(parecer.getContrato().getProduto())){
				LogCEF.debug("getNomeProduto(): O parecer ou o contrato ou o produto estão em branco.");
				return "";
			}
			String result = this.parecer.getContrato().getProduto().getCodigoFormatado() + " " + this.parecer.getContrato().getProduto().getNoProduto();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCodigoContrato(){
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato())){
				LogCEF.debug("getCodigoContrato(): O parecer ou o contrato estão em branco.");
				return "";
			}
			String result = this.parecer.getContrato().getCoContrato();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNumeroAvaliacaoSiric(){
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato())){
				LogCEF.debug("getNumeroAvaliacaoSiric(): O parecer ou o contrato estão em branco.");
				return "";
			}
			String result = this.parecer.getContrato().getCoAvaliacaoSiric();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCodigoCliente(){
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato())){
				LogCEF.debug("getCodigoCliente(): O parecer ou o contrato estão em branco.");
				return "";
			}
			String result = this.parecer.getContrato().getCoCliente();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeCliente(){
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato())
					|| Utilities.empty(this.parecer.getContrato().getNoCliente())){
				LogCEF.debug("getNomeCliente(): O parecer ou o contrato ou o nome do cliente estão em branco.");
				return "";
			}
			String result = this.parecer.getContrato().getNoCliente().toUpperCase();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	// TODO testar a formatação de várias formas
	public String getNumeroIdentificadorCliente(){
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato())){
				LogCEF.debug("getNumeroIdentificadorCliente(): O parecer ou o contrato estão em branco.");
				return "";
			}
			String result = ContratoMB.formataDocumento(this.parecer.getContrato());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	

	public String getNomeEmpregadoResponsavelParecer() {
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getCoResponsavelParecer())){
				LogCEF.debug("getNomeEmpregadoResponsavelParecer(): O parecer ou o coResponsavelParecer estão em branco.");
				return "";
			}
			String result = this.unidadeDAO.getNomeEmpregado(parecer.getCoResponsavelParecer());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeFuncaoResponsavelParecer(){
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getCoResponsavelParecer())){
				LogCEF.debug("getNomeFuncaoResponsavelParecer(): O parecer ou o coResponsavelParecer estão em branco.");
				return "";
			}
			String funcaoCargo = "";
			funcaoCargo = this.unidadeDAO.getFuncaoEmpregado(parecer.getCoResponsavelParecer());
			if (Utilities.notEmpty(funcaoCargo))
				return funcaoCargo;
			else {
				String result = this.unidadeDAO.getCargoEmpregado(parecer.getCoResponsavelParecer());
				return (Utilities.empty(result)) ? "" : result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCodigoTomador(){
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato())){
				LogCEF.debug("getCodigoTomador(): O parecer ou o contrato estão em branco.");
				return "";
			}
			String result = this.parecer.getContrato().getCoCliente();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeTomador(){
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato())
					|| Utilities.empty(this.parecer.getContrato().getNoCliente())){
				LogCEF.debug("getNomeTomador(): O parecer ou o contrato ou o nome do tomador estão em branco.");
				return "";
			}
			String result = this.parecer.getContrato().getNoCliente().toUpperCase();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	// TODO testar a formatação de várias formas
	public String getNumeroIdentificadorTomador(){
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato())){
				LogCEF.debug("getNumeroIdentificadorTomador(): O parecer ou o contrato estão em branco.");
				return "";
			}
			String result = ContratoMB.formataDocumento(this.parecer.getContrato());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNumeroAvaliacaoSiricTomador(){
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato())){
				LogCEF.debug("getNumeroAvaliacaoSiricTomador(): O parecer ou o contrato estão em branco.");
				return "";
			}
			String result = this.parecer.getContrato().getCoAvaliacaoSiricTomador();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getListaDocumentosApontamentoInconforme(){
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getVerificacaoContratoParecer())){
				LogCEF.debug("getListaDocumentosApontamentoInconforme(): O parecer ou a verificacaoContratoParecer estão em branco.");
				return "";
			}
			String result = this.resumoParecerBO.getResumoPreviaParecerInconformidadesComParecer(parecer.getVerificacaoContratoParecer().getNuVerificacaoContrato());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getListaDocumentosConformesComObservacao(){
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getVerificacaoContratoParecer())){
				LogCEF.debug("getListaDocumentosConformesComObservacao(): O parecer ou a verificacaoContratoParecer estão em branco.");
				return "";
			}
			String result = this.resumoParecerBO.getResumoPreviaParecerConformidadesComParecer(parecer.getVerificacaoContratoParecer().getNuVerificacaoContrato());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeGestorAssinaturaParecer(){
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getCoGerenteParecer())){
				LogCEF.debug("getNomeGestorAssinaturaParecer(): A matrícula do Gestor para Assinatura está em branco.");
				return "";
			}
			String result = gerenteUnidadeBO.findByNuMatricula(parecer.getCoGerenteParecer()).getNoPessoa();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeFuncaoGestorAssinaturaParecer(){
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getCoGerenteParecer())){
				LogCEF.debug("getNomeFuncaoGestorAssinaturaParecer(): A matrícula do Gestor para Assinatura está em branco.");
				return "";
			}
			String result = gerenteUnidadeBO.findByNuMatricula(parecer.getCoGerenteParecer()).getNoFuncao(); 
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getDataValidadeAvaliacaoOperacao()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato().getDtValidadeAvaliacaoSiric())){
				LogCEF.debug("getDataValidadeAvaliacaoOperacao(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = localFormatDate(parecer.getContrato().getDetalhesContrato().getDtValidadeAvaliacaoSiric());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getDataValidadeAvaliacaoTomador()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato().getDtValidadeAvaliacaoTomador())){
				LogCEF.debug("getDataValidadeAvaliacaoTomador(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = localFormatDate(parecer.getContrato().getDetalhesContrato().getDtValidadeAvaliacaoTomador());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeUnidadeAlienacaoBensImoveis()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getNuUnidade())){
				LogCEF.debug("getNomeUnidadeAlienacaoBensImoveis(): O parecer ou o contrato estão em branco.");
				return "";
			}
			String result = unidadeDAO.getNomeUnidadeAlienacaoBensImoveisByUnidade(parecer.getContrato().getNuUnidade());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCodigoUnidadeAlienacaoBensImoveis()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getNuUnidade())){
				LogCEF.debug("getCodigoUnidadeAlienacaoBensImoveis(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = unidadeDAO.getCodigoUnidadeAlienacaoBensImoveisByUnidade(parecer.getContrato().getNuUnidade());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeUnidadeResponsavelContrato()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getNuUnidade())){
				LogCEF.debug("getNomeUnidadeResponsavelContrato(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = unidadeDAO.getNomeUnidadeByUnidade(parecer.getContrato().getNuUnidade());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCodigoUnidadeResponsavelContrato()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getNuUnidade())){
				LogCEF.debug("getCodigoUnidadeResponsavelContrato(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = parecer.getContrato().getNuUnidade().toString();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeUnidadeSuperiorHierarquicaResponsavelContrato()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getNuUnidade())){
				LogCEF.debug("getNomeUnidadeSuperiorHierarquicaResponsavelContrato(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = unidadeDAO.getNomeUnidadeSuperiorPrimeiroNivel(parecer.getContrato().getNuUnidade());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCodigoUnidadeSuperiorHierarquicaResponsavelContrato()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getNuUnidade())){
				LogCEF.debug("getCodigoUnidadeSuperiorHierarquicaResponsavelContrato(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = unidadeDAO.getCodigoUnidadeSuperiorPrimeiroNivel(parecer.getContrato().getNuUnidade());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeConveniadoResponsavelContrato()
	{
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato()) 
					|| Utilities.empty(parecer.getContrato().getDetalhesContrato()) 
					|| Utilities.empty(parecer.getContrato().getDetalhesContrato().getNuConveniado())){
				LogCEF.debug("getNomeConveniadoResponsavelContrato(): O parecer ou o contrato estão em branco.");
				return "";
			}
			String nomeConveniado = detalhesContratoDAO.getNomeByNuConveniado(parecer.getContrato().getDetalhesContrato().getNuConveniado()); 
			if(Utilities.empty(nomeConveniado)) {
				LogCEF.debug("getNomeConveniadoResponsavelContrato(): O nome do conveniado está em branco.");
				return "";
			}
			String result = nomeConveniado.toUpperCase();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getSiglaTipoUnidadeBensImoveisVinculacaoResponsavel()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getNuUnidade())){
				LogCEF.debug("getSiglaTipoUnidadeBensImoveisVinculacaoResponsavel(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = unidadeDAO.getSiglaUnidadeAlienacaoBensImoveisByUnidade(parecer.getContrato().getNuUnidade());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getSiglaTipoUnidadeSuperiorHierarquicaResponsavelContrato()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getNuUnidade())){
				LogCEF.debug("getSiglaTipoUnidadeSuperiorHierarquicaResponsavelContrato(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = unidadeDAO.getSiglaUnidadeSuperiorPrimeiroNivel(parecer.getContrato().getNuUnidade());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNumeroIdentificadorEmpreendimento()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato())){
				LogCEF.debug("getNumeroIdentificadorEmpreendimento(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = parecer.getContrato().getDetalhesContrato().getNuIdentificadorEmpreendimento();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeEmpreendimento()
	{
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato()) 
					|| Utilities.empty(parecer.getContrato().getDetalhesContrato())
					|| Utilities.empty(parecer.getContrato().getDetalhesContrato().getNoEmpreendimento())){
				LogCEF.debug("getNomeEmpreendimento(): O parecer ou o contrato ou o nome do empreendimento estão em branco.");
				return "";
			}		
			String result = parecer.getContrato().getDetalhesContrato().getNoEmpreendimento().toUpperCase();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getNumeroIdentificadorVendedor()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato())){
				LogCEF.debug("getNumeroIdentificadorVendedor(): O parecer ou o contrato estão em branco.");
				return "";
			}
			String result = parecer.getContrato().getDetalhesContrato().getNuIdentificadorVendedor();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomeVendedor()
	{
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato()) 
					|| Utilities.empty(parecer.getContrato().getDetalhesContrato())
					|| Utilities.empty(parecer.getContrato().getDetalhesContrato().getNoVendedor())){
				LogCEF.debug("getNomeVendedor(): O parecer ou o contrato ou o nome do vendedor estão em branco.");
				return "";
			}		
			String result = parecer.getContrato().getDetalhesContrato().getNoVendedor().toUpperCase();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getListaDocumentosItemDataValidade()
	{
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getVerificacaoContratoParecer())){
				LogCEF.debug("getListaDocumentosItemDataValidade(): O parecer ou a verificacaoContratoParecer estão em branco.");
				return "";
			}
			String result = this.resumoParecerBO.getResumoPreviaParecerItemDataValidade(parecer.getVerificacaoContratoParecer().getNuVerificacaoContrato());
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getNumeroDamp()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato().getNoCampoLivre1())){
				LogCEF.debug("getNumeroDamp(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = parecer.getContrato().getDetalhesContrato().getNoCampoLivre1();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getDataDamp()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato().getNoCampoLivre2())){
				LogCEF.debug("getDataDamp(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = parecer.getContrato().getDetalhesContrato().getNoCampoLivre2();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getNomePrimeiroCoObrigado()
	{
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato()) 
					|| Utilities.empty(parecer.getContrato().getDetalhesContrato()) 
					|| Utilities.empty(parecer.getContrato().getDetalhesContrato().getNoCampoLivre4())){
				LogCEF.debug("getNomePrimeiroCoObrigado(): O parecer ou o contrato ou o nome do primeiro co-obrigado estão em branco.");
				return "";
			}		
			String result = parecer.getContrato().getDetalhesContrato().getNoCampoLivre4().toUpperCase();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCodPrimeiroCoObrigado()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato().getNoCampoLivre5())){
				LogCEF.debug("getCodPrimeiroCoObrigado(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = parecer.getContrato().getDetalhesContrato().getNoCampoLivre5();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getNomeSegundoCoObrigado()
	{
		try {
			if(Utilities.empty(parecer) 
					|| Utilities.empty(parecer.getContrato()) 
					|| Utilities.empty(parecer.getContrato().getDetalhesContrato()) 
					|| Utilities.empty(parecer.getContrato().getDetalhesContrato().getNoCampoLivre6())){
				LogCEF.debug("getNomeSegundoCoObrigado(): O parecer ou o contrato ou o nome do segundo co-obrigado estão em branco.");
				return "";
			}		
			String result = parecer.getContrato().getDetalhesContrato().getNoCampoLivre6().toUpperCase();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCodSegundoCoObrigado()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato().getNoCampoLivre7())){
				LogCEF.debug("getCodSegundoCoObrigado(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = parecer.getContrato().getDetalhesContrato().getNoCampoLivre7();
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getValorRessarcimento()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato()) || Utilities.empty(parecer.getContrato().getDetalhesContrato().getNoCampoLivre3())){
				LogCEF.debug("getValorRessarcimento(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String result = localFormatCurrency(Double.parseDouble(parecer.getContrato().getDetalhesContrato().getNoCampoLivre3()));
			return (Utilities.empty(result)) ? "" : result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCodigoContratoMascara()
	{
		try {
			if(Utilities.empty(parecer) || Utilities.empty(parecer.getContrato()) || Utilities.empty(parecer.getContrato().getNuUnidade()) 
					|| Utilities.empty(parecer.getContrato().getProduto()) || Utilities.empty(parecer.getContrato().getProduto().getOperacaoFormatada()) 
					|| Utilities.empty(parecer.getContrato().getCoContrato())) {
				LogCEF.debug("getCodigoContratoMascara(): O parecer ou o contrato estão em branco.");
				return "";
			}		
			String unidade = ConvertUtils.padZerosLeft(String.valueOf(parecer.getContrato().getNuUnidade()), 4);
			String contrato = ConvertUtils.padZerosLeft(String.valueOf(parecer.getContrato().getCoContrato().substring(0, parecer.getContrato().getCoContrato().length()-1)), 8);
			String verificacao = parecer.getContrato().getCoContrato().substring(parecer.getContrato().getCoContrato().length()-1);		
			return unidade + "." + parecer.getContrato().getProduto().getOperacaoFormatada() + "." + contrato + "-" + verificacao;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
