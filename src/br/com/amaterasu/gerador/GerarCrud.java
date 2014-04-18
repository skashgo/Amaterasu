/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.gerador;

import br.com.amaterasu.model.Component;
import br.com.amaterasu.model.ModelCrud;
import br.com.amaterasu.model.ModelProjeto;
import br.com.amaterasu.util.AmaterasuClassLoader;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.model.Field;
import br.com.amaterasu.model.Model;
import br.com.amaterasu.util.Format;
import br.com.amaterasu.util.IAmaterasuModel;
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
            //instanciando e passando o objeto ModelCrud para o modelo;
            String classe = "br.com.amaterasu.model.ModelCrud";
            ((ModelCrud) new AmaterasuClassLoader(jar, classe).getInstance()).set(ModelCrud.i());
            //instanciando e passando o objeto ModelProjeto para o modelo;
            classe = "br.com.amaterasu.model.ModelProjeto";
            ((ModelProjeto) new AmaterasuClassLoader(jar, classe).getInstance()).set(ModelProjeto.i());
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
            //instanciando e passando o objeto ModelCrud para o modelo;
            String classe = "br.com.amaterasu.model.ModelCrud";
            ((ModelCrud) new AmaterasuClassLoader(jar, classe).getInstance()).set(ModelCrud.i());
            //instanciando e passando o objeto ModelProjeto para o modelo;
            classe = "br.com.amaterasu.model.ModelProjeto";
            ((ModelProjeto) new AmaterasuClassLoader(jar, classe).getInstance()).set(ModelProjeto.i());
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
     * Este método le a classe bean e separa os atributos da classe, preenchendo
     * a lista de field de ModelCrud;
     *
     * @throws AmaterasuException
     */
    public static void readBean() throws AmaterasuException {
        try {
            File file = new File(ModelCrud.i().getCaminhoClasseBean());
            if (!file.exists()) {
                throw new AmaterasuException("O caminho informado está incorreto.\n" + file.getAbsolutePath(), false);
            }
            
            //Neste bloco de codigo abaixo, estou verificando se o modelo ainda esta no historico e se todos dos campos se mantem
            List<Field> listFieldsDoBean = getFieldsBean(file);
            List<Field> listFieldsDoHistorico = ModelCrud.i().getListFields();
            List<Field> listResultado = new ArrayList<Field>();

            for (Field fieldBean : listFieldsDoBean) {
                boolean novoField = true;
                for (Field fieldHistorico : listFieldsDoHistorico) {
                    if (fieldBean.getNome().equals(fieldHistorico.getNome())) {
                        fieldHistorico.setTipo(fieldBean.getTipo());
                        listResultado.add(fieldHistorico);
                        novoField = false;
                        break;
                    }
                }
                if (novoField) {
                    listResultado.add(fieldBean);
                }
            }

            ModelCrud.i().setListFields(listResultado);
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
                    f.setComponente(Component.CHECKBOX);
                    for (String s : listLinhasArquivoBean) {
                        if (s.contains("is" + Format.maiuscula1(f.getNome()))) {
                            f.setMetodoIS(true);
                            break;
                        }
                    }
                } else if (f.getTipo().equals("Date")) {
                    f.setComponente(Component.DATA);
                } else if (f.getTipo().equalsIgnoreCase("Double")
                        || f.getTipo().equalsIgnoreCase("Float")
                        || f.getTipo().equalsIgnoreCase("String")
                        || f.getTipo().equalsIgnoreCase("Integer")
                        || f.getTipo().equalsIgnoreCase("int")
                        || f.getTipo().equalsIgnoreCase("Decimal")
                        || f.getTipo().equalsIgnoreCase("Byte")
                        || f.getTipo().equalsIgnoreCase("Long")
                        || f.getTipo().equalsIgnoreCase("Short")
                        || f.getTipo().equalsIgnoreCase("char")
                        || f.getTipo().equalsIgnoreCase("Character")) {
                    f.setComponente(Component.INPUT);
                } else if (f.getTipo().contains("<")) {
                    for (String t : file.getParentFile().list()) {
                        String tipo = f.getTipo();
                        tipo = tipo.substring(tipo.indexOf("<") + 1, tipo.indexOf(">"));
                        f.setComponente(Component.TABLE);
                        if (t.replace(".java", "").equalsIgnoreCase(tipo)) {
                            f.setBean(true);
                            break;
                        }
                    }
                } else {
                    for (String t : file.getParentFile().list()) {
                        String tipo = f.getTipo();
                        if (t.replace(".java", "").equalsIgnoreCase(tipo)) {
                            f.setComponente(Component.COMBOBOX);
                            f.setBean(true);
                            break;
                        }
                    }
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
