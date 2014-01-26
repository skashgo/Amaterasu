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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.ICategoriaProdutoBO;
import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.ProdutoVO;
import br.gov.caixa.siiac.model.domain.CategoriaProduto;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

/**
 * @author GisConsult
 *
 */
@Service()
@Scope("request")
public class ProdutoMB extends AbstractMB {
	
	/**
	 * Filtro Simples
	 */
	private String pesquisaString;
	private Boolean pesquisaMostraInativos = false;
	
	private List<Produto> produtoList = new ArrayList<Produto>();
	private transient List<CategoriaProduto> categoriaListCombo = new ArrayList<CategoriaProduto>();
	private transient boolean produtoInativo = false;
	
	private String campoCodProduto;
	private String campoNomeProduto;
	private Integer showSituacao = 1;
	private String campoOperacao;
	private String campoModalidade;
	private String campoSiglaSistema;
	private String campoTipoPessoa;
	private BigDecimal campoValorLimite;
	private Integer campoCategoriaProduto;
	private String nomeOperacao;
	private String nomeModalidade;
	private boolean campoIcAtivo;
	private boolean desabilitaCampoProduto;
	private boolean produtoSIICO;
	private boolean desabilitaNomeProduto = true;
	
	private String rotularColuna = "INATIVAR";
	private static final String TIPO_PESSOA_FISICA = "FISICA";
	private static final String TIPO_PESSOA_JURIDICA = "JURIDICA";
	private static final String SITUACAO_ATIVA = "ATIVO";
	private static final String SITUACAO_INATIVA = "INATIVO";
	private static final String MENSAGEM = "msgsProduto";
	private static final String COLUNA_INATIVAR = "COLUMN.inativar";
	private static final String COLUNA_ATIVAR = "COLUMN.ativar";
	public static final int SITUACAO_ATIVO = 1;
	public static final int SITUACAO_INATIVO = 2;
	private transient IProdutoBO produtoBO;
	private transient ICategoriaProdutoBO categoriaProdutoBO;
	
	private CategoriaProduto categoriaProduto;
	private Produto produto;
	
	private String descricaoOperacao;
	private String campoDescricaoModalidade;
	private boolean modalidadeVazia = true;
	
	private String validaProduto;
	private String coProduto = "";
	
	@Autowired
	public void setProdutoBO(IProdutoBO produtoBO) {
		produto = new Produto();
		this.produtoBO = produtoBO;
		
		// TODO: Refatorar para usar @PostConstruct
		fillCampos();
		preencherFiltros();
	}
	
	@Autowired
	public void setCategoriaProdutoBO(ICategoriaProdutoBO categoriaProdutoBO) {
		categoriaProduto = new CategoriaProduto();
		this.categoriaProdutoBO = categoriaProdutoBO;
		categoriaListCombo = categoriaProdutoBO.getListFiltro("", false);
	}
	
	
	public ProdutoMB() {
		produtoList = new ArrayList<Produto>();
		
	}
	
	private void preencherFiltros(){
		getFilterInSession(this.getClass());
		
		setFiltro();
		
		if(getFilterBase().isModoAvancado()){
			try {
				setProdutoList(carregaListaProduto(true));
				setModoFiltro();
			} catch (SIIACException e) {
				error(MSGS, e.getMessage());
				LogCEF.error(e.getMessage());
			}
		}else{
			try {
				if(getFilterBase().getString("pesquisaString") != null || getFilterBase().getBoolean("pesquisaMostraInativos") != false){
					setProdutoList(carregaListaProdutoFiltroSimples());
				}else{
					setProdutoList(carregaListaProduto(false));
				}
			
				setModoInicio();
			} catch (SIIACException e) {
				error(MSGS, e.getMessage());
				LogCEF.error(e.getMessage());
			}
			
		}
	}
	
	
	// Metodo a ser anotado com @PostConstruct
	public void fillCampos() {
		rotularColuna = "INATIVAR";
		setPesquisaString("");
		setCampoNomeProduto("");
		setShowSituacao(1);
		setCampoOperacao("");
		setCampoModalidade("");
		setCampoSiglaSistema("");
		setCampoTipoPessoa("");
		setCampoValorLimite(null);
		setCampoCategoriaProduto(0);
		setCampoIcAtivo(false);
		setPesquisaMostraInativos(false);
		setCampoDescricaoModalidade("");
		setCampoNomeProduto("");
		setDescricaoOperacao("");
		
		
	}
	
