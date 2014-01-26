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
@Table(name = "iactb041_preferencia_usro_srvco", schema = "iacsm001")
public class PreferenciaUsuarioServico implements java.io.Serializable {
	
	private static final long serialVersionUID = 565151631036L;
	private PreferenciaUsuarioServicoId id = new PreferenciaUsuarioServicoId();
	private Usuario usuario;
	private ServicoVerificacao servicoVerificacao;
	
	@EmbeddedId
	@AttributeOverrides({ 
		@AttributeOverride(name = "nuServicoVerificacao", column = @Column(name = "nu_servico_verificacao", nullable = false)), 
		@AttributeOverride(name = "coUsuario", column = @Column(name = "co_usuario", nullable = false, length = 7)) 
	})
	public PreferenciaUsuarioServicoId getId() {
		return id;
	}
	
	public void setId(PreferenciaUsuarioServicoId id) {
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
	@JoinColumn(name = "nu_servico_verificacao", nullable = false, insertable = false, updatable = false)
	public ServicoVerificacao getServicoVerificacao() {
		return servicoVerificacao;
	}
	
	public void setServicoVerificacao(ServicoVerificacao servicoVerificacao) {
		this.servicoVerificacao = servicoVerificacao;
	}
	
}
