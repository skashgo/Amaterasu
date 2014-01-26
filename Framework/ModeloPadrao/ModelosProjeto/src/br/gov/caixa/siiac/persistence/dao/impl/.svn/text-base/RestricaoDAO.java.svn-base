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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.Restricao;
import br.gov.caixa.siiac.model.domain.RestricaoApontamento;
import br.gov.caixa.siiac.persistence.dao.IRestricaoDAO;

/**
 * @author GisConsult
 *
 */
@Repository
@Scope("prototype")
public class RestricaoDAO extends GenericDAO<Restricao> implements IRestricaoDAO {

	/**
	 * @param persistenceClass
	 */
	public RestricaoDAO() {
		super(Restricao.class);
	}
	
	public List<Restricao> getAllNotApontamento(ApontamentoChecklistProduto apontamento) throws DAOException {
		DetachedCriteria dc = DetachedCriteria.forClass(RestricaoApontamento.class);
		dc.add(Restrictions.eq("apontamentoChecklistProduto", apontamento))
		.setProjection(Projections.property("restricao.nuRestricao"));
		
		Criteria c = getCriteria();
		c.add(Property.forName("nuRestricao").notIn(dc));
		
		return findByCriteria(c);
	}
	
	public List<Restricao> getAllApontamento(ApontamentoChecklistProduto apontamento) throws DAOException {
		DetachedCriteria dc = DetachedCriteria.forClass(RestricaoApontamento.class);
		dc.add(Restrictions.eq("apontamentoChecklistProduto", apontamento))
		.setProjection(Projections.property("restricao.nuRestricao"));
		
		Criteria c = getCriteria();
		c.add(Property.forName("nuRestricao").in(dc));
		
		return findByCriteria(c);
	}
}
