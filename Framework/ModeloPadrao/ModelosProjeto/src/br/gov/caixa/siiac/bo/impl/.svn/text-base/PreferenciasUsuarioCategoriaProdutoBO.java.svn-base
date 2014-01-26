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

import br.gov.caixa.siiac.bo.IPreferenciasUsuarioCategoriaProdutoBO;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuarioCategoriaProduto;
import br.gov.caixa.siiac.persistence.dao.IPreferenciasUsuarioCategoriaProdutoDAO;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class PreferenciasUsuarioCategoriaProdutoBO implements IPreferenciasUsuarioCategoriaProdutoBO {

	
	private transient IPreferenciasUsuarioCategoriaProdutoDAO preferenciasUsuarioCategoriaProdutoDAO;
	
	@Autowired
	public void setPreferenciasUsuarioCategoriaProdutoDAO(IPreferenciasUsuarioCategoriaProdutoDAO preferenciasUsuarioCategoriaProdutoDAO) {
		this.preferenciasUsuarioCategoriaProdutoDAO = preferenciasUsuarioCategoriaProdutoDAO;
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IPreferenciasUsuarioCategoriaProdutoBO#getAll(java.lang.String)
	 */
	@Transactional
	public List<PreferenciasUsuarioCategoriaProduto> getAll(String matricula) {
		Criteria criteria = preferenciasUsuarioCategoriaProdutoDAO.getCriteria().add(Restrictions.eq("id.coUsuario", matricula));
		return preferenciasUsuarioCategoriaProdutoDAO.findByCriteria(criteria);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IPreferenciasUsuarioCategoriaProdutoBO#salvaLista(java.lang.String, java.util.List)
	 */
	@Transactional
	public void salvaLista(String matricula, List<PreferenciasUsuarioCategoriaProduto> categorias) {
		preferenciasUsuarioCategoriaProdutoDAO.deleteAll(matricula, categorias);
		preferenciasUsuarioCategoriaProdutoDAO.saveAll(matricula, categorias);

	}

}
