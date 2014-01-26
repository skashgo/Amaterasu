/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.model;

import br.com.amaterasu.gerador.GerarCrud;
import br.com.amaterasu.gerador.ManterXML;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.Field;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.PathFramework;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maykon
 */
public class CriarCRUDBean {

    private static CriarCRUDBean i = null;
    private String nomeModelo;
    public String caminhoClasseBean;
    public String nomeBean;
    public String nomeCasoUso;
    public boolean modoCadastro;
    public boolean modoAlteracao;
    public boolean modoExclusaoFisica;
    public boolean modoVisualizacao;
    public boolean modoFiltro;
    public boolean modoExclusaoLogica;
    public boolean modoGrid;
    public boolean layoutGrid;
    public Field campoExclusaoLogica;
    public List<Field> listFields = new ArrayList<Field>();

    public static CriarCRUDBean i() {
        if (i == null) {
            i = new CriarCRUDBean();
        }
        return i;
    }

    public void set(CriarCRUDBean i) {
        this.i = i;
    }

    public void save() throws AmaterasuException {
        File file = new File(CriarProjetoBean.i().getCaminhoAmaterasu() + IConstants.barra + "CRUDs" + IConstants.barra + i().getNomeCasoUso() + ".xml");
        ManterXML.writeXML(file, i());
        GerarCrud.gerar(PathFramework.pathFrameworkModelosCRUD(CriarProjetoBean.i().getModelo()) + getNomeModelo());
        i = null;
    }
    
    public String info() throws AmaterasuException{
        return GerarCrud.getInfo(PathFramework.pathFrameworkModelosCRUD(CriarProjetoBean.i().getModelo()) + getNomeModelo());
    }

    public void get() throws AmaterasuException {
        File file = new File("");
        i = (CriarCRUDBean) ManterXML.readXML(file);
    }

    public void readBean() throws AmaterasuException {
        GerarCrud.readBean();
    }

    public String getNomeModelo() {
        return nomeModelo;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }

    public String getCaminhoClasseBean() {
        if (caminhoClasseBean != null) {
            return caminhoClasseBean.replace("/", IConstants.barra).replace("\\", IConstants.barra);
        } else {
            return caminhoClasseBean;
        }
    }

    public void setCaminhoClasseBean(String caminhoClasseBean) {
        this.caminhoClasseBean = caminhoClasseBean;
    }

    public String getNomeCasoUso() {
        if (nomeCasoUso != null) {
            return nomeCasoUso.trim().replace(" ", "").replace("-", "");
        }else{
            return nomeCasoUso;
        }
    }

    public void setNomeCasoUso(String nomeCasoUso) {
        this.nomeCasoUso = nomeCasoUso;
    }

    public boolean isModoCadastro() {
        return modoCadastro;
    }

    public void setModoCadastro(boolean modoCadastro) {
        this.modoCadastro = modoCadastro;
    }

    public boolean isModoAlteracao() {
        return modoAlteracao;
    }

    public void setModoAlteracao(boolean modoAlteracao) {
        this.modoAlteracao = modoAlteracao;
    }

    public boolean isModoExclusaoFisica() {
        return modoExclusaoFisica;
    }

    public void setModoExclusaoFisica(boolean modoExclusaoFisica) {
        this.modoExclusaoFisica = modoExclusaoFisica;
    }

    public boolean isModoVisualizacao() {
        return modoVisualizacao;
    }

    public void setModoVisualizacao(boolean modoVisualizacao) {
        this.modoVisualizacao = modoVisualizacao;
    }

    public boolean isModoFiltro() {
        return modoFiltro;
    }

    public void setModoFiltro(boolean modoFiltro) {
        this.modoFiltro = modoFiltro;
    }

    public boolean isModoExclusaoLogica() {
        return modoExclusaoLogica;
    }

    public void setModoExclusaoLogica(boolean modoExclusaoLogica) {
        this.modoExclusaoLogica = modoExclusaoLogica;
    }

    public boolean isModoGrid() {
        return modoGrid;
    }

    public void setModoGrid(boolean modoGrid) {
        this.modoGrid = modoGrid;
    }

    public Field getCampoExclusaoLogica() {
        return campoExclusaoLogica;
    }

    public void setCampoExclusaoLogica(Field campoExclusaoLogica) {
        this.campoExclusaoLogica = campoExclusaoLogica;
    }

    public List<Field> getListFields() {
        return listFields;
    }

    public void setListFields(List<Field> listFields) {
        this.listFields = listFields;
    }

    public boolean isLayoutGrid() {
        return layoutGrid;
    }

    public void setLayoutGrid(boolean layoutGrid) {
        this.layoutGrid = layoutGrid;
    }

    public String getNomeBean() {
        return nomeBean;
    }

    public void setNomeBean(String nomeBean) {
        this.nomeBean = nomeBean;
    }
    
}
