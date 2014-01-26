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
@Table(name = "iacvw006_caixa_postal_unidade", schema = "icosm001")
public class CaixaPostalUnidade implements java.io.Serializable {
	
	private static final long serialVersionUID = 198119819419810L;
	private CaixaPostalUnidadeId id;
	
	public CaixaPostalUnidade() {
	}
	
	public CaixaPostalUnidade(CaixaPostalUnidadeId id) {
		this.id = id;
	}
	
	@EmbeddedId
	@AttributeOverrides({ 
		@AttributeOverride(name = "nuUnidadeV03", column = @Column(name = "nu_unidade_v03")), 
		@AttributeOverride(name = "nuNaturalV03", column = @Column(name = "nu_natural_v03")), 
		@AttributeOverride(name = "nuSqnlCmnco", column = @Column(name = "nu_sqnl_cmnco")), 
		@AttributeOverride(name = "coComunicacao", column = @Column(name = "co_comunicacao", length = 40)), 
		@AttributeOverride(name = "coCmploCmnco", column = @Column(name = "co_cmplo_cmnco", length = 10)), 
		@AttributeOverride(name = "icTipoMeioCmnco", column = @Column(name = "ic_tipo_meio_cmnco")), 
		@AttributeOverride(name = "nuTipoCmncoM04", column = @Column(name = "nu_tipo_cmnco_m04")) })
	public CaixaPostalUnidadeId getId() {
		return this.id;
	}
	
	public void setId(CaixaPostalUnidadeId id) {
		this.id = id;
	}
	
}
