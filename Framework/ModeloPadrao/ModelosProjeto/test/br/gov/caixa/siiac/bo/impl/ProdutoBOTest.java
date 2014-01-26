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

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.model.domain.Produto;

/**
 *
 * @author GisConsult
 */
public class ProdutoBOTest extends TestWithSpring {
	
	@Autowired
	private IProdutoBO instance;
	
	/**
	 * Test of findById method, of class ProdutoBO.
	 */
	@Test
	public void testFindById() {
		System.out.println("findById");
		try {
			String codProduto = "0001001";
			Produto result = instance.findById(codProduto);
			assertNotNull(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of findByNome method, of class ProdutoBO.
	 */
	@Test
	public void testFindByNome() {
		System.out.println("findByNome");
		try {
			String nome = "Cadastro Produto IE6";
			List<Produto> result = instance.findByNome(nome);
			assertTrue(!result.isEmpty());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of findByNomeAndOperacao method, of class ProdutoBO.
	 */
	@Test
	public void testFindByNomeAndOperacao() {
		System.out.println("findByNomeAndOperacao");
		try {
			String nome = "Cadastro Produto IE6";
			Short operacao = 1;
			List<Produto> result = instance.findByNomeAndOperacao(nome, operacao);
			assertTrue(!result.isEmpty());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of findByOperacao method, of class ProdutoBO.
	 */
	@Test
	public void testFindByOperacao() {
		System.out.println("findByOperacao");
		try {
			Short operacao = 1;
			List<Produto> result = instance.findByOperacao(operacao);
			assertTrue(!result.isEmpty());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of getListProduto method, of class ProdutoBO.
	 */
	@Test
	public void testGetListProduto() {
		System.out.println("getListProduto");
		try {
			List result = instance.getListProduto();
			assertTrue(result.size() > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of getPesquisaProduto method, of class ProdutoBO.
	 */
	@Test
	public void testGetPesquisaProduto() {
		System.out.println("getPesquisaProduto");
		try {
			List result = instance.getPesquisaProduto("Cadastro Produto IE6", true, "", "", "", null, null, null, null, true);
			assertTrue(result.size() > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of gravarProduto method, of class ProdutoBO.
	 */
	@Test
	public void testGravarProduto() {
		System.out.println("gravarProduto");
		try {
			instance.gravarProduto("1503000", "Produto Teste", (short)150, (short)3000, "SD", 0d, "dad", 3, (short)1, true, false);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of ativarDesativarProduto method, of class ProdutoBO.
	 */
	@Test
	public void testAtivarDesativarProduto() {
		System.out.println("ativarDesativarProduto");
		try {
			instance.ativarDesativarProduto("0001001", false);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
		
	/**
	 * Test of getAllNaoPreferenciais method, of class ProdutoUsuarioBO.
	 */
	@Test
	public void testGetAllNaoPreferenciais() {
		System.out.println("getAllNaoPreferenciais");
		try {
			String matricula = "c000456";
			List result = instance.getAllNaoPreferenciais(matricula);
			assertTrue(result.size() > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
}
