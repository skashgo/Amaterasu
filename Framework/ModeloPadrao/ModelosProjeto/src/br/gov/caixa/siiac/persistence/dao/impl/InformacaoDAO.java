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

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Informacao;
import br.gov.caixa.siiac.persistence.dao.IInformacaoDAO;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class InformacaoDAO extends GenericDAO<Informacao> implements IInformacaoDAO {

	
	public InformacaoDAO() {
		super(Informacao.class);
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IInformacaoDAO#exist(java.lang.String)
	 */
	public Boolean exist(String noInformacao) throws SIIACException {
		Criteria c = getCriteria();
		if(Utilities.notEmpty(noInformacao)) {
			c.add(Restrictions.ilike("noInformacao",noInformacao, MatchMode.EXACT));
		}
		c.setProjection(Projections.rowCount());
		return ((Integer) c.uniqueResult()).intValue() > 0;
	}
}
