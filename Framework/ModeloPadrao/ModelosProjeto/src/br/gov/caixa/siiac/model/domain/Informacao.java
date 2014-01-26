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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "iactb040_informacao", schema = "iacsm001")
public class Informacao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer nuInformacao;
	private String noInformacao;
	private String deInformacao;
	private String coUsuarioResponsavel;
	private Date dtInicio;
	private Date dtFim;
	
	public Informacao() {
	}
	
	public Informacao(Integer nuInformacao) {
		this.nuInformacao = nuInformacao;
	}
	
	public Informacao(Integer nuInformacao, String noInformacao, String deInformacao, Date dtInicio, String coUsuarioResponsavel) {
		this.nuInformacao = nuInformacao;
		this.noInformacao = noInformacao;
		this.deInformacao = deInformacao;
		this.dtInicio = dtInicio;
		this.coUsuarioResponsavel = coUsuarioResponsavel;
	}
	
	@Id
	@Column(name = "nu_informacao", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq024_informacao")
	@SequenceGenerator(name = "iacsq024_informacao", sequenceName = "iacsm001.iacsq024_informacao")
	public Integer getNuInformacao() {
		return nuInformacao;
	}
	
	public void setNuInformacao(Integer nuInformacao) {
		this.nuInformacao = nuInformacao;
	}
	
	@Column(name = "no_informacao", nullable = false, length = 70)
	public String getNoInformacao() {
		return noInformacao;
	}
	
	public void setNoInformacao(String noInformacao) {
		this.noInformacao = noInformacao;
	}
	
	@Column(name = "de_informacao", nullable = false, length = 2147483647)
	public String getDeInformacao() {
		return deInformacao;
	}
	
	public void setDeInformacao(String deInformacao) {
		this.deInformacao = deInformacao;
	}
	
	@Column(name = "dt_inicio")
	@Temporal(TemporalType.DATE)
	public Date getDtInicio() {
		return dtInicio;
	}
	
	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}
	
	@Column(name = "dt_fim")
	@Temporal(TemporalType.DATE)
	public Date getDtFim() {
		return dtFim;
	}
	
	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}
	
	@Column(name = "co_usuario_responsavel", nullable = false, length = 7)
	public String getCoUsuarioResponsavel() {
		return coUsuarioResponsavel;
	}
	
	public void setCoUsuarioResponsavel(String coUsuarioResponsavel) {
		this.coUsuarioResponsavel = coUsuarioResponsavel;
	}
}
