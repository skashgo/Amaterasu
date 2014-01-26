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

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ItemVerificacao;

/**
 * @author GisConsult
 *
 */
public interface IItemVerificacaoBO {
	
	/**
	 * Busca todos os itens de verificacao ativos.
	 * @return todos os itens de verificacao ativos no banco
	 */
	List<ItemVerificacao> getAllAtivos();
	
	/**
	 * Busca todos os itens de verificacao inativos.
	 * @return todos os itens de verificacao inativos no banco
	 */
	List<ItemVerificacao> getAllInativos();
	
	/**
	 * Persiste determinado item de verificacao
	 * @param itemVerificacao
	 */
	void salvar(ItemVerificacao itemVerificacao);

	/**
	 * Verifica se determinado item de verificacao
	 * ja esta contido no banco de dados.
	 * @param itemVerificacao
	 * @return true se o item de verificacao já existe ou false caso não exista
	 */
	boolean jaExiste(ItemVerificacao itemVerificacao) throws SIIACException;
	
	/**
	 * Busca uma lista de servicos de verificacao.
	 * @param itemVerificacao
	 * @param cadastro
	 * @return
	 */
	List<ItemVerificacao> realizaConsulta(ItemVerificacao itemVerificacao, boolean cadastro);
	
	/**
	 * Ativa determinado item de verificacao
	 * @param itemVerificacao
	 */
	void ativar(ItemVerificacao itemVerificacao);
	
	/**
	 * Inativa determinado item de verificacao
	 * @param itemVerificacao
	 */
	void inativar(ItemVerificacao itemVerificacao);
	
	/**
	 * Atualiza o item de verificacao.
	 * @param itemVerificacao
	 */
	void update(ItemVerificacao itemVerificacao);
	
	/**
	 * Busca todos os itens de verificaçã não vinculados
	 * ao checklist atual.
	 * @return lista de itens de verificação
	 */
	List<ItemVerificacao> getAllNotBloco(BlocoChecklistProduto bloco) throws DAOException;
	
	/**
	 * Busca todos os itens de verificaçã vinculados
	 * ao checklist atual.
	 * @return lista de itens de verificação
	 */
	List<ItemVerificacao> getAllBloco(BlocoChecklistProduto bloco) throws DAOException;

	/**
	 * @param pesquisaString
	 * @param pesquisaMostraInativos
	 * @return
	 */
	List<ItemVerificacao> getListFiltro(String pesquisaString, Boolean pesquisaMostraInativos, Boolean controleDataValidade) throws SIIACException ;

	/**
	 * @param itemVerificacao
	 * @return
	 */
	boolean isAtivo(ItemVerificacao itemVerificacao);
}
