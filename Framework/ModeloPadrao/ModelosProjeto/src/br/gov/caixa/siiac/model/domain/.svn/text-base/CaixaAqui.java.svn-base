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

/**
 * @author GIS Consult
 *
 */
@Entity
@Table(name = "iacvw009_caixa_aqui", schema = "icosm001")
public class CaixaAqui implements java.io.Serializable {
	
	private static final long serialVersionUID = 198119819419810L;
	private CaixaAquiId id;
	
	
	public CaixaAqui() {
	}

	public CaixaAqui(CaixaAquiId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({ 
		@AttributeOverride(name = "nuCnpj", column = @Column(name = "nu_cnpj")), 
		@AttributeOverride(name = "noEmpresa", column = @Column(name = "no_empresa", length = 40)), 
		@AttributeOverride(name = "noFantasia", column = @Column(name = "no_fantasia", length = 40)),
		@AttributeOverride(name = "noEmail", column = @Column(name = "no_email", length = 50)),
		@AttributeOverride(name = "noResponsavel", column = @Column(name = "no_responsavel", length = 30)),
		@AttributeOverride(name = "nuConvenioC23", column = @Column(name = "nu_convenio_c23"))})
	public CaixaAquiId getId() {
		return id;
	}

	public void setId(CaixaAquiId id) {
		this.id = id;
	}
	
	

}
