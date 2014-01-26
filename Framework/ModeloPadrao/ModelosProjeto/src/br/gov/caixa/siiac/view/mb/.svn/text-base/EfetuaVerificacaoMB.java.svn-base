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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.MethodExpression;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.MethodExpressionActionListener;

import org.ajax4jsf.component.html.HtmlActionParameter;
import org.ajax4jsf.component.html.HtmlAjaxCommandLink;
import org.ajax4jsf.component.html.HtmlAjaxSupport;
import org.richfaces.component.html.HtmlCalendar;
import org.richfaces.component.html.HtmlPanel;
import org.richfaces.component.html.HtmlSimpleTogglePanel;
import org.richfaces.component.html.HtmlSpacer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IAtualizaPrazoVerificacaoBO;
import br.gov.caixa.siiac.bo.IEfetuaVerificacaoBO;
import br.gov.caixa.siiac.bo.IEnviaParecerBO;
import br.gov.caixa.siiac.bo.IGerenteUnidadeBO;
import br.gov.caixa.siiac.bo.IParecerBO;
import br.gov.caixa.siiac.bo.IPreferenciaUsuarioGerenteBO;
import br.gov.caixa.siiac.bo.IResumoParecerBO;
import br.gov.caixa.siiac.bo.ITemplateParecerBO;
import br.gov.caixa.siiac.bo.impl.EfetuaVerificacaoBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.exception.SIIACRuntimeException;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.GerenteUnidade;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;
import br.gov.caixa.siiac.model.domain.Parecer;
import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioGerente;
import br.gov.caixa.siiac.model.domain.ResultadoApontamentoProduto;
import br.gov.caixa.siiac.model.domain.TemplateParecer;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoObservacoes;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoParecer;
import br.gov.caixa.siiac.util.FormatUtil;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

@Service()
@Scope("session")
public class EfetuaVerificacaoMB extends AbstractMB {
	
	private static final String MSGS_EFETUA_VERIFICACAO = "msgsEfetuaVerificacao";
	private static final String MSG_ITENS_SEM_DATA_VALIDADE = "MSG_ITENS_SEM_DATA_VALIDADE";
	private static final String MSG_NAO_EXISTE_TEMPLATE_CONFORME = "MSG_NAO_EXISTE_TEMPLATE_CONFORME";
	private static final String MSG_NAO_EXISTE_TEMPLATE_INCONFORME = "MSG_NAO_EXISTE_TEMPLATE_INCONFORME";
	private static final String MSG_NAO_E_POSSIVEL_IMPORTAR_VERIFICACAO_PREJUDICADA = "MSG_NAO_E_POSSIVEL_IMPORTAR_VERIFICACAO_PREJUDICADA";
	public static final String APONTAMENTO = "3";

	public static final int REGISTRO_POR_PAGINA = 10;
	
	private Integer id;
	private String descricao;
	private String opcao = "0";
	private Contrato contrato;
	
	// BOs
	private IEfetuaVerificacaoBO efetuaVerificacaoBO;
	private ITemplateParecerBO templateParecerBO;
	private IEnviaParecerBO enviaParecerBO;
	private IAtualizaPrazoVerificacaoBO atualizaPrazoVerificacaoBO;
	private IResumoParecerBO resumoParecerBO;
	private IPreferenciaUsuarioGerenteBO preferenciaUsuarioGerenteBO;
	
	private HtmlPanel panelChecklist = new HtmlPanel();
	private VerificacaoContratoVO verificacaoContratoVO;
	private ChecklistServicoVerificacaoProduto arvoreChecklist = new ChecklistServicoVerificacaoProduto();
	private Map<String, VerificacaoContratoObservacoes> listVerificacaoContratoObservacoes;
	private Map<String, ResultadoApontamentoProduto> listResultadoApontamentoProdutos;
	private Map<Integer, ResultadoApontamentoProduto> listResultadoApontamentoProdutosBD;
	private VerificacaoContrato verificacaoContrato;
	private boolean isImportado;
	private boolean estadoItemInconforme;
	private boolean estadoItemNaoVerificado;
	private boolean estadoBlocoInconforme;
	private boolean estadoBlocoNaoVerificado;
	private boolean disabled;
	private int currentPage = 1;
	private int totalPages;
	
	public EfetuaVerificacaoMB() {
		// Limpa as listas de responstas da verificação que esta na memoria
		listResultadoApontamentoProdutos = new HashMap<String, ResultadoApontamentoProduto>();
		listVerificacaoContratoObservacoes = new HashMap<String, VerificacaoContratoObservacoes>();
		// recupera a verificação que foi colocada na sessão na tela de
		// escolhe_verificacao
		verificacaoContratoVO = (VerificacaoContratoVO) getHttpSession().getAttribute("verificacaoContratoVO");
		// recupera o contrato que foi colocado na sessão na tela de agenda
		contrato = (Contrato) getHttpSession().getAttribute("contrato");
		if (verificacaoContratoVO == null) {
			verificacaoContratoVO = new VerificacaoContratoVO();
		}
		if (contrato == null) {
			contrato = new Contrato();
		}
		
		
	}
	
	@Autowired
	public void setEfetuaVerificacaoBO(IEfetuaVerificacaoBO efetuaVerificacaoBO) {
		this.efetuaVerificacaoBO = efetuaVerificacaoBO;
		if (verificacaoContratoVO.getIcEstadoVerificacao().equals(VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_INCONFORME)) {
			verificacaoContrato = efetuaVerificacaoBO.clone(verificacaoContratoVO);
		} else {
			verificacaoContrato = efetuaVerificacaoBO.findVerificacaoContratoById(verificacaoContratoVO.getNuVerificacaoContrato());
			
			if(verificacaoContratoVO.getNuChecklistServicoVerificacaoProduto() == null) {
				ChecklistServicoVerificacaoProduto check = efetuaVerificacaoBO.checklistByNuServico(verificacaoContratoVO.getNuServicoVerificacaoProduto());
				
				if (Utilities.notEmpty(check))
				{
					verificacaoContratoVO.setNuChecklistServicoVerificacaoProduto(check.getNuChecklistServicoVerificacaoProduto());
				} else {
					error(MSGS, MN002);
					warn("Este serviço de verificação não possui checklist PUBLICADO para este produto.");
					return ;
				}
			}
			
		}
		atualizaList();
	}

	@Autowired
	public void setTemplateParecerBO(ITemplateParecerBO templateParecerBO) {
		this.templateParecerBO = templateParecerBO; 
	}
	
	@Autowired
	public void setEnviaParecerBO(IEnviaParecerBO enviaParecerBO) {
		this.enviaParecerBO = enviaParecerBO; 
	}
	
	@Autowired
	public void setAtualizaPrazoVerificacaoBO(IAtualizaPrazoVerificacaoBO atualizaPrazoVerificacaoBO) {
		this.atualizaPrazoVerificacaoBO = atualizaPrazoVerificacaoBO; 
	}
	
	@Autowired
	public void setResumoParecerBO(IResumoParecerBO resumoParecerBO) {
		this.resumoParecerBO = resumoParecerBO; 
	}
	
	@Autowired
	public void setPreferenciaUsuarioGerenteBO(
			IPreferenciaUsuarioGerenteBO preferenciaUsuarioGerenteBO) {
		this.preferenciaUsuarioGerenteBO = preferenciaUsuarioGerenteBO;
	}

	public String doVoltar() {
		return "toEscolheVerificacao";
	}
	
	// click no botao importar verificação anterior
	public void doImportar(ActionEvent evt) throws SIIACException {
		
		/*
		 * - Se tiver data vencida: 
		 * 		- MN028 – Não é possível importar
		 * 				  verificação anterior pois existem documentos vencidos. 
		 * 		- return
		 * 
		 * - Verificar se o checklist utilizado na verificação inconforme
		 * anterior é o mesmo checklist publicado na data atual. 
		 * 		- MN027 
		 * 		- return
		 */		
		
		
		
		//Verificando se o CHECKLIST é o mesmo
		Integer nuCheckList = efetuaVerificacaoBO.buscaChecklistVerificacaoAntiga(verificacaoContrato.getNuVerificacaoContratoPai());
		
		if (nuCheckList != null && nuCheckList > 0 && nuCheckList.equals(arvoreChecklist.getNuChecklistServicoVerificacaoProduto()))
		{
			if (efetuaVerificacaoBO.isVerificacaoParecerPrejudicada(verificacaoContrato.getNuVerificacaoContratoPai())) {
				error(MSGS_EFETUA_VERIFICACAO, MSG_NAO_E_POSSIVEL_IMPORTAR_VERIFICACAO_PREJUDICADA);
				return;
			} else if (efetuaVerificacaoBO.validaDataItemImportaVerificacao(verificacaoContratoVO.getNuVerificacaoContrato()) > 0) {
				error(MSGS, MN028);
				return;
			} else {
				isImportado = true;
				atualizaList();
				getPanelChecklist();				
			}
		} else {
			error(MSGS, MN027);
			return;
		}		
			
	}
	
	// click no botao salvar dos modais de Observação
	// adiciona a observação informada no modal na memoria
	public void doSaveObservacao(ActionEvent evt) {
		if (isBloco()) {
			VerificacaoContratoObservacoes verificacaoContratoObservacoes = listVerificacaoContratoObservacoes.get(idBloco());
			verificacaoContratoObservacoes.setDeObservacao(descricao);
			listVerificacaoContratoObservacoes.put(idBloco(), verificacaoContratoObservacoes);
		}
		if (isItem()) {
			VerificacaoContratoObservacoes verificacaoContratoObservacoes = listVerificacaoContratoObservacoes.get(idItem());
			verificacaoContratoObservacoes.setDeObservacao(descricao);
			listVerificacaoContratoObservacoes.put(idItem(), verificacaoContratoObservacoes);
		}
		if (isApontamento()) {
			ResultadoApontamentoProduto resultadoApontamentoProduto = listResultadoApontamentoProdutos.get(idApontamento());
			resultadoApontamentoProduto.setDeObservacao(descricao);
			listResultadoApontamentoProdutos.put(idApontamento(), resultadoApontamentoProduto);
		}
	}
	
	// click no icone abrir observação
	// recupera a ultima observação adicionada para aquele bloco
	public void doObservacaoBloco(ActionEvent evt) {
		id = Integer.parseInt(evt.getComponent().getId().replace("btnObsBloco", ""));
		descricao = "";
		if (listVerificacaoContratoObservacoes.get(idBloco()) != null && isShowValue()) {
			descricao = listVerificacaoContratoObservacoes.get(idBloco()).getDeObservacao();
		}
		modeBloco();
	}
	
	// click no icone abrir observação
	// recupera a ultima observação adicionada para aquele Item
	public void doObservacaoItem(ActionEvent evt) {
		id = Integer.parseInt(evt.getComponent().getId().replace("btnObsItem", ""));
		descricao = "";
		if (listVerificacaoContratoObservacoes.get(idItem()) != null && isShowValue()) {
			descricao = listVerificacaoContratoObservacoes.get(idItem()).getDeObservacao();
		}
		modeItem();
	}
	
