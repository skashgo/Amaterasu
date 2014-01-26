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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.format.FormatUitils;

/**
 * @author GIS Consult
 *
 */
@Entity
public class GerenteUnidade implements java.io.Serializable {
	
	private static final String LABEL_SUBSTITUTO_EVENTUAL = "SUBSTITUTO EVENTUAL";
	
	private static final long serialVersionUID = 963172578210L;
	private Short nuUnidade;
	private Integer nuMatricula;
	private String noPessoa;
	private Short nuUnidadeAlocacao;
	private String noFuncao;
	private Short nuTipoFuncao;
	private Boolean eventual;
	
	
	/**
	 * 
	 */
	public GerenteUnidade() {
		super();
	}
	
	/**
	 * @param nuUnidade
	 * @param nuMatricula
	 * @param noPessoa
	 * @param nuUnidadeAlocacao
	 * @param noFuncao
	 * @param nuTipoFuncao
	 */
	public GerenteUnidade(Short nuUnidade, Integer nuMatricula,
			String noPessoa, Short nuUnidadeAlocacao, String noFuncao,
			Short nuTipoFuncao) {
		super();
		this.nuUnidade = nuUnidade;
		this.nuMatricula = nuMatricula;
		this.noPessoa = noPessoa;
		this.nuUnidadeAlocacao = nuUnidadeAlocacao;
		this.noFuncao = noFuncao;
		this.nuTipoFuncao = nuTipoFuncao;
	}

	@Column(name = "nu_unidade")
	public Short getNuUnidade() {
		return nuUnidade;
	}

	public void setNuUnidade(Short nuUnidade) {
		this.nuUnidade = nuUnidade;
	}

	@Id
	@Column(name = "nu_matricula")
	public Integer getNuMatricula() {
		return nuMatricula;
	}

	public void setNuMatricula(Integer nuMatricula) {
		this.nuMatricula = nuMatricula;
	}

	@Column(name = "no_pessoa")
	public String getNoPessoa() {
		return noPessoa;
	}

	public void setNoPessoa(String noPessoa) {
		this.noPessoa = noPessoa;
	}

	@Column(name = "nu_undde_alcco_u24")
	public Short getNuUnidadeAlocacao() {
		return nuUnidadeAlocacao;
	}

	public void setNuUnidadeAlocacao(Short nuUnidadeAlocacao) {
		this.nuUnidadeAlocacao = nuUnidadeAlocacao;
	}

	@Column(name = "no_funcao")
	public String getNoFuncao() {
		return noFuncao;
	}

	public void setNoFuncao(String noFuncao) {
		this.noFuncao = noFuncao;
	}

	@Column(name = "nu_tipo_funcao_h04")
	public Short getNuTipoFuncao() {
		return nuTipoFuncao;
	}

	public void setNuTipoFuncao(Short nuTipoFuncao) {
		this.nuTipoFuncao = nuTipoFuncao;
	}
	
	@Column(name = "eventual")
	public Boolean getEventual() {
		return this.eventual;
	}
	
	public void setEventual(Boolean eventual) {
		this.eventual = eventual;
	}
	
	private static final Integer SIZE_MATRICULA = 6;
	
	@Transient
	public String getStrNuMatricula(){
		return "c" + ConvertUtils.padZerosLeft(String.valueOf(this.getNuMatricula()), SIZE_MATRICULA);
	}
	
	@Transient
	public String getStrEventual(){
		return (eventual ? LABEL_SUBSTITUTO_EVENTUAL : "");
	}
}
