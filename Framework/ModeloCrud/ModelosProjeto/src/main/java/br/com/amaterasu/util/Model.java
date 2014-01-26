#PATH[src/main/java/br/com/amaterasu/util/Model]
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.util;

/**
 *
 * @author Maykon
 */
public class Model {

    private String caminho;
    private String conteudo;

    public Model() {
    }

    public Model(String caminho, String conteudo) {
        this.caminho = caminho;
        this.conteudo = conteudo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}