	// click no icone abrir observação
	// recupera a ultima observação adicionada para aquele Apontamento
	public void doObservacaoApontamento(ActionEvent evt) {
		id = Integer.parseInt(evt.getComponent().getId().replace("btnObsApontamento", ""));
		descricao = "";
		if (listResultadoApontamentoProdutos.get(idApontamento()) != null && isShowValue()) {
			descricao = listResultadoApontamentoProdutos.get(idApontamento()).getDeObservacao();
		}
		modeApontamento();
	}
	
	// click no checkbox disabilitar (Bloco e Item)
	// adiciona o novo estado (disabilitado/habilitado) na memoria
	public void doCheckDisabled(ActionEvent evt) {
		String idEvt = getRequest().getParameter("idCheck");
		if (idEvt.contains("ckBloco")) {
			id = Integer.parseInt(idEvt.replace("ckBloco", ""));
			VerificacaoContratoObservacoes verificacaoContratoObservacoes = listVerificacaoContratoObservacoes.get(idBloco());
			if (verificacaoContratoObservacoes != null) {
				verificacaoContratoObservacoes.setIcDesabilitado(!isValidBoolean(verificacaoContratoObservacoes.getIcDesabilitado()));
				listVerificacaoContratoObservacoes.put(idBloco(), verificacaoContratoObservacoes);
			}
		}
		if (idEvt.contains("ckItemDisabled")) {
			id = Integer.parseInt(idEvt.replace("ckItemDisabled", ""));
			VerificacaoContratoObservacoes verificacaoContratoObservacoes = listVerificacaoContratoObservacoes.get(idItem());
			if (verificacaoContratoObservacoes != null) {
				verificacaoContratoObservacoes.setIcDesabilitado(!isValidBoolean(verificacaoContratoObservacoes.getIcDesabilitado()));
				listVerificacaoContratoObservacoes.put(idItem(), verificacaoContratoObservacoes);
			}
		}
	}
	
	// click no checkbox conforme (Item)
	// adiciona o novo estado (Conforme/Não Conforme) na memoria
	public void doCheckConforme(ActionEvent evt) {
		String idEvt = getRequest().getParameter("idCheck");
		id = Integer.parseInt(idEvt.replace("ckItemConform", ""));
		VerificacaoContratoObservacoes verificacaoContratoObservacoes = listVerificacaoContratoObservacoes.get(idItem());
		if (verificacaoContratoObservacoes != null) {
			verificacaoContratoObservacoes.setIcConforme(!isValidBoolean(verificacaoContratoObservacoes.getIcConforme()));
			listVerificacaoContratoObservacoes.put(idItem(), verificacaoContratoObservacoes);
			
			// A seguinte regra se dá para quando um item for marcado como conforme, todos seus apontamentos também são marcados como conforme.
			// Se o item tem sua opção de conforme desmarcado, todos seus apontamentos passam a ser "Não-Verificado";
			// ----- INICIO -----
			char icResultadoApontamentoFilho = '0';
			if(verificacaoContratoObservacoes.getIcConforme()){
				icResultadoApontamentoFilho = '1';
			}			
			Iterator<Map.Entry<String, ResultadoApontamentoProduto>> iterator = listResultadoApontamentoProdutos.entrySet().iterator();
			while(iterator.hasNext()){
				Map.Entry<String, ResultadoApontamentoProduto> entry = iterator.next();
				ResultadoApontamentoProduto resultadoApontamentoProduto = entry.getValue();
				if(resultadoApontamentoProduto.getApontamentoChecklistProduto().getItemVerificacaoChecklistProduto().getNuItemVerificacaoChecklistProduto()
						.equals(verificacaoContratoObservacoes.getItemVerificacaoChecklistProduto().getNuItemVerificacaoChecklistProduto())){
					id = Integer.parseInt(entry.getKey());
					resultadoApontamentoProduto.setIcResultadoApontamentoChecklist(icResultadoApontamentoFilho);
				}
			}
			// ----- FIM -----
			
		}
	}
	
	// Adicionando Data (Item)
	public void doAdicionaDataItem(ActionEvent evt) throws ParseException {
		String idEvt = getRequest().getParameter("idItem");
		String data = getRequest().getParameter("dataItem");
		
		id = Integer.parseInt(idEvt.replace("calItem", "").replace("InputDate", ""));
		VerificacaoContratoObservacoes verificacaoContratoObservacoes = listVerificacaoContratoObservacoes.get(idItem());
		if (verificacaoContratoObservacoes != null) {
			if(data == null || data.length()==0){
				verificacaoContratoObservacoes.setDtValidade(null);
			}else{
				verificacaoContratoObservacoes.setDtValidade(new SimpleDateFormat("dd/MM/yyyy").parse(data));
			}
			listVerificacaoContratoObservacoes.put(idItem(), verificacaoContratoObservacoes);
		}
	}
	
	// click no checkbox inconforme (Apontamento)
	// adiciona o novo estado (inconforme/nao inconforme) na memoria
	public void doCheckInconforme(ActionEvent evt) {
		String idEvt = getRequest().getParameter("idCheck");
		id = Integer.parseInt(idEvt.replace("ckApontamento", ""));
		ResultadoApontamentoProduto resultadoApontamentoProduto = listResultadoApontamentoProdutos.get(idApontamento());
		if (resultadoApontamentoProduto != null) {
			if (resultadoApontamentoProduto.getIcResultadoApontamentoChecklist() == ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_NAO_VERIFICADA || resultadoApontamentoProduto.getIcResultadoApontamentoChecklist() == ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_CONFORME) {
				resultadoApontamentoProduto.setIcResultadoApontamentoChecklist(ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_INCONFORME);
			} else {
				resultadoApontamentoProduto.setIcResultadoApontamentoChecklist(ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_NAO_VERIFICADA);
			}
			listResultadoApontamentoProdutos.put(idApontamento(), resultadoApontamentoProduto);
		}
	}
	
	// click do botão salvar (up and down)
	public String doSave() {
		return this.doSave(true);
	}
	
	/**
	 * Salva tudo que esta na memoria direto no banco de dados
	 * @param showMessage Valida se a mensagem de sucesso deve ou não ser apresentada.
	 * @return
	 */
	private String doSave(Boolean showMessage) {
		boolean sucess = true;
		// se for um verificação com parecer inconforme, copiar registro da
		// tabela 55 para 18
		if (verificacaoContratoVO.getIcEstadoVerificacao().equals(VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_INCONFORME)) {
			verificacaoContrato = efetuaVerificacaoBO.criaVerificacaoContratoFromVerificacaoContratoParecer(verificacaoContrato, verificacaoContratoVO);
			verificacaoContratoVO.setNuVerificacaoContrato(verificacaoContrato.getNuVerificacaoContrato());
			verificacaoContratoVO.setIcEstadoVerificacao(VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_NAO_VERIFICADA);

			atualizaPrazoVerificacaoBO.atualizaPrazoVerificao(this.verificacaoContrato.getContrato().getNuContrato());
		}
		

		
		// Salva as informações de cabeçalho - iacsm001.iactb018_verificacao_contrato (dt_verificacao e co_rspnl_verificacao)
		verificacaoContrato.setDtVerificacao(new Date());
		
		if(verificacaoContrato.getChecklist() == null) {
			ChecklistServicoVerificacaoProduto chkList = efetuaVerificacaoBO.checklistByNuServico(verificacaoContratoVO.getNuServicoVerificacaoProduto());
			if (Utilities.empty(chkList))
			{
				error(MSGS, MN002);
				warn("Este serviço de verificação não possui checklist PUBLICADO para este produto.");
				return "toEscolheVerificacao";
			}
			verificacaoContrato.setChecklist(chkList);
		}
		
		verificacaoContrato.setCoRspnlVerificacao(SegurancaUsuario.getInstance().getUsuario().getMatricula());
		this.efetuaVerificacaoBO.saveVerificacaoContratoCabecalho(verificacaoContrato);
		
		// Iterar as responstas de todos os blocos e itens
		for (VerificacaoContratoObservacoes verificacaoContratoObservacoes : listVerificacaoContratoObservacoes.values()) {
			verificacaoContratoObservacoes.setVerificacaoContrato(verificacaoContrato);
			// verifica se é referente a bloco
			if (verificacaoContratoObservacoes.getBlocoChecklistProduto() != null) {
				// Caso o bloco possua uma observação ou esteja Desabilitado,
				// grava essa informação na tabela de
				// 'Observações' da Verificação sendo editada
				if (Utilities.notEmpty(verificacaoContratoObservacoes.getDeObservacao()) || isValidBoolean(verificacaoContratoObservacoes.getIcDesabilitado())) {
					try {
						efetuaVerificacaoBO.saveBlocoItem(verificacaoContratoObservacoes);
					} catch (SIIACException e) {
						sucess = false;
						error(MSGS, MN002);
						error(MSGS, "Erro ao salvar verificação do bloco: " + verificacaoContratoObservacoes.getBlocoChecklistProduto().getNumeroDeOrdem());
						LogCEF.error("Erro ao salvar verificação do bloco: " + e.getMessage());
					}
				} else {
					// Verifica se já existe registro na base sem observação e
					// não desabilitado para ser excluido
					if (verificacaoContratoObservacoes.getNuVerificacaoContratoObservacao() != null) {
						try {
							efetuaVerificacaoBO.deleteBlocoItem(verificacaoContratoObservacoes);
						} catch (SIIACException e) {
							sucess = false;
							error(MSGS, MN002);
							error(MSGS, "Erro ao excluir verificação do bloco: " + verificacaoContratoObservacoes.getBlocoChecklistProduto().getNumeroDeOrdem());
							LogCEF.error("Erro ao excluir verificação do bloco: " + e.getMessage());
						}
					}
				}
			}
			// verifica se é referente a item
			if (verificacaoContratoObservacoes.getItemVerificacaoChecklistProduto() != null) {
				// Caso o item possua uma observação ou esteja Desabilitado ou
				// acao_conforme_item estiver marcada, grava
				// essa informação na tabela de
				// 'Observações' da Verificação sendo editada
				if (Utilities.notEmpty(verificacaoContratoObservacoes.getDeObservacao()) || isValidBoolean(verificacaoContratoObservacoes.getIcDesabilitado()) || isValidBoolean(verificacaoContratoObservacoes.getIcConforme()) || Utilities.notEmpty(verificacaoContratoObservacoes.getDtValidade())) {
					try {
						efetuaVerificacaoBO.saveBlocoItem(verificacaoContratoObservacoes);
					} catch (SIIACException e) {
						sucess = false;
						error(MSGS, MN002);
						error(MSGS, "Erro ao salvar verificação do item: " + verificacaoContratoObservacoes.getItemVerificacaoChecklistProduto().getNumeroDeOrdem());
						LogCEF.error("Erro ao salvar verificação do item: " + e.getMessage());
					}
					
				} else {
					// Verifica se já existe registro na base sem observação e
					// não desabilitado para ser excluido
					if (verificacaoContratoObservacoes.getNuVerificacaoContratoObservacao() != null) {
						try {
							efetuaVerificacaoBO.deleteBlocoItem(verificacaoContratoObservacoes);
						} catch (SIIACException e) {
							sucess = false;
							error(MSGS, MN002);
							error(MSGS, "Erro ao excluir verificação do item: " + verificacaoContratoObservacoes.getBlocoChecklistProduto().getNumeroDeOrdem());
							LogCEF.error("Erro ao excluir verificação do item: " + e.getMessage());
						}
					}
				}
				boolean existeInconforme = false;
				// percorre a lista de resultado dos apontamentos
				for (ResultadoApontamentoProduto resultadoApontamentoProduto : listResultadoApontamentoProdutos.values()) {
					// verifica se o resultado de apontamento pertence ao item
					if (resultadoApontamentoProduto.getApontamentoChecklistProduto().getItemVerificacaoChecklistProduto().getNuItemVerificacaoChecklistProduto().equals(verificacaoContratoObservacoes.getItemVerificacaoChecklistProduto().getNuItemVerificacaoChecklistProduto())) {
						// Caso o Item esteja marcado como 'Conforme', atribui o
						// valor 'Conforme' para todos os seus
						// Apontamentos
						if (isValidBoolean(verificacaoContratoObservacoes.getIcConforme())) {
							resultadoApontamentoProduto.setIcResultadoApontamentoChecklist(ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_CONFORME);
						} else {
							// Verifica se existe algum apontamento inconforme
							if (resultadoApontamentoProduto.getIcResultadoApontamentoChecklist() == ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_INCONFORME) {
								existeInconforme = true;
							}
						}
					}
				}
				// se existir algum apontamento inconforme
				if (existeInconforme) {
					// percorre a lista de resultado dos apontamentos
					for (ResultadoApontamentoProduto resultadoApontamentoProduto : listResultadoApontamentoProdutos.values()) {
						// verifica se o resultado de apontamento pertence ao
						// item e se ele é não verificado
						if (resultadoApontamentoProduto.getApontamentoChecklistProduto().getItemVerificacaoChecklistProduto().getNuItemVerificacaoChecklistProduto().equals(verificacaoContratoObservacoes.getItemVerificacaoChecklistProduto().getNuItemVerificacaoChecklistProduto()) && resultadoApontamentoProduto.getIcResultadoApontamentoChecklist() == ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_NAO_VERIFICADA) {
							resultadoApontamentoProduto.setIcResultadoApontamentoChecklist(ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_CONFORME);
						}
					}
				} else {
					if (!isValidBoolean(verificacaoContratoObservacoes.getIcConforme())) {
						// percorre a lista de resultado dos apontamentos
						for (ResultadoApontamentoProduto resultadoApontamentoProduto : listResultadoApontamentoProdutos.values()) {
							// verifica se o resultado de apontamento pertence
							// ao item
							if (resultadoApontamentoProduto.getApontamentoChecklistProduto().getItemVerificacaoChecklistProduto().getNuItemVerificacaoChecklistProduto().equals(verificacaoContratoObservacoes.getItemVerificacaoChecklistProduto().getNuItemVerificacaoChecklistProduto())) {
								resultadoApontamentoProduto.setIcResultadoApontamentoChecklist(ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_NAO_VERIFICADA);
							}
						}
					}
				}
			}
		}
		// Itera as respostas de todos os apontamentos
		for (ResultadoApontamentoProduto resultadoApontamentoProduto : listResultadoApontamentoProdutos.values()) {
			resultadoApontamentoProduto.setVerificacaoContrato(verificacaoContrato);
			try {
				efetuaVerificacaoBO.saveApontamento(resultadoApontamentoProduto);
			} catch (SIIACException e) {
				sucess = false;
				error(MSGS, MN002);
				error(MSGS, "Erro ao salvar verificação do apontamento: " + resultadoApontamentoProduto.getApontamentoChecklistProduto().getNumeroDeOrdem());
				LogCEF.error("Erro ao salvar verificação do apontamento: " + e.getMessage());
			}
		}
		
		if (sucess && showMessage) {
			info(MSGS, MN001);
		}
		// atualizar as informações da tela
		// atualizaList();
		// getPanelChecklist();
		
		//Atualiza o estado do campo "Situação" no cabeçalho.
		atualizaSituacaoCabecalho();
		
		return "toEscolheVerificacao";
	}
	
