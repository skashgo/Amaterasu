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
package br.gov.caixa.siiac.view.mb;

import java.io.IOException;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IBuildTemplateParecerBO;
import br.gov.caixa.siiac.bo.IContratoBO;
import br.gov.caixa.siiac.bo.IEfetuaVerificacaoBO;
import br.gov.caixa.siiac.bo.IVerificacaoContratoBO;
import br.gov.caixa.siiac.bo.IVerificacaoContratoParecerBO;
import br.gov.caixa.siiac.bo.impl.MatrizAcessoBO;
import br.gov.caixa.siiac.model.HistoricoParecerVO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoParecer;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
@Service()
@Scope("request")
public class EscolheVerificacaoMB extends AbstractMB {
	
	private static final String ESTADO_VERIFICACAO_APONTAMENTO_LABEL_NAO_VERIFICADA = "NÃO VERIFICADA";
	private static final String ESTADO_VERIFICACAO_APONTAMENTO_LABEL_INCONFORME = "INCONFORME";
	private static final String ESTADO_VERIFICACAO_APONTAMENTO_LABEL_CONFORME = "CONFORME";
	private static final String ESTADO_VERIFICACAO_APONTAMENTO_ID_NAO_VERIFICADA = "NV";
	private static final String ESTADO_VERIFICACAO_APONTAMENTO_ID_CONFORME = "CO";
	private static final String ESTADO_VERIFICACAO_APONTAMENTO_ID_INCONFORME = "IC";
	
	private Contrato contrato;
	private IVerificacaoContratoBO verificacaoContratoBO;
	private IVerificacaoContratoParecerBO verificacaoContratoParecerBO;
	private IContratoBO contratoBO;
	private IBuildTemplateParecerBO buildTemplateParecerBO;
	private IEfetuaVerificacaoBO efetuaVerificacaoBO;
	
	private List<VerificacaoContratoVO> listVerificacaoVO;
	private List<HistoricoParecerVO> listHistoricoParecerVO;
	
	private boolean exibeBotaoVoltar;
	
	@Autowired
	public void setVerificacaoContratoBO(IVerificacaoContratoBO verificacaoContratoBO) {
		this.verificacaoContratoBO = verificacaoContratoBO;
	}
	
	@Autowired
	public void setEfetuaVerificacaoBO(IEfetuaVerificacaoBO efetuaVerificacaoBO) {
		this.efetuaVerificacaoBO = efetuaVerificacaoBO;
	}
	
	@Autowired
	public void setVerificacaoContratoParecerBO(IVerificacaoContratoParecerBO verificacaoContratoParecerBO) {
		this.verificacaoContratoParecerBO = verificacaoContratoParecerBO;
	}
	
	@Autowired
	public void setBuildTemplateParecerBO(IBuildTemplateParecerBO buildTemplateParecerBO) {
		this.buildTemplateParecerBO = buildTemplateParecerBO;
	}
	
	@Autowired
	public void setContratoBO(IContratoBO contratoBO) {
		this.contratoBO = contratoBO;
	}
	
	public EscolheVerificacaoMB() {
		contrato = (Contrato) getHttpSession().getAttribute("contrato");
			

		Boolean isVerificacaoPreventiva = (Boolean) getHttpSession().getAttribute(VerificacaoPreventivaMB.IS_VERIFICACAO_PREVENTIVA);
		
		if (isVerificacaoPreventiva != null && isVerificacaoPreventiva)
		{
			exibeBotaoVoltar = true;
		} else {
			exibeBotaoVoltar = false;
		}
	}
	
	public String doVoltar()
	{
		return "toVerificacaoPreventiva";
	}
	
