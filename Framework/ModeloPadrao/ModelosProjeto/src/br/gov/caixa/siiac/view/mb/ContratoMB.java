package br.gov.caixa.siiac.view.mb;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.IAtualizaPrazoVerificacaoBO;
import br.gov.caixa.siiac.bo.ICanalComercialProdutoBO;
import br.gov.caixa.siiac.bo.IContratoBO;
import br.gov.caixa.siiac.bo.IGarantiaBO;
import br.gov.caixa.siiac.bo.IGeraAgendaBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.bo.impl.MatrizAcessoBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.model.domain.CanalComercializacaoProduto;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.DetalhesContrato;
import br.gov.caixa.siiac.model.domain.Garantia;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.siiac.util.Valida;
import br.gov.caixa.siiac.view.mb.pagination.IPaginationOnDemand;
import br.gov.caixa.siiac.view.mb.pagination.PaginationOnDemand;
import br.gov.caixa.util.Utilities;

@Service()
@Scope("request")
public class ContratoMB extends AbstractMB implements IPaginationOnDemand{
	
	/**
	 * Filtro Simples
	 */
	private String pesquisaString;
	private Boolean pesquisaMostraInativos;
	
	private static final String CARREGA_CONTRATO = "carregaContrato";
	private static final String CARREGA_GARANTIA = "carregaGarantia";
	private static final String GARANTIA = "garantia";
	private static final String ACTION = "action";
	private static final char ACTION_CAD = 'c';
	private static final char ACTION_ALT = 'a';
	private static final char ACTION_VIEW = 'v';
	public static String MENSAGEM_CAMPO_VALIDACAO = "";
	private Contrato contrato = new Contrato();
	private Contrato contratoImportado;
	private IGarantiaBO garantiaBO;
	private ICanalComercialProdutoBO canalComercialProdutoBO;
	private IContratoBO contratoBO;
	private IUnidadeBO unidadeBO;
	private List<SelectItem> selectItemPfPj;
	private List<SelectItem> selectItemTipoIdentificadorCliente;
	private List<SelectItem> selectItemTpCanalComerc;
	private List<Contrato> listContrato;
	private String nuUnidade;
	private String coProduto;
	private Short campoOperacao;
	private String mascaraTipoIdentificadorCliente;
	private BigDecimal valorInicial;
	private BigDecimal valorFinal;
	private Boolean carregaContrato;
	private boolean showInputIdentificacaoClienteVazio;
	private boolean showInputCPF;
	private boolean showInputCNPJ;
	private boolean showInputNIS;
	private boolean disableComboTpIdentificador = true;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	

	@Autowired
	private IGeraAgendaBO geraAgendaBO;
	@Autowired
	private IAtualizaPrazoVerificacaoBO atualizaPrazoVerificacaoBO;

	public ContratoMB() {
		showInputIdentificacaoClienteVazio = true;
		contrato.setIcAtivo(Boolean.TRUE);
		carregaContrato = (Boolean) getHttpSession().getAttribute(CARREGA_CONTRATO);
		if (carregaContrato == null) {
			carregaContrato = false;
		}
		getFilterInSession(this.getClass());
	}
	
	@Autowired
	public void setUnidadeBO(IUnidadeBO unidadeBO) {
		this.unidadeBO = unidadeBO;
	}
	
	@Autowired
	public void setContratoBO(IContratoBO contratoBO) {
		this.contratoBO = contratoBO;
	}
	
	@Autowired
	public void setCanalComercialProdutoBO(ICanalComercialProdutoBO canalComercialProdutoBO) {
		this.canalComercialProdutoBO = canalComercialProdutoBO;
	}
	
	@Autowired
	public void setGarantiaBO(IGarantiaBO garantiaBO) {
		this.garantiaBO = garantiaBO;
		inicializa();
		init();
	}
	
	private void inicializa() {
		if (getHttpSession().getAttribute(CARREGA_GARANTIA) != null) {
			doVisualizar(null);
			return;
		}
		modoFiltro();
	}
	
	public Contrato getContratoImportado() {
		return contratoImportado;
	}
	
	public String getNuIdentificadorCliente() {
		return formataDocumento(contrato);
	}
	
	public void setNuIdentificadorCliente(String nuIdentificadorCliente) {
		if (nuIdentificadorCliente != null) {
			nuIdentificadorCliente = nuIdentificadorCliente.replaceAll("/", "");
			nuIdentificadorCliente = nuIdentificadorCliente.replaceAll("\\.", "");
			nuIdentificadorCliente = nuIdentificadorCliente.replaceAll("-", "");
			
			contrato.setNuIdentificadorCliente(nuIdentificadorCliente);
		}
	}
	
	public void formataUnidade(ActionEvent evt) {
		nuUnidade = nuUnidade.replace("_", "");
		if (nuUnidade != null && !nuUnidade.equals("") && Integer.parseInt(nuUnidade) != 0) {
			nuUnidade = ConvertUtils.padZerosLeft(nuUnidade, 4);
		}
	}
	
	public void setContratoImportado(Contrato contratoImportado) {
		this.contratoImportado = contratoImportado;
	}
	
