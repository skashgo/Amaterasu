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
package br.gov.caixa.siiac.controller.aop;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.controller.listeners.PhaseListenerControllVerificacaoContrato;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.util.ServicoVerificacaoProdutoUsados;

/**
 * Esta classe � um aspecto referente a classe br.gov.caixa.siiac.view.mb.EfetuaVerificacaoMB, interceptando<br>
 * m�todos para controle de acesso.<br>
 * 
 * <br>
 * A inser��o de um aspecto � ideal, pois desta forma n�o necessita de uma interven��o diretamente<br>
 * na classe que se deseja controlar.<br>
 * <br>
 * Foi necess�rio pois, caso um usu�rio acesse um servi�o em uma aba do navegado, e<br>
 * em outra aba acesse outro recurso, o bloqueio obtido na primeira aba ser� liberado, pois<br>
 * a URL foi alterada, ou seja, o usu�rio navegou para outra p�gina. Pois para o servidor<br>
 * � indiferente de qual aba foi realizada a requisi��o. Desta forma se outro usu�rio tentar <br>
 * o mesmo servi�o, ir� conseguir. Por�m a tela com o mesmo servi�o ainda ficou aberta para o<br>
 * primeiro usu�rio, desta forma quando o primeiro usu�rio tentar realizar alguma opera��o, <br>
 * deve ser validado se ele ainda possui o bloqueio. Como a URL de destino n�o � para <br>
 * "/jsp/verificacao/efetua_verificacao.xhtml" para o caso das fun��es Salvar e Voltar, por exxemplo,<br>
 * pois elas redirecionam para "/jsp/verificacao/escolhe_verificacao.xhtml", o PhaseListener n�o ir� atuar.<br>
 * Desta forma sendo necess�rio detectar a chamada as essas funcionalidades via aspecto.
 * <br>
 * <br>
 * 
 * Fazer o controle somente por aspecto e n�o por URL, implica na dificuldade em detectar quando o usu�rio<br>
 * deixou o recurso, para que este seja liberado.
 * <br>
 * Para que seja reconhecida como um Aspecto � necess�rio ser um bean gerenciado pelo spring, anotada com
 * <br> "@Service" e "@Aspect".<br>
 * Para tanto no config do spring, deve ser existir a entrada "<aop:aspectj-autoproxy proxy-target-class="true"/>".<br>
 * O atributo "proxy-target-class="true"" faz com que seja usado o gerador de proxy da biblioteca CGLIB e n�o a padr�o do<br>
 * JDK. A padr�o do JDK trabalha somente com classes que implementam interfaces a CGLIB com qualquer classe.
 * 
 * @see PhaseListenerControllVerificacaoContrato
 * @see ServicoVerificacaoProdutoUsados
 * @author Caixa Econ�mica Federal
 *
 */
@Aspect
@Service()
public class ControlaConcorrenciaVerificacaoProdutoAOP 
{
	/**
	 * Interceptar� todos os m�todos da classe " br.gov.caixa.siiac.view.mb.EfetuaVerificacaoMB", <br>
	 * que iniciem com "do", que receba qualquer quantidade de par�metros e que seja public e retorne String.
	 * 
	 * @param pjp - Objeto criado pelo proxy para acesso ao objeto original
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(public String  br.gov.caixa.siiac.view.mb.EfetuaVerificacaoMB.do*(..))")
	public Object do1(ProceedingJoinPoint pjp) throws Throwable
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		NavigationHandler nh = facesContext.getApplication().getNavigationHandler();    
		

		boolean ret=ServicoVerificacaoProdutoUsados.isLock(session.getId());
		if (!ret)
		{
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não é mais possível realizar a operação desejada, o recurso está em uso por outro usuário.","Não é mais possível realizar a operação desejada, o recurso está em uso por outro usuário."));
			nh.handleNavigation(facesContext, null, "toEscolheVerificacao");
			return "";
		}
		else
		{
			//invoca o objeto original
			return pjp.proceed();
		}
	}
	
	/**
	 * Interceptar� todos os m�todos da classe " br.gov.caixa.siiac.view.mb.EfetuaVerificacaoMB", <br>
	 * que iniciem com "do", que receba qualquer quantidade de par�metros, que seja public e retorne void.	 
	 * 
	 * @param pjp - Objeto criado pelo proxy para acesso ao objeto original
	 * @throws Throwable
	 */
	@Around("execution(public void  br.gov.caixa.siiac.view.mb.EfetuaVerificacaoMB.do*(..))")
	public void do2(ProceedingJoinPoint pjp) throws Throwable
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		NavigationHandler nh = facesContext.getApplication().getNavigationHandler();    
		
		boolean ret=ServicoVerificacaoProdutoUsados.isLock(session.getId());
		if (!ret)
		{
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"N�o � poss�vel utilizar este servi�o verifica��o contrato, pois ja esta em uso.","N�o � poss�vel utilizar este servi�o verifica��o contrato, pois ja esta em uso."));
			nh.handleNavigation(facesContext, null, "toEscolheVerificacao");
		}
		else
		{
			//invoca o objeto original
			pjp.proceed();
		}
	}
}
