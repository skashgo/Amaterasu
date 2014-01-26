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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IProdutoUsuarioBO;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ProdutoUsuario;
import br.gov.caixa.siiac.persistence.dao.IProdutoUsuarioDAO;

/**
 * @author GisConsult
 *
 */
@Service
@Scope("prototype")
public class ProdutoUsuarioBO implements IProdutoUsuarioBO {
	
	private transient IProdutoUsuarioDAO produtoUsuarioDAO;
	
	@Autowired
	public void setProdutoUsuarioDAO(IProdutoUsuarioDAO produtoUsuarioDAO){
		this.produtoUsuarioDAO = produtoUsuarioDAO;
	}

	@Transactional
	public ProdutoUsuario get(Integer nuProdutoUsuario) {
		return produtoUsuarioDAO.findById(nuProdutoUsuario);
	}
	
	@Transactional
	public List<ProdutoUsuario> getAllAssociados(String matricula){
		return this.produtoUsuarioDAO.getAllAssociados(matricula);
	}
	
	@Transactional
	public List<Produto> getAllNaoAssociados(String matricula) {
		List<Produto> produtos = produtoUsuarioDAO.getAllNaoAssociados(matricula);
		return produtos;
	}

	@Transactional
	public void saveAllProdutosDaLista(String matricula, List<ProdutoUsuario> produtos) {
		produtoUsuarioDAO.saveAllProdutosDaLista(matricula, produtos);	
	}
	
	@Transactional
	public void deleteAllProdutosNaoExistentesNaLista(String matricula, List<ProdutoUsuario> produtos) {
		produtoUsuarioDAO.deleteAllProdutosNaoExistentesNaLista(matricula, produtos);
	}
	
	@Transactional
	public boolean gravarProduto(String matricula, String coProduto) {
		return this.produtoUsuarioDAO.gravarProduto(matricula, coProduto);
	}

	@Transactional
	public boolean deletarProduto(String matricula, String coProduto) {
		return this.produtoUsuarioDAO.deletarProduto(matricula, coProduto);
	}

	@Transactional
	public void adjustAllProdutosPreferenciais(String matricula) {
		this.produtoUsuarioDAO.adjustAllProdutosPreferenciais(matricula);
	}

	@Transactional
	public void adjustAllServicosPreferenciais(String matricula) {
		this.produtoUsuarioDAO.adjustAllServicosPreferenciais(matricula);
	}
}
