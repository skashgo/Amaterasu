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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.richfaces.component.html.HtmlPanel;
import org.richfaces.component.html.HtmlSimpleTogglePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.IApontamentoBO;
import br.gov.caixa.siiac.bo.IApontamentoChecklistProdutoBO;
import br.gov.caixa.siiac.bo.IBlocoChecklistBO;
import br.gov.caixa.siiac.bo.IBlocoChecklistProdutoBO;
import br.gov.caixa.siiac.bo.IChecklistBO;
import br.gov.caixa.siiac.bo.IItemVerificacaoBO;
import br.gov.caixa.siiac.bo.IItemVerificacaoChecklistProdutoBO;
import br.gov.caixa.siiac.bo.IRestricaoBO;
import br.gov.caixa.siiac.facade.IVinculacoesChecklistFacade;
import br.gov.caixa.siiac.model.ChecklistVO;
import br.gov.caixa.siiac.model.VinculacoesChecklistVO;
import br.gov.caixa.siiac.model.domain.Apontamento;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.BlocoChecklist;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.CanalComercializacaoProduto;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.ItemVerificacao;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;
import br.gov.caixa.siiac.model.domain.Restricao;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 * 
 */

@Service()
@Scope("session")
public class ChecklistAlteraMB extends AbstractMB{
	
	private ChecklistServicoVerificacaoProduto checklist = new ChecklistServicoVerificacaoProduto();
	private IApontamentoBO apontamentoBO;
	private IApontamentoChecklistProdutoBO apontamentoChecklistProdutoBO;
	private IBlocoChecklistBO blocoChecklistBO;
	private IBlocoChecklistProdutoBO blocoChecklistProdutoBO;
	private IChecklistBO checklistBO;
	private IItemVerificacaoBO itemVerificacaoBO;
	private IItemVerificacaoChecklistProdutoBO itemVerificacaoChecklistProdutoBO;
	private IRestricaoBO restricaoBO;
	private IVinculacoesChecklistFacade vinculacoesChecklistFacade;
	private Integer codChecklist;
	private List<SelectItem> listItemBlocos;
	private List<SelectItem> listNaoVinculados = new ArrayList<SelectItem>();
	private List<SelectItem> listVinculados = new ArrayList<SelectItem>();
	private List<BlocoChecklist> blocos;
	private List<ChecklistServicoVerificacaoProduto> listChecklists;
	private List<ChecklistServicoVerificacaoProduto> listChecklistsSelecionados = new ArrayList<ChecklistServicoVerificacaoProduto>();
	private ChecklistVO checklistVO = new ChecklistVO();
	private VinculacoesChecklistVO vinculacoesChecklistVO = new VinculacoesChecklistVO();
	private String rotuloDragDrop;
	private boolean showAltera;
	private boolean showModal = false;
	private boolean showModalAjuda;
	private boolean showModalOrdena;
	private boolean showModalDragAndDrop;
	private HtmlPanel itensPanel = new HtmlPanel();
	private String SITUACAO;
	private String nivel;
	private Integer id;
    Integer qtdBloco = 0;
    Integer qtdItem  = 0;
    Integer qtdApto  = 0;
    
    private boolean validInsertBloco = true;
    private boolean ativaCheckBoxAjuda;
    private boolean ativaRadioBoxAjuda;
    
	public ChecklistAlteraMB() {
		String action = (String)((HttpSession) getExternalContext().getSession(false)).getAttribute("acao");
		if(action.equals("visualiza")){
			setModoVisualiza();
		}else{
			setModoInicio();
		}
	}

	@Autowired
	public void setChecklistBO(IChecklistBO checklistBO) {
		codChecklist = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo");
		this.checklistBO = checklistBO;
		checklist = checklistBO.findById(codChecklist);
//		atualizaSituacao();
	}

	@Autowired
	public void setBlocoChecklistBO(IBlocoChecklistBO blocoChecklistBO) {
		this.blocoChecklistBO = blocoChecklistBO;
	}

	@Autowired
	public void setBlocoChecklistProdutoBO(IBlocoChecklistProdutoBO blocoChecklistProdutoBO) {
		this.blocoChecklistProdutoBO = blocoChecklistProdutoBO;
	}

	@Autowired
	public void setItemVerificacaoChecklistProdutoBO(IItemVerificacaoChecklistProdutoBO itemVerificacaoChecklistProdutoBO) {
		this.itemVerificacaoChecklistProdutoBO = itemVerificacaoChecklistProdutoBO;
	}

	@Autowired
	public void setApontamentoChecklistProdutoBO(IApontamentoChecklistProdutoBO apontamentoChecklistProdutoBO) {
		this.apontamentoChecklistProdutoBO = apontamentoChecklistProdutoBO;
	}

	@Autowired
	public void setItemVerificacaoBO(IItemVerificacaoBO itemVerificacaoBO) {
		this.itemVerificacaoBO = itemVerificacaoBO;
	}
	
	@Autowired
	public void setApontamentoBO(IApontamentoBO apontamentoBO) {
		this.apontamentoBO = apontamentoBO;
	}
	
	@Autowired
	public void setRestricaoBO(IRestricaoBO restricaoBO) {
		this.restricaoBO = restricaoBO;
	}

	@Autowired
	public void setVinculacoesChecklistFacade(IVinculacoesChecklistFacade vinculacoesChecklistFacade) {
		this.vinculacoesChecklistFacade = vinculacoesChecklistFacade;
	}
		
	/**
	 * @return the codChecklist
	 */
	public Integer getCodChecklist() {
		return codChecklist;
	}

	/**
	 * @param codChecklist the codChecklist to set
	 */
	public void setCodChecklist(Integer codChecklist) {
		this.codChecklist = codChecklist;
		
		checklist = checklistBO.findById(codChecklist);
	}

	/**
	 * @return the checklist
	 */
	public ChecklistServicoVerificacaoProduto getChecklist() {
		return checklist;
	}

