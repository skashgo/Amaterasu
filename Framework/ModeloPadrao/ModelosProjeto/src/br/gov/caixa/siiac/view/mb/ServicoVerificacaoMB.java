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

import br.gov.caixa.siiac.bo.IServicoVerificacaoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.util.LogCEF;

/**
 * @author GisConsult
 *
 */
@Service()
@Scope("request")
public class ServicoVerificacaoMB extends AbstractMB {
	
	private transient IServicoVerificacaoBO servicoVerificacaoBO;
	private transient List<ServicoVerificacao> servicosVerificacao;
	private transient ServicoVerificacao servicoVerificacao = new ServicoVerificacao();

	/**
	 * Filtro
	 */
	private String pesquisaString;
	private Boolean pesquisaMostraInativos;
	
	/**
	 * No momento em que o managed bean ServicoVerificacaoMB
	 * e criado, o atributo STATE_MODE recebe o valor MODO_CONSULTA.
	 */
	public ServicoVerificacaoMB() {
		setModoFiltro();
	}
	
	@Autowired
	public void setServicoVerificacaoBO(IServicoVerificacaoBO servicoVerificacaoBO) {
		this.servicoVerificacaoBO = servicoVerificacaoBO;
		modo_Filtro();
	}
	
	public void setServicoVerificacao(ServicoVerificacao servicoVerificacao) {
		this.servicoVerificacao = servicoVerificacao;
	}
	

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
	
	/**
	 * 
	 * @return ServicoVerificacao
	 */
	public ServicoVerificacao getServicoVerificacao() {
		return this.servicoVerificacao;
	}
	
	/**
	 * 
	 * @return lista de servicos de verificacao
	 */
	public List<ServicoVerificacao> getServicosVerificacao() {
		return this.servicosVerificacao;
	}
	
	/**
	 * Preenche as op��es Ativo e Inativo existentes no Radio Button.
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
		servicoVerificacao = new ServicoVerificacao();
		servicosVerificacao = atualizaList();
	}
	
	/**
	 * Ativa o servico de verificacao (grid)
	 */
	public void doAtivarGrid() {
		ServicoVerificacao servicoVerificacao = obtemServicoVerificacaoPopulado();
		servicoVerificacaoBO.ativar(servicoVerificacao);
		modo_Filtro();
		info(MSGS, MN012);
	}
	
	/**
	 * Inativa o servico de verificacao (grid)
	 */
	public void doInativarGrid() {
		ServicoVerificacao servicoVerificacao = obtemServicoVerificacaoPopulado();
		servicoVerificacaoBO.inativar(servicoVerificacao);
		modo_Filtro();
		info(MSGS, MN011);
	}
	
	/**
	 * Recupera o servico de verificacao existente em determinada linha da 
	 * tabela tbServicosVerificacao quando o botao Inativar ou Ativar e clicado.
	 * @return
	 */
	private ServicoVerificacao obtemServicoVerificacaoPopulado() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServicoVerificacao populado = (ServicoVerificacao) facesContext.getExternalContext().getRequestMap().get("servicoVerificacao");
		
