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

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "iactb017_cntrle_numero_parecer", schema = "iacsm001")
public class ControleNumeroParecer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected ControleNumeroParecerId controleNumeroParecerId;
	private Parecer parecer;
	
	public ControleNumeroParecer() {
	}
	
	public ControleNumeroParecer(ControleNumeroParecerId controleNumeroParecerId) {
		this.controleNumeroParecerId = controleNumeroParecerId;
	}
	
	public ControleNumeroParecer(int nuParecer, Short aaParecer, Short nuUnidade, int nuNatural) {
		this.controleNumeroParecerId = new ControleNumeroParecerId(nuParecer, aaParecer, nuUnidade, nuNatural);
	}
	
	@EmbeddedId
	public ControleNumeroParecerId getCntrleNumeroParecerPK() {
		return controleNumeroParecerId;
	}
	
	public void setCntrleNumeroParecerPK(ControleNumeroParecerId controleNumeroParecerId) {
		this.controleNumeroParecerId = controleNumeroParecerId;
	}
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "controleNumeroParecer")
	public Parecer getParecer() {
		return parecer;
	}
	
	public void setParecer(Parecer parecer) {
		this.parecer = parecer;
	}
}
