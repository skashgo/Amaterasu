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
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.ITipoUnidadeBO;
import br.gov.caixa.siiac.model.domain.TipoUnidade;
import br.gov.caixa.siiac.persistence.dao.ITipoUnidadeDAO;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class TipoUnidadeBO implements ITipoUnidadeBO{

	private ITipoUnidadeDAO tipoUnidadeDAO;
	
	
	@Autowired
	public void setTipoUnidadeDAO(ITipoUnidadeDAO tipoUnidadeDAO) {
		this.tipoUnidadeDAO = tipoUnidadeDAO;
	}



	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.ITipoUnidadeBO#findAll()
	 */
	@Transactional
	public List<TipoUnidade> findAll() {
		Criteria c = tipoUnidadeDAO.getCriteria();
		
		c.addOrder(Order.asc("deTipoUnidade"));
		
		return tipoUnidadeDAO.findByCriteria(c);
	}
	
	@Transactional
	public String getNomeTipoUnidadeById(Short nuTipoUnidade)
	{
		return tipoUnidadeDAO.getNomeTipoUnidadeById(nuTipoUnidade);
	}
	
}
