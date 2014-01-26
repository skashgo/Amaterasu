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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IRestricaoBO;
import br.gov.caixa.siiac.model.domain.Restricao;


public class RestricaoBOTest extends TestWithSpring{
	
	@Autowired
	private IRestricaoBO restricaoBO;
	
	@Test
	public void testaGetListFiltroByCodigo() {
		Restricao restricao = new Restricao();
		restricao.setCoRestricao("87");
		
		try {
			List<Restricao> lista = restricaoBO.getListFiltro(restricao);
			assertTrue(lista.size() == 2);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaGetListFiltroByNome() {
		Restricao restricao = new Restricao();
		restricao.setNoRestricao("CADASTRO RESTRIÇÃO");
		
		try {
			List<Restricao> lista = restricaoBO.getListFiltro(restricao);
			assertTrue(lista.size() == 1);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaGetListFiltroBySisDestino() {
		Restricao restricao = new Restricao();
		restricao.setSistemaDestino("SIIAC");
		
		try {
			List<Restricao> lista = restricaoBO.getListFiltro(restricao);
			assertTrue(lista.size() == 1);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaGetListFiltroByAtividadeInatividade() {
		Restricao restricao = new Restricao();
		restricao.setIcAtiva(false);
		try {
			List<Restricao> lista = restricaoBO.getListFiltro(restricao);
			assertTrue(lista.size() == 1);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaAtivaInativa() {
		Restricao restricao = new Restricao();
		restricao.setCoRestricao("87");
		restricao.setNoRestricao("sipcl");
		restricao.setSistemaDestino("SIIAC");
		restricao.setIcAtiva(true);
		
		try {
			restricaoBO.ativaInativa(restricao);
			assertFalse(restricao.getIcAtiva());
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaMerge() {
		Restricao restricao = new Restricao();
		restricao.setNuRestricao(3);
		restricao.setCoRestricao("66");
		restricao.setNoRestricao("Restricao3");
		restricao.setSistemaDestino("SIIAC");
		
		try {
			restricaoBO.merge(restricao);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaExist() {
		Restricao restricao = new Restricao();
		restricao.setNuRestricao(2);
		restricao.setCoRestricao("01");
		restricao.setDeRestricao("CADASTRO RESTRIÇÃO");
		restricao.setSistemaDestino("SIIAC 87");
		
		try {
			assertTrue(restricaoBO.exist(restricao, false));
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaAtividade() {
		Restricao restricao = new Restricao();
		restricao.setNuRestricao(1);
		restricao.setNoRestricao("CADASTRO RESTRIÇÃO");
		restricao.setCoRestricao("01");
		restricao.setSistemaDestino("SIIAC 87");
		
		try {
			assertTrue(restricaoBO.isAtivo(restricao));
		} catch(Exception ex) {
			fail();
		}
	}
}