	// busca a arvore de checklist no banco de dados para montar os blocos, item
	// e apontamento da verificação
	private void atualizaList() {
		arvoreChecklist = efetuaVerificacaoBO.getMontaArvoreChecklist(verificacaoContratoVO);
		listResultadoApontamentoProdutos = new HashMap<String, ResultadoApontamentoProduto>();
		listVerificacaoContratoObservacoes = new HashMap<String, VerificacaoContratoObservacoes>();
	}
	
	public void setPanelChecklist(HtmlPanel panelChecklist) {
		this.panelChecklist = panelChecklist;
	}
	
	/**
	 * Este metodo itera o checklist e chama os metodos que criarão as linhas
	 * dos blocos, itens e apontamentos.
	 * 
	 * @return
	 */
	public HtmlPanel getPanelChecklist() {
		elementsSrc.clear();
		int ini = (currentPage * REGISTRO_POR_PAGINA) - REGISTRO_POR_PAGINA, fim = ini + REGISTRO_POR_PAGINA, i = 0;
		panelChecklist.setStyle("width:99%");
		if (arvoreChecklist == null) {
			return panelChecklist;
		}
		listResultadoApontamentoProdutosBD = efetuaVerificacaoBO.getResultadoApontamentoProduto(verificacaoContrato, verificacaoContratoVO);
		final List<UIComponent> childrenPanel = panelChecklist.getChildren();
		panelChecklist.getChildren().clear();
		//ordenação por numero de ordem
		Collections.sort(arvoreChecklist.getBlocoChecklistProdutoList(), new Comparator<BlocoChecklistProduto>() {
			public int compare(BlocoChecklistProduto obj1, BlocoChecklistProduto obj2) {
				return obj1.getNumeroDeOrdem().compareTo(obj2.getNumeroDeOrdem());
			}
		});
		//ordenação por icPrejudicado (os blocos prejudicados devem aparecer primeiro)
		Collections.sort(arvoreChecklist.getBlocoChecklistProdutoList(), new Comparator<BlocoChecklistProduto>() {
			public int compare(BlocoChecklistProduto obj1, BlocoChecklistProduto obj2) {
				if(obj1.getIcPrejudicado()==obj2.getIcPrejudicado()){
					return 0;
				}else if(obj1.getIcPrejudicado()){
					return -1;
				}else{
					return 1;
				}
			}
		});
		boolean prejudicado = false;
		for (BlocoChecklistProduto b : arvoreChecklist.getBlocoChecklistProdutoList()) {
			if (i >= ini && i < fim) {
				Integer blocoPrejudicado = getBlocoPrejudicado();
				boolean isPrejudicado = false;
				if(blocoPrejudicado != null && ! blocoPrejudicado.equals(b.getNuBlocoChecklistProduto())){
					isPrejudicado = true;
				}
				List<UIComponent> childrenPanelBloco = panelBlocos(childrenPanel, b, isPrejudicado);
				Collections.sort(b.getItemVerificacaoChecklistProdutoList(), new Comparator<ItemVerificacaoChecklistProduto>() {
					public int compare(ItemVerificacaoChecklistProduto obj1, ItemVerificacaoChecklistProduto obj2) {
						return obj1.getNumeroDeOrdem().compareTo(obj2.getNumeroDeOrdem());
					}
				});
				estadoBlocoInconforme = false;
				estadoBlocoNaoVerificado = false;
				for (ItemVerificacaoChecklistProduto iv : b.getItemVerificacaoChecklistProdutoList()) {
					disabled = false;
					List<UIComponent> childrenPanelItem = panelItens(childrenPanelBloco, iv);
					Collections.sort(iv.getApontamentoChecklistProdutoList(), new Comparator<ApontamentoChecklistProduto>() {
						public int compare(ApontamentoChecklistProduto obj1, ApontamentoChecklistProduto obj2) {
							return obj1.getNumeroDeOrdem().compareTo(obj2.getNumeroDeOrdem());
						}
					});
					estadoItemInconforme = false;
					estadoItemNaoVerificado = false;
					for (ApontamentoChecklistProduto ap : iv.getApontamentoChecklistProdutoList()) {
						panelApontamentos(childrenPanelItem, ap);
					}
					if (childrenPanelItem.size() != 0) {
						// verifica o estado dos apontamentos(Conforme,
						// Inconforme, não verificado) para alterar o icone do
						// Item
						childrenPanelItem.get(childrenPanelItem.size() - 1).getParent().getFacets().get("header").getChildren().add(getPanelLeftItem(iv));
						HtmlPanelGroup divClear = new HtmlPanelGroup();
						divClear.setLayout("block");
						divClear.setStyle("clear: both;");
						
						childrenPanelItem.get(childrenPanelItem.size() - 1).getParent().getFacets().get("header").getChildren().add(divClear);
					}
				}
				if (childrenPanelBloco.size() != 0) {
					// verifica o estado dos Itens(Conforme, Inconforme, não
					// verificado) para alterar o icone do Bloco
					childrenPanelBloco.get(childrenPanelBloco.size() - 1).getParent().getFacets().get("header").getChildren().add(getPanelLeftBloco(b, isPrejudicado));
					HtmlPanelGroup divClear = new HtmlPanelGroup();
					divClear.setLayout("block");
					divClear.setStyle("clear: both;");
					
					childrenPanelBloco.get(childrenPanelBloco.size() - 1).getParent().getFacets().get("header").getChildren().add(divClear);
				}
			}
			i++;
		}
		if (arvoreChecklist.getBlocoChecklistProdutoList().size() > REGISTRO_POR_PAGINA) {
			montaPagination(arvoreChecklist.getBlocoChecklistProdutoList().size());
		}
		return panelChecklist;
	}
	
