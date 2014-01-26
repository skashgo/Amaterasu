#PATH[src/main/java/br/com/amaterasu/util/WriteClass]
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.util;

/**
 * Esta classe auxilia na criação de classes java
 *
 * @author Maykon
 */
public class WriteClass {

    private StringBuilder sb;
    private StringBuilder sbCopyright;
    private StringBuilder sbPackage;
    private StringBuilder sbImport;
    private StringBuilder sbanotationClass;
    private StringBuilder sbClass;
    private StringBuilder sbGettterSetter;

    public StringBuilder getSBClass() {
        StringBuilder sbRetorno = new StringBuilder();
        sbRetorno.append(sbCopyright).append("\n");
        sbRetorno.append(sbPackage).append("\n");
        sbRetorno.append(sbImport).append("\n");
        sbRetorno.append(sbanotationClass);
        sbRetorno.append(sbClass);
        sbRetorno.append(sb);

        if (sbGettterSetter == null || sbGettterSetter.length() < 24) {
            return sbRetorno.append("\n}");
        }
        return sbRetorno.append(sbGettterSetter).append("\n}");
    }

    public WriteClass() {
        sb = new StringBuilder();
        sbCopyright = new StringBuilder();
        sbPackage = new StringBuilder();
        sbImport = new StringBuilder();
        sbanotationClass = new StringBuilder();
        sbClass = new StringBuilder();
        sbGettterSetter = new StringBuilder();
    }

    public void setNewLine() {
        sb.append("\n");
    }

    public void setComment(String s) {
        sb.append("\n\t//").append(s);
    }

    public void setPackage(String s) {
        sbPackage.append("\npackage ").append(s);
    }

    public void setImport(String s) {
        sbImport.append("\nimport ").append(s);
    }

    public void setClass(String s) {
        sbClass.append("\n").append(s);
    }

    /**
     * este metodo declara um atributo, pode instancia-lo e criar getter/setter
     *
     * @param type (ex. String)
     * @param name (ex. descricao)
     * @param inicio (ex. \"minha descricao\" ou new Usuario()) obs. quando for
     * String inserir tbm as aspas \"texto\"
     * @param getterSetter (true = será criado um metodo getter e setter para o
     * atributo)
     */
    public void setAttribute(String type, String name, String inicio, Boolean getter, Boolean Setter) {
        sb.append("\n\tprivate ").append(type).append(" ").append(name);
        if (inicio == null || inicio.isEmpty()) {
            sb.append(";");
        } else {
            sb.append(" = ").append(inicio).append(";");
        }
        if (getter) {
            sbGettterSetter.append("\n\tpublic ").append(type).append(" get").append(Format.maiuscula1(name)).append("() {");
            sbGettterSetter.append("\n\t\treturn this.").append(name).append(";");
            sbGettterSetter.append("\n\t}");
            sbGettterSetter.append("\n");
        }
        if (Setter) {
            sbGettterSetter.append("\n\tpublic void set").append(Format.maiuscula1(name)).append("(").append(type).append(" ").append(name).append(") {");
            sbGettterSetter.append("\n\t\tthis.").append(name).append(" = ").append(name).append(";");
            sbGettterSetter.append("\n\t}");
            sbGettterSetter.append("\n");
        }
    }

    /**
     * Declara um atributo injetavel. Será criado a declaração do atributo, e
     * seu metodo set com annotation
     *
     * @Autowired e caso o parametro modoInicio seja true será setado a chamada
     * do metodo setModoInicio();
     * @param type
     * @param name
     * @param modoInicio
     */
    public void setAttributeInjection(String type, String name, boolean modoInicio) {
        sb.append("\n\tprivate ").append(type).append(" ").append(name).append(";");

        sbGettterSetter.append("\n\t@Autowired");
        sbGettterSetter.append("\n\tpublic void set").append(Format.maiuscula1(name)).append("(").append(type).append(" ").append(name).append(") {");
        sbGettterSetter.append("\n\t\tthis.").append(name).append(" = ").append(name).append(";");
        if (modoInicio) {
            sbGettterSetter.append("\n\t\tmodoInicio();");
        }
        sbGettterSetter.append("\n\t}");
        sbGettterSetter.append("\n");
    }

    public void setMethod(String s) {
        sb.append("\n\t").append(s);
    }

    public void setAnotationClass(String s) {
        sbanotationClass.append("\n").append(s);
    }

    public void setCopyright(String copyright) {
        sbCopyright.append(copyright);
    }
}
