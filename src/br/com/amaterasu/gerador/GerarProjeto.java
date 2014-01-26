/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.gerador;

import br.com.amaterasu.model.CriarProjetoBean;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.PathFramework;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 *
 * @author Maykon
 */
public class GerarProjeto {

    /**
     * Este metodo vai ler o arquivo Pacotes.txt que deve estar na raiz pasta do
     * modelo do framework e criar as estruturas de pasta conforme informado no
     * arquivo.
     *
     * @throws AmaterasuException
     */
    public static void gerar() throws AmaterasuException {
        try {
            //Cria a estrutura de diretorios conforme definido no arquivo pacotes (Arquitetura)
            File file = null;
            File conf = new File(PathFramework.pathFrameworkPacotes(CriarProjetoBean.i().getModelo()));
            for (String s : ManterTXT.readListLine(conf)) {
                if (!s.startsWith("#")) {
                    String path = s.replace("@{pacotePadrao}", CriarProjetoBean.i().getPacotePadrao())
                            .replace(".", IConstants.barra)
                            .replace("/", IConstants.barra)
                            .replace("\\", IConstants.barra);
                    file = new File(CriarProjetoBean.i().getCaminho() + path);
                    file.mkdirs();
                }
            }
            //Copia/Converte os arquivos da pasta raiz para a raiz do projeto (.classpath, .project, pom.xml...)
            File raiz = new File(PathFramework.pathFrameworkRaiz(CriarProjetoBean.i().getModelo()));
            String stringArquivo = "";
            for (File f : raiz.listFiles()) {
                stringArquivo = ManterTXT.readFile(f).toString();
                for (Field field : CriarProjetoBean.class.getFields()) {
                    String value = "";
                    if (field.get(CriarProjetoBean.i()) != null) {
                        value = field.get(CriarProjetoBean.i()).toString();
                    }
                    stringArquivo = stringArquivo.toString().replace("@{" + field.getName() + "}", value);
                }
                ManterTXT.write(new StringBuilder(stringArquivo), new File(CriarProjetoBean.i().getCaminho() + f.getName()));
            }
            //Copia/Converte todos os arquivos que estão da pasta de modelos projeto
            File arquivosModelo = new File(PathFramework.pathFrameworkModelosProjeto(CriarProjetoBean.i().getModelo()));
            abrirArquivoRecursivo(arquivosModelo);

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
        } catch (NullPointerException ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao gerar estrutura do projeto.", true);
        }
    }

    private static void abrirArquivoRecursivo(File arquivoModelo) throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException, NullPointerException {
        String stringArquivo = "";
        String pathArquivo = "";
        for (File f : arquivoModelo.listFiles()) {
            if (f.isDirectory()) {
                abrirArquivoRecursivo(f);
            } else {
                stringArquivo = ManterTXT.readFile(f).toString();
                if (stringArquivo.equals("") || !stringArquivo.contains("#PATH[")) {
                    continue;
                }
                for (Field field : CriarProjetoBean.class.getFields()) {
                    String value = "";
                    if (field.get(CriarProjetoBean.i()) != null) {
                        value = field.get(CriarProjetoBean.i()).toString();
                    }
                    stringArquivo = stringArquivo.toString().replace("@{" + field.getName() + "}", value);
                }
                pathArquivo = stringArquivo.substring(stringArquivo.indexOf("#PATH[") + 6, stringArquivo.indexOf("]"));
                pathArquivo = pathArquivo.replace(".", IConstants.barra).replace("/", IConstants.barra).replace("\\", IConstants.barra);
                pathArquivo = pathArquivo.concat(f.getName().substring(f.getName().indexOf(".")));
                System.out.println(pathArquivo);
                stringArquivo = stringArquivo.substring(stringArquivo.indexOf("]") + 1);
                ManterTXT.write(new StringBuilder(stringArquivo), new File(CriarProjetoBean.i().getCaminho() + pathArquivo));
            }
        }
    }
}
