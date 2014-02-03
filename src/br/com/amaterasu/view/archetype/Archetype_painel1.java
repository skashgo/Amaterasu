/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.archetype;

import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.model.ModelArchetype;
import br.com.amaterasu.util.IPainel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Maykon
 */
public class Archetype_painel1 extends javax.swing.JPanel implements IPainel {

    private List<br.com.amaterasu.model.Archetype> listArchetype = new ArrayList<br.com.amaterasu.model.Archetype>();

    public Archetype_painel1() {
        initComponents();
        DefaultListModel model = new DefaultListModel();
        try {
            ModelArchetype.i().get();
            listArchetype = ModelArchetype.i().getList();
            for (br.com.amaterasu.model.Archetype a : listArchetype) {
                model.addElement(a.getArchetypeArtifactId());
            }
            jList1.setModel(model);
        } catch (AmaterasuException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTFGroupID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTFArtifactID = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jTFVersion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTFDescricao = new javax.swing.JTextArea();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/AbrirProjeto_painel1"); // NOI18N
        jLabel1.setText(bundle.getString("CAMINHO_PROJETO_AMATERASU")); // NOI18N

        jLabel2.setText(bundle.getString("CAMINHO_PROJETO")); // NOI18N

        jList1.setBorder(javax.swing.BorderFactory.createTitledBorder("Archetypes"));
        jList1.setToolTipText(bundle.getString("ULTIMO_PROJETO_ABERTO")); // NOI18N
        jScrollPane1.setViewportView(jList1);

        jLabel3.setText("Archetype Version:");

        jLabel4.setText("Archetype Description:");

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
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                            .addComponent(jTFArtifactID)
                            .addComponent(jTFGroupID)
                            .addComponent(jTFVersion))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTFArtifactID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTFGroupID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTFVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTFArtifactID;
    private javax.swing.JTextArea jTFDescricao;
    private javax.swing.JTextField jTFGroupID;
    private javax.swing.JTextField jTFVersion;
    // End of variables declaration//GEN-END:variables

    @Override
    public void binding() throws AmaterasuException {
        if (jTFArtifactID.getText().equals("") || jTFGroupID.getText().equals("") || jTFVersion.getText().isEmpty()) {
            throw new AmaterasuException("Campo Obrigatorio: \n*Artefact Id \n*Group Id \n*Version", false);
        }
        br.com.amaterasu.model.Archetype archetype = new br.com.amaterasu.model.Archetype();
        archetype.setArchetypeArtifactId(jTFArtifactID.getText());
        archetype.setArchetypeGroupId(jTFGroupID.getText());
        archetype.setArchetypeVersion(jTFVersion.getText());
        archetype.setArchetypeDescription(jTFDescricao.getText());
        ModelArchetype.i().get();
        ModelArchetype.i().getList().add(archetype);
    }

    @Override
    public void atualizaPainel() {
    }
}
