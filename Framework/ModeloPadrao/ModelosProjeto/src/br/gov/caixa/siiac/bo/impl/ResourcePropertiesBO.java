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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IResourcePropertiesBO;
import br.gov.caixa.siiac.persistence.dao.IResourcePropertiesDAO;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class ResourcePropertiesBO implements IResourcePropertiesBO {
	private transient IResourcePropertiesDAO resourcePropertiesDAO;
	
	@Autowired
	public void setResourcePropertiesDAO(IResourcePropertiesDAO resourcePropertiesDAO) {
		this.resourcePropertiesDAO = resourcePropertiesDAO;
	}
	
	@Transactional
	public String getPropriedade(String chave, String grupo) throws DAOException {
		return resourcePropertiesDAO.getPropriedade(chave, grupo);
	}
	
}
