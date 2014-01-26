/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.util;

/**
 *
 * @author Maykon
 */
public class AmaterasuException extends Exception {

    public AmaterasuException() {
    }

    /**
     * 
     * @param message
     * @param log se true aparecerá a mensagem "Verificar log para mais detalhes."
     */
    public AmaterasuException(String message, boolean log) {
        super(message + (log ? "\nVerificar log para mais detalhes." : ""));

    }

    public AmaterasuException(Throwable cause) {
        super(cause);
    }

    public AmaterasuException(String message, Throwable cause) {
        super(message, cause);
    }
}
