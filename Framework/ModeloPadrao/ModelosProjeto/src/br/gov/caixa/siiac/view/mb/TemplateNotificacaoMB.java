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
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IBuildTemplateNotificacaoBO;
import br.gov.caixa.siiac.bo.ITemplateNotificacaoBO;
import br.gov.caixa.siiac.bo.ITipoUnidadeBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.TemplateNotificacaoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.TemplateNotificacao;
import br.gov.caixa.siiac.model.domain.TipoUnidade;
import br.gov.caixa.siiac.util.DefinedLanguageNotificacao;
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
public class TemplateNotificacaoMB extends AbstractMB 
{
	private static String MENSAGEM_VALIDACAO_TEMPLATE = "";
		
	@Autowired
	private IBuildTemplateNotificacaoBO buildTemplateNotificacaoBO;
	
	@Autowired
	private ITemplateNotificacaoBO templateNotificacaoBO;	
	
	@Autowired
	private ITipoUnidadeBO tipoUnidadeBO;
	
	private TemplateNotificacao templateNotificacao;
	private List<SelectItem> listTipoNotificacao = new ArrayList<SelectItem>();
	private List<SelectItem> listTipoUnidade = new ArrayList<SelectItem>();
	private List<TemplateNotificacao> listTemplateNotificacao = new ArrayList<TemplateNotificacao>();
	private List<TemplateNotificacao> listTemplateNotificacaoImportacao = new ArrayList<TemplateNotificacao>();
	private List<SelectItem> listTags = new ArrayList<SelectItem>();
	private List<TemplateNotificacaoVO> listTemplateNotificacaoVO = new ArrayList<TemplateNotificacaoVO>();
	
	//Auxiliares
	private String listAllTagstoString="";
	private String textoHTMLPreenchido="";
	private boolean modoAlteracao;
	
	//Pesquisa Simples
	private String pesquisaSimples;
	
	//Pesquisa Avançada
	private String pesquisaAssunto;
	private String pesquisaCodTipoUnidade;
	private String pesquisaCodTipoNotificacao;
	
	private String assunto;
	private String codTipoUnidade;
	private String codTipoNotificacao;
	private String mensagemErro = "";
	private String textoNotificacao;
	
	private boolean modalImportaDados = false;
	private boolean modalReplicaDados = false;
	
	private boolean replicarDados = false;
	private boolean valido;
	
	
	//##############################################################################
	//Métodos
	
	@PostConstruct
	public void init() throws SIIACException
	{
		templateNotificacao = new TemplateNotificacao();
		buildTemplateNotificacaoBO.setDefinedLanguageNotificacao(new DefinedLanguageNotificacao());
		loadListTipoUnidade(listTipoUnidade);
		loadListTipoNotificacao(listTipoNotificacao);
		loadListTags(listTags);
		listAllTagstoString = buildTemplateNotificacaoBO.getListAllTagstoString();
		textoNotificacao = "";
		
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
		
		pesquisaCodTipoUnidade = getFilterBase().getString("pesquisaCodTipoUnidade");
		pesquisaCodTipoNotificacao = getFilterBase().getString("pesquisaCodTipoNotificacao");
		pesquisaAssunto = getFilterBase().getString("pesquisaAssunto");
		pesquisaSimples = getFilterBase().getString("pesquisaSimples");
		
		if (getFilterBase().isModoAvancado())
		{
			setModoFiltro();
		} else {
			setModoInicio();
		}
		
		listTemplateNotificacao = doBuscaRegistros(); 
	}
	
	/**
	 * @param listTags2
	 */
	private void loadListTags(List<SelectItem> listTags) {
		if (listTags == null)
			return;
		{
			this.listTags.clear();
			List<String> listT = buildTemplateNotificacaoBO.getListTags();

			listTags.add(new SelectItem("0", "Selecione..."));

			for (String s : listT) {
				listTags.add(new SelectItem(s, s));
			}
		}

	}
	
	private void loadListTipoUnidade(List<SelectItem> listTipoUnidade) {
		if (listTipoUnidade == null)
			return;
		{
			this.listTipoUnidade.clear();
			List<TipoUnidade> listT = tipoUnidadeBO.findAll();

			listTipoUnidade.add(new SelectItem("", "Selecione..."));

			for (TipoUnidade t : listT) {
				listTipoUnidade.add(new SelectItem(t.getNuTpUnidadeU21(), t.getDeTipoUnidade()));
			}
		}
	}
	
