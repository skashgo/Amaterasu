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

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author GIS Consult
 *
 */
@Entity
@Table(name = "iactb039_undde_ntfda_vrfco", schema = "iacsm001")
public class UnidadeNotificadaVerificacao {

	private static final long serialVersionUID = 29340948230L;
	private String nuNotificacao;
	private TemplateNotificacao templateNotificacao;
	private Date dtEnvioNotificacao;
	private Character icNivelUnidadeNoTificada;
	private Short nuNaturalUnidadeNotificadaVerificacao;
	private Short nuUnidadeNotificadaVerificacao;
	private ServicoVerificacaoProduto servicoVerificacaoProduto;
	private VerificacaoContrato verificacaoContrato;

	public UnidadeNotificadaVerificacao() {

	}
	
	public UnidadeNotificadaVerificacao(String nuNotificacao,
			TemplateNotificacao templateNotificacao,
			Date dtEnvioNotificacao, Character icNivelUnidadeNoTificada,
			Short nuNaturalUnidadeNotificadaVerificacao,
			Short nuUnidadeNotificadaVerificacao,
			ServicoVerificacaoProduto servicoVerificacaoProduto,
			VerificacaoContrato verificacaoContrato) {

		this.nuNotificacao = nuNotificacao;
		this.templateNotificacao = templateNotificacao;
		this.dtEnvioNotificacao = dtEnvioNotificacao;
		this.icNivelUnidadeNoTificada = icNivelUnidadeNoTificada;
		this.nuNaturalUnidadeNotificadaVerificacao = nuNaturalUnidadeNotificadaVerificacao;
		this.nuUnidadeNotificadaVerificacao = nuUnidadeNotificadaVerificacao;
		this.servicoVerificacaoProduto = servicoVerificacaoProduto;
		this.verificacaoContrato = verificacaoContrato;
	}

	@Id
	@Column(name="nu_notificacao", nullable=false)
	public String getNuNotificacao() {
		return nuNotificacao;
	}

	public void setNuNotificacao(String nuNotificacao) {
		this.nuNotificacao = nuNotificacao;
	}

	@Column(name="dt_envio_notificacao", nullable=false)
	public Date getDtEnvioNotificacao() {
		return dtEnvioNotificacao;
	}

	public void setDtEnvioNotificacao(Date dtEnvioNotificacao) {
		this.dtEnvioNotificacao = dtEnvioNotificacao;
	}

	@Column(name="ic_nivel_unidade_notificada", nullable=false)
	public Character getIcNivelUnidadeNoTificada() {
		return icNivelUnidadeNoTificada;
	}

	public void setIcNivelUnidadeNoTificada(Character icNivelUnidadeNoTificada) {
		this.icNivelUnidadeNoTificada = icNivelUnidadeNoTificada;
	}

	@Column(name="nu_natural_undde_ntfda_verfco", nullable=false)
	public Short getNuNaturalUnidadeNotificadaVerificacao() {
		return nuNaturalUnidadeNotificadaVerificacao;
	}

	public void setNuNaturalUnidadeNotificadaVerificacao(
			Short nuNaturalUnidadeNotificadaVerificacao) {
		this.nuNaturalUnidadeNotificadaVerificacao = nuNaturalUnidadeNotificadaVerificacao;
	}

	@Column(name="nu_undde_notificada_verificacao", nullable=false)
	public Short getNuUnidadeNotificadaVerificacao() {
		return nuUnidadeNotificadaVerificacao;
	}

	public void setNuUnidadeNotificadaVerificacao(
			Short nuUnidadeNotificadaVerificacao) {
		this.nuUnidadeNotificadaVerificacao = nuUnidadeNotificadaVerificacao;
	}

	@JoinColumn(name = "nu_template_notificacao", referencedColumnName = "nu_template_notificacao", nullable = false)
	@ManyToOne
	public TemplateNotificacao getTemplateNotificacao() {
		return templateNotificacao;
	}

	public void setTemplateNotificacao(TemplateNotificacao templateNotificacao) {
		this.templateNotificacao = templateNotificacao;
	}

	@JoinColumn(name = "nu_servico_verificacao_produto", referencedColumnName = "nu_servico_verificacao_produto", nullable = false)
	@ManyToOne
	public ServicoVerificacaoProduto getServicoVerificacaoProduto() {
		return servicoVerificacaoProduto;
	}

	public void setServicoVerificacaoProduto(ServicoVerificacaoProduto servicoVerificacaoProduto) {
		this.servicoVerificacaoProduto = servicoVerificacaoProduto;
	}

	@JoinColumn(name = "nu_verificacao_contrato", referencedColumnName = "nu_verificacao_contrato", nullable = false)
	@ManyToOne
	public VerificacaoContrato getVerificacaoContrato() {
		return verificacaoContrato;
	}

	public void setVerificacaoContrato(VerificacaoContrato verificacaoContrato) {
		this.verificacaoContrato = verificacaoContrato;
	}

}
