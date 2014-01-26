/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.model;

import br.com.amaterasu.gerador.ManterXML;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.HistoricoProjeto;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilizada para escrever arquivo xml com os dados dos ultimos projetos
 * abertos. A partir deste historico, o usuario não vai precisar informar todas
 * as vezes que for abrir um projeto, o endereço do projeto e projeto amaterasu
 *
 * @author Maykon
 */
public class HistoricoProjetoBean {

    private static HistoricoProjetoBean i = null;
    private List<HistoricoProjeto> list = new ArrayList<HistoricoProjeto>();

    public static HistoricoProjetoBean i() {
        if (i == null) {
            i = new HistoricoProjetoBean();
        }
        return i;
    }

    public void save() throws AmaterasuException {
        File file = new File("historico.xml");
        ManterXML.writeXML(file, i());
        i = null;
    }

    public void get() throws AmaterasuException {
        File file = new File("historico.xml");
        i = (HistoricoProjetoBean) ManterXML.readXML(file);
    }

    public List<HistoricoProjeto> getList() {
        return list;
    }

    public void setList(List<HistoricoProjeto> list) {
        this.list = list;
    }
}