	public String doEfetuaVerificacao() throws Exception{
		VerificacaoContratoVO verificacaoContratoVO = (VerificacaoContratoVO) getRequestMap().get("row");
		
		//Validando se existe CheckList Publicado
		
		if (Utilities.notEmpty(verificacaoContratoVO.getNuChecklistServicoVerificacaoProduto()))
		{
			if (!efetuaVerificacaoBO.existeChecklistByNuServico(verificacaoContratoVO.getNuServicoVerificacaoProduto()))
			{
				error(MSGS, MN002);
				warn("Este serviço de verificação não possui checklist PUBLICADO para este produto.");
				return "toEscolheVerificacao";
			}
		}
		
		if (verificacaoContratoVO.getIcEstadoVerificacao().equals(VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_NAO_VERIFICADA)) {
			
			if (verificacaoContratoBO.existe(verificacaoContratoVO.getNuVerificacaoContrato()))
			{
				getHttpSession().setAttribute("verificacaoContratoVO", verificacaoContratoVO);
				getSessionMap().put("efetuaVerificacaoMB", null);
				return "toEfetuaVerificacao";
			} else {
				
				Integer nuContrato = contrato.getNuContrato();
				contrato = new Contrato();
				contrato = contratoBO.findById(nuContrato);
				listVerificacaoVO.clear();
				listVerificacaoVO = verificacaoContratoBO.listVerificacaoVOByContrato(contrato);
				error("Já foi emitido um parecer para esta verificação.");
				return "toEscolheVerificacao";
			}
			
		} else {
			if (verificacaoContratoParecerBO.existe(verificacaoContratoVO.getNuVerificacaoContrato()))
			{
				getHttpSession().setAttribute("verificacaoContratoVO", verificacaoContratoVO);
				getSessionMap().put("efetuaVerificacaoMB", null);
				return "toEfetuaVerificacao";
			} else {
				
				Integer nuContrato = contrato.getNuContrato();
				contrato = new Contrato();
				contrato = contratoBO.findById(nuContrato);
				listVerificacaoVO.clear();
				listVerificacaoVO = verificacaoContratoBO.listVerificacaoVOByContrato(contrato);
				error("Já foi criada uma nova verificação para ela.");
				return "toEscolheVerificacao";
			}
		}
			
		
	}
	
	public void doHistoricoParecer(ActionEvent evt) {
		VerificacaoContratoVO verificacaoContratoVO = (VerificacaoContratoVO) getRequestMap().get("row");
		listHistoricoParecerVO = this.verificacaoContratoParecerBO.getListHistoricoParecer(
							contrato.getNuContrato(), 
							verificacaoContratoVO.getNuServicoVerificacaoProduto());
	}
	
	public String doVisualizaParecer(){
		VerificacaoContratoVO verificacaoContratoVO = (VerificacaoContratoVO) getRequestMap().get("row");
		try{
			byte[] bs = buildTemplateParecerBO.getPDFParecer(verificacaoContratoVO.getNuVerificacaoContrato());
			this.downloadPDF(bs, buildTemplateParecerBO.getNomeParecer(verificacaoContratoVO.getNuVerificacaoContrato()));
		} catch (Exception e) {
			error(MSGS, MN002);
		}
		return "toEscolheVerificacao";
	}
	
	public String doVisualizaParecerFromHistorico(){
		HistoricoParecerVO historico = (HistoricoParecerVO) getRequestMap().get("row");
		try{
			byte[] bs = buildTemplateParecerBO.getPDFParecer(historico.getNuVerificacaoContrato());
			this.downloadPDF(bs, historico.getDescricaoParecer());
		} catch (Exception e) {
			error(MSGS, MN002);
		}
		return "toEscolheVerificacao";
	}
	
	private void downloadPDF(byte[] bs, String nome) throws IOException {
		getResponse().setHeader("Pragma", "");
		getResponse().setHeader("Cache-Control", "");
		getResponse().setHeader("Expires", "");
		getResponse().setCharacterEncoding("UTF-8");
		getResponse().setContentType("application/download");
		getResponse().setHeader("Content-disposition",
				"attachment; filename=\"" + nome + ".pdf\"");
		getResponse().getOutputStream().write(bs);
		
		getFacesContext().responseComplete();
	}
	