	private Integer getBlocoPrejudicado(){
		return this.efetuaVerificacaoBO.buscaPrimeiroBlocoPrejudicado(verificacaoContratoVO.getNuVerificacaoContrato());
	}
	/**
	 * @param size
	 * @param i
	 */
	private void montaPagination(int size) {
		if (size == 0) {
			return;
		}
		HtmlPanelGroup pnlPagination = new HtmlPanelGroup();
		pnlPagination.setLayout("block");
		pnlPagination.setId("pnlPagination");
		pnlPagination.setStyle("text-align:center;");
		totalPages = size / REGISTRO_POR_PAGINA;
		if (size % REGISTRO_POR_PAGINA > 0) {
			totalPages++;
		}
		// VOLTAR INICIO PAGINA
		HtmlAjaxCommandLink linkInicio = new HtmlAjaxCommandLink();
		linkInicio.setId("linkInicio");
		if (currentPage > 1) {
			linkInicio.setReRender("pnlEfetuaVerificacao");
			linkInicio.setStatus("sts");
			linkInicio.addActionListener(actionListener("#{efetuaVerificacaoMB.doPaginar}"));
			linkInicio.setOncomplete("atualizaTooltipEDesabilitados()");
		}
		linkInicio.setStyle("vertical-align: bottom;*vertical-align: middle;*display: inline-block !important");
		HtmlGraphicImage imgInicio = new HtmlGraphicImage();
		imgInicio.setValue("/SIIAC/images/tabela/seta_primeiro.gif");
		linkInicio.getChildren().add(imgInicio);
		pnlPagination.getChildren().add(linkInicio);
		
		// VOLTAR PAGINA
		HtmlAjaxCommandLink linkVoltar = new HtmlAjaxCommandLink();
		linkVoltar.setId("linkVoltar");
		if (currentPage > 1) {
			linkVoltar.setReRender("pnlEfetuaVerificacao");
			linkVoltar.setStatus("sts");
			linkVoltar.addActionListener(actionListener("#{efetuaVerificacaoMB.doPaginar}"));
			linkVoltar.setOncomplete("atualizaTooltipEDesabilitados()");
		}
		linkVoltar.setStyle("vertical-align: bottom;*vertical-align: middle;*display: inline-block !important");
		HtmlGraphicImage imgVoltar = new HtmlGraphicImage();
		imgVoltar.setValue("/SIIAC/images/tabela/seta_anterior.gif");
		linkVoltar.getChildren().add(imgVoltar);
		pnlPagination.getChildren().add(linkVoltar);
		// IR PARA PAGINA
		for (int i = 1; i <= totalPages; i++) {
			HtmlAjaxCommandLink linkPage = new HtmlAjaxCommandLink();
			linkPage.setValue(i);
			linkPage.setId("linkPage" + i);
			if (currentPage == i) {
				linkPage.setStyle("text-decoration: underline;");
				linkPage.setStyleClass("dr-dscr-act rich-datascr-act classe-datascroller classe-datascroller-selected");
			} else {
				linkPage.addActionListener(actionListener("#{efetuaVerificacaoMB.doPaginar}"));
				linkPage.setReRender("pnlEfetuaVerificacao");
				linkPage.setStatus("sts");
				linkPage.setStyle("text-decoration: none;");
				linkPage.setStyleClass("dr-dscr-inact rich-datascr-inact classe-datascroller classe-datascroller-unselected");
				linkPage.setOncomplete("atualizaTooltipEDesabilitados()");
			}
			pnlPagination.getChildren().add(linkPage);
		}
		// PROXIMA PAGINA
		HtmlAjaxCommandLink linkProximo = new HtmlAjaxCommandLink();
		linkProximo.setId("linkProximo");
		if (currentPage < totalPages) {
			linkProximo.addActionListener(actionListener("#{efetuaVerificacaoMB.doPaginar}"));
			linkProximo.setReRender("pnlEfetuaVerificacao");
			linkProximo.setStatus("sts");
			linkProximo.setOncomplete("atualizaTooltipEDesabilitados()");
		}
		linkProximo.setStyle("vertical-align: bottom;*vertical-align: middle;*display: inline-block !important");
		HtmlGraphicImage imgProximo = new HtmlGraphicImage();
		imgProximo.setValue("/SIIAC/images/tabela/seta_proximo.gif");
		linkProximo.getChildren().add(imgProximo);
		pnlPagination.getChildren().add(linkProximo);
		// ULTIMA PAGINA
		HtmlAjaxCommandLink linkUltima = new HtmlAjaxCommandLink();
		linkUltima.setId("linkUltima");
		if (currentPage < totalPages) {
			linkUltima.addActionListener(actionListener("#{efetuaVerificacaoMB.doPaginar}"));
			linkUltima.setReRender("pnlEfetuaVerificacao");
			linkUltima.setStatus("sts");
			linkUltima.setOncomplete("atualizaTooltipEDesabilitados()");
		}
		linkUltima.setStyle("vertical-align: bottom;*vertical-align: middle;*display: inline-block !important");
		HtmlGraphicImage imgUltima = new HtmlGraphicImage();
		imgUltima.setValue("/SIIAC/images/tabela/seta_ultimo.gif");
		linkUltima.getChildren().add(imgUltima);
		pnlPagination.getChildren().add(linkUltima);
		
		panelChecklist.getChildren().add(pnlPagination);
	}
	
	public void doPaginar(ActionEvent evt) {
		if (evt.getComponent().getId().equals("linkInicio")) {
			currentPage = 1;
			getPanelChecklist();
			return;
		}
		if (evt.getComponent().getId().equals("linkVoltar")) {
			currentPage--;
			getPanelChecklist();
			return;
		}
		if (evt.getComponent().getId().contains("linkPage")) {
			int page = Integer.parseInt(evt.getComponent().getId().replace("linkPage", ""));
			currentPage = page;
			getPanelChecklist();
			return;
		}
		if (evt.getComponent().getId().equals("linkProximo")) {
			currentPage++;
			getPanelChecklist();
			return;
		}
		if (evt.getComponent().getId().equals("linkUltima")) {
			currentPage = totalPages;
			getPanelChecklist();
			return;
		}
	}
	
	/**
	 * Cria linha de blocos
	 * 
	 * @param childrenPanel
	 * @param b
	 *            .getBlocoChecklist()
	 * @param blocoPrejudicado 
	 * @return
	 */
	private List<UIComponent> panelBlocos(List<UIComponent> childrenPanel, BlocoChecklistProduto b, boolean isPrejudicado) {
		// verifica se existe informação já gravadas no banco e add no map
		VerificacaoContratoObservacoes verificacaoContratoObservacoes = null;
		if (!listVerificacaoContratoObservacoes.containsKey(EfetuaVerificacaoBO.BLOCO + b.getNuBlocoChecklistProduto())) {
			if (isShowValue()) {
				verificacaoContratoObservacoes = efetuaVerificacaoBO.getVerificacaoContratoObservacoes(b.getNuBlocoChecklistProduto(), EfetuaVerificacaoBO.BLOCO, verificacaoContratoVO);
			}
			if (verificacaoContratoObservacoes == null) {
				verificacaoContratoObservacoes = new VerificacaoContratoObservacoes();
				verificacaoContratoObservacoes.setBlocoChecklistProduto(b);
				verificacaoContratoObservacoes.setIcFonte(VerificacaoContratoObservacoes.FONTE_OBSERVACAO_APONTAMENTO_ID_BLOCO);
				verificacaoContratoObservacoes.setVerificacaoContrato(verificacaoContrato);
				verificacaoContratoObservacoes.setIcConforme(false);
				verificacaoContratoObservacoes.setIcDesabilitado(false);
			}
			listVerificacaoContratoObservacoes.put(EfetuaVerificacaoBO.BLOCO + b.getNuBlocoChecklistProduto(), verificacaoContratoObservacoes);
		} else {
			verificacaoContratoObservacoes = listVerificacaoContratoObservacoes.get(EfetuaVerificacaoBO.BLOCO + b.getNuBlocoChecklistProduto());
		}
		// Cria o componente SimpleTogglePanel para um bloco
		final HtmlSimpleTogglePanel toggleBloco = new HtmlSimpleTogglePanel();
		toggleBloco.setId("toggleBloco".concat(String.valueOf(b.getNuBlocoChecklistProduto())));
		toggleBloco.setOpened(false);
		toggleBloco.setSwitchType("client");
		toggleBloco.setIgnoreDupResponses(true);
		toggleBloco.setStyle("width:99%; background-color: #CED5E5 !important; border-bottom: 10px solid #fff; padding: 5px 0 1px;");
		StringBuffer arrayItem = new StringBuffer();
		arrayItem.append("new Array(");
		for (ItemVerificacaoChecklistProduto i : b.getItemVerificacaoChecklistProdutoList()) {
			arrayItem.append(i.getNuItemVerificacaoChecklistProduto() + ",");
		}
		arrayItem.append(0);
		arrayItem.append(")");
		String prejudicado = "";
		if(isPrejudicado){
			toggleBloco.setStyleClass("desabilitadoPorPrejudicada");
//			toggleBloco.setOnclick("escondePrejudicada('formEfetuaVerificacao:" + toggleBloco.getId() + "');");
			prejudicado = "return false   ;";
		}
		toggleBloco.setOnexpand("openAllChildren(event, " + arrayItem + ")");
		
		
		// Cria um painel para inserir os componentes alinhados a esquerda
		// (icone, nome bloco e icone ajuda)
		HtmlPanelGroup panelGrupLeft = new HtmlPanelGroup();
		
		HtmlPanelGroup panelGrupRight = new HtmlPanelGroup();
		panelGrupRight.setLayout("block");
		panelGrupRight.setStyleClass("espacoCabecalho");
		
		HtmlSelectBooleanCheckbox check = new HtmlSelectBooleanCheckbox();
		check.setId("ckBloco".concat(String.valueOf(b.getNuBlocoChecklistProduto())));
		check.setOnclick("esconde('formEfetuaVerificacao:" + check.getId() + "', 'formEfetuaVerificacao:" + toggleBloco.getId() + "');");
		check.setStyleClass("checkDesabilita");
		check.setRendered(isValidBoolean(b.getIcDesabilita()));
		if (isShowValue()) {
			check.setValue(isValidBoolean(verificacaoContratoObservacoes.getIcDesabilitado()));
		}
		if(isPrejudicado){
			check.setDisabled(true);
		}
		
		HtmlAjaxSupport ajax = new HtmlAjaxSupport();
		ajax.setEvent("onchange");
		ajax.addActionListener(actionListener("#{efetuaVerificacaoMB.doCheckDisabled}"));
		
		HtmlActionParameter param = new HtmlActionParameter();
		param.setValue(check.getId());
		param.setName("idCheck");
		ajax.getChildren().add(param);
		
		check.getChildren().add(ajax);
		
		panelGrupRight.getChildren().add(check);
		
		HtmlOutputLabel textDesabilita = new HtmlOutputLabel();
		textDesabilita.setValue(getMessage(MSGS_EFETUA_VERIFICACAO, "Desabilita"));
		textDesabilita.setStyle("vertical-align: 2px; margin: 0 20px 0 5px;font-weight: normal;");
		textDesabilita.setRendered(isValidBoolean(b.getIcDesabilita()));
		
		panelGrupRight.getChildren().add(textDesabilita);
		
		HtmlAjaxCommandLink btnObs = new HtmlAjaxCommandLink();
		btnObs.setId("btnObsBloco".concat(String.valueOf(b.getNuBlocoChecklistProduto())));
		btnObs.addActionListener(actionListener("#{efetuaVerificacaoMB.doObservacaoBloco}"));
		btnObs.setReRender("modalObservacao");
		if (isValidBoolean(b.getIcDesabilita())) {
			btnObs.setOnclick(prejudicado + "if(!document.getElementById('formEfetuaVerificacao:" + check.getId() + "').checked){SimpleTogglePanelManager.toggleOnClient(event,'formEfetuaVerificacao:" + toggleBloco.getId() + "');}");
		} else {
			btnObs.setOnclick(prejudicado + "SimpleTogglePanelManager.toggleOnClient(event,'formEfetuaVerificacao:" + toggleBloco.getId() + "');");
		}
		btnObs.setStyle("border-style:none;border-width:0;margin-right:10px");
		btnObs.setOncomplete("Richfaces.showModalPanel('modalObservacao')");
		
		HtmlGraphicImage iconObs = new HtmlGraphicImage();
		iconObs.setTitle(getMessage(MSGS_EFETUA_VERIFICACAO, "Observacao"));
		iconObs.setValue("/images/verificacao/icone_observacao1.gif");
		
		btnObs.getChildren().add(iconObs);
		panelGrupRight.getChildren().add(btnObs);
		
		panelGrupLeft.getChildren().add(panelGrupRight);
		
		toggleBloco.getChildren().add(panelGrupLeft);
		toggleBloco.getFacets().put("header", toggleBloco.getChildren().get(0));
		childrenPanel.add(toggleBloco);
		
		return toggleBloco.getChildren();
	}
	
