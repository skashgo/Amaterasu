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
package br.gov.caixa.siiac.bo;

import java.math.BigDecimal;
import java.util.List;

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.ProdutoVO;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.util.FilterBase;

/**
 * @author GisConsult
 * @author GisConsult
 *
 */
public interface IProdutoBO {
	
	/**
	 * Busca o produto pelo id.
	 * @param codProduto
	 * @return
	 */
	Produto findById(String codProduto);
	
	Produto findEagerById(String codProduto);
	
	/**
	 * Retorna uma lista de produtos com base no nome
	 * @param nome
	 * @return
	 */
	List<Produto> findByNome(String nome);
	
	/**
	 * Retorna uma lista de produtos com base no nome e na operação
	 * @param nome
	 * @param operacao
	 * @return
	 */
	List<Produto> findByNomeAndOperacao(String nome, Short operacao);
	
	/**
	 * Retorna uma lista de produtos com base na operacao
	 * fornecida.
	 * @param produtosAbrangentes 
	 */
	List<Produto> findByOperacao(Short operacao, List<String> produtosAbrangentes);
	
	/**
	 * Retorna a lista com todos os produtos.
	 * @return
	 */
	List<Produto> getListProduto();
	
	/**
	 * Retorna a lista com todos os produtos de acordo com a categoria.
	 * @return
	 */
	List<Produto> getListProdutoByCategoria(Integer nuCategoria);

	/**
	 * getPesquisaProduto
	 * 
	 * @param campoNome         => Pesquisar por Nome  
	 * @param campoSituacao		=> Pesquisar por Situacao (ativa / inativa)
	 * @param campoOperacao		=> Pesquisar por Opera��o
	 * @param campoModalidade	=> Pesquisar por Modalidade
	 * @param campoSiglaSistema => Pesquisar por Sigla Sistema
	 * @param campoTipoPessoa	=> Pesquisar por Tipo de Pessoa (Fisica / Juridica)
	 * @param campoValorLimite  => Pesquisar por Valor Limite
	 * 
	 * @return List <Produto>
	 * @throws SIIACException 
	 */	
	List<Produto> getPesquisaProduto(String campoNome, 
									boolean campoSituacao, 
									String campoOperacao, 
									String campoModalidade, 
									String campoSiglaSistema, 
									BigDecimal campoValorLimite,
									String campoTipoPessoa, 
									String campoNuCategoriaProduto, 
									boolean fazConsulta) throws SIIACException;
	

	List<Produto> getPesquisaProduto(FilterBase filtro, boolean fazConsulta) throws SIIACException;
	
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
	 * Altera o estado do produto (ativo/inativo).
	 * @param coProduto
	 * @param ativar
	 * @throws SIIACException
	 */
	public void ativarDesativarProduto(String coProduto, boolean ativar) throws SIIACException;
	

	/**
	 * Recupera List<Produto> vinculados a matricula passada como parametro 
	 * 		que nao estao selecionados como preferenciais.
	 * @param matricula
	 * @return
	 */
	List<Produto> getAllNaoPreferenciais(String matricula, Short nuPerfil);
	
	@Deprecated
	List<Produto> getListProdutoFiltroSimples(String pesquisa, Boolean mostrarInativos) throws SIIACException;
	
	List<Produto> getListProdutoFiltroSimples(FilterBase filtro) throws SIIACException;

	
	ProdutoVO getProdutoSIICO(String nuOperacao);
	
	public boolean existeProdutoSIICO(String nuOperacao);
	
	public boolean verificaProdutoAtivoInativo(String coProduto, boolean ativo);
	
	public boolean verificaNomeProduto(String noProduto);

	/**
	 * @param produtoAbrangentes
	 * @return
	 */
	List<Produto> getProdutosByListCoProduto(List<String> produtoAbrangentes);
}
