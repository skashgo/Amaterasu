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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author GIS Consult
 *
 */
public class RelatorioVerificacaoVO {

	private String situacao;
	private Short coGiret;
	private Short coSr;
	private Short coUnidade;
	private String coProduto;
	private String coContrato;
	private String noCliente;
	private String nuIdentificador;
	private Date dtFormatada;
	private Double valorVerif;
	
	public String getSituacao() {
		return situacao;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public Short getCoGiret() {
		return coGiret;
	}
	
	public void setCoGiret(Short coGiret) {
		this.coGiret = coGiret;
	}
	
	public Short getCoSr() {
		return coSr;
	}
	
	public void setCoSr(Short coSr) {
		this.coSr = coSr;
	}
	
	public Short getCoUnidade() {
		return coUnidade;
	}
	
	public void setCoUnidade(Short coUnidade) {
		this.coUnidade = coUnidade;
	}
	
	public String getCoProduto() {
		return coProduto;
	}
	
	public void setCoProduto(String coProduto) {
		this.coProduto = coProduto;
	}
	
	public String getCoContrato() {
		return coContrato;
	}
	
	public void setCoContrato(String coContrato) {
		this.coContrato = coContrato;
	}
	
	public String getNoCliente() {
		return noCliente;
	}
	
	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}
	
	public String getNuIdentificador() {
		return nuIdentificador;
	}
	
	public void setNuIdentificador(String nuIdentificador) {
		this.nuIdentificador = nuIdentificador;
	}
	
	public Date getDtFormatada() {
		return dtFormatada;
	}
	
	public void setDtFormatada(Date dtFormatada) {
		this.dtFormatada = dtFormatada;
	}
	
	public Double getValorVerif() {
		return valorVerif;
	}
	
	public void setValorVerif(Double valorVerif) {
		this.valorVerif = valorVerif;
	}
	
}
