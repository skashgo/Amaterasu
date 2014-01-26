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

import java.math.BigDecimal;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IContratoBO;
import br.gov.caixa.siiac.bo.IDetalhesContratoBO;
import br.gov.caixa.siiac.bo.IGarantiaBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.bo.IVerificacaoContratoBO;
import br.gov.caixa.siiac.bo.impl.VerificacaoContratoBO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.Garantia;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */

@Service()
@Scope("request")
public class CabecalhoDetalhesVerificacaoMB extends AbstractMB {
	
	private static final String ULTIMA_LOCALIZACAO_ENCAMINHADA_LIQUIDACAO_LABEL = "ENCAMINHADA PARA LIQUIDAÇÃO";
	private static final String ULTIMA_LOCALIZACAO_RETIRADA_CASA_FORTE_LABEL = "RETIRADA DA CASA FORTE";
	private static final String ULTIMA_LOCALIZACAO_CASA_FORTE_LABEL = "CASA FORTE";
	private static final String PESSOA_JURIDICA_LABEL = "PESSOA JURÍDICA";
	private static final String PESSOA_FISICA_LABEL = "PESSOA FÍSICA";
	private static final String PF_ID = "PF";
	
	public static final String ICON_VERIFICADA = "/images/icon_verifica.gif";
	public static final String ICON_A_VERIFICAR = "/images/icon_pend.gif";
	public static final String ICON_PARCIALMENTE_INCONFORME = "/images/icon_parcialmente_inconforme.gif";
	public static final String ICON_INCONFORME_V = "/images/icon_inconforme.gif";
	
	public static final String ICON_VERIFICACAO_PARCIAL = "/images/VerificacaoParcial.gif";
	public static final String ICON_CONFORME = "/images/Conforme.gif";
	public static final String ICON_INCONFORME = "/images/Inconforme.gif";
	
	@Autowired
	private IDetalhesContratoBO detalhesContratoBO;
	
	private String SITUACAO_CONTRATO;
	private IGarantiaBO garantiaBO;
	private IUnidadeBO unidadeBO;
	private IContratoBO contratoBO;
	
	private IVerificacaoContratoBO verificacaoContratoBO;
	private Contrato contrato;
	private List<Garantia> garantias;
	private Garantia garantiaVisualizada;
	private boolean ehVerificacaoPreventiva = false;
	private String contratoPreventiva = "";
	
	private BigDecimal valorRessarcimento;
	private String nomeConveniado;
	
	private Boolean verificado = null;
	private boolean possuiGarantia = false;
	
	public CabecalhoDetalhesVerificacaoMB() {
		contrato = (Contrato)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("contrato");
	}
	
	@Autowired
	public void setGarantiaBO(IGarantiaBO garantiaBO) {
		this.garantiaBO = garantiaBO;
	}
	
	@Autowired
	public void setUnidadeBO(IUnidadeBO unidadeBO) {
		this.unidadeBO = unidadeBO;
	}
		
	public IContratoBO getContratoBO() {
		return contratoBO;
	}

	@Autowired
	public void setContratoBO(IContratoBO contratoBO) {
		this.contratoBO = contratoBO;
	}

	@Autowired
	public void setVerificacaoContratoBO(IVerificacaoContratoBO verificacaoContratoBO) {
		this.verificacaoContratoBO = verificacaoContratoBO;
		inicializa();
	}

	public void inicializa() {
		SITUACAO_CONTRATO = verificacaoContratoBO.obtemSituacaoContrato(contrato.getNuContrato());
	}
	
	public Contrato getContrato() {
		return this.contrato;
	}
	
	public void setGarantiaVisualizada(Garantia garantiaVisualizada) {
		this.garantiaVisualizada = garantiaVisualizada;
	}
	
	public Garantia getGarantiaVisualizada() {
		return this.garantiaVisualizada;
	}
	
	public List<Garantia> getListGarantias() {
		if(garantias == null) {
			try {
				garantias = garantiaBO.getAllGarantiasInContrato(contrato);
			} catch (DAOException ex) {
				LogCEF.error(ex);
			}
		}
		
		return garantias;
	}
	
	public void doVisualizarGarantia(ActionEvent ev) {
		Garantia g = (Garantia)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		setGarantiaVisualizada(g);
	}
	
	public String getSituacaoContrato() {
		return SITUACAO_CONTRATO;
	}
	
	public String getIconeSituacao() {
		if(SITUACAO_CONTRATO.equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_NAO_VERIFICADA)) {
			return "";
		}
		
