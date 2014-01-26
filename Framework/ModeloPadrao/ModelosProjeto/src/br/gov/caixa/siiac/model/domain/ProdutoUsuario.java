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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb038_produto_usuario", schema = "iacsm001")
@TrailClass(fonte = "USUARIO_PRODUTO")
public class ProdutoUsuario {
	
	private Integer nuProdutoUsuario;
	private Usuario usuario;
	private Produto produto;
	
	@Id
	@Column(name = "nu_produto_usuario", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacSQ001_produto_usuario")
	@SequenceGenerator(name = "iacSQ001_produto_usuario", sequenceName = "iacsm001.iacSQ001_produto_usuario")
	@TrailLog(insertID = true, updateID = true, deleteID = true)
	public Integer getNuProdutoUsuario() {
		return nuProdutoUsuario;
	}
	
	public void setNuProdutoUsuario(Integer nuProdutoUsuario) {
		this.nuProdutoUsuario = nuProdutoUsuario;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "co_usuario", nullable = false)
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Transient
	@TrailLog(name = "co_usuario")
	public String getUsuarioTrilha(){
		if (usuario != null) {
			return usuario.getCoUsuario();
		}
		return null;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "co_produto", nullable = false)
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	@Transient
	@TrailLog(name = "co_produto")
	public String getProdutoTrilha(){
		if (produto != null) {
			return produto.getCoProduto();
		}
		return null;
	}
}
