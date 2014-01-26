/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.text;

/**
 *
 * @author Maykon
 */
public class Txt {

    public static String getTxt(String nomeArquivo, String var) {
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/" + nomeArquivo); // NOI18N
        return bundle.getString(var);
    }
}
