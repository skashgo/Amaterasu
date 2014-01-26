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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author GIS Consult
 *
 */

@Entity
@Table(name = "iactb036_tipo_garantia", schema = "iacsm001")
public class TipoGarantia implements Serializable {
	
	private static final long serialVersionUID = 12348424800L;
	private Integer nuTipoGarantia;
	private String noTipoGarantia;
	private Boolean icAtivo = true;
	private List<Garantia> garantiaList = new ArrayList<Garantia>();
	
	public TipoGarantia() {
	}
	
	public TipoGarantia(Integer nuTipoGarantia, String noTipoGarantia, Boolean icAtivo) {
		this.nuTipoGarantia = nuTipoGarantia;
		this.noTipoGarantia = noTipoGarantia;
		this.icAtivo = icAtivo;
	}
	
	@Id
	@Column(name = "nu_tipo_garantia", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq010_tipo_garantia")
	@SequenceGenerator(name = "iacsq010_tipo_garantia", sequenceName = "iacsm001.iacsq010_tipo_garantia")
	public Integer getNuTipoGarantia() {
		return nuTipoGarantia;
	}
	
	public void setNuTipoGarantia(Integer nuTipoGarantia) {
		this.nuTipoGarantia = nuTipoGarantia;
	}
	
	@Column(name = "no_tipo_garantia", nullable = false)
	public String getNoTipoGarantia() {
		return noTipoGarantia;
	}
	
	public void setNoTipoGarantia(String noTipoGarantia) {
		this.noTipoGarantia = noTipoGarantia;
	}
	
	@Column(name = "ic_ativo", nullable = false)
	public Boolean getIcAtivo() {
		return icAtivo;
	}
	
	public void setIcAtivo(Boolean icAtivo) {
		this.icAtivo = icAtivo;
	}
	
	@OneToMany(mappedBy = "tipoGarantia", fetch = FetchType.LAZY)
	public List<Garantia> getGarantiaList() {
		return garantiaList;
	}
	
	public void setGarantiaList(List<Garantia> garantiaList) {
		this.garantiaList = garantiaList;
	}
	
}
