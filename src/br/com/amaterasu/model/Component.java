/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MAYKON
 */
public class Component {

    public static final String INPUT = "Input";
    public static final String OUTPUT = "Output";
    public static final String CHECKBOX = "CheckBox";
    public static final String RADIOBOX = "RadioBox";
    public static final String COMBOBOX = "ComboBox";
    public static final String TEXTAREA = "TextArea";
    public static final String IMAGE = "Image";
    public static final String DATA = "Data";
    public static final String TABLE = "Table";
    public static final String EXPANDIR_OBJETO = "Expandir Objeto";

    public static List<String> getComponent() {
        List<String> list = new ArrayList<String>();
        list.add(INPUT);
        list.add(OUTPUT);
        list.add(CHECKBOX);
        list.add(RADIOBOX);
        list.add(COMBOBOX);
        list.add(TEXTAREA);
        list.add(IMAGE);
        list.add(DATA);
        list.add(TABLE);
        list.add(EXPANDIR_OBJETO);

        return list;
    }
}