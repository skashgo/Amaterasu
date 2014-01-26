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

import br.gov.caixa.siiac.bo.IItemVerificacaoBO;
import br.gov.caixa.siiac.model.domain.ItemVerificacao;

public class ItemVerificacaoBOTest extends TestWithSpring {
	
	@Autowired
	private IItemVerificacaoBO itemVerificacaoBO;
	
	@Test
	public void testaGetAllAtivos() {
		List<ItemVerificacao> lista = itemVerificacaoBO.getAllAtivos();
		try {
			assertTrue(lista.size() == 3);
		} catch (Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaGetAllInativos() {
		List<ItemVerificacao> lista = itemVerificacaoBO.getAllInativos();
		try {
			assertTrue(lista.size() == 3);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaSalvar() {
		ItemVerificacao itemNovo = new ItemVerificacao();
		itemNovo.setNuItemVerificacao(002);
		itemNovo.setNoItemVerificacao("ItemVerificacao002");
		try {
			itemVerificacaoBO.salvar(itemNovo);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaJaExiste() {
		ItemVerificacao itemVerificacao = new ItemVerificacao();
		itemVerificacao.setNuItemVerificacao(4);
		itemVerificacao.setNoItemVerificacao("item verificação");
		itemVerificacao.setIcAtivo(false);
		
		try {
			assertTrue(itemVerificacaoBO.jaExiste(itemVerificacao, true));
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaNaoExiste() {
		ItemVerificacao itemVerificacao = new ItemVerificacao();
		itemVerificacao.setNuItemVerificacao(645);
		itemVerificacao.setNoItemVerificacao("ItemVerificacao Novo");
		itemVerificacao.setIcAtivo(true);
		
		try {
			assertFalse(itemVerificacaoBO.jaExiste(itemVerificacao, false));
		}catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaConsultaItemVerificacaoExistente() {
		ItemVerificacao itemVerificacao = new ItemVerificacao();
		itemVerificacao.setNuItemVerificacao(4);
		itemVerificacao.setNoItemVerificacao("item verificação");
		itemVerificacao.setIcAtivo(false);
		
		List<ItemVerificacao> lista = itemVerificacaoBO.realizaConsulta(itemVerificacao, false);
		try {
			assertTrue(lista.size() == 1);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaConsultaItemVerificacaoNaoExistente() {
		ItemVerificacao itemVerificacao = new ItemVerificacao();
		itemVerificacao.setNuItemVerificacao(354);
		itemVerificacao.setNoItemVerificacao("ItemVerificacao6");
		itemVerificacao.setIcAtivo(true);
		
		List<ItemVerificacao> lista = itemVerificacaoBO.realizaConsulta(itemVerificacao, false);
		try {
			assertTrue(lista.size() == 0);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaAtivaItemVerificacao() {
		ItemVerificacao itemVerificacao = new ItemVerificacao();
		itemVerificacao.setNuItemVerificacao(225);
		itemVerificacao.setNoItemVerificacao("ItemVerificacao Desativado");
		itemVerificacao.setIcAtivo(false);
		
		try {
			itemVerificacaoBO.ativar(itemVerificacao);
			assertTrue(itemVerificacao.getIcAtivo());
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaInativaItemVerificacao() {
		ItemVerificacao itemVerificacao = new ItemVerificacao();
		itemVerificacao.setNuItemVerificacao(233);
		itemVerificacao.setNoItemVerificacao("ItemVerificacao Ativado");
		itemVerificacao.setIcAtivo(true);
		
		try {
			itemVerificacaoBO.inativar(itemVerificacao);
			assertFalse(itemVerificacao.getIcAtivo());
		} catch(Exception ex) {
			fail();
		}
	}
}
