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

import br.gov.caixa.siiac.bo.IItemVerificacaoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.ItemVerificacao;
import br.gov.caixa.siiac.util.LogCEF;

@Service()
@Scope("request")
public class ItemVerificacaoMB extends AbstractMB {
	
	private transient IItemVerificacaoBO itemVerificacaoBO;
	private transient ItemVerificacao itemVerificacao = new ItemVerificacao();
	private transient List<ItemVerificacao> itensVerificacao;

	/**
	 * Filtro
	 */
	private String pesquisaString;
	private Boolean pesquisaMostraInativos;
	private Boolean pesquisaControleDataValidade;
	
	
	public ItemVerificacaoMB() {
		setModoFiltro();
	}
	
	@Autowired
	public void setItemVerificacaoBO(IItemVerificacaoBO itemVerificacaoBO) {
		this.itemVerificacaoBO = itemVerificacaoBO;
		itensVerificacao = atualizaList();
	}
	
	public void setItemVerificacao(ItemVerificacao itemVerificacao) {
		this.itemVerificacao = itemVerificacao;
	}
	
	public ItemVerificacao getItemVerificacao() {
		return this.itemVerificacao;
	}
	
	public List<ItemVerificacao> getItensVerificacao() {
		return this.itensVerificacao;
	}
	
	/**
	 * Preenche as opcoes Ativo e Inativo existentes
	 * no Radio Button.
	 * @return lista de SelectItem
	 */
	public List<SelectItem> getOpcoes() {
		List<SelectItem> opcoes = new ArrayList<SelectItem>();
		opcoes.add(new SelectItem(true, "Ativos"));
		opcoes.add(new SelectItem(false, "Inativos"));
		
		return opcoes;
	}

	/**
	 * Coloca a view no modo inicial.
	 */
	private void modo_Filtro() {
		setModoFiltro();
		pesquisaString = "";
		pesquisaMostraInativos = false;
		itemVerificacao = new ItemVerificacao();
		itensVerificacao = atualizaList();
	}
	
	/**
	 * Ativa o item de verificacao.
	 */
	public void doAtivar() {
		ItemVerificacao itemVerificacao = obtemItemVerificacaoPopulado();
		itemVerificacaoBO.ativar(itemVerificacao);
		modo_Filtro();
	}
	
	/**
	 * Ativa o servico de verificacao no grid.
	 */
	public void doAtivarGrid() {
		this.doAtivar();
		info(MSGS, MN012);
	}
	
	/**
	 * Inativa o item de verificacao.
	 */
	public void doInativar() {
		ItemVerificacao itemVerificacao = obtemItemVerificacaoPopulado();
		itemVerificacaoBO.inativar(itemVerificacao);
		modo_Filtro();
	}
	
	/**
	 * Inativa o servico de verificacao no grid.
	 */
	public void doInativarGrid() {
		this.doInativar();
		info(MSGS, MN011);
	}
	
	/**
	 * Recupera o item de verificacao existente em determinada linha da 
	 * tabela tbItensVerificacao quando o botao Inativar ou Ativar e clicado.
	 * @return
	 */
	private ItemVerificacao obtemItemVerificacaoPopulado() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ItemVerificacao populado = (ItemVerificacao) facesContext.getExternalContext().getRequestMap().get("itemVerificacao");
		
