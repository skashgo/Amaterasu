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

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.PreferenciasUsuarioCategoriaProduto;
import br.gov.caixa.siiac.persistence.dao.IPreferenciasUsuarioCategoriaProdutoDAO;

/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class PreferenciasUsuarioCategoriaProdutoDAO extends GenericDAO<PreferenciasUsuarioCategoriaProduto> implements IPreferenciasUsuarioCategoriaProdutoDAO {

	public PreferenciasUsuarioCategoriaProdutoDAO() {
		super(PreferenciasUsuarioCategoriaProduto.class);
	}


	public List<PreferenciasUsuarioCategoriaProduto> findByUsuario(String matricula) {
		String sql = "select * from iacsm001.iactb064_preferencia_usro_ctgra where co_usuario = :nuMatricula";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuMatricula", matricula);
		
		
		
		return query.list();
	}
	
	/**
	 * Verifica se determinada matrícula possui ou não categoria preferencial
	 * @param matricula
	 * @return
	 */
	public boolean hasPreferencial(String matricula){
		Criteria c = getCriteria()
		.add(Restrictions.eq("usuario.coUsuario", matricula))
		.setProjection(Projections.rowCount());
		return ((Integer) c.uniqueResult()).intValue() > 0;
	}


	@SuppressWarnings("unchecked")
	public void deleteAll(String matricula, List<PreferenciasUsuarioCategoriaProduto> categorias) {
		List<Integer> lista = getCriteria()
							.add(Restrictions.eq("id.coUsuario", matricula))
							.setProjection(Projections.property("id.nuCategoriaProduto")).list();
		
		for(Integer valor : lista){
			boolean contain = false;
			for(PreferenciasUsuarioCategoriaProduto p : categorias){
				if(p.getCategoriaProduto().getNuCategoriaProduto() == valor){
					contain = true;
					break;
				}
			}
			
			if(!contain){
				SQLQuery q = getSession().createSQLQuery("SELECT * FROM iacsm001.iactb064_preferencia_usro_ctgra WHERE nu_categoria_produto = :categoriaproduto AND co_usuario = :coUsuario ").addEntity(PreferenciasUsuarioCategoriaProduto.class);
				q.setInteger("categoriaproduto", valor);
				q.setString("coUsuario", matricula);
				for (Object o : q.list()) {
					PreferenciasUsuarioCategoriaProduto  pucp = (PreferenciasUsuarioCategoriaProduto) o;
					delete(pucp);
				}
			}
		}
		
		getSession().flush();
	}
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IPreferenciaUsuarioServicoDAO#deleteAll(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void saveAll(String matricula, List<PreferenciasUsuarioCategoriaProduto> categorias) {

		List<Integer> lista = getCriteria()
							.add(Restrictions.eq("id.coUsuario", matricula))
							.setProjection(Projections.property("id.nuCategoriaProduto")).list();
		
		
		for(PreferenciasUsuarioCategoriaProduto p : categorias){
			boolean contain = false;
			for(Integer valor : lista){
				if(p.getCategoriaProduto().getNuCategoriaProduto() == valor){
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
