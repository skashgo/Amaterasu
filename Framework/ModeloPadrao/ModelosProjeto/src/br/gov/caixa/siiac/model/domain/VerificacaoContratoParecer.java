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
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "iactb055_vrfco_cntro_prcr", schema = "iacsm001")
public class VerificacaoContratoParecer implements Serializable {
	
	public static final String ESTADO_VERIFICACAO_ID_NAO_VERIFICADA = "NV";
	public static final String ESTADO_VERIFICACAO_ID_CONFORME = "CO";
	public static final String ESTADO_VERIFICACAO_ID_INCONFORME = "IC";
	
	public static final String ESTADO_VERIFICACAO_LABEL_NAO_VERIFICADA = "NÃO VERIFICADA";
	public static final String ESTADO_VERIFICACAO_LABEL_CONFORME = "CONFORME";
	public static final String ESTADO_VERIFICACAO_LABEL_INCONFORME = "INCONFORME";
	
	private static final long serialVersionUID = 1L;
	private Integer nuVerificacaoContrato;
	private int nuContrato;
	private int nuServicoVerificacaoProduto;
	private Short nuUnidadeResponsavelVerificacao;
	private int nuNaturalUnidadeResponsavelVerificacao;
	private Date dtInclusaoVerificacao;
	private Date dtVerificacao;
	private Date dtLimiteVerificacao;
	private String coResponsavelVerificacao;
	private String icEstadoVerificacao;
	private Boolean icSuspensa;
	private boolean icUltimaHierarquia;
	private VerificacaoContrato verificacaoContrato;
	private List<ResultadoApontamentoProdutoParecer> resultadoApontamentoProdutoParecerList;
	private List<VerificacaoContratoObservacoesParecer> verificacaoContratoObservacoesParecerList;
	private List<VerificacaoContratoParecer> verificacaoContratoParecerList;
	private VerificacaoContratoParecer verificacaoContratoPai;
	private List<Parecer> parecerList;
	private Integer nuChecklistServicoVerificacaoProduto;
	
	public VerificacaoContratoParecer() {
	}
	
	public VerificacaoContratoParecer(Integer nuVerificacaoContrato) {
		this.nuVerificacaoContrato = nuVerificacaoContrato;
	}
	
	public VerificacaoContratoParecer(Integer nuVerificacaoContrato, int nuContrato, int nuServicoVerificacaoProduto, Short nuUnidadeResponsavelVerificacao, int nuNaturalUnidadeResponsavelVerificacao, Date dtInclusaoVerificacao, Date dtLimiteVerificacao, String icEstadoVerificacao, boolean icUltimaHierarquia) {
		this.nuVerificacaoContrato = nuVerificacaoContrato;
		this.nuContrato = nuContrato;
		this.nuServicoVerificacaoProduto = nuServicoVerificacaoProduto;
		this.nuUnidadeResponsavelVerificacao = nuUnidadeResponsavelVerificacao;
		this.nuNaturalUnidadeResponsavelVerificacao = nuNaturalUnidadeResponsavelVerificacao;
		this.dtInclusaoVerificacao = dtInclusaoVerificacao;
		this.dtLimiteVerificacao = dtLimiteVerificacao;
		this.icEstadoVerificacao = icEstadoVerificacao;
		this.icUltimaHierarquia = icUltimaHierarquia;
	}
	
	@Id
	@Column(name = "nu_verificacao_contrato")
	public Integer getNuVerificacaoContrato() {
		return nuVerificacaoContrato;
	}
	
	public void setNuVerificacaoContrato(Integer nuVerificacaoContrato) {
		this.nuVerificacaoContrato = nuVerificacaoContrato;
	}
	
	@Column(name = "nu_contrato")
	public int getNuContrato() {
		return nuContrato;
	}
	
	public void setNuContrato(int nuContrato) {
		this.nuContrato = nuContrato;
	}
	
	@Column(name = "nu_servico_verificacao_produto")
	public int getNuServicoVerificacaoProduto() {
		return nuServicoVerificacaoProduto;
	}
	
	public void setNuServicoVerificacaoProduto(int nuServicoVerificacaoProduto) {
		this.nuServicoVerificacaoProduto = nuServicoVerificacaoProduto;
	}
	
	@Column(name = "nu_unidade_rspnl_verificacao")
	public Short getNuUnidadeResponsavelVerificacao() {
		return nuUnidadeResponsavelVerificacao;
	}
	
	public void setNuUnidadeResponsavelVerificacao(Short nuUnidadeResponsavelVerificacao) {
		this.nuUnidadeResponsavelVerificacao = nuUnidadeResponsavelVerificacao;
	}
	
	@Column(name = "nu_natural_undde_rspnl_vrfccao")
	public int getNuNaturalUnidadeResponsavelVerificacao() {
		return nuNaturalUnidadeResponsavelVerificacao;
	}
	
	public void setNuNaturalUnidadeResponsavelVerificacao(int nuNaturalUnidadeResponsavelVerificacao) {
		this.nuNaturalUnidadeResponsavelVerificacao = nuNaturalUnidadeResponsavelVerificacao;
	}
	
