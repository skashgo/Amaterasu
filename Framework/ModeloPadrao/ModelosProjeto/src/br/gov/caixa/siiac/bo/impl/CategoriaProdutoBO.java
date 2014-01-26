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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.ICategoriaProdutoBO;
import br.gov.caixa.siiac.model.domain.CategoriaProduto;
import br.gov.caixa.siiac.persistence.dao.ICategoriaProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IProdutoUsuarioDAO;

@Service
public class CategoriaProdutoBO implements ICategoriaProdutoBO{
	
	private ICategoriaProdutoDAO categoriaProdutoDAO;
	private IProdutoUsuarioDAO produtoUsuarioDAO;

	@Autowired
	public void setCategoriaProdutoDAO(ICategoriaProdutoDAO categoriaProdutoDAO) {
		this.categoriaProdutoDAO = categoriaProdutoDAO;
	}
	
	@Transactional
	public void save(CategoriaProduto categoriaProduto) {
		categoriaProdutoDAO.merge(categoriaProduto);
	}

	@Transactional
	public CategoriaProduto findById(Integer codCategoria) {
		return categoriaProdutoDAO.findById(codCategoria);
	}

	@Transactional
	public List<CategoriaProduto> getListFiltro(String pesquisaString, boolean pesquisaMostraInativos) {
		return categoriaProdutoDAO.getListFiltro(pesquisaString, pesquisaMostraInativos);
	}
	
	@Transactional
	public void setProdutoUsuarioDAO(IProdutoUsuarioDAO produtoUsuarioDAO) {
		this.produtoUsuarioDAO = produtoUsuarioDAO;
	}

	@Transactional
	public boolean exist(CategoriaProduto categoriaProduto) {
		return categoriaProdutoDAO.exist(categoriaProduto);
	}
	
	@Transactional
	public List<CategoriaProduto> getAllNaoPreferenciais(String matricula, Short nuPerfil) {
		// Valida se o usuário possui todos os produtos vinculados com ele.
		boolean usuarioTemTodosProdutos = produtoUsuarioDAO.getCountProdutoUsuario(matricula).equals(0);
		
		if(usuarioTemTodosProdutos) {
			return categoriaProdutoDAO.getCategoriasProdutos(matricula, nuPerfil);
		} else {
			return categoriaProdutoDAO.getCategoriasUsuario(matricula, nuPerfil);
		}
	}
	
	@Transactional
	public List<CategoriaProduto> getCategoriasUsuario(String matricula, Short nuPerfil)
	{
		return categoriaProdutoDAO.getCategoriasUsuario(matricula, nuPerfil);
	}
	
	@Transactional
	public List<CategoriaProduto> getCategoriasPreferenciaisUsuario(String matricula)
	{
		Criteria criteria = categoriaProdutoDAO.getCriteria();
		criteria.createAlias("preferenciasUsuarioCategoriaProduto", "p");
		criteria.add(Restrictions.eq("p.id.coUsuario", matricula));
		
		return categoriaProdutoDAO.findByCriteria(criteria);
	}
}