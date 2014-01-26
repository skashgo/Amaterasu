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
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IRestricaoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.Restricao;
import br.gov.caixa.siiac.persistence.dao.IRestricaoDAO;
import br.gov.caixa.util.Utilities;

@Service
@Scope("prototype")
public class RestricaoBO implements IRestricaoBO {

	private transient IRestricaoDAO restricaoDAO;
	
	@Autowired
	public void setRestricaoDAO(IRestricaoDAO restricaoDAO){
		this.restricaoDAO = restricaoDAO;
	}
	
	@Transactional
	public List<Restricao> getListFiltro(String pesquisa, Boolean mostrarInativos) throws SIIACException {
		Criteria c = restricaoDAO.getCriteria();
		if (Utilities.notEmpty(pesquisa)) {
			
			Disjunction disjuction = Restrictions.disjunction();
			disjuction.add(Restrictions.ilike("coRestricao", pesquisa, MatchMode.ANYWHERE));
			disjuction.add(Restrictions.ilike("noRestricao", pesquisa, MatchMode.ANYWHERE));
			disjuction.add(Restrictions.ilike("sistemaDestino", pesquisa, MatchMode.ANYWHERE));
			
			c.add(disjuction);
		}		

		if (Utilities.notEmpty(mostrarInativos) && mostrarInativos.equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtiva", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtiva", Boolean.TRUE));
		}
		
		return restricaoDAO.findByCriteria(c);
	}

	@Transactional
	public void ativaInativa(Restricao restricao) throws SIIACException {
		if (restricao.getIcAtiva()) {
			restricao.setIcAtiva(false);
		} else {
			restricao.setIcAtiva(true);
		}
		restricaoDAO.merge(restricao);
	}

	@Transactional
	public void merge(Restricao restricao) throws SIIACException {
		restricao.setIcAtiva(true);
		restricaoDAO.merge(restricao);		
	}

	@Transactional
	public boolean exist(Restricao restricao) throws SIIACException {
		if (Utilities.notEmpty(restricao)) {
			Criteria c = restricaoDAO.getCriteria();
			 
			//Existe pelo código e sistema destino
			if (Utilities.notEmpty(restricao.getCoRestricao())) {
				c.add(Restrictions.eq("coRestricao", restricao.getCoRestricao()));
			}
			if (Utilities.notEmpty(restricao.getSistemaDestino())) {
				c.add(Restrictions.eq("sistemaDestino", restricao.getSistemaDestino()).ignoreCase());
			}
			
			if(Utilities.notEmpty(restricao.getNuRestricao())){
				c.add(Restrictions.ne("nuRestricao", restricao.getNuRestricao()));
			}
			c.setProjection(Projections.rowCount());
			return ((Integer) c.uniqueResult()).intValue() > 0;
		}
		return false;
	}

	@Transactional
	public boolean isAtivo(Restricao restricao) {
		Criteria c = restricaoDAO.getCriteria();
		c.add(Restrictions.eq("nuRestricao", restricao.getNuRestricao()));
		c.setProjection(Property.forName("icAtiva"));
		return Boolean.valueOf(String.valueOf(c.uniqueResult()));
	}
	
	@Transactional
	public List<Restricao> getAllApontamento(ApontamentoChecklistProduto apontamento) throws DAOException {
		return restricaoDAO.getAllApontamento(apontamento);
	}
	
	@Transactional
	public List<Restricao> getAllNotApontamento(ApontamentoChecklistProduto apontamento) throws DAOException {
		return restricaoDAO.getAllNotApontamento(apontamento);
	}
}
