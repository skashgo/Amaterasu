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
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.ICanalComercialProdutoBO;
import br.gov.caixa.siiac.bo.IChecklistBO;
import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.bo.IServicoVerificacaoBO;
import br.gov.caixa.siiac.bo.IServicoVerificacaoProdutoBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.exception.ReportErrorCreateDatasourceException;
import br.gov.caixa.siiac.exception.ReportFinalNullException;
import br.gov.caixa.siiac.exception.ReportInvalidPathException;
import br.gov.caixa.siiac.model.ChecklistVO;
import br.gov.caixa.siiac.model.VinculacoesChecklistVO;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.CanalComercializacaoProduto;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

@Service()
@Scope("request")
public class ChecklistMB extends AbstractMB {
	
	private static final String LABEL_REVOGADO_NAO = "NÃO";
	private static final String LABEL_REVOGADO_SIM = "SIM";
	
	/**
	 * Filtro Simples
	 */
	private String pesquisaSimples;
	
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	private ChecklistServicoVerificacaoProduto checklist;
	private IChecklistBO checklistBO;
	private IProdutoBO produtoBO;
	private IServicoVerificacaoBO servicoVerificacaoBO;
	private IServicoVerificacaoProdutoBO servicoVerificacaoProdutoBO;
	private ICanalComercialProdutoBO canalComercialProdutoBO;
	private Integer idChecklistImportado;
	private Integer idChecklist;
	private List<SelectItem> listTpCanalComercializacao;
	private List<ChecklistServicoVerificacaoProduto> listChecklists;
	private List<ChecklistServicoVerificacaoProduto> listImportacao;
	private ChecklistVO checklistVO = new ChecklistVO();
	private VinculacoesChecklistVO vinculacoesVO = new VinculacoesChecklistVO();
	private boolean isImportacao;
	private boolean existeChecklist;
	private boolean validado;
	
	// -------------------------------------------------------------------------
	// Filtro Avançado
	private Short operacao;
	private String filtroProduto;
	private String situacao;
	private Integer codServicoVerificacao;
	private String codProduto;
	private Date dataInicio;
	private Date dataFim;
	private Short nuCanal;
	private boolean revogado;
	
	private static String MENSAGEM = "";
	
	public ChecklistMB() {
	}
	
	@PostConstruct
	public void init() {
		setFiltro();
	}
	
	/**
	 * @param checklistBO
	 */
	@Autowired
	public void setChecklistBO(IChecklistBO checklistBO) {
		this.checklistBO = checklistBO;
	}
	
	@Autowired
	public void setProdutoBO(IProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}
	
	@Autowired
	public void setServicoVerificacaoBO(IServicoVerificacaoBO servicoVerificacaoBO) {
		this.servicoVerificacaoBO = servicoVerificacaoBO;
	}
	
	@Autowired
	public void setCanalComercialProdutoBO(ICanalComercialProdutoBO canalComercialProdutoBO) {
		this.canalComercialProdutoBO = canalComercialProdutoBO;
	}
	
	@Autowired
	public void setServicoVerificacaoProdutoBO(IServicoVerificacaoProdutoBO servicoVerificacaoProdutoBO) {
		this.servicoVerificacaoProdutoBO = servicoVerificacaoProdutoBO;
		setModoInicio();
	}
	
	@Autowired
	public void setMatrizAbrangenciaBO(IMatrizAbrangenciaBO matrizAbrangenciaBO) {
		this.matrizAbrangenciaBO = matrizAbrangenciaBO;
	}
	
	public void setIdChecklist(Integer idChecklist) {
		this.idChecklist = idChecklist;
	}
	
	public Integer getIdChecklist() {
		return idChecklist;
	}
	
	/**
	 * @return the idChecklistImportado
	 */
	public Integer getIdChecklistImportado() {
		return idChecklistImportado;
	}
	
	/**
	 * @param idChecklistImportado
	 *            the idChecklistImportado to set
	 */
	public void setIdChecklistImportado(Integer idChecklistImportado) {
		this.idChecklistImportado = idChecklistImportado;
	}
	
