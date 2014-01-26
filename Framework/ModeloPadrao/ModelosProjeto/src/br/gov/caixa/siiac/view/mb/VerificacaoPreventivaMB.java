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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.IAtualizaPrazoVerificacaoBO;
import br.gov.caixa.siiac.bo.ICanalComercialProdutoBO;
import br.gov.caixa.siiac.bo.ICategoriaProdutoBO;
import br.gov.caixa.siiac.bo.IChecklistBO;
import br.gov.caixa.siiac.bo.IContratoBO;
import br.gov.caixa.siiac.bo.IDetalhesContratoBO;
import br.gov.caixa.siiac.bo.IGeraAgendaBO;
import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.bo.IServicoVerificacaoBO;
import br.gov.caixa.siiac.bo.IServicoVerificacaoProdutoBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.bo.IVerificacaoContratoBO;
import br.gov.caixa.siiac.bo.IVerificacaoContratoParecerBO;
import br.gov.caixa.siiac.bo.IVerificacaoPreventivaBO;
import br.gov.caixa.siiac.bo.impl.VerificacaoContratoBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.FiltroVerificacaoPreventivaVO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.CanalComercializacaoProduto;
import br.gov.caixa.siiac.model.domain.CategoriaProduto;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.DetalhesContrato;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.util.Valida;
import br.gov.caixa.siiac.view.mb.pagination.IPaginationOnDemand;
import br.gov.caixa.siiac.view.mb.pagination.PaginationOnDemand;
import br.gov.caixa.util.Utilities;

@Service()
@Scope("request")
public class VerificacaoPreventivaMB extends AbstractMB implements IPaginationOnDemand{

	@Autowired
	private IProdutoBO produtoBO;

	@Autowired
	private IUnidadeBO unidadeBO;

	@Autowired
	private IContratoBO contratoBO;
	
	@Autowired
	private IChecklistBO checklistBO;

	@Autowired
	private IServicoVerificacaoBO servicoVerificacaoBO;

	@Autowired
	private IVerificacaoPreventivaBO verificacaoPreventivaBO;
	
	@Autowired
	private IVerificacaoContratoBO verificacaoContratoBO;
	
	@Autowired
	private IVerificacaoContratoParecerBO verificacaoContratoParecerBO;

	@Autowired
	private IGeraAgendaBO geraAgendaBO;
	
	@Autowired
	private IAtualizaPrazoVerificacaoBO atualizaPrazoVerificacaoBO;
	
	@Autowired
	private IServicoVerificacaoProdutoBO servicoVerificacaoProdutoBO;
	
	@Autowired
	private IDetalhesContratoBO detalhesContratoBO;
	
	@Autowired
	private ICanalComercialProdutoBO canalComercialProdutoBO;
	
	@Autowired
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;

	// Cadastro
	private String filtroCadastro;

	// Pesquisa Simples
	private String pesquisaString = "";
	private Boolean produtosPreferenciais = true;

	private String situacao;
	
	//Campos para alteração
	private String unidade;
	private String produto;
	private String servicoVerificacao;
	private String categoria;
	
	// Pesquisa Avançada
	private String filtroProduto;
	private String filtroUnidade;
	private String codProduto;
	private String codUnidade;
	private String codCanalComercializacao;
	private String codSituacaoVerificacao;
	private String filtroOperacao;
	private String filtroContrato;
	private String filtroDocumentos;
	private String filtroAvalSiric;
	private String filtroAvalCliente;
	private String idProdutoSelect;
	private String codServicoVerificacao;
	private String filtroUsuarioResp;
	private String filtroCodCliente;
	private String filtroNomeCliente;
	private String icPfPj;
	private String codSituacao;
	private String nuIdentificadorClienteFiltro;
	private String filtroCodEmpreendimento;
	private String filtroCodVendedor;
	private String filtroNomeEmpreendimento;
	private String filtroNomeVendedor;
	private String filtroCodPrimeiroCoObrigado;
	private String filtroCodSegundoCoObrigado;
	private String filtroNomePrimeiroCoObrigado;
	private String filtroNomeSegundoCoObrigado;
	private String filtroNumeroDamp;
	private String icTipoIndentificadorCliente;
	private String filtroNomeConveniado;
	private Boolean somenteSuspensas = false;
	private BigDecimal valorInicialFiltro;
	private BigDecimal valorFinalFiltro;
	private Date filtroDataIniInclusao;
	private Date filtroDataFimInclusao;
	private Date filtroDataIniVerificacao;
	private Date filtroDataFimVerificacao;
	private List<SelectItem> listProdutos = new ArrayList<SelectItem>();
	private List<SelectItem> listUnidades = new ArrayList<SelectItem>();
	private List<SelectItem> listServicoVerificacao = new ArrayList<SelectItem>();
	private List<SelectItem> selectItemPfPj;
	private List<SelectItem> selectItemTipoIdentificadorCliente;
	private List<SelectItem> selectItemTipoIdentificadorClienteFiltro;
	private List<SelectItem> selectItemTpCanalComerc;
	private transient List<CategoriaProduto> categoriaListCombo = new ArrayList<CategoriaProduto>();

	private List<Contrato> listaVerificacaoPreventiva;
	private Contrato verificacaoPreventiva = new Contrato();

	private boolean showInputIdentificacaoClienteVazio;
	private boolean showInputCPF;
	private boolean showInputCNPJ;
	private boolean showInputNIS;
	private boolean disableComboTpIdentificador = true;
	private boolean showAlteraVerificacao;
	private boolean showExcluiVerificacao;
	private boolean alteracao;
	
	private transient ICategoriaProdutoBO categoriaProdutoBO;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static final String CONTRATO_SESSAO = "verificacaoPreventiva";
	
	private String codContratoFormatado;
	
	private Integer codConveniado;
	private Integer campoCategoriaProduto;
	private String nomeConveniado;
	
	private BigDecimal valorRessarcimento;
	
	private Date dataRessarcimentoDamp;
	SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

	public FiltroVerificacaoPreventivaVO filtroVO = new FiltroVerificacaoPreventivaVO();
	
