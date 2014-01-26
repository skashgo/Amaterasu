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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb007_item_verificacao", schema = "iacsm001")
@TrailClass(fonte = "ITEM_VERIFICACAO")
public class ItemVerificacao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer nuItemVerificacao;
	private String noItemVerificacao;
	private Boolean icAtivo = true;
	private Boolean icDataValidade = true;
	private String noResumidoItemVerificacao;
	private List<ItemVerificacaoChecklistProduto> itemVerificacaoChecklistProdutoList = new ArrayList<ItemVerificacaoChecklistProduto>();
	
	public ItemVerificacao() {
	}
	
	public ItemVerificacao(Integer numero) {
		this.nuItemVerificacao = numero;
	}
	
	public ItemVerificacao(Integer numero, String nome, Boolean icAtivo, Boolean icDataValidade) {
		this.nuItemVerificacao = numero;
		this.noItemVerificacao = nome;
		this.icAtivo = icAtivo;
		this.icDataValidade = icDataValidade;
	}
	
	@Id
	@Column(name = "nu_item_vrfco_checklist", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq008_item_verificacao")
	@SequenceGenerator(name = "iacsq008_item_verificacao", sequenceName = "iacsm001.iacsq008_item_verificacao")
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public Integer getNuItemVerificacao() {
		return nuItemVerificacao;
	}
	
	public void setNuItemVerificacao(Integer numero) {
		this.nuItemVerificacao = numero;
	}
	
	@Column(name = "no_item_vrfco_checklist", nullable = false)
	@TrailLog
	public String getNoItemVerificacao() {
		return noItemVerificacao;
	}
	
	public void setNoItemVerificacao(String nome) {
		this.noItemVerificacao = nome;
	}
	
	@Column(name = "ic_ativo", nullable = false)
	@TrailLog
	public Boolean getIcAtivo() {
		return icAtivo;
	}
	
	public void setIcAtivo(Boolean icAtivo) {
		this.icAtivo = icAtivo;
	}
	
	@Column(name = "ic_data_validade", nullable = false)
	@TrailLog
	public Boolean getIcDataValidade() {
		return icDataValidade;
	}
	
	public void setIcDataValidade(Boolean icDataValidade) {
		this.icDataValidade = icDataValidade;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "itemVerificacao")
	public List<ItemVerificacaoChecklistProduto> getItemVerificacaoChecklistProdutoList() {
		return itemVerificacaoChecklistProdutoList;
	}
	
	public void setItemVerificacaoChecklistProdutoList(List<ItemVerificacaoChecklistProduto> itemVerificacaoChecklistProduto) {
		this.itemVerificacaoChecklistProdutoList = itemVerificacaoChecklistProduto;
	}

	@Column(name = "no_rsmdo_item_vrfco_checklist", nullable = true)
	@TrailLog
	public String getNoResumidoItemVerificacao() {
		return noResumidoItemVerificacao;
	}

	public void setNoResumidoItemVerificacao(String noResumidoItemVerificacao) {
		this.noResumidoItemVerificacao = noResumidoItemVerificacao;
	}
}