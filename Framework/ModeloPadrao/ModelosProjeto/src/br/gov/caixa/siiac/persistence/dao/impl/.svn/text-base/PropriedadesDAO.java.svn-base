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

import org.hibernate.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.Propriedades;
import br.gov.caixa.siiac.persistence.dao.IPropriedadesDAO;

@Repository
@Scope("prototype")
public class PropriedadesDAO extends GenericDAO<Propriedades> implements IPropriedadesDAO {
	
	public PropriedadesDAO() {
		super(Propriedades.class);
	}
	
	public String getPropriedade(String propriedade) {
		Query q = getSession().createQuery("SELECT p.noValor FROM Propriedades p WHERE p.noPropriedade = :propriedade");
		q.setParameter("propriedade", propriedade);
		return (String) q.uniqueResult();
	}

	public List<String> getPropriedades(String noGrupo) {
		Query q = getSession().createQuery("SELECT p.noValor FROM Propriedades p WHERE p.noGrupo = :noGrupo");
		q.setParameter("noGrupo", noGrupo);
		return q.list();
	}
}