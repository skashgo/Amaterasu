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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb005_chklst_srvco_produto", schema = "iacsm001")
@TrailClass(fonte = "CHECKLIST")
public class ChecklistServicoVerificacaoProduto implements java.io.Serializable {
	
	private static final long serialVersionUID = 1995017007244547036L;
	private Integer nuChecklistServicoVerificacaoProduto;
	private ServicoVerificacaoProduto servicoVerificacaoProduto;
	private Date dataInicio;
	private Date dataFim;
	private String icSituacao;
	private List<BlocoChecklistProduto> blocoChecklistProdutoList = new ArrayList<BlocoChecklistProduto>();
	private CanalComercializacaoProduto canalComercializacao;
	private boolean icRevogado;
	private transient boolean selecionado;
	private List<VerificacaoContrato> verificacaoContratoList = new ArrayList<VerificacaoContrato>();
	
	public void setNuChecklistServicoVerificacaoProduto(Integer numero) {
		this.nuChecklistServicoVerificacaoProduto = numero;
	}
	
	@Id
	@Column(name = "nu_chklst_srvco_vrfco_produto", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq011_chklst_srvco_produto")
	@SequenceGenerator(name = "iacsq011_chklst_srvco_produto", sequenceName = "iacsm001.iacsq011_chklst_srvco_produto")
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public Integer getNuChecklistServicoVerificacaoProduto() {
		return this.nuChecklistServicoVerificacaoProduto;
	}
	
	public void setServicoVerificacaoProduto(ServicoVerificacaoProduto servicoVerificacaoProduto) {
		this.servicoVerificacaoProduto = servicoVerificacaoProduto;
	}
	
	@JoinColumn(name = "nu_servico_verificacao_produto", referencedColumnName = "nu_servico_verificacao_produto", nullable = false)
	@ManyToOne
	public ServicoVerificacaoProduto getServicoVerificacaoProduto() {
		return this.servicoVerificacaoProduto;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	@Transient
	@TrailLog(deleteID = true, insertID = true, updateID = true, name = "nu_servico_verificacao_produto")
	public Integer getServicoVerificacaoProdutoTrilha(){
		if(servicoVerificacaoProduto != null){
			return servicoVerificacaoProduto.getNuServicoVerificacaoProduto();
		}
		return null;
	}
	
	@Column(name = "dt_inicio_checklist")
	@TrailLog
	public Date getDataInicio() {
		return this.dataInicio;
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	@Column(name = "dt_fim_checklist")
	@TrailLog
	public Date getDataFim() {
		return this.dataFim;
	}
	
	public void setIcSituacao(String icSituacao) {
		this.icSituacao = icSituacao;
	}
	
	@Column(name = "ic_situacao_checklist")
	@TrailLog
	public String getIcSituacao() {
		return this.icSituacao;
	}
	
	@JoinColumn(name = "nu_canal_cmrco", referencedColumnName = "nu_canal_cmrco")
	@ManyToOne()
	public CanalComercializacaoProduto getCanalComercializacao() {
		return canalComercializacao;
	}
	
	public void setCanalComercializacao(CanalComercializacaoProduto canalCmrco) {
		this.canalComercializacao = canalCmrco;
	}
	
	@Transient
	@TrailLog(name = "nu_canal_cmrco")
	public Short getCanalComercializacaoTrilha(){
		if(canalComercializacao != null){
			return canalComercializacao.getNuCanalCmrco();
		}
		return null;
	}
	
	@Column(name = "ic_revogado")
	@TrailLog
	public boolean getIcRevogado() {
		return icRevogado;
	}
	
	public void setIcRevogado(boolean icRevogado) {
		this.icRevogado = icRevogado;
	}
	
	@OneToMany(mappedBy = "checklistServicoVerificacaoProduto", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.MERGE })
	@OrderBy("numeroDeOrdem")
	public List<BlocoChecklistProduto> getBlocoChecklistProdutoList() {
		return blocoChecklistProdutoList;
	}
	
	public void setBlocoChecklistProdutoList(List<BlocoChecklistProduto> blocoChecklistProdutoList) {
		this.blocoChecklistProdutoList = blocoChecklistProdutoList;
	}
	
	@OneToMany(mappedBy = "checklist", fetch = FetchType.LAZY)
	public List<VerificacaoContrato> getVerificacaoContratoList() {
		return verificacaoContratoList;
	}
	
	public void setVerificacaoContratoList(List<VerificacaoContrato> verificacaoContratoList) {
		this.verificacaoContratoList = verificacaoContratoList;
	}
	
	@Transient
	public boolean isShowAcaoExcluir() {
		if (icSituacao.trim().toUpperCase().equals("PROJETO")) {
			return true;
		}
		return false;
	}
	
	@Transient
	public boolean isShowAcaoAlterar() {
		if (icSituacao.trim().toUpperCase().equals("REVOGADO")) {
			return false;
		}
		return true;
	}
	
	/**
	 * ATRIBUTO AUXILIAR UTILIZADO NO GRID
	 * PARA INFORMAR QUAL CHECKLIST FOI
	 * SELECIONADO PARA RECEBER A REPLICAÇÃO
	 * DAS ALTERAÇÕES
	 */
	
	@Transient
	public boolean isSelecionado() {
		return selecionado;
	}
	
	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}
}
