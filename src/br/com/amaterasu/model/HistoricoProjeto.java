/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.model;

import br.com.amaterasu.util.IConstants;

/**
 * Informações de historico de projetos abertos
 *
 * @author Maykon
 */
public class HistoricoProjeto {

    private String projeto;
    private String caminhoProjeto;
    private String caminhoProjetoAmaterasu;

    public HistoricoProjeto(String projeto, String pathProjeto, String pathProjetoAmaterasu) {
        this.projeto = projeto;
        this.caminhoProjeto = pathProjeto;
        this.caminhoProjetoAmaterasu = pathProjetoAmaterasu;
    }

    public HistoricoProjeto() {
    }

    public String getProjeto() {
        return projeto;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }

    public String getCaminhoProjeto() {
        if (caminhoProjeto != null) {
            return caminhoProjeto.replace("/", IConstants.barra).replace("\\", IConstants.barra);
        } else {
            return caminhoProjeto;
        }
    }

    public void setCaminhoProjeto(String pathProjeto) {
        this.caminhoProjeto = pathProjeto;
    }

    public String getCaminhoProjetoAmaterasu() {
        if (caminhoProjetoAmaterasu != null) {
            return caminhoProjetoAmaterasu.replace("/", IConstants.barra).replace("\\", IConstants.barra);
        } else {
            return caminhoProjetoAmaterasu;
        }
    }

    public void setCaminhoProjetoAmaterasu(String pathProjetoAmaterasu) {
        this.caminhoProjetoAmaterasu = pathProjetoAmaterasu;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.projeto != null ? this.projeto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HistoricoProjeto other = (HistoricoProjeto) obj;
        if ((this.projeto == null) ? (other.projeto != null) : !this.projeto.equals(other.projeto)) {
            return false;
        }
        return true;
    }
}
