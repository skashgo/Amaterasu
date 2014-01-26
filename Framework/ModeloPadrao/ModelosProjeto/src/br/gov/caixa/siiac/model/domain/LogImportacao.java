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
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "iactb054_log_importacao", schema = "iacsm001")
public class LogImportacao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long nuLogImportacao;
	private Date tsImportacao;
	private String noArquivoImportado;
	private Date dtReferencia;
	private Date dtGeracao;
	private String qtRegistros;
	private String qtRegistrosIncluidos;
	private String qtRegistrosAlterados;
	private String qtRegistrosRejeitados;
	private String sgSistemaGerador;
	private Boolean icStatusImportacao;
	private String coTerminoImportacao;
	private Boolean icReprocessamento;
	private BigInteger nuChaveRegistroReprocessado;
	
	public LogImportacao() {
	}
	
	public LogImportacao(Long nuLogImportacao) {
		this.nuLogImportacao = nuLogImportacao;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq019_log_importacao")
	@SequenceGenerator(name = "iacsq019_log_importacao", sequenceName = "iacsm001.iacsq019_log_importacao")
	@Column(name = "nu_log_importacao")
	public Long getNuLogImportacao() {
		return nuLogImportacao;
	}
	
	public void setNuLogImportacao(Long nuLogImportacao) {
		this.nuLogImportacao = nuLogImportacao;
	}
	
	@Column(name = "ts_importacao")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTsImportacao() {
		return tsImportacao;
	}
	
	public void setTsImportacao(Date tsImportacao) {
		this.tsImportacao = tsImportacao;
	}
	
	@Column(name = "no_arquivo_importado")
	public String getNoArquivoImportado() {
		return noArquivoImportado;
	}
	
	public void setNoArquivoImportado(String noArquivoImportado) {
		this.noArquivoImportado = noArquivoImportado;
	}
	
	@Column(name = "dt_referencia")
	@Temporal(TemporalType.DATE)
	public Date getDtReferencia() {
		return dtReferencia;
	}
	
	public void setDtReferencia(Date dtReferencia) {
		this.dtReferencia = dtReferencia;
	}
	
	@Column(name = "dt_geracao")
	@Temporal(TemporalType.DATE)
	public Date getDtGeracao() {
		return dtGeracao;
	}
	
	public void setDtGeracao(Date dtGeracao) {
		this.dtGeracao = dtGeracao;
	}
	
	@Column(name = "qt_registros")
	public String getQtRegistros() {
		return qtRegistros;
	}
	
	public void setQtRegistros(String qtRegistros) {
		this.qtRegistros = qtRegistros;
	}
	
	@Column(name = "qt_registros_incluidos")
	public String getQtRegistrosIncluidos() {
		return qtRegistrosIncluidos;
	}
	
	public void setQtRegistrosIncluidos(String qtRegistrosIncluidos) {
		this.qtRegistrosIncluidos = qtRegistrosIncluidos;
	}
	
	@Column(name = "qt_registros_alterados")
	public String getQtRegistrosAlterados() {
		return qtRegistrosAlterados;
	}
	
	public void setQtRegistrosAlterados(String qtRegistrosAlterados) {
		this.qtRegistrosAlterados = qtRegistrosAlterados;
	}
	
	@Column(name = "qt_registros_rejeitados")
	public String getQtRegistrosRejeitados() {
		return qtRegistrosRejeitados;
	}
	
	public void setQtRegistrosRejeitados(String qtRegistrosRejeitados) {
		this.qtRegistrosRejeitados = qtRegistrosRejeitados;
	}
	
	@Column(name = "sg_sistema_gerador")
	public String getSgSistemaGerador() {
		return sgSistemaGerador;
	}
	
	public void setSgSistemaGerador(String sgSistemaGerador) {
		this.sgSistemaGerador = sgSistemaGerador;
	}
	
	@Column(name = "ic_status_importacao")
	public Boolean getIcStatusImportacao() {
		return icStatusImportacao;
	}
	
	public void setIcStatusImportacao(Boolean icStatusImportacao) {
		this.icStatusImportacao = icStatusImportacao;
	}
	
	@Column(name = "co_termino_importacao")
	public String getCoTerminoImportacao() {
		return coTerminoImportacao;
	}
	
	public void setCoTerminoImportacao(String coTerminoImportacao) {
		this.coTerminoImportacao = coTerminoImportacao;
	}
	
	@Column(name = "ic_reprocessamento")
	public Boolean getIcReprocessamento() {
		return icReprocessamento;
	}
	
	public void setIcReprocessamento(Boolean icReprocessamento) {
		this.icReprocessamento = icReprocessamento;
	}
	
	@Column(name = "nu_chave_registro_reprocessado")
	public BigInteger getNuChaveRegistroReprocessado() {
		return nuChaveRegistroReprocessado;
	}
	
	public void setNuChaveRegistroReprocessado(BigInteger nuChaveRegistroReprocessado) {
		this.nuChaveRegistroReprocessado = nuChaveRegistroReprocessado;
	}
}
