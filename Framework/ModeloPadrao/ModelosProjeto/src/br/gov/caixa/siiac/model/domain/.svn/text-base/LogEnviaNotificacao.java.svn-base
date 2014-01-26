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
import javax.persistence.Table;

/**
 * @author GIS Consult
 *
 */
@Entity
@Table(name = "iactb065_log_envio_notificacao", schema = "iacsm001")
public class LogEnviaNotificacao implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private LogEnviaNotificacaoId id;
	private Integer quantidadeNotificacaoSucesso;
	private String deUnidadeSucesso;
	private Integer quantidadeNotificacaoErro;
	private String deUnidadeErro;

	@EmbeddedId
	@AttributeOverrides({ 
		@AttributeOverride(name = "assuntoNotiicacao", column = @Column(name = "no_assunto_notificacao")), 
		@AttributeOverride(name = "dataEnvioNotificacao", column = @Column(name = "dt_envio_notificacao")) })
	public LogEnviaNotificacaoId getId() {
		return id;
	}

	public void setId(LogEnviaNotificacaoId id) {
		this.id = id;
	}

	@Column(name = "qt_notificacao_sucesso")
	public Integer getQuantidadeNotificacaoSucesso() {
		return quantidadeNotificacaoSucesso;
	}

	public void setQuantidadeNotificacaoSucesso(Integer quantidadeNotificacaoSucesso) {
		this.quantidadeNotificacaoSucesso = quantidadeNotificacaoSucesso;
	}

	@Column(name = "de_unidades_sucesso")	
	public String getDeUnidadeSucesso() {
		return deUnidadeSucesso;
	}

	public void setDeUnidadeSucesso(String deUnidadeSucesso) {
		this.deUnidadeSucesso = deUnidadeSucesso;
	}
	
	@Column(name = "qt_notificacao_erro")	
	public Integer getQuantidadeNotificacaoErro() {
		return quantidadeNotificacaoErro;
	}

	public void setQuantidadeNotificacaoErro(Integer quantidadeNotificacaoErro) {
		this.quantidadeNotificacaoErro = quantidadeNotificacaoErro;
	}

	@Column(name = "de_unidades_erro")	
	public String getDeUnidadeErro() {
		return deUnidadeErro;
	}

	public void setDeUnidadeErro(String deUnidadeErro) {
		this.deUnidadeErro = deUnidadeErro;
	}

}
