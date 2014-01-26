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
package br.gov.caixa.siiac.persistence.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.TemplateParecer;
import br.gov.caixa.siiac.model.domain.TemplateParecerDestinatarios;
import br.gov.caixa.siiac.persistence.dao.ITemplateParecerDAO;

/**
 * @author GIS Consult
 *
 */

@Repository
@Scope("prototype")
public class TemplateParecerDAO extends GenericDAO<TemplateParecer> implements ITemplateParecerDAO 
{
	public TemplateParecerDAO() {
		super(TemplateParecer.class);
	}
	
	/**
	 * ativarDesativarTemplate
	 * 
	 * @param idTemplate - Código do Template
	 * @param ativar - true = para ativar o template / false = desativar
	 */
	public void ativarDesativarTemplate(Integer idTemplate, boolean ativar) throws SIIACException {
		try {
			TemplateParecer t = findById(idTemplate);
			t.setIcAtivo(ativar);
			merge(t);
		}catch (Exception e) {
			throw new SIIACException("Erro em desativar: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.ITemplateParecerDAO#findByServicoVerificacaoProdutoAndResultado(br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto, java.lang.Boolean)
	 */
	public TemplateParecer findByServicoVerificacaoProdutoAndResultado(
											ServicoVerificacaoProduto servicoVerificacaoProduto,
											Boolean resultado) {
		Criteria crit = getCriteria()
							.add(Restrictions.eq("servicoVerificacaoProduto", servicoVerificacaoProduto))
							.add(Restrictions.eq("icTipoParecer", resultado));
		return (TemplateParecer) crit.uniqueResult();
	}

	/**
	 * @param nuTemplateParecer
	 */
	public List<Integer> getDestinatariosEnviarEmailId(Integer nuTemplateParecer) {
		Criteria crit = this.getSession().createCriteria(TemplateParecerDestinatarios.class);
		
		crit.add(Restrictions.eq("id.nuTemplateParecer", nuTemplateParecer));
		crit.add(Restrictions.eq("icTipoDestinatario", TemplateParecerDestinatarios.EMAIL_TIPO_DESTINATARIO_TO));
		
		crit.setProjection(Projections.property("id.coDestinatario"));
		return crit.list();
	}
	public List<Integer> getDestinatariosEnviarComCopiaEmailId(Integer nuTemplateParecer) {
		Criteria crit = this.getSession().createCriteria(TemplateParecerDestinatarios.class);
		
		crit.add(Restrictions.eq("id.nuTemplateParecer", nuTemplateParecer));
		crit.add(Restrictions.eq("icTipoDestinatario", TemplateParecerDestinatarios.EMAIL_TIPO_DESTINATARIO_CC));
		
		crit.setProjection(Projections.property("id.coDestinatario"));
		return crit.list();
	}
	
}
