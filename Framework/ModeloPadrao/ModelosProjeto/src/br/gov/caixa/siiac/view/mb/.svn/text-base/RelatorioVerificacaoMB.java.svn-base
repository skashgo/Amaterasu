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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.format.FormatUitils;
import br.gov.caixa.siiac.bo.IChecklistBO;
import br.gov.caixa.siiac.bo.IContratoBO;
import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.bo.IServicoVerificacaoBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.bo.IVerificacaoContratoBO;
import br.gov.caixa.siiac.bo.IVerificacaoPreventivaBO;
import br.gov.caixa.siiac.bo.impl.VerificacaoContratoBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.exception.ReportErrorCreateDatasourceException;
import br.gov.caixa.siiac.exception.ReportFinalNullException;
import br.gov.caixa.siiac.exception.ReportInvalidPathException;
import br.gov.caixa.siiac.model.FiltroVerificacaoPreventivaVO;
import br.gov.caixa.siiac.model.RelatorioVerificacaoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.util.FormatUtil;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

@Service()
@Scope("request")
public class RelatorioVerificacaoMB extends AbstractMB {

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
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	
	// Cadastro
	private String filtroCadastro;

	// Pesquisa Simples
	private String pesquisaString = "";
	private Boolean produtosPreferenciais = true;

	
	// Pesquisa Avançada
	private String filtroProduto;
	private String filtroUnidade;
	private String codProduto;
	private String codUnidade;
	private String filtroContrato;
	private String filtroDocumentos;
	private String filtroAvalSiric;
	private String filtroAvalCliente;
	private String idProdutoSelect;
	private String codServicoVerificacao;
	private String filtroUsuarioResp;
	private String codSituacao;
	private Boolean somenteSuspensas;
	private Date filtroDataIniInclusao;
	private Date filtroDataFimInclusao;
	private Date filtroDataIniVerificacao;
	private Date filtroDataFimVerificacao;
	private List<SelectItem> listProdutos = new ArrayList<SelectItem>();
	private List<SelectItem> listUnidades = new ArrayList<SelectItem>();
	private List<SelectItem> listServicoVerificacao = new ArrayList<SelectItem>();
	private List<SelectItem> selectItemPfPj;
	private List<SelectItem> selectItemTipoIdentificadorCliente;
	private Integer codConveniado;
	private String filtroNomeConveniado;
	private String filtroCodEmpreendimento;
	private String filtroNomeEmpreendimento;

	private List<Contrato> listaVerificacaoPreventiva;
	private Contrato verificacaoPreventiva = new Contrato();

	private boolean showInputIdentificacaoClienteVazio;
	private boolean showInputCPF;
	private boolean showInputCNPJ;
	private boolean showInputNIS;
	private boolean disableComboTpIdentificador = true;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static final String CONTRATO_SESSAO = "verificacaoPreventiva";
	
	@PostConstruct
	public void init() {
		try {
			loadListProdutos();
			loadListUnidades();
			setModoFiltro();
		} catch (Exception e) {
			LogCEF.error(e);
		}
	}

