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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.gov.caixa.siiac.model.domain.CategoriaProduto;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuario;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuarioCategoriaProduto;
import br.gov.caixa.siiac.model.domain.Produto;

/**
 * @author GIS Consult Esta classe é usada pelo
 *         br.gov.caixa.siiac.bo.impl.ContratoBO. Ela era uma instância desta
 *         classe e preenche seus atributos com os parâmetros necessários para
 *         fazer o filtro solicitado pelo usuário.
 */
public class ContratoVO {

	// Controle de tipo de filtro a ser usado
	private Integer tipoFiltro;
	public static final Integer SIMPLE_FILTER = 1;
	public static final Integer ADVANCED_FILTER = 2;

	private Boolean produtosPreferenciais;

	// Filtros para quando for Filtro Avançado
	private Contrato contrato;
	private String coUsuario;
	private Short nuUnidade;
	private List<Short> unidades;
	private List<PreferenciasUsuario> preferenciasUsuario = new ArrayList<PreferenciasUsuario>();
	private List<CategoriaProduto> usuarioCategoria = new ArrayList<CategoriaProduto>();
	private List<Produto> preferenciasUsuarioStr = new ArrayList<Produto>();
	private List<BigDecimal> faixa = new ArrayList<BigDecimal>();
	private Date dataInclusaoInicio;
	private Date dataInclusaoFim;
	private Date dataVerificacaoInicio;
	private Date dataVerificacaoFim;
	private Boolean icSuspensa;
	private Integer nuServicoVerificacao;
	private Short campoOperacao;
	private Boolean existeProdutosUsuario = false;
	private String icSituacaoContrato;
	private List<Integer> nuConveniados = new ArrayList<Integer>();
	
	private BigDecimal valorInicialFiltro;
	private BigDecimal valorFinalFiltro;
		

	// Filtros para quando for Filtro Simples
	private String pesquisaSimples;
	private Boolean mostraInativos;

	public ContratoVO() {
		setSimpleFilter();
	}

	// Controle de Tipo de Filtro
	public boolean isSimpleFilter() {
		return ContratoVO.SIMPLE_FILTER.equals(getTipoFiltro());
	}
	public void setSimpleFilter() {
		this.setTipoFiltro(ContratoVO.SIMPLE_FILTER);
	}
	public boolean isAdvancedFilter() {
		return ContratoVO.ADVANCED_FILTER.equals(getTipoFiltro());
	}
	public void setAdvancedFilter() {
		this.setTipoFiltro(ContratoVO.ADVANCED_FILTER);
	}

	// Getters and Setters
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	public String getCoUsuario() {
		return coUsuario;
	}
	public void setCoUsuario(String coUsuario) {
		this.coUsuario = coUsuario;
	}
	public Short getNuUnidade() {
		return nuUnidade;
	}
	public void setNuUnidade(Short nuUnidade) {
		this.nuUnidade = nuUnidade;
	}
	public List<Short> getUnidades() {
		return unidades;
	}
	public void setUnidades(List<Short> unidades) {
		this.unidades = unidades;
	}
	public String getPesquisaSimples() {
		return pesquisaSimples;
	}
	public void setPesquisaSimples(String pesquisaSimples) {
		this.pesquisaSimples = pesquisaSimples;
	}
	public Boolean getMostraInativos() {
		return mostraInativos;
	}
	public void setMostraInativos(Boolean mostraInativos) {
		this.mostraInativos = mostraInativos;
	}
	private Integer getTipoFiltro() {
		return tipoFiltro;
	}
	private void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}
	public List<BigDecimal> getFaixa() {
		return this.faixa;
	}
	public void setFaixa(List<BigDecimal> faixa) {
		this.faixa = faixa;
	}
	public void addFaixa(BigDecimal faixa) {
		this.faixa.add(faixa);
	}
	public void addAllFaixa(BigDecimal... faixa) {
		for (int i = 0; i < faixa.length; i++) {
			this.faixa.add(faixa[i]);
		}
	}

	public Boolean getProdutosPreferenciais() {
		return produtosPreferenciais;
	}

	public void setProdutosPreferenciais(Boolean produtosPreferenciais) {
		this.produtosPreferenciais = produtosPreferenciais;
	}

	public List<PreferenciasUsuario> getPreferenciasUsuario() {
		return preferenciasUsuario;
	}

	public void setPreferenciasUsuario(
			List<PreferenciasUsuario> preferenciasUsuario) {
		this.preferenciasUsuario = preferenciasUsuario;
	}

	public List<Produto> getPreferenciasUsuarioStr() {
		return preferenciasUsuarioStr;
	}

	public void setPreferenciasUsuarioStr(List<Produto> preferenciasUsuarioStr) {
		this.preferenciasUsuarioStr = preferenciasUsuarioStr;
	}

	public Date getDataInclusaoInicio() {
		return dataInclusaoInicio;
	}

	public void setDataInclusaoInicio(Date dataInclusaoInicio) {
		this.dataInclusaoInicio = dataInclusaoInicio;
	}

	public Date getDataInclusaoFim() {
		return dataInclusaoFim;
	}

	public void setDataInclusaoFim(Date dataInclusaoFim) {
		this.dataInclusaoFim = dataInclusaoFim;
	}

	public Boolean getIcSuspensa() {
		return icSuspensa;
	}

	public void setIcSuspensa(Boolean icSuspensa) {
		this.icSuspensa = icSuspensa;
	}

	public Integer getNuServicoVerificacao() {
		return nuServicoVerificacao;
	}

	public void setNuServicoVerificacao(Integer nuServicoVerificacao) {
		this.nuServicoVerificacao = nuServicoVerificacao;
	}

	public Date getDataVerificacaoInicio() {
		return dataVerificacaoInicio;
	}

	public void setDataVerificacaoInicio(Date dataVerificacaoInicio) {
		this.dataVerificacaoInicio = dataVerificacaoInicio;
	}

	public Date getDataVerificacaoFim() {
		return dataVerificacaoFim;
	}

	public void setDataVerificacaoFim(Date dataVerificacaoFim) {
		this.dataVerificacaoFim = dataVerificacaoFim;
	}

	public Boolean getExisteProdutosUsuario() {
		return existeProdutosUsuario;
	}

	public void setExisteProdutosUsuario(Boolean existeProdutosUsuario) {
		this.existeProdutosUsuario = existeProdutosUsuario;
	}

	public Short getCampoOperacao() {
		return campoOperacao;
	}

	public void setCampoOperacao(Short campoOperacao) {
		this.campoOperacao = campoOperacao;
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

	public List<CategoriaProduto> getUsuarioCategoria() {
		return usuarioCategoria;
	}

	public void setUsuarioCategoria(
			List<CategoriaProduto> usuarioCategoria) {
		this.usuarioCategoria = usuarioCategoria;
	}

	public String getIcSituacaoContrato() {
		return icSituacaoContrato;
	}

	public void setIcSituacaoContrato(String icSituacaoContrato) {
		this.icSituacaoContrato = icSituacaoContrato;
	}

	public List<Integer> getNuConveniados() {
		return nuConveniados;
	}

	public void setNuConveniados(List<Integer> nuConveniados) {
		this.nuConveniados = nuConveniados;
	}
}