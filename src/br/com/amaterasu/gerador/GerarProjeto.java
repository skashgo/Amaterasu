/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.gerador;

import br.com.amaterasu.model.Config;
import br.com.amaterasu.model.ModelProjeto;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.view.criarprojeto.Result;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

/**
 *
 * @author Maykon
 */
public class GerarProjeto {

    private static BufferedReader input;
    private static String line;

    public static void gerar() throws AmaterasuException {
        Config.read();
        if (Config.i() == null
                || Config.i().getCaminhoCatalogMaven() == null
                || Config.i().getCaminhoMaven() == null
                || Config.i().getCaminhoRepoMaven() == null
                || Config.i().getSo() == null
                || Config.i().getCaminhoCatalogMaven().isEmpty()
                || Config.i().getCaminhoMaven().isEmpty()
                || Config.i().getCaminhoRepoMaven().isEmpty()) {
            throw new AmaterasuException("Falta informação de configuração do projeto Amaterasu.\nVerificar no menu Configurar/Parametrização", false);
        }
        if (ModelProjeto.i().getArchetype().isProjetoExistente()) {
            JOptionPane.showMessageDialog(null, "Projeto Vinculado com Sucesso.");
            return;
        }
        try {
            StringBuilder command = new StringBuilder();
            command.append("@ECHO OFF\n");
            command.append("ECHO INICIANDO!!!\n");
            command.append("ECHO.\n");
            command.append("ECHO ################### AMATERASU ########################\n");
            command.append("ECHO ########     Este processo utiliza a ferramenta MAVEN \n");
            command.append("ECHO ########     com a funcionalidade de archetype         \n");
            command.append("ECHO ######################################################\n");
            command.append("ECHO.\n");
            command.append("@ECHO ON\n");
            if (Config.i().getSo().equals(Config.SO.WIN)) {
                command.append(ModelProjeto.i().getCaminho().substring(0, 1)).append(":\n");
            }
            command.append("cd \"").append(ModelProjeto.i().getCaminho()).append("\"\n");
            if (Config.i().getSo().equals(Config.SO.WIN)) {
                command.append("set MAVEN_OPTS=-Dmaven.repo.local=\"").append(Config.i().getCaminhoRepoMaven()).append("\" \n");
            } else {
                command.append("env MAVEN_OPTS=-Dmaven.repo.local=\"").append(Config.i().getCaminhoRepoMaven()).append("\" \n");
            }
            command.append("\"").append(Config.i().getCaminhoMaven()).append(IConstants.barra)
                    .append("mvn\" archetype:generate")
                    .append(" -DarchetypeCatalog=\"file:///").append(Config.i().getCaminhoCatalogMaven()).append(IConstants.barra).append("archetype-catalog.xml\"")
                    .append(" -DarchetypeGroupId=").append(ModelProjeto.i().getArchetype().getArchetypeGroupId())
                    .append(" -DarchetypeArtifactId=").append(ModelProjeto.i().getArchetype().getArchetypeArtifactId())
                    .append(" -DarchetypeVersion=").append(ModelProjeto.i().getArchetype().getArchetypeVersion())
                    .append(" -DgroupId=").append(ModelProjeto.i().getPacotePadrao())
                    .append(" -DartifactId=").append(ModelProjeto.i().getNomeProjeto())
                    .append(" -DinteractiveMode=false\n");
            command.append("ECHO FIM DA OPERACAO!!!");
            if (Config.i().getSo().equals(Config.SO.WIN)) {
                ManterTXT.write(command, new File("project.bat"));
            } else {
                ManterTXT.write(command, new File("project.sh"));
            }
            Process p = Runtime.getRuntime().exec("cmd /c project.bat");
            input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String r = "";
            while ((line = input.readLine()) != null) {
                r += line + "\n";
            }
            input.close();
            new Result(r).setVisible(true);

        } catch (IOException ex) {
            throw new AmaterasuException(ex);
        }
    }
}
