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
package br.gov.caixa.siiac.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author GIS Consult
 */
@Entity
public class LogEnvioParecerVO {
	
	private int nuParecer;
	private Short aaParecer;
	private Short nuUnidade;
	private Short nuNatural;
	private Date dtParecer;
	private String coProduto;
	private String coResponsavelParecer;
	private String noParecer;
	private Boolean icSituacaoEnvioParecer;
	
	// Pesquisa Avançada
	private Short nuUnidadeFiltro;
	private Short nuUnidadeVerificacaoFiltro;
	private Short nuCanalFiltro;
	private String coProdutoFiltro;
	private String coUsuarioRespFiltro;
	private Integer nuParecerFiltro;
	private String coContratoFiltro;
	private Integer nuConveniadoFiltro;
	private Date inicioFiltro;
	private Date fimFiltro;
	private boolean somenteComErroFiltro;
	
	private boolean reenviar;
	
	public LogEnvioParecerVO() {
	}
	
	public LogEnvioParecerVO(int nuParecer, Short aaParecer, Short nuUnidade, Short nuNatural, Date dtParecer, String coProduto, String coResponsavelParecer, String noParecer, Boolean icSituacaoEnvioParecer) {
		this.nuParecer = nuParecer;
		this.aaParecer = aaParecer;
		this.nuUnidade = nuUnidade;
		this.nuNatural = nuNatural;
		this.dtParecer = dtParecer;
		this.coProduto = coProduto;
		this.coResponsavelParecer = coResponsavelParecer;
		this.noParecer = noParecer;
		this.icSituacaoEnvioParecer = icSituacaoEnvioParecer;
	}
	
	@Id
	@Column(name = "nu_parecer")
	public int getNuParecer() {
		return nuParecer;
	}
	
	public void setNuParecer(int nuParecer) {
		this.nuParecer = nuParecer;
	}
	
	@Column(name = "aa_parecer")
	public Short getAaParecer() {
		return aaParecer;
	}
	
	public void setAaParecer(Short aaParecer) {
		this.aaParecer = aaParecer;
	}
	
	@Column(name = "nu_unidade")
	public Short getNuUnidade() {
		return nuUnidade;
	}
	
	public void setNuUnidade(Short nuUnidade) {
		this.nuUnidade = nuUnidade;
	}
	
	@Column(name = "nu_natural")
	public Short getNuNatural() {
		return nuNatural;
	}
	
	public void setNuNatural(Short nuNatural) {
		this.nuNatural = nuNatural;
	}
	
	@Column(name = "dt_parecer")
	public Date getDtParecer() {
		return dtParecer;
	}
	
	public void setDtParecer(Date dtParecer) {
		this.dtParecer = dtParecer;
	}
	
	@Column(name = "co_produto")
	public String getCoProduto() {
		return coProduto;
	}
	
	public void setCoProduto(String coProduto) {
		this.coProduto = coProduto;
	}
	
	@Column(name = "co_responsavel_parecer")
	public String getCoResponsavelParecer() {
		return coResponsavelParecer;
	}
	
	public void setCoResponsavelParecer(String coResponsavelParecer) {
		this.coResponsavelParecer = coResponsavelParecer;
	}
	
	@Column(name = "no_parecer")
	public String getNoParecer() {
		return noParecer;
	}
	
	public void setNoParecer(String noParecer) {
		this.noParecer = noParecer;
	}
	
	@Column(name = "ic_situacao_envio_parecer")
	public Boolean getIcSituacaoEnvioParecer() {
		return icSituacaoEnvioParecer;
	}
	
	public void setIcSituacaoEnvioParecer(Boolean icSituacaoEnvioParecer) {
		this.icSituacaoEnvioParecer = icSituacaoEnvioParecer;
	}
	
	@Transient
	public boolean isReenviar() {
		return reenviar;
	}
	
	public void setReenviar(boolean reenviar) {
		this.reenviar = reenviar;
	}
	
	@Transient
	public Short getNuUnidadeFiltro() {
		return nuUnidadeFiltro;
	}
	
	public void setNuUnidadeFiltro(Short nuUnidadeFiltro) {
		this.nuUnidadeFiltro = nuUnidadeFiltro;
	}
	
	@Transient
	public Short getNuUnidadeVerificacaoFiltro() {
		return nuUnidadeVerificacaoFiltro;
	}
	
	public void setNuUnidadeVerificacaoFiltro(Short nuUnidadeVerificacaoFiltro) {
		this.nuUnidadeVerificacaoFiltro = nuUnidadeVerificacaoFiltro;
	}
	
	@Transient
	public Short getNuCanalFiltro() {
		return nuCanalFiltro;
	}
	
	public void setNuCanalFiltro(Short nuCanalFiltro) {
		this.nuCanalFiltro = nuCanalFiltro;
	}
	
	@Transient
	public String getCoProdutoFiltro() {
		return coProdutoFiltro;
	}
	
	public void setCoProdutoFiltro(String coProdutoFiltro) {
		this.coProdutoFiltro = coProdutoFiltro;
	}
	
	@Transient
	public String getCoUsuarioRespFiltro() {
		return coUsuarioRespFiltro;
	}
	
	public void setCoUsuarioRespFiltro(String coUsuarioRespFiltro) {
		this.coUsuarioRespFiltro = coUsuarioRespFiltro;
	}
	
	@Transient
	public Integer getNuParecerFiltro() {
		return nuParecerFiltro;
	}
	
	public void setNuParecerFiltro(Integer nuParecerFiltro) {
		this.nuParecerFiltro = nuParecerFiltro;
	}
	
	@Transient
	public String getCoContratoFiltro() {
		return coContratoFiltro;
	}
	
	public void setCoContratoFiltro(String coContratoFiltro) {
		this.coContratoFiltro = coContratoFiltro;
	}
	
	@Transient
	public Integer getNuConveniadoFiltro() {
		return nuConveniadoFiltro;
	}
	
	public void setNuConveniadoFiltro(Integer nuConveniadoFiltro) {
		this.nuConveniadoFiltro = nuConveniadoFiltro;
	}
	
	@Transient
	public Date getInicioFiltro() {
		return inicioFiltro;
	}
	
	public void setInicioFiltro(Date inicioFiltro) {
		this.inicioFiltro = inicioFiltro;
	}
	
	@Transient
	public Date getFimFiltro() {
		return fimFiltro;
	}
	
	public void setFimFiltro(Date fimFiltro) {
		this.fimFiltro = fimFiltro;
	}
	
	@Transient
	public boolean isSomenteComErroFiltro() {
		return somenteComErroFiltro;
	}
	
	public void setSomenteComErroFiltro(boolean somenteComErroFiltro) {
		this.somenteComErroFiltro = somenteComErroFiltro;
	}
	
}