	public List<VerificacaoContratoVO> getListVerificacaoVO() {
		try{
			if(listVerificacaoVO == null) {
				listVerificacaoVO = verificacaoContratoBO.listVerificacaoVOByContrato(contrato);
			}
		} catch (Exception e){
			LogCEF.error(e.getMessage());
		}
		
		return listVerificacaoVO;
	}
	
	public List<HistoricoParecerVO> getListHistoricoParecerVO() {
		return listHistoricoParecerVO;
	}
	
	public boolean isShowChecklist() {
		VerificacaoContratoVO vo = (VerificacaoContratoVO) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		
		if(vo != null) {
			Short nuPerfil = getUsuarioLogado().getPerfis().get(0);
			return !vo.getIcEstadoVerificacao().trim().equals(EscolheVerificacaoMB.ESTADO_VERIFICACAO_APONTAMENTO_ID_CONFORME) && 
			MatrizAcessoBO.acessoModuloEfetuaServicoVerificacao(nuPerfil, MatrizAcessoBO.MARCA_INCONFORMIDADES_RESTRICOES, MatrizAcessoBO.TIPO_ACAO_ALTERA) ||
			MatrizAcessoBO.acessoModuloEfetuaServicoVerificacao(nuPerfil, MatrizAcessoBO.ALTERA_VERIFICACAO, MatrizAcessoBO.TIPO_ACAO_ALTERA);
		}
		
		return false;
	}
	
	public boolean isShowHistorico() {
		VerificacaoContratoVO vo = (VerificacaoContratoVO) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		
		if(vo != null) {
			if(!vo.getIcEstadoVerificacao().trim().equals(EscolheVerificacaoMB.ESTADO_VERIFICACAO_APONTAMENTO_ID_NAO_VERIFICADA) 
					|| Utilities.notEmpty(vo.getNuVerificacaoContratoPai())) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isShowParecer() {
		VerificacaoContratoVO vo = (VerificacaoContratoVO) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		Short nuPerfil = getUsuarioLogado().getPerfis().get(0);
		if(vo != null) {
			return !vo.getIcEstadoVerificacao().trim().equals(EscolheVerificacaoMB.ESTADO_VERIFICACAO_APONTAMENTO_ID_NAO_VERIFICADA) && MatrizAcessoBO.acessoModuloConsultaVerificacao(nuPerfil, MatrizAcessoBO.VISUALIZA_PARECER, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
		}
		return false;
	}
	
	public String getSituacaoFormatada() {
		FacesContext contexto = FacesContext.getCurrentInstance();
		VerificacaoContratoVO vo = (VerificacaoContratoVO)contexto.getExternalContext().getRequestMap().get("row");
		
		if(vo != null && vo.getIcEstadoVerificacao() != null && !vo.getIcEstadoVerificacao().trim().equals("")) {
			if(vo.getIcEstadoVerificacao().trim().equals(ESTADO_VERIFICACAO_APONTAMENTO_ID_CONFORME)) {
				return ESTADO_VERIFICACAO_APONTAMENTO_LABEL_CONFORME;
			} else if(vo.getIcEstadoVerificacao().trim().equals(ESTADO_VERIFICACAO_APONTAMENTO_ID_INCONFORME)) {
				return ESTADO_VERIFICACAO_APONTAMENTO_LABEL_INCONFORME;
			} else {
				return ESTADO_VERIFICACAO_APONTAMENTO_LABEL_NAO_VERIFICADA;
			}
		}
		
		return "";
	}

	public boolean isExibeBotaoVoltar() {		
		return exibeBotaoVoltar;
	}

	public void setExibeBotaoVoltar(boolean exibeBotaoVoltar) {
		this.exibeBotaoVoltar = exibeBotaoVoltar;
	}
}