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
package br.gov.caixa.siiac.view.mb;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IRestricaoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Restricao;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

@Service()
@Scope("request")
public class RestricaoMB extends AbstractMB {
	/**
	 * Variaveis
	 */
	private Restricao restricao = new Restricao();
	private List<Restricao> listRestricao = new ArrayList<Restricao>();
	private transient IRestricaoBO restricaoBO;
	
	/**
	 * Filtro
	 */
	private String pesquisaString;
	private Boolean pesquisaMostraInativos;

	
	@Autowired
	public void setRestricaoBO(IRestricaoBO restricaoBO) {
		this.restricaoBO = restricaoBO;
		modo_Inicio();
	}
	
	public RestricaoMB() {
	}
	
	//Methods Actions
	/**
	 * Valida a insercao/alteracao de uma restricao
	 */
	public Boolean doValida() {
		
		if(Utilities.empty(restricao) || Utilities.empty(restricao.getCoRestricao().trim())) {
			error(MSGS, MN002);
			warn( "Campo código não informado.");
			return false;
		}

		if (Utilities.empty(restricao.getSistemaDestino().trim()) && !restricao.getIcAtiva() == false) {
			error(MSGS, MN002);
			warn( "Campo Sistema de Destino não informado.");
			return false;
		}
		
		if (isModoAltera() && !restricaoBO.isAtivo(restricao)) {
			error(MSGS, MN013);
			return false;
		}
		try {
			if (restricaoBO.exist(restricao)) {
				error(MSGS, MN015);
				return false;
			}
		} catch (SIIACException e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Inclui uma nova restricao
	 * @param evt
	 */
	public void doIncluir(ActionEvent evt){
		if(doGravar(evt)){
			info(MSGS, MN007);
			modo_Inicio();
		}
	}
	
	/**
	 * Altera uma restricao
	 * @param evt
	 */
	public void doAlterar(ActionEvent evt){
		if(doGravar(evt)){
			info(MSGS, MN008);
			modo_Inicio();
		}
	}
	
	/**
	 * Grava (insere/altera) uma restricao 
	 * @param evt
	 * @return
	 */
	private boolean doGravar(ActionEvent evt) {
		if (doValida()) {
			try {
				restricaoBO.merge(restricao);
				return true;
			} catch (Exception e) {
				error(MSGS, MN002);
				LogCEF.error(e.getMessage());
			}
		}
		return false;
	}
	
	/**
	 * Filtra as restricoes da tabela
	 * @param evt
	 */
	public void doFiltrar(ActionEvent evt) {
		List<Restricao> lista = atualizaList();
		if (lista == null || lista.isEmpty()) {
			warn(MSGS, MN016);
			restricao = new Restricao();
			lista = atualizaList();
		}
		setListRestricao(lista);
		
	}
	
	/**
	 * Ativa/Inativa a restricao atraves do botao do grid
	 * @param evt
	 */
	public void doDesabilitarNaGrid(ActionEvent evt) {
		restricao = (Restricao) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		try {
			restricaoBO.ativaInativa(restricao);
			if(restricao.getIcAtiva()){
				info(MSGS, MN012);
			}else{
				info(MSGS, MN011);
			}
			modo_Inicio();
		} catch (Exception e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
		setListRestricao(atualizaList());
	}
	
	/**
	 * Ativa/Inativa a restricao atraves do botao no formulario
	 * @param evt
	 */
	public void doDesabilitarNoButton(ActionEvent evt) {
		try {
			restricaoBO.ativaInativa(restricao);
			if(restricao.getIcAtiva()){
				info(MSGS, MN012);
			}else{
				info(MSGS, MN011);
			}
			modo_Visualizar();
		} catch (Exception e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
	}
	
	/**
	 * Clique no botao 'Alterar' na p�gina de visualizacao de uma restricao.
	 * @param evt
	 */
	public void doAtualizar(ActionEvent evt) {
		restricao = (Restricao) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		modo_Atualizar();
	}
	
	/**
	 * Clique do botao 'Visualizar' uma restricao na tabela.
	 * @param evt
	 */
	public void doVisualizar(ActionEvent evt) {
		restricao = (Restricao) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		modo_Visualizar();
	}
	
	/**
	 * Clique do botao 'Cadastrar' na tela principal.
	 * @param evt
	 */
	public void doCadastrar(ActionEvent evt) {
		modo_Cadastrar();
	}
	
	/**
	 * Clique do botao 'Cancelar'/'Voltar'.
	 * @param evt
	 */
	public void doCancelar(ActionEvent evt) {
		if(isModoCadastro() || isModoAltera()){ // Pra n�o aparecer a mensagem quando for o botao "Voltar", que est� no modo visualizacao.
			warn(MSGS, MN003);
		}
		modo_Inicio();
	}
	
	/**
	 * Atualiza a lista da tabela.
	 * @return
	 */
	private List<Restricao> atualizaList() {
		try {
			return restricaoBO.getListFiltro(pesquisaString, pesquisaMostraInativos);
		} catch (SIIACException e) {
			restricao = new Restricao();
			warn(MSGS, MN016);
			LogCEF.error(e.getMessage());
		}
		return new ArrayList<Restricao>();
	}
	
	/**
	 * Retorna a lista pro radio-button de ativos/inativos
	 * @return
	 */
	public List<SelectItem> getSelectItemIcAtivo() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem(true, "ativos"));
		list.add(new SelectItem(false, "inativos"));
		return list;
	}
	
	/**
	 * Coloca a view no modo inicial.
	 */
	private void modo_Inicio() {
		setModoInicio();
		restricao = new Restricao();
		pesquisaString = "";
		pesquisaMostraInativos = false;
		setListRestricao(atualizaList());
	}
	
	/**
	 * Coloca a view no modo de cadastro.
	 */
	private void modo_Cadastrar() {
		setModoCadastro();
		restricao = new Restricao();
	}
	
	/**
	 * Coloca a view no modo de alteracao.
	 */
	private void modo_Atualizar() {
		setModoAltera();
	}
	
	/**
	 * Coloca a view no modo de visualizacao.
	 */
	private void modo_Visualizar() {
		setModoVisualiza();
	}
	
	//Getters and Setters
	public String getPesquisaString() {
		return this.pesquisaString;
	}
	
	public void setPesquisaString(String pesquisaString) { 
		this.pesquisaString = pesquisaString;
	}

	public Boolean getPesquisaMostraInativos() {
		return this.pesquisaMostraInativos;
	}
	
	public void setPesquisaMostraInativos(Boolean pesquisaMostraInativos) { 
		this.pesquisaMostraInativos = pesquisaMostraInativos;
	}
	
	public Restricao getRestricao() {
		return this.restricao;
	}
	
	public void setRestricao(Restricao restricao) {
		this.restricao = restricao;
	}
	
	public List<Restricao> getListRestricao() {
		return this.listRestricao;
	}
	
	public void setListRestricao(List<Restricao> listRestricao) {
		this.listRestricao = listRestricao;
	}
	
	
	// Renderers

	/**
	 * Verifica se deve ser mostrado o BreadCrumb inicial 
	 */
	public boolean isShowBreadInicio(){
		return isModoInicio();
	}

	/**
	 * Verifica se deve ser mostrado o BreadCrumb de cadastro 
	 */
	public boolean isShowBreadNovo(){
		return isModoCadastro();
	}

	/**
	 * Verifica se deve ser mostrado o BreadCrumb de visualizacao 
	 */
	public boolean isShowBreadVisualizar(){
		return isModoVisualiza();
	}

	/**
	 * Verifica se deve ser mostrado o BreadCrumb de alteracao 
	 */
	public boolean isShowBreadAlterar(){
		return isModoAltera();
	}
	
	/**
	 * Valida se deve ser mostrado o painel que engloba a visualizacao e cadastro/alteracao.
	 * @return
	 */
	public boolean isShowPanelCadastroVisualizacao() {
		return isModoCadastro() || isModoVisualiza() || isModoAltera();
	}	
	
	/**
	 * Valida se deve ser mostrado o painel de cadastro/alteracao
	 * @return
	 */
	public boolean isShowPanelCadastro() {
		return isModoCadastro() || isModoAltera();
	}
	
	/**
	 * Valida se deve ser mostrado o painel de visualizacao
	 * @return
	 */
	public boolean isShowPanelVisualizar() {
		return isModoVisualiza();
	}
	
	/**
	 * Valida se deve ser mostrado o campo input do codigo
	 * @return
	 */
	public boolean isShowInputCodigo(){
		return isModoCadastro();
	}
	
	/**
	 * Valida se deve ser mostrado o campo output do codigo
	 * @return
	 */
	public boolean isShowOutputCodigo(){
		return isModoVisualiza() || isModoAltera();
	}

	/**
	 * Valida se deve ser desabilitado o campo input do codigo
	 * @return
	 */
	public boolean isDisabledInputCodigo(){
		return isModoVisualiza() || isModoAltera();
	}

	/**
	 * Valida se deve ser mostrado o campo input do nome
	 * @return
	 */
	public boolean isShowInputNome(){
		return isModoCadastro() || isModoAltera();
	}
	
	/**
	 * Valida se deve ser mostrado o campo output do nome
	 * @return
	 */
	public boolean isShowOutputNome(){
		return isModoVisualiza();
	}

	/**
	 * Valida se deve ser mostrado o campo input da descricao
	 * @return
	 */
	public boolean isShowInputDescricao(){
		return isModoCadastro() || isModoAltera();
	}

	/**
	 * Valida se deve ser mostrado o campo output da descricao
	 * @return
	 */
	public boolean isShowOutputDescricao(){
		return isModoVisualiza();
	}

	/**
	 * Valida se deve ser mostrado o campo input do sistema destino
	 * @return
	 */
	public boolean isShowInputSistemaDestino(){
		return isModoCadastro() || isModoAltera();
	}

	/**
	 * Valida se deve ser mostrado o campo output do sistema destino
	 * @return
	 */
	public boolean isShowOutputSistemaDestino(){
		return isModoVisualiza();
	}

	/**
	 * Valida se deve ser mostrada a coluna com os campos de situacao na visualizacao
	 * @return
	 */
	public boolean isShowLabelSituacaoVisualizar(){
		return isModoVisualiza() && Utilities.notEmpty(restricao);
	}

	/**
	 * Valida se deve ser mostrado o campo de situacao como ATIVO
	 * @return
	 */
	public boolean isShowSituacaoVisualizarAtivo(){
		return isModoVisualiza() && Utilities.notEmpty(restricao) && restricao.getIcAtiva();
	}

	/**
	 * Valida se deve ser mostrado o campo de situacao como INATIVO
	 * @return
	 */
	public boolean isShowSituacaoVisualizarInativo(){
		return isModoVisualiza() && Utilities.notEmpty(restricao) && !restricao.getIcAtiva();
	}

	/**
	 * Valida se deve ser mostrado o botao ATIVAR na tela de visualizacao
	 * @return
	 */
	public boolean isShowButtonAtivar(){
		return isModoVisualiza() && Utilities.notEmpty(restricao) && !restricao.getIcAtiva();
	}

	/**
	 * Valida se deve ser mostrado o botao INATIVAR na tela de visualizacao
	 * @return
	 */
	public boolean isShowButtonInativar(){
		return isModoVisualiza() && Utilities.notEmpty(restricao) && restricao.getIcAtiva();
	}
	
	/**
	 * Valida se deve ser mostrado o painel com os filtros.
	 * @return
	 */
	public boolean isShowPanelFiltro() {
		return isModoInicio();
	}
	
	/**
	 * Valida se deve ser mostrada a lista de restricoes.
	 * @return
	 */
	public boolean isShowPanelLista() {
		return isModoInicio();
	}

	/**
	 * Valida se deve ser mostrado o botao CANCELAR.
	 * @return
	 */
	public boolean isShowButtonCancelar() {
		return isModoCadastro() || isModoAltera();
	}

	/**
	 * Valida se deve ser mostrado o botao VOLTAR.
	 * @return
	 */
	public boolean isShowButtonVoltar() {
		return isModoVisualiza();
	}

	/**
	 * Valida se deve ser mostrado o botao SALVAR (inclusao).
	 * @return
	 */
	public boolean isShowButtonSalvarCadastro(){
		return isModoCadastro();
	}

	/**
	 * Valida se deve ser mostrado o botao SALVAR (alteracao).
	 * @return
	 */	
	public boolean isShowButtonSalvarAltaracao(){
		return isModoAltera();
	}

	/**
	 * Valida se deve ser mostrado o painel com o botao para chamar a tela de novo cadastro.
	 * @return
	 */
	public boolean isShowPanelButtonCadastrar() {
		return isModoInicio();
	}

	/**
	 * Valida se deve ser mostrado o painel com os botoes da visualizacao.
	 * @return
	 */
	public boolean isShowPanelButtonVisualizar(){
		return isModoVisualiza();
	}

	/**
	 * Valida se deve ser mostrado o painel com os botoes da insercao/alteracao.
	 * @return
	 */
	public boolean isShowPanelButtonSalvar(){
		return isModoAltera() || isModoCadastro();
	}
	

	/**
	 * Verifica se deve ser mostrado o título de inclusão
	 */
	public boolean isShowTitleIncluir() {
		return isModoCadastro();
	}
	
	/**
	 * Verifica se deve ser mostrado o título de visualização 
	 */
	public boolean isShowTitleVisualizar() {
		return isModoVisualiza();
	}
	
	/**
	 * Verifica se deve ser mostrado o título de alteração
	 */
	public boolean isShowTitleAlterar() {
		return isModoAltera();
	}
	
}