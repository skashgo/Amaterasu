/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.model;

import br.com.amaterasu.gerador.GerarModelo;
import br.com.amaterasu.gerador.GerarProjeto;
import br.com.amaterasu.gerador.ManterXML;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.NodeJtree;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maykon
 */
public class CriarModeloBean {

    private static CriarModeloBean i = null;
    private String nomeModelo;
    private List<NodeJtree> arvore = new ArrayList<NodeJtree>();
    private List<String> tecnoligas = new ArrayList<String>();
    private String caminho;

    public static CriarModeloBean i() {
        if (i == null) {
            i = new CriarModeloBean();
        }
        return i;
    }

    public void save() throws AmaterasuException {
        GerarModelo.gerar();
        i = null;
    }

    public void saveCrud() throws AmaterasuException {
        CriarProjetoBean.i().setModelo(IConstants.DIR_MODELO_CRUD);
        CriarProjetoBean.i().setCaminho(caminho + IConstants.barra + nomeModelo + IConstants.barra);
        CriarProjetoBean.i().setNomeProjeto(nomeModelo);
        CriarProjetoBean.i().setPacotePadrao("");
        GerarProjeto.gerar();
        i = null;
    }

    public void get() throws AmaterasuException {
        File file = new File("");
        i = (CriarModeloBean) ManterXML.readXML(file);
    }

    public String getNomeModelo() {
        return nomeModelo;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }

    public List<NodeJtree> getArvore() {
        return arvore;
    }

    public void setArvore(List<NodeJtree> arvore) {
        this.arvore = arvore;
    }

    public List<String> getTecnoligas() {
        return tecnoligas;
    }

    public void setTecnoligas(List<String> tecnoligas) {
        this.tecnoligas = tecnoligas;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
