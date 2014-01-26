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
    String getInfo();
}
