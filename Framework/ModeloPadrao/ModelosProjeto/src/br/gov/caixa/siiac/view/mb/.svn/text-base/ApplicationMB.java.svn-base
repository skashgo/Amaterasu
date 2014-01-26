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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.IUsuarioBO;
import br.gov.caixa.siiac.bo.ListaPerfil;
import br.gov.caixa.siiac.bo.impl.MatrizAcessoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.util.LogCEF;

/**
 * @author GisConsult
 *
 */
@Service()
@Scope("request")
public class ApplicationMB extends AbstractMB {
	private static final long serialVersionUID = -8418408093838990492L;
	private static String contexto = "/SIIAC";
	private static final String GARANTIA = "garantia";
	private static final String ACTION = "action";
	private static final String CARREGA_CONTRATO = "carregaContrato";
	private static final String CARREGA_GARANTIA = "carregaGarantia";
	public boolean mostraLinkAgenda = false;
	
	@Autowired
	private IUsuarioBO usuarioBO;
	
	/**
	 * ApplicationMB
	 */
	public ApplicationMB() {
	}
	
	/**
	 * setContexto
	 * 
	 * @param contexto
	 */
	public void setContexto(String contexto) {
		ApplicationMB.contexto = contexto;
	}
	
	/**
	 * getContexto
	 * 
	 * @return
	 */
	public String getContexto() {
		return contexto;
	}
	
	/**
	 * getNomeUsuario
	 * 
	 * @return
	 */
	public String getNomeUsuario() {
		return getUsuarioLogado().getNome();
	}
	
	/**
	 * getPerfilUsuario
	 * 
	 * @return
	 */
	public String getPerfilUsuario() {
		if (isGestorSistema())
			return ListaPerfil.PERFIL_NOME_GESTOR_SISTEMA;
		else if (isAuditor())
			return ListaPerfil.PERFIL_NOME_AUDITOR;
		else if (isGestorProduto())
			return ListaPerfil.PERFIL_NOME_GESTOR_PRODUTO;
		else if (isRegionalConformidade())
			return ListaPerfil.PERFIL_NOME_REGIONAL_CONFORMIDADE;
		else if (isUnidadeAtendimento())
			return ListaPerfil.PERFIL_NOME_UNIDADE_ATENDIMENTO;
		else if (isVerificadorConformidade())
			return ListaPerfil.PERFIL_NOME_VERIFICADOR_CONFORMIDADE;
		else if (isCentralAtendimentoSuporte())
			return ListaPerfil.PERFIL_NOME_CENTRAL_DE_ATENDIMENTO_E_SUPORTE;
		else if (isVerificadorRegional())
			return ListaPerfil.PERFIL_NOME_VERIFICADOR_REGIONAL;
		else if (isVerificadorNacional())
			return ListaPerfil.PERFIL_NOME_VERIFICADOR_NACIONAL;
		return "";
	}
	
	/**
	 * getUnidade
	 * 
	 * @return
	 */
	public String getUnidade() {
		return ConvertUtils.padZerosLeft(String.valueOf(getUsuarioLogado().getUnidade()), 4);
	}
	
	/**
	 * getCidade
	 * 
	 * @return
	 */
	public String getCidade() {
		return "";
	}
	
	/**
	 * getHora
	 * 
	 * @return
	 */
	public String getHora() {
		Locale brasil = new Locale("pt", "BR");
		return new SimpleDateFormat("HH:mm").format(Calendar.getInstance(brasil).getTime());
	}
	
	/**
	 * getSaudacao
	 * 
	 * @return
	 */
	public String getSaudacao() {
		return "Bem-vindo " + getUsuarioLogado().getNome();
		
	}
	
