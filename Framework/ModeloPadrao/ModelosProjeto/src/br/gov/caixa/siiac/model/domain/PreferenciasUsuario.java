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

@Entity
@Table(name = "iactb035_preferencias_usuario", schema = "iacsm001")
public class PreferenciasUsuario {
	
	private PreferenciasUsuarioId id = new PreferenciasUsuarioId();
	private Produto produto;
	private Usuario usuario;
	private String icEstadoVerificacao;
	private String icSituacaoContrato;
	private String icEstadoGarantia;
	
	@EmbeddedId
	@AttributeOverrides({ 
		@AttributeOverride(name = "coProduto", column = @Column(name = "co_produto", nullable = false, length = 7)), 
		@AttributeOverride(name = "coUsuario", column = @Column(name = "co_usuario", nullable = false, length = 7)) 
	})
	public PreferenciasUsuarioId getId() {
		return id;
	}
	
	public void setId(PreferenciasUsuarioId id) {
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
	@JoinColumn(name = "co_produto", nullable = false, insertable = false, updatable = false)
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	@Column(name = "ic_estado_verificacao", length = 2)
	public String getIcEstadoVerificacao() {
		return icEstadoVerificacao;
	}
	
	public void setIcEstadoVerificacao(String icEstadoVerificacao) {
		this.icEstadoVerificacao = icEstadoVerificacao;
	}
	
	@Column(name = "ic_situacao_contrato", length = 6)
	public String getIcSituacaoContrato() {
		return icSituacaoContrato;
	}
	
	public void setIcSituacaoContrato(String icSituacaoContrato) {
		this.icSituacaoContrato = icSituacaoContrato;
	}
	
	@Column(name = "ic_estado_garantia", length = 2)
	public String getIcEstadoGarantia() {
		return icEstadoGarantia;
	}
	
	public void setIcEstadoGarantia(String icEstadoGarantia) {
		this.icEstadoGarantia = icEstadoGarantia;
	}
}
