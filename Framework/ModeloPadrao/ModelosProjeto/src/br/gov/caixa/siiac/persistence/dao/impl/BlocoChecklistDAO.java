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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.BlocoChecklist;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.persistence.dao.IBlocoChecklistDAO;
import br.gov.caixa.util.Utilities;

@Repository
@Scope("prototype")
public class BlocoChecklistDAO extends GenericDAO<BlocoChecklist> implements IBlocoChecklistDAO {
	
	public BlocoChecklistDAO() {
		super(BlocoChecklist.class);
	}
	
	public List<BlocoChecklist> getAllNotChecklist(ChecklistServicoVerificacaoProduto checklist) throws DAOException {
		DetachedCriteria dc = DetachedCriteria.forClass(BlocoChecklistProduto.class);
		 dc.add(Restrictions.eq("checklistServicoVerificacaoProduto", checklist))
		 .setProjection(Projections.property("blocoChecklist.nuBlocoChecklist"));
		
		 Criteria c = getCriteria()
			 .add(Property.forName("nuBlocoChecklist").notIn(dc))
			 .add(Restrictions.eq("icAtivo", Boolean.TRUE))
			 .addOrder(Order.asc("noBlocoChecklist"));
		 
		 return findByCriteria(c);
	}
	
	public Boolean exist(String nome) throws SIIACException {
		//Existe pelo nome
		Criteria c = getCriteria();
		if (Utilities.notEmpty(nome)) {
			c.add(Restrictions.ilike("noBlocoChecklist", nome, MatchMode.EXACT));
		}
		c.setProjection(Projections.rowCount());
		return ((Integer) c.uniqueResult()).intValue() > 0;
	}
	
}