package br.gov.caixa.siiac.view.mb;

import br.gov.caixa.siiac.model.domain.CategoriaProduto;
import br.gov.caixa.siiac.bo.ICategoriaProdutoBO;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service()
@Scope("request")
public class CategoriaProdutoMB extends AbstractMB {
	
	private CategoriaProduto categoriaProduto = new CategoriaProduto();
	private List<CategoriaProduto> listCategoriaProduto = new ArrayList<CategoriaProduto>();
	private ICategoriaProdutoBO categoriaProdutoBO;
	private boolean pesquisaMostraInativos;
	private String pesquisaString;
	
	public CategoriaProdutoMB() {
	}
	
	//Methods Actions
	public Boolean doValidaCadastro() {
		boolean valida = true;
		if (categoriaProduto.getNoCategoriaProduto() == null || categoriaProduto.getNoCategoriaProduto().equals("")) {
			error(MSGS, MN002);
			warn("Campo NOME Obrigatório.");
			valida = false;
		}
		if (categoriaProdutoBO.exist(categoriaProduto)) {
			error(MSGS, MN015);
			return false;
		}
		return valida;
	}
	
	public void doGravaNovo(ActionEvent evt) {
		categoriaProduto.setNoCategoriaProduto(categoriaProduto.getNoCategoriaProduto().trim());
		categoriaProduto.setSgCategoria(categoriaProduto.getSgCategoria().trim());
		if (doValidaCadastro()) {
			try {
				categoriaProdutoBO.save(categoriaProduto);
				info(MSGS, MN007);
				modoInicio();
			} catch (Exception e) {
				error(MSGS, MN002);
			}
		}
	}
	
	public Boolean doValidaAlterar() {
		boolean valida = true;
		if (categoriaProduto.getNoCategoriaProduto() == null || categoriaProduto.getNoCategoriaProduto().equals("")) {
			warn("Campo NOME Obrigatório.");
			valida = false;
		}
		//verifica se a categoria esta inativa
		if (isModoAltera() && !categoriaProduto.isIcAtivo()) {
			error(MSGS, MN013);
			return false;
		}
		if (categoriaProdutoBO.exist(categoriaProduto)) {
			error(MSGS, MN015);
			return false;
		}
		return valida;
	}
	
	public void doGravaAlteracao(ActionEvent evt) {
		categoriaProduto.setNoCategoriaProduto(categoriaProduto.getNoCategoriaProduto().trim());
		categoriaProduto.setSgCategoria(categoriaProduto.getSgCategoria().trim());
		if (doValidaAlterar()) {
			try {
				categoriaProdutoBO.save(categoriaProduto);
				info(MSGS, MN008);
				modoInicio();
			} catch (Exception e) {
				error(MSGS, MN002);
			}
		}
	}
	
	public void doFiltrar(ActionEvent evt) {
		atualizaList();
		if (listCategoriaProduto.size() == 0) {
			warn(MSGS, MN016);
		}
	}
	
	public void doInativarAtivar(ActionEvent evt) {
		categoriaProduto = (CategoriaProduto) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		try {
			if (categoriaProduto.isIcAtivo()) {
				categoriaProduto.setIcAtivo(false);
				categoriaProdutoBO.save(categoriaProduto);
				info(MSGS, MN011);
			} else {
				categoriaProduto.setIcAtivo(true);
				categoriaProdutoBO.save(categoriaProduto);
				info(MSGS, MN012);
			}
		} catch (Exception e) {
			error(MSGS, MN002);
		}
		modoInicio();
	}
	
	public void doAlterar(ActionEvent evt) {
		categoriaProduto = (CategoriaProduto) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		if (categoriaProduto.getNoCategoriaProduto() != null) {
			categoriaProduto.setNoCategoriaProduto(categoriaProduto.getNoCategoriaProduto().trim());
		}
		if (categoriaProduto.getSgCategoria() != null) {
			categoriaProduto.setSgCategoria(categoriaProduto.getSgCategoria().trim());
		}
		setModoAltera();
	}
	
	public void doVisualizar(ActionEvent evt) {
		categoriaProduto = (CategoriaProduto) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		setModoVisualiza();
	}
	
	public void doCadastrar(ActionEvent evt) {
		setModoCadastro();
		categoriaProduto = new CategoriaProduto();
	}
	
	public void doCancelar(ActionEvent evt) {
		warn(MSGS, MN003);
		modoInicio();
	}
	
	private void atualizaList() {
		try {
			listCategoriaProduto = categoriaProdutoBO.getListFiltro(pesquisaString, pesquisaMostraInativos);
		} catch (Exception e) {
			warn(MSGS, MN016);
		}
	}
	
	public void modoInicio() {
		setModoInicio();
		categoriaProduto = new CategoriaProduto();
		pesquisaString = "";
		pesquisaMostraInativos = false;
		atualizaList();
	}
	
	//Methods Shows
	
	public boolean isShowPanelCadastro() {
		return isModoCadastro();
	}
	
	public boolean isShowPanelAltera() {
		return isModoAltera();
	}
	
	public boolean isShowPanelFiltro() {
		return isModoInicio();
	}
	
	public boolean isShowPanelLista() {
		return isModoInicio();
	}
	
	public boolean isShowButtonCadastro() {
		return isModoInicio();
	}
	
	public boolean isShowPanelVisualizacao() {
		return isModoVisualiza();
	}
	
	//Methods Disableds
	
	//Methods Formatters
	
	//Getters and Setters
	public CategoriaProduto getCategoriaProduto() {
		return this.categoriaProduto;
	}
	
	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}
	
	public List<CategoriaProduto> getListCategoriaProduto() {
		return this.listCategoriaProduto;
	}
	
	public void setListCategoriaProduto(List<CategoriaProduto> listCategoriaProduto) {
		this.listCategoriaProduto = listCategoriaProduto;
	}
	
	public boolean isPesquisaMostraInativos() {
		return pesquisaMostraInativos;
	}
	
	public void setPesquisaMostraInativos(boolean pesquisaMostraInativos) {
		this.pesquisaMostraInativos = pesquisaMostraInativos;
	}
	
	public String getPesquisaString() {
		return pesquisaString;
	}
	
	public void setPesquisaString(String pesquisaString) {
		this.pesquisaString = pesquisaString;
	}
	
	@Autowired
	public void setCategoriaProdutoBO(ICategoriaProdutoBO categoriaProdutoBO) {
		this.categoriaProdutoBO = categoriaProdutoBO;
		modoInicio();
	}
	
}