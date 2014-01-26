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
package br.gov.caixa.siiac.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.HistoricoParecerVO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoParecer;

/**
 * @author GIS Consult
 *
 */
public interface IVerificacaoContratoParecerDAO extends IGenericDAO<VerificacaoContratoParecer> {
	
	List<VerificacaoContratoVO> listVerificacaoByContrato(Contrato contrato) throws DAOException;
	
	int getQtdVerificacaoApontamentoConforme(Integer nuContrato);
	
	int getQtdVerificacaoApontamentoInconforme(Integer nuContrato);

	/**
	 * @param nuContrato
	 * @param nuServicoVerificacaoProduto
	 * @return
	 */
	List<HistoricoParecerVO> getListHistoricoParecer(Integer nuContrato, Integer nuServicoVerificacaoProduto);
	
	public VerificacaoContratoVO verificacaoByContrato(Contrato contrato);
}
