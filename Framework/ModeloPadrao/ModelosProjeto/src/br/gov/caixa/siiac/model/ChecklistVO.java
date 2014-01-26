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
package br.gov.caixa.siiac.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ChecklistVO {
	
	public static final String CHKLST_SITUACAO_PROJETO = "PROJETO";
	public static final String CHKLST_SITUACAO_AUTORIZADO = "AUTORIZADO";
	public static final String CHKLST_SITUACAO_PUBLICADO = "PUBLICADO";
	public static final String CHKLST_SITUACAO_REVOGADO_LABEL = "REVOGADO";
	private List<String> listSituacoes;
	private Short operacao;
	private String filtroProduto;
	private String situacao;
	private Integer codServicoVerificacao;
	private String noProduto;
	private String codProduto;
	private Date dataInicio;
	private Date dataFim;
	private Short nuCanal;
	private boolean revogado;
	private Short modalidade;
	private Integer codBloco;
	private Boolean obrigatorio;
	private String descBlocoChecklist;

	private String nomeAjuda;
	private String situacaoAjuda;
    private boolean checkNaoAplica;
    private boolean prejudicado;
	
	public Short getModalidade() {
		return modalidade;
	}

	public void setModalidade(Short modalidade) {
		this.modalidade = modalidade;
	}
	
	/**
	 * @return the listSituacoes
	 */
	public List<String> getListSituacoes() {
		if(listSituacoes == null) {
			listSituacoes = new ArrayList<String>();
			listSituacoes.add(CHKLST_SITUACAO_PROJETO);
			listSituacoes.add(CHKLST_SITUACAO_AUTORIZADO);
			listSituacoes.add(CHKLST_SITUACAO_PUBLICADO);
		}
		
		return listSituacoes;
	}

	/**
	 * @param listSituacoes
	 */
	public void setListSituacoes(List<String> listSituacoes) {
		this.listSituacoes = listSituacoes;
	}

	/**
	 * @return numero da operacao
	 */
	public Short getOperacao() {
		return operacao;
	}

	/**
	 * @param numero da operacao
	 */
	public void setOperacao(Short operacao) {
		this.operacao = operacao;
	}

	/**
	 * @return nome da situacao
	 */
	public String getSituacao() {
		return situacao;
	}
	
	/**
	 * 
	 * @param filtroOperacao
	 */
	public void setFiltroProduto(String filtroOperacao) {
		this.filtroProduto = filtroOperacao;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFiltroProduto() {
		return this.filtroProduto;
	}
	
	/**
	 * @param nome da situacao
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	/**
	 * @return codigo do servico deverificacao
	 */
	public Integer getCodServicoVerificacao() {
		return codServicoVerificacao;
	}

	/**
	 * @param codigo do servico deverificacao
	 */
	public void setCodServicoVerificacao(Integer codServicoVerificacao) {
		this.codServicoVerificacao = codServicoVerificacao;
	}

	/**
	 * @return the noProduto
	 */
	public String getNoProduto() {
		return noProduto;
	}

	/**
	 * @param noProduto the noProduto to set
	 */
	public void setNoProduto(String noProduto) {
		this.noProduto = noProduto;
	}

	/**
	 * @return codigo do produto
	 */
	public String getCodProduto() {
		return codProduto;
	}

	/**
	 * @param codigo do produto
	 */
	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}

	/**
	 * @return data de ínicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param data de ínicio
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return data final
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param data final
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return the nuCanal
	 */
	public Short getNuCanal() {
		return nuCanal;
	}

	/**
	 * @param nuCanal the nuCanal to set
	 */
	public void setNuCanal(Short nuCanal) {
		this.nuCanal = nuCanal;
	}

	/**
	 * @return the icRevogado
	 */
	public boolean isRevogado() {
		return revogado;
	}

	/**
	 * @param icRevogado the icRevogado to set
	 */
	public void setRevogado(boolean revogado) {
		this.revogado = revogado;
	}

	/**
	 * @return the codBloco
	 */
	public Integer getCodBloco() {
		return codBloco;
	}

	/**
	 * @param codBloco the codBloco to set
	 */
	public void setCodBloco(Integer codBloco) {
		this.codBloco = codBloco;
	}

	/**
	 * @return the isObrigatorio
	 */
	public Boolean getObrigatorio() {
		return obrigatorio;
	}

	/**
	 * @param obrigatorio the isObrigatorio to set
	 */
	public void setObrigatorio(Boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	/**
	 * @return the descBlocoChecklist
	 */
	public String getDescBlocoChecklist() {
		return descBlocoChecklist;
	}

	/**
	 * @param descBlocoChecklist the descBlocoChecklist to set
	 */
	public void setDescBlocoChecklist(String descBlocoChecklist) {
		this.descBlocoChecklist = descBlocoChecklist;
	}

	/**
	 * @param nomeAjuda the nomeAjuda to set
	 */
	public void setNomeAjuda(String nomeAjuda) {
		this.nomeAjuda = nomeAjuda;
	}

	/**
	 * @return the nomeAjuda
	 */
	public String getNomeAjuda() {
		return nomeAjuda;
	}

	/**
	 * @param situacaoAjuda the situacaoAjuda to set
	 */
	public void setSituacaoAjuda(String situacaoAjuda) {
		this.situacaoAjuda = situacaoAjuda;
	}

	/**
	 * @return the situacaoAjuda
	 */
	public String getSituacaoAjuda() {
		return situacaoAjuda;
	}

	/**
	 * @param checkNaoAplica the checkNaoAplica to set
	 */
	public void setCheckNaoAplica(boolean checkNaoAplica) {
		this.checkNaoAplica = checkNaoAplica;
	}

	/**
	 * @return the checkNaoAplica
	 */
	public boolean isCheckNaoAplica() {
		return checkNaoAplica;
	}

	public boolean isPrejudicado() {
		return prejudicado;
	}

	public void setPrejudicado(boolean prejudicado) {
		this.prejudicado = prejudicado;
	}
}