	private void loadListTipoNotificacao(List<SelectItem> listTipoNotificacao) {
		if (listTipoNotificacao == null)
			return;
		{
			this.listTipoNotificacao.clear();

			listTipoNotificacao.add(new SelectItem("", "Selecione..."));

			listTipoNotificacao.add(new SelectItem(TIPO_NOTIFICACAO_ID_ALERTA_PRAZO, TIPO_NOTIFICACAO_LABEL_ALERTA_PRAZO));
			listTipoNotificacao.add(new SelectItem(TIPO_NOTIFICACAO_ID_AVISO_ABERTURA, TIPO_NOTIFICACAO_LABEL_AVISO_ABERTURA));
			listTipoNotificacao.add(new SelectItem(TIPO_NOTIFICACAO_ID_ENVIO_AV_GESTAO, TIPO_NOTIFICACAO_LABEL_ENVIO_AV_GESTAO));
			listTipoNotificacao.add(new SelectItem(TIPO_NOTIFICACAO_ID_ENVIO_AV_SURET, TIPO_NOTIFICACAO_LABEL_ENVIO_AV_SURET));
			listTipoNotificacao.add(new SelectItem(TIPO_NOTIFICACAO_ID_VENCIMENTO_PRAZO, TIPO_NOTIFICACAO_LABEL_VENCIMENTO_PRAZO));
		}
	}
	
	public void doConsultarAvancado(ActionEvent evt) {
		setModoFiltro();
	}
	
	/**
	 * Método responsável por realizar a busca dos registros
	 * @return
	 */
	public List<TemplateNotificacao> doBuscaRegistros()
	{
		List<TemplateNotificacao> busca = new ArrayList<TemplateNotificacao>();
		
		//Limpa o filtro
		getFilterBase().limparFiltros();
		
		try {
			if (isModoInicio()) {
				
				doLimparAvancado();
				
				//Modo simples
				getFilterBase().setModoSimples();
				
				getFilterBase().addToFilter("pesquisaSimples", pesquisaSimples);
				
				//Define que o filtro base possui um filtro de template notificacao.
				getFilterBase().addToFilter("filtroTemplateNotificacao", true);
				
				//Coloca o filtro com os valores adicionados na sessão.
				putFilterBase(this.getClass());
				
				busca = carregaListaTemplateNotificacaoFiltroSimples();
				
			} else {
				
				doLimparSimples();
				
				//Modo avançado
				getFilterBase().setModoAvancado();

				getFilterBase().addToFilter("pesquisaCodTipoUnidade", pesquisaCodTipoUnidade);
				getFilterBase().addToFilter("pesquisaCodTipoNotificacao", pesquisaCodTipoNotificacao);
				getFilterBase().addToFilter("pesquisaAssunto", pesquisaAssunto);
						
				//Define que o filtro base possui um filtro de template notificacao.
				getFilterBase().addToFilter("filtroTemplateNotificacao", true);
				
				//Coloca o filtro com os valores adicionados na sessão.
				putFilterBase(this.getClass());
				
				busca = carregaListaTemplatNotificacaoFiltroAvancado();
								
			}
		} catch (SIIACException ex) {
			LogCEF.error(ex);
		}
		
		return busca;
	}
	
	public boolean getShowConsultarAvancado() {
		return isModoFiltro();
	}
	
	public boolean getShowConsultarSimples() {
		return isModoInicio();
	}
	
	public void doConsultarSimples(ActionEvent evt) {
		setModoInicio();
	}
	
	public boolean isShowNovo() {
		return isModoCadastro() || isModoAltera();
	}
	
	public void doCadastrar(ActionEvent evt) throws SIIACException {
		limpaCamposTemplateNotificacao();
		setModoCadastro();
		modoAlteracao = false;
		listTemplateNotificacao = carregaListaTemplateNotificacaoFiltroSimples();
	}
	
	public void doLimpar(ActionEvent evt)
	{
		assunto = "";
		codTipoNotificacao = "";
		codTipoUnidade = "";
		
		pesquisaAssunto = "";
		pesquisaCodTipoUnidade = "";
		pesquisaCodTipoNotificacao = "";
		
		pesquisaSimples = "";
		
		//limpa os filtros da sessão.
		getFilterBase().limparFiltros();
	}
	
