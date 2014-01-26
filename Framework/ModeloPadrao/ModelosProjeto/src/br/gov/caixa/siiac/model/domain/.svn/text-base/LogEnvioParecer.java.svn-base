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
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author GIS Consult
 */
@Entity
@Table(name = "iactb069_log_envio_parecer", schema = "iacsm001")
public class LogEnvioParecer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected LogEnvioParecerId id;
	private Date dtEnvioParecer;
	private Boolean icSituacaoEnvioParecer;
	private String deEnvioParecer;
	
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "nuParecer", column = @Column(name = "nu_parecer")), @AttributeOverride(name = "aaParecer", column = @Column(name = "aa_parecer")), @AttributeOverride(name = "nuUnidade", column = @Column(name = "nu_unidade")), @AttributeOverride(name = "nuNatural", column = @Column(name = "nu_natural")) })
	public LogEnvioParecerId getId() {
		return id;
	}
	
	public void setId(LogEnvioParecerId id) {
		this.id = id;
	}
	
	@Column(name = "dt_envio_parecer", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDtEnvioParecer() {
		return dtEnvioParecer;
	}
	
	public void setDtEnvioParecer(Date dtEnvioParecer) {
		this.dtEnvioParecer = dtEnvioParecer;
	}
	
	@Column(name = "ic_situacao_envio_parecer")
	public Boolean getIcSituacaoEnvioParecer() {
		return icSituacaoEnvioParecer;
	}
	
	public void setIcSituacaoEnvioParecer(Boolean icSituacaoEnvioParecer) {
		this.icSituacaoEnvioParecer = icSituacaoEnvioParecer;
	}
	
	@Column(name = "de_envio_parecer")
	public String getDeEnvioParecer() {
		return deEnvioParecer;
	}
	
	public void setDeEnvioParecer(String deEnvioParecer) {
		this.deEnvioParecer = deEnvioParecer;
	}
	
}