	private void setFiltro(){
		campoNomeProduto = getFilterBase().getString("campoNomeProduto");
		pesquisaMostraInativos = getFilterBase().getBoolean("pesquisaMostraInativos");
		campoOperacao = getFilterBase().getString("campoOperacao");
		campoModalidade = getFilterBase().getString("campoModalidade");
		campoSiglaSistema = getFilterBase().getString("campoSiglaSistema");
		campoValorLimite = getFilterBase().getBigDecimal("campoValorLimite");
		campoTipoPessoa = getFilterBase().getString("campoTipoPessoa");
		campoCategoriaProduto =  getFilterBase().getInt("campoCategoriaProduto");
		
		pesquisaString = getFilterBase().getString("pesquisaString");
		pesquisaMostraInativos = getFilterBase().getBoolean("pesquisaMostraInativos");
	}
	
	public void doLimpar(ActionEvent evt)
	{
		fillCampos();
		getFilterBase().limparFiltros();
		putFilterBase(this.getClass());
		
		getFilterInSession(this.getClass());
		
		setFiltro();
		
		if(getFilterBase().isModoAvancado()){
			try {
				setProdutoList(carregaListaProduto(true));
				setModoFiltro();
			} catch (SIIACException e) {
				error(MSGS, e.getMessage());
				LogCEF.error(e.getMessage());
			}
		}else{
			try {
				if(getFilterBase().getString("pesquisaString") != null || getFilterBase().getBoolean("pesquisaMostraInativos") != false){
					setProdutoList(carregaListaProdutoFiltroSimples());
				}else{
					setProdutoList(carregaListaProduto(false));
				}
			
				setModoInicio();
			} catch (SIIACException e) {
				error(MSGS, e.getMessage());
				LogCEF.error(e.getMessage());
			}
			
		}
	}
	
	/**
	 * carregaListaProduto
	 * @param utilizaParametros 
	 * @return List<Produto>
	 * @throws SIIACException 
	 */
	public List<Produto> carregaListaProduto(boolean fazConsulta) throws SIIACException {
		
	

		
		return produtoBO.getPesquisaProduto(campoNomeProduto, pesquisaMostraInativos, campoOperacao, 
				campoModalidade, campoSiglaSistema, campoValorLimite, campoTipoPessoa, 
				String.valueOf(campoCategoriaProduto), fazConsulta);
	}
	
	/**
	 * carregaListaProduto
	 * @param utilizaParametros 
	 * @return List<Produto>
	 * @throws SIIACException 
	 */
	public List<Produto> carregaListaProdutoFiltroSimples() throws SIIACException {
		
		return produtoBO.getListProdutoFiltroSimples(getFilterBase());
	}
	
	/**
	 * doCadastrarClick
	 * @return
	 */
	public void doCadastrarClick() {
		setModoCadastro();
		campoIcAtivo = false;
		fillCampos();
	}
	
	/**
	 * doCancelarClick
	 * @param evt
	 */
	public void doVoltarClick(ActionEvent evt) {
		setModoInicio();
		fillCampos();
		produtoInativo = false;
		
		getFilterInSession(this.getClass());
		
		setFiltro();
		
		if(getFilterBase().isModoAvancado()){
			try {
				setProdutoList(carregaListaProduto(true));
				setModoFiltro();
			} catch (SIIACException e) {
				error(MSGS, e.getMessage());
				LogCEF.error(e.getMessage());
			}
		}else{
			try {
				if(getFilterBase().getString("pesquisaString") != null || getFilterBase().getBoolean("pesquisaMostraInativos") != false){
					setProdutoList(carregaListaProdutoFiltroSimples());
				}else{
					setProdutoList(carregaListaProduto(false));
				}
			
				setModoInicio();
			} catch (SIIACException e) {
				error(MSGS, e.getMessage());
				LogCEF.error(e.getMessage());
			}
			
		}
	}
	
