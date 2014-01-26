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

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IEnviaNotificacaoBO;

/**
 * @author GIS Consult
 *
 */
public class EnviaNotificacaoBOTest extends TestWithSpring {
	
	
	IEnviaNotificacaoBO enviaNotificacaoBO;
	
	@Autowired
	public void setEnviaNotificacaoBO(IEnviaNotificacaoBO enviaNotificacaoBO) {
		this.enviaNotificacaoBO = enviaNotificacaoBO;
	}


	@Test
	public void testeEnviaNotificacao()
	{
		try
		{
		enviaNotificacaoBO.obtemVerificacoesRN090A();
		} catch (Exception e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
