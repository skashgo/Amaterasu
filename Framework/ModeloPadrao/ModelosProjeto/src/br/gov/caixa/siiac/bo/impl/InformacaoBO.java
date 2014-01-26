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
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IInformacaoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Informacao;
import br.gov.caixa.siiac.persistence.dao.IInformacaoDAO;
import br.gov.caixa.siiac.util.FilterBase;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */

@Service
@Scope("prototype")
public class InformacaoBO implements IInformacaoBO {

	private transient IInformacaoDAO informacaoDAO;
	
	@Transactional
	public void save(Informacao informacao) {
		informacaoDAO.merge(informacao);
	}
	
	@Autowired
	public void setInformacaoDAO(IInformacaoDAO informacaoDAO) {
		this.informacaoDAO = informacaoDAO;
	}	

	@Transactional
	public void exclui(Informacao informacao) throws SIIACException {
		informacaoDAO.delete(informacao);
	}

	@Transactional
	public void merge(Informacao informacao) throws SIIACException {
		informacaoDAO.merge(informacao);
	}

	@Transactional
	public Boolean exist(Informacao informacao) throws SIIACException {
		if (Utilities.notEmpty(informacao)) {
			
			//Existe pelo assunto
			Criteria c = informacaoDAO.getCriteria();
			if (Utilities.notEmpty(informacao.getNoInformacao())) {
				c.add(Restrictions.eq("noInformacao", informacao.getNoInformacao()).ignoreCase());
				if(Utilities.notEmpty(informacao.getNuInformacao())){
					c.add(Restrictions.ne("nuInformacao", informacao.getNuInformacao()));
				}
			}
			c.setProjection(Projections.rowCount());
			return ((Integer) c.uniqueResult()).intValue() > 0;
		}
		return false;
	}

	@Transactional
	public List<Informacao> getListFiltro(FilterBase filtro) throws SIIACException {
		
		String pesquisa = filtro.getString("pesquisaSimples");
		
		Criteria c = informacaoDAO.getCriteria();
		if (Utilities.notEmpty(pesquisa)) {
			c.add(Restrictions.ilike("noInformacao", pesquisa, MatchMode.ANYWHERE));
		}		
		c.addOrder(Order.asc("noInformacao"));
		return informacaoDAO.findByCriteria(c);
	}
	
	@Transactional
	public List<Informacao> getListLogin() throws SIIACException {
		Criteria c = informacaoDAO.getCriteria();
		
		c.add(Restrictions.or(
				Restrictions.ge("dtFim", new Date()),
				Restrictions.isNull("dtFim")
				)
			  );
		
			
		c.addOrder(Order.asc("dtFim"));
		return informacaoDAO.findByCriteria(c);
	}

	@Transactional
	public List<Informacao> getListFiltroAv(FilterBase filtro) throws SIIACException {
		Criteria c = informacaoDAO.getCriteria();
		
		String pesquisa = filtro.getString("pesquisaAssunto");
		Date dtInicio = filtro.getDate("pesquisaDtInicio");
		Date dtFim = filtro.getDate("pesquisaDtFim");
		
		if(Utilities.notEmpty(pesquisa)) {
			c.add(Restrictions.ilike("noInformacao", pesquisa, MatchMode.ANYWHERE));
		}
		
		if(Utilities.notEmpty(dtInicio)) {
			c.add(Restrictions.eq("dtInicio", dtInicio));
		}
		
		if(Utilities.notEmpty(dtFim)) {
			c.add(Restrictions.eq("dtFim", dtFim));
		}
		c.addOrder(Order.asc("noInformacao"));
		return informacaoDAO.findByCriteria(c);
	}

}
