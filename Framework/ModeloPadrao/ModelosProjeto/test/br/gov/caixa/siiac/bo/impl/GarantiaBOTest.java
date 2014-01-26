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

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IGarantiaBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.model.domain.Garantia;
import br.gov.caixa.siiac.security.UsuarioPrincipalJAAS;

/**
 *
 * @author Gis Consult
 */
public class GarantiaBOTest extends TestWithSpring {
	
	@Autowired
	private IGarantiaBO garantiaBO;
	private Garantia garantia = new Garantia();
	
	@BeforeClass
	public static void setUpClass() {
		UsuarioPrincipalJAAS user = new UsuarioPrincipalJAAS(UsuarioPrincipalJAAS.SIIAC_USER_INFO);
		user.setMatricula("c000001");
		user.setNome("Zezinho");
		List<Short> perfis = new ArrayList<Short>();
		perfis.add((short) 1);
		user.setPerfis(perfis);
		user.setUnidade((short) 5301);
		user.setNuNatural(5301);
		SegurancaUsuario.getInstance().setUsuario(user);
	}
	
	@Test
	public void testGetListFiltro() {
		try {
			List<Garantia> list = garantiaBO.getListFiltro(garantia, true, null);
			assertTrue(list.size() > 0);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSave() {
		try {
			garantia = new Garantia();
			garantia = garantiaBO.getListFiltro(garantia, true, null).get(0);
			garantia.setNuGarantia(null);
			garantiaBO.save(garantia);
		} catch (Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testInativar() {
		try {
			garantia = new Garantia();
			garantia = garantiaBO.getListFiltro(garantia, false, null).get(0);
			garantiaBO.inativar(garantia);
		} catch (Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testExistGarantia() {
		try {
			garantia = new Garantia();
			garantia = garantiaBO.getListFiltro(garantia, false, null).get(0);
			assertTrue(garantiaBO.existGarantia(garantia.getCoGarantia(), null));
		} catch (Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testAddTrilhaGarantia() {
		try {
			garantia = new Garantia();
			garantia = garantiaBO.getListFiltro(garantia, false, null).get(0);
			garantiaBO.addTrilhaGarantia('I', garantia, "192.168.0.111");
		} catch (Exception ex) {
			fail();
		}
	}
}
