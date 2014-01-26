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
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author GIS Consult
 *
 */
@Embeddable
public class CaixaAquiId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal nuCnpj;
	private String noEmpresa;
	private String noFantasia;
	private String noEmail;
	private String noResponsavel;
	private Integer nuConvenioC23;

	public CaixaAquiId() {
	}

	public CaixaAquiId(BigDecimal nuCnpj, String noEmpresa,
			String noResponsavel, Integer nuConvenioC23, String noFantasia) {
		this.nuCnpj = nuCnpj;
		this.noEmpresa = noEmpresa;
		this.noResponsavel = noResponsavel;
		this.noFantasia = noFantasia;
		this.nuConvenioC23 = nuConvenioC23;
	}

	@Column(name = "nu_cnpj", nullable = true)
	public BigDecimal getNuCnpj() {
		return nuCnpj;
	}
	
	public void setNuCnpj(BigDecimal nuCnpj) {
		this.nuCnpj = nuCnpj;
	}
	
	@Column(name = "no_empresa", nullable = true)
	public String getNoEmpresa() {
		return noEmpresa;
	}
	
	public void setNoEmpresa(String noEmpresa) {
		this.noEmpresa = noEmpresa;
	}
	
	@Column(name = "no_responsavel", nullable = true)
	public String getNoResponsavel() {
		return noResponsavel;
	}
	
	public void setNoResponsavel(String noResponsavel) {
		this.noResponsavel = noResponsavel;
	}
	
	@Column(name = "nu_convenio_c23", nullable = true)
	public Integer getNuConvenioC23() {
		return nuConvenioC23;
	}
	
	public void setNuConvenioC23(Integer nuConvenioC23) {
		this.nuConvenioC23 = nuConvenioC23;
	}

	@Column(name = "no_email", nullable = true)
	public String getNoEmail() {
		return noEmail;
	}

	public void setNoEmail(String noEmail) {
		this.noEmail = noEmail;
	}

	@Column(name = "no_fantasia", nullable = true)
	public String getNoFantasia() {
		return noFantasia;
	}

	public void setNoFantasia(String noFantasia) {
		this.noFantasia = noFantasia;
	}
}
