/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.gerador;

import br.com.amaterasu.model.CriarModeloBean;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.NodeJtree;
import br.com.amaterasu.util.PathFramework;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Maykon
 */
public class GerarModelo {

    /**
     * Este metodo vai ler o arquivo conf.txt que deve estar na mesma pasta que
     * o jar do projeto Amaterasu e criar as estruturas de pasta conforme
     * informado no arquivo.
     *
     * @throws AmaterasuException
     */
    public static void gerar() throws AmaterasuException {
        try {
            //Instancia de file com endereco do novo modelo
            File dirNovoModelo = new File(PathFramework.pathFramework() + IConstants.barra + CriarModeloBean.i().getNomeModelo() + IConstants.barra);
            //Cria o diretorio do novo modelo
            dirNovoModelo.mkdirs();
            //Cria o diretorio raiz dentro do diretorio novo modelo
            new File(dirNovoModelo.getAbsolutePath() + IConstants.barra + IConstants.DIR_RAIZ).mkdirs();
            //Cria o diretorio ArquivosModelo dentro do diretorio novo modelo
            new File(dirNovoModelo.getAbsolutePath() + IConstants.barra + IConstants.DIR_MODELOS_PROJETO).mkdirs();
            //Instancia de file modelo Padrao
            File dirModeloRaiz = new File(PathFramework.pathFrameworkRaiz(IConstants.MODELO_PADRAO));
            //Copia os arquivos dentro da pasta raiz do modelo padrao para a pasta raiz do novo modelo
            for (File f : dirModeloRaiz.listFiles()) {
                ManterDiretorio.copyFile(f, new File(PathFramework.pathFrameworkRaiz(CriarModeloBean.i().getNomeModelo()) + IConstants.barra + f.getName()));
            }

            //Cria o arquivo de Especificacao.txt
            StringBuilder sbEsp = new StringBuilder();
            for (String s : CriarModeloBean.i().getTecnoligas()) {
                sbEsp.append(s).append(System.getProperty("line.separator"));
            }
            ManterTXT.write(sbEsp, new File(dirNovoModelo + IConstants.barra + IConstants.FILE_ESPECIFICACAO));


            //Cria o arquivo de Pacotes.txt do novo modelo
            StringBuilder sb = new StringBuilder("#1 Estrutura do projeto");
            for (NodeJtree node : CriarModeloBean.i().getArvore()) {
                sb.append(System.getProperty("line.separator")).append(node.getNome().substring(node.getNome().indexOf("-") + 1));
            }
            ManterTXT.write(sb, new File(dirNovoModelo + IConstants.barra + IConstants.FILE_PACOTES));

            //Instancia de file com endereco do diretorio ArquivosModelo do novo modelo
            File dirArquivosModeloNovo = new File(PathFramework.pathFrameworkModelosProjeto(CriarModeloBean.i().getNomeModelo()));
            File dirArquivosModeloPadrao = new File(PathFramework.pathFrameworkModelosProjeto(IConstants.MODELO_PADRAO));

            abrirArquivoRecursivo(dirArquivosModeloNovo, dirArquivosModeloPadrao);
        } catch (IllegalArgumentException ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao gerar estrutura do projeto.", true);
        } catch (IllegalAccessException ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao gerar estrutura do projeto.", true);
        } catch (FileNotFoundException ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao gerar estrutura do projeto.", true);
        } catch (IOException ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao gerar estrutura do projeto.", true);
        }
    }

    private static void abrirArquivoRecursivo(File modeloNovo, File modeloPadrao) throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException {
        String stringArquivo = "";
        String pathArquivo = "";
        //percorre a lista de arquivos da pasta modelo padrao
        for (File f : modeloPadrao.listFiles()) {
            //se o arquivo for um diretorio cria um diretorio com o mesmo nome da pasta modelo novo
            if (f.isDirectory()) {
                File fileNovo = new File(modeloNovo.getAbsolutePath() + IConstants.barra + f.getName());
                fileNovo.mkdirs();
                abrirArquivoRecursivo(fileNovo, f);
                //se não for um diretorio copia o arquivo substituindo a primera linha (path)
            } else {
                stringArquivo = ManterTXT.readFile(f).toString();

                //------------
                //Implementar aqui a substituição do path
                //-------------

                //Cria arquivo 
                ManterTXT.write(new StringBuilder(stringArquivo), new File(modeloNovo.getAbsolutePath() + IConstants.barra + f.getName()));
            }
        }
    }
}
