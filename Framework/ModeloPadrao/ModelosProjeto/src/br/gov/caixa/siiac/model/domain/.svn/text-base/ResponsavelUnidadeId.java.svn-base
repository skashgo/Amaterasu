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
public class ResponsavelUnidadeId implements java.io.Serializable {
	
	private static final long serialVersionUID = 55849649840L;
	private Short nuUnidade;
	private Integer nuMatriculaH01;
	private Character icEventual;
	
	public ResponsavelUnidadeId() {
	}
	
	public ResponsavelUnidadeId(Short nuUnidade, Integer nuMatriculaH01, Character icEventual) {
		this.nuUnidade = nuUnidade;
		this.nuMatriculaH01 = nuMatriculaH01;
		this.icEventual = icEventual;
	}
	
	@Column(name = "nu_unidade")
	public Short getNuUnidade() {
		return this.nuUnidade;
	}
	
	public void setNuUnidade(Short nuUnidade) {
		this.nuUnidade = nuUnidade;
	}
	
	@Column(name = "nu_matricula_h01")
	public Integer getNuMatriculaH01() {
		return this.nuMatriculaH01;
	}
	
	public void setNuMatriculaH01(Integer nuMatriculaH01) {
		this.nuMatriculaH01 = nuMatriculaH01;
	}
	
	@Column(name = "ic_eventual", length = 1)
	public Character getIcEventual() {
		return this.icEventual;
	}
	
	public void setIcEventual(Character icEventual) {
		this.icEventual = icEventual;
	}
}
