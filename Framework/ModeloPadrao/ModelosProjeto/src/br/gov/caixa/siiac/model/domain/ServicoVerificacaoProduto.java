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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb003_servico_vrfco_produto", schema = "iacsm001")
@TrailClass(fonte = "PRODUTO_SERVICO_VERIFICACAO")
public class ServicoVerificacaoProduto implements java.io.Serializable {
	
	private static final long serialVersionUID = 8494498894981L;
	private Integer nuServicoVerificacaoProduto;
	private ServicoVerificacao servicoVerificacao;
	private Produto produto;
	private Boolean icContaAbertaLote;
	private Integer pzVerificacao;
	private Integer pzAvgestao;
	private Integer pzAvsuret;
	private Integer pzNotificacaoNivel1;
	private Integer pzNotificacaoNivel2;
	private Short pzNotificacaoUnidadeResponsavel;
	private Date dtInicioVinculacao;
	private Date dtFimVinculacao;
	private String icTipoAcaoServico;
	private Short pzNotificacaoNivel3;
	private List<VerificacaoContrato> verificacaoContratoList;
	private List<UnidadeNotificadaVerificacao> unidadeNotificadaVerificacaoList;
	private List<TemplateParecer> templateParecerList;
	private List<ChecklistServicoVerificacaoProduto> checklistServicoVerificacaoProdutoList;
	
	public ServicoVerificacaoProduto() {
	}
	
