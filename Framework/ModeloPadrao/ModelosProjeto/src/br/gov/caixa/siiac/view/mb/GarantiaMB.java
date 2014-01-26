package br.gov.caixa.siiac.view.mb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.IContratoBO;
import br.gov.caixa.siiac.bo.IGarantiaBO;
import br.gov.caixa.siiac.bo.ITipoGarantiaBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.bo.IUsuarioADBO;
import br.gov.caixa.siiac.bo.impl.MatrizAcessoBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.Garantia;
import br.gov.caixa.siiac.model.domain.TipoGarantia;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

@Service()
@Scope("request")
public class GarantiaMB extends AbstractMB {
	
	private static final String ULTIMA_LOCALIZACAO_ENCAMINHADA_LIQUIDACAO_LABEL = "ENCAMINHADA PARA LIQUIDAÇÃO";
	private static final String ULTIMA_LOCALIZACAO_RETIRADA_CASA_FORTE_LABEL = "RETIRADA DA CASA FORTE";
	private static final String ULTIMA_LOCALIZACAO_CASA_FORTE_LABEL = "CASA FORTE";
	private static final Integer MODO_REALIZA_INVENTARIO = 5;
	private static final String CARREGA_CONTRATO = "carregaContrato";
	private static final String CARREGA_GARANTIA = "carregaGarantia";
	private static final String GARANTIA = "garantia";
	private static final String ACTION = "action";
	private static final char ACTION_CAD = 'c';
	private static final char ACTION_ALT = 'a';
	private static final char ACTION_VIEW = 'v';
	
	private String pesquisaString;
	private boolean pesquisaMostraInativos = false;
	private IGarantiaBO garantiaBO;
	private IContratoBO contratoBO;
	private IUnidadeBO unidadeBO;
	private ITipoGarantiaBO tipoGarantiaBO;
	private IUsuarioADBO usuarioADBO;
	
	private Garantia garantia = new Garantia();;
	private List<Garantia> listGarantia = new ArrayList<Garantia>();
	private List<TipoGarantia> listTipoGarantia = new ArrayList<TipoGarantia>();
	private BigDecimal[] valor = new BigDecimal[2]; // "valor de: " = valor[0] "A" = valor[1]
	private Date dtUltimoInventario;
	private String locUltimoInventario;
	private String respUltimoInventario = getUsuarioLogado().getMatricula();
	private int page;
	private String nuUnidade;
	private Short nuPerfil = getUsuarioLogado().getPerfis().get(0);
	
	public GarantiaMB() {
	}
	
	// Methods Actions
	public Boolean doValidaNovo() {
		
		// Valida codGarantia (Obrigatorio)
		if (garantia.getCoGarantia() == null) {
			error(MSGS, MN002);
			warn("CÓD. GARANTIA SISTEMA ORIGEM é um campo obrigatório.");
			return false;
		} else {
			// Valida já existe a garantia no sistema
			if (garantiaBO.existGarantia(garantia.getCoGarantia(), garantia.getNuGarantia())) {
				error(MSGS, MN015);
				return false;
			}
		}
		
		// Valida contrato (Obrigatorio)
		if (garantia.getContrato() == null || garantia.getContrato().getNuContrato() == null) {
			error(MSGS, MN002);
			warn("CONTRATO é um campo obrigatório.");
			return false;
		}
		
		// Valida valor garantia (Obrigatorio)
		if (garantia.getVrGarantia() == null || garantia.getVrGarantia().equals("")) {
			error(MSGS, MN002);
			warn("VALOR GARANTIA é um campo obrigatório.");
			return false;
		}
		
		// Valida se valor garantia é maior que valor NominalContrato
		// TODO Aguardando definição da Ana Flávia sobre o caso de não haver valor nominal
		if (Utilities.notEmpty(garantia.getContrato().getVrNominalContrato())) {
			if (garantia.getVrGarantia().compareTo(garantia.getContrato().getVrNominalContrato()) > 0) {
				error(MSGS, MN002);
				warn("VALOR GARANTIA deve ser menor ou igual ao VALOR NOMINAL do Contrato.");
				return false;
			}
		}
		
		
		// Valida tipo garantia (Obrigatorio)
		if (garantia.getTipoGarantia() == null || garantia.getTipoGarantia().getNuTipoGarantia() == null || garantia.getTipoGarantia().getNuTipoGarantia() == 0) {
			error(MSGS, MN002);
			warn("TIPO GARANTIA é um campo obrigatório.");
			return false;
		}
		
		// Valida data ultima ultima (Obrigatorio)
		if (garantia.getDtUltimaLocalizacao() == null) {
			error(MSGS, MN002);
			warn("DATA ÚLTIMA LOCALIZAÇÃO é um campo obrigatório.");
			return false;
		}
		
		// Valida localizacaoUltimoInventario (obrigatorio)
		if (garantia.getIcUltimaLocalizacao() == null || garantia.getIcUltimaLocalizacao().equals("")) {
			error(MSGS, MN002);
			warn("ÚLTIMA LOCALIZAÇÃO é obrigatório.");
			return false;
		}
		
		try {
			if (garantia.getCoUsuarioSolicitante() != null && !garantia.getCoUsuarioSolicitante().equals("")) {
				String user = garantia.getCoUsuarioSolicitante().charAt(0) + ConvertUtils.padZerosLeft(garantia.getCoUsuarioSolicitante().substring(1), 6);
				garantia.setCoUsuarioSolicitante(user);
				// Valida usuario no AD
				if (!usuarioADBO.existeUsuarioAD(garantia.getCoUsuarioSolicitante())) {
					error(MSGS, MN002);
					warn("Usuário solicitante não esta cadastrado no AD");
					return false;
				}
			}
		} catch (SIIACException e) {
			LogCEF.error("Erro ao Validar usuario no AD: " + e.getMessage());
			error(MSGS, MN002);
			warn("Erro ao validar usuário solicitante no AD");
		}
		return true;
	}
	
