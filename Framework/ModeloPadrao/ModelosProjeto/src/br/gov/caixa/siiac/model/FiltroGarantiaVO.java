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
public class FiltroGarantiaVO {
	private String filtroUnidade;
	private String filtroContrato;
	private Integer filtroTipoGarantia;
	private Integer filtroCodGarantiaOrigem;
	private BigDecimal filtroVolor1;
	private BigDecimal filtroVolor2;
	private String filtroUltimaLocalizacao;
	private String filtroLocalizacaoUltimoInventario;
	private Date filtroDataUltimoInventario;
	private boolean filtroMostrarInativos;
	
	public String getFiltroUnidade() {
		return filtroUnidade;
	}
	
	public void setFiltroUnidade(String filtroUnidade) {
		this.filtroUnidade = filtroUnidade;
	}
	
	public String getFiltroContrato() {
		return filtroContrato;
	}
	
	public void setFiltroContrato(String filtroContrato) {
		this.filtroContrato = filtroContrato;
	}
	
	public Integer getFiltroTipoGarantia() {
		return filtroTipoGarantia;
	}
	
	public void setFiltroTipoGarantia(Integer filtroTipoGarantia) {
		this.filtroTipoGarantia = filtroTipoGarantia;
	}
	
	public Integer getFiltroCodGarantiaOrigem() {
		return filtroCodGarantiaOrigem;
	}
	
	public void setFiltroCodGarantiaOrigem(Integer filtroCodGarantiaOrigem) {
		this.filtroCodGarantiaOrigem = filtroCodGarantiaOrigem;
	}
	
	public BigDecimal getFiltroVolor1() {
		return filtroVolor1;
	}
	
	public void setFiltroVolor1(BigDecimal filtroVolor1) {
		this.filtroVolor1 = filtroVolor1;
	}
	
	public BigDecimal getFiltroVolor2() {
		return filtroVolor2;
	}
	
	public void setFiltroVolor2(BigDecimal filtroVolor2) {
		this.filtroVolor2 = filtroVolor2;
	}
	
	public String getFiltroUltimaLocalizacao() {
		return filtroUltimaLocalizacao;
	}
	
	public void setFiltroUltimaLocalizacao(String filtroUltimaLocalizacao) {
		this.filtroUltimaLocalizacao = filtroUltimaLocalizacao;
	}
	
	public String getFiltroLocalizacaoUltimoInventario() {
		return filtroLocalizacaoUltimoInventario;
	}
	
	public void setFiltroLocalizacaoUltimoInventario(String filtroLocalizacaoUltimoInventario) {
		this.filtroLocalizacaoUltimoInventario = filtroLocalizacaoUltimoInventario;
	}
	
	public Date getFiltroDataUltimoInventario() {
		return filtroDataUltimoInventario;
	}
	
	public void setFiltroDataUltimoInventario(Date filtroDataUltimoInventario) {
		this.filtroDataUltimoInventario = filtroDataUltimoInventario;
	}
	
	public boolean isFiltroMostrarInativos() {
		return filtroMostrarInativos;
	}
	
	public void setFiltroMostrarInativos(boolean filtroMostrarInativos) {
		this.filtroMostrarInativos = filtroMostrarInativos;
	}
	
}
