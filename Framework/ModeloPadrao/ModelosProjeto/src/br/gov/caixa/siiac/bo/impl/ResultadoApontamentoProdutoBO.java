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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IResultadoApontamentoProdutoBO;
import br.gov.caixa.siiac.persistence.dao.IResultadoApontamentoProdutoDAO;

/**
 * @author GIS Consult
 * 
 */
@Service
@Scope("prototype")
public class ResultadoApontamentoProdutoBO
		implements
			IResultadoApontamentoProdutoBO {

	private IResultadoApontamentoProdutoDAO resultadoDAO;

	@Autowired
	public void setResultadoDAO(IResultadoApontamentoProdutoDAO resultadoDAO) {
		this.resultadoDAO = resultadoDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.gov.caixa.siiac.bo.IResultadoApontamentoProdutoBO#delete(java.lang
	 * .Integer)
	 */
	public void delete(Integer nuContrato) {
		try {
			resultadoDAO.delete(nuContrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