	private void doLimparAvancado()
	{
		pesquisaAssunto = "";
		pesquisaCodTipoUnidade = "";
		pesquisaCodTipoNotificacao = "";
	}
	
	private void doLimparSimples()
	{
		pesquisaSimples = "";
	}
	
	public String doCarregar()
	{
		if (Utilities.notEmpty(templateNotificacao.getNuTemplateNotificacao()) && templateNotificacao.getNuTemplateNotificacao() > 0)
		{
			templateNotificacao = templateNotificacaoBO.findById(templateNotificacao.getNuTemplateNotificacao());
			codTipoNotificacao = templateNotificacao.getIcTipoNotificacao().toString();
			templateNotificacao.setNoReferenciaNotificacao("");
			templateNotificacao.setIcTipoNotificacao(null);
			templateNotificacao.setNuTemplateNotificacao(0);
			textoNotificacao = templateNotificacao.getTextoNotificacao();
			modalImportaDados = false;
			
			return "toTemplateNotificacao";
		} else {
			error("Selecione um Template para importar.");
			modalImportaDados = true;
			return "";
		}
		
	}
	
	public void doCancelarImportaDados(ActionEvent evt)
	{
		templateNotificacao.setNuTemplateNotificacao(0);
		modalImportaDados = false;
	}
	
	public void doCancelarReplicaDados(ActionEvent evt)
	{
		modalReplicaDados = false;
	}
	
	public boolean isShowDataTable() {
		return isModoInicio() || isModoFiltro();
	}
	
	public String doCancelarClick() throws SIIACException
	{
		setFiltro();
		limpaCamposTemplateNotificacao();
		return "toTemplateNotificacao";
	}
	
	public void doCancelar() throws SIIACException {
		setFiltro();
		textoNotificacao = "";
		limpaCamposTemplateNotificacao();
		warn(MSGS, MN003);
	}
	
	private void limpaCamposTemplateNotificacao()
	{
		templateNotificacao = new TemplateNotificacao();
		assunto = "";
		codTipoUnidade = "";
		codTipoNotificacao = "";
	}
	
	public List<TemplateNotificacao> carregaListaTemplateNotificacaoFiltroSimples() throws SIIACException {
		return templateNotificacaoBO.getListTemplateNotificacaoFiltroSimples(getFilterBase());
	}
	
	public List<TemplateNotificacao> carregaListaTemplatNotificacaoFiltroAvancado() throws SIIACException {
		return templateNotificacaoBO.getListTemplateNotificacaoFiltroAvancado(getFilterBase());
	}
	
