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
@Table(name = "iactb026_prgrfo_tmplte_ntfccao", schema = "iacsm001")
public class ParagrafoTemplateNotificacao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long nuParagrafoTemplateNotificacao;
	private String icParagrafo;
	private String deParagrafoTemplateNotificacao;
	private TemplateNotificacao templateNotificacao;
	
	public ParagrafoTemplateNotificacao() {
	}
	
	public ParagrafoTemplateNotificacao(Long nuPrgrfoTmplteNtfccao) {
		this.nuParagrafoTemplateNotificacao = nuPrgrfoTmplteNtfccao;
	}
	
	public ParagrafoTemplateNotificacao(Long nuPrgrfoTmplteNtfccao, String icParagrafo, String dePrgrfoTmplteNtfccao) {
		this.nuParagrafoTemplateNotificacao = nuPrgrfoTmplteNtfccao;
		this.icParagrafo = icParagrafo;
		this.deParagrafoTemplateNotificacao = dePrgrfoTmplteNtfccao;
	}
	
	@Id
	@Column(name = "nu_prgrfo_tmplte_ntfccao")
	public Long getNuParagrafoTemplateNotificacao() {
		return nuParagrafoTemplateNotificacao;
	}
	
	public void setNuParagrafoTemplateNotificacao(Long nuPrgrfoTmplteNtfccao) {
		this.nuParagrafoTemplateNotificacao = nuPrgrfoTmplteNtfccao;
	}
	
	@Column(name = "ic_paragrafo")
	public String getIcParagrafo() {
		return icParagrafo;
	}
	
	public void setIcParagrafo(String icParagrafo) {
		this.icParagrafo = icParagrafo;
	}
	
	@Column(name = "de_prgrfo_tmplte_ntfccao")
	public String getDeParagrafoTemplateNotificacao() {
		return deParagrafoTemplateNotificacao;
	}
	
	public void setDeParagrafoTemplateNotificacao(String dePrgrfoTmplteNtfccao) {
		this.deParagrafoTemplateNotificacao = dePrgrfoTmplteNtfccao;
	}
	
	@JoinColumn(name = "nu_template_notificacao", referencedColumnName = "nu_template_notificacao")
	@ManyToOne
	public TemplateNotificacao getTemplateNotificacao() {
		return templateNotificacao;
	}
	
	public void setTemplateNotificacao(TemplateNotificacao nuTemplateNotificacao) {
		this.templateNotificacao = nuTemplateNotificacao;
	}
}
