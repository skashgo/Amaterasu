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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IServicoVerificacaoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioServico;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.persistence.dao.IProdutoUsuarioDAO;
import br.gov.caixa.siiac.persistence.dao.IServicoVerificacaoDAO;
import br.gov.caixa.util.Utilities;

/**
 * @author GisConsult
 *
 */
@Service
@Scope("prototype")
public class ServicoVerificacaoBO implements IServicoVerificacaoBO {
	
	private IServicoVerificacaoDAO servicoVerificacaoDAO;
	private IProdutoUsuarioDAO produtoUsuarioDAO;

	@Autowired
	public void setServicoVerificacaoDAO(IServicoVerificacaoDAO servicoVerificacaoDAO) {
		this.servicoVerificacaoDAO = servicoVerificacaoDAO;
	}
	
	@Autowired
	public void setProdutoUsuarioDAO(IProdutoUsuarioDAO produtoUsuarioDAO) {
		this.produtoUsuarioDAO = produtoUsuarioDAO;
	}
	
	@Transactional
	public List<ServicoVerificacao> get(ServicoVerificacao servicoVerificacao) {
		Criteria criteria = servicoVerificacaoDAO.getCriteria();
		SimpleExpression condicao1 = Restrictions.eq("noServicoVerificacao", servicoVerificacao.getNoServicoVerificacao());
		SimpleExpression condicao2 = Restrictions.eq("icAtivo", servicoVerificacao.isIcAtivo());
		criteria.add(Restrictions.and(condicao1, condicao2));
		
		return servicoVerificacaoDAO.findByCriteria(criteria);
	}

	@Transactional
	public ServicoVerificacao get(Integer nuServicoVerificacaoCombo) {
		return servicoVerificacaoDAO.findById(nuServicoVerificacaoCombo);
	}
	
	@Transactional
	public List<ServicoVerificacao> getAllAtivos() {
		Criteria criteria = servicoVerificacaoDAO.getCriteria();
		criteria.add(Restrictions.eq("icAtivo", true))
		.addOrder(Order.asc("noServicoVerificacao"));
		
		return servicoVerificacaoDAO.findByCriteria(criteria);
	}
	
	@Transactional
	public List<ServicoVerificacao> getAllInativos() {
		Criteria criteria = servicoVerificacaoDAO.getCriteria();
		criteria.add(Restrictions.eq("icAtivo", false))
		.addOrder(Order.asc("noServicoVerificacao"));
		
		return servicoVerificacaoDAO.findByCriteria(criteria);
	}
	
	@Transactional
	public void salvar(ServicoVerificacao servicoVerificacao) {
		servicoVerificacaoDAO.save(servicoVerificacao);
	}
	
	@Transactional
	public boolean jaExiste(ServicoVerificacao servicoVerificacao) {
		Criteria c = servicoVerificacaoDAO.getCriteria();
		if (Utilities.notEmpty(servicoVerificacao.getNoServicoVerificacao())) {
			c.add(Restrictions.eq("noServicoVerificacao", servicoVerificacao.getNoServicoVerificacao()).ignoreCase());
			if(Utilities.notEmpty(servicoVerificacao.getNuServicoVerificacao())){
				c.add(Restrictions.ne("nuServicoVerificacao", servicoVerificacao.getNuServicoVerificacao()));
			}
		}
		c.setProjection(Projections.rowCount());
		return ((Integer) c.uniqueResult()).intValue() > 0;
	}
	
	@Transactional
	public List<ServicoVerificacao> realizaConsulta(ServicoVerificacao servicoVerificacao, boolean isCadastro) {
		Criteria criteria = servicoVerificacaoDAO.getCriteria();
		if(!isCadastro) {
			Criterion condicao1 = Restrictions.ilike("noServicoVerificacao", servicoVerificacao.getNoServicoVerificacao(), MatchMode.ANYWHERE);
			SimpleExpression condicao2 = Restrictions.eq("icAtivo", servicoVerificacao.isIcAtivo());
			criteria.add(Restrictions.and(condicao1, condicao2));
		} else {
			criteria.add(Restrictions.ilike("noServicoVerificacao", servicoVerificacao.getNoServicoVerificacao(), MatchMode.EXACT));
		}
		return servicoVerificacaoDAO.findByCriteria(criteria);
	}
	
