#PATH[src/main/java/br/com/amaterasu/main/ClasseMB]
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.main;

import br.com.amaterasu.model.CriarCRUDBean;
import br.com.amaterasu.model.CriarCRUDBean;
import br.com.amaterasu.model.CriarProjetoBean;
import br.com.amaterasu.model.CriarProjetoBean;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.Model;
import br.com.amaterasu.util.WriteClass;

/**
 *
 * @author Maykon
 */
public class ClasseMB {

    String projeto = CriarProjetoBean.i().getCaminho() + IConstants.barra + "src" + IConstants.barra + CriarProjetoBean.i().getPacotePadrao().replace(".", IConstants.barra) + IConstants.barra;
    Model model = new Model();
    String pathMB = projeto + "view" + IConstants.barra + "mb" + IConstants.barra + CriarCRUDBean.i().getNomeCasoUso() + "MB.java";

    public Model contentFile() {
        WriteClass mb = new WriteClass();
        //---------escrever classe aqui-----------//

        mb.setCopyright(CriarProjetoBean.i().getCopyright());

        //---------------------------------------//
        model.setConteudo(mb.getSBClass().toString());
        model.setCaminho(pathMB);
        return model;
    }
}
