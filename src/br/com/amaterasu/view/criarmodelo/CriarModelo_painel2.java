/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.criarmodelo;

import br.com.amaterasu.model.CriarModeloBean;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IPainel;
import javax.swing.DefaultListModel;

/**
 *
 * @author Maykon
 */
public class CriarModelo_painel2 extends javax.swing.JPanel implements IPainel {

    private DefaultListModel modelList = new DefaultListModel();

    public CriarModelo_painel2() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTFTecnologia = new javax.swing.JTextField();
        jBAdicionar = new javax.swing.JButton();
        jBRemover = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jLTecnologia = new javax.swing.JList();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/CriarModelo_painel2"); // NOI18N
        jLabel1.setText(bundle.getString("TECNOLOGIAS")); // NOI18N

        jTFTecnologia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFTecnologiaActionPerformed(evt);
            }
        });

        jBAdicionar.setText(bundle.getString("ADICIONAR")); // NOI18N
        jBAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAdicionarActionPerformed(evt);
            }
        });

        jBRemover.setText(bundle.getString("REMOVER")); // NOI18N
        jBRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemoverActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(jLTecnologia);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFTecnologia, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBAdicionar)
                            .addComponent(jBRemover))))
                .addGap(96, 394, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTFTecnologia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBAdicionar)
                        .addGap(18, 18, 18)
                        .addComponent(jBRemover)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAdicionarActionPerformed
        modelList.addElement(jTFTecnologia.getText());
        jTFTecnologia.setText("");
        jLTecnologia.setModel(modelList);
    }//GEN-LAST:event_jBAdicionarActionPerformed

    private void jBRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemoverActionPerformed
        if (jLTecnologia.getSelectedIndex() != -1) {
            modelList.remove(jLTecnologia.getSelectedIndex());
            jLTecnologia.setModel(modelList);
        }
    }//GEN-LAST:event_jBRemoverActionPerformed

    private void jTFTecnologiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFTecnologiaActionPerformed
        jBAdicionarActionPerformed(evt);
    }//GEN-LAST:event_jTFTecnologiaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAdicionar;
    private javax.swing.JButton jBRemover;
    private javax.swing.JList jLTecnologia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTFTecnologia;
    // End of variables declaration//GEN-END:variables

    @Override
    public void binding() throws AmaterasuException {
        for (int i = 0; i < jLTecnologia.getModel().getSize(); i++) {
            CriarModeloBean.i().getTecnoligas().add(jLTecnologia.getModel().getElementAt(i).toString());
        }
    }

    @Override
    public void atualizaPainel() {
    }
}