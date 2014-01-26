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
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.bo.IProdutoUsuarioBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.bo.IUsuarioADBO;
import br.gov.caixa.siiac.bo.IUsuarioBO;
import br.gov.caixa.siiac.bo.ListaPerfil;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.model.domain.Usuario;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

/**
 * @author GisConsult
 *
 */
@Service()
@Scope("request")
public class UsuarioMB extends AbstractMB {

	/**
	 * Filtro Simples
	 */
	private String pesquisaString;
	private Boolean pesquisaMostraInativos;
	
	private static final String SEPARADOR_NOME_UNIDADE = " - ";
	public static final int TAMANHO_MATRICULA = 6;
	private transient boolean showGrid = true;
	private transient boolean showConsultarSimples = true;
	private transient boolean showConsultarAvancado = false;
	private transient boolean showCadastro = false;
	private transient boolean showButtonNovo = true;
	private Boolean todosProdutos = true;
	private transient boolean perfilInativo = false;
	
	private boolean alterar = false;
	private boolean showListProdutos = false;
	private boolean todos = true;
	private String matricula;
	private String noUsuario;
	private String filtroNome;
	private String filtroUnidade;
	private String filtroMatricula;
	
	private String cadProduto;
	private Short filtroPerfil;
	private Short perfil;
	private Integer showSituacao = 1;
	private String rotularColuna = "INATIVAR";
	
	private Short nuUnidade;
	private String noUnidade;
	private List<Short> listaUnidadesAbrangencia = new ArrayList<Short>();
	
	//TODO Este objeto n�o deve ser inicializado aqui, deve ser inicializado somente quando for usado.
	private transient HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	private List<Usuario> usuarioList = new ArrayList<Usuario>();
	private List<Produto> produtoList = new ArrayList<Produto>();
	private List<Produto> produtoListCombo = new ArrayList<Produto>();
	
	private static final String MENSAGEM = "msgsUsuario";
	private static final String COLUNA_INATIVAR = "COLUMN.inativar";
	private static final String COLUNA_ATIVAR = "COLUMN.ativar";
	
	//Variavel para controlar quando o campo de situacao e alterado para a consulta e o retorno e vazio, voltando o valor para seu antigo.
	private Integer situacaoAntiga = null;
	
	private static final String STYLE_NOME_USUARIO = "font-weight:bold;color:Red;";
	private transient String nomeUsuario;
	private transient String style;
	
	private IUsuarioBO usuarioBO;
	private IUsuarioADBO usuarioADBO;
	private IUnidadeBO unidadeBO;
	private IProdutoUsuarioBO produtoUsuarioBO;
	private IProdutoBO produtoBO;
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	
	private List<SelectItem> listPerfil = new ArrayList<SelectItem>();
	private List<Integer> listPerfisUsuario = new ArrayList<Integer>();
	
	
	@Autowired
	public void setUsuarioBO(IUsuarioBO usuarioBO) {
		this.usuarioBO = usuarioBO;
		
		// TODO: Refatorar para usar @PostConstruct
		fillCampos();
	}

	@Autowired
	public void setUnidadeBO(IUnidadeBO unidadeBO) {
		this.unidadeBO = unidadeBO;
	}
	
	@Autowired
	public void setUsuarioADBO(IUsuarioADBO usuarioADBO) {
		this.usuarioADBO = usuarioADBO;
	}
	
	@Autowired
	public void setProdutoUsuarioBO(IProdutoUsuarioBO produtoUsuarioBO) {
		this.produtoUsuarioBO = produtoUsuarioBO;
	}
	
	@Autowired
	public void setMatrizAbrangenciaBO(IMatrizAbrangenciaBO matrizAbrangenciaBO) {
		this.matrizAbrangenciaBO = matrizAbrangenciaBO;
	}

	@Autowired
	public void setProdutoBO(IProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
		produtoListCombo = produtoBO.getListProduto();
	}
	
	public UsuarioMB() {
		produtoList = new ArrayList<Produto>();
		preencheListPerfis();
	}
	
