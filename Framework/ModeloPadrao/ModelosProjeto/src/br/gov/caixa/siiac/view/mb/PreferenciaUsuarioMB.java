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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.ICategoriaProdutoBO;
import br.gov.caixa.siiac.bo.IGerenteUnidadeBO;
import br.gov.caixa.siiac.bo.IPreferenciaUsuarioGerenteBO;
import br.gov.caixa.siiac.bo.IPreferenciaUsuarioServicoBO;
import br.gov.caixa.siiac.bo.IPreferenciasUsuarioBO;
import br.gov.caixa.siiac.bo.IPreferenciasUsuarioCategoriaProdutoBO;
import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.bo.IProdutoUsuarioBO;
import br.gov.caixa.siiac.bo.IServicoVerificacaoBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.bo.IUsuarioADBO;
import br.gov.caixa.siiac.bo.IUsuarioBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.facade.IPreferenciaUsuarioFacade;
import br.gov.caixa.siiac.model.domain.CategoriaProduto;
import br.gov.caixa.siiac.model.domain.GerenteUnidade;
import br.gov.caixa.siiac.model.domain.Perfil;
import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioGerente;
import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioServico;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuario;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuarioCategoriaProduto;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.model.domain.Usuario;
import br.gov.caixa.siiac.util.FormatUtil;
import br.gov.caixa.siiac.util.LogCEF;

@Service()
@Scope("request")
public class PreferenciaUsuarioMB extends AbstractMB {
	
	/**
	 * 
	 */
	private static final String SEPARADOR_LABEL_COMBO = " - ";

	public static final int TAMANHO_MATRICULA = 6;
	
	private transient IPreferenciaUsuarioFacade preferenciaUsuarioFacade;
	private transient IProdutoUsuarioBO produtoUsuarioBO;
	private transient IProdutoBO produtoBO;
	private transient IPreferenciasUsuarioBO preferenciasUsuarioBO;
	private transient IPreferenciasUsuarioCategoriaProdutoBO preferenciasUsuarioCategoriaProdutoBO;
	private transient IServicoVerificacaoBO servicoVerificacaoBO;
	private transient IPreferenciaUsuarioServicoBO preferenciaUsuarioServicoBO;
	private transient IUnidadeBO unidadeBO;
	private transient IUsuarioADBO usuarioADBO;
	private transient IUsuarioBO usuarioBO;
	private transient ICategoriaProdutoBO categoriaBO;
	private transient IGerenteUnidadeBO gerenteBO;
	private transient IPreferenciaUsuarioGerenteBO preferenciaUsuarioGerenteBO;
	
	private boolean todosProdutos = true;
	private boolean showListaProdutos = false;
	
	private boolean todosServicos = true;
	private boolean showListaServicos = false;
	
	private boolean todasCategorias = true;
	private boolean showListaCategorias = false;
	
	private String nomeGerente;
	private String codGerente;
	
	
	@Autowired
	public void setPreferenciaUsuarioFacade(IPreferenciaUsuarioFacade preferenciaUsuarioFacade) {
		this.preferenciaUsuarioFacade = preferenciaUsuarioFacade;
	}
	
	@Autowired
	public void setProdutoUsuarioBO(IProdutoUsuarioBO produtoUsuarioBO) {
		this.produtoUsuarioBO = produtoUsuarioBO;
	}
	
	@Autowired
	public void setProdutoBO(IProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}

	@Autowired
	public void setCategoriaBO(ICategoriaProdutoBO categoriaBO) {
		this.categoriaBO = categoriaBO;
	}

	@Autowired
	public void setPreferenciasUsuarioBO(IPreferenciasUsuarioBO preferenciasUsuarioBO) {
		this.preferenciasUsuarioBO = preferenciasUsuarioBO;
	}
	
	@Autowired
	public void setPreferenciasUsuarioCategoriaProdutoBO(IPreferenciasUsuarioCategoriaProdutoBO preferenciasUsuarioCategoriaProdutoBO) {
		this.preferenciasUsuarioCategoriaProdutoBO = preferenciasUsuarioCategoriaProdutoBO;
	}
	
	@Autowired
	public void setServicoVerificacaoBO(IServicoVerificacaoBO servicoVerificacaoBO) {
		this.servicoVerificacaoBO = servicoVerificacaoBO;
	}
	
