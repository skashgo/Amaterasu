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

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IPreferenciasUsuarioBO;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuario;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.TipoGarantia;
import br.gov.caixa.siiac.model.domain.TipoGarantiaProduto;
import br.gov.caixa.siiac.model.domain.Usuario;

/**
 *
 * @author GisConsult
 */
public class PreferenciasUsuarioBOTest extends TestWithSpring {
	@Autowired
	private IPreferenciasUsuarioBO preferenciasUsuarioBO;
	
	/**
	 * Test of salvaLista method, of class PreferenciasUsuarioBO.
	 */
	@Test
	public void testSalvaLista() {
		System.out.println("salvaLista");
		try {
			String matricula = "c025759";
			List<PreferenciasUsuario> produtos = new ArrayList<PreferenciasUsuario>();
			PreferenciasUsuario preferenciasUsuario = new PreferenciasUsuario();
			preferenciasUsuario.setIcEstadoGarantia("AB");
			preferenciasUsuario.setIcEstadoVerificacao("BA");
			preferenciasUsuario.setIcSituacaoContrato("ABCDEF");
			
			Produto produto = new Produto();
			produto.setCoProduto("0001001");
			
			Usuario usuario = new Usuario();
			usuario.setCoUsuario(matricula);
			
			preferenciasUsuario.setProduto(produto);
			preferenciasUsuario.setUsuario(usuario);
			
			preferenciasUsuario.getId().setCoProduto(produto.getCoProduto());
			preferenciasUsuario.getId().setCoUsuario(matricula);
			TipoGarantia tp = new TipoGarantia();
			tp.setNuTipoGarantia(1);
			TipoGarantiaProduto tpgp = new TipoGarantiaProduto();
			tpgp.setTipoGarantia(tp);
			tpgp.setCoProduto(produto);
			preferenciasUsuario.setNuTipoGarantiaProduto(tpgp);
			
			produtos.add(preferenciasUsuario);
			preferenciasUsuarioBO.salvaLista(matricula, produtos);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of getAll method, of class PreferenciasUsuarioBO.
	 */
	@Test
	public void testGetAll() {
		System.out.println("getAll");
		try {
			String matricula = "c025759";
			List result = null;
			result = preferenciasUsuarioBO.getAll(matricula);
			assertTrue(result.size() > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
}
