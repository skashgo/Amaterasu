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

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IGeraAgendaBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.security.UsuarioPrincipalJAAS;

/**
 *
 * @author Gis Consult
 */
public class GeraAgendaBOTest extends TestWithSpring {
	
	@Autowired
	private IGeraAgendaBO geraAgendaBO;
	
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
	public void testGeraAgendaVerificacaoPreventiva(){
		Integer nuContrato = 1;
		Integer nuServicoVerificacaoProduto = 48;
		try {
			geraAgendaBO.geraAgendaVerificacaoPreventiva(nuContrato, nuServicoVerificacaoProduto);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testGeraAgendaUmContrato(){
		Integer nuContrato = 1;
		try {
			geraAgendaBO.geraAgendaUmContrato(nuContrato);
		} catch(Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testGeraAgendaTodosContratos(){
		try {
			geraAgendaBO.geraAgendaTodosContratos();
		} catch(Exception ex) {
			fail();
		}
	}
}
