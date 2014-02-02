/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.gerador;

import br.com.amaterasu.model.ModelCrud;
import br.com.amaterasu.model.ModelProjeto;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.PathFramework;
import java.io.File;
import java.util.List;
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
public class GerarCrudTest {

    public GerarCrudTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ModelProjeto.i().setCaminho("D://Teste/ProjetoAmaterasu_Teste");
        ModelProjeto.i().setPacotePadrao("br.com.amaterasu");
        ModelProjeto.i().setNomeProjeto("Amaterasu");
        ModelProjeto.i().setCaminhoServidor("D:\\Projetos\\Aplicativos\\jboss-4.2.3.GA");
        ModelProjeto.i().setModelo(IConstants.MODELO_PADRAO);
        ModelProjeto.i().setCliente("GIS");
        ModelProjeto.i().setCaminhoAmaterasu("D://Teste/ProjetoAmaterasu_Teste_Amaterasu");
        ModelProjeto.i().setNomeCompleto("Amaterasu v 1.0 Framework/Tools");
        ModelProjeto.i().setCopyright("/**"
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
        ModelCrud.i().setCaminhoClasseBean("D:\\Projetos\\Projetos Eclipse\\workspace Ganymede\\SIIAC\\src\\br\\gov\\caixa\\siiac\\model\\domain\\Apontamento.java");
        ModelCrud.i().setNomeCasoUso("Apontamento");
        ModelCrud.i().setNomeModelo("Amaterasu-Model-1.0.jar");
//        ModelCrud.i().setModoFiltro(true);
        ModelCrud.i().setModoGrid(true);
//        ModelCrud.i().setModoCadastro(true);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of readBean method, of class GerarCrud.
     */
    @Test
    public void testReadBean() throws Exception {
        System.out.println("readBean");
        GerarCrud.readBean();
    }

    /**
     * Test of getFieldsBean method, of class GerarCrud.
     */
    @Test
    public void testGetFieldsBean() throws Exception {
        System.out.println("getFieldsBean");
        File file = new File(ModelCrud.i().getCaminhoClasseBean());
        int expResult = 4;
        List result = GerarCrud.getFieldsBean(file);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of gerar method, of class GerarCrud.
     */
    @Test
    public void testGerar() throws Exception {
        System.out.println("gerar");
        ModelCrud.i().getListFields().get(1).setShowFiltro(true);
        ModelCrud.i().getListFields().get(3).setShowFiltro(true);
        GerarCrud.gerar(PathFramework.pathFrameworkModelosCRUD(ModelProjeto.i().getModelo()) + ModelCrud.i().getNomeModelo());
    }
}