		return populado;
	}
	
	/**
	 * Verifica se determinado item de verificacao 
	 * ja existe no banco de dados.
	 * @param itemVerificacao
	 * @return true se o item de verificacao ja existe ou false caso contrario
	 */
	private boolean jaExiste(ItemVerificacao itemVerificacao) throws SIIACException {
		return itemVerificacaoBO.jaExiste(itemVerificacao);
	}

	/**
	 * Atualiza a lista da tabela.
	 * @return
	 */
	private List<ItemVerificacao> atualizaList() {
		try {
			return itemVerificacaoBO.getListFiltro(pesquisaString, pesquisaMostraInativos, pesquisaControleDataValidade);
		} catch (SIIACException e) {
			warn(MSGS, MN016);
			LogCEF.warn(e.getMessage());
		}
		return new ArrayList<ItemVerificacao>();
	}
	
	
	/**
	 * Retorna as propriedades do objeto itemVerificacao
	 * ao seu estado padrao.
	 */
	private void limparItemDeVerificacao() {
		this.itemVerificacao = new ItemVerificacao();
	}
	
	/**
	 * Realiza a consulta.
	 */
	public void doFiltrar(ActionEvent evt) {
		List<ItemVerificacao> lista = atualizaList();
		if (lista == null || lista.isEmpty()) {
			warn(MSGS, MN016);
		}
		itensVerificacao = lista;
	}
	
	/**
	 * Realiza a persist�ncia do item de verificacao
	 * no banco de dados.
	 */
	public void doSalvar() {

		if (isModoAltera() && !itemVerificacaoBO.isAtivo(itemVerificacao)) {
			error(MSGS, MN013);
			return;
		}
		
		if (itemVerificacao.getNoItemVerificacao() == null || itemVerificacao.getNoItemVerificacao().trim().equals("")) {
			error(MSGS, MN002);
			warn( "Campo NOME não informado.");
			return;
		}
		
		if (isModoCadastro()) {
			boolean salvou = salvar();
			if (salvou) {
				modo_Filtro();
			}
		} else {
			try {
				if (jaExiste(itemVerificacao)) {
					verificaAtividadeInatividade(itemVerificacao);
				} else {
					atualizar(itemVerificacao);
					info(MSGS, MN008);
					modo_Filtro();
				}
			} catch (SIIACException ex) {
				error(MSGS, MN002);
				LogCEF.error(ex.getMessage());
			}
		}
	}
	
	public void modoCadastrar() {
		setModoCadastro();
		itemVerificacao = new ItemVerificacao();
		itemVerificacao.setIcDataValidade(false);
	}
	
	/**
	 * Verifica se o item de verificacao esta ativo ou inativo e
	 * com base nessa analise faz a impressao das respectivas mensagens.
	 * @param itemVerificacao
	 */
	private void verificaAtividadeInatividade(ItemVerificacao itemVerificacao) {
		warn(MSGS, MN015);
	}
	
	/**
	 * Atualiza o item de verificacao.
	 * @param itemVerificacao
	 */
	private void atualizar(ItemVerificacao itemVerificacao) {
		itemVerificacaoBO.update(itemVerificacao);
	}
	
	/**
	 * Salva o item de verificacao.
	 * @return true se o item de verificacao foi salvo, ou false caso contrario
	 */
	private boolean salvar() {
		try {
			if (jaExiste(itemVerificacao)) {
				verificaAtividadeInatividade(itemVerificacao);
			} else {
				itemVerificacaoBO.salvar(itemVerificacao);
				info(MSGS, MN007);
				return true;
			}
		} catch (SIIACException ex) {
			error(MSGS, MN002);
			LogCEF.error(ex.getMessage());
		}
		return false;
	}
	
	/**
	 * Altera o estado da tela para o MODO_ALTERA_RESUMO e faz
	 * determinadas verificacoes.
	 */
	public void doAlteraResumo() {
		setModoVisualiza();
		itemVerificacao = new ItemVerificacao();
		ItemVerificacao itemObtidoDoGrid = obtemItemVerificacaoPopulado();
		itemVerificacao.setNuItemVerificacao(itemObtidoDoGrid.getNuItemVerificacao());
		itemVerificacao.setNoItemVerificacao(itemObtidoDoGrid.getNoItemVerificacao());
		itemVerificacao.setIcAtivo(itemObtidoDoGrid.getIcAtivo());
		itemVerificacao.setIcDataValidade(itemObtidoDoGrid.getIcDataValidade());
	}
	
	/**
	 * Altera o STATE_MODE para MODO_ALTERA.
	 */
	public void doAlterar() {
		setModoAltera();
		itemVerificacao = new ItemVerificacao();
		ItemVerificacao itemObtidoDoGrid = obtemItemVerificacaoPopulado();
		itemVerificacao.setNuItemVerificacao(itemObtidoDoGrid.getNuItemVerificacao());
		itemVerificacao.setNoItemVerificacao(itemObtidoDoGrid.getNoItemVerificacao());
		itemVerificacao.setIcAtivo(itemObtidoDoGrid.getIcAtivo());
		itemVerificacao.setIcDataValidade(itemObtidoDoGrid.getIcDataValidade());
		itemVerificacao.setNoResumidoItemVerificacao(itemObtidoDoGrid.getNoResumidoItemVerificacao());
	}
	
	/**
	 * Altera o STATE_MODE para MODO_CONSULTA.
	 */
	public void doCancelar() {
		warn(MSGS, MN003);
		setModoFiltro();
		limparItemDeVerificacao();
		modo_Filtro();
	}
	
	/**
	 * Altera o STATE_MODE para MODO_CADASTRO.
	 */
	public void doCadastrar(ActionEvent evt) {
		modoCadastrar();
	}
	
	/**
	 * Altera o STATE_MODE para MODO_CONSULTA.
	 */
	public void doContinuar() {
		setModoFiltro();
	}
	
	/**
	 * Ativa ou inativa o item de verificacao com base em seu
	 * estado atual.
	 */
	public void doMudaStatus() {
		if (itemVerificacao.getIcAtivo()) {
			itemVerificacaoBO.inativar(itemVerificacao);
			info(MSGS, MN011);
		} else {
			itemVerificacaoBO.ativar(itemVerificacao);
			info(MSGS, MN012);
		}
		modo_Filtro();
	}
	
	/**
	 * Altera o STATE_MODE para MODO_CONSULTA e reseta
	 * o item de verificacao.
	 */
	public void doVoltar() {
		setModoFiltro();
		itemVerificacao = new ItemVerificacao();
		modo_Filtro();
	}
	
	/**
	 * Verifica se o STATE_MODE esta setado no
	 * MODO_CONSULTA.
	 * @return true ou false
	 */
	public boolean isConsulta() {
		return isModoFiltro();
	}
	
	/**
	 * Verifica se o STATE_MODE esta setado no
	 * MODO_ALTERA.
	 * @return true ou false
	 */
	public boolean isAltera() {
		return isModoAltera();
	}
	
	/**
	 * Verifica se o STATE_MODE esta setado no
	 * MODO_ALTERA_RESUMO.
	 * @return true ou false
	 */
	public boolean isAltera_Resumo() {
		return isModoVisualiza();
	}
	
	/**
	 * Verifica se o STATE_MODE esta setado no
	 * MODO_CADASTRO.
	 * @return true ou false
	 */
	public boolean isCadastro() {
		return isModoCadastro();
	}
	
	/**
	 * Controla a exibicao do selectOneRadio.
	 * @return true ou false
	 */
	public boolean isShowOneRadio() {
		return isConsulta();
	}
