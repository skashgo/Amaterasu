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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.taglibs.standard.tag.common.core.SetSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IInformacaoBO;
import br.gov.caixa.siiac.bo.impl.MatrizAcessoBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.Informacao;
import br.gov.caixa.siiac.model.domain.TemplateNotificacao;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */

@Service()
@Scope("request")
public class InformacaoMB extends AbstractMB {

	private Informacao informacao = new Informacao();
	private List<Informacao> listInformacao = new ArrayList<Informacao>();
	private List<Informacao> listLogin = new ArrayList<Informacao>();
	
	private String pesquisaSimples;
	
	private String pesquisaAssunto;
	private Date pesquisaDtInicio;
	private Date pesquisaDtFim;
	
	private Date dataFim;
	private String textoDescricao ="";
	private IInformacaoBO informacaoBO;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public Date dataSist = new Date();
	public Date dataAltIni = new Date();
	public Date dataAltFim = new Date();
	
	public InformacaoMB() {
	}
	
	@PostConstruct
	public void init() throws SIIACException
	{
		setFiltro();
	}
	
	/**
	 * Pega o Filtro na sessão e seta as variaveis da view<br/>
	 * com seus respectivos valores que estavam guardados<br/>
	 * no filtro da sessão. 
	 * @see AbstractMB.getFilteriInSession();
	 */
	private void setFiltro(){
		//Pega o filtro na sessão.
		getFilterInSession(this.getClass());
		
		pesquisaAssunto = getFilterBase().getString("pesquisaAssunto");
		pesquisaDtInicio = getFilterBase().getDate("pesquisaDtInicio");
		pesquisaDtFim = getFilterBase().getDate("pesquisaDtFim");
		pesquisaSimples = getFilterBase().getString("pesquisaSimples");
		
		if (getFilterBase().isModoAvancado())
		{
			setModoFiltro();
		} else {
			setModoInicio();
		}
		
		setListInformacao(doBuscaRegistros());
	}
	
	public boolean doValida() {

		if(informacao == null || informacao.getNoInformacao().trim() == null || informacao.getNoInformacao().trim().equals("")) {
			error(MSGS, MN002);
			warn( "Campo ASSUNTO Obrigatório.");
			return false;
		}
		
		if(informacao == null || informacao.getDtInicio() == null || informacao.getDtInicio().equals("")) {
			error(MSGS, MN002);
			warn( "Campo DATA INÍCIO Obrigatório.");
			return false;
		}
		
		while(informacao.getDeInformacao().contains("  ")) {
			informacao.getDeInformacao().replace("  ", " ");
		}
		
		if(informacao == null || informacao.getDeInformacao().trim() == null || informacao.getDeInformacao().trim().equals("")) {
			error(MSGS, MN002);
			warn( "Campo TEXTO Obrigatório.");
			return false;
		}
		
//		if(!isModoAltera()) {
//			if(informacao == null || Utilities.isBeforeAndNotEquals(informacao.getDtInicio(), dataSist)) {
//				error(MSGS, MN002);
//				warn(" Campo DATA INÍCIO menor que DATA ATUAL.");
//				return false;
//			}			
//		} else {
//			if(informacao == null || Utilities.isBeforeAndNotEquals(informacao.getDtInicio(), dataAltIni)) {
//				error(MSGS, MN002);
//				warn(" Campo DATA INICIO menor que data já cadastrada.");
//				return false;
//			}
			
			if(informacao.getDtFim() != null) {
				if(informacao == null || Utilities.isBeforeAndNotEquals(informacao.getDtFim(), dataSist)) {
					error(MSGS, MN002);
					warn(" Campo DATA FIM menor que DATA ATUAL.");
					return false;
				}			
			}			
		

		if(informacao.getDtFim() != null) {
			if(informacao == null || informacao.getDtInicio().getTime() > informacao.getDtFim().getTime()) {
				error(MSGS, MN002);
				warn(" Campo DATA INÍCIO deve ser menor que Campo DATA FIM.");
				return false;
			}
		}
		
		try {
			if (informacaoBO.exist(informacao)) {
				error(MSGS, MN033);
				return false;
			}
		} catch (SIIACException e) {
			error(MSGS, MN002);
			LogCEF.error(e.getMessage());
			return false;
		}
		return true;		
	}

	public boolean getShowConsultarSimples() {
		return isModoInicio();
	}
	
