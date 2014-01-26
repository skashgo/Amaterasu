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

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;

public interface IChecklistDAO extends IGenericDAO<ChecklistServicoVerificacaoProduto> {
	
	public List<ChecklistServicoVerificacaoProduto> findByChecklist(ChecklistServicoVerificacaoProduto checklist) throws DAOException;
	
	public List<ChecklistServicoVerificacaoProduto> filter(Short operacao, String codProduto, String noProduto, String situacao, 
			Integer codServicoVerificacao, Date dataInicio, Date dataFim, boolean revogado, Short nuCanal) throws DAOException;

	/**
	 * Retorna uma lista de checklist  filtrada pela situacao e data inicio
	 * @param chklstSituacaoAutorizado
	 * @param date
	 * @return
	 */
	public List<ChecklistServicoVerificacaoProduto> findByDataIniSituacao(String chklstSituacaoAutorizado, Date date);

	/**
	 * @param chklstSituacaoPublicado
	 * @param servicoVerificacaoProduto
	 * @return
	 */
	public ChecklistServicoVerificacaoProduto findBySituacaoServicoVerificacaoProduto(String chklstSituacaoPublicado, ServicoVerificacaoProduto servicoVerificacaoProduto);
	
	/**
	 * Retorna todos os checklist que não estão revogados e não seja
	 * o contenha o id passado.
	 * @param cod
	 * @param situacao
	 * @return
	 * @throws DAOException
	 */
	public List<ChecklistServicoVerificacaoProduto> getAllNotIn(Integer cod, String situacao) throws DAOException;
	
	List<ChecklistServicoVerificacaoProduto> buscaChecklistAutorizadoOuPublicado(ChecklistServicoVerificacaoProduto checklist) throws DAOException;
	
	List<ChecklistServicoVerificacaoProduto> buscaChecklistPublicado(ChecklistServicoVerificacaoProduto checklist) throws DAOException;
	
	/**
	 * Valida se existe um checklist publicado, não-revogado, de mesma data inicio e mesmo serviço verificação produto
	 * @return
	 */
	boolean existeChecklistPublicadoDataInicio(ChecklistServicoVerificacaoProduto checklist) throws DAOException;
	
	public Connection getConnection();

	/**
	 * @param produtos
	 * @return
	 */
	public List<ChecklistServicoVerificacaoProduto> getChecklistsByProdutoabrangentes(List<Produto> produtos);
}