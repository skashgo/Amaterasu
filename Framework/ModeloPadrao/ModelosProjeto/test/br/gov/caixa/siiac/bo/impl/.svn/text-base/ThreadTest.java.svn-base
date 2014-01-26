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

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IChecklistBO;
import br.gov.caixa.siiac.persistence.dao.IGarantiaDAO;

/**
 *
 * @author GisConsult
 */

public class ThreadTest extends TestWithSpring {
	@Autowired
	private transient IChecklistBO checklistBO;
	@Autowired
	private transient IGarantiaDAO dao;
	
	//	@Test
	public void testThread() {
		System.out.println("Testando Thread");
		try {
			checklistBO.updateSituacaoChecklist();
			System.out.println("Fim teste");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void teste() {
		try{
			System.out.println(dao.getListFiltroSimples("7875", false).size());
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
