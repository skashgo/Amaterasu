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
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import br.gov.caixa.siiac.bo.IPreferenciaUsuarioGerenteBO;
import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioGerente;
import br.gov.caixa.siiac.persistence.dao.IPreferenciaUsuarioGerenteDAO;

/**
 * @author GIS Consult
 *
 */

@Service
@Scope("prototype")
public class PreferenciaUsuarioGerenteBO implements IPreferenciaUsuarioGerenteBO {
	private transient IPreferenciaUsuarioGerenteDAO preferenciaUsuarioGerenteDAO;


	public IPreferenciaUsuarioGerenteDAO getPreferenciaUsuarioGerenteDAO() {
		return preferenciaUsuarioGerenteDAO;
	}

	@Autowired
	public void setPreferenciaUsuarioGerenteDAO(
			IPreferenciaUsuarioGerenteDAO preferenciaUsuarioGerenteDAO) {
		this.preferenciaUsuarioGerenteDAO = preferenciaUsuarioGerenteDAO;
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.impl.IPreferenciaUsuarioGerenteBO#salvarLista(java.lang.String, java.util.List)
	 */
	@Transactional
	public void salvarLista(String matricula, List<PreferenciaUsuarioGerente> gerentes){
		preferenciaUsuarioGerenteDAO.deleteAll(matricula);
		preferenciaUsuarioGerenteDAO.saveAll(matricula, gerentes);
	}
	
	@Transactional
	public List<PreferenciaUsuarioGerente> getAll(String matricula) {
		Criteria criteria = preferenciaUsuarioGerenteDAO.getCriteria().add(Restrictions.eq("id.coUsuario", matricula));
		
		return preferenciaUsuarioGerenteDAO.findByCriteria(criteria);
	}


	
	

}
