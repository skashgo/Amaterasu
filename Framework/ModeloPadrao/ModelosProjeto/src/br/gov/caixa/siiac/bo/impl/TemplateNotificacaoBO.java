/**
 * Copyright (c) 2009-2011 Caixa Econ�mica Federal. Todos os direitos
 * reservados.
 * 
 * Caixa Econ�mica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade
 * 
 * Este programa de computador foi desenvolvido sob demanda da CAIXA e est�
 * protegido por leis de direitos autorais e tratados internacionais. As
 * condi��es de c�pia e utiliza��o do todo ou partes dependem de autoriza��o da
 * empresa. C�pias n�o s�o permitidas sem expressa autoriza��o. N�o pode ser
 * comercializado ou utilizado para prop�sitos particulares.
 * 
 * Uso exclusivo da Caixa Econ�mica Federal. A reprodu��o ou distribui��o n�o
 * autorizada deste programa ou de parte dele, resultar� em puni��es civis e
 * criminais e os infratores incorrem em san��es previstas na legisla��o em
 * vigor.
 * 
 * Hist�rico do Subversion:
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
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.ITemplateNotificacaoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.TemplateNotificacao;
import br.gov.caixa.siiac.persistence.dao.impl.TemplateNotificacaoDAO;
import br.gov.caixa.siiac.util.FilterBase;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */

@Service
@Scope("prototype")
public class TemplateNotificacaoBO implements ITemplateNotificacaoBO 
{

	@Autowired
	private TemplateNotificacaoDAO templateNotificacaoDAO;
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.ITemplateNotificacaoBO#merge(br.gov.caixa.siiac.model.domain.TemplateNotificacao)
	 */
	@Transactional
	public void merge(TemplateNotificacao templateNotificacao) {
		templateNotificacaoDAO.merge(templateNotificacao);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.ITemplateNotificacaoBO#findAll()
	 */
	@Transactional
	public List<TemplateNotificacao> findAll() {
		return templateNotificacaoDAO.findAll();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.ITemplateNotificacaoBO#getListTemplateNotificacaoFiltroSimples(java.lang.String)
	 */
	@Transactional
	public List<TemplateNotificacao> getListTemplateNotificacaoFiltroSimples(
			FilterBase filtro) throws SIIACException {
		
		Criteria c = templateNotificacaoDAO.getCriteria();
		
		String pesquisa = filtro.getString("pesquisaSimples");
		
		if(Utilities.notEmpty(pesquisa)){
			c.add(Restrictions.ilike("noAssuntoNotificacao", "%" + pesquisa + "%"));			
		}
		
		c.addOrder(Order.asc("noAssuntoNotificacao"));
		
		return templateNotificacaoDAO.findByCriteria(c);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.ITemplateNotificacaoBO#getListTemplateNotificacaoFiltroAvancado(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<TemplateNotificacao> getListTemplateNotificacaoFiltroAvancado(
			FilterBase filtro)
			throws SIIACException {
		
		String codTipoUnidade = filtro.getString("pesquisaCodTipoUnidade");
		String codTipoNotificacao = filtro.getString("pesquisaCodTipoNotificacao");
		String assunto = filtro.getString("pesquisaAssunto");
		
		Criteria c = templateNotificacaoDAO.getCriteria();
		
		if(Utilities.notEmpty(assunto)){
			c.add(Restrictions.ilike("noAssuntoNotificacao", "%" + assunto + "%"));			
		}
		
		if(Utilities.notEmpty(codTipoUnidade)){
			c.add(Restrictions.eq("nuTipoUnidadeNotificada", Short.parseShort(codTipoUnidade)));			
		}
		
		if(Utilities.notEmpty(codTipoNotificacao)){
			c.add(Restrictions.eq("icTipoNotificacao", codTipoNotificacao));			
		}
		
		c.addOrder(Order.asc("noAssuntoNotificacao"));
		
		return templateNotificacaoDAO.findByCriteria(c);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.ITemplateNotificacaoBO#findById(java.lang.Integer)
	 */
	@Transactional
	public TemplateNotificacao findById(Integer id) {
		return templateNotificacaoDAO.findById(id);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.ITemplateNotificacaoBO#delete(java.lang.Integer)
	 */
	@Transactional
	public void delete(TemplateNotificacao templateNotificacao) throws SIIACException {
		templateNotificacaoDAO.delete(templateNotificacao);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.ITemplateNotificacaoBO#exist(br.gov.caixa.siiac.model.domain.TemplateNotificacao)
	 */
	@Transactional
	public Boolean exist(TemplateNotificacao templateNotificacao, Boolean isAlteracao)
			throws SIIACException {
		Criteria c = templateNotificacaoDAO.getCriteria();
		
		c.add(Restrictions.eq("nuTipoUnidadeNotificada", templateNotificacao.getNuTipoUnidadeNotificada()));
		c.add(Restrictions.eq("icTipoNotificacao", templateNotificacao.getIcTipoNotificacao()));
		
		if (isAlteracao)
		{
			c.add(Restrictions.ne("nuTemplateNotificacao", templateNotificacao.getNuTemplateNotificacao()));
		}
		
		return templateNotificacaoDAO.findByCriteria(c).isEmpty();
	}
		
}
