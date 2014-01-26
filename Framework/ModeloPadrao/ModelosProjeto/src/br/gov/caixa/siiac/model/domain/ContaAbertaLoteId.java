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

@Embeddable
public class ContaAbertaLoteId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int coConta;
	private Short nuOperacao;
	
	public ContaAbertaLoteId() {
	}
	
	public ContaAbertaLoteId(int coConta, Short nuOperacao) {
		this.coConta = coConta;
		this.nuOperacao = nuOperacao;
	}
	
	@Column(name = "co_conta", nullable = false)
	public int getCoConta() {
		return coConta;
	}
	
	public void setCoConta(int coConta) {
		this.coConta = coConta;
	}
	
	@Column(name = "nu_operacao", nullable = false)
	public Short getNuOperacao() {
		return nuOperacao;
	}
	
	public void setNuOperacao(Short nuOperacao) {
		this.nuOperacao = nuOperacao;
	}
}