	// Methods Actions
	public Boolean doValida() {
		
		// Valida codGarantia (Obrigatorio)
		if (garantia.getCoGarantia() == null) {
			error(MSGS, MN002);
			warn("CÓD. GARANTIA SISTEMA ORIGEM é um campo obrigatório.");
			return false;
		} else {
			// Valida já existe a garantia no sistema
			if (garantiaBO.existGarantia(garantia.getCoGarantia(), garantia.getNuGarantia())) {
				error(MSGS, MN015);
				return false;
			}
		}
		
		// Valida contrato (Obrigatorio)
		if (garantia.getContrato() == null || garantia.getContrato().getNuContrato() == null) {
			error(MSGS, MN002);
			warn("CONTRATO é um campo obrigatório.");
			return false;
		}
		
		// Valida valor garantia (Obrigatorio)
		if (garantia.getVrGarantia() == null || garantia.getVrGarantia().equals("")) {
			error(MSGS, MN002);
			warn("VALOR GARANTIA é um campo obrigatório.");
			return false;
		}
		
		if (Utilities.notEmpty(garantia.getContrato().getVrNominalContrato())) {
			// Valida se valor garantia é maior que valor NominalContrato
			if (garantia.getVrGarantia().compareTo(garantia.getContrato().getVrNominalContrato()) > 0) {
				error(MSGS, MN002);
				warn("VALOR GARANTIA deve ser menor ou igual ao VALOR NOMINAL do Contrato.");
				return false;
			}
		}
		
		// Valida tipo garantia (Obrigatorio)
		if (garantia.getTipoGarantia() == null || garantia.getTipoGarantia().getNuTipoGarantia() == null || garantia.getTipoGarantia().getNuTipoGarantia() == 0) {
			error(MSGS, MN002);
			warn("TIPO GARANTIA é um campo obrigatório.");
			return false;
		}
		
		// Valida data ultimo inventario (Obrigatorio)
		if (garantia.getDtUltimoInventarioGarantia() == null) {
			error(MSGS, MN002);
			warn("DATA ÚLTIMO INVENTÁRIO é um campo obrigatório.");
			return false;
		}
		
		// Valida data ultima localizacao
		if (garantia.getDtUltimaLocalizacao() != null && garantia.getDtUltimaLocalizacao().after(garantia.getDtUltimoInventarioGarantia())) {
			error(MSGS, MN002);
			warn("DATA ÚLTIMA LOCALIZAÇÃO informada deve ser menor que a DATA ÚLTIMO INVENTÁRIO.");
			return false;
		}
		
		// Valida localizacaoUltimoInventario (obrigatorio)
		if (garantia.getIcLocalizacaoUltimoInventari() == null || garantia.getIcLocalizacaoUltimoInventari().equals("")) {
			error(MSGS, MN002);
			warn("LOCALIZAÇÃO ÚLTIMO INVENTÁRIO é obrigatório.");
			return false;
		}
		
		try {
			if (garantia.getCoUsuarioSolicitante() != null && !garantia.getCoUsuarioSolicitante().equals("")) {
				String user = garantia.getCoUsuarioSolicitante().charAt(0) + ConvertUtils.padZerosLeft(garantia.getCoUsuarioSolicitante().substring(1), 6);
				garantia.setCoUsuarioSolicitante(user);
				// Valida usuario no AD
				if (!usuarioADBO.existeUsuarioAD(garantia.getCoUsuarioSolicitante())) {
					error(MSGS, MN002);
					warn("Usuário solicitante não esta cadastrado no AD");
					return false;
				}
			}
		} catch (SIIACException e) {
			LogCEF.error("Erro ao Validar usuario no AD: " + e.getMessage());
			error(MSGS, MN002);
			warn("Erro ao validar usuário solicitante no AD");
		}
		return true;
	}
	
