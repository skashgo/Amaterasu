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

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.model.domain.Unidade;

/**
 *
 * @author GisConsult
 */
public class UnidadeBOTest extends TestWithSpring {
	@Autowired
	private IUnidadeBO instance;
	
	/**
	* Test of getUnidade method, of class UnidadeBO.
	*/
	@Test
	public void testGetUnidade() {
		System.out.println("getUnidade");
		try {
			Short nuUnidade = 2;
			Integer nuNatural = 2;
			Unidade result = instance.getUnidade(nuUnidade, nuNatural);
			assertNotNull(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
