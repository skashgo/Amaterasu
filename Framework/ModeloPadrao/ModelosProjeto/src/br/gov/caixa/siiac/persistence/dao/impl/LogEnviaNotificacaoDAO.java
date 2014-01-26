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
package br.gov.caixa.siiac.persistence.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.LogEnviaNotificacao;
import br.gov.caixa.siiac.persistence.dao.ILogEnviaNotificacaoDAO;

/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class LogEnviaNotificacaoDAO extends GenericDAO<LogEnviaNotificacao> implements ILogEnviaNotificacaoDAO {

	public LogEnviaNotificacaoDAO() {
		super(LogEnviaNotificacao.class);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.ILogEnviaNotificacaoDAO#exist(java.lang.String, java.util.Date)
	 */
	public Boolean exist(String assunto, Date data) {

		Criteria crit = getCriteria();
		crit.add(Restrictions.ilike("id.assuntoNotificacao", assunto));
		crit.add(Restrictions.eq("id.dataEnvioNotificacao", new Date()));
		
		return !crit.list().isEmpty();
	}

}