	public String doVisualizaBotao()
	{
		try {
			
			if (Utilities.notEmpty(textoNotificacao))
			{
				
				String caminhaAbsoluto = "src=\"" + getRequest().getRealPath("images") + System.getProperty("file.separator");
	
				String ret = buildTemplateNotificacaoBO.processShow(textoNotificacao);
				
				ret = ret.replace("src=\"../../images/", caminhaAbsoluto).replace("{<img", "<img").replace("/>}", "/>");
	
				setTextoHTMLPreenchido(ret);
	
				byte[] bs = geraPDF(ret);
	
				getResponse().setHeader("Pragma", "");
				getResponse().setHeader("Cache-Control", "");
				getResponse().setHeader("Expires", "");
				getResponse().setCharacterEncoding("UTF-8");
				getResponse().setContentType("application/download");
				getResponse().setHeader("Content-disposition",
						"attachment; filename=\"notificacao.pdf\"");
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
	
	public String doVisualizaClick()
	{
		templateNotificacao = templateNotificacaoBO.findById(templateNotificacao.getNuTemplateNotificacao());

		try {

			String caminhaAbsoluto = "src=\"" + getRequest().getRealPath("images") + System.getProperty("file.separator");

			String ret = buildTemplateNotificacaoBO.processShow(templateNotificacao.getTextoNotificacao());
			
			ret = ret.replace("src=\"../../images/", caminhaAbsoluto).replace("{<img", "<img").replace("/>}", "/>");

			setTextoHTMLPreenchido(ret);

			byte[] bs = geraPDF(ret);

			getResponse().setHeader("Pragma", "");
			getResponse().setHeader("Cache-Control", "");
			getResponse().setHeader("Expires", "");
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().setContentType("application/download");
			getResponse().setHeader("Content-disposition",
					"attachment; filename=\"notificacao.pdf\"");
			getResponse().getOutputStream().write(bs);

			getFacesContext().responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "toTemplateNotificacao";
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
	
	/**
	 * Realiza a consulta dos dados.
	 */
	public void doConsultarClick(ActionEvent evt) {
		
		List<TemplateNotificacao> busca = doBuscaRegistros();
		
		if (busca.isEmpty()) {
			warn(MSGS, MN016);
		}
		
		listTemplateNotificacao = busca;
		
	}
	
	public void doAlterarSimples(ActionEvent evt) throws SIIACException
	{
		Integer gravar = doGravar();
		if (gravar == ERRO)
		{
			error("Item já cadastrado. Consulte todos os itens.");
		} else if (gravar == SUCESSO){
			info(MSGS, MN008);
			setFiltro();
			textoNotificacao = "";
		} else if (gravar == EXCECAO){
			error("Nao foi possivel realizar a operacao. " + mensagemErro);
		}
	}
	
	public Integer doAlterarReplicar() throws SIIACException
	{
		return doGravar();		
	}
	
	public void doSalvar(ActionEvent evt) throws SIIACException
	{
		Integer gravar = doGravar();
		if (gravar == ERRO)
		{
			error("Item já cadastrado. Consulte todos os itens.");
		} else if (gravar == SUCESSO){
			info(MSGS, MN007);
			setFiltro();
			textoNotificacao = "";
		} else  if (gravar == EXCECAO){
			error("Nao foi possivel realizar a operacao. " + mensagemErro);
		}
	}
	
	public Integer doGravar() throws SIIACException {
		
		//Retorno será inteiro, existem 3 casos
		templateNotificacao.setTextoNotificacao(textoNotificacao);
		
		if (doValida()) {
			
			try {
				
				boolean aptoCadastro = false;
				
				if (isModoCadastro())
				{
					templateNotificacao.setIcTipoNotificacao(codTipoNotificacao.charAt(0));
					templateNotificacao.setNuTipoUnidadeNotificada(Short.parseShort(codTipoUnidade));
					templateNotificacao.setTextoNotificacao(FormatUtil.parseHtmlCharacters(templateNotificacao.getTextoNotificacao()));
					
					aptoCadastro = templateNotificacaoBO.exist(templateNotificacao, Boolean.FALSE);					
					
					if (aptoCadastro)
						templateNotificacaoBO.merge(templateNotificacao);
					else
					{
						return ERRO;
					}
					
				} else {
					
					templateNotificacaoBO.merge(templateNotificacao);
					
				}
								
				listTemplateNotificacao = carregaListaTemplateNotificacaoFiltroSimples();
				if (isModoCadastro()) {
					return SUCESSO;
				} else if (isModoAltera()) {
					return SUCESSO;
				}
				
			} catch (Exception e) {
				mensagemErro = e.getMessage();
			}
			
			return EXCECAO;
		} else {
			exibeMensagemErro();
		}
		
		setFiltro();
		
		return -1;
	}
	
	public void doFazNada(ActionEvent evt){}
	
	public void doAtualizar() {
		
		templateNotificacao = templateNotificacaoBO.findById(templateNotificacao.getNuTemplateNotificacao());
		textoNotificacao = templateNotificacao.getTextoNotificacao();
		
		codTipoNotificacao = templateNotificacao.getIcTipoNotificacao().toString();
		codTipoUnidade = templateNotificacao.getNuTipoUnidadeNotificada() + "";
		
		modoAlteracao = true;
		doMostraReplicarDados();
		setModoAltera();		
	}
	
	public void doMostraImportarDados()
	{
		modalImportaDados = true;
		listTemplateNotificacaoImportacao.clear();
		listTemplateNotificacaoImportacao = templateNotificacaoBO.findAll();
	}
	
	public void doMostraReplicaDados(ActionEvent evt)
	{
		modalReplicaDados = true;
		replicarDados = true;
	}
	
	public void doMostraReplicarDados()
	{
		listTemplateNotificacaoVO.clear();
		TemplateNotificacaoVO temp;
		
		for (TemplateNotificacao vo : templateNotificacaoBO.findAll()) 
		{
			if (!vo.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
			{
				temp = new TemplateNotificacaoVO();
				temp.setNoAssuntoNotificacao(vo.getNoAssuntoNotificacao());
				temp.setNuTemplateNotificacao(vo.getNuTemplateNotificacao());
				temp.setReplicado(false);
				
				listTemplateNotificacaoVO.add(temp);
			}
		}
	}
	
	public void doReplicar(ActionEvent evt) throws SIIACException
	{
		Integer retorno = doAlterarReplicar();
		
		try {
			TemplateNotificacao templateNotificacaoAux = new TemplateNotificacao();
			boolean verificador = false;
			
			if (retorno == SUCESSO)
			{
				for (TemplateNotificacaoVO vo : listTemplateNotificacaoVO) {
					
					if (vo.getReplicado())
					{
						templateNotificacaoAux = new TemplateNotificacao();
						templateNotificacaoAux = templateNotificacaoBO.findById(vo.getNuTemplateNotificacao());
						templateNotificacaoAux.setTextoNotificacao(textoNotificacao);
						
						templateNotificacaoBO.merge(templateNotificacaoAux);
						verificador = true;
					}					
					
				}
				
				if (!verificador)
				{
					modalReplicaDados = true;
					error("Selecione ao menos um Template para replicar os dados.");
					return ;
				}
				
				info(MSGS, MN008);
				setModoInicio();
				textoNotificacao = "";
				modalReplicaDados = false;
			} else if (retorno == ERRO) {
				error("Item já cadastrado. Consulte todos os itens.");
				return;
			} else if (retorno == EXCECAO){
				error("Nao foi possivel realizar a operacao. " + mensagemErro);
				return;
			}
			
			
		} catch (Exception e)
		{
			error("Nao foi possivel realizar a operacao. " + e.getMessage());
			modalReplicaDados = false;
		}
		
		
	}
	
	public void doDelete() {
		try {
			templateNotificacao = templateNotificacaoBO.findById(templateNotificacao.getNuTemplateNotificacao());
			templateNotificacaoBO.delete(templateNotificacao);
			listTemplateNotificacao = carregaListaTemplateNotificacaoFiltroSimples();
			info(MSGS, MN009);
			
			setFiltro();
			
		} catch (Exception e)
		{
			error("Nao foi possivel realizar a operacao. " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void doValidaTemplate(ActionEvent evt) throws SIIACException
	{
		valido = doValida();
				
		if (valido)
		{
			templateNotificacao.setIcTipoNotificacao(codTipoNotificacao.charAt(0));
			templateNotificacao.setNuTipoUnidadeNotificada(Short.parseShort(codTipoUnidade));
			
			valido = templateNotificacaoBO.exist(templateNotificacao, Boolean.TRUE);
			
			if (!valido)
			{
				MENSAGEM_VALIDACAO_TEMPLATE = "Item já cadastrado. Consulte todos os itens.";
			}
		}
	}
	public String doImprimeMensagem() {
		exibeMensagemErro();
		return "";
	}
	
	public void exibeMensagemErro()
	{
		error(getMessage(MSGS, MN002));
		warn(MENSAGEM_VALIDACAO_TEMPLATE);
	}
	
	public Boolean doValida()
	{
		if (templateNotificacao.getNoAssuntoNotificacao() == null || Utilities.empty(templateNotificacao.getNoAssuntoNotificacao())) {
			MENSAGEM_VALIDACAO_TEMPLATE = "Campo ASSUNTO Obrigatório.";
			return false;
		}
		
		if (codTipoUnidade == null || Utilities.empty(codTipoUnidade)) {
			MENSAGEM_VALIDACAO_TEMPLATE = "Campo TIPO UNIDADE Obrigatório.";
			return false;
		}
		
		if (codTipoNotificacao == null || Utilities.empty(codTipoNotificacao)) {
			MENSAGEM_VALIDACAO_TEMPLATE = "Campo TIPO NOTIFICAÇÃO Obrigatório.";
			return false;
		}
		
		if (templateNotificacao.getTextoNotificacao() == null || Utilities.empty(templateNotificacao.getTextoNotificacao())) {
			MENSAGEM_VALIDACAO_TEMPLATE = "Campo TEXTO Obrigatório.";
			return false;
		}
		
		return true;
	}
	
	//##############################################################################
	//Getters and Setters
	public TemplateNotificacao getTemplateNotificacao() {
		return templateNotificacao;
	}

	public void setTemplateNotificacao(TemplateNotificacao templateNotificacao) {
		this.templateNotificacao = templateNotificacao;
	}

	public List<SelectItem> getListTipoNotificacao() {
		return listTipoNotificacao;
	}

	public void setListTipoNotificacao(List<SelectItem> listTipoNotificacao) {
		this.listTipoNotificacao = listTipoNotificacao;
	}

	public List<SelectItem> getListTipoUnidade() {
		return listTipoUnidade;
	}

	public void setListTipoUnidade(List<SelectItem> listTipoUnidade) {
		this.listTipoUnidade = listTipoUnidade;
	}

	public List<TemplateNotificacao> getListTemplateNotificacao() {
		return listTemplateNotificacao;
	}
	
	public void setListTemplateNotificacao(
			List<TemplateNotificacao> listTemplateNotificacao) {
		this.listTemplateNotificacao = listTemplateNotificacao;
	}

	public IBuildTemplateNotificacaoBO getBuildTemplateNotificacaoBO() {
		return buildTemplateNotificacaoBO;
	}

	public void setBuildTemplateNotificacaoBO(
			IBuildTemplateNotificacaoBO buildTemplateNotificacaoBO) {
		this.buildTemplateNotificacaoBO = buildTemplateNotificacaoBO;
	}

	public List<SelectItem> getListTags() {
		return listTags;
	}

	public void setListTags(List<SelectItem> listTags) {
		this.listTags = listTags;
	}

	public String getListAllTagstoString() {
		return listAllTagstoString;
	}

	public void setListAllTagstoString(String listAllTagstoString) {
		this.listAllTagstoString = listAllTagstoString;
	}

	public String getTextoHTMLPreenchido() {
		return textoHTMLPreenchido;
	}

	public void setTextoHTMLPreenchido(String textoHTMLPreenchido) {
		this.textoHTMLPreenchido = textoHTMLPreenchido;
	}

	public boolean isModoAlteracao() {
		return modoAlteracao;
	}

	public void setModoAlteracao(boolean modoAlteracao) {
		this.modoAlteracao = modoAlteracao;
	}

	public String getPesquisaSimples() {
		return pesquisaSimples;
	}

	public void setPesquisaSimples(String pesquisaSimples) {
		this.pesquisaSimples = pesquisaSimples;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getCodTipoUnidade() {
		return codTipoUnidade;
	}

	public void setCodTipoUnidade(String codTipoUnidade) {
		this.codTipoUnidade = codTipoUnidade;
	}

	public String getCodTipoNotificacao() {
		return codTipoNotificacao;
	}

	public void setCodTipoNotificacao(String codTipoNotificacao) {
		this.codTipoNotificacao = codTipoNotificacao;
	}

	public ITemplateNotificacaoBO getTemplateNotificacaoBO() {
		return templateNotificacaoBO;
	}

	public void setTemplateNotificacaoBO(
			ITemplateNotificacaoBO templateNotificacaoBO) {
		this.templateNotificacaoBO = templateNotificacaoBO;
	}

	public void setTipoUnidadeBO(ITipoUnidadeBO tipoUnidadeBO) {
		this.tipoUnidadeBO = tipoUnidadeBO;
	}

	public List<TemplateNotificacao> getListTemplateNotificacaoImportacao() {
		return listTemplateNotificacaoImportacao;
	}

	public void setListTemplateNotificacaoImportacao(
			List<TemplateNotificacao> listTemplateNotificacaoImportacao) {
		this.listTemplateNotificacaoImportacao = listTemplateNotificacaoImportacao;
	}


	public List<TemplateNotificacaoVO> getListTemplateNotificacaoVO() {
		return listTemplateNotificacaoVO;
	}

	public void setListTemplateNotificacaoVO(
			List<TemplateNotificacaoVO> listTemplateNotificacaoVO) {
		this.listTemplateNotificacaoVO = listTemplateNotificacaoVO;
	}


	public boolean isModalImportaDados() {
		return modalImportaDados;
	}

	public void setModalImportaDados(boolean modalImportaDados) {
		this.modalImportaDados = modalImportaDados;
	}

	public boolean isModalReplicaDados() {
		return modalReplicaDados;
	}

	public void setModalReplicaDados(boolean modalReplicaDados) {
		this.modalReplicaDados = modalReplicaDados;
	}

	public String getTextoNotificacao() {
		return textoNotificacao;
	}

	public void setTextoNotificacao(String textoNotificacao) {
		this.textoNotificacao = textoNotificacao;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}
	
	public String getTipoNotificacao()
	{
		String retorno = "";
		
		templateNotificacao = (TemplateNotificacao) getRequestMap().get("row");
		
		switch (templateNotificacao.getIcTipoNotificacao()) {
			case TIPO_NOTIFICACAO_ID_CHAR_AVISO_ABERTURA :
				retorno = TIPO_NOTIFICACAO_LABEL_AVISO_ABERTURA;
				break;
				
			case TIPO_NOTIFICACAO_ID_CHAR_ALERTA_PRAZO :
				retorno = TIPO_NOTIFICACAO_LABEL_ALERTA_PRAZO;
				break;
				
			case TIPO_NOTIFICACAO_ID_CHAR_VENCIMENTO_PRAZO :
				retorno = TIPO_NOTIFICACAO_LABEL_VENCIMENTO_PRAZO;
				break;
				
			case TIPO_NOTIFICACAO_ID_CHAR_ENVIO_AV_GESTAO :
				retorno = TIPO_NOTIFICACAO_LABEL_ENVIO_AV_GESTAO;
				break;
				
			case TIPO_NOTIFICACAO_ID_CHAR_ENVIO_AV_SURET :
				retorno = TIPO_NOTIFICACAO_LABEL_ENVIO_AV_SURET;
				break;

			default :
				retorno = "";
				break;
		}
		
		return retorno;
	}
	
	public String getTipoUnidade()
	{
		templateNotificacao = (TemplateNotificacao) getRequestMap().get("row");
		
		if (Utilities.notEmpty(templateNotificacao.getNuTipoUnidadeNotificada()))
		{
			return tipoUnidadeBO.getNomeTipoUnidadeById(templateNotificacao.getNuTipoUnidadeNotificada());
		} else {
			return "";
		}
	}


	public String getPesquisaAssunto() {
		return pesquisaAssunto;
	}

	public void setPesquisaAssunto(String pesquisaAssunto) {
		this.pesquisaAssunto = pesquisaAssunto;
	}

	public String getPesquisaCodTipoUnidade() {
		return pesquisaCodTipoUnidade;
	}

	public void setPesquisaCodTipoUnidade(String pesquisaCodTipoUnidade) {
		this.pesquisaCodTipoUnidade = pesquisaCodTipoUnidade;
	}

	public String getPesquisaCodTipoNotificacao() {
		return pesquisaCodTipoNotificacao;
	}

	public void setPesquisaCodTipoNotificacao(String pesquisaCodTipoNotificacao) {
		this.pesquisaCodTipoNotificacao = pesquisaCodTipoNotificacao;
	}


	//#####################################################################3
	//Constantes
	public static final String TIPO_NOTIFICACAO_ID_AVISO_ABERTURA = "0";
	public static final String TIPO_NOTIFICACAO_ID_ALERTA_PRAZO = "1";
	public static final String TIPO_NOTIFICACAO_ID_VENCIMENTO_PRAZO = "2";
	public static final String TIPO_NOTIFICACAO_ID_ENVIO_AV_GESTAO = "3";
	public static final String TIPO_NOTIFICACAO_ID_ENVIO_AV_SURET = "4";
	public static final String TIPO_NOTIFICACAO_LABEL_AVISO_ABERTURA = "Aviso Abertura";
	public static final String TIPO_NOTIFICACAO_LABEL_ALERTA_PRAZO = "Alerta Prazo";
	public static final String TIPO_NOTIFICACAO_LABEL_VENCIMENTO_PRAZO = "Vencimento Prazo";
	public static final String TIPO_NOTIFICACAO_LABEL_ENVIO_AV_GESTAO = "Envio AV Gestão";
	public static final String TIPO_NOTIFICACAO_LABEL_ENVIO_AV_SURET = "Envio AV SURET";
	
	public static final Integer SUCESSO = 0;
	public static final Integer ERRO = 1;
	public static final Integer EXCECAO = 2;
	
	public static final char TIPO_NOTIFICACAO_ID_CHAR_AVISO_ABERTURA = '0';
	public static final char TIPO_NOTIFICACAO_ID_CHAR_ALERTA_PRAZO = '1';
	public static final char TIPO_NOTIFICACAO_ID_CHAR_VENCIMENTO_PRAZO = '2';
	public static final char TIPO_NOTIFICACAO_ID_CHAR_ENVIO_AV_GESTAO = '3';
	public static final char TIPO_NOTIFICACAO_ID_CHAR_ENVIO_AV_SURET = '4';
}
