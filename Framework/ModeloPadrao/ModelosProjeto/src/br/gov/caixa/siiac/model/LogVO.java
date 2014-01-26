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

/**
 * @author GIS Consult
 *
 */
public class LogVO {
	private Integer idLogImportacao;
	private Date tsImportacao;
	private Boolean icStatusImportado;
	private String coTerminoImportacao;
	private String noArquivoImportado;
	private Date dtReferencia;
	private Date dtGeracao;
	private Integer qtRegistros;
	private Integer qtRegistrosIncluidos;
	private Integer qtRegistrosAlterados;
	private String sgSistemaGerador;
	
	public Integer getIdLogImportacao() {
		return idLogImportacao;
	}
	
	public void setIdLogImportacao(Integer idLogImportacao) {
		this.idLogImportacao = idLogImportacao;
	}
	
	public Date getTsImportacao() {
		return tsImportacao;
	}
	
	public void setTsImportacao(Date tsImportacao) {
		this.tsImportacao = tsImportacao;
	}
	
	public Boolean getIcStatusImportado() {
		return icStatusImportado;
	}
	
	public void setIcStatusImportado(Boolean icStatusImportado) {
		this.icStatusImportado = icStatusImportado;
	}
	
	public String getCoTerminoImportacao() {
		return coTerminoImportacao;
	}
	
	public void setCoTerminoImportacao(String coTerminoImportacao) {
		this.coTerminoImportacao = coTerminoImportacao;
	}
	
	public String getNoArquivoImportado() {
		return noArquivoImportado;
	}
	
	public void setNoArquivoImportado(String noArquivoImportado) {
		this.noArquivoImportado = noArquivoImportado;
	}
	
	public Date getDtReferencia() {
		return dtReferencia;
	}
	
	public void setDtReferencia(Date dtReferencia) {
		this.dtReferencia = dtReferencia;
	}
	
	public Date getDtGeracao() {
		return dtGeracao;
	}
	
	public void setDtGeracao(Date dtGeracao) {
		this.dtGeracao = dtGeracao;
	}
	
	public Integer getQtRegistros() {
		return qtRegistros;
	}
	
	public void setQtRegistros(Integer qtRegistros) {
		this.qtRegistros = qtRegistros;
	}
	
	public Integer getQtRegistrosIncluidos() {
		return qtRegistrosIncluidos;
	}
	
	public void setQtRegistrosIncluidos(Integer qtRegistrosIncluidos) {
		this.qtRegistrosIncluidos = qtRegistrosIncluidos;
	}
	
	public Integer getQtRegistrosAlterados() {
		return qtRegistrosAlterados;
	}
	
	public void setQtRegistrosAlterados(Integer qtRegistrosAlterados) {
		this.qtRegistrosAlterados = qtRegistrosAlterados;
	}
	
	public String getSgSistemaGerador() {
		return sgSistemaGerador;
	}
	
	public void setSgSistemaGerador(String sgSistemaGerador) {
		this.sgSistemaGerador = sgSistemaGerador;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coTerminoImportacao == null) ? 0 : coTerminoImportacao.hashCode());
		result = prime * result + ((dtGeracao == null) ? 0 : dtGeracao.hashCode());
		result = prime * result + ((dtReferencia == null) ? 0 : dtReferencia.hashCode());
		result = prime * result + ((icStatusImportado == null) ? 0 : icStatusImportado.hashCode());
		result = prime * result + ((idLogImportacao == null) ? 0 : idLogImportacao.hashCode());
		result = prime * result + ((noArquivoImportado == null) ? 0 : noArquivoImportado.hashCode());
		result = prime * result + ((qtRegistros == null) ? 0 : qtRegistros.hashCode());
		result = prime * result + ((qtRegistrosAlterados == null) ? 0 : qtRegistrosAlterados.hashCode());
		result = prime * result + ((qtRegistrosIncluidos == null) ? 0 : qtRegistrosIncluidos.hashCode());
		result = prime * result + ((sgSistemaGerador == null) ? 0 : sgSistemaGerador.hashCode());
		result = prime * result + ((tsImportacao == null) ? 0 : tsImportacao.hashCode());
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
		LogVO other = (LogVO) obj;
		if (coTerminoImportacao == null) {
			if (other.coTerminoImportacao != null)
				return false;
		} else if (!coTerminoImportacao.equals(other.coTerminoImportacao))
			return false;
		if (dtGeracao == null) {
			if (other.dtGeracao != null)
				return false;
		} else if (!dtGeracao.equals(other.dtGeracao))
			return false;
		if (dtReferencia == null) {
			if (other.dtReferencia != null)
				return false;
		} else if (!dtReferencia.equals(other.dtReferencia))
			return false;
		if (icStatusImportado == null) {
			if (other.icStatusImportado != null)
				return false;
		} else if (!icStatusImportado.equals(other.icStatusImportado))
			return false;
		if (idLogImportacao == null) {
			if (other.idLogImportacao != null)
				return false;
		} else if (!idLogImportacao.equals(other.idLogImportacao))
			return false;
		if (noArquivoImportado == null) {
			if (other.noArquivoImportado != null)
				return false;
		} else if (!noArquivoImportado.equals(other.noArquivoImportado))
			return false;
		if (qtRegistros == null) {
			if (other.qtRegistros != null)
				return false;
		} else if (!qtRegistros.equals(other.qtRegistros))
			return false;
		if (qtRegistrosAlterados == null) {
			if (other.qtRegistrosAlterados != null)
				return false;
		} else if (!qtRegistrosAlterados.equals(other.qtRegistrosAlterados))
			return false;
		if (qtRegistrosIncluidos == null) {
			if (other.qtRegistrosIncluidos != null)
				return false;
		} else if (!qtRegistrosIncluidos.equals(other.qtRegistrosIncluidos))
			return false;
		if (sgSistemaGerador == null) {
			if (other.sgSistemaGerador != null)
				return false;
		} else if (!sgSistemaGerador.equals(other.sgSistemaGerador))
			return false;
		if (tsImportacao == null) {
			if (other.tsImportacao != null)
				return false;
		} else if (!tsImportacao.equals(other.tsImportacao))
			return false;
		return true;
	}
}
