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
@Table(name = "iactb009_restricao", schema = "iacsm001")
@TrailClass(fonte = "RESTRICAO")
public class Restricao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer nuRestricao;
	private String coRestricao;
	private String noRestricao;
	private String deRestricao;
	private String sistemaDestino;
	private Boolean icAtiva = true;
	private List<RestricaoApontamento> restricaoApontamentoList = new ArrayList<RestricaoApontamento>();
	
	public Restricao() {
	}
	
	public Restricao(Integer numero, String codigo, String nome, String descricao, String sistemaDestino, Boolean icAtiva) {
		this.nuRestricao = numero;
		this.coRestricao = codigo;
		this.noRestricao = nome;
		this.deRestricao = descricao;
		this.sistemaDestino = sistemaDestino;
		this.icAtiva = icAtiva;
	}
	
	@Id
	@Column(name = "nu_restricao", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq006_restricao")
	@SequenceGenerator(name = "iacsq006_restricao", sequenceName = "iacsm001.iacsq006_restricao")
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public Integer getNuRestricao() {
		return nuRestricao;
	}
	
	public void setNuRestricao(Integer numero) {
		this.nuRestricao = numero;
	}
	
	@Column(name = "co_restricao", nullable = false, length = 2)
	@TrailLog
	public String getCoRestricao() {
		return coRestricao;
	}
	
	public void setCoRestricao(String codigo) {
		this.coRestricao = codigo;
	}
	
	@Column(name = "no_restricao", length = 70)
	@TrailLog
	public String getNoRestricao() {
		return noRestricao;
	}
	
	public void setNoRestricao(String nome) {
		this.noRestricao = nome;
	}
	
	@Column(name = "de_restricao")
	@TrailLog
	public String getDeRestricao() {
		return deRestricao;
	}
	
	public void setDeRestricao(String descricao) {
		this.deRestricao = descricao;
	}
	
	@Column(name = "sg_sistema_restricao", nullable = false, length = 8)
	@TrailLog
	public String getSistemaDestino() {
		return sistemaDestino;
	}
	
	public void setSistemaDestino(String sistemaDestino) {
		this.sistemaDestino = sistemaDestino;
	}
	
	@Column(name = "ic_ativa", nullable = false)
	@TrailLog
	public Boolean getIcAtiva() {
		return icAtiva;
	}
	
	public void setIcAtiva(Boolean icAtiva) {
		this.icAtiva = icAtiva;
	}
	
	@OneToMany(mappedBy = "restricao", fetch = FetchType.LAZY)
	public List<RestricaoApontamento> getRestricaoApontamentoList() {
		return restricaoApontamentoList;
	}
	
	public void setRestricaoApontamentoList(List<RestricaoApontamento> restricaoApontamentoList) {
		this.restricaoApontamentoList = restricaoApontamentoList;
	}
	
}
