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

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.siiac.util.Anexo;

/**
 * @author GIS Consult
 */
public class EnvioParecerVO {
	
	private List<Anexo> anexos = new ArrayList<Anexo>();
	private List<String> destinatarios = new ArrayList<String>();
	private List<String> copias = new ArrayList<String>();
	private String de;
	private String assunto;
	private String conteudo;
	private List<EnvioParecerVOId> id = new ArrayList<EnvioParecerVOId>();
	
	public EnvioParecerVO() {
	}
	
	public List<Anexo> getAnexos() {
		return anexos;
	}
	
	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}
	
	public List<String> getDestinatarios() {
		return destinatarios;
	}
	
	public void setDestinatarios(List<String> destinatarios) {
		this.destinatarios = destinatarios;
	}
	
	public List<String> getCopias() {
		return copias;
	}
	
	public void setCopias(List<String> copias) {
		this.copias = copias;
	}
	
	public String getDe() {
		return de;
	}
	
	public void setDe(String de) {
		this.de = de;
	}
	
	public List<EnvioParecerVOId> getId() {
		return id;
	}
	
	public void setId(List<EnvioParecerVOId> id) {
		this.id = id;
	}
	
	public String getAssunto() {
		return assunto;
	}
	
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
}