	/**
	 * doCancelarClick
	 * @param evt
	 */
	public void doCancelarClick(ActionEvent evt) {
		warn(MSGS, MN003);
		setModoInicio();
		fillCampos();
		
		getFilterInSession(this.getClass());
		
		setFiltro();
		
		if(getFilterBase().isModoAvancado()){
			try {
				setProdutoList(carregaListaProduto(true));
				setModoFiltro();
			} catch (SIIACException e) {
				error(MSGS, e.getMessage());
				LogCEF.error(e.getMessage());
			}
		}else{
			try {
				if(getFilterBase().getString("pesquisaString") != null || getFilterBase().getBoolean("pesquisaMostraInativos") != false){
					setProdutoList(carregaListaProdutoFiltroSimples());
				}else{
					setProdutoList(carregaListaProduto(false));
				}
			
				setModoInicio();
			} catch (SIIACException e) {
				error(MSGS, e.getMessage());
				LogCEF.error(e.getMessage());
			}
			
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
			if (isModoInicio()) {
				
				getFilterBase().limparFiltros();
				getFilterBase().addToFilter("pesquisaString", pesquisaString);
				getFilterBase().addToFilter("pesquisaMostraInativos", pesquisaMostraInativos);
				getFilterBase().setModoSimples();
				putFilterBase(this.getClass());
				
				List<Produto> lis = carregaListaProdutoFiltroSimples();
				if (lis == null || lis.isEmpty()) {
					warn(MSGS, MN016);
				}
				setProdutoList(lis);
			} else {
				getFilterBase().limparFiltros();
				getFilterBase().addToFilter("campoNomeProduto", campoNomeProduto);
				getFilterBase().addToFilter("pesquisaMostraInativos", pesquisaMostraInativos);
				getFilterBase().addToFilter("campoOperacao", campoOperacao);
				getFilterBase().addToFilter("campoModalidade", campoModalidade);
				getFilterBase().addToFilter("campoSiglaSistema", campoSiglaSistema);
				getFilterBase().addToFilter("campoValorLimite", campoValorLimite);
				getFilterBase().addToFilter("campoTipoPessoa", campoTipoPessoa);
				getFilterBase().addToFilter("campoCategoriaProduto", String.valueOf(campoCategoriaProduto));
				getFilterBase().setModoAvancado();
				putFilterBase(this.getClass());
				
				List<Produto> lis = carregaListaProduto(true);
				if (lis == null || lis.isEmpty()) {
					warn(MSGS, MN016);
				}
				setProdutoList(lis);
			}
		} catch (SIIACException e) {
			error(e.getMessage());
			LogCEF.error(e.getMessage());
		}
	}
	
	
	
	/**
	 * doVisualizaClick
	 * @return
	 */
	public String doVisualizaClick() {
		setModoVisualiza();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Produto gridProduto = (Produto) facesContext.getExternalContext().getRequestMap().get("rowPro");
		
		if (gridProduto.isIcAtivo() == false) {
			produtoInativo = true;
		}
		
		campoCodProduto = gridProduto.getCoProduto();
		campoNomeProduto = gridProduto.getNoProduto();
		campoSiglaSistema = gridProduto.getSgSistemaOrigem();
		campoValorLimite = gridProduto.getVrLimiteVerificacao();
		campoCategoriaProduto = gridProduto.getCategoriaProduto().getNuCategoriaProduto();
		campoIcAtivo = gridProduto.isIcAtivo();
		campoTipoPessoa = gridProduto.getIcPfPj();
		campoModalidade = String.valueOf(gridProduto.getNuModalidade());
		campoOperacao = String.valueOf(gridProduto.getNuOperacao());
		setPesquisaMostraInativos(false);
		
		
		return "toProduto";
	}
	
