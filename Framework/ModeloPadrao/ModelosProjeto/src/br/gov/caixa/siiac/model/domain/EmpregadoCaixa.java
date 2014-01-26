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
@Table(name = "iacw002_empregado_caixa", schema = "icosm001")
public class EmpregadoCaixa implements java.io.Serializable {
	
	private static final long serialVersionUID = 41543531266L;
	private EmpregadoCaixaId id;
	
	public EmpregadoCaixa() {
	}
	
	public EmpregadoCaixa(EmpregadoCaixaId id) {
		this.id = id;
	}
	
	@EmbeddedId
	@AttributeOverrides({ 
		@AttributeOverride(name = "nuUnidade", column = @Column(name = "nu_unidade")), 
		@AttributeOverride(name = "nuMatricula", column = @Column(name = "nu_matricula")), 
		@AttributeOverride(name = "noPessoa", column = @Column(name = "no_pessoa", length = 70)), 
		@AttributeOverride(name = "nuUnddeAlccoU24", column = @Column(name = "nu_undde_alcco_u24")) })
	public EmpregadoCaixaId getId() {
		return this.id;
	}
	
	public void setId(EmpregadoCaixaId id) {
		this.id = id;
	}
	
}
