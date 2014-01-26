/**
 * Copyright (c) 2009-2011 Caixa Econ�mica Federal. Todos os direitos
 * reservados.
 * 
 * Caixa Econ�mica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade
 * 
 * Este programa de computador foi desenvolvido sob demanda da CAIXA e est�
 * protegido por leis de direitos autorais e tratados internacionais. As
 * condi��es de c�pia e utiliza��o do todo ou partes dependem de autoriza��o da
 * empresa. C�pias n�o s�o permitidas sem expressa autoriza��o. N�o pode ser
 * comercializado ou utilizado para prop�sitos particulares.
 * 
 * Uso exclusivo da Caixa Econ�mica Federal. A reprodu��o ou distribui��o n�o
 * autorizada deste programa ou de parte dele, resultar� em puni��es civis e
 * criminais e os infratores incorrem em san��es previstas na legisla��o em
 * vigor.
 * 
 * Hist�rico do Subversion:
 * 
 * LastChangedRevision:  
 * LastChangedBy:  
 * LastChangedDate: 
 * 
 * HeadURL: 
 * 
 */
package br.gov.caixa.siiac.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author GIS Consult
 *
 */
@Entity
@Table(name = "iactb043_propriedades", schema = "iacsm001")
public class Propriedades {
	private static final long serialVersionUID = 1L;
	public static final String SCHEMA = "iacsm001";
	public final static String TABLE = "iactb043_propriedades";
	
	private String noPropriedade;
	private String noValor;
	private String noGrupo;
	
	@Id
	@Column(name = "no_propriedade")
	public String getNoPropriedade() {
		return noPropriedade;
	}
	
	public void setNoPropriedade(String noPropriedade) {
		this.noPropriedade = noPropriedade;
	}
	
	@Column(name = "no_valor_propriedade")
	public String getNoValor() {
		return noValor;
	}
	
	public void setNoValor(String noValor) {
		this.noValor = noValor;
	}
	
	@Column(name = "no_grupo")
	public String getNoGrupo() {
		return noGrupo;
	}
	
	public void setNoGrupo(String noGrupo) {
		this.noGrupo = noGrupo;
	}
	
	public final static String getNomeTabela() {
		return SCHEMA + "." + TABLE;
	}
	
}
