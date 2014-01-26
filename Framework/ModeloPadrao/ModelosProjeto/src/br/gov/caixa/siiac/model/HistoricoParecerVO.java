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
package br.gov.caixa.siiac.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import br.gov.caixa.siiac.model.domain.VerificacaoContratoParecer;

/**
 * @author GIS Consult
 *
 */
@Entity
public class HistoricoParecerVO {

	/**
	 * Constants 
	 */
	private static final String VERIFICACAO_DOSSIE_CONTRATO = "Verificação Dossiê Contrato";
	private static final String STRIPE = "/";
	private static final String SPACE = " ";
	private static final String UNDERLINE = "_";
	private static final String PA = "PA";
	
	private Integer nuVerificacaoContrato;
	private Integer nuVerificacaoContratoPai;
	private Date dtVerificacao;
	private String icEstadoVerificacao;
	private String coResponsavelVerificacao;
	private Integer nuParecer;
	private Short aaParecer;
	private String coContratoConta;
	private Short nuUnidade;
	
	@Id
	@Column(name = "nu_verificacao_contrato")
	public Integer getNuVerificacaoContrato() {
		return nuVerificacaoContrato;
	}
	
	public void setNuVerificacaoContrato(Integer nuVerificacaoContrato) {
		this.nuVerificacaoContrato = nuVerificacaoContrato;
	}
	
	@Column(name = "nu_verificacao_contrato_pai")
	public Integer getNuVerificacaoContratoPai() {
		return nuVerificacaoContratoPai;
	}
	
	public void setNuVerificacaoContratoPai(Integer nuVerificacaoContratoPai) {
		this.nuVerificacaoContratoPai = nuVerificacaoContratoPai;
	}
	
	@Column(name = "dt_verificacao")
	public Date getDtVerificacao() {
		return dtVerificacao;
	}
	
	public void setDtVerificacao(Date dtVerificacao) {
		this.dtVerificacao = dtVerificacao;
	}
	
	@Column(name = "ic_estado_verificacao")
	public String getIcEstadoVerificacao() {
		return icEstadoVerificacao;
	}
	
	public void setIcEstadoVerificacao(String icEstadoVerificacao) {
		this.icEstadoVerificacao = icEstadoVerificacao;
	}
	
	@Column(name = "co_rspnl_verificacao")
	public String getCoResponsavelVerificacao() {
		return coResponsavelVerificacao;
	}
	
	public void setCoResponsavelVerificacao(String coResponsavelVerificacao) {
		this.coResponsavelVerificacao = coResponsavelVerificacao;
	}
	
	@Column(name = "nu_parecer")
	public Integer getNuParecer() {
		return nuParecer;
	}

	public void setNuParecer(Integer nuParecer) {
		this.nuParecer = nuParecer;
	}

	@Column(name = "aa_parecer")
	public Short getAaParecer() {
		return aaParecer;
	}

	public void setAaParecer(Short aaParecer) {
		this.aaParecer = aaParecer;
	}

	@Column(name = "co_contrato_conta")
	public String getCoContratoConta() {
		return coContratoConta;
	}

	public void setCoContratoConta(String coContratoConta) {
		this.coContratoConta = coContratoConta;
	}

	@Column(name = "nu_unidade")
	public Short getNuUnidade() {
		return nuUnidade;
	}

	public void setNuUnidade(Short nuUnidade) {
		this.nuUnidade = nuUnidade;
	}

	@Transient
	public String getDescricaoParecer(){
		return PA + UNDERLINE + numeroFormatado(Integer.parseInt(this.getNuUnidade().toString()),4) + UNDERLINE + numeroFormatado(this.getNuParecer(), 5) + UNDERLINE + this.getAaParecer();
	}
	
	@Transient
	public String getIcEstadoVerificacaoFormatado() {
		if(this.getIcEstadoVerificacao() == null)
			return "";
		
		if(this.getIcEstadoVerificacao().equals(VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_CONFORME)){
			return VerificacaoContratoParecer.ESTADO_VERIFICACAO_LABEL_CONFORME;
		}
		if(this.getIcEstadoVerificacao().equals(VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_INCONFORME)){
			return VerificacaoContratoParecer.ESTADO_VERIFICACAO_LABEL_INCONFORME;
		}
		return "";
	}
	
	
	@Transient
	private static String numeroFormatado(Integer numero, Integer qtdZeros)
    {
        String retorno = "";
        
        String aux = numero.toString();
        
        for (int m = 0; m < (qtdZeros - aux.length()); m++)
        {
            retorno += "0";
        }
        
        retorno += numero;
        
        return retorno;
    }
}
