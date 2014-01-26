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
@Table(name = "iactb006_bloco_checklist", schema = "iacsm001")
@TrailClass(fonte = "BLOCO_VERIFICACAO")
public class BlocoChecklist implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer nuBlocoChecklist;
	private String noBlocoChecklist;
	private String noResumidoBlocoChecklist;
	private Boolean icAtivo = true;
	private List<BlocoChecklistProduto> blocoChecklistProdutoList = new ArrayList<BlocoChecklistProduto>();
	
	public BlocoChecklist() {
	}
	
	public BlocoChecklist(Integer numero, String nome, Boolean icAtivo) {
		this.nuBlocoChecklist = numero;
		this.noBlocoChecklist = nome;
		this.icAtivo = icAtivo;
	}
	
	@Id
	@Column(name = "nu_bloco_checklist", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq012_bloco_chklst_produto")
	@SequenceGenerator(name = "iacsq012_bloco_chklst_produto", sequenceName = "iacsm001.iacsq012_bloco_chklst_produto")
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public Integer getNuBlocoChecklist() {
		return nuBlocoChecklist;
	}
	
	public void setNuBlocoChecklist(Integer numero) {
		this.nuBlocoChecklist = numero;
	}
	
	@Column(name = "no_bloco_checklist", nullable = false, length = 70)
	@TrailLog
	public String getNoBlocoChecklist() {
		return noBlocoChecklist;
	}
	
	public void setNoBlocoChecklist(String nome) {
		this.noBlocoChecklist = nome;
	}
	
	@Column(name = "ic_ativo", nullable = false)
	@TrailLog
	public Boolean getIcAtivo() {
		return icAtivo;
	}
	
	public void setIcAtivo(Boolean icAtivo) {
		this.icAtivo = icAtivo;
	}
	
	@OneToMany(mappedBy = "blocoChecklist", fetch = FetchType.LAZY)
	public List<BlocoChecklistProduto> getBlocoChecklistProdutoList() {
		return blocoChecklistProdutoList;
	}
	
	public void setBlocoChecklistProdutoList(List<BlocoChecklistProduto> blocoChecklistProdutoList) {
		this.blocoChecklistProdutoList = blocoChecklistProdutoList;
	}

	@Column(name = "no_resumido_bloco_checklist", nullable = true)
	@TrailLog
	public String getNoResumidoBlocoChecklist() {
		return noResumidoBlocoChecklist;
	}

	public void setNoResumidoBlocoChecklist(String noResumidoBlocoChecklist) {
		this.noResumidoBlocoChecklist = noResumidoBlocoChecklist;
	}
	
	
}
