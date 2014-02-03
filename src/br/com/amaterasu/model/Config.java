/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.model;

import br.com.amaterasu.gerador.ManterXML;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IConstants;
import java.io.File;

/**
 *
 * @author MAYKON
 */
public class Config {

    private static Config i;
    private String caminhoRepoMaven;
    private String caminhoMaven;
    private String caminhoCatalogMaven;
    private SO so;

    public static Config i() {
        if (i == null) {
            i = new Config();
        }
        return i;
    }

    public static void set(Config i) {
        Config.i = i;
    }

    public static void fechar() {
        i = null;
    }

    public void save() throws AmaterasuException {
        File file = new File("config.xml");
        ManterXML.writeXML(file, i());
    }

    public static void read() throws AmaterasuException {
        File file = new File("config.xml");
        if (!file.exists()) {
            throw new AmaterasuException("Arquivo config.xml não encontrado no caminho: " + file.getAbsolutePath(), false);
        }
        set((Config) ManterXML.readXML(file));
    }

    public String getCaminhoRepoMaven() {
        return caminhoRepoMaven;
    }

    public void setCaminhoRepoMaven(String caminhoRepoMaven) {
        this.caminhoRepoMaven = caminhoRepoMaven;
    }

    public String getCaminhoMaven() {
        return caminhoMaven;
    }

    public void setCaminhoMaven(String caminhoMaven) {
        this.caminhoMaven = caminhoMaven;
    }

    public String getCaminhoCatalogMaven() {
        return caminhoCatalogMaven;
    }

    public void setCaminhoCatalogMaven(String caminhoCatalogMaven) {
        this.caminhoCatalogMaven = caminhoCatalogMaven;
    }

    public SO getSo() {
        return so;
    }

    public void setSo(SO so) {
        this.so = so;
    }

    public enum SO {

        WIN, LINUX;
    }
}
