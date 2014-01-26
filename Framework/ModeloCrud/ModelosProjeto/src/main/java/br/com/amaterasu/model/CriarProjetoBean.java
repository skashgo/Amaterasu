#PATH[src/main/java/br/com/amaterasu/model/CriarProjetoBean]
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.model;

import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IConstants;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maykon
 */
public class CriarProjetoBean {

    private static CriarProjetoBean i = null;
    public String nomeProjeto;
    public String nomeCompleto;
    public String cliente;
    public String pacotePadrao;
    public String caminho;
    public String caminhoAmaterasu;
    public String servidor;
    public String caminhoServidor;
    private String modelo;
    public String copyright;
    private String dataCriacao;
    private String dataUltimaAlteracao;
    private List<String> tecnologia = new ArrayList<String>();
    private boolean jasperReport;
    private boolean pentaho;
    private boolean apachePOI;

    public static CriarProjetoBean i() {
        if (i == null) {
            i = new CriarProjetoBean();
        }
        return i;
    }

    public void set(CriarProjetoBean i) {
        this.i = i;
    }

    public static void fechar() {
        i = null;
    }

    public void save() throws AmaterasuException {
    }

    public void get() throws AmaterasuException {
    }

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getPacotePadrao() {
        return pacotePadrao;
    }

    public void setPacotePadrao(String pacotePadrao) {
        this.pacotePadrao = pacotePadrao;
    }

    public String getCaminho() {
        if (caminho != null) {
            return caminho.replace("/", IConstants.barra).replace("\\", IConstants.barra);
        } else {
            return caminho;
        }
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getCaminhoAmaterasu() {
        if (caminhoAmaterasu != null) {
            return caminhoAmaterasu.replace("/", IConstants.barra).replace("\\", IConstants.barra);
        } else {
            return caminhoAmaterasu;
        }
    }

    public void setCaminhoAmaterasu(String caminhoAmaterasu) {
        this.caminhoAmaterasu = caminhoAmaterasu;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCaminhoServidor() {
        if (caminhoServidor != null) {
            return caminhoServidor.replace("/", IConstants.barra).replace("\\", IConstants.barra);
        } else {
            return caminhoServidor;
        }
    }

    public void setCaminhoServidor(String caminhoServidor) {
        this.caminhoServidor = caminhoServidor;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(String dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public boolean isJasperReport() {
        return jasperReport;
    }

    public void setJasperReport(boolean jasperReport) {
        this.jasperReport = jasperReport;
    }

    public boolean isPentaho() {
        return pentaho;
    }

    public void setPentaho(boolean pentaho) {
        this.pentaho = pentaho;
    }

    public boolean isApachePOI() {
        return apachePOI;
    }

    public void setApachePOI(boolean apachePOI) {
        this.apachePOI = apachePOI;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public List<String> getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(List<String> tecnologia) {
        this.tecnologia = tecnologia;
    }
}
