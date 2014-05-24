/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.model;

import br.com.amaterasu.gerador.ManterXML;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maykon
 */
public class ModelArchetype {

    private static ModelArchetype i = null;
    private List<Archetype> list = new ArrayList<Archetype>();

    public static ModelArchetype i() {
        if (i == null) {
            i = new ModelArchetype();
        }
        return i;
    }

    public void save() throws AmaterasuException {
        File file = new File("archetype.xml");
        ManterXML.writeXML(file, i());
        i = null;
        for (Archetype a : list) {
            File dirArch = new File(IConstants.DIR_CRUD + IConstants.barra + a.getArchetypeArtifactId());
            if (!dirArch.exists()) {
                dirArch.mkdirs();
            }
        }
    }

    public void get() throws AmaterasuException {
        File file = new File("archetype.xml");
        i = (ModelArchetype) ManterXML.readXML(file);
        if (i != null && i.getList() != null && !i.getList().isEmpty()) {
            for (Archetype a : i.getList()) {
                if (a.isProjetoExistente()) {
                    return;
                }
            }
        }
        Archetype arch = new Archetype();
        arch.setArchetypeArtifactId("Projeto Existente");
        arch.setArchetypeDescription("Archetype utilizado para projeto já existente. Não será gerado nenhum artefato. Seu propósito é servir para configurar um projeto já existe no sistema Amateraso permitindo assim a utilização dos recursos de geração de crud e outros.");
        arch.setProjetoExistente(true);
        i().getList().add(arch);
        i().save();
        File dirArch = new File(IConstants.DIR_CRUD + IConstants.barra + arch.getArchetypeArtifactId());
        if (!dirArch.exists()) {
            dirArch.mkdirs();
        }
    }

    public List<Archetype> getList() {
        return list;
    }

    public void setList(List<Archetype> list) {
        this.list = list;
    }

}