	public void doLimpar(ActionEvent evt) {
		valor = new BigDecimal[2];
		nuUnidade = "";
		pesquisaMostraInativos = false;
		garantia = new Garantia();
		// limpa os filtros da sessão.
		getFilterBase().limparFiltros();
	}
	
	/**
	 * Grava uma nova garantia
	 * 
	 * @param evt
	 */
	public void doGravaNovo(ActionEvent evt) {
		
		garantia.setCoUsuarioSolicitante(SegurancaUsuario.getInstance().getUsuario().getMatricula());
		
		if (doValidaNovo()) {
			try {
				
				garantia.setIcLocalizacaoUltimoInventari(garantia.getIcUltimaLocalizacao());
				garantia.setDtUltimoInventarioGarantia(garantia.getDtUltimaLocalizacao());
				
				garantiaBO.save(garantia);
				setModoInicio();
				page = 1;
				garantia = new Garantia();
				// atualizaList();
				if (getHttpSession().getAttribute(CARREGA_GARANTIA) == null) {
					limpaSessao();
				}
				info(MSGS, MN007);
			} catch (Exception e) {
				LogCEF.error("Erro ao Gravar: " + e.getMessage());
				error(MSGS, MN002);
			}
		}
	}
	
	/**
	 * Grava uma nova garantia e retorna para a tela de Contrato
	 * 
	 * @return
	 */
	public String doGravaNovoByContrato() {
		
		garantia.setIcLocalizacaoUltimoInventari(garantia.getIcUltimaLocalizacao());
		garantia.setDtUltimoInventarioGarantia(garantia.getDtUltimaLocalizacao());
		if (doValida()) {
			doGravaNovo(null);
			return "toContrato";
		} else {
			return "";
		}
	}
	
	/**
	 * Grava uma Alteração de garantia e retorna para a tela de Contrato
	 * 
	 * @return
	 */
	public String doGravaAlteracaoByContrato() {
		if (doValida()) {
			doGravaAlteracao(null);
			return "toContrato";
		} else {
			return "";
		}
	}
	
	/**
	 * Grava a alteração de uma garantia
	 * 
	 * @param evt
	 */
	public void doGravaAlteracao(ActionEvent evt) {
		if (doValida()) {
			try {
				garantiaBO.save(garantia);
				info(MSGS, MN008);
				setModoInicio();
				page = 1;
				garantia = new Garantia();
				atualizaList();
				if (getHttpSession().getAttribute(CARREGA_GARANTIA) == null) {
					limpaSessao();
				}
			} catch (Exception e) {
				LogCEF.error("Erro ao Gravar: " + e.getMessage());
				error(MSGS, MN002);
			}
		}
	}
	
	/**
	 * Grava o inventario das garantias selecionas
	 * 
	 * @param evt
	 */
	public void doGravaInventario(ActionEvent evt) {
		String contratos = "";
		boolean valida = true;
		// Valida data ultimo inventario (Obrigatorio)
		if (dtUltimoInventario == null) {
			error(MSGS, MN002);
			warn("DATA ÚLTIMO INVENTÁRIO é um campo obrigatório.");
			valida = false;
		}
		
		if (!valida) {
			return;
		}
		for (Garantia g : listGarantia) {
			if (g.isCheckInventario()) {
				if (g.getDtUltimaLocalizacao() != null) {
					if (g.getDtUltimaLocalizacao().after(dtUltimoInventario)) {
						contratos += g.getContrato().getCoContrato() + ", ";
						continue;
					}
				}
				g.setDtUltimoInventarioGarantia(dtUltimoInventario);
				g.setIcLocalizacaoUltimoInventari(locUltimoInventario);
				g.setCoResponsavelInventario(respUltimoInventario);
				try {
					garantiaBO.save(g);
				} catch (Exception e) {
					LogCEF.error("Erro ao Gravar: " + e.getMessage());
					error(MSGS, MN002);
				}
			}
		}
		if (contratos.equals("")) {
			info(MSGS, MN008);
		} else {
			warn("O inventário da(s) garantia(s) do(s) contrato(s) " + contratos.substring(0, contratos.length() - 2) + " não pode ser realizado porque a DATA ÚLTIMA LOCALIZAÇÃO é superior a DATA ÚLTIMO INVENTÁRIO.");
		}
		setModoInicio();
		page = 1;
		garantia = new Garantia();
		atualizaList();
	}
	
