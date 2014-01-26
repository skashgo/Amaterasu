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

import br.gov.caixa.siiac.bo.IEfetuaVerificacaoBO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;

/**
 * @author GIS Consult
 *
 */
public class EfetuaVerificacaoBOTest extends TestWithSpring {
	
	@Autowired
	IEfetuaVerificacaoBO efetuaVerificacaoBO;
	
//	@Test
	public void testGetTabelas() {
		try {
			VerificacaoContratoVO verificacaoContratoVO = new VerificacaoContratoVO();
			verificacaoContratoVO.setCoRspnlVerificacao("c000001");
			verificacaoContratoVO.setDtLimiteVerificacao(new Date());
			verificacaoContratoVO.setDtVerificacao(new Date());
			verificacaoContratoVO.setIcEstadoVerificacao("FD");
			verificacaoContratoVO.setNoServicoVerificacao("ddddd");
			verificacaoContratoVO.setNuChecklistServicoVerificacaoProduto(3);
			verificacaoContratoVO.setNuVerificacaoContrato(1);
			System.out.println("################ " + efetuaVerificacaoBO.getMontaArvoreChecklist(verificacaoContratoVO).getBlocoChecklistProdutoList().size());
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSetNaoVerificadoToConforme() {
		try{
			Integer nuVerificacaoContrato = 44;
			this.efetuaVerificacaoBO.setNaoVerificadoToConforme(nuVerificacaoContrato);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
