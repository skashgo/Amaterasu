/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.util;

import java.util.Observer;

/**
 *
 * @author Maykon
 */
public interface IPainelRodape extends Observer {

    void doVoltar();

    void doProximo();

    void doFinalizar();

    void doCancelar();

    void doAjuda();
}
