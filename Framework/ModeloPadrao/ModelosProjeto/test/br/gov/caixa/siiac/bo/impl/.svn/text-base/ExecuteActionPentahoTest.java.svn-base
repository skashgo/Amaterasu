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
package br.gov.caixa.siiac.bo.impl;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.MillisecondInstantPrinter;

import br.gov.caixa.siiac.bo.IExecuteViewActionServletBO;
import br.gov.caixa.siiac.bo.IResourcePropertiesBO;
import br.gov.caixa.siiac.bo.ITemplateParecerBO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.TemplateParecer;
import br.gov.caixa.siiac.system.SystemProperties;
import br.gov.caixa.siiac.util.pentaho.Parameter;
import br.gov.caixa.siiac.util.pentaho.ViewActionServlet;

/**
 * @author GIS Consult
 *
 */
public class ExecuteActionPentahoTest extends TestWithSpring
{
	@Autowired
	private IResourcePropertiesBO resourcePropertiesBO;
	
	@Autowired
	private IExecuteViewActionServletBO executeViewActionServletBO;

	private Contrato contrato=new Contrato();
	private String urlAction="";
	private String data = "";
	
	
	@Before
	public void setUp()
	{
		contrato.setNuContrato(106);

		urlAction="http://apia:8080/pentaho/ViewAction";
        data = "solution=SIIAC&path=action&action=ACTION_GERA_AGENDA_VERIFICACAO.xaction&userid=joe&password=password&nu_contrato=106";
		
	}
	
	
	@Test
	public void testaExecucaoViewActionInPentahoBIServer()
	{
		try
		{

            // Send data
            URL url = new URL(urlAction);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(0);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                System.out.println("Linha: "+line);
            }
            wr.close();
            rd.close();
   
           System.out.println("FIM");
			
		}
		catch (Exception erro)
		{
			erro.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testaViewActionServlet()
	{
		try
		{
			ViewActionServlet viewActionServlet=new ViewActionServlet();
		
			viewActionServlet.setAssertResponse(resourcePropertiesBO.getPropriedade("geraagenda.assertresponse.parameter.action.servlet", "pentaho"));
			viewActionServlet.setAction(resourcePropertiesBO.getPropriedade("geraagenda.parameter.action.servlet", "pentaho"));
			viewActionServlet.setPath(resourcePropertiesBO.getPropriedade("path.parameter.action.servlet", "pentaho"));
			
			viewActionServlet.setUrl(resourcePropertiesBO.getPropriedade("url.action.servlet", "pentaho"));
			viewActionServlet.setSolution(resourcePropertiesBO.getPropriedade("solution.parameter.action.servlet", "pentaho"));
			viewActionServlet.setUserid(resourcePropertiesBO.getPropriedade("userid.parameter.action.servlet", "pentaho"));
			viewActionServlet.setPassword(resourcePropertiesBO.getPropriedade("password.parameter.action.servlet", "pentaho"));
			
			
			
			String paramUser=resourcePropertiesBO.getPropriedade("user.parameters.action.servlet", "pentaho");
			
			List<String> listParamUsers=ViewActionServlet.rawParametersToListString(paramUser);
			
			viewActionServlet.getListParameters().add(new Parameter(listParamUsers.get(0), "106"));
			
			viewActionServlet.execute();
			
			System.out.println(viewActionServlet.getResponseString());
			
		}
		catch (Exception erro)
		{
			erro.printStackTrace();
			fail();
		}		
	}
	
	@Test
	public void executeGeraAgendaBatch()
	{
		try
		{
			executeViewActionServletBO.executeGeraAgendaBatch(contrato);
		}
		catch (Exception erro)
		{
			erro.printStackTrace();
			fail();
		}
	
	}
}

