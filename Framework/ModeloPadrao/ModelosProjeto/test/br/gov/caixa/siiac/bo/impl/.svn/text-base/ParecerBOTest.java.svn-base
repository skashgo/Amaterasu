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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IParecerBO;
import br.gov.caixa.siiac.bo.ListaPerfil;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.security.UsuarioPrincipalJAAS;

/**
 * @author GIS Consult
 *
 */
public class ParecerBOTest extends TestWithSpring {

	@Autowired
	private IParecerBO parecerBO;

	@Test
	public void testMoveRegistrosParaTabelaComParecer() {
		try {
			Integer nuVerificacaoContrato = 13126;
			
			IUsuario usuario = new UsuarioPrincipalJAAS("Gis Consult");
			usuario.setNome("Gis Consult");
			usuario.setMatricula("c000001");
			usuario.setUnidade((short)7875);
			usuario.setNuNatural(5541);
			List<Short> perfis = new ArrayList<Short>();
			perfis.add(ListaPerfil.PERFIL_GESTOR_SISTEMA);
			usuario.setPerfis(perfis); 

			String caminhoPastaImagens = "D:\\Desenvolvimento\\Projetos\\SIIAC\\trunk\\implementacao\\fontes\\java\\SIIAC\\WebContent\\images";
			
			parecerBO.geraParecer(nuVerificacaoContrato, usuario, caminhoPastaImagens);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
}
