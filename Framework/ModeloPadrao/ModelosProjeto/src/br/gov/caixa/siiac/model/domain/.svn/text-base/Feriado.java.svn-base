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
import java.util.Date;

/**
 * @author GIS Consult
 *
 */

public class Feriado implements Serializable {
	
	private static final long serialVersionUID = 5083484834348823681L;
	
	public final static String SCHEMA = "icosm001";
	public final static String VIEW = "ESTVW001_FERIADO";
	
	/**
	 * getNomeTabela
	 * @return
	 */
	public final static String getNomeTabela() {
		return SCHEMA + "." + VIEW;
	}
	
	public static final String CAMPO_UNIDADE = "nu_unidade";
	public static final String CAMPO_NATURAL = "nu_natural";
	public static final String CAMPO_DATA = "dt_feriado";
	public static final String CAMPO_SIGLA_UF = "sg_uf_l22";
	public static final String CAMPO_TIPO = "co_abrna_ggrfa_f03";
	
	private Integer nuUnidade;
	private Integer nuNatural;
	private Date dtFeriado;
	private String sgUfL22;
	private String sgTipoFeriado;
	
	/**
	 * setNuUnidade
	 * @param nuUnidade
	 */
	public void setNuUnidade(Integer nuUnidade) {
		this.nuUnidade = nuUnidade;
	}
	
	/**
	 * getNuUnidade
	 * @return
	 */
	public Integer getNuUnidade() {
		return nuUnidade;
	}
	
	/**
	 * setNuNatural
	 * @param nuNatural
	 */
	public void setNuNatural(Integer nuNatural) {
		this.nuNatural = nuNatural;
	}
	
	/**
	 * getNuNatural
	 * @return
	 */
	public Integer getNuNatural() {
		return nuNatural;
	}
	
	/**
	 * setDtFeriado
	 * @param dtFeriado
	 */
	public void setDtFeriado(Date dtFeriado) {
		this.dtFeriado = dtFeriado;
	}
	
	/**
	 * getDtFeriado
	 * @return
	 */
	public Date getDtFeriado() {
		return dtFeriado;
	}
	
	public String getSgUfL22() {
		return sgUfL22;
	}
	
	public void setSgUfL22(String sgUfL22) {
		this.sgUfL22 = sgUfL22;
	}
	
	public void setSgTipoFeriado(String sgTipoFeriado) {
		this.sgTipoFeriado = sgTipoFeriado;
	}
	
	public String getSgTipoFeriado() {
		return sgTipoFeriado;
	}
	
}
