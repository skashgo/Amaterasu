/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.criarprojeto;

import br.com.amaterasu.model.ModelProjeto;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.IPainel;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;

/**
 *
 * @author Maykon
 */
public class CriarProjeto_painel1 extends javax.swing.JPanel implements IPainel {

    public CriarProjeto_painel1() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        JTFNomeProjeto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTFCliente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTFPacotePadrao = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTFNomeCompleto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTFCaminho = new javax.swing.JTextField();
        jBCaminhoProjeto = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jCBServidor = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        JTFCaminhoProjetoAmaterasu = new javax.swing.JTextField();
        JBCaminhoProjetoAmaterasu = new javax.swing.JButton();
        jTFCaminhoServidor = new javax.swing.JTextField();
        JBCaminhoServidor = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/CriarProjeto_painel1"); // NOI18N
        jLabel1.setText(bundle.getString("LABEL_NOME_PROJETO")); // NOI18N

        JTFNomeProjeto.setToolTipText(bundle.getString("TOOLTIP_NOME_PROJETO")); // NOI18N

        jLabel2.setText(bundle.getString("LABEL_CLIENTE")); // NOI18N

        jLabel3.setText(bundle.getString("LABEL_PACOTE_PADRAO")); // NOI18N

        jTFPacotePadrao.setToolTipText(bundle.getString("TOOLTIP_PACOTE_PADRAO")); // NOI18N

        jLabel4.setText(bundle.getString("LABEL_NOME_COMPLETO")); // NOI18N

        jTFNomeCompleto.setToolTipText(bundle.getString("TOOLTIP_NOME_COMPLETO")); // NOI18N

        jLabel5.setText("Caminho Projeto:");

        jBCaminhoProjeto.setText("Add");
        jBCaminhoProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCaminhoProjetoActionPerformed(evt);
            }
        });

        jLabel6.setText("Servidor:");

        jCBServidor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "JBoss 4.2", "JBoss AS 7", "Tomcat" }));

        jLabel7.setText("Caminho Projeto Amaterasu:");

        JBCaminhoProjetoAmaterasu.setText("Add");
        JBCaminhoProjetoAmaterasu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBCaminhoProjetoAmaterasuActionPerformed(evt);
            }
        });

        JBCaminhoServidor.setText("Add");
        JBCaminhoServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBCaminhoServidorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTFCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                            .addComponent(JTFNomeProjeto))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTFNomeCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                                    .addComponent(jTFPacotePadrao)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCBServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTFCaminhoServidor, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                                    .addComponent(jTFCaminho, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(JTFCaminhoProjetoAmaterasu, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBCaminhoProjeto)
                                    .addComponent(JBCaminhoProjetoAmaterasu)
                                    .addComponent(JBCaminhoServidor))))
                        .addGap(18, 18, 18))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(JTFNomeProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTFNomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFPacotePadrao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTFCaminho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBCaminhoProjeto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(JTFCaminhoProjetoAmaterasu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBCaminhoProjetoAmaterasu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jCBServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFCaminhoServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBCaminhoServidor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBCaminhoProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCaminhoProjetoActionPerformed
        JFileChooser chooser = new JFileChooser(jTFCaminho.getText());
        chooser.setDialogTitle("Selecione um diretório raiz para o PROJETO");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            jTFCaminho.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_jBCaminhoProjetoActionPerformed

    private void JBCaminhoProjetoAmaterasuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBCaminhoProjetoAmaterasuActionPerformed
        JFileChooser chooser = new JFileChooser(JTFCaminhoProjetoAmaterasu.getText());
        chooser.setDialogTitle("Selecione um diretório raiz para o PROJETO Amaterasu");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            JTFCaminhoProjetoAmaterasu.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_JBCaminhoProjetoAmaterasuActionPerformed

    private void JBCaminhoServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBCaminhoServidorActionPerformed
        JFileChooser chooser = new JFileChooser(jTFCaminhoServidor.getText());
        chooser.setDialogTitle("Selecione o diretório raiz do Servidor de Aplicação.");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            jTFCaminhoServidor.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_JBCaminhoServidorActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBCaminhoProjetoAmaterasu;
    private javax.swing.JButton JBCaminhoServidor;
    private javax.swing.JTextField JTFCaminhoProjetoAmaterasu;
    private javax.swing.JTextField JTFNomeProjeto;
    private javax.swing.JButton jBCaminhoProjeto;
    private javax.swing.JComboBox jCBServidor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTFCaminho;
    private javax.swing.JTextField jTFCaminhoServidor;
    private javax.swing.JTextField jTFCliente;
    private javax.swing.JTextField jTFNomeCompleto;
    private javax.swing.JTextField jTFPacotePadrao;
    // End of variables declaration//GEN-END:variables

    @Override
    public void binding() throws AmaterasuException {
        if (JTFNomeProjeto.getText().equals("") || JTFCaminhoProjetoAmaterasu.getText().equals("") || jTFCaminho.getText().equals("")) {
            throw new AmaterasuException("Campos Obrigatorios: \n*Nome do Projeto\n*Caminho Projeto\n*Caminho Projeto Amaterasu", false);
        }
        ModelProjeto.i().setNomeProjeto(JTFNomeProjeto.getText());
        ModelProjeto.i().setNomeCompleto(jTFNomeCompleto.getText());
        ModelProjeto.i().setCaminho(jTFCaminho.getText() + IConstants.barra);
        ModelProjeto.i().setCaminhoAmaterasu(JTFCaminhoProjetoAmaterasu.getText() + IConstants.barra + JTFNomeProjeto.getText() + "_AMATERASU" + IConstants.barra);
        ModelProjeto.i().setCliente(jTFCliente.getText());
        ModelProjeto.i().setPacotePadrao(jTFPacotePadrao.getText());
        ModelProjeto.i().setServidor(jCBServidor.getSelectedItem().toString());
        ModelProjeto.i().setCaminhoServidor(jTFCaminhoServidor.getText());
        ModelProjeto.i().setDataCriacao(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
    }

    @Override
    public void atualizaPainel() {
    }
}
