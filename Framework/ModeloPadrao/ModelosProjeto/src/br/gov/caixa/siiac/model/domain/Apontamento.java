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
@Table(name = "iactb008_apontamento_checklist", schema = "iacsm001")
@TrailClass(fonte = "APONTAMENTO")
public class Apontamento implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer nuApontamentoChecklist;
	private String noApontamentoChecklist;
	private Boolean icAtivo = true;
	private List<ApontamentoChecklistProduto> apontamentoChecklistProduto = new ArrayList<ApontamentoChecklistProduto>();
	
	public Apontamento() {
	}
	
	public Apontamento(Integer numero, String nome, Boolean icAtivo) {
		this.nuApontamentoChecklist = numero;
		this.noApontamentoChecklist = nome;
		this.icAtivo = icAtivo;
	}
	
	@Id
	@Column(name = "nu_aptmnto_checklist", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq009_apontamento_checklist")
	@SequenceGenerator(name = "iacsq009_apontamento_checklist", sequenceName = "iacsm001.iacsq009_apontamento_checklist")
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public Integer getNuApontamentoChecklist() {
		return nuApontamentoChecklist;
	}
	
	public void setNuApontamentoChecklist(Integer numero) {
		this.nuApontamentoChecklist = numero;
	}
	
	@Column(name = "no_aptmnto_checklist", nullable = false, length = 70)
	@TrailLog
	public String getNoApontamentoChecklist() {
		return noApontamentoChecklist;
	}
	
	public void setNoApontamentoChecklist(String nome) {
		this.noApontamentoChecklist = nome;
	}
	
	@Column(name = "ic_ativo", nullable = false)
	@TrailLog
	public Boolean getIcAtivo() {
		return icAtivo;
	}
	
	public void setIcAtivo(Boolean icAtivo) {
		this.icAtivo = icAtivo;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "apontamento")
	public List<ApontamentoChecklistProduto> getApontamentoChecklistProduto() {
		return apontamentoChecklistProduto;
	}
	
	public void setApontamentoChecklistProduto(List<ApontamentoChecklistProduto> apontamentoChecklistProduto) {
		this.apontamentoChecklistProduto = apontamentoChecklistProduto;
	}
	
}
