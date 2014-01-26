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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IBlocoChecklistBO;
import br.gov.caixa.siiac.model.domain.BlocoChecklist;
import br.gov.caixa.siiac.model.domain.Contrato;

/**
 *
 * @author GisConsult
 */
public class ContratoBOTest extends TestWithSpring {
	
	@Autowired
	private IBlocoChecklistBO blocoChecklistBO;
	
	/**
	 * Test of getListFiltro method, of class BlocoChecklistBO.
	 */
	public void testGetListFiltro() {
		try {
			BlocoChecklist blocoChecklist = new BlocoChecklist();
			blocoChecklist.setNoBlocoChecklist("Documentos");
			//Busca uma lista de Blocos que contenha a palavra Documentos em seu nome
			assertTrue(blocoChecklistBO.getListFiltro(blocoChecklist).size() > 0);
		} catch (Exception e) {
			fail();
		}
	}
	
	/**
	 * Test of merge method, of class BlocoChecklistBO.
	 */
	@Test
	public void testMerge() {
		try {
			Contrato contrato = new Contrato();
			contrato.setCoContrato("1");
			
			
		} catch (Exception e) {
			fail();
		}
	}
	
}