	@Autowired
	public void setPreferenciaUsuarioServicoBO(IPreferenciaUsuarioServicoBO preferenciaUsuarioServicoBO) {
		this.preferenciaUsuarioServicoBO = preferenciaUsuarioServicoBO;
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
	public void setUsuarioBO(IUsuarioBO usuarioBO) {
		this.usuarioBO = usuarioBO;
		
		// TODO: Refatorar para usar @PostConstruct
		fillCampos();
	}
	
	@Autowired
	public void setGerenteBO(IGerenteUnidadeBO gerenteBO) {
		this.gerenteBO = gerenteBO;
	}
	
	@Autowired
	public void setPreferenciaUsuarioGerenteBO(
			IPreferenciaUsuarioGerenteBO preferenciaUsuarioGerenteBO) {
		this.preferenciaUsuarioGerenteBO = preferenciaUsuarioGerenteBO;
	}




	private final static Integer MODO_CADASTRO = 1;
	private final static Integer MODO_CONSULTA = 2;
	private transient Integer stateMode = null;
	
	private String matricula;
	private transient Usuario usuario;
	
	private boolean disableGerentes = false;
	
	//Categorias
	private String itemCategoria;
	private List<SelectItem> selectItemCategorias = new ArrayList<SelectItem>();
	private List<PreferenciasUsuarioCategoriaProduto> listaCategorias = new ArrayList<PreferenciasUsuarioCategoriaProduto>();
	
	//Produtos
	private String itemProduto;
	private List<SelectItem> selectItemProdutos = new ArrayList<SelectItem>();
	private List<PreferenciasUsuario> listaProdutos = new ArrayList<PreferenciasUsuario>();
	
	//Servicos
	private Short itemServico;
	private List<SelectItem> selectItemServicos = new ArrayList<SelectItem>();
	private List<PreferenciaUsuarioServico> listaServicos = new ArrayList<PreferenciaUsuarioServico>();
	
	//Gestores
	private String itemGerente;
	private List<SelectItem> selectItemGerentes = new ArrayList<SelectItem>(0);
	private List<PreferenciaUsuarioGerente> listaGerentes = new ArrayList<PreferenciaUsuarioGerente>(0);
	
	public PreferenciaUsuarioMB() {
		if(stateMode == null){
			stateMode = MODO_CADASTRO;
		}
	}
	
	// Metodo a ser anotado com @PostConstruct
	/**
	 *	Metodo que preenche os campos da tela. (Painel de usuario, Lista de produtos, servicos, etc.) 
	 */
	public void fillCampos(){
		if(preencheDadosUsuario()){
			
			fillSelectItemCategorias();	
			fillListaCategorias();
					
			fillSelectItemProdutos();
			fillListaProdutos();
			fillSelectItemServicos();
			fillListaServicos();
			
			fillSelectItemGerentes();
			fillListaGerentes();
			
			todosProdutos = getPossuiTodosProdutos();
			showListaProdutos = !todosProdutos;
			
			todasCategorias = getPossuiTodasCategorias();
			showListaCategorias= !todasCategorias;
			
			todosServicos = getPossuiTodosServicos();
			showListaServicos = !todosServicos;
		}
	}
	
	/**
	 * Metodo que recupera os dados do usuario
	 * Verifica se esta na opcao de CADASTRO e utiliza o usuario logado.
	 * Se estiver na opcao de CONSULTA, utiliza a matricula digitada.
	 * @return
	 */
	private boolean preencheDadosUsuario(){
		try{
			if(isCadastro()){
				setMatricula(ConvertUtils.padZerosLeft(String.valueOf(getUsuarioLogado().getMatricula()), TAMANHO_MATRICULA));
				usuario = usuarioBO.getUsuarioByMatricula(getMatricula());
			}else{
				setMatricula(getMatricula().substring(0,1) + ConvertUtils.padZerosLeft(getMatricula().substring(1), TAMANHO_MATRICULA));
				Usuario u = usuarioBO.getUsuarioByMatricula(getMatricula().toLowerCase());
				if(u == null){
					warn("usuario não encontrado.");
					return false;
				}else{
					usuario = u;
				}
			}
		} catch (SIIACException e) {
			LogCEF.error(e.getMessage());
			return false;
		} catch (Exception e) {
			LogCEF.error(e.getMessage());
			return false;
		}
		return true;
		
	}
	
	/**
	 * Metodo que preenche a lista de Produtos para o comboBox
	 */
	public void fillSelectItemProdutos() {
		selectItemProdutos = new ArrayList<SelectItem>();
		Short nuPerfil = (short) usuario.getPerfil().getNuPerfil();
		List<Produto> lis = produtoBO.getAllNaoPreferenciais(getMatricula(), nuPerfil);

		for(Produto p : lis) {
			selectItemProdutos.add(new SelectItem(p.getCoProduto(), 
					p.getCoProduto() + SEPARADOR_LABEL_COMBO + p.getNoProduto()));
		}
	}
	
	/**
	 * Metodo que preenche a lista de Categorias para o comboBox
	 */
	public void fillSelectItemCategorias() {
		selectItemCategorias = new ArrayList<SelectItem>();

		Short nuPerfil = (short) usuario.getPerfil().getNuPerfil();
		List<CategoriaProduto> lis = categoriaBO.getAllNaoPreferenciais(getMatricula(), nuPerfil);

		for(CategoriaProduto c : lis) {
			selectItemCategorias.add(new SelectItem(c.getNuCategoriaProduto(), 
					c.getSgCategoria() + SEPARADOR_LABEL_COMBO + c.getNoCategoriaProduto()));
		}
	}

	/**
	 * Metodo que preenche a lista de servicos para o comboBox
	 */
	public void fillSelectItemServicos() {
		selectItemServicos = new ArrayList<SelectItem>();
		List<ServicoVerificacao> lis = servicoVerificacaoBO.getAllNaoPreferenciais(getMatricula());

		for(ServicoVerificacao sv : lis) {
			selectItemServicos.add(new SelectItem(sv.getNuServicoVerificacao(), 
					sv.getNoServicoVerificacao()));
		}
	}
	
	/**
	 * Metodo que preenche a lista de Produtos para a tabela
	 */
	public void fillListaProdutos() {
		listaProdutos = new ArrayList<PreferenciasUsuario>();
		listaProdutos = preferenciasUsuarioBO.getAll(getMatricula());
	}
	
	/**
	 * Metodo que preenche a lista de Categorias para a tabela
	 */
	public void fillListaCategorias() {
				
		listaCategorias = new ArrayList<PreferenciasUsuarioCategoriaProduto>();
		listaCategorias = preferenciasUsuarioCategoriaProdutoBO.getAll(getMatricula());
		
	}

	/**
	 * Metodo que preenche a lista de servicos para a tabela
	 */
	public void fillListaServicos() {
		listaServicos = new ArrayList<PreferenciaUsuarioServico>();
		listaServicos = preferenciaUsuarioServicoBO.getAll(getMatricula());
	}
	
	public void fillListaGerentes(){
		listaGerentes = new ArrayList<PreferenciaUsuarioGerente>();
		listaGerentes = preferenciaUsuarioGerenteBO.getAll(getMatricula());
	}
	
	public void fillSelectItemGerentes(){
		selectItemGerentes = new ArrayList<SelectItem>();
		List<GerenteUnidade> list = preferenciasUsuarioBO.getListGerentesUnidade(SegurancaUsuario.getInstance().getUsuario().getUnidade());
		
		for (GerenteUnidade gerenteUnidade : list) {
			selectItemGerentes.add(new SelectItem(String.valueOf(gerenteUnidade.getNuMatricula()), FormatUtil.formatMatricula(gerenteUnidade.getNuMatricula()) + 
					SEPARADOR_LABEL_COMBO + gerenteUnidade.getNoPessoa()));
		}
	}
	
	public String getNomeGerente(){
		PreferenciaUsuarioGerente p = (PreferenciaUsuarioGerente) getRequestMap().get("itemGerente");
		 GerenteUnidade g = gerenteBO.findByNuMatricula(p.getId().getCoGerentePreferencial().toString());
		 
		 return g.getNoPessoa();
	}
	
	public String getMatriculaGerente(){
		PreferenciaUsuarioGerente p = (PreferenciaUsuarioGerente) getRequestMap().get("itemGerente");
		 GerenteUnidade gerenteUnidade = gerenteBO.findByNuMatricula(p.getId().getCoGerentePreferencial().toString());
		 
		 return FormatUtil.formatMatricula(gerenteUnidade.getNuMatricula());
	}

	/** ---------------------------------------------------------------------------------------
	 * 
	 */
	/**
	 * Clique do botao "Consultar Outro usuario". Altera a renderizacao de tela para Modo Consulta.
	 */
	public void doConsultarOutroUsuario(ActionEvent evt){
		stateMode = MODO_CONSULTA;
	}
	
	/**
	 * Clique do botao "Consultar".
	 * @param evt
	 */
	public void doConsultar(ActionEvent evt){
		fillCampos();
	}
	
	/**
	 * Clique do botao "Voltar".
	 * @return
	 */
	public String doVoltar(){
		stateMode = MODO_CADASTRO;
		fillCampos();
		return "";
	}

	/**
	 * Clique do botao "Incluir" do painel de Produtos. 
	 * 	Verifica se ha um produto selecionado no combo e o adiciona.	 
	 *  @param evt
	 */
	public void doIncluirProduto(ActionEvent evt){
		if(getItemProduto() != null && !getItemProduto().equals("0") && !getItemProduto().equals("")){
			addProduto(getItemProduto());
		}
		else{
			warn("Selecione um produto.");
		}
	}
	
	/**
//	 * Clique do botao "Incluir" do painel de Categorias. 
	 * 	Verifica se ha um produto selecionado no combo e o adiciona.	 
	 *  @param evt
	 */
	public void doIncluirCategoria(ActionEvent evt){
		if(getItemCategoria() != null && !getItemCategoria().equals("0") && !getItemCategoria().equals("")){
			addCategoria(getItemCategoria());
		}
		else{
			warn("Selecione uma categoria.");
		}
	}
	
	/**
	 * Recupera os valores referentes ao produto do codProduto, adiciona na lista de produtos e remove do comboBox.
	 * @param codProduto
	 * @throws SIIACException 
	 */
	private void addProduto(String codProduto) {
		Produto prod = produtoBO.findById(codProduto);
		
		PreferenciasUsuario p = new PreferenciasUsuario();
		p.setProduto(prod);
		p.getId().setCoProduto(prod.getCoProduto());
		p.getId().setCoUsuario(getMatricula());
		listaProdutos.add(p);
		
		for(SelectItem si : selectItemProdutos) {
			if(String.valueOf(si.getValue()).equals(codProduto)){
				selectItemProdutos.remove(si);
				break;
			}
		}
	}
	
	/**
	 * Recupera os valores referentes ao produto do codCategoria, adiciona na lista de categorias e remove do comboBox.
	 * @param codCategoria
	 * @throws SIIACException 
	 */
	private void addCategoria(String codCategoria) {
				
		PreferenciasUsuarioCategoriaProduto p = new PreferenciasUsuarioCategoriaProduto();
		CategoriaProduto c = new CategoriaProduto();
		
		c = categoriaBO.findById(Integer.parseInt(codCategoria));
		p.setCategoriaProduto(c);
		p.getId().setNuCategoriaProduto(c.getNuCategoriaProduto());
		p.getId().setCoUsuario(getMatricula());
		listaCategorias.add(p);
		
		for(SelectItem si : selectItemCategorias) {
			if(String.valueOf(si.getValue()).equals(codCategoria)){
				selectItemCategorias.remove(si);
				break;
			}
		}
	}
	
	/**
	 * Remove o produto selecionado da lista de produtos e o adiciona novamente ao comboBox.
	 * @param evt
	 */
	public void doRemoverProduto(ActionEvent evt){
		PreferenciasUsuario u = (PreferenciasUsuario) FacesContext.getCurrentInstance().getExternalContext()
										.getRequestMap().get("item");
		listaProdutos.remove(u);
		if(u.getProduto().isIcAtivo()){
			selectItemProdutos.add(new SelectItem(u.getProduto().getCoProduto(), 
					u.getProduto().getCoProduto() + SEPARADOR_LABEL_COMBO + u.getProduto().getNoProduto()));
		}
	}
	
	/**
	 * Remove o categoria selecionado da lista de categorias e o adiciona novamente ao comboBox.
	 * @param evt
	 */
	public void doRemoverCategoria(ActionEvent evt){
		PreferenciasUsuarioCategoriaProduto p = (PreferenciasUsuarioCategoriaProduto) FacesContext.getCurrentInstance().getExternalContext()
										.getRequestMap().get("item");
		listaCategorias.remove(p);
		if(p.getCategoriaProduto().isIcAtivo()){
			selectItemCategorias.add(new SelectItem(p.getCategoriaProduto().getNuCategoriaProduto(), 
					p.getCategoriaProduto().getSgCategoria() + SEPARADOR_LABEL_COMBO + p.getCategoriaProduto().getNoCategoriaProduto()));
		}
	}
	
	public void doIncluirGerente(ActionEvent evt){
		if(getItemGerente() != null && !getItemGerente().equals(Short.valueOf("0")) && !getItemGerente().equals("")){
			addGerente(getItemGerente());
		} else{
			warn("Selecione um gerente.");
		}
	}
	public void addGerente(String codGerente){
		GerenteUnidade gerenteUnidade = gerenteBO.findByNuMatricula(codGerente);
		
		PreferenciaUsuarioGerente p = new PreferenciaUsuarioGerente();
		p.getId().setCoGerentePreferencial(gerenteUnidade.getNuMatricula());
		p.getId().setCoUsuario(getMatricula());
		
		try {
			p.setUsuario(usuarioBO.getUsuarioByMatricula(getUsuarioLogado().getMatricula()));
		} catch (SIIACException e) {
			// TODO Auto-generated catch block
			LogCEF.error(e.getMessage());
		}
		
		listaGerentes.add(p);
	}

	/**
	 * Clique do botao "Incluir" do painel de servicos. 
	 * 	Verifica se ha um servico selecionado no combo e o adiciona.	 
	 *  @param evt
	 */
	public void doIncluirServico(ActionEvent evt){
		if(getItemServico() != null && !getItemServico().equals(Short.valueOf("0")) && !getItemServico().equals("")){
			addServico(getItemServico());
		}
		else{
			warn("Selecione um serviço.");
		}
	}

	/**
	 * Recupera os valores referentes ao servico do codServico, adiciona na lista de servicos e remove do comboBox.
	 * @param codServico
	 */
	private void addServico(Short codServico) {
		ServicoVerificacao servicoVerificacao = servicoVerificacaoBO.get((int)codServico);

		PreferenciaUsuarioServico p = new PreferenciaUsuarioServico();
		p.setServicoVerificacao(servicoVerificacao);
		p.getId().setNuServicoVerificacao(servicoVerificacao.getNuServicoVerificacao());
		p.getId().setCoUsuario(getMatricula());
		try {
			p.setUsuario(usuarioBO.getUsuarioByMatricula(getUsuarioLogado().getMatricula()));
		} catch (SIIACException e) {
			// TODO Auto-generated catch block
			LogCEF.error(e.getMessage());
		}
		
		listaServicos.add(p);

		for(SelectItem si : selectItemServicos) {
			if(Short.valueOf(si.getValue().toString()).equals(codServico)){
				selectItemServicos.remove(si);
				break;
			}
		}
	}
	
	/**
	 * Remove o servico selecionado da lista de servicos e o adiciona novamente ao comboBox.
	 * @param evt
	 */
	public void doRemoverServico(ActionEvent evt){
		PreferenciaUsuarioServico u = (PreferenciaUsuarioServico) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestMap().get("item");
		
		listaServicos.remove(u);
		if(u.getServicoVerificacao().isIcAtivo()){
			selectItemServicos.add(new SelectItem(u.getServicoVerificacao().getNuServicoVerificacao(), 
					u.getServicoVerificacao().getNoServicoVerificacao()));
		}
	}
	
	public void doRemoverGerente(ActionEvent evt){
		PreferenciaUsuarioGerente u = (PreferenciaUsuarioGerente) FacesContext.getCurrentInstance().getExternalContext()
		.getRequestMap().get("itemGerente");
		listaGerentes.remove(u);
		
	}
	
	/**
	 * Salva as altera��es realizadas. 
	 * @return
	 */
	public String doSalvar(){
		preferenciaUsuarioFacade.salvar(getMatricula(), listaProdutos, listaServicos, listaCategorias, listaGerentes);
				
		fillSelectItemProdutos();
		fillListaProdutos();
		fillSelectItemServicos();
		fillListaServicos();
		fillSelectItemCategorias();
		fillListaCategorias();
		fillSelectItemGerentes();
		fillListaGerentes();
		info(MSGS, MN008);
		return "";
	}

	/**
	 * Limpa a lista de produtos preferenciais e restaura o combobox.
	 * @param evt
	 */
	public void doCheckTodosProdutos(ActionEvent evt){
		
		if (todosProdutos)
		{
			for(PreferenciasUsuario p : listaProdutos) {
				if(p.getProduto().isIcAtivo()){
					selectItemProdutos.add(new SelectItem(p.getProduto().getCoProduto(),
							p.getProduto().getCoProduto() + SEPARADOR_LABEL_COMBO + p.getProduto().getNoProduto()));
				}
			}
			
			listaProdutos.clear();
			showListaProdutos = false;
		} else {
			listaProdutos.clear();
			showListaProdutos = true;
		}
		
	}
	
	/**
	 * Limpa a lista de categorias preferenciais e restaura o combobox.
	 * @param evt
	 */
	public void doCheckTodasCategorias(ActionEvent evt){
		
		Short nuPerfil = (short) usuario.getPerfil().getNuPerfil();
		if (todasCategorias)
		{
			List<CategoriaProduto> lis = categoriaBO.getAllNaoPreferenciais(getMatricula(), nuPerfil);
			selectItemCategorias.clear();

			for(CategoriaProduto c : lis) {
				selectItemCategorias.add(new SelectItem(c.getNuCategoriaProduto(), 
						c.getNuCategoriaProduto() + SEPARADOR_LABEL_COMBO + c.getNoCategoriaProduto()));
			}
			
			listaCategorias.clear();
			showListaCategorias= false;
		} else {
			listaCategorias.clear();
			showListaCategorias = true;
		}
		
	}

	/**
	 * Limpa a lista de serviços preferenciais e restaura o combobox.
	 * @param evt
	 */
	public void doCheckTodosServicos(ActionEvent evt){
		
		if (todosServicos)
		{
			for(PreferenciaUsuarioServico p : listaServicos) {
				if(p.getServicoVerificacao().isIcAtivo()){
					selectItemServicos.add(new SelectItem(p.getServicoVerificacao().getNuServicoVerificacao(),
										   p.getServicoVerificacao().getNoServicoVerificacao()));
				}
			}
			
			listaServicos.clear();
			showListaServicos = false;
		} else {
			listaServicos.clear();
			showListaServicos = true;
		}
		
	}
	
	
	/** ---------------------------------------------------------------------------------------
	 * Getters and Setters
	 */
	
	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula.replace("_","");
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
			Unidade u = unidadeBO.getUnidade(usuarioADBO.getNuUnidade(usuario.getCoUsuario()));
			return u.getId().getNuUnidade().toString() + SEPARADOR_LABEL_COMBO + u.getNomeAbreviado();
		} catch (SIIACException e) {
			LogCEF.error(e.getMessage());
			return "";
		}
	}

	/**
	 * @return the perfil
	 */
	public String getPerfil() {
		return usuario.getPerfil().getNoPerfil();
	}
	
	/**
	 * @return the itemProduto
	 */
	public String getItemProduto() {
		return itemProduto;
	}

	/**
	 * @param itemProduto the itemProduto to set
	 */
	public void setItemProduto(String itemProduto) {
		this.itemProduto = itemProduto;
	}

	/**
	 * @return the selectItemProdutos
	 */
	public List<SelectItem> getSelectItemProdutos() {
		Collections.sort(selectItemProdutos, new Comparator<SelectItem>() {
			public int compare(SelectItem obj1, SelectItem obj2) {
				return obj1.getLabel().compareTo(obj2.getLabel());
			}
		});
		return selectItemProdutos;
	}

	/**
	 * @param selectItemProdutos the selectItemProdutos to set
	 */
	public void setSelectItemProdutos(List<SelectItem> selectItemProdutos) {
		this.selectItemProdutos = selectItemProdutos;
	}

	/**
	 * @return the listaProdutos
	 */
	public List<PreferenciasUsuario> getListaProdutos() {
		return listaProdutos;
	}

	/**
	 * @param listaProdutos the listaProdutos to set
	 */
	public void setListaProdutos(List<PreferenciasUsuario> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	/**
	 * @return the itemServico
	 */
	public Short getItemServico() {
		return itemServico;
	}

	/**
	 * @param itemServico the itemServico to set
	 */
	public void setItemServico(Short itemServico) {
		this.itemServico = itemServico;
	}

	/**
	 * @return the selectItemServicos
	 */
	public List<SelectItem> getSelectItemServicos() {
		Collections.sort(selectItemServicos, new Comparator<SelectItem>() {
			public int compare(SelectItem obj1, SelectItem obj2) {
				return obj1.getLabel().compareTo(obj2.getLabel());
			}
		});
		return selectItemServicos;
	}

	/**
	 * @param selectItemServicos the selectItemServicos to set
	 */
	public void setSelectItemServicos(List<SelectItem> selectItemServicos) {
		this.selectItemServicos = selectItemServicos;
	}

	/**
	 * @return the listaServicos
	 */
	public List<PreferenciaUsuarioServico> getListaServicos() {
		return listaServicos;
	}

	/**
	 * @param listaServicos the listaServicos to set
	 */
	public void setListaServicos(List<PreferenciaUsuarioServico> listaServicos) {
		this.listaServicos = listaServicos;
	}
	
	public List<SelectItem> getSelectItemGerentes(){
		return selectItemGerentes;
	}
	
	public String getItemGerente(){
		return itemGerente;
	}
	
	public void setItemGerente(String itemGerente){
		this.itemGerente = itemGerente; 
	}
	
	public List<PreferenciaUsuarioGerente> getListaGerentes() {
		return listaGerentes;
	}

	public void setListaGerentes(List<PreferenciaUsuarioGerente> listaGerentes) {
		this.listaGerentes = listaGerentes;
	}
	
	/**
	 * ----------------------------------------------------
	 * Metodos que controlam renderizacao em tela
	 * @return
	 */
	
	


	/**
	 * Verifica se o modo atual e Cadastro (com a matr�cula do usuario logado, podendo alterar valores) 
	 */
	private boolean isCadastro(){
		return stateMode.equals(MODO_CADASTRO);
	}
	
	/**
	 * Verifica se o modo atual e Consulta (apenas visualizacao de dados de outros usuarios)
	 * @return
	 */
	private boolean isConsulta(){
		return stateMode.equals(MODO_CONSULTA);
	}
	
	/**
	 * Valida se o usuario deve ser mostrado como ativo no painel.
	 * @return
	 */
	public boolean isShowSituacaoAtivo(){
		return usuario.getIcAtivo();
	}
	
	/**
	 * Valida se o usuario deve ser mostrado como inativo no painel.
	 * @return
	 */
	public boolean isShowSituacaoInativo(){
		return !usuario.getIcAtivo();
	}
	
	/**
	 * Valida se o botao "Consultar Outro usuario" deve ser mostrado.
	 * @return
	 */
	public boolean isShowConsultarOutroUsuario(){
		return isCadastro() && 
			(isGestorSistema()
				|| isCentralAtendimentoSuporte());
	}
	
	/**
	 * Valida se o botao "Consultar" deve ser mostrado.
	 * @return
	 */
	public boolean isShowConsultar(){
		return isConsulta() &&
			(isGestorSistema()
				|| isCentralAtendimentoSuporte());
	}
	
	/**
	 * Valida se a label vazia (que serve para encaixe de CSS no IE) deve ser mostrada.
	 * @return
	 */
	public boolean isShowLabelVazia(){
		return !isShowConsultarOutroUsuario() && !isShowConsultar();
	}
	
	/**
	 * Valida se o campo de matricula deve ser mostrado como input (para insercao de dados para a consulta).
	 * @return
	 */
	public boolean isShowInputMatricula(){
		return isConsulta();
	}
	
	/**
	 * Valida se o campo de matricula deve ser mostrado como output.
	 * @return
	 */
	public boolean isShowOutputMatricula(){
		return isCadastro();
	}
	
	/**
	 * Valida se a label Preferencias ... deve ser mostrada na tela. 
	 * @return
	 */
	public boolean isShowLabelPreferencia() {
		return isCadastro();
	}
	
	/**
	 * Valida se o painel de cadastro de produtos preferenciais deve ser mostrado.
	 * @return
	 */
	public boolean isShowCadastrarProduto(){
		return isCadastro();
	}
	
	/**
	 * Valida se o painel de cadastro de categorias preferenciais deve ser mostrado.
	 * @return
	 */
	public boolean isShowCadastrarCategoria(){
		return isCadastro();
	}
	

	/**
	 * Valida se o painel de cadastro de produtos preferenciais deve ser mostrado.
	 * @return
	 */
	public boolean getIsShowConsulta(){
		return stateMode == MODO_CONSULTA;
	}
	
	/**
	 * Valida se a coluna de remocao de produtos preferenciais deve ser mostrada na tabela. 
	 * @return
	 */
	public boolean isShowRemoverProduto(){
		return isCadastro();
	}
	
	/**
	 * Valida se a coluna de remocao de produtos preferenciais deve ser mostrada na tabela. 
	 * @return
	 */
	public boolean isShowRemoverCategoria(){
		return isCadastro();
	}
	
	/**
	 * Valida se o painel de cadastro de servicos preferenciais deve ser mostrado.
	 * @return
	 */
	public boolean isShowCadastrarServico(){
		return isCadastro();
	}
	
	/**
	 * Valida se a coluna de remocao de servicos preferenciais deve ser mostrada na tela.
	 * @return
	 */
	public boolean isShowRemoverServico(){
		return isCadastro();
	}
	
	/**
	 * Valida se o botao "Salvar" deve ser mostrado.
	 * @return
	 */
	public boolean isShowSalvar(){
		return isCadastro();
	}
	
	/**
	 * Valida se o botao "Voltar" deve ser mostrado.
	 * @return
	 */
	public boolean isShowVoltar(){
		return isConsulta();
	}
	
	/**
	 * Verifica se há produtos na lista de preferenciais para que o botão de "Todos" seja renderizado.
	 * @return
	 */
	public boolean isShowButtonTodosProdutos(){
		return listaProdutos != null && !listaProdutos.isEmpty();
	}

	/**
	 * Verifica se há serviços na lista de preferenciais para que o botão de "Todos" seja renderizado.
	 * @return
	 */
	public boolean isShowButtonTodosServicos(){
		return listaServicos != null && !listaServicos.isEmpty();
	}

	public boolean isTodosProdutos() {
		return todosProdutos;
	}

	public void setTodosProdutos(boolean todosProdutos) {
		this.todosProdutos = todosProdutos;
	}

	public boolean isShowListaProdutos() {
		return showListaProdutos;
	}

	public void setShowListaProdutos(boolean showListaProdutos) {
		this.showListaProdutos = showListaProdutos;
	}

	public boolean isTodosServicos() {
		return todosServicos;
	}

	public void setTodosServicos(boolean todosServicos) {
		this.todosServicos = todosServicos;
	}

	public boolean isShowListaServicos() {
		return showListaServicos;
	}

	public void setShowListaServicos(boolean showListaServicos) {
		this.showListaServicos = showListaServicos;
	}
	
	public boolean getPossuiTodosProdutos()
	{
		if (listaProdutos.isEmpty())
		{
			return true;
		}
		
		return false;
	}
	
	public boolean getPossuiTodasCategorias()
	{
		if (listaCategorias.isEmpty())
		{
			return true;
		}
		
		return false;
	}
	
	public boolean getPossuiTodosServicos()
	{
		if (listaServicos.isEmpty())
		{
			return true;
		}
		
		return false;
	}

	public String getItemCategoria() {
		return itemCategoria;
	}

	public void setItemCategoria(String itemCategoria) {
		this.itemCategoria = itemCategoria;
	}

	public List<SelectItem> getSelectItemCategorias() {
		return selectItemCategorias;
	}

	public void setSelectItemCategorias(List<SelectItem> selectItemCategorias) {
		this.selectItemCategorias = selectItemCategorias;
	}

	public List<PreferenciasUsuarioCategoriaProduto> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<PreferenciasUsuarioCategoriaProduto> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public boolean isTodasCategorias() {
		return todasCategorias;
	}

	public void setTodasCategorias(boolean todasCategorias) {
		this.todasCategorias = todasCategorias;
	}

	public boolean isShowListaCategorias() {
		return showListaCategorias;
	}

	public void setShowListaCategorias(boolean showListaCategorias) {
		this.showListaCategorias = showListaCategorias;
	}

	
	public boolean isDisableGerentes() {
		disableGerentes = listaGerentes.size() > 0;
		return disableGerentes;
	}

	

	
	
}