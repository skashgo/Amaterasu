#PATH[src/main/java/br/com/amaterasu/main/ClasseBO]
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
public class ClasseBO {

    String projeto = CriarProjetoBean.i().getCaminho() + IConstants.barra + "src" + IConstants.barra + CriarProjetoBean.i().getPacotePadrao().replace(".", IConstants.barra) + IConstants.barra;
    Model model = new Model();
    String pathBO = projeto + "bo" + IConstants.barra + "impl" + IConstants.barra + CriarCRUDBean.i().getNomeCasoUso() + "BO.java";

    public Model contentFile() {
        WriteClass bo = new WriteClass();
        //---------escrever classe aqui-----------//

        bo.setCopyright(CriarProjetoBean.i().getCopyright());

        //---------------------------------------//
        model.setConteudo(bo.getSBClass().toString());
        model.setCaminho(pathBO);
        return model;
    }
}
