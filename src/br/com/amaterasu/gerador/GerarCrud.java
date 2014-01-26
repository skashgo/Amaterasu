/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.gerador;

import br.com.amaterasu.model.CriarCRUDBean;
import br.com.amaterasu.model.CriarProjetoBean;
import br.com.amaterasu.util.AmaterasuClassLoader;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.Field;
import br.com.amaterasu.util.Format;
import br.com.amaterasu.util.IAmaterasuModel;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.Model;
import br.com.amaterasu.util.PathFramework;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Maykon
 */
public class GerarCrud {

    public static String getInfo(String path) throws AmaterasuException {
        String info = "";
        try {
            String jar = "jar:file:" + path + "!/";
            //instanciando e passando o objeto CriarCRUDBean para o modelo;
            String classe = "br.com.amaterasu.model.CriarCRUDBean";
            ((CriarCRUDBean) new AmaterasuClassLoader(jar, classe).getInstance()).set(CriarCRUDBean.i());
            //instanciando e passando o objeto CriarProjetoBean para o modelo;
            classe = "br.com.amaterasu.model.CriarProjetoBean";
            ((CriarProjetoBean) new AmaterasuClassLoader(jar, classe).getInstance()).set(CriarProjetoBean.i());
            //instanciando e buscando o resultado do modelo;
            classe = "br.com.amaterasu.main.AmaterasuModel";
            info = ((IAmaterasuModel) new AmaterasuClassLoader(jar, classe).getInstance()).getInfo();
        } catch (Exception ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao acessar jar de modelo.", true);
        }
        return info;
    }

    public static void gerar(String path) throws AmaterasuException {
        List<Model> list = new ArrayList<Model>();
        try {
            String jar = "jar:file:" + path + "!/";
            //instanciando e passando o objeto CriarCRUDBean para o modelo;
            String classe = "br.com.amaterasu.model.CriarCRUDBean";
            ((CriarCRUDBean) new AmaterasuClassLoader(jar, classe).getInstance()).set(CriarCRUDBean.i());
            //instanciando e passando o objeto CriarProjetoBean para o modelo;
            classe = "br.com.amaterasu.model.CriarProjetoBean";
            ((CriarProjetoBean) new AmaterasuClassLoader(jar, classe).getInstance()).set(CriarProjetoBean.i());
            //instanciando e buscando o resultado do modelo;
            classe = "br.com.amaterasu.main.AmaterasuModel";
            list = ((IAmaterasuModel) new AmaterasuClassLoader(jar, classe).getInstance()).getListContentFile();
        } catch (Exception ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao acessar jar de modelo.", true);
        }
        try {
            String arquivosGerados = "Arquivos Criados/Alterados:\n";
            for (Model model : list) {
                System.out.println(model.getCaminho());
                if (model.getDocument() != null) {
                    ManterXML.writeXML(model.getDocument(), new File(model.getCaminho()));
                } else {
                    ManterTXT.write(new StringBuilder(model.getConteudo()), new File(model.getCaminho()));
                }
                arquivosGerados += model.getCaminho() + "\n";
            }
            JOptionPane.showMessageDialog(null, arquivosGerados);
            System.out.println("CRUD gerado com sucesso!");
        } catch (Exception ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao escrever arquivos do CRUD.", true);
        }
    }

    /**
     * Este metodo copia/converte todos os arquivos do modelo Crud para o
     * projeto
     *
     * @throws AmaterasuException
     */
    @Deprecated
    public static void gerar() throws AmaterasuException {
        try {
            //Copia/Converte todos os arquivos que estão da pasta de modelos CRUD
            File arquivosModelo = new File(PathFramework.pathFrameworkModelosCRUD(CriarProjetoBean.i().getModelo()));
            abrirArquivoRecursivo2(arquivosModelo);

        } catch (IllegalArgumentException ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao gerar CRUD.", true);
        } catch (IllegalAccessException ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao gerar CRUD.", true);
        } catch (IOException ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao gerar CRUD.", true);
        } catch (NullPointerException ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao gerar CRUD.", true);
        }
    }

