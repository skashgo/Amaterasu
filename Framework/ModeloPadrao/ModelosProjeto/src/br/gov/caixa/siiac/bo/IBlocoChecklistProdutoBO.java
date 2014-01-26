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
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;

/**
 * @author GIS Consult
 *
 */
public interface IBlocoChecklistProdutoBO {
	
	/**
	 * Retorna todos os blocos checklists existentes no sistema.
	 * @return lista de BlocoChecklistProduto
	 */
	public List<BlocoChecklistProduto> getAll() throws DAOException;
	
	/**
	 * Busca a última ordem existente no banco.
	 * @return última ordem
	 * @throws DAOException
	 */
	public Integer getMaxOrdem(Integer idChecklist) throws DAOException;
	
	public BlocoChecklistProduto merge(BlocoChecklistProduto blocoChecklistProduto) throws DAOException;
	
	/**
	 * Remove o bloco passado como parametro.
	 * @param blocoParaRemover
	 * @throws DAOException
	 */
	public void removeBloco(BlocoChecklistProduto blocoParaRemover) throws DAOException;
	
	public BlocoChecklistProduto findById(Integer codBloco);
	
}
