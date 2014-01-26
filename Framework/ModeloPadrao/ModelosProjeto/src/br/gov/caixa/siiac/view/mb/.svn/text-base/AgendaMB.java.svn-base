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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;

import org.richfaces.component.html.HtmlDataTable;
import org.richfaces.component.html.HtmlDatascroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.IAgendaBO;
import br.gov.caixa.siiac.bo.IVerificacaoContratoBO;
import br.gov.caixa.siiac.bo.impl.MatrizAcessoBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.view.mb.pagination.PagedDataModel;

@Service()
@Scope("request")
public class AgendaMB extends AbstractMB {
	
	private IAgendaBO agendaBO;
	private IVerificacaoContratoBO verificacaoContratoBO;
	private List<Contrato> listContratoAgenda = new ArrayList<Contrato>();
	private boolean filtrarPreferenciais = false;
	private boolean painelVerMaisRegistros = true;
	private DataModel dataModel;
	private HtmlDataTable dataTable = new HtmlDataTable();
	private HtmlDatascroller datascroller = new HtmlDatascroller();
	private IUsuario userLogado = SegurancaUsuario.getInstance().getUsuario();
	private int firstValue, previousValue = -1;
	private Integer totalListSize;
	private Map<String, Integer> order = new HashMap<String, Integer>();
	private boolean isOrder;
	
	private static final String MSGS_AGENDA = "msgsAgenda";
	private static final String MSG_AGENDA_NAO_GERADA = "MSG_AGENDA_NAO_GERADA";
	
	private static final String CONST_SEM_PRAZO = "Sem prazo";
	private static final String CONST_DIAS = " dia(s)";
	
	public AgendaMB() {
		
	}
	
	public void doCheckPreferenciais(ActionEvent evt) {
		atualizaList();
	}
	
