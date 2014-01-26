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
package br.gov.caixa.siiac.util.pentaho;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;

import br.gov.caixa.siiac.util.pentaho.exception.ViewActionServletException;

/**
 * @author GIS Consult
 *
 */
public class ViewActionServlet
{

	private String url="";
	private List<Parameter> listParameters=new ArrayList<Parameter>();
	private String assertResponse="";
	
	private String solution="";
	private String path="";
	private String action="";
	private String userid="";
	private String password="";
	
	private int connectTimeout=20000;
	private int readTimeout=0;
	private String responseString="";
	
	
	public void execute()
	{
		if (getUrl()==null || getUrl().equals(""))
        	throw new ViewActionServletException("URL não pode ser nula ou vazia.");

		if (getSolution()==null || getSolution().equals(""))
        	throw new ViewActionServletException("Solution não pode ser nula ou vazia.");
		
		if (getPath()==null || getPath().equals(""))
        	throw new ViewActionServletException("Path não pode ser nulo ou vazia.");

		if (getAction()==null || getAction().equals(""))
        	throw new ViewActionServletException("Action não pode ser nula ou vazia.");

		if (getUserid()==null || getUserid().equals(""))
        	throw new ViewActionServletException("Userid não pode ser nulo ou vazia.");

		if (getPassword()==null || getPassword().equals(""))
        	throw new ViewActionServletException("Password não pode ser nula ou vazia.");
		
		if (getAssertResponse()==null || getAssertResponse().equals(""))
        	throw new ViewActionServletException("AssertResponse não pode ser nulo ou vazio.");

		if (getListParameters()==null)
        	throw new ViewActionServletException("Lista de parâmetros não pode ser nula.");

		String fixedParameters="solution="+getSolution()+"&path="+getPath()+"&action="+getAction()+"&userid="+getUserid()+"&password="+getPassword();
        String line;
        String lines="";
        
		try
		{
			URL urlA = new URL(getUrl());
            URLConnection conn = urlA.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(getConnectTimeout());
            conn.setReadTimeout(getReadTimeout());
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            
            String paramsStr=listParametersToString(listParameters);
            
            if (paramsStr.equals(""))
            	wr.write(fixedParameters);
            else
            	wr.write(fixedParameters+"&"+paramsStr);
            	
            wr.flush();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            while ((line = rd.readLine()) != null) 
            {
            	lines=lines+line;
            }
            
            wr.close();
            rd.close();
            
            responseString=lines;
		}
		catch (Exception erro)
		{
			throw new ViewActionServletException("Erro na execução da action: "+erro.getMessage());
		}
		
        if (!lines.contains(assertResponse))
        	throw new ViewActionServletException("Erro na execução da action. Conteúdo retornado pela action não contém o valor configurado como sucesso (assertResponse)");
		
		
	}
	
	private String listParametersToString(List<Parameter> list)
	{
		String ret="";
		String sep="";
		
		if (list==null)
			throw new ViewActionServletException("Lista de par�metros n�o pode ser nula.");
		
		for (int i=0;i<list.size();i++)
		{
			Parameter p=list.get(i);
			
			if (i==0)
				sep="";
			else
				sep="&";
			
			ret=ret+ sep+p.getName()+"="+p.getValue();
		}
		
		return ret;
	}
	
	public static List<String> rawParametersToListString(String parametesStr)
	{
		List<String> listString=new ArrayList<String>();
		String sep=";";
		
		if (parametesStr==null || parametesStr.equals(""))
			throw new ViewActionServletException("Par�metros n�o pode ser nulos ou vazio .");
		
		
		if (!parametesStr.contains(sep))
			throw new ViewActionServletException("Par�metros n�o possui o separador correto: (;)");
			
		String[] sp=parametesStr.split(sep);
		
		if (sp==null || sp.length<=0)
			throw new ViewActionServletException("Par�metros n�o puderam ser recuperados, podem n�o estar no formado correto: "+parametesStr);
		
		
		for (int i=0;i<sp.length;i++)
			listString.add(sp[i].trim());
			
		return listString;
	}
	
	

	public void setSolution(String solution) {
		this.solution = solution;
	}




	public String getSolution() {
		return solution;
	}




	public void setPath(String path) {
		this.path = path;
	}




	public String getPath() {
		return path;
	}




	public void setAction(String action) {
		this.action = action;
	}




	public String getAction() {
		return action;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getPassword() {
		return password;
	}



	public void setAssertResponse(String assertResponse) {
		this.assertResponse = assertResponse;
	}



	public String getAssertResponse() {
		return assertResponse;
	}




	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url=url;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}



	public String getUserid() {
		return userid;
	}



	public void setListParameters(List<Parameter> listParameters) {
		this.listParameters = listParameters;
	}



	public List<Parameter> getListParameters() {
		return listParameters;
	}



	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}



	public int getConnectTimeout() {
		return connectTimeout;
	}



	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}



	public int getReadTimeout() {
		return readTimeout;
	}



	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}



	public String getResponseString() {
		return responseString;
	}
	
	
	
}
