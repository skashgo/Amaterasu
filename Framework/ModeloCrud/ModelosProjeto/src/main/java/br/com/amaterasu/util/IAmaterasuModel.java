#PATH[src/main/java/br/com/amaterasu/util/IAmaterasuModel]
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.util;

import java.util.List;

/**
 *
 * @author Maykon
 */
public interface IAmaterasuModel {

    List<Model> getListContentFile() throws AmaterasuException;
}
