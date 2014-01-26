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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb044_restricao_apontamento", schema = "iacsm001")
@TrailClass(fonte = "CHECKLIST")
public class RestricaoApontamento implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer nuRestricaoApontamento;
	private ApontamentoChecklistProduto apontamentoChecklistProduto;
	private Restricao restricao;
	
	public void setNuRestricaoApontamento(Integer numero) {
		this.nuRestricaoApontamento = numero;
	}
	
	@Id
	@Column(name = "nu_restricao_apontamento", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq015_restricao_apontamento")
	@SequenceGenerator(name = "iacsq015_restricao_apontamento", sequenceName = "iacsm001.iacsq015_restricao_apontamento")
	@TrailLog(insertID = true, updateID = true, deleteID = true)
	public Integer getNuRestricaoApontamento() {
		return this.nuRestricaoApontamento;
	}
	
	public void setApontamentoChecklistProduto(ApontamentoChecklistProduto apontamentoChecklistProduto) {
		this.apontamentoChecklistProduto = apontamentoChecklistProduto;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nu_aptmnto_chklst_produto")
	public ApontamentoChecklistProduto getApontamentoChecklistProduto() {
		return this.apontamentoChecklistProduto;
	}
	
	@Transient
	@TrailLog(name = "nu_aptmnto_chklst_produto")
	public Integer getApontamentoChecklistProdutoTrilha(){
		if (apontamentoChecklistProduto != null) {
			return apontamentoChecklistProduto.getNuApontamentoChecklistProduto();
		}
		return null;
	}

	public void setRestricao(Restricao restricao) {
		this.restricao = restricao;
	}	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nu_restricao")
	public Restricao getRestricao() {
		return this.restricao;
	}
	
	@Transient
	@TrailLog(name = "nu_chklst_srvco_vrfco_produto")
	public Integer getChecklistTrilha(){
		if (apontamentoChecklistProduto != null) {
			if (apontamentoChecklistProduto.getItemVerificacaoChecklistProduto() != null) {
				if (apontamentoChecklistProduto.getItemVerificacaoChecklistProduto().getBlocoChecklistProduto() != null) {
					if (apontamentoChecklistProduto.getItemVerificacaoChecklistProduto().getBlocoChecklistProduto().getChecklistServicoVerificacaoProduto() != null) {
						return apontamentoChecklistProduto.getItemVerificacaoChecklistProduto().getBlocoChecklistProduto().getChecklistServicoVerificacaoProduto().getNuChecklistServicoVerificacaoProduto();
					}
				}
			}
		}
		return null;
	}
}