	@Id
	@Column(name = "nu_servico_verificacao_produto", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacSQ003_servico_vrfco_produto")
	@SequenceGenerator(name = "iacSQ003_servico_vrfco_produto", sequenceName = "iacsm001.iacSQ003_servico_vrfco_produto")
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public Integer getNuServicoVerificacaoProduto() {
		return this.nuServicoVerificacaoProduto;
	}
	
	public void setNuServicoVerificacaoProduto(Integer nuServicoVerificacaoProduto) {
		this.nuServicoVerificacaoProduto = nuServicoVerificacaoProduto;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nu_servico_verificacao", nullable = false)
	public ServicoVerificacao getServicoVerificacao() {
		return this.servicoVerificacao;
	}
	
	public void setServicoVerificacao(ServicoVerificacao servicoVerificacao) {
		this.servicoVerificacao = servicoVerificacao;
	}
	
	@Transient
	@TrailLog(deleteID = true, insertID = true, updateID = true, name="nu_servico_verificacao")
	public Integer getServicoVerificacaoTrilha(){
		if(servicoVerificacao != null){
			return servicoVerificacao.getNuServicoVerificacao();
		}
		return null;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "co_produto", nullable = false)
	public Produto getProduto() {
		return this.produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	@Transient
	@TrailLog(deleteID = true, insertID = true, updateID = true, name = "co_produto")
	public String getProdutoTrilha(){
		if(produto != null){
			return produto.getCoProduto();
		}
		return null;
	}
	
	@Column(name = "ic_conta_aberta_lote")
	@TrailLog
	public Boolean getIcContaAbertaLote() {
		return icContaAbertaLote;
	}
	
	public void setIcContaAbertaLote(Boolean icContaAbertaLote) {
		this.icContaAbertaLote = icContaAbertaLote;
	}
	
	@Column(name = "pz_verificacao")
	@TrailLog
	public Integer getPzVerificacao() {
		return pzVerificacao;
	}
	
	public void setPzVerificacao(Integer pzVerificacao) {
		this.pzVerificacao = pzVerificacao;
	}
	
	@Column(name = "pz_avgestao")
	@TrailLog
	public Integer getPzAvgestao() {
		return pzAvgestao;
	}
	
	public void setPzAvgestao(Integer pzAvgestao) {
		this.pzAvgestao = pzAvgestao;
	}
	
	@Column(name = "pz_avsuret")
	@TrailLog
	public Integer getPzAvsuret() {
		return pzAvsuret;
	}
	
	public void setPzAvsuret(Integer pzAvsuret) {
		this.pzAvsuret = pzAvsuret;
	}
	
	@Column(name = "pz_notificacao_nivel1")
	@TrailLog
	public Integer getPzNotificacaoNivel1() {
		return pzNotificacaoNivel1;
	}
	
	public void setPzNotificacaoNivel1(Integer pzNotificacaoNivel1) {
		this.pzNotificacaoNivel1 = pzNotificacaoNivel1;
	}
	
	@Column(name = "pz_notificacao_nivel2")
	@TrailLog
	public Integer getPzNotificacaoNivel2() {
		return pzNotificacaoNivel2;
	}
	
	public void setPzNotificacaoNivel2(Integer pzNotificacaoNivel2) {
		this.pzNotificacaoNivel2 = pzNotificacaoNivel2;
	}
	
	@Column(name = "dt_inicio_vinculacao", length = 13, nullable = false)
	@TrailLog
	public Date getDtInicioVinculacao() {
		return dtInicioVinculacao;
	}
	
	public void setDtInicioVinculacao(Date dtInicioVinculacao) {
		this.dtInicioVinculacao = dtInicioVinculacao;
	}
	
	@Column(name = "dt_fim_vinculacao", length = 13)
	@TrailLog
	public Date getDtFimVinculacao() {
		return dtFimVinculacao;
	}
	
	public void setDtFimVinculacao(Date dtFimVinculacao) {
		this.dtFimVinculacao = dtFimVinculacao;
	}
	
	@Column(name = "ic_tipo_acao_servico")
	@TrailLog
	public String getIcTipoAcaoServico() {
		return icTipoAcaoServico;
	}
	
	public void setIcTipoAcaoServico(String icTipoAcaoServico) {
		this.icTipoAcaoServico = icTipoAcaoServico;
	}
	
	@Column(name = "pz_notificacao_nivel3")
	@TrailLog
	public Short getPzNotificacaoNivel3() {
		return pzNotificacaoNivel3;
	}
	
	public void setPzNotificacaoNivel3(Short pzNotificacaoNivel3) {
		this.pzNotificacaoNivel3 = pzNotificacaoNivel3;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "servicoVerificacaoProduto")
	public List<VerificacaoContrato> getVerificacaoContratoList() {
		return verificacaoContratoList;
	}
	
	public void setVerificacaoContratoList(List<VerificacaoContrato> verificacaoContratoList) {
		this.verificacaoContratoList = verificacaoContratoList;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "servicoVerificacaoProduto")
	public List<UnidadeNotificadaVerificacao> getUnidadeNotificadaVerificacaoList() {
		return unidadeNotificadaVerificacaoList;
	}
	
	public void setUnidadeNotificadaVerificacaoList(List<UnidadeNotificadaVerificacao> unddeNtfdaVrfcoList) {
		this.unidadeNotificadaVerificacaoList = unddeNtfdaVrfcoList;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "servicoVerificacaoProduto")
	public List<TemplateParecer> getTemplateParecerList() {
		return templateParecerList;
	}
	
	public void setTemplateParecerList(List<TemplateParecer> templateParecerList) {
		this.templateParecerList = templateParecerList;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "servicoVerificacaoProduto")
	public List<ChecklistServicoVerificacaoProduto> getChecklistServicoVerificacaoProdutoList() {
		return checklistServicoVerificacaoProdutoList;
	}
	
	public void setChecklistServicoVerificacaoProdutoList(List<ChecklistServicoVerificacaoProduto> chklstSrvcoProdutoList) {
		this.checklistServicoVerificacaoProdutoList = chklstSrvcoProdutoList;
	}

	@Column(name = "pz_notificacao_undde_rspnsl", nullable = true)
	@TrailLog
	public Short getPzNotificacaoUnidadeResponsavel() {
		return pzNotificacaoUnidadeResponsavel;
	}

	public void setPzNotificacaoUnidadeResponsavel(
			Short pzNotificacaoUnidadeResponsavel) {
		this.pzNotificacaoUnidadeResponsavel = pzNotificacaoUnidadeResponsavel;
	}
	
}
