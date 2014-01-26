/**
 * Copyright (c) 2009-2011 Caixa Econ�mica Federal. Todos os direitos
 * reservados.
 * 
 * Caixa Econ�mica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade
 * 
 * Este programa de computador foi desenvolvido sob demanda da CAIXA e est�
 * protegido por leis de direitos autorais e tratados internacionais. As
 * condi��es de c�pia e utiliza��o do todo ou partes dependem de autoriza��o da
 * empresa. C�pias n�o s�o permitidas sem expressa autoriza��o. N�o pode ser
 * comercializado ou utilizado para prop�sitos particulares.
 * 
 * Uso exclusivo da Caixa Econ�mica Federal. A reprodu��o ou distribui��o n�o
 * autorizada deste programa ou de parte dele, resultar� em puni��es civis e
 * criminais e os infratores incorrem em san��es previstas na legisla��o em
 * vigor.
 * 
 * Hist�rico do Subversion:
 * 
 * LastChangedRevision:  
 * LastChangedBy:  
 * LastChangedDate: 
 * 
 * HeadURL: 
 * 
 */
package br.gov.caixa.siiac.view.mb;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IBuildTemplateParecerBO;
import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.bo.IServicoVerificacaoBO;
import br.gov.caixa.siiac.bo.IServicoVerificacaoProdutoBO;
import br.gov.caixa.siiac.bo.ITemplateParecerBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.EnviarEmailsVO;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.TemplateNotificacao;
import br.gov.caixa.siiac.model.domain.TemplateParecer;
import br.gov.caixa.siiac.model.domain.TemplateParecerDestinatarios;
import br.gov.caixa.siiac.model.domain.TemplateParecerDestinatariosId;
import br.gov.caixa.siiac.util.DefinedLanguage;
import br.gov.caixa.siiac.util.FormatUtil;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfWriter;


/**
 * @author GIS Consult
 *
 */
@Service()
@Scope("request")
public class TemplateParecerMB extends AbstractMB 
{
	@Autowired
	private IProdutoBO produtoBO;
	@Autowired
	private ITemplateParecerBO templateParecerBO;
	@Autowired
	private IBuildTemplateParecerBO buildTemplateParecerBO;
	
	private IServicoVerificacaoBO servicoVerificacaoBO;
	
	private IServicoVerificacaoProdutoBO servicoVerificacaoProdutoBO;
	
	private TemplateParecer templateParecer;
	private String idProdutoSelect;
	private List<SelectItem> listProdutos=new ArrayList<SelectItem>();
	private List<SelectItem> listTipoParecer=new ArrayList<SelectItem>();
	private List<SelectItem> listServicoVerificacao=new ArrayList<SelectItem>();
	private List<TemplateParecer> listTemplateParecer=new ArrayList<TemplateParecer>();
	private List<SelectItem> listTags=new ArrayList<SelectItem>();
	private String listAllTagstoString="";
	private String textoHTMLPreenchido="";
	private boolean modoAlteracao;
	
	//Pesquisa Simples
	private String pesquisaString;
	private Boolean pesquisaMostraInativos = false;
	//Pesquisa Avançada
	private String filtroProduto;
	private String codProduto;
	private String codServicoVerificacao;
	private String tipoParecer;
	
	private String filtroCadastro;
	private boolean modalImportaDados = false;
	
	private List<TemplateParecer> listTemplateParecerImportacao = new ArrayList<TemplateParecer>();
	
	
	@Autowired
	public void setServicoVerificacaoBO(IServicoVerificacaoBO servicoVerificacaoBO) {
		this.servicoVerificacaoBO = servicoVerificacaoBO;
	}

	@Autowired
	public void setServicoVerificacaoProdutoBO(IServicoVerificacaoProdutoBO servicoVerificacaoProdutoBO) {
		this.servicoVerificacaoProdutoBO = servicoVerificacaoProdutoBO;
	}
	
	@PostConstruct
	public void init() throws SIIACException
	{
		templateParecer=new TemplateParecer();
		ServicoVerificacaoProduto s=new ServicoVerificacaoProduto();
		templateParecer.setServicoVerificacaoProduto(s);
		
		buildTemplateParecerBO.setDefinedLanguage(new DefinedLanguage());
		
		loadListProdutos(listProdutos);
		loadListTipoParecer(listTipoParecer);
		loadListTags(listTags);
		listAllTagstoString = buildTemplateParecerBO.getListAllTagstoString();
		pesquisaString = "";
		pesquisaMostraInativos  = false;
		
		setFiltro();
		
	}
	
	/**
	 * Pega o Filtro na sessão e seta as variaveis da view<br/>
	 * com seus respectivos valores que estavam guardados<br/>
	 * no filtro da sessão. 
	 * @throws SIIACException 
	 * @see AbstractMB.getFilteriInSession();
	 */
	private void setFiltro() throws SIIACException{
		//Pega o filtro na sessão.
		getFilterInSession(this.getClass());
		
		
		
		
		if(getFilterBase().isModoAvancado()){
			setModoFiltro();
			codProduto = getFilterBase().getString("codProduto");
			tipoParecer = getFilterBase().getString("tipoParecer");
			codServicoVerificacao = getFilterBase().getString("codServicoVerificacao");
			pesquisaMostraInativos = getFilterBase().getBoolean("pesquisaMostraInativos");
			listTemplateParecer = carregaListaTemplateParecerFiltroAvancado();
		}else{
			setModoInicio();
			pesquisaString = getFilterBase().getString("pesquisaString");
			pesquisaMostraInativos = getFilterBase().getBoolean("pesquisaMostraInativos");
			listTemplateParecer = carregaListaTemplateParecerFiltroSimples();
		}
		
		listTemplateParecer = doBuscaRegistros();
		
		
	}
	
	
	/**
	 * @param listTags2
	 */
	private void loadListTags(List<SelectItem> listTags) 
	{
		if (listTags==null)
			return;
		{
			this.listTags.clear();
			List<String> listT=buildTemplateParecerBO.getListTags();
			
			listTags.add(new SelectItem("0","Selecione..."));
			
			for (String s:listT)
			{
				listTags.add(new SelectItem(s,s));
			}
		}		
		
	}

