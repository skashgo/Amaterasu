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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "iactb045_conta_aberta_lote", schema = "iacsm001")
public class ContaAbertaLote implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected ContaAbertaLoteId contaAbertaLoteId;
	private Short nuModalidade;
	private Short nuUnidade;
	private int nuNatural;
	private int coCliente;
	private String noCliente;
	private int nuIdentificadorCliente;
	private Date dtContrato;
	
	public ContaAbertaLote() {
	}
	
	public ContaAbertaLote(ContaAbertaLoteId contaAbertaLoteId) {
		this.contaAbertaLoteId = contaAbertaLoteId;
	}
	
	public ContaAbertaLote(ContaAbertaLoteId contaAbertaLoteId, Short nuModalidade, Short nuUnidade, int nuNatural, Date dtContrato, int coCliente, String noCliente, int nuIdentificadorCliente) {
		this.contaAbertaLoteId = contaAbertaLoteId;
		this.nuModalidade = nuModalidade;
		this.nuUnidade = nuUnidade;
		this.nuNatural = nuNatural;
		this.dtContrato = dtContrato;
		this.coCliente = coCliente;
		this.noCliente = noCliente;
		this.nuIdentificadorCliente = nuIdentificadorCliente;
	}
	
	public ContaAbertaLote(int coConta, Short nuOperacao) {
		this.contaAbertaLoteId = new ContaAbertaLoteId(coConta, nuOperacao);
	}
	
	@EmbeddedId
	public ContaAbertaLoteId getContaAbertaLotePK() {
		return contaAbertaLoteId;
	}
	
	public void setContaAbertaLotePK(ContaAbertaLoteId contaAbertaLoteId) {
		this.contaAbertaLoteId = contaAbertaLoteId;
	}
	
	@Column(name = "nu_modalidade", nullable = false)
	public Short getNuModalidade() {
		return nuModalidade;
	}
	
	public void setNuModalidade(Short nuModalidade) {
		this.nuModalidade = nuModalidade;
	}
	
	@Column(name = "nu_unidade", nullable = false)
	public Short getNuUnidade() {
		return nuUnidade;
	}
	
	public void setNuUnidade(Short nuUnidade) {
		this.nuUnidade = nuUnidade;
	}
	
	@Column(name = "nu_natural", nullable = false)
	public int getNuNatural() {
		return nuNatural;
	}
	
	public void setNuNatural(int nuNatural) {
		this.nuNatural = nuNatural;
	}
	
	@Column(name = "dt_contrato")
	@Temporal(TemporalType.DATE)
	public Date getDtContrato() {
		return dtContrato;
	}
	
	public void setDtContrato(Date dtContrato) {
		this.dtContrato = dtContrato;
	}
	
	@Column(name = "co_cliente", nullable = false)
	public int getCoCliente() {
		return coCliente;
	}
	
	public void setCoCliente(int coCliente) {
		this.coCliente = coCliente;
	}
	
	@Column(name = "no_cliente", nullable = false, length = 70)
	public String getNoCliente() {
		return noCliente;
	}
	
	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}
	
	@Column(name = "nu_identificador_cliente", nullable = false)
	public int getNuIdentificadorCliente() {
		return nuIdentificadorCliente;
	}
	
	public void setNuIdentificadorCliente(int nuIdentificadorCliente) {
		this.nuIdentificadorCliente = nuIdentificadorCliente;
	}
}
