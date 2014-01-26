/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.util;

import java.util.Observable;

/**
 * Listener utilizado para interligar eventos de diferentes interfaces
 *
 * @author Maykon
 */
public class ObserverAction extends Observable {

    /**
     * Recebe uma String informando qual evento esta disparando a acao;
     * @param action
     */
    public void setAction(String action) {
        setChanged();
        notifyObservers(action);
    }
}
