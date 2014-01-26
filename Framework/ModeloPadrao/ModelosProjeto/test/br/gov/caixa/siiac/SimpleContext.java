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


import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NameParser;
import java.util.Hashtable;

/**
 * Classe para representar um simples contexto JNDI, sem complexidade, apenas para que possibilite realizar
 * o bind entre o DataSource e o nome JNDI especificado.
 */
public class SimpleContext implements Context
{

	private transient Hashtable table = new Hashtable();

    /**
     * Method lookup not yet implemented.
     *
     *
     * @param name
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public Object lookup( Name name )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method lookup() not yet implemented." );
    }

    /**
     * Method lookup Returns the SimpleDataSource.
     *
     *
     * @param name
     *
     * @return A copy of the SimpleDataSource class
     *
     * @throws NamingException
     *
     */
    public Object lookup( String name )
    throws NamingException
    {
        return table.get( name );
    }

    /**
     * Method bind not yet implemented.
     *
     *
     * @param name
     * @param obj
     *
     * @throws NamingException
     *
     */
    public void bind( Name name, Object obj )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method bind() not yet implemented." );
    }

    /**
     * Method bind the SimpleDataSource for use.
     *
     *
     * @param name
     * @param obj
     *
     * @throws NamingException
     *
     */
    public void bind( String name, Object obj )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        table.put( name, obj );
    }

    /**
     * Method rebind not yet implemented.
     *
     *
     * @param name
     * @param obj
     *
     * @throws NamingException
     *
     */
    public void rebind( Name name, Object obj )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method rebind() not yet implemented." );
    }

    /**
     * Method rebind not yet implemented.
     *
     *
     * @param name
     * @param obj
     *
     * @throws NamingException
     *
     */
    public void rebind( String name, Object obj )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method rebind() not yet implemented." );
    }

    /**
     * Method unbind not yet implemented.
     *
     *
     * @param name
     *
     * @throws NamingException
     *
     */
    public void unbind( Name name )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method unbind() not yet implemented." );
    }

    /**
     * Method unbind not yet implemented.
     *
     *
     * @param name
     *
     * @throws NamingException
     *
     */
    public void unbind( String name )
    throws NamingException
    {
        table.remove( name );
    }

    /**
     * Method rename not yet implemented.
     *
     *
     * @param oldName
     * @param newName
     *
     * @throws NamingException
     *
     */
    public void rename( Name oldName, Name newName )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method rename() not yet implemented." );
    }

    /**
     * Method rename not yet implemented.
     *
     *
     * @param oldName
     * @param newName
     *
     * @throws NamingException
     *
     */
    public void rename( String oldName, String newName )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method rename() not yet implemented." );
    }

    /**
     * Method list not yet implemented.
     *
     *
     * @param name
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public NamingEnumeration list( Name name )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method list() not yet implemented." );
    }

    /**
     * Method list not yet implemented.
     *
     *
     * @param name
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public NamingEnumeration list( String name )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method list() not yet implemented." );
    }

    /**
     * Method listBindings not yet implemented.
     *
     *
     * @param name
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public NamingEnumeration listBindings( Name name )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method listBindings() not yet implemented." );
    }

    /**
     * Method listBindings not yet implemented.
     *
     *
     * @param name
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public NamingEnumeration listBindings( String name )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method listBindings() not yet implemented." );
    }

    /**
     * Method destroySubcontext not yet implemented.
     *
     *
     * @param name
     *
     * @throws NamingException
     *
     */
    public void destroySubcontext( Name name )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method destroySubcontext() not yet implemented." );
    }

    /**
     * Method destroySubcontext not yet implemented.
     *
     *
     * @param name
     *
     * @throws NamingException
     *
     */
    public void destroySubcontext( String name )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method destroySubcontext() not yet implemented." );
    }

    /**
     * Method createSubcontext not yet implemented.
     *
     *
     * @param name
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public Context createSubcontext( Name name )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method createSubcontext() not yet implemented." );
    }

    /**
     * Method createSubcontext not yet implemented.
     *
     *
     * @param name
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public Context createSubcontext( String name )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method createSubcontext() not yet implemented." );
    }

    /**
     * Method lookupLink not yet implemented.
     *
     *
     * @param name
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public Object lookupLink( Name name )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method lookupLink() not yet implemented." );
    }

    /**
     * Method lookupLink not yet implemented.
     *
     *
     * @param name
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public Object lookupLink( String name )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method lookupLink() not yet implemented." );
    }

    /**
     * Method getNameParser not yet implemented.
     *
     *
     * @param name
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public NameParser getNameParser( Name name )
    throws NamingException
    {
    	return new br.gov.caixa.siiac.NameParser();
      
    }

    /**
     * Method getNameParser not yet implemented.
     *
     *
     * @param name
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public NameParser getNameParser( String name )
    throws NamingException
    {

    	return new br.gov.caixa.siiac.NameParser();
    }

    /**
     * Method composeName not yet implemented.
     *
     *
     * @param name
     * @param prefix
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public Name composeName( Name name, Name prefix )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method composeName() not yet implemented." );
    }

    /**
     * Method composeName not yet implemented.
     *
     *
     * @param name
     * @param prefix
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public String composeName( String name, String prefix )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method composeName() not yet implemented." );
    }

    /**
     * Method addToEnvironment not yet implemented.
     *
     *
     * @param propName
     * @param propVal
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public Object addToEnvironment( String propName, Object propVal )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method addToEnvironment() not yet implemented." );
    }

    /**
     * Method removeFromEnvironment not yet implemented.
     *
     *
     * @param propName
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public Object removeFromEnvironment( String propName )
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method removeFromEnvironment() not yet implemented." );
    }

    /**
     * Method getEnvironment not yet implemented.
     *
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public Hashtable getEnvironment()
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method getEnvironment() not yet implemented." );
    }

    /**
     * Method close not yet implemented.
     *
     *
     * @throws NamingException
     *
     */
    public void close()
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method close() not yet implemented." );
    }

    /**
     * Method getNameInNamespace not yet implemented.
     *
     *
     * @return
     *
     * @throws NamingException
     *
     */
    public String getNameInNamespace()
    throws NamingException
    {

        /**@todo: Implement this javax.naming.Context method*/
        throw new java.lang.UnsupportedOperationException(
            "Method getNameInNamespace() not yet implemented." );
    }
}