	@Column(name = "dt_inclusao_verificacao")
	@Temporal(TemporalType.DATE)
	public Date getDtInclusaoVerificacao() {
		return dtInclusaoVerificacao;
	}
	
	public void setDtInclusaoVerificacao(Date dtInclusaoVerificacao) {
		this.dtInclusaoVerificacao = dtInclusaoVerificacao;
	}
	
	@Column(name = "dt_verificacao")
	@Temporal(TemporalType.DATE)
	public Date getDtVerificacao() {
		return dtVerificacao;
	}
	
	public void setDtVerificacao(Date dtVerificacao) {
		this.dtVerificacao = dtVerificacao;
	}
	
	@Column(name = "dt_limite_verificacao")
	@Temporal(TemporalType.DATE)
	public Date getDtLimiteVerificacao() {
		return dtLimiteVerificacao;
	}
	
	public void setDtLimiteVerificacao(Date dtLimiteVerificacao) {
		this.dtLimiteVerificacao = dtLimiteVerificacao;
	}
	
	@Column(name = "co_rspnl_verificacao")
	public String getCoResponsavelVerificacao() {
		return coResponsavelVerificacao;
	}
	
	public void setCoResponsavelVerificacao(String coResponsavelVerificacao) {
		this.coResponsavelVerificacao = coResponsavelVerificacao;
	}
	
	@Column(name = "ic_estado_verificacao")
	public String getIcEstadoVerificacao() {
		return icEstadoVerificacao;
	}
	
	public void setIcEstadoVerificacao(String icEstadoVerificacao) {
		this.icEstadoVerificacao = icEstadoVerificacao;
	}
	
	@Column(name = "ic_suspensa")
	public Boolean getIcSuspensa() {
		return icSuspensa;
	}
	
	public void setIcSuspensa(Boolean icSuspensa) {
		this.icSuspensa = icSuspensa;
	}
	
	@Column(name = "ic_ultima_hierarquia")
	public boolean getIcUltimaHierarquia() {
		return icUltimaHierarquia;
	}
	
	public void setIcUltimaHierarquia(boolean icUltimaHierarquia) {
		this.icUltimaHierarquia = icUltimaHierarquia;
	}
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "verificacaoContratoParecer")
	public VerificacaoContrato getVerificacaoContrato() {
		return verificacaoContrato;
	}
	
	public void setVerificacaoContrato(VerificacaoContrato verificacaoContrato) {
		this.verificacaoContrato = verificacaoContrato;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "verificacaoContrato")
	public List<ResultadoApontamentoProdutoParecer> getResultadoApontamentoProdutoParecerList() {
		return resultadoApontamentoProdutoParecerList;
	}
	
	public void setResultadoApontamentoProdutoParecerList(List<ResultadoApontamentoProdutoParecer> rsltoAptmntoPrdtoPrList) {
		this.resultadoApontamentoProdutoParecerList = rsltoAptmntoPrdtoPrList;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "verificacaoContratoParecer")
	public List<VerificacaoContratoObservacoesParecer> getVerificacaoContratoObservacoesParecerList() {
		return verificacaoContratoObservacoesParecerList;
	}
	
	public void setVerificacaoContratoObservacoesParecerList(List<VerificacaoContratoObservacoesParecer> vrfcoCntroObsroPrcrList) {
		this.verificacaoContratoObservacoesParecerList = vrfcoCntroObsroPrcrList;
	}
	
	@OneToMany(mappedBy = "verificacaoContratoPai")
	public List<VerificacaoContratoParecer> getVerificacaoContratoParecerList() {
		return verificacaoContratoParecerList;
	}
	
	public void setVerificacaoContratoParecerList(List<VerificacaoContratoParecer> vrfcoCntroPrcrList) {
		this.verificacaoContratoParecerList = vrfcoCntroPrcrList;
	}
	
	@JoinColumn(name = "nu_verificacao_contrato_pai", referencedColumnName = "nu_verificacao_contrato")
	@ManyToOne
	public VerificacaoContratoParecer getVerificacaoContratoPai() {
		return verificacaoContratoPai;
	}
	
	public void setVerificacaoContratoPai(VerificacaoContratoParecer nuVerificacaoContratoPai) {
		this.verificacaoContratoPai = nuVerificacaoContratoPai;
	}
	
	@OneToMany(mappedBy = "verificacaoContratoParecer")
	public List<Parecer> getParecerList() {
		return parecerList;
	}
	
	public void setParecerList(List<Parecer> parecerList) {
		this.parecerList = parecerList;
	}
	
	@Column(name = "nu_chklst_srvco_vrfco_produto")
	public Integer getNuChecklistServicoVerificacaoProduto() {
		return nuChecklistServicoVerificacaoProduto;
	}
	
	public void setNuChecklistServicoVerificacaoProduto(Integer nuChecklistServicoVerificacaoProduto) {
		this.nuChecklistServicoVerificacaoProduto = nuChecklistServicoVerificacaoProduto;
	}
}