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

import br.gov.caixa.siiac.bo.IApontamentoBO;
import br.gov.caixa.siiac.model.domain.Apontamento;

public class ApontamentoBOTest extends TestWithSpring {
	
	@Autowired
	private IApontamentoBO apontamentoBO;
	//######################################################################################
	/**
	 * Testando o método apontamentoBO.getListFiltro()
	 */
	@Test
	public void testaGetListFiltroEncontraAtivos() {

		String pesquisa = "Possui participação em outras empresas";
		//Valor do checkbok. Caso esteja FALSE buscará apenas os ativos
		//Caso esteja TRUE buscará apenas os inativos
		boolean mostraInativos = false; 
		
		try {
			List<Apontamento> lista = apontamentoBO.getListFiltro(pesquisa, mostraInativos);
			assertTrue(lista.size() == 1);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaGetListFiltroEncontraInativos() {

		String pesquisa = "Encontra Inativos";
		//Valor do checkbok. Caso esteja FALSE buscará apenas os ativos
		//Caso esteja TRUE buscará apenas os inativos
		boolean mostraInativos = true; 
		
		try {
			List<Apontamento> lista = apontamentoBO.getListFiltro(pesquisa, mostraInativos);
			assertTrue(lista.size() == 1);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaGetListFiltroNaoEncontraAtivos() {

		String pesquisa = "Zimbábue";
		//Valor do checkbok. Caso esteja FALSE buscará apenas os ativos
		//Caso esteja TRUE buscará apenas os inativos
		boolean mostraInativos = false; 
		
		try {
			List<Apontamento> lista = apontamentoBO.getListFiltro(pesquisa, mostraInativos);
			assertTrue(lista.size() == 1);
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaGetListFiltroNaoEncontraInativos() {

		String pesquisa = "Não Encontra inativos";
		//Valor do checkbok. Caso esteja FALSE buscará apenas os ativos
		//Caso esteja TRUE buscará apenas os inativos
		boolean mostraInativos = true; 
		
		try {
			List<Apontamento> lista = apontamentoBO.getListFiltro(pesquisa, mostraInativos);
			assertTrue(lista.size() == 1);
		} catch(Exception ex) {
			fail();
		}
	}
	//######################################################################################
	
	
	//######################################################################################
	/**
	 * Testando o método existUpdate()
	 */
	@Test
	public void testaExistTrue() {
		Apontamento apontamento = new Apontamento();
		apontamento.setNuApontamentoChecklist(13);
		apontamento.setNoApontamentoChecklist("CADASTRO DE APONTAMENTO PARA BUSCAR NOME");
		apontamento.setIcAtivo(true);
		
		try {
			assertFalse(apontamentoBO.exist(apontamento));
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaExistFalse() {
		Apontamento apontamento = new Apontamento();
		apontamento.setNuApontamentoChecklist(99);
		apontamento.setNoApontamentoChecklist("Não existe Apontamento com esse Nome");
		apontamento.setIcAtivo(true);
		
		try {
			assertFalse(apontamentoBO.exist(apontamento));
		} catch(Exception ex) {
			fail();
		}
	}
	//######################################################################################
	
	
	//######################################################################################
	/**
	 * Testando o método merge()
	 */
	@Test
	public void testaMerge() {
		Apontamento apontamento = new Apontamento();
		apontamento.setNuApontamentoChecklist(28);
		apontamento.setNoApontamentoChecklist("Apontamento Novo Teste");
		apontamento.setIcAtivo(true);
		
		try {
			apontamentoBO.merge(apontamento);
		} catch(Exception ex) {
			fail();
		}
	}
	//######################################################################################
	
	
	//######################################################################################
	/**
	 * Testando o método ativaInativa()
	 */
	@Test
	public void testaAtiva() {
		Apontamento apontamento = new Apontamento();
		apontamento.setNuApontamentoChecklist(10);
		apontamento.setNoApontamentoChecklist("falta datar Encontra Inativos");
		apontamento.setIcAtivo(false);
		
		try {
			apontamentoBO.ativaInativa(apontamento);
			assertTrue(apontamento.getIcAtivo());
		} catch(Exception ex) {
			fail();
		}
	}
	
	@Test
	public void testaInativa() {
		Apontamento apontamento = new Apontamento();
		apontamento.setNuApontamentoChecklist(13);
		apontamento.setNoApontamentoChecklist("CADASTRO DE APONTAMENTO PARA BUSCAR NOME");
		apontamento.setIcAtivo(true);
		
		try {
			apontamentoBO.ativaInativa(apontamento);
			assertTrue(apontamento.getIcAtivo());
		} catch(Exception ex) {
			fail();
		}
	}
	//######################################################################################
}