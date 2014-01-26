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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author GIS Consult
 * 
 */
@Entity
public class EnviaNotificacaoVO {

	private String codigoVerificacao;
	private String codigoContrato;
	private Integer codigoServicoVerificacao;
	private Short unidadeResponsavel;
	private Short numeroNaturalUnidadeResponsavel;
	private String matriculaResponsavelVerificacao;
	private Integer codigoChecklist;
	
	private Integer numeroTemplate;
	private List<String> emailUnidade = new ArrayList<String>();
	private List<String> emailCopia = new ArrayList<String>();
	private Integer matriculaResponsavelUnidade;
	private String emailResponsavelUnidade;
	private Integer matriculaEventualResponsavelUnidade;
	private String emailEventualResponsavelUnidade;
	private Date dataProcessamento;

	public EnviaNotificacaoVO() {

	}

	@Id
	@Column(name = "codigoverificacao")
	public String getCodigoVerificacao() {
		return codigoVerificacao;
	}

	public void setCodigoVerificacao(String codigoVerificacao) {
		this.codigoVerificacao = codigoVerificacao;
	}

	@Column(name = "codigocontrato")
	public String getCodigoContrato() {
		return codigoContrato;
	}

	public void setCodigoContrato(String codigoContrato) {
		this.codigoContrato = codigoContrato;
	}

	@Column(name = "codigoservicoverificacao")
	public Integer getCodigoServicoVerificacao() {
		return codigoServicoVerificacao;
	}

	public void setCodigoServicoVerificacao(Integer codigoServicoVerificacao) {
		this.codigoServicoVerificacao = codigoServicoVerificacao;
	}

	@Column(name = "unidaderesponsavel")
	public Short getUnidadeResponsavel() {
		return unidadeResponsavel;
	}

	public void setUnidadeResponsavel(Short unidadeResponsavel) {
		this.unidadeResponsavel = unidadeResponsavel;
	}

	@Column(name = "numeronaturalunidaderesponsavel")
	public Short getNumeroNaturalUnidadeResponsavel() {
		return numeroNaturalUnidadeResponsavel;
	}

	public void setNumeroNaturalUnidadeResponsavel(
			Short numeroNaturalUnidadeResponsavel) {
		this.numeroNaturalUnidadeResponsavel = numeroNaturalUnidadeResponsavel;
	}

	@Column(name = "matricularesponsavel")
	public String getMatriculaResponsavelVerificacao() {
		return matriculaResponsavelVerificacao;
	}


	public void setMatriculaResponsavelVerificacao(
			String matriculaResponsavelVerificacao) {
		this.matriculaResponsavelVerificacao = matriculaResponsavelVerificacao;
	}

	@Column(name = "codigochecklist")
	public Integer getCodigoChecklist() {
		return codigoChecklist;
	}

	
	public void setCodigoChecklist(Integer codigoChecklist) {
		this.codigoChecklist = codigoChecklist;
	}

	@Transient
	public Integer getNumeroTemplate() {
		return numeroTemplate;
	}

	public void setNumeroTemplate(Integer numeroTemplate) {
		this.numeroTemplate = numeroTemplate;
	}

	@Transient
	public List<String> getEmailUnidade() {
		return emailUnidade;
	}

	public void setEmailUnidade(List<String> emailUnidade) {
		this.emailUnidade = emailUnidade;
	}

	@Transient
	public List<String> getEmailCopia() {
		return emailCopia;
	}

	public void setEmailCopia(List<String> emailCopia) {
		this.emailCopia = emailCopia;
	}

	@Transient
	public Integer getMatriculaResponsavelUnidade() {
		return matriculaResponsavelUnidade;
	}

	public void setMatriculaResponsavelUnidade(
			Integer matriculaResponsavelUnidade) {
		this.matriculaResponsavelUnidade = matriculaResponsavelUnidade;
	}

	@Transient
	public String getEmailResponsavelUnidade() {
		return emailResponsavelUnidade;
	}

	public void setEmailResponsavelUnidade(String emailResponsavelUnidade) {
		this.emailResponsavelUnidade = emailResponsavelUnidade;
	}

	@Transient
	public Integer getMatriculaEventualResponsavelUnidade() {
		return matriculaEventualResponsavelUnidade;
	}

	public void setMatriculaEventualResponsavelUnidade(
			Integer matriculaEventualResponsavelUnidade) {
		this.matriculaEventualResponsavelUnidade = matriculaEventualResponsavelUnidade;
	}

	@Transient
	public String getEmailEventualResponsavelUnidade() {
		return emailEventualResponsavelUnidade;
	}

	public void setEmailEventualResponsavelUnidade(
			String emailEventualResponsavelUnidade) {
		this.emailEventualResponsavelUnidade = emailEventualResponsavelUnidade;
	}

	@Transient
	public Date getDataProcessamento() {
		return dataProcessamento;
	}

	public void setDataProcessamento(Date dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}
}