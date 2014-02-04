/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.model;

import br.com.amaterasu.gerador.GerarProjeto;
import br.com.amaterasu.gerador.ManterXML;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IConstants;
import java.io.File;

/**
 *
 * @author Maykon
 */
public class ModelProjeto {

    private static ModelProjeto i = null;
    public String nomeProjeto;
    public String nomeCompleto;
    public String cliente;
    public String pacotePadrao;
    public String caminho;
    public String caminhoAmaterasu;
    public String servidor;
    public String caminhoServidor;
    private Archetype archetype;
    public String copyright;
    private String dataCriacao;
    private String dataUltimaAlteracao;
    private boolean jasperReport;
    private boolean pentaho;
    private boolean apachePOI;

    public static ModelProjeto i() {
        if (i == null) {
            i = new ModelProjeto();
        }
        return i;
    }

    public void set(ModelProjeto i) {
        this.i = i;
    }

    public static void fechar() {
        i = null;
    }

    public void save() throws AmaterasuException {
        File file = new File(i().getCaminhoAmaterasu() + IConstants.barra + "Geral" + IConstants.barra + "projeto.xml");
        ManterXML.writeXML(file, i());
        GerarProjeto.gerar();
        i = null;
    }

    public void get() throws AmaterasuException {
        File file = new File(i().getCaminhoAmaterasu() + IConstants.barra + "Geral" + IConstants.barra + "projeto.xml");
        if (!file.exists()) {
            throw new AmaterasuException("O Caminho do Projeto Amaterasu informado está incorreto, ou falta arquivos importantes para abrir-lo.", false);
        }
        if (!new File(i().getCaminho()).exists()) {
            throw new AmaterasuException("O Caminho do Projeto informado está incorreto.", false);
        }
        String caminhoAmat = i().getCaminhoAmaterasu();
        String caminhoProj = i().getCaminho();
        i = (ModelProjeto) ManterXML.readXML(file);
        if (i == null) {
            throw new AmaterasuException("O arquivo projeto.xml do Projeto Amaterasu está incompleto e o projeto não poderá ser aberto.", false);
        }
        i().setCaminho(caminhoProj);
        i().setCaminhoAmaterasu(caminhoAmat);
        HistoricoProjeto histTemp = null;
        for (HistoricoProjeto hp : HistoricoProjetoBean.i().getList()) {
            if (hp.getProjeto().equals(i().getNomeProjeto())) {
                histTemp = hp;
            }
        }
        if (histTemp != null) {
            HistoricoProjetoBean.i().getList().remove(histTemp);
        }
        HistoricoProjetoBean.i().getList().add(new HistoricoProjeto(i().getNomeProjeto(), i().getCaminho(), i().getCaminhoAmaterasu()));
        HistoricoProjetoBean.i().save();
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

    public Archetype getArchetype() {
        return archetype;
    }

    public void setArchetype(Archetype archetype) {
        this.archetype = archetype;
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

}