	public void fillCampos() {
		session.setAttribute("NomeUsuario", "");
		filtroNome = "";
		filtroUnidade = "";
		filtroMatricula = "";
		pesquisaMostraInativos = false;
		setFiltroNome("");
		setFiltroUnidade("");
		setFiltroMatricula("");
		rotularColuna = "ATIVAR";
		showSituacao = 1;
		setFiltro();
		
		if(getFilterBase().isModoAvancado()){
			try {
				setUsuarioList(carregaListaUsuario(true));
				showConsultarSimples = false;
				showConsultarAvancado = true;
			} catch (SIIACException e) {
				error(MSGS, e.getMessage());
				LogCEF.error(e.getMessage());
			}
		}else{
			try {
				if(getFilterBase().getString("pesquisaString") != null || getFilterBase().getBoolean("pesquisaMostraInativos") != false){
					setUsuarioList(carregaListaUsuarioFiltroSimples());
				}else{
					setUsuarioList(carregaListaUsuario(false));
				}
			
				showConsultarSimples = true;
				showConsultarAvancado =  false;
			} catch (SIIACException e) {
				error(MSGS, e.getMessage());
				LogCEF.error(e.getMessage());
			}
			
		}
		
		
		
		//Preenchendo a lista de unidades que o usuário têm acesso
		listaUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(
						getUsuarioLogado().getPerfis().get(0),
						getUsuarioLogado().getUnidade());
		
	}
	
	private void setFiltro(){
		getFilterInSession(this.getClass());
		
		pesquisaMostraInativos = getFilterBase().getBoolean("pesquisaMostraInativos");
		filtroNome = getFilterBase().getString("filtroNome");
		filtroUnidade = getFilterBase().getString("filtroUnidade");
		filtroMatricula = getFilterBase().getString("filtroMatricula");
		filtroPerfil = getFilterBase().getShort("filtroPerfil");
		
		pesquisaString = getFilterBase().getString("pesquisaString");
		pesquisaMostraInativos = getFilterBase().getBoolean("pesquisaMostraInativos");
	}
	
	/**
	 * isAlterar
	 * @return
	 */
	public boolean isAlterar() {
		return this.alterar;
	}
	
	/**
	 * setAlterar
	 * @param alterar
	 */
	private void setAlterar(boolean alterar) {
		this.alterar = alterar;
	}
	
	/**
	 * getMatricula
	 * @return
	 */
	public String getMatricula() {
		return matricula;
	}
	
	/**
	 * setMatricula
	 * @param matricula
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	/**
	 * getNoUsuario
	 * @return
	 */
	public String getNoUsuario() {
		return noUsuario;
	}
	
	/**
	 * setNoUsuario
	 * @param noUsuario
	 */
	public void setNoUsuario(String noUsuario) {
		this.noUsuario = noUsuario;
	}
	
	/**
	 * getFiltroPerfil
	 * @return
	 */
	public Short getFiltroPerfil() {
		return filtroPerfil;
	}
	
	/**
	 * setFiltroPerfil
	 * @param filtroPerfil
	 */
	public void setFiltroPerfil(Short filtroPerfil) {
		this.filtroPerfil = filtroPerfil;
	}
	
	/**
	 * getPerfil
	 * @return
	 */
	public Short getPerfil() {
		return perfil;
	}
	
	/**
	 * setPerfil
	 * @param perfil
	 */
	public void setPerfil(Short perfil) {
		this.perfil = perfil;
	}
	
	/**
	 * getListPerfil - Lista de Perfis - combo
	 * @return
	 */
	public List<SelectItem> getListPerfil() {
		return listPerfil;
	}
	
	/**
	 * getCadProduto
	 * @return
	 */
	public String getCadProduto() {
		return cadProduto;
	}
	
	/**
	 * setCadProduto
	 * @param cadProduto
	 */
	public void setCadProduto(String cadProduto) {
		this.cadProduto = cadProduto;
	}
	
	/**
	 * getListProduto - Lista de Produtos - combo
	 * @return
	 */
	public List<SelectItem> getListProduto() {
		List<SelectItem> list = new ArrayList<SelectItem>();
//		if (produtoListCombo.size() > 0) {
//			list.add(new SelectItem("0", "TODOS OS PRODUTOS"));
//		}
		
		for (Produto pu : produtoListCombo) {
			if(pu.isIcAtivo()){
				list.add(new SelectItem(pu.getCoProduto(), pu.getCoProduto() + SEPARADOR_NOME_UNIDADE + pu.getNoProduto()));
			}
		}
		Collections.sort(list, new Comparator<SelectItem>() {
			public int compare(SelectItem obj1, SelectItem obj2) {
				return obj1.getLabel().compareTo(obj2.getLabel());
			}
		});
		return list;
	}
	
