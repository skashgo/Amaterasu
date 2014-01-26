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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb016_parecer", schema = "iacsm001")
@TrailClass(fonte = "PARECER")
public class Parecer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected ParecerId parecerId;
	private String noParecer;
	private String coResponsavelParecer;
	private TemplateParecer templateParecer;
	private VerificacaoContratoParecer verificacaoContratoParecer;
	private ControleNumeroParecer controleNumeroParecer;
	private Contrato contrato;
	private Date dtParecer;
	private ArquivoImagemParecer arquivoImagemParecer;
	private String coGerenteParecer;
	
	public Parecer() {
	}
	
	public Parecer(ParecerId parecerId) {
		this.parecerId = parecerId;
	}
	
	public Parecer(ParecerId parecerId, Date dtParecer, String noParecer, String coResponsavelParecer) {
		this.parecerId = parecerId;
		this.dtParecer = dtParecer;
		this.noParecer = noParecer;
		this.coResponsavelParecer = coResponsavelParecer;
	}
	
	public Parecer(int nuParecer, Short aaParecer, Short nuUnidade, Short nuNatural) {
		this.parecerId = new ParecerId(nuParecer, aaParecer, nuUnidade, nuNatural);
	}
	
	@EmbeddedId
	@TrailLog(inner = true)
	public ParecerId getParecerId() {
		return parecerId;
	}
	
	public void setParecerId(ParecerId parecerId) {
		this.parecerId = parecerId;
	}
	
	@Column(name = "dt_parecer")
	@Temporal(TemporalType.DATE)
	@TrailLog
	public Date getDtParecer() {
		return dtParecer;
	}
	
	public void setDtParecer(Date dtParecer) {
		this.dtParecer = dtParecer;
	}
	
	@Column(name = "no_parecer", nullable = false)
	@TrailLog
	public String getNoParecer() {
		return noParecer;
	}
	
	public void setNoParecer(String noParecer) {
		this.noParecer = noParecer;
	}
	
	@Column(name = "co_responsavel_parecer", nullable = false, length = 7)
	@TrailLog
	public String getCoResponsavelParecer() {
		return coResponsavelParecer;
	}
	
	public void setCoResponsavelParecer(String coResponsavelParecer) {
		this.coResponsavelParecer = coResponsavelParecer;
	}
	
	@JoinColumn(name = "nu_template_parecer", referencedColumnName = "nu_template_parecer", nullable = false)
	@ManyToOne
	public TemplateParecer getTemplateParecer() {
		return templateParecer;
	}
	
	public void setTemplateParecer(TemplateParecer nuTemplateParecer) {
		this.templateParecer = nuTemplateParecer;
	}
	
	@Transient
	@TrailLog(name = "nu_template_parecer")
	public Integer getTemplateParecerTrilha(){
		if (templateParecer != null) {
			return templateParecer.getNuTemplateParecer();
		}
		return null;
	}
	
	@JoinColumn(name = "nu_verificacao_contrato", referencedColumnName = "nu_verificacao_contrato", nullable = false)
	@ManyToOne
	public VerificacaoContratoParecer getVerificacaoContratoParecer() {
		return verificacaoContratoParecer;
	}
	
	public void setVerificacaoContratoParecer(VerificacaoContratoParecer verificacaoContratoParecer) {
		this.verificacaoContratoParecer = verificacaoContratoParecer;
	}
	
	@Transient
	@TrailLog(name = "nu_verificacao_contrato")
	public Integer getVerificacaoContratoParecerTrilha(){
		if (verificacaoContratoParecer != null) {
			return verificacaoContratoParecer.getNuChecklistServicoVerificacaoProduto();
		}
		return null;
	}
	
	@JoinColumns({ 
		@JoinColumn(name = "nu_parecer", referencedColumnName = "nu_parecer", nullable = false, insertable = false, updatable = false), 
		@JoinColumn(name = "aa_parecer", referencedColumnName = "aa_parecer", nullable = false, insertable = false, updatable = false), 
		@JoinColumn(name = "nu_unidade", referencedColumnName = "nu_unidade", nullable = false, insertable = false, updatable = false), 
		@JoinColumn(name = "nu_natural", referencedColumnName = "nu_natural", nullable = false, insertable = false, updatable = false) 
		})
	@ManyToOne
	public ControleNumeroParecer getControleNumeroParecer() {
		return controleNumeroParecer;
	}
	
	public void setControleNumeroParecer(ControleNumeroParecer controleNumeroParecer) {
		this.controleNumeroParecer = controleNumeroParecer;
	}
	
	@JoinColumn(name = "nu_contrato", referencedColumnName = "nu_contrato", nullable = false)
	@ManyToOne
	public Contrato getContrato() {
		return contrato;
	}
	
	public void setContrato(Contrato nuContrato) {
		this.contrato = nuContrato;
	}
	
	@Transient
	@TrailLog(insertID = true, updateID = true, deleteID = true, name = "nu_contrato")
	public Integer getContratoTrilha(){
		if (contrato != null) {
			return contrato.getNuContrato();
		}
		return null;
	}
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "parecer")
	public ArquivoImagemParecer getArquivoImagemParecer() {
		return arquivoImagemParecer;
	}
	
	public void setArquivoImagemParecer(ArquivoImagemParecer arquivoImagemParecer) {
		this.arquivoImagemParecer = arquivoImagemParecer;
	}
	
	@Column(name = "co_gerente_parecer", nullable = false, length = 7)
	@TrailLog
	public String getCoGerenteParecer() {
		return coGerenteParecer;
	}

	public void setCoGerenteParecer(String coGerenteParecer) {
		this.coGerenteParecer = coGerenteParecer;
	}
}
