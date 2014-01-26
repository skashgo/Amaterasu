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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb019_template_parecer", schema = "iacsm001")
@TrailClass(fonte = "MODELO_PARECER")
public class TemplateParecer implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer nuTemplateParecer;
	private Boolean icTipoParecer;
	private String noAssuntoParecer;
	private String noReferenciaParecer;
	private String textoParecer;
	private ServicoVerificacaoProduto servicoVerificacaoProduto;
	private Boolean icAtivo;
	private List<TemplateParecerDestinatarios> listaTemplateParecerDestinatarios = new ArrayList<TemplateParecerDestinatarios>();
	
	
	public TemplateParecer() {
	}
	
	public TemplateParecer(Integer nuTemplateParecer) {
		this.nuTemplateParecer = nuTemplateParecer;
	}
	
	public TemplateParecer(Integer nuTemplateParecer, Short nuUnidadeResponsavelParecer, int nuNaturalUnidadeResponsavelParecer, Boolean icTipoParecer, String noAssuntoParecer, String coAssinatura1) {
		this.nuTemplateParecer = nuTemplateParecer;
		this.icTipoParecer = icTipoParecer;
		this.noAssuntoParecer = noAssuntoParecer;
	}
	
	@Id
	@Column(name = "nu_template_parecer", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq023_template_parecer")
	@SequenceGenerator(name = "iacsq023_template_parecer", sequenceName = "iacsm001.iacsq023_template_parecer")
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public Integer getNuTemplateParecer() {
		return nuTemplateParecer;
	}
	
	public void setNuTemplateParecer(Integer nuTemplateParecer) {
		this.nuTemplateParecer = nuTemplateParecer;
	}
	
	@Column(name = "ic_tipo_parecer", nullable = false)
	@TrailLog
	public Boolean getIcTipoParecer() {
		return icTipoParecer;
	}
	
	public void setIcTipoParecer(Boolean icTipoParecer) {
		this.icTipoParecer = icTipoParecer;
	}
	
	@Column(name = "no_assunto_parecer", nullable = false)
	@TrailLog
	public String getNoAssuntoParecer() {
		return noAssuntoParecer;
	}
	
	public void setNoAssuntoParecer(String noAssuntoParecer) {
		this.noAssuntoParecer = noAssuntoParecer;
	}
	
	@Column(name = "no_referencia_parecer", length = 70)
	@TrailLog
	public String getNoReferenciaParecer() {
		return noReferenciaParecer;
	}
	
	public void setNoReferenciaParecer(String noReferenciaParecer) {
		this.noReferenciaParecer = noReferenciaParecer;
	}
	
	
	@JoinColumn(name = "nu_servico_verificacao_produto", referencedColumnName = "nu_servico_verificacao_produto", nullable = false)
	@ManyToOne(optional = false)
	public ServicoVerificacaoProduto getServicoVerificacaoProduto() {
		return servicoVerificacaoProduto;
	}
	
	public void setServicoVerificacaoProduto(ServicoVerificacaoProduto nuServicoVerificacaoProduto) {
		this.servicoVerificacaoProduto = nuServicoVerificacaoProduto;
	}

	@Transient
	@TrailLog(deleteID = true, insertID = true, updateID = true, name = "nu_servico_verificacao_produto")
	public Integer getServicoVerificacaoProdutoTrilha(){
		if(servicoVerificacaoProduto != null){
			return servicoVerificacaoProduto.getNuServicoVerificacaoProduto();
		}
		return null;
	}
	
	public void setTextoParecer(String textoParecer) {
		this.textoParecer = textoParecer;
	}

	@Column(name = "de_texto_parecer",nullable=false)
	@TrailLog
	public String getTextoParecer() {
		return textoParecer;
	}
	
	@Column(name = "ic_ativo", nullable = false)
	@TrailLog
	public Boolean isIcAtivo() {
		return this.icAtivo;
	}
	
	public void setIcAtivo(Boolean icAtivo) {
		this.icAtivo = icAtivo;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "templateParecer", cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<TemplateParecerDestinatarios> getListaTemplateParecerDestinatarios() {
		return listaTemplateParecerDestinatarios;
	}

	public void setListaTemplateParecerDestinatarios(
			List<TemplateParecerDestinatarios> listaTemplateParecerDestinatarios) {
		this.listaTemplateParecerDestinatarios = listaTemplateParecerDestinatarios;
	}


	/*
	 * Variável auxiliar para apresentação no Grid
	 */
	private static final String PARECER_CONFORME = "CONFORME";
	private static final String PARECER_INCONFORME = "INCONFORME";
	
	@Transient
	public String getStrTipoParecer()
	{
		String tipoParecer = getIcTipoParecer() ? PARECER_CONFORME : PARECER_INCONFORME;
				
		return tipoParecer;
	}
}
