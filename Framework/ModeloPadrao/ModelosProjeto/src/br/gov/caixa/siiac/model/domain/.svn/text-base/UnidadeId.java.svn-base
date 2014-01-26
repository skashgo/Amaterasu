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

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UnidadeId implements java.io.Serializable {
	
	private static final long serialVersionUID = 85749084189489L;
	private Short nuUnidade;
	private Integer nuNatural;
	
	public UnidadeId() {
		
	}
	
	public UnidadeId(Short nuUnidade, Integer nuNatural) {
		this.nuUnidade = nuUnidade;
		this.nuNatural = nuNatural;
	}
	
	@Column(name = "nu_unidade")
	public Short getNuUnidade() {
		return this.nuUnidade;
	}
	
	public void setNuUnidade(Short nuUnidade) {
		this.nuUnidade = nuUnidade;
	}
	
	@Column(name = "nu_natural")
	public Integer getNuNatural() {
		return this.nuNatural;
	}
	
	public void setNuNatural(Integer nuNatural) {
		this.nuNatural = nuNatural;
	}
}