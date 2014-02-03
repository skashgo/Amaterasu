/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.criarprojeto;

import br.com.amaterasu.model.Archetype;
import br.com.amaterasu.model.ModelArchetype;
import br.com.amaterasu.model.ModelProjeto;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IPainel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Maykon
 */
public class CriarProjeto_painel2 extends javax.swing.JPanel implements IPainel {

    public CriarProjeto_painel2() {
        initComponents();
        try {
            ModelArchetype.i().get();
            if (ModelArchetype.i().getList() != null && !ModelArchetype.i().getList().isEmpty()) {
                jTFDescricao.setText(ModelArchetype.i().getList().get(0).getArchetypeDescription());
            }
        } catch (AmaterasuException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jCBModeloProjeto = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jCBPentaho = new javax.swing.JCheckBox();
        jCBJasperReport = new javax.swing.JCheckBox();
        jCBApachePOI = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTFDescricao = new javax.swing.JTextArea();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/CriarProjeto_painel2"); // NOI18N
        jLabel1.setText(bundle.getString("MODELO_PROJETO")); // NOI18N

        jCBModeloProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBModeloProjetoActionPerformed(evt);
            }
        });

        jCheckBox1.setSelected(true);
        jCheckBox1.setText(bundle.getString("UTILIZAR_FRAMEWORK_AMATERASU")); // NOI18N
        jCheckBox1.setEnabled(false);

        jLabel2.setText(bundle.getString("TECNOLOGIA")); // NOI18N

        jCBPentaho.setText(bundle.getString("PENTAHO")); // NOI18N

        jCBJasperReport.setText(bundle.getString("JASPER_REPORT")); // NOI18N

        jCBApachePOI.setText(bundle.getString("APACHE_POI")); // NOI18N

        jTFDescricao.setEditable(false);
        jTFDescricao.setColumns(20);
        jTFDescricao.setLineWrap(true);
        jTFDescricao.setRows(5);
        jScrollPane2.setViewportView(jTFDescricao);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCBModeloProjeto, 0, 260, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(jCBPentaho)
                    .addComponent(jCBJasperReport)
                    .addComponent(jCBApachePOI))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jCBModeloProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCBPentaho)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCBJasperReport)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCBApachePOI)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCBModeloProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBModeloProjetoActionPerformed
        for (Archetype a : ModelArchetype.i().getList()) {
            if (a.getArchetypeArtifactId().equals(jCBModeloProjeto.getSelectedItem().toString())) {
                jTFDescricao.setText(a.getArchetypeDescription());
                break;
            }
        }

    }//GEN-LAST:event_jCBModeloProjetoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCBApachePOI;
    private javax.swing.JCheckBox jCBJasperReport;
    private javax.swing.JComboBox jCBModeloProjeto;
    private javax.swing.JCheckBox jCBPentaho;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTFDescricao;
    // End of variables declaration//GEN-END:variables

    @Override
    public void binding() {
        try {
            ModelArchetype.i().get();
            for (Archetype a : ModelArchetype.i().getList()) {
                if (a.getArchetypeArtifactId().equals(jCBModeloProjeto.getSelectedItem().toString())) {
                    ModelProjeto.i().setArchetype(a);
                    break;
                }
            }
            ModelProjeto.i().setJasperReport(jCBJasperReport.isSelected());
            ModelProjeto.i().setPentaho(jCBPentaho.isSelected());
            ModelProjeto.i().setApachePOI(jCBApachePOI.isSelected());

        } catch (AmaterasuException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public void atualizaPainel() {
        DefaultComboBoxModel modelArch = new DefaultComboBoxModel();
        for (Archetype a : ModelArchetype.i().getList()) {
            modelArch.addElement(a.getArchetypeArtifactId());
        }
        jCBModeloProjeto.setModel(modelArch);

        if (ModelProjeto.i().getArchetype() != null && !ModelProjeto.i().getArchetype().getArchetypeArtifactId().equals("")) {
            jCBModeloProjeto.setSelectedItem(ModelProjeto.i().getArchetype().getArchetypeGroupId());
        }
        if (jCBModeloProjeto.getSelectedItem() != null) {

        }
    }
}