	/**
	 * Cria linha de Itens
	 * 
	 * @param childrenPanel
	 * @param bloco
	 * @return
	 */
	private List<UIComponent> panelItens(List<UIComponent> childrenPanel, ItemVerificacaoChecklistProduto iv) {
		// verifica se existe informação já gravadas no banco e add no map
		VerificacaoContratoObservacoes verificacaoContratoObservacoes = null;
		if (!listVerificacaoContratoObservacoes.containsKey(EfetuaVerificacaoBO.ITEM + iv.getNuItemVerificacaoChecklistProduto())) {
			if (isShowValue()) {
				verificacaoContratoObservacoes = efetuaVerificacaoBO.getVerificacaoContratoObservacoes(iv.getNuItemVerificacaoChecklistProduto(), EfetuaVerificacaoBO.ITEM, verificacaoContratoVO);
			}
			if (verificacaoContratoObservacoes == null) {
				verificacaoContratoObservacoes = new VerificacaoContratoObservacoes();
				verificacaoContratoObservacoes.setItemVerificacaoChecklistProduto(iv);
				verificacaoContratoObservacoes.setIcFonte(VerificacaoContratoObservacoes.FONTE_OBSERVACAO_APONTAMENTO_ID_ITEM);
				verificacaoContratoObservacoes.setVerificacaoContrato(verificacaoContrato);
				verificacaoContratoObservacoes.setIcConforme(false);
			}
			listVerificacaoContratoObservacoes.put(EfetuaVerificacaoBO.ITEM + iv.getNuItemVerificacaoChecklistProduto(), verificacaoContratoObservacoes);
		} else {
			verificacaoContratoObservacoes = listVerificacaoContratoObservacoes.get(EfetuaVerificacaoBO.ITEM + iv.getNuItemVerificacaoChecklistProduto());
		}
		disabled = isValidBoolean(verificacaoContratoObservacoes.getIcConforme());
		// Cria componente SimpleTogglePanel para Item
		final HtmlSimpleTogglePanel toggleItem = new HtmlSimpleTogglePanel();
		toggleItem.setId("toggleItem".concat(String.valueOf(iv.getNuItemVerificacaoChecklistProduto())));
		toggleItem.setOpened(false);
		toggleItem.setSwitchType("client");
		toggleItem.setIgnoreDupResponses(true);
		toggleItem.setStyle("width:99%; background-color:#EDEDED !important; border-bottom: 10px solid #fff; padding: 5px 0 1px;");
		HtmlPanelGroup panel = new HtmlPanelGroup();
		
		HtmlPanelGroup panelGrupRight = new HtmlPanelGroup();
		panelGrupRight.setLayout("block");
		panelGrupRight.setStyleClass("espacoCabecalho");
		
		//#######################################################################
		if (iv.getItemVerificacao().getIcDataValidade())
		{			
			//Adicionar Campo Data Aqui
			HtmlCalendar calendario = new HtmlCalendar();
			
			calendario.setOnchanged("nomeDoComponente(" + iv.getNuItemVerificacaoChecklistProduto() + ")");
			calendario.setId("calItem".concat(String.valueOf(String.valueOf(iv.getNuItemVerificacaoChecklistProduto()))));
			calendario.setValue(verificacaoContratoObservacoes.getDtValidade());
			calendario.setInputSize(10);
			calendario.setEnableManualInput(true);
			calendario.setOninputblur("validaData(this); nomeDoComponente(" + iv.getNuItemVerificacaoChecklistProduto() + ")");
			calendario.setInputClass("maskDate");
			calendario.setStyle("padding-right: 8px;");
			calendario.setDatePattern("dd/MM/yyyy");
			calendario.setFirstWeekDay(0);
			calendario.setOninputclick("SimpleTogglePanelManager.toggleOnClient(event,'formEfetuaVerificacao:" + toggleItem.getId() + "');");
			calendario.setOncollapse("SimpleTogglePanelManager.toggleOnClient(event,'formEfetuaVerificacao:" + toggleItem.getId() + "');");

			HtmlPanelGrid grid = new HtmlPanelGrid();
			grid.setStyle("display: table; float: left;");
			grid.setTitle("Data de Validade");
			
			grid.getChildren().add(calendario);
			
			HtmlOutputText linkAncor = new HtmlOutputText();
			linkAncor.setEscape(false);
			linkAncor.setValue("<a name='item"+iv.getNuItemVerificacaoChecklistProduto()+"'></a>");
					
			panelGrupRight.getChildren().add(grid);
			
			panelGrupRight.getChildren().add(linkAncor);

			HtmlSpacer spacer = new HtmlSpacer();
			spacer.setWidth("10px;");
			panelGrupRight.getChildren().add(spacer);
		}
		//#######################################################################
		
		
		HtmlSelectBooleanCheckbox checkConform = new HtmlSelectBooleanCheckbox();
		checkConform.setId("ckItemConform".concat(String.valueOf(iv.getNuItemVerificacaoChecklistProduto())));
		String idIt = iv.getNuItemVerificacaoChecklistProduto().toString();
		String idBl = iv.getBlocoChecklistProduto().getNuBlocoChecklistProduto().toString();
		StringBuffer arrayAp = new StringBuffer();
		arrayAp.append("new Array(");
		for (ApontamentoChecklistProduto a : iv.getApontamentoChecklistProdutoList()) {
			arrayAp.append(a.getNuApontamentoChecklistProduto() + ",");
		}
		arrayAp.append(0);
		arrayAp.append(")");
		StringBuffer arrayIt = new StringBuffer();
		arrayIt.append("new Array(");
		for (ItemVerificacaoChecklistProduto i : iv.getBlocoChecklistProduto().getItemVerificacaoChecklistProdutoList()) {
			arrayIt.append(i.getNuItemVerificacaoChecklistProduto() + ",");
		}
		arrayIt.append(0);
		arrayIt.append(")");
		StringBuffer functionPrejudicado = new StringBuffer();
		if (iv.getBlocoChecklistProduto().getIcPrejudicado()) {
			int idBlocoPrejudicado = iv.getBlocoChecklistProduto().getNuBlocoChecklistProduto();
			functionPrejudicado.append("closeBlocoNaoPrejudicado(this, " + idBlocoPrejudicado + ", new Array(");
			for (BlocoChecklistProduto b : iv.getBlocoChecklistProduto().getChecklistServicoVerificacaoProduto().getBlocoChecklistProdutoList()) {
				functionPrejudicado.append(b.getNuBlocoChecklistProduto() + ",");
			}
			functionPrejudicado.append(0);
			functionPrejudicado.append("));");
		}
		if (isValidBoolean(iv.getIcDesabilitaItem())) {
			checkConform.setOnclick("if(!document.getElementById('formEfetuaVerificacao:ckItemDisabled" + iv.getNuItemVerificacaoChecklistProduto() + "').checked){SimpleTogglePanelManager.toggleOnClient(event,'formEfetuaVerificacao:" + toggleItem.getId() + "');}doItemConforme(this,"+arrayAp+","+idIt+"," + arrayIt+","+idBl+");" + functionPrejudicado);
		} else {
			checkConform.setOnclick("SimpleTogglePanelManager.toggleOnClient(event,'formEfetuaVerificacao:" + toggleItem.getId() + "');doItemConforme(this,"+arrayAp+","+idIt+"," + arrayIt+","+idBl+");"+ functionPrejudicado);
		}
		if (isShowValue()) {
			checkConform.setValue(isValidBoolean(verificacaoContratoObservacoes.getIcConforme()));
		}
		
		HtmlAjaxSupport ajax = new HtmlAjaxSupport();
		ajax.setEvent("onchange");
		ajax.addActionListener(actionListener("#{efetuaVerificacaoMB.doCheckConforme}"));
		
		HtmlActionParameter param = new HtmlActionParameter();
		param.setValue(checkConform.getId());
		param.setName("idCheck");
		ajax.getChildren().add(param);
		
		checkConform.getChildren().add(ajax);
		
		panelGrupRight.getChildren().add(checkConform);
		
		HtmlOutputLabel textConform = new HtmlOutputLabel();
		textConform.setValue(getMessage(MSGS_EFETUA_VERIFICACAO, "Conforme"));
		textConform.setStyle("vertical-align: 2px; margin: 0 40px 0 5px;font-weight: normal;position:static;");
		
		panelGrupRight.getChildren().add(textConform);
		
		HtmlSelectBooleanCheckbox checkDisabled = new HtmlSelectBooleanCheckbox();
		checkDisabled.setId("ckItemDisabled".concat(String.valueOf(iv.getNuItemVerificacaoChecklistProduto())));
		checkDisabled.setOnclick("esconde('formEfetuaVerificacao:" + checkDisabled.getId() + "', 'formEfetuaVerificacao:" + toggleItem.getId() + "', 'formEfetuaVerificacao:" + toggleItem.getId() + "');doItemDesabilita(this,"+arrayAp+","+idIt+"," + arrayIt+","+idBl+");");
		checkDisabled.setRendered(isValidBoolean(iv.getIcDesabilitaItem()));
		checkDisabled.setStyleClass("checkDesabilita");
		if (isShowValue()) {
			checkDisabled.setValue(isValidBoolean(verificacaoContratoObservacoes.getIcDesabilitado()));
		}
		
		HtmlAjaxSupport ajaxDisabled = new HtmlAjaxSupport();
		ajaxDisabled.setEvent("onchange");
		ajaxDisabled.addActionListener(actionListener("#{efetuaVerificacaoMB.doCheckDisabled}"));
		
		HtmlActionParameter param2 = new HtmlActionParameter();
		param2.setValue(checkDisabled.getId());
		param2.setName("idCheck");
		ajaxDisabled.getChildren().add(param2);
		
		checkDisabled.getChildren().add(ajaxDisabled);
		
		panelGrupRight.getChildren().add(checkDisabled);
		
		HtmlOutputLabel textDisabled = new HtmlOutputLabel();
		textDisabled.setValue(getMessage(MSGS_EFETUA_VERIFICACAO, "Desabilita"));
		textDisabled.setStyle("vertical-align: 2px; margin: 0 20px 0 5px;font-weight: normal;position:static;");
		textDisabled.setRendered(isValidBoolean(iv.getIcDesabilitaItem()));
		
		panelGrupRight.getChildren().add(textDisabled);
		
		HtmlAjaxCommandLink btnObs = new HtmlAjaxCommandLink();
		btnObs.setId("btnObsItem".concat(String.valueOf(iv.getNuItemVerificacaoChecklistProduto())));
		btnObs.addActionListener(actionListener("#{efetuaVerificacaoMB.doObservacaoItem}"));
		if (isValidBoolean(iv.getIcDesabilitaItem())) {
			btnObs.setOnclick("if(!document.getElementById('formEfetuaVerificacao:" + checkDisabled.getId() + "').checked){SimpleTogglePanelManager.toggleOnClient(event,'formEfetuaVerificacao:" + toggleItem.getId() + "');}");
		} else {
			btnObs.setOnclick("SimpleTogglePanelManager.toggleOnClient(event,'formEfetuaVerificacao:" + toggleItem.getId() + "');");
		}
		btnObs.setStyle("border-style:none;border-width:0;margin-right:10px");
		btnObs.setReRender("modalObservacao");
		btnObs.setOncomplete("Richfaces.showModalPanel('modalObservacao')");
		
		HtmlGraphicImage iconObs = new HtmlGraphicImage();
		iconObs.setTitle(getMessage(MSGS_EFETUA_VERIFICACAO, "Observacao"));
		iconObs.setValue("/images/verificacao/icone_observacao1.gif");
		
		btnObs.getChildren().add(iconObs);
		panelGrupRight.getChildren().add(btnObs);
		
		panel.getChildren().add(panelGrupRight);
		
		toggleItem.getChildren().add(panel);
		toggleItem.getFacets().put("header", toggleItem.getChildren().get(0));
		childrenPanel.add(toggleItem);
		
		HtmlPanelGroup pnlInconformidades = new HtmlPanelGroup();
		pnlInconformidades.setLayout("block");
		pnlInconformidades.setStyle("float: left; margin-right: 8px; margin-bottom: 20px;");
		
		HtmlOutputText txt = new HtmlOutputText();
		txt.setValue(getMessage(MSGS_EFETUA_VERIFICACAO, "TITLE.inconformidades"));
		txt.setStyleClass("tituloPagina");
		
		pnlInconformidades.getChildren().add(txt);
		
		toggleItem.getChildren().add(pnlInconformidades);
		
		HtmlPanelGroup divClear = new HtmlPanelGroup();
		divClear.setLayout("block");
		divClear.setStyle("clear: both;");
		toggleItem.getChildren().add(divClear);
		return toggleItem.getChildren();
	}
	
