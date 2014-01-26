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

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.bo.IProdutoUsuarioBO;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ProdutoUsuario;
import br.gov.caixa.siiac.model.domain.Usuario;

/**
 *
 * @author GisConsult
 */
public class ProdutoUsuarioBOTest extends TestWithSpring {
	@Autowired
	private IProdutoUsuarioBO instance;
	@Autowired
	private IProdutoBO produtoBO;
	
	/**
	 * Test of getAllAssociados method, of class ProdutoUsuarioBO.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testGetAllAssociados() {
		System.out.println("getAllAssociados");
		try {
			String matricula = "c000001";
			List result = instance.getAllAssociados(matricula);
			assertTrue(result.size() > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of get method, of class ProdutoUsuarioBO.
	 */
	@Test
	public void testGet() {
		System.out.println("get");
		try {
			ProdutoUsuario result = instance.get(1);
			assertNotNull(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of getAllNaoAssociados method, of class ProdutoUsuarioBO.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testGetAllNaoAssociados() {
		System.out.println("getAllNaoAssociados");
		try {
			String matricula = "c000001";
			List result = instance.getAllNaoAssociados(matricula);
			assertTrue(result.size() > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of saveAllProdutosDaLista method, of class ProdutoUsuarioBO.
	 */
	@Test
	public void testSaveAllProdutosDaLista() {
		System.out.println("saveAllProdutosDaLista");
		try {
			String matricula = "c000001";
			List<ProdutoUsuario> produtos = new ArrayList<ProdutoUsuario>();
			ProdutoUsuario pu = new ProdutoUsuario();

			//deve existir pelo menos 1 produto cadastrado
			List<Produto> listProduto = produtoBO.getListProduto();
			pu.setProduto(listProduto.get(0));

			Usuario usuario = new Usuario();
			usuario.setCoUsuario(matricula);
			pu.setUsuario(usuario);
			
			produtos.add(pu);
			
			instance.saveAllProdutosDaLista(matricula, produtos);
			//se chegar sem erros, est� correto
			assertTrue(true);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	/**
	 * Test of deleteAllProdutosNaoExistentesNaLista method, of class ProdutoUsuarioBO.
	 */
	@Test
	public void testDeleteAllProdutosNaoExistentesNaLista() {
		System.out.println("deleteAllProdutosNaoExistentesNaLista");
		try {
			String matricula = "c000001";
			List<ProdutoUsuario> produtos = new ArrayList<ProdutoUsuario>();
			
			ProdutoUsuario pu = new ProdutoUsuario();
			
			//pega um produto da lista
			//List<Produto> listProduto = produtoBO.getListProduto();
			//pu.setProduto(listProduto.get(0));
			Produto produto = new Produto();
			produto.setCoProduto("1");
			pu.setProduto(produto);
			
			//pega o usuario da matricula
			//List<Usuario> listUsuario = usuarioBO.getListUsuario(null, matricula, true, null, null, null, null, false);
			//pu.setUsuario(listUsuario.get(0));
			Usuario usuario = new Usuario();
			usuario.setCoUsuario(matricula);
			pu.setUsuario(usuario);
			
			produtos.add(pu);
			
			instance.deleteAllProdutosNaoExistentesNaLista(matricula, produtos);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of gravarProduto method, of class ProdutoUsuarioBO.
	 */
	@Test
	public void testGravarProduto() {
		System.out.println("gravarProduto");
		try {
			String matricula = "c000001";
			String coProduto = "0001001";
			boolean expResult = true;
			boolean result = instance.gravarProduto(matricula, coProduto);
			assertEquals(expResult, result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of deletarProduto method, of class ProdutoUsuarioBO.
	 */
	@Test
	public void testDeletarProduto() {
		System.out.println("deletarProduto");
		try {
			String matricula = "c000001";
			String coProduto = "0001001";
			boolean expResult = true;
			boolean result = instance.deletarProduto(matricula, coProduto);
			assertEquals(expResult, result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of adjustAllProdutosPreferenciais method, of class ProdutoUsuarioBO.
	 */
	@Test
	public void testAdjustAllProdutosPreferenciais() {
		System.out.println("adjustAllProdutosPreferenciais");
		try {
			String matricula = "c000001";
			instance.adjustAllProdutosPreferenciais(matricula);			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of adjustAllServicosPreferenciais method, of class ProdutoUsuarioBO.
	 */
	@Test
	public void testAdjustAllServicosPreferenciais() {
		System.out.println("adjustAllServicosPreferenciais");
		try {
			String matricula = "c000001";
			instance.adjustAllServicosPreferenciais(matricula);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
