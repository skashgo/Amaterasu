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

import br.gov.caixa.siiac.bo.IServicoVerificacaoBO;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;

public class ServicoVerificacaoBOTest extends TestWithSpring {
	
	@Autowired
	private IServicoVerificacaoBO servicoVerificacaoBO;
	
	@Test
	public void testaGetPorNumeroENome() {
		ServicoVerificacao servicoVerificacao = new ServicoVerificacao();
		servicoVerificacao.setNuServicoVerificacao(1);
		servicoVerificacao.setNoServicoVerificacao("CADASTRO SERVIÇO VERIFICAÇÃO IE6");
		servicoVerificacao.setIcAtivo(true);
		
		try {
			List<ServicoVerificacao> lista = servicoVerificacaoBO.get(servicoVerificacao);
			assertTrue(lista.size() == 1);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaGetPorNumero() {
		ServicoVerificacao servicoObtido = servicoVerificacaoBO.get(4);
		
		try {
			assertTrue(servicoObtido.getNoServicoVerificacao().equals("Quarto Serviço 4"));
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaGetAllAtivos() {
		List<ServicoVerificacao> lista = servicoVerificacaoBO.getAllAtivos();
		
		try {
			assertTrue(lista.size() == 5);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaGetAllInativos() {
		List<ServicoVerificacao> lista = servicoVerificacaoBO.getAllInativos();
		
		try {
			assertTrue(lista.size() == 2);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaSalvar() {
		ServicoVerificacao servicoVerificacao = new ServicoVerificacao();
		servicoVerificacao.setNuServicoVerificacao(555);
		servicoVerificacao.setNoServicoVerificacao("Novo ServicoVerificacao Teste");
		servicoVerificacao.setIcAtivo(true);
		
		try {
			servicoVerificacaoBO.salvar(servicoVerificacao);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaJaExiste() {
		ServicoVerificacao servicoVerificacao = new ServicoVerificacao();
		servicoVerificacao.setNuServicoVerificacao(3);
		servicoVerificacao.setNoServicoVerificacao("Terceiro Serviço");
		servicoVerificacao.setIcAtivo(true);
		
		try {
			assertTrue(servicoVerificacaoBO.jaExiste(servicoVerificacao));
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaNaoExiste() {
		ServicoVerificacao servicoVerificacao = new ServicoVerificacao();
		servicoVerificacao.setNuServicoVerificacao(33);
		servicoVerificacao.setNoServicoVerificacao("ServicoNovo");
		servicoVerificacao.setIcAtivo(true);
		
		try {
			assertFalse(servicoVerificacaoBO.jaExiste(servicoVerificacao));
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaConsulta() {
		ServicoVerificacao servicoVerificacao = new ServicoVerificacao();
		servicoVerificacao.setNuServicoVerificacao(1);
		servicoVerificacao.setNoServicoVerificacao("CADASTRO SERVIÇO VERIFICAÇÃO IE6");
		servicoVerificacao.setIcAtivo(true);
		
		List<ServicoVerificacao> lista = servicoVerificacaoBO.realizaConsulta(servicoVerificacao, false);
		
		try {
			assertTrue(lista.size() == 1);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaAtivaServicoVerificacao() {
		ServicoVerificacao servicoVerificacao = new ServicoVerificacao();
		servicoVerificacao.setNuServicoVerificacao(1);
		servicoVerificacao.setNoServicoVerificacao("CADASTRO SERVIÇO VERIFICAÇÃO IE6");
		servicoVerificacao.setIcAtivo(false);
		
		servicoVerificacaoBO.ativar(servicoVerificacao);
		
		try {
			assertTrue(servicoVerificacao.isIcAtivo());
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaInativaServicoVerificacao() {
		ServicoVerificacao servicoVerificacao = new ServicoVerificacao();
		servicoVerificacao.setNuServicoVerificacao(2);
		servicoVerificacao.setNoServicoVerificacao("SERVIÇO COM O NOME MUITO LONGO, 47 CARACTERES DE EXTENSÃO");
		servicoVerificacao.setIcAtivo(true);
		
		servicoVerificacaoBO.inativar(servicoVerificacao);
		
		try {
			assertFalse(servicoVerificacao.isIcAtivo());
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaGetAllServicoNotProduto() {
		Produto produto = new Produto();
		produto.setCoProduto("0001001");
		
		List<ServicoVerificacao> lista = servicoVerificacaoBO.getAllServicoNotProduto(produto);
		
		try {
			assertTrue(lista.size() == 4);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testGetAll(){
		List<ServicoVerificacao> list = servicoVerificacaoBO.getAllNaoPreferenciais("c000001");
	}

}
