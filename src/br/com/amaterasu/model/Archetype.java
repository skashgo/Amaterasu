/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.model;

/**
 *
 * @author MAYKON
 */
public class Archetype {

    private String archetypeArtifactId;
    private String archetypeGroupId;
    private String archetypeVersion;
    private String archetypeDescription;
    private boolean projetoExistente;

    public String getArchetypeArtifactId() {
        return archetypeArtifactId;
    }

    public void setArchetypeArtifactId(String archetypeArtifactId) {
        this.archetypeArtifactId = archetypeArtifactId;
    }

    public String getArchetypeGroupId() {
        return archetypeGroupId;
    }

    public void setArchetypeGroupId(String archetypeGroupId) {
        this.archetypeGroupId = archetypeGroupId;
    }

    public String getArchetypeVersion() {
        return archetypeVersion;
    }

    public void setArchetypeVersion(String archetypeVersion) {
        this.archetypeVersion = archetypeVersion;
    }

    public String getArchetypeDescription() {
        return archetypeDescription;
    }

    public void setArchetypeDescription(String archetypeDescription) {
        this.archetypeDescription = archetypeDescription;
    }

    public boolean isProjetoExistente() {
        return projetoExistente;
    }

    public void setProjetoExistente(boolean projetoExistente) {
        this.projetoExistente = projetoExistente;
    }
}