	/**
	 * carregaListaUsuario
	 * @param utilizaParametros 
	 * @return List<Usuario>
	 * @throws SIIACException 
	 */
	public List<Usuario> carregaListaUsuario(boolean fazConsulta) throws SIIACException {
		List<Usuario> lis = new ArrayList<Usuario>();
		lis = usuarioBO.getListUsuario(getUsuarioLogado(), String.valueOf(getUsuarioLogado().getMatricula()), 
				getFilterBase(), fazConsulta, listPerfisUsuario);
		
		if (lis == null || lis.isEmpty()) {
			if (situacaoAntiga != null && situacaoAntiga != showSituacao) {
				showSituacao = situacaoAntiga;
			}
		}
		
		situacaoAntiga = showSituacao;
		return lis;
	}
	
	/**
	 * carregaListaUsuario
	 * @param utilizaParametros 
	 * @return List<Usuario>
	 * @throws SIIACException 
	 */
	public List<Usuario> carregaListaUsuarioFiltroSimples() throws SIIACException {
		
		List<Usuario> lis = usuarioBO.getListUsuarioFiltroSimples(getUsuarioLogado(), getFilterBase());
		
		if (lis == null || lis.isEmpty()) {
			if (situacaoAntiga != null && situacaoAntiga != showSituacao) {
				showSituacao = situacaoAntiga;
			}
		}
		
		situacaoAntiga = showSituacao;
		return lis;
	}
	
	public boolean doValida() {
		if (matricula == null || matricula.trim().equals("")) {
			error(MSGS, MN002);
			warn("Campo MATRÍCULA não informado.");
			return false;
		}
		
		if (matricula == null || !matricula.trim().substring(0, 1).equalsIgnoreCase("c") 
				&& !matricula.trim().substring(0, 1).equalsIgnoreCase("p")){
			error(MSGS, MN002);
			warn("MATRÍCULA inválida.");
			return false;
		}
		
		if (perfil == null || perfil == 0 || perfil.equals(0)) {
			error(MSGS, MN002);
			warn("Campo PERFIL não informado.");
			return false;
		}
		
		//Verificando se a Unidade existe
		if (nuUnidade != null && nuUnidade > 0)
		{
			if (unidadeBO.getUnidade(nuUnidade) == null)
			{
				error(MSGS, MN002);
				warn("UNIDADE não é válida.");
				return false;
			}				
		}
		
		return true;
	}
	
	public void modoInicio(){
		showConsultarSimples = true;
		showConsultarAvancado = false;
		showCadastro = false;
		showButtonNovo = true;
		showGrid = true;
		nuUnidade = null;
		noUnidade = "";
		try {
			usuarioList = carregaListaUsuario(true);
		} catch (SIIACException e) {
			error(MSGS, e.getMessage());
			LogCEF.error(e.getMessage());
		}
		produtoList = new ArrayList<Produto>();
		produtoListCombo = produtoBO.getListProduto();
		session.setAttribute("NomeUsuario", "");
	}
	
