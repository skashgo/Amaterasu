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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IPreferenciasUsuarioBO;
import br.gov.caixa.siiac.model.domain.CategoriaProduto;
import br.gov.caixa.siiac.model.domain.GerenteUnidade;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuario;
import br.gov.caixa.siiac.persistence.dao.IPreferenciasUsuarioDAO;

/**
 * @author GisConsult
 *
 */
@Service
@Scope("prototype")
public class PreferenciasUsuarioBO implements IPreferenciasUsuarioBO{
	
	private transient IPreferenciasUsuarioDAO preferenciasUsuarioDAO;
	
	@Autowired
	public void setPreferenciasUsuarioDAO(IPreferenciasUsuarioDAO preferenciasUsuarioDAO) {
		this.preferenciasUsuarioDAO = preferenciasUsuarioDAO;
	}

	@Transactional
	public List<PreferenciasUsuario> getAll(String matricula) {
		Criteria criteria = preferenciasUsuarioDAO.getCriteria().add(Restrictions.eq("id.coUsuario", matricula));
		
		return preferenciasUsuarioDAO.findByCriteria(criteria);
	}
	
	@Transactional
	public List<PreferenciasUsuario> getAll(String matricula, List<CategoriaProduto> categorias) {
		
		List<PreferenciasUsuario> retorno = new ArrayList<PreferenciasUsuario>();
		
		//Verificando se o usuário possui todos os produtos preferenciais
		if (preferenciasUsuarioDAO.getAll(matricula, "").isEmpty())
		{
			/*
			 * Possui todos os produtos preferênciais, portanto
			 * será feita uma busca em todos os produtos cadastrados passando como parâmetro as categorias
			 * resultantes do select acima
			 */ 
		}
		
		Criteria criteria = preferenciasUsuarioDAO.getCriteria();
		
		criteria.add(Restrictions.eq("id.coUsuario", matricula));
		
		if (!categorias.isEmpty())
		{
			criteria.createAlias("produto", "p");
			criteria.add(Restrictions.in("p.categoriaProduto", categorias));
		}

		
		return preferenciasUsuarioDAO.findByCriteria(criteria);
	}

	/*
	 * Esse metodo nao esta anotado como @Transactional pois o metodo no facade que o chama ja possui tal anotacao.
	 */
	public void salvaLista(String matricula, List<PreferenciasUsuario> produtos) {
		preferenciasUsuarioDAO.deleteAll(matricula, produtos);
		preferenciasUsuarioDAO.saveAll(matricula, produtos);
		
	}
	
	/**
	 * retorna os produtos preferenciais
	 * @param matricula
	 * @return
	 */
	@Transactional
	public List<String> listProdutoPreferencial(String matricula) {
		return preferenciasUsuarioDAO.listProdutoPreferencial(matricula);
	}
	/**
	 * retorna os serviços preferenciais
	 * @param matricula
	 * @return
	 */
	@Transactional
	public List<String> listServicoPreferencial(String matricula) {
		return preferenciasUsuarioDAO.listServicoPreferencial(matricula);
	}
	
	@Transactional
	public List<GerenteUnidade> getListGerentesUnidade(Short nuUnidade) {
		return preferenciasUsuarioDAO.getListGerentesUnidade(nuUnidade);
	}
}
