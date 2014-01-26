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
@Table(name = "iactb010_bloco_chklst_produto", schema = "iacsm001")
@TrailClass(fonte = "CHECKLIST")
public class BlocoChecklistProduto implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer nuBlocoChecklistProduto;
	private ChecklistServicoVerificacaoProduto checklistServicoVerificacaoProduto;
	private BlocoChecklist blocoChecklist;
	private String descricao;
	private Integer numeroDeOrdem;
	private Boolean icObrigatorio;
	private Boolean icDesabilita;
	private boolean icPrejudicado = false;
	private List<ItemVerificacaoChecklistProduto> itemVerificacaoChecklistProdutoList = new ArrayList<ItemVerificacaoChecklistProduto>();
	private List<VerificacaoContratoObservacoes> VerificacaoContratoObservacoesList = new ArrayList<VerificacaoContratoObservacoes>();
	
	public void setNuBlocoChecklistProduto(Integer numero) {
		this.nuBlocoChecklistProduto = numero;
	}
	
	@Id
	@Column(name = "nu_blco_chklst_srvco_vrfco_prdt", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq012_bloco_chklst_produto")
	@SequenceGenerator(name = "iacsq012_bloco_chklst_produto", sequenceName = "iacsm001.iacsq012_bloco_chklst_produto")
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public Integer getNuBlocoChecklistProduto() {
		return this.nuBlocoChecklistProduto;
	}
	
	public void setChecklistServicoVerificacaoProduto(ChecklistServicoVerificacaoProduto checklistServicoVerificacaoProduto) {
		this.checklistServicoVerificacaoProduto = checklistServicoVerificacaoProduto;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nu_chklst_srvco_vrfco_produto")
	public ChecklistServicoVerificacaoProduto getChecklistServicoVerificacaoProduto() {
		return this.checklistServicoVerificacaoProduto;
	}
	
	public void setBlocoChecklist(BlocoChecklist blocoChecklist) {
		this.blocoChecklist = blocoChecklist;
	}
	
	@Transient
	@TrailLog(insertID = true, deleteID = true, updateID = true, name = "nu_chklst_srvco_vrfco_produto")
	public Integer getChecklistServicoVerificacaoProdutoTrilha(){
		if(checklistServicoVerificacaoProduto != null){
			return checklistServicoVerificacaoProduto.getNuChecklistServicoVerificacaoProduto();
		}
		return null;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nu_bloco_checklist")
	public BlocoChecklist getBlocoChecklist() {
		return this.blocoChecklist;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Transient
	@TrailLog(name="nu_bloco_checklist")
	public Integer getBlocoChecklistTrilha(){
		if (blocoChecklist != null) {
			return blocoChecklist.getNuBlocoChecklist();
		}
		return null;
	}
	
	@Column(name = "de_bloco_chklst_produto")
	@TrailLog
	public String getDescricao() {
		return this.descricao;
	}
	
	public void setNumeroDeOrdem(Integer numeroDeOrdem) {
		this.numeroDeOrdem = numeroDeOrdem;
	}
	
	@Column(name = "nu_ordem_bloco_chklst")
	@TrailLog
	public Integer getNumeroDeOrdem() {
		return this.numeroDeOrdem;
	}
	
	public void setIcObrigatorio(Boolean icObrigatorio) {
		this.icObrigatorio = icObrigatorio;
	}
	
	@Column(name = "ic_bloco_obrigatorio")
	@TrailLog
	public Boolean getIcObrigatorio() {
		return this.icObrigatorio;
	}
	
	public void setIcDesabilita(Boolean icDesabilita) {
		this.icDesabilita = icDesabilita;
	}
	
	@Column(name = "ic_desabilita_bloco")
	@TrailLog
	public Boolean getIcDesabilita() {
		return this.icDesabilita;
	}
	
	@OneToMany(mappedBy = "blocoChecklistProduto", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.MERGE })
	public List<ItemVerificacaoChecklistProduto> getItemVerificacaoChecklistProdutoList() {
		return itemVerificacaoChecklistProdutoList;
	}
	
	public void setItemVerificacaoChecklistProdutoList(List<ItemVerificacaoChecklistProduto> itemVerificacaoChecklistProdutoList) {
		this.itemVerificacaoChecklistProdutoList = itemVerificacaoChecklistProdutoList;
	}
	
	@OneToMany(mappedBy = "blocoChecklistProduto")
	public List<VerificacaoContratoObservacoes> getVerificacaoContratoObservacoesList() {
		return VerificacaoContratoObservacoesList;
	}
	
	public void setVerificacaoContratoObservacoesList(List<VerificacaoContratoObservacoes> vrfcoCntroObsroList) {
		this.VerificacaoContratoObservacoesList = vrfcoCntroObsroList;
	}

	@Column(name="ic_bloco_analise_prejudicada", nullable=false)
	@TrailLog
	public boolean getIcPrejudicado() {
		return icPrejudicado;
	}

	public void setIcPrejudicado(boolean icPrejudicado) {
		this.icPrejudicado = icPrejudicado;
	}
}