	/**
	 * Cria linha de Apontamento
	 * 
	 * @param childrenPanel
	 * @param disabled
	 * @param bloco
	 * @return
	 */
	private List<UIComponent> panelApontamentos(List<UIComponent> childrenPanel, ApontamentoChecklistProduto ap) {
		// verifica se existe informação já gravadas no banco e add no map
		ResultadoApontamentoProduto resultadoApontamentoProduto = null;
		if (!listResultadoApontamentoProdutos.containsKey(APONTAMENTO + ap.getNuApontamentoChecklistProduto())) {
			if (isShowValue()) {
				resultadoApontamentoProduto = listResultadoApontamentoProdutosBD.get(ap.getNuApontamentoChecklistProduto());
			}
			if (resultadoApontamentoProduto == null || resultadoApontamentoProduto.getNuResultadoApontamentoProduto() == null) {
				resultadoApontamentoProduto = new ResultadoApontamentoProduto();
				resultadoApontamentoProduto.setApontamentoChecklistProduto(ap);
				resultadoApontamentoProduto.setContrato(contrato);
				resultadoApontamentoProduto.setCoResponsavelResultado(getUsuarioLogado().getMatricula());
				resultadoApontamentoProduto.setVerificacaoContrato(verificacaoContrato);
			}
			listResultadoApontamentoProdutos.put(APONTAMENTO + ap.getNuApontamentoChecklistProduto(), resultadoApontamentoProduto);
		} else {
			resultadoApontamentoProduto = listResultadoApontamentoProdutos.get(APONTAMENTO + ap.getNuApontamentoChecklistProduto());
		}
		// Cria o panel de verificacao do apontamento
		final HtmlPanel panelApontamento = new HtmlPanel();
		panelApontamento.setId("panelChkApontamento".concat(String.valueOf(ap.getNuApontamentoChecklistProduto())));
		panelApontamento.setStyle("width:99%; background-color:#F7F7F7; border-bottom: 2px solid #fff; padding:5px 0px;");
		
		HtmlPanelGroup panel = new HtmlPanelGroup();
		panel.setLayout("block");
		panel.setId("panelLeftApontamento" + ap.getNuApontamentoChecklistProduto());
		
		HtmlPanelGroup panelIcoLeft = new HtmlPanelGroup();
		panelIcoLeft.setLayout("block");
		panelIcoLeft.setId("panelIcoLeftApontamento" + ap.getNuApontamentoChecklistProduto());
		panelIcoLeft.setStyle("float: left; padding: 0px 5px 0px 10px;");
		
		HtmlGraphicImage icon = new HtmlGraphicImage();
		icon.setId("iconApontamento" + ap.getNuApontamentoChecklistProduto());
		if (isShowValue()) {
			if (resultadoApontamentoProduto.getIcResultadoApontamentoChecklist() == ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_INCONFORME) {
				estadoItemInconforme = true;
				icon.setTitle("Apontamento Inconforme");
				icon.setValue("/images/verificacao/apontamentoI.gif");
			}
			if (resultadoApontamentoProduto.getIcResultadoApontamentoChecklist() == ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_CONFORME) {
				icon.setTitle("Apontamento Conforme");
				icon.setValue("/images/verificacao/apontamentoC.gif");
			}
			if (resultadoApontamentoProduto.getIcResultadoApontamentoChecklist() == ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_NAO_VERIFICADA) {
				estadoItemNaoVerificado = true;
				icon.setTitle("Apontamento");
				icon.setValue("/images/verificacao/apontamento.gif");
			}
		} else {
			estadoItemNaoVerificado = true;
			icon.setTitle("Apontamento");
			icon.setValue("/images/verificacao/apontamento.gif");
			
		}
		
		icon.setWidth("15");
		icon.setHeight("15");
		icon.setStyle("margin: 2px 10px 0 4px; float:left;");
		
		panelIcoLeft.getChildren().add(icon);
		
		panel.getChildren().add(panelIcoLeft);
		
		HtmlPanelGroup pnlDiv = new HtmlPanelGroup();
		pnlDiv.setLayout("block");
		pnlDiv.setStyle("width: 760px; float: left;");
		pnlDiv.setId("panelDiv" + ap.getNuApontamentoChecklistProduto());
		
		HtmlPanelGroup pnl = new HtmlPanelGroup();
		pnl.setStyle("display: table;vertical-align: 5px;");
		pnl.setStyleClass("colorBlue");
		pnl.setId("pnl" + ap.getNuApontamentoChecklistProduto());
		
		HtmlOutputText text = new HtmlOutputText();
		text.setValue(ap.getNumeroDeOrdem() + "- " + ap.getApontamento().getNoApontamentoChecklist());
		
		pnl.getChildren().add(text);
		
		if (Utilities.notEmpty(ap.getDescricao()))
		{
			HtmlGraphicImage iconHelp = new HtmlGraphicImage();
			iconHelp.setTitle(ap.getDescricao());
			iconHelp.setValue("/images/verificacao/icon_ajuda.gif");
			iconHelp.setStyle("margin-left:10px;");
			iconHelp.setStyleClass("toolTip");
			
			pnl.getChildren().add(iconHelp);
		}
		
		
		pnlDiv.getChildren().add(pnl);
		
		panel.getChildren().add(pnlDiv);
		
		HtmlPanelGroup panelGrupRight = new HtmlPanelGroup();
		panelGrupRight.setLayout("block");
		panelGrupRight.setStyleClass("espacoCabecalho");
		
		HtmlSelectBooleanCheckbox check = new HtmlSelectBooleanCheckbox();
		check.setId("ckApontamento".concat(String.valueOf(ap.getNuApontamentoChecklistProduto())));
		check.setStyle("margin-right:20px;");
		String idAp = ap.getNuApontamentoChecklistProduto().toString();
		String idIt = ap.getItemVerificacaoChecklistProduto().getNuItemVerificacaoChecklistProduto().toString();
		String idBl = ap.getItemVerificacaoChecklistProduto().getBlocoChecklistProduto().getNuBlocoChecklistProduto().toString();
		StringBuffer arrayAp = new StringBuffer();
		arrayAp.append("new Array(");
		for (ApontamentoChecklistProduto a : ap.getItemVerificacaoChecklistProduto().getApontamentoChecklistProdutoList()) {
			arrayAp.append(a.getNuApontamentoChecklistProduto() + ",");
		}
		arrayAp.append(0);
		arrayAp.append(")");
		StringBuffer arrayIt = new StringBuffer();
		arrayIt.append("new Array(");
		for (ItemVerificacaoChecklistProduto i : ap.getItemVerificacaoChecklistProduto().getBlocoChecklistProduto().getItemVerificacaoChecklistProdutoList()) {
			arrayIt.append(i.getNuItemVerificacaoChecklistProduto() + ",");
		}
		arrayIt.append(0);
		arrayIt.append(")");
		
		StringBuffer functionPrejudicado = new StringBuffer();
		if (ap.getItemVerificacaoChecklistProduto().getBlocoChecklistProduto().getIcPrejudicado()) {
			int idBlocoPrejudicado = ap.getItemVerificacaoChecklistProduto().getBlocoChecklistProduto().getNuBlocoChecklistProduto();
			functionPrejudicado.append("closeBlocoNaoPrejudicado(this, " + idBlocoPrejudicado + ", new Array(");
			for (BlocoChecklistProduto b : ap.getItemVerificacaoChecklistProduto().getBlocoChecklistProduto().getChecklistServicoVerificacaoProduto().getBlocoChecklistProdutoList()) {
				functionPrejudicado.append(b.getNuBlocoChecklistProduto() + ",");
			}
			functionPrejudicado.append(0);
			functionPrejudicado.append("));");
		}
		
		check.setOnclick("doInconforme(this, " + idAp + "," + arrayAp + "," + idIt + "," + arrayIt + "," + idBl + "); " + functionPrejudicado);
		if (isShowValue()) {
			check.setValue(resultadoApontamentoProduto.getIcResultadoApontamentoChecklist() == ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_INCONFORME);
		}
		check.setDisabled(disabled);
		
		HtmlAjaxSupport ajax = new HtmlAjaxSupport();
		ajax.setId("ajaxDoInconforme".concat(String.valueOf(ap.getNuApontamentoChecklistProduto())));
		ajax.setEvent("onchange");
		ajax.addActionListener(actionListener("#{efetuaVerificacaoMB.doCheckInconforme}"));
		
		HtmlActionParameter param = new HtmlActionParameter();
		param.setValue(check.getId());
		param.setName("idCheck");
		ajax.getChildren().add(param);
		
		check.getChildren().add(ajax);
		
		panelGrupRight.getChildren().add(check);
		
		HtmlAjaxCommandLink btnObs = new HtmlAjaxCommandLink();
		btnObs.setId("btnObsApontamento".concat(String.valueOf(ap.getNuApontamentoChecklistProduto())));
		btnObs.addActionListener(actionListener("#{efetuaVerificacaoMB.doObservacaoApontamento}"));
		btnObs.setStyle("border-style:none;border-width:0;margin-right:10px");
		btnObs.setReRender("modalObservacao");
		btnObs.setOncomplete("Richfaces.showModalPanel('modalObservacao')");
		
		HtmlGraphicImage iconObs = new HtmlGraphicImage();
		iconObs.setTitle("Observação");
		iconObs.setValue("/images/verificacao/icone_observacao1.gif");
		
		btnObs.getChildren().add(iconObs);
		panelGrupRight.getChildren().add(btnObs);
		
		panel.getChildren().add(panelGrupRight);
		
		panelApontamento.getChildren().add(panel);
		childrenPanel.add(panelApontamento);
		
		HtmlPanelGroup divClear = new HtmlPanelGroup();
		divClear.setLayout("block");
		divClear.setStyle("clear: both;");
		
		panelApontamento.getChildren().add(divClear);
		
		return panelApontamento.getChildren();
	}
	
