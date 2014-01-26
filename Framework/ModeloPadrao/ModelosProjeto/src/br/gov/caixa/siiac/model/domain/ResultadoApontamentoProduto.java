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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb013_rslto_aptmnto_produto", schema = "iacsm001")
@TrailClass(fonte = "EFETUA_VERIFICACAO")
public class ResultadoApontamentoProduto implements Serializable {
	
	public static final Character ESTADO_VERIFICACAO_APONTAMENTO_ID_NAO_VERIFICADA = '0';
	public static final Character ESTADO_VERIFICACAO_APONTAMENTO_ID_CONFORME = '1';
	public static final Character ESTADO_VERIFICACAO_APONTAMENTO_ID_INCONFORME = '2';

	public static final String ESTADO_VERIFICACAO_APONTAMENTO_LABEL_NAO_VERIFICADA = "N�o Verificado";
	public static final String ESTADO_VERIFICACAO_APONTAMENTO_LABEL_CONFORME = "Conforme";
	public static final String ESTADO_VERIFICACAO_APONTAMENTO_LABEL_INCONFORME = "Inconforme";

	private static final long serialVersionUID = 1L;
	private Integer nuResultadoApontamentoProduto;
	private String coResponsavelResultado;
	private String deObservacao;
	private VerificacaoContrato verificacaoContrato;
	private ApontamentoChecklistProduto apontamentoChecklistProduto;
	private Contrato contrato;
	private char icResultadoApontamentoChecklist = '0';
	
	public ResultadoApontamentoProduto() {
	}
	
	public ResultadoApontamentoProduto(Integer nuRsltoAptmntoProduto) {
		this.nuResultadoApontamentoProduto = nuRsltoAptmntoProduto;
	}
	
	public ResultadoApontamentoProduto(Integer nuRsltoAptmntoProduto, boolean icResultadoAptmntoChklst, String coRspnlResultado) {
		this.nuResultadoApontamentoProduto = nuRsltoAptmntoProduto;
		this.coResponsavelResultado = coRspnlResultado;
	}
	
	@Id
	@Column(name = "nu_rslto_aptmnto_produto", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq021_rslto_aptmnto_produto")
	@SequenceGenerator(name = "iacsq021_rslto_aptmnto_produto", sequenceName = "iacsm001.iacsq021_rslto_aptmnto_produto")
	@TrailLog(insertID = true, updateID = true, deleteID = true)
	public Integer getNuResultadoApontamentoProduto() {
		return nuResultadoApontamentoProduto;
	}
	
	public void setNuResultadoApontamentoProduto(Integer nuRsltoAptmntoProduto) {
		this.nuResultadoApontamentoProduto = nuRsltoAptmntoProduto;
	}
	
	@Column(name = "co_rspnl_resultado", nullable = false, length = 7)
	public String getCoResponsavelResultado() {
		return coResponsavelResultado;
	}
	
	public void setCoResponsavelResultado(String coRspnlResultado) {
		this.coResponsavelResultado = coRspnlResultado;
	}
	
	@Column(name = "de_observacao", length = 2147483647)
	public String getDeObservacao() {
		return deObservacao;
	}
	
	public void setDeObservacao(String deObservacao) {
		this.deObservacao = deObservacao;
	}
	
	@JoinColumn(name = "nu_verificacao_contrato", referencedColumnName = "nu_verificacao_contrato")
	@ManyToOne
	public VerificacaoContrato getVerificacaoContrato() {
		return verificacaoContrato;
	}
	
	public void setVerificacaoContrato(VerificacaoContrato nuVerificacaoContrato) {
		this.verificacaoContrato = nuVerificacaoContrato;
	}
	
	@JoinColumn(name = "nu_aptmnto_chklst_produto", referencedColumnName = "nu_aptmnto_chklst_produto")
	@ManyToOne
	public ApontamentoChecklistProduto getApontamentoChecklistProduto() {
		return apontamentoChecklistProduto;
	}
	
	public void setApontamentoChecklistProduto(ApontamentoChecklistProduto nuAptmntoChklstProduto) {
		this.apontamentoChecklistProduto = nuAptmntoChklstProduto;
	}
	
	@JoinColumn(name = "nu_contrato", referencedColumnName = "nu_contrato", nullable = false)
	@ManyToOne
	public Contrato getContrato() {
		return contrato;
	}
	
	public void setContrato(Contrato nuContrato) {
		this.contrato = nuContrato;
	}
	
	@Column(name = "ic_resultado_aptmnto_chklst")
	@TrailLog
	public char getIcResultadoApontamentoChecklist() {
		return icResultadoApontamentoChecklist;
	}
	
	public void setIcResultadoApontamentoChecklist(char icResultadoAptmntoChklst) {
		this.icResultadoApontamentoChecklist = icResultadoAptmntoChklst;
	}
	
	@Transient
	@TrailLog(name = "nu_chklst_srvco_vrfco_produto")
	public Integer getChecklistTrilha(){
		if (verificacaoContrato != null) {
			if (verificacaoContrato.getChecklist() != null) {
				return verificacaoContrato.getChecklist().getNuChecklistServicoVerificacaoProduto();
			}
		}
		return null;
	}
}