	/**
	 * @param checklist
	 *            the checklist to set
	 */
	public void setChecklist(ChecklistServicoVerificacaoProduto checklist) {
		this.checklist = checklist;
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
	 * Preenche o combo de blocos com todos os blocos não vinculados ao presente
	 * checklist.
	 * 
	 * @return lista de itens de seleção
	 */
	public List<SelectItem> getListItemBlocos() {
		
			try {
				blocos = blocoChecklistBO.getAllNotChecklist(checklist);
				listItemBlocos = new ArrayList<SelectItem>();

				for (BlocoChecklist bloco : blocos) {
					listItemBlocos.add(new SelectItem(bloco.getNuBlocoChecklist(), bloco.getNoBlocoChecklist()));
				}
			} catch (DAOException ex) {
				LogCEF.error(ex);
			}

		return listItemBlocos;
	}

//	private void atualizaSituacao() {
//		if (checklist.getIcSituacao().trim().equals(ChecklistVO.CHKLST_SITUACAO_REVOGADO)) { 
//			setShowRevogado(false);
//			setShowAutorizado(false);
//			setShowPublicado(false);
//
//		} else if (checklist.getIcSituacao().trim().equals(ChecklistVO.CHKLST_SITUACAO_AUTORIZADO)) {
//			setShowRevogado(true);
//			setShowAutorizado(false);
//			setShowPublicado(true);
//
////			setShowAcaoAlterar(true);
//		} else if (checklist.getIcSituacao().trim().equals(ChecklistVO.CHKLST_SITUACAO_PROJETO)) {
//			setShowRevogado(true);
//			setShowAutorizado(true);
//			setShowPublicado(false);
//
////			setShowAcaoAlterar(true);
//		} else if (checklist.getIcSituacao().trim().equals(ChecklistVO.CHKLST_SITUACAO_PUBLICADO)) {
//			setShowRevogado(true);
//			setShowAutorizado(false);
//			setShowPublicado(false);
//
////			setShowAcaoAlterar(true);
//		}
//	}
	
	private void fillListVinculados(String tipo, Integer id) {
		listVinculados = new ArrayList<SelectItem>();
		try {
			if(tipo.equals("bloco")) {
				BlocoChecklistProduto bloco = blocoChecklistProdutoBO.findById(id);
				List<ItemVerificacao> itensDeVerificacaoVinculados = itemVerificacaoBO.getAllBloco(bloco);
				
				for(ItemVerificacao item : itensDeVerificacaoVinculados) {
					listVinculados.add(new SelectItem(item.getNuItemVerificacao(), item.getNoItemVerificacao()));
				}
			} else if(tipo.equals("item")) {
				ItemVerificacaoChecklistProduto item = itemVerificacaoChecklistProdutoBO.findById(id);
				List<Apontamento> apontamentosVinculados = apontamentoBO.getAllItem(item);
				
				for(Apontamento apontamento : apontamentosVinculados) {
					listVinculados.add(new SelectItem(apontamento.getNuApontamentoChecklist(), apontamento.getNoApontamentoChecklist()));
				}
			} else if(tipo.trim().equals("apontamento")) {
				ApontamentoChecklistProduto apontamento = apontamentoChecklistProdutoBO.findById(id);
				List<Restricao> restricoesVinculadas = restricaoBO.getAllApontamento(apontamento);
				
				for(Restricao restricao : restricoesVinculadas) {
					listVinculados.add(new SelectItem(restricao.getNuRestricao(), restricao.getNoRestricao()));
				}
			} else {
				List<BlocoChecklistProduto> bloco = checklist.getBlocoChecklistProdutoList();
				
				for(BlocoChecklistProduto blc : bloco) {
					listVinculados.add(new SelectItem(blc.getBlocoChecklist().getNuBlocoChecklist(), blc.getBlocoChecklist().getNoBlocoChecklist()));
				}
			}
		} catch(DAOException ex) {
			LogCEF.error(ex);
		}
	}
	
	public List<SelectItem> getVinculadosList() {
		return listVinculados;
	}

	private void fillListNaoVinculados(String tipo, Integer id) {
		//Limpando a lista...
		listNaoVinculados = new ArrayList<SelectItem>();
		
		try {
			if(tipo.trim().equals("bloco")) {
				BlocoChecklistProduto blocoBusca = blocoChecklistProdutoBO.findById(id);
				List<ItemVerificacao> itensDeVerificacaoNaoVinculados = itemVerificacaoBO.getAllNotBloco(blocoBusca);
				
				for(ItemVerificacao item : itensDeVerificacaoNaoVinculados) {
					listNaoVinculados.add(new SelectItem(item.getNuItemVerificacao(), item.getNoItemVerificacao()));
				}
			} else if(tipo.trim().equals("item")) {
				ItemVerificacaoChecklistProduto item = itemVerificacaoChecklistProdutoBO.findById(id);
				List<Apontamento> apontamentosNaoVinculados = apontamentoBO.getAllNotItem(item);
				
				for(Apontamento apontamento : apontamentosNaoVinculados) {
					listNaoVinculados.add(new SelectItem(apontamento.getNuApontamentoChecklist(), apontamento.getNoApontamentoChecklist()));
				}
			} else {
				ApontamentoChecklistProduto apontamento = apontamentoChecklistProdutoBO.findById(id);
				List<Restricao> restricoesNaoVinculadas = restricaoBO.getAllNotApontamento(apontamento);
				
				for(Restricao restricao : restricoesNaoVinculadas) {
					listNaoVinculados.add(new SelectItem(restricao.getNuRestricao(), restricao.getNoRestricao()));
				}
			}
		} catch(DAOException ex) {
			LogCEF.error(ex);
		}
	}
	
	public List<SelectItem> getNaoVinculadosList() {
		return listNaoVinculados;
	}

	/**
	 * Insere o bloco no checklist e remove o mesmo do combobox.
	 * 
	 * @param ev
	 */
	public void doInsertBloco(ActionEvent ev) {
		if (checklistVO.getCodBloco().equals(0)) {
			warn("É Necessário escolher pelo menos um bloco para inserir.");
		}
		
		Integer codAuxBloco;
		
		for (SelectItem item : listItemBlocos) {
			codAuxBloco = (Integer) item.getValue();
			if (codAuxBloco.equals(checklistVO.getCodBloco())) {
				listItemBlocos.remove(item);
				
				checklistBO.adicionaBlocoNoChecklist(blocos, codAuxBloco, checklist);
				break;
			}
		}
		checklist = checklistBO.findById(codChecklist);
		getItensPanel();
	}

	/**
	 * Atualiza a lista
	 */
	public String checklistUpdate() {
		String action = ((HttpServletRequest) getExternalContext().getRequest()).getParameter("action");
		String vinculados = ((HttpServletRequest) getExternalContext().getRequest()).getParameter("value");
		setValidInsertBloco(true);
		
		if (action != null && action.equals("insert")) {
			if (vinculados == null || vinculados.equals("0")) {
				setValidInsertBloco(false);
				warn("É Necessário escolher pelo menos um bloco para inserir.");
				return "";
			}
			vinculacoesChecklistVO.setActionInsert();
			vinculacoesChecklistVO.setNivelBloco();
			vinculacoesChecklistVO.setVinculadasStr(vinculados);
			vinculacoesChecklistVO.setChecklistPrincipal(codChecklist);
			vinculacoesChecklistVO.setPai(codChecklist);
			setShowModalDragAndDrop(true);
		} else if (action.equals("dragdrop")) {
			vinculacoesChecklistVO.setActionDragDrop();
			vinculacoesChecklistVO.setVinculadasStr(vinculados);
		} else if (action.equals("delete")) {
			String tipo = ((HttpServletRequest) getExternalContext().getRequest()).getParameter("type");
			vinculacoesChecklistVO.setActionDelete();
			vinculacoesChecklistVO.setChecklistPrincipal(codChecklist);

			if("bloco".equals(tipo)){
				vinculacoesChecklistVO.setNivelBloco();
				BlocoChecklistProduto bloco = blocoChecklistProdutoBO.findById(Integer.parseInt(vinculados));
				vinculacoesChecklistVO.setVinculadasStr(String.valueOf(bloco.getBlocoChecklist().getNuBlocoChecklist()));
				vinculacoesChecklistVO.setPai(bloco.getChecklistServicoVerificacaoProduto().getNuChecklistServicoVerificacaoProduto());
			}else if("item".equals(tipo)){
				vinculacoesChecklistVO.setNivelItem();
				ItemVerificacaoChecklistProduto item = itemVerificacaoChecklistProdutoBO.findById(Integer.parseInt(vinculados));
				vinculacoesChecklistVO.setVinculadasStr(String.valueOf(item.getItemVerificacao().getNuItemVerificacao()));
				vinculacoesChecklistVO.setPai(item.getBlocoChecklistProduto().getNuBlocoChecklistProduto());
			}else if("apontamento".equals(tipo)){
				vinculacoesChecklistVO.setNivelApontamento();
				ApontamentoChecklistProduto apontamento = apontamentoChecklistProdutoBO.findById(Integer.parseInt(vinculados));
				vinculacoesChecklistVO.setVinculadasStr(String.valueOf(apontamento.getApontamento().getNuApontamentoChecklist()));
				vinculacoesChecklistVO.setPai(apontamento.getItemVerificacaoChecklistProduto().getNuItemVerificacaoChecklistProduto());
			}
		} else if(action.equals("update")) { //do Ajuda
			String textarea = ((HttpServletRequest) getExternalContext().getRequest()).getParameter("textarea");
			String radiobut = ((HttpServletRequest) getExternalContext().getRequest()).getParameter("radiobut");
			String checkbox = ((HttpServletRequest) getExternalContext().getRequest()).getParameter("checkbox");
			String checkboxPrejudicado = ((HttpServletRequest) getExternalContext().getRequest()).getParameter("checkboxPrejudicado");

			try {
				if (nivel.trim().equals("bloco")) {
					BlocoChecklistProduto bloco = blocoChecklistProdutoBO.findById(id);
					bloco.setDescricao(textarea); 
					
					if (radiobut.equals("1")) {
						bloco.setIcObrigatorio(true);
						bloco.setIcDesabilita(false);
					} else if (radiobut.equals("2")) {
						bloco.setIcObrigatorio(false);
						bloco.setIcDesabilita(true);
					} else {
						bloco.setIcObrigatorio(false);
						bloco.setIcDesabilita(false);
					}
					bloco.setIcPrejudicado(new Boolean(checkboxPrejudicado));
		
					blocoChecklistProdutoBO.merge(bloco);
					vinculacoesChecklistVO.setVinculadasStr(String.valueOf(bloco.getBlocoChecklist().getNuBlocoChecklist()));
					vinculacoesChecklistVO.setNivelBloco();
					vinculacoesChecklistVO.setPai(bloco.getChecklistServicoVerificacaoProduto().getNuChecklistServicoVerificacaoProduto());
				} else if(nivel.trim().equals("item")) {
					ItemVerificacaoChecklistProduto item = itemVerificacaoChecklistProdutoBO.findById(id);
					item.setDeItemVerificacaoChecklistProduto(textarea);
					if (checkbox.equals("true")) {
						item.setIcDesabilitaItem(true);
					} else {
						item.setIcDesabilitaItem(false);
					}
					itemVerificacaoChecklistProdutoBO.merge(item);
					vinculacoesChecklistVO.setNivelItem();
					vinculacoesChecklistVO.setPai(item.getBlocoChecklistProduto().getNuBlocoChecklistProduto());
					vinculacoesChecklistVO.setVinculadasStr(String.valueOf(item.getItemVerificacao().getNuItemVerificacao()));
				} else {
					ApontamentoChecklistProduto apto = apontamentoChecklistProdutoBO.findById(id);
					apto.setDescricao(textarea);
					if (checkbox.equals("true")) {
						apto.setIcNaoAplica(true);
					} else {
						apto.setIcNaoAplica(false);
					}
					
					apontamentoChecklistProdutoBO.merge(apto);
					vinculacoesChecklistVO.setNivelApontamento();
					vinculacoesChecklistVO.setPai(apto.getItemVerificacaoChecklistProduto().getNuItemVerificacaoChecklistProduto());
					vinculacoesChecklistVO.setVinculadasStr(String.valueOf(apto.getApontamento().getNuApontamentoChecklist()));
				}
		
				vinculacoesChecklistVO.setActionUpdate();
				vinculacoesChecklistVO.setChecklistPrincipal(codChecklist);
			} catch (DAOException ex) {
				LogCEF.error(ex);
			}
		}
		
		try {
			listChecklists = checklistBO.getAllNotIn(codChecklist, checklist.getIcSituacao());
		} catch (DAOException e) {
			LogCEF.error(e);
		}
		
		setModoInicio();
		doFechaModal(null);
		return "";
	}

	public String doOpenDragClick() {
		setShowModal(true);
		setShowModalDragAndDrop(true);
		setShowModalOrdena(false);
		setShowModalAjuda(false);
		return "";
	}

	public String doOpenHelpClick() {
		setRotuloDragDrop("AJUDA");
		setShowModal(true);
		setShowModalDragAndDrop(false);
		setShowModalAjuda(true);
		setShowModalOrdena(false);
		return "";
	}

	/**
	 * abre o modal com a lista de blocos para ordenacao.
	 * 
	 * @param ev
	 */
	public void doOrdenarBloco(ActionEvent ev) {
		setRotuloDragDrop("ORDENAR BLOCOS");
		setShowModal(true);
		setShowModalDragAndDrop(false);
		setShowModalAjuda(false);
		setShowModalOrdena(true);
		fillListVinculados("", 0);
		vinculacoesChecklistVO.setNivelBloco();
		vinculacoesChecklistVO.setChecklistPrincipal(codChecklist);

	}

	public String doExcluirClick() {
		reRenderAll("PanelDragDrop");
		return "";
	}
	
	public String doReplication() {
		setShowModalAjuda(false);
		setShowModalOrdena(false);
		setShowModalDragAndDrop(false);
		setShowModalDragAndDrop(true);
		return null;
	}
	
	public void saveChanges(ActionEvent evt) {
		Integer id = null;
		
		if(vinculacoesChecklistVO.isActionInsert()){
			id = vinculacoesChecklistFacade.saveBlocoNoChecklist(vinculacoesChecklistVO, listChecklistsSelecionados);
		}else if(vinculacoesChecklistVO.isActionDrapDrop()){
			id = vinculacoesChecklistFacade.executarRotinaDragDropVinculacoes(vinculacoesChecklistVO, listChecklistsSelecionados);
		}else if(vinculacoesChecklistVO.isActionDelete()){
			id = vinculacoesChecklistFacade.executarRotinaDeleteVinculacoes(vinculacoesChecklistVO, listChecklistsSelecionados);
		} else if(vinculacoesChecklistVO.isActionUpdate()){ //do Ajuda
			id = vinculacoesChecklistFacade.executarRotinaUpdateVinculacoes(vinculacoesChecklistVO, listChecklistsSelecionados);
		}

		// Volta o checklist atual para o estado de projeto, caso este esteja como AUTORIZADO
		if(id != null && this.checklistBO.findById(id).getIcSituacao().trim().equals(ChecklistVO.CHKLST_SITUACAO_AUTORIZADO)) {
			this.checklistBO.retornarParaProjeto(id);
		}
		// Volta os checklists replicados para o estado de projeto, caso estes estejam como AUTORIZADO 
		for(ChecklistServicoVerificacaoProduto chk : listChecklistsSelecionados) {
			if(chk.getIcSituacao().trim().equals(ChecklistVO.CHKLST_SITUACAO_AUTORIZADO)){
				this.checklistBO.retornarParaProjeto(chk.getNuChecklistServicoVerificacaoProduto());
			}
		}
		
		setModoInicio();
		setChecklist(checklistBO.findById(id));
		setCodChecklist(id);
		vinculacoesChecklistVO = new VinculacoesChecklistVO();
		listChecklistsSelecionados.clear();

		info(MSGS, MN008);
		doFechaModal(null);
		getItensPanel();
	}
	
	/**
	 * @param rotuloDragDrop
	 *            the rotuloDragDrop to set
	 */
	public void setRotuloDragDrop(String rotuloDragDrop) {
		this.rotuloDragDrop = rotuloDragDrop;
	}

	/**
	 * @return the rotuloDragDrop
	 */
	public String getRotuloDragDrop() {
		return rotuloDragDrop;
	}
	
	/**
	 * AÇÃO DO BOTÃO AUTORIZAR
	 * @return
	 */
	public String doAutorizado() {
		if(checklistBO.validaChecklistParaAutorizacao(checklist)){
			SITUACAO = ChecklistVO.CHKLST_SITUACAO_AUTORIZADO;
			
			getHttpSession().setAttribute("checklistAltera", true);
			
			return doAlteraSituacaoClick();
		}else{
			warn("Para ser autorizado, um checklist deve ter pelo menos um bloco, cada bloco com pelo menos um item e cada item com pelo menos um apontamento.");
			return "";
		}
	}
	
	/**
	 * AÇÃO DO BOTÃO PUBLICAR
	 * @return
	 */
	public String doPublicado() {
		SITUACAO = ChecklistVO.CHKLST_SITUACAO_PUBLICADO;
		return doAlteraSituacaoClick();
	}

	/**
	 * AÇÃO DO BOTÃO REVOGAR
	 * @return
	 */
	public String doRevogado() {
		return doAlteraSituacaoClick();
	}
	
	
	public String doAlteraSituacaoClick() {
		try {
			checklist = checklistBO.findById(checklist.getNuChecklistServicoVerificacaoProduto());
			
			if(SITUACAO.equals(ChecklistVO.CHKLST_SITUACAO_AUTORIZADO)) {
				if(checklistBO.podeAutorizar(checklist)) {
					checklistBO.autoriza(checklist);
				} else {
					warn("msgsChecklist", "MSG_IMPOSSIVEL_AUTORIZAR");
				}
			} else if(SITUACAO.equals(ChecklistVO.CHKLST_SITUACAO_PUBLICADO)){
				if(checklistBO.podePublicar(checklist)) {
					checklistBO.publica(checklist);
				} else {
					warn("msgsChecklist", "MSG_IMPOSSIVEL_PUBLICAR");
				}
			} else {
				checklistBO.revogarChecklist(checklist.getNuChecklistServicoVerificacaoProduto());
			}
			info(MSGS, MN001);
			setShowAltera(false);
		} catch (Exception ex) {
			LogCEF.error(ex);
			error(MSGS, MN002);
		}
		SITUACAO = "";
		
		getHttpSession().setAttribute("checklistAltera", true);
		
		return "toChecklist";
	}
	
	private Date obtemDataSemHoras(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			return sdf.parse(sdf.format(data));
		} catch(ParseException ex) {
			LogCEF.error(ex);
		}
		
		return null;
	}	
	