	/**
	 * doAlteraClick
	 * 
	 * @param evt
	 * @throws SIIACException
	 */
	public String doAlteraClick() {
		setModoAltera();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Produto gridProduto = (Produto) facesContext.getExternalContext().getRequestMap().get("rowPro");
		
		campoCodProduto = gridProduto.getCoProduto();
		campoNomeProduto = gridProduto.getNoProduto();
		campoSiglaSistema = gridProduto.getSgSistemaOrigem();
		campoValorLimite = gridProduto.getVrLimiteVerificacao();
		campoCategoriaProduto = gridProduto.getCategoriaProduto().getNuCategoriaProduto();
		campoIcAtivo = gridProduto.isIcAtivo();
		campoTipoPessoa = gridProduto.getIcPfPj();
		
		if (Utilities.notEmpty(gridProduto.getNoModalidade()))
			campoDescricaoModalidade = gridProduto.getNoModalidade();
		else
			campoDescricaoModalidade = "";
		
		campoModalidade = ConvertUtils.padZerosLeft(String.valueOf(gridProduto.getNuModalidade()), 3);
		campoOperacao = ConvertUtils.padZerosLeft(String.valueOf(gridProduto.getNuOperacao()), 4);
		
		//Realiza a busca pelo produto
		if (produtoBO.existeProdutoSIICO(campoOperacao))
		{
			ProdutoVO produtoVO = new ProdutoVO();
			produtoVO = produtoBO.getProdutoSIICO(campoOperacao);
			descricaoOperacao = produtoVO.getNoProduto();
			campoSiglaSistema = produtoVO.getSiglaSistema();
			
			campoNomeProduto = descricaoOperacao.trim() + " " + campoDescricaoModalidade.trim();
			
		}
		
		if (Utilities.empty(campoModalidade) || campoModalidade.equals("000"))
		{
			campoDescricaoModalidade = "";
			modalidadeVazia = true;
		} else {
			modalidadeVazia = false;
		}
		
		return "toProduto";
	}
	
	public boolean verificaProdutoAtivoInativo( String coProduto, boolean ativo)
	{
		return produtoBO.verificaProdutoAtivoInativo(coProduto, ativo);
	}
	
	public void doValidaProduto(ActionEvent evt)
	{
		validaProduto = "0";
		if (!isModoAltera()) {
			if (Utilities.empty(campoOperacao))
			{
				error(MSGS, MN002);
				warn("Campo Operação não informado.");
				return;
			}
			
			if (!produtoBO.existeProdutoSIICO(campoOperacao))
			{
				error(MSGS, MN002);
				warn(MSG_PRODUTO_SIICO_INEXISTENTE);
				return;
			}
			
			if (Utilities.empty(campoNomeProduto)) {
				error(MSGS, MN002);
				warn("Campo Nome do Produto não informado.");
				return;
			}
			
			if (Utilities.empty(campoSiglaSistema)) {
				error(MSGS, MN002);
				warn("Campo Sigla do Sistema não informado.");
				return;
			}
		}
		
		
		if (Utilities.notEmpty(campoModalidade) && !campoModalidade.equals("000"))
		{
			if (Utilities.empty(campoDescricaoModalidade))
			{
				error(MSGS, MN002);
				warn("Campo Descrição da Modalidade não informado.");
				return;
			}
		} else {
			campoDescricaoModalidade = "";
		}
		
		if (campoCategoriaProduto == 0) {
			error(MSGS, MN002);
			warn("Campo Categoria não informado.");
			return;
		}		
		
		if (campoValorLimite == null) {
			setCampoValorLimite(null);
		}
				
		if (isModoAltera()) {
			coProduto = campoCodProduto;
		} else { //se for inclusao
		
			coProduto = ConvertUtils.padZerosLeft(campoOperacao, 4) + ConvertUtils.padZerosLeft(campoModalidade, 3);
			
			if (coProduto.equals("0000000")) { //nao passar com zeros 
				error(MSGS, XX024);
				return;
			}
			
			//Verificando produto ativo / inativo
			if (verificaProdutoAtivoInativo(coProduto, true))
			{
				error(MSGS, MN023);
				return;
			}
			
			//Verificando produto ativo / inativo
			if (verificaProdutoAtivoInativo(coProduto, false))
			{
				validaProduto = "1";
				return;
			}
			
			
			//Verifica se o nome do produto existe na base
			if (produtoBO.verificaNomeProduto(campoNomeProduto))
			{
				error(MSGS, MN015);
				return;
			}
		}
		
		validaProduto = "2";
		
	}
	
