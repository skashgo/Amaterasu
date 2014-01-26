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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IVerificacaoContratoParecerBO;
import br.gov.caixa.siiac.model.HistoricoParecerVO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoParecerDAO;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class VerificacaoContratoParecerBO implements IVerificacaoContratoParecerBO {

	private IVerificacaoContratoParecerDAO verificacaoContratoParecerDAO;
	
	@Autowired
	public void setVerificacaoContratoParecerDAO(IVerificacaoContratoParecerDAO verificacaoContratoParecerDAO) {
		this.verificacaoContratoParecerDAO = verificacaoContratoParecerDAO;
	}

	@Transactional
	public List<HistoricoParecerVO> getListHistoricoParecer(Integer nuContrato, Integer nuServicoVerificacaoProduto) {
		return this.verificacaoContratoParecerDAO.getListHistoricoParecer(nuContrato, nuServicoVerificacaoProduto);
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IVerificacaoContratoBO#existe(java.lang.Integer)
	 */
	@Transactional
	public boolean existe(Integer nuVerificacaoContrato) {
		
		Criteria c = verificacaoContratoParecerDAO.getCriteria();
		c.add(Restrictions.eq("nuVerificacaoContrato", nuVerificacaoContrato));
		c.add(Restrictions.eq("icUltimaHierarquia", true));
		c.setProjection(Projections.count("nuVerificacaoContrato"));
		return ((Integer) c.uniqueResult()).intValue() > 0;
	}
	
	@Transactional
	public boolean permitirAlteracao(Integer nuContrato) {
		Criteria c = verificacaoContratoParecerDAO.getCriteria();
		c.add(Restrictions.eq("nuContrato", nuContrato));
		c.add(Restrictions.eq("icEstadoVerificacao", "CO"));
		c.add(Restrictions.eq("icUltimaHierarquia", Boolean.TRUE));

		c.setProjection(Projections.count("nuVerificacaoContrato"));
		return ((Integer) c.uniqueResult()).intValue() == 0;
	}
		
	@Transactional
	public VerificacaoContratoVO verificacaoByContrato(Contrato contrato) throws Exception {
		VerificacaoContratoVO verificacao = new VerificacaoContratoVO();
		try {
			verificacaoContratoParecerDAO.flush();
			verificacao = verificacaoContratoParecerDAO.verificacaoByContrato(contrato);
			
			return verificacao;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}