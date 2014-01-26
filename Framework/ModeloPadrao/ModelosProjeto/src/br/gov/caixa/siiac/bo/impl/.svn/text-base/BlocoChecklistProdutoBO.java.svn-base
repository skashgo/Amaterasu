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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IBlocoChecklistProdutoBO;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.persistence.dao.IBlocoChecklistProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IVinculacoesChecklistDAO;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class BlocoChecklistProdutoBO implements IBlocoChecklistProdutoBO {
	
	private IBlocoChecklistProdutoDAO blocoChecklistProdutoDAO;
	private IVinculacoesChecklistDAO vinculacoesChecklistDAO;
	
	@Autowired
	public void setBlocoChecklistProdutoDAO(IBlocoChecklistProdutoDAO blocoChecklistProdutoDAO) {
		this.blocoChecklistProdutoDAO = blocoChecklistProdutoDAO;
	}
	
	@Autowired
	public void setVinculacoesChecklistDAO(IVinculacoesChecklistDAO vinculacoesChecklistDAO) {
		this.vinculacoesChecklistDAO = vinculacoesChecklistDAO;
	}
	
	@Transactional
	public List<BlocoChecklistProduto> getAll() throws DAOException {
		return blocoChecklistProdutoDAO.findAll();
	}
	
	@Transactional
	public Integer getMaxOrdem(Integer idChecklist) throws DAOException {
		return vinculacoesChecklistDAO.getBlocoLastPosition(idChecklist);
	}
	
	@Transactional
	public BlocoChecklistProduto merge(BlocoChecklistProduto blocoChecklistProduto) throws DAOException {
		return blocoChecklistProdutoDAO.merge(blocoChecklistProduto);
	}
	
	@Transactional
	public void removeBloco(BlocoChecklistProduto blocoParaRemover) throws DAOException {
		blocoChecklistProdutoDAO.refresh(blocoParaRemover);
		blocoChecklistProdutoDAO.delete(blocoParaRemover);
	}
	
	@Transactional
	public BlocoChecklistProduto findById(Integer codBloco) {
		return blocoChecklistProdutoDAO.findById(codBloco);
	}
	
}
