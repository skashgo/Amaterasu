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
import javax.persistence.Embeddable;

/**
 * @author GIS Consult
 *
 */
@Embeddable
public class PreferenciasUsuarioId implements java.io.Serializable {
	
	private static final long serialVersionUID = 8792364525236750132L;
	private String coUsuario;
	private String coProduto;
	
	@Column(name = "co_usuario", nullable = false, length = 7)
	public String getCoUsuario() {
		return coUsuario;
	}
	
	public void setCoUsuario(String coUsuario) {
		this.coUsuario = coUsuario;
	}
	
	@Column(name = "co_produto", nullable = false)
	public String getCoProduto() {
		return coProduto;
	}
	
	public void setCoProduto(String coProduto) {
		this.coProduto = coProduto;
	}
}
