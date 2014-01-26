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

import br.gov.caixa.siiac.bo.IServicoVerificacaoProdutoBO;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;

/**
 *
 * @author GisConsult
 */
public class ServicoVerificacaoProdutoBOTest extends TestWithSpring {
	
	@Autowired
	private IServicoVerificacaoProdutoBO instance;
	
	/**
	 * Test of get method by nuServicoVerificacaoProduto, of class ServicoVerificacaoProdutoBO.
	 */
	@Test
	public void testGetByNuServicoVerificacaoProduto() {
		System.out.println("get by nuServicoVerificacaoProduto");
		try {
			ServicoVerificacaoProduto result = instance.get(2);
			assertNotNull(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test of get method by nuServicoVerificacaoProduto, of class ServicoVerificacaoProdutoBO.
	 */
	@Test
	public void testGetByProdutoAndServicoVerificacao(){
		System.out.println("get by Produto and ServicoVerificacao");
		try{
			Produto prod = new Produto();
			prod.setCoProduto("0001001");
	
			ServicoVerificacao sv = new ServicoVerificacao();
			sv.setNuServicoVerificacao(1);
	
			ServicoVerificacaoProduto svp = instance.get(prod, sv);
			assertNotNull(svp);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of getAll method, of class ServicoVerificacaoProdutoBO.
	 */
	@Test
	public void testGetAll() {
		System.out.println("getAll");
		try {
			Produto p = new Produto();
			p.setCoProduto("0001001");
			List<ServicoVerificacaoProduto> result = instance.getAll(p);
			assertTrue(!result.isEmpty());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of salvar method, of class ServicoVerificacaoProdutoBO.
	 */
	@Test
	public void testSalvar() {
		System.out.println("salvar");
		try {
			ServicoVerificacaoProduto svp = new ServicoVerificacaoProduto();

			Produto p = new Produto();
			p.setCoProduto("0001001");
			
			ServicoVerificacao sv = new ServicoVerificacao();
			sv.setNuServicoVerificacao(4);
			
			svp.setProduto(p);
			svp.setServicoVerificacao(sv);
			
			instance.salvar(svp);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of excluir method, of class ServicoVerificacaoProdutoBO.
	 */
	@Test
	public void testExcluir() {
		System.out.println("excluir");
		try {
			ServicoVerificacaoProduto svp = new ServicoVerificacaoProduto();
			svp.setNuServicoVerificacaoProduto(1);
			
			instance.excluir(svp);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
}
