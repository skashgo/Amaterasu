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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iacvw005_tipo_unidade", schema = "icosm001")
public class TipoUnidade implements java.io.Serializable {
	
	private static final long serialVersionUID = 12348424800L;
	private Short nuTpUnidadeU21;
	private String deTipoUnidade;
	private String sgTipoUnidade;
	private Character coSbssaOrgnlU14;
	private Date dtFim;
	
	public TipoUnidade() {
	}
	
	public TipoUnidade(Short nuTpUnidadeU21) {
		this.nuTpUnidadeU21 = nuTpUnidadeU21;
	}
	
	public TipoUnidade(Short nuTpUnidadeU21, String sgTipoUnidade, Character coSbssaOrgnlU14, Date dtFim) {
		this.nuTpUnidadeU21 = nuTpUnidadeU21;
		this.sgTipoUnidade = sgTipoUnidade;
		this.coSbssaOrgnlU14 = coSbssaOrgnlU14;
		this.dtFim = dtFim;
	}
	
	@Id
	@Column(name = "nu_tp_unidade_u21")
	public Short getNuTpUnidadeU21() {
		return nuTpUnidadeU21;
	}

	public void setNuTpUnidadeU21(Short nuTpUnidadeU21) {
		this.nuTpUnidadeU21 = nuTpUnidadeU21;
	}

	@Column(name = "de_tipo_unidade")
	public String getDeTipoUnidade() {
		return deTipoUnidade;
	}

	public void setDeTipoUnidade(String deTipoUnidade) {
		this.deTipoUnidade = deTipoUnidade;
	}

	@Column(name = "sg_tipo_unidade", length = 5)
	public String getSgTipoUnidade() {
		return this.sgTipoUnidade;
	}
	
	public void setSgTipoUnidade(String sgTipoUnidade) {
		this.sgTipoUnidade = sgTipoUnidade;
	}
	
	@Column(name = "co_sbssa_orgnl_u14", length = 1)
	public Character getCoSbssaOrgnlU14() {
		return this.coSbssaOrgnlU14;
	}
	
	public void setCoSbssaOrgnlU14(Character coSbssaOrgnlU14) {
		this.coSbssaOrgnlU14 = coSbssaOrgnlU14;
	}
	
	@Column(name = "dt_fim", length = 13)
	public Date getDtFim() {
		return this.dtFim;
	}
	
	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}
}