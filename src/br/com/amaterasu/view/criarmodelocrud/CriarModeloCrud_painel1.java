/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.criarmodelocrud;

import br.com.amaterasu.model.CriarModeloBean;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IPainel;
import javax.swing.JFileChooser;

/**
 *
 * @author Maykon
 */
public class CriarModeloCrud_painel1 extends javax.swing.JPanel implements IPainel {

    public CriarModeloCrud_painel1() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        JTFNovoModelo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTFCaminho = new javax.swing.JTextField();
        jBAddCaminhoProjeto = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/CriarModelo_painel1"); // NOI18N
        jLabel2.setText(bundle.getString("NOME_MODELO")); // NOI18N

        JTFNovoModelo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JTFNovoModeloKeyReleased(evt);
            }
        });

        jLabel1.setText("Caminho:");

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/AbrirProjeto_painel1"); // NOI18N
        jBAddCaminhoProjeto.setText(bundle1.getString("ADD")); // NOI18N
        jBAddCaminhoProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddCaminhoProjetoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JTFNovoModelo)
                    .addComponent(jTFCaminho, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBAddCaminhoProjeto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(JTFNovoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTFCaminho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBAddCaminhoProjeto))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void JTFNovoModeloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTFNovoModeloKeyReleased
    }//GEN-LAST:event_JTFNovoModeloKeyReleased

    private void jBAddCaminhoProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddCaminhoProjetoActionPerformed
        JFileChooser chooser = new JFileChooser(jTFCaminho.getText());
        chooser.setDialogTitle("Selecione o diretório para o PROJETO MODELO CRUD");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            jTFCaminho.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_jBAddCaminhoProjetoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JTFNovoModelo;
    private javax.swing.JButton jBAddCaminhoProjeto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTFCaminho;
    // End of variables declaration//GEN-END:variables

    @Override
    public void binding() throws AmaterasuException {
        if (JTFNovoModelo.getText().equals("")) {
            throw new AmaterasuException("Campo Obrigatorio: \n*Nome do Modelo", false);
        }
        CriarModeloBean.i().setNomeModelo(JTFNovoModelo.getText());
        CriarModeloBean.i().setCaminho(jTFCaminho.getText());
    }

    @Override
    public void atualizaPainel() {
    }
}