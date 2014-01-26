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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author GIS Consult
 *
 */
@Entity
@Table(name = "iactb064_preferencia_usro_ctgra", schema = "iacsm001")
public class PreferenciasUsuarioCategoriaProduto implements java.io.Serializable {
	
	private static final long serialVersionUID = 565128091036L;
	private PreferenciaUsuarioCategoriaProdutoId id = new PreferenciaUsuarioCategoriaProdutoId();
	
	private Usuario usuario;
	private CategoriaProduto categoriaProduto;
	
	@EmbeddedId
	@AttributeOverrides({ 
		@AttributeOverride(name = "nuCategoriaProduto", column = @Column(name = "nu_categoria_produto", nullable = false)), 
		@AttributeOverride(name = "coUsuario", column = @Column(name = "co_usuario", nullable = false, length = 7)) 
	})
	public PreferenciaUsuarioCategoriaProdutoId getId() {
		return id;
	}
	
	public void setId(PreferenciaUsuarioCategoriaProdutoId id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "co_usuario", nullable = false, insertable = false, updatable = false)
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nu_categoria_produto", nullable = false, insertable = false, updatable = false)
	public CategoriaProduto getCategoriaProduto() {
		return categoriaProduto;
	}
	
	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}
	
	

}
