/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Maykon
 */
public class Table {

    private DefaultTableModel modal;
    private List<Column> listColumn = new ArrayList<Column>();
    private Object[][] objects;
    private List<Object[]> listObj = new ArrayList<Object[]>();
    private Column sortColumn;
    private boolean sortable;
    private int codColumn;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Map<Integer, JComboBox> combo = new HashMap<Integer, JComboBox>();

    public void addColumn(String title, Class type, Boolean edit) {
        listColumn.add(new Column(title, type, edit));
    }

    public void addColumn(String title, Class<JComboBox> aClass, JComboBox cb, boolean b) {
        combo.put(listColumn.size(), cb);
        listColumn.add(new Column(title, aClass, b));
    }

    public void createTable(JTable jTable) {
        List<Object[]> listObjFormated = new ArrayList<Object[]>();
        if (sortable) {
            sort();
        }


        for (Object[] o : listObj) {
            int j = 0;
            Object[] obj = new Object[listColumn.size()];
            for (Column c : listColumn) {
                if (c.getType().equals(Date.class)) {
                    Date d = (Date) o[j];
                    if (d == null) {
                        obj[j] = "";
                    } else {
                        obj[j] = sdf.format(d);
                    }

                } else {
                    obj[j] = o[j];
                }
                j++;
            }
            listObjFormated.add(obj);

        }
        String[] titles = new String[listColumn.size()];
        final Class[] types = new Class[listColumn.size()];
        final boolean[] canEdit = new boolean[listColumn.size()];
        int i = 0;

        for (Column c : listColumn) {
            titles[i] = c.getTitle();
            if (c.getType().equals(JComboBox.class)) {
                types[i] = Object.class;
            } else if (c.getType().equals(Date.class)) {
                types[i] = String.class;
            } else {
                types[i] = c.getType();
            }
            canEdit[i] = c.getEdit();
            i++;
        }
        int x = 0;
        objects = new Object[listObjFormated.size()][listColumn.size()];
        for (Object[] o : listObjFormated) {
            objects[x] = o;
            x++;
        }
        modal = new DefaultTableModel(objects, titles) {

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        jTable.setModel(modal);
        for (Integer key : combo.keySet()) {
            TableColumn tableColumn = jTable.getColumnModel().getColumn(key);
            tableColumn.setCellEditor(new DefaultCellEditor(combo.get(key)));
        }
    }

    public void addLineValue(Object... obj) {
        Object[] o = obj;
        listObj.add(o);
    }

    public void sortColumn(int i) {
        sortable = true;
        sortColumn = listColumn.get(i);
        codColumn = i;
    }

    private void sort() {
        Collections.sort(listObj, new ComparatorColumn(true, sortColumn.getType()));
    }

    class ComparatorColumn implements Comparator {

        boolean crescente = true;
        Class clazz;

        public ComparatorColumn(boolean crescente, Class clazz) {
            this.clazz = clazz;
            this.crescente = crescente;
        }

        public int compare(Object o1, Object o2) {
            Object[] d1 = (Object[]) o1;
            Object[] d2 = (Object[]) o2;
            if (d1[codColumn] == null && d2[codColumn] == null) {
                return 0;
            }
            if (d1[codColumn] == null) {
                return -1;
            }
            if (d2[codColumn] == null) {
                return 1;
            }
            if (clazz.equals(Date.class)) {
                Date data1 = (Date) d1[codColumn];
                Date data2 = (Date) d2[codColumn];
                if (crescente) {
                    return data1.compareTo(data2);
                } else {
                    return data2.compareTo(data1);
                }
            }
            if (clazz.equals(String.class)) {
                String data1 = (String) d1[codColumn];
                String data2 = (String) d2[codColumn];
                if (crescente) {
                    return data1.compareTo(data2);
                } else {
                    return data2.compareTo(data1);
                }
            }
            if (clazz.equals(Integer.class)) {
                Integer data1 = (Integer) d1[codColumn];
                Integer data2 = (Integer) d2[codColumn];
                if (crescente) {
                    return data1.compareTo(data2);
                } else {
                    return data2.compareTo(data1);
                }
            }
            if (clazz.equals(Double.class)) {
                Double data1 = (Double) d1[codColumn];
                Double data2 = (Double) d2[codColumn];
                if (crescente) {
                    return data1.compareTo(data2);
                } else {
                    return data2.compareTo(data1);
                }
            }
            if (clazz.equals(Boolean.class)) {
                Boolean data1 = (Boolean) d1[codColumn];
                Boolean data2 = (Boolean) d2[codColumn];
                if (crescente) {
                    return data1.compareTo(data2);
                } else {
                    return data2.compareTo(data1);
                }
            }
            return 0;

        }
    }

    class Column {

        private String title;
        private Class type;
        private Boolean edit;

        public Column(String title, Class type, Boolean edit) {
            this.title = title;
            this.type = type;
            this.edit = edit;
        }

        /**
         * @return the title
         */
        public String getTitle() {
            return title;
        }

        /**
         * @param title the title to set
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return the type
         */
        public Class getType() {
            return type;
        }

        /**
         * @param type the type to set
         */
        public void setType(Class type) {
            this.type = type;
        }

        /**
         * @return the edit
         */
        public Boolean getEdit() {
            return edit;
        }

        /**
         * @param edit the edit to set
         */
        public void setEdit(Boolean edit) {
            this.edit = edit;
        }
    }
}