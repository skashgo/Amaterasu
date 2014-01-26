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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.BlocoChecklist;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.DetalhesContrato;
import br.gov.caixa.siiac.model.domain.Garantia;
import br.gov.caixa.siiac.model.domain.TrilhaHistorico;
import br.gov.caixa.siiac.persistence.dao.IBlocoChecklistDAO;
import br.gov.caixa.siiac.security.UsuarioPrincipalJAAS;
import br.gov.caixa.siiac.util.TrilhaHistoricoUtil;
import br.gov.caixa.siiac.util.VariableScope;

/**
 * @author GIS Consult
 *
 */
public class TrilhaHistoricoBOTest extends TestWithSpring {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		VariableScope.i().setRemoteAddr("192.168.0.154");
		UsuarioPrincipalJAAS user = new UsuarioPrincipalJAAS("");
		user.setMatricula("c000001");
		user.setNome("Usuario Teste");
		user.setNuNatural(1);
		user.setUnidade((short) 1);
		List<Short> perfis = new ArrayList<Short>();
		perfis.add((short) 1);
		user.setPerfis(perfis);
		SegurancaUsuario.getInstance().setUsuario(user);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testGetUpdate() {
		Garantia garantiaAntigo = new Garantia();
		garantiaAntigo.setNuGarantia(124);
		garantiaAntigo.setCoResponsavelInventario("c000001");
		garantiaAntigo.setDtUltimaLocalizacao(new Date());
		garantiaAntigo.setVrGarantia(new BigDecimal(200));
		Garantia garantiaNovo = new Garantia();
		garantiaNovo.setNuGarantia(124);
		garantiaNovo.setCoResponsavelInventario("c000005");
		garantiaNovo.setDtUltimaLocalizacao(new Date(2000 + 1900, 12, 1));
		garantiaNovo.setVrGarantia(new BigDecimal(500));
		try {
			TrilhaHistorico t = TrilhaHistoricoUtil.i().getTrilhaForMerge(garantiaAntigo, garantiaNovo);
			
		} catch (SIIACException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testGetInsert() {
		Garantia garantiaNovo = new Garantia();
		garantiaNovo.setNuGarantia(412);
		garantiaNovo.setCoResponsavelInventario("c000005");
		garantiaNovo.setDtUltimaLocalizacao(new Date(2000 + 1900, 12, 1));
		garantiaNovo.setVrGarantia(new BigDecimal(500));
		try {
			TrilhaHistorico t = TrilhaHistoricoUtil.i().getTrilhaForSave(garantiaNovo);
		} catch (SIIACException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testGetDelete() {
		Garantia garantiaNovo = new Garantia();
		garantiaNovo.setNuGarantia(412);
		garantiaNovo.setCoResponsavelInventario("c000005");
		garantiaNovo.setDtUltimaLocalizacao(new Date(2000 + 1900, 12, 1));
		garantiaNovo.setVrGarantia(new BigDecimal(500));
		try {
			TrilhaHistoricoUtil.i().getTrilhaForDelete(garantiaNovo);
		} catch (SIIACException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public final void testGetUpdateContrato() {
		Contrato contrato = new Contrato();
		contrato.setNuContrato(123);
		contrato.setCoContrato("52245");
		contrato.setCoCliente("38dfljsdflkj3");
		DetalhesContrato detalhesContrato = new DetalhesContrato();
		detalhesContrato.setNuConveniado(23556);
		detalhesContrato.setNoCampoLivre1("Campo livre 1");
		contrato.setDetalhesContrato(detalhesContrato);
		
		Contrato contratoNovo = new Contrato();
		contratoNovo.setNuContrato(345);
		contratoNovo.setCoContrato("4534");
		contratoNovo.setCoCliente("sdfdfsd ddd");
		DetalhesContrato detalhesContratoNovo = new DetalhesContrato();
		detalhesContratoNovo.setNuConveniado(43545);
		detalhesContratoNovo.setNoCampoLivre1("Campo livre 234");
		contratoNovo.setDetalhesContrato(detalhesContratoNovo);
		
		try {
			TrilhaHistorico t = TrilhaHistoricoUtil.i().getTrilhaForMerge(contrato, contratoNovo);
		} catch (SIIACException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Autowired
	private IBlocoChecklistDAO dao;
	BlocoChecklist bloco;
	
	@Test
	public void testInsert() {
		BlocoChecklist b = new BlocoChecklist();
		b.setNoBlocoChecklist("saddflkjasdçlfj");
		b.setNoResumidoBlocoChecklist("jdf");
		bloco = dao.merge(b);
	}
	
	@Test
	public void testDelete() {
//		dao.delete(dao.findById(315));
	}
	
	public static void main(String[] args) {
		Integer[] in = {1,33, 23, 1, 3, 4};
		System.out.println(in.toString());
	}
}
