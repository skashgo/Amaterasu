#PATH[src/main/java/br/com/amaterasu/main/AmaterasuModel]
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.main;

import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IAmaterasuModel;
import br.com.amaterasu.util.Model;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maykon
 */
public class AmaterasuModel implements IAmaterasuModel {

    @Override
    public List<Model> getListContentFile() throws AmaterasuException {
        List<Model> list = new ArrayList<Model>();
        list.add(new ClasseMB().contentFile());
        list.add(new ClasseBO().contentFile());
        return list;
    }
}
