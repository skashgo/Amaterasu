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

import br.gov.caixa.siiac.model.domain.VerificacaoContratoObservacoes;

/**
 * @author GIS Consult
 *
 */
public interface IVerificacaoContratoObservacaoDAO extends IGenericDAO<VerificacaoContratoObservacoes> {
	
	/**
	 * Busca a Observação cadastrada para um Bloco ou Item
	 * @param id
	 * @param fonteObservacaoApontamentoIdBloco
	 * @param vc nuVerificacaoContrato
	 * @return
	 */
	VerificacaoContratoObservacoes findById_Fonte(Integer id, Character fonte, Integer vc);

	/**
	 * Busca a lista de observações na base de dados de acordo com seu número de verificação.
	 * @param nuVerificacaoContrato
	 * @return
	 */
	List<VerificacaoContratoObservacoes> findByNuVerificacaoContrato(Integer nuVerificacaoContrato);
	
}
