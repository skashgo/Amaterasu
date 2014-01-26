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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb018_verificacao_contrato", schema = "iacsm001")
@TrailClass(fonte = "VERIFICACAO")
public class VerificacaoContrato implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer nuVerificacaoContrato;
	private Short nuUnidadeResponsavelVerificacao;
	private String coRspnlVerificacao;
	private String icEstadoVerificacao;
	private ChecklistServicoVerificacaoProduto checklist;
	private Contrato contrato;
	private ServicoVerificacaoProduto servicoVerificacaoProduto;
	private List<UnidadeNotificadaVerificacao> UnidadeNotificadaVerificacaoList;
	private List<ResultadoApontamentoProduto> ResultadoApontamentoProdutoList;
	private int nuNaturalUnidadeResponsavelVerificacao;
	private Date dtInclusaoVerificacao;
	private Date dtVerificacao;
	private Date dtLimiteVerificacao;
	private Boolean icSuspensa;
	private Integer nuVerificacaoContratoPai;
	private boolean icUltimaHierarquia = true;
	private VerificacaoContratoParecer verificacaoContratoParecer;
	private List<VerificacaoContratoObservacoes> verificacaoContratoObservacoesList;
	private Character icModoCriacao;
	
	public VerificacaoContrato() {
	}
	
	public VerificacaoContrato(Integer nuVerificacaoContrato) {
		this.nuVerificacaoContrato = nuVerificacaoContrato;
	}
	
	public VerificacaoContrato(Integer nuVerificacaoContrato, Short nuUnidadeRspnlVerificacao, int nuNaturalUnddeRspnlVrfccao) {
		this.nuVerificacaoContrato = nuVerificacaoContrato;
		this.nuUnidadeResponsavelVerificacao = nuUnidadeRspnlVerificacao;
		this.nuNaturalUnidadeResponsavelVerificacao = nuNaturalUnddeRspnlVrfccao;
	}
	
	@Id
	@Column(name = "nu_verificacao_contrato", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq020_verificacao_contrato")
	@SequenceGenerator(name = "iacsq020_verificacao_contrato", sequenceName = "iacsm001.iacsq020_verificacao_contrato")
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public Integer getNuVerificacaoContrato() {
		return nuVerificacaoContrato;
	}
	
	public void setNuVerificacaoContrato(Integer nuVerificacaoContrato) {
		this.nuVerificacaoContrato = nuVerificacaoContrato;
	}
	
	@Column(name = "nu_unidade_rspnl_verificacao", nullable = false)
	@TrailLog
	public Short getNuUnidadeResponsavelVerificacao() {
		return nuUnidadeResponsavelVerificacao;
	}
	
	public void setNuUnidadeResponsavelVerificacao(Short nuUnidadeRspnlVerificacao) {
		this.nuUnidadeResponsavelVerificacao = nuUnidadeRspnlVerificacao;
	}
	
	@Column(name = "dt_verificacao")
	@Temporal(TemporalType.DATE)
	@TrailLog
	public Date getDtVerificacao() {
		return dtVerificacao;
	}
	
	public void setDtVerificacao(Date dtVerificacao) {
		this.dtVerificacao = dtVerificacao;
	}
	
	@Column(name = "co_rspnl_verificacao", length = 7)
	@TrailLog
	public String getCoRspnlVerificacao() {
		return coRspnlVerificacao;
	}
	
	public void setCoRspnlVerificacao(String coRspnlVerificacao) {
		this.coRspnlVerificacao = coRspnlVerificacao;
	}
	
	@Column(name = "ic_estado_verificacao", length = 2, nullable = false)
	@TrailLog
	public String getIcEstadoVerificacao() {
		return icEstadoVerificacao;
	}
	
	public void setIcEstadoVerificacao(String icEstadoVerificacao) {
		this.icEstadoVerificacao = icEstadoVerificacao;
	}
	
	@JoinColumn(name = "nu_chklst_srvco_vrfco_produto", referencedColumnName = "nu_chklst_srvco_vrfco_produto", nullable = true)
	@ManyToOne
	public ChecklistServicoVerificacaoProduto getChecklist() {
		return checklist;
	}
	
	public void setChecklist(ChecklistServicoVerificacaoProduto checklist) {
		this.checklist = checklist;
	}
	
	@Transient
	@TrailLog(name = "nu_chklst_srvco_vrfco_produto")
	public Integer getChecklistTrilha(){
		if(checklist != null){
			return checklist.getNuChecklistServicoVerificacaoProduto();
		}
		return null;
	}
	
	@JoinColumn(name = "nu_contrato", referencedColumnName = "nu_contrato", nullable = false)
	@ManyToOne
	public Contrato getContrato() {
		return contrato;
	}
	
	public void setContrato(Contrato nuContrato) {
		this.contrato = nuContrato;
	}
	
	@Transient
	@TrailLog(deleteID = true, insertID = true, updateID = true, name = "nu_contrato")
	public Integer getContratoTrilha(){
		if(contrato != null){
			return contrato.getNuContrato();
		}
		return null;
	}
	
	
	@JoinColumn(name = "nu_servico_verificacao_produto", referencedColumnName = "nu_servico_verificacao_produto", nullable = false)
	@ManyToOne
	public ServicoVerificacaoProduto getServicoVerificacaoProduto() {
		return servicoVerificacaoProduto;
	}
	
	public void setServicoVerificacaoProduto(ServicoVerificacaoProduto nuServicoVerificacaoProduto) {
		this.servicoVerificacaoProduto = nuServicoVerificacaoProduto;
	}
	
	@Transient
	@TrailLog(name = "nu_servico_verificacao_produto")
	public Integer getServicoVerificacaoProdutoTrilha(){
		if(servicoVerificacaoProduto != null){
			return servicoVerificacaoProduto.getNuServicoVerificacaoProduto();
		}
		return null;
	}	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "verificacaoContrato")
	public List<UnidadeNotificadaVerificacao> getUnidadeNotificadaVerificacaoList() {
		return UnidadeNotificadaVerificacaoList;
	}
	
	public void setUnidadeNotificadaVerificacaoList(List<UnidadeNotificadaVerificacao> unddeNtfdaVrfcoList) {
		this.UnidadeNotificadaVerificacaoList = unddeNtfdaVrfcoList;
	}
		
	@OneToMany(mappedBy = "verificacaoContrato", cascade = CascadeType.REMOVE)
	public List<ResultadoApontamentoProduto> getResultadoApontamentoProdutoList() {
		return ResultadoApontamentoProdutoList;
	}
	
	public void setResultadoApontamentoProdutoList(List<ResultadoApontamentoProduto> rsltoAptmntoProdutoList) {
		this.ResultadoApontamentoProdutoList = rsltoAptmntoProdutoList;
	}
	
	@Column(name = "nu_natural_undde_rspnl_vrfccao")
	@TrailLog
	public int getNuNaturalUnidadeResponsavelVerificacao() {
		return nuNaturalUnidadeResponsavelVerificacao;
	}
	
	public void setNuNaturalUnidadeResponsavelVerificacao(int nuNaturalUnddeRspnlVrfccao) {
		this.nuNaturalUnidadeResponsavelVerificacao = nuNaturalUnddeRspnlVrfccao;
	}
	
	@Column(name = "dt_inclusao_verificacao")
	@Temporal(TemporalType.DATE)
	@TrailLog
	public Date getDtInclusaoVerificacao() {
		return dtInclusaoVerificacao;
	}
	
	public void setDtInclusaoVerificacao(Date dtInclusaoVerificacao) {
		this.dtInclusaoVerificacao = dtInclusaoVerificacao;
	}
	
	@Column(name = "dt_limite_verificacao")
	@Temporal(TemporalType.DATE)
	@TrailLog
	public Date getDtLimiteVerificacao() {
		return dtLimiteVerificacao;
	}
	
	public void setDtLimiteVerificacao(Date dtLimiteVerificacao) {
		this.dtLimiteVerificacao = dtLimiteVerificacao;
	}
	
	@Column(name = "ic_suspensa")
	@TrailLog
	public Boolean getIcSuspensa() {
		return icSuspensa;
	}
	
	public void setIcSuspensa(Boolean icSuspensa) {
		this.icSuspensa = icSuspensa;
	}
	
	@Column(name = "nu_verificacao_contrato_pai")
	@TrailLog
	public Integer getNuVerificacaoContratoPai() {
		return nuVerificacaoContratoPai;
	}
	
	public void setNuVerificacaoContratoPai(Integer nuVerificacaoContratoPai) {
		this.nuVerificacaoContratoPai = nuVerificacaoContratoPai;
	}
	
	@Column(name = "ic_ultima_hierarquia", nullable = false)
	@TrailLog
	public boolean getIcUltimaHierarquia() {
		return icUltimaHierarquia;
	}
	
	public void setIcUltimaHierarquia(boolean icUltimaHierarquia) {
		this.icUltimaHierarquia = icUltimaHierarquia;
	}
	
	@JoinColumn(name = "nu_verificacao_contrato", referencedColumnName = "nu_verificacao_contrato")
	@OneToOne
	public VerificacaoContratoParecer getVerificacaoContratoParecer() {
		return verificacaoContratoParecer;
	}
	
	public void setVerificacaoContratoParecer(VerificacaoContratoParecer verificacaoContratoParecer) {
		this.verificacaoContratoParecer = verificacaoContratoParecer;
	}
	
	@Transient
	@TrailLog(name = "nu_verificacao_contrato")
	public Integer getVerificacaoContratoParecerTrilha(){
		if(verificacaoContratoParecer != null){
			return verificacaoContratoParecer.getNuVerificacaoContrato();
		}
		return null;
	}
	
	@OneToMany(mappedBy = "verificacaoContrato", cascade = CascadeType.REMOVE)
	public List<VerificacaoContratoObservacoes> getVerificacaoContratoObservacoesList() {
		return verificacaoContratoObservacoesList;
	}
	
	public void setVerificacaoContratoObservacoesList(List<VerificacaoContratoObservacoes> verificacaoContratoObservacoesList) {
		this.verificacaoContratoObservacoesList = verificacaoContratoObservacoesList;
	}

	@Column(name = "ic_modo_criacao")
	@TrailLog
	public Character getIcModoCriacao() {
		return icModoCriacao;
	}

	public void setIcModoCriacao(Character icModoCriacao) {
		this.icModoCriacao = icModoCriacao;
	}
	
}