	public void doConsultarAvancado(ActionEvent evt) {
		setModoFiltro();
		setFiltro();
	}
	
	public void doConsultarSimples(ActionEvent evt) {
		setModoInicio();
		setFiltro();
	}
	
	public void doConsultarClick(ActionEvent evt) {
		if (nuUnidade != null && !nuUnidade.equals("")) {
			garantia.getContrato().setNuUnidade(Short.valueOf(nuUnidade));
		}
		boolean empty = atualizaList();
		if (nuUnidade != null && !nuUnidade.equals("")) {
			nuUnidade = ConvertUtils.padZerosLeft(nuUnidade, 4);
		} else {
			nuUnidade = "";
		}
		getFilterBase().limparFiltros();
		if (isModoFiltro()) {
			// Adicionando filtros ao objeto de filtro
			getFilterBase().addToFilter("nuUnidade", nuUnidade);
			getFilterBase().addToFilter("coContrato", garantia.getContrato().getCoContrato());
			getFilterBase().addToFilter("nuTipoGarantia", garantia.getTipoGarantia().getNuTipoGarantia());
			getFilterBase().addToFilter("coGarantia", garantia.getCoGarantia());
			getFilterBase().addToFilter("valor1", valor[0]);
			getFilterBase().addToFilter("valor2", valor[1]);
			getFilterBase().addToFilter("icUltimaLocalizacao", garantia.getIcUltimaLocalizacao());
			getFilterBase().addToFilter("icLocalizacaoUltimoInventari", garantia.getIcLocalizacaoUltimoInventari());
			getFilterBase().addToFilter("dtUltimoInventarioGarantia", garantia.getDtUltimoInventarioGarantia());
			getFilterBase().addToFilter("pesquisaMostraInativos", pesquisaMostraInativos);
			getFilterBase().setModoAvancado();
		} else {
			getFilterBase().addToFilter("pesquisaMostraInativos", pesquisaMostraInativos);
			getFilterBase().addToFilter("pesquisaString", pesquisaString);
			getFilterBase().setModoSimples();
		}
		
		// Fim da adição dos filtros.
		
		// Define que o filtro base possui um filtro de verificação.
		getFilterBase().addToFilter("filtroVerificacao", true);
		
		// Coloca o filtro com os valores adicionados na sessão.
		putFilterBase(this.getClass());
		if (empty) {
			warn(MSGS, MN016);
		}
	}
	
	public void toAlterar(ActionEvent evt) {
		garantia = (Garantia) getRequestMap().get("row");
		if (!garantia.isIcAtivo()) {
			error(MSGS, MN013);
			doModoInicio();
			return;
		}
		setModoAltera();
	}
	
	public void toVisualizar(ActionEvent evt) {
		garantia = (Garantia) getRequestMap().get("row");
		setModoVisualiza();
		page = 1;
	}
	
	public void doInativar(ActionEvent evt) {
		garantia = (Garantia) getRequestMap().get("row");
		try {
			garantiaBO.inativar(garantia);
			info(MSGS, MN009);
		} catch (Exception e) {
			LogCEF.error("Erro ao Inativar: " + e.getMessage());
			error(MSGS, MN002);
		}
		doModoInicio();
	}
	
	public void doCadastrarClick(ActionEvent evt) {
		setFiltro();
		setModoCadastro();
		garantia = new Garantia();
		garantia.setDtUltimoInventarioGarantia(new Date());
		garantia.setCoResponsavelInventario(getUsuarioLogado().getMatricula());
	}
	
	public String doCancelar() {
		if (getHttpSession().getAttribute(CARREGA_GARANTIA) != null) {
			return doCancelarByContrato();
		}
		limpaSessao();
		if (!isModoVisualiza()) {
			warn(MSGS, MN003);
		}
		doModoInicio();
		return "";
	}
	
	/**
	 * Cancela o cadastro de garantia e retorna para a tela de Contrato
	 * 
	 * @return
	 */
	public String doCancelarByContrato() {
		if (!isModoVisualiza()) {
			warn(MSGS, MN003);
		}
		return "toContrato";
	}
	
	public void toPesquisar(ActionEvent evt) {
		limpaSessao();
		doModoInicio();
	}
	