	@Transactional
	public void ativar(ServicoVerificacao servicoVerificacao) {
		servicoVerificacao.setIcAtivo(true);
		servicoVerificacaoDAO.merge(servicoVerificacao);
	}
	
	@Transactional
	public void inativar(ServicoVerificacao servicoVerificacao) {
		servicoVerificacao.setIcAtivo(false);
		servicoVerificacaoDAO.merge(servicoVerificacao);
	}

	@Transactional
	public void update(ServicoVerificacao servicoVerificacao) {
		servicoVerificacaoDAO.saveOrUpdate(servicoVerificacao);
		
	}

	@Transactional
	public List<ServicoVerificacao> getAllServicoNotProduto(Produto produto) {
		
		DetachedCriteria servProd = DetachedCriteria.forClass(ServicoVerificacaoProduto.class)
						.add(Restrictions.eq("produto", produto))
						.add(Restrictions.isNull("dtFimVinculacao"))
						.setProjection(Projections.property("servicoVerificacao.nuServicoVerificacao"));
		
		Criteria cri = servicoVerificacaoDAO.getCriteria();
		cri.add(Subqueries.propertyNotIn("nuServicoVerificacao", servProd));
		cri.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		return servicoVerificacaoDAO.findByCriteria(cri);
	}

	@Transactional
	public List<ServicoVerificacao> getAllServicoProduto(Produto produto) {
		
		DetachedCriteria servProd = DetachedCriteria.forClass(ServicoVerificacaoProduto.class)
						.add(Restrictions.eq("produto", produto))
						.add(Restrictions.isNull("dtFimVinculacao"))
						.setProjection(Projections.property("servicoVerificacao.nuServicoVerificacao"));
		
		Criteria cri = servicoVerificacaoDAO.getCriteria();
		cri.add(Subqueries.propertyIn("nuServicoVerificacao", servProd));
		cri.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		return servicoVerificacaoDAO.findByCriteria(cri);
	}
	
	@Transactional
	public boolean isAtivo(Integer nuServicoVerificacaoCombo) {
		Criteria c = servicoVerificacaoDAO.getCriteria();
		c.add(Restrictions.idEq(nuServicoVerificacaoCombo));
		c.setProjection(Property.forName("icAtivo"));
		return Boolean.valueOf(String.valueOf(c.uniqueResult()));		
	}

	@Transactional
	public List<ServicoVerificacao> getAllNaoPreferenciais(String matricula) {
		// Valida se o usuário possui todos os produtos vinculados com ele.
		boolean usuarioTemTodosProdutos = produtoUsuarioDAO.getCountProdutoUsuario(matricula).equals(0);
		
		DetachedCriteria dc = DetachedCriteria.forClass(PreferenciaUsuarioServico.class)
					.add(Restrictions.eq("id.coUsuario", matricula))
					.setProjection(Projections.property("id.nuServicoVerificacao"));
		
		Criteria crit = servicoVerificacaoDAO.getCriteria();
		crit.createAlias("servicoVerificacaoProdutoList", "svp");
		crit.createAlias("svp.produto", "p");

		if(!usuarioTemTodosProdutos){
			crit.createAlias("p.produtosUsuarioList", "pu");
			crit.add(Restrictions.eq("pu.usuario.coUsuario", matricula));
		}
		crit.add(Restrictions.eq("p.icAtivo", Boolean.TRUE));
		crit.add(Restrictions.eq("icAtivo", Boolean.TRUE));

		crit.add(Property.forName("nuServicoVerificacao").notIn(dc));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); // Para fazer o distinct na classe principal da Criteria.
		
		return servicoVerificacaoDAO.findByCriteria(crit);
	}

	@Transactional
	public List<ServicoVerificacao> getListFiltro(String pesquisa, Boolean mostrarInativos) throws SIIACException {
		Criteria c = servicoVerificacaoDAO.getCriteria();
		if (Utilities.notEmpty(pesquisa)) {
			c.add(Restrictions.ilike("noServicoVerificacao", pesquisa, MatchMode.ANYWHERE));
		}
		if (Utilities.notEmpty(mostrarInativos) && mostrarInativos.equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		c.addOrder(Order.asc("noServicoVerificacao"));
		return servicoVerificacaoDAO.findByCriteria(c);
	}
}