		if(SITUACAO_CONTRATO.equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_VERIFICACAO_PARCIAL)) {
			return ICON_VERIFICACAO_PARCIAL;
		}
		
		if(SITUACAO_CONTRATO.equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_CONFORME)) {
			return ICON_CONFORME;
		}
		
		if(SITUACAO_CONTRATO.equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_INCONFORME)) {
			return ICON_INCONFORME;
		}
		
		return "";
	}
	
	public String getTipoPessoa() {
		if(contrato != null) {
			if(contrato.getIcPfPj().trim().equals(PF_ID)) {
				return PESSOA_FISICA_LABEL;
			}
			
			return PESSOA_JURIDICA_LABEL;
		}
		
		return "";
	}
	
	public String getNomeUnidade() {
		Unidade unid = unidadeBO.getUnidade(contrato.getNuUnidade(), contrato.getNuNatural());
		
		if(unid != null) {
			return unid.getId().getNuUnidade() + " - " + unid.getNoUnidade();
		}
			
		return "";
	}
	
	public String getIdClienteFormatado() {
		return ContratoMB.formataDocumento(contrato);
	}
	
	public String getIcUltimaLocalizacaoFormatada() {
		if(garantiaVisualizada != null) {
			if(garantiaVisualizada.getIcUltimaLocalizacao() != null && 
					!garantiaVisualizada.getIcUltimaLocalizacao().trim().equals("")) {
				if(garantiaVisualizada.getIcUltimaLocalizacao().trim().equals("CF")) {
					return ULTIMA_LOCALIZACAO_CASA_FORTE_LABEL;
				} else if(garantiaVisualizada.getIcUltimaLocalizacao().trim().equals("RF")) {
					return ULTIMA_LOCALIZACAO_RETIRADA_CASA_FORTE_LABEL;
				} else {
					return ULTIMA_LOCALIZACAO_ENCAMINHADA_LIQUIDACAO_LABEL;
				}
			}
		}
		
		return "";
	}
	
	public String getIcLocalizacaoUltimoInventarioFormatada() {
		if(garantiaVisualizada != null) {
			if(garantiaVisualizada.getIcLocalizacaoUltimoInventari() != null && 
					!garantiaVisualizada.getIcLocalizacaoUltimoInventari().trim().equals("")) {
				if(garantiaVisualizada.getIcLocalizacaoUltimoInventari().trim().equals("CF")) {
					return ULTIMA_LOCALIZACAO_CASA_FORTE_LABEL;
				} else if(garantiaVisualizada.getIcLocalizacaoUltimoInventari().trim().equals("RF")) {
					return ULTIMA_LOCALIZACAO_RETIRADA_CASA_FORTE_LABEL;
				} else {
					return ULTIMA_LOCALIZACAO_ENCAMINHADA_LIQUIDACAO_LABEL;
				}
			}
		}
		
		return "";
	}
	
	public boolean isShowIconeSituacao(){
		return Utilities.notEmpty(getIconeSituacao());
	}
	
	public boolean isEhVerificacaoPreventiva() {
		if (getHttpSession().getAttribute(VerificacaoPreventivaMB.IS_VERIFICACAO_PREVENTIVA) != null)
		{
			ehVerificacaoPreventiva = true;
			return (Boolean) getHttpSession().getAttribute(VerificacaoPreventivaMB.IS_VERIFICACAO_PREVENTIVA);
		} else {
			if(Utilities.notEmpty(contrato.getCoContrato()))
			{
				ehVerificacaoPreventiva = false;
				return false;
			} else {
				ehVerificacaoPreventiva = true;
				return true;
			}
		}
	}

	public void setEhVerificacaoPreventiva(boolean ehVerificacaoPreventiva) {
		this.ehVerificacaoPreventiva = ehVerificacaoPreventiva;
	}

	public String getContratoPreventiva() {
		if(Utilities.notEmpty(contrato.getCoContrato()))
		{
			return "CONTRATO";
		} else {
			return "PREVENTIVA";
		}
	}

	public void setContratoPreventiva(String contratoPreventiva) {
		this.contratoPreventiva = contratoPreventiva;
	}
	
	public boolean isPossuiGarantias()
	{
		if (verificado == null)
		{
			verificado = true;
			
			contratoBO.inicializaLista(contrato);
			if (contrato.getGarantias() == null || contrato.getGarantias().size() == 0)
				possuiGarantia = false;
			else
				possuiGarantia = true;
		}
		
		return possuiGarantia;
		
	}

	public BigDecimal getValorRessarcimento() {
		if (Utilities.notEmpty(contrato.getDetalhesContrato()) && Utilities.notEmpty(contrato.getDetalhesContrato().getNoCampoLivre3()))
		{
			valorRessarcimento = BigDecimal.valueOf(Double.parseDouble(contrato.getDetalhesContrato().getNoCampoLivre3()));
		}
		return valorRessarcimento;
	}

	public void setValorRessarcimento(BigDecimal valorRessarcimento) {
		this.valorRessarcimento = valorRessarcimento;
	}

	public String getNomeConveniado() {
		if (Utilities.notEmpty(contrato.getDetalhesContrato()) && Utilities.notEmpty(contrato.getDetalhesContrato().getNuConveniado()) && contrato.getDetalhesContrato().getNuConveniado() > 0)
		{
			nomeConveniado = detalhesContratoBO.getNomeByNuConveniado(contrato.getDetalhesContrato().getNuConveniado());
		}
		
		return nomeConveniado;
	}

	public void setNomeConveniado(String nomeConveniado) {
		this.nomeConveniado = nomeConveniado;
	}

	public IDetalhesContratoBO getDetalhesContratoBO() {
		return detalhesContratoBO;
	}

	public void setDetalhesContratoBO(IDetalhesContratoBO detalhesContratoBO) {
		this.detalhesContratoBO = detalhesContratoBO;
	}

	public Boolean isVerificado() {
		return verificado;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}
	
	
}