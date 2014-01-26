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

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 	br.gov.caixa.siiac.bo.impl.UnidadeBOTest.class, 
						br.gov.caixa.siiac.bo.impl.ProdutoBOTest.class, 
						br.gov.caixa.siiac.bo.impl.PreferenciaUsuarioServicoBOTest.class, 
						br.gov.caixa.siiac.bo.impl.UsuarioBOTest.class,
						br.gov.caixa.siiac.bo.impl.ProdutoUsuarioBOTest.class,
						br.gov.caixa.siiac.bo.impl.ServicoVerificacaoProdutoBOTest.class,
						br.gov.caixa.siiac.bo.impl.PreferenciasUsuarioBOTest.class,
						br.gov.caixa.siiac.bo.impl.UsuarioADBOTest.class,
						br.gov.caixa.siiac.bo.impl.BlocoChecklistBOTest.class })
public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTest(AllTests.suite());
		//$JUnit-END$
		return suite;
	}
	
}
