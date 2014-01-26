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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author GIS Consult
 *
 */
@Entity
public class TagListaItemDataValidadeVO {

	private Integer idBloco;
	private Integer idItem;
	private String noBloco;
	private String noItem;
	private String obsBloco;
	private String obsItem;
	private Date dtValidade;
	
	
	public TagListaItemDataValidadeVO() {
	}

	@Column(name = "id_bloco")
	public Integer getIdBloco() {
		return idBloco;
	}

	public void setIdBloco(Integer idBloco) {
		this.idBloco = idBloco;
	}

	@Id
	@Column(name = "id_item")
	public Integer getIdItem() {
		return idItem;
	}

	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}

	@Column(name = "no_bloco")
	public String getNoBloco() {
		return noBloco;
	}

	public void setNoBloco(String noBloco) {
		this.noBloco = noBloco;
	}

	@Column(name = "no_item")
	public String getNoItem() {
		return noItem;
	}

	public void setNoItem(String noItem) {
		this.noItem = noItem;
	}

	@Column(name = "obs_bloco")
	public String getObsBloco() {
		return obsBloco;
	}

	public void setObsBloco(String obsBloco) {
		this.obsBloco = obsBloco;
	}

	@Column(name = "obs_item")
	public String getObsItem() {
		return obsItem;
	}

	public void setObsItem(String obsItem) {
		this.obsItem = obsItem;
	}

	@Column(name = "dt_validade")
	public Date getDtValidade() {
		return dtValidade;
	}

	public void setDtValidade(Date dtValidade) {
		this.dtValidade = dtValidade;
	}
	
	
}