	// -----------------------------------------------------------------------------
	/**
	 * Realiza a consulta dos dados.
	 * 
	 * @throws DAOException
	 */
	public String doConsultarClick() {
		
		try {
			
			if (Utilities.notEmpty(filtroDataIniInclusao) || Utilities.notEmpty(filtroDataFimInclusao))
			{
				// Validando as datas
				if (Utilities.empty(filtroDataIniInclusao)) {
					error("Data inicial do período de inclusão não pode ser nula");
					return "";
				}

				if (Utilities.empty(filtroDataFimInclusao)) {
					error("Data final do período de inclusão não pode ser nula");
					return "";
				}
				

				// Comparando Datas entre si
				if (filtroDataFimInclusao.before(filtroDataIniInclusao)) {
					error("Data final do período de inclusão não pode ser menor que a data inicial");
					return "";
				}
			}
			
			if (Utilities.notEmpty(filtroDataIniVerificacao) || Utilities.notEmpty(filtroDataFimVerificacao))
			{
				if (Utilities.empty(filtroDataIniVerificacao)) {
					error("Data inicial do período de verificação não pode ser nula");
					return "";
				}

				if (Utilities.empty(filtroDataFimVerificacao)) {
					error("Data final do período de verificação não pode ser nula");
					return "";
				}


				if (filtroDataFimVerificacao.before(filtroDataIniVerificacao)) {
					error("Data final do período de verificação não pode ser menor que a data inicial");
					return "";
				}
			}

			setModoVisualiza();
				
			filtroContrato = filtroContrato.replace(".", "").replace("-", "");
			
			if (Utilities.notEmpty(filtroUsuarioResp))
			{
				String letra = filtroUsuarioResp.substring(0,1);
				
				Pattern patternNumbers = Pattern.compile("[^0-9]*");
								
				filtroUsuarioResp = letra + ConvertUtils.padZerosLeft(patternNumbers.matcher(filtroUsuarioResp).replaceAll(""), 6);
			}

			// Caso tudo esteja OK
			FiltroVerificacaoPreventivaVO filtro = new FiltroVerificacaoPreventivaVO(
					codProduto, codUnidade, filtroContrato, filtroAvalSiric,
					filtroAvalCliente, codServicoVerificacao,
					filtroUsuarioResp, codSituacao, filtroDocumentos,
					filtroCodEmpreendimento, filtroNomeEmpreendimento,
					filtroDataIniInclusao, filtroDataFimInclusao,
					filtroDataIniVerificacao, filtroDataFimVerificacao,
					codConveniado, somenteSuspensas, filtroNomeConveniado);
			
			
			List<Contrato> busca = verificacaoPreventivaBO.advancedFilter(
					getUsuarioLogado(), obtemPerfil(), filtro, null);
				
			if (busca.isEmpty()) {
				warn(MSGS, MN016);
				return "";
			}
			listaVerificacaoPreventiva = new ArrayList<Contrato>();
			listaVerificacaoPreventiva = busca;
			
		} catch (DAOException ex) {
			LogCEF.error(ex);
		}
		
		
		return "toListaVerificacao";

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

	public void limparCamposVerificacaoPreventiva() throws DAOException {
		verificacaoPreventiva = new Contrato();
		filtroProduto = "";
		filtroUnidade = "";
		codProduto = "";
		codUnidade = "";
		filtroContrato = "";
		filtroDocumentos = "";
		filtroAvalSiric = "";
		filtroAvalCliente = "";
		idProdutoSelect = "";
		codServicoVerificacao = "";
		filtroUsuarioResp = "";
		codSituacao = "";
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
		return "";
	}
	
	public String getDtContratoFormatada() {
		Date dtContrato = null;

		if (verificacaoPreventiva != null) {
			dtContrato = verificacaoPreventiva.getDtContrato();
		}

		return sdf.format(dtContrato);
	}
	
	public String doCancelarClick() {
		setModoFiltro();
		
		filtroProduto = "";
		filtroUnidade = "";
		codProduto = "";
		codUnidade = "";
		filtroContrato = "";
		filtroDocumentos = "";
		filtroAvalSiric = "";
		filtroAvalCliente = "";
		idProdutoSelect = "";
		codServicoVerificacao = "";
		filtroUsuarioResp = "";
		codSituacao = "";
		somenteSuspensas = false;
		filtroDataIniInclusao = null;
		filtroDataFimInclusao = null;
		filtroDataIniVerificacao = null;
		filtroDataFimVerificacao = null;
		codConveniado = null;
		filtroNomeConveniado = "";
		filtroCodEmpreendimento = "";
		filtroNomeEmpreendimento = "";
		
		return "toRelVerificacao";
	}
	
	private boolean errorDownload = false;
	
	public boolean isErrorDownload() {
		return errorDownload;
	}

	public void setErrorDownload(boolean errorDownload) {
		this.errorDownload = errorDownload;
	}
	
	public String geraPDF() {
		try {
			doGerarRelatorio("pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String exportaXLS() {
		try {
			doGerarRelatorio("xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doGerarRelatorio(String tipo) {
		byte[] b;
		String matricula = SegurancaUsuario.getInstance().getUsuario().getMatricula();
		List<RelatorioVerificacaoVO> list = new ArrayList<RelatorioVerificacaoVO>();
		Map param = new HashMap();
		
		String dtIniInclusao = "";
		String dtFimInclusao = "";
		String dtIniVerif = "";
		String dtFimVerif = "";
		
		if (Utilities.notEmpty(getFiltroDataIniInclusao()))
			dtIniInclusao = sdf.format(getFiltroDataIniInclusao());
		
		if (Utilities.notEmpty(getFiltroDataFimInclusao()))
			dtFimInclusao = sdf.format(getFiltroDataFimInclusao());
		
		if (Utilities.notEmpty(getFiltroDataIniVerificacao()))
			dtIniVerif = sdf.format(getFiltroDataIniVerificacao());
		
		if (Utilities.notEmpty(getFiltroDataFimVerificacao()))
			dtFimVerif = sdf.format(getFiltroDataFimVerificacao());
		
		String unidade = "";
		if(codUnidade != null && codUnidade.length()>0){
			unidade = ConvertUtils.padZerosLeft(codUnidade, 4);
		}
		param.put("unidade", unidade);
		param.put("contrato", getFiltroContrato());
		param.put("produto", getFiltroProduto());
		param.put("docs", getFiltroDocumentos());
		param.put("avOperacao", getFiltroAvalSiric());
		param.put("avTomador", getFiltroAvalCliente());
		param.put("usuario", getFiltroUsuarioResp());
		param.put("situacao", getCodSituacao());
		param.put("peInclusaoIni", dtIniInclusao);
		param.put("peInclusaoFim", dtFimInclusao);
		param.put("codConveniado", verificacaoPreventiva.getNuIdentificadorCliente());
		param.put("nomeConveniado", verificacaoPreventiva.getNuIdentificadorCliente());
		param.put("codEmpreend", verificacaoPreventiva.getNuIdentificadorCliente());
		param.put("nomeEmpreend", verificacaoPreventiva.getNuIdentificadorCliente());
		param.put("peVerifIni", dtIniVerif);
		param.put("peVerifFim", dtFimVerif);
		param.put("servVerificacao", getCodServicoVerificacao());
		
		for(Contrato c : listaVerificacaoPreventiva) {
			RelatorioVerificacaoVO rel = new RelatorioVerificacaoVO();
			rel.setSituacao(verificacaoContratoBO.obtemSituacaoContrato(c.getNuContrato()).toString());
			rel.setCoUnidade(c.getNuUnidade());
			rel.setCoProduto(c.getProduto().getCodigoFormatado());
			rel.setCoContrato(c.getCoContrato());
			rel.setNoCliente(c.getNoCliente());
			rel.setNuIdentificador(c.getNuIdentificadorCliente());
			rel.setDtFormatada(c.getDtContrato());
			if(c.getVrNominalContrato()!= null) {
				rel.setValorVerif(c.getVrNominalContrato().doubleValue());
			}
			
			list.add(rel);
		}
		
		try {
			b = verificacaoPreventivaBO.geraRelatorio(param, tipo, getCaminhoRelatorio(), matricula, list);
			String nomeArquivo = "relatorio_verificacao." + tipo;
			addRelatorioSessao(b, nomeArquivo);
			LogCEF.debug("Relatorio gerado com sucesso");
		} catch (JRException e) {
			error("Erro ao gerar relatório.");
			LogCEF.error(e.getMessage());
			errorDownload = true;
		} catch (ReportInvalidPathException e) {
			error("Erro ao gerar relatório.");
			LogCEF.error(e.getMessage());
			errorDownload = true;
		} catch (ReportErrorCreateDatasourceException e) {
			error("não existe dados para gerar este relatório.");
			LogCEF.error(e.getMessage());
			errorDownload = true;
		} catch (ReportFinalNullException e) {
			error("Erro ao gerar relatório.");
			LogCEF.error(e.getMessage());
			errorDownload = true;
		} catch (DAOException e) {
			error("Erro ao gerar relatório.");
			LogCEF.error(e.getMessage());
			errorDownload = true;
		}
	}
	
	public String downloadRel() {
		try {
			downloadRelatorio();
		} catch (IOException e) {
			error("Erro ao gerar relatório.");
			LogCEF.error(e.getMessage());
		}
		return "";
	}	



	// -----------------------------------------------------------------------------
	// Variáveis de controle
	
	public boolean isShowPanelVisualizacao() {
		return isModoVisualiza();
	}
	
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
		return isModoCadastro();
	}
	
	public boolean isShowTabela() {
		return isModoVisualiza();
	}

	public boolean getShowConsultarAvancado() {
		return isModoFiltro();
	}

	public void doConsultarAvancado(ActionEvent evt) {
		setModoFiltro();
	}

	public void doConsultarSimples(ActionEvent evt) {
		setModoFiltro();
	}

	public void changeComboPfPj(ActionEvent ev) {
		verificacaoPreventiva.setNuIdentificadorCliente(null);
		if (verificacaoPreventiva.getIcPfPj() != null
				&& !verificacaoPreventiva.getIcPfPj().equals("")) {
			if (verificacaoPreventiva.getIcPfPj().trim()
					.equals(Contrato.TIPO_PESSOA_FISICA_ID)) {
				setDisableComboTpIdentificador(false);
				setShowInputNIS(true);
				setShowInputCNPJ(false);
				setShowInputCPF(false);
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
		verificacaoPreventiva.setNuIdentificadorCliente(null);
		if (verificacaoPreventiva.getIcTipoIndentificadorCliente() != null
				&& !verificacaoPreventiva.getIcTipoIndentificadorCliente()
						.equals("")) {
			
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

		produtos = contratoBO.getLikeProduto("");


		if(listProdutos == null) listProdutos = new ArrayList<SelectItem>();
		listProdutos.clear();
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
		
	}

	/**
	 * Preenche o combo de produtos.
	 * 
	 * @return lista de itens de seleção de produtos
	 * @throws DAOException
	 */
	public List<SelectItem> getListUnidades() throws DAOException {

		List<SelectItem> list = new ArrayList<SelectItem>();
		List<Unidade> unidades;
		
		Short nuPerfil = getUsuarioLogado().getPerfis().get(0);
		Short nuUnidade = getUsuarioLogado().getUnidade();
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
			list.add(new SelectItem(u.getId().getNuUnidade(), ConvertUtils.padZerosLeft(String.valueOf(u.getId().getNuUnidade()), 4) + " - " + u.getNomeAbreviado()));
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

	public List<SelectItem> getListSituacao() {
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
			try {
				this.listaVerificacaoPreventiva = verificacaoPreventivaBO.simpleFilter(getUsuarioLogado(), obtemPerfil(),
								pesquisaString, produtosPreferenciais, null);
			} catch (DAOException ex) {
				LogCEF.error(ex);
			}
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

	protected IUsuario getUsuarioLogado() {
		return SegurancaUsuario.getInstance().getUsuario();
	}

	private Short obtemPerfil() {
		return getUsuarioLogado().getPerfis().get(0);
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
		if (situacao.equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_CONFORME))
		{
			return CabecalhoDetalhesVerificacaoMB.ICON_CONFORME;
		} else if (situacao.equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_INCONFORME))
		{
			return CabecalhoDetalhesVerificacaoMB.ICON_INCONFORME;
		} else if (situacao.equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_VERIFICACAO_PARCIAL))
		{
			return CabecalhoDetalhesVerificacaoMB.ICON_VERIFICACAO_PARCIAL;
		} else if (situacao.equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_NAO_VERIFICADA))
		{
			return "/images/transparente.gif";
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

	public String getFiltroNomeConveniado() {
		return filtroNomeConveniado;
	}

	public void setFiltroNomeConveniado(String filtroNomeConveniado) {
		this.filtroNomeConveniado = filtroNomeConveniado;
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

	public static final String IS_VERIFICACAO_PREVENTIVA = "isVerificacaoPreventiva";
	
	private Short getPerfilUserLogado() {
		return SegurancaUsuario.getInstance().getUsuario().getPerfis().get(0);
	}
}