	public HtmlPanelGroup getPanelLeftItem(ItemVerificacaoChecklistProduto iv) {
		HtmlPanelGroup panel = new HtmlPanelGroup();
		panel.setLayout("block");
		panel.setId("panelLeftItem" + iv.getNuItemVerificacaoChecklistProduto());
		
		HtmlGraphicImage icon = new HtmlGraphicImage();
		icon.setId("iconItem" + iv.getNuItemVerificacaoChecklistProduto());
		if (!isShowValue()) {
			icon.setTitle("Item");
			icon.setValue("/images/verificacao/item.gif");
			estadoBlocoNaoVerificado = true;
		} else {
			if (estadoItemInconforme) {
				icon.setTitle("Item Inconforme");
				icon.setValue("/images/verificacao/itemI.gif");
				estadoBlocoInconforme = true;
			} else {
				if (estadoItemNaoVerificado) {
					icon.setTitle("Item");
					icon.setValue("/images/verificacao/item.gif");
					estadoBlocoNaoVerificado = true;
				} else {
					icon.setTitle("Item Conforme");
					icon.setValue("/images/verificacao/itemC.gif");
				}
			}
		}
		icon.setStyle("margin: 2px 10px 0 4px; float:left;");
		icon.setWidth("15");
		icon.setHeight("15");
		
		HtmlPanelGroup panelIcoLeft = new HtmlPanelGroup();
		panelIcoLeft.setLayout("block");
		panelIcoLeft.setId("panelIcoLeftItem" + iv.getNuItemVerificacaoChecklistProduto());
		panelIcoLeft.setStyle("float: left; padding: 0px 5px 0px 10px;");
		
		panelIcoLeft.getChildren().add(icon);
		
		panel.getChildren().add(panelIcoLeft);
		
		HtmlPanelGroup pnlDiv = new HtmlPanelGroup();
		pnlDiv.setLayout("block");
		if (isValidBoolean(iv.getIcDesabilitaItem())) {
			if (iv.getItemVerificacao().getIcDataValidade())
				pnlDiv.setStyle("width: 480px; float: left;");
			else
				pnlDiv.setStyle("width: 560px; float: left;");
		} else {
			if (iv.getItemVerificacao().getIcDataValidade())
				pnlDiv.setStyle("width: 560px; float: left;");
			else
				pnlDiv.setStyle("width: 680px; float: left;");
		}
		
		HtmlPanelGroup pnl = new HtmlPanelGroup();
		pnl.setStyleClass("colorBlue");
		
		HtmlOutputText text = new HtmlOutputText();
		text.setValue(iv.getNumeroDeOrdem() + "- " + iv.getItemVerificacao().getNoItemVerificacao());
		
		pnl.getChildren().add(text);
		
		if (Utilities.notEmpty(iv.getDeItemVerificacaoChecklistProduto()))
		{
			HtmlGraphicImage iconHelp = new HtmlGraphicImage();
			iconHelp.setTitle(iv.getDeItemVerificacaoChecklistProduto());
			iconHelp.setValue("/images/verificacao/icon_ajuda.gif");
			iconHelp.setStyle("margin-left:10px;");
			
			pnl.getChildren().add(iconHelp);
			
			iconHelp.setStyleClass("toolTip");
		}
		
		pnlDiv.getChildren().add(pnl);
		
		panel.getChildren().add(pnlDiv);
		
		return panel;
	}
	
	public HtmlPanelGroup getPanelLeftBloco(BlocoChecklistProduto b, boolean isPrejudicado) {
		HtmlPanelGroup panelGrupLeft = new HtmlPanelGroup();
		panelGrupLeft.setLayout("block");
		panelGrupLeft.setStyle("background-color:#CED5E5");
		panelGrupLeft.setId("panelLeftBloco" + b.getNuBlocoChecklistProduto());
		
		// Cria o icone de bloco para adicionar no painel
		HtmlGraphicImage iconBloco = new HtmlGraphicImage();
		iconBloco.setId("iconBloco" + b.getNuBlocoChecklistProduto());
		
		if (!isShowValue()) {
			iconBloco.setTitle("Bloco");
			iconBloco.setValue("/images/verificacao/bloco.gif");
		} else {
			if (estadoBlocoInconforme) {
				iconBloco.setTitle("Bloco Inconforme");
				iconBloco.setValue("/images/verificacao/blocoI.gif");
			} else {
				if (estadoBlocoNaoVerificado) {
					iconBloco.setTitle("Bloco");
					iconBloco.setValue("/images/verificacao/bloco.gif");
				} else {
					iconBloco.setTitle("Bloco Conforme");
					iconBloco.setValue("/images/verificacao/blocoC.gif");
				}
			}
		}
		elementsSrc.add(iconBloco.getValue().toString());
		
		if(isPrejudicado){
			iconBloco.setTitle("Bloco");
			iconBloco.setValue("/images/verificacao/blocoP.gif");
		}
		
		iconBloco.setStyle("margin: 2px 10px 0 4px; float:left;");
		iconBloco.setWidth("15");
		iconBloco.setHeight("15");
		
		HtmlPanelGroup panelIcoLeft = new HtmlPanelGroup();
		panelIcoLeft.setLayout("block");
		panelIcoLeft.setId("panelIcoLeftBloco" + b.getNuBlocoChecklistProduto());
		panelIcoLeft.setStyle("float: left; padding: 0px 5px 0px 10px;");
		
		panelIcoLeft.getChildren().add(iconBloco);
		
		// adiciona o icone do bloco no painel (panelGrupLeft)
		panelGrupLeft.getChildren().add(panelIcoLeft);
		
		HtmlPanelGroup pnlDiv = new HtmlPanelGroup();
		pnlDiv.setLayout("block");
		pnlDiv.setStyle("float:left; width: 720px;");
		
		HtmlPanelGroup pnl = new HtmlPanelGroup();
		pnl.setStyle("display: table;vertical-align: 5px;");
		pnl.setStyleClass("colorBlue");
		
		// cria um outputText com o nome do bloco
		HtmlOutputText text = new HtmlOutputText();
		text.setValue(b.getNumeroDeOrdem() + "- " + b.getBlocoChecklist().getNoBlocoChecklist());
		pnl.getChildren().add(text);
		
		if (Utilities.notEmpty(b.getDescricao()))
		{
			HtmlGraphicImage iconHelp = new HtmlGraphicImage();
			iconHelp.setTitle(b.getDescricao());
			iconHelp.setValue("/images/verificacao/icon_ajuda.gif");
			iconHelp.setStyle("margin-left:10px;");
			iconHelp.setStyleClass("toolTip");
			
			pnl.getChildren().add(iconHelp);
		}
		
		pnlDiv.getChildren().add(pnl);
		
		panelGrupLeft.getChildren().add(pnlDiv);
		return panelGrupLeft;
	}
	
	private ActionListener actionListener(String value) {
		MethodExpression actionListener = getApplication().getExpressionFactory().createMethodExpression(getFacesContext().getELContext(), value, null, new Class[] { ActionEvent.class });
		return new MethodExpressionActionListener(actionListener);
	}
	
	
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public boolean isBloco() {
		return opcao.equals(EfetuaVerificacaoBO.BLOCO);
	}
	
	public boolean isItem() {
		return opcao.equals(EfetuaVerificacaoBO.ITEM);
	}
	
	public boolean isApontamento() {
		return opcao.equals(APONTAMENTO);
	}
	
	public void modeBloco() {
		opcao = EfetuaVerificacaoBO.BLOCO;
	}
	
	public void modeItem() {
		opcao = EfetuaVerificacaoBO.ITEM;
	}
	
	public void modeApontamento() {
		opcao = APONTAMENTO;
	}
	
	public String idBloco() {
		return EfetuaVerificacaoBO.BLOCO + id;
	}
	
	public String idItem() {
		return EfetuaVerificacaoBO.ITEM + id;
	}
	
	public String idApontamento() {
		return APONTAMENTO + id;
	}
	
	public String getHeaderModal() {
		if (isBloco()) {
			return getMessage(MSGS_EFETUA_VERIFICACAO, "Observacao") + " " + getMessage(MSGS_EFETUA_VERIFICACAO, "Bloco");
		}
		
		if (isItem()) {
			return getMessage(MSGS_EFETUA_VERIFICACAO, "Observacao") + " " + getMessage(MSGS_EFETUA_VERIFICACAO, "Item");
		}
		
		if (isApontamento()) {
			return getMessage(MSGS_EFETUA_VERIFICACAO, "Observacao") + " " + getMessage(MSGS_EFETUA_VERIFICACAO, "Apontamento");
		}
		return "";
	}
	
	// para funcionar a importação de verificações anteriores em verificações
	// Inconformes basta descomentar estas linhas abaixo e retirar o atual
	// return false;
	public boolean isShowImportaVerificacoesIncoformes() {
		if (verificacaoContratoVO == null
				|| verificacaoContratoVO.getIcEstadoVerificacao() == null) {
			return false;
		}
		return verificacaoContratoVO.getIcEstadoVerificacao().equals(
				VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_INCONFORME)
				&& !isImportado;
	}
	
	/**
	 * valida se o valor var é null e retorna false else retorna var
	 * 
	 * @param var
	 * @return true/false
	 */
	public boolean isValidBoolean(Boolean var) {
		if (var == null) {
			return false;
		}
		return var.booleanValue();
	}
	
	// se true pode inserir as responstas anteriores na memoria
	private boolean isShowValue() {
		if (verificacaoContratoVO == null || verificacaoContratoVO.getIcEstadoVerificacao() == null) {
			return false;
		}
		if (!verificacaoContratoVO.getIcEstadoVerificacao().equals(VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_INCONFORME)) {
			return true;
		}
		return isImportado;
	}
	
	
	/** 
	 * ------------------------------------------------ Emissão de parecer -----------------------------------------------------------------------
	 * 
	 */
	
	private IGerenteUnidadeBO gerenteUnidadeBO;
	private IParecerBO parecerBO;
	
