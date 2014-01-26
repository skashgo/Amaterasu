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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author GIS Consult
 * 
 */
public class FiltroVerificacaoPreventivaVO {
	private String codProduto;
	private String codUnidade;
	private String codCanalComercializacao;
	private String filtroOperacao;
	private String filtroContrato;
	private String filtroAvalSiric;
	private String filtroAvalCliente;
	private String codServicoVerificacao;
	private String filtroUsuarioResp;
	private String filtroCodCliente;
	private String filtroNomeCliente;
	private String icPfPj;
	private String codSituacao;
	private String nuIdentificadorClienteFiltro;
	private String filtroCodEmpreendimento;
	private String filtroNomeEmpreendimento;
	private String filtroCodVendedor;
	private String filtroNomeVendedor;
	private String filtroCodPrimeiroCoObrigado;
	private String filtroCodSegundoCoObrigado;
	private String filtroNomePrimeiroCoObrigado;
	private String filtroNomeSegundoCoObrigado;
	private String filtroNumeroDamp;
	private String icTipoIndentificadorCliente;
	private BigDecimal valorInicialFiltro;
	private BigDecimal valorFinalFiltro;
	private Date filtroDataIniInclusao;
	private Date filtroDataFimInclusao;
	private Date filtroDataIniVerificacao;
	private Date filtroDataFimVerificacao;
	private BigDecimal valorRessarcimento;
	private Date dataRessarcimentoDamp;
	private Integer codConveniado;
	private String icSituacaoContrato;
	private boolean produtosPreferenciais;
	private boolean somenteSuspensas;
	private String nomeConveniado;
		
	
	public FiltroVerificacaoPreventivaVO(String codProduto, String codUnidade,
			String codCanalComercializacao, String filtroOperacao,
			String filtroContrato, String filtroAvalSiric,
			String filtroAvalCliente, String codServicoVerificacao,
			String filtroUsuarioResp, String filtroCodCliente,
			String filtroNomeCliente, String icPfPj, String codSituacao,
			String nuIdentificadorClienteFiltro,
			String filtroCodEmpreendimento, String filtroNomeEmpreendimento,
			String filtroCodPrimeiroCoObrigado,
			String filtroCodSegundoCoObrigado,
			String filtroNomePrimeiroCoObrigado,
			String filtroNomeSegundoCoObrigado, String filtroNumeroDamp,
			String icTipoIndentificadorCliente, BigDecimal valorInicialFiltro,
			BigDecimal valorFinalFiltro, Date filtroDataIniInclusao,
			Date filtroDataFimInclusao, Date filtroDataIniVerificacao,
			Date filtroDataFimVerificacao, BigDecimal valorRessarcimento,
			Date dataRessarcimentoDamp, Integer codConveniado, String icSituacaoContrato,
			String filtroCodVendedor, String filtroNomeVendedor,
			String nomeConveniado) {
		
		this.codProduto = codProduto;
		this.codUnidade = codUnidade;
		this.codCanalComercializacao = codCanalComercializacao;
		this.filtroOperacao = filtroOperacao;
		this.filtroContrato = filtroContrato;
		this.filtroAvalSiric = filtroAvalSiric;
		this.filtroAvalCliente = filtroAvalCliente;
		this.codServicoVerificacao = codServicoVerificacao;
		this.filtroUsuarioResp = filtroUsuarioResp;
		this.filtroCodCliente = filtroCodCliente;
		this.filtroNomeCliente = filtroNomeCliente;
		this.icPfPj = icPfPj;
		this.codSituacao = codSituacao;
		this.nuIdentificadorClienteFiltro = nuIdentificadorClienteFiltro;
		this.filtroCodEmpreendimento = filtroCodEmpreendimento;
		this.filtroNomeEmpreendimento = filtroNomeEmpreendimento;
		this.filtroCodPrimeiroCoObrigado = filtroCodPrimeiroCoObrigado;
		this.filtroCodSegundoCoObrigado = filtroCodSegundoCoObrigado;
		this.filtroNomePrimeiroCoObrigado = filtroNomePrimeiroCoObrigado;
		this.filtroNomeSegundoCoObrigado = filtroNomeSegundoCoObrigado;
		this.filtroNumeroDamp = filtroNumeroDamp;
		this.icTipoIndentificadorCliente = icTipoIndentificadorCliente;
		this.valorInicialFiltro = valorInicialFiltro;
		this.valorFinalFiltro = valorFinalFiltro;
		this.filtroDataIniInclusao = filtroDataIniInclusao;
		this.filtroDataFimInclusao = filtroDataFimInclusao;
		this.filtroDataIniVerificacao = filtroDataIniVerificacao;
		this.filtroDataFimVerificacao = filtroDataFimVerificacao;
		this.valorRessarcimento = valorRessarcimento;
		this.dataRessarcimentoDamp = dataRessarcimentoDamp;
		this.codConveniado = codConveniado;
		this.icSituacaoContrato = icSituacaoContrato;
		this.filtroCodVendedor = filtroCodVendedor;
		this.filtroNomeVendedor = filtroNomeVendedor;
		this.nomeConveniado = nomeConveniado;
	}
	

	