	/**
	 * Método responsável por alterar o produto na seguinte situação:
	 * 	- Caso o código do produto já exista na base, e o produto referente à este código
	 * 	  esteja inativo, será perguntado ao usuário se deseja ativar e alterar o produto
	 *    em questão, caso ele confirme a operação, será chamado este método.
	 * 	 * @param evt
	 */
	public void doAlterarProduto(ActionEvent evt)
	{
		try {
			boolean retorno = false;
			
			coProduto = ConvertUtils.padZerosLeft(campoOperacao, 4) + ConvertUtils.padZerosLeft(campoModalidade, 3);
			
			retorno = produtoBO.gravarProduto(coProduto, campoNomeProduto,
					Short.parseShort(campoModalidade),
					Short.parseShort(campoOperacao), campoSiglaSistema,
					campoValorLimite, campoTipoPessoa, campoCategoriaProduto,
					campoIcAtivo, false, descricaoOperacao,
					campoDescricaoModalidade);
			
			if (retorno) {
				info(MSGS, MN007);
				setModoInicio();
				fillCampos();
			} else {
				error(MSGS, MN002);
			}
			
		} catch (Exception e)
		{
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
		
	}
	
	/**
	 * doSalvarClick
	 * @return
	 */
	public void doSalvarClick(ActionEvent evt) {
		try {
			boolean retorno = false;
						
			retorno = produtoBO.gravarProduto(coProduto, campoNomeProduto, Short.parseShort(campoModalidade), Short.parseShort(campoOperacao), campoSiglaSistema, campoValorLimite, campoTipoPessoa, campoCategoriaProduto, campoIcAtivo, isModoAltera(), descricaoOperacao, campoDescricaoModalidade);
			
			if (retorno) {
				if (isModoAltera()) {
					info(MSGS, MN008);
				} else {
					info(MSGS, MN007);
				}
				setModoInicio();
				fillCampos();
			} else {
				error(MSGS, MN002);
			}
			
		} catch (Exception e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
	}
	
	/**
	 * doVincularClick
	 * @return
	 */
	public String doVincularClick() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Produto gridProduto = (Produto) facesContext.getExternalContext().getRequestMap().get("rowPro");
		
		((HttpServletRequest) facesContext.getExternalContext().getRequest()).setAttribute("campoNomeProduto", gridProduto.getCoProduto());
		return "toServicoProduto";
	}
	
	public void doConsultarSimples(ActionEvent evt) {
		setModoInicio();
	}
	
	public void doConsultarAvancado(ActionEvent evt) {
		setModoFiltro();
	}
	
	/**
	 * getListSituacao - Lista de Situacoes - combo
	 * @return
	 */
	public List<SelectItem> getListSituacao() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem("1", SITUACAO_ATIVA));
		list.add(new SelectItem("2", SITUACAO_INATIVA));
		return list;
	}
	
	/**
	 * getListTipoPessoa - Lista de Tipo de Pessoa - combo
	 * @return
	 */
	public List<SelectItem> getListTipoPessoa() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem("PF", TIPO_PESSOA_FISICA));
		list.add(new SelectItem("PJ", TIPO_PESSOA_JURIDICA));
		return list;
	}
	
	/**
	 * getListCategoriaProduto - Lista de Categorias para o Produto - combo
	 * @return
	 */
	public List<SelectItem> getListCategoriaProduto() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		
		for (CategoriaProduto cp : categoriaListCombo) {
			list.add(new SelectItem(cp.getNuCategoriaProduto(), cp.getNoCategoriaProduto()));
		}
		
		return list;
	}
	
	/**
	 * setCampoNomeProduto
	 * @param campoNomeProduto
	 */
	public void setCampoNomeProduto(String campoNomeProduto) {
		this.campoNomeProduto = campoNomeProduto.trim();
	}
	
	/**
	 * getCampoNomeProduto
	 * @return
	 */
	public String getCampoNomeProduto() {
		return campoNomeProduto;
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
		this.rotularColuna = rotularColuna.trim();
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
	 * setCampoOperacao
	 * @param campoOperacao
	 */
	public void setCampoOperacao(String campoOperacao) {
		this.campoOperacao = ConvertUtils.padZerosLeft(campoOperacao, 4);
	}
	
	/**
	 * getCampoOperacao
	 * @return
	 */
	public String getCampoOperacao() {
		return campoOperacao;
	}
	
	/**
	 * setCampoModalidade
	 * @param campoModalidade
	 */
	public void setCampoModalidade(String campoModalidade) {
		this.campoModalidade = ConvertUtils.padZerosLeft(campoModalidade, 3);
	}
	
	/**
	 * getCampoModalidade
	 * @return
	 */
	public String getCampoModalidade() {
		return campoModalidade;
	}
	
	/**
	 * setCampoSiglaSistema
	 * @param campoSiglaSistema
	 */
	public void setCampoSiglaSistema(String campoSiglaSistema) {
		this.campoSiglaSistema = campoSiglaSistema.trim();
	}
	
	/**
	 * getCampoSiglaSistema
	 * @return
	 */
	public String getCampoSiglaSistema() {
		return campoSiglaSistema;
	}
	
	/**
	 * setCampoTipoPessoa
	 * @param campoTipoPessoa
	 */
	public void setCampoTipoPessoa(String campoTipoPessoa) {
		if (Utilities.notEmpty(campoTipoPessoa))
			this.campoTipoPessoa = campoTipoPessoa.trim();
		else
			this.campoTipoPessoa = campoTipoPessoa;		
	}
	
	/**
	 * getCampoTipoPessoa
	 * @return
	 */
	public String getCampoTipoPessoa() {
		return campoTipoPessoa;
	}
	
	/**
	 * setCampoCategoriaProduto
	 * @param campoCategoriaProduto
	 */
	public void setCampoCategoriaProduto(Integer campoCategoriaProduto) {
		this.campoCategoriaProduto = campoCategoriaProduto;
	}
	
	/**
	 * getCampoCategoriaProduto
	 * @return
	 */
	public Integer getCampoCategoriaProduto() {
		return campoCategoriaProduto;
	}
	
	/**
	 * setCampoValorLimite
	 * @param campoValorLimite
	 */
	public void setCampoValorLimite(BigDecimal campoValorLimite) {
		this.campoValorLimite = campoValorLimite;
	}
	
	/**
	 * getCampoValorLimite
	 * @return
	 */
	public BigDecimal getCampoValorLimite() {
		return campoValorLimite;
	}
	
	/**
	 * setCampoCodProduto
	 * @param campoCodProduto
	 */
	public void setCampoCodProduto(String campoCodProduto) {
		this.campoCodProduto = campoCodProduto.trim();
	}
	
	/**
	 * getCampoCodProduto
	 * @return
	 */
	public String getCampoCodProduto() {
		if (campoCodProduto.length() > 4) {
			return campoCodProduto.substring(0, 4) + "-" + campoCodProduto.substring(4);
		} else {
			return "";
		}
	}
	
	/**
	 * setCampoIcAtivo
	 * @param campoIcAtivo
	 */
	public void setCampoIcAtivo(boolean campoIcAtivo) {
		this.campoIcAtivo = campoIcAtivo;
	}
	
	/**
	 * getCampoIcAtivo
	 * @return
	 */
	public boolean getCampoIcAtivo() {
		return campoIcAtivo;
	}
	
	/**
	 * getProdutoList
	 * @return
	 */
	public List<Produto> getProdutoList() {
		return this.produtoList;
	}
	
	public void setProdutoList(List<Produto> produtoList) {
		this.produtoList = produtoList;
	}
	
	/**
	 * getMensagemConfirmacao
	 * @return
	 */
	public String getMensagemConfirmacao() {
		if (isModoAltera()) {
			return getMessage(MSGS, MA003);
		} else {
			return getMessage(MSGS, MA002);
		}
	}
	
	/**
	 * getShowButtonDesativar
	 * @return
	 */
	public boolean getShowButtonDesativar() {
		return getShowSituacao() == SITUACAO_ATIVO;
	}
	
	/**
	 * getShowButtonAtivar
	 * @return
	 */
	public boolean getShowButtonAtivar() {
		return getShowSituacao() == SITUACAO_INATIVO;
	}
	
	/**
	 * doInativaClick
	 * @param evt
	 */
	public String doInativaClick() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Produto gridProduto = (Produto) facesContext.getExternalContext().getRequestMap().get("rowPro");
		try {
			produtoBO.ativarDesativarProduto(gridProduto.getCoProduto(), false);
			info(MSGS, MN011);
			setProdutoList(carregaListaProduto(false));
		} catch (SIIACException e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
		return "produto";
	}
	
	/**
	 * doAtivaClick
	 * @param evt
	 */
	public void doAtivaClick(ActionEvent evt) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Produto gridProduto = (Produto) facesContext.getExternalContext().getRequestMap().get("rowPro");
		try {
			produtoBO.ativarDesativarProduto(gridProduto.getCoProduto(), true);
			info(MSGS, MN012);
			pesquisaMostraInativos = false;
			setProdutoList(carregaListaProduto(false));
		} catch (SIIACException e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
	}
	
	public void doInativaPeloBotao(ActionEvent evt) {
		try {
			produtoBO.ativarDesativarProduto(campoCodProduto, false);
			info(MSGS, MN011);
			campoIcAtivo = false;
			setProdutoList(carregaListaProduto(false));
		} catch (SIIACException e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
	}
	
	public void doAtivaPeloBotao(ActionEvent evt) {
		try {
			produtoBO.ativarDesativarProduto(campoCodProduto, true);
			info(MSGS, MN012);
			campoIcAtivo = true;
			pesquisaMostraInativos = false;
			setProdutoList(carregaListaProduto(true));
		} catch (SIIACException e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
	}
	
	/**
	 * getProdutoInativo
	 * @return
	 */
	public boolean getProdutoInativo() {
		return produtoInativo;
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
	
	public void buscaProdutoSIICO(ActionEvent evt)
	{
		if (Utilities.notEmpty(campoOperacao))
		{
			//Realiza a busca pelo produto
			if (produtoBO.existeProdutoSIICO(campoOperacao))
			{
				ProdutoVO produtoVO = new ProdutoVO();
				produtoVO = produtoBO.getProdutoSIICO(campoOperacao);
				descricaoOperacao = produtoVO.getNoProduto();
				campoSiglaSistema = produtoVO.getSiglaSistema();
				
				campoNomeProduto = descricaoOperacao.trim() + " " + campoDescricaoModalidade.trim();
				
			} else {
				descricaoOperacao = "";
				campoSiglaSistema = "";
				campoNomeProduto = "";
				error(MSGS, MN002);
				warn("Operação não existente na base SIICO");
			}
		}
	}
	
	public void doVerificaModalidade(ActionEvent evt) {
		if (Utilities.empty(campoModalidade) || campoModalidade.equals("000"))
		{
			campoDescricaoModalidade = "";
			modalidadeVazia = true;
		} else {
			modalidadeVazia = false;
		}
		campoNomeProduto = descricaoOperacao.trim() + " " + campoDescricaoModalidade.trim();
		if (Utilities.notEmpty(campoOperacao)){
			if (!produtoBO.existeProdutoSIICO(campoOperacao)){
				error(MSGS, MN002);
				warn("Operação não existente na base SIICO");
			}
		}
	}
	
	///_________________________
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
	
	public boolean isShowOutputValues() {
		return isModoVisualiza();
	}
	
	public boolean isShowInputValues() {
		return isModoAltera();
	}
	
	public boolean isShowPanelButtons() {
		return isModoCadastro() || isModoAltera() || isModoVisualiza();
	}
	
	public boolean isShowCheckMostrarInativos() {
		return isModoFiltro();
	}
	
	public boolean isShowLinkConsultarSimples() {
		return isModoFiltro();
	}
	
	public boolean isShowButtonConsultarAvancado() {
		return isModoFiltro();
	}
	
	public boolean isShowButtonNovoAvancado() {
		return isModoFiltro();
	}
	
	public boolean isShowButtonSalvar() {
		return isModoCadastro() || isModoAltera();
	}
	
	public boolean isShowButtonCancelar() {
		return isModoCadastro() || isModoAltera();
	}
	
	public boolean isShowButtonVoltar() {
		return isModoVisualiza();
	}
	
	/**
	 * Verifica se deve ser mostrado o painel de consulta simples
	 * @return
	 */
	public boolean getShowConsultarSimples() {
		return isModoInicio();
	}
	
	/**
	 * Verifica se deve ser mostrado o painel de consulta avançado
	 * @return
	 */
	public boolean getShowConsultarAvancado() {
		return isModoFiltro() || isModoCadastro(); // Também verifica modo cadastro pois o mesmo formulário é usado para cadastro e filtro avançado.
	}
	
	public boolean isShowFormAlterar() {
		return isModoAltera() || isModoVisualiza();
	}
	
	public boolean isShowDataTable() {
		return isModoInicio() || isModoFiltro();
	}

	public String getCampoNomeCategoriaProduto() {
		return categoriaProdutoBO.findById(campoCategoriaProduto).getNoCategoriaProduto();
	}

	public boolean isDesabilitaCampoProduto() {
		
		if (produtoSIICO)
		{
			if (campoModalidade.trim().equalsIgnoreCase("000"))
			{
				desabilitaCampoProduto = true;
			} else {
				desabilitaCampoProduto = false;
			}
		}
		
		return desabilitaCampoProduto;
	}

	public void setDesabilitaCampoProduto(boolean desabilitaCampoProduto) {
		this.desabilitaCampoProduto = desabilitaCampoProduto;
	}

	public boolean isProdutoSIICO() {
		return produtoSIICO;
	}

	public void setProdutoSIICO(boolean produtoSIICO) {
		this.produtoSIICO = produtoSIICO;
	}

	public boolean isDesabilitaNomeProduto() {
		if (campoModalidade.trim().equals("000"))
		{
			return true;
		} else {
			return false;
		}
	}

	public void setDesabilitaNomeProduto(boolean desabilitaNomeProduto) {
		this.desabilitaNomeProduto = desabilitaNomeProduto;
	}

	public String getNomeOperacao() {
		return nomeOperacao;
	}

	public void setNomeOperacao(String nomeOperacao) {
		this.nomeOperacao = nomeOperacao;
	}

	public String getNomeModalidade() {
		return nomeModalidade;
	}

	public void setNomeModalidade(String nomeModalidade) {
		this.nomeModalidade = nomeModalidade;
	}
	
	public String getDescricaoOperacao() {
		return descricaoOperacao;
	}

	public void setDescricaoOperacao(String descricaoOperacao) {
		this.descricaoOperacao = descricaoOperacao;
	}

	public String getCampoDescricaoModalidade() {
		return campoDescricaoModalidade;
	}

	public void setCampoDescricaoModalidade(String campoDescricaoModalidade) {
		this.campoDescricaoModalidade = campoDescricaoModalidade;
	}

	public boolean isModalidadeVazia() {
		return modalidadeVazia;
	}

	public void setModalidadeVazia(boolean modalidadeVazia) {
		this.modalidadeVazia = modalidadeVazia;
	}

	public String getValidaProduto() {
		return validaProduto;
	}

	public void setValidaProduto(String validaProduto) {
		this.validaProduto = validaProduto;
	}

	private static final String MSG_PRODUTO_SIICO_INEXISTENTE = "Operação não existente na base SIICO";
}
