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

import java.math.BigDecimal;
import java.util.List;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.ProdutoVO;
import br.gov.caixa.siiac.model.domain.Produto;

/**
 * @author GisConsult
 * @author GisConsult
 *
 */
public interface IProdutoDAO extends IGenericDAO<Produto>{

	/**
	 * getPesquisaProduto
	 * 
	 * @param campoNome         => Pesquisar por Nome  
	 * @param campoSituacao		=> Pesquisar por Situacao (ativa / inativa)
	 * @param campoOperacao		=> Pesquisar por Operacao
	 * @param campoModalidade	=> Pesquisar por Modalidade
	 * @param campoSiglaSistema => Pesquisar por Sigla Sistema
	 * @param campoTipoPessoa	=> Pesquisar por Tipo de Pessoa (Fisica / Juridica)
	 * @param campoValorLimite  => Pesquisar por Valor Limite
	 * 
	 * @return List <Produto>
	 * @throws SIIACException 
	 */
	List<Produto> getPesquisaProduto(String campoNome, 
 									 Boolean campoSituacao,  
									 String campoOperacao, 
									 String campoModalidade, 
									 String campoSiglaSistema, 
 									 BigDecimal campoValorLimite, 
									 String campoTipoPessoa, 
									 String campoNuCategoriaProduto, 
									 boolean fazConsulta) throws SIIACException;

	/**
	 * gravarProduto
	 * @param	(String)coProduto 		-> codigo do Produto a ser gravado
	 * @param 	(String)noProduto 		-> Nome do Produto 
	 * @param 	(Short)nuModalidade 	-> Numero da Modalidade
	 * @param 	(Short)nuOperacao 		-> Numero da Operacao
	 * @param 	(String)sgSistemaOrigem -> Sigla do Sistema de Origem
	 * @return	boolean					-> true salvou / false erro 
	 */
	boolean gravarProduto(String coProduto, 
						  String noProduto, 
						  short nuModalidade, 
						  short nuOperacao, 
						  String sgSistemaOrigem, 
 						  BigDecimal vrLimiteVerificacao, 
						  String icPfPj, 
						  int nuCategoriaProduto, 
						  boolean icAtivo,
						  boolean altera,
						  String noOperacao,
						  String noModalidade);

	/**
	 * ativarDesativarProduto
	 * 
	 * @param coProduto - Codigo do Produto
	 * @param ativar - true = ativa o produto / false = desativa
	 * @throws DAOException
	 */
	public void ativarDesativarProduto(String coProduto, boolean ativar) throws SIIACException;
	
	/**
	 * Busca uma lista de produtos baseado em seu nome.
	 * @param nome
	 * @return lista de produtos 
	 */
	public List<Produto> findByNome(String nome);
	
	/**
	 * Busca uma lista de produto baseado em sua operacao.
	 * @param operacao
	 * @param produtosAbrangentes 
	 * @return lista de produtos
	 */
	List<Produto> findByOperacao(Short operacao, List<String> produtosAbrangentes);
	
	/**
	 * Busca produtos baseado na operação e no nome.
	 * @param nome
	 * @param operacao
	 * @return lista de produtos
	 */
	List<Produto> findByNomeAndOperacao(String nome, Short operacao);

	
	/**
	 * Retorna os produtos associados (na tabela ProdutoUsuario) a determinado usuario que nao estao marcados como preferenciais.
	 * @param matricula
	 * @return
	 */
	List<Produto> getAllNaoPreferenciaisFromProdutoUsuario(String matricula, Short nuPerfil);

	/**
	 * Retorna todos os produtos do sistema que nao estao marcados como preferenciais do usuario.
	 * @param matricula
	 * @return
	 */
	List<Produto> getAllNaoPreferenciaisFromProduto(String matricula, Short nuPerfil);
	
	List<Produto> getProdutosAtivosOrdenadosByCodLikeProduto(String coProduto, List<String> produtoAbrangentes);
	List<Produto> getProdutosAtivosOrdenadosByCodLikeProdutoByCategoria(String coProduto, Integer nuCategoria,  List<String> produtoAbrangentes);
	
	ProdutoVO getProdutoSIICO(String nuOperacao);
	
	public boolean existeProdutoSIICO(String nuOperacao);
	List<Produto> getProdutosByListCoProduto(List<String> produtoAbrangentes);
	
}