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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author GIS Consult
 *
 */
@Embeddable
public class LogEnviaNotificacaoId implements java.io.Serializable {
	
	private static final long serialVersionUID = 134325852432L;
	
	private String assuntoNotificacao;
	private Date dataEnvioNotificacao;
	
	public LogEnviaNotificacaoId() {
	}
	
	public LogEnviaNotificacaoId(String assuntoNotificacao,
			Date dataEnvioNotificacao) {
		this.assuntoNotificacao = assuntoNotificacao;
		this.dataEnvioNotificacao = dataEnvioNotificacao;
	}


	@Column(name = "no_assunto_notificacao", nullable=false)
	public String getAssuntoNotificacao() {
		return assuntoNotificacao;
	}
	
	public void setAssuntoNotificacao(String assuntoNotificacao) {
		this.assuntoNotificacao = assuntoNotificacao;
	}
	
	@Column(name = "qt_notificacao_sucesso", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataEnvioNotificacao() {
		return dataEnvioNotificacao;
	}
	
	public void setDataEnvioNotificacao(Date dataEnvioNotificacao) {
		this.dataEnvioNotificacao = dataEnvioNotificacao;
	}

}
