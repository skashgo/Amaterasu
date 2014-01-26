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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "iactb023_log_arquivo_retorno", schema = "iacsm001")
public class LogArquivoRetorno implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long nuLogArquivoRetorno;
	private Date tsFimProcessamento;
	private String noArquivoGerado;
	private Date dtReferencia;
	private Date dtProcessamento;
	private Long qtRegistros;
	private String sgSistemaDestino;
	private Boolean icStatusProcessamento;
	private String coTerminoProcessamento;
	private Boolean icReprocessamento;
	private BigInteger nuChaveRegistroReprocessado;
	
	public LogArquivoRetorno() {
	}
	
	public LogArquivoRetorno(Long nuLogArquivoRetorno) {
		this.nuLogArquivoRetorno = nuLogArquivoRetorno;
	}
	
	@Id
	@Column(name = "nu_log_arquivo_retorno")
	public Long getNuLogArquivoRetorno() {
		return nuLogArquivoRetorno;
	}
	
	public void setNuLogArquivoRetorno(Long nuLogArquivoRetorno) {
		this.nuLogArquivoRetorno = nuLogArquivoRetorno;
	}
	
	@Column(name = "ts_fim_processamento")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTsFimProcessamento() {
		return tsFimProcessamento;
	}
	
	public void setTsFimProcessamento(Date tsFimProcessamento) {
		this.tsFimProcessamento = tsFimProcessamento;
	}
	
	@Column(name = "no_arquivo_gerado")
	public String getNoArquivoGerado() {
		return noArquivoGerado;
	}
	
	public void setNoArquivoGerado(String noArquivoGerado) {
		this.noArquivoGerado = noArquivoGerado;
	}
	
	@Column(name = "dt_referencia")
	@Temporal(TemporalType.DATE)
	public Date getDtReferencia() {
		return dtReferencia;
	}
	
	public void setDtReferencia(Date dtReferencia) {
		this.dtReferencia = dtReferencia;
	}
	
	@Column(name = "dt_processamento")
	@Temporal(TemporalType.DATE)
	public Date getDtProcessamento() {
		return dtProcessamento;
	}
	
	public void setDtProcessamento(Date dtProcessamento) {
		this.dtProcessamento = dtProcessamento;
	}
	
	@Column(name = "qt_registros")
	public Long getQtRegistros() {
		return qtRegistros;
	}
	
	public void setQtRegistros(Long qtRegistros) {
		this.qtRegistros = qtRegistros;
	}
	
	@Column(name = "sg_sistema_destino")
	public String getSgSistemaDestino() {
		return sgSistemaDestino;
	}
	
	public void setSgSistemaDestino(String sgSistemaDestino) {
		this.sgSistemaDestino = sgSistemaDestino;
	}
	
	@Column(name = "ic_status_processamento")
	public Boolean getIcStatusProcessamento() {
		return icStatusProcessamento;
	}
	
	public void setIcStatusProcessamento(Boolean icStatusProcessamento) {
		this.icStatusProcessamento = icStatusProcessamento;
	}
	
	@Column(name = "co_termino_processamento")
	public String getCoTerminoProcessamento() {
		return coTerminoProcessamento;
	}
	
	public void setCoTerminoProcessamento(String coTerminoProcessamento) {
		this.coTerminoProcessamento = coTerminoProcessamento;
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
