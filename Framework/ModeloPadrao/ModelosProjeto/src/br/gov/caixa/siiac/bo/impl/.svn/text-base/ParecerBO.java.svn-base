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
package br.gov.caixa.siiac.bo.impl;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IBuildTemplateParecerBO;
import br.gov.caixa.siiac.bo.IEfetuaVerificacaoBO;
import br.gov.caixa.siiac.bo.IParecerBO;
import br.gov.caixa.siiac.exception.SIIACRuntimeException;
import br.gov.caixa.siiac.model.domain.ArquivoImagemParecer;
import br.gov.caixa.siiac.model.domain.ArquivoImagemParecerId;
import br.gov.caixa.siiac.model.domain.ControleNumeroParecer;
import br.gov.caixa.siiac.model.domain.Parecer;
import br.gov.caixa.siiac.model.domain.ParecerId;
import br.gov.caixa.siiac.model.domain.ResultadoApontamentoProduto;
import br.gov.caixa.siiac.model.domain.ResultadoApontamentoProdutoParecer;
import br.gov.caixa.siiac.model.domain.TemplateParecer;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoObservacoes;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoObservacoesParecer;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoParecer;
import br.gov.caixa.siiac.persistence.dao.IArquivoImagemParecerDAO;
import br.gov.caixa.siiac.persistence.dao.IControleNumeroParecerDAO;
import br.gov.caixa.siiac.persistence.dao.IParecerDAO;
import br.gov.caixa.siiac.persistence.dao.IPropriedadesDAO;
import br.gov.caixa.siiac.persistence.dao.IResultadoApontamentoProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IResultadoApontamentoProdutoParecerDAO;
import br.gov.caixa.siiac.persistence.dao.ITemplateParecerDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoObservacaoDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoObservacaoParecerDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoParecerDAO;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.util.AppContext;
import br.gov.caixa.siiac.util.DefinedLanguage;
import br.gov.caixa.siiac.util.FormatUtil;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class ParecerBO implements IParecerBO{
	
	/**
	 * Variável utilizada para controlar sincronismo entre a geração de pareceres, onde só poderá um usuário emitir parecer por vez.
	 */
	static String TOKEN_GERA_PARECER = "TOKEN_GERA_PARECER";
	
	private IEfetuaVerificacaoBO efetuaVerificacaoBO;
	private IParecerDAO parecerDAO;
	private ITemplateParecerDAO templateParecerDAO;
	private IControleNumeroParecerDAO controleNumeroParecerDAO;
	private IVerificacaoContratoDAO verificacaoContratoDAO;
	private IVerificacaoContratoParecerDAO verificacaoContratoParecerDAO;
	private IResultadoApontamentoProdutoDAO resultadoApontamentoProdutoDAO;
	private IResultadoApontamentoProdutoParecerDAO resultadoApontamentoProdutoParecerDAO;
	private IVerificacaoContratoObservacaoDAO verificacaoContratoObservacaoDAO;
	private IVerificacaoContratoObservacaoParecerDAO verificacaoContratoObservacaoParecerDAO;
	private IBuildTemplateParecerBO buildTemplateParecerBO;
	private IArquivoImagemParecerDAO arquivoImagemParecerDAO;
	private IPropriedadesDAO propriedadesDAO;

	private static final String QUEBRA_DE_LINHA = "<br/>";
	
	private static final String PARECER_INCONFORME = "INCONFORME";
	private static final String PARECER_CONFORME = "CONFORME";
	
	@Autowired
	public void setEfetuaVerificacaoBO(IEfetuaVerificacaoBO efetuaVerificacaoBO){
		this.efetuaVerificacaoBO = efetuaVerificacaoBO;
	}
	
	@Autowired
	public void setParecerDAO(IParecerDAO parecerDAO){
		this.parecerDAO = parecerDAO;
	}
	
	@Autowired
	public void setTemplateParecerDAO(ITemplateParecerDAO templateParecerDAO){
		this.templateParecerDAO = templateParecerDAO;
	}
	
	@Autowired
	public void setControleNumeroParecerDAO(IControleNumeroParecerDAO controleNumeroParecerDAO){
		this.controleNumeroParecerDAO = controleNumeroParecerDAO;
	}
	
	@Autowired
	public void setVerificacaoContratoDAO(IVerificacaoContratoDAO verificacaoContratoDAO){
		this.verificacaoContratoDAO = verificacaoContratoDAO;
	}
	
	@Autowired
	public void setVerificacaoContratoParecerDAO(IVerificacaoContratoParecerDAO verificacaoContratoParecerDAO){
		this.verificacaoContratoParecerDAO = verificacaoContratoParecerDAO;
	}
	
	@Autowired
	public void setResultadoApontamentoProdutoDAO(IResultadoApontamentoProdutoDAO resultadoApontamentoProdutoDAO){
		this.resultadoApontamentoProdutoDAO = resultadoApontamentoProdutoDAO;
	}
	
	@Autowired
	public void setVerificacaoContratoObservacaoDAO(IVerificacaoContratoObservacaoDAO verificacaoContratoObservacaoDAO){
		this.verificacaoContratoObservacaoDAO = verificacaoContratoObservacaoDAO;
	}
	
	@Autowired
	public void setVerificacaoContratoObservacaoParecerDAO(IVerificacaoContratoObservacaoParecerDAO verificacaoContratoObservacaoParecerDAO){
		this.verificacaoContratoObservacaoParecerDAO = verificacaoContratoObservacaoParecerDAO;
	}
	
	@Autowired
	public void setResultadoApontamentoProdutoParecerDAO(IResultadoApontamentoProdutoParecerDAO resultadoApontamentoProdutoParecerDAO){
		this.resultadoApontamentoProdutoParecerDAO = resultadoApontamentoProdutoParecerDAO;
	}
	
	@Autowired
	public void setBuildTemplateParecerBO(IBuildTemplateParecerBO buildTemplateParecerBO){
		this.buildTemplateParecerBO = buildTemplateParecerBO;
	}
	
	@Autowired
	public void setArquivoImagemParecerDAO(IArquivoImagemParecerDAO arquivoImagemParecerDAO){
		this.arquivoImagemParecerDAO = arquivoImagemParecerDAO;
	}
	
	@Autowired
	public void setPropriedadesDAO(IPropriedadesDAO propriedadesDAO){
		this.propriedadesDAO = propriedadesDAO;
	}
	
	
	@Transactional
	public Parecer findById(Integer nuParecer, Short anoParecer, Short nuUnidade, Short nuNatural) {
		return this.parecerDAO.findById(new ParecerId(nuParecer, anoParecer, nuUnidade, nuNatural));
	}
	
	@Transactional
	public Parecer geraParecer(Integer nuVerificacaoContrato, IUsuario usuario, Integer nuMatriculaGerenteUnidadeSelecionado, String caminhoPastaImagens, Boolean... emitirParecer) throws SIIACRuntimeException {
		try{
			
			synchronized (TOKEN_GERA_PARECER) {
				// Valida se a verificação está conforme
				Boolean isConforme = this.efetuaVerificacaoBO.isConforme(nuVerificacaoContrato);
				
				// Busca a verificação a ser trabalhada
				VerificacaoContrato verificacao = this.verificacaoContratoDAO.findById(nuVerificacaoContrato);
				
				// Move registros para a tabel de verificação com parecer e retorna o objeto 
				VerificacaoContratoParecer verificacaoParecer = this.moveRegistrosParaTabelaComParecer(verificacao, isConforme);
				
				// Insere o parecer e retorna o objeto
				Parecer parecer = this.insereParecer(verificacao, verificacaoParecer, isConforme, usuario, nuMatriculaGerenteUnidadeSelecionado);
				
				// Gera o PDF do parecer e insere na tabela
				this.geraArquivoImagemParecer(parecer, usuario, caminhoPastaImagens);
				
				return parecer;
			}
			
		} catch (Exception e) {
			LogCEF.error("Erro na emissão do parecer, na parte SYNCHRONIZED");
			e.printStackTrace();
		}
		return null;
	}

	public VerificacaoContratoParecer moveRegistrosParaTabelaComParecer(VerificacaoContrato verificacaoContrato, Boolean isConforme) {
		VerificacaoContratoParecer verificacaoParecer = this.copyVerificacaoContrato(verificacaoContrato, isConforme);
		this.copyResultadoApontamentoProduto(verificacaoParecer);
		this.copyVerificacaoContratoObservacoes(verificacaoParecer);
		this.deleteVerificacaoContratoObservacoesSemParecer(verificacaoContrato);		
		return verificacaoParecer;
	}
	
	public Integer getNumeroParecer(Short aaParecer, Short nuUnidade, Integer nuNatural) {
		
		Integer lastId = this.parecerDAO.getLastId(aaParecer, nuUnidade, nuNatural);
		
		Integer nextId;
		if(lastId == null){
			nextId = 1;
		}
		else{
			nextId = lastId + 1;
		}
		
		ControleNumeroParecer controleNumeroParecer = new ControleNumeroParecer(nextId, aaParecer, nuUnidade, nuNatural);
		this.controleNumeroParecerDAO.merge(controleNumeroParecer);
		return nextId;
	}
	
	
	private Parecer insereParecer(VerificacaoContrato verificacao, VerificacaoContratoParecer verificacaoParecer, Boolean isConforme, IUsuario usuario, Integer matricula)
				throws SIIACRuntimeException {

		TemplateParecer templateParecer = this.templateParecerDAO.findByServicoVerificacaoProdutoAndResultado
													(
															verificacao.getServicoVerificacaoProduto(), 
															isConforme
														);
		if(Utilities.empty(templateParecer)){
			throw new SIIACRuntimeException("Este serviço de verificação não possui parecer para este tipo de resultado.");
		}
		
		Parecer parecer = new Parecer();
		
		Short aaParecer = Short.valueOf(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		 Integer nuParecer = this.getNumeroParecer(aaParecer,
				    usuario.getUnidade(),
				    usuario.getNuNatural());
		 
		parecer.setParecerId(new ParecerId(nuParecer, 
										   aaParecer, 
										   usuario.getUnidade(),
										   Short.valueOf(String.valueOf(usuario.getNuNatural()))));
		parecer.setDtParecer(new Date());
		parecer.setVerificacaoContratoParecer(verificacaoParecer);
		parecer.setContrato(verificacao.getContrato());
		parecer.setCoGerenteParecer(FormatUtil.formatMatricula(matricula));
		
		parecer.setTemplateParecer(templateParecer);
		parecer.setNoParecer(templateParecer.getNoAssuntoParecer());
		parecer.setCoResponsavelParecer(usuario.getMatricula());
		this.parecerDAO.merge(parecer);
		
		return parecer;
		
	}

	private VerificacaoContratoParecer copyVerificacaoContrato(VerificacaoContrato verificacao, Boolean isConforme) {
		
		VerificacaoContratoParecer verificacaoParecer = new VerificacaoContratoParecer();
		verificacaoParecer.setNuVerificacaoContrato(verificacao.getNuVerificacaoContrato());
		verificacaoParecer.setNuContrato(verificacao.getContrato().getNuContrato());
		verificacaoParecer.setNuServicoVerificacaoProduto(verificacao.getServicoVerificacaoProduto().getNuServicoVerificacaoProduto());
		verificacaoParecer.setNuUnidadeResponsavelVerificacao(verificacao.getNuUnidadeResponsavelVerificacao());
		verificacaoParecer.setNuNaturalUnidadeResponsavelVerificacao(verificacao.getNuNaturalUnidadeResponsavelVerificacao());
		verificacaoParecer.setNuChecklistServicoVerificacaoProduto(verificacao.getChecklist().getNuChecklistServicoVerificacaoProduto());
		verificacaoParecer.setDtInclusaoVerificacao(verificacao.getDtInclusaoVerificacao());
		verificacaoParecer.setDtVerificacao(verificacao.getDtVerificacao());
		verificacaoParecer.setDtLimiteVerificacao(verificacao.getDtLimiteVerificacao());
		verificacaoParecer.setCoResponsavelVerificacao(verificacao.getCoRspnlVerificacao());
		verificacaoParecer.setIcEstadoVerificacao( isConforme ? 
													VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_CONFORME :
													VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_INCONFORME);	
		verificacaoParecer.setIcSuspensa(verificacao.getIcSuspensa());
		if(Utilities.notEmpty(verificacao.getNuVerificacaoContratoPai())){
			verificacaoParecer.setVerificacaoContratoPai(this.verificacaoContratoParecerDAO.findById(verificacao.getNuVerificacaoContratoPai()));
		}
		verificacaoParecer.setIcUltimaHierarquia(Boolean.TRUE);
		
		this.verificacaoContratoParecerDAO.merge(verificacaoParecer);
		
		return verificacaoParecer;
	}
	
	private void copyResultadoApontamentoProduto(VerificacaoContratoParecer verificacaoParecer) {
		List<ResultadoApontamentoProduto> resultados = this.resultadoApontamentoProdutoDAO
																.findByNuVerificacaoContrato(verificacaoParecer.getNuVerificacaoContrato());
		
		for(ResultadoApontamentoProduto r : resultados) {
			ResultadoApontamentoProdutoParecer resultadoParecer = new ResultadoApontamentoProdutoParecer();
			resultadoParecer.setCoResponsavelResultado(r.getCoResponsavelResultado());
			resultadoParecer.setDeObservacao(r.getDeObservacao());
			resultadoParecer.setIcResultadoApontamentoChecklist(r.getIcResultadoApontamentoChecklist());
			resultadoParecer.setNuApontamentoChecklistProduto(r.getApontamentoChecklistProduto().getNuApontamentoChecklistProduto());
			resultadoParecer.setNuContrato(r.getContrato().getNuContrato());
			resultadoParecer.setNuResultadoApontamentoProduto(r.getNuResultadoApontamentoProduto());
			resultadoParecer.setVerificacaoContrato(verificacaoParecer);
			
			this.resultadoApontamentoProdutoParecerDAO.merge(resultadoParecer);
		}
	}
	
	private void copyVerificacaoContratoObservacoes(VerificacaoContratoParecer verificacaoParecer) {
		List<VerificacaoContratoObservacoes> observacoes = this.verificacaoContratoObservacaoDAO
																.findByNuVerificacaoContrato(verificacaoParecer.getNuVerificacaoContrato());
		
		for(VerificacaoContratoObservacoes vco : observacoes) {
			VerificacaoContratoObservacoesParecer vcoParecer = new VerificacaoContratoObservacoesParecer();
			vcoParecer.setDeObservacao(vco.getDeObservacao());
			vcoParecer.setDtValidade(vco.getDtValidade());
			vcoParecer.setIcConforme(vco.getIcConforme());
			vcoParecer.setIcDesabilitado(vco.getIcDesabilitado());
			vcoParecer.setIcFonte(vco.getIcFonte());
			if(Utilities.notEmpty(vco.getBlocoChecklistProduto())){
				vcoParecer.setNuBlocoChecklistServicoVerificacaoProduto(vco.getBlocoChecklistProduto().getNuBlocoChecklistProduto());
			}
			if(Utilities.notEmpty(vco.getItemVerificacaoChecklistProduto())){
				vcoParecer.setNuItemChecklistServicoVerificacaoProduto(vco.getItemVerificacaoChecklistProduto().getNuItemVerificacaoChecklistProduto());
			}
			vcoParecer.setNuVerificacaoContrato(verificacaoParecer.getNuVerificacaoContrato());
			vcoParecer.setNuVerificacaoContratoObservacao(vco.getNuVerificacaoContratoObservacao());
			vcoParecer.setVerificacaoContratoParecer(verificacaoParecer);
			
			this.verificacaoContratoObservacaoParecerDAO.merge(vcoParecer);
		}
	}

	private void deleteVerificacaoContratoObservacoesSemParecer(VerificacaoContrato verificacao) {		
		this.verificacaoContratoDAO.delete(verificacao);
	}
	
	private ArquivoImagemParecer geraArquivoImagemParecer(Parecer parecer, IUsuario usuario, String caminhoPastaImagens) throws SIIACRuntimeException {

		DefinedLanguage dl = (DefinedLanguage) AppContext.getApplicationContext().getBean("definedLanguage");
		dl.setParecer(parecer);
		dl.setUsuario(usuario);
//		dl.setParecerBO(this);
		this.buildTemplateParecerBO.setDefinedLanguage(dl);
		this.parecerDAO.flush();
		
		try{
			byte[] pdf = buildTemplateParecerBO.generateReport(parecer.getTemplateParecer().getTextoParecer(), caminhoPastaImagens);
			ArquivoImagemParecer arquivoImagemParecer = new ArquivoImagemParecer();
			
			ArquivoImagemParecerId id = new ArquivoImagemParecerId();
			id.setNuParecer(parecer.getParecerId().getNuParecer());
			id.setAaParecer(parecer.getParecerId().getAaParecer());
			id.setNuUnidade(parecer.getParecerId().getNuUnidade());
			id.setNuNatural(parecer.getParecerId().getNuNatural());
			arquivoImagemParecer.setArquivoImagemParecerPK(id);
			
			arquivoImagemParecer.setImParecer(pdf);
			
			arquivoImagemParecer.setParecer(parecer);
			
			arquivoImagemParecerDAO.merge(arquivoImagemParecer);
			
			return arquivoImagemParecer;
		} catch (Exception e) {
			LogCEF.error(e.getMessage());
			throw new SIIACRuntimeException("Não foi possível gerar o arquivo PDF");
		}
	}

	@Transactional
	public void executaEnviaParecer(Parecer parecer) throws SIIACRuntimeException {
		try{
			// String wsdlURL = "http://localhost:8080/SIIAC-WS/EnviaParecerWS?wsdl";

			String wsdlURL = this.propriedadesDAO.getPropriedade("url.webservice");
			if(Utilities.empty(wsdlURL)){
				LogCEF.error("Não foi possível enviar o parecer pois não há o parâmetro 'url.webservice' configurado na tabela de propriedades.");
				return;
			}

			URL url = new URL(wsdlURL + "/EnviaParecerWS?wsdl");
	 
	        br.gov.caixa.siiac.ws.client.EnviaParecerWSService service = new br.gov.caixa.siiac.ws.client.EnviaParecerWSServiceLocator();
	 
	        br.gov.caixa.siiac.ws.client.EnviaParecerWS ws = service.getEnviaParecerWSPort(url);
	        
	        ws.enviaParecer(parecer.getParecerId().getNuParecer(), parecer.getParecerId().getAaParecer(), parecer.getParecerId().getNuUnidade(), parecer.getParecerId().getNuNatural());
		 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
