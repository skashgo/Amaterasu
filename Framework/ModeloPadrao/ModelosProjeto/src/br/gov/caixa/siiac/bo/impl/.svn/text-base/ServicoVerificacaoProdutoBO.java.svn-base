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

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IServicoVerificacaoProdutoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.ChecklistVO;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.persistence.dao.IServicoVerificacaoProdutoDAO;
import br.gov.caixa.util.Utilities;

/**
 * @author GisConsult
 *
 */
@Service
@Scope("prototype")
public class ServicoVerificacaoProdutoBO implements IServicoVerificacaoProdutoBO {

	private transient IServicoVerificacaoProdutoDAO servicoVerificacaoProdutoDAO;
	
	@Autowired
	public void setServicoVerificacaoProdutoDAO(IServicoVerificacaoProdutoDAO servicoVerificacaoProdutoDAO) {
		this.servicoVerificacaoProdutoDAO = servicoVerificacaoProdutoDAO;
	}
	
	@Transactional
	public ServicoVerificacaoProduto get(Integer nuServicoVerificacaoProduto) {
		return servicoVerificacaoProdutoDAO.findById(nuServicoVerificacaoProduto);
	}
	
	@Transactional
	public ServicoVerificacaoProduto get(Produto prod, ServicoVerificacao sv) {
		return servicoVerificacaoProdutoDAO.get(prod, sv);
	}
	
	@Transactional
	public List<ServicoVerificacaoProduto> getAll(Produto produto) {
		Criteria c = servicoVerificacaoProdutoDAO.getCriteria();
		c.add(Restrictions.eq("produto", produto));
		c.add(Restrictions.isNull("dtFimVinculacao"));
		return servicoVerificacaoProdutoDAO.findByCriteria(c);
	}

	@Transactional
	public void salvar(ServicoVerificacaoProduto servicoVerificacaoProduto) {
		Integer nuServicoVerificacaoProduto = this.idExistente(servicoVerificacaoProduto);
		if(Utilities.notEmpty(nuServicoVerificacaoProduto)){
			servicoVerificacaoProduto.setNuServicoVerificacaoProduto(nuServicoVerificacaoProduto);
		}
		servicoVerificacaoProduto.setDtInicioVinculacao(new Date());
		servicoVerificacaoProduto.setDtFimVinculacao(null);
		this.servicoVerificacaoProdutoDAO.merge(servicoVerificacaoProduto);
	}

	@Transactional
	private Integer idExistente(ServicoVerificacaoProduto servicoVerificacaoProduto) {
		Criteria c = servicoVerificacaoProdutoDAO.getCriteria();
		c.add(Restrictions.eq("produto", servicoVerificacaoProduto.getProduto()));
		c.add(Restrictions.eq("servicoVerificacao", servicoVerificacaoProduto.getServicoVerificacao()));
		c.setProjection(Projections.id());
		return c.uniqueResult() != null ? Integer.valueOf(String.valueOf(c.uniqueResult())) : null;
	}

	@Transactional
	public void excluir(ServicoVerificacaoProduto servicoVerificacaoProduto) {
		servicoVerificacaoProduto.setDtFimVinculacao(new Date());
		this.servicoVerificacaoProdutoDAO.merge(servicoVerificacaoProduto);
	}
	
}
