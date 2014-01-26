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
@Table(name = "iactb011_item_vrfco_cklst_prdto", schema = "iacsm001")
@TrailClass(fonte = "CHECKLIST")
public class ItemVerificacaoChecklistProduto implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer nuItemVerificacaoChecklistProduto;
	private ItemVerificacao itemVerificacao;
	private BlocoChecklistProduto blocoChecklistProduto;
	private String deItemVerificacaoChecklistProduto;
	private Integer numeroDeOrdem;
	private List<ApontamentoChecklistProduto> apontamentoChecklistProdutoList = new ArrayList<ApontamentoChecklistProduto>();
	private Boolean icDesabilitaItem;
	private List<VerificacaoContratoObservacoes> verificacaoContratoObservacoesList = new ArrayList<VerificacaoContratoObservacoes>();
	
	public void setNuItemVerificacaoChecklistProduto(Integer numero) {
		this.nuItemVerificacaoChecklistProduto = numero;
	}
	
	@Id
	@Column(name = "nu_item_chklst_srvco_vrfco_prdt", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq013_item_vrfco_cklst_prdto")
	@SequenceGenerator(name = "iacsq013_item_vrfco_cklst_prdto", sequenceName = "iacsm001.iacsq013_item_vrfco_cklst_prdto")
	@TrailLog(insertID = true, updateID = true, deleteID = true)
	public Integer getNuItemVerificacaoChecklistProduto() {
		return this.nuItemVerificacaoChecklistProduto;
	}
	
	public void setItemVerificacao(ItemVerificacao itemVerificacao) {
		this.itemVerificacao = itemVerificacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nu_item_vrfco_checklist")
	public ItemVerificacao getItemVerificacao() {
		return this.itemVerificacao;
	}
	
	public void setBlocoChecklistProduto(BlocoChecklistProduto blocoChecklistProduto) {
		this.blocoChecklistProduto = blocoChecklistProduto;
	}
	
	@Transient
	@TrailLog(name = "nu_item_vrfco_checklist")
	public Integer getItemVerficacaoTrilha(){
		if (itemVerificacao != null) {
			return itemVerificacao.getNuItemVerificacao();
		}
		return null;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nu_blco_chklst_srvco_vrfco_prdt")
	public BlocoChecklistProduto getBlocoChecklistProduto() {
		return this.blocoChecklistProduto;
	}
	
	public void setDeItemVerificacaoChecklistProduto(String descricao) {
		this.deItemVerificacaoChecklistProduto = descricao;
	}
	
	@Transient
	@TrailLog(name = "nu_blco_chklst_srvco_vrfco_prdt")
	public Integer getBlocoChecklistProdutoTrilha(){
		if (blocoChecklistProduto != null) {
			return blocoChecklistProduto.getNuBlocoChecklistProduto();
		}
		return null;
	}
	
	@Column(name = "de_item_vrfco_chklst_produto")
	@TrailLog
	public String getDeItemVerificacaoChecklistProduto() {
		return this.deItemVerificacaoChecklistProduto;
	}
	
	public void setNumeroDeOrdem(Integer numeroDeOrdem) {
		this.numeroDeOrdem = numeroDeOrdem;
	}
	
	@Column(name = "nu_ordem_item_vrfco_chklst")
	@TrailLog
	public Integer getNumeroDeOrdem() {
		return this.numeroDeOrdem;
	}
	
	@OneToMany(mappedBy = "itemVerificacaoChecklistProduto", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.MERGE })
	public List<ApontamentoChecklistProduto> getApontamentoChecklistProdutoList() {
		return apontamentoChecklistProdutoList;
	}
	
	public void setApontamentoChecklistProdutoList(List<ApontamentoChecklistProduto> apontamentoChecklistProduto) {
		this.apontamentoChecklistProdutoList = apontamentoChecklistProduto;
	}
	
	@Column(name = "ic_desabilita_item")
	@TrailLog
	public Boolean getIcDesabilitaItem() {
		return icDesabilitaItem;
	}
	
	public void setIcDesabilitaItem(Boolean icDesabilitaItem) {
		this.icDesabilitaItem = icDesabilitaItem;
	}
	
	@OneToMany(mappedBy = "itemVerificacaoChecklistProduto")
	public List<VerificacaoContratoObservacoes> getVerificacaoContratoObservacoesList() {
		return verificacaoContratoObservacoesList;
	}
	
	public void setVerificacaoContratoObservacoesList(List<VerificacaoContratoObservacoes> vrfcoCntroObsroList) {
		this.verificacaoContratoObservacoesList = vrfcoCntroObsroList;
	}
	
	@Transient
	@TrailLog(insertID = true, updateID = true, deleteID = true, name = "nu_chklst_srvco_vrfco_produto")
	public Integer getChecklistTrilha(){
		if (blocoChecklistProduto != null) {
			if (blocoChecklistProduto.getChecklistServicoVerificacaoProduto() != null) {
				return blocoChecklistProduto.getChecklistServicoVerificacaoProduto().getNuChecklistServicoVerificacaoProduto();
			}
		}
		return null;
	}
}
