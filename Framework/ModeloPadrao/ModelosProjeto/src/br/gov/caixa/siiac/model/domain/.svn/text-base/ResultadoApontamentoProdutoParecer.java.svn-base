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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "iactb057_rslto_aptmnto_prdto_pr", schema = "iacsm001")
public class ResultadoApontamentoProdutoParecer implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer nuResultadoApontamentoProduto;
	private int nuApontamentoChecklistProduto;
	private int nuContrato;
	private char icResultadoApontamentoChecklist;
	private String coResponsavelResultado;
	private String deObservacao;
	private VerificacaoContratoParecer verificacaoContrato;
	
	public ResultadoApontamentoProdutoParecer() {
	}
	
	public ResultadoApontamentoProdutoParecer(Integer nuRsltoAptmntoProduto) {
		this.nuResultadoApontamentoProduto = nuRsltoAptmntoProduto;
	}
	
	public ResultadoApontamentoProdutoParecer(Integer nuRsltoAptmntoProduto, int nuAptmntoChklstProduto, int nuContrato, char icResultadoAptmntoChklst, String coRspnlResultado) {
		this.nuResultadoApontamentoProduto = nuRsltoAptmntoProduto;
		this.nuApontamentoChecklistProduto = nuAptmntoChklstProduto;
		this.nuContrato = nuContrato;
		this.icResultadoApontamentoChecklist = icResultadoAptmntoChklst;
		this.coResponsavelResultado = coRspnlResultado;
	}
	
	@Id
	@Column(name = "nu_rslto_aptmnto_produto")
	public Integer getNuResultadoApontamentoProduto() {
		return nuResultadoApontamentoProduto;
	}
	
	public void setNuResultadoApontamentoProduto(Integer nuRsltoAptmntoProduto) {
		this.nuResultadoApontamentoProduto = nuRsltoAptmntoProduto;
	}
	
	@Column(name = "nu_aptmnto_chklst_produto")
	public int getNuApontamentoChecklistProduto() {
		return nuApontamentoChecklistProduto;
	}
	
	public void setNuApontamentoChecklistProduto(int nuAptmntoChklstProduto) {
		this.nuApontamentoChecklistProduto = nuAptmntoChklstProduto;
	}
	
	@Column(name = "nu_contrato")
	public int getNuContrato() {
		return nuContrato;
	}
	
	public void setNuContrato(int nuContrato) {
		this.nuContrato = nuContrato;
	}
	
	@Column(name = "ic_resultado_aptmnto_chklst")
	public char getIcResultadoApontamentoChecklist() {
		return icResultadoApontamentoChecklist;
	}
	
	public void setIcResultadoApontamentoChecklist(char icResultadoAptmntoChklst) {
		this.icResultadoApontamentoChecklist = icResultadoAptmntoChklst;
	}
	
	@Column(name = "co_rspnl_resultado")
	public String getCoResponsavelResultado() {
		return coResponsavelResultado;
	}
	
	public void setCoResponsavelResultado(String coRspnlResultado) {
		this.coResponsavelResultado = coRspnlResultado;
	}
	
	@Column(name = "de_observacao")
	public String getDeObservacao() {
		return deObservacao;
	}
	
	public void setDeObservacao(String deObservacao) {
		this.deObservacao = deObservacao;
	}
	
	@JoinColumn(name = "nu_verificacao_contrato", referencedColumnName = "nu_verificacao_contrato")
	@ManyToOne
	public VerificacaoContratoParecer getVerificacaoContrato() {
		return verificacaoContrato;
	}
	
	public void setVerificacaoContrato(VerificacaoContratoParecer nuVerificacaoContrato) {
		this.verificacaoContrato = nuVerificacaoContrato;
	}
}