	public FiltroVerificacaoPreventivaVO(String codProduto, String codUnidade,
			String filtroContrato, String filtroAvalSiric,
			String filtroAvalCliente, String codServicoVerificacao,
			String filtroUsuarioResp, String icSituacaoContrato,
			String nuIdentificadorClienteFiltro,
			String filtroCodEmpreendimento, String filtroNomeEmpreendimento,
			Date filtroDataIniInclusao, Date filtroDataFimInclusao,
			Date filtroDataIniVerificacao, Date filtroDataFimVerificacao,
			Integer codConveniado, boolean somenteSuspensas,
			String nomeConveniado) {
		this.codProduto = codProduto;
		this.codUnidade = codUnidade;
		this.filtroContrato = filtroContrato;
		this.filtroAvalSiric = filtroAvalSiric;
		this.filtroAvalCliente = filtroAvalCliente;
		this.codServicoVerificacao = codServicoVerificacao;
		this.filtroUsuarioResp = filtroUsuarioResp;
		this.icSituacaoContrato = icSituacaoContrato;
		this.nuIdentificadorClienteFiltro = nuIdentificadorClienteFiltro;
		this.filtroCodEmpreendimento = filtroCodEmpreendimento;
		this.filtroNomeEmpreendimento = filtroNomeEmpreendimento;
		this.filtroDataIniInclusao = filtroDataIniInclusao;
		this.filtroDataFimInclusao = filtroDataFimInclusao;
		this.filtroDataIniVerificacao = filtroDataIniVerificacao;
		this.filtroDataFimVerificacao = filtroDataFimVerificacao;
		this.codConveniado = codConveniado;
		this.somenteSuspensas = somenteSuspensas;
		this.nomeConveniado = nomeConveniado;
	}




	public FiltroVerificacaoPreventivaVO() {
	}

