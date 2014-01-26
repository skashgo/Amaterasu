/**
 * Copyright (c) 2009-2011 Caixa Econ�mica Federal. Todos os direitos
 * reservados.
 * 
 * Caixa Econ�mica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade
 * 
 * Este programa de computador foi desenvolvido sob demanda da CAIXA e est�
 * protegido por leis de direitos autorais e tratados internacionais. As
 * condi��es de c�pia e utiliza��o do todo ou partes dependem de autoriza��o da
 * empresa. C�pias n�o s�o permitidas sem expressa autoriza��o. N�o pode ser
 * comercializado ou utilizado para prop�sitos particulares.
 * 
 * Uso exclusivo da Caixa Econ�mica Federal. A reprodu��o ou distribui��o n�o
 * autorizada deste programa ou de parte dele, resultar� em puni��es civis e
 * criminais e os infratores incorrem em san��es previstas na legisla��o em
 * vigor.
 * 
 * Hist�rico do Subversion:
 * 
 * LastChangedRevision:  
 * LastChangedBy:  
 * LastChangedDate: 
 * 
 * HeadURL: 
 * 
 */
package br.gov.caixa.siiac.controller.listeners;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import br.gov.caixa.siiac.controller.aop.ControlaConcorrenciaVerificacaoProdutoAOP;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.util.ServicoVerificacaoProdutoUsados;

/**
 * O objetivo desta classe � controlar o acesso a funcionalidade de servi�o de verifica��o contrato/produto.<br>
 * Pois se tal controle n�o existir, v�rios usu�rio podem usar o mesmo servi�o verifi��o, o que<br>
 * tornaria o processo incosistente perante cada usu�rio. Essa classe permite que somente um usu�rio<br>
 * por vez acesse um determinado tipo de servi�o de verifica��o. Para que outro usu�rio use, o usu�rio atual<br>
 * deve liberar o acesso.<br>
 * <br>
 * Quando o usu�rio acessa a url "/jsp/verificacao/efetua_verificacao.xhtml" para um determinado servi�o verifica��o <br>
 * e nenhum outro usu�rio esta usando este mesmo servi�o, este servi�o fica bloqueado para outros usu�rio. Caso outro<br>
 * tente acessar este mesmo servi�o receber� uma mensagem de aviso. Quando o usu�rio deixa a p�gina de servi�o verifica��o<br>
 * o respectivo servi�o verifica��o � liberado. Caso o usu�rio feche o browser ou deixe a sess�o expirar, o servi�o verifica��o<br>
 * tamb�m � liberado ao t�rmindo da sess�o.
 * 
 * Esta classe � um listener de fases do JSF, deve ser registrada no arquivo faces-config<br>
 * para que seja reconhecido.<br>
 * <br>
 * Possue m�todos que s�o executados antes e depois da fase PhaseId.RENDER_RESPONSE. <br>
 * Somente o m�todo executado antes da fase � usado (beforePhase). Esta fase � especial, pois<br>
 * nela � poss�vel obter a URL destino correta ( e n�o postergada como em outros locais), mesmo
 * sem o uso do redirect.
 *  
 *  @see ServicoVerificacaoProdutoUsados
 *  @see SessionListener
 *  @see ControlaConcorrenciaVerificacaoProdutoAOP
 * @author Caixa Econ�mica Federal
 *
 */
public class PhaseListenerControllVerificacaoContrato implements PhaseListener
{
	
	/**
	 * M�todo executado depois da fase PhaseId.RENDER_RESPONSE, n�o implementado nesta classe.
	 * 
	 * @param event - Objeto referente ao contexto JSF, cont�m refer�ncia para o FacesContext.
	 */
	public void afterPhase(PhaseEvent event)
	{
		
	
	}
	
	/**
	 * 
	 * M�todo executado antes da fase PhaseId.RENDER_RESPONSE.
	 * 
	 * @see PhaseListenerControllVerificacaoContrato m�todo "doControllEfetuaVerificacao"
	 * @param event - Objeto referente ao contexto JSF, cont�m refer�ncia para o FacesContext.
	 */
	public void beforePhase(PhaseEvent event) 
	{
		doControllEfetuaVerificacao(event);
	}
	