		return populado;
	}

	/**
	 * Atualiza a lista da tabela.
	 * @return
	 */
	private List<ServicoVerificacao> atualizaList() {
		try {
			return servicoVerificacaoBO.getListFiltro(pesquisaString, pesquisaMostraInativos);
		} catch (SIIACException e) {
			warn(MSGS, MN016);
			LogCEF.error(e.getMessage());
		}
		return new ArrayList<ServicoVerificacao>();
	}
	
	
	/**
	 * Verifica se determinado servico de verificacao 
	 * ja existe no banco de dados.
	 * @param servicoVerificacao
	 * @return true se o servico de verificacao ja existe ou false caso contrario
	 */
	private boolean jaExiste(ServicoVerificacao servicoVerificacao) {
		return servicoVerificacaoBO.jaExiste(servicoVerificacao);
	}
		
	
	/**
	 * Realiza a consulta.
	 */
	public void doFiltrar(ActionEvent evt) {
		List<ServicoVerificacao> lista = atualizaList();
		if (lista == null || lista.isEmpty()) {
			warn(MSGS, MN016);
		}
		servicosVerificacao = lista;
	}
	
	
	/**
	 * Realiza a persistencia do servico de verificacao
	 * no banco de dados.
	 * @return
	 */
	public void doSalvar() {
		if (servicoVerificacao.getNoServicoVerificacao().trim().equals("")) {
			error(MSGS, MN002);
			warn( "Campo NOME Obrigatório.");
			return;
		}
		if (isModoCadastro()) {
			
			if (jaExiste(servicoVerificacao)) {
				error(MSGS, MN015);
				return;
			} else {
				servicoVerificacaoBO.salvar(servicoVerificacao);
				info(MSGS, MN007);
				modo_Filtro();
			}
		} else {
			if (jaExiste(servicoVerificacao)) {
				warn(MSGS, MN015);
				return;

			} else if (!servicoVerificacaoBO.isAtivo(servicoVerificacao.getNuServicoVerificacao())) {
				warn(MSGS, MN013);
				return;
			} else {
				servicoVerificacaoBO.update(servicoVerificacao);
				info(MSGS, MN008);
				servicoVerificacao = new ServicoVerificacao();
				modo_Filtro();
			}
		}
	}
		
	/**
	 * Ativa o modo altera_resumo caso o servico de verificacao
	 * nao esteja inativo.
	 */
	public void doAlteraResumo() {
		setModoVisualiza();
		ServicoVerificacao servicoObtidoDoGrid = obtemServicoVerificacaoPopulado();
		servicoVerificacao.setNuServicoVerificacao(servicoObtidoDoGrid.getNuServicoVerificacao());
		servicoVerificacao.setNoServicoVerificacao(servicoObtidoDoGrid.getNoServicoVerificacao());
		servicoVerificacao.setIcAtivo(servicoObtidoDoGrid.isIcAtivo());
	}
	
	/**
	 * Altera o STATE_MODE para MODO_ALTERA.
	 */
	public void doAlterar() {
		setModoAltera();
		ServicoVerificacao servicoObtidoDoGrid = obtemServicoVerificacaoPopulado();
		servicoVerificacao.setNuServicoVerificacao(servicoObtidoDoGrid.getNuServicoVerificacao());
		servicoVerificacao.setNoServicoVerificacao(servicoObtidoDoGrid.getNoServicoVerificacao());
		servicoVerificacao.setIcAtivo(servicoObtidoDoGrid.isIcAtivo());
	}
	
	/**
	 * Ativa o STATE_MODE para MODO_CONSULTA.
	 */
	public void doCancelar() {
		warn(MSGS, MN003);
		modo_Filtro();
		
	}
	
	/**
	 * Ativa o STATE_MODE para o MODO_CADASTRO.
	 */
	public void doCadastrar(ActionEvent evt) {
		setModoCadastro();
		servicoVerificacao = new ServicoVerificacao();
	}
	
	public void doMudaStatus() {
		if (servicoVerificacao.isIcAtivo()) {
			servicoVerificacaoBO.inativar(servicoVerificacao);
			info(MSGS, MN011);
		} else {
			servicoVerificacaoBO.ativar(servicoVerificacao);
			info(MSGS, MN012);
		}
	}
	
	/**
	 * Ativa o STATE_MODE para o MODO_CADASTRO.
	 */
	public void doVoltar() {
		setModoFiltro();
		servicoVerificacao = new ServicoVerificacao();
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
	 * MODO_ALTERADO.
	 * @return true ou false
	 */
	public boolean isAltera() {
		return isModoAltera();
	}
	
	public boolean isShowPanelCadastro() {
		return isModoAltera() || isModoCadastro();
	}
	
	/**
	 * Verifica se o STATE_MODE esta setado no
	 * MODO_ALTERA_RESUMO.
	 * @return
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
	
	/**
	 * Verifica se o servico de verificacao esta inativo
	 * @return true se o servico de verificacao esta inativo ou
	 * 			false caso contrario
	 */
	public boolean isShowAtivar() {
		return !servicoVerificacao.isIcAtivo();
	}
	
	/**
	 * Verifica se o servico de verificacao esta ativo
	 * @return true se o servico de verificacao esta ativo
	 * 			ou false caso contrario
	 */
	public boolean isShowInativar() {
		return servicoVerificacao.isIcAtivo();
	}
	
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
		return isAltera_Resumo() && servicoVerificacao.isIcAtivo();
	}
	
	/**
	 * Controla a exibicao do botao Ativar.
	 * @return true ou false
	 */
	public boolean isShowBtnAtivar() {
		return isAltera_Resumo() && !servicoVerificacao.isIcAtivo();
	}
	
	/**
	 * Controla exibicao do botao Voltar.
	 * @return true ou false
	 */
	public boolean isShowBtnVoltar() {
		return isAltera_Resumo();
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
		return servicoVerificacao.isIcAtivo();
	}
	
	/**
	 * Controla a exibicao do botao e do icone Inativo.
	 * @return true ou false
	 */
	public boolean isShowSituacaoInativo() {
		return !servicoVerificacao.isIcAtivo();
	}
	
	/**
	 * Controla a exibicao do titulo na tela de pesquisa.
	 * @return true ou false
	 */
	public boolean isShowTituloPesquisar() {
		return isConsulta();
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
	 * Controla a exibicao do titulo.
	 * @return true ou false
	 */
	public boolean isShowHeader() {
		return true;
	}
	
	/**
	 * Verifica se deve ser mostrado o título de alteração
	 */
	public boolean isShowTitleAlterar() {
		return isModoAltera();
	}
	
	public boolean isShowAltera() {
		return isAltera();
	}
	
	/**
	 * Controla a exibicao do panel principal.
	 * @return true ou false.
	 */
	public boolean isShowPanelPrincipal() {
		return isModoFiltro() || isModoInicio();
	}
	
	/**
	 * Controla a exibicao da tabela servicos de verificacao.
	 * @return true ou false.
	 */
	public boolean isShowTable() {
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
}