	/**
	 * @return the validado
	 */
	public boolean isValidado() {
		return validado;
	}
	
	/**
	 * @param validado
	 *            the validado to set
	 */
	public void setValidado(boolean validado) {
		this.validado = validado;
	}
	
	public boolean getExisteChecklist() {
		return existeChecklist;
	}
	
	public void setExisteChecklist(boolean existeChecklist) {
		this.existeChecklist = existeChecklist;
	}
	
	/**
	 * @return checklistVO
	 */
	public ChecklistVO getChecklistVO() {
		return checklistVO;
	}
	
	/**
	 * @param checklistVO
	 */
	public void setChecklistVO(ChecklistVO checklistVO) {
		this.checklistVO = checklistVO;
	}
	
	/**
	 * @return the vinculacoesVO
	 */
	public VinculacoesChecklistVO getVinculacoesVO() {
		return vinculacoesVO;
	}
	
	/**
	 * @param vinculacoesVO
	 *            the vinculacoesVO to set
	 */
	public void setVinculacoesVO(VinculacoesChecklistVO vinculacoesVO) {
		this.vinculacoesVO = vinculacoesVO;
	}
	
	public ChecklistServicoVerificacaoProduto getChecklist() {
		return checklist;
	}
	
	/**
	 * Preenche o grid de checklists.
	 * 
	 * @return lista de itens de seleção de checklists
	 * @throws DAOException
	 */
	public List<ChecklistServicoVerificacaoProduto> getListChecklists() throws DAOException {
		
		Boolean origemChecklist = (Boolean) getHttpSession().getAttribute("checklistAltera");
		
		if (Utilities.notEmpty(origemChecklist) && origemChecklist)
		{
			listChecklists = new ArrayList<ChecklistServicoVerificacaoProduto>();
			listChecklists = doBuscaRegistros();
			
			getHttpSession().setAttribute("checklistAltera", false);
		}
		
		return listChecklists;
	}
	
	/**
	 * Preenche o combo de situações.
	 * 
	 * @return lista de itens de seleção de situações
	 */
	public List<SelectItem> getListSituacoes() {
		List<SelectItem> situacoes = new ArrayList<SelectItem>();
		for (String situacao : checklistVO.getListSituacoes()) {
			situacoes.add(new SelectItem(situacao));
		}
		
		Collections.sort(situacoes, new Comparator<SelectItem>() {
			public int compare(SelectItem obj1, SelectItem obj2) {
				return obj1.getLabel().compareTo(obj2.getLabel());
			}
		});
		
		return situacoes;
	}
	
	/**
	 * Preenche o combo de produtos.
	 * 
	 * @return lista de itens de seleção de produtos
	 */
	public List<SelectItem> getListProdutos() {
		
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<Produto> produtos;
		Short nuPerfil = getUsuarioLogado().getPerfis().get(0);
		String matricula = getUsuarioLogado().getMatricula();
		List<String> produtosAbrangentes = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, nuPerfil);
		if (operacao != null && !operacao.equals(0)) {
			produtos = produtoBO.findByOperacao(operacao, produtosAbrangentes);
		} else {
			produtos = matrizAbrangenciaBO.getListAbrangenciaProdutoObj(matricula, nuPerfil);
			if (produtos == null) {
				produtos = produtoBO.getListProduto();
			}
		}
		boolean first = Utilities.notEmpty(filtroProduto);
		for (Produto p : produtos) {
			// Valida se o valor digitado no filtro é e se ele está contido na concatenação.
			if (Utilities.notEmpty(filtroProduto) && !(p.getOperacaoFormatada() + "-" + p.getModalidadeFormatada() + " " + p.getNoProduto()).contains(filtroProduto)) {
				continue;
			}
			list.add(new SelectItem(p.getCoProduto(), p.getOperacaoFormatada() + "-" + p.getModalidadeFormatada() + " " + p.getNoProduto()));
		}
		
		if (first && !produtos.isEmpty()) {
			first = false;
			codProduto = produtos.get(0).getCoProduto();
		}
		
