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
package br.gov.caixa.siiac.persistence.dao;

import java.util.List;

import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ProdutoUsuario;

/**
 * @author GisConsult
 *
 */
public interface IProdutoUsuarioDAO extends IGenericDAO<ProdutoUsuario> {

	/**
	 * Retorna os produtos associados a determinado usuario.
	 * @param matricula
	 * @return
	 */
	List<ProdutoUsuario> getAllAssociados(String matricula);
	
	/**
	 * Retorna os produtos associados a determinado usuario. Utilizando like
	 * @param matricula
	 * @param coProduto
	 * @param produtoAbrangentes 
	 * @return
	 */
	List<ProdutoUsuario> getAllAssociadosLikeProduto(String matricula, String coProduto, List<String> produtoAbrangentes);
	List<ProdutoUsuario> getAllAssociadosLikeProdutoByCategoria(String matricula, String coProduto, Integer nuCategoria, List<String> produtoAbrangentes);
	
	
	/**
	 * Busca todos os produtos existentes na tabela iactb001_produto que nao estao
	 * associados com o usuario corrente, ou seja, com a matricula fornecida na busca.
	 * A associacao dos produtos com seus respectivos usuarios e realizada na tabela 
	 * iactb038_produto_usuario.
	 * @param matricula
	 * @return todos os produtos que nao estao associados com a matricula fornecida
	 */
	List<Produto> getAllNaoAssociados(String matricula);
	
	void limpaProdutosUsuario(String matricula);
	
	/**
	 * Limpa a tabela iacsm001.iactb038_produto_usuario, desassociando os produtos existentes
	 * a determinado usuario, de acordo com a matricula passada como parametro.
	 * @param matricula
	 */
	void limpaProdutosUsuarioAssociados(String matricula);
	
	/**
	 * Apaga do banco todos os produtos pertecentes a determinada matricula
	 * que nao constam na lista de produtos passada como parametro
	 * @param matricula
	 * @param produtos
	 */
	void deleteAllProdutosNaoExistentesNaLista(String matricula, List<ProdutoUsuario> produtos);
	
	/**
	 * Grava uma lista de produtos pertecentes a determinada matricula
	 * @param matricula
	 * @param produtos
	 */
	void saveAllProdutosDaLista(String matricula, List<ProdutoUsuario> produtos);

	/**
	 * @param matricula
	 * @param coProduto
	 * @return 
	 */
	boolean gravarProduto(String matricula, String coProduto);

	/**
	 * @param matricula
	 * @param coProduto
	 */
	boolean deletarProduto(String matricula, String coProduto);

	/**
	 * @param matricula
	 */
	void adjustAllProdutosPreferenciais(String matricula);

	/**
	 * @param matricula
	 */
	void adjustAllServicosPreferenciais(String matricula);

	/**
	 * Retorna a quantidade de produtos que o usuário possui vinculado a ele na tabela ProdutoUsuario
	 * @param matricula
	 * @return
	 */
	Integer getCountProdutoUsuario(String matricula);
	Integer getCountProdutoUsuarioByCategoria(String matricula, int nuCategoria);

	/**
	 * Retorna uma lista de coProduto da tabela ProdutoUsuario filtrando pelo coUsuario
	 * @param matriculaUserLogado
	 * @return
	 */
	List<String> listProdutoUsuarioByCoProduto(String matriculaUserLogado);
}
