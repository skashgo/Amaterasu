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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IBlocoChecklistBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.BlocoChecklist;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.persistence.dao.IBlocoChecklistDAO;
import br.gov.caixa.util.Utilities;

@Service
@Scope("prototype")
public class BlocoChecklistBO implements IBlocoChecklistBO {
	
	private transient IBlocoChecklistDAO blocoChecklistDAO;
	
	@Transactional
	public List<BlocoChecklist> getListFiltro(String pesquisa, Boolean mostrarInativos) throws SIIACException {
		Criteria c = blocoChecklistDAO.getCriteria();
		if (Utilities.notEmpty(pesquisa)) {
			c.add(Restrictions.ilike("noBlocoChecklist", pesquisa, MatchMode.ANYWHERE));
		}
		if (Utilities.notEmpty(mostrarInativos) && mostrarInativos.equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		c.addOrder(Order.asc("noBlocoChecklist"));
		return blocoChecklistDAO.findByCriteria(c);
	}
	
	//Getters and Setters
	@Autowired
	public void setBlocoChecklistDAO(IBlocoChecklistDAO blocoChecklistDAO) {
		this.blocoChecklistDAO = blocoChecklistDAO;
	}
	
	@Transactional
	public void merge(BlocoChecklist blocoChecklist) throws SIIACException {
		blocoChecklist.setIcAtivo(true);
		blocoChecklistDAO.merge(blocoChecklist);
	}
	
	@Transactional
	public Boolean exist(BlocoChecklist blocoChecklist) throws SIIACException {
		if (Utilities.notEmpty(blocoChecklist)) {
			//Existe pelo nome
			Criteria c = blocoChecklistDAO.getCriteria();
			if (Utilities.notEmpty(blocoChecklist.getNoBlocoChecklist())) {
				c.add(Restrictions.eq("noBlocoChecklist", blocoChecklist.getNoBlocoChecklist()).ignoreCase());
				if(Utilities.notEmpty(blocoChecklist.getNuBlocoChecklist())){
					c.add(Restrictions.ne("nuBlocoChecklist", blocoChecklist.getNuBlocoChecklist()));
				}
			}
			c.setProjection(Projections.rowCount());
			return ((Integer) c.uniqueResult()).intValue() > 0;
		}
		return false;
	}
	
	@Transactional
	public void ativaInativa(BlocoChecklist blocoChecklist) throws SIIACException {
		if (blocoChecklist.getIcAtivo()) {
			blocoChecklist.setIcAtivo(false);
		} else {
			blocoChecklist.setIcAtivo(true);
		}
		blocoChecklistDAO.merge(blocoChecklist);
	}
	
	@Transactional
	public List<BlocoChecklist> getAllNotChecklist(ChecklistServicoVerificacaoProduto checklist) throws DAOException {
		return blocoChecklistDAO.getAllNotChecklist(checklist);
	}
	
	@Transactional
	public boolean isAtivo(BlocoChecklist blocoChecklist) {
		Criteria c = blocoChecklistDAO.getCriteria();
		c.add(Restrictions.eq("nuBlocoChecklist", blocoChecklist.getNuBlocoChecklist()));
		c.setProjection(Property.forName("icAtivo"));
		return Boolean.valueOf(String.valueOf(c.uniqueResult()));
	}
}