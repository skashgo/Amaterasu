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
public class EmpregadoCaixaId implements java.io.Serializable {
	
	private static final long serialVersionUID = 4324225451L;
	private Short nuUnidade;
	private Integer nuMatricula;
	private String noPessoa;
	private Short nuUnddeAlccoU24;
	
	public EmpregadoCaixaId() {
	}
	
	public EmpregadoCaixaId(Short nuUnidade, Integer nuMatricula, String noPessoa, Short nuUnddeAlccoU24) {
		this.nuUnidade = nuUnidade;
		this.nuMatricula = nuMatricula;
		this.noPessoa = noPessoa;
		this.nuUnddeAlccoU24 = nuUnddeAlccoU24;
	}
	
	@Column(name = "nu_unidade")
	public Short getNuUnidade() {
		return this.nuUnidade;
	}
	
	public void setNuUnidade(Short nuUnidade) {
		this.nuUnidade = nuUnidade;
	}
	
	@Column(name = "nu_matricula")
	public Integer getNuMatricula() {
		return this.nuMatricula;
	}
	
	public void setNuMatricula(Integer nuMatricula) {
		this.nuMatricula = nuMatricula;
	}
	
	@Column(name = "no_pessoa", length = 70)
	public String getNoPessoa() {
		return this.noPessoa;
	}
	
	public void setNoPessoa(String noPessoa) {
		this.noPessoa = noPessoa;
	}
	
	@Column(name = "nu_undde_alcco_u24")
	public Short getNuUnddeAlccoU24() {
		return this.nuUnddeAlccoU24;
	}
	
	public void setNuUnddeAlccoU24(Short nuUnddeAlccoU24) {
		this.nuUnddeAlccoU24 = nuUnddeAlccoU24;
	}
}
