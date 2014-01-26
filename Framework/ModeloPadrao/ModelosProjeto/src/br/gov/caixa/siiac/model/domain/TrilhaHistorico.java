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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author GIS Consult
 */
@Entity
@Table(name = "iactb015_trilha_historico", schema = "iacsm001")
public class TrilhaHistorico {
	
	private String nuTrilhaHistorico;
	private String coResponsavelAcao;
	private Date tsAcao;
	private String nuIpUsuario;
	private String icAcao;
	private String noAcao;
	private String deCamposVelhos;
	private String deCamposNovos;
	private String deItem;
	
	@Id
	@Column(name = "nu_trilha_historico", length = 36, nullable = false)
	public String getNuTrilhaHistorico() {
		return nuTrilhaHistorico;
	}
	
	public void setNuTrilhaHistorico(String nuTrilhaHistorico) {
		this.nuTrilhaHistorico = nuTrilhaHistorico;
	}
	
	@Column(name = "co_responsavel_acao", length = 30, nullable = false)
	public String getCoResponsavelAcao() {
		return coResponsavelAcao;
	}
	
	public void setCoResponsavelAcao(String coResponsavelAcao) {
		this.coResponsavelAcao = coResponsavelAcao;
	}
	
	@Column(name = "ts_acao", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTsAcao() {
		return tsAcao;
	}
	
	public void setTsAcao(Date tsAcao) {
		this.tsAcao = tsAcao;
	}
	
	@Column(name = "nu_ip_usuario", length = 40, nullable = false)
	public String getNuIpUsuario() {
		return nuIpUsuario;
	}
	
	public void setNuIpUsuario(String nuIpUsuario) {
		this.nuIpUsuario = nuIpUsuario;
	}
	
	@Column(name = "ic_acao", length = 20, nullable = false)
	public String getIcAcao() {
		return icAcao;
	}
	
	public void setIcAcao(String icAcao) {
		this.icAcao = icAcao;
	}
	
	@Column(name = "no_acao", length = 31, nullable = false)
	public String getNoAcao() {
		return noAcao;
	}
	
	public void setNoAcao(String noAcao) {
		this.noAcao = noAcao;
	}
	
	@Column(name = "de_campos_velhos", nullable = false)
	public String getDeCamposVelhos() {
		return deCamposVelhos;
	}
	
	public void setDeCamposVelhos(String deCamposVelhos) {
		this.deCamposVelhos = deCamposVelhos;
	}
	
	@Column(name = "de_campos_novos")
	public String getDeCamposNovos() {
		return deCamposNovos;
	}
	
	public void setDeCamposNovos(String deCamposNovos) {
		this.deCamposNovos = deCamposNovos;
	}
	
	@Column(name = "de_item")
	public String getDeItem() {
		return deItem;
	}
	
	public void setDeItem(String deItem) {
		this.deItem = deItem;
	}
	
}