	/**
	 * getData
	 * 
	 * @return
	 */
	public String getData() {
		Locale brasil = new Locale("pt", "BR");
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, brasil);
		return df.format(new Date());
	}
	
	/**
	 * @return the current year as String
	 */
	public String getYear() {
		return new SimpleDateFormat("yyyy").format(new Date());
	}
	
	/**
	 * logout
	 * 
	 * @return
	 */
	public String logout() {
		getHttpSession().invalidate();
		
		if (getHttpSession() != null) {
			getHttpSession().invalidate();
		}
		
		try {
			getResponse().sendRedirect(getRequest().getContextPath() + "/jsp/index.jsf");
		} catch (IOException e) {
			LogCEF.error(e.getMessage());
		}
		
		return "toLogin";
	}
	
	/**
	 * Métodos de acesso às páginas através do menu.
	 * 
	 */
	public String toApontamento() {
		getRequestMap().remove("apontamentoMB");
		return "toApontamento";
	}
	
	public String toRelVerificacao() {
		getRequestMap().remove("relatorioVerificacaoMB");
		return "toRelVerificacao";
	}
	
	public String toVerificacaoPreventiva() {
		getRequestMap().remove("verificacaoPreventivaMB");
		return "toVerificacaoPreventiva";	
	}
	
	public String toLogParecer() {
		getRequestMap().remove("logParecerMB");
		return "toLogParecer";
	}
	
	public String toBlocoChecklist() {
		getRequestMap().remove("blocoChecklistMB");
		return "toBlocoChecklist";
	}
	
	public String toCategoriaproduto() {
		getRequestMap().remove("categoriaProdutoMB");
		return "toCategoriaproduto";
	}
	
	public String toChecklist() {
		getRequestMap().remove("checklistMB");
		return "toChecklist";
	}
	
	public String toRelatorioChecklist() {
		getRequestMap().remove("checklistMB");
		return "toRelatorioChecklist";
	}
	
	public String toContrato() {
		getRequestMap().remove("contratoMB");
		limpaSessao();
		return "toContrato";
	}
	
	public String toGarantia() {
		getRequestMap().remove("garantiaMB");
		limpaSessao();
		return "toGarantia";
	}
	
	public String toTemplateParecer() {
		getRequestMap().remove("templateParecerMB");
		limpaSessao();
		return "toTemplateParecer";
	}
	
	public String toTemplateNotificacao() {
		getRequestMap().remove("templateNotificacaoMB");
		limpaSessao();
		return "toTemplateNotificacao";
	}
	
	public String toItemVerificacao() {
		getRequestMap().remove("itemVerificacaoMB");
		return "toItemVerificacao";
	}
	
	public String toInformacao() {
		getRequestMap().remove("informacaoMB");
		return "toInformacao";
	}
	
	public String toPreferenciaUsuario() {
		getRequestMap().remove("preferenciaUsuarioMB");
		return "toPreferenciaUsuario";
	}
	
	public String toProduto() {
		getRequestMap().remove("produtoMB");
		return "toProduto";
	}
	
	public String toRestricao() {
		getRequestMap().remove("restricaoMB");
		return "toRestricao";
	}
	
	public String toServicoVerificacao() {
		getRequestMap().remove("servicoVerificacaoMB");
		return "toServicoVerificacao";
	}
	
	public String toTipoGarantia() {
		getRequestMap().remove("tipoGarantiaMB");
		return "toTipoGarantia";
	}
	
	public String toUsuario() {
		getRequestMap().remove("usuarioMB");
		return "toUsuario";
	}
	
	public String toIndex() {
		if (isRegionalConformidade() || isVerificadorConformidade() || isVerificadorNacional() || isVerificadorRegional()) {
			getRequestMap().remove("agendaMB");
			return "toAgenda";
		}
		return "toIndex";
	}
	
	/**
	 * Métodos que validam a renderização dos menus.
	 */
	
	public boolean isShowMenuApontamento() {
		return MatrizAcessoBO.acessoModuloMantemApontamento(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuRelVerificacao() {
		return MatrizAcessoBO.acessoModuloConsultaRelatorios(getNuPerfil(), MatrizAcessoBO.ACOMPANHAMENTO_VERIFICACOES, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuBlocoChecklist() {
		return MatrizAcessoBO.acessoModuloMantemBlocoChecklist(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuCategoriaProduto() {
		return isGestorSistema();
	}
	
	public boolean isShowMenuChecklist() {
		return MatrizAcessoBO.acessoModuloMantemChecklist(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuRelatorioChecklist() {
		return MatrizAcessoBO.acessoModuloConsultaRelatorios(getNuPerfil(), MatrizAcessoBO.ESTRUTURA_COMPLETA_CHECKLIST, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuContrato() {
		return MatrizAcessoBO.acessoModuloMantemContrato(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuGarantia() {
		return MatrizAcessoBO.acessoModuloMantemGarantia(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuItemVerificacao() {
		return MatrizAcessoBO.acessoModuloMantemItemVerificacao(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuPreferencias() {
		try {
			return MatrizAcessoBO.acessoModuloMantemPreferencias(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA) && usuarioBO.seExisteUsuario(getUsuarioLogado().getMatricula());
		} catch (SIIACException e) {
			LogCEF.error("Erro ao validar acesso a tela de preferencia. " + e.getMessage());
		}
		return false;
	}
	
	public boolean isShowMenuProduto() {
		return MatrizAcessoBO.acessoModuloMantemProduto(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuRestricao() {
		return MatrizAcessoBO.acessoModuloMantemRestricao(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuServicoVerificacao() {
		return MatrizAcessoBO.acessoModuloMantemServicoVerificacao(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuTipoGarantia() {
		return MatrizAcessoBO.acessoModuloMantemTipoGarantia(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuUsuario() {
		return MatrizAcessoBO.acessoModuloMantemUsuario(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuTemplateParecer() {
		return MatrizAcessoBO.acessoModuloMantemTemplateParecer(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuTemplateNotificacao() {
		return MatrizAcessoBO.acessoModuloMantemTemplateNotificacao(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuInformacao() {
		return MatrizAcessoBO.acessoModuloMantemInformacao(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuVerificacaoPreventiva() {
		return MatrizAcessoBO.acessoModuloConsultaVerificacao(getNuPerfil(), MatrizAcessoBO.CONSULTA_VERIFICACAO, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	public boolean isShowMenuLogParecer() {
		return MatrizAcessoBO.acessoModuloMantemLogParecer(getNuPerfil(), MatrizAcessoBO.ACAO_PERFIL, MatrizAcessoBO.TIPO_ACAO_VISUALIZA);
	}
	
	private void limpaSessao() {
		getHttpSession().setAttribute(GARANTIA, null);
		getHttpSession().setAttribute(ACTION, null);
		getHttpSession().setAttribute(CARREGA_CONTRATO, null);
		getHttpSession().setAttribute(CARREGA_GARANTIA, null);
	}
	
	public boolean isMostraLinkAgenda() {
		if (getPerfilUsuario().equals(ListaPerfil.PERFIL_NOME_VERIFICADOR_CONFORMIDADE) ||
				getPerfilUsuario().equals(ListaPerfil.PERFIL_NOME_REGIONAL_CONFORMIDADE) ||
				getPerfilUsuario().equals(ListaPerfil.PERFIL_NOME_VERIFICADOR_NACIONAL) ||
				getPerfilUsuario().equals(ListaPerfil.PERFIL_NOME_VERIFICADOR_REGIONAL) )
		{
			mostraLinkAgenda = true;
		} else {
			mostraLinkAgenda = false;
		}
		return mostraLinkAgenda;
	}

	public void setMostraLinkAgenda(boolean mostraLinkAgenda) {
		this.mostraLinkAgenda = mostraLinkAgenda;
	}
	
	public Short getNuPerfil() {
		if (getUsuarioLogado() != null && getUsuarioLogado().getPerfis() != null) {
			return getUsuarioLogado().getPerfis().get(0);
		}
		return 0;
	}
}
