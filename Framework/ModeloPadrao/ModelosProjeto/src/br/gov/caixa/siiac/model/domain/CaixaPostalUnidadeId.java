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
public class CaixaPostalUnidadeId implements java.io.Serializable {
	
	private static final long serialVersionUID = 134325852432L;
	private Short nuUnidadeV03;
	private Integer nuNaturalV03;
	private Integer nuSqnlCmnco;
	private String coComunicacao;
	private String coCmploCmnco;
	private Short icTipoMeioCmnco;
	private Short nuTipoCmncoM04;
	
	public CaixaPostalUnidadeId() {
	}
	
	public CaixaPostalUnidadeId(Short nuUnidadeV03, Integer nuNaturalV03, Integer nuSqnlCmnco, String coComunicacao, String coCmploCmnco, Short icTipoMeioCmnco, Short nuTipoCmncoM04) {
		this.nuUnidadeV03 = nuUnidadeV03;
		this.nuNaturalV03 = nuNaturalV03;
		this.nuSqnlCmnco = nuSqnlCmnco;
		this.coComunicacao = coComunicacao;
		this.coCmploCmnco = coCmploCmnco;
		this.icTipoMeioCmnco = icTipoMeioCmnco;
		this.nuTipoCmncoM04 = nuTipoCmncoM04;
	}
	
	@Column(name = "nu_unidade_v03")
	public Short getNuUnidadeV03() {
		return this.nuUnidadeV03;
	}
	
	public void setNuUnidadeV03(Short nuUnidadeV03) {
		this.nuUnidadeV03 = nuUnidadeV03;
	}
	
	@Column(name = "nu_natural_v03")
	public Integer getNuNaturalV03() {
		return this.nuNaturalV03;
	}
	
	public void setNuNaturalV03(Integer nuNaturalV03) {
		this.nuNaturalV03 = nuNaturalV03;
	}
	
	@Column(name = "nu_sqnl_cmnco")
	public Integer getNuSqnlCmnco() {
		return this.nuSqnlCmnco;
	}
	
	public void setNuSqnlCmnco(Integer nuSqnlCmnco) {
		this.nuSqnlCmnco = nuSqnlCmnco;
	}
	
	@Column(name = "co_comunicacao", length = 40)
	public String getCoComunicacao() {
		return this.coComunicacao;
	}
	
	public void setCoComunicacao(String coComunicacao) {
		this.coComunicacao = coComunicacao;
	}
	
	@Column(name = "co_cmplo_cmnco", length = 10)
	public String getCoCmploCmnco() {
		return this.coCmploCmnco;
	}
	
	public void setCoCmploCmnco(String coCmploCmnco) {
		this.coCmploCmnco = coCmploCmnco;
	}
	
	@Column(name = "ic_tipo_meio_cmnco")
	public Short getIcTipoMeioCmnco() {
		return this.icTipoMeioCmnco;
	}
	
	public void setIcTipoMeioCmnco(Short icTipoMeioCmnco) {
		this.icTipoMeioCmnco = icTipoMeioCmnco;
	}
	
	@Column(name = "nu_tipo_cmnco_m04")
	public Short getNuTipoCmncoM04() {
		return this.nuTipoCmncoM04;
	}
	
	public void setNuTipoCmncoM04(Short nuTipoCmncoM04) {
		this.nuTipoCmncoM04 = nuTipoCmncoM04;
	}
}
