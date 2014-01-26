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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.domain.Apontamento;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;
import br.gov.caixa.siiac.persistence.dao.IApontamentoDAO;

/**
 * @author GisConsult
 *
 */
@Repository
@Scope("prototype")
public class ApontamentoDAO extends GenericDAO<Apontamento> implements IApontamentoDAO {

	/**
	 * @param persistenceClass
	 */
	public ApontamentoDAO() {
		super(Apontamento.class);
	}
	
	public List<Apontamento> getAllNotItem(ItemVerificacaoChecklistProduto item) throws DAOException {
		DetachedCriteria dc = DetachedCriteria.forClass(ApontamentoChecklistProduto.class);
		dc.add(Restrictions.eq("itemVerificacaoChecklistProduto", item))
		.setProjection(Projections.property("apontamento.nuApontamentoChecklist"));
		
		Criteria c = getCriteria()
				.add(Property.forName("nuApontamentoChecklist").notIn(dc))
				.add(Restrictions.eq("icAtivo", Boolean.TRUE))
				.addOrder(Order.asc("noApontamentoChecklist"));
		
		return findByCriteria(c);
	}
	
	public List<Apontamento> getAllItem(ItemVerificacaoChecklistProduto item) throws DAOException {
		Criteria c = getCriteria();
		c.createAlias("apontamentoChecklistProduto", "acp");
		c.add(Restrictions.eq("acp.itemVerificacaoChecklistProduto", item));
		c.addOrder(Order.asc("acp.numeroDeOrdem"));
		
		return findByCriteria(c);
	}
}