	public void toRealizarInventario(ActionEvent evt) {
		boolean selected = false;
		dtUltimoInventario = null;
		locUltimoInventario = "";
		for (Garantia g : listGarantia) {
			if (g.isCheckInventario()) {
				selected = true;
				break;
			}
		}
		if (!selected) {
			warn(MSGS, MSGGAR0001);
		} else {
			setModoRealizaInventario();
		}
	}
	
	public String toSelecionaContrato() {
		getHttpSession().setAttribute(GARANTIA, garantia);
		getHttpSession().setAttribute(CARREGA_CONTRATO, true);
		if (isModoAltera()) {
			getHttpSession().setAttribute(ACTION, ACTION_ALT);
		} else {
			getHttpSession().setAttribute(ACTION, ACTION_CAD);
		}
		return "toContrato";
	}
	
	public void doModoInicio() {
		garantia = (Garantia) getHttpSession().getAttribute(GARANTIA);
		Character action = (Character) getHttpSession().getAttribute(ACTION);
		pesquisaString = "";
		pesquisaMostraInativos = false;
		if (garantia == null) {
			garantia = new Garantia();
			garantia.setTipoGarantia(new TipoGarantia());
			this.valor = new BigDecimal[2];
			getFilterInSession(this.getClass());
			if (getFilterBase().isModoAvancado()) {
				setModoFiltro();
			} else {
				setModoFiltro();
				setModoInicio();
			}
			page = 1;
			setFiltro();
			atualizaList();
		} else { // veio da tela de contrato
			if (action != null && action == ACTION_VIEW) {
				setModoVisualiza();
			}
			if (action != null && action == ACTION_ALT) {
				setModoAltera();
			}
			if (action != null && action == ACTION_CAD) {
				setModoCadastro();
				garantia.setDtUltimoInventarioGarantia(new Date());
				garantia.setCoResponsavelInventario(getUsuarioLogado().getMatricula());
			}
		}
	}
	
	private boolean atualizaList() {
		try {
			if (isModoInicio()) {
				listGarantia = garantiaBO.getListFiltroSimples(pesquisaString, pesquisaMostraInativos);
			} else {
				listGarantia = garantiaBO.getListFiltro(garantia, pesquisaMostraInativos, valor);
			}
			return listGarantia.size() <= 0;
		} catch (Exception e) {
			LogCEF.info("Erro ao realizar filtro: " + e.getMessage());
			error(MSGS, MN002);
		}
		return false;
	}
	
	public List<String> selectItemContratoCoContrato(Object objeto) {
		List<String> list = new ArrayList<String>();
		List<Contrato> contratos = new ArrayList<Contrato>();
		if (!objeto.equals("") && !objeto.equals("null")) {
			garantia.getContrato().setCoContrato(objeto.toString());
		} else {
			garantia.getContrato().setCoContrato("");
		}
		
		try {
			Short unidadeShort = null;
			if (nuUnidade != null && !"".equals(nuUnidade)) {
				unidadeShort = Short.valueOf(nuUnidade);
			}
			contratos = contratoBO.getLikeCoContrato(String.valueOf(objeto), unidadeShort);
		} catch (Exception e) {
			LogCEF.error("Erro ao buscar contratos");
			error(MSGS, MN002);
		}
		for (Contrato c : contratos) {
			list.add(c.getCoContrato());
		}
		return list;
	}
	
	public List<String[]> selectItemContratoNuUnidade(Object objeto) {
		String value = objeto.toString().replace("_", "");
		List<Unidade> unidades = new ArrayList<Unidade>();
		List<String[]> unidadeStr = new ArrayList<String[]>();
		if (!value.equals("") && !value.equals("null")) {
			garantia.getContrato().setNuUnidade(Short.valueOf(value));
		} else {
			garantia.getContrato().setNuUnidade(null);
		}
		try {
			unidades = unidadeBO.getLikeNuUnidade(value);
		} catch (Exception e) {
			LogCEF.error("Erro ao buscar unidades");
			error(MSGS, MN002);
		}
		
		for (Unidade u : unidades) {
			String[] s = new String[2];
			s[0] = ConvertUtils.padZerosLeft(String.valueOf(u.getId().getNuUnidade()), 4);
			s[1] = u.getNomeAbreviado();
			unidadeStr.add(s);
		}
		return unidadeStr;
	}
	
