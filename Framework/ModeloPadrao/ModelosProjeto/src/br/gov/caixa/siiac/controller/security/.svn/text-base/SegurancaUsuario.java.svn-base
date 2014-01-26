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
package br.gov.caixa.siiac.controller.security;

import java.security.Principal;
import java.util.Set;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.caixa.siiac.bo.ListaPerfil;
import br.gov.caixa.siiac.security.ActiveDirectoryManager;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.security.UsuarioPrincipalJAAS;

/**
 * @author Gis Consult
 *
 */
public class SegurancaUsuario {

	private static SegurancaUsuario instance;
	private static final Log LOG = LogFactory.getLog(SegurancaUsuario.class);
	
	/**
	 * getInstance
	 * @return
	 */
	public static SegurancaUsuario getInstance(){
		if(instance == null){
			instance = new SegurancaUsuario();
		}
		return instance;
	}

	private Principal user;
	
	/**
	 * setInstance
	 * @param s
	 */
	public void setInstance(SegurancaUsuario s){
		instance = s;
	}
	/**
	 * Retorna uma instancia de IUsuario, com informacoes do usuario logado no sistema.<br/>
	 * Esse metodo manipula internamente uma implementacao da interface IUsuario, para que a aplicacao<br/>
	 * nao tenha dependencias diretas da origem do usuario e/ou do modo como foi implementado o login.
	 * 
	 * @return
	 */
	public IUsuario getUsuario(){
		LOG.debug("getUsuario INICIO");
		
		IUsuario upj = null;
		if(org.jboss.security.SecurityAssociation.getSubject()==null){
			return (IUsuario) user;
		}
		//Aqui a aplicação torna-se dependente de login por JAAS (pois utiliza diretamente a API de segurança
		//do JBoss). Porém, ao retorna uma interface, a troca da implementação pode causar um impacto mínimo.
		if (org.jboss.security.SecurityAssociation.getSubject() != null) {
			Set<Principal> principals = org.jboss.security.SecurityAssociation.getSubject().getPrincipals();
			for (Principal usuarioPrincipalJAAS : principals) {
				//O nome SiiacUserInfo � utilizado 
				if (usuarioPrincipalJAAS.getName().equals(UsuarioPrincipalJAAS.SIIAC_USER_INFO)) {
					upj = (IUsuario)usuarioPrincipalJAAS;
					break;
				}
			}
		} else {
			LOG.debug("O SUBJECT está null!");
		}
		
		if (upj == null) {
			LOG.debug("Nao foi encontrado o Principal com as informacoes do usuario");
		}
		
		return upj;
	}
	
	/**
	 * Verifica se o usuario logado possui determinado perfil
	 * @param codigoPerfil Codigo do perfil 
	 * @return true, o usuario logado tem o perfil. false, o usuario nao tem o perfil.
	 * @see br.gov.caixa.siroe.model.Perfil.java
	 */
	public boolean verificaPerfil(Short codigoPerfil, HttpServletRequest rq) {
		
		boolean temPerfil = false;
		
		temPerfil = rq.isUserInRole(String.valueOf(codigoPerfil));
		LOG.debug("verificarPerfil() - "+codigoPerfil+": "+temPerfil);

		return temPerfil;
	}
	
	/**
	 * Metodo sobrescrito para nao ter que buscar a request na propria classe invocadora toda vez que for necessario utilizar a verificacao de perfil.
	 * @param codigoPerfil
	 * @return verificaPerfil(Short codigoPerfil, HttpServletRequest rq); 
	 */
	public boolean verificaPerfil(Short codigoPerfil){
//		HttpServletRequest rq = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		
//		return verificaPerfil(codigoPerfil, rq);
		
		return isPerfil(codigoPerfil);
	}
	
	
	/**
	 * @param codigoPerfil
	 * @return
	 */
	private boolean isPerfil(Short codigoPerfil) {
		return getUsuario().contemPerfil(codigoPerfil);
	}

	public String getPerfilAsString(){
		if(verificaPerfil(ListaPerfil.PERFIL_GESTOR_SISTEMA)){
			return ListaPerfil.PERFIL_NOME_GESTOR_SISTEMA;
		} else if (verificaPerfil(ListaPerfil.PERFIL_AUDITOR)) {
			return ListaPerfil.PERFIL_NOME_AUDITOR;
		} else if (verificaPerfil(ListaPerfil.PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE)) {
			return ListaPerfil.PERFIL_NOME_CENTRAL_DE_ATENDIMENTO_E_SUPORTE;
		} else if (verificaPerfil(ListaPerfil.PERFIL_GESTOR_PRODUTO)) {
			return ListaPerfil.PERFIL_NOME_GESTOR_PRODUTO;
		} else if (verificaPerfil(ListaPerfil.PERFIL_REGIONAL_CONFORMIDADE)) {
			return ListaPerfil.PERFIL_NOME_REGIONAL_CONFORMIDADE;
		} else if (verificaPerfil(ListaPerfil.PERFIL_UNIDADE_ATENDIMENTO)) {
			return ListaPerfil.PERFIL_NOME_UNIDADE_ATENDIMENTO;
		} else if (verificaPerfil(ListaPerfil.PERFIL_VERIFICADOR_CONFORMIDADE)) {
			return ListaPerfil.PERFIL_NOME_VERIFICADOR_CONFORMIDADE;
		}else if (verificaPerfil(ListaPerfil.PERFIL_VERIFICADOR_REGIONAL)) {
			return ListaPerfil.PERFIL_NOME_VERIFICADOR_REGIONAL;
		}else if (verificaPerfil(ListaPerfil.PERFIL_VERIFICADOR_NACIONAL)) {
			return ListaPerfil.PERFIL_NOME_VERIFICADOR_NACIONAL;
		}
		return "";
	}

	/**
	 * 
	 * Acesso ao AD
	 */
	public String getUserBasicAttributes(String username, String attribute) throws NamingException {
		return ActiveDirectoryManager.getInstance().getUserBasicAttributes(getUsuario().getMatricula(), attribute);
	}

	/**
	 * @param user
	 */
	public void setUsuario(UsuarioPrincipalJAAS user) {
		this.user = user;		
	}
	
}
