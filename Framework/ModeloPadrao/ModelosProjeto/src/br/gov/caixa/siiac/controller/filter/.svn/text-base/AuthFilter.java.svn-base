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

package br.gov.caixa.siiac.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.gov.caixa.siiac.bo.impl.MatrizAcessoBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.view.mb.ApplicationMB;

/**
 * The Class AuthFilter.
 */
public class AuthFilter implements Filter {
	
	/**
	 * The Constant USER_IN_SESSION.
	 */
	private static final String USER_IN_SESSION = "user_in_session";
	
	/**
	 * The LOGIN.
	 */
	private static String LOGIN = "login";
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest rq = (HttpServletRequest) request;
		HttpServletResponse rp = (HttpServletResponse) response;
		boolean auth = rq.getSession().getAttribute(USER_IN_SESSION) != null;
		boolean isFromLogin = rq.getRequestURL().toString().contains("login.jsf");
		rp.addHeader("cache-control", "NO-CACHE");
		rp.addHeader("PRAGMA", "NO-CACHE");
		
		boolean isPages = rq.getRequestURL().toString().contains("/jsp/");
		//Verifica se a requisicao feita atualmente nao e uma pagina (seja css, images, js) ou se e a pagina de login e faz o filtro normalmente.
		if (!isPages) {
			chain.doFilter(request, response);
		}
		//Se for uma pagina e n�o existir usuario na sessao, redireciona pra fora do sistema.
		else if ((!auth && !isFromLogin) && (isPages) && SegurancaUsuario.getInstance().getUsuario() == null) {
			rp.sendRedirect("/jsp/index.jsf");
		}
		//Se existir usuario em sessao de LoginModule e for uma pagina.
		else {
			HttpSession session = rq.getSession(false);
			
			//Se n�o existir usuario na sessao do browser mas existir na sessao do LoginModule, 
			//faz o redirecionamento pra pagina correta de acordo com o perfil do usuario.
			if (!auth && SegurancaUsuario.getInstance().getUsuario() != null) {
				String urlRedirect = "";
				Short nuPerfil = SegurancaUsuario.getInstance().getUsuario().getPerfis().get(0);
				if(MatrizAcessoBO.acessoModuloVisualiaAgendaVerificacoes(nuPerfil, MatrizAcessoBO.VISUALIZA_AGENDA_VERIFICACOES, MatrizAcessoBO.TIPO_ACAO_VISUALIZA)){
					urlRedirect = "/SIIAC/jsp/agenda/agenda.jsf";
				}else{
					urlRedirect = "/SIIAC/jsp/index.jsf";
				}
				
				
				rq.getSession().setAttribute(USER_IN_SESSION, true);
				if (!urlRedirect.equals("") && session != null && session.getAttribute(LOGIN) != null && session.getAttribute(LOGIN) == LOGIN) {
					session.setAttribute(LOGIN, null);
				}
				rp.sendRedirect(urlRedirect);
			}
			//Caso j� exista usuario em sessao e no LoginModule, faz o filtro normalmente.
			else {
				if(rq.getRequestURL().toString().contains("/jsp/index.jsf")){
					String urlRedirect = "";
					Short nuPerfil = SegurancaUsuario.getInstance().getUsuario().getPerfis().get(0);
					if(MatrizAcessoBO.acessoModuloVisualiaAgendaVerificacoes(nuPerfil, MatrizAcessoBO.VISUALIZA_AGENDA_VERIFICACOES, MatrizAcessoBO.TIPO_ACAO_VISUALIZA)){
						urlRedirect = "/SIIAC/jsp/agenda/agenda.jsf";
						rp.sendRedirect(urlRedirect);
					}else{
						chain.doFilter(request, response);
					}
				}else{
					chain.doFilter(request, response);
				}
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		ApplicationMB mb = new ApplicationMB();
		String contexto = "/SIIAC"; //SystemProperties.getInstance().getPropertie("contexto");
		
		if (contexto == null || contexto.equals("/")) {
			contexto = "";
		}
		
		mb.setContexto(contexto);
	}
}
