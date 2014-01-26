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

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.SituacaoVerificacaoVO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;

/**
 * @author GIS Consult
 *
 */
public interface IVerificacaoContratoDAO extends IGenericDAO<VerificacaoContrato> {

	List<VerificacaoContratoVO> listVerificacaoByContrato(Contrato contrato);
	
	int getQtdVerificacaoApontamentoNaoVerificada(Integer nuContrato);
		
	SituacaoVerificacaoVO getQtdVerificacaoSituacaoParecer(Integer nuContrato);
	
	public VerificacaoContratoVO verificacaoByContrato(Contrato contrato);
	
	public ChecklistServicoVerificacaoProduto checklistByNuServico(Integer nuServico);
	
	public boolean existeChecklistByNuServico(Integer nuServico);
	
	String getSituacaoContrato(Integer nuContrato);
	
	/**
	 * Método responsável por deletar todas as verificações de um determinado
	 * Contrato passado como parâmetro
	 * @param nuContrato
	 */
	void delete (Integer nuContrato);
}