	private boolean validaDados() {
		if(isProjeto()) {
			if(validaData(checklist) && checklistBO.podeAutorizar(checklist)) {
				return true;
			} else {
				return false;
			}
		} else if(isAutorizado() && !isRevogado()) {
			if(validaData(checklist) && checklistBO.podePublicar(checklist)) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}	
	
	private boolean validaData(ChecklistServicoVerificacaoProduto checklist) {
		if (!Utilities.empty(checklist.getDataInicio())) {
			Date dataAltera = obtemDataSemHoras(checklist.getDataInicio());
			Date dataSistema = obtemDataSemHoras(new Date());
			if (!checklistBO.validaDataInicio(checklist) && dataAltera.compareTo(dataSistema) < 0) {
				error(MSGS, MN002);
				warn("Campo DATA INÍCIO menor que a data atual do sistema.");
				return false;
			}
			if (!Utilities.empty(checklist.getDataFim())) {
				Date dataFim = obtemDataSemHoras(checklist.getDataFim());
				if (dataFim.compareTo(dataAltera) < 0) {
					error(MSGS, MN002);
					warn( "Campo DATA FINAL menor que a DATA INICIAL.");
					return false;
				}
				if(dataFim.compareTo(dataSistema) < 0) {
					error(MSGS, MN002);
					warn( "Campo DATA FINAL menor que a data do sistema.");
					return false;
				}
			}
		} else {
			error(MSGS, MN002);
			warn( "Campo DATA INÍCIO obrigatório.");
			return false;
		}
		return true;
	}
	
	public String doSalvarClick() {
		setModoInicio();
		if(!validaDados() || !validaData(checklist)) {
			return "";
		}
		try {
			checklist.setIcSituacao(ChecklistVO.CHKLST_SITUACAO_PROJETO);
			checklistBO.merge(checklist);
			info(MSGS, MN008);
			
			getHttpSession().setAttribute("checklistAltera", true);
			
			return "toChecklist";
		} catch (DAOException e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
			return "";
		}
	}

	/**
	 * Clique do botao 'Cancelar'/'Voltar'.
	 * @param evt
	 */
	public String doCancelarClick() {
		if (!isModoVisualiza()) {
			warn(MSGS, MN003);
		}
		setShowAltera(false);
		setModoInicio();
		
		getHttpSession().setAttribute("checklistAltera", true);
		
		return "toChecklist";
	}
	
	/**
	 * @return the itensPanel
	 */
	public HtmlPanel getItensPanel() {
  	    itensPanel.setStyle("width:99%");
		List<BlocoChecklistProduto> blocos = checklist.getBlocoChecklistProdutoList();

        final List<UIComponent> childrenPanel = itensPanel.getChildren();
        qtdBloco = 0;
        
        Collections.sort(blocos, new Comparator<BlocoChecklistProduto>() {
			public int compare(BlocoChecklistProduto obj1, BlocoChecklistProduto obj2) {
				return obj1.getNumeroDeOrdem().compareTo(obj2.getNumeroDeOrdem());
			}
		});
       
        itensPanel.getChildren().clear();

		for (BlocoChecklistProduto bloco : blocos) {
			qtdBloco = qtdBloco + 1;

			//faz a rotina de montagem do menu bloco
		    final HtmlSimpleTogglePanel linhaBloco = new HtmlSimpleTogglePanel();
		    linhaBloco.setOpened(false); 
		    linhaBloco.setSwitchType("client");  
		    linhaBloco.setIgnoreDupResponses(true);
		    linhaBloco.setStyle("width:99%");
		    final List<UIComponent> childrenBloco = linhaBloco.getChildren();
		    
	        childrenBloco.add(montaTags(String.valueOf(qtdBloco) + " - " + bloco.getBlocoChecklist().getNoBlocoChecklist(), bloco.getNuBlocoChecklistProduto().toString(), "bloco")); //adicionar as tags aqui dentro
		    linhaBloco.getFacets().put("header", childrenBloco.get(0)); //adiciono o facet acima inteiro no bloco criado
			childrenPanel.add(linhaBloco);	
		    
			List<ItemVerificacaoChecklistProduto> items =  bloco.getItemVerificacaoChecklistProdutoList();
	        qtdItem = 0;
	        
	        Collections.sort(items, new Comparator<ItemVerificacaoChecklistProduto>() {
				public int compare(ItemVerificacaoChecklistProduto obj1, ItemVerificacaoChecklistProduto obj2) {
					return obj1.getNumeroDeOrdem().compareTo(obj2.getNumeroDeOrdem());
				}
			});
	        
	        
			for (ItemVerificacaoChecklistProduto item : items) {
				qtdItem = qtdItem + 1;

				//faz a rotina de montagem do panel item
			    final HtmlSimpleTogglePanel linhaItem = new HtmlSimpleTogglePanel();
			    linhaItem.setOpened(false); 
			    linhaItem.setSwitchType("client");  
			    linhaItem.setIgnoreDupResponses(true);
			    linhaItem.setStyle("width:99%");

			    final List<UIComponent> childrenItem = linhaItem.getChildren();

		        childrenItem.add(montaTags(String.valueOf(qtdItem) + " - " + item.getItemVerificacao().getNoItemVerificacao(), item.getNuItemVerificacaoChecklistProduto().toString(), "item")); //adicionar as tags aqui dentro
			    linhaItem.getFacets().put("header", childrenItem.get(0)); //adiciono o facet acima inteiro no bloco criado
				childrenBloco.add(linhaItem);	
				
				List<ApontamentoChecklistProduto> aptos = item.getApontamentoChecklistProdutoList();
	        	qtdApto = 0;
	        	
	        	Collections.sort(aptos, new Comparator<ApontamentoChecklistProduto>() {
	    			public int compare(ApontamentoChecklistProduto obj1, ApontamentoChecklistProduto obj2) {
	    				return obj1.getNumeroDeOrdem().compareTo(obj2.getNumeroDeOrdem());
	    			}
	    		});
				for (ApontamentoChecklistProduto apto : aptos) {
					qtdApto = qtdApto + 1;
					//faz a rotina de montagem do menu apto
				    final HtmlSimpleTogglePanel linhaApto = new HtmlSimpleTogglePanel();
				    linhaApto.setOpened(false); 
				    linhaApto.setSwitchType("client");  
				    linhaApto.setIgnoreDupResponses(true);
				    linhaApto.setStyle("width:99%");

				    final List<UIComponent> childrenApto = linhaApto.getChildren();
			        
			        childrenApto.add(montaTags(String.valueOf(qtdApto) + " - " + apto.getApontamento().getNoApontamentoChecklist(), apto.getNuApontamentoChecklistProduto().toString(), "apontamento")); //adicionar as tags aqui dentro
				    linhaApto.getFacets().put("header", childrenApto.get(0)); //adiciono o facet acima inteiro no bloco criado
					childrenItem.add(linhaApto);	

				} //do for de Aptos
				
				childrenBloco.add(linhaItem);	
			} //do for de items

			childrenPanel.add(linhaBloco);	
		} //do for de blocos
		return itensPanel;
	}
	
	public HtmlPanelGroup montaTags(String nome, String id, String tipo) {
		FacesContext context = FacesContext.getCurrentInstance();
		MethodExpression actionListener = context.getApplication().getExpressionFactory()
		.createMethodExpression(context.getELContext(), "#{checklistAlteraMB.btnActionListener}", null, new Class[]{
			ActionEvent.class});
		
		HtmlPanelGroup panelGrupo = new HtmlPanelGroup();
		String titulo = "";
		
		if(tipo.equals("bloco")) {
			titulo = "Itens";
		} else if(tipo.equals("item")) {
			titulo = "Apontamentos";
		} else if(tipo.equals("apontamento")) {
			titulo = "Restrições";
		}
        
        //criando o icone do cabecalho
        HtmlGraphicImage icone = new HtmlGraphicImage(); 	
		icone.setTitle(tipo); 
		icone.setValue("/images/" + tipo + ".jpg");
		icone.setStyle("margin-right:10px; float:left;");
        
        //criar o texto do cabeçalho
        HtmlOutputText texto = new HtmlOutputText();
		if (nome != null && nome.length() > 104) {
			nome = nome.substring(0, 104) + "...";
		}
        texto.setValue(nome);
        texto.setStyleClass("rotuloArvore");
        
        HtmlPanelGroup panelGrupoBotao = new HtmlPanelGroup();
        panelGrupoBotao.setStyleClass("espacoCabecalho");
	    final List<UIComponent> childrenPanelGrupoBotao = panelGrupoBotao.getChildren();
	    
        //criando o icone do Alterar
	    HtmlCommandLink btnAlt = new HtmlCommandLink();
		btnAlt.setId("btnAlt".concat(tipo).concat(id));
		btnAlt.addActionListener(new MethodExpressionActionListener(actionListener));
		btnAlt.setStyle("border-style:none;border-width:0;margin-right:10px");
		
	    final List<UIComponent> childrenBtnAlt = btnAlt.getChildren();
		
        HtmlGraphicImage iconeAlt = new HtmlGraphicImage(); 	
        iconeAlt.setTitle("Vincular " + titulo);	
        iconeAlt.setValue("/images/icon_averificar.gif");
        childrenBtnAlt.add(iconeAlt);

        //criando o icone do Ajuda
		HtmlCommandLink btnHlp = new HtmlCommandLink();
		btnHlp.addActionListener(new MethodExpressionActionListener(actionListener));
		btnHlp.setId("btnHlp".concat(tipo).concat(id));
		btnHlp.setStyle("border-style:none;border-width:0;margin-right:10px");
	    final List<UIComponent> childrenBtnHlp = btnHlp.getChildren();
		
        HtmlGraphicImage iconeHlp = new HtmlGraphicImage(); 	
        iconeHlp.setTitle("Dados do " + tipo.substring(0,1).toUpperCase() + tipo.substring(1,tipo.length()));	
        iconeHlp.setValue("/images/icon_frequencia.gif");
        childrenBtnHlp.add(iconeHlp);
        
        //criando o icone do Excluir
		HtmlCommandLink btnExc = new HtmlCommandLink();
		btnExc.setId("btnExc".concat(tipo).concat(id));
		btnExc.setOnclick("if(confirm('" + getMessage("msgsCef", MA005) + "')) { jsReplicate('delete', '"+ id +"', '"+tipo+"'); return false; }");
		btnExc.setStyle("border-style:none;border-width:0;margin-right:10px");
	    final List<UIComponent> childrenBtnExc = btnExc.getChildren();
		
        HtmlGraphicImage iconeExc = new HtmlGraphicImage(); 	
        iconeExc.setTitle("Excluir " + tipo.substring(0,1).toUpperCase() + tipo.substring(1,tipo.length()));
        
		
		iconeExc.setValue("/images/icon_-delete.gif");
        childrenBtnExc.add(iconeExc);
        
        //adicionar as tags criadas acima
        panelGrupo.getChildren().add(icone);
        panelGrupo.getChildren().add(texto);

        btnAlt.setRendered(isShowButtonVincular());
        btnExc.setRendered(isShowButtonExcluir());
      
       	childrenPanelGrupoBotao.add(btnAlt);
       	childrenPanelGrupoBotao.add(btnExc);
        childrenPanelGrupoBotao.add(btnHlp);
        
        panelGrupo.getChildren().add(panelGrupoBotao);
        
		return panelGrupo;
	}

	public void btnActionListener(ActionEvent ev) {
		HtmlCommandLink btnAcao = (HtmlCommandLink) ev.getSource();
		nivel= "";
		id = null;
		if(btnAcao.getId().contains("bloco")) {
			nivel = btnAcao.getId().substring(6, "bloco".length()+6);
			id = Integer.parseInt(btnAcao.getId().substring("bloco".length()+6, btnAcao.getId().length()));
			vinculacoesChecklistVO.setNivelItem();
		} else if(btnAcao.getId().contains("item")) {
			nivel = btnAcao.getId().substring(6, "item".length()+6);
			id = Integer.parseInt(btnAcao.getId().substring("item".length()+6, btnAcao.getId().length()));
			vinculacoesChecklistVO.setNivelApontamento();
		} else {
			nivel = btnAcao.getId().substring(6, "apontamento".length()+6);
			id = Integer.parseInt(btnAcao.getId().substring("apontamento".length()+6, btnAcao.getId().length()));
			vinculacoesChecklistVO.setNivelRestricao();
		}
		
		vinculacoesChecklistVO.setChecklistPrincipal(codChecklist);
		vinculacoesChecklistVO.setPai(id);
		fillListNaoVinculados(nivel, id); 
		fillListVinculados(nivel, id);
		
		if(btnAcao.getId().contains("btnAlt")) {
			if(btnAcao.getId().contains("bloco")) {
				setRotuloDragDrop("VINCULAR ITENS AO BLOCO");
			} else if(btnAcao.getId().contains("item")) {
				setRotuloDragDrop("VINCULAR APONTAMENTOS AO ITEM");
			} else {
				setRotuloDragDrop("VINCULAR RESTRIÇÕES AO APONTAMENTO");
			}

			doOpenDragClick();
		} else if(btnAcao.getId().contains("btnHlp")) {
			if(btnAcao.getId().contains("bloco")) {
				BlocoChecklistProduto bloco = blocoChecklistProdutoBO.findById(id);
				checklistVO.setNomeAjuda(bloco.getDescricao());
				
				if (Utilities.empty(bloco.getIcObrigatorio())) {
					checklistVO.setSituacaoAjuda("0"); //0 - SELECIONE
				} else if (Utilities.empty(bloco.getIcDesabilita())) {
					checklistVO.setSituacaoAjuda("0"); //0 - SELECIONE
				} else if (bloco.getIcObrigatorio().equals(true)) {
					checklistVO.setSituacaoAjuda("1"); //1 - OBRIGATÓRIO
				} else if (bloco.getIcDesabilita().equals(true)) {
					checklistVO.setSituacaoAjuda("2"); //2 - DESABILITADO
				} else {
					checklistVO.setSituacaoAjuda("3"); //3 - NÃO SE APLICA
				}
				checklistVO.setPrejudicado(bloco.getIcPrejudicado());
				setAtivaRadioBoxAjuda(true);
				setAtivaCheckBoxAjuda(false);
			} else if(btnAcao.getId().contains("item")) {
				ItemVerificacaoChecklistProduto item = itemVerificacaoChecklistProdutoBO.findById(id);
				if(item.getIcDesabilitaItem() != null){
					checklistVO.setCheckNaoAplica(item.getIcDesabilitaItem());
				}else{
					checklistVO.setCheckNaoAplica(false);
				}
				checklistVO.setNomeAjuda(item.getDeItemVerificacaoChecklistProduto());
				checklistVO.setSituacaoAjuda("0"); //0 - SELECIONE
				setAtivaRadioBoxAjuda(false);
				setAtivaCheckBoxAjuda(true);
			} else {
				ApontamentoChecklistProduto apto = apontamentoChecklistProdutoBO.findById(id);
				checklistVO.setNomeAjuda(apto.getDescricao());

				if (Utilities.empty(apto.getIcNaoAplica())) {
					checklistVO.setCheckNaoAplica(false); //Vazio
				} else if (apto.getIcNaoAplica().equals(true)) {
					checklistVO.setCheckNaoAplica(true); //NÃO SE APLICA
				} else {
					checklistVO.setCheckNaoAplica(false); //VAZIO
				}
				setAtivaRadioBoxAjuda(false);
				setAtivaCheckBoxAjuda(true);
			}
			
			doOpenHelpClick();
		} else {
			doExcluirClick();
		}
	}
	
	/**
	 * @param itensPanel
	 *            the itensPanel to set
	 */
	public void setItensPanel(HtmlPanel itensPanel) {
		this.itensPanel = itensPanel;
	}

	/**
	 * getListSituacao - Lista de Situacoes - combo
	 * 
	 * @return
	 */
	public List<SelectItem> getListSituacao() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem("1", "OBRIGATÓRIO"));
		list.add(new SelectItem("2", "DESABILITADO"));
		list.add(new SelectItem("3", "NÃO SE APLICA"));
		return list;
	}

	/**
	 * @param showAltera
	 *            the showAltera to set
	 */
	public void setShowAltera(boolean showAltera) {
		this.showAltera = showAltera;
	}

	/**
	 * @return the showAltera
	 */
	public boolean isShowAltera() {
		return showAltera;
	}

	
	public void setShowModal(boolean showModal) {
		this.showModal = showModal;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isShowModal() {
		return this.showModal;
	}

	/**
	 * @param showModalAjuda
	 *            the showModalAjuda to set
	 */
	public void setShowModalAjuda(boolean showModalAjuda) {
		this.showModalAjuda = showModalAjuda;
	}

	/**
	 * @return the showModalAjuda
	 */
	public boolean isShowModalAjuda() {
		return showModalAjuda;
	}
	
	/**
	 * @return the showModalDragAndDrop
	 */
	public boolean isShowModalDragAndDrop() {
		return showModalDragAndDrop;
	}

	/**
	 * @param showModalDragAndDrop the showModalDragAndDrop to set
	 */
	public void setShowModalDragAndDrop(boolean showModalDragAndDrop) {
		this.showModalDragAndDrop = showModalDragAndDrop;
	}

	/**
	 * @return the listChecklists
	 */
	public List<ChecklistServicoVerificacaoProduto> getListChecklists() {
		return listChecklists;
	}

	/**
	 * @param listChecklists the listChecklists to set
	 */
	public void setListChecklists(List<ChecklistServicoVerificacaoProduto> listChecklists) {
		this.listChecklists = listChecklists;
	}
	
	
	public void changeCheckbox(ActionEvent evt) {
		ChecklistServicoVerificacaoProduto ch = (ChecklistServicoVerificacaoProduto)
				getExternalContext().getRequestMap().get("checklist");
		
		for(ChecklistServicoVerificacaoProduto check : listChecklistsSelecionados) {
			if(ch.getNuChecklistServicoVerificacaoProduto().equals(check.getNuChecklistServicoVerificacaoProduto())) {
				return;
			}
		}
		
		listChecklistsSelecionados.add(ch);	
	}

	/**
	 * @param showModalOrdena the showModalOrdena to set
	 */
	public void setShowModalOrdena(boolean showModalOrdena) {
		this.showModalOrdena = showModalOrdena;
	}

	/**
	 * @return the showModalOrdena
	 */
	public boolean isShowModalOrdena() {
		return showModalOrdena;
	}

	/**
	 * @return the showRevogado
	 */
	public boolean isShowRevogado() {
		return !isModoVisualiza() && (isAutorizado() || isPublicado()) && !isRevogado();
	}
	
	public boolean isShowSalvarDragDrop(){
		return !isRevogado() && !isModoVisualiza() && isShowModalDragAndDrop();
	}
	
	public boolean isShowSalvarOrdemBloco(){
		return !isRevogado() && !isModoVisualiza()  && isShowModalOrdena();
	}
	
	public boolean isShowPublicado() {
		return !isModoVisualiza() && isAutorizado() && !isRevogado();
	}
	
	private boolean isAutorizado() {
		return checklist.getIcSituacao().trim().equals(ChecklistVO.CHKLST_SITUACAO_AUTORIZADO);
	}

	public boolean isShowAutorizado() {
		return !isModoVisualiza() && isProjeto() && !isRevogado();
	}

	private boolean isProjeto() {
		return checklist.getIcSituacao().trim().equals(ChecklistVO.CHKLST_SITUACAO_PROJETO);
	}
	
	public void doFechaModal(ActionEvent evt){
		setShowModal(false);
	}
	
	public boolean isRevogado(){
		return checklist.getIcRevogado();
	}
	
	public boolean isValidInsertBloco(){
		return this.validInsertBloco;
	}
	
	public void setValidInsertBloco(boolean validInsertBloco){
		this.validInsertBloco = validInsertBloco;
	}
	
	/**
	 * @param ativaCheckBoxAjuda the ativaCheckBoxAjuda to set
	 */
	public void setAtivaCheckBoxAjuda(boolean ativaCheckBoxAjuda) {
		this.ativaCheckBoxAjuda = ativaCheckBoxAjuda;
	}

	/**
	 * @return the ativaCheckBoxAjuda
	 */
	public boolean isAtivaCheckBoxAjuda() {
		return ativaCheckBoxAjuda;
	}

	/**
	 * @param ativaRadioBoxAjuda the ativaRadioBoxAjuda to set
	 */
	public void setAtivaRadioBoxAjuda(boolean ativaRadioBoxAjuda) {
		this.ativaRadioBoxAjuda = ativaRadioBoxAjuda;
	}

	/**
	 * @return the ativaRadioBoxAjuda
	 */
	public boolean isAtivaRadioBoxAjuda() {
		return ativaRadioBoxAjuda;
	}
	
	/**
	 * Mensagem exibida quando o checklist
	 * está publicado.
	 * @return
	 */
	public String getMensagemPublicado() {
		if(checklist.getIcSituacao().trim().equals(ChecklistVO.CHKLST_SITUACAO_PUBLICADO)) 
			return "Checklist publicado e em uso.";
		return "";
	}
	
	public boolean isShowTitleAlterar(){
		return isModoInicio();
	}
	
	public boolean isShowTitleVisualizar(){
		return isModoVisualiza();
	}
	
	private boolean isPublicado() {
		return checklist.getIcSituacao().trim().equals(ChecklistVO.CHKLST_SITUACAO_PUBLICADO);
	}
	
	public boolean isShowOutputDataInicio() {
		if(isModoVisualiza()) {
			return true;
		}
		return isRevogado() || isPublicado();
	}

	public boolean isShowInputDataInicio() {
		if(isModoVisualiza()) {
			return false;
		}
		return !isRevogado() && !isPublicado();
	}
	
	public boolean isShowOutputDataFim() {
		if(isModoVisualiza()) {
			return true;
		}
		return isRevogado() || isPublicado();
	}
	
	public boolean isShowInputDataFim() {
		if(isModoVisualiza()) {
			return false;
		}
		return !isRevogado() && !isPublicado();
	}
	
	public boolean isShowButtonSalvar() {
		return !isModoVisualiza() && (isProjeto() || isAutorizado()) && !isRevogado();
	}
	
	public boolean isShowButtonSalvarAjuda() {
		return !isRevogado() && !isModoVisualiza();
	}
	
	public boolean isShowPanelAddBloco() {
		return !isRevogado() && !isModoVisualiza();
	}
	
	public boolean isShowButtonVincular() {
		if(isModoVisualiza()) {
			return false;
		}
		return !isRevogado() && !isPublicado();
	}
	
	public boolean isShowButtonExcluir() {
		if(isModoVisualiza()) {
			return false;
		}
		return !isRevogado() && !isPublicado();
	}
	
	public String getLabelCancelarVoltar(){
		if(isModoVisualiza()){
			return getMessage("msgsChecklist", "ACTION.voltar");
		}else{
			return getMessage("msgsChecklist", "ACTION.cancelar");
		}
	}
	
	public boolean isShowComboTipoCanal() {
		if(isModoVisualiza()) {
			return false;
		}
		return !isRevogado() && !isPublicado();
	}
	
	public boolean isShowOutputTipoCanal() {
		if(isModoVisualiza()) {
			return true;
		}
		return isRevogado() || isPublicado();
	}
	
	public Short getNuCanalComercializacao() {
		if(checklist.getCanalComercializacao() != null) {
			return checklist.getCanalComercializacao().getNuCanalCmrco();
		}
		
		return null;
	}
	
	public void setNuCanalComercializacao(Short nuCanal) {
		if(nuCanal != null) {
			CanalComercializacaoProduto canal = new CanalComercializacaoProduto();
			canal.setNuCanalCmrco(nuCanal);
			checklist.setCanalComercializacao(canal);
		} else {
			checklist.setCanalComercializacao(null);
		}
	}
	
	public void doMarcarPrejudicado(ActionEvent evt)
	{
		if (checklistVO.isPrejudicado())
		{
			checklistVO.setSituacaoAjuda("1");
		}
	}
	
	public String getNuOperacaoFormatada() {
		Short nuOperacao = checklist.getServicoVerificacaoProduto().getProduto().getNuOperacao();
		if(nuOperacao != null) {
			return ConvertUtils.padZerosLeft(nuOperacao.toString(), 4);
		}
		
		return "";
	}
	
	public String getNuModalidadeFormatada() {
		Short nuModalidade = checklist.getServicoVerificacaoProduto().getProduto().getNuModalidade();
		if(nuModalidade != null) {
			return ConvertUtils.padZerosLeft(nuModalidade.toString(), 3);
		}
		
		return "";
	}
}