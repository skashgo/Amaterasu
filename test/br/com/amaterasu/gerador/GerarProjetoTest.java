/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.gerador;

import br.com.amaterasu.model.Archetype;
import br.com.amaterasu.model.Config;
import br.com.amaterasu.model.ModelProjeto;
import java.io.BufferedReader;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Maykon
 */
public class GerarProjetoTest {

    static String line;
    static BufferedReader input;

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
        
        Config.i().setCaminhoCatalogMaven("D:\\Maykon\\Plataforma Amaterasu\\Core\\apache-maven-3.0.4");
        Config.i().setCaminhoMaven("D:\\Maykon\\Plataforma Amaterasu\\Core\\apache-maven-3.0.4\\bin");
        Config.i().setCaminhoRepoMaven("D:\\Maykon\\Plataforma Amaterasu\\Core\\apache-maven-3.0.4\\repository");
        
        ModelProjeto.i().setCaminho("D:\\TESTE");
        ModelProjeto.i().setPacotePadrao("br.com.amaterasu");
        ModelProjeto.i().setNomeProjeto("Amaterasu");
        ModelProjeto.i().setCaminhoServidor("D:\\Projetos\\Aplicativos\\jboss-4.2.3.GA");
        Archetype archetype=new Archetype();
        archetype.setArchetypeArtifactId("ModeloJEE6-archetype");
        archetype.setArchetypeGroupId("br.com.modelo");
        archetype.setArchetypeVersion("0.0.1");
        ModelProjeto.i().setArchetype(archetype);
        ModelProjeto.i().setCliente("GIS");
        ModelProjeto.i().setCaminhoAmaterasu("D:\\TESTE");
        ModelProjeto.i().setNomeCompleto("Amaterasu v 1.0 Framework/Tools");
        ModelProjeto.i().setCopyright("/**"
                + "\n* TODOS OS DIREITOS RESERVADOS"
                + "\n*/");
        File file = new File(ModelProjeto.i().getCaminho());
        ManterDiretorio.deleteDir(file);
        GerarProjeto.gerar();
        assertTrue("Erro ao gerar projeto.", file.exists());
        ManterDiretorio.deleteDir(file);

    }

}
