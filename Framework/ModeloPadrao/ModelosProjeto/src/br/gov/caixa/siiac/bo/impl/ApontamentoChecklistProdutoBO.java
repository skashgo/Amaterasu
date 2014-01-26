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
package br.gov.caixa.siiac.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IApontamentoChecklistProdutoBO;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.persistence.dao.IApontamentoChecklistProdutoDAO;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class ApontamentoChecklistProdutoBO implements IApontamentoChecklistProdutoBO {
	
	private IApontamentoChecklistProdutoDAO apontamentoChecklistProdutoDAO;
	
	@Autowired
	public void setApontamentoChecklistProdutoDAO(IApontamentoChecklistProdutoDAO apontamentoChecklistProdutoDAO) {
		this.apontamentoChecklistProdutoDAO = apontamentoChecklistProdutoDAO;
	}
	
	@Transactional
	public void removeApontamento(ApontamentoChecklistProduto apontamento) throws DAOException {
		apontamentoChecklistProdutoDAO.refresh(apontamento);
		apontamentoChecklistProdutoDAO.delete(apontamento);
	}

	@Transactional
	public ApontamentoChecklistProduto findById(Integer codApontamento) {
		return apontamentoChecklistProdutoDAO.findById(codApontamento);
	}

	@Transactional
	public ApontamentoChecklistProduto merge(ApontamentoChecklistProduto apontamentoChecklistProduto) throws DAOException {
		return apontamentoChecklistProdutoDAO.merge(apontamentoChecklistProduto);
	}
	
}
