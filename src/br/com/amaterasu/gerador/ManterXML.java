/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.gerador;

import br.com.amaterasu.util.AmaterasuException;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Maykon
 */
public class ManterXML {

    public static void writeXML(File file, Object classe) throws AmaterasuException {
        try {
            if (!file.exists() && file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
            encoder.writeObject(classe);
            encoder.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro ao criar xml. (" + file.getName() + ")");
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao criar xml.(" + file.getName() + ")", true);
        }
    }

    public static void writeXML(Document doc, File file) throws AmaterasuException {
        FileWriter out = null;
        try {
            if (!file.exists() && file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            out = new FileWriter(file);
            XMLWriter writer = new XMLWriter(out, OutputFormat.createPrettyPrint());
            writer.write(doc);
            writer.close();
        } catch (IOException ex) {
            System.out.println("Erro ao criar xml. (" + file.getName() + ")");
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao criar xml.(" + file.getName() + ")", true);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                System.out.println("Erro ao criar xml. (" + file.getName() + ")");
                ManterLog.write(ex);
                throw new AmaterasuException("Erro ao criar xml.(" + file.getName() + ")", true);
            }
        }
    }

    public static Object readXML(File file) throws AmaterasuException {
        Object classe = null;
        try {
            if (file.exists()) {
                XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
                classe = decoder.readObject();
                decoder.close();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Erro ao ler xml(" + file.getName() + ")");
            ManterLog.write(ex);
            throw new AmaterasuException("Erro ao ler xml. (" + file.getName() + ")", true);
        }
        return classe;
    }
}
