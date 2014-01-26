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
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.ITemplateParecerBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.TemplateParecer;
import br.gov.caixa.siiac.model.domain.TemplateParecerDestinatarios;
import br.gov.caixa.siiac.persistence.dao.ITemplateParecerDAO;
import br.gov.caixa.siiac.util.FilterBase;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */

@Service
@Scope("prototype")
public class TemplateParecerBO implements ITemplateParecerBO 
{
	@Autowired
	private ITemplateParecerDAO templateParecerDAO;
	
	private static final String CONFORME = "CONFORME";
	private static final String INCONFORME = "INCONFORME";
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.ITemplateParecerBO#merge(br.gov.caixa.siiac.model.domain.TemplateParecer)
	 */
	
	@Transactional
	public void merge(TemplateParecer tp) 
	{
		templateParecerDAO.saveOrUpdate(tp);
	}
	
	@Transactional
	public TemplateParecer findById(Integer id) 
	{
		TemplateParecer template = templateParecerDAO.findById(id);
		Hibernate.initialize(template.getListaTemplateParecerDestinatarios());
		return template;
	}
	

	@Transactional
	public List<TemplateParecer> findAll() 
	{
		return templateParecerDAO.findAll();
	}
	
	@Transactional
	public TemplateParecer findByServicoVerificacaoProdutoAndResultado(
										ServicoVerificacaoProduto servicoVerificacaoProduto,
										Boolean resultado){
		return this.templateParecerDAO.findByServicoVerificacaoProdutoAndResultado(servicoVerificacaoProduto, resultado);
	}

	
	@Transactional
	public List<TemplateParecer> getListTemplateParecerFiltroSimples(String pesquisa, Boolean mostrarInativos) throws SIIACException {
		
		Criteria c = templateParecerDAO.getCriteria();
		
		if(Utilities.notEmpty(pesquisa)){

			Disjunction disjuction = Restrictions.disjunction();
			
			if(pesquisa != null && (pesquisa.length() < 7)){
			
				Pattern patternMask = Pattern.compile("[0-9]{4}[-]?[0-9]{3}");
				if(patternMask.matcher(pesquisa).matches()){
					disjuction.add(Restrictions.eq("nuTemplateParecer", pesquisa.replace("-","")));
				}
			}
			
			Boolean tipoParecer = null;

			if (pesquisa.toUpperCase().contains(INCONFORME))
			{
				tipoParecer = false;
			} else if (pesquisa.toUpperCase().contains(CONFORME))
			{
				tipoParecer = true;
			}
			
			c.createAlias("servicoVerificacaoProduto", "svp");
			c.createAlias("svp.servicoVerificacao", "sv");
			disjuction.add(Restrictions.ilike("sv.noServicoVerificacao", pesquisa, MatchMode.ANYWHERE));
			
			if (tipoParecer != null)
			{
				disjuction.add(Restrictions.eq("icTipoParecer", tipoParecer));
			}
			
			c.add(disjuction);
		}
		
		if (Utilities.notEmpty(mostrarInativos) && mostrarInativos.equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		return templateParecerDAO.findByCriteria(c);
	}
	
	@Transactional
	public List<TemplateParecer> getListTemplateParecerFiltroSimples(FilterBase filtro) throws SIIACException {
		
		Criteria c = templateParecerDAO.getCriteria();
		
		if(Utilities.notEmpty(filtro.getString("pesquisaString"))){

			Disjunction disjuction = Restrictions.disjunction();
			
			if(filtro.getString("pesquisaString") != null && (filtro.getString("pesquisaString").length() < 7)){
			
				Pattern patternMask = Pattern.compile("[0-9]{4}[-]?[0-9]{3}");
				if(patternMask.matcher(filtro.getString("pesquisaString")).matches()){
					disjuction.add(Restrictions.eq("nuTemplateParecer", filtro.getString("pesquisaString").replace("-","")));
				}
			}
			
			Boolean tipoParecer = null;

			if (filtro.getString("pesquisaString").toUpperCase().contains(INCONFORME))
			{
				tipoParecer = false;
			} else if (filtro.getString("pesquisaString").toUpperCase().contains(CONFORME))
			{
				tipoParecer = true;
			}
			
			c.createAlias("servicoVerificacaoProduto", "svp");
			c.createAlias("svp.servicoVerificacao", "sv");
			disjuction.add(Restrictions.ilike("sv.noServicoVerificacao", filtro.getString("pesquisaString"), MatchMode.ANYWHERE));
			
			if (tipoParecer != null)
			{
				disjuction.add(Restrictions.eq("icTipoParecer", tipoParecer));
			}
			
			c.add(disjuction);
		}
		
		if (Utilities.notEmpty(filtro.getString("pesquisaMostraInativos")) && filtro.getString("pesquisaMostraInativos").equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		return templateParecerDAO.findByCriteria(c);
	}
	
	@Transactional
	public List<TemplateParecer> getListTemplateParecerFiltroAvancado(String codProduto, String tipoParecer, String codServicoVerificacao, Boolean mostrarInativos) throws SIIACException {
		
		Criteria c = templateParecerDAO.getCriteria();
		
		if(Utilities.notEmpty(codProduto) || Utilities.notEmpty(codServicoVerificacao)){
			c.createAlias("servicoVerificacaoProduto", "svp");
			
			if(Utilities.notEmpty(codProduto)){
				c.createAlias("svp.produto", "p");
				c.add(Restrictions.eq("p.coProduto", codProduto));
			}
			
			if(Utilities.notEmpty(codServicoVerificacao)){
				c.createAlias("svp.servicoVerificacao", "sv");
				c.add(Restrictions.eq("sv.nuServicoVerificacao", Integer.parseInt(codServicoVerificacao)));
			}
		}
		
		if (Utilities.notEmpty(tipoParecer)){
			if (tipoParecer.equalsIgnoreCase("true"))
			{
				c.add(Restrictions.eq("icTipoParecer", Boolean.TRUE));
			} else {
				c.add(Restrictions.eq("icTipoParecer", Boolean.FALSE));
			}
		}
		
		
		if (Utilities.notEmpty(mostrarInativos) && mostrarInativos.equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		return templateParecerDAO.findByCriteria(c);
	}
	
	@Transactional
	public List<TemplateParecer> getListTemplateParecerFiltroAvancado(FilterBase filtro) throws SIIACException {
		
		Criteria c = templateParecerDAO.getCriteria();
		
		if(Utilities.notEmpty(filtro.getString("codProduto")) || Utilities.notEmpty(filtro.getString("codServicoVerificacao"))){
			c.createAlias("servicoVerificacaoProduto", "svp");
			
			if(Utilities.notEmpty(filtro.getString("codProduto"))){
				c.createAlias("svp.produto", "p");
				c.add(Restrictions.eq("p.coProduto", filtro.getString("codProduto")));
			}
			
			if(Utilities.notEmpty(filtro.getString("codServicoVerificacao"))){
				c.createAlias("svp.servicoVerificacao", "sv");
				c.add(Restrictions.eq("sv.nuServicoVerificacao", Integer.parseInt(filtro.getString("codServicoVerificacao"))));
			}
		}
		
		if (Utilities.notEmpty(filtro.getString("tipoParecer"))){
			if (filtro.getString("tipoParecer").equalsIgnoreCase("true"))
			{
				c.add(Restrictions.eq("icTipoParecer", Boolean.TRUE));
			} else {
				c.add(Restrictions.eq("icTipoParecer", Boolean.FALSE));
			}
		}
		
		
		if (Utilities.notEmpty(filtro.getString("mostrarInativos")) && filtro.getString("mostrarInativos").equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		return templateParecerDAO.findByCriteria(c);
	}
	
	/**
	 * @param templateParecer
	 * @return FALSE caso exista algum registro com os parâmetros passados e TRUE caso não exista
	 * @throws SIIACException
	 */
	@Transactional
	public Boolean exist(TemplateParecer templateParecer) throws SIIACException {
		
		Criteria c = templateParecerDAO.getCriteria();
		
		c.createAlias("servicoVerificacaoProduto", "svp");
		c.createAlias("svp.produto", "p");
		c.add(Restrictions.eq("p.coProduto", templateParecer.getServicoVerificacaoProduto().getProduto().getCoProduto()));
		c.createAlias("svp.servicoVerificacao", "sv");
		c.add(Restrictions.eq("sv.nuServicoVerificacao", templateParecer.getServicoVerificacaoProduto().getServicoVerificacao().getNuServicoVerificacao()));
		c.add(Restrictions.eq("icTipoParecer", templateParecer.getIcTipoParecer()));
		
		return templateParecerDAO.findByCriteria(c).isEmpty();
	}
	
	/**
	 * ativarDesativarTemplate
	 * @param idTemplate
	 * @throws SIIACException 
	 */
	@Transactional
	public void ativarDesativarTemplate(Integer idTemplate, boolean ativar) throws SIIACException {
		templateParecerDAO.ativarDesativarTemplate(idTemplate, ativar);
	}

	@Transactional
	public List<Integer> getDestinatariosEnviarEmailId(Integer nuTemplateParecer) {
		return this.templateParecerDAO.getDestinatariosEnviarEmailId(nuTemplateParecer);
	}
	
	@Transactional
	public List<Integer> getDestinatariosEnviarEmailComCopiaId(Integer nuTemplateParecer) {
		return this.templateParecerDAO.getDestinatariosEnviarComCopiaEmailId(nuTemplateParecer);
	}

	
	
}
