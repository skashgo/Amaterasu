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

import java.util.List;

import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ProdutoUsuario;

/**
 * @author GisConsult
 *
 */
public interface IProdutoUsuarioBO {

	/**
	 * Recupera List<ProdutoUsuario> vinculados a matricula passada como parametro.
	 * @param matricula
	 * @return
	 */
	List<ProdutoUsuario> getAllAssociados(String matricula);

	/**
	 * Recupera o ProdutoUsuario de acordo com o nuProdutoUsuario passado como parametro.
	 * @param itemProduto
	 * @param matricula
	 * @return
	 */
	ProdutoUsuario get(Integer nuProdutoUsuario);
	

	/**
	 * Busca todos os produtos existentes na tabela iactb001_produto que nao estao
	 * associados com o usuario corrente, ou seja, com a matricula fornecida na busca.
	 * A associacao dos produtos com seus respectivos usuarios e realizada na tabela 
	 * iactb038_produto_usuario.
	 * @param matricula
	 * @return todos os produtos que n�o est�o associados com a matricula fornecida
	 */
	List<Produto> getAllNaoAssociados(String matricula);
	
	/**
	 * Grava todos os produtos existentes no grid da p�gina.
	 * @param matricula
	 * @param produtos
	 */
	void saveAllProdutosDaLista(String matricula, List<ProdutoUsuario> produtos);
	
	/**
	 * Verifica todos os produtos vinculados a determinada matricula recebida como parametro
	 * e compara os mesmos com os produtos existentes na lista recebida como parametro.
	 * Essa comparacao tem por objetivo eliminar os produtos existentes no banco 
	 * que nao existem na lista.
	 * @param matricula
	 * @param produtos
	 */
	void deleteAllProdutosNaoExistentesNaLista(String matricula, List<ProdutoUsuario> produtos);
	
	/**
	 * Vincula determinado produto a determinada matricula.
	 * @param matricula
	 * @param coProduto
	 * @return true se o produto foi gravado com sucesso ou false caso contr�rio
	 */
	boolean gravarProduto(String matricula, String coProduto);

	/**
	 * Desvincula determinado produto a determinada matricula.
	 * @param matricula
	 * @param coProduto
	 * @return true se produto foi apagado com sucesso ou false caso contrario.
	 */
	boolean deletarProduto(String matricula, String coProduto);

	/**
	 * Ajusta a lista de produtos preferenciais, deletando aqueles produtos que não estão mais vinculados ao usuário
	 * @param matricula
	 */
	void adjustAllProdutosPreferenciais(String matricula);

	/**
	 * Ajusta a lista de serviços preferenciais, deletando aqueles serviços que seus produtos não estão mais vinculados ao usuário
	 * @param matricula
	 */
	void adjustAllServicosPreferenciais(String matricula);
}