	public List<SelectItem> getSelectItemNuTipoGarantia() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		if (tipoGarantiaBO != null && listTipoGarantia.isEmpty()) {
			try {
				listTipoGarantia = tipoGarantiaBO.getListFiltro("", Boolean.FALSE);
			} catch (SIIACException e) {
				LogCEF.info("Erro ao buscar lista de tipo de garantia: " + e.getMessage());
				error(MSGS, MN002);
			}
		}
		list.add(new SelectItem("0", "Selecione..."));
		// No filtro lista todos os tipos de garantias
		for (TipoGarantia tg : listTipoGarantia) {
			list.add(new SelectItem(tg.getNuTipoGarantia(), tg.getNoTipoGarantia()));
		}
		return list;
	}
	
	public List<SelectItem> getSelectItemIcUltimaLocalizacao() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem("", "Selecione..."));
		list.add(new SelectItem(Contrato.LOCALIZACAO_CONTRATO_ID_CASA_FORTE, Contrato.LOCALIZACAO_CONTRATO_LABEL_CASA_FORTE));
		list.add(new SelectItem(Contrato.LOCALIZACAO_CONTRATO_ID_RETIRADA_CASA_FORTE, Contrato.LOCALIZACAO_CONTRATO_LABEL_RETIRADA_CASA_FORTE));
		list.add(new SelectItem(Contrato.LOCALIZACAO_CONTRATO_ID_ENCAMINHADA_LIQUIDACAO, Contrato.LOCALIZACAO_CONTRATO_LABEL_ENCAMINHADA_LIQUIDACAO));
		return list;
	}
	
	public List<SelectItem> getSelectItemIcLocalizacaoUltimoInventari() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem("", "Selecione..."));
		list.add(new SelectItem(Contrato.LOCALIZACAO_CONTRATO_ID_CASA_FORTE, Contrato.LOCALIZACAO_CONTRATO_LABEL_CASA_FORTE));
		list.add(new SelectItem(Contrato.LOCALIZACAO_CONTRATO_ID_RETIRADA_CASA_FORTE, Contrato.LOCALIZACAO_CONTRATO_LABEL_RETIRADA_CASA_FORTE));
		list.add(new SelectItem(Contrato.LOCALIZACAO_CONTRATO_ID_ENCAMINHADA_LIQUIDACAO, Contrato.LOCALIZACAO_CONTRATO_LABEL_ENCAMINHADA_LIQUIDACAO));
		return list;
	}
	
	private void limpaSessao() {
		getHttpSession().setAttribute(GARANTIA, null);
		getHttpSession().setAttribute(ACTION, null);
		getHttpSession().setAttribute(CARREGA_CONTRATO, null);
	}
	
	// Methods Show
	public boolean isShowCadastraGarantiaByContrato() {
		return getHttpSession().getAttribute(CARREGA_GARANTIA) != null;
	}
	
	public boolean isShowCheckbox() {
		return isAtivo();
	}
	
	public boolean isShowButtonSelecionaContrato() {
		return getHttpSession().getAttribute(CARREGA_GARANTIA) == null && isModoCadastro();
	}
	
	public boolean isShowPanelCadastro() {
		return isModoCadastro() || isModoAltera();
	}
	
	public boolean isShowPanelFiltro() {
		return isModoInicio();
	}
	
	public boolean isShowPanelLista() {
		return isModoInicio() || isModoFiltro();
	}
	
	public boolean isShowButtonRealizarInventario() {
		return (isGestorSistema() || isRegionalConformidade() || isVerificadorConformidade());
	}
	
	public boolean isShowButtonCadastro() {
		return isModoInicio() && (isGestorSistema() || isRegionalConformidade() || isVerificadorConformidade());
	}
	
	public boolean isShowButtonAlterar() {
		return MatrizAcessoBO.acessoModuloMantemGarantia(nuPerfil, MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_ALTERA) && isAtivo();
	}
	
	public boolean isShowButtonExcluir() {
		return MatrizAcessoBO.acessoModuloMantemGarantia(nuPerfil, MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_EXCLUI) && isAtivo();
	}
	
	private boolean isAtivo() {
		Garantia garantia = (Garantia) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		if (garantia != null) {
			return garantia.isIcAtivo();
		}
		
		return false;
	}
	
	public boolean isShowPanelVisualizacao() {
		return isModoVisualiza();
	}
	
	public boolean isShowBreadCadastro() {
		return isModoCadastro();
	}
	
	public boolean isShowPanelAlteracao() {
		return isModoAltera();
	}
	
	public boolean isShowPanelRealizaInventario() {
		return isModoRealizaInventario();
	}
	
	// /_________________________
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
		return isModoFiltro() && MatrizAcessoBO.acessoModuloMantemGarantia(nuPerfil, MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_INSERE);
	}
	
	public boolean isShowButtonNovo() {
		return isModoInicio() && MatrizAcessoBO.acessoModuloMantemGarantia(nuPerfil, MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_INSERE);
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
	 * 
	 * @return
	 */
	public boolean getShowConsultarSimples() {
		return isModoInicio();
	}
	
	/**
	 * Verifica se deve ser mostrado o painel de consulta avançado
	 * 
	 * @return
	 */
	public boolean getShowConsultarAvancado() {
		return isModoFiltro();
	}
	
	public boolean isShowFormAlterar() {
		return isModoAltera() || isModoVisualiza();
	}
	
	public boolean isShowDataTable() {
		return isModoInicio() || isModoFiltro();
	}
	
	private void setModoRealizaInventario() {
		setCurrentState(MODO_REALIZA_INVENTARIO);
	}
	
	private boolean isModoRealizaInventario() {
		return getCurrentState() == MODO_REALIZA_INVENTARIO;
	}
	
	public boolean isDisabledCodGarantia() {
		return isModoAltera();
	}
	
	public boolean isDisabledLocalizacaoUltimoInventario() {
		return isModoAltera();
	}
	
	// Getters and Setters
	
	public String getUnidadeFormatadaGrid() {
		Garantia garantia = (Garantia) getRequestMap().get("row");
		if (garantia.getContrato().getNuUnidade() == null || garantia.getContrato().getNuUnidade() == 0) {
			return "";
		}
		return ConvertUtils.padZerosLeft(String.valueOf(garantia.getContrato().getNuUnidade()), 4);
	}
	
	public void formataUnidade(ActionEvent evt) {
		nuUnidade = nuUnidade.replace("_", "");
		if (nuUnidade != null && !nuUnidade.equals("") && Integer.parseInt(nuUnidade) != 0) {
			nuUnidade = ConvertUtils.padZerosLeft(nuUnidade, 4);
		}
	}
	
	public String getPesquisaString() {
		return pesquisaString;
	}
	
	public void setPesquisaString(String pesquisaString) {
		this.pesquisaString = pesquisaString;
	}
	
	public boolean isPesquisaMostraInativos() {
		return pesquisaMostraInativos;
	}
	
	public void setPesquisaMostraInativos(boolean pesquisaMostraInativos) {
		this.pesquisaMostraInativos = pesquisaMostraInativos;
	}
	
	public String getNuUnidade() {
		return nuUnidade;
	}
	
	public void setNuUnidade(String nuUnidade) {
		this.nuUnidade = nuUnidade;
	}
	
	/**
	 * Método fake apenas para que o método nuUnidade seja chamado assim que o usuário digitar uma unidade no filtro.
	 * 
	 * @param evt
	 */
	public void fakeSetaNuUnidade(ActionEvent evt) {
	}
	
	public String getdescricaoFormatadaGrid() {
		Garantia garantia = (Garantia) getRequestMap().get("row");
		if (garantia.getDeGarantia() != null) {
			if (garantia.getDeGarantia().equals("") || garantia.getDeGarantia().length() < 20) {
				return garantia.getDeGarantia();
			}
			
			return garantia.getDeGarantia().substring(0, 17) + "...";
		}
		
		return "";
	}
	
	public String getTipoGarantiaFormatadaGrid() {
		Garantia garantia = (Garantia) getRequestMap().get("row");
		if (garantia.getTipoGarantia() == null) {
			return "";
		}
		if (garantia.getTipoGarantia().getNoTipoGarantia().equals("") || garantia.getTipoGarantia().getNoTipoGarantia().length() < 20) {
			return garantia.getTipoGarantia().getNoTipoGarantia();
		}
		return garantia.getTipoGarantia().getNoTipoGarantia().substring(0, 17) + "...";
	}
	
	public String getUnidadeFormatada() {
		if (garantia.getContrato().getNuUnidade() == null || garantia.getContrato().getNuUnidade() == 0) {
			return "";
		}
		return ConvertUtils.padZerosLeft(String.valueOf(garantia.getContrato().getNuUnidade()), 4);
	}
	
	public String getDataContratoFormatada() {
		if (garantia.getContrato().getDtContrato() == null) {
			return "";
		}
		return ConvertUtils.dateFormat(garantia.getContrato().getDtContrato());
	}
	
	public String getDataUltimaLocalizacaoFormatada() {
		if (garantia.getDtUltimaLocalizacao() == null) {
			return "";
		}
		return ConvertUtils.dateFormat(garantia.getDtUltimaLocalizacao());
	}
	
	public String getDataInventarioFormatada() {
		if (garantia.getDtUltimoInventarioGarantia() == null) {
			return "";
		}
		return ConvertUtils.dateFormat(garantia.getDtUltimoInventarioGarantia());
	}
	
	public BigDecimal[] getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal[] valor) {
		this.valor = valor;
	}
	
	public Garantia getGarantia() {
		return this.garantia;
	}
	
	public void setGarantia(Garantia garantia) {
		this.garantia = garantia;
	}
	
	public List<Garantia> getListGarantia() {
		return this.listGarantia;
	}
	
	public void setListGarantia(List<Garantia> listGarantia) {
		this.listGarantia = listGarantia;
	}
	
	public Date getDtUltimoInventario() {
		return dtUltimoInventario;
	}
	
	public void setDtUltimoInventario(Date dtUltimoInventario) {
		this.dtUltimoInventario = dtUltimoInventario;
	}
	
	public String getLocUltimoInventario() {
		return locUltimoInventario;
	}
	
	public void setLocUltimoInventario(String locUltimoInventario) {
		this.locUltimoInventario = locUltimoInventario;
	}
	
	public String getRespUltimoInventario() {
		return respUltimoInventario;
	}
	
	public void setRespUltimoInventario(String respUltimoInventario) {
		this.respUltimoInventario = respUltimoInventario;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
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
	public void setTipoGarantiaBO(ITipoGarantiaBO tipoGarantiaBO) {
		this.tipoGarantiaBO = tipoGarantiaBO;
	}
	
	@Autowired
	public void setUsuarioADBO(IUsuarioADBO usuarioADBO) {
		this.usuarioADBO = usuarioADBO;
	}
	
	@Autowired
	public void setGarantiaBO(IGarantiaBO garantiaBO) {
		this.garantiaBO = garantiaBO;
		doModoInicio();
	}

	public String getIcUltimaLocalizacaoFormatada() {
		if (garantia != null) {
			if (garantia.getIcUltimaLocalizacao() != null && !garantia.getIcUltimaLocalizacao().trim().equals("")) {
				if (garantia.getIcUltimaLocalizacao().trim().equals("CF")) {
					return ULTIMA_LOCALIZACAO_CASA_FORTE_LABEL;
				} else if (garantia.getIcUltimaLocalizacao().trim().equals("RF")) {
					return ULTIMA_LOCALIZACAO_RETIRADA_CASA_FORTE_LABEL;
				} else {
					return ULTIMA_LOCALIZACAO_ENCAMINHADA_LIQUIDACAO_LABEL;
				}
			}
		}
		
		return "";
	}
	
	public String getIcLocalizacaoUltimoInventarioFormatada() {
		if (garantia != null) {
			if (garantia.getIcLocalizacaoUltimoInventari() != null && !garantia.getIcLocalizacaoUltimoInventari().trim().equals("")) {
				if (garantia.getIcLocalizacaoUltimoInventari().trim().equals("CF")) {
					return ULTIMA_LOCALIZACAO_CASA_FORTE_LABEL;
				} else if (garantia.getIcLocalizacaoUltimoInventari().trim().equals("RF")) {
					return ULTIMA_LOCALIZACAO_RETIRADA_CASA_FORTE_LABEL;
				} else {
					return ULTIMA_LOCALIZACAO_ENCAMINHADA_LIQUIDACAO_LABEL;
				}
			}
		}
		
		return "";
	}
	
	// ########Controles de filtros em sessao############//
	/**
	 * Pega o Filtro na sessão e seta as variaveis da view<br/>
	 * com seus respectivos valores que estavam guardados<br/>
	 * no filtro da sessão.
	 * 
	 * @see AbstractMB.getFilteriInSession();
	 */
	private void setFiltro() {
		// Pega o filtro na sessão.
		getFilterInSession(this.getClass());
		nuUnidade = getFilterBase().getString("nuUnidade");
		if (nuUnidade != null) {
			garantia.getContrato().setNuUnidade(Short.valueOf(nuUnidade));
		}
		garantia.getContrato().setCoContrato(getFilterBase().getString("coContrato"));
		garantia.getTipoGarantia().setNuTipoGarantia(getFilterBase().getInt("nuTipoGarantia"));
		garantia.setCoGarantia(getFilterBase().getInt("coGarantia"));
		valor[0] = getFilterBase().getBigDecimal("valor1");
		valor[1] = getFilterBase().getBigDecimal("valor2");
		garantia.setIcUltimaLocalizacao(getFilterBase().getString("icUltimaLocalizacao"));
		garantia.setIcLocalizacaoUltimoInventari(getFilterBase().getString("icLocalizacaoUltimoInventari"));
		garantia.setDtUltimoInventarioGarantia(getFilterBase().getDate("dtUltimoInventarioGarantia"));
		pesquisaMostraInativos = getFilterBase().getBoolean("pesquisaMostraInativos");
		pesquisaString = getFilterBase().getString("pesquisaString");
	}
}