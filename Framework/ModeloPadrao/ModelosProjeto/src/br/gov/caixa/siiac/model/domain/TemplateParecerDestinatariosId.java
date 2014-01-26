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

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author GIS Consult
 *
 */
@Embeddable
public class TemplateParecerDestinatariosId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer nuTemplateParecer;
	private Integer coDestinatario;
	
	
	public TemplateParecerDestinatariosId() {
	}

	public TemplateParecerDestinatariosId(Integer nuTemplateParecer,
			Integer coDestinatario) {
		this.nuTemplateParecer = nuTemplateParecer;
		this.coDestinatario = coDestinatario;
	}

	@Column(name = "nu_template_parecer")
	public Integer getNuTemplateParecer() {
		return nuTemplateParecer;
	}
	
	public void setNuTemplateParecer(Integer nuTemplateParecer) {
		this.nuTemplateParecer = nuTemplateParecer;
	}
	
	@Column(name = "co_destinatario")
	public Integer getCoDestinatario() {
		return coDestinatario;
	}
	
	public void setCoDestinatario(Integer coDestinatario) {
		this.coDestinatario = coDestinatario;
	}
	
}