//	
//	/**
//	 * Verifica se o item de verificacao esta inativo
//	 * @return true se o item de verificacao esta inativo ou
//	 * 			false caso contrario
//	 */
//	public boolean isShowAtivar() {
//		return !pesquisaMostraInativos;
//	}
//	
//	/**
//	 * Verifica se o item de verificacao esta ativo
//	 * @return true se o item de verificacao esta ativo
//	 * 			ou false caso contrario
//	 */
//	public boolean isShowInativar() {
//		return !pesquisaMostraInativos;
//	}
	
	/**
	 * Verifica se a opcao ALTERAR esta configurada.
	 * @return true ou false
	 */
	public boolean isShowAlterar() {
		return isConsulta();
	}
	
	/**
	 * Controla a exibicao do botao Atualizar.
	 * @return true ou false
	 */
	public boolean isShowBtnAtualizar() {
		return isAltera();
	}
	
	/**
	 * Controla a exibicao do botao Alterar.
	 * @return true ou false
	 */
	public boolean isShowBtnAlterar() {
		return isAltera_Resumo();
	}
	
	/**
	 * Controla a exibicao do botao Inativar.
	 * @return true ou false.
	 */
	public boolean isShowBtnInativar() {
		return isAltera_Resumo() && itemVerificacao.getIcAtivo();
	}
	
	/**
	 * Controla a exibicao do botao Ativar.
	 * @return true ou false
	 */
	public boolean isShowBtnAtivar() {
		return isAltera_Resumo() && !itemVerificacao.getIcAtivo();
	}
	
	/**
	 * Controla exibicao do botao Voltar.
	 * @return true ou false
	 */
	public boolean isShowBtnVoltar() {
		return isAltera_Resumo();
	}
	
	/**
	 * Controla exibicao do campo codigo.
	 * @return true ou false
	 */
	public boolean isShowCampoCodigo() {
		return isCadastro();
	}
	
	/**
	 * Exibe o botao cancelar.
	 * @return true ou false.
	 */
	public boolean isShowCancelar() {
		return isAltera() || isCadastro();
	}
	
	/**
	 * Controla a exibicao do botao Consultar.
	 * @return true ou false.
	 */
	public boolean isShowConsultar() {
		return isConsulta();
	}
	
	/**
	 * Controla a exibicao do botao Salvar.
	 * @return true ou false
	 */
	public boolean isShowSalvar() {
		return isCadastro();
	}
	
	/**
	 * Controla a exibicao do botao e do icone Ativo.
	 * @return true ou false
	 */
	public boolean isShowSituacaoAtivo() {
		return itemVerificacao.getIcAtivo();
	}
	
	/**
	 * Controla a exibicao do botao e do icone Inativo.
	 * @return true ou false
	 */
	public boolean isShowSituacaoInativo() {
		return !itemVerificacao.getIcAtivo();
	}
	
	/**
	 * Controla a exibicao do titulo na tela de pesquisa.
	 * @return true ou false
	 */
	public boolean isShowTituloPesquisar() {
		return isConsulta();
	}
	
	/**
	 * Controla a exibicao do titulo.
	 * @return true ou false
	 */
	public boolean isShowHeader() {
		return true;
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
	 * Controla a exibicao do panel principal.
	 * @return true ou false.
	 */
	public boolean isShowPanelPrincipal() {
		return isModoFiltro() || isModoInicio();
	}
	
	/**
	 * Controla a exibicao da tabela itens de verificacao.
	 * @return true ou false.
	 */
	public boolean isShowTableItensVerificacao() {
		return isConsulta();
	}
	
	/**
	 * Controla a exibicao do bread de inicio.
	 * @return true ou false
	 */
	public boolean isShowBreadInicio() {
		return isConsulta();
	}
	
	/**
	 * Controla a exibicao do bread de cadastro.
	 * @return true ou false
	 */
	public boolean isShowBreadNovo() {
		return isCadastro();
	}
	
	/**
	 * Controla a exibicao do bread de alteracao.
	 * @return true ou false
	 */
	public boolean isShowBreadAlterar() {
		return isAltera_Resumo();
	}
	
	/**
	 * Controla a exibicao do bread de edicao.
	 * @return true ou false
	 */
	public boolean isShowBreadEditar() {
		return isAltera();
	}

	/**
	 * Valida se deve ser mostrado o painel com o botao para chamar a tela de novo cadastro.
	 * @return
	 */
	public boolean isShowPanelButtonCadastrar() {
		return isModoFiltro();
	}
	
	/**
	 * Getters and Setters
	 */

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

	public Boolean getPesquisaControleDataValidade() {
		return pesquisaControleDataValidade;
	}

	public void setPesquisaControleDataValidade(Boolean pesquisaControleDataValidade) {
		this.pesquisaControleDataValidade = pesquisaControleDataValidade;
	}
}