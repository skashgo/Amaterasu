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
package br.gov.caixa.siiac.system;

import java.util.ResourceBundle;

/**
 * @author GisConsult
 *
 */
public class SystemProperties {
	
	private static SystemProperties sp=null;
	
	public static SystemProperties getInstance()
	{
		if (sp==null)
		{
			return new SystemProperties();
		}
		else
			return sp;
	}
	
	//Parametros do sistema

	public String getPropertie(String key)
	{
		ResourceBundle resourceConfig = null;
		String path=this.getClass().getPackage().getName();;
		resourceConfig = java.util.ResourceBundle.getBundle(path+".system");
		
		return resourceConfig.getString(key);
	}
	
}