		return list;
	}
	
	public void doLimpar(ActionEvent evt) {
		checklistVO = new ChecklistVO();
		
		// limpa os filtros da sessão.
		getFilterBase().limparFiltros();
		
		operacao = null;
		filtroProduto = "";
		situacao = "";
		codServicoVerificacao = null;
		codProduto = "";
		dataInicio = null;
		dataFim = null;
		nuCanal = null;
		revogado = false;
		pesquisaSimples = "";
		
	}
	
	private void doLimparAvancado() {
		operacao = null;
		filtroProduto = "";
		situacao = "";
		codServicoVerificacao = null;
		codProduto = "";
		dataInicio = null;
		dataFim = null;
		nuCanal = null;
		revogado = false;
	}
	
	private void doLimparSimples() {
		pesquisaSimples = "";
	}
	
	/**
	 * Preenche o combo de serviços de verificacao.
	 * 
	 * @return lista de itens de seleção de serviços de verificação
	 */
	public List<SelectItem> getListServicoVerificacao() {
		
		String codProduto;
		
		if (isModoFiltro())
			codProduto = this.codProduto;
		else
			codProduto = checklistVO.getCodProduto();
		
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
	 */
	public void doCadastrar(ActionEvent evt) {
		limparCamposVO();
		checklistVO.setSituacao(ChecklistVO.CHKLST_SITUACAO_PROJETO);
		
		listImportacao = new ArrayList<ChecklistServicoVerificacaoProduto>();
		listImportacao = checklistBO.simpleFilter();
		
		setModoCadastro();
	}
	
	private void limparCamposVO() {
		checklistVO.setOperacao(null);
		checklistVO.setCodProduto("");
		checklistVO.setCodServicoVerificacao(null);
		checklistVO.setFiltroProduto("");
		checklistVO.setDataInicio(null);
		checklistVO.setDataFim(null);
		checklistVO.setSituacao(null);
		checklistVO.setRevogado(false);
		checklistVO.setNuCanal(null);
	}
	
	/**
	 * Realiza a consulta dos dados.
	 */
	public void doConsultarClick(ActionEvent evt) {
		List<ChecklistServicoVerificacaoProduto> busca = doBuscaRegistros();
		if (busca.isEmpty()) {
			warn(MSGS, MN016);
		}
		listChecklists = busca;
	}
	
	/**
	 * Método responsável por realizar a busca dos registros
	 * 
	 * @return
	 */
	public List<ChecklistServicoVerificacaoProduto> doBuscaRegistros() {
		List<ChecklistServicoVerificacaoProduto> busca = new ArrayList<ChecklistServicoVerificacaoProduto>();
		
		// Limpa o filtro
		getFilterBase().limparFiltros();
		
		if (isModoInicio()) {
			
			doLimparAvancado();
			
			// Modo simples
			getFilterBase().setModoSimples();
			
			getFilterBase().addToFilter("pesquisaSimples", pesquisaSimples);
			
			// Define que o filtro base possui um filtro de checklist.
			getFilterBase().addToFilter("filtroChecklist", true);
			
			// Coloca o filtro com os valores adicionados na sessão.
			putFilterBase(this.getClass());
			
			busca = checklistBO.simpleFilter(getFilterBase());
			
		} else {
			
			doLimparSimples();
			
			// Modo avançado
			getFilterBase().setModoAvancado();
			
			getFilterBase().addToFilter("operacao", operacao);
			getFilterBase().addToFilter("codProduto", codProduto);
			getFilterBase().addToFilter("filtroProduto", filtroProduto);
			getFilterBase().addToFilter("situacao", situacao);
			getFilterBase().addToFilter("codServicoVerificacao", codServicoVerificacao);
			getFilterBase().addToFilter("dataInicio", dataInicio);
			getFilterBase().addToFilter("dataFim", dataFim);
			getFilterBase().addToFilter("revogado", revogado);
			getFilterBase().addToFilter("nuCanal", nuCanal);
			
			// Define que o filtro base possui um filtro de checklist.
			getFilterBase().addToFilter("filtroChecklist", true);
			
			// Coloca o filtro com os valores adicionados na sessão.
			putFilterBase(this.getClass());
			
			try {
				busca = checklistBO.filter(getFilterBase());
			} catch (DAOException ex) {
				LogCEF.error(ex);
			}
		}
		
		return busca;
	}
	
	/**
	 * Realiza a importação de um checklist já existente no sistema.
	 */
	public void doImportarClick() {
		isImportacao = true;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		checklist = (ChecklistServicoVerificacaoProduto) facesContext.getExternalContext().getRequestMap().get("checklist");
		
		idChecklistImportado = checklist.getNuChecklistServicoVerificacaoProduto();
	}
	
	/**
	 * Exclui o checklist selecionado no grid.
	 */
	public void doExcluirClick() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ChecklistServicoVerificacaoProduto checklist = (ChecklistServicoVerificacaoProduto) facesContext.getExternalContext().getRequestMap().get("checklist");
		try {
			checklistBO.delete(checklist);
			listChecklists = null;
			
			setFiltro();
			
			info(MSGS, MN009);
		} catch (DAOException ex) {
			LogCEF.error(ex);
		}
	}
	
	/**
	 * Altera o estado da tela para o modo de pesquisa.
	 * 
	 * @return null
	 */
	public String doPesquisarClick() {
		checklistVO.setOperacao(null);
		checklistVO.setSituacao("");
		checklistVO.setCodProduto("");
		checklistVO.setFiltroProduto("");
		checklistVO.setDataInicio(null);
		checklistVO.setDataFim(null);
		checklistVO.setRevogado(false);
		checklistVO.setNuCanal(null);
		warn(MSGS, MN003);
		setModoInicio();
		return null;
	}
	
	private void modoInicio() {
		setModoInicio();
		checklist = new ChecklistServicoVerificacaoProduto();
	}
	
	/**
	 * Verifica se o checklist a ser cadastro já existe no sistema.
	 */
	public void doTrySave(ActionEvent evt) {
		setValidado(true);
		
		if (validaCampos()) {
			Produto prod = new Produto();
			prod.setCoProduto(checklistVO.getCodProduto());
			
			ServicoVerificacao sv = new ServicoVerificacao();
			sv.setNuServicoVerificacao(checklistVO.getCodServicoVerificacao());
			
			ServicoVerificacaoProduto svp = servicoVerificacaoProdutoBO.get(prod, sv);
			
			checklist = new ChecklistServicoVerificacaoProduto();
			checklist.setServicoVerificacaoProduto(svp);
			checklist.setIcSituacao(checklistVO.getSituacao());
			checklist.setDataInicio(checklistVO.getDataInicio());
			checklist.setDataFim(checklistVO.getDataFim());
			checklist.setIcRevogado(false);
			
			if (checklistVO.getNuCanal() != null) {
				checklist.setCanalComercializacao(canalComercialProdutoBO.findById(checklistVO.getNuCanal()));
			}
			try {
				List<ChecklistServicoVerificacaoProduto> busca = checklistBO.findByChecklist(checklist);
				if (busca.size() == 1) {
					setExisteChecklist(true);
				} else {
					setExisteChecklist(false);
				}
			} catch (DAOException ex) {
				LogCEF.error(ex);
			}
		} else {
			setValidado(false);
		}
	}
	
	/**
	 * Salva o checklist.
	 * 
	 * @return null
	 * @throws DAOException
	 */
	public String doSave() throws DAOException {
		if (validado) {
			try {
				if (isImportacao) {
					checklist = checklistBO.merge(checklist);
					checklistBO.duplicateFilhosChecklist(checklist, idChecklistImportado);
					setIdChecklist(checklist.getNuChecklistServicoVerificacaoProduto());
					isImportacao = false;
					setFiltro();
					return doAlteraClick();
				}
				setIdChecklist(checklistBO.merge(checklist).getNuChecklistServicoVerificacaoProduto());
			} catch (DAOException ex) {
				LogCEF.error(ex);
			}
			info(MSGS, MN007);
			setFiltro();
			return doAlteraClick();
		}
		
		error(MSGS, MN002);
		warn(MENSAGEM);
		MENSAGEM = "";
		return null;
	}
	
	/**
	 * Substitui o checklist que está sendo cadastro pelo existente no sistema.
	 * 
	 * @return null
	 */
	public String doReplaceChecklist() {
		try {
			List<ChecklistServicoVerificacaoProduto> busca = checklistBO.findByChecklist(checklist);
			checklist = busca.get(0);
			setIdChecklist(checklist.getNuChecklistServicoVerificacaoProduto());
			checklist.setDataInicio(checklistVO.getDataInicio());
			checklist.setDataFim(checklistVO.getDataFim());
			if (checklistVO.getNuCanal() != null) {
				checklist.setCanalComercializacao(canalComercialProdutoBO.findById(checklistVO.getNuCanal()));
			}
			Produto p = new Produto();
			p.setCoProduto(checklistVO.getCodProduto());
			ServicoVerificacao sv = new ServicoVerificacao();
			sv.setNuServicoVerificacao(checklistVO.getCodServicoVerificacao());
			checklist.setServicoVerificacaoProduto(servicoVerificacaoProdutoBO.get(p, sv));
			
			if (isImportacao) {
				checklist.setBlocoChecklistProdutoList(new ArrayList<BlocoChecklistProduto>());
				checklistBO.replaceChecklist(checklist, idChecklistImportado);
			} else {
				checklistBO.merge(checklist);
			}
			isImportacao = false;
		} catch (DAOException ex) {
			LogCEF.error(ex);
		}
		info(MSGS, MN007);
		return doAlteraClick();
	}
	
	/**
	 * Valida se os campos estão preenchidos baseado na regra de negócio.
	 * 
	 * @return true ou false
	 */
	private boolean validaCampos() {
		
		if (Utilities.empty(checklistVO.getCodProduto())) {
			MENSAGEM = "Campo PRODUTO obrigatório.";
			return false;
		}
		
		if (Utilities.empty(checklistVO.getCodServicoVerificacao())) {
			MENSAGEM = "Campo SERVIÇO DE VERIFICAÇÃO obrigatório.";
			return false;
		}
		if (Utilities.empty(checklistVO.getSituacao())) {
			MENSAGEM = "Campo SITUAÇÃO obrigatório.";
			return false;
		}
		if (!Utilities.empty(checklistVO.getDataInicio())) {
			Date dataSistema = obtemDataSemHoras(new Date());
			Date dataInicio = obtemDataSemHoras(checklistVO.getDataInicio());
			if (dataInicio.compareTo(dataSistema) < 0) {
				MENSAGEM = "Campo DATA INÍCIO menor que a data atual do sistema.";
				return false;
			}
			if (!Utilities.empty(checklistVO.getDataFim())) {
				Date dataFim = obtemDataSemHoras(checklistVO.getDataFim());
				if (dataFim.compareTo(dataInicio) < 0) {
					MENSAGEM = "Campo DATA FINAL menor que a DATA INICIAL.";
					return false;
				}
			}
		} else {
			MENSAGEM = "Campo DATA INÍCIO obrigatório.";
			return false;
		}
		return true;
	}
	
	/**
	 * Método utilitário para configurar a data para que possua somente o ano, mês e dia.
	 * 
	 * @param data
	 * @return data sem horas, minutos e segundos
	 */
	private Date obtemDataSemHoras(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			return sdf.parse(sdf.format(data));
		} catch (ParseException ex) {
			LogCEF.error(ex);
		}
		
		return null;
	}
	
	/**
	 * Verifica se o método foi chamado a partir da tela inicial. Caso tenha sido, obtém o checklist do grid e verifica
	 * se já existe uma instância de ChecklistAlteraMB e existindo atribui o código do checklist que será alterado para
	 * este MB. Caso não exista uma instância de ChecklistAlteraMB, basta setar o código na session que o mesmo será
	 * responsável por obter esse código durante sua inicialização, quando o método setChecklistBO for injetado.
	 * 
	 * @return outcome
	 */
	public String doAlteraClick() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ChecklistServicoVerificacaoProduto grid = (ChecklistServicoVerificacaoProduto) externalContext.getRequestMap().get("checklist");
		
		ChecklistAlteraMB mb = (ChecklistAlteraMB) externalContext.getSessionMap().get("checklistAlteraMB");
		Integer codChecklist = null;
		if (grid != null) {
			codChecklist = grid.getNuChecklistServicoVerificacaoProduto();
		}
		if (mb == null) {
			if (getIdChecklist() != null) {
				((HttpSession) externalContext.getSession(false)).setAttribute("codigo", getIdChecklist());
			} else {
				((HttpSession) externalContext.getSession(false)).setAttribute("codigo", codChecklist);
			}
			((HttpSession) externalContext.getSession(false)).setAttribute("acao", "altera");
		} else {
			if (getIdChecklist() != null) {
				mb.setCodChecklist(getIdChecklist());
			} else {
				mb.setCodChecklist(codChecklist);
			}
			mb.getChecklistVO().setCodBloco(null);
			mb.setModoInicio();
		}
		
		return "toChecklistProdutoAltera";
	}
	
	/**
	 * Verifica se o método foi chamado a partir da tela inicial. Caso tenha sido, obtém o checklist do grid e verifica
	 * se já existe uma instância de ChecklistAlteraMB e existindo atribui o código do checklist que será alterado para
	 * este MB. Caso não exista uma instância de ChecklistAlteraMB, basta setar o código na session que o mesmo será
	 * responsável por obter esse código durante sua inicialização, quando o método setChecklistBO for injetado.
	 * 
	 * @return outcome
	 */
	public String doVisualizaClick() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		ChecklistServicoVerificacaoProduto grid = (ChecklistServicoVerificacaoProduto) context.getExternalContext().getRequestMap().get("checklist");
		
		ChecklistAlteraMB mb = (ChecklistAlteraMB) context.getExternalContext().getSessionMap().get("checklistAlteraMB");
		Integer codChecklist = grid.getNuChecklistServicoVerificacaoProduto();
		
		if (mb == null) {
			((HttpSession) context.getExternalContext().getSession(false)).setAttribute("codigo", codChecklist);
			((HttpSession) context.getExternalContext().getSession(false)).setAttribute("acao", "visualiza");
		} else {
			mb.setCodChecklist(codChecklist);
			mb.getChecklistVO().setCodBloco(null);
			mb.setModoVisualiza();
		}
		
		return "toChecklistProdutoAltera";
	}
	
	public boolean isShowNovo() {
		return isModoCadastro();
	}
	
	public boolean isShowAltera() {
		return isModoAltera();
	}
	
	public boolean isShowDataTable() {
		return isModoInicio() || isModoFiltro();
	}
	
	/**
	 * Clique do botao 'Cancelar'/'Voltar'.
	 * 
	 * @param evt
	 */
	public void doCancelar() {
		setFiltro();
		
		checklistVO = new ChecklistVO();
		checklist = new ChecklistServicoVerificacaoProduto();
		idChecklistImportado = null;
		isImportacao = false;
		warn(MSGS, MN003);
	}
	
	public String getPesquisaSimples() {
		return this.pesquisaSimples;
	}
	
	public void setPesquisaSimples(String pesquisaSimples) {
		this.pesquisaSimples = pesquisaSimples;
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
	
	public void doConsultarSimples(ActionEvent evt) {
		setModoInicio();
	}
	
	public void doConsultarAvancado(ActionEvent evt) {
		setModoFiltro();
	}
	
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
		
		pesquisaSimples = getFilterBase().getString("pesquisaSimples");
		operacao = getFilterBase().getShort("operacao");
		codProduto = getFilterBase().getString("codProduto");
		filtroProduto = getFilterBase().getString("filtroProduto");
		situacao = getFilterBase().getString("situacao");
		codServicoVerificacao = getFilterBase().getInt("codServicoVerificacao");
		dataInicio = getFilterBase().getDate("dataInicio");
		dataFim = getFilterBase().getDate("dataFim");
		revogado = getFilterBase().getBoolean("revogado");
		nuCanal = getFilterBase().getShort("nuCanal");
		
		if (getFilterBase().isModoAvancado()) {
			setModoFiltro();
		} else {
			setModoInicio();
		}
		
		// Método que realiza a busca no momento da construção do Bean
		listChecklists = doBuscaRegistros();
	}
	
	public List<SelectItem> getListTipoCanalComercializacao() {
		if (listTpCanalComercializacao == null) {
			listTpCanalComercializacao = new ArrayList<SelectItem>();
			List<CanalComercializacaoProduto> listCanal = canalComercialProdutoBO.getListCanalComercial();
			for (CanalComercializacaoProduto ccp : listCanal) {
				listTpCanalComercializacao.add(new SelectItem(ccp.getNuCanalCmrco(), ccp.getNoCanalCmrco()));
			}
		}
		
		return listTpCanalComercializacao;
	}
	
	public boolean isShowColumnAlterar() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		checklist = (ChecklistServicoVerificacaoProduto) facesContext.getExternalContext().getRequestMap().get("checklist");
		if (checklist.getIcRevogado() == true || checklist.getIcSituacao().trim().equals(ChecklistVO.CHKLST_SITUACAO_PUBLICADO)) {
			return false;
		}
		
		return true;
	}
	
	public String getRevogadoFormatado() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		checklist = (ChecklistServicoVerificacaoProduto) facesContext.getExternalContext().getRequestMap().get("checklist");
		if (checklist.getIcRevogado() == true) {
			return LABEL_REVOGADO_SIM;
		}
		
		return LABEL_REVOGADO_NAO;
	}
	
	public void doConsultarRelatorioClick(ActionEvent evt) {
		setModoFiltro();
		doConsultarClick(evt);
	}
	
	private boolean errorDownload = false;
	
	public boolean isErrorDownload() {
		return errorDownload;
	}
	
	public void setErrorDownload(boolean errorDownload) {
		this.errorDownload = errorDownload;
	}
	
	public void doGerarRelatorio(ActionEvent evt) {
		ChecklistServicoVerificacaoProduto chk = (ChecklistServicoVerificacaoProduto) getRequestMap().get("checklist");
		byte[] b;
		String matricula = SegurancaUsuario.getInstance().getUsuario().getMatricula();
		String caminho = getCaminhoRelatorio();
		
		try {
			b = checklistBO.geraRelatorio(caminho, matricula, chk.getNuChecklistServicoVerificacaoProduto());
			String nomeArquivo = "RelatorioChecklist.pdf";
			addRelatorioSessao(b, nomeArquivo);
			LogCEF.debug("Relatorio gerado com sucesso");
			errorDownload = false;
		} catch (JRException e) {
			error("Erro ao gerar relatório.");
			LogCEF.error(e.getMessage());
			errorDownload = true;
		} catch (ReportInvalidPathException e) {
			error("Erro ao gerar relatório.");
			LogCEF.error(e.getMessage());
			errorDownload = true;
		} catch (ReportErrorCreateDatasourceException e) {
			error("Não existe dados para gerar este relatório.");
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
	
	// Getter e Setters Filtro Avançado
	
	public String getSituacao() {
		return situacao;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public Integer getCodServicoVerificacao() {
		return codServicoVerificacao;
	}
	
	public void setCodServicoVerificacao(Integer codServicoVerificacao) {
		this.codServicoVerificacao = codServicoVerificacao;
	}
	
	public String getCodProduto() {
		return codProduto;
	}
	
	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public Date getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public Short getNuCanal() {
		return nuCanal;
	}
	
	public void setNuCanal(Short nuCanal) {
		this.nuCanal = nuCanal;
	}
	
	public boolean isRevogado() {
		return revogado;
	}
	
	public void setRevogado(boolean revogado) {
		this.revogado = revogado;
	}
	
	public Short getOperacao() {
		return operacao;
	}
	
	public void setOperacao(Short operacao) {
		this.operacao = operacao;
	}
	
	public String getFiltroProduto() {
		return filtroProduto;
	}
	
	public void setFiltroProduto(String filtroProduto) {
		this.filtroProduto = filtroProduto;
	}
	
	public List<ChecklistServicoVerificacaoProduto> getListImportacao() {
		return listImportacao;
	}
	
	public void setListImportacao(List<ChecklistServicoVerificacaoProduto> listImportacao) {
		this.listImportacao = listImportacao;
	}
}