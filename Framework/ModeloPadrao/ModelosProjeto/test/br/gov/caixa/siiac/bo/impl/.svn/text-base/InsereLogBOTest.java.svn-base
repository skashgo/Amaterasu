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

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IInsereLogBO;
import br.gov.caixa.siiac.model.LogVO;

/**
 *
 * @author Gis Consult
 */
public class InsereLogBOTest extends TestWithSpring {
	
	@Autowired
	private IInsereLogBO insereLogBO;
	
	@Test
	public void testInsereLogBO() {
		try {
			LogVO logVO = new LogVO();
			logVO.setCoTerminoImportacao("");
			logVO.setDtGeracao(new Date());
			logVO.setDtReferencia(new Date());
			logVO.setIcStatusImportado(true);
			logVO.setNoArquivoImportado("Arquivo teste");
			logVO.setQtRegistros(2);
			logVO.setQtRegistrosAlterados(1);
			logVO.setQtRegistrosIncluidos(2);
			logVO.setSgSistemaGerador("SIEST");
			logVO.setTsImportacao(new Date());
			String subject = "Teste Envio de Email Log Importação";
			//Busca uma lista de Blocos que contenha a palavra Documentos em seu nome
			insereLogBO.insereLog(logVO, subject);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