	@Autowired
	private void setGerenteUnidadeBO(IGerenteUnidadeBO gerenteUnidadeBO) {
		this.gerenteUnidadeBO = gerenteUnidadeBO;
	}
	
	@Autowired
	private void setParecerBO(IParecerBO parecerBO) {
		this.parecerBO = parecerBO;
	}
	
	private String validToParecer = "0";
	private List<GerenteUnidade> listGerentesUnidade;
	private String textoResumoParecer;
	private Integer nuMatriculaGerenteUnidadeSelecionado;
	private boolean showModalEmitirParecer = false;
	
	public String getValidToParecer() {
		return this.validToParecer;
	}

	public void setValidToParecer(String validToParecer){
		this.validToParecer = validToParecer;
	}

	public List<GerenteUnidade> getListGerentesUnidade() {
		return this.listGerentesUnidade;
	}
	
	public void setListGerentesUnidade(List<GerenteUnidade> listGerentesUnidade){
		this.listGerentesUnidade = listGerentesUnidade;
	}

	public String getTextoResumoParecer() {
		return this.textoResumoParecer;
	}
	
	public void setTextoResumoParecer(String textoResumoParecer){
		this.textoResumoParecer = textoResumoParecer;
	}
	
	public Integer getNuMatriculaGerenteUnidadeSelecionado(){
		return this.nuMatriculaGerenteUnidadeSelecionado;
	}
	
	public void setNuMatriculaGerenteUnidadeSelecionado(Integer nuMatriculaGerenteUnidadeSelecionado){
		this.nuMatriculaGerenteUnidadeSelecionado = nuMatriculaGerenteUnidadeSelecionado;
	}
	
	public boolean isShowModalEmitirParecer(){
		return this.showModalEmitirParecer;
	}
	
	public void setShowModalEmitirParecer(boolean showModalEmitirParecer){
		this.showModalEmitirParecer = showModalEmitirParecer;
	}
	
	public void doEmitirParecer(ActionEvent evt) {
		this.doSave(false);
		atualizaList();
		getPanelChecklist();
		List<PreferenciaUsuarioGerente> lista = preferenciaUsuarioGerenteBO.getAll(SegurancaUsuario.getInstance().getUsuario().getMatricula());
		
		if(lista != null && !lista.isEmpty()){
			this.setNuMatriculaGerenteUnidadeSelecionado(lista.get(0).getId().getCoGerentePreferencial());
		}
		else{
			this.setNuMatriculaGerenteUnidadeSelecionado(null);
		}
	}
	private String blocoDataObrigatoria;
	
	public String getBlocoDataObrigatoria(){
		return blocoDataObrigatoria;
	}
	public void validateToParecer(ActionEvent evt){

		setValidToParecer(this.efetuaVerificacaoBO.validaParaParecer(this.verificacaoContratoVO.getNuVerificacaoContrato()));

		
		// Se não possuir template conforme para o serviço que está sendo trabalhado.  
		if(getValidToParecer().equals("3")){
			warn(MSGS_EFETUA_VERIFICACAO, MSG_NAO_EXISTE_TEMPLATE_CONFORME);
			return;
		}

		// Se não possuir template inconforme para o serviço que está sendo trabalhado.  
		if(getValidToParecer().equals("4")){
			warn(MSGS_EFETUA_VERIFICACAO, MSG_NAO_EXISTE_TEMPLATE_INCONFORME);
			return;
		}
		
		for(BlocoChecklistProduto b : arvoreChecklist.getBlocoChecklistProdutoList()){
			if(b.getIcPrejudicado()){
				boolean prejudicado = efetuaVerificacaoBO.isVerificacaoPrejudicada(b.getNuBlocoChecklistProduto(), verificacaoContratoVO.getNuVerificacaoContrato());
				if(prejudicado){
					setValidToParecer("0");
					return;
				}
			}
		}
		
		List validaDataObrigatoria = this.efetuaVerificacaoBO.validaDataObrigatoria(this.verificacaoContratoVO.getNuVerificacaoContrato());
		StringBuffer arrayBloco = new StringBuffer();
		for (Object i : validaDataObrigatoria) {
			Object[] o = (Object[]) i;
			if(arrayBloco.length() == 0){
				arrayBloco.append(o[1] + ",");
			}
			arrayBloco.append(o[0] + ",");
		}
		arrayBloco.append(0);
		blocoDataObrigatoria = arrayBloco.toString();
		
		// Se possuir algum item que tenha a data de validade como obrigatória e não preenchida, será exibida uma mensagem. 
		if(validaDataObrigatoria.size()>0){
			setValidToParecer("1");
//			warn(MSGS_EFETUA_VERIFICACAO, MSG_ITENS_SEM_DATA_VALIDADE);
		}
		
	}
	
	public void atualizaEmails(ActionEvent evt)
	{
		Boolean isConforme = this.efetuaVerificacaoBO.isConforme(verificacaoContrato.getNuVerificacaoContrato());
		TemplateParecer templateParecer = this.templateParecerBO.findByServicoVerificacaoProdutoAndResultado
													(
															verificacaoContrato.getServicoVerificacaoProduto(), 
															isConforme
														);
		initializeEmails(isConforme, templateParecer);
		initializeEmailsComCopia(isConforme, templateParecer);
	}
	
	public void openModalParecer(ActionEvent evt){
		
		// Executar a rotina de marcar como Conforme todos os apontamentos não verificados, caso a verificação 
		// possua apontamentos não verificados e o usuário confirmar que pode alterar 
		// todos para "Conforme" (Se o sistema acessar este método, significa que o usuário confirmou).
		if(getValidToParecer().equals("2")){
			this.efetuaVerificacaoBO.setNaoVerificadoToConforme(this.verificacaoContratoVO.getNuVerificacaoContrato());
		}
		
		setTextoResumoParecer(this.resumoParecerBO.getResumoParecer(this.verificacaoContratoVO.getNuVerificacaoContrato()));
		Boolean isConforme = this.efetuaVerificacaoBO.isConforme(verificacaoContrato.getNuVerificacaoContrato());
		TemplateParecer templateParecer = this.templateParecerBO.findByServicoVerificacaoProdutoAndResultado
													(
															verificacaoContrato.getServicoVerificacaoProduto(), 
															isConforme
														);
		initializeEmails(isConforme, templateParecer);
		initializeEmailsComCopia(isConforme, templateParecer);
		listGerentesUnidade = this.gerenteUnidadeBO.getListGerentesUnidade(SegurancaUsuario.getInstance().getUsuario().getUnidade());
		
		// Não precisa mais atualizar o 'checklist' pois não há mais a funcionalidade de marcar como conforme, aqueles que não foram verificados.
//		atualizaList();
//		getPanelChecklist();
		setShowModalEmitirParecer(Boolean.TRUE);
		atualizaSituacaoCabecalho();
	}
	
	public void closeModalParecer(ActionEvent evt) {
		setShowModalEmitirParecer(Boolean.FALSE);
		
		// Não precisa mais atualizar o 'checklist' pois não há mais a funcionalidade de marcar como conforme, aqueles que não foram verificados.
//		atualizaList();
//		getPanelChecklist();

		atualizaSituacaoCabecalho();
		
		warn(MSGS, MN003);
	}
	
	public void doSaveParecer(ActionEvent evt) {
		try{
			if(Utilities.empty(this.getNuMatriculaGerenteUnidadeSelecionado())){
				warn(MSGS, MN002);
				warn("O Gerente de Unidade deve ser selecionado.");
				return;
			}
			Parecer parecer = parecerBO.geraParecer(this.verificacaoContratoVO.getNuVerificacaoContrato(), 
							  SegurancaUsuario.getInstance().getUsuario(), 
							  this.getNuMatriculaGerenteUnidadeSelecionado(),
							  "src=\"" + getRequest().getRealPath("images") + System.getProperty("file.separator"), 
							  true);
			this.parecerBO.executaEnviaParecer(parecer);
			
			
			
			setShowModalEmitirParecer(Boolean.FALSE);
			info(MSGS, MN001);
			atualizaSituacaoCabecalho();
			atualizaPrazoVerificacaoBO.atualizaPrazoVerificao(this.verificacaoContrato.getContrato().getNuContrato());

			NavigationHandler nh = getFacesContext().getApplication().getNavigationHandler();
			nh.handleNavigation(getFacesContext(), null, "toEscolheVerificacao");
		} catch (SIIACRuntimeException e) {
			error(e.getMessage());
			return;
		}
	}
	
	//Atualiza o estado do campo "Situação" no cabeçalho.	
	private void atualizaSituacaoCabecalho(){
		CabecalhoDetalhesVerificacaoMB cabecalhoDetalhesVerificacaoMB = (CabecalhoDetalhesVerificacaoMB) 
																				getRequestMap().get("cabecalhoDetalhesVerificacaoMB");
		cabecalhoDetalhesVerificacaoMB.inicializa();
	}

	public String getListDestinatariosEnviar() {
		return this.emailsDestinatarios;	
	}
	
	public String getListDestinatariosEnviarComCopia() {
		return this.emailsDestinatariosComCopia;
	}
	
	private void initializeEmails(Boolean isConforme, TemplateParecer templateParecer) {
		List<Integer> list = this.templateParecerBO.getDestinatariosEnviarEmailId(templateParecer.getNuTemplateParecer());
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < list.size(); i++) {
			String[] r = this.enviaParecerBO.getEmail(
					list.get(i), 
					contrato, 
					FormatUtil.formatMatricula(nuMatriculaGerenteUnidadeSelecionado), 
					getUsuarioLogado().getMatricula(), 
					verificacaoContrato.getCoRspnlVerificacao());
			
			if(r != null && r.length == 2 && r[0] != null){
				if(builder != null && builder.length() > 0) {
					builder.append(", ");
				}
				
				builder.append(r[1]);
				builder.append("<");
				builder.append(r[0]);
				builder.append(">");
			}
		}
		
		emailsDestinatarios = builder.toString();
	}
	
	private void initializeEmailsComCopia(Boolean isConforme, TemplateParecer templateParecer) {
		
		List<Integer> list = this.templateParecerBO.getDestinatariosEnviarEmailComCopiaId(templateParecer.getNuTemplateParecer());
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < list.size(); i++) {
			String[] r = this.enviaParecerBO.getEmail(
					list.get(i), 
					contrato, 
					FormatUtil.formatMatricula(nuMatriculaGerenteUnidadeSelecionado), 
					getUsuarioLogado().getMatricula(), 
					verificacaoContrato.getCoRspnlVerificacao());
			
			if(r != null && r.length == 2 && r[0] != null){
				if(builder != null && builder.length() > 0) {
					builder.append(", ");
				}
				
				builder.append(r[1]);
				builder.append("<");
				builder.append(r[0]);
				builder.append(">");
			}
		}
		
		emailsDestinatariosComCopia = builder.toString();
	} 
	
	String emailsDestinatarios = "";
	String emailsDestinatariosComCopia = "";

	@SuppressWarnings("all")
	public List getTestA4jRepeat() {
		return elementsSrc;
	}
	
	List<String> elementsSrc = new ArrayList<String>();
	
	public void changeMatriculaGerente(ActionEvent evt) {
	
	}
}