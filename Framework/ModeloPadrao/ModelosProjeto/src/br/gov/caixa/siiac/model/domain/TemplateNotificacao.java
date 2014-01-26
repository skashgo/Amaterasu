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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb020_template_notificacao", schema = "iacsm001")
@TrailClass(fonte = "MODELO_NOTIFICACAO")
public class TemplateNotificacao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer nuTemplateNotificacao;
	private Short nuTipoUnidadeNotificada;
	private Character icTipoNotificacao;
	private String coCxPostalSaida;
	private String noAssuntoNotificacao;
	private String noReferenciaNotificacao;
	private String textoNotificacao;
		
	public TemplateNotificacao() {
	}
	
	public TemplateNotificacao(Integer nuTemplateNotificacao) {
		this.nuTemplateNotificacao = nuTemplateNotificacao;
	}
	
	
	public TemplateNotificacao(Integer nuTemplateNotificacao,
			Short nuTipoUnidadeNotificada, Character icTipoNotificacao,
			String coCxPostalSaida, String noAssuntoNotificacao,
			String noReferenciaNotificacao, String textoNotificacao) {
		this.nuTemplateNotificacao = nuTemplateNotificacao;
		this.nuTipoUnidadeNotificada = nuTipoUnidadeNotificada;
		this.icTipoNotificacao = icTipoNotificacao;
		this.coCxPostalSaida = coCxPostalSaida;
		this.noAssuntoNotificacao = noAssuntoNotificacao;
		this.noReferenciaNotificacao = noReferenciaNotificacao;
		this.textoNotificacao = textoNotificacao;
	}

	@Id
	@Column(name = "nu_template_notificacao", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq025_template_notificacao")
	@SequenceGenerator(name = "iacsq025_template_notificacao", sequenceName = "iacsm001.iacsq025_template_notificacao")
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public Integer getNuTemplateNotificacao() {
		return nuTemplateNotificacao;
	}
	
	public void setNuTemplateNotificacao(Integer nuTemplateNotificacao) {
		this.nuTemplateNotificacao = nuTemplateNotificacao;
	}
	
	@Column(name = "nu_tipo_unidade_notificada", nullable = false)
	@TrailLog
	public Short getNuTipoUnidadeNotificada() {
		return nuTipoUnidadeNotificada;
	}
	
	public void setNuTipoUnidadeNotificada(Short nuTipoUnidadeNotificada) {
		this.nuTipoUnidadeNotificada = nuTipoUnidadeNotificada;
	}
	
	@Column(name = "ic_tipo_notificacao", nullable = false, length = 1)
	@TrailLog
	public Character getIcTipoNotificacao() {
		return icTipoNotificacao;
	}
	
	public void setIcTipoNotificacao(Character icTipoNotificacao) {
		this.icTipoNotificacao = icTipoNotificacao;
	}
	
	@Column(name = "co_cx_postal_saida", length = 10)
	@TrailLog
	public String getCoCxPostalSaida() {
		return coCxPostalSaida;
	}
	
	public void setCoCxPostalSaida(String coCxPostalSaida) {
		this.coCxPostalSaida = coCxPostalSaida;
	}
	
	@Column(name = "no_assunto_notificacao", nullable = false, length = 70)
	@TrailLog
	public String getNoAssuntoNotificacao() {
		return noAssuntoNotificacao;
	}
	
	public void setNoAssuntoNotificacao(String noAssuntoNotificacao) {
		this.noAssuntoNotificacao = noAssuntoNotificacao;
	}
	
	@Column(name = "no_referencia_notificacao", length = 70)
	@TrailLog
	public String getNoReferenciaNotificacao() {
		return noReferenciaNotificacao;
	}
	
	public void setNoReferenciaNotificacao(String noReferenciaNotificacao) {
		this.noReferenciaNotificacao = noReferenciaNotificacao;
	}
	
	public void setTextoNotificacao(String textoNotificacao) {
		this.textoNotificacao = textoNotificacao;
	}

	@Column(name = "de_texto_notificacao",nullable=false)
	@TrailLog
	public String getTextoNotificacao() {
		return textoNotificacao;
	}
	
}
