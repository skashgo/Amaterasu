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
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IItemVerificacaoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ItemVerificacao;
import br.gov.caixa.siiac.persistence.dao.IItemVerificacaoDAO;
import br.gov.caixa.util.Utilities;

/**
 * @author GisConsult
 *
 */
@Service
@Scope("prototype")
public class ItemVerificacaoBO implements IItemVerificacaoBO {
	
	private IItemVerificacaoDAO itemVerificacaoDAO;
	
	@Autowired
	public void setItemVerificacaoDAO(IItemVerificacaoDAO itemVerificacaoDAO) {
		this.itemVerificacaoDAO = itemVerificacaoDAO;
	}

	@Transactional
	public List<ItemVerificacao> getAllAtivos() {
		Criteria criteria = itemVerificacaoDAO.getCriteria();
		criteria.add(Restrictions.eq("icAtivo", true))
		.addOrder(Order.asc("noItemVerificacao"));
		
		return itemVerificacaoDAO.findByCriteria(criteria);
	}
	
	@Transactional
	public List<ItemVerificacao> getAllInativos() {
		Criteria criteria = itemVerificacaoDAO.getCriteria();
		criteria.add(Restrictions.eq("icAtivo", false))
		.addOrder(Order.asc("noItemVerificacao"));
		
		return itemVerificacaoDAO.findByCriteria(criteria);
	}
	
	@Transactional
	public void salvar(ItemVerificacao itemVerificacao) {
		itemVerificacaoDAO.save(itemVerificacao);
	}
	
	@Transactional
	public boolean jaExiste(ItemVerificacao itemVerificacao) throws SIIACException {
		Criteria c = itemVerificacaoDAO.getCriteria();
		if (Utilities.notEmpty(itemVerificacao.getNoItemVerificacao())) {
			c.add(Restrictions.eq("noItemVerificacao", itemVerificacao.getNoItemVerificacao()).ignoreCase());
			if(Utilities.notEmpty(itemVerificacao.getNuItemVerificacao())){
				c.add(Restrictions.ne("nuItemVerificacao", itemVerificacao.getNuItemVerificacao()));
			}
		}
		c.setProjection(Projections.rowCount());
		return ((Integer) c.uniqueResult()).intValue() > 0;
	}
	
	@Transactional
	public List<ItemVerificacao> realizaConsulta(ItemVerificacao itemVerificacao, boolean isCadastro) {
		Criteria criteria = itemVerificacaoDAO.getCriteria();
		if(!isCadastro) {
			Criterion condicao1 = Restrictions.ilike("noItemVerificacao", itemVerificacao.getNoItemVerificacao(), MatchMode.ANYWHERE);
			SimpleExpression condicao2 = Restrictions.eq("icAtivo", itemVerificacao.getIcAtivo());
			criteria.add(Restrictions.and(condicao1, condicao2));
		} else {
			criteria.add(Restrictions.ilike("noItemVerificacao", itemVerificacao.getNoItemVerificacao(), MatchMode.EXACT));
		}
		return itemVerificacaoDAO.findByCriteria(criteria);
	}
	
	@Transactional
	public void ativar(ItemVerificacao itemVerificacao) {
		itemVerificacao.setIcAtivo(true);
		itemVerificacaoDAO.merge(itemVerificacao);
	}
	
	@Transactional
	public void inativar(ItemVerificacao itemVerificacao) {
		itemVerificacao.setIcAtivo(false);
		itemVerificacaoDAO.merge(itemVerificacao);
	}

	@Transactional
	public void update(ItemVerificacao itemVerificacao) {
		itemVerificacaoDAO.saveOrUpdate(itemVerificacao);
	}
	
	@Transactional
	public List<ItemVerificacao> getAllNotBloco(BlocoChecklistProduto bloco) throws DAOException {
		return itemVerificacaoDAO.getAllNotBloco(bloco);
	}
	
	@Transactional
	public List<ItemVerificacao> getAllBloco(BlocoChecklistProduto bloco)
			throws DAOException {
		return itemVerificacaoDAO.getAllChecklist(bloco);
	}

	@Transactional
	public List<ItemVerificacao> getListFiltro(String pesquisa, Boolean mostrarInativos, Boolean controleDataValidade) throws SIIACException {
		Criteria c = itemVerificacaoDAO.getCriteria();
		if (Utilities.notEmpty(pesquisa)) {
			c.add(Restrictions.ilike("noItemVerificacao", pesquisa, MatchMode.ANYWHERE));
		}
		if (Utilities.notEmpty(mostrarInativos) && mostrarInativos.equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		c.addOrder(Order.asc("noItemVerificacao"));
		return itemVerificacaoDAO.findByCriteria(c);
	}
	
	@Transactional
	public boolean isAtivo(ItemVerificacao itemVerificacao) {
		Criteria c = itemVerificacaoDAO.getCriteria();
		c.add(Restrictions.eq("nuItemVerificacao", itemVerificacao.getNuItemVerificacao()));
		c.setProjection(Property.forName("icAtivo"));
		return Boolean.valueOf(String.valueOf(c.uniqueResult()));
	}
	
}
