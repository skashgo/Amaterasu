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
package br.gov.caixa.siiac.util;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * @author GIS Consult
 *
 */
public final class VariableScope {
	private static VariableScope i;
	private HttpSession httpSession = null;
	private Map<String, Object> variableRequestScope = new HashMap<String, Object>();
	private Map<String, Object> variableSessionScope = new HashMap<String, Object>();
	private String remoteAddr; // ip do cliente
	
	public static VariableScope i() {
		if (i == null) {
			i = new VariableScope();
		}
		return i;
	}
	
	private HttpSession getSession() {
		if (FacesContext.getCurrentInstance() != null) {
			httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		}
		return httpSession;
	}
	
	private Map<String, Object> getRequestMap() {
		if (FacesContext.getCurrentInstance() != null) {
			return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		}
		return null;
	}
	
	public String getRemoteAddr() {
		if (FacesContext.getCurrentInstance() != null) {
			return ((javax.servlet.http.HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
		} else {
			return remoteAddr;
		}
	}
	
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	
	public void invalidateSession() {
		if (FacesContext.getCurrentInstance() != null) {
			getSession().invalidate();
		} else {
			variableSessionScope.clear();
		}
	}
	
	public void setValueSession(String key, Object value) {
		if (FacesContext.getCurrentInstance() != null) {
			getSession().setAttribute(key, value);
		} else {
			variableSessionScope.put(key, value);
		}
	}
	
	public void removeValueSession(String key) {
		if (FacesContext.getCurrentInstance() != null) {
			getSession().removeAttribute(key);
		} else {
			variableSessionScope.remove(key);
		}
	}
	
	public Object getValueSession(String key) {
		if (FacesContext.getCurrentInstance() != null) {
			return getSession().getAttribute(key);
		} else {
			return variableSessionScope.get(key);
		}
	}
	
	public void clearRequest() {
		if (FacesContext.getCurrentInstance() != null) {
			getRequestMap().clear();
		} else {
			variableRequestScope.clear();
		}
	}
	
	public void setValueRequest(String key, Object value) {
		if (FacesContext.getCurrentInstance() != null) {
			getRequestMap().put(key, value);
		} else {
			variableRequestScope.put(key, value);
		}
	}
	
	public void removeValueRequest(String key) {
		if (FacesContext.getCurrentInstance() != null) {
			getRequestMap().remove(key);
		} else {
			variableRequestScope.remove(key);
		}
	}
	
	public Object getValueRequest(String key) {
		if (FacesContext.getCurrentInstance() != null) {
			return getRequestMap().get(key);
		} else {
			return variableRequestScope.get(key);
		}
	}
	
}
