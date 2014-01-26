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

import org.hibernate.SQLQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioGerente;
import br.gov.caixa.siiac.persistence.dao.IPreferenciaUsuarioGerenteDAO;


/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class PreferenciaUsuarioGerenteDAO extends GenericDAO<PreferenciaUsuarioGerente> implements IPreferenciaUsuarioGerenteDAO {

	/**
	 * @param persistenceClass
	 */
	public PreferenciaUsuarioGerenteDAO() {
		super(PreferenciaUsuarioGerente.class);
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.impl.PreferenciaUsuarioGerenteDA#saveAll(java.lang.String, java.util.List)
	 */
	public void saveAll(String matricula, List<PreferenciaUsuarioGerente> gerentes) {
		
		for(PreferenciaUsuarioGerente p : gerentes){
				merge(p);
		}
		getSession().flush();
	}
	
	public void deleteAll(String matricula) {
				
		SQLQuery q = getSession().createSQLQuery("DELETE FROM iacsm001.iactb068_preferencia_grne_prcr WHERE co_usuario  = :coUsuario ").addEntity(PreferenciaUsuarioGerente.class);
		q.setString("coUsuario", matricula);
		for (Object o : q.list()) {
			PreferenciaUsuarioGerente pug = (PreferenciaUsuarioGerente) o;
			delete(pug);
		}
	}
	
	

	
	

}
