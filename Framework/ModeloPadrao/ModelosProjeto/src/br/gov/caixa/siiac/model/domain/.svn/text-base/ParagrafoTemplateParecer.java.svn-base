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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "iactb024_prgrfo_tmplte_parecer", schema = "iacsm001")
public class ParagrafoTemplateParecer implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long nuParagrafo;
	private String icParagrafo;
	private String deParagrafo;
	private TemplateParecer templateParecer;
	
	public ParagrafoTemplateParecer() {
	}
	
	public ParagrafoTemplateParecer(Long nuParagrafo) {
		this.nuParagrafo = nuParagrafo;
	}
	
	public ParagrafoTemplateParecer(Long nuParagrafo, String icParagrafo, String deParagrafo) {
		this.nuParagrafo = nuParagrafo;
		this.icParagrafo = icParagrafo;
		this.deParagrafo = deParagrafo;
	}
	
	@Id
	@Column(name = "nu_paragrafo")
	public Long getNuParagrafo() {
		return nuParagrafo;
	}
	
	public void setNuParagrafo(Long nuParagrafo) {
		this.nuParagrafo = nuParagrafo;
	}
	
	@Column(name = "ic_paragrafo")
	public String getIcParagrafo() {
		return icParagrafo;
	}
	
	public void setIcParagrafo(String icParagrafo) {
		this.icParagrafo = icParagrafo;
	}
	
	@Column(name = "de_paragrafo")
	public String getDeParagrafo() {
		return deParagrafo;
	}
	
	public void setDeParagrafo(String deParagrafo) {
		this.deParagrafo = deParagrafo;
	}
	
	@JoinColumn(name = "nu_template_parecer", referencedColumnName = "nu_template_parecer")
	@ManyToOne
	public TemplateParecer getTemplateParecer() {
		return templateParecer;
	}
	
	public void setTemplateParecer(TemplateParecer nuTemplateParecer) {
		this.templateParecer = nuTemplateParecer;
	}
}
