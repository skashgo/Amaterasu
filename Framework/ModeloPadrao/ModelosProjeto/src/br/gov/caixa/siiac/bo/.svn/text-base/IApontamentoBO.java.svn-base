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
import br.gov.caixa.siiac.model.domain.Apontamento;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;

public interface IApontamentoBO {

	/**
	 * Retorna a lista de apontamentos de acordo com o filtro passado.
	 * @param apontamento
	 * @return lista de apontamentos.
	 * @throws SIIACException
	 */
	public List<Apontamento> getListFiltro(String pesquisa, Boolean ativo) throws SIIACException;

	/**
	 * Valida se o apontamento existe na base de dados
	 * @param apontamento
	 * @return true se o apontamento existe ou false caso contrario.
	 * @throws SIIACException
	 */
	public Boolean exist(Apontamento apontamento)throws SIIACException;
	
	/**
	 * Atribui ativo para o apontamento e o salva na base de dados.
	 * @param apontamento
	 * @throws SIIACException
	 */
	public void merge(Apontamento apontamento) throws SIIACException;
	
	/**
	 * Altera o estado atual do apontamento (ativo => inativo e inativo => ativo) 
	 * e o salva na base de dados.
	 * @param apontamento
	 * @throws SIIACException
	 */
	public void ativaInativa(Apontamento apontamento) throws SIIACException;
	
	/**
	 * Retorna todos os apontamnetos não vinculados a determinado item.
	 * @param item
	 * @return lista de apontamentos
	 * @throws DAOException
	 */
	List<Apontamento> getAllNotItem(ItemVerificacaoChecklistProduto item) throws DAOException;
	
	/**
	 * Retorna todos os apontamnetos vinculados a determinado item.
	 * @param item
	 * @return
	 * @throws DAOException
	 */
	List<Apontamento> getAllItem(ItemVerificacaoChecklistProduto item) throws DAOException;

	/**
	 * @param apontamento
	 * @return
	 */
	public boolean isAtivo(Apontamento apontamento); 
}
