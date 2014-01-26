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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "iactb033_perfil", schema = "iacsm001")
public class Perfil implements java.io.Serializable {
	
	public static final Short PERFIL_GESTOR_SISTEMA = 1;
	public static final Short PERFIL_REGIONAL_CONFORMIDADE = 2;
	public static final Short PERFIL_VERIFICADOR_CONFORMIDADE = 3;
	public static final Short PERFIL_GESTOR_PRODUTO = 4;
	public static final Short PERFIL_UNIDADE_ATENDIMENTO = 5;
	public static final Short PERFIL_AUDITOR = 6;
	public static final Short PERFIL_CENTRAL_ATENDIMENTO_SUPORTE = 7;
	public static final Short PERFIL_VERIFICADOR_NACIONAL = 8;
	public static final Short PERFIL_VERIFICADOR_REGIONAL = 9;
	
	private static final long serialVersionUID = 121367534213L;
	private int nuPerfil;
	private String noPerfil;
	private Set<Usuario> usuarioList = new HashSet<Usuario>(0);
	
	public Perfil() {
	}
	
	public Perfil(int nuPerfil, String noPerfil) {
		this.nuPerfil = nuPerfil;
		this.noPerfil = noPerfil;
	}
	
	public Perfil(int nuPerfil, String noPerfil, Set<Usuario> usuarios) {
		this.nuPerfil = nuPerfil;
		this.noPerfil = noPerfil;
		this.usuarioList = usuarios;
	}
	
	@Id
	@Column(name = "nu_perfil", unique = true, nullable = false)
	public int getNuPerfil() {
		return this.nuPerfil;
	}
	
	public void setNuPerfil(int nuPerfil) {
		this.nuPerfil = nuPerfil;
	}
	
	@Column(name = "no_perfil", nullable = false, length = 50)
	public String getNoPerfil() {
		return this.noPerfil;
	}
	
	public void setNoPerfil(String noPerfil) {
		this.noPerfil = noPerfil;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "perfil")
	public Set<Usuario> getUsuarioList() {
		return this.usuarioList;
	}
	
	public void setUsuarioList(Set<Usuario> usuarios) {
		this.usuarioList = usuarios;
	}
	
}
