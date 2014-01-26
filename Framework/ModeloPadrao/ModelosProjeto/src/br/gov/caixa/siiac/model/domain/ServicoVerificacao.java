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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "iactb002_servico_verificacao", schema = "iacsm001")
public class ServicoVerificacao implements java.io.Serializable {
	
	private static final long serialVersionUID = 887746548710L;
	private int nuServicoVerificacao;
	private String noServicoVerificacao;
	private boolean icAtivo = true;
	private Set<ServicoVerificacaoProduto> servicoVerificacaoProdutoList = new HashSet<ServicoVerificacaoProduto>(0);
	
	public ServicoVerificacao() {
	}
	
	public ServicoVerificacao(int nuServicoVerificacao, String noServicoVerificacao, boolean icAtivo) {
		this.nuServicoVerificacao = nuServicoVerificacao;
		this.noServicoVerificacao = noServicoVerificacao;
		this.icAtivo = icAtivo;
	}
	
	public ServicoVerificacao(int nuServicoVerificacao, String noServicoVerificacao, boolean icAtivo, Set<ServicoVerificacaoProduto> servicoVerificacaoProdutos) {
		this.nuServicoVerificacao = nuServicoVerificacao;
		this.noServicoVerificacao = noServicoVerificacao;
		this.icAtivo = icAtivo;
		this.servicoVerificacaoProdutoList = servicoVerificacaoProdutos;
	}
	
	@Id
	@Column(name = "nu_servico_verificacao", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacSQ002_servico_verificacao")
	@SequenceGenerator(name = "iacSQ002_servico_verificacao", sequenceName = "iacsm001.iacSQ002_servico_verificacao")
	public int getNuServicoVerificacao() {
		return this.nuServicoVerificacao;
	}
	
	public void setNuServicoVerificacao(int nuServicoVerificacao) {
		this.nuServicoVerificacao = nuServicoVerificacao;
	}
	
	@Column(name = "no_servico_verificacao", nullable = false, length = 70)
	public String getNoServicoVerificacao() {
		return this.noServicoVerificacao;
	}
	
	public void setNoServicoVerificacao(String noServicoVerificacao) {
		this.noServicoVerificacao = noServicoVerificacao;
	}
	
	@Column(name = "ic_ativo", nullable = false)
	public boolean isIcAtivo() {
		return this.icAtivo;
	}
	
	public void setIcAtivo(boolean icAtivo) {
		this.icAtivo = icAtivo;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "servicoVerificacao")
	public Set<ServicoVerificacaoProduto> getServicoVerificacaoProdutoList() {
		return this.servicoVerificacaoProdutoList;
	}
	
	public void setServicoVerificacaoProdutoList(Set<ServicoVerificacaoProduto> servicoVerificacaoProdutos) {
		this.servicoVerificacaoProdutoList = servicoVerificacaoProdutos;
	}
	
}
