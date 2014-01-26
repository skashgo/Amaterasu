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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author GIS Consult
 *
 */
@Entity
@Table(name = "iactb056_vrfco_cntro_obsro", schema = "iacsm001")
public class VerificacaoContratoObservacoes implements Serializable {
	
	public static final Character FONTE_OBSERVACAO_APONTAMENTO_ID_BLOCO = 'B';
	public static final Character FONTE_OBSERVACAO_APONTAMENTO_ID_ITEM = 'I';
	
	private static final long serialVersionUID = 1L;
	protected Integer nuVerificacaoContratoObservacao;
	private char icFonte;
	private String deObservacao;
	private Boolean icConforme;
	private Boolean icDesabilitado;
	private VerificacaoContrato verificacaoContrato;
	private BlocoChecklistProduto blocoChecklistProduto;
	private ItemVerificacaoChecklistProduto itemVerificacaoChecklistProduto;
	private Date dtValidade;
	
	public VerificacaoContratoObservacoes() {
	}
	
	@Id
	@Column(name = "nu_vrfco_cntro_obsro", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = " iacsq022_vrfco_cntro_obsro")
	@SequenceGenerator(name = " iacsq022_vrfco_cntro_obsro", sequenceName = "iacsm001.iacsq022_vrfco_cntro_obsro")
	public Integer getNuVerificacaoContratoObservacao() {
		return nuVerificacaoContratoObservacao;
	}
	
	public void setNuVerificacaoContratoObservacao(Integer nuVerificacaoContratoObservacao) {
		this.nuVerificacaoContratoObservacao = nuVerificacaoContratoObservacao;
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
	
	@ManyToOne
	@JoinColumn(name = "nu_verificacao_contrato", nullable = false)
	public VerificacaoContrato getVerificacaoContrato() {
		return verificacaoContrato;
	}
	
	public void setVerificacaoContrato(VerificacaoContrato verificacaoContrato) {
		this.verificacaoContrato = verificacaoContrato;
	}
	
	@JoinColumn(name = "nu_blco_chklst_srvco_vrfco_prdt", referencedColumnName = "nu_blco_chklst_srvco_vrfco_prdt", insertable = true, updatable = false)
	@ManyToOne
	public BlocoChecklistProduto getBlocoChecklistProduto() {
		return blocoChecklistProduto;
	}
	
	public void setBlocoChecklistProduto(BlocoChecklistProduto blocoChecklistProduto) {
		this.blocoChecklistProduto = blocoChecklistProduto;
	}
	
	@JoinColumn(name = "nu_item_chklst_srvco_vrfco_prdt", referencedColumnName = "nu_item_chklst_srvco_vrfco_prdt", insertable = true, updatable = false)
	@ManyToOne
	public ItemVerificacaoChecklistProduto getItemVerificacaoChecklistProduto() {
		return itemVerificacaoChecklistProduto;
	}
	
	public void setItemVerificacaoChecklistProduto(ItemVerificacaoChecklistProduto itemVerificacaoChecklistProduto) {
		this.itemVerificacaoChecklistProduto = itemVerificacaoChecklistProduto;
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
