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

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.ICanalComercialProdutoBO;
import br.gov.caixa.siiac.model.domain.CanalComercializacaoProduto;
import br.gov.caixa.siiac.persistence.dao.ICanalComercialProdutoDAO;

/**
 * @author GisConsult
 *
 */
@Service
@Scope("prototype")
public class CanalComercialProdutoBO implements ICanalComercialProdutoBO {
	private transient ICanalComercialProdutoDAO canalComercialProdutoDAO;
	
	@Autowired
	public void setCanalComercialProdutoDAO(ICanalComercialProdutoDAO canalComercialProdutoDAO) {
		this.canalComercialProdutoDAO = canalComercialProdutoDAO;
	}
	
	@Transactional
	public CanalComercializacaoProduto findById(Short codCanal) {
		return canalComercialProdutoDAO.findById(codCanal);
	}

	@Transactional
	public List<CanalComercializacaoProduto> getListCanalComercial() {
		Criteria crit = canalComercialProdutoDAO.getCriteria();
		crit.addOrder(Order.asc("noCanalCmrco"));
		return canalComercialProdutoDAO.findByCriteria(crit);
	}

}
