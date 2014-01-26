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
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioServico;
import br.gov.caixa.siiac.persistence.dao.IPreferenciaUsuarioServicoDAO;

/**
 * @author GisConsult
 *
 */
@Repository
@Scope("prototype")
public class PreferenciaUsuarioServicoDAO extends GenericDAO<PreferenciaUsuarioServico>
												implements IPreferenciaUsuarioServicoDAO {

	/**
	 * @param persistenceClass
	 */
	public PreferenciaUsuarioServicoDAO() {
		super(PreferenciaUsuarioServico.class);
	}

	@SuppressWarnings("unchecked")
	public void deleteAll(String matricula, List<PreferenciaUsuarioServico> servicos) {
				
		List<Integer> lista = getCriteria()
							.add(Restrictions.eq("id.coUsuario", matricula))
							.setProjection(Projections.property("id.nuServicoVerificacao")).list();
		
		for(Integer valor : lista){
			boolean contain = false;
			for(PreferenciaUsuarioServico p : servicos){
				if(p.getServicoVerificacao().getNuServicoVerificacao() == valor){
					contain = true;
					break;
				}
			}
			
			if(!contain){
				SQLQuery q = getSession().createSQLQuery("DELETE FROM iacsm001.iactb041_preferencia_usro_srvco WHERE nu_servico_verificacao = :servicoverificacao ").addEntity(PreferenciaUsuarioServico.class);
				q.setInteger("servicoverificacao", valor);
				for (Object o : q.list()) {
					PreferenciaUsuarioServico pus = (PreferenciaUsuarioServico) o;
					delete(pus);
				}
			}
		}
		
		getSession().flush();
	}
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IPreferenciaUsuarioServicoDAO#deleteAll(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void saveAll(String matricula, List<PreferenciaUsuarioServico> servicos) {

		List<Integer> lista = getCriteria()
							.add(Restrictions.eq("id.coUsuario", matricula))
							.setProjection(Projections.property("id.nuServicoVerificacao")).list();
		
		
		for(PreferenciaUsuarioServico p : servicos){
			boolean contain = false;
			for(Integer valor : lista){
				if(p.getServicoVerificacao().getNuServicoVerificacao() == valor){
					contain = true;
					break;
				}
			}
			
			if(!contain){
				merge(p);
			}
		}
		
		getSession().flush();
	}

}
