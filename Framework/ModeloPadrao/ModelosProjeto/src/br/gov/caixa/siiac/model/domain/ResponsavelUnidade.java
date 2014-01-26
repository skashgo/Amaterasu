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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "iacvw001_rspnl_unidade", schema = "icosm001")
public class ResponsavelUnidade implements java.io.Serializable {
	
	private static final long serialVersionUID = 54357622342L;
	private ResponsavelUnidadeId id;
	
	public ResponsavelUnidade() {
	}
	
	public ResponsavelUnidade(ResponsavelUnidadeId id) {
		this.id = id;
	}
	
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "nuUnidade", column = @Column(name = "nu_unidade")), @AttributeOverride(name = "nuMatriculaH01", column = @Column(name = "nu_matricula_h01")), @AttributeOverride(name = "icEventual", column = @Column(name = "ic_eventual", length = 1)) })
	public ResponsavelUnidadeId getId() {
		return this.id;
	}
	
	public void setId(ResponsavelUnidadeId id) {
		this.id = id;
	}
	
}
