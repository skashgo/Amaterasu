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

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb021_detalhes_contrato", schema = "iacsm001")
public class DetalhesContrato implements java.io.Serializable {

	private static final long serialVersionUID = -3331191741908609470L;
	private Integer nuContrato;
	private Date dtVencimentoContrato;
	private BigDecimal vrLiquidoContrato;
	private Date dtUltimaRenovacao;
	private Date dtLiquidacaoContrato;
	private Date dtInadimplenciaContrato;
	private boolean icAtivo = false;
	private Integer nuConveniado;
	private String nuIdentificadorEmpreendimento;
	private String noEmpreendimento;
	private String nuIdentificadorVendedor;
	private String noVendedor;
	private Date dtValidadeAvaliacaoTomador;
	private Date dtValidadeAvaliacaoSiric;
	
	//Campos livres
	private String noCampoLivre1;
	private String noCampoLivre2;
	private String noCampoLivre3;
	private String noCampoLivre4;
	private String noCampoLivre5;
	private String noCampoLivre6;
	private String noCampoLivre7;
	private String noCampoLivre8;
	private String noCampoLivre9;
	private String noCampoLivre10;

	public DetalhesContrato() {
	}

	public DetalhesContrato(Date dtVencimentoContrato, BigDecimal vrNominalContrato, Date dtUltimaRenovacao, Date dtLiquidacaoContrato, Date dtInadimplenciaContrato) {
		this.dtVencimentoContrato = dtVencimentoContrato;
		this.vrLiquidoContrato = vrNominalContrato;
		this.dtUltimaRenovacao = dtUltimaRenovacao;
		this.dtLiquidacaoContrato = dtLiquidacaoContrato;
		this.dtInadimplenciaContrato = dtInadimplenciaContrato;
	}

	@Id
	@Column(name = "nu_contrato", nullable = false)
	@TrailLog
	public Integer getNuContrato() {
		return this.nuContrato;
	}

