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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author GIS Consult
 *
 */
@Entity
public class VerificacaoContratoVO {
	public static final String VERIFICACAO_NAO_VERIFICADO= "NV";
	public static final String VERIFICACAO_INCONFORME = "IC";
	public static final String VERIFICACAO_CONFORME = "CO";
	
	private Integer nuVerificacaoContrato;
	private String noServicoVerificacao;
	private String icEstadoVerificacao;
	private Date dtLimiteVerificacao;
	private Date dtVerificacao;
	private String coRspnlVerificacao;
	private Integer nuChecklistServicoVerificacaoProduto;
	private Integer nuVerificacaoContratoPai;
	private Integer nuServicoVerificacaoProduto;
	
	public VerificacaoContratoVO() {}

	@Id
	@Column(name = "nuVerificacaoContrato")
	public Integer getNuVerificacaoContrato() {
		return nuVerificacaoContrato;
	}
	
	public void setNuVerificacaoContrato(Integer nuVerificacaoContrato) {
		this.nuVerificacaoContrato = nuVerificacaoContrato;
	}
	
	@Column(name = "noServicoVerificacao")
	public String getNoServicoVerificacao() {
		return noServicoVerificacao;
	}
	
	public void setNoServicoVerificacao(String noServicoVerificacao) {
		this.noServicoVerificacao = noServicoVerificacao;
	}
	
	@Column(name = "icEstadoVerificacao")
	public String getIcEstadoVerificacao() {
		return icEstadoVerificacao;
	}
	
	public void setIcEstadoVerificacao(String icEstadoVerificacao) {
		this.icEstadoVerificacao = icEstadoVerificacao;
	}
	
	@Column(name = "dtLimiteVerificacao")
	public Date getDtLimiteVerificacao() {
		return dtLimiteVerificacao;
	}
	
	public void setDtLimiteVerificacao(Date dtLimiteVerificacao) {
		this.dtLimiteVerificacao = dtLimiteVerificacao;
	}
	
	@Column(name = "dtVerificacao")
	public Date getDtVerificacao() {
		return dtVerificacao;
	}
	
	public void setDtVerificacao(Date dtVerificacao) {
		this.dtVerificacao = dtVerificacao;
	}
	
	@Column(name = "coRspnlVerificacao")
	public String getCoRspnlVerificacao() {
		return coRspnlVerificacao;
	}
	
	public void setCoRspnlVerificacao(String coRspnlVerificacao) {
		this.coRspnlVerificacao = coRspnlVerificacao;
	}

	@Column(name = "nuChecklistServicoVerificacaoProduto")
	public Integer getNuChecklistServicoVerificacaoProduto() {
		return nuChecklistServicoVerificacaoProduto;
	}

	public void setNuChecklistServicoVerificacaoProduto(Integer nuChecklistServicoVerificacaoProduto) {
		this.nuChecklistServicoVerificacaoProduto = nuChecklistServicoVerificacaoProduto;
	}

	@Column(name = "nuVerificacaoContratoPai")
	public Integer getNuVerificacaoContratoPai() {
		return nuVerificacaoContratoPai;
	}
	
	public void setNuVerificacaoContratoPai(Integer nuVerificacaoContratoPai) {
		this.nuVerificacaoContratoPai = nuVerificacaoContratoPai;
	}
	
	@Column(name = "nuServicoVerificacaoProduto")
	public Integer getNuServicoVerificacaoProduto(){
		return this.nuServicoVerificacaoProduto;
	}
	
	public void setNuServicoVerificacaoProduto(Integer nuServicoVerificacaoProduto) {
		this.nuServicoVerificacaoProduto = nuServicoVerificacaoProduto;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((coRspnlVerificacao == null) ? 0 : coRspnlVerificacao
						.hashCode());
		result = prime
				* result
				+ ((dtLimiteVerificacao == null) ? 0 : dtLimiteVerificacao
						.hashCode());
		result = prime * result
				+ ((dtVerificacao == null) ? 0 : dtVerificacao.hashCode());
		result = prime
				* result
				+ ((icEstadoVerificacao == null) ? 0 : icEstadoVerificacao
						.hashCode());
		result = prime
				* result
				+ ((noServicoVerificacao == null) ? 0 : noServicoVerificacao
						.hashCode());
		result = prime
				* result
				+ ((nuVerificacaoContrato == null) ? 0 : nuVerificacaoContrato
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VerificacaoContratoVO other = (VerificacaoContratoVO) obj;
		if (coRspnlVerificacao == null) {
			if (other.coRspnlVerificacao != null)
				return false;
		} else if (!coRspnlVerificacao.equals(other.coRspnlVerificacao))
			return false;
		if (dtLimiteVerificacao == null) {
			if (other.dtLimiteVerificacao != null)
				return false;
		} else if (!dtLimiteVerificacao.equals(other.dtLimiteVerificacao))
			return false;
		if (dtVerificacao == null) {
			if (other.dtVerificacao != null)
				return false;
		} else if (!dtVerificacao.equals(other.dtVerificacao))
			return false;
		if (icEstadoVerificacao == null) {
			if (other.icEstadoVerificacao != null)
				return false;
		} else if (!icEstadoVerificacao.equals(other.icEstadoVerificacao))
			return false;
		if (noServicoVerificacao == null) {
			if (other.noServicoVerificacao != null)
				return false;
		} else if (!noServicoVerificacao.equals(other.noServicoVerificacao))
			return false;
		if (nuVerificacaoContrato == null) {
			if (other.nuVerificacaoContrato != null)
				return false;
		} else if (!nuVerificacaoContrato.equals(other.nuVerificacaoContrato))
			return false;
		return true;
	}
	
	//Constantes
	public static final String INSERE_MANUAL = "0";
	public static final String INSERE_IMPORTADO = "1";
}
