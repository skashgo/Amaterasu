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

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
public class PropriedadesEmail {
	
	private String usuario;
	private String senha;
	private String servidor;
	private String porta;
	private String assunto;
	private String corpo;
	private String remetente;
	private InternetAddress[] destTo;
	private InternetAddress[] destToCC;
	private List<Anexo> anexos = new ArrayList<Anexo>();
	
	
	public PropriedadesEmail() {
	}
		
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public InternetAddress[] getDestTo() {
		return destTo;
	}

	public void setDestTo(InternetAddress[] destTo) {
		this.destTo = destTo;
	}

	public InternetAddress[] getDestToCC() {
		return destToCC;
	}

	public void setDestToCC(InternetAddress[] destToCC) {
		this.destToCC = destToCC;
	}

	public List<Anexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	public String getRemetente() {
		if (Utilities.empty(remetente))
			return this.usuario;
		
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public void setDestinatarios(List<String> destinatarios, List<String> copia) throws AddressException
    {
    	destTo = new InternetAddress[destinatarios.size()];
    	
    	int cont = 0;
    	if (destinatarios != null)
    	{
	    	for (String dest : destinatarios) {
				destTo[cont] = new InternetAddress(dest);
				cont++;
			}
    	}
    	    	
    	cont = 0;
    	if (copia != null)
    	{
    		destToCC = new InternetAddress[copia.size()];
    		for (String cop : copia) {
	    		destToCC[cont] = new InternetAddress(cop);
				cont++;
			}
    	}
    	
    }

}
