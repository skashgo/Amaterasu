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
import br.gov.caixa.siiac.model.domain.BlocoChecklist;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;

public interface IBlocoChecklistBO {
	
	/**
	 * Retorna todos os blocos não vinculados
	 * ao checklist passado como parâmetro.
	 * @param codChecklist
	 * @return lista de BlocoChecklist
	 */
	public List<BlocoChecklist> getAllNotChecklist(ChecklistServicoVerificacaoProduto checklist) throws DAOException;
	
	/**
	 * retorna uma lista de bloco de checklist filtrando pelos valores inseridos no objeto
	 * @param blocoChecklist
	 * @return List<BlocoChecklist>
	 * @throws SIIACException
	 */
	public List<BlocoChecklist> getListFiltro(String pesesquisa, Boolean ativos) throws SIIACException;
	
	/**
	 * grava/altera um objeto do tipo BlocoChecklist
	 * @param blocoChecklist
	 * @throws SIIACException
	 */
	public void merge(BlocoChecklist blocoChecklist) throws SIIACException;
	
	/**
	 * retorna um booleano indicando ja existe este objeto no banco 
	 * @param blocoChecklist
	 * @param isCadastro
	 * @return
	 * @throws SIIACException
	 */
	public Boolean exist(BlocoChecklist blocoChecklist) throws SIIACException;
	
	/**
	 * if o objeto estiver inativado ele sera ativado, caso contrario ele sera inativado
	 * @param blocoChecklist
	 * @throws SIIACException
	 */
	public void ativaInativa(BlocoChecklist blocoChecklist) throws SIIACException;

	/**
	 * @param blocoChecklist
	 * @return
	 */
	public boolean isAtivo(BlocoChecklist blocoChecklist);
}