	public String doCancelarClick() throws SIIACException
	{
		templateParecer=new TemplateParecer();
		listServicoVerificacao=new ArrayList<SelectItem>();
		idProdutoSelect="0";
		ServicoVerificacaoProduto s=new ServicoVerificacaoProduto();
		templateParecer.setServicoVerificacaoProduto(s);
		setFiltro();
		return "toTemplateParecer";
	}
	
	public void doLimpar(ActionEvent evt)
	{
		filtroProduto = "";
		codProduto = "";
		pesquisaMostraInativos = false;
		codServicoVerificacao = "";
		tipoParecer = "";
		
		getFilterBase().limparFiltros();
		
	}
	
	public String doVisualizaBotao()
	{
		try {
			
			if (Utilities.notEmpty(templateParecer.getTextoParecer()))
			{
				String caminhaAbsoluto = "src=\"" + getRequest().getRealPath("images") + System.getProperty("file.separator");

				String ret = buildTemplateParecerBO.processShow(templateParecer.getTextoParecer());
				
				ret = ret.replace("src=\"../../images/", caminhaAbsoluto).replace("{<img", "<img").replace("/>}", "/>");
				
				setTextoHTMLPreenchido(ret);
				

				byte[] bs = geraPDF(ret);

				getResponse().setHeader("Pragma", "");
				getResponse().setHeader("Cache-Control", "");
				getResponse().setHeader("Expires", "");
				getResponse().setCharacterEncoding("UTF-8");
				getResponse().setContentType("application/download");
				getResponse().setHeader("Content-disposition",
						"attachment; filename=\"parecer.pdf\"");
				getResponse().getOutputStream().write(bs);

				getFacesContext().responseComplete();
				
			} else {
				warn("O Texto do Template deve estar preenchido.");
			}

			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void doFazNada(ActionEvent evt){}
	
	public String doVisualizaClick()
	{
		templateParecer = templateParecerBO.findById(templateParecer.getNuTemplateParecer());

		try {

			String caminhaAbsoluto = "src=\"" + getRequest().getRealPath("images") + System.getProperty("file.separator");

			
			String ret = buildTemplateParecerBO.processShow(templateParecer.getTextoParecer());
			
			ret = ret.replace("src=\"../../images/", caminhaAbsoluto).replace("{<img", "<img").replace("/>}", "/>");
			
			setTextoHTMLPreenchido(ret);

			byte[] bs = geraPDF(ret);

			getResponse().setHeader("Pragma", "");
			getResponse().setHeader("Cache-Control", "");
			getResponse().setHeader("Expires", "");
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().setContentType("application/download");
			getResponse().setHeader("Content-disposition",
					"attachment; filename=\"parecer.pdf\"");
			getResponse().getOutputStream().write(bs);

			getFacesContext().responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "toTemplateParecer";
	}
	
	public byte[] geraPDF(String html) {
		try {
			// Gerando o PDF
			// Criando um ByteArrayInputStram com o byte[] da string que contém
			// o HTML
			ByteArrayInputStream byteArrayLinputStream = new ByteArrayInputStream(
					html.getBytes());

			// Criando o Reader com o conteúdo HTML
			Reader htmlreader = new BufferedReader(new InputStreamReader(
					byteArrayLinputStream));

			Document pdfDocument = new Document();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(pdfDocument, baos);
			pdfDocument.open();
			StyleSheet styles = new StyleSheet();
			styles.loadTagStyle("body", "font", "Verdana");
			ArrayList arrayElementList = HTMLWorker.parseToList(htmlreader,
					styles);
			for (int i = 0; i < arrayElementList.size(); ++i) {
				Element e = (Element) arrayElementList.get(i);
				pdfDocument.add(e);
			}

			pdfDocument.close();

			byte[] bs = baos.toByteArray();

			return bs;
		} catch (DocumentException e) {
			error(e.getMessage());
		} catch (IOException ex) {
			error(ex.getMessage());
		}

		return null;
	}

	
	public void doSalvar(ActionEvent evt) throws SIIACException
	{
		templateParecerBO.merge(templateParecer);
		doCancelarClick();
	}
	
	public void setIdProdutoSelect(String idProdutoSelect) {
		this.idProdutoSelect = idProdutoSelect;
	}

	public String getIdProdutoSelect() {
		return idProdutoSelect;
	}

	public void setListProdutos(List<SelectItem> listProdutos) {
		this.listProdutos = listProdutos;
	}

	private void loadListTipoParecer(List<SelectItem> listTipoParecer)
	{
		listTipoParecer.add(new SelectItem(Boolean.TRUE,"Conforme"));
		listTipoParecer.add(new SelectItem(Boolean.FALSE,"Inconforme"));		
	}
	

	private void loadListTemplateParecer(List<TemplateParecer> listTemplateParecer)
	{
		if (listTemplateParecer==null)
			return;
		{
			this.listTemplateParecer.clear();
			this.listTemplateParecer=templateParecerBO.findAll();
		}
	}
	
	private void loadListProdutos(List<SelectItem> listProdutos)
	{
		if (listProdutos==null)
			return;
		{
			this.listProdutos.clear();
			List<Produto> listP=produtoBO.getListProduto();
			
			listProdutos.add(new SelectItem("0","Selecione..."));
			
			for (Produto p:listP)
			{
				listProdutos.add(new SelectItem(p.getCoProduto(),p.getCodigoFormatado()+" "+ p.getNoProduto()));
			}
		}
	}
	
	/**
	 * carregaListTemplateParecerFiltroSimples
	 * @param utilizaParametros 
	 * @return List<TemplateParecer>
	 * @throws SIIACException
	 */
	public List<TemplateParecer> carregaListaTemplateParecerFiltroSimples() throws SIIACException {		
		return templateParecerBO.getListTemplateParecerFiltroSimples(getFilterBase());
	}
	
	/**
	 * carregaListTemplateParecerFiltroAvancado
	 * @param utilizaParametros 
	 * @return List<TemplateParecer>
	 * @throws SIIACException
	 */
	public List<TemplateParecer> carregaListaTemplateParecerFiltroAvancado() throws SIIACException {
		return templateParecerBO.getListTemplateParecerFiltroAvancado(getFilterBase());
	}
	
	public void changeValueChangeProduto(ActionEvent evt)
	{
		loadListServicoVerificacao(listServicoVerificacao,idProdutoSelect);
	}
	
	private void loadListServicoVerificacao(List<SelectItem> listServicoVerificacao, String idProdutoSelect)
	{
		if (listServicoVerificacao==null || idProdutoSelect==null || idProdutoSelect.equals(""))
			return;
		{
			this.listServicoVerificacao.clear();
			
			Produto produto=produtoBO.findEagerById(idProdutoSelect);
			
			List<ServicoVerificacaoProduto> list=produto.getServicoVerificacaoProdutoList();
			
			listServicoVerificacao.add(new SelectItem("0","Selecione..."));
			
			for (ServicoVerificacaoProduto s:list)
			{
				listServicoVerificacao.add(new SelectItem(s.getNuServicoVerificacaoProduto(),s.getServicoVerificacao().getNoServicoVerificacao()));
			}
		}	
	}
	
	public void setListTipoParecer(List<SelectItem> listTipoParecer) {
		this.listTipoParecer = listTipoParecer;
	}

	public List<SelectItem> getListTipoParecer() {
		return listTipoParecer;
	}

	public void setProdutoBO(IProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}

	public IProdutoBO getProdutoBO() {
		return produtoBO;
	}

	public void setTemplateParecer(TemplateParecer templateParecer) {
		this.templateParecer = templateParecer;
	}

	public TemplateParecer getTemplateParecer() {
		return templateParecer;
	}

	public void setListServicoVerificacao(List<SelectItem> listServicoVerificacao) {
		this.listServicoVerificacao = listServicoVerificacao;
	}

	public void setTemplateParecerBO(ITemplateParecerBO templateParecerBO) {
		this.templateParecerBO = templateParecerBO;
	}

	public ITemplateParecerBO getTemplateParecerBO() {
		return templateParecerBO;
	}

	public void setListTemplateParecer(List<TemplateParecer> listTemplateParecer) {
		this.listTemplateParecer = listTemplateParecer;
	}

	public List<TemplateParecer> getListTemplateParecer()
	{
		return listTemplateParecer;
	}

	public void setBuildTemplateParecerBO(IBuildTemplateParecerBO buildTemplateParecerBO) {
		this.buildTemplateParecerBO = buildTemplateParecerBO;
	}

	public IBuildTemplateParecerBO getBuildTemplateParecerBO() {
		return buildTemplateParecerBO;
	}

	public void setListTags(List<SelectItem> listTags) {
		this.listTags = listTags;
	}

	public List<SelectItem> getListTags() {
		return listTags;
	}


	public void setListAllTagstoString(String listAllTagstoString) {
		this.listAllTagstoString = listAllTagstoString;
	}


	public String getListAllTagstoString() {
		return listAllTagstoString;
	}


	public void setTextoHTMLPreenchido(String textoHTMLPreenchido) {
		this.textoHTMLPreenchido = textoHTMLPreenchido;
	}


	public String getTextoHTMLPreenchido() {
		return textoHTMLPreenchido;
	}


	public String getPesquisaString() {
		return pesquisaString;
	}


	public void setPesquisaString(String pesquisaString) {
		this.pesquisaString = pesquisaString;
	}

	public Boolean getPesquisaMostraInativos() {
		return pesquisaMostraInativos;
	}

	public void setPesquisaMostraInativos(Boolean pesquisaMostraInativos) {
		this.pesquisaMostraInativos = pesquisaMostraInativos;
	}
	
	/**
	 * Verifica se deve ser mostrado o painel de consulta simples
	 * @return
	 */
	public boolean getShowConsultarSimples() {
		return isModoInicio();
	}
	
	/**
	 * Realiza a consulta dos dados.
	 */
	public void doConsultarClick(ActionEvent evt) {
		
		List<TemplateParecer> busca = doBuscaRegistros();
		
		if (busca.isEmpty()) {
			warn(MSGS, MN016);
		}
		
		limparCampos();
		
		listTemplateParecer = busca;		
	}
	
	/**
	 * Método responsável por realizar a busca dos registros
	 * @return
	 */
	public List<TemplateParecer> doBuscaRegistros()
	{
		List<TemplateParecer> busca = new ArrayList<TemplateParecer>();
		
		//Limpa o filtro
		getFilterBase().limparFiltros();
		
		try {
			if (isModoInicio()) {
								
				//Modo simples
				getFilterBase().setModoSimples();
				
				getFilterBase().addToFilter("pesquisaString", pesquisaString);
				getFilterBase().addToFilter("pesquisaMostraInativos", pesquisaMostraInativos);
				
				//Define que o filtro base possui um filtro de template notificacao.
				getFilterBase().addToFilter("filtroTemplateParecer", true);
				
				//Coloca o filtro com os valores adicionados na sessão.
				putFilterBase(this.getClass());
				
				busca = carregaListaTemplateParecerFiltroSimples();
				
			} else {
				
				//Modo avançado
				getFilterBase().setModoAvancado();

				getFilterBase().addToFilter("codProduto", codProduto);
				getFilterBase().addToFilter("tipoParecer", tipoParecer);
				getFilterBase().addToFilter("codServicoVerificacao", codServicoVerificacao);
				getFilterBase().addToFilter("pesquisaMostraInativos", pesquisaMostraInativos);
						
				//Define que o filtro base possui um filtro de template notificacao.
				getFilterBase().addToFilter("filtroTemplateParecer", true);
				
				//Coloca o filtro com os valores adicionados na sessão.
				putFilterBase(this.getClass());
				
				busca = carregaListaTemplateParecerFiltroAvancado();
								
			}
			
			
		} catch (SIIACException ex) {
			LogCEF.error(ex);
		}
		
		return busca;
	}
	
	/**
	 * Preenche o combo de produtos.
	 * 
	 * @return lista de itens de seleção de produtos
	 */
	public List<SelectItem> getListProdutos() {
		
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<Produto> produtos;

		produtos = produtoBO.getListProduto();
	
		for (Produto p : produtos) {
			
			if (isModoCadastro())
			{
				// Valida se o valor digitado no filtro é e se ele está contido na concatenação.
				if(Utilities.notEmpty(filtroCadastro) && 
						!(p.getOperacaoFormatada() + "-" + p.getModalidadeFormatada() + " " + p.getNoProduto()).contains(filtroCadastro)){
					continue;
				}
				list.add(new SelectItem(p.getCoProduto(), p.getOperacaoFormatada()
						+ "-" + p.getModalidadeFormatada() + " " + p.getNoProduto()));
			} else {
				// Valida se o valor digitado no filtro é e se ele está contido na concatenação.
				if(Utilities.notEmpty(filtroProduto) && 
						!(p.getOperacaoFormatada() + "-" + p.getModalidadeFormatada() + " " + p.getNoProduto()).contains(filtroProduto)){
					continue;
				}
				list.add(new SelectItem(p.getCoProduto(), p.getOperacaoFormatada()
						+ "-" + p.getModalidadeFormatada() + " " + p.getNoProduto()));
			}
		}
		return list;
	}
	
	/**
	 * Preenche o combo de serviços de verificacao.
	 * 
	 * @return lista de itens de seleção de serviços de verificação
	 */
	public List<SelectItem> getListServicoVerificacao() {
		
		List<ServicoVerificacao> servicos;
		List<SelectItem> list = new ArrayList<SelectItem>();

		if (codProduto != null && !codProduto.equals("")) {
			Produto produto = produtoBO.findById(codProduto);
			servicos = servicoVerificacaoBO.getAllServicoProduto(produto);

			for (ServicoVerificacao sv : servicos) {
				list.add(new SelectItem(sv.getNuServicoVerificacao(), sv.getNoServicoVerificacao()));
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
	 * Inicializa os valores de checklistVO
	 * 
	 * @return outcome vazio para atualizar a tela
	 * @throws SIIACException 
	 */
	public void doCadastrar(ActionEvent evt) throws SIIACException {
		limparCamposTemplateParecer();
		setModoCadastro();
		modoAlteracao = false;
		populateListEnviar();
		populateListEnviarComCopia();
		listTemplateParecer = carregaListaTemplateParecerFiltroSimples();
	}
	

	/**
	 * Clique do botao 'Cancelar'/'Voltar'.
	 * @param evt
	 * @throws SIIACException 
	 */
	public void doCancelar() throws SIIACException {
		
		limparCamposTemplateParecer();
		getFilterInSession(this.getClass());
		if(getFilterBase().isModoAvancado()){
			setFiltro();
			codProduto = getFilterBase().getString("codProduto");
			tipoParecer = getFilterBase().getString("tipoParecer");
			codServicoVerificacao = getFilterBase().getString("codServicoVerificacao");
			pesquisaMostraInativos = getFilterBase().getBoolean("pesquisaMostraInativos");
			setModoFiltro();
		}
		else{
			setFiltro();
			pesquisaString = getFilterBase().getString("pesquisaString");
			pesquisaMostraInativos = getFilterBase().getBoolean("pesquisaMostraInativos");
			listTemplateParecer = carregaListaTemplateParecerFiltroSimples();
			setModoInicio();
		}
		
		warn(MSGS, MN003);
		
	}
	
	/**
	 * doInativaClick
	 * @param evt
	 */
	public void doInativaClick() {
		try {
			templateParecerBO.ativarDesativarTemplate(templateParecer.getNuTemplateParecer(), false);
			info(MSGS, MN011);
			
			setFiltro();
		} catch (SIIACException e) {
			error(MSGS, MN002 + e.getMessage());
		}
	}
	
	/**
	 * doAtivaClick
	 * @param evt
	 */
	public void doAtivaClick() {
		try {
			templateParecerBO.ativarDesativarTemplate(templateParecer.getNuTemplateParecer(), true);
			info(MSGS, MN012);
			listTemplateParecer = carregaListaTemplateParecerFiltroSimples();
		} catch (SIIACException e) {
			error(MSGS, MN002 + e.getMessage());
		}
	}
	
	/**
	 * Grava (insere/altera) um template parecer
	 * @param evt
	 * @return
	 * @throws SIIACException 
	 */
	public void doGravar(ActionEvent evt) throws SIIACException {
		
		if (doValida()) {
			
			try {
				
				boolean aptoCadastro = false;
				
				if (isModoCadastro())
				{
					//Preenchendo Template Parecer
					Produto produto = new Produto();
					produto = produtoBO.findById(codProduto);
					
					ServicoVerificacao servico = new ServicoVerificacao();
					servico = servicoVerificacaoBO.get(Integer.parseInt(codServicoVerificacao));
					
					templateParecer.setIcTipoParecer(Boolean.valueOf(tipoParecer));
					templateParecer.setTextoParecer(FormatUtil.parseHtmlCharacters(templateParecer.getTextoParecer()));
					
					templateParecer.setServicoVerificacaoProduto(servicoVerificacaoProdutoBO.get(produto, servico));
										
					
					if (templateParecer.isIcAtivo() == null)
						templateParecer.setIcAtivo(true);
					
					
					//Verificando se o Template Parecer utilizando 
					//os campos 'Tipo do Template', 'Produto' e 'Serviço de Verificação'
					aptoCadastro = templateParecerBO.exist(templateParecer);					
					
					if (aptoCadastro) {
						templateParecerBO.merge(templateParecer);

					} else
					{
						error(MSGS, MN015);
						return;
					}
					
				} else {
					templateParecerBO.merge(templateParecer);
				}
				
				
				templateParecer.getListaTemplateParecerDestinatarios().clear();
				for(EnviarEmailsVO enviarVo : listEnviar){
					if(enviarVo.getSelected()){
						TemplateParecerDestinatariosId tpdId = null;
						if(templateParecer.getNuTemplateParecer() != null){
							tpdId = new TemplateParecerDestinatariosId();
							
							tpdId.setCoDestinatario(enviarVo.getId());
							tpdId.setNuTemplateParecer(templateParecer.getNuTemplateParecer());
						}

						
						TemplateParecerDestinatarios tpd = new TemplateParecerDestinatarios(tpdId, TemplateParecerDestinatarios.EMAIL_TIPO_DESTINATARIO_TO, templateParecer);
						
						templateParecer.getListaTemplateParecerDestinatarios().add(tpd);
					}
				}
				for(EnviarEmailsVO enviarComCopiaVo : listEnviarComCopia){
					if(enviarComCopiaVo.getSelected()){
						TemplateParecerDestinatariosId tpdId = null;
						if(templateParecer.getNuTemplateParecer() != null){
							tpdId = new TemplateParecerDestinatariosId();
							
							tpdId.setCoDestinatario(enviarComCopiaVo.getId());
							tpdId.setNuTemplateParecer(templateParecer.getNuTemplateParecer());
						}

						
						TemplateParecerDestinatarios tpd = new TemplateParecerDestinatarios(tpdId, TemplateParecerDestinatarios.EMAIL_TIPO_DESTINATARIO_CC, templateParecer);
						
						templateParecer.getListaTemplateParecerDestinatarios().add(tpd);
					}
				}
				templateParecerBO.merge(templateParecer);
				
				listTemplateParecer = carregaListaTemplateParecerFiltroSimples();
				if (isModoCadastro()) {
					info(MSGS, MN007);
				} else if (isModoAltera()) {
					info(MSGS, MN008);
				}
				
			} catch (Exception e) {
				error(getMessage(MSGS, MN002) + " " + e.getMessage());
				e.printStackTrace();
			}
			setFiltro();
		}
	}
	
	/**
	 * Clique no botao 'Alterar' na pagina de visualizacao de um Template Parecer.
	 * @param evt
	 */
	public void doAtualizar() {
		
		templateParecer = templateParecerBO.findById(templateParecer.getNuTemplateParecer());
		
		if (templateParecer.isIcAtivo())
		{
			codProduto = templateParecer.getServicoVerificacaoProduto().getProduto().getCoProduto();
			codServicoVerificacao = templateParecer.getServicoVerificacaoProduto().getServicoVerificacao().getNuServicoVerificacao() + "";
			tipoParecer = templateParecer.getIcTipoParecer().toString();
			
			destinatariosEnviarId = this.templateParecerBO.getDestinatariosEnviarEmailId(templateParecer.getNuTemplateParecer());
			destinatariosEnviarComCopiaId = this.templateParecerBO.getDestinatariosEnviarEmailComCopiaId(templateParecer.getNuTemplateParecer());
			
			populateListEnviar();
			populateListEnviarComCopia();
			
			modoAlteracao = true;
			
			
			setModoAltera();
		} else {
			error(getMessage(MSGS, MN013));
		}
		
	}
	
	/**
	 * Valida a insercao/alteracao de um apontamento
	 */
	public Boolean doValida() {
		
		if (codProduto == null || Utilities.empty(codProduto)) {
			error(getMessage(MSGS, MN002));
			warn( "Campo PRODUTO Obrigatório.");
			return false;
		}
		
		if (tipoParecer == null || Utilities.empty(tipoParecer)) {
			error(getMessage(MSGS, MN002));
			warn( "Campo TIPO PARECER Obrigatório.");
			return false;
		}
		
		if (codServicoVerificacao == null || Utilities.empty(codServicoVerificacao)) {
			error(getMessage(MSGS, MN002));
			warn( "Campo SERVIÇO DE VERIFICAÇÃO Obrigatório.");
			return false;
		}
		
		if (templateParecer.getNoAssuntoParecer() == null || Utilities.empty(templateParecer.getNoAssuntoParecer())) {
			error(getMessage(MSGS, MN002));
			warn( "Campo ASSUNTO Obrigatório.");
			return false;
		}
		
		if (templateParecer.getTextoParecer() == null || Utilities.empty(templateParecer.getTextoParecer())) {
			error(getMessage(MSGS, MN002));
			warn( "Campo TEXTO Obrigatório.");
			return false;
		}
		
		if(listEnviar != null && ! listEnviar.isEmpty() && listEnviarComCopia != null && ! listEnviarComCopia.isEmpty() ){
			for(EnviarEmailsVO voEnviar : listEnviar) {
				if(voEnviar.getSelected()){
					for(EnviarEmailsVO voEnviarComCopia : listEnviarComCopia){
						if(voEnviarComCopia.getSelected() && voEnviar.getId().equals(voEnviarComCopia.getId())){
							error(getMessage(MSGS, MN002));
							warn( "O e-mail \"" + voEnviar.getName() + "\" está selecionado nas duas listas.");
							return false;
						}
					}
				}
			}
			
		}
		
		
		return true;
	}
	
	public void doMostraImportarDados()
	{
		modalImportaDados = true;
		listTemplateParecerImportacao.clear();
		listTemplateParecerImportacao = templateParecerBO.findAll();
	}
	
	public void doCancelarImportaDados(ActionEvent evt)
	{
		templateParecer.setNuTemplateParecer(0);
		modalImportaDados = false;
	}
	
	public String doCarregar()
	{
		if (Utilities.notEmpty(templateParecer.getNuTemplateParecer()) && templateParecer.getNuTemplateParecer() > 0)
		{
			TemplateParecer aux = templateParecerBO.findById(templateParecer.getNuTemplateParecer());
			
			templateParecer = new TemplateParecer();
			templateParecer.setIcAtivo(true);
			templateParecer.setNoAssuntoParecer(aux.getNoAssuntoParecer());
			templateParecer.setNoReferenciaParecer(aux.getNoReferenciaParecer());
			templateParecer.setTextoParecer(aux.getTextoParecer());
			tipoParecer = aux.getIcTipoParecer().toString();
			
			modalImportaDados = false;
			
			return "toTemplateParecer";
		} else {
			error("Selecione um Template para importar.");
			modalImportaDados = true;
			return "";
		}
		
	}
	
	
	/**
	 * getShowButtonDesativar
	 * @return
	 */
	public boolean getShowButtonDesativar() {
		return !getPesquisaMostraInativos();
	}
	
	/**
	 * getShowButtonAtivar
	 * @return
	 */
	public boolean getShowButtonAtivar() {
		return getPesquisaMostraInativos();
	}
	
	public void limparCamposTemplateParecer()
	{
		templateParecer = new TemplateParecer();
		filtroCadastro = "";
		codProduto = "";
		codServicoVerificacao = "";
		tipoParecer = null;
	}
	/**
	 * Verifica se deve ser mostrado o painel de consulta avançado
	 * @return
	 */
	public boolean getShowConsultarAvancado() {
		return isModoFiltro();
	}
	
	public boolean isShowNovo() {
		return isModoCadastro() || isModoAltera();
	}
	
	public void doConsultarSimples(ActionEvent evt) {
		setModoInicio();
	}
	
	public boolean isShowFormAlterar() {
		return isModoAltera() || isModoVisualiza();
	}
	
	public boolean isShowDataTable() {
		return isModoInicio() || isModoFiltro();
	}


	public String getFiltroProduto() {
		return filtroProduto;
	}


	public void setFiltroProduto(String filtroProduto) {
		this.filtroProduto = filtroProduto;
	}


	public String getCodProduto() {
		return codProduto;
	}


	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}

	public void doConsultarAvancado(ActionEvent evt) {
		getFilterInSession(this.getClass());
		codProduto = getFilterBase().getString("codProduto");
		tipoParecer = getFilterBase().getString("tipoParecer");
		codServicoVerificacao = getFilterBase().getString("codServicoVerificacao");
		pesquisaMostraInativos = getFilterBase().getBoolean("pesquisaMostraInativos");
		setModoFiltro();
	}
	
	private void limparCampos(){
		
		if(getFilterBase().isModoAvancado()){
			pesquisaString = "";
			pesquisaMostraInativos = false;
			
			
		}else{
			codProduto = "";
			tipoParecer = "";
			codServicoVerificacao = "";
			pesquisaMostraInativos = false;
		}
	}


	public String getCodServicoVerificacao() {
		return codServicoVerificacao;
	}


	public void setCodServicoVerificacao(String codServicoVerificacao) {
		this.codServicoVerificacao = codServicoVerificacao;
	}

	public String getTipoParecer() {
		return tipoParecer;
	}

	public void setTipoParecer(String tipoParecer) {
		this.tipoParecer = tipoParecer;
	}

	public String getFiltroCadastro() {

		return filtroCadastro;
	}

	public void setFiltroCadastro(String filtroCadastro) {

		this.filtroCadastro = filtroCadastro;
	}

	public boolean isModoAlteracao() {
		return modoAlteracao;
	}

	public void setModoAlteracao(boolean modoAlteracao) {
		this.modoAlteracao = modoAlteracao;
	}

	public boolean isModalImportaDados() {
		return modalImportaDados;
	}

	public void setModalImportaDados(boolean modalImportaDados) {
		this.modalImportaDados = modalImportaDados;
	}

	public List<TemplateParecer> getListTemplateParecerImportacao() {
		return listTemplateParecerImportacao;
	}

	public void setListTemplateParecerImportacao(
			List<TemplateParecer> listTemplateParecerImportacao) {
		this.listTemplateParecerImportacao = listTemplateParecerImportacao;
	}

	public List<EnviarEmailsVO> getListEnviar() {
		return listEnviar;
	}
	
	public List<EnviarEmailsVO> getListEnviarComCopia() {
		return this.listEnviarComCopia;
	}
	
//	public void doSelectEmailToEnviar(ActionEvent evt) {
//		SelectItem si = (SelectItem) getExternalContext().getRequestMap().get("rowEnviar");
//		
//		if(idsToEnviar == null) idsToEnviar = new ArrayList<Integer>();
//		
//		if(idsToEnviar.contains((Integer) si.getValue())){
//			idsToEnviar.remove((Integer) si.getValue());
//		}else{
//			idsToEnviar.add((Integer) si.getValue());
//		}
//	}
	
//	public void doSelectEmailToEnviarComCopia(ActionEvent evt) {
//		SelectItem si = (SelectItem) getExternalContext().getRequestMap().get("rowEnviarComCopia");
//		
//		if(idsToEnviarComCopia == null) idsToEnviarComCopia = new ArrayList<Integer>();
//		
//		if(idsToEnviarComCopia.contains((Integer) si.getValue())){
//			idsToEnviarComCopia.remove((Integer) si.getValue());
//		}else{
//			idsToEnviarComCopia.add((Integer) si.getValue());
//		}
//	}
	
	List<Integer> destinatariosEnviarId = new ArrayList<Integer>();
	List<Integer> destinatariosEnviarComCopiaId = new ArrayList<Integer>();
	
//	List<Integer> idsToEnviar = new ArrayList<Integer>();
//	List<Integer> idsToEnviarComCopia = new ArrayList<Integer>();
	
	private List<EnviarEmailsVO> listEnviar;
	private List<EnviarEmailsVO> listEnviarComCopia;
	
	
	private void populateListEnviar() {
		listEnviar = new ArrayList<EnviarEmailsVO>();
		listEnviar.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_CONVENIADO, 
						TemplateParecerDestinatarios.EMAIL_LABEL_CONVENIADO, 
						destinatariosEnviarId.contains(TemplateParecerDestinatarios.EMAIL_ID_CONVENIADO)
					)
			 );
		listEnviar.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_EMPREGADO_RESPONSAVEL_PARECER, 
						TemplateParecerDestinatarios.EMAIL_LABEL_EMPREGADO_RESPONSAVEL_PARECER, 
						destinatariosEnviarId.contains(TemplateParecerDestinatarios.EMAIL_ID_EMPREGADO_RESPONSAVEL_PARECER)
				)
		);
		listEnviar.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_GERENTE_UNIDADE_RESPONSAVEL_CONTRATO, 
						TemplateParecerDestinatarios.EMAIL_LABEL_GERENTE_UNIDADE_RESPONSAVEL_CONTRATO, 
						destinatariosEnviarId.contains(TemplateParecerDestinatarios.EMAIL_ID_GERENTE_UNIDADE_RESPONSAVEL_CONTRATO)
				)
		);
		listEnviar.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_GERENTE_ASSINA_PARECER, 
						TemplateParecerDestinatarios.EMAIL_LABEL_GERENTE_ASSINA_PARECER, 
						destinatariosEnviarId.contains(TemplateParecerDestinatarios.EMAIL_ID_GERENTE_ASSINA_PARECER)
				)
		);
		
		listEnviar.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_ALIENACAO_BENS_MOVEIS_IMOVEIS_UNIDADE_RESPONSAVEL_CONTRATO, 
						TemplateParecerDestinatarios.EMAIL_LABEL_UNIDADE_ALIENACAO_BENS_MOVEIS_IMOVEIS_UNIDADE_RESPONSAVEL_CONTRATO, 
						destinatariosEnviarId.contains(TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_ALIENACAO_BENS_MOVEIS_IMOVEIS_UNIDADE_RESPONSAVEL_CONTRATO)
				)
		);
		listEnviar.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_CONFORMIDADE_UNIDADE_RESPONSAVEL_CONTRATO, 
						TemplateParecerDestinatarios.EMAIL_LABEL_UNIDADE_CONFORMIDADE_UNIDADE_RESPONSAVEL_CONTRATO, 
						destinatariosEnviarId.contains(TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_CONFORMIDADE_UNIDADE_RESPONSAVEL_CONTRATO)
				)
		);
		listEnviar.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_EMPREGADO_RESPONSAVEL_PARECER, 
						TemplateParecerDestinatarios.EMAIL_LABEL_UNIDADE_EMPREGADO_RESPONSAVEL_PARECER, 
						destinatariosEnviarId.contains(TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_EMPREGADO_RESPONSAVEL_PARECER)
				)
		);
		listEnviar.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_RESPONSAVEL_CONTRATO, 
						TemplateParecerDestinatarios.EMAIL_LABEL_UNIDADE_RESPONSAVEL_CONTRATO, 
						destinatariosEnviarId.contains(TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_RESPONSAVEL_CONTRATO)
				)
		);
		listEnviar.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_SUPERIOR_HIERARQUICA_UNIDADE_RESPONSAVEL_CONTRATO, 
						TemplateParecerDestinatarios.EMAIL_LABEL_UNIDADE_SUPERIOR_HIERARQUICA_UNIDADE_RESPONSAVEL_CONTRATO, 
						destinatariosEnviarId.contains(TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_SUPERIOR_HIERARQUICA_UNIDADE_RESPONSAVEL_CONTRATO)
				)
		);
		listEnviar.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_VENDEDOR, 
						TemplateParecerDestinatarios.EMAIL_LABEL_VENDEDOR, 
						destinatariosEnviarId.contains(TemplateParecerDestinatarios.EMAIL_ID_VENDEDOR)
				)
		);

		
	}
	
	private void populateListEnviarComCopia() {
		listEnviarComCopia = new ArrayList<EnviarEmailsVO>(); 
		listEnviarComCopia.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_CONVENIADO, 
						TemplateParecerDestinatarios.EMAIL_LABEL_CONVENIADO, 
						destinatariosEnviarComCopiaId.contains(TemplateParecerDestinatarios.EMAIL_ID_CONVENIADO)
					)
			 );
		listEnviarComCopia.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_EMPREGADO_RESPONSAVEL_PARECER, 
						TemplateParecerDestinatarios.EMAIL_LABEL_EMPREGADO_RESPONSAVEL_PARECER, 
						destinatariosEnviarComCopiaId.contains(TemplateParecerDestinatarios.EMAIL_ID_EMPREGADO_RESPONSAVEL_PARECER)
				)
		);
		listEnviarComCopia.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_GERENTE_UNIDADE_RESPONSAVEL_CONTRATO, 
						TemplateParecerDestinatarios.EMAIL_LABEL_GERENTE_UNIDADE_RESPONSAVEL_CONTRATO, 
						destinatariosEnviarComCopiaId.contains(TemplateParecerDestinatarios.EMAIL_ID_GERENTE_UNIDADE_RESPONSAVEL_CONTRATO)
				)
		);
		listEnviarComCopia.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_GERENTE_ASSINA_PARECER, 
						TemplateParecerDestinatarios.EMAIL_LABEL_GERENTE_ASSINA_PARECER, 
						destinatariosEnviarComCopiaId.contains(TemplateParecerDestinatarios.EMAIL_ID_GERENTE_ASSINA_PARECER)
				)
		);
		
		listEnviarComCopia.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_ALIENACAO_BENS_MOVEIS_IMOVEIS_UNIDADE_RESPONSAVEL_CONTRATO, 
						TemplateParecerDestinatarios.EMAIL_LABEL_UNIDADE_ALIENACAO_BENS_MOVEIS_IMOVEIS_UNIDADE_RESPONSAVEL_CONTRATO, 
						destinatariosEnviarComCopiaId.contains(TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_ALIENACAO_BENS_MOVEIS_IMOVEIS_UNIDADE_RESPONSAVEL_CONTRATO)
				)
		);
		listEnviarComCopia.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_CONFORMIDADE_UNIDADE_RESPONSAVEL_CONTRATO, 
						TemplateParecerDestinatarios.EMAIL_LABEL_UNIDADE_CONFORMIDADE_UNIDADE_RESPONSAVEL_CONTRATO, 
						destinatariosEnviarComCopiaId.contains(TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_CONFORMIDADE_UNIDADE_RESPONSAVEL_CONTRATO)
				)
		);
		listEnviarComCopia.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_EMPREGADO_RESPONSAVEL_PARECER, 
						TemplateParecerDestinatarios.EMAIL_LABEL_UNIDADE_EMPREGADO_RESPONSAVEL_PARECER, 
						destinatariosEnviarComCopiaId.contains(TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_EMPREGADO_RESPONSAVEL_PARECER)
				)
		);
		listEnviarComCopia.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_RESPONSAVEL_CONTRATO, 
						TemplateParecerDestinatarios.EMAIL_LABEL_UNIDADE_RESPONSAVEL_CONTRATO, 
						destinatariosEnviarComCopiaId.contains(TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_RESPONSAVEL_CONTRATO)
				)
		);
		listEnviarComCopia.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_SUPERIOR_HIERARQUICA_UNIDADE_RESPONSAVEL_CONTRATO, 
						TemplateParecerDestinatarios.EMAIL_LABEL_UNIDADE_SUPERIOR_HIERARQUICA_UNIDADE_RESPONSAVEL_CONTRATO, 
						destinatariosEnviarComCopiaId.contains(TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_SUPERIOR_HIERARQUICA_UNIDADE_RESPONSAVEL_CONTRATO)
				)
		);
		listEnviarComCopia.add(
				new EnviarEmailsVO(
						TemplateParecerDestinatarios.EMAIL_ID_VENDEDOR, 
						TemplateParecerDestinatarios.EMAIL_LABEL_VENDEDOR, 
						destinatariosEnviarComCopiaId.contains(TemplateParecerDestinatarios.EMAIL_ID_VENDEDOR)
				)
		);
		
	}
	
	public Integer getCountEnviar() {
		Integer i = 0;
		for(EnviarEmailsVO vo : listEnviar){
			if(vo.getSelected()){
				i++;
			}
		}
		return i;
	}
	
	public Integer getCountEnviarComCopia() {
		Integer i = 0;
		for(EnviarEmailsVO vo : listEnviarComCopia){
			if(vo.getSelected()){
				i++;
			}
		}
		return i;
	}

}