    private static void abrirArquivoRecursivo2(File arquivoModelo) throws IOException, IllegalArgumentException, IllegalAccessException, NullPointerException {
        String pathArquivo = "";
        String valueArquivo = "";
        for (File f : arquivoModelo.listFiles()) {
            if (f.isDirectory()) {
                abrirArquivoRecursivo2(f);
            } else {
                //le o arquivo do modelo e atribui para a variavel valueArquivo
                List<String> lines = ManterTXT.readListLine(f);
                //--------------Verifica se o arquivo possui configuração de path (local para onde copiar o arquivo e nome do mesmo)
                if (lines == null || lines.isEmpty() || lines.get(0).equals("") || !lines.get(0).contains("#PATH[")) {
                    continue;
                }
                for (String line : lines) {
                    line = showLine(line);
                    //----------------substitui as variaveis do arquivo--------------------//


                    for (java.lang.reflect.Field field : CriarProjetoBean.class
                            .getFields()) {
                        String value = field.get(CriarProjetoBean.i()) != null ? field.get(CriarProjetoBean.i()).toString() : "";
                        line = line.toString().replace("@{" + field.getName() + "}", value);
                        line = line.toString().replace("@{UC-" + field.getName() + "}", Format.maiuscula1(value));
                        line = line.toString().replace("@{LC-" + field.getName() + "}", Format.minuscula1(value));
                    }


                    for (java.lang.reflect.Field field : CriarCRUDBean.class
                            .getFields()) {
                        String value = field.get(CriarCRUDBean.i()) != null ? field.get(CriarCRUDBean.i()).toString() : "";
                        line = line.toString().replace("@{" + field.getName() + "}", value);
                        line = line.toString().replace("@{UC-" + field.getName() + "}", Format.maiuscula1(value));
                        line = line.toString().replace("@{LC-" + field.getName() + "}", Format.minuscula1(value));
                    }
                    if (line.contains("@{FF-field}")) {
                        line = line.replace("@{FF-field}", "");
                        String lineTemp = line;
                        for (Field field : CriarCRUDBean.i().getListFields()) {
                            line = lineTemp;
                            for (java.lang.reflect.Field fieldBean : field.getClass().getFields()) {
                                //Valida Show line
                                line = showLineEE(line, fieldBean, field);
                                line = showLineOR(line, fieldBean, field);
                                //-------------
                                String value = fieldBean.get(field) != null ? fieldBean.get(field).toString() : "";
                                line = line.toString().replace("@{VF-" + fieldBean.getName() + "}", value);
                                line = line.toString().replace("@{UC-VF" + fieldBean.getName() + "}", Format.maiuscula1(value));
                                line = line.toString().replace("@{LC-VF" + fieldBean.getName() + "}", Format.minuscula1(value));
                            }
                            if (!line.equals("")) {
                                line += "\n";
                            }
                            valueArquivo += line;
                        }
                        line = "";
                    }
                    line += "\n";
                    valueArquivo += line;
                }
                //-------------escreve o arquivo-------------------//
                pathArquivo = valueArquivo.substring(valueArquivo.indexOf("#PATH[") + 6, valueArquivo.indexOf("]"));
                pathArquivo = pathArquivo.replace(".", IConstants.barra).replace("/", IConstants.barra).replace("\\", IConstants.barra);
                pathArquivo = pathArquivo.concat(f.getName().substring(f.getName().indexOf(".")));
                valueArquivo = valueArquivo.substring(valueArquivo.indexOf("]") + 1);
                ManterTXT.write(new StringBuilder(valueArquivo), new File(CriarProjetoBean.i().getCaminho() + IConstants.barra + pathArquivo));
            }
        }

    }
    static Boolean show = null;

    private static String showLine(String line) throws IllegalArgumentException, IllegalAccessException {
        show = null;


        for (java.lang.reflect.Field field : CriarProjetoBean.class
                .getFields()) {
            line = showLineEE(line, field, CriarProjetoBean.i());
            line = showLineOR(line, field, CriarProjetoBean.i());
        }


        for (java.lang.reflect.Field field : CriarCRUDBean.class
                .getFields()) {
            line = showLineEE(line, field, CriarCRUDBean.i());
            line = showLineOR(line, field, CriarCRUDBean.i());
        }
        if (show != null && !show) {
            line = "";
        }
        return line;
    }

    private static String showLineEE(String line, java.lang.reflect.Field field, Object object) throws IllegalArgumentException, IllegalAccessException {
        String value = field.get(object) != null ? field.get(object).toString() : "";
        //se começar com Show Line, valida se o valor é false e não insere a linha
        System.out.println("@{SLE-" + field.getName() + "}");
        if (line.contains("@{SLE-" + field.getName() + "}")) {
            if (value.equals("false")) {
                return "";
            } else {
                line = line.replace("@{SLE-" + field.getName() + "}", "");
                return showLineEE(line, field, object);
            }
        }
        return line;
    }

