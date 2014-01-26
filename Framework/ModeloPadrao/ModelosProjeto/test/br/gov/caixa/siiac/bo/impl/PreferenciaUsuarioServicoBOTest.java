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

import br.gov.caixa.siiac.bo.IPreferenciaUsuarioServicoBO;
import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioServico;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.Usuario;

/**
 *
 * @author GisConsult
 */
public class PreferenciaUsuarioServicoBOTest extends TestWithSpring {
	
	@Autowired
	private IPreferenciaUsuarioServicoBO instance;

	
	/**
	 * Test of getAll method, of class PreferenciaUsuarioServicoBO.
	 */
	@Test
	public void testGetAll() {
		System.out.println("getAll");
		try {
			List result = instance.getAll("c025759");
			assertTrue(result.size() > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test of salvarLista method, of class PreferenciaUsuarioServicoBO.
	 */
	@Test
	public void testSalvarLista() {
		System.out.println("salvarLista");
		try {
			String matricula = "c025759";
			List<PreferenciaUsuarioServico> servicos = new ArrayList<PreferenciaUsuarioServico>();
			
			PreferenciaUsuarioServico pus = new PreferenciaUsuarioServico();

			Usuario usuario = new Usuario();
			usuario.setCoUsuario(matricula);
			
			pus.setUsuario(usuario);
			pus.getId().setCoUsuario(usuario.getCoUsuario());
			
			ServicoVerificacao servicoVerificacao = new ServicoVerificacao();
			servicoVerificacao.setNuServicoVerificacao(1);
			
			pus.setServicoVerificacao(servicoVerificacao);
			pus.getId().setNuServicoVerificacao(servicoVerificacao.getNuServicoVerificacao());
						
			instance.salvarLista(matricula, servicos);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
