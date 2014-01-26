#PATH[src/main/java/br/com/amaterasu/util/Field]
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.util;

/**
 *
 * @author Maykon
 */
public class Field implements Comparable<Field> {

    public String nome;        //nome do atributo
    public String tipo;
    public boolean metodoIS;    //caso o campo seja do tipo booleano verificar se esta utilizando o padrão get ou is
    public boolean showCadastro;
    public boolean showAlteracao;
    public boolean showGrid;
    public boolean showFiltro;
    public boolean showVisualizacao;
    public String componente;
    public String label;
    public boolean local;      //quando a variavel não representa um atributo do bean.
    public Integer tamanho;        //maxlength
    public boolean obrigatorio;
    public boolean show;       //indica que precisa de metodo de controle da propriedade rendered
    public boolean disabled;   //indica que precisa de metodo de controle da propriedade disabled
    public boolean formatter;  //indica que precisa de um metodo para fazer a formatação.
    public String mascara;     //define a mascara do campo
    public boolean focus;      //informa que este campo deverá ter focus;
    public Integer ordem;          //indica a ordem de apresentação dos campos
    public boolean bean;       //indica que o tipo deste campo representa outro bean
    public String idCombo;
    public String descCombo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isMetodoIS() {
        return metodoIS;
    }

    public void setMetodoIS(boolean metodoIS) {
        this.metodoIS = metodoIS;
    }

    public boolean isShowCadastro() {
        return showCadastro;
    }

    public void setShowCadastro(boolean showCadastro) {
        this.showCadastro = showCadastro;
    }

    public boolean isShowAlteracao() {
        return showAlteracao;
    }

    public void setShowAlteracao(boolean showAlteracao) {
        this.showAlteracao = showAlteracao;
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    public boolean isShowFiltro() {
        return showFiltro;
    }

    public void setShowFiltro(boolean showFiltro) {
        this.showFiltro = showFiltro;
    }

    public boolean isShowVisualizacao() {
        return showVisualizacao;
    }

    public void setShowVisualizacao(boolean showVisualizacao) {
        this.showVisualizacao = showVisualizacao;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public boolean isObrigatorio() {
        return obrigatorio;
    }

    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isFormatter() {
        return formatter;
    }

    public void setFormatter(boolean formatter) {
        this.formatter = formatter;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public boolean isBean() {
        return bean;
    }

    public void setBean(boolean bean) {
        this.bean = bean;
    }

    public String getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(String idCombo) {
        this.idCombo = idCombo;
    }

    public String getDescCombo() {
        return descCombo;
    }

    public void setDescCombo(String descCombo) {
        this.descCombo = descCombo;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public int compareTo(Field o) {
        if (ordem == null && o.getOrdem() == null) {
            return 0;
        }
        if (ordem == null) {
            return 1;
        }
        if (o.getOrdem() == null) {
            return -1;
        }
        return this.ordem.compareTo(o.getOrdem());
    }
}
