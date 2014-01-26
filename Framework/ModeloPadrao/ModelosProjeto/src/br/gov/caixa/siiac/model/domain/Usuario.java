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
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb034_usuario", schema = "iacsm001")
@TrailClass(fonte = "USUARIO")
public class Usuario implements java.io.Serializable {
	
	private static final long serialVersionUID = 87849049494980L;
	private String coUsuario;
	private Perfil perfil;
	private String coResponsavel;
	private Date tsInclusaoAlteracao;
	private Boolean icAtivo;
	private Short nuUnidadeVerificacao;
	private Integer nuNaturalUnidadeVerificacao;
	private List<ProdutoUsuario> produtoUsuarioList = new ArrayList<ProdutoUsuario>();
	
	public Usuario() {
	}
	
	public Usuario(Perfil perfil, String coResponsavel, Date tsInclusaoAlteracao, Boolean icAtivo) {
		this.perfil = perfil;
		this.coResponsavel = coResponsavel;
		this.tsInclusaoAlteracao = tsInclusaoAlteracao;
		this.icAtivo = icAtivo;
	}
	
	@Id
	@Column(name = "co_usuario", unique = true, nullable = false, length = 7)
	@TrailLog(insertID = true, updateID = true, deleteID = true)
	public String getCoUsuario() {
		return this.coUsuario;
	}
	
	public void setCoUsuario(String coUsuario) {
		this.coUsuario = coUsuario;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nu_perfil")
	public Perfil getPerfil() {
		return this.perfil;
	}
	
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	@Transient
	@TrailLog(name = "nu_perfil")
	public Integer getPerfilTrilha(){
		if (perfil != null) {
			return perfil.getNuPerfil();
		}
		return null;
	}
	
	@Column(name = "co_responsavel", length = 7)
	@TrailLog
	public String getCoResponsavel() {
		return this.coResponsavel;
	}
	
	public void setCoResponsavel(String coResponsavel) {
		this.coResponsavel = coResponsavel;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_inclusao_alteracao", length = 29)
	@TrailLog
	public Date getTsInclusaoAlteracao() {
		return this.tsInclusaoAlteracao;
	}
	
	public void setTsInclusaoAlteracao(Date tsInclusaoAlteracao) {
		this.tsInclusaoAlteracao = tsInclusaoAlteracao;
	}
	
	@Column(name = "ic_ativo")
	@TrailLog
	public Boolean getIcAtivo() {
		return this.icAtivo;
	}
	
	public void setIcAtivo(Boolean icAtivo) {
		this.icAtivo = icAtivo;
	}
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	public List<ProdutoUsuario> getProdutoUsuarioList() {
		return produtoUsuarioList;
	}
	
	public void setProdutoUsuarioList(List<ProdutoUsuario> produtoUsuarioList) {
		this.produtoUsuarioList = produtoUsuarioList;
	}

	@Column(name = "nu_unidade_verificacao")
	@TrailLog
	public Short getNuUnidadeVerificacao() {
		return nuUnidadeVerificacao;
	}

	public void setNuUnidadeVerificacao(Short nuUnidadeVerificacao) {
		this.nuUnidadeVerificacao = nuUnidadeVerificacao;
	}

	@Column(name = "nu_natural_unidade_verificacao")
	@TrailLog
	public Integer getNuNaturalUnidadeVerificacao() {
		return nuNaturalUnidadeVerificacao;
	}

	public void setNuNaturalUnidadeVerificacao(Integer nuNaturalUnidadeVerificacao) {
		this.nuNaturalUnidadeVerificacao = nuNaturalUnidadeVerificacao;
	}
}