	public void setNuContrato(Integer nuContrato) {
		this.nuContrato = nuContrato;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_vencimento_contrato", length = 13)
	@TrailLog
	public Date getDtVencimentoContrato() {
		return this.dtVencimentoContrato;
	}

	public void setDtVencimentoContrato(Date dtVencimentoContrato) {
		this.dtVencimentoContrato = dtVencimentoContrato;
	}

	@Column(name = "vr_liquido_contrato", precision = 16)
	@TrailLog
	public BigDecimal getVrLiquidoContrato() {
		return this.vrLiquidoContrato;
	}

	public void setVrLiquidoContrato(BigDecimal vrLiquidoContrato) {
		this.vrLiquidoContrato = vrLiquidoContrato;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_ultima_renovacao", length = 13)
	@TrailLog
	public Date getDtUltimaRenovacao() {
		return this.dtUltimaRenovacao;
	}

	public void setDtUltimaRenovacao(Date dtUltimaRenovacao) {
		this.dtUltimaRenovacao = dtUltimaRenovacao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_liquidacao_contrato", length = 13)
	@TrailLog
	public Date getDtLiquidacaoContrato() {
		return this.dtLiquidacaoContrato;
	}

	public void setDtLiquidacaoContrato(Date dtLiquidacaoContrato) {
		this.dtLiquidacaoContrato = dtLiquidacaoContrato;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_inadimplencia_contrato", length = 13)
	@TrailLog
	public Date getDtInadimplenciaContrato() {
		return this.dtInadimplenciaContrato;
	}

	public void setDtInadimplenciaContrato(Date dtInadimplenciaContrato) {
		this.dtInadimplenciaContrato = dtInadimplenciaContrato;
	}

	@Column(name = "ic_ativo", nullable = false)
	@TrailLog
	public boolean isIcAtivo() {
		return icAtivo;
	}

	public void setIcAtivo(boolean icAtivo) {
		this.icAtivo = icAtivo;
	}

	@Column(name = "nu_convenio_c23", nullable = true)
	@TrailLog
	public Integer getNuConveniado() {
		return nuConveniado;
	}

	public void setNuConveniado(Integer nuConveniado) {
		this.nuConveniado = nuConveniado;
	}

	@Column(name = "nu_identificador_empreendimento", nullable = true)
	@TrailLog
	public String getNuIdentificadorEmpreendimento() {
		return nuIdentificadorEmpreendimento;
	}

	public void setNuIdentificadorEmpreendimento(
			String nuIdentificadorEmpreendimento) {
		this.nuIdentificadorEmpreendimento = nuIdentificadorEmpreendimento;
	}

	@Column(name = "no_empreendimento", nullable = true)
	@TrailLog
	public String getNoEmpreendimento() {
		return noEmpreendimento;
	}

	public void setNoEmpreendimento(String noEmpreendimento) {
		this.noEmpreendimento = noEmpreendimento;
	}

	@Column(name = "nu_identificador_vendedor", nullable = true)
	@TrailLog
	public String getNuIdentificadorVendedor() {
		return nuIdentificadorVendedor;
	}

	public void setNuIdentificadorVendedor(String nuIdentificadorVendedor) {
		this.nuIdentificadorVendedor = nuIdentificadorVendedor;
	}

	@Column(name = "no_vendedor", nullable = true)
	@TrailLog
	public String getNoVendedor() {
		return noVendedor;
	}

	public void setNoVendedor(String noVendedor) {
		this.noVendedor = noVendedor;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_vldde_avlco_tmdr", length = 13)
	@TrailLog
	public Date getDtValidadeAvaliacaoTomador() {
		return dtValidadeAvaliacaoTomador;
	}

	public void setDtValidadeAvaliacaoTomador(Date dtValidadeAvaliacaoTomador) {
		this.dtValidadeAvaliacaoTomador = dtValidadeAvaliacaoTomador;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_vldde_avlco_siric", length = 13)
	@TrailLog
	public Date getDtValidadeAvaliacaoSiric() {
		return dtValidadeAvaliacaoSiric;
	}

	public void setDtValidadeAvaliacaoSiric(
			Date dtValidadeAvaliacaoSiric) {
		this.dtValidadeAvaliacaoSiric = dtValidadeAvaliacaoSiric;
	}

	
	//Campos Livres
	@Column(name = "no_campolivre1", length = 50)
	@TrailLog
	public String getNoCampoLivre1() {
		return noCampoLivre1;
	}

	public void setNoCampoLivre1(String noCampoLivre1) {
		this.noCampoLivre1 = noCampoLivre1;
	}

	@Column(name = "no_campolivre2", length = 50)
	@TrailLog
	public String getNoCampoLivre2() {
		return noCampoLivre2;
	}

	public void setNoCampoLivre2(String noCampoLivre2) {
		this.noCampoLivre2 = noCampoLivre2;
	}

	@Column(name = "no_campolivre3", length = 50)
	@TrailLog
	public String getNoCampoLivre3() {
		return noCampoLivre3;
	}

	public void setNoCampoLivre3(String noCampoLivre3) {
		this.noCampoLivre3 = noCampoLivre3;
	}

	@Column(name = "no_campolivre4", length = 50)
	@TrailLog
	public String getNoCampoLivre4() {
		return noCampoLivre4;
	}

	public void setNoCampoLivre4(String noCampoLivre4) {
		this.noCampoLivre4 = noCampoLivre4;
	}

	@Column(name = "no_campolivre5", length = 50)
	@TrailLog
	public String getNoCampoLivre5() {
		return noCampoLivre5;
	}

	public void setNoCampoLivre5(String noCampoLivre5) {
		this.noCampoLivre5 = noCampoLivre5;
	}

	@Column(name = "no_campolivre6", length = 50)
	@TrailLog
	public String getNoCampoLivre6() {
		return noCampoLivre6;
	}

	public void setNoCampoLivre6(String noCampoLivre6) {
		this.noCampoLivre6 = noCampoLivre6;
	}

	@Column(name = "no_campolivre7", length = 50)
	@TrailLog
	public String getNoCampoLivre7() {
		return noCampoLivre7;
	}

	public void setNoCampoLivre7(String noCampoLivre7) {
		this.noCampoLivre7 = noCampoLivre7;
	}

	@Column(name = "no_campolivre8", length = 50)
	@TrailLog
	public String getNoCampoLivre8() {
		return noCampoLivre8;
	}

	public void setNoCampoLivre8(String noCampoLivre8) {
		this.noCampoLivre8 = noCampoLivre8;
	}
	
	@Column(name = "no_campolivre9", length = 50)
	@TrailLog
	public String getNoCampoLivre9() {
		return noCampoLivre9;
	}

	public void setNoCampoLivre9(String noCampoLivre9) {
		this.noCampoLivre9 = noCampoLivre9;
	}

	@Column(name = "no_campolivre10", length = 50)
	@TrailLog
	public String getNoCampoLivre10() {
		return noCampoLivre10;
	}

	public void setNoCampoLivre10(String noCampoLivre10) {
		this.noCampoLivre10 = noCampoLivre10;
	}

}
