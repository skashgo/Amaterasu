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
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.ResultadoApontamentoProduto;
import br.gov.caixa.siiac.model.domain.TrilhaHistorico;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;
import br.gov.caixa.siiac.persistence.dao.IResultadoApontamentoProdutoDAO;
import br.gov.caixa.siiac.util.FormatUtil;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.siiac.util.LogCEFUtil;
import br.gov.caixa.siiac.util.TrilhaHistoricoUtil;

/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class ResultadoApontamentoProdutoDAO extends GenericDAO<ResultadoApontamentoProduto> implements IResultadoApontamentoProdutoDAO {
	
	public ResultadoApontamentoProdutoDAO() {
		super(ResultadoApontamentoProduto.class);
	}

	 
	public List<ResultadoApontamentoProduto> findByIdApontamentoChecklistProduto(VerificacaoContrato verificacaoContrato) {
		Criteria c = getCriteria();
		c.createAlias("verificacaoContrato", "vc");
		c.createAlias("contrato", "c");
//		c.add(Restrictions.eq("apontamentoChecklistProduto", acp));
		c.add(Restrictions.eq("vc.nuVerificacaoContrato", verificacaoContrato.getNuVerificacaoContrato()));
		c.add(Restrictions.eq("c.nuContrato", verificacaoContrato.getContrato().getNuContrato()));
		return c.list();
	}


	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IResultadoApontamentoProdutoDAO#findByNuVerificacaoContrato(java.lang.Integer)
	 */
	public List<ResultadoApontamentoProduto> findByNuVerificacaoContrato(Integer nuVerificacaoContrato) {
		Criteria crit = getCriteria();
		crit.add(Restrictions.eq("verificacaoContrato.nuVerificacaoContrato", nuVerificacaoContrato));
		return crit.list();
	}
	
	public void delete (Integer nuContrato)
	{
		String sql = "DELETE FROM iacsm001.iactb013_rslto_aptmnto_produto where nu_contrato = :nuContrato";
			
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuContrato", nuContrato);
		query.executeUpdate();
	}
}