	/**
	 * Método responsável por buscar o nome da unidade que o usuário
	 * digitou no momento da alteração e inclusão de um novo usuário
	 * @param evt
	 */
	public void doBuscaNomeUnidade(ActionEvent evt)
	{
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
					noUnidade = "";
					nuUnidade = null;
				}
			} else {
				Unidade unidade = unidadeBO.getUnidade(nuUnidade);
				noUnidade = unidade.getNomeAbreviado();
			}
			
		}

	}
	
	/**
	 * doSalvarClick
	 * @param evt
	 */
	public void doSalvarClick(ActionEvent evt) {
		try {
			boolean retorno = false;
			if(!doValida()){
				return;
			}
			if (isAlterar() == false) {
				if (perfilInativo == true) { //somente salvar se for ativo 
					error(MSGS, MN013);
					return;
				}
				
				if (!usuarioADBO.existeUsuarioAD(matricula)) {	
					error(MSGS, MN004);
					return;
				}
				
				retorno = usuarioBO.seExisteUsuario(matricula);
				if (retorno) {
					error(MSGS, MN015);
					return;
				}
			}
			
			retorno = usuarioBO.gravarUsuario(getUsuarioLogado(), matricula, perfil, alterar, nuUnidade);
			
			if (retorno) {
				
				for (Produto pu : produtoList) {
					retorno = produtoUsuarioBO.gravarProduto(matricula, pu.getCoProduto());
				}
				if (isAlterar()) {
					info(MSGS, MN008);
				} else {
					info(MSGS, MN007);
				}
				modoInicio();
				fillCampos();
			} else {
				error(MSGS, MN002);
			}
			
			
		} catch (SIIACException se) {
			warn(MSGS, se.getMessage());
			LogCEF.error(se.getMessage());
		} catch (Exception e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
	}
	
	/**
	 * doCancelarClick
	 * @param evt
	 */
	public void doCancelarClick(ActionEvent evt) {
		warn(MSGS, MN003);
		showCadastro = false;
		showConsultarSimples = true;
		showConsultarAvancado = false;
		showButtonNovo = true;
		showGrid = true;
		session.setAttribute("NomeUsuario", "");
		setAlterar(false);
		fillCampos();
		setFiltro();
		if(getFilterBase().isModoAvancado()){
			try {
				setUsuarioList(carregaListaUsuario(true));
				showConsultarSimples = false;
				showConsultarAvancado = true;
			} catch (SIIACException e) {
				error(MSGS, e.getMessage());
				LogCEF.error(e.getMessage());
			}
		}else{
			try {
				if(getFilterBase().getString("pesquisaString") != null || getFilterBase().getBoolean("pesquisaMostraInativos") != false){
					setUsuarioList(carregaListaUsuarioFiltroSimples());
				}else{
					setUsuarioList(carregaListaUsuario(false));
				}
			
				showConsultarSimples = true;
				showConsultarAvancado =  false;
			} catch (SIIACException e) {
				error(MSGS, e.getMessage());
				LogCEF.error(e.getMessage());
			}
			
		}
		
	}
	
	/**
	 * doIncluirProdutoClick
	 * @param evt
	 */
	public void doIncluirProdutoClick(ActionEvent evt) {
		if (cadProduto.equals("0")) { //todos os produtos
			warn("Selecione um produto a ser adicionado");
		} else {
			Produto p = produtoBO.findById(cadProduto);
			produtoList.add(p);
			//usei o produtoListCombo.remove(p); mas o objeto p não estava sendo encontrado na lista para excluir (não estava excluindo);
			for (int i = 0; i < produtoListCombo.size(); i++) {
				if (produtoListCombo.get(i).getCoProduto().equals(p.getCoProduto())) {
					produtoListCombo.remove(i);
				}
			}
		}
	}
	
	/**
	 * doNovoClick
	 * @return
	 */
	public void doCadastrar(ActionEvent evt) {
		showButtonNovo = false;
		showGrid = false;
		showConsultarSimples = false;
		showConsultarAvancado = false;
		showCadastro = true;
		todosProdutos = true;
		setAlterar(false);
		setMatricula("");
		perfil = 0;
		filtroPerfil = 0;
	}
	
	private void limparCampos(){
			pesquisaString = "";
			pesquisaMostraInativos = false;
			pesquisaMostraInativos = false;
			filtroNome = "";
			filtroUnidade = "";
			filtroMatricula = "";
			filtroPerfil = null;
			
			
	}
	
	public void doLimpar(ActionEvent evt){
		filtroPerfil = 0;
		limparCampos();
		getFilterBase().limparFiltros();
//		fillCampos();
	}
	
	/**
	 * doExcluirProdutoClick
	 * @return
	 */
	public String doExcluirProdutoClick() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Produto gridProduto = (Produto) facesContext.getExternalContext().getRequestMap().get("rowPro");
		produtoList.remove(gridProduto);
		produtoListCombo.add(gridProduto);
		return "toUsuario";
	}
	
	/**
	 * doAlteraClick
	 * @return
	 */
	public String doAlteraClick() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Usuario gridUsuario = (Usuario) facesContext.getExternalContext().getRequestMap().get("rowUsu");
		
		if (gridUsuario.getIcAtivo() == false) {
			perfilInativo = true;
		}
		
		((HttpServletRequest) facesContext.getExternalContext().getRequest()).setAttribute("matricula", gridUsuario.getCoUsuario());
		((HttpServletRequest) facesContext.getExternalContext().getRequest()).setAttribute("acao", "alterar");
		return "toProdutoUsuario";
	}
	
	/**
	 * doVisualizaClick
	 * @return
	 */
	public String doVisualizaClick() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Usuario gridUsuario = (Usuario) facesContext.getExternalContext().getRequestMap().get("rowUsu");
		
		if (gridUsuario.getIcAtivo() == false) {
			perfilInativo = true;
		}
		
		((HttpServletRequest) facesContext.getExternalContext().getRequest()).setAttribute("matricula", gridUsuario.getCoUsuario());
		((HttpServletRequest) facesContext.getExternalContext().getRequest()).setAttribute("acao", "visualizar");
		return "toProdutoUsuario";
	}
	
	/**
	 * doInativaClick
	 * @param evt
	 */
	public String doInativaClick() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Usuario gridUsuario = (Usuario) facesContext.getExternalContext().getRequestMap().get("rowUsu");
		try {
			usuarioBO.ativarDesativarUsuario(gridUsuario.getCoUsuario(), false);
			info(MSGS, MN011);
			modoInicio();
			fillCampos();
		} catch (SIIACException e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
		return "usuario";
	}
	
	/**
	 * doAtivaClick
	 * @param evt
	 */
	public void doAtivaClick(ActionEvent evt) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Usuario gridUsuario = (Usuario) facesContext.getExternalContext().getRequestMap().get("rowUsu");
		try {
			usuarioBO.ativarDesativarUsuario(gridUsuario.getCoUsuario(), true);
			info(MSGS, MN012);
			modoInicio();
			fillCampos();
		} catch (SIIACException e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
	}
	
	/**
	 * doConsultarClick
	 * 
	 * @param evt
	 * @throws SIIACException
	 */
	public void doConsultarClick(ActionEvent evt) {
		try {
			if(getShowConsultarAvancado()){
				
				getFilterBase().limparFiltros();
				getFilterBase().addToFilter("pesquisaMostraInativos", pesquisaMostraInativos);
				getFilterBase().addToFilter("filtroNome", filtroNome);
				getFilterBase().addToFilter("filtroUnidade", filtroUnidade);
				getFilterBase().addToFilter("filtroMatricula", filtroMatricula);
				getFilterBase().addToFilter("filtroPerfil", String.valueOf(filtroPerfil));
				getFilterBase().setModoAvancado();
				putFilterBase(this.getClass());
				
				if (Utilities.notEmpty(filtroUnidade)) {
					if (!usuarioBO.existeUnidade(filtroUnidade)) {
						fillCampos();
						filtroPerfil = 0;
						warn(MSGS, XX017);
						return;
					} else if (!usuarioADBO.existeUsuarioUnidade(filtroUnidade)) {
						fillCampos();
						filtroPerfil = 0;
						warn(MSGS, XX018);
						return;
					}
				}
				
				List<Usuario> lis = carregaListaUsuario(true);
				if (lis == null || lis.isEmpty()) {
					warn(MSGS, MN016);
				}
				setUsuarioList(lis);
			}else{
				getFilterBase().limparFiltros();
				getFilterBase().addToFilter("pesquisaString", pesquisaString);
				getFilterBase().addToFilter("pesquisaMostraInativos", pesquisaMostraInativos);
				getFilterBase().setModoSimples();
				putFilterBase(this.getClass());
				
				List<Usuario> lis = carregaListaUsuarioFiltroSimples();
				if (lis == null || lis.isEmpty()) {
					warn(MSGS, MN016);
				}
//				limparCampos();
				setUsuarioList(lis);
				
			}
		} catch (SIIACException e) {
			error(MSGS, e.getMessage());
			LogCEF.error(e.getMessage());
		}
	}

	/**
	 * Limpa a lista de produtos e restaura o combobox.
	 * @param evt
	 */
	public void doCheckTodos(ActionEvent evt){
				
		if (todos)
		{
			produtoList.clear();
			produtoListCombo.clear();
			produtoListCombo = produtoBO.getListProduto();
			setShowListProdutos(false);
		} else {
			produtoList.clear();
			produtoListCombo.clear();
			produtoListCombo = produtoBO.getListProduto();
			setShowListProdutos(true);
		}
	}
	
	
	public void doConsultarSimples(ActionEvent evt) {
		showConsultarSimples = true;
		showConsultarAvancado = false;
	}
	
	public void doConsultarAvancado(ActionEvent evt) {
		showConsultarSimples = false;
		showConsultarAvancado = true;
	}
	
	/**
	 * getShowButtonNovo
	 * @return
	 */
	public boolean getShowButtonNovo() {
		return showButtonNovo;
	}
	
	/**
	 * getShowGrid
	 * @return
	 */
	public boolean getShowGrid() {
		return showGrid;
	}
	
	/**
	 * getShowCadastro
	 * @return
	 */
	public boolean getShowCadastro() {
		return showCadastro;
	}
	
	public void setTodosProdutos(Boolean todosProdutos) {
		this.todosProdutos = todosProdutos;
	}
	
	/**
	 * getTodosProdutos
	 * @return
	 */
	public Boolean getTodosProdutos() {
		return todosProdutos;
	}
	
	public boolean isShowListProdutos() {
		return showListProdutos;
	}

	public void setShowListProdutos(boolean showListProdutos) {
		this.showListProdutos = showListProdutos;
	}

	/**
	 * getShowConsultarSimples
	 * @return
	 */
	public boolean getShowConsultar() {
		return showConsultarSimples || showConsultarAvancado;
	}

	/**
	 * getShowConsultarSimples
	 * @return
	 */
	public boolean getShowConsultarSimples() {
		return showConsultarSimples;
	}
	
	/**
	 * getShowConsultarSimples
	 * @return
	 */
	public boolean getShowConsultarAvancado() {
		return showConsultarAvancado;
	}

	/**
	 * Verifica se há produtos na lista de associados para que o botão de "Todos" seja renderizado.
	 * @return
	 */
	public boolean isShowButtonTodos(){
		return produtoList != null && !produtoList.isEmpty();
	}
	
	/**
	 * setFiltroMatricula
	 * @param filtroMatricula
	 */
	public void setFiltroMatricula(String filtroMatricula) {
		this.filtroMatricula = filtroMatricula;
	}
	
	/**
	 * getFiltroMatricula
	 * @return
	 */
	public String getFiltroMatricula() {
		return filtroMatricula;
	}
	
	/**
	 * setFiltroNome
	 * @param filtroNome
	 */
	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}
	
	/**
	 * getFiltroNome
	 * @return
	 */
	public String getFiltroNome() {
		return filtroNome;
	}
	
	/**
	 * setFiltroUnidade
	 * @param filtroUnidade
	 */
	public void setFiltroUnidade(String filtroUnidade) {
		this.filtroUnidade = filtroUnidade;
	}
	
	/**
	 * getFiltroUnidade
	 * @return
	 */
	public String getFiltroUnidade() {
		return filtroUnidade;
	}
	
	/**
	 * getProdutoUsuarioList
	 * @return
	 */
	public List<Produto> getProdutoList() {
		return this.produtoList;
	}
	
	public void setProdutoList(List<Produto> produtoList) {
		this.produtoList = produtoList;
	}
	
	/**
	 * getUsuarioList
	 * @return
	 */
	public List<Usuario> getUsuarioList() {
		return this.usuarioList;
	}
	
	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}
	
	/**
	 * getRotularColuna
	 * @return
	 */
	public String getRotularColuna() {
		if (showSituacao == 1) {
			rotularColuna = getMessage(MENSAGEM, COLUNA_INATIVAR);
		} else {
			rotularColuna = getMessage(MENSAGEM, COLUNA_ATIVAR);
		}
		return rotularColuna;
	}
	
	/**
	 * setRotularColuna
	 * @param rotularColuna
	 */
	public void setRotularColuna(String rotularColuna) {
		this.rotularColuna = rotularColuna;
	}
	
	/**
	 * getShowSituacao
	 * @return
	 */
	public Integer getShowSituacao() {
		return showSituacao;
	}
	
	/**
	 * setShowSituacao
	 * @param showSituacao
	 */
	public void setShowSituacao(Integer showSituacao) {
		this.showSituacao = showSituacao;
	}
	
	/**
	 * getShowButtonDesativar
	 * @return
	 */
	public boolean getShowButtonDesativar() {
		return !getPesquisaMostraInativos() && !isCentralAtendimentoSuporte();
	}
	
	/**
	 * getShowButtonAtivar
	 * @return
	 */
	public boolean getShowButtonAtivar() {
		return getPesquisaMostraInativos() && !isCentralAtendimentoSuporte();
	}
	
	/**
	 * getPerfilInativo
	 * @return
	 */
	public boolean getPerfilInativo() {
		return perfilInativo;
	}
	
	/**
	 * valida a matricula do Usuario
	 */
	public void matriculaValidator(ActionEvent evt) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(Utilities.empty(matricula)){
			session.setAttribute("NomeUsuario", "");
			return;
		}
		
		String matr = matricula.substring(0, 1).toLowerCase() + ConvertUtils.padZerosLeft(matricula.replace("_", "").substring(1), TAMANHO_MATRICULA);

		try{
			if (usuarioADBO.existeUsuarioAD(matr)) {
				Unidade unidade = unidadeBO.getUnidade(usuarioADBO.getNuUnidade(matr));
								
				nomeUsuario = usuarioADBO.getNomeUsuario(matr) + " - Unidade: " + unidade.getId().getNuUnidade() + " / " + unidade.getNomeAbreviado();
				style = "font-weight:bold;";
				matricula = matr;
			} else {
				nomeUsuario = getMessage(MSGS, MN004);
				style = STYLE_NOME_USUARIO;
			}
		} catch (SIIACException e) {
			nomeUsuario = getMessage(MSGS, e.getMessage());
			style = STYLE_NOME_USUARIO;
			LogCEF.error(e.getMessage());
		}
		
		session.setAttribute("NomeUsuario", nomeUsuario);
		session.setAttribute("Style", style);
	}
	
	
	/**
	 * getMensagemConfirmacao
	 * @return
	 */
	public String getMensagemConfirmacao() {
		if (isAlterar()) {
			return getMessage(MSGS, MA003);
		} else {
			return getMessage(MSGS, MA002);
		}
	}
	
	public String getNomeList(){
		try{
			return usuarioADBO.getNomeUsuario(
					((Usuario)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("rowUsu")).getCoUsuario());
		} catch (SIIACException e){
			LogCEF.error(e.getMessage());
			return "";
		}
	}
	
	public String getUnidadeList(){
		try {
			Usuario usuario = (Usuario) getRequestMap().get("rowUsu");
			Unidade u = null;
			if(usuario.getIcAtivo() && usuario.getNuUnidadeVerificacao() != null){
				u = unidadeBO.getUnidade(usuario.getNuUnidadeVerificacao());
			}else{
				u = unidadeBO.getUnidade(usuarioADBO.getNuUnidade(usuario.getCoUsuario()));	
			}
			return u.getId().getNuUnidade().toString() + SEPARADOR_NOME_UNIDADE + u.getNomeAbreviado();
		} catch (SIIACException e) {
			LogCEF.error(e.getMessage());
			return "";
		}
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

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}

	public void setListPerfil(List<SelectItem> listPerfil) {
		this.listPerfil = listPerfil;
	}
	
	public boolean getShowNovo(){
		return isGestorSistema() || isRegionalConformidade();
	}
	
	public boolean getShowAcao(){
		return isGestorSistema() || isRegionalConformidade();
	}
	
	public boolean getShowLimpar(){
		return showConsultarAvancado;
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

	public List<Short> getListaUnidadesAbrangencia() {
		return listaUnidadesAbrangencia;
	}

	public void setListaUnidadesAbrangencia(List<Short> listaUnidadesAbrangencia) {
		this.listaUnidadesAbrangencia = listaUnidadesAbrangencia;
	}
	
	private void preencheListPerfis() {
		if (!matricula.startsWith("c") && !matricula.startsWith("C")){
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE, ListaPerfil.PERFIL_NOME_CENTRAL_DE_ATENDIMENTO_E_SUPORTE));
			return;
		}
		if (isGestorSistema()) {
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_GESTOR_SISTEMA, ListaPerfil.PERFIL_NOME_GESTOR_SISTEMA));
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_AUDITOR, ListaPerfil.PERFIL_NOME_AUDITOR));
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE, ListaPerfil.PERFIL_NOME_CENTRAL_DE_ATENDIMENTO_E_SUPORTE));
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_GESTOR_PRODUTO, ListaPerfil.PERFIL_NOME_GESTOR_PRODUTO));
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_REGIONAL_CONFORMIDADE, ListaPerfil.PERFIL_NOME_REGIONAL_CONFORMIDADE));
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_VERIFICADOR_NACIONAL, ListaPerfil.PERFIL_NOME_VERIFICADOR_NACIONAL));
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_VERIFICADOR_REGIONAL, ListaPerfil.PERFIL_NOME_VERIFICADOR_REGIONAL));
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_UNIDADE_ATENDIMENTO, ListaPerfil.PERFIL_NOME_UNIDADE_ATENDIMENTO));
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_VERIFICADOR_CONFORMIDADE, ListaPerfil.PERFIL_NOME_VERIFICADOR_CONFORMIDADE));
			
			listPerfisUsuario.add(ListaPerfil.PERFIL_GESTOR_SISTEMA.intValue());
			listPerfisUsuario.add(ListaPerfil.PERFIL_AUDITOR.intValue());
			listPerfisUsuario.add(ListaPerfil.PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE.intValue());
			listPerfisUsuario.add(ListaPerfil.PERFIL_GESTOR_PRODUTO.intValue());
			listPerfisUsuario.add(ListaPerfil.PERFIL_REGIONAL_CONFORMIDADE.intValue());
			listPerfisUsuario.add(ListaPerfil.PERFIL_VERIFICADOR_NACIONAL.intValue());
			listPerfisUsuario.add(ListaPerfil.PERFIL_VERIFICADOR_REGIONAL.intValue());
			listPerfisUsuario.add(ListaPerfil.PERFIL_UNIDADE_ATENDIMENTO.intValue());
			listPerfisUsuario.add(ListaPerfil.PERFIL_VERIFICADOR_CONFORMIDADE.intValue());
		}
		if (isRegionalConformidade()) {
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_VERIFICADOR_REGIONAL, ListaPerfil.PERFIL_NOME_VERIFICADOR_REGIONAL));
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_VERIFICADOR_CONFORMIDADE, ListaPerfil.PERFIL_NOME_VERIFICADOR_CONFORMIDADE));
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_UNIDADE_ATENDIMENTO, ListaPerfil.PERFIL_NOME_UNIDADE_ATENDIMENTO));
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_AUDITOR, ListaPerfil.PERFIL_NOME_AUDITOR));
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE, ListaPerfil.PERFIL_NOME_CENTRAL_DE_ATENDIMENTO_E_SUPORTE));
			
			listPerfisUsuario.add(ListaPerfil.PERFIL_VERIFICADOR_REGIONAL.intValue());
			listPerfisUsuario.add(ListaPerfil.PERFIL_VERIFICADOR_CONFORMIDADE.intValue());
			listPerfisUsuario.add(ListaPerfil.PERFIL_UNIDADE_ATENDIMENTO.intValue());
			listPerfisUsuario.add(ListaPerfil.PERFIL_AUDITOR.intValue());
			listPerfisUsuario.add(ListaPerfil.PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE.intValue());
		}
		if (isUnidadeAtendimento()) {
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_UNIDADE_ATENDIMENTO, ListaPerfil.PERFIL_NOME_UNIDADE_ATENDIMENTO));

			listPerfisUsuario.add(ListaPerfil.PERFIL_UNIDADE_ATENDIMENTO.intValue());
		}
		if (isCentralAtendimentoSuporte()) {
			listPerfil.add(new SelectItem(ListaPerfil.PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE, ListaPerfil.PERFIL_NOME_CENTRAL_DE_ATENDIMENTO_E_SUPORTE));

			listPerfisUsuario.add(ListaPerfil.PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE.intValue());
		}
		Collections.sort(listPerfil, new Comparator<SelectItem>() {
			public int compare(SelectItem item1, SelectItem item2) {
				return item1.getLabel().toString().compareTo(item2.getLabel().toString());
			}
		});
	}
}