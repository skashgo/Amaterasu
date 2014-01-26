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
package br.gov.caixa.siiac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author GIS Consult
 *
 */
@Entity
public class ProdutoVO {
	
	private String coProduto;
	private String noProduto;
	private String ultimaSitucacao;
	private String siglaSistema;
	
	public ProdutoVO() {
	}
	
	public ProdutoVO(String coProduto, String noProduto, String ultimaSitucacao,
			String siglaSistema) {
		this.coProduto = coProduto;
		this.noProduto = noProduto;
		this.ultimaSitucacao = ultimaSitucacao;
		this.siglaSistema = siglaSistema;
	}

	
	@Id
	@Column(name = "nu_produto")
	public String getCoProduto() {
		return coProduto;
	}
	
	public void setCoProduto(String coProduto) {
		this.coProduto = coProduto;
	}
	
	@Column(name = "no_comercial_prdto")
	public String getNoProduto() {
		return noProduto;
	}
	
	public void setNoProduto(String noProduto) {
		this.noProduto = noProduto;
	}
	
	@Column(name = "co_ultima_situacao")
	public String getUltimaSituacao() {
		return ultimaSitucacao;
	}
	
	public void setUltimaSituacao(String ultimaSitucacao) {
		this.ultimaSitucacao = ultimaSitucacao;
	}
	
	@Column(name = "sg_sistema")
	public String getSiglaSistema() {
		return siglaSistema;
	}
	
	public void setSiglaSistema(String siglaSistema) {
		this.siglaSistema = siglaSistema;
	}
	
	
}
