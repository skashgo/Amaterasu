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
package br.gov.caixa.siiac.persistence.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ItemVerificacao;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;
import br.gov.caixa.siiac.persistence.dao.IItemVerificacaoDAO;

/**
 * @author GisConsult
 *
 */
@Repository
@Scope("prototype")
public class ItemVerificacaoDAO extends GenericDAO<ItemVerificacao> implements IItemVerificacaoDAO {
	
	public ItemVerificacaoDAO() {
		super(ItemVerificacao.class);
	}
	
	public List<ItemVerificacao> getAllNotBloco(BlocoChecklistProduto bloco) {
		DetachedCriteria dc = DetachedCriteria.forClass(ItemVerificacaoChecklistProduto.class);
		dc.add(Restrictions.eq("blocoChecklistProduto", bloco))
		.setProjection(Projections.property("itemVerificacao.nuItemVerificacao"));
		
		Criteria c = getCriteria()
				.add(Property.forName("nuItemVerificacao").notIn(dc))
				.add(Restrictions.eq("icAtivo", Boolean.TRUE))
				.addOrder(Order.asc("noItemVerificacao"));
		
		return findByCriteria(c);
	}
	
	public List<ItemVerificacao> getAllChecklist(BlocoChecklistProduto bloco)
			throws DAOException {
		Criteria c = getCriteria();
		c.createAlias("itemVerificacaoChecklistProdutoList", "ivcp");
		c.add(Restrictions.eq("ivcp.blocoChecklistProduto", bloco));
		c.addOrder(Order.asc("ivcp.numeroDeOrdem"));
		
		return findByCriteria(c);
	}
}