    private static String showLineOR(String line, java.lang.reflect.Field field, Object object) throws IllegalArgumentException, IllegalAccessException {
        String value = field.get(object) != null ? field.get(object).toString() : "";
        //se começar com Show Line, valida se o valor é false e não insere a linha
        System.out.println("@{SLO-" + field.getName() + "}");
        if (line.contains("@{SLO-" + field.getName() + "}")) {
            if (value.equals("true")) {
                show = true;
                line = line.replace("@{SLO-" + field.getName() + "}", "");
            } else {
                line = line.replace("@{SLO-" + field.getName() + "}", "");
                if (show == null) {
                    show = false;
                }
            }
            return showLineOR(line, field, object);
        }
        return line;
    }

    public static String getComponente(Field ff) throws IOException, IllegalArgumentException, IllegalAccessException {
        String str = "", retorno = "";
        File file = new File(PathFramework.pathFrameworkComponentes(CriarProjetoBean.i().getModelo(), CriarCRUDBean.i().getNomeModelo()) + ff.getComponente() + ".txt");
        if (file.exists()) {
            str = ManterTXT.readFile(file).toString();


            if (!str.equals("")) {
                for (java.lang.reflect.Field fieldField : Field.class
                        .getFields()) {
                    String value = "";

                    if (fieldField.get(ff)
                            != null) {
                        value = fieldField.get(ff).toString();
                    }

                    if (str.indexOf(
                            "#" + fieldField.getName()) != -1) {
                        retorno += str.substring(str.indexOf("#" + fieldField.getName()), str.indexOf("#" + fieldField.getName() + "#"));
                    }
                    for (java.lang.reflect.Field field : CriarCRUDBean.class
                            .getFields()) {
                        String value2 = "";

                        if (field.get(CriarCRUDBean.i()) != null) {
                            value2 = field.get(CriarCRUDBean.i()).toString();
                        }
                        retorno = retorno.toString().replace("@{UC-" + field.getName() + "}", Format.maiuscula1(value2));
                        retorno = retorno.toString().replace("@{LC-" + field.getName() + "}", Format.minuscula1(value2));
                        retorno = retorno.toString().replace("@{" + field.getName() + "}", value2);
                        retorno = retorno.toString().replace("@{field}", ff.getNome());
                    }
                }
            }
        }
        return retorno;
    }

    /**
     * Este método le a classe bean e separa os atributos da classe, preenchendo
     * a lista de field de CriarCRUDBean;
     *
     * @throws AmaterasuException
     */
    public static void readBean() throws AmaterasuException {
        try {
            File file = new File(CriarCRUDBean.i().getCaminhoClasseBean());
            if (!file.exists()) {
                throw new AmaterasuException("O caminho informado está incorreto.\n" + file.getAbsolutePath(), false);
            }
            CriarCRUDBean.i().setListFields(getFieldsBean(file));
        } catch (FileNotFoundException ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao ler classe Bean.", true);
        } catch (IOException ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao ler classe Bean.", true);
        } catch (Exception ex) {
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao ler classe Bean.", true);
        }

    }

    public static List<Field> getFieldsBean(File file) throws AmaterasuException, FileNotFoundException, IOException {
        List<String> listLinhasArquivoBean = ManterTXT.readListLine(file);
        if (listLinhasArquivoBean.size() < 4) {
            throw new AmaterasuException("A classe Bean informada (" + file.getName() + ") não possui o mínimo de informação necessária para gerar o CRUD.", false);
        }
        List<Field> list = new ArrayList<Field>();
        Field f = null;
        for (String line : listLinhasArquivoBean) {
            line = trim(line);
            if (line.contains("private") && line.endsWith(";") && !line.contains("static")) {
                line = line.replace("private", "").replace(";", "").trim();
                f = new Field();
                f.setNome(line.split(" ")[1]);
                f.setTipo(line.split(" ")[0]);
                if (f.getTipo().equalsIgnoreCase("Boolean")) {
                    f.setComponente("CheckBox");
                    for (String s : listLinhasArquivoBean) {
                        if (s.contains("is" + Format.maiuscula1(f.getNome()))) {
                            f.setMetodoIS(true);
                            break;
                        }
                    }
                } else if (f.getTipo().equals("Date")) {
                    f.setComponente("Data");
                } else {
                    f.setComponente("Input");
                }
                f.setLabel(Format.maiuscula1(f.getNome()));
                list.add(f);
            }
        }
        return list;
    }

    private static String trim(String str) {
        return str.replaceAll(" +", " ").trim();
    }
}