	@PostConstruct
	public void init() {
		
		try {
			loadListProdutos();
			loadListUnidades();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		alteracao = false;
		pDemand.addObserver(this);
		setFiltro();
		
		if(getFilterBase().isModoAvancado()){
			setModoFiltro();
		}else{
			setModoInicio();
		}
		
	}
	
	public void doLimpar(ActionEvent evt) throws DAOException
	{
		limparCamposVerificacaoPreventiva();
		
		//limpa os filtros da sessão.
		getFilterBase().limparFiltros();
		putFilterBase(this.getClass());
		pDemand.atualizaList();
	}

	// -----------------------------------------------------------------------------
	/**
	 * Realiza a consulta dos dados.
	 * 
	 * @throws DAOException
	 */
	public void doConsultarClick(ActionEvent evt) {
		getFilterBase().limparFiltros();
		if (isModoInicio()) {
			
			getFilterBase().setModoSimples();
			getFilterBase().addToFilter("pesquisaString", pesquisaString);
			getFilterBase().addToFilter("produtosPreferenciais", produtosPreferenciais);
			putFilterBase(this.getClass());
			
			pDemand.atualizaList();
			if (listaVerificacaoPreventiva.isEmpty()) {
				warn(MSGS, MN016);
			}

		} else {
			if (Utilities.notEmpty(filtroDataIniInclusao) || Utilities.notEmpty(filtroDataFimInclusao))
			{
				// Validando as datas
				if (Utilities.empty(filtroDataIniInclusao)) {
					error("Data inicial do período de inclusão não pode ser nula");
					return;
				}

				if (Utilities.empty(filtroDataFimInclusao)) {
					error("Data final do período de inclusão não pode ser nula");
					return;
				}
				

				// Comparando Datas entre si
				if (filtroDataFimInclusao.before(filtroDataIniInclusao)) {
					error("Data final do período de inclusão não pode ser menor que a data inicial");
					return;
				}
			}
			
			if (Utilities.notEmpty(filtroDataIniVerificacao) || Utilities.notEmpty(filtroDataFimVerificacao))
			{
				if (Utilities.empty(filtroDataIniVerificacao)) {
					error("Data inicial do período de verificação não pode ser nula");
					return;
				}

				if (Utilities.empty(filtroDataFimVerificacao)) {
					error("Data final do período de verificação não pode ser nula");
					return;
				}


				if (filtroDataFimVerificacao.before(filtroDataIniVerificacao)) {
					error("Data final do período de verificação não pode ser menor que a data inicial");
					return;
				}
			}
			
			if (Utilities.notEmpty(filtroUsuarioResp))
			{
				String letra = filtroUsuarioResp.substring(0,1);
				
				Pattern patternNumbers = Pattern.compile("[^0-9]*");
								
				filtroUsuarioResp = letra + ConvertUtils.padZerosLeft(patternNumbers.matcher(filtroUsuarioResp).replaceAll(""), 6);
			}
			
			if (valorInicialFiltro != null && valorFinalFiltro != null)
			{
				if (valorInicialFiltro.compareTo(valorFinalFiltro) > 0)
				{
					error("O valor inicial do contrato/verificação deve ser menor que o valor final");
					return;
				}
			}
			
			filtroContrato = filtroContrato.replace(".", "").replace("-", "");
			
			//Adicionando filtros ao objeto de filtro
			getFilterBase().addToFilter("codProduto", codProduto);
			getFilterBase().addToFilter("codUnidade", codUnidade);
			getFilterBase().addToFilter("codCanalComercializacao", codCanalComercializacao);
			getFilterBase().addToFilter("filtroOperacao", filtroOperacao);
			getFilterBase().addToFilter("filtroContrato", filtroContrato);
			getFilterBase().addToFilter("filtroAvalSiric", filtroAvalSiric);
			getFilterBase().addToFilter("filtroAvalCliente", filtroAvalCliente);
			getFilterBase().addToFilter("codServicoVerificacao", codServicoVerificacao);
			getFilterBase().addToFilter("filtroUsuarioResp", filtroUsuarioResp);
			getFilterBase().addToFilter("filtroCodCliente", filtroCodCliente);
			getFilterBase().addToFilter("verificacaoPreventivaIcPfPj", 
					verificacaoPreventiva.getIcPfPj());
			
			getFilterBase().addToFilter("codSituacao", codSituacao);
			getFilterBase().addToFilter("verificacaoPreventivaNuIdentificadorCliente", 
					verificacaoPreventiva.getNuIdentificadorCliente());
			
			getFilterBase().addToFilter("filtroCodEmpreendimento", filtroCodEmpreendimento);
			getFilterBase().addToFilter("filtroCodPrimeiroCoObrigado", filtroCodPrimeiroCoObrigado);
			getFilterBase().addToFilter("filtroCodSegundoCoObrigado", filtroCodSegundoCoObrigado);
			getFilterBase().addToFilter("filtroNomePrimeiroCoObrigado", filtroNomePrimeiroCoObrigado);
			getFilterBase().addToFilter("filtroNomeSegundoCoObrigado", filtroNomeSegundoCoObrigado);
			getFilterBase().addToFilter("filtroNumeroDamp", filtroNumeroDamp);
			getFilterBase().addToFilter("verificacaoPreventivaIcTipoIndentificadorCliente", 
					verificacaoPreventiva.getIcTipoIndentificadorCliente());
			getFilterBase().addToFilter("valorInicialFiltro", valorInicialFiltro);
			getFilterBase().addToFilter("valorFinalFiltro", valorFinalFiltro);
			getFilterBase().addToFilter("filtroDataIniInclusao", filtroDataIniInclusao);
			getFilterBase().addToFilter("filtroDataFimInclusao", filtroDataFimInclusao);
			getFilterBase().addToFilter("filtroDataIniVerificacao", filtroDataIniVerificacao);
			getFilterBase().addToFilter("filtroDataFimVerificacao", filtroDataFimVerificacao);
			getFilterBase().addToFilter("valorRessarcimento", valorRessarcimento);
			getFilterBase().addToFilter("dataRessarcimentoDamp", dataRessarcimentoDamp);
			getFilterBase().addToFilter("codConveniado", codConveniado);
			getFilterBase().addToFilter("codSituacaoVerificacao", codSituacaoVerificacao);
			getFilterBase().addToFilter("filtroCodVendedor", filtroCodVendedor);
			getFilterBase().addToFilter("filtroNomeVendedor", filtroNomeVendedor);
			getFilterBase().addToFilter("filtroNomeConveniado", filtroNomeConveniado);
			getFilterBase().addToFilter("filtroNomeCliente", filtroNomeCliente);
			getFilterBase().addToFilter("filtroNomeEmpreendimento", filtroNomeEmpreendimento);
			//Fim da adição dos filtros.
			
			getFilterBase().setModoAvancado();
			
			//Coloca o filtro com os valores adicionados na sessão.
			putFilterBase(this.getClass());

			// Caso tudo esteja OK
			pDemand.atualizaList();
			if (listaVerificacaoPreventiva.isEmpty()) {
				warn(MSGS, MN016);
			}

		}

	}
	
	/**
	 * Pega o Filtro na sessão e seta as variaveis da view<br/>
	 * com seus respectivos valores que estavam guardados<br/>
	 * no filtro da sessão. 
	 * @see AbstractMB.getFilteriInSession();
	 */
	private void setFiltro(){
		//Pega o filtro na sessão.
		getFilterInSession(this.getClass());
		
		codProduto =  getFilterBase().getString("codProduto");
		codUnidade =  getFilterBase().getString("codUnidade");
		codCanalComercializacao = getFilterBase().getString("codCanalComercializacao");
		filtroOperacao = getFilterBase().getString("filtroOperacao");
		filtroContrato = getFilterBase().getString("filtroContrato");
		filtroAvalSiric = getFilterBase().getString("filtroAvalSiric");
		filtroAvalCliente = getFilterBase().getString("filtroAvalCliente");
		codServicoVerificacao =  getFilterBase().getString("codServicoVerificacao");
		filtroUsuarioResp = getFilterBase().getString("filtroUsuarioResp");
		filtroCodCliente = getFilterBase().getString("filtroCodCliente");
		verificacaoPreventiva.setIcPfPj(getFilterBase().getString("verificacaoPreventivaIcPfPj"));
		
		codSituacao= getFilterBase().getString("codSituacao");
		verificacaoPreventiva.setNuIdentificadorCliente(
				getFilterBase().getString("verificacaoPreventivaNuIdentificadorCliente"));
	
		filtroCodEmpreendimento = getFilterBase().getString("filtroCodEmpreendimento");
		filtroCodPrimeiroCoObrigado = getFilterBase().getString("filtroCodPrimeiroCoObrigado");
		filtroCodSegundoCoObrigado = getFilterBase().getString("filtroCodSegundoCoObrigado");
		filtroNomePrimeiroCoObrigado = getFilterBase().getString("filtroNomePrimeiroCoObrigado");
		filtroNomeSegundoCoObrigado = getFilterBase().getString("filtroNomeSegundoCoObrigado");
		filtroNumeroDamp = getFilterBase().getString("filtroNumeroDamp");
		verificacaoPreventiva.setIcTipoIndentificadorCliente(
				getFilterBase().getString("verificacaoPreventivaIcTipoIndentificadorCliente"));
		
		valorInicialFiltro = getFilterBase().getBigDecimal("valorInicialFiltro");
		valorFinalFiltro = getFilterBase().getBigDecimal("valorFinalFiltro");
		
		filtroDataIniInclusao = getFilterBase().getDate("filtroDataIniInclusao");
		filtroDataFimInclusao = getFilterBase().getDate("filtroDataFimInclusao");
		filtroDataIniVerificacao = getFilterBase().getDate("filtroDataIniVerificacao");
		filtroDataFimVerificacao = getFilterBase().getDate("filtroDataFimVerificacao");
		valorRessarcimento = getFilterBase().getBigDecimal("valorRessarcimento");
		dataRessarcimentoDamp = getFilterBase().getDate("dataRessarcimentoDamp");
		codConveniado = getFilterBase().getInt("codConveniado");
		codSituacaoVerificacao = getFilterBase().getString("codSituacaoVerificacao");
		filtroCodVendedor = getFilterBase().getString("filtroCodVendedor");
		filtroNomeVendedor = getFilterBase().getString("filtroNomeVendedor");
		filtroNomeConveniado = getFilterBase().getString("filtroNomeConveniado");
		filtroNomeCliente = getFilterBase().getString("filtroNomeCliente");
		filtroNomeEmpreendimento = getFilterBase().getString("filtroNomeEmpreendimento");
		
		pesquisaString = getFilterBase().getString("pesquisaString");
		produtosPreferenciais = getFilterBase().getBoolean("produtosPreferenciais");
		
		if(verificacaoPreventiva != null && verificacaoPreventiva.getIcPfPj() != null && !verificacaoPreventiva.getIcPfPj().equals("")){
			disableComboTpIdentificador = false;
			if(getSelectItemTipoIdentificadorCliente().equals("Cpf")){
				showInputNIS = false;
				showInputCNPJ = false;
				showInputCPF = true;
			}
			else if(getSelectItemTipoIdentificadorCliente().equals("NIS")){
				showInputCNPJ = false;
				showInputCPF = false;
				showInputNIS = true;
			}
			else{
				showInputCPF = false;
				showInputNIS = false;
				showInputCNPJ = true;
			}
		}
	}

	private void loadListServicoVerificacao(
			List<SelectItem> listServicoVerificacao, String idProdutoSelect) {
		if (listServicoVerificacao == null || idProdutoSelect == null
				|| idProdutoSelect.equals(""))
			return;
		{
			this.listServicoVerificacao.clear();

			Produto produto = produtoBO.findEagerById(idProdutoSelect);

			List<ServicoVerificacaoProduto> list = produto
					.getServicoVerificacaoProdutoList();

			listServicoVerificacao.add(new SelectItem("0", "Selecione..."));

			for (ServicoVerificacaoProduto s : list) {
				listServicoVerificacao.add(new SelectItem(s
						.getNuServicoVerificacaoProduto(), s
						.getServicoVerificacao().getNoServicoVerificacao()));
			}
		}
	}

	public void changeValueChangeProduto(ActionEvent evt) {
		loadListServicoVerificacao(listServicoVerificacao, idProdutoSelect);
	}

	public void doCadastrar(ActionEvent evt) throws SIIACException,
			DAOException {
		limparCamposVerificacaoPreventiva();
		setModoCadastro();
		setShowInputIdentificacaoClienteVazio(true);
		setShowInputCNPJ(false);
		setShowInputCPF(false);
		setShowInputNIS(false);
		setDisableComboTpIdentificador(true);
	}

	public void limparCamposVerificacaoPreventiva() throws DAOException {
		verificacaoPreventiva = new Contrato();
		filtroCodVendedor = "";
		filtroNomeVendedor = "";
		codSituacaoVerificacao = "";
		filtroProduto = "";
		filtroUnidade = "";
		codProduto = "";
		codUnidade = "";
		codCanalComercializacao = "";
		filtroOperacao = "";
		filtroContrato = "";
		filtroDocumentos = "";
		filtroAvalSiric = "";
		filtroAvalCliente = "";
		idProdutoSelect = "";
		codServicoVerificacao = "";
		filtroUsuarioResp = "";
		filtroCodCliente = "";
		filtroNomeCliente = "";
		icPfPj = "";
		codSituacao = "";
		nuIdentificadorClienteFiltro = "";
		filtroCodEmpreendimento = "";
		filtroNomeEmpreendimento = "";
		filtroCodPrimeiroCoObrigado = "";
		filtroCodSegundoCoObrigado = "";
		filtroNomePrimeiroCoObrigado = "";
		filtroNomeSegundoCoObrigado = "";
		filtroNumeroDamp = "";
		icTipoIndentificadorCliente = "";
		campoCategoriaProduto = null;
		codConveniado = null;
		nomeConveniado = "";
		valorRessarcimento = null;
		valorInicialFiltro = null;
		valorFinalFiltro = null;
		dataRessarcimentoDamp = null;
		filtroDataIniInclusao = null;
		filtroDataFimInclusao = null;
		filtroDataIniVerificacao = null;
		filtroDataFimVerificacao = null;
		listUnidades = new ArrayList<SelectItem>();
		listProdutos = new ArrayList<SelectItem>();
		loadListProdutos();
		loadListUnidades();
		
		
	}

	public String getCodProdutoFormatado() {
		String codProduto = "";

		codProduto = verificacaoPreventiva.getProduto().getCoProduto();

		if (Utilities.notEmpty(codProduto)
				&& Utilities.lengthEquals(codProduto, 7)) {
			// Valida se tem 7 caracteres pois a máscara de produto tem 7
			// caracteres.
			StringBuilder builder = new StringBuilder(codProduto);
			builder.insert(4, '-');
			return builder.toString();
		}

		return "";

	}
	
	public String getCodContratoFormatado() throws ParseException {
		String codContrato = "";
		String mascara = "AAAA.AAA.AAAAAAA-A";
		
		Contrato contrato = (Contrato) getRequestMap().get("contrato");

		codContrato = contrato.getCoContrato();

		if (Utilities.notEmpty(codContrato)) {
			return formatString(codContrato, mascara);
		}

		return "";

	}
	
	public void doAtualizaCategoria(ActionEvent evt)
	{
		codProduto = "";
		codServicoVerificacao = "";
		listServicoVerificacao = new ArrayList<SelectItem>();
		try{
			loadListProdutos();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public String getCodUnidadeFormatado() {
		String unidadeFormatada = "";
		if (isModoAltera() || isModoVisualiza()) {
			unidadeFormatada = verificacaoPreventiva.getNuUnidade().toString();
		}
		return ConvertUtils.padZerosLeft(unidadeFormatada, 4);
	}
	
	public boolean isShowTipoIdentificador() {
		if (verificacaoPreventiva != null) {
			if (!verificacaoPreventiva.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID)) {
				return true;
			}
		}
		
		return false;
	}

	public String getNuIdentificadorCliente() {
		return formataDocumento(verificacaoPreventiva);
	}
	
	/**
	 * Formata o documento do contrato: NIS, CPF ou CNPJ.
	 * @return documento formatado
	 */
	public static String formataDocumento(Contrato contrato) {
		
		if (contrato.getIcTipoIndentificadorCliente() != null)
		{
			if (contrato.getNuIdentificadorCliente() != null && !contrato.getNuIdentificadorCliente().equals("")) {
				if (contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CPF_ID)) {
					char[] documento = contrato.getNuIdentificadorCliente().toCharArray();
					StringBuilder docFormatado = new StringBuilder();
					for (int i = 0; i < documento.length; i++) {
						if (i == 3 || i == 6) {
							docFormatado.append(".");
						}
						if (i == 9) {
							docFormatado.append("-");
						}
						docFormatado.append(documento[i]);
					}
					return docFormatado.toString();
				} else {
					if (contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_NIS_ID)) {
						char[] documento = contrato.getNuIdentificadorCliente().toCharArray();
						StringBuilder docFormatado = new StringBuilder();
						
						for (int i = documento.length, j = 0; i > 0; i--, j++) {
							if (i == documento.length - 1) {
								docFormatado.append("-");
								j = 0;
							}
							if (j == 3) {
								docFormatado.append(".");
								j = 0;
							}
							docFormatado.append(documento[i - 1]);
						}
						return docFormatado.reverse().toString();
					} else {
						char[] documento = contrato.getNuIdentificadorCliente().toCharArray();
						StringBuilder docFormatado = new StringBuilder();
						for (int i = 0; i < documento.length; i++) {
							if (i == 2 || i == 5) {
								docFormatado.append(".");
							}
							if (i == 8) {
								docFormatado.append("/");
							}
							if (i == 12) {
								docFormatado.append("-");
							}
							docFormatado.append(documento[i]);
						}
						return docFormatado.toString();
					}
			
				}
			}
		}
		return "";
	}
	
