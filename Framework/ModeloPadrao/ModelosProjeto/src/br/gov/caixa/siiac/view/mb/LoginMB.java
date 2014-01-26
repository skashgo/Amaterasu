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

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author GisConsult
 *
 */
@Service()
@Scope("request")
public class LoginMB extends AbstractMB implements Serializable {
	private static final long serialVersionUID = -3875513360013318652L;
	
	private transient HttpSession session;

	/**
	 * LoginMB
	 */
	public LoginMB() {
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	/**
	 * getMensagemErro
	 * @return
	 */
	public String getMensagemErro() {
		if (session.getAttribute("jboss.jaas.login.error") != null) {
			return session.getAttribute("jboss.jaas.login.error").toString();
		} else {
			return getMessage("msgs", "APPLICATION.falhaAutenticacao");
		}
	}
}
