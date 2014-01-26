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
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.Restricao;

public interface IRestricaoBO {

	/**
	 * @param restricao
	 * @return lista de restricoes de acordo com os atributos
	 * 			preenchidos no objeto passado como parametro.
	 */
	List<Restricao> getListFiltro(String pesquisa, Boolean mostrarInativos) throws SIIACException;

	/**
	 * Ativa a retricao caso ela esteja inativada, caso contrario
	 * a inativa.
	 * @param restricao
	 */
	void ativaInativa(Restricao restricao) throws SIIACException;

	/**
	 * Insere a retricao caso ela nao exista no banco de dados,
	 * caso contrario realiza sua atualizacao.
	 * @param restricao
	 */
	void merge(Restricao restricao) throws SIIACException;

	/**
	 * Verifica se a retricao existe no banco de dados.
	 * @param restricao
	 * @return true se a retricao existe ou false caso contrario
	 */
	boolean exist(Restricao restricao) throws SIIACException;

	/**
	 * Valida se a retricao a ser salva esta ativa na base.
	 * @param restricao
	 * @return true se a retricao ou false caso contrario
	 */
	boolean isAtivo(Restricao restricao);
	
	/**
	 * Retorna todas as restrições não vinculadas a determinado apontamento.
	 * @param apontamento
	 * @return lista de restricoes
	 * @throws DAOException
	 */
	List<Restricao> getAllNotApontamento(ApontamentoChecklistProduto apontamento) throws DAOException;
	
	/**
	 * Retorna todas as restrições pertecentes a determinado apontamento.
	 * @param apontamento
	 * @return lista de restrições
	 * @throws DAOException
	 */
	List<Restricao> getAllApontamento(ApontamentoChecklistProduto apontamento) throws DAOException;

}