	/**
	 * M�todo que retorna a fase desejada a ser monitorada.
	 */
	public PhaseId getPhaseId() 
	{
		return PhaseId.RENDER_RESPONSE;
	}
	
	
	/**
	 *M�todo que verifica se a URL "/jsp/verificacao/efetua_verificacao.xhtml" esta sendo acessada,
	 *<br> caso esteja, verifica se o acesso pode ser realizado, liberando o acesso ou mostrando mensagem de erro<br>
	 *atrav�s de uma mensagem FacesMessage, e redirecionando o  usu�rio para regra de navega��o "toEscolheVerificacao".<br>
	 *<br>
	 *Caso a respectiva URL n�o esteja sendo acessada, ou seja, o usu�rio navegou para outra p�gina, � verificado se<br>
	 *existe bloqueio a ser liberado, caso exista, este � liberado.
	 * 
	 * @param event - Objeto referente ao contexto JSF, cont�m refer�ncia para o FacesContext.
	 */
	public void doControllEfetuaVerificacao(PhaseEvent event)
	{
		FacesContext facesContext = event.getFacesContext();
		String currentPage = facesContext.getViewRoot().getViewId();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		NavigationHandler nh = facesContext.getApplication().getNavigationHandler();    

		//N�o trata requi��es que n�o sejam para/jsp, ou seja requisi��es para /css, /js etc
		if (!currentPage.contains("jsp"))
			return;
		

		//Verifica se o usu�rio esta tentando navegar para a URL em quest�o.
		//Se sim, obtem o bloqeio e libera o acesso, caso contr�rio (servi�o ja em uso)
		//mostra erro e redireciona para p�gina de escolha do servi�o
		if (currentPage.contains("/jsp/verificacao/efetua_verificacao.xhtml"))
		{
			//obtem o objeto servi�o veririca��o selecionado da grid de escolha e verifica��o.
			VerificacaoContratoVO v = (VerificacaoContratoVO) session.getAttribute("verificacaoContratoVO");
			boolean ret=ServicoVerificacaoProdutoUsados.getLock(v.getNuVerificacaoContrato(), SegurancaUsuario.getInstance().getUsuario().getMatricula(),session.getId());

			if (ret)
			{
				//insere URL na session para depois comparar se o usu�rio navegou para outra p�gina
				session.setAttribute(ServicoVerificacaoProdutoUsados.URL_SESSION_ESCOLHE_VERIFICACAO,"/jsp/verificacao/efetua_verificacao.xhtml");
			}
			else
			{
				nh.handleNavigation(facesContext, null, "toEscolheVerificacao");
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"N\u00E3o \u00E9 poss\u00EDvel acessar o recurso solicitado, pois esta em uso por outro usu\u00E1rio.","N\u00E3o \u00E9 poss\u00EDvel acessar o recurso solicitado, pois esta em uso por outro usu\u00E1rio."));	
			}
		}
		else
		{
			//Validando se a sessão não é NULL para evitar NPE
			if (session != null)
			{
				Object ourl=session.getAttribute(ServicoVerificacaoProdutoUsados.URL_SESSION_ESCOLHE_VERIFICACAO);
				String url="";
				
				if (ourl==null)
					return;
				else
				{
					url=ourl.toString();
				}
				
				//Se existe bloqueio e o usu�rio navegou para outra p�gina, libera o bloqueio
				if (ServicoVerificacaoProdutoUsados.isLock(session.getId())
						&& !currentPage.contains(url))
				{
					session.removeAttribute(ServicoVerificacaoProdutoUsados.URL_SESSION_ESCOLHE_VERIFICACAO);
					ServicoVerificacaoProdutoUsados.releasedlock(session.getId());
				}
			} else {
				return;
			}
		}
	}
}
