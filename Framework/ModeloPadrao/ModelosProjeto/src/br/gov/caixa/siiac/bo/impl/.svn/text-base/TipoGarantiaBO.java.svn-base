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

import br.gov.caixa.siiac.bo.ITipoGarantiaBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Apontamento;
import br.gov.caixa.siiac.model.domain.TipoGarantia;
import br.gov.caixa.siiac.persistence.dao.ITipoGarantiaDAO;
import br.gov.caixa.util.Utilities;

@Service
@Scope("prototype")
public class TipoGarantiaBO implements ITipoGarantiaBO{

	private transient ITipoGarantiaDAO tipoGarantiaDAO;

	@Transactional
	public void save(TipoGarantia tipoGarantia) {
		tipoGarantiaDAO.merge(tipoGarantia);
	}
	@Autowired
	public void setTipoGarantiaDAO(ITipoGarantiaDAO tipoGarantiaDAO) {
		this.tipoGarantiaDAO = tipoGarantiaDAO;
	}
	
	@Transactional
	public void merge(TipoGarantia tipoGarantia) throws SIIACException {
		tipoGarantia.setIcAtivo(true);
		tipoGarantiaDAO.merge(tipoGarantia);
	}
	
	@Transactional
	public Boolean exist(TipoGarantia tipoGarantia) throws SIIACException {
		if (Utilities.notEmpty(tipoGarantia)) {
					
			//Existe pelo nome
			Criteria c = tipoGarantiaDAO.getCriteria();
			if (Utilities.notEmpty(tipoGarantia.getNoTipoGarantia())) {
				c.add(Restrictions.eq("noTipoGarantia", tipoGarantia.getNoTipoGarantia()).ignoreCase());
				if(Utilities.notEmpty(tipoGarantia.getNuTipoGarantia())){
					c.add(Restrictions.ne("nuTipoGarantia", tipoGarantia.getNuTipoGarantia()));
				}
			}
			c.setProjection(Projections.rowCount());
			return ((Integer) c.uniqueResult()).intValue() > 0;
		}
		return false;
	}
	
	@Transactional
	public void ativaInativa(TipoGarantia tipoGarantia) throws SIIACException{
		if(tipoGarantia.getIcAtivo()) {
			tipoGarantia.setIcAtivo(false);
		} else {
			tipoGarantia.setIcAtivo(true);
		}
		tipoGarantiaDAO.merge(tipoGarantia);
	}

	@Transactional
	public List<TipoGarantia> getListFiltro(String pesquisa, Boolean mostrarInativos) throws SIIACException {
		Criteria c = tipoGarantiaDAO.getCriteria();
		if (Utilities.notEmpty(pesquisa)) {
			c.add(Restrictions.ilike("noTipoGarantia", pesquisa, MatchMode.ANYWHERE));
		}
		if (Utilities.notEmpty(mostrarInativos) && mostrarInativos.equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		c.addOrder(Order.asc("noTipoGarantia"));
		return tipoGarantiaDAO.findByCriteria(c);
	}
	
	@Transactional
	public boolean isAtivo(TipoGarantia tipoGarantia) {
		Criteria c = tipoGarantiaDAO.getCriteria();
		c.add(Restrictions.eq("nuTipoGarantia", tipoGarantia.getNuTipoGarantia()));
		c.setProjection(Property.forName("icAtivo"));
		return Boolean.valueOf(String.valueOf(c.uniqueResult()));
	}
}