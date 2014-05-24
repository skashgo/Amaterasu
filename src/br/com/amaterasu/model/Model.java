/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.model;

import org.dom4j.Document;

/**
 *
 * @author Maykon
 */
public class Model {

    private String caminho;
    private String conteudo;
    private Document document;

    public Model() {
    }

    public Model(String caminho, String conteudo) {
        this.caminho = caminho;
        this.conteudo = conteudo;
    }

    public Model(String caminho, Document document) {
        this.caminho = caminho;
        this.document = document;
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}