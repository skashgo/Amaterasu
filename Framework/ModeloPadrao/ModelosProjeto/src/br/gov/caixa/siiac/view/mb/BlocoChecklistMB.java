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
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IBlocoChecklistBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.BlocoChecklist;
import br.gov.caixa.siiac.util.LogCEF;

@Service()
@Scope("request")
public class BlocoChecklistMB extends AbstractMB {
		
	private BlocoChecklist blocoChecklist = new BlocoChecklist();;
	private List<BlocoChecklist> listBlocoChecklist = new ArrayList<BlocoChecklist>();
	private transient IBlocoChecklistBO blocoChecklistBO;

	/**
	 * Filtro
	 */
	private String pesquisaString;
	private Boolean pesquisaMostraInativos;
	
	
	//Methods Actions
	public Boolean doValida() {
		//valida se foi inserido todos os dados no formulario
		if (blocoChecklist == null || blocoChecklist.getNoBlocoChecklist() == null || blocoChecklist.getNoBlocoChecklist().trim().equals("") && !blocoChecklist.getIcAtivo() == false) {
			error(MSGS, MN002);
			warn( "Campo NOME Obrigatório.");
			return false;
		}
		//verifica se o bloco esta inativo
		if (isModoAltera() && !blocoChecklistBO.isAtivo(blocoChecklist)) {
			error(MSGS, MN013);
			return false;
		}
		try {
			if (blocoChecklistBO.exist(blocoChecklist)) {
				error(MSGS, MN015);
				return false;
			}
		} catch (SIIACException e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	public void doGravar(ActionEvent evt) {
		if (doValida()) {
			try {
				blocoChecklistBO.merge(blocoChecklist);
				if (isModoAltera()) {
					info(MSGS, MN008);
				} else {
					info(MSGS, MN007);
				}
			} catch (Exception e) {
				error(MSGS, MN002);
				LogCEF.error(e.getMessage());
			}
			modoInicio();
		}
	}
	
	public void doFiltrar(ActionEvent evt) {
		List<BlocoChecklist> lista = atualizaList();
		if (lista == null || lista.isEmpty()) {
			warn(MSGS, MN016);
		}
		setListBlocoChecklist(lista);
	}
	
	public void doDesabilitarNaGrid(ActionEvent evt) {
		blocoChecklist = (BlocoChecklist) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		try {
			blocoChecklistBO.ativaInativa(blocoChecklist);
			if (blocoChecklist.getIcAtivo()) {
				info(MSGS, MN012);
			} else {
				info(MSGS, MN011);
			}
		} catch (Exception e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
		}
		modoInicio();
		setListBlocoChecklist(atualizaList());
	}
	
	public void doDesabilitarNoButton(ActionEvent evt) {
		try {
			blocoChecklistBO.ativaInativa(blocoChecklist);
			if (blocoChecklist.getIcAtivo()) {
				info(MSGS, MN012);
			} else {
				info(MSGS, MN011);
			}
			modoVisualizar();
		} catch (Exception e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
			modoInicio();
		}
	}
	
	public void doAtualizar(ActionEvent evt) {
		blocoChecklist = (BlocoChecklist) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		modoAtualizar();
	}
	
	public void doVisualizar(ActionEvent evt) {
		blocoChecklist = (BlocoChecklist) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		modoVisualizar();
	}
	
	public void doCadastrar(ActionEvent evt) {
		modoCadastrar();
	}
	
	public void doCancelar(ActionEvent evt) {
		warn(MSGS, MN003);
		modoInicio();
	}
	
	public void doVoltar(ActionEvent evt) {
		modoInicio();
	}
	
	private List<BlocoChecklist> atualizaList() {
		try {
			return blocoChecklistBO.getListFiltro(pesquisaString, pesquisaMostraInativos);
		} catch (SIIACException e) {
			warn(MSGS, MN016);
			LogCEF.error(e.getMessage());
		}
		return new ArrayList<BlocoChecklist>();
	}
	
	public List<SelectItem> getSelectItemIcAtivo() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem(true, "Ativos"));
		list.add(new SelectItem(false, "Inativos"));
		return list;
	}
	
	
	private void modoInicio() {
		setModoInicio();
		blocoChecklist = new BlocoChecklist();
		pesquisaString = "";
		pesquisaMostraInativos = false;
		setListBlocoChecklist(atualizaList());
	}
	
	private void modoCadastrar() {
		setModoCadastro();
		blocoChecklist = new BlocoChecklist();
	}
	
	private void modoAtualizar() {
		setModoAltera();
	}
	
	private void modoVisualizar() {
		setModoVisualiza();
	}
	
	//Getters and Setters
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
	
	public String getLabelConfirm() {
		if (isModoAltera()) {
			return getMessage(MSGS, MA003);
		} else {
			return getMessage(MSGS, MA002);
		}
	}
	
	public BlocoChecklist getBlocoChecklist() {
		return this.blocoChecklist;
	}
	
	public void setBlocoChecklist(BlocoChecklist blocoChecklist) {
		this.blocoChecklist = blocoChecklist;
	}
	
	public boolean isShowPanelCadastro() {
		return isModoCadastro() || isModoAltera();
	}
	
	public boolean isShowPanelFiltro() {
		return isModoInicio();
	}
	
	public boolean isShowPanelLista() {
		return isModoInicio();
	}
	
	public boolean isShowPanelButtonCadastrar() {
		return isModoInicio();
	}
	
	public boolean isShowPanelVisualizacao() {
		return isModoVisualiza();
	}
		
	public boolean isShowCadastrar() {
		return isModoCadastro();
	}
	
	public List<BlocoChecklist> getListBlocoChecklist() {
		return this.listBlocoChecklist;
	}
	
	public void setListBlocoChecklist(List<BlocoChecklist> listBlocoChecklist) {
		this.listBlocoChecklist = listBlocoChecklist;
	}
	
	@Autowired
	public void setBlocoChecklistBO(IBlocoChecklistBO blocoChecklistBO) {
		this.blocoChecklistBO = blocoChecklistBO;
		modoInicio();
	}
	

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
	
	
}