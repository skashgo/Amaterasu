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

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IVerificacaoContratoBO;
import br.gov.caixa.siiac.model.SituacaoVerificacaoVO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoParecerDAO;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class VerificacaoContratoBO implements IVerificacaoContratoBO {

	public static final String SITUACAO_CONTRATO_VERIFICACAO_CONFORME = "CONFORME";
	public static final String SITUACAO_CONTRATO_VERIFICACAO_INCONFORME = "INCONFORME";
	public static final String SITUACAO_CONTRATO_VERIFICACAO_NAO_VERIFICADA = "NÃO VERIFICADA";
	public static final String SITUACAO_CONTRATO_VERIFICACAO_VERIFICACAO_PARCIAL = "VERIFICAÇÃO PARCIAL";
	
	public static final String SITUACAO_CONTRATO_VERIFICACAO_CONFORME_SIGLA = "CO";
	public static final String SITUACAO_CONTRATO_VERIFICACAO_INCONFORME_SIGLA = "IC";
	public static final String SITUACAO_CONTRATO_VERIFICACAO_NAO_VERIFICADA_SIGLA = "NV";
	public static final String SITUACAO_CONTRATO_VERIFICACAO_VERIFICACAO_PARCIAL_SIGLA = "VP";
	
		
	private IVerificacaoContratoDAO verificacaoContratoDAO;
	private IVerificacaoContratoParecerDAO verificacaoContratoParecerDAO;
	
	@Autowired
	public void setVerificacaoContratoDAO(IVerificacaoContratoDAO verificacaoContratoDAO) {
		this.verificacaoContratoDAO = verificacaoContratoDAO;
	}
	
	@Autowired
	public void setVerificacaoContratoParecerDAO(IVerificacaoContratoParecerDAO verificacaoContratoParecerDAO) {
		this.verificacaoContratoParecerDAO = verificacaoContratoParecerDAO;
	}
		
	@Transactional
	public List<VerificacaoContratoVO> listVerificacaoVOByContrato(Contrato contrato) throws Exception {
		List<VerificacaoContratoVO> listVO = null;
		try {
			listVO = verificacaoContratoDAO.listVerificacaoByContrato(contrato);
			List<VerificacaoContratoVO> listVOParecer = verificacaoContratoParecerDAO.listVerificacaoByContrato(contrato);
			listVO.addAll(listVOParecer);

			return listVO;
			
		} catch (DAOException e) {
			throw e;
		}
	}
	
	@Transactional
	public VerificacaoContratoVO verificacaoByContrato(Contrato contrato) throws Exception {
		VerificacaoContratoVO verificacao = new VerificacaoContratoVO();
		try {
			verificacaoContratoDAO.flush();
			verificacao = verificacaoContratoDAO.verificacaoByContrato(contrato);
			
			return verificacao;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public String obtemSituacaoContrato(Integer nuContrato) {
				
		String situacaoContrato = verificacaoContratoDAO.getSituacaoContrato(nuContrato);
		
		if (situacaoContrato.equalsIgnoreCase(SITUACAO_CONTRATO_VERIFICACAO_CONFORME_SIGLA))
		{
			return SITUACAO_CONTRATO_VERIFICACAO_CONFORME;
		} else if (situacaoContrato.equalsIgnoreCase(SITUACAO_CONTRATO_VERIFICACAO_INCONFORME_SIGLA))
		{
			return SITUACAO_CONTRATO_VERIFICACAO_INCONFORME;
		} else if (situacaoContrato.equalsIgnoreCase(SITUACAO_CONTRATO_VERIFICACAO_NAO_VERIFICADA_SIGLA))
		{
			return SITUACAO_CONTRATO_VERIFICACAO_NAO_VERIFICADA;
		} else if (situacaoContrato.equalsIgnoreCase(SITUACAO_CONTRATO_VERIFICACAO_VERIFICACAO_PARCIAL_SIGLA))
		{
			return SITUACAO_CONTRATO_VERIFICACAO_VERIFICACAO_PARCIAL;
		}
		
		return "";
	}
	

	@Transactional
	public VerificacaoContrato findById(Integer nuVerificacaoContrato) {
		return verificacaoContratoDAO.findById(nuVerificacaoContrato);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IVerificacaoContratoBO#existe(java.lang.Integer)
	 */
	@Transactional
	public boolean existe(Integer nuVerificacaoContrato) {
		
		Criteria c = verificacaoContratoDAO.getCriteria();
		c.add(Restrictions.eq("nuVerificacaoContrato", nuVerificacaoContrato));
		c.setProjection(Projections.rowCount());

		return ((Integer) c.uniqueResult()).intValue() > 0;
	}
	
	@Transactional
	public void delete (Integer nuContrato)
	{
		verificacaoContratoDAO.delete(nuContrato);
	}
}