	public boolean isShowButtonNovo() {
		
		return isModoInicio() && MatrizAcessoBO.acessoModuloMantemInformacao(getPerifl(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_INSERE);
	}
	
	public boolean isShowButtonNovoAvancado() {
		return isModoFiltro() && MatrizAcessoBO.acessoModuloMantemInformacao(getPerifl(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_INSERE);
	}
	
	public boolean isShowButtonAltera() {
		return (isModoInicio() || isModoFiltro()) && MatrizAcessoBO.acessoModuloMantemInformacao(getPerifl(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_ALTERA);
	}
	
	public boolean isShowButtonExclui() {
		return (isModoInicio() || isModoFiltro()) && MatrizAcessoBO.acessoModuloMantemInformacao(getPerifl(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_EXCLUI);
	}
	
	private Short getPerifl() {
		Short nuPerfil = null;
		if(getUsuarioLogado() != null){
			nuPerfil = getUsuarioLogado().getPerfis().get(0);
		}
		return nuPerfil;
	}

	public boolean getShowConsultarAvancado() {
		return isModoFiltro();
	}
	
	public void doConsultarAvancado(ActionEvent evt) {
		informacao = new Informacao();
		setModoFiltro();
	}
	
	public void doConsultarSimples(ActionEvent evt) {
		setModoInicio();
	}	
	
	// Methods Show
	public boolean isShowPanelCadastro() {
		return isModoCadastro() || isModoAltera();
	}
	
	public boolean isShowBreadNovo() {
		return isModoCadastro();
	}	

	public boolean isShowPanelFiltro() {
		return isModoInicio() || isModoFiltro();
	}

	public boolean isShowPanelLista() {
		return isModoInicio() || isModoFiltro();
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
	
	public void doVisualizar(ActionEvent evt) {
		informacao = (Informacao) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		setModoVisualiza();
	}

	public void doVisualizaModal(ActionEvent evt) {
		textoDescricao = getData() + ", " + getHora() + "<br/><br/>" + informacao.getDeInformacao();
	}	
	
	public void doAtualizar(ActionEvent evt) {
		informacao = (Informacao) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		dataAltIni = informacao.getDtInicio();
		dataAltFim = informacao.getDtFim();
		setModoAltera();
	}
	
	public void doCadastrar(ActionEvent evt) {
		setModoCadastro();
		informacao = new Informacao();
	}

	public void doCancelar(ActionEvent evt) {
		setFiltro();
		warn(MSGS, MN003);		
		modoInicio();
	}
	
	private List<Informacao> atualizaListLogin() {
		
		try {
			return informacaoBO.getListLogin();
		} catch (SIIACException e) {
			warn(MSGS, MN016);
			LogCEF.error(e.getMessage());
		}
		return new ArrayList<Informacao>();
	}	

	private void modoInicio() {
		informacao = new Informacao();
		setListLogin(atualizaListLogin()); 
	}	
	
	public void doDesabilitar(ActionEvent evt) {
		informacao = (Informacao) FacesContext.getCurrentInstance()
				.getExternalContext().getRequestMap().get("row");
		try {
			informacaoBO.exclui(informacao);
			info(MSGS, MN009);
		} catch (Exception e) {
			error("msgInformacao", "error_delete");
			LogCEF.error(e.getMessage());
		}
		
		setFiltro();
	}	
	
	public void doLimpar(ActionEvent evt)
	{
		pesquisaSimples = "";
		pesquisaAssunto = "";
		pesquisaDtInicio = null;
		pesquisaDtFim = null;
		informacao = new Informacao();
		
		//limpa os filtros da sessão.
		getFilterBase().limparFiltros();
	}
	
	public void doGravar(ActionEvent evt) {
		String matricula = SegurancaUsuario.getInstance().getUsuario().getMatricula();
		if (doValida()) {
			try {
				if(!isModoAltera()) {
					informacao.setCoUsuarioResponsavel(matricula);
					informacaoBO.merge(informacao);
				} else {
					informacaoBO.merge(informacao);
				}
				if (isModoAltera()) {
					info(MSGS, MN008);
				} else {
					info(MSGS, MN007);
				}
				
				setFiltro();
			} catch (Exception e) {
				error("msgInformacao", "error_save");
				LogCEF.error(e.getMessage());
			}
			
		}
	}	
	
	public void doConsultarClick(ActionEvent evt) {
		
		List<Informacao> busca = doBuscaRegistros();
		
		if (busca.isEmpty()) {
			warn(MSGS, MN016);
		}
		
		setListInformacao(busca);
			
	}
	
	private void doLimparAvancado()
	{
		pesquisaAssunto = "";
		pesquisaDtInicio = null;
		pesquisaDtFim = null;
	}
	
	private void doLimparSimples()
	{
		pesquisaSimples = "";
	}
	
	/**
	 * Método responsável por realizar a busca dos registros
	 * @return
	 */
	public List<Informacao> doBuscaRegistros()
	{
		List<Informacao> busca = new ArrayList<Informacao>();
		
		//Limpa o filtro
		getFilterBase().limparFiltros();
		
		try {
			if (isModoInicio()) {
				
				doLimparAvancado();
				
				//Modo simples
				getFilterBase().setModoSimples();
				
				getFilterBase().addToFilter("pesquisaSimples", pesquisaSimples);
				
				//Define que o filtro base possui um filtro de template notificacao.
				getFilterBase().addToFilter("filtroTemplateNotificacao", true);
				
				//Coloca o filtro com os valores adicionados na sessão.
				putFilterBase(this.getClass());
				
				busca = informacaoBO.getListFiltro(getFilterBase());
				
			} else {
				
				doLimparSimples();
				
				//Modo avançado
				getFilterBase().setModoAvancado();

				getFilterBase().addToFilter("pesquisaDtInicio", pesquisaDtInicio);
				getFilterBase().addToFilter("pesquisaDtFim", pesquisaDtFim);
				getFilterBase().addToFilter("pesquisaAssunto", pesquisaAssunto);
						
				//Define que o filtro base possui um filtro de template notificacao.
				getFilterBase().addToFilter("filtroTemplateNotificacao", true);
				
				//Coloca o filtro com os valores adicionados na sessão.
				putFilterBase(this.getClass());
				
				busca = informacaoBO.getListFiltroAv(getFilterBase());
								
			}
		} catch (SIIACException ex) {
			LogCEF.error(ex);
		}
		
		return busca;
	}
	
	public List<Informacao> getListInformacao() {
		return listInformacao;
	}
	
	public void setListInformacao(List<Informacao> listInformacao) {
		this.listInformacao = listInformacao;
	}
	
	public List<Informacao> getListLogin() {
		return listLogin;
	}
	
	public void setListLogin(List<Informacao> listLogin) {
		this.listLogin = listLogin;
	}
	
	public String getPesquisaSimples() {
		return pesquisaSimples;
	}
	
	public void setPesquisaSimples(String pesquisaSimples) {
		this.pesquisaSimples = pesquisaSimples;
	}
	
	public Date getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public Informacao getInformacao() {
		return informacao;
	}
	
	public void setInformacao(Informacao informacao) {
		this.informacao = informacao;
	}
	
	/**
	 * @return the textoDescricao
	 */
	public String getTextoDescricao() {
		return textoDescricao;
	}

	/**
	 * @param textoDescricao the textoDescricao to set
	 */
	public void setTextoDescricao(String textoDescricao) {
		this.textoDescricao = textoDescricao;
	}

	public String getLabelConfirm() {
		if (isModoAltera()) {
			return getMessage(MSGS, MA003);
		} else {
			return getMessage(MSGS, MA002);
		}
	}
	
	public String getData() {
		Locale brasil = new Locale("pt", "BR");
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, brasil);
		return df.format(new Date());
	}
	
	public String getHora() {
		Locale brasil = new Locale("pt", "BR");
		return new SimpleDateFormat("HH:mm").format(Calendar.getInstance(brasil).getTime());
	}	
	
	public void doTextoDesc(ActionEvent evt) {
			informacao = (Informacao) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
			textoDescricao = getData() + ", " + getHora() + "<br/><br/>" + informacao.getDeInformacao();
	}
	
	public String getPegaValorText() {
		informacao = (Informacao) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		
		if(informacao.getNoInformacao().length() > 44) {
			return informacao.getNoInformacao().substring(0,44) + "...";
		}
		
		return informacao.getNoInformacao();
	}
	
	public String getLabelAlt() {
		informacao = (Informacao) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		dataSist.setHours(0);
		dataSist.setMinutes(0);
		dataSist.setSeconds(0);
		
			if(informacao.getDtInicio().before(dataSist)) {
				return getMessage(MSGS, MN032);
			}
		return "";
	}
	
	public String getLabelExcluir() {
		
		informacao = (Informacao) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");		
		
		dataSist.setHours(0);
		dataSist.setMinutes(0);
		dataSist.setSeconds(0);
		
			if(informacao.getDtInicio().after(dataSist)) {
				return getMessage(MSGS, MA005);
			} else {
				return getMessage(MSGS, MN032);
			}
	}
	
	public String getDataInicioFormat() {
		informacao = (Informacao) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
		String data = sdf.format(informacao.getDtInicio());
		
		return data;
	}
	
	public String getDataFimFormat() {
		
		if(informacao.getDtFim() != null) {
			informacao = (Informacao) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("row");
			String data = sdf.format(informacao.getDtFim());
			
			return data;
		} else {
			return "";
		}

	}	
	
	@Autowired
	public void setInformacaoBO(IInformacaoBO informacaoBO) {
		this.informacaoBO = informacaoBO;
		modoInicio();
	}

	public Date getPesquisaDtInicio() {
		return pesquisaDtInicio;
	}

	public void setPesquisaDtInicio(Date pesquisaDtInicio) {
		this.pesquisaDtInicio = pesquisaDtInicio;
	}

	public Date getPesquisaDtFim() {
		return pesquisaDtFim;
	}

	public void setPesquisaDtFim(Date pesquisaDtFim) {
		this.pesquisaDtFim = pesquisaDtFim;
	}

	public String getPesquisaAssunto() {
		return pesquisaAssunto;
	}

	public void setPesquisaAssunto(String pesquisaAssunto) {
		this.pesquisaAssunto = pesquisaAssunto;
	}	
}
