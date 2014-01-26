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
package br.gov.caixa.siiac.bo.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IExecuteViewActionServletBO;
import br.gov.caixa.siiac.bo.IResourcePropertiesBO;
import br.gov.caixa.siiac.exception.SIIACRuntimeException;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.util.pentaho.Parameter;
import br.gov.caixa.siiac.util.pentaho.ViewActionServlet;

/**
 * @author GIS Consult
 *
 */
@Service
public class ExecuteViewActionServletBO implements IExecuteViewActionServletBO 
{
	@Autowired
	private IResourcePropertiesBO resourcePropertiesBO;
	
	ViewActionServlet viewActionServlet=new ViewActionServlet();
	
	Logger logger=Logger.getLogger(ExecuteViewActionServletBO.class);
	
	public void executeGeraAgendaBatch(Contrato contrato) 
	{
		try
		{
			logger.info("Inicio");
			
			viewActionServlet.setAssertResponse(resourcePropertiesBO.getPropriedade("geraagenda.assertresponse.parameter.action.servlet", "pentaho"));
			viewActionServlet.setAction(resourcePropertiesBO.getPropriedade("geraagenda.parameter.action.servlet", "pentaho"));
			viewActionServlet.setPath(resourcePropertiesBO.getPropriedade("path.parameter.action.servlet", "pentaho"));
			
			viewActionServlet.setUrl(resourcePropertiesBO.getPropriedade("url.action.servlet", "pentaho"));
			viewActionServlet.setSolution(resourcePropertiesBO.getPropriedade("solution.parameter.action.servlet", "pentaho"));
			viewActionServlet.setUserid(resourcePropertiesBO.getPropriedade("userid.parameter.action.servlet", "pentaho"));
			viewActionServlet.setPassword(resourcePropertiesBO.getPropriedade("password.parameter.action.servlet", "pentaho"));
			
			
			String paramUser=resourcePropertiesBO.getPropriedade("user.parameters.action.servlet", "pentaho");
			
			List<String> listParamUsers=ViewActionServlet.rawParametersToListString(paramUser);
			
			viewActionServlet.getListParameters().clear();
			viewActionServlet.getListParameters().add(new Parameter(listParamUsers.get(0),String.valueOf(contrato.getNuContrato().intValue())));
			
			viewActionServlet.execute();
			
			logger.info("Fim");
			logger.info(viewActionServlet.getAssertResponse());
		}
		catch (Exception erro)
		{
			throw new SIIACRuntimeException("Erro na rotina de geração de agenda no Pentho BI Server: " + erro.getMessage());
		}
	}
	
	
	public void executeGeraAgendaPreventivaBatch(Contrato contrato, Integer nuServicoVerificacaoProduto)
	{
		try
		{
			logger.info("Inicio");
			
			viewActionServlet.setAssertResponse(resourcePropertiesBO.getPropriedade("geraagenda.assertresponse.parameter.action.servlet", "pentaho"));
			viewActionServlet.setAction(resourcePropertiesBO.getPropriedade("geraagendapreventiva.parameter.action.servlet", "pentaho"));
			viewActionServlet.setPath(resourcePropertiesBO.getPropriedade("path.parameter.action.servlet", "pentaho"));
			
			viewActionServlet.setUrl(resourcePropertiesBO.getPropriedade("url.action.servlet", "pentaho"));
			viewActionServlet.setSolution(resourcePropertiesBO.getPropriedade("solution.parameter.action.servlet", "pentaho"));
			viewActionServlet.setUserid(resourcePropertiesBO.getPropriedade("userid.parameter.action.servlet", "pentaho"));
			viewActionServlet.setPassword(resourcePropertiesBO.getPropriedade("password.parameter.action.servlet", "pentaho"));
			
			
			String paramUser = resourcePropertiesBO.getPropriedade("user.parameters.action.servlet", "pentaho");
			
			List<String> listParamUsers = ViewActionServlet.rawParametersToListString(paramUser);
			
			viewActionServlet.getListParameters().clear();
			viewActionServlet.getListParameters().add(new Parameter(listParamUsers.get(0),String.valueOf(contrato.getNuContrato().intValue())));
			viewActionServlet.getListParameters().add(new Parameter(listParamUsers.get(1),String.valueOf(nuServicoVerificacaoProduto)));
			
			viewActionServlet.execute();
			
			logger.info("Fim");
			logger.info(viewActionServlet.getAssertResponse());
		}
		catch (Exception erro)
		{
			erro.printStackTrace();
			throw new SIIACRuntimeException("Erro na rotina de geração de agenda preventiva no Pentho BI Server: " + erro.getMessage());
		}
	}

}
