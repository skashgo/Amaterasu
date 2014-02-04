/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.configurar;

import br.com.amaterasu.model.Config;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IPainel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Maykon
 */
public class Config_painel1 extends javax.swing.JPanel implements IPainel {

    public Config_painel1() {
        initComponents();
        try {
            Config.read();
            jTFCaminhoCatalogMaven.setText(Config.i().getCaminhoCatalogMaven());
            jTFCaminhoMaven.setText(Config.i().getCaminhoMaven());
            jTFCaminhoRepoMaven.setText(Config.i().getCaminhoRepoMaven());
            if (Config.i().getSo().equals(Config.SO.WIN)) {
                jRBWin.setSelected(true);
            }
            if (Config.i().getSo().equals(Config.SO.LINUX)) {
                jRBLinux.setSelected(true);
            }
        } catch (AmaterasuException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jTFCaminhoCatalogMaven = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTFCaminhoMaven = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTFCaminhoRepoMaven = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jRBWin = new javax.swing.JRadioButton();
        jRBLinux = new javax.swing.JRadioButton();
        jBtCaminhoMaven = new javax.swing.JButton();
        jBtCaminhoCatalogMaven = new javax.swing.JButton();
        jBtCaminhoRepoMaven = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/Config_painel1"); // NOI18N
        jLabel1.setText(bundle.getString("CAMINHO_CATALOG_MAVEN")); // NOI18N

        jTFCaminhoCatalogMaven.setToolTipText("ex.: D:\\\\Plataforma Amaterasu\\\\Core\\\\apache-maven-3.0.4");

        jLabel2.setText(bundle.getString("CAMINHO_MAVEN")); // NOI18N

        jTFCaminhoMaven.setToolTipText("ex.: C:\\\\Plataforma Amaterasu\\\\Core\\\\apache-maven-3.0.4\\\\bin");

        jLabel3.setText(bundle.getString("CAMINHO_REPO_MAVEN")); // NOI18N

        jTFCaminhoRepoMaven.setToolTipText("ex.: D:\\\\Plataforma Amaterasu\\\\Core\\\\apache-maven-3.0.4\\\\repository");

        jLabel5.setText(bundle.getString("SISTEMA_OPERACIONAL")); // NOI18N
        jLabel5.setToolTipText("Informe o Sistema Operacional que o Amaterasu está sendo executado.");

        buttonGroup1.add(jRBWin);
        jRBWin.setSelected(true);
        jRBWin.setText("WINDOWS");
        jRBWin.setToolTipText("Informe o Sistema Operacional que o Amaterasu está sendo executado.");

        buttonGroup1.add(jRBLinux);
        jRBLinux.setText("LINUX");
        jRBLinux.setToolTipText("Informe o Sistema Operacional que o Amaterasu está sendo executado.");

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/Principal"); // NOI18N
        jBtCaminhoMaven.setText(bundle1.getString("ABRIR")); // NOI18N
        jBtCaminhoMaven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtCaminhoMavenActionPerformed(evt);
            }
        });

        jBtCaminhoCatalogMaven.setText(bundle1.getString("ABRIR")); // NOI18N
        jBtCaminhoCatalogMaven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtCaminhoCatalogMavenActionPerformed(evt);
            }
        });

        jBtCaminhoRepoMaven.setText(bundle1.getString("ABRIR")); // NOI18N
        jBtCaminhoRepoMaven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtCaminhoRepoMavenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRBWin)
                        .addGap(18, 18, 18)
                        .addComponent(jRBLinux))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTFCaminhoCatalogMaven)
                            .addComponent(jTFCaminhoMaven, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                            .addComponent(jTFCaminhoRepoMaven))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtCaminhoMaven)
                            .addComponent(jBtCaminhoCatalogMaven)
                            .addComponent(jBtCaminhoRepoMaven))))
                .addContainerGap(285, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFCaminhoMaven, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtCaminhoMaven))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTFCaminhoCatalogMaven, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtCaminhoCatalogMaven))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFCaminhoRepoMaven, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtCaminhoRepoMaven))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jRBWin)
                    .addComponent(jRBLinux))
                .addContainerGap(111, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBtCaminhoMavenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtCaminhoMavenActionPerformed
        JFileChooser chooser = new JFileChooser(jTFCaminhoMaven.getText());
        chooser.setDialogTitle("Selecione o diretório bin do programa Maven");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            jTFCaminhoMaven.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_jBtCaminhoMavenActionPerformed

    private void jBtCaminhoCatalogMavenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtCaminhoCatalogMavenActionPerformed
        JFileChooser chooser = new JFileChooser(jTFCaminhoCatalogMaven.getText());
        chooser.setDialogTitle("Selecione o diretório onde está o arquivo archetype-catalog.xml do programa Maven");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            jTFCaminhoCatalogMaven.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_jBtCaminhoCatalogMavenActionPerformed

    private void jBtCaminhoRepoMavenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtCaminhoRepoMavenActionPerformed
        JFileChooser chooser = new JFileChooser(jTFCaminhoRepoMaven.getText());
        chooser.setDialogTitle("Selecione o diretório repository do programa Maven");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            jTFCaminhoRepoMaven.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_jBtCaminhoRepoMavenActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBtCaminhoCatalogMaven;
    private javax.swing.JButton jBtCaminhoMaven;
    private javax.swing.JButton jBtCaminhoRepoMaven;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JRadioButton jRBLinux;
    private javax.swing.JRadioButton jRBWin;
    private javax.swing.JTextField jTFCaminhoCatalogMaven;
    private javax.swing.JTextField jTFCaminhoMaven;
    private javax.swing.JTextField jTFCaminhoRepoMaven;
    // End of variables declaration//GEN-END:variables

    @Override
    public void binding() throws AmaterasuException {
        Config.i().setCaminhoCatalogMaven(jTFCaminhoCatalogMaven.getText());
        Config.i().setCaminhoMaven(jTFCaminhoMaven.getText());
        Config.i().setCaminhoRepoMaven(jTFCaminhoRepoMaven.getText());
        if (jRBLinux.isSelected()) {
            Config.i().setSo(Config.SO.LINUX);
        }
        if (jRBWin.isSelected()) {
            Config.i().setSo(Config.SO.WIN);
        }
    }

    @Override
    public void atualizaPainel() {
    }
}
