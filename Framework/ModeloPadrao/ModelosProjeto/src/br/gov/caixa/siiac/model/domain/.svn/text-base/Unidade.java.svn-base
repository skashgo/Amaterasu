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

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.caixa.format.FormatUitils;
import br.gov.caixa.util.Utilities;

@Entity
@Table(name = "iacvw003_unidade", schema = "icosm001")
public class Unidade implements java.io.Serializable {
	
	private static final long serialVersionUID = 908948908484L;
	private UnidadeId id;
	private String noUnidade;
	private String sgUnidade;
	private Long nuCgcUnidade;
	private Date dtFim;
	private Short nuTipoUnidadeU21;
	private String sgTipoUnidade;
	private String sgUfL22;
	private String sgLocalizacao;
	
	public Unidade() {
	}
	
	public Unidade(String noUnidade, String sgUnidade, Long nuCgcUnidade, Date dtFim, Short nuTipoUnidadeU21, String sgTipoUnidadeU21, String sgUfL22, String sgLocalizacao) {
		this.noUnidade = noUnidade;
		this.sgUnidade = sgUnidade;
		this.nuCgcUnidade = nuCgcUnidade;
		this.dtFim = dtFim;
		this.nuTipoUnidadeU21 = nuTipoUnidadeU21;
		this.sgTipoUnidade = sgTipoUnidadeU21;
		this.sgUfL22 = sgUfL22;
		this.sgLocalizacao = sgLocalizacao;
	}
	
	@EmbeddedId
	@AttributeOverrides({ 
		@AttributeOverride(name = "nuUnidade", column = @Column(name = "nu_unidade", nullable = false)), 
		@AttributeOverride(name = "nuNatural", column = @Column(name = "nu_natural", nullable = false)) 
	})
	public UnidadeId getId() {
		return this.id;
	}
	
	public void setId(UnidadeId id) {
		this.id = id;
	}
	
	@Column(name = "no_unidade", length = 35)
	public String getNoUnidade() {
		return this.noUnidade;
	}
	
	public void setNoUnidade(String noUnidade) {
		this.noUnidade = noUnidade;
	}
	
	@Column(name = "sg_unidade", length = 5)
	public String getSgUnidade() {
		return this.sgUnidade;
	}
	
	public void setSgUnidade(String sgUnidade) {
		this.sgUnidade = sgUnidade;
	}
	
	@Column(name = "nu_cgc_unidade", precision = 12, scale = 0)
	public Long getNuCgcUnidade() {
		return this.nuCgcUnidade;
	}
	
	public void setNuCgcUnidade(Long nuCgcUnidade) {
		this.nuCgcUnidade = nuCgcUnidade;
	}
	
	@Column(name = "dt_fim", length = 13)
	public Date getDtFim() {
		return this.dtFim;
	}
	
	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}
	
	@Column(name = "nu_tp_unidade_u21")
	public Short getNuTipoUnidadeU21() {
		return this.nuTipoUnidadeU21;
	}
	
	public void setNuTipoUnidadeU21(Short nuTipoUnidadeU21) {
		this.nuTipoUnidadeU21 = nuTipoUnidadeU21;
	}

	@Column(name = "sg_tipo_unidade")
	public String getSgTipoUnidade() {
		return this.sgTipoUnidade;
	}
	
	public void setSgTipoUnidade(String sgTipoUnidade) {
		this.sgTipoUnidade = sgTipoUnidade;
	}
	
	@Column(name = "sg_uf_l22", length = 2)
	public String getSgUfL22() {
		return this.sgUfL22;
	}
	
	public void setSgUfL22(String sgUfL22) {
		this.sgUfL22 = sgUfL22;
	}
	
	@Column(name = "sg_localizacao", length = 2)
	public String getSgLocalizacao() {
		return this.sgLocalizacao;
	}
	
	public void setSgLocalizacao(String sgLocalizacao) {
		this.sgLocalizacao = sgLocalizacao;
	}
	
	@Transient
	public String getNomeAbreviado() {
		
		String siglaUf = "";
		Short tipoUnidade = 0;
		String siglaUnidade = "";
		String siglaLocalizacao = "";
		String noUnidade = "";
		String siglaTipoUnidade = "";
		
		if (!Utilities.empty(getSgUfL22())) {
			siglaUf = getSgUfL22().trim();
		}
		
		if (!Utilities.empty(getNuTipoUnidadeU21())) {
			tipoUnidade = getNuTipoUnidadeU21();
		}
		
		if (!Utilities.empty(getSgUnidade())) {
			siglaUnidade = getSgUnidade().trim();
		}
		
		if (!Utilities.empty(getSgLocalizacao())) {
			siglaLocalizacao = getSgLocalizacao().trim();
		}
		
		if (!Utilities.empty(getNoUnidade())) {
			noUnidade = getNoUnidade().trim();
		}
		
		if (!Utilities.empty(getSgTipoUnidade())) {
			siglaTipoUnidade = getSgTipoUnidade().trim();
		}
		
		return FormatUitils.getNameForUnidade(siglaUf, tipoUnidade, siglaUnidade, siglaLocalizacao, noUnidade, siglaTipoUnidade);
	}
	
	@Transient
	public String getNomeCompleto() {
		if(Utilities.notEmpty(getSgTipoUnidade()) && Utilities.notEmpty(getNoUnidade())) {
			return getSgTipoUnidade() + " " + getNoUnidade();
		}else if(Utilities.notEmpty(getNoUnidade())){
			return getNoUnidade();
		}else if(Utilities.notEmpty(getSgTipoUnidade())){
			return getSgTipoUnidade();
		}
		return "";
	}
}
