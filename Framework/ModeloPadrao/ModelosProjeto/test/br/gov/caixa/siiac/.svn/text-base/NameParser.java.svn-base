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
package br.gov.caixa.siiac;

import java.util.Properties;

import javax.naming.CompoundName;
import javax.naming.Name;
import javax.naming.NamingException;

/**
 * Implementacao utilizada apenas para poder realizar bind de um datasource a um nome JNDI.
 * A implementacao foi extra�da da propria documentacao do JRE.
 *
 */
public class NameParser implements javax.naming.NameParser {

	private static final Properties syntax = new Properties();
	static {
	    syntax.put("jndi.syntax.direction", "right_to_left");
	    syntax.put("jndi.syntax.separator", ".");
	    syntax.put("jndi.syntax.ignorecase", "false");
	    syntax.put("jndi.syntax.escape", "\\");
	    syntax.put("jndi.syntax.beginquote", "'");
	}
	
	/**
	 * parse
	 */
	public Name parse(String name) throws NamingException {
	    return new CompoundName(name, syntax);
	}

}
