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
import br.gov.caixa.siiac.bo.IApontamentoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Apontamento;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;
import br.gov.caixa.siiac.persistence.dao.IApontamentoDAO;
import br.gov.caixa.util.Utilities;

/**
 * @author GisConsult
 *
 */
@Service
@Scope("prototype")
public class ApontamentoBO implements IApontamentoBO {

	private transient IApontamentoDAO apontamentoDAO;
	
	@Autowired
	public void setApontamentoDAO(IApontamentoDAO apontamentoDAO){
		this.apontamentoDAO = apontamentoDAO;
	}
	
	@Transactional
	public List<Apontamento> getListFiltro(String pesquisa, Boolean mostrarInativos) throws SIIACException {
		Criteria c = apontamentoDAO.getCriteria();
		if (Utilities.notEmpty(pesquisa)) {
			c.add(Restrictions.ilike("noApontamentoChecklist", pesquisa, MatchMode.ANYWHERE));
		}
		if (Utilities.notEmpty(mostrarInativos) && mostrarInativos.equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		c.addOrder(Order.asc("noApontamentoChecklist"));
		return apontamentoDAO.findByCriteria(c);
	}

	@Transactional
	public Boolean exist(Apontamento apontamento) throws SIIACException {
		if (Utilities.notEmpty(apontamento)) {
		
			//Existe pelo nome
			Criteria c = apontamentoDAO.getCriteria();
			if (Utilities.notEmpty(apontamento.getNoApontamentoChecklist())) {
				c.add(Restrictions.eq("noApontamentoChecklist", apontamento.getNoApontamentoChecklist()).ignoreCase());
				if(Utilities.notEmpty(apontamento.getNuApontamentoChecklist())){
					c.add(Restrictions.ne("nuApontamentoChecklist", apontamento.getNuApontamentoChecklist()));
				}
			}
			c.setProjection(Projections.rowCount());
			return ((Integer) c.uniqueResult()).intValue() > 0;
		}
		return false;
		
	}

	@Transactional
	public void merge(Apontamento apontamento) throws SIIACException {
		apontamento.setIcAtivo(true);
		apontamentoDAO.merge(apontamento);
	}

	@Transactional
	public void ativaInativa(Apontamento apontamento) throws SIIACException {

		if (apontamento.getIcAtivo()) {
			apontamento.setIcAtivo(false);
		} else {
			apontamento.setIcAtivo(true);
		}
		apontamentoDAO.merge(apontamento);
		
	}
	
	@Transactional
	public List<Apontamento> getAllNotItem(ItemVerificacaoChecklistProduto item) throws DAOException {
		return apontamentoDAO.getAllNotItem(item);
	}
	
	@Transactional
	public List<Apontamento> getAllItem(ItemVerificacaoChecklistProduto item)
			throws DAOException {
		return apontamentoDAO.getAllItem(item);
	}
	
	@Transactional
	public boolean isAtivo(Apontamento apontamento) {
		Criteria c = apontamentoDAO.getCriteria();
		c.add(Restrictions.eq("nuApontamentoChecklist", apontamento.getNuApontamentoChecklist()));
		c.setProjection(Property.forName("icAtivo"));
		return Boolean.valueOf(String.valueOf(c.uniqueResult()));
	}
}