	public String getDtContratoFormatada() {
		Date dtContrato = null;

		if (verificacaoPreventiva != null) {
			dtContrato = verificacaoPreventiva.getDtContrato();
		}

		return sdf.format(dtContrato);
	}

	/**
	 * Clique do botao 'Cancelar'/'Voltar'.
	 * 
	 * @param evt
	 * @throws SIIACException
	 * @throws DAOException
	 */
	public void doCancelar(ActionEvent evt) throws SIIACException, DAOException {
		alteracao = false;
		limparCamposVerificacaoPreventiva();
		getFilterInSession(this.getClass());
		setFiltro();
		if(getFilterBase().isModoAvancado()){
			setModoFiltro();
		}else{
			setModoInicio();
		}
		
		warn(MSGS, MN003);
	}

	/**
	 * Valida a insercao/alteracao de um apontamento
	 */
	public Boolean doValida() {

		if (codUnidade == null || Utilities.empty(codUnidade)) {
			error(getMessage(MSGS, MN002));
			warn("Campo UNIDADE Obrigatório.");
			return false;
		}

		if (codProduto == null || Utilities.empty(codProduto)) {
			error(getMessage(MSGS, MN002));
			warn("Campo PRODUTO Obrigatório.");
			return false;
		}

		if (!alteracao)
		{
			if (codServicoVerificacao == null
					|| Utilities.empty(codServicoVerificacao)) {
				error(getMessage(MSGS, MN002));
				warn("Campo SERVIÇO DE VERIFICAÇÃO Obrigatório.");
				return false;
			}
			
			if(! this.checklistBO.existByProdutoEServicoVerificacao(codProduto, Integer.parseInt(codServicoVerificacao), new Date())){
				error(getMessage(MSGS, MN002));
				warn("Este serviço de verificação não possui checklist PUBLICADO para este produto.");
				return false;
			}
		}
		
		
		if (codConveniado != null && codConveniado > 0)
		{
			if (!detalhesContratoBO.existeNuConveniado(codConveniado))
			{
				error(getMessage(MSGS, MN002));
				warn("Código do Conveniado não existe na base de dados.");
				return false;
			}
		}
			
		if(Utilities.notEmpty(verificacaoPreventiva.getNuIdentificadorCliente())){
			if(verificacaoPreventiva.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID)){
				if(!Valida.isValidCNPJ(verificacaoPreventiva.getNuIdentificadorCliente())){
					warn("CNPJ Inválido!");
					return false;
				}
			}else{
				if(verificacaoPreventiva.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CPF_ID)){
					if(!Valida.isValidCPF(verificacaoPreventiva.getNuIdentificadorCliente())){
						warn("CPF Inválido!");
						return false;
					}
				}
			}
		}
		
		return true;
	}

	public void doValidaCPF_CNPJ(ActionEvent evt){
		if(Utilities.notEmpty(verificacaoPreventiva.getNuIdentificadorCliente())){
			if(verificacaoPreventiva.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID)){
				if(!Valida.isValidCNPJ(verificacaoPreventiva.getNuIdentificadorCliente())){
					warn("CNPJ digitado Inválido!");
				}
			}else{
				if(verificacaoPreventiva.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CPF_ID)){
					if(!Valida.isValidCPF(verificacaoPreventiva.getNuIdentificadorCliente())){
						warn("CPF digitado Inválido!");
					}
				}
			}
		}
	}
	
	/**
	 * Grava (insere/altera) um template parecer
	 * 
	 * @param evt
	 * @return
	 */
	@SuppressWarnings({ "all" })
	public void doGravar(ActionEvent evt) {
		

		if (doValida()) {
			
			Contrato contratoSalvo = new Contrato();
			
			try {
				DetalhesContrato detalhesContrato = new DetalhesContrato();
				detalhesContrato = verificacaoPreventiva.getDetalhesContrato();
				if (codConveniado != null && codConveniado > 0)
					detalhesContrato.setNuConveniado(codConveniado);
				else
					detalhesContrato.setNuConveniado(null);
				
				if (dataRessarcimentoDamp != null)
				{
					detalhesContrato.setNoCampoLivre2(formataData.format(dataRessarcimentoDamp));
				}
				
				if (valorRessarcimento != null)
				{
					detalhesContrato.setNoCampoLivre3(valorRessarcimento.toString());
				}
				
				if (Utilities.empty(verificacaoPreventiva.getIcPfPj()))
				{
					verificacaoPreventiva.setIcPfPj(null);
					verificacaoPreventiva.setIcTipoIndentificadorCliente(null);
				}

				Produto produto = new Produto();
				produto = produtoBO.findById(codProduto);
				verificacaoPreventiva.setProduto(produto);
				
				verificacaoPreventiva.setNuOperacao(produto.getNuOperacao());
				
				verificacaoPreventiva.setNuUnidade(Short.parseShort(codUnidade));
				verificacaoPreventiva.setNuNatural(unidadeBO.getNuNaturalUnidade(Short.parseShort(codUnidade)));
				verificacaoPreventiva.setIcSituacaoContaContrato(Contrato.SITUACAO_CONTRATO_ID_NAOCT);
				if (!alteracao)
				{
					verificacaoPreventiva.setIcAgendaGerada(Contrato.AGENDA_GERADA_ID_INSERIDA);
					verificacaoPreventiva.setDtInclusao(new Date());
					verificacaoPreventiva.setDtContrato(new Date());
				}
				else
				{
					verificacaoPreventiva.setIcAgendaGerada(Contrato.AGENDA_GERADA_ID_GERACAO_OK);
				}
				verificacaoPreventiva.setIcTipoContrato('1');
				verificacaoPreventiva.setIcPrazoVerificacao(Contrato.PRAZO_VERIFICACAO_ID_CONTRATO_A_VERIFICAR);
				verificacaoPreventiva.setIcAtivo(Boolean.TRUE);
				
				verificacaoPreventiva.setIcTipoVerificacaoContrato(Contrato.TIPO_VERIFICACAO_CONTRATO_ID_VERIFICACAO_PREVENTIVA);
				
				if (Utilities.notEmpty(verificacaoPreventiva.getNuIdentificadorCliente()))
					verificacaoPreventiva.setNuIdentificadorCliente(verificacaoPreventiva.getNuIdentificadorCliente().replace(".", "").replace("-", "").replace("/", ""));
				else
					verificacaoPreventiva.setNuIdentificadorCliente("");
								
				contratoSalvo = verificacaoPreventivaBO.merge(verificacaoPreventiva);
								
				detalhesContrato.setNuContrato(contratoSalvo.getNuContrato());
				if (!alteracao)
					detalhesContratoBO.merge(detalhesContrato);
				else
					detalhesContratoBO.update(detalhesContrato);
				
	
				if (!alteracao)
				{
					ServicoVerificacao servicoVerificacao = this.servicoVerificacaoBO.get(Integer.parseInt(codServicoVerificacao));
					
					ServicoVerificacaoProduto servicoVerificacaoProduto = this.servicoVerificacaoProdutoBO.get(produto, servicoVerificacao);
					
					geraAgendaBO.geraAgendaVerificacaoPreventiva(contratoSalvo.getNuContrato(), servicoVerificacaoProduto.getNuServicoVerificacaoProduto());
					atualizaPrazoVerificacaoBO.atualizaPrazoVerificao(contratoSalvo.getNuContrato());
				}
					//				} else {
//					info(MSGS, MN008);
//					limparCamposVerificacaoPreventiva();
//					setModoInicio();
//				}
					
				VerificacaoContratoVO verificacaoContratoVO = new VerificacaoContratoVO();
				verificacaoContratoVO = verificacaoContratoBO.verificacaoByContrato(verificacaoPreventiva);
				
				if (Utilities.empty(verificacaoContratoVO)){
					verificacaoContratoVO = verificacaoContratoParecerBO.verificacaoByContrato(verificacaoPreventiva);
				}
				
				if (Utilities.notEmpty(verificacaoContratoVO))
				{
					getHttpSession().setAttribute("contrato", contratoSalvo);
					getHttpSession().setAttribute("verificacaoContratoVO", verificacaoContratoVO);	
					getHttpSession().setAttribute(IS_VERIFICACAO_PREVENTIVA, true);
					getSessionMap().put("efetuaVerificacaoMB", null);
					
					if (!alteracao)
					{
						info(MSGS, MN007);
					} else {
						info(MSGS, MN008);
					}

					// Envia o usuário para a tela de efetua_verificacao
					NavigationHandler nh = getFacesContext().getApplication().getNavigationHandler();
					nh.handleNavigation(getFacesContext(), null, "toEfetuaVerificacao");
				} else {
					error(MSGS, MN002);
					if (!alteracao)
					{
						verificacaoPreventivaBO.delete(contratoSalvo);
					}
				}
				
				
			} catch (Exception e) {
				error(MSGS, MN002);
				verificacaoPreventivaBO.delete(contratoSalvo);
				e.printStackTrace();
			}
			
		}
	}
	
	public List<SelectItem> getSelectItemTpCanalComercializacao() {
		if (selectItemTpCanalComerc == null) {
			List<CanalComercializacaoProduto> listTipoCanalComerc;
			listTipoCanalComerc = canalComercialProdutoBO.getListCanalComercial();
			selectItemTpCanalComerc = new ArrayList<SelectItem>();
			for (CanalComercializacaoProduto canalComercProd : listTipoCanalComerc) {
				selectItemTpCanalComerc.add(new SelectItem(canalComercProd.getNuCanalCmrco(), canalComercProd.getNoCanalCmrco()));
			}
		}
		
		return selectItemTpCanalComerc;
	}

	public boolean isShowPanelVisualizacao() {
		return isModoVisualiza();
	}
	
	public boolean isShowAlteraVerificacao()
	{
		//Caso a verificação esteja com a situação "A Verificar", deverá ser apresentado o ícone.
		Contrato contrato = (Contrato) getRequestMap().get("contrato");
		
		if (Utilities.notEmpty(contrato.getIcTipoVerificacaoContrato()) && contrato.getIcTipoVerificacaoContrato().equals(Contrato.TIPO_VERIFICACAO_CONTRATO_ID_VERIFICACAO_PREVENTIVA))
		{
			return verificacaoContratoParecerBO.permitirAlteracao(contrato.getNuContrato());
		}
		
		return false;

	}
	
	public boolean isShowExcluiVerificacao()
	{
		//Caso a verificação esteja com a situação "Não Verificada"
		//e não possua nenhum parecer gerado, deverá ser apresentado o ícone.
		Contrato contrato = (Contrato) getRequestMap().get("contrato");
		
		return verificacaoPreventivaBO.permitirExclusao(contrato.getNuContrato());
		
	}
	
	public void doDelete(ActionEvent evt)
	{
		try {
			Contrato contrato = (Contrato) getRequestMap().get("contrato");
			verificacaoPreventivaBO.delete(contrato);
			
			pDemand.atualizaList();
			
			info(MSGS, MN009);
			
		} catch (Exception e) {
			e.printStackTrace();
			error(MSGS, MN002);
			error(e.getMessage());
		}		
	}

	public String doVisualizar() throws Exception {
		verificacaoPreventiva = (Contrato) getRequestMap().get("contrato");
		getHttpSession().setAttribute("contrato", verificacaoPreventiva);
		getHttpSession().setAttribute(IS_VERIFICACAO_PREVENTIVA, true);

		return "toEscolheVerificacao";
		
	}
	
	public String doAlterar() throws DAOException, ParseException
	{
		limparCamposVerificacaoPreventiva();
		verificacaoPreventiva = (Contrato) getRequestMap().get("contrato");
		setModoCadastro();
		setShowInputIdentificacaoClienteVazio(true);
		if(verificacaoPreventiva.getIcTipoIndentificadorCliente() != null){
			if(verificacaoPreventiva.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID)){
				setShowInputCNPJ(true);
			}else if(verificacaoPreventiva.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CPF_ID)){
				setShowInputCPF(true);
			}else if(verificacaoPreventiva.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID)){
				setShowInputNIS(true);			
			}
		}
		setDisableComboTpIdentificador(false);
		changeComboPfPjAltera();
		changeComboTpIdentificadorAltera();
		
		alteracao = true;
		

		//Unidade
		//Padrão: código - nome, estado
		if(verificacaoPreventiva.getNuUnidade() != null){
			codUnidade = verificacaoPreventiva.getNuUnidade().toString();
			unidade = verificacaoPreventiva.getNuUnidade()
					+ " - "
					+ unidadeBO.getNomeUnidadeByUnidade(verificacaoPreventiva
							.getNuUnidade());
		}
		//Produto
		//Padrão: código - nome
		if(verificacaoPreventiva.getProduto() != null){
			codProduto = verificacaoPreventiva.getProduto().getCoProduto();
			produto = verificacaoPreventiva.getProduto().getCodigoFormatado()
					+ " - " 
					+ verificacaoPreventiva.getProduto().getNoProduto();
			
			//Serviço
			//Padrão: nome
	//		servicoVerificacao = verificacaoPreventiva.getV
			
			//Categoria
			if(verificacaoPreventiva.getProduto().getCategoriaProduto() != null){
				categoria = verificacaoPreventiva.getProduto().getCategoriaProduto().getNoCategoriaProduto();
			}
		}
		if (Utilities.notEmpty(verificacaoPreventiva.getDetalhesContrato()) && Utilities.notEmpty(verificacaoPreventiva.getDetalhesContrato().getNoCampoLivre3()))
			valorRessarcimento = BigDecimal.valueOf(Double.parseDouble(verificacaoPreventiva.getDetalhesContrato().getNoCampoLivre3()));
		
		if (Utilities.notEmpty(verificacaoPreventiva.getDetalhesContrato()) && Utilities.notEmpty(verificacaoPreventiva.getDetalhesContrato().getNoCampoLivre2()))
			dataRessarcimentoDamp = new SimpleDateFormat("dd/MM/yyyy").parse(verificacaoPreventiva.getDetalhesContrato().getNoCampoLivre2());
		
		if (Utilities.notEmpty(verificacaoPreventiva.getDetalhesContrato()) && Utilities.notEmpty(verificacaoPreventiva.getDetalhesContrato().getNuConveniado()) && verificacaoPreventiva.getDetalhesContrato().getNuConveniado() > 0)
		{
			codConveniado = verificacaoPreventiva.getDetalhesContrato().getNuConveniado();
		}
		
		if (verificacaoPreventiva.getDetalhesContrato() == null)
			verificacaoPreventiva.setDetalhesContrato(new DetalhesContrato());
		
		return "toVerificacaoPreventiva";
		
		
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

	// -----------------------------------------------------------------------------
	// Variáveis de controle
	public boolean isShowPanelFiltro() {
		return isModoInicio() || isModoFiltro();
	}

	public boolean isShowPanelLista() {
		return isModoInicio() || isModoFiltro();
	}

	public boolean getShowConsultarSimples() {
		return isModoInicio();
	}

	public boolean isShowNovo() {
		return isModoCadastro() || isModoAltera();
	}

	public boolean getShowConsultarAvancado() {
		return isModoFiltro();
	}

	public void doConsultarAvancado(ActionEvent evt) {
		setModoFiltro();
		setFiltro();
	}

	public void doConsultarSimples(ActionEvent evt) {
		setModoInicio();
	}

	public void changeComboPfPj(ActionEvent ev) {
//		verificacaoPreventiva.setNuIdentificadorCliente(null);
		setNuIdentificadorCliente("");
		if (verificacaoPreventiva.getIcPfPj() != null
				&& !verificacaoPreventiva.getIcPfPj().equals("")) {
			if (verificacaoPreventiva.getIcPfPj().trim()
					.equals(Contrato.TIPO_PESSOA_FISICA_ID)) {
				setDisableComboTpIdentificador(false);
				setShowInputNIS(false);
				setShowInputCNPJ(false);
				setShowInputCPF(true);
				setShowInputIdentificacaoClienteVazio(false);
				return;
			} else {
				setShowInputNIS(false);
				setShowInputCNPJ(true);
				setShowInputCPF(false);
				setDisableComboTpIdentificador(true);
				setShowInputIdentificacaoClienteVazio(false);
				verificacaoPreventiva
						.setIcTipoIndentificadorCliente(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID);
				return;
			}
		}
		setShowInputNIS(false);
		setShowInputCNPJ(false);
		setShowInputCPF(false);
		setShowInputIdentificacaoClienteVazio(true);
		setDisableComboTpIdentificador(true);
		verificacaoPreventiva.setIcTipoIndentificadorCliente("");
	}

	
	public void changeComboTpIdentificador(ActionEvent ev) {
//		verificacaoPreventiva.setNuIdentificadorCliente(null);
		
		setNuIdentificadorCliente("");
		if (verificacaoPreventiva.getIcTipoIndentificadorCliente() != null
				&& !verificacaoPreventiva.getIcTipoIndentificadorCliente().equals("")) {

			if (verificacaoPreventiva.getIcTipoIndentificadorCliente().equals(
					Contrato.TIPO_IDENTIFICADOR_CPF_ID)) {
				setShowInputNIS(false);
				setShowInputCNPJ(false);
				setShowInputCPF(true);
				setShowInputIdentificacaoClienteVazio(false);
			} else {
				setShowInputNIS(true);
				setShowInputCNPJ(false);
				setShowInputCPF(false);
				setShowInputIdentificacaoClienteVazio(false);
			}
			return;
		}
		setShowInputNIS(false);
		setShowInputCPF(false);
		setShowInputCNPJ(false);
		setShowInputIdentificacaoClienteVazio(true);
	}
	
	private void changeComboPfPjAltera() {
		if (verificacaoPreventiva.getIcPfPj() != null
				&& !verificacaoPreventiva.getIcPfPj().equals("")) {
			if (verificacaoPreventiva.getIcPfPj().trim()
					.equals(Contrato.TIPO_PESSOA_FISICA_ID)) {
				setDisableComboTpIdentificador(false);
				setShowInputNIS(false);
				setShowInputCNPJ(false);
				setShowInputCPF(true);
				setShowInputIdentificacaoClienteVazio(false);
				return;
			} else {
				setShowInputNIS(false);
				setShowInputCNPJ(true);
				setShowInputCPF(false);
				setDisableComboTpIdentificador(true);
				setShowInputIdentificacaoClienteVazio(false);
				verificacaoPreventiva
						.setIcTipoIndentificadorCliente(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID);
				return;
			}
		}
		setShowInputNIS(false);
		setShowInputCNPJ(false);
		setShowInputCPF(false);
		setShowInputIdentificacaoClienteVazio(true);
		setDisableComboTpIdentificador(true);
		verificacaoPreventiva.setIcTipoIndentificadorCliente("");
	}
	
	private void changeComboTpIdentificadorAltera() {
		if (verificacaoPreventiva.getIcTipoIndentificadorCliente() != null
				&& !verificacaoPreventiva.getIcTipoIndentificadorCliente().equals("")) {

			if (verificacaoPreventiva.getIcTipoIndentificadorCliente().equals(
					Contrato.TIPO_IDENTIFICADOR_CPF_ID)) {
				setShowInputNIS(false);
				setShowInputCNPJ(false);
				setShowInputCPF(true);
				setShowInputIdentificacaoClienteVazio(false);
			} else {
				setShowInputNIS(true);
				setShowInputCNPJ(false);
				setShowInputCPF(false);
				setShowInputIdentificacaoClienteVazio(false);
			}
			return;
		}
		setShowInputNIS(false);
		setShowInputCPF(false);
		setShowInputCNPJ(false);
		setShowInputIdentificacaoClienteVazio(true);
	}

	// -----------------------------------------------------------------------------
	// Getters e Setters

	/**
	 * Preenche o combo de produtos.
	 * 
	 * @return lista de itens de seleção de produtos
	 */
	public List<SelectItem> getListProdutos() {
		return this.listProdutos;
	}
	
	public void loadListProdutos(ActionEvent evt) {
		try{
			this.loadListProdutos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadListProdutos() throws DAOException {
		
		List<Produto> produtos;

		if (campoCategoriaProduto != null && campoCategoriaProduto > 0)
		{
			produtos = contratoBO.getLikeProdutoByCategoria("", campoCategoriaProduto);
		} else {
			produtos = contratoBO.getLikeProduto("");
		}

		if(listProdutos == null) listProdutos = new ArrayList<SelectItem>();
		listProdutos.clear();
//		List<SelectItem> list = new ArrayList<SelectItem>();
		boolean first = Utilities.notEmpty(filtroProduto);
		codProduto = "";
		for (Produto p : produtos) {

			// Valida se o valor digitado no filtro é e se ele está contido
			// na concatenação.
			if (Utilities.notEmpty(filtroProduto)
					&& !(p.getOperacaoFormatada() + "-"
							+ p.getModalidadeFormatada() + " " + p
							.getNoProduto()).contains(filtroProduto)) {
				continue;
			}
			listProdutos.add(new SelectItem(p.getCoProduto(), p
					.getOperacaoFormatada()
					+ "-"
					+ p.getModalidadeFormatada() + " " + p.getNoProduto()));
			if(first){
				first = false;
				codProduto = p.getCoProduto();
			}
		}
//		return list;
		
	}

	/**
	 * Preenche o combo de produtos.
	 * 
	 * @return lista de itens de seleção de produtos
	 * @throws DAOException
	 */
	public List<SelectItem> getListUnidades() throws DAOException {
		return this.listUnidades;
	}
	
	public void loadListUnidades(ActionEvent evt) {
		try{
			this.loadListUnidades();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadListUnidades() throws DAOException {

		if(listUnidades == null) listUnidades = new ArrayList<SelectItem>();
		listUnidades.clear();

//		List<SelectItem> list = new ArrayList<SelectItem>();
		List<Unidade> unidades;
		
		Short nuPerfil = getPerfilUserLogado();
		Short nuUnidade = SegurancaUsuario.getInstance().getUsuario().getUnidade();
		unidades = matrizAbrangenciaBO.getListAbrangenciaUnidadesObj(nuPerfil, nuUnidade);
		if(unidades == null){
			unidades = unidadeBO.getAll();
		}
		boolean first = Utilities.notEmpty(filtroUnidade);
		codUnidade = "";
		for (Unidade u : unidades) {
			if (Utilities.notEmpty(filtroUnidade)) {
				Pattern patternNumbers = Pattern.compile("[0-9]*");
				if (patternNumbers.matcher(filtroUnidade).matches()){
					if(! ConvertUtils.padZerosLeft(String.valueOf(u.getId().getNuUnidade()), 4).contains(filtroUnidade)){
						continue;
					}
				}else{
					if(!(u.getId().getNuUnidade() + " - " + u.getNomeAbreviado().toUpperCase()).contains(filtroUnidade.replaceFirst("0*", "").toUpperCase())){
						continue;
					}
				}
			}
			if(first){
				first = false;
				codUnidade = String.valueOf(u.getId().getNuUnidade());
			}
			listUnidades.add(new SelectItem(u.getId().getNuUnidade(), ConvertUtils.padZerosLeft(String.valueOf(u.getId().getNuUnidade()), 4) + " - " + u.getNomeAbreviado()));
		}
//		return listUnidades;
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
				list.add(new SelectItem(sv.getNuServicoVerificacao(), sv
						.getNoServicoVerificacao()));
			}
		}

		Collections.sort(list, new Comparator<SelectItem>() {
			public int compare(SelectItem obj1, SelectItem obj2) {
				return obj1.getLabel().compareTo(obj2.getLabel());
			}
		});

		return list;
	}
	
	public void buscaConveniado()
	{
		nomeConveniado = "";
		if (Utilities.notEmpty(codConveniado) && codConveniado > 0)
		{
			//Realiza a busca pelo nome do conveniado
			nomeConveniado = detalhesContratoBO.getNomeByNuConveniado(codConveniado);
		}
	}

	public List<SelectItem> getListSituacao() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_ATIVO,
				Contrato.SITUACAO_CONTRATO_LABEL_ATIVO));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_INDME,
				Contrato.SITUACAO_CONTRATO_LABEL_INDME));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_LQDCO,
				Contrato.SITUACAO_CONTRATO_LABEL_LQDCO));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_LQDDO,
				Contrato.SITUACAO_CONTRATO_LABEL_LQDDO));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_VNCDO,
				Contrato.SITUACAO_CONTRATO_LABEL_VNCDO));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_EECRR,
				Contrato.SITUACAO_CONTRATO_LABEL_EECRR));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_IRRGL,
				Contrato.SITUACAO_CONTRATO_LABEL_IRRGL));

		return list;
	}
	
	public List<SelectItem> getListVerificacao() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_CONFORME_SIGLA,
				VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_CONFORME));
		list.add(new SelectItem(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_INCONFORME_SIGLA,
				VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_INCONFORME));
		list.add(new SelectItem(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_NAO_VERIFICADA_SIGLA,
				VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_NAO_VERIFICADA));
		list.add(new SelectItem(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_VERIFICACAO_PARCIAL_SIGLA,
				VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_VERIFICACAO_PARCIAL));
		
		return list;
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

	public void setListProdutos(List<SelectItem> listProdutos) {
		this.listProdutos = listProdutos;
	}

	public IProdutoBO getProdutoBO() {
		return produtoBO;
	}

	public void setProdutoBO(IProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}

	public IUnidadeBO getUnidadeBO() {
		return unidadeBO;
	}

	public IDetalhesContratoBO getDetalhesContratoBO() {
		return detalhesContratoBO;
	}

	public void setDetalhesContratoBO(IDetalhesContratoBO detalhesContratoBO) {
		this.detalhesContratoBO = detalhesContratoBO;
	}

	public void setUnidadeBO(IUnidadeBO unidadeBO) {
		this.unidadeBO = unidadeBO;
	}

	public String getFiltroCadastro() {
		return filtroCadastro;
	}

	public void setFiltroCadastro(String filtroCadastro) {
		this.filtroCadastro = filtroCadastro;
	}

	public String getFiltroUnidade() {
		return filtroUnidade;
	}

	public void setFiltroUnidade(String filtroUnidade) {
		this.filtroUnidade = filtroUnidade;
	}

	public String getCodUnidade() {
		return codUnidade;
	}

	public void setCodUnidade(String codUnidade) {
		this.codUnidade = codUnidade;
	}

	public void setListUnidades(List<SelectItem> listUnidades) {
		this.listUnidades = listUnidades;
	}

	public String getFiltroContrato() {
		return filtroContrato;
	}

	public void setFiltroContrato(String FiltroContrato) {
		this.filtroContrato = FiltroContrato;
	}

	public String getFiltroDocumentos() {
		return filtroDocumentos;
	}

	public void setFiltroDocumentos(String filtroDocumentos) {
		this.filtroDocumentos = filtroDocumentos;
	}

	public String getFiltroAvalSiric() {
		return filtroAvalSiric;
	}

	public void setFiltroAvalSiric(String filtroAvalSiric) {
		this.filtroAvalSiric = filtroAvalSiric;
	}

	public String getFiltroAvalCliente() {
		return filtroAvalCliente;
	}

	public void setFiltroAvalCliente(String filtroAvalCliente) {
		this.filtroAvalCliente = filtroAvalCliente;
	}

	public String getPesquisaString() {
		return pesquisaString;
	}

	public void setPesquisaString(String pesquisaString) {
		this.pesquisaString = pesquisaString;
	}

	public void setListServicoVerificacao(
			List<SelectItem> listServicoVerificacao) {
		this.listServicoVerificacao = listServicoVerificacao;
	}

	public IServicoVerificacaoBO getServicoVerificacaoBO() {
		return servicoVerificacaoBO;
	}

	public void setServicoVerificacaoBO(
			IServicoVerificacaoBO servicoVerificacaoBO) {
		this.servicoVerificacaoBO = servicoVerificacaoBO;
	}

	public String getIdProdutoSelect() {
		return idProdutoSelect;
	}

	public void setIdProdutoSelect(String idProdutoSelect) {
		this.idProdutoSelect = idProdutoSelect;
	}

	public String getCodServicoVerificacao() {
		return codServicoVerificacao;
	}

	public void setCodServicoVerificacao(String codServicoVerificacao) {
		this.codServicoVerificacao = codServicoVerificacao;
	}

	public String getFiltroUsuarioResp() {
		return filtroUsuarioResp;
	}

	public void setFiltroUsuarioResp(String filtroUsuarioResp) {
		this.filtroUsuarioResp = filtroUsuarioResp;
	}

	public String getCodSituacao() {
		return codSituacao;
	}

	public void setCodSituacao(String codSituacao) {
		this.codSituacao = codSituacao;
	}

	public Boolean getSomenteSuspensas() {
		return somenteSuspensas;
	}

	public void setSomenteSuspensas(Boolean somenteSuspensas) {
		this.somenteSuspensas = somenteSuspensas;
	}

	public Date getFiltroDataFimInclusao() {
		return filtroDataFimInclusao;
	}

	public void setFiltroDataFimInclusao(Date filtroDataFimInclusao) {
		this.filtroDataFimInclusao = filtroDataFimInclusao;
	}

	public Date getFiltroDataIniInclusao() {
		return filtroDataIniInclusao;
	}

	public void setFiltroDataIniInclusao(Date filtroDataIniInclusao) {
		this.filtroDataIniInclusao = filtroDataIniInclusao;
	}

	public Date getFiltroDataIniVerificacao() {
		return filtroDataIniVerificacao;
	}

	public void setFiltroDataIniVerificacao(Date filtroDataIniVerificacao) {
		this.filtroDataIniVerificacao = filtroDataIniVerificacao;
	}

	public Date getFiltroDataFimVerificacao() {
		return filtroDataFimVerificacao;
	}

	public void setFiltroDataFimVerificacao(Date filtroDataFimVerificacao) {
		this.filtroDataFimVerificacao = filtroDataFimVerificacao;
	}

	public List<Contrato> getListaVerificacaoPreventiva() {

		if (this.listaVerificacaoPreventiva == null) {
			pDemand.atualizaList();
		}
		return this.listaVerificacaoPreventiva;
	}

	public void setListaVerificacaoPreventiva(
			List<Contrato> listaVerificacaoPreventiva) {
		this.listaVerificacaoPreventiva = listaVerificacaoPreventiva;
	}

	public Boolean getProdutosPreferenciais() {
		return produtosPreferenciais;
	}

	public void setProdutosPreferenciais(Boolean produtosPreferenciais) {
		this.produtosPreferenciais = produtosPreferenciais;
	}

	@Override
	protected IUsuario getUsuarioLogado() {
		return SegurancaUsuario.getInstance().getUsuario();
	}

	public Contrato getVerificacaoPreventiva() {
		return verificacaoPreventiva;
	}

	public void setVerificacaoPreventiva(Contrato verificacaoPreventiva) {
		this.verificacaoPreventiva = verificacaoPreventiva;
	}

	public List<SelectItem> getSelectItemPfPj() {
		if (selectItemPfPj == null) {
			selectItemPfPj = new ArrayList<SelectItem>();
			selectItemPfPj.add(new SelectItem("", "Selecione..."));
			selectItemPfPj.add(new SelectItem(Contrato.TIPO_PESSOA_FISICA_ID,
					Contrato.TIPO_PESSOA_FISICA_LABEL));
			selectItemPfPj.add(new SelectItem(Contrato.TIPO_PESSOA_JURIDICA_ID,
					Contrato.TIPO_PESSOA_JURIDICA_LABEL));
		}

		return selectItemPfPj;
	}

	public List<SelectItem> getSelectItemTipoIdentificadorCliente() {
		selectItemTipoIdentificadorCliente = new ArrayList<SelectItem>();

		if (verificacaoPreventiva.getIcPfPj() != null
				&& !verificacaoPreventiva.getIcPfPj().equals("")) {
			if (verificacaoPreventiva.getIcPfPj().equals(
					Contrato.TIPO_PESSOA_FISICA_ID)) {
				selectItemTipoIdentificadorCliente.add(new SelectItem(
						Contrato.TIPO_IDENTIFICADOR_CPF_ID,
						Contrato.TIPO_IDENTIFICADOR_CPF_LABEL));
				selectItemTipoIdentificadorCliente.add(new SelectItem(
						Contrato.TIPO_IDENTIFICADOR_NIS_ID,
						Contrato.TIPO_IDENTIFICADOR_NIS_LABEL));				
			} else {
				selectItemTipoIdentificadorCliente.add(new SelectItem(
						Contrato.TIPO_IDENTIFICADOR_CNPJ_ID,
						Contrato.TIPO_IDENTIFICADOR_CNPJ_LABEL));
			}
		}

		return selectItemTipoIdentificadorCliente;
	}
	
	public List<SelectItem> getSelectItemTipoIdentificadorClienteFiltro() {
		selectItemTipoIdentificadorClienteFiltro = new ArrayList<SelectItem>();

		if (getIcPfPj() != null
				&& !getIcPfPj().equals("")) {
			if (getIcPfPj().equals(
					Contrato.TIPO_PESSOA_FISICA_ID)) {
				selectItemTipoIdentificadorClienteFiltro.add(new SelectItem(
						Contrato.TIPO_IDENTIFICADOR_CPF_ID,
						Contrato.TIPO_IDENTIFICADOR_CPF_LABEL));
				selectItemTipoIdentificadorClienteFiltro.add(new SelectItem(
						Contrato.TIPO_IDENTIFICADOR_NIS_ID,
						Contrato.TIPO_IDENTIFICADOR_NIS_LABEL));				
			} else {
				selectItemTipoIdentificadorClienteFiltro.add(new SelectItem(
						Contrato.TIPO_IDENTIFICADOR_CNPJ_ID,
						Contrato.TIPO_IDENTIFICADOR_CNPJ_LABEL));
			}
		}

		return selectItemTipoIdentificadorClienteFiltro;
	}

	public void setSelectItemPfPj(List<SelectItem> selectItemPfPj) {
		this.selectItemPfPj = selectItemPfPj;
	}

	public void setSelectItemTipoIdentificadorCliente(
			List<SelectItem> selectItemTipoIdentificadorCliente) {
		this.selectItemTipoIdentificadorCliente = selectItemTipoIdentificadorCliente;
	}

	public boolean isShowInputIdentificacaoClienteVazio() {
		return showInputIdentificacaoClienteVazio;
	}

	public void setShowInputIdentificacaoClienteVazio(
			boolean showInputIdentificacaoClienteVazio) {
		this.showInputIdentificacaoClienteVazio = showInputIdentificacaoClienteVazio;
	}

	public boolean isShowInputCPF() {
		return showInputCPF;
	}

	public void setShowInputCPF(boolean showInputCPF) {
		this.showInputCPF = showInputCPF;
	}

	public boolean isShowInputCNPJ() {
		return showInputCNPJ;
	}

	public void setShowInputCNPJ(boolean showInputCNPJ) {
		this.showInputCNPJ = showInputCNPJ;
	}

	public boolean isShowInputNIS() {
		return showInputNIS;
	}

	public void setShowInputNIS(boolean showInputNIS) {
		this.showInputNIS = showInputNIS;
	}

	public boolean isDisableComboTpIdentificador() {
		return disableComboTpIdentificador;
	}

	public void setDisableComboTpIdentificador(
			boolean disableComboTpIdentificador) {
		this.disableComboTpIdentificador = disableComboTpIdentificador;
	}

	public void setContratoBO(IContratoBO contratoBO) {
		this.contratoBO = contratoBO;
	}

	public void setVerificacaoPreventivaBO(
			IVerificacaoPreventivaBO verificacaoPreventivaBO) {
		this.verificacaoPreventivaBO = verificacaoPreventivaBO;
	}

	public void setVerificacaoContratoBO(
			IVerificacaoContratoBO verificacaoContratoBO) {
		this.verificacaoContratoBO = verificacaoContratoBO;
	}
	
	public String getSituacao()
	{
		Contrato contrato = (Contrato) getRequestMap().get("contrato");
		String situacao = verificacaoContratoBO.obtemSituacaoContrato(contrato.getNuContrato());
		
		if (situacao.equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_VERIFICACAO_PARCIAL))
		{
			return CabecalhoDetalhesVerificacaoMB.ICON_VERIFICACAO_PARCIAL;
		} else if (situacao.equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_CONFORME))
		{
			return CabecalhoDetalhesVerificacaoMB.ICON_CONFORME;
		} else if (situacao.equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_INCONFORME))
		{
			return CabecalhoDetalhesVerificacaoMB.ICON_INCONFORME;
		} 
		
		return "/images/transparente.gif";
		
	}
	
	public static String formatString(String string, String mask) throws java.text.ParseException {
		javax.swing.text.MaskFormatter mf = new javax.swing.text.MaskFormatter(mask);
		mf.setValueContainsLiteralCharacters(false);
		return mf.valueToString(string);
	}


	public Integer getCodConveniado() {
		return codConveniado;
	}

	public void setCodConveniado(Integer codConveniado) {
		this.codConveniado = codConveniado;
	}

	public String getNomeConveniado() {
		buscaConveniado();
		return nomeConveniado;
	}

	public void setNomeConveniado(String nomeConveniado) {
		this.nomeConveniado = nomeConveniado;
	}

	public Integer getCampoCategoriaProduto() {
		return campoCategoriaProduto;
	}

	public void setCampoCategoriaProduto(Integer campoCategoriaProduto) {
		this.campoCategoriaProduto = campoCategoriaProduto;
	}

	@Autowired
	public void setCategoriaProdutoBO(ICategoriaProdutoBO categoriaProdutoBO) {
		CategoriaProduto categoriaProduto = new CategoriaProduto();
		this.categoriaProdutoBO = categoriaProdutoBO;
		categoriaListCombo = categoriaProdutoBO.getListFiltro("", false);
	}

	public Date getDataRessarcimentoDamp() {
		return dataRessarcimentoDamp;
	}

	public void setDataRessarcimentoDamp(Date dataRessarcimentoDamp) {
		this.dataRessarcimentoDamp = dataRessarcimentoDamp;
	}

	public BigDecimal getValorRessarcimento() {
		return valorRessarcimento;
	}

	public void setValorRessarcimento(BigDecimal valorRessarcimento) {
		this.valorRessarcimento = valorRessarcimento;
	}

	public String getCodCanalComercializacao() {
		return codCanalComercializacao;
	}

	public void setCodCanalComercializacao(String codCanalComercializacao) {
		this.codCanalComercializacao = codCanalComercializacao;
	}

	public String getFiltroOperacao() {
		return filtroOperacao;
	}

	public void setFiltroOperacao(String filtroOperacao) {
		this.filtroOperacao = filtroOperacao;
	}

	public void setCodContratoFormatado(String codContratoFormatado) {
		this.codContratoFormatado = codContratoFormatado;
	}

	public String getFiltroCodCliente() {
		return filtroCodCliente;
	}

	public void setFiltroCodCliente(String filtroCodCliente) {
		this.filtroCodCliente = filtroCodCliente;
	}

	public String getFiltroNomeCliente() {
		return filtroNomeCliente;
	}

	public void setFiltroNomeCliente(String filtroNomeCliente) {
		this.filtroNomeCliente = filtroNomeCliente;
	}

	public String getIcPfPj() {
		return icPfPj;
	}

	public void setIcPfPj(String icPfPj) {
		this.icPfPj = icPfPj;
	}

	public String getIcTipoIndentificadorCliente() {
		return icTipoIndentificadorCliente;
	}

	public void setIcTipoIndentificadorCliente(String icTipoIndentificadorCliente) {
		this.icTipoIndentificadorCliente = icTipoIndentificadorCliente;
	}

	public String getFiltroCodEmpreendimento() {
		return filtroCodEmpreendimento;
	}

	public void setFiltroCodEmpreendimento(String filtroCodEmpreendimento) {
		this.filtroCodEmpreendimento = filtroCodEmpreendimento;
	}

	public String getFiltroNomeEmpreendimento() {
		return filtroNomeEmpreendimento;
	}

	public void setFiltroNomeEmpreendimento(String filtroNomeEmpreendimento) {
		this.filtroNomeEmpreendimento = filtroNomeEmpreendimento;
	}

	public String getFiltroCodPrimeiroCoObrigado() {
		return filtroCodPrimeiroCoObrigado;
	}

	public void setFiltroCodPrimeiroCoObrigado(String filtroCodPrimeiroCoObrigado) {
		this.filtroCodPrimeiroCoObrigado = filtroCodPrimeiroCoObrigado;
	}

	public String getFiltroCodSegundoCoObrigado() {
		return filtroCodSegundoCoObrigado;
	}

	public void setFiltroCodSegundoCoObrigado(String filtroCodSegundoCoObrigado) {
		this.filtroCodSegundoCoObrigado = filtroCodSegundoCoObrigado;
	}

	public String getFiltroNomePrimeiroCoObrigado() {
		return filtroNomePrimeiroCoObrigado;
	}

	public void setFiltroNomePrimeiroCoObrigado(String filtroNomePrimeiroCoObrigado) {
		this.filtroNomePrimeiroCoObrigado = filtroNomePrimeiroCoObrigado;
	}

	public String getFiltroNomeSegundoCoObrigado() {
		return filtroNomeSegundoCoObrigado;
	}

	public void setFiltroNomeSegundoCoObrigado(String filtroNomeSegundoCoObrigado) {
		this.filtroNomeSegundoCoObrigado = filtroNomeSegundoCoObrigado;
	}

	public String getFiltroNumeroDamp() {
		return filtroNumeroDamp;
	}

	public void setFiltroNumeroDamp(String filtroNumeroDamp) {
		this.filtroNumeroDamp = filtroNumeroDamp;
	}

	public BigDecimal getValorInicialFiltro() {
		return valorInicialFiltro;
	}

	public void setValorInicialFiltro(BigDecimal valorInicialFiltro) {
		this.valorInicialFiltro = valorInicialFiltro;
	}

	public BigDecimal getValorFinalFiltro() {
		return valorFinalFiltro;
	}

	public void setValorFinalFiltro(BigDecimal valorFinalFiltro) {
		this.valorFinalFiltro = valorFinalFiltro;
	}


	public String getNuIdentificadorClienteFiltro() {
		return nuIdentificadorClienteFiltro;
	}

	public void setNuIdentificadorClienteFiltro(String nuIdentificadorClienteFiltro) {
		this.nuIdentificadorClienteFiltro = nuIdentificadorClienteFiltro;
	}


	public String getCodSituacaoVerificacao() {
		return codSituacaoVerificacao;
	}

	public void setCodSituacaoVerificacao(String codSituacaoVerificacao) {
		this.codSituacaoVerificacao = codSituacaoVerificacao;
	}


	public void setShowAlteraVerificacao(boolean showAlteraVerificacao) {
		this.showAlteraVerificacao = showAlteraVerificacao;
	}
	
	public boolean isAlteracao() {
		return alteracao;
	}

	public void setAlteracao(boolean alteracao) {
		this.alteracao = alteracao;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getServicoVerificacao() {
		return servicoVerificacao;
	}

	public void setServicoVerificacao(String servicoVerificacao) {
		this.servicoVerificacao = servicoVerificacao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getFiltroCodVendedor() {
		return filtroCodVendedor;
	}

	public void setFiltroCodVendedor(String filtroCodVendedor) {
		this.filtroCodVendedor = filtroCodVendedor;
	}

	public String getFiltroNomeVendedor() {
		return filtroNomeVendedor;
	}

	public void setFiltroNomeVendedor(String filtroNomeVendedor) {
		this.filtroNomeVendedor = filtroNomeVendedor;
	}
	
	public void setNuIdentificadorCliente(String nuIdentificadorCliente) {
		if (nuIdentificadorCliente != null) {
			nuIdentificadorCliente = nuIdentificadorCliente.replaceAll("/", "");
			nuIdentificadorCliente = nuIdentificadorCliente.replaceAll("\\.", "");
			nuIdentificadorCliente = nuIdentificadorCliente.replaceAll("-", "");
			
			verificacaoPreventiva.setNuIdentificadorCliente(nuIdentificadorCliente);
		}
	}
	
	public String getFiltroNomeConveniado() {
		return filtroNomeConveniado;
	}

	public void setFiltroNomeConveniado(String filtroNomeConveniado) {
		this.filtroNomeConveniado = filtroNomeConveniado;
	}

	// -----------------------------------------------------------------
	// Constantes
	private static final String SERROR_DATA_INC_INI_NULA = "Data inicial do período de inclusão não pode ser nula";
	private static final String SERROR_DATA_INC_FIM_NULA = "Data final do período de inclusão não pode ser nula";
	private static final String SERROR_DATA_INC_FIM_MENOR = "Data final do período de inclusão não pode ser menor que a data inicial";
	private static final String SERROR_DATA_VER_INI_NULA = "Data inicial do período de verificação não pode ser nula";
	private static final String SERROR_DATA_VER_FIM_NULA = "Data final do período de verificação não pode ser nula";
	private static final String SERROR_DATA_VER_FIM_MENOR = "Data final do período de verificação não pode ser menor que a data inicial";
	public static final String IS_VERIFICACAO_PREVENTIVA = "isVerificacaoPreventiva";
	
	private Short getPerfilUserLogado() {
		return SegurancaUsuario.getInstance().getUsuario().getPerfis().get(0);
	}
	
	//---------------------Pagination begin--------------------------//
	private PaginationOnDemand pDemand = new PaginationOnDemand();
	
	public void update(Observable o, Object arg) {
		try {
			int count = 0;
			
				if(getFilterBase() != null && getFilterBase().isModoAvancado()){
					this.listaVerificacaoPreventiva = verificacaoPreventivaBO.advancedFilter(getUsuarioLogado(), getPerfilUserLogado(), getFilterBase(), pDemand.getParam());
					count = verificacaoPreventivaBO.advancedFilterCount(getUsuarioLogado(), getPerfilUserLogado(), getFilterBase(), pDemand.getParam());
				} else{
					this.listaVerificacaoPreventiva = verificacaoPreventivaBO.simpleFilter(getUsuarioLogado(), getUsuarioLogado().getPerfis().get(0), getFilterBase(), pDemand.getParam());
					count = verificacaoPreventivaBO.simpleFilterCount(getUsuarioLogado(), getUsuarioLogado().getPerfis().get(0), getFilterBase(), pDemand.getParam());
				}
			
			pDemand.setListItens(listaVerificacaoPreventiva, count);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}


	public PaginationOnDemand getpDemand() {
		return pDemand;
	}

	public void setpDemand(PaginationOnDemand pDemand) {
		this.pDemand = pDemand;
	}
	//----------------------Pagination end------------------------//
}
