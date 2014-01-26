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


import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;

/**
 * Classe para criacao do contexto inicial JNDI. Apenas para ser utilizado ao executar testes unitarios
 * com JUnit, onde e necessario associar um nome JNDI com um DataSource.
 */
public class SimpleContextFactory implements InitialContextFactory
{
	//singleton para guardar o contexto criado por essa f�brica
    private static SimpleContext instance;

    /**
     * Retorna o SimpleContext para uso.
     * @param environment
     * @return
     */
    public Context getInitialContext( Hashtable environment )
    {
        if ( instance == null )
        {
			instance = new SimpleContext();
        }
        return instance;
    }
}
