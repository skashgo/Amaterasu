/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.criarcrud;

import br.com.amaterasu.model.CriarCRUDBean;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.Field;
import br.com.amaterasu.util.IPainel;
import br.com.amaterasu.util.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Maykon
 */
public class CriarCrud_painel3 extends javax.swing.JPanel implements IPainel {

    private Integer VAR = 0;
    private Integer COMP = 1;
    private Integer MAX = 2;
    private Integer OBRIG = 3;
    private Integer REND = 4;
    private Integer DISAB = 5;
    private Integer FORMATTER = 6;
    private Integer MASC = 7;
    private Integer FOCUS = 8;
    private Integer ORDER = 9;

    public CriarCrud_painel3() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jCkRendered = new javax.swing.JCheckBox();
        jCkDisabled = new javax.swing.JCheckBox();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Selecionar todos");

        jCkRendered.setText("Show");
        jCkRendered.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCkRenderedActionPerformed(evt);
            }
        });

        jCkDisabled.setText("Disabled");
        jCkDisabled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCkDisabledActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCkRendered)
                        .addGap(18, 18, 18)
                        .addComponent(jCkDisabled))
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCkRendered)
                    .addComponent(jCkDisabled))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(561, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(337, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(66, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCkRenderedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCkRenderedActionPerformed
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            jTable1.setValueAt(jCkRendered.isSelected(), i, REND);
        }
    }//GEN-LAST:event_jCkRenderedActionPerformed

    private void jCkDisabledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCkDisabledActionPerformed
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            jTable1.setValueAt(jCkDisabled.isSelected(), i, DISAB);
        }
    }//GEN-LAST:event_jCkDisabledActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCkDisabled;
    private javax.swing.JCheckBox jCkRendered;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void binding() throws AmaterasuException {
        List<Field> listFieldTemp = new ArrayList<Field>();

        for (Field f : CriarCRUDBean.i().getListFields()) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                if (jTable1.getValueAt(i, VAR).equals(f.getNome()) && jTable1.getValueAt(i, COMP).equals(f.getComponente())) {
                    f.setTamanho((Integer) jTable1.getValueAt(i, MAX));
                    f.setObrigatorio((Boolean) jTable1.getValueAt(i, OBRIG));
                    f.setShow((Boolean) jTable1.getValueAt(i, REND));
                    f.setDisabled((Boolean) jTable1.getValueAt(i, DISAB));
                    f.setFormatter((Boolean) jTable1.getValueAt(i, FORMATTER));
                    f.setMascara((String) jTable1.getValueAt(i, MASC));
                    f.setFocus((Boolean) jTable1.getValueAt(i, FOCUS));
                    f.setOrdem((Integer) jTable1.getValueAt(i, ORDER));
                    listFieldTemp.add(f);
                    break;
                }
            }
        }
        Collections.sort(listFieldTemp);
        CriarCRUDBean.i().setListFields(listFieldTemp);
    }

    private void updateTables() {
        Table table = new Table();
        table.addColumn("Variável", String.class, false);
        table.addColumn("Componente", String.class, false);
        table.addColumn("Maxlength", Integer.class, true);
        table.addColumn("Obrigatórios", Boolean.class, true);
        table.addColumn("Rendered", Boolean.class, true);
        table.addColumn("Disabled", Boolean.class, true);
        table.addColumn("Formatter", Boolean.class, true);
        table.addColumn("Mascara", String.class, true);
        table.addColumn("Focus", Boolean.class, true);
        table.addColumn("Ordenar", Integer.class, true);
        for (Field f : CriarCRUDBean.i().getListFields()) {
            table.addLineValue(f.getNome(), f.getComponente(), f.getTamanho(), f.isObrigatorio(), f.isShow(), f.isDisabled(), f.isFormatter(), f.getMascara(), f.isFocus(), f.getOrdem());
        }
        table.createTable(jTable1);
    }

    @Override
    public void atualizaPainel() {
        updateTables();
    }
}