	public String getNuUnidade() {
		return nuUnidade;
	}
	
	public void setNuUnidade(String nuUnidade) {
		this.nuUnidade = nuUnidade;
	}
	
	public String getCoProduto() {
		return coProduto;
	}
	
	public void setCoProduto(String coProduto) {
		this.coProduto = coProduto;
	}
	
	public String getMascaraTipoIdentificadorCliente() {
		return mascaraTipoIdentificadorCliente;
	}
	
	public void setMascaraTipoIdentificadorCliente(String mascaraTipoIdentificadorCliente) {
		this.mascaraTipoIdentificadorCliente = mascaraTipoIdentificadorCliente;
	}
	
	public BigDecimal getValorInicial() {
		return valorInicial;
	}
	
	public void setValorInicial(BigDecimal valorInicial) {
		this.valorInicial = valorInicial;
	}
	
	public BigDecimal getValorFinal() {
		return valorFinal;
	}
	
	public void setValorFinal(BigDecimal valorFinal) {
		this.valorFinal = valorFinal;
	}
	
	public Contrato getContrato() {
		return this.contrato;
	}
	
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
		
	public String doGravarListener(ActionEvent evt)
	{
		doGravar();
		return "";		
	}
	
	public void doLimpar(ActionEvent evt)
	{
		nuUnidade = "";
		coProduto = "";
		pesquisaString = "";
		pesquisaMostraInativos = false;
		campoOperacao = null;
		valorInicial = null;
		valorFinal = null;
		contrato = new Contrato();
		
		//limpa o filtro na sessão.
		getFilterBase().limparFiltros();
		pDemand.atualizaList();
	}
	
	public void doValidaCPF_CNPJ(ActionEvent evt) {
		if (contrato.getNuIdentificadorCliente() != null || !contrato.getNuIdentificadorCliente().toString().equals("")) {
			if (contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID)) {
				if (!Valida.isValidCNPJ(contrato.getNuIdentificadorCliente())) {
					warn("CNPJ digitado Inválido!");
				}
			} else {
				if (contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CPF_ID)) {
					if (!Valida.isValidCPF(contrato.getNuIdentificadorCliente())) {
						warn("CPF digitado Inválido!");
					}
				}
			}
		}
	}
	
	public void doGravar() {
		boolean update = false;
		Contrato contratoAntigo = null;
		if(contrato.getVrNominalContrato() != null && String.valueOf(contrato.getVrNominalContrato().longValue()).length()>14){
			warn("Valor Nominal ultrapassou a quantidade máxima de digitos");
			return;
		}
		if(contrato.getDetalhesContrato().getVrLiquidoContrato() != null && String.valueOf(contrato.getDetalhesContrato().getVrLiquidoContrato().longValue()).length()>14){
			warn("Valor Liquido ultrapassou a quantidade máxima de digitos");
			return;
		}
		if (Utilities.notEmpty(nuUnidade)) {
			contrato.setNuUnidade(Short.valueOf(nuUnidade));
			nuUnidade = ConvertUtils.padZerosLeft(nuUnidade, 4);
		}
		if (Utilities.notEmpty(coProduto)) {
			contrato.getProduto().setCoProduto(coProduto.replace("-", ""));
		}
		if (contratoBO.validaContrato(contrato)) {
			try {
				contrato.setDtInclusao(new Date());
				if (contrato.getNuContrato() != null) {
					update = true;
					contratoAntigo = contratoBO.findById(contrato.getNuContrato());
					contrato.setIcAgendaGerada(contratoBO.verificaCamposAlteradosAgenda(contratoAntigo, contrato));
					contrato.setIcAtivo(Boolean.TRUE);
				} else {
					Integer nuNatural = unidadeBO.getNuNaturalUnidade(contrato.getNuUnidade());
					if (nuNatural != null) {
						contrato.setNuNatural(nuNatural);
					}
					contrato.setIcPrazoVerificacao(Contrato.PRAZO_VERIFICACAO_ID_CONTRATO_A_VERIFICAR);
					contrato.setIcAgendaGerada(Contrato.AGENDA_GERADA_ID_INSERIDA);
				}
				contrato.setIcTipoContrato('0');
				contrato.setIcTipoVerificacaoContrato(Contrato.TIPO_VERIFICACAO_CONTRATO_ID_CONTRATO);
				contrato = contratoBO.save(contrato);

				if(update){
					info(MSGS, MN008);
				}else{
					info(MSGS, MN007);
				}
				
				if(! Contrato.AGENDA_GERADA_ID_GERACAO_OK.equals(contrato.getIcAgendaGerada())){
					geraAgendaBO.geraAgendaUmContrato(contrato.getNuContrato());
					atualizaPrazoVerificacaoBO.atualizaPrazoVerificao(contrato.getNuContrato());
				}
				contrato = new Contrato();
				contrato.setIcAtivo(Boolean.TRUE);
				nuUnidade = "";
				coProduto = "";
				pesquisaString = "";
				pesquisaMostraInativos = false;
				modoFiltro();
				atualizaList();
			} catch (Exception ex) 
			{
				error(getMessage(MSGS, "MN002")+": "+ex.getMessage());
				LogCEF.error(ex);
			}
		} else {
			error(MSGS, MN002);
			warn(ContratoMB.MENSAGEM_CAMPO_VALIDACAO);
		}
		
		return;
	}
	
	public String doCadastrarGarantia() {
		if (getHttpSession() != null) {
			Garantia garantia = new Garantia();
			garantia.setContrato(contrato);
			getHttpSession().setAttribute(GARANTIA, garantia);
			getHttpSession().setAttribute(CARREGA_GARANTIA, true);
			getHttpSession().setAttribute(ACTION, ACTION_CAD);
		}
		
		return "toGarantia";
	}
	
	public void doConsultarClick(ActionEvent evt) {
		contratoImportado = null;
		if (isModoInicio()) {
			pDemand.atualizaList();
		} else {
			if (nuUnidade != null && !nuUnidade.equals("")) {
				contrato.setNuUnidade(Short.valueOf(nuUnidade));
			}
			coProduto = coProduto.replace("-", "");
			if (coProduto != null && !coProduto.equals("")) {
				contrato.getProduto().setCoProduto(coProduto);
			}
			pDemand.atualizaList();
			if (nuUnidade != null && !nuUnidade.equals("")) {
				nuUnidade = ConvertUtils.padZerosLeft(nuUnidade, 4);
			}
		}
		if (listContrato.isEmpty()) {
			warn(MSGS, MN016);
		}
	}
	
	public void doDesabilitar(ActionEvent evt) {
		contrato = (Contrato) getRequestMap().get("contrato");
		try {
			if (contrato.getIcAtivo().equals(Boolean.TRUE)) {
				contrato.setIcAtivo(Boolean.FALSE);
				info(MSGS, MN011);
			} else {
				contrato.setIcAtivo(Boolean.TRUE);
				info(MSGS, MN012);
			}
			contrato = contratoBO.save(contrato);
			contrato = new Contrato();
			contrato.setIcAtivo(Boolean.TRUE);
			modoFiltro();
			atualizaList();
		} catch (Exception e) {
			error("msgcontrato", "error_save");
			LogCEF.error(e.getMessage());
		}
	}
	
	public String doAlterar() {
		contrato = (Contrato) getRequestMap().get("contrato");
		if (contrato.getIcAtivo().equals(Boolean.FALSE)) {
			contrato = new Contrato();
			contrato.setIcAtivo(Boolean.FALSE);
			error(MSGS, MN013);
		} else {
			if (contrato.getDetalhesContrato() != null) {
				if (contrato.getIcSituacaoContaContrato() != null)
					contrato.setIcSituacaoContaContrato(contrato.getIcSituacaoContaContrato().trim());
			} else {
				contrato.setDetalhesContrato(new DetalhesContrato());
			}
			setValorInicial(null);
			setValorFinal(null);
			setCoProduto(contrato.getProduto().getCoProduto());
			setModoAltera();
		}
		
		return "";
	}
	
	public void doVisualizar(ActionEvent evt) {
		try {
			if (getHttpSession().getAttribute(GARANTIA) != null) {
				contrato = ((Garantia) getHttpSession().getAttribute(GARANTIA)).getContrato();
				contrato.setGarantias(garantiaBO.getAllGarantiasInContrato(contrato));
				setModoVisualiza();
				return;
			}
			setValorInicial(null);
			setValorFinal(null);
			contrato = (Contrato) getRequestMap().get("contrato");
			contrato.setGarantias(garantiaBO.getAllGarantiasInContrato(contrato));
			setModoVisualiza();
		} catch (DAOException ex) {
			LogCEF.error(ex);
		}
	}
	
	public void doInativar(ActionEvent evt) {
		Garantia garantia = (Garantia) getRequestMap().get("row");
		try {
			garantiaBO.inativar(garantia);
			info(MSGS, MN009);
			contrato.setGarantias(garantiaBO.getAllGarantiasInContrato(contrato));
		} catch (Exception e) {
			LogCEF.error("Erro ao Inativar: " + e.getMessage());
			error(MSGS, MN002);
		}
		
		limpaSessao();
	}
	
	public String doVisualizarGarantia() {
		Garantia garantia = (Garantia) getRequestMap().get("row");
		getHttpSession().setAttribute(ACTION, ACTION_VIEW);
		getHttpSession().setAttribute(GARANTIA, garantia);
		getHttpSession().setAttribute(CARREGA_GARANTIA, true);
		return "toGarantia";
	}
	
	public void doCadastrar(ActionEvent evt) {
		setModoCadastro();
		contrato = new Contrato();
		contrato.setIcAtivo(Boolean.TRUE);
		setValorInicial(null);
		setValorFinal(null);
		setShowInputIdentificacaoClienteVazio(true);
		setShowInputCNPJ(false);
		setShowInputCPF(false);
		setShowInputNIS(false);
		setDisableComboTpIdentificador(true);
		coProduto = "";
		nuUnidade = "";
	}
	
	public String doAlterarGarantia() {
		Garantia garantia = (Garantia) getRequestMap().get("row");
		getHttpSession().setAttribute(ACTION, ACTION_ALT);
		getHttpSession().setAttribute(GARANTIA, garantia);
		getHttpSession().setAttribute(CARREGA_GARANTIA, true);
		return "toGarantia";
	}
	
	public void doCancelar(ActionEvent ev) {
		contrato = new Contrato();
		setNuUnidade(null);
		modoFiltro();
		atualizaList();
		warn(MSGS, MN003);
		limpaSessao();
	}
	
	private void atualizaList() {
		try {
			pDemand.atualizaList();
		} catch (Exception ex) {
			LogCEF.error(ex);
		}
	}
	
	public List<SelectItem> getSelectItemAtivosInativos() {
		List<SelectItem> itens = new ArrayList<SelectItem>();
		itens.add(new SelectItem(Contrato.SIMNAO_ID_SIM, Contrato.SIMNAO_LABEL_SIM));
		itens.add(new SelectItem(Contrato.SIMNAO_ID_NAO, Contrato.SIMNAO_LABEL_NAO));
		
		return itens;
	}
	
	public List<String> selectItemProdutos(Object object) {
		String value = object.toString().replace("_", "").replace("-", "");
		List<String> produtosStr = new ArrayList<String>();
		List<Produto> produtos = new ArrayList<Produto>();
		if (!value.equals("") && !value.equals("null")) {
			contrato.getProduto().setCoProduto(value);
		} else {
			contrato.getProduto().setCoProduto(null);
			value = "";
		}
		try {
			produtos = contratoBO.getLikeProduto(value);
		} catch (Exception e) {
			LogCEF.error("Erro ao buscar unidades");
			error(MSGS, MN002);
		}
		for (Produto p : produtos) {
			produtosStr.add(p.getCodigoFormatado() + "-" + p.getNoProduto());
		}
		return produtosStr;
	}
	
	public List<String[]> selectItemContratoUnidade(Object objeto) {
		String value = objeto.toString().replace("_", "");
		List<String[]> unidadeStr = new ArrayList<String[]>();
		List<Unidade> unidades = new ArrayList<Unidade>();
		if (!value.equals("") && !value.equals("null")) {
			contrato.setNuUnidade(Short.valueOf(value));
		} else {
			contrato.setNuUnidade(null);
		}
		try {
			unidades = unidadeBO.getLikeNuUnidade(value);
		} catch (Exception e) {
			LogCEF.error("Erro ao buscar unidades: " + e.getMessage());
			error(MSGS, MN002);
		}
		for (Unidade u : unidades) {
			String[] s = new String[2];
			s[0] = String.valueOf(u.getId().getNuUnidade());
			s[1] = u.getNomeAbreviado();
			unidadeStr.add(s);
		}
		
		return unidadeStr;
	}
	
	public List<SelectItem> getSelectItemPfPj() {
		if (selectItemPfPj == null) {
			selectItemPfPj = new ArrayList<SelectItem>();
			selectItemPfPj.add(new SelectItem("", "Selecione..."));
			selectItemPfPj.add(new SelectItem(Contrato.TIPO_PESSOA_FISICA_ID, Contrato.TIPO_PESSOA_FISICA_LABEL));
			selectItemPfPj.add(new SelectItem(Contrato.TIPO_PESSOA_JURIDICA_ID, Contrato.TIPO_PESSOA_JURIDICA_LABEL));
		}
		
		return selectItemPfPj;
	}
	
	public List<SelectItem> getSelectItemTipoIdentificadorCliente() {
		selectItemTipoIdentificadorCliente = new ArrayList<SelectItem>();
		
		if (contrato.getIcPfPj() != null && !contrato.getIcPfPj().equals("")) {
			if (contrato.getIcPfPj().equals(Contrato.TIPO_PESSOA_FISICA_ID)) {
				selectItemTipoIdentificadorCliente.add(new SelectItem(Contrato.TIPO_IDENTIFICADOR_CPF_ID, Contrato.TIPO_IDENTIFICADOR_CPF_LABEL));
				selectItemTipoIdentificadorCliente.add(new SelectItem(Contrato.TIPO_IDENTIFICADOR_NIS_ID, Contrato.TIPO_IDENTIFICADOR_NIS_LABEL));
			} else {
				selectItemTipoIdentificadorCliente.add(new SelectItem(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID, Contrato.TIPO_IDENTIFICADOR_CNPJ_LABEL));
			}
		}
		
		return selectItemTipoIdentificadorCliente;
	}
	
	public List<SelectItem> getSelectItemSituacoes() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem("", "Selecione..."));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_ATIVO, Contrato.SITUACAO_CONTRATO_LABEL_ATIVO));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_INDME, Contrato.SITUACAO_CONTRATO_LABEL_INDME));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_LQDCO, Contrato.SITUACAO_CONTRATO_LABEL_LQDCO));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_LQDDO, Contrato.SITUACAO_CONTRATO_LABEL_LQDDO));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_VNCDO, Contrato.SITUACAO_CONTRATO_LABEL_VNCDO));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_EECRR, Contrato.SITUACAO_CONTRATO_LABEL_EECRR));
		list.add(new SelectItem(Contrato.SITUACAO_CONTRATO_ID_IRRGL, Contrato.SITUACAO_CONTRATO_LABEL_IRRGL));
		
		return list;
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
	
	public void changeComboPfPj(ActionEvent ev) {
		contrato.setNuIdentificadorCliente(null);
		if (contrato.getIcPfPj() != null && !contrato.getIcPfPj().equals("")) {
			if (contrato.getIcPfPj().trim().equals(Contrato.TIPO_PESSOA_FISICA_ID)) {

				contrato.setIcTipoIndentificadorCliente(Contrato.TIPO_IDENTIFICADOR_CPF_ID);
				setDisableComboTpIdentificador(false);
			
				setShowInputNIS(contrato.getIcTipoIndentificadorCliente() != null && contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_NIS_ID));
				setShowInputCPF(contrato.getIcTipoIndentificadorCliente() != null && contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CPF_ID));
				setShowInputCNPJ(contrato.getIcTipoIndentificadorCliente() != null && contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID));
				setShowInputIdentificacaoClienteVazio(false);
				return;
			} else {
				setShowInputNIS(false);
				setShowInputCNPJ(true);
				setShowInputCPF(false);
				setDisableComboTpIdentificador(true);
				setShowInputIdentificacaoClienteVazio(false);
				contrato.setIcTipoIndentificadorCliente(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID);
				return;
			}
		}
		setShowInputNIS(false);
		setShowInputCNPJ(false);
		setShowInputCPF(false);
		setShowInputIdentificacaoClienteVazio(true);
		setDisableComboTpIdentificador(true);
		contrato.setIcTipoIndentificadorCliente("");
	}
	
	public void changeComboTpIdentificador(ActionEvent ev) {
		contrato.setNuIdentificadorCliente(null);
		if (contrato.getIcTipoIndentificadorCliente() != null && !contrato.getIcTipoIndentificadorCliente().equals("")) {
			if (contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CPF_ID)) {
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
	
	public String getUnidadeFormatadaGrid() {
		Garantia garantia = (Garantia) getRequestMap().get("row");
		if (garantia.getContrato().getNuUnidade() == null || garantia.getContrato().getNuUnidade() == 0) {
			return "";
		}
		return ConvertUtils.padZerosLeft(String.valueOf(garantia.getContrato().getNuUnidade()), 4);
	}
	
	public String getDescricaoFormatadaGrid() {
		Garantia garantia = (Garantia) getRequestMap().get("row");
		if (garantia.getDeGarantia().equals("") || garantia.getDeGarantia().length() < 20) {
			return garantia.getDeGarantia();
		}
		return garantia.getDeGarantia().substring(0, 17) + "...";
	}
	
	public String getTipoGarantiaFormatadaGrid() {
		Garantia garantia = (Garantia) getRequestMap().get("row");
		if (garantia.getTipoGarantia() == null || garantia.getTipoGarantia().getNuTipoGarantia() == null) {
			return "";
		}
		if (garantia.getTipoGarantia().getNoTipoGarantia().equals("") || garantia.getTipoGarantia().getNoTipoGarantia().length() < 20) {
			return garantia.getTipoGarantia().getNoTipoGarantia();
		}
		return garantia.getTipoGarantia().getNoTipoGarantia().substring(0, 17) + "...";
	}
	
	private void limpaSessao() {
		getHttpSession().setAttribute(GARANTIA, null);
		getHttpSession().setAttribute(ACTION, null);
		getHttpSession().setAttribute(CARREGA_GARANTIA, null);
	}
	
	public String getMascaraIdentificadorCliente() {
		return this.mascaraTipoIdentificadorCliente;
	}
	
	public boolean isShowPanelAltera() {
		return isModoAltera();
	}
	
	public boolean isShowPanelCadastro() {
		return isModoCadastro();
	}
	
	public boolean isShowPanelFiltro() {
		return isModoInicio() || isModoFiltro();
	}
	
	public boolean isShowPanelLista() {
		return isModoInicio() || isModoFiltro();
	}
	
	public boolean isShowPanelListaGarantia() {
		return isModoVisualiza();
	}
	
	public boolean isShowButtonCadastro() {
		if(! isModoInicio() && ! isModoFiltro()){
			return false;
		}
		
		return MatrizAcessoBO.acessoModuloMantemContrato(
					getUsuarioLogado().getPerfis().get(0), 
					MatrizAcessoBO.ACAO_PERFIL, 
					MatrizAcessoBO.TIPO_ACAO_INSERE);
	}
	
	public boolean isShowButtonAlterar() {
		if (carregaContrato != null && carregaContrato) {
			return false;
		}
		
		if(! isModoInicio() && ! isModoFiltro()){
			return false;
		}
		Contrato c = (Contrato) getRequestMap().get("contrato");
		
		return MatrizAcessoBO.acessoModuloMantemContrato(
					getUsuarioLogado().getPerfis().get(0), 
					MatrizAcessoBO.ACAO_PERFIL, 
					MatrizAcessoBO.TIPO_ACAO_ALTERA) && c.getIcTipoContrato().equals('0');
	}
	
	public boolean isShowButtonDesabilitarHabilitar() {
		if (carregaContrato != null && carregaContrato) {
			return false;
		}
		
		if(! isModoInicio() && ! isModoFiltro()){
			return false;
		}
		
		return MatrizAcessoBO.acessoModuloMantemContrato(
					getUsuarioLogado().getPerfis().get(0), 
					MatrizAcessoBO.ACAO_PERFIL, 
					MatrizAcessoBO.TIPO_ACAO_EXCLUI);
	}
	
	public boolean isShowButtonCadastroGarantia() {
		if (MatrizAcessoBO.acessoModuloMantemGarantia(
				getUsuarioLogado().getPerfis().get(0), 
				MatrizAcessoBO.ACAO_PERFIL, 
				MatrizAcessoBO.TIPO_ACAO_INSERE)){
			
			return contrato.getIcAtivo() != null && contrato.getIcAtivo().equals(Boolean.TRUE);
		}
		
		return false;
	}
	
	public boolean isShowButtonAlterarGarantia() {
		if (MatrizAcessoBO.acessoModuloMantemGarantia(
				getUsuarioLogado().getPerfis().get(0), 
				MatrizAcessoBO.ACAO_PERFIL, 
				MatrizAcessoBO.TIPO_ACAO_ALTERA)){
			
			return contrato.getIcAtivo() != null && contrato.getIcAtivo().equals(Boolean.TRUE);
		}
		
		return false;
	}
	
	public boolean isShowButtonExcluirGarantia() {
		if (MatrizAcessoBO.acessoModuloMantemGarantia(
				getUsuarioLogado().getPerfis().get(0), 
				MatrizAcessoBO.ACAO_PERFIL, 
				MatrizAcessoBO.TIPO_ACAO_EXCLUI)){
			
			return contrato.getIcAtivo() != null && contrato.getIcAtivo().equals(Boolean.TRUE);
		}
		
		return false;
	}
	
	public boolean isShowButtonCarregaContrato() {
		return carregaContrato != null && carregaContrato;
	}
	
	public boolean isShowPanelVisualizacao() {
		return isModoVisualiza();
	}
	
	public boolean isShowInputIdentificacaoClienteVazio() {
		return this.showInputIdentificacaoClienteVazio;
	}
	
	public void setShowInputIdentificacaoClienteVazio(boolean showInputIdentificacaoClienteVazio) {
		this.showInputIdentificacaoClienteVazio = showInputIdentificacaoClienteVazio;
	}
	
	public void setShowInputCPF(boolean showInputCPF) {
		this.showInputCPF = showInputCPF;
	}
	
	public boolean isShowInputCPF() {
		return this.showInputCPF;
	}
	
	public void setShowInputCNPJ(boolean showInputCNPJ) {
		this.showInputCNPJ = showInputCNPJ;
	}
	
	public boolean isShowInputCNPJ() {
		return this.showInputCNPJ;
	}
	
	public void setShowInputNIS(boolean showInputNIS) {
		this.showInputNIS = showInputNIS;
	}
	
	public boolean isShowInputNIS() {
		return this.showInputNIS;
	}
	
	public void setDisableComboTpIdentificador(boolean disable) {
		disableComboTpIdentificador = disable;
	}
	
	public boolean isDisableComboTpIdentificador() {
		return disableComboTpIdentificador;
	}
	
	/**
	 * MÉTODOS AUXILIARES PARA A FORMATAÇÃO DE CAMPOS
	 * NO DATAGRID DE CONTATOS.
	 * 
	 */
	public String getIcContaAbertaLoteFormatada() {
		if (contrato.getIcContaAbertaLote() != null) {
			if (contrato.getIcContaAbertaLote().equals(Boolean.TRUE)) {
				return "Sim";
			}
			
			return "Não";
		}
		
		return " ";
	}
	
	public String getCodProdutoFormatado() {
		String codProduto = "";
		
		if (isModoInicio() || isModoFiltro()) {
			Contrato contrato = (Contrato) getRequestMap().get("contrato");
			codProduto = contrato.getProduto().getCoProduto();
		} else {
			codProduto = contrato.getProduto().getCoProduto();
		}
		
		if (Utilities.notEmpty(codProduto) && Utilities.lengthEquals(codProduto, 7)) {
			// Valida se tem 7 caracteres pois a máscara de produto tem 7 caracteres.
			StringBuilder builder = new StringBuilder(codProduto);
			builder.insert(4, '-');
			return builder.toString();
		}
		
		return "";
		
	}
	
	public String getCodUnidadeFormatado() {
		String unidadeFormatada = "";
		if (isModoAltera() || isModoVisualiza()) {
			unidadeFormatada = contrato.getNuUnidade().toString();
		}
		
		if (isModoInicio() || isModoFiltro()) {
			Contrato contrato = (Contrato) getRequestMap().get("contrato");
			unidadeFormatada = contrato.getNuUnidade().toString();
		}
		return ConvertUtils.padZerosLeft(unidadeFormatada, 4);
	}
	
	public String getNomeUnidade(){
		Contrato contrato = (Contrato) getRequestMap().get("contrato");
		return unidadeBO.getNomeUnidadeByUnidade(contrato.getNuUnidade());
	}
	
	public String getDtContratoFormatada() {
		Date dtContrato = null;
		
		if (isModoInicio() || isModoFiltro()) {
			Contrato contrato = (Contrato) getRequestMap().get("contrato");
			if (contrato != null) {
				dtContrato = contrato.getDtContrato();
			}
		} else {
			if (contrato != null) {
				dtContrato = contrato.getDtContrato();
			}
		}
		
		if (Utilities.empty(dtContrato))
			return "";
		
		return sdf.format(dtContrato);
	}
	
	public String getDtVencimentoContratoFormatada() {
		Date dtVencimentoContrato = null;
		
		if (isModoAltera() || isModoVisualiza()) {
			if (contrato != null && contrato.getDetalhesContrato() != null) {
				dtVencimentoContrato = contrato.getDetalhesContrato().getDtVencimentoContrato();
				if (dtVencimentoContrato != null) {
					return sdf.format(dtVencimentoContrato);
				}
			}
		}
		
		return "";
	}
	
	public String getDtInadimplenciaContratoFormatada() {
		Date dtInadimplenciaContrato = null;
		
		if (isModoAltera() || isModoVisualiza()) {
			if (contrato != null && contrato.getDetalhesContrato() != null) {
				dtInadimplenciaContrato = contrato.getDetalhesContrato().getDtInadimplenciaContrato();
				if (dtInadimplenciaContrato != null) {
					return sdf.format(dtInadimplenciaContrato);
				}
			}
		}
		
		return "";
	}
	
	public String getDtUltimaRenovacaoContratoFormatada() {
		Date dtUltimaRenovacaoContrato = null;
		
		if (isModoAltera() || isModoVisualiza()) {
			if (contrato != null && contrato.getDetalhesContrato() != null) {
				dtUltimaRenovacaoContrato = contrato.getDetalhesContrato().getDtUltimaRenovacao();
				if (dtUltimaRenovacaoContrato != null) {
					return sdf.format(dtUltimaRenovacaoContrato);
				}
			}
		}
		
		return "";
	}
	
	public String getDtLiquidacaoContratoFormatada() {
		Date dtLiquidacaoContrato = null;
		
		if (isModoAltera() || isModoVisualiza()) {
			if (contrato != null && contrato.getDetalhesContrato() != null) {
				dtLiquidacaoContrato = contrato.getDetalhesContrato().getDtLiquidacaoContrato();
				if (dtLiquidacaoContrato != null) {
					return sdf.format(dtLiquidacaoContrato);
				}
			}
		}
		
		return "";
	}
	
	public String getNuIdentificadorClienteFormatado() {
		if (isModoInicio() || isModoFiltro()) {
			Contrato contrato = (Contrato) getRequestMap().get("contrato");
			if (contrato != null) {
				return formataDocumento(contrato);
			}
		}
		
		return "";
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
	
	public String carregaContrato() {
		if (contratoImportado == null) {
			info("Selecione um contrato.");
			return "";
		}
		if (!contratoImportado.getIcAtivo()) {
			info("Este contrato esta inativado e não pode ser escolhido.");
			contratoImportado = null;
			return "";
		}
		Garantia garantia = (Garantia) getHttpSession().getAttribute(GARANTIA);
		if (garantia != null) {
			garantia.setContrato(contratoImportado);
			getHttpSession().setAttribute(GARANTIA, garantia);
		} else {
			error("Erro ao recuperar garantia da sessão.");
		}
		
		return "toGarantia";
	}
	
	public boolean isShowColAcao() {
		if (!carregaContrato) {
			return true;
		}
		
		return false;
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
		return isModoFiltro();
	}
	
	public void doConsultarSimples(ActionEvent evt) {
		setModoInicio();
		setFiltro();
	}
	
	public void doConsultarAvancado(ActionEvent evt) {
		setModoFiltro();
		setFiltro();
		
		if(contrato == null){
			contrato = new Contrato();
		}
		
		contrato.setIcAtivo(Boolean.TRUE);
		carregaContrato = (Boolean) getHttpSession().getAttribute(CARREGA_CONTRATO);
		if (carregaContrato == null) {
			carregaContrato = false;
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
	
	public String getDescCanalComercializacao() {
		CanalComercializacaoProduto ccp = null;
		if (contrato != null && Utilities.notEmpty(contrato.getNuCanalComercializacao())) {
			ccp = canalComercialProdutoBO.findById(contrato.getNuCanalComercializacao());
		}
		return ccp != null ? ccp.getNoCanalCmrco() : "";
	}
	
	public boolean isShowTipoIdentificador() {
		if (contrato != null && Utilities.notEmpty(contrato.getIcTipoIndentificadorCliente())) {
			if (!contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID)) {
				return true;
			}
		}
		
		return false;
	}
	
	public String getTipoIdentificadorClienteFormatado() {
		if (contrato != null) {
			if (contrato.getIcTipoIndentificadorCliente() != null && !contrato.getIcTipoIndentificadorCliente().trim().equals("")) {
				if (contrato.getIcTipoIndentificadorCliente().trim().equals(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID)) {
					return Contrato.TIPO_IDENTIFICADOR_CNPJ_LABEL;
				}
				return contrato.getIcTipoIndentificadorCliente();
			}
		}
		
		return "";
	}

	public boolean isShowGeneratingLabel() {
		Contrato contrato = (Contrato) getRequestMap().get("contrato");
		return ! Contrato.AGENDA_GERADA_ID_GERACAO_OK.equals(contrato.getIcAgendaGerada());
	}
	
	public boolean isShowActionButtons() {
		Contrato contrato = (Contrato) getRequestMap().get("contrato");
		return Contrato.AGENDA_GERADA_ID_GERACAO_OK.equals(contrato.getIcAgendaGerada());
	}

	public Short getCampoOperacao() {
		return campoOperacao;
	}

	public void setCampoOperacao(Short campoOperacao) {
		this.campoOperacao = campoOperacao;
	}
	
	public Short getPerfilUserLogado(){
		return SegurancaUsuario.getInstance().getUsuario().getPerfis().get(0);
	}

	//---------------------Pagination begin--------------------------//
	private PaginationOnDemand pDemand = new PaginationOnDemand();
	
	public void init() {
		pDemand.addObserver(this);
	}
	
	public void update(Observable o, Object arg) {
		try {
			int count = 0;
			addFilter();
			if (isModoInicio()) {
				this.listContrato = contratoBO.simpleFilter(getUsuarioLogado(), getPerfilUserLogado(), pesquisaString, pesquisaMostraInativos, pDemand.getParam());
				count = contratoBO.simpleFilterCount(getUsuarioLogado(), getPerfilUserLogado(), pesquisaString, pesquisaMostraInativos, pDemand.getParam());
			} else {
				this.listContrato = contratoBO.advancedFilter(getFilterBase(), getUsuarioLogado(), getPerfilUserLogado(), pDemand.getParam());
				count = contratoBO.advancedFilterCount(getFilterBase(), getUsuarioLogado(), getPerfilUserLogado(), pDemand.getParam());
			}
			pDemand.setListItens(listContrato, count);
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
	
	//########Controles de filtros em sessao############//
	/**
	 * Pega o Filtro na sessão e seta as variaveis da view<br/>
	 * com seus respectivos valores que estavam guardados<br/>
	 * no filtro da sessão. 
	 * @see AbstractMB.getFilteriInSession();
	 */
	private void setFiltro(){
		//Pega o filtro na sessão.
		getFilterInSession(this.getClass());
		contrato = getFilterBase().getContrato("contrato");
		
		nuUnidade = getFilterBase().getString("nuUnidade");
		coProduto = getFilterBase().getString("coProduto");
		campoOperacao = getFilterBase().getShort("campoOperacao");
 		valorInicial =getFilterBase().getBigDecimal("valorInicial");
		valorFinal = getFilterBase().getBigDecimal("valorFinal");
		pesquisaMostraInativos = getFilterBase().getBoolean("pesquisaMostraInativos");
		pesquisaString = getFilterBase().getString("pesquisaString");
		
		if(contrato != null && contrato.getIcPfPj() != null){
			disableComboTpIdentificador = false;
		
			if(contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CPF_LABEL)){
				
				showInputNIS = false;
				showInputCNPJ = false;
				showInputCPF = true ;
				showInputIdentificacaoClienteVazio = false;
			}
			else if(contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_NIS_LABEL)){
				showInputCNPJ = false;
				showInputCPF = false;
				showInputNIS = true;
				showInputIdentificacaoClienteVazio = false;
			}
			else{
				showInputCPF = false;
				showInputNIS = false;
				showInputCNPJ = true;
				showInputIdentificacaoClienteVazio = false;
			}
		}
		
	}
	
	private void addFilter(){
		getFilterBase().limparFiltros();
		if(isModoFiltro()){
			getFilterBase().addToFilter("contrato", contrato);
			
			getFilterBase().addToFilter("campoOperacao", campoOperacao);
			getFilterBase().addToFilter("pesquisaMostraInativos", pesquisaMostraInativos);
			getFilterBase().addToFilter("valorInicial", valorInicial);
			getFilterBase().addToFilter("valorFinal", valorFinal);
			getFilterBase().addToFilter("coProduto", coProduto);
			getFilterBase().addToFilter("nuUnidade", nuUnidade);
			getFilterBase().setModoAvancado();
		}else{
			getFilterBase().addToFilter("pesquisaMostraInativos", pesquisaMostraInativos);
			getFilterBase().addToFilter("pesquisaString", pesquisaString);
			getFilterBase().setModoSimples();
		}
		//Diz ao filtro que ele possui um filtro de contrato.
		getFilterBase().addToFilter("filtroContrato", true);
		//Coloca o filtro na sessão;
		putFilterBase(this.getClass());
	}
	
	private void modoFiltro(){
		getFilterInSession(this.getClass());
		if(getFilterBase().isModoAvancado()){
			setModoFiltro();
			setFiltro();
		}else{
			setModoInicio();
			setFiltro();
		}
	}
}