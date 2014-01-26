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
package br.gov.caixa.siiac.model.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "iactb058_vrfco_cntro_obsro_prcr", schema = "iacsm001")
public class VerificacaoContratoObservacoesParecer implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer nuVerificacaoContratoObservacao;
	private char icFonte;
	private String deObservacao;
	private Boolean icConforme;
	private Boolean icDesabilitado;
	private VerificacaoContratoParecer verificacaoContratoParecer;
	private Integer nuVerificacaoContrato;
	private Integer nuBlocoChecklistServicoVerificacaoProduto;
	private Integer nuItemChecklistServicoVerificacaoProduto;
	private Date dtValidade;
	
	@Id
	@Column(name = "nu_vrfco_cntro_obsro", nullable = false)
	public Integer getNuVerificacaoContratoObservacao() {
		return nuVerificacaoContratoObservacao;
	}
	
	public void setNuVerificacaoContratoObservacao(Integer nuVerificacaoContratoObservacao) {
		this.nuVerificacaoContratoObservacao = nuVerificacaoContratoObservacao;
	}
	
	@Column(name = "nu_verificacao_contrato", nullable = false)
	public Integer getNuVerificacaoContrato() {
		return nuVerificacaoContrato;
	}
	
	public void setNuVerificacaoContrato(Integer nuVerificacaoContrato) {
		this.nuVerificacaoContrato = nuVerificacaoContrato;
	}
	
	@Column(name = "nu_blco_chklst_srvco_vrfco_prdt")
	public Integer getNuBlocoChecklistServicoVerificacaoProduto() {
		return nuBlocoChecklistServicoVerificacaoProduto;
	}
	
	public void setNuBlocoChecklistServicoVerificacaoProduto(Integer nuBlcoChklstSrvcoVrfcoPrdt) {
		this.nuBlocoChecklistServicoVerificacaoProduto = nuBlcoChklstSrvcoVrfcoPrdt;
	}
	
	@Column(name = "nu_item_chklst_srvco_vrfco_prdt")
	public Integer getNuItemChecklistServicoVerificacaoProduto() {
		return nuItemChecklistServicoVerificacaoProduto;
	}
	
	public void setNuItemChecklistServicoVerificacaoProduto(Integer nuItemChklstSrvcoVrfcoPrdt) {
		this.nuItemChecklistServicoVerificacaoProduto = nuItemChklstSrvcoVrfcoPrdt;
	}
	
	@Column(name = "ic_fonte")
	public char getIcFonte() {
		return icFonte;
	}
	
	public void setIcFonte(char icFonte) {
		this.icFonte = icFonte;
	}
	
	@Column(name = "de_observacao")
	public String getDeObservacao() {
		return deObservacao;
	}
	
	public void setDeObservacao(String deObservacao) {
		this.deObservacao = deObservacao;
	}
	
	@Column(name = "ic_conforme")
	public Boolean getIcConforme() {
		return icConforme;
	}
	
	public void setIcConforme(Boolean icConforme) {
		this.icConforme = icConforme;
	}
	
	@Column(name = "ic_desabilitado")
	public Boolean getIcDesabilitado() {
		return icDesabilitado;
	}
	
	public void setIcDesabilitado(Boolean icDesabilitado) {
		this.icDesabilitado = icDesabilitado;
	}
	
	@JoinColumn(name = "nu_verificacao_contrato", referencedColumnName = "nu_verificacao_contrato", insertable = false, updatable = false)
	@ManyToOne
	public VerificacaoContratoParecer getVerificacaoContratoParecer() {
		return verificacaoContratoParecer;
	}
	
	public void setVerificacaoContratoParecer(VerificacaoContratoParecer verificacaoContratoParecer) {
		this.verificacaoContratoParecer = verificacaoContratoParecer;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_validade", nullable = true, length = 13)
	public Date getDtValidade() {
		return this.dtValidade;
	}
	
	public void setDtValidade(Date dtValidade) {
		this.dtValidade = dtValidade;
	}
}
