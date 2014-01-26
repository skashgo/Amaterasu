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

import br.gov.caixa.siiac.bo.ITipoGarantiaBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.TipoGarantia;
import br.gov.caixa.siiac.util.LogCEF;

@Service()
@Scope("request")
public class TipoGarantiaMB extends AbstractMB {

	private TipoGarantia tipoGarantia = new TipoGarantia();;
	private List<TipoGarantia> listTipoGarantia = new ArrayList<TipoGarantia>();
	private ITipoGarantiaBO tipoGarantiaBO;

	/**
	 * Filtro
	 */
	private String pesquisaString;
	private Boolean pesquisaMostraInativos;
	
	public TipoGarantiaMB() {
	}

	//Methods Actions
	public Boolean doValida() {
		//valida se foi inserido todos os dados no formulario
		if (tipoGarantia == null || tipoGarantia.getNoTipoGarantia() == null || tipoGarantia.getNoTipoGarantia().trim().equals("") && !tipoGarantia.getIcAtivo() == false) {
			error(MSGS, MN002);
			warn( "Campo NOME Obrigatório.");
			return false;
		}
		// verifica se o bloco esta inativo
		if (isModoAltera() && !tipoGarantiaBO.isAtivo(tipoGarantia)) {
			error(MSGS, MN013);
			return false;
		}
		try {
			if (tipoGarantiaBO.exist(tipoGarantia)) {
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
				tipoGarantiaBO.merge(tipoGarantia);
				if (isModoAltera()) {
					info(MSGS, MN008);
				} else {
					info(MSGS, MN007);
				}
			} catch (Exception e) {
				error("msgtipogarantia", "error_save");
				LogCEF.error(e.getMessage());
			}
			modoInicio();
		}
	}

	public void doFiltrar(ActionEvent evt) {
		List<TipoGarantia> lista = atualizaList();
		if (lista == null || lista.isEmpty()) {
			warn(MSGS, MN016);
		}
		setListTipoGarantia(lista);
	}

	public void doDesabilitar(ActionEvent evt) {
		tipoGarantia = (TipoGarantia) FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap().get("row");
		try {
			tipoGarantiaBO.ativaInativa(tipoGarantia);
			if (tipoGarantia.getIcAtivo()) {
				info(MSGS, MN012);
			} else {
				info(MSGS, MN011);
			}
		} catch (Exception e) {
			error("msgtipogarantia", "error_save");
			LogCEF.error(e.getMessage());
		}
		modoInicio();
		setListTipoGarantia(atualizaList());
	}

	public void doDesabilitarNoButton(ActionEvent evt) {
		try {
			tipoGarantiaBO.ativaInativa(tipoGarantia);
			if (tipoGarantia.getIcAtivo()) {
				info(MSGS, MN012);
			} else {
				info(MSGS, MN011);
			}
		} catch (Exception e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
			modoInicio();
		}
	}

	public void doAtualizar(ActionEvent evt) {
		tipoGarantia = (TipoGarantia) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		setModoAltera();
	}

	public void doVisualizar(ActionEvent evt) {
		tipoGarantia = (TipoGarantia) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		setModoVisualiza();
	}

	public void doCadastrar(ActionEvent evt) {
		setModoCadastro();
		tipoGarantia = new TipoGarantia();
	}

	public void doCancelar(ActionEvent evt) {
		warn(MSGS, MN003);
		modoInicio();
	}

	private void modoInicio() {
		setModoInicio();
		tipoGarantia = new TipoGarantia();
		pesquisaString = "";
		pesquisaMostraInativos = false;
		setListTipoGarantia(atualizaList());
	}
	
	/**
	 * Atualiza a lista da tabela.
	 * @return
	 */
	private List<TipoGarantia> atualizaList() {
		try {
			return tipoGarantiaBO.getListFiltro(pesquisaString, pesquisaMostraInativos);
		} catch (SIIACException e) {
			warn(MSGS, MN016);
			LogCEF.error(e.getMessage());
		}
		return new ArrayList<TipoGarantia>();
	}

	public List<SelectItem> getSelectItemIcAtivo() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem(true, "Ativos"));
		list.add(new SelectItem(false, "Inativos"));
		return list;
	}

	// Methods Show

	public boolean isShowPanelCadastro() {
		return isModoCadastro() || isModoAltera();
	}
	
	public boolean isShowBreadNovo() {
		return isModoCadastro();
	}	

	public boolean isShowPanelFiltro() {
		return isModoInicio();
	}

	public boolean isShowPanelLista() {
		return isModoInicio();
	}

	public boolean isShowAlterar() {
		return isModoAltera();
	}
	
	public boolean isShowCadastrar() {
		return isModoCadastro();
	}

	public boolean isShowButtonCadastro() {
		return isModoInicio();
	}

	public boolean isShowPanelVisualizacao() {
		return isModoVisualiza();
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
	/**
	 * Valida se deve ser mostrado o painel com o botao para chamar a tela de novo cadastro.
	 * @return
	 */
	public boolean isShowPanelButtonCadastrar() {
		return isModoInicio();
	}

	// Getters and Setters
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

	public TipoGarantia getTipoGarantia() {
		return this.tipoGarantia;
	}

	public void setTipoGarantia(TipoGarantia tipoGarantia) {
		this.tipoGarantia = tipoGarantia;
	}

	public List<TipoGarantia> getListTipoGarantia() {
		return this.listTipoGarantia;
	}

	public void setListTipoGarantia(List<TipoGarantia> listTipoGarantia) {
		this.listTipoGarantia = listTipoGarantia;
	}

	@Autowired
	public void setTipoGarantiaBO(ITipoGarantiaBO tipoGarantiaBO) {
		this.tipoGarantiaBO = tipoGarantiaBO;
		modoInicio();
	}

}