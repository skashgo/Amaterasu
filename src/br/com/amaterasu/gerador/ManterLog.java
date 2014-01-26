/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.gerador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Maykon
 */
public class ManterLog {

    public static void write(Exception ex) {
        try {
            File file = new File("log.txt");
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw, true);
            pw.write(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss ").format(new Date()));
            ex.printStackTrace(pw);
            pw.close();
            fw.close();
        } catch (IOException ex1) {
            JOptionPane.showMessageDialog(null, "Erro em gerar log.\n" + ex1.getMessage());
        }
    }
}
