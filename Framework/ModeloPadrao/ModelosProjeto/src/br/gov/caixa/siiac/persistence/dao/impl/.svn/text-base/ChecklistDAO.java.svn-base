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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.ChecklistVO;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.persistence.dao.IChecklistDAO;

@Repository
@Scope("prototype")
public class ChecklistDAO extends GenericDAO<ChecklistServicoVerificacaoProduto> implements IChecklistDAO {
	
	/**
	 * 
	 */
	public ChecklistDAO() {
		super(ChecklistServicoVerificacaoProduto.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ChecklistServicoVerificacaoProduto> findByChecklist(ChecklistServicoVerificacaoProduto checklist) throws DAOException {
		Criteria c = getCriteria();
		
		c.add(Restrictions.eq("servicoVerificacaoProduto", checklist.getServicoVerificacaoProduto()))
		.add(Restrictions.ilike("icSituacao", ChecklistVO.CHKLST_SITUACAO_PROJETO, MatchMode.ANYWHERE))
		.add(Restrictions.eq("dataInicio", checklist.getDataInicio()));
		List<ChecklistServicoVerificacaoProduto> busca = c.list();
		
		return busca;
	}
	
	
	
	public List<ChecklistServicoVerificacaoProduto> filter(Short operacao, String codProduto, String noProduto, String situacao, Integer codServicoVerificacao, 
			Date dataInicio, Date dataFim, boolean revogado, Short nuCanal) {
		Object[] estados = {false, true};
		Criteria c = getCriteria();
		c.createAlias("servicoVerificacaoProduto", "svp");
		c.createAlias("svp.servicoVerificacao", "sv");
		c.createAlias("svp.produto", "prod");
		c.createAlias("canalComercializacao", "canal");
		
		if (operacao != null && operacao != 0) {
			c.add(Restrictions.eq("prod.nuOperacao", operacao));
		}
		
		if (codProduto != null && !codProduto.equals("")) {
			c.add(Restrictions.eq("prod.coProduto", codProduto));
		} else {
			if (noProduto != null && !noProduto.equals("")) {
				c.add(Restrictions.ilike("prod.noProduto", noProduto, MatchMode.ANYWHERE));
			}
		}
		
		if (situacao != null && !situacao.equals("")) {
			c.add(Restrictions.ilike("icSituacao", situacao, MatchMode.ANYWHERE));
		}
		
		if (codServicoVerificacao != null && codServicoVerificacao != 0) {
			c.add(Restrictions.eq("sv.nuServicoVerificacao", codServicoVerificacao));
		}
		
		if (dataInicio != null) {
			c.add(Restrictions.eq("dataInicio", dataInicio));
		}
		
		if (dataFim != null) {
			c.add(Restrictions.eq("dataFim", dataFim));
		}
		
		if(revogado == true) {
			c.add(Restrictions.in("icRevogado", estados));
		} else {
			c.add(Restrictions.eq("icRevogado", Boolean.FALSE));
		}
		
		if(nuCanal != null) {
			c.add(Restrictions.eq("canal.nuCanalCmrco", nuCanal));
		}
		
		return findByCriteria(c);
	}
	
	public List<ChecklistServicoVerificacaoProduto> findByDataIniSituacao(String chklstSituacaoAutorizado, Date date) {
		Criteria c = getCriteria();
		c.add(Restrictions.eq("icSituacao", chklstSituacaoAutorizado))
		.add(Restrictions.eq("dataInicio", date))
		.add(Restrictions.eq("icRevogado", false));
		
		return findByCriteria(c);
	}
	
	public ChecklistServicoVerificacaoProduto findBySituacaoServicoVerificacaoProduto(String chklstSituacaoPublicado, ServicoVerificacaoProduto servicoVerificacaoProduto) {
		Criteria c = getCriteria();
		c.add(Restrictions.eq("icSituacao", chklstSituacaoPublicado))
		.add(Restrictions.eq("servicoVerificacaoProduto", servicoVerificacaoProduto))
		.add(Restrictions.eq("icRevogado", false));
		
		return (ChecklistServicoVerificacaoProduto) c.uniqueResult();
	}
	
	public List<ChecklistServicoVerificacaoProduto> getAllNotIn(Integer cod, String situacao) throws DAOException {
		Criteria c = getCriteria();
		c.add(Restrictions.ne("nuChecklistServicoVerificacaoProduto", cod))
		.add(Restrictions.ne("icSituacao", ChecklistVO.CHKLST_SITUACAO_PUBLICADO).ignoreCase())
		.add(Restrictions.eq("icRevogado", false))
		.add(Restrictions.ilike("icSituacao", situacao.trim(), MatchMode.ANYWHERE));

		return findByCriteria(c);
	}
	
	public List<ChecklistServicoVerificacaoProduto> buscaChecklistAutorizadoOuPublicado(ChecklistServicoVerificacaoProduto checklist)
		throws DAOException {
		Object[] situacoes = {ChecklistVO.CHKLST_SITUACAO_AUTORIZADO, ChecklistVO.CHKLST_SITUACAO_PUBLICADO};
		Criteria c = getCriteria();
		c.add(Restrictions.eq("servicoVerificacaoProduto", checklist.getServicoVerificacaoProduto()))
		.add(Restrictions.eq("dataInicio", checklist.getDataInicio()))
		.add(Restrictions.in("icSituacao", situacoes));
		
		return c.list();
	}
	
	public List<ChecklistServicoVerificacaoProduto> buscaChecklistPublicado(ChecklistServicoVerificacaoProduto checklist) {
		Object[] situacoes = {ChecklistVO.CHKLST_SITUACAO_PUBLICADO};
		Criteria c = getCriteria();
		c.add(Restrictions.eq("servicoVerificacaoProduto", checklist.getServicoVerificacaoProduto()))
//		.add(Restrictions.eq("dataInicio", checklist.getDataInicio()))
		.add(Restrictions.eq("icRevogado", Boolean.FALSE))
		.add(Restrictions.in("icSituacao", situacoes));
		
		return c.list();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IChecklistDAO#existeChecklistPublicadoDataInicio(br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto)
	 */
	public boolean existeChecklistPublicadoDataInicio(ChecklistServicoVerificacaoProduto checklist) throws DAOException {
		Criteria c = getCriteria()
		.add(Restrictions.eq("servicoVerificacaoProduto", checklist.getServicoVerificacaoProduto()))
		.add(Restrictions.eq("dataInicio", checklist.getDataInicio()))
		.add(Restrictions.eq("icRevogado", Boolean.FALSE))
		.add(Restrictions.eq("icSituacao", ChecklistVO.CHKLST_SITUACAO_PUBLICADO));
		
		return c.list() != null && ! c.list().isEmpty();
	}
	
	public Connection getConnection() {
		return getSession().connection();
	}

	public List<ChecklistServicoVerificacaoProduto> getChecklistsByProdutoabrangentes(List<Produto> produtos) {
		Criteria c = getSession().createCriteria(ChecklistServicoVerificacaoProduto.class);
		if(produtos != null && produtos.isEmpty()){
			return new ArrayList<ChecklistServicoVerificacaoProduto>();
		}
		if (produtos != null) {
			c.createAlias("servicoVerificacaoProduto", "svp");
			c.add(Restrictions.in("svp.produto", produtos));
		}
		return c.list();
	}
}