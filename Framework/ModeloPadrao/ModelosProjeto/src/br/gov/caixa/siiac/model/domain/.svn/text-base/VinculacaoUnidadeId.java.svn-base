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

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VinculacaoUnidadeId implements java.io.Serializable {
	
	private static final long serialVersionUID = 14234254242L;
	private Short nuUnidade;
	private Short nuUndeVnclraU24;
	private Short nuTpVinculoU22;
	private Short nuProcessoU27;
	
	public VinculacaoUnidadeId() {
	}
	
	public VinculacaoUnidadeId(Short nuUnidade, Short nuUndeVnclraU24, Short nuTpVinculoU22, Short nuProcessoU27) {
		this.nuUnidade = nuUnidade;
		this.nuUndeVnclraU24 = nuUndeVnclraU24;
		this.nuTpVinculoU22 = nuTpVinculoU22;
		this.nuProcessoU27 = nuProcessoU27;
	}
	
	@Column(name = "nu_unidade")
	public Short getNuUnidade() {
		return this.nuUnidade;
	}
	
	public void setNuUnidade(Short nuUnidade) {
		this.nuUnidade = nuUnidade;
	}
	
	@Column(name = "nu_unde_vnclra_u24")
	public Short getNuUndeVnclraU24() {
		return this.nuUndeVnclraU24;
	}
	
	public void setNuUndeVnclraU24(Short nuUndeVnclraU24) {
		this.nuUndeVnclraU24 = nuUndeVnclraU24;
	}
	
	@Column(name = "nu_tp_vinculo_u22")
	public Short getNuTpVinculoU22() {
		return this.nuTpVinculoU22;
	}
	
	public void setNuTpVinculoU22(Short nuTpVinculoU22) {
		this.nuTpVinculoU22 = nuTpVinculoU22;
	}
	
	@Column(name = "nu_processo_u27")
	public Short getNuProcessoU27() {
		return this.nuProcessoU27;
	}
	
	public void setNuProcessoU27(Short nuProcessoU27) {
		this.nuProcessoU27 = nuProcessoU27;
	}
}
