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
import javax.persistence.Embeddable;

import br.gov.caixa.siiac.util.annotation.TrailLog;

@Embeddable
public class ParecerId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int nuParecer;
	private Short aaParecer;
	private Short nuUnidade;
	private Short nuNatural;
	
	public ParecerId() {
	}
	
	public ParecerId(int nuParecer, Short aaParecer, Short nuUnidade, Short nuNatural) {
		this.nuParecer = nuParecer;
		this.aaParecer = aaParecer;
		this.nuUnidade = nuUnidade;
		this.nuNatural = nuNatural;
	}
	
	@Column(name = "nu_parecer", nullable = false)
	@TrailLog(insertID = true, updateID = true, deleteID = true)
	public int getNuParecer() {
		return nuParecer;
	}
	
	public void setNuParecer(int nuParecer) {
		this.nuParecer = nuParecer;
	}
	
	@Column(name = "aa_parecer", nullable = false)
	@TrailLog(insertID = true, updateID = true, deleteID = true)
	public Short getAaParecer() {
		return aaParecer;
	}
	
	public void setAaParecer(Short aaParecer) {
		this.aaParecer = aaParecer;
	}
	
	@Column(name = "nu_unidade", nullable = false)
	@TrailLog
	public Short getNuUnidade() {
		return nuUnidade;
	}
	
	public void setNuUnidade(Short nuUnidade) {
		this.nuUnidade = nuUnidade;
	}
	
	@Column(name = "nu_natural", nullable = false)
	@TrailLog
	public Short getNuNatural() {
		return nuNatural;
	}
	
	public void setNuNatural(Short nuNatural) {
		this.nuNatural = nuNatural;
	}
	
}
