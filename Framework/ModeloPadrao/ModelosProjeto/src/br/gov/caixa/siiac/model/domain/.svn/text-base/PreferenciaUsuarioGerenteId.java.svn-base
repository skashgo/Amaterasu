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
public class PreferenciaUsuarioGerenteId implements Serializable{

	
	private static final long serialVersionUID = 7703565646458045148L;

	private String coUsuario;
	private Integer coGerentePreferencial;
	
	@Column(name = "co_usuario", nullable = false, length = 7)
	public String getCoUsuario() {
		return coUsuario;
	}
	
	public void setCoUsuario(String coUsuario) {
		this.coUsuario = coUsuario;
	}

	@Column(name = "co_gerente_preferencial", nullable = false)
	public Integer getCoGerentePreferencial() {
		return coGerentePreferencial;
	}

	
	public void setCoGerentePreferencial(Integer coGerentePreferencial) {
		this.coGerentePreferencial = coGerentePreferencial;
	}
	


}
