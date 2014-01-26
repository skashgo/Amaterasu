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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author GIS Consult
 *
 */
@Entity
@Table(name = "iactb067_tmple_prcr_dstrs", schema = "iacsm001")
public class TemplateParecerDestinatarios implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected TemplateParecerDestinatariosId templateParecerDestinatariosId;
	private Character icTipoDestinatario;
	private TemplateParecer templateParecer;

	public TemplateParecerDestinatarios() {
	}

	public TemplateParecerDestinatarios(
			TemplateParecerDestinatariosId templateParecerDestinatariosId,
			Character icTipoDestinatario, TemplateParecer templateParecer) {
		this.templateParecerDestinatariosId = templateParecerDestinatariosId;
		this.icTipoDestinatario = icTipoDestinatario;
		this.templateParecer = templateParecer;
	}

	@EmbeddedId
	@AttributeOverrides({ 
		@AttributeOverride(name = "nuTemplateParecer", column = @Column(name = "nu_template_parecer", nullable = false)), 
		@AttributeOverride(name = "coDestinatario", column = @Column(name = "co_destinatario", nullable = false)) 
	})
	public TemplateParecerDestinatariosId getTemplateParecerDestinatariosId() {
		return templateParecerDestinatariosId;
	}
	
	public void setTemplateParecerDestinatariosId(
			TemplateParecerDestinatariosId templateParecerDestinatariosId) {
		this.templateParecerDestinatariosId = templateParecerDestinatariosId;
	}
	
	@Column(name = "ic_tipo_destinatario")
	public Character getIcTipoDestinatario() {
		return icTipoDestinatario;
	}
	
	public void setIcTipoDestinatario(Character icTipoDestinatario) {
		this.icTipoDestinatario = icTipoDestinatario;
	}
		
	@JoinColumn(name = "nu_template_parecer", referencedColumnName = "nu_template_parecer", nullable = false,
			insertable=false, updatable=false)
	@ManyToOne
	public TemplateParecer getTemplateParecer() {
		return templateParecer;
	}
	
	public void setTemplateParecer(TemplateParecer templateParecer) {
		this.templateParecer = templateParecer;
	}
	
	//Constantes
	public static final int EMAIL_ID_CONVENIADO = 1;
	public static final int EMAIL_ID_EMPREGADO_RESPONSAVEL_PARECER = 2;
	public static final int EMAIL_ID_GERENTE_UNIDADE_RESPONSAVEL_CONTRATO = 3;
	public static final int EMAIL_ID_GERENTE_ASSINA_PARECER = 4;
	public static final int EMAIL_ID_UNIDADE_ALIENACAO_BENS_MOVEIS_IMOVEIS_UNIDADE_RESPONSAVEL_CONTRATO = 5;
	public static final int EMAIL_ID_UNIDADE_CONFORMIDADE_UNIDADE_RESPONSAVEL_CONTRATO = 6;
	public static final int EMAIL_ID_UNIDADE_EMPREGADO_RESPONSAVEL_PARECER = 7;
	public static final int EMAIL_ID_UNIDADE_RESPONSAVEL_CONTRATO = 8;
	public static final int EMAIL_ID_UNIDADE_SUPERIOR_HIERARQUICA_UNIDADE_RESPONSAVEL_CONTRATO = 9;
	public static final int EMAIL_ID_VENDEDOR = 10;

	public static final String EMAIL_LABEL_CONVENIADO = "Conveniado";
	public static final String EMAIL_LABEL_EMPREGADO_RESPONSAVEL_PARECER = "Empregado responsável pelo parecer";
	public static final String EMAIL_LABEL_GERENTE_UNIDADE_RESPONSAVEL_CONTRATO = "Gerente da Unidade responsável pelo contrato";
	public static final String EMAIL_LABEL_GERENTE_ASSINA_PARECER = "Gerente que assina o parecer";
	public static final String EMAIL_LABEL_UNIDADE_ALIENACAO_BENS_MOVEIS_IMOVEIS_UNIDADE_RESPONSAVEL_CONTRATO = "Unidade de alienação de bens móveis e imóveis de vinculação da unidade responsável pelo contrato";
	public static final String EMAIL_LABEL_UNIDADE_CONFORMIDADE_UNIDADE_RESPONSAVEL_CONTRATO = "Unidade de conformidade de vinculação da unidade responsável pelo contrato";
	public static final String EMAIL_LABEL_UNIDADE_EMPREGADO_RESPONSAVEL_PARECER = "Unidade do Empregado responsável pelo parecer";
	public static final String EMAIL_LABEL_UNIDADE_RESPONSAVEL_CONTRATO = "Unidade responsável pelo contrato";
	public static final String EMAIL_LABEL_UNIDADE_SUPERIOR_HIERARQUICA_UNIDADE_RESPONSAVEL_CONTRATO = "Unidade superior hierárquica da unidade responsável pelo contrato";
	public static final String EMAIL_LABEL_VENDEDOR = "Vendedor";
	
	public static final Character EMAIL_TIPO_DESTINATARIO_TO = '1';
	public static final Character EMAIL_TIPO_DESTINATARIO_CC = '2';
}
