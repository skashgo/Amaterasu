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

import java.util.ArrayList;
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
@Table(name = "iactb012_aptmnto_chklst_produto", schema = "iacsm001")
@TrailClass(fonte = "CHECKLIST")
public class ApontamentoChecklistProduto implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer nuApontamentoChecklistProduto;
	private Apontamento apontamento;
	private String descricao;
	private Integer numeroDeOrdem;
	private Boolean icNaoAplica;
	private ItemVerificacaoChecklistProduto itemVerificacaoChecklistProduto;
	private List<RestricaoApontamento> restricaoApontamento = new ArrayList<RestricaoApontamento>();
	private List<ResultadoApontamentoProduto> resultadoApontamentoProdutoList = new ArrayList<ResultadoApontamentoProduto>();
	
	public void setNuApontamentoChecklistProduto(Integer numero) {
		this.nuApontamentoChecklistProduto = numero;
	}
	
	@Id
	@Column(name = "nu_aptmnto_chklst_produto", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq014_aptmnto_chklst_produto")
	@SequenceGenerator(name = "iacsq014_aptmnto_chklst_produto", sequenceName = "iacsm001.iacsq014_aptmnto_chklst_produto")
	@TrailLog(insertID = true, updateID = true, deleteID = true)
	public Integer getNuApontamentoChecklistProduto() {
		return this.nuApontamentoChecklistProduto;
	}
	
	public void setItemVerificacaoChecklistProduto(ItemVerificacaoChecklistProduto itemVerificacaoChecklistProduto) {
		this.itemVerificacaoChecklistProduto = itemVerificacaoChecklistProduto;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nu_item_chklst_srvco_vrfco_prdt")
	public ItemVerificacaoChecklistProduto getItemVerificacaoChecklistProduto() {
		return this.itemVerificacaoChecklistProduto;
	}
	
	public void setApontamento(Apontamento apontamento) {
		this.apontamento = apontamento;
	}
	
	@Transient
	@TrailLog(name = "nu_item_chklst_srvco_vrfco_prdt")
	public Integer getItemVerificacaoChecklistProdutoTrilha(){
		if (itemVerificacaoChecklistProduto != null) {
			return itemVerificacaoChecklistProduto.getNuItemVerificacaoChecklistProduto();
		}
		return null;
	}
	
	@ManyToOne
	@JoinColumn(name = "nu_aptmnto_checklist")
	public Apontamento getApontamento() {
		return this.apontamento;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Transient
	@TrailLog(name = "nu_aptmnto_checklist")
	public Integer getApontamentoTrilha(){
		if (apontamento != null) {
			return apontamento.getNuApontamentoChecklist();
		}
		return null;
	}
	
	
	@Column(name = "de_aptmnto_chklst_produto")
	@TrailLog
	public String getDescricao() {
		return this.descricao;
	}
	
	public void setNumeroDeOrdem(Integer numeroDeOrdem) {
		this.numeroDeOrdem = numeroDeOrdem;
	}
	
	@Column(name = "nu_ordem_aptmnto_chklst")
	@TrailLog
	public Integer getNumeroDeOrdem() {
		return this.numeroDeOrdem;
	}
	
	public void setIcNaoAplica(Boolean icNaoAplica) {
		this.icNaoAplica = icNaoAplica;
	}
	
	@Column(name = "ic_nao_aplica")
	@TrailLog
	public Boolean getIcNaoAplica() {
		return this.icNaoAplica;
	}
	
	@OneToMany(mappedBy = "apontamentoChecklistProduto", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.MERGE })
	public List<RestricaoApontamento> getRestricaoApontamento() {
		return restricaoApontamento;
	}
	
	public void setRestricaoApontamento(List<RestricaoApontamento> restricaoApontamento) {
		this.restricaoApontamento = restricaoApontamento;
	}
	
	@OneToMany(mappedBy = "apontamentoChecklistProduto")
	public List<ResultadoApontamentoProduto> getResultadoApontamentoProdutoList() {
		return resultadoApontamentoProdutoList;
	}
	
	public void setResultadoApontamentoProdutoList(List<ResultadoApontamentoProduto> rsltoAptmntoProdutoList) {
		this.resultadoApontamentoProdutoList = rsltoAptmntoProdutoList;
	}
	
	@Transient
	@TrailLog(insertID = true, updateID = true, deleteID = true, name = "nu_chklst_srvco_vrfco_produto")
	public Integer getChecklistTrilha(){
		if (itemVerificacaoChecklistProduto != null) {
			if (itemVerificacaoChecklistProduto.getBlocoChecklistProduto() != null) {
				if (itemVerificacaoChecklistProduto.getBlocoChecklistProduto().getChecklistServicoVerificacaoProduto() != null) {
					return itemVerificacaoChecklistProduto.getBlocoChecklistProduto().getChecklistServicoVerificacaoProduto().getNuChecklistServicoVerificacaoProduto();
				}
			}
		}
		return null;
	}
}
