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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.bo.IProdutoUsuarioBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.bo.IUsuarioADBO;
import br.gov.caixa.siiac.bo.IUsuarioBO;
import br.gov.caixa.siiac.bo.ListaPerfil;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.facade.IProdutoUsuarioFacade;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ProdutoUsuario;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.model.domain.Usuario;
import br.gov.caixa.siiac.util.LogCEF;

/**
 * @author GisConsult
 *
 */
@Service()
@Scope("request")
public class ProdutoUsuarioMB extends AbstractMB {
	
	/**
	 * BOs
	 */
	private transient IProdutoUsuarioFacade produtoUsuarioFacade;
	private transient IProdutoUsuarioBO produtoUsuarioBO;
	private transient IUsuarioADBO usuarioADBO;
	private transient IUsuarioBO usuarioBO;
	private transient IUnidadeBO unidadeBO;
	private transient IProdutoBO produtoBO;
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	
	private transient Usuario usuario;
	private boolean checkTodosProdutos;
	private String matricula;
	private String codProduto;
	private Short nuPerfil;
	private transient List<ProdutoUsuario> produtosAssociados;
	private List<SelectItem> selectItemProdutos;
	private transient List<SelectItem> selectItemPerfis;
	private boolean todos;
	
	private Short nuUnidade;
	private String noUnidade;
	private List<Short> listaUnidadesAbrangencia = new ArrayList<Short>();
	
	private static final String SEPARADOR_LABEL_COMBO = " - ";
	
	public ProdutoUsuarioMB() {
		matricula = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("matricula");
		
		String acao = (String) ((HttpServletRequest)getExternalContext().getRequest()).getAttribute("acao");
		if(acao.equals("visualizar")){
			setModoFiltro();
		}else{
			setModoAltera();
		}
	}
	
	/**
	 * Injeta o produtoFacade
	 * @param produtoFacade
	 */
	@Autowired
	public void setProdutoUsuarioFacade(IProdutoUsuarioFacade produtoUsuarioFacade) {
		this.produtoUsuarioFacade = produtoUsuarioFacade;
	}
	
	/**
	 * Injeta o produtoUsuarioBO
	 * @param produtoUsuarioBO
	 */
	@Autowired
	public void setProdutoUsuarioBO(IProdutoUsuarioBO produtoUsuarioBO) {
		this.produtoUsuarioBO = produtoUsuarioBO;
	}
	
	/**
	 * Injeta o produtoBO
	 * @param produtoBO
	 */
	@Autowired
	public void setProdutoBO(IProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}
	
	/**
	 * Injeta o produtoBO
	 * @param produtoBO
	 */
	@Autowired
	public void setUnidadeBO(IUnidadeBO unidadeBO) {
		this.unidadeBO = unidadeBO;
	}

	/**
	 * Injeta o usuarioADBO
	 * @param usuarioADBO
	 */
	@Autowired
	public void setUsuarioADBO(IUsuarioADBO usuarioADBO) {
		this.usuarioADBO = usuarioADBO;
	}
	
	/**
	 * Injeta o usuarioBO
	 * @param usuarioBO
	 * @throws SIIACException 
	 */
	@Autowired
	public void setUsuarioBO(IUsuarioBO usuarioBO) throws SIIACException {
		this.usuarioBO = usuarioBO;
		preencheListPerfis();
		fillCampos();
	}
	
	/**
	 * 
	 * @param matricula
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	/**
	 * 
	 * @param checkTodosProdutos
	 */
	public void setCheckTodosProdutos(boolean checkTodosProdutos) {
		this.checkTodosProdutos = checkTodosProdutos;
	}
	
