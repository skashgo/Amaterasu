/**
 * Copyright (c) 2009-2011 Caixa Econômica Federal. To os direitos
 * reserva.
 * 
 * Caixa Econômica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade
 * 
 * Este programa de computador foi desenvolvido sob demanda da CAIXA e está
 * protegido por leis de direitos autorais e trata internacionais. As
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

import br.gov.caixa.siiac.bo.IApontamentoBO;
import br.gov.caixa.siiac.bo.impl.MatrizAcessoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Apontamento;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

@Service()
@Scope("request")
public class ApontamentoMB extends AbstractMB {
	
	/**
	 * Variaveis
	 */
	private Apontamento apontamento = new Apontamento();
	private List<Apontamento> listApontamento = new ArrayList<Apontamento>();
	private transient IApontamentoBO apontamentoBO;
	private Short nuPerfil = getUsuarioLogado().getPerfis().get(0);

	/**
	 * Filtro
	 */
	private String pesquisaString;
	private Boolean pesquisaMostraInativos;
	
	@Autowired
	public void setApontamentoBO(IApontamentoBO apontamentoBO) {
		this.apontamentoBO = apontamentoBO;
		modo_Inicio();
	}
	
	public ApontamentoMB() {
		
	}
	
	//Methods Actions
	/**
	 * Valida a insercao/alteracao de um apontamento
	 */
	public Boolean doValida() {
		if (apontamento.getNoApontamentoChecklist() == null || Utilities.empty(apontamento.getNoApontamentoChecklist().trim()) && !apontamento.getIcAtivo() == false) {
			error(getMessage(MSGS, MN002));
			warn( "Campo NOME Obrigatório.");
			return false;
		}
		if (isModoAltera() && !apontamentoBO.isAtivo(apontamento)) {
			error(MSGS, MN013);
			return false;
		}
		try {
			if (apontamentoBO.exist(apontamento)) {
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
	 * Grava (insere/altera) um apontamento
	 * @param evt
	 * @return
	 */
	public void doGravar(ActionEvent evt) {
		
		if (doValida()) {
			try {
				apontamentoBO.merge(apontamento);
				if (isModoCadastro()) {
					info(MSGS, MN007);
				} else if (isModoAltera()) {
					info(MSGS, MN008);
				}
			} catch (Exception e) {
				error(MSGS, MN002);
				LogCEF.error(e.getMessage());
			}
			modo_Inicio();
		}
	}
	
	/**
	 * Filtra os apontamentos da tabela
	 * @param evt
	 */
	public void doFiltrar(ActionEvent evt) {
		List<Apontamento> lista = atualizaList();
		if (lista == null || lista.isEmpty()) {
			warn(MSGS, MN016);
		}
		setListApontamento(lista);
	}
	
	/**
	 * Ativa/Inativa o apontamento atraves do botao do grid
	 * @param evt
	 */
	public void doDesabilitarNaGrid(ActionEvent evt) {
		apontamento = (Apontamento) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		try {
			apontamentoBO.ativaInativa(apontamento);
			if (apontamento.getIcAtivo()) {
				info(MSGS, MN012);
			} else {
				info(MSGS, MN011);
			}
			modo_Inicio();
		} catch (Exception e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
		setListApontamento(atualizaList());
	}
	
	/**
	 * Ativa/Inativa o apontamento atraves do botao no formul�rio
	 * @param evt
	 */
	public void doDesabilitarNoButton(ActionEvent evt) {
		try {
			apontamentoBO.ativaInativa(apontamento);
			if (apontamento.getIcAtivo()) {
				info(MSGS, MN012);
			} else {
				info(MSGS, MN011);
			}
			modo_Visualizar();
		} catch (Exception e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
	}
	
	/**
	 * Clique no botao 'Alterar' na pagina de visualizacao de um apontamento.
	 * @param evt
	 */
	public void doAtualizar(ActionEvent evt) {
		apontamento = (Apontamento) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		modo_Atualizar();
	}
	
	/**
	 * Clique do botao 'Visualizar' um apontamento na tabela.
	 * @param evt
	 */
	public void doVisualizar(ActionEvent evt) {
		apontamento = (Apontamento) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
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
		warn(MSGS, MN003);
		modo_Inicio();
	}
	
	/**
	 * Atualiza a lista da tabela.
	 * @return
	 */
	private List<Apontamento> atualizaList() {
		try {
			return apontamentoBO.getListFiltro(pesquisaString, pesquisaMostraInativos);
		} catch (SIIACException e) {
			warn(MSGS, MN016);
			LogCEF.error(e.getMessage());
		}
		return new ArrayList<Apontamento>();
	}
	
	/**
	 * Retorna a lista pro radio-button de ativos/inativos
	 * @return
	 */
	public List<SelectItem> getSelectItemIcAtivo() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem(true, "Ativos"));
		list.add(new SelectItem(false, "Inativos"));
		return list;
	}
	
	/**
	 * Coloca a view no modo inicial.
	 */
	private void modo_Inicio() {
		setModoInicio();
		apontamento = new Apontamento();
		pesquisaString = "";
		pesquisaMostraInativos = false;
		setListApontamento(atualizaList());
	}
	
	/**
	 * Coloca a view no modo de cadastro.
	 */
	private void modo_Cadastrar() {
		setModoCadastro();
		apontamento = new Apontamento();
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
		return this.pesquisaMostraInativos && MatrizAcessoBO.acessoModuloMantemApontamento(nuPerfil, MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_EXCLUI);
	}
	
	public void setPesquisaMostraInativos(Boolean pesquisaMostraInativos) { 
		this.pesquisaMostraInativos = pesquisaMostraInativos;
	}
	
	public Apontamento getApontamento() {
		return this.apontamento;
	}
	
	public void setApontamento(Apontamento apontamento) {
		this.apontamento = apontamento;
	}
	
	public List<Apontamento> getListApontamento() {
		return this.listApontamento;
	}
	
	public void setListApontamento(List<Apontamento> listApontamento) {
		this.listApontamento = listApontamento;
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
	
	/**
	 * Valida se deve ser mostrado o painel que engloba a visualizacao e cadastro/alteracao.
	 * @return
	 */
	public boolean isShowPanelCadastroVisualizar() {
		return isModoCadastro() || isModoVisualiza() || isModoAltera();
	}
	
	/**
	 * Valida se deve ser mostrado o painel de cadastro/alteracao
	 * @return
	 */
	public boolean isShowPanelCadastro() {
		return isModoCadastro() || isModoAltera();
	}
	
	public boolean isShowAltera() {
		return MatrizAcessoBO.acessoModuloMantemApontamento(nuPerfil, MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_ALTERA);
	}
	
	public boolean isShowCadastro() {
		return isModoCadastro();
	}
	
	/**
	 * Valida se deve ser mostrado o painel de visualizacao
	 * @return
	 */
	public boolean isShowPanelVisualizar() {
		return isModoVisualiza();
	}
	
	/**
	 * Valida se deve ser mostrado o campo input do numero
	 * @return
	 */
	public boolean isShowInputNumero() {
		return isModoCadastro();
	}
	
	/**
	 * Valida se deve ser mostrado o campo output do numero
	 * @return
	 */
	public boolean isShowOutputNumero() {
		return isModoVisualiza() || isModoAltera();
	}
	
	/**
	 * Valida se deve ser desabilitado o campo input do numero
	 * @return
	 */
	public boolean isDisabledInputNumero() {
		return isModoVisualiza() || isModoAltera();
	}
	
	/**
	 * Valida se deve ser mostrado o campo input do nome
	 * @return
	 */
	public boolean isShowInputNome() {
		return isModoCadastro() || isModoAltera();
	}
	
	/**
	 * Valida se deve ser mostrado o campo output do nome
	 * @return
	 */
	public boolean isShowOutputNome() {
		return isModoVisualiza();
	}
	
	/**
	 * Valida se deve ser mostrado o botao ATIVAR na tela de visualizacao
	 * @return
	 */
	public boolean isShowButtonAtivar() {
		return isModoVisualiza() && Utilities.notEmpty(apontamento) && !apontamento.getIcAtivo();
	}
	
	/**
	 * Valida se deve ser mostrado o botao INATIVAR na tela de visualizacao
	 * @return
	 */
	public boolean isShowButtonInativar() {
		return isModoVisualiza() && Utilities.notEmpty(apontamento) && apontamento.getIcAtivo();
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
	 * Valida se deve ser mostrado o painel com o botao para chamar a tela de novo cadastro.
	 * @return
	 */
	public boolean isShowPanelButtonCadastrar() {
		return isModoInicio() && MatrizAcessoBO.acessoModuloMantemApontamento(nuPerfil, MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_INSERE);
	}
	
	/**
	 * Valida se deve ser mostrado o painel com os botoes da visualizacao.
	 * @return
	 */
	public boolean isShowPanelButtonVisualizar() {
		return isModoVisualiza();
	}
	
	/**
	 * Valida se deve ser mostrado o painel com os bot�es da insercao/alteracao.
	 * @return
	 */
	public boolean isShowPanelButtonSalvar() {
		return isModoAltera() || isModoCadastro();
	}
	
	/**
	 * Valida se deve ser mostrado o botao SALVAR (inclusao).
	 * @return
	 */
	public boolean isShowButtonInserir() {
		return isModoCadastro();
	}
	
	/**
	 * Valida se deve ser mostrado o botao SALVAR (alteracao).
	 * @return
	 */
	public boolean isShowButtonAlterar() {
		return isModoAltera();
	}
	
	/**
	 * Valida se deve ser mostrada a coluna com os campos de situacao na visualizacao
	 * @return
	 */
	public boolean isShowLabelSituacaoVisualizar() {
		return isModoVisualiza();
	}
	
	/**
	 * Valida se deve ser mostrado o campo de situacao como ATIVO
	 * @return
	 */
	public boolean isShowSituacaoVisualizarAtivo() {
		return isModoVisualiza() && Utilities.notEmpty(apontamento) && apontamento.getIcAtivo();
	}
	
	/**
	 * Valida se deve ser mostrado o campo de situacao como INATIVO
	 * @return
	 */
	public boolean isShowSituacaoVisualizarInativo() {
		return isModoVisualiza() && Utilities.notEmpty(apontamento) && !apontamento.getIcAtivo();
	}
	
}