	public String getCodProduto() {
		return codProduto;
	}
	
	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}
	
	public String getCodUnidade() {
		return codUnidade;
	}
	
	public void setCodUnidade(String codUnidade) {
		this.codUnidade = codUnidade;
	}
	
	public String getCodCanalComercializacao() {
		return codCanalComercializacao;
	}
	
	public void setCodCanalComercializacao(String codCanalComercializacao) {
		this.codCanalComercializacao = codCanalComercializacao;
	}
	
	public String getFiltroOperacao() {
		return filtroOperacao;
	}
	
	public void setFiltroOperacao(String filtroOperacao) {
		this.filtroOperacao = filtroOperacao;
	}
	
	public String getFiltroContrato() {
		return filtroContrato;
	}
	
	public void setFiltroContrato(String filtroContrato) {
		this.filtroContrato = filtroContrato;
	}
	
	public String getFiltroAvalSiric() {
		return filtroAvalSiric;
	}
	
	public void setFiltroAvalSiric(String filtroAvalSiric) {
		this.filtroAvalSiric = filtroAvalSiric;
	}
	
	public String getFiltroAvalCliente() {
		return filtroAvalCliente;
	}
	
	public void setFiltroAvalCliente(String filtroAvalCliente) {
		this.filtroAvalCliente = filtroAvalCliente;
	}
	
	public String getCodServicoVerificacao() {
		return codServicoVerificacao;
	}
	
	public void setCodServicoVerificacao(String codServicoVerificacao) {
		this.codServicoVerificacao = codServicoVerificacao;
	}
	
	public String getFiltroUsuarioResp() {
		return filtroUsuarioResp;
	}
	
	public void setFiltroUsuarioResp(String filtroUsuarioResp) {
		this.filtroUsuarioResp = filtroUsuarioResp;
	}
	
	public String getFiltroCodCliente() {
		return filtroCodCliente;
	}
	
	public void setFiltroCodCliente(String filtroCodCliente) {
		this.filtroCodCliente = filtroCodCliente;
	}
	
	public String getFiltroNomeCliente() {
		return filtroNomeCliente;
	}
	
	public void setFiltroNomeCliente(String filtroNomeCliente) {
		this.filtroNomeCliente = filtroNomeCliente;
	}
	
	public String getIcPfPj() {
		return icPfPj;
	}
	
	public void setIcPfPj(String icPfPj) {
		this.icPfPj = icPfPj;
	}
	
	public String getCodSituacao() {
		return codSituacao;
	}
	
	public void setCodSituacao(String codSituacao) {
		this.codSituacao = codSituacao;
	}
	
	public String getNuIdentificadorClienteFiltro() {
		return nuIdentificadorClienteFiltro;
	}
	
	public void setNuIdentificadorClienteFiltro(
			String nuIdentificadorClienteFiltro) {
		this.nuIdentificadorClienteFiltro = nuIdentificadorClienteFiltro;
	}
	
	public String getFiltroCodEmpreendimento() {
		return filtroCodEmpreendimento;
	}
	
	public void setFiltroCodEmpreendimento(String filtroCodEmpreendimento) {
		this.filtroCodEmpreendimento = filtroCodEmpreendimento;
	}
	
	public String getFiltroNomeEmpreendimento() {
		return filtroNomeEmpreendimento;
	}
	
	public void setFiltroNomeEmpreendimento(String filtroNomeEmpreendimento) {
		this.filtroNomeEmpreendimento = filtroNomeEmpreendimento;
	}
	
	public String getFiltroCodPrimeiroCoObrigado() {
		return filtroCodPrimeiroCoObrigado;
	}
	
	public void setFiltroCodPrimeiroCoObrigado(
			String filtroCodPrimeiroCoObrigado) {
		this.filtroCodPrimeiroCoObrigado = filtroCodPrimeiroCoObrigado;
	}
	
	public String getFiltroCodSegundoCoObrigado() {
		return filtroCodSegundoCoObrigado;
	}
	
	public void setFiltroCodSegundoCoObrigado(String filtroCodSegundoCoObrigado) {
		this.filtroCodSegundoCoObrigado = filtroCodSegundoCoObrigado;
	}
	
	public String getFiltroNomePrimeiroCoObrigado() {
		return filtroNomePrimeiroCoObrigado;
	}
	
	public void setFiltroNomePrimeiroCoObrigado(
			String filtroNomePrimeiroCoObrigado) {
		this.filtroNomePrimeiroCoObrigado = filtroNomePrimeiroCoObrigado;
	}
	
	public String getFiltroNomeSegundoCoObrigado() {
		return filtroNomeSegundoCoObrigado;
	}
	
	public void setFiltroNomeSegundoCoObrigado(
			String filtroNomeSegundoCoObrigado) {
		this.filtroNomeSegundoCoObrigado = filtroNomeSegundoCoObrigado;
	}
	
	public String getFiltroNumeroDamp() {
		return filtroNumeroDamp;
	}
	
	public void setFiltroNumeroDamp(String filtroNumeroDamp) {
		this.filtroNumeroDamp = filtroNumeroDamp;
	}
	
	public String getIcTipoIndentificadorCliente() {
		return icTipoIndentificadorCliente;
	}
	
	public void setIcTipoIndentificadorCliente(
			String icTipoIndentificadorCliente) {
		this.icTipoIndentificadorCliente = icTipoIndentificadorCliente;
	}
	
	public BigDecimal getValorInicialFiltro() {
		return valorInicialFiltro;
	}
	
	public void setValorInicialFiltro(BigDecimal valorInicialFiltro) {
		this.valorInicialFiltro = valorInicialFiltro;
	}
	
	public BigDecimal getValorFinalFiltro() {
		return valorFinalFiltro;
	}
	
	public void setValorFinalFiltro(BigDecimal valorFinalFiltro) {
		this.valorFinalFiltro = valorFinalFiltro;
	}
	
	public Date getFiltroDataIniInclusao() {
		return filtroDataIniInclusao;
	}
	
	public void setFiltroDataIniInclusao(Date filtroDataIniInclusao) {
		this.filtroDataIniInclusao = filtroDataIniInclusao;
	}
	
	public Date getFiltroDataFimInclusao() {
		return filtroDataFimInclusao;
	}
	
	public void setFiltroDataFimInclusao(Date filtroDataFimInclusao) {
		this.filtroDataFimInclusao = filtroDataFimInclusao;
	}
	
	public Date getFiltroDataIniVerificacao() {
		return filtroDataIniVerificacao;
	}
	
	public void setFiltroDataIniVerificacao(Date filtroDataIniVerificacao) {
		this.filtroDataIniVerificacao = filtroDataIniVerificacao;
	}
	
	public Date getFiltroDataFimVerificacao() {
		return filtroDataFimVerificacao;
	}
	
	public void setFiltroDataFimVerificacao(Date filtroDataFimVerificacao) {
		this.filtroDataFimVerificacao = filtroDataFimVerificacao;
	}

	public BigDecimal getValorRessarcimento() {
		return valorRessarcimento;
	}

	public void setValorRessarcimento(BigDecimal valorRessarcimento) {
		this.valorRessarcimento = valorRessarcimento;
	}

	public Date getDataRessarcimentoDamp() {
		return dataRessarcimentoDamp;
	}

	public void setDataRessarcimentoDamp(Date dataRessarcimentoDamp) {
		this.dataRessarcimentoDamp = dataRessarcimentoDamp;
	}

	public Integer getCodConveniado() {
		return codConveniado;
	}

	public void setCodConveniado(Integer codConveniado) {
		this.codConveniado = codConveniado;
	}

	public String getIcSituacaoContrato() {
		return icSituacaoContrato;
	}

	public void setIcSituacaoContrato(String icSituacaoContrato) {
		this.icSituacaoContrato = icSituacaoContrato;
	}

	public String getFiltroCodVendedor() {
		return filtroCodVendedor;
	}

	public void setFiltroCodVendedor(String filtroCodVendedor) {
		this.filtroCodVendedor = filtroCodVendedor;
	}

	public String getFiltroNomeVendedor() {
		return filtroNomeVendedor;
	}

	public void setFiltroNomeVendedor(String filtroNomeVendedor) {
		this.filtroNomeVendedor = filtroNomeVendedor;
	}


	public boolean isProdutosPreferenciais() {
		return produtosPreferenciais;
	}


	public void setProdutosPreferenciais(boolean produtosPreferenciais) {
		this.produtosPreferenciais = produtosPreferenciais;
	}


	public boolean isSomenteSuspensas() {
		return somenteSuspensas;
	}


	public void setSomenteSuspensas(boolean somenteSuspensas) {
		this.somenteSuspensas = somenteSuspensas;
	}


	public String getNomeConveniado() {
		return nomeConveniado;
	}


	public void setNomeConveniado(String nomeConveniado) {
		this.nomeConveniado = nomeConveniado;
	}
	
	
}
