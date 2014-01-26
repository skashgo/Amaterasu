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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb001_produto", schema = "iacsm001")
@TrailClass(fonte = "PRODUTO")
public class Produto implements java.io.Serializable {
	
	private static final long serialVersionUID = 29340948230L;
	private String coProduto;
	private String noProduto;
	private Short nuOperacao;
	private Short nuModalidade;
	private CategoriaProduto categoriaProduto;
	private String sgSistemaOrigem;
	private BigDecimal vrLimiteVerificacao;
	private boolean icAtivo;
	private String icPfPj;
	private String noOperacao;
	private String noModalidade;
	private List<ProdutoUsuario> produtosUsuarioList = new ArrayList<ProdutoUsuario>();
	private List<Contrato> contratoList = new ArrayList<Contrato>();
	private List<ServicoVerificacaoProduto> servicoVerificacaoProdutoList = new ArrayList<ServicoVerificacaoProduto>();
	private List<PreferenciasUsuario> preferenciasUsuarioList = new ArrayList<PreferenciasUsuario>();
	
	public Produto() {
	}
	
	public Produto(String coProduto, String noProduto, Short nuOperacao, CategoriaProduto categoriaProduto, String sgSistemaOrigem, boolean icAtivo) {
		this.coProduto = coProduto;
		this.noProduto = noProduto;
		this.nuOperacao = nuOperacao;
		this.categoriaProduto = categoriaProduto;
		this.sgSistemaOrigem = sgSistemaOrigem;
		this.icAtivo = icAtivo;
	}
	
	public Produto(String coProduto, String noProduto, Short nuOperacao, Short nuModalidade, CategoriaProduto categoriaProduto, String sgSistemaOrigem, BigDecimal vrLimiteVerificacao, boolean icAtivo, String icPfPj) {
		this.coProduto = coProduto;
		this.noProduto = noProduto;
		this.nuOperacao = nuOperacao;
		this.nuModalidade = nuModalidade;
		this.categoriaProduto = categoriaProduto;
		this.sgSistemaOrigem = sgSistemaOrigem;
		this.vrLimiteVerificacao = vrLimiteVerificacao;
		this.icAtivo = icAtivo;
		this.icPfPj = icPfPj;
	}
	
	@Id
	@Column(name = "co_produto", unique = true, nullable = false, length = 7)
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public String getCoProduto() {
		return this.coProduto;
	}
	
	public void setCoProduto(String coProduto) {
		this.coProduto = coProduto;
	}
	
	@Column(name = "no_produto", nullable = false, length = 70)
	@TrailLog
	public String getNoProduto() {
		return this.noProduto;
	}
	
	public void setNoProduto(String noProduto) {
		this.noProduto = noProduto;
	}
	
	@Column(name = "nu_operacao", nullable = false)
	@TrailLog
	public Short getNuOperacao() {
		return this.nuOperacao;
	}
	
	public void setNuOperacao(Short nuOperacao) {
		this.nuOperacao = nuOperacao;
	}
	
	@Column(name = "nu_modalidade")
	@TrailLog
	public Short getNuModalidade() {
		return this.nuModalidade;
	}
	
	public void setNuModalidade(Short nuModalidade) {
		this.nuModalidade = nuModalidade;
	}
	
	@ManyToOne
	@JoinColumn(name = "nu_categoria_produto", nullable = false)
	public CategoriaProduto getCategoriaProduto() {
		return this.categoriaProduto;
	}
	
	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}
	
	@Transient
	@TrailLog(name = "nu_categoria_produto")
	public Integer getCategoriaProdutoTrilha(){
		if(categoriaProduto != null){
			return categoriaProduto.getNuCategoriaProduto();
		}
		return null;
	}
	
	@Column(name = "sg_sistema_origem", nullable = false, length = 8)
	@TrailLog
	public String getSgSistemaOrigem() {
		return this.sgSistemaOrigem;
	}
	
	public void setSgSistemaOrigem(String sgSistemaOrigem) {
		this.sgSistemaOrigem = sgSistemaOrigem;
	}
	
	@Column(name = "vr_limite_verificacao", precision = 16)
	@TrailLog
	public BigDecimal getVrLimiteVerificacao() {
		return this.vrLimiteVerificacao;
	}
	
	public void setVrLimiteVerificacao(BigDecimal vrLimiteVerificacao) {
		this.vrLimiteVerificacao = vrLimiteVerificacao;
	}
	
	@Column(name = "ic_ativo", nullable = false)
	@TrailLog
	public boolean isIcAtivo() {
		return this.icAtivo;
	}
	
	public void setIcAtivo(boolean icAtivo) {
		this.icAtivo = icAtivo;
	}
	
	@Column(name = "ic_pf_pj", length = 2)
	@TrailLog
	public String getIcPfPj() {
		return this.icPfPj;
	}
	
	public void setIcPfPj(String icPfPj) {
		this.icPfPj = icPfPj;
	}
	
	@OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
	public List<ProdutoUsuario> getProdutosUsuarioList() {
		return this.produtosUsuarioList;
	}
	
	public void setProdutosUsuarioList(List<ProdutoUsuario> produtosUsuario) {
		this.produtosUsuarioList = produtosUsuario;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "produto")
	public List<Contrato> getContratoList() {
		return contratoList;
	}
	
	public void setContratoList(List<Contrato> contratos) {
		this.contratoList = contratos;
	}
	
	@OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
	public List<ServicoVerificacaoProduto> getServicoVerificacaoProdutoList() {
		return servicoVerificacaoProdutoList;
	}
	
	public void setServicoVerificacaoProdutoList(List<ServicoVerificacaoProduto> servicoVerificacaoProdutoList) {
		this.servicoVerificacaoProdutoList = servicoVerificacaoProdutoList;
	}
	
	@OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
	public List<PreferenciasUsuario> getPreferenciasUsuarioList() {
		return preferenciasUsuarioList;
	}
	
	public void setPreferenciasUsuarioList(List<PreferenciasUsuario> preferenciasUsuarioList) {
		this.preferenciasUsuarioList = preferenciasUsuarioList;
	}
	
	@Column(name = "no_operacao", length = 40)
	@TrailLog
	public String getNoOperacao() {
		return noOperacao;
	}

	public void setNoOperacao(String noOperacao) {
		this.noOperacao = noOperacao;
	}

	@Column(name = "no_modalidade", length = 29)
	@TrailLog
	public String getNoModalidade() {
		return noModalidade;
	}

	public void setNoModalidade(String noModalidade) {
		this.noModalidade = noModalidade;
	}

	/**
	 * Utilitárias para máscaras
	 */
	private static final int MASK_SIZE_MODALIDADE = 3;
	private static final int MASK_SIZE_OPERACAO = 4;
	
	@Transient
	public String getModalidadeFormatada() {
		return ConvertUtils.padZerosLeft(String.valueOf(nuModalidade), MASK_SIZE_MODALIDADE);
	}
	
	@Transient
	public String getOperacaoFormatada() {
		return ConvertUtils.padZerosLeft(String.valueOf(nuOperacao), MASK_SIZE_OPERACAO);
	}
	
	@Transient
	public String getCodigoFormatado() {
		return getOperacaoFormatada() + "-" + getModalidadeFormatada();
	}
}
