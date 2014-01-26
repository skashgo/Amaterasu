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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IUsuarioBO;
import br.gov.caixa.siiac.bo.ListaPerfil;
import br.gov.caixa.siiac.model.domain.Usuario;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.security.UsuarioPrincipalJAAS;

/**
 *
 * @author GisConsult
 */
public class UsuarioBOTest extends TestWithSpring {
	
	@Autowired
	private IUsuarioBO instance;

	@BeforeClass
	public static void setUpClass(){
		setUpActiveDirectory();
	}
	
	
	
	/**
	 * Test of getListUsuarioByGestorSistema method, of class UsuarioBO.
	 */
	@Test
	public void testGetListUsuarioByGestorSistema() throws Exception {
		System.out.println("testGetListUsuarioByGestorSistema");
		try {
			String matricula = "c000001";
			IUsuario usuario = new UsuarioPrincipalJAAS("Gis Consult");
			usuario.setNome("Gis Consult");
			usuario.setMatricula(matricula);
			usuario.setUnidade((short)0002);
			usuario.setNuNatural(0002);
			List<Short> perfis = new ArrayList<Short>();
			perfis.add(ListaPerfil.PERFIL_GESTOR_SISTEMA);
			usuario.setPerfis(perfis);
			List<Usuario> result = instance.getListUsuario(usuario, matricula, true, null, null, null, null, true);
			assertTrue(result != null && !result.isEmpty());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test of getUsuarioByMatricula method, of class UsuarioBO.
	 */
	@Test
	public void testGetListUsuarioByRegionalConformidade() throws Exception {
		System.out.println("testGetListUsuarioByRegionalConformidade");
		try {
			String matricula = "c000001";
			IUsuario usuario = new UsuarioPrincipalJAAS("Gis Consult");
			usuario.setNome("Gis Consult");
			usuario.setMatricula(matricula);
			usuario.setUnidade((short)0002);
			usuario.setNuNatural(0002);
			List<Short> perfis = new ArrayList<Short>();
			perfis.add(ListaPerfil.PERFIL_REGIONAL_CONFORMIDADE);
			usuario.setPerfis(perfis);
			List<Usuario> result = instance.getListUsuario(usuario, matricula, true, null, null, null, null, true);
			assertTrue(result != null && !result.isEmpty());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of gravarUsuario method, of class UsuarioBO.
	 */
	@Test
	public void testGravarUsuarioInsert() throws Exception {
		System.out.println("testGravarUsuarioInsert");
		try {
			String matricula = "c000001";
			IUsuario usuario = new UsuarioPrincipalJAAS("Gis Consult");
			usuario.setNome("Gis Consult");
			usuario.setMatricula(matricula);
			usuario.setUnidade((short)0002);
			usuario.setNuNatural(0002);
			List<Short> perfis = new ArrayList<Short>();
			perfis.add(ListaPerfil.PERFIL_GESTOR_SISTEMA);
			usuario.setPerfis(perfis);
			boolean result = instance.gravarUsuario(usuario, "c000009", (short)2, false);
			assertTrue(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of gravarUsuario method, of class UsuarioBO.
	 */
	@Test
	public void testGravarUsuarioUpdate() throws Exception {
		System.out.println("testGravarUsuarioUpdate");
		try {
			String matricula = "c000001";
			IUsuario usuario = new UsuarioPrincipalJAAS("Gis Consult");
			usuario.setNome("Gis Consult");
			usuario.setMatricula(matricula);
			usuario.setUnidade((short)0002);
			usuario.setNuNatural(0002);
			List<Short> perfis = new ArrayList<Short>();
			perfis.add(ListaPerfil.PERFIL_GESTOR_SISTEMA);
			usuario.setPerfis(perfis);
			boolean result = instance.gravarUsuario(usuario, "c000004", (short)2, true);
			assertTrue(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of getUsuarioByMatricula method, of class UsuarioBO.
	 */
	@Test
	public void testGetUsuarioByMatricula() throws Exception {
		System.out.println("getUsuarioByMatricula");
		try {
			String matricula = "c000001";
			Usuario result = instance.getUsuarioByMatricula(matricula);
			assertNotNull(result);
		} catch (Exception e) {
			fail("The test case is a prototype.");
		}
	}
	
	/**
	 * Test of ativarDesativarUsuario method, of class UsuarioBO.
	 */
	@Test
	public void testAtivarDesativarUsuario() throws Exception {
		System.out.println("ativarDesativarUsuario");
		try {
			String matr = "c000001";
			boolean ativar = true;
			instance.ativarDesativarUsuario(matr, ativar);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of existeUnidade method, of class UsuarioBO.
	 */
	@Test
	public void testExisteUnidade() {
		System.out.println("existeUnidade");
		try {
			String nuUnidade = "2";
			boolean expResult = true;
			boolean result = instance.existeUnidade(nuUnidade);
			assertEquals(expResult, result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of seExisteUsuario method, of class UsuarioBO.
	 */
	@Test
	public void testSeExisteUsuario() throws Exception {
		System.out.println("seExisteUsuario");
		try {
			String matricula = "c000001";
			boolean expResult = true;
			boolean result = instance.seExisteUsuario(matricula);
			assertEquals(expResult, result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
