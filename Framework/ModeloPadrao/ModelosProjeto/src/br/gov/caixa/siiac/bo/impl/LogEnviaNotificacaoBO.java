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

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.ILogEnviaNotificacaoBO;
import br.gov.caixa.siiac.model.domain.LogEnviaNotificacao;
import br.gov.caixa.siiac.persistence.dao.ILogEnviaNotificacaoDAO;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class LogEnviaNotificacaoBO implements ILogEnviaNotificacaoBO {

	private ILogEnviaNotificacaoDAO logEnviaNotificacaoDAO;
	
	@Autowired
	public void setLogEnviaNotificacaoDAO(
			ILogEnviaNotificacaoDAO logEnviaNotificacaoDAO) {
		this.logEnviaNotificacaoDAO = logEnviaNotificacaoDAO;
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.ILogEnviaNotificacaoBO#merge(br.gov.caixa.siiac.model.domain.LogEnviaNotificacao)
	 */
	public void merge(LogEnviaNotificacao logEnviaNotificacao) {
		// TODO Auto-generated method stub
		logEnviaNotificacaoDAO.merge(logEnviaNotificacao);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.ILogEnviaNotificacaoBO#exist(java.lang.String, java.util.Date)
	 */
	public Boolean exist(String assunto, Date data) {
		// TODO Auto-generated method stub
		return logEnviaNotificacaoDAO.exist(assunto, data);
	}

}