	public String doDetalhesVerificacao() {
		Contrato contrato = (Contrato) getRequestMap().get("row");
		if (this.agendaBO.existeAgendaContrato(contrato.getNuContrato())) {
			getHttpSession().setAttribute(VerificacaoPreventivaMB.IS_VERIFICACAO_PREVENTIVA, null);
			getHttpSession().setAttribute("contrato", contrato);
			try {
				VerificacaoContratoVO verificacaoContratoVO = agendaBO.hasOnlyOneVerificacaoContratoAVerificar(contrato);
				if (verificacaoContratoVO != null) {
					getHttpSession().setAttribute("verificacaoContratoVO", verificacaoContratoVO);
					getSessionMap().put("efetuaVerificacaoMB", null);
					return "toEfetuaVerificacao";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "toEscolheVerificacao";
		}
		warn(getMessage(MSGS_AGENDA, MSG_AGENDA_NAO_GERADA));
		return "";
	}
	
	public String doDetalhesVerificacaoPreventiva(Contrato contrato) {
		if (this.agendaBO.existeAgendaContrato(contrato.getNuContrato())) {
			getHttpSession().setAttribute("contrato", contrato);
			return "toEscolheVerificacao";
		}
		warn(getMessage(MSGS_AGENDA, MSG_AGENDA_NAO_GERADA));
		return "";
	}
	
	private void atualizaList() {
		order.put("prazo", 1);
		totalListSize = agendaBO.getListaContratoAgendaCount(filtrarPreferenciais, userLogado);
		listContratoAgenda = agendaBO.getListaContratoAgenda(filtrarPreferenciais, userLogado, 0, getDataTable().getRows(), order);
	}
	
	@Autowired
	public void setAgendaBO(IAgendaBO agendaBO) {
		this.agendaBO = agendaBO;
		atualizaList();
	}
	
	public List<Contrato> getListContratoAgenda() {
		return listContratoAgenda;
	}
	
	public void setListContratoAgenda(List<Contrato> listContratoAgenda) {
		this.listContratoAgenda = listContratoAgenda;
	}
	
	public boolean isShowVerificacaoParcial() {
		Contrato contrato = (Contrato) getRequestMap().get("row");
		return agendaBO.isVerificacaoParcial(contrato);
	}
	
	public boolean isFiltrarPreferenciais() {
		return filtrarPreferenciais;
	}
	
	public void setFiltrarPreferenciais(boolean filtrarPreferenciais) {
		this.filtrarPreferenciais = filtrarPreferenciais;
	}
	
	public boolean isShowForaPrazoIco() {
		Contrato contrato = (Contrato) getRequestMap().get("row");
		return contrato.getQtPrazoVerificacao() != null && contrato.getQtPrazoVerificacao().intValue() < 0 && agendaBO.hasVerificacaoContratoAVerificar(contrato);
	}
	
	public boolean isShowAVeriricarIco() {
		Contrato contrato = (Contrato) getRequestMap().get("row");
		return (contrato.getQtPrazoVerificacao() == null || contrato.getQtPrazoVerificacao().intValue() >= 0) && agendaBO.hasVerificacaoContratoAVerificar(contrato);
	}
	
	public String getFormatIcPfPj() {
		Contrato contrato = (Contrato) getRequestMap().get("row");
		return ContratoMB.formataDocumento(contrato);
	}
	
	public boolean isShowGeneratingLabel() {
		Contrato contrato = (Contrato) getRequestMap().get("row");
		return !Contrato.AGENDA_GERADA_ID_GERACAO_OK.equals(contrato.getIcAgendaGerada());
	}
	
	public boolean isShowActionButton() {
		Contrato contrato = (Contrato) getRequestMap().get("row");
		Short nuPerfil = getUsuarioLogado().getPerfis().get(0);
		return Contrato.AGENDA_GERADA_ID_GERACAO_OK.equals(contrato.getIcAgendaGerada()) && MatrizAcessoBO.acessoModuloConsultaVerificacao(nuPerfil, MatrizAcessoBO.CONSULTA_VERIFICACAO, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isPainelVerMaisRegistros() {
		return painelVerMaisRegistros;
	}
	
	public void setPainelVerMaisRegistros(boolean painelVerMaisRegistros) {
		this.painelVerMaisRegistros = painelVerMaisRegistros;
	}
	
	@Autowired
	public void setVerificacaoContratoBO(IVerificacaoContratoBO verificacaoContratoBO) {
		this.verificacaoContratoBO = verificacaoContratoBO;
	}
	
	public DataModel getDataModel() {
		firstValue = dataTable.getRows() * (datascroller.getPage() - 1);
		if (previousValue == -1) {
			totalListSize = agendaBO.getListaContratoAgendaCount(filtrarPreferenciais, userLogado);
		}
		if (firstValue != previousValue || isOrder) {
			listContratoAgenda = agendaBO.getListaContratoAgenda(filtrarPreferenciais, userLogado, firstValue, getDataTable().getRows(), order);
			previousValue = firstValue;
			if (totalListSize == null) {
				totalListSize = 0;
			}
		}
		dataModel = new PagedDataModel(listContratoAgenda, totalListSize, 30);
		return dataModel;
	}
	
	public void setDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}
	
	public HtmlDataTable getDataTable() {
		return dataTable;
	}
	
	public void setDataTable(HtmlDataTable dataTable) {
		this.dataTable = dataTable;
	}
	
	public HtmlDatascroller getDatascroller() {
		return datascroller;
	}
	
	public void setDatascroller(HtmlDatascroller datascroller) {
		this.datascroller = datascroller;
	}
	
	public void sortSgContrato(ActionEvent evt) {
		isOrder = true;
		sort("sgContrato");
		getDataModel();
	}
	
	public void sortPrazo(ActionEvent evt) {
		isOrder = true;
		sort("prazo");
		getDataModel();
	}
	
	public void sortProduto(ActionEvent evt) {
		isOrder = true;
		sort("produto");
		getDataModel();
	}
	
	public void sortContrato(ActionEvent evt) {
		isOrder = true;
		sort("contrato");
		getDataModel();
	}
	
	public void sortNoCliente(ActionEvent evt) {
		isOrder = true;
		sort("noCliente");
		getDataModel();
	}
	
	public void sortCpfCnpj(ActionEvent evt) {
		isOrder = true;
		sort("cpfCnpj");
		getDataModel();
	}
	
	public void sortValor(ActionEvent evt) {
		isOrder = true;
		sort("valor");
		getDataModel();
	}
	
	private void sort(String nome) {
		if (order.get(nome) == null) {
			order.put(nome, 1);
		} else {
			if (order.get(nome) == 1) {
				order.put(nome, 2);
			} else {
				order.put(nome, 1);
			}
		}
		for (String s : order.keySet()) {
			if (!s.equals(nome)) {
				order.put(s, null);
			}
		}
	}
	
	public Map<String, Integer> getOrder() {
		return order;
	}
	
	public void setOrder(Map<String, Integer> order) {
		this.order = order;
	}
	
	public String getQtPrazoVerificacao() {
		Contrato contrato = (Contrato) getRequestMap().get("row");
		if(contrato.getQtPrazoVerificacao() == null) {
			return CONST_SEM_PRAZO;
		}else{
			return contrato.getQtPrazoVerificacao() + CONST_DIAS;
		}
	}
	
	public String getStyleColPrazo(){
		Contrato contrato = (Contrato) getRequestMap().get("row");
		if(contrato.getQtPrazoVerificacao() == null || contrato.getQtPrazoVerificacao() >= 0){
			return "color:#38599C;";
		}else{
			return "color:#ff0000;";
		}
	}
	
	public String getContrato() {
		Contrato contrato = (Contrato) getRequestMap().get("row");
		return ConvertUtils.padZerosLeft(String.valueOf(contrato.getNuUnidade()), 4) + "." + ConvertUtils.padZerosLeft(String.valueOf(contrato.getNuOperacao()), 3) + "." + contrato.getNuContrato();
	}
}