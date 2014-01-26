/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.gerador;

import br.com.amaterasu.model.CriarProjetoBean;
import br.com.amaterasu.util.IConstants;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Maykon
 */
public class GerarProjetoTest {

    public GerarProjetoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of gerar method, of class GerarProjeto.
     */
    @Test
    public void testGerar() throws Exception {
        System.out.println("gerar");
        CriarProjetoBean.i().setCaminho("D://Teste/ProjetoAmaterasu_Teste/");
        CriarProjetoBean.i().setPacotePadrao("br.com.amaterasu");
        CriarProjetoBean.i().setNomeProjeto("Amaterasu");
        CriarProjetoBean.i().setCaminhoServidor("D:\\Projetos\\Aplicativos\\jboss-4.2.3.GA");
        CriarProjetoBean.i().setModelo(IConstants.MODELO_PADRAO);
        CriarProjetoBean.i().setCliente("GIS");
        CriarProjetoBean.i().setCaminhoAmaterasu("D://Teste/ProjetoAmaterasu_Teste_Amaterasu/");
        CriarProjetoBean.i().setNomeCompleto("Amaterasu v 1.0 Framework/Tools");
        CriarProjetoBean.i().setCopyright("/**"
                + "\n* Copyright (c) 2009-2011 Caixa Econômica Federal. Todos os direitos"
                + "\n* reservados."
                + "\n* "
                + "\n* Caixa Econômica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade"
                + "\n* "
                + "\n* Este programa de computador foi desenvolvido sob demanda da CAIXA e está"
                + "\n* protegido por leis de direitos autorais e tratados internacionais. As"
                + "\n* condições de cópia e utilização do todo ou partes dependem de autorização da"
                + "\n* empresa. Cópias não são permitidas sem expressa autorização. Não pode ser"
                + "\n* comercializado ou utilizado para propósitos particulares."
                + "\n* "
                + "\n* Uso exclusivo da Caixa Econômica Federal. A reprodução ou distribuição não"
                + "\n* autorizada deste programa ou de parte dele, resultará em punições civis e"
                + "\n* criminais e os infratores incorrem em sanções previstas na legislação em"
                + "\n* vigor."
                + "\n* "
                + "\n* Histórico do Subversion:"
                + "\n* "
                + "\n* LastChangedRevision:  "
                + "\n* LastChangedBy:  "
                + "\n* LastChangedDate: "
                + "\n* "
                + "\n* HeadURL: "
                + "\n* "
                + "\n*/");
        File file = new File(CriarProjetoBean.i().getCaminho());
        ManterDiretorio.deleteDir(file);
        GerarProjeto.gerar();
        assertTrue("Erro ao gerar projeto.", file.exists());
//        ManterDiretorio.deleteDir(file);

    }
}