	/**
	 * 
	 * @param itemProduto
	 */
	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}
	
	/**
	 * 
	 * @param nuPerfil
	 */
	public void setNuPerfil(Short nuPerfil) {
		this.nuPerfil = nuPerfil;
	}
	
	/**
	 * 
	 * @param selectItemProdutos
	 */
	public void setSelectItemProdutos(List<SelectItem> selectItemProdutos) {
		this.selectItemProdutos = selectItemProdutos;
	}
	
	/**
	 * Ordena a selectItemProdutos quee utilizada no combo de Produtos.
	 * Para tal finalidade, o metodo sort de Collections
	 * juntamente com um Comparator e utilizado.
	 * @return list de SelectItem ordenada por código do Produto
	 */
	public List<SelectItem> getSelectItemProdutos() {
		Collections.sort(selectItemProdutos, new Comparator<SelectItem>() {
			public int compare(SelectItem item1, SelectItem item2) {
				return item1.getLabel().compareTo(item2.getLabel());
			}
		});
		return this.selectItemProdutos;
	}
	
	/**
	 * 
	 * @param perfis
	 */
	public void selectItemPerfis(List<SelectItem> perfis) {
		this.selectItemPerfis = perfis;
	}
	
	/**
	 * 
	 * @return SelectItem de perfis
	 */
	public List<SelectItem> getSelectItemPerfis() {
		return this.selectItemPerfis;
	}
	
	/**
	 * 
	 * @return true ou false para o atributo checkTodosProdutos;
	 */
	public boolean isCheckTodosProdutos() {
		return this.checkTodosProdutos;
	}
	
	/**
	 * 
	 * @return codigo do produto
	 */
	public String getCodProduto() {
		return this.codProduto;
	}
	
	/**
	 * 
	 * @return nome do perfil
	 */
	public String getNoPerfil() {
		return usuario.getPerfil().getNoPerfil();
	}
	
	/**
	 * 
	 * @return número do perfil
	 */
	public Short getNuPerfil() {
		return nuPerfil;
	}
	
	/**
	 * 
	 * @return matricula do usuario
	 */
	public String getMatricula() {
		return this.matricula;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		try {
			return usuarioADBO.getNomeUsuario(usuario.getCoUsuario());
		} catch (SIIACException e) {
			LogCEF.error(e.getMessage());
			return "";
		}
	}
	/**
	 * @return the unidade
	 */
	public String getUnidade() {
		try {
			
			//Buscando unidade no banco, caso venha NULL, buscar no AD
			nuUnidade = usuarioBO.getUnidadeByUsuario(usuario.getCoUsuario());
			Unidade u = unidadeBO.getUnidade(nuUnidade);
			noUnidade = u.getNomeAbreviado();
			
			return u.getId().getNuUnidade().toString() + SEPARADOR_LABEL_COMBO + u.getNomeAbreviado();
		} catch (SIIACException e) {
			LogCEF.error(e.getMessage());
			return "";
		}
	}
	
	/**
	 * 
	 * @return lista de produtos associados ao usuario corrente
	 */
	public List<ProdutoUsuario> getProdutosAssociados() {
		return this.produtosAssociados;
	}
	
	/**
	 * 
	 * @param matricula
	 * @return list de produtos nao associados a determinada matricula
	 */
	public List<Produto> getAllNaoAssociados(String matricula) {
		return produtoUsuarioBO.getAllNaoAssociados(matricula);
	}
	
	/**
	 * E invocado no momento da criacao do ProdutoUsuarioMB e
	 * possui a finalidade de preencher todos os componentes 
	 * existentes na tela produto-usuario.xhtml.
	 * @throws SIIACException 
	 */
	private void fillCampos() throws SIIACException {
		fillDadosUsuarios();
		if (isAltera()) {
			boolean possuiP = false;
			
			if (matricula.substring(0, 1).equalsIgnoreCase("p"))
				possuiP = true;
			
			fillSelectItemProdutos();
			
			nuUnidade = usuarioBO.getUnidadeByUsuario(usuario.getCoUsuario());
			
			Unidade unidade = unidadeBO.getUnidade(nuUnidade);
			noUnidade = unidade.getNomeAbreviado();
			
			//Preenchendo a lista de unidades que o usuário têm acesso
			listaUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(
							getUsuarioLogado().getPerfis().get(0),
							getUsuarioLogado().getUnidade());
		}
		fillProdutosAssociados();
		
		if (isAltera())
		{
			todos = getPossuiTodosProdutos();
		}
	}
	
	/**
	 * Carrega o objeto usuario de acordo com a matricula
	 * existente no ProdutoUsuarioMB.
	 */
	private void fillDadosUsuarios() {
		try {
			usuario = usuarioBO.getUsuarioByMatricula(matricula);	
			nuPerfil = (short) usuario.getPerfil().getNuPerfil();
		} catch (SIIACException e) {
			LogCEF.error(e);
		}
	}
	
	
	/**
	 * Preenche a lista de produtos
	 */
	private void fillSelectItemProdutos() {
		selectItemProdutos = new ArrayList<SelectItem>();
		List<Produto> produtos = produtoUsuarioBO.getAllNaoAssociados(matricula);
		
		for (Produto p : produtos) {
			selectItemProdutos.add(new SelectItem(p.getCoProduto(), p.getCoProduto() + " - " + p.getNoProduto()));
		}
	}
	
	/**
	 * Preenche a lista de produtos associados
	 */
	private void fillProdutosAssociados() {
		produtosAssociados = new ArrayList<ProdutoUsuario>();
		produtosAssociados = produtoUsuarioBO.getAllAssociados(matricula);
	}
	
	/**
	 * Verifica se um ou todos os produtos serão adicionados na lista produtosAssociados.
	 * Utiliza o método adicionaProduto para inserir o(s) produto(s) na lista.
	 * @param ActionEvent
	 */
	public void incluirProduto(ActionEvent ev) {
		if ((getCodProduto() == null || getCodProduto().equals("")) && (selectItemProdutos == null || selectItemProdutos.size() == 0)) {
			return;
			
		} else if ((getCodProduto() == null || getCodProduto().equals("")) && (selectItemProdutos != null || selectItemProdutos.size() > 0)) {
			if (!checkTodosProdutos) {
				warn("Selecione um produto");
				return;
			} else if (checkTodosProdutos) {
				for (SelectItem item : selectItemProdutos) {
					adicionaProduto(item.getValue().toString());
				}
				selectItemProdutos.clear();
				
			}
		} else {
			adicionaProduto(getCodProduto());
		}
		getPossuiTodosProdutos();
	}
	
	/**
	 * Adiciona determinado produto na lista produtosAssociados.
	 * Através do parâmetro itemProduto o produto é obtido
	 * e salvo na lista produtosAssociados. Tal produto inserido,
	 * é removido do combo de Produtos.
	 * @param codProduto
	 */
	private void adicionaProduto(String codProduto) {
		
		try {
			Produto produto = produtoBO.findById(codProduto);
			
			ProdutoUsuario pu = new ProdutoUsuario();
			pu.setUsuario(usuario);
			pu.setProduto(produto);
			
			produtosAssociados.add(pu);
			
			limpaItemDoComboProdutos(codProduto);
		} catch (Exception e) {
			LogCEF.error(e);
		}
	}
	
	/**
	 * Retira determinado produto existente no comboProdutos.
	 * @param itemProduto
	 */
	private void limpaItemDoComboProdutos(String codProduto) {
		if (!checkTodosProdutos) {
			for (SelectItem si : selectItemProdutos) {
				if (si.getValue().toString().equals(codProduto)) {
					selectItemProdutos.remove(si);
					break;
				}
			}
		}
	}
	
	/**
	 * Método responsável por buscar o nome da unidade que o usuário
	 * digitou no momento da alteração e inclusão de um novo usuário
	 * @param evt
	 */
	public void doBuscaNomeUnidade(ActionEvent evt)
	{		
		noUnidade = "";
		
		if (nuUnidade != null && nuUnidade > 0)
		{
			if (listaUnidadesAbrangencia != null && !listaUnidadesAbrangencia.isEmpty())
			{
				if (listaUnidadesAbrangencia.contains(nuUnidade))
				{
					Unidade unidade = unidadeBO.getUnidade(nuUnidade);
					noUnidade = unidade.getNomeAbreviado();
				} else {
					warn("UNIDADE inválida!");
				}
			} else {
				Unidade unidade = unidadeBO.getUnidade(nuUnidade);
				if(unidade != null){
					noUnidade = unidade.getNomeAbreviado();
				}
			}
			
		}

	}
	
	/**
	 * Remove determinado produto da lista produtosAssociads
	 */
	public void removerProduto(ActionEvent ev) {
		ProdutoUsuario puTb = (ProdutoUsuario) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("item");
		
		// TODO: testar essa parte.
		produtosAssociados.remove(puTb);
		if(puTb.getProduto().isIcAtivo()){
			selectItemProdutos.add(
					new SelectItem(puTb.getProduto().getCoProduto(), 
							puTb.getProduto().getCoProduto() + SEPARADOR_LABEL_COMBO + puTb.getProduto().getNoProduto()));
		}
	}
	
	/**
	 * Define o status do usuario, ou seja, ativa ou inativa
	 * o usuario no sistema. Ao final o método fillCampos é invocado
	 * para que a tela seja atualizada.
	 * @throws SIIACException 
	 */
	public void doAplicaStatus() throws SIIACException {
		if (usuario.getIcAtivo()) {
			try {
				usuarioBO.ativarDesativarUsuario(matricula, false);
				info(MSGS, MN011);
			} catch (SIIACException ex) {
				LogCEF.error(ex);
			}
		} else {
			try {
				usuarioBO.ativarDesativarUsuario(matricula, true);
				info(MSGS, MN012);
			} catch (SIIACException ex) {
				LogCEF.error(ex);
			}
		}
		fillCampos();
	}
	
	/**
	 * Salva o usuario com seus respectivos atributos
	 */
	public String doSalvar() {
		if (isShowSituacaoAtivo() == false) {
			error(MSGS, MN013);
			return "";
		}
		
		try {
			Unidade unidade = unidadeBO.getUnidade(nuUnidade);
			if(unidade == null && nuUnidade != null){
				warn(MSGS, XX017);
				return "";
			}
			boolean apto = false;
			
			if (nuUnidade != null && nuUnidade > 0)
			{
				if (listaUnidadesAbrangencia != null && !listaUnidadesAbrangencia.isEmpty())
				{
					if (listaUnidadesAbrangencia.contains(nuUnidade))
					{
						apto = true;
					} else {
						apto = false;
					}
				} else {
					apto = true;
				}				
			}else{
				apto = true;
			}
			
			if (apto)
			{
				usuarioBO.gravarUsuario(getUsuarioLogado(), matricula, nuPerfil, true, nuUnidade);
			} else {
				warn("UNIDADE inválida para este Perfil.");
				return "";
			}
			
		} catch (SIIACException ex) {
			LogCEF.error(ex);
		}
		produtoUsuarioFacade.salvar(matricula, produtosAssociados);
		
		info(MSGS, MN008);
		return "toUsuario";
	}

	/**
	 * Volta para a tela de lista de usuario
	 */
	public String doCancelar() {
		warn(MSGS, MN003);
		return "toUsuario";
	}

	/**
	 * Volta para a tela de lista de usuario
	 */
	public String doVoltar() {
		return "toUsuario";
	}
	
	/**
	 * Altera o atributo STATE_MODE para MODO_ALTERA
	 * @throws SIIACException 
	 */
	public void doAlterar() throws SIIACException {
		setModoAltera();
		fillCampos();
	}
	
	/**
	 * Limpa a lista de produtos e restaura o combobox.
	 * @param evt
	 */
	public void doCheckTodos(ActionEvent evt){
		for(ProdutoUsuario p : produtosAssociados) {
			if(p.getProduto().isIcAtivo()){
				selectItemProdutos.add(new SelectItem(p.getProduto().getCoProduto(),
						p.getProduto().getCoProduto() + SEPARADOR_LABEL_COMBO + p.getProduto().getNoProduto()));
			}
		}
		produtosAssociados.clear();
	}
	
	/**
	 * Verifica se o atributo STATE_MODE esta no MODO_ALTERA
	 * @return true ou false
	 */
	private boolean isAltera() {
		return isModoAltera();
	}
	
	/**
	 * Verifica se o atributo STATE_MODE esta no MODO_CONSULTA
	 * @return true ou false
	 */
	private boolean isConsulta() {
		return isModoFiltro();
	}
	
	/**
	 * Verifica se o STATE_MODE esta no MODO_CONSULTA
	 * @return true ou false
	 */
	public boolean isShowPerfil() {
		return isConsulta();
	}
	
	public boolean isShowProdutos()
	{
		if (isConsulta())
			return true;
		
		if (todos )
			return true;
		
		return false;
	}
	
	/**
	 * Verifica se o STATE_MODE esta no MODO_ALTERA
	 * @return true ou false
	 */
	public boolean isShowComboPerfis() {
		return isAltera();
	}
	
	/**
	 * Verifica se o STATE_MODE esta no MODO_ALTERA
	 * @return true ou false
	 */
	public boolean isShowPanelProdutos() {
		return isAltera();
	}
	
	/**
	 * Verifica se o usuario esta ATIVO
	 * @return true ou false
	 */
	public boolean isShowSituacaoAtivo() {
		return usuario.getIcAtivo();
	}
	
	/**
	 * Verifica se o usuario esta INATIVO
	 * @return true ou false
	 */
	public boolean isShowSituacaoInativo() {
		return !usuario.getIcAtivo();
	}
	
	/**
	 * Verifica se o STATE_MODE esta no MODO_ALTERA
	 * @return
	 */
	public boolean isShowRemoverProduto() {
		return isAltera();
	}
	
	/**
	 * Verifica se o STATE_MODE esta no MODO_CONSULTA
	 * @return true ou false
	 */
	public boolean isShowAlterar() {
		return isConsulta();
	}
	
	/**
	 * Verifica se o STATE_MODE esta no MODO_ALTERA
	 * @return true ou false
	 */
	public boolean isShowSalvar() {
		return isAltera();
	}
	
	/**
	 * Verifica se o STATE_MODE esta no MODO_ALTERA
	 * @return true ou false
	 */
	public boolean isShowVoltar() {
		return isAltera() || isConsulta();
	}
	
	/**
	 * Verifica se o usuario nao esta ativo e
	 * se o STATE_MODE esta no MODO_CONSULTA
	 * @return true ou false
	 */
	public boolean isShowAtivarUsuario() {
		return !usuario.getIcAtivo() && isConsulta();
	}
	
	/**
	 * Verifica se o usuario esta ativo e	
	 * se o STATE_MODE esta no MODO_CONSULTA
	 * @return true ou false
	 */
	public boolean isShowInativarUsuario() {
		return usuario.getIcAtivo() && isConsulta();
	}
	
	/**
	 * Verifica se há produtos na lista de associados para que o botão de "Todos" seja renderizado.
	 * @return
	 */
	public boolean isShowButtonTodos(){
		return produtosAssociados != null && !produtosAssociados.isEmpty();
	}
	
	public boolean isShowTitleAlterar() {
		return isAltera();
	}

	public boolean isShowTitleVisualizar() {
		return isConsulta();
	}

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}
	
	public Short getNuUnidade() {
		return nuUnidade;
	}

	public void setNuUnidade(Short nuUnidade) {
		this.nuUnidade = nuUnidade;
	}

	public String getNoUnidade() {
		return noUnidade;
	}

	public void setNoUnidade(String noUnidade) {
		this.noUnidade = noUnidade;
	}

	public boolean getPossuiTodosProdutos()
	{
		if (produtosAssociados.isEmpty())
		{
			return true;
		}
		
		return false;
	}

	@Autowired
	public void setMatrizAbrangenciaBO(IMatrizAbrangenciaBO matrizAbrangenciaBO) {
		this.matrizAbrangenciaBO = matrizAbrangenciaBO;
	}
	
	private void preencheListPerfis() {
		if (isGestorSistema()) {
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_GESTOR_SISTEMA, ListaPerfil.PERFIL_NOME_GESTOR_SISTEMA));
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_AUDITOR, ListaPerfil.PERFIL_NOME_AUDITOR));
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE, ListaPerfil.PERFIL_NOME_CENTRAL_DE_ATENDIMENTO_E_SUPORTE));
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_GESTOR_PRODUTO, ListaPerfil.PERFIL_NOME_GESTOR_PRODUTO));
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_REGIONAL_CONFORMIDADE, ListaPerfil.PERFIL_NOME_REGIONAL_CONFORMIDADE));
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_VERIFICADOR_NACIONAL, ListaPerfil.PERFIL_NOME_VERIFICADOR_NACIONAL));
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_VERIFICADOR_REGIONAL, ListaPerfil.PERFIL_NOME_VERIFICADOR_REGIONAL));
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_UNIDADE_ATENDIMENTO, ListaPerfil.PERFIL_NOME_UNIDADE_ATENDIMENTO));
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_VERIFICADOR_CONFORMIDADE, ListaPerfil.PERFIL_NOME_VERIFICADOR_CONFORMIDADE));
		}
		if (isRegionalConformidade()) {
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_VERIFICADOR_REGIONAL, ListaPerfil.PERFIL_NOME_VERIFICADOR_REGIONAL));
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_VERIFICADOR_CONFORMIDADE, ListaPerfil.PERFIL_NOME_VERIFICADOR_CONFORMIDADE));
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_UNIDADE_ATENDIMENTO, ListaPerfil.PERFIL_NOME_UNIDADE_ATENDIMENTO));
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_AUDITOR, ListaPerfil.PERFIL_NOME_AUDITOR));
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE, ListaPerfil.PERFIL_NOME_CENTRAL_DE_ATENDIMENTO_E_SUPORTE));
		}
		if (isUnidadeAtendimento()) {
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_UNIDADE_ATENDIMENTO, ListaPerfil.PERFIL_NOME_UNIDADE_ATENDIMENTO));
		}
		if (isCentralAtendimentoSuporte()) {
			selectItemPerfis.add(new SelectItem(ListaPerfil.PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE, ListaPerfil.PERFIL_NOME_CENTRAL_DE_ATENDIMENTO_E_SUPORTE));
		}
		Collections.sort(selectItemPerfis, new Comparator<SelectItem>() {
			public int compare(SelectItem item1, SelectItem item2) {
				return item1.getLabel().toString().compareTo(item2.getLabel().toString());
			}
		});
	}
}
