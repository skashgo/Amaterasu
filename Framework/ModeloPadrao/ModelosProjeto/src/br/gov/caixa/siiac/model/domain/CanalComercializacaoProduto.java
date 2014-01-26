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

@Entity
@Table(name = "iacvw008_canal_cmrzo_produto", schema = "icosm001")
public class CanalComercializacaoProduto implements java.io.Serializable {
	
	private static final long serialVersionUID = 4345664221L;
	private Short nuCanalCmrco;
	private String noCanalCmrco;
	private Date dtInicioCanal;
	private Date dtFimCanal;
	
	public CanalComercializacaoProduto() {
	}
	
	public CanalComercializacaoProduto(Short nuCanalCmrco, String noCanalCmrco, Date dtInicioCanal, Date dtFimCanal) {
		this.nuCanalCmrco = nuCanalCmrco;
		this.noCanalCmrco = noCanalCmrco;
		this.dtInicioCanal = dtInicioCanal;
		this.dtFimCanal = dtFimCanal;
	}
	
	@Id
	@Column(name = "nu_canal_cmrco")
	public Short getNuCanalCmrco() {
		return this.nuCanalCmrco;
	}
	
	public void setNuCanalCmrco(Short nuCanalCmrco) {
		this.nuCanalCmrco = nuCanalCmrco;
	}
	
	@Column(name = "no_canal_cmrco", length = 30)
	public String getNoCanalCmrco() {
		return this.noCanalCmrco;
	}
	
	public void setNoCanalCmrco(String noCanalCmrco) {
		this.noCanalCmrco = noCanalCmrco;
	}
	
	@Column(name = "dt_inicio_canal", length = 13)
	public Date getDtInicioCanal() {
		return this.dtInicioCanal;
	}
	
	public void setDtInicioCanal(Date dtInicioCanal) {
		this.dtInicioCanal = dtInicioCanal;
	}
	
	@Column(name = "dt_fim_canal", length = 13)
	public Date getDtFimCanal() {
		return this.dtFimCanal;
	}
	
	public void setDtFimCanal(Date dtFimCanal) {
		this.dtFimCanal = dtFimCanal;
	}
}
