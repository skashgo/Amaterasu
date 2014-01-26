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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.bo.IUsuarioADBO;
import br.gov.caixa.siiac.model.domain.Unidade;

/**
 *
 * @author GisConsult
 */
public class UsuarioADBOTest extends TestWithSpring {
	
	@Autowired
	private IUsuarioADBO usuarioADBO;
	
	@Autowired
	private IUnidadeBO unidadeBO; 
	
	
	@BeforeClass
	public static void setUpClass(){
		setUpActiveDirectory();
	}
	
	@Test
	public void testExisteUsuarioAD() {
		System.out.println("existeUsuarioAD");
		try {
			String matricula = "c000001";
			boolean result = usuarioADBO.existeUsuarioAD(matricula);
			assertTrue(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetNomeUsuario() {
		System.out.println("getNomeUsuario");
		try {
			String matricula = "c000001";
			String result = usuarioADBO.getNomeUsuario(matricula);
			assertNotNull(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetNuUnidade() {
		System.out.println("getNuUnidade");
		try {
			String matricula = "c000001";
			Short result = usuarioADBO.getNuUnidade(matricula);
			assertNotNull(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testVerificaUnidade() {
		System.out.println("verificaUnidade");
		try {
			String matricula = "c000001";
			List<Unidade> l = new ArrayList<Unidade>();
			l.add(unidadeBO.getUnidade((short) 7875));
			boolean result = usuarioADBO.verificaUnidade(matricula, l);
			assertTrue(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testExisteUsuarioUnidade() {
		System.out.println("existeUsuarioUnidade");
		try {
			boolean result = usuarioADBO.existeUsuarioUnidade("7875");
			assertTrue(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
