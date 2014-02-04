/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.abrirprojeto;

import br.com.amaterasu.model.ModelProjeto;
import br.com.amaterasu.model.HistoricoProjetoBean;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.model.HistoricoProjeto;
import br.com.amaterasu.util.IPainel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Maykon
 */
public class AbrirProjeto_painel1 extends javax.swing.JPanel implements IPainel {

    private List<HistoricoProjeto> listHistoricoProjetos = new ArrayList<HistoricoProjeto>();

    public AbrirProjeto_painel1() {
        initComponents();
        DefaultListModel model = new DefaultListModel();
        try {
            HistoricoProjetoBean.i().get();
            listHistoricoProjetos = HistoricoProjetoBean.i().getList();
            for (HistoricoProjeto hp : listHistoricoProjetos) {
                model.addElement(hp.getProjeto());
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
        jTFCaminhoProjetoAmaterasu = new javax.swing.JTextField();
        jBAddCaminhoProjetoAmaterasu = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTFCaminhoProjeto = new javax.swing.JTextField();
        jBAddCaminhoProjeto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/AbrirProjeto_painel1"); // NOI18N
        jLabel1.setText(bundle.getString("CAMINHO_PROJETO_AMATERASU")); // NOI18N

        jBAddCaminhoProjetoAmaterasu.setText(bundle.getString("ADD")); // NOI18N
        jBAddCaminhoProjetoAmaterasu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddCaminhoProjetoAmaterasuActionPerformed(evt);
            }
        });

        jLabel2.setText(bundle.getString("CAMINHO_PROJETO")); // NOI18N

        jBAddCaminhoProjeto.setText(bundle.getString("ADD")); // NOI18N
        jBAddCaminhoProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddCaminhoProjetoActionPerformed(evt);
            }
        });

        jList1.setBorder(javax.swing.BorderFactory.createTitledBorder("Histórico"));
        jList1.setToolTipText(bundle.getString("ULTIMO_PROJETO_ABERTO")); // NOI18N
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFCaminhoProjetoAmaterasu)
                    .addComponent(jTFCaminhoProjeto, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBAddCaminhoProjetoAmaterasu)
                    .addComponent(jBAddCaminhoProjeto))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
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
                            .addComponent(jTFCaminhoProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBAddCaminhoProjeto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTFCaminhoProjetoAmaterasu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBAddCaminhoProjetoAmaterasu))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBAddCaminhoProjetoAmaterasuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddCaminhoProjetoAmaterasuActionPerformed
        JFileChooser chooser = new JFileChooser(jTFCaminhoProjetoAmaterasu.getText());
        chooser.setDialogTitle("Selecione o diretório raiz do PROJETO Amaterasu que deseja abrir");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            jTFCaminhoProjetoAmaterasu.setText(chooser.getSelectedFile().getAbsolutePath());
        }

    }//GEN-LAST:event_jBAddCaminhoProjetoAmaterasuActionPerformed

    private void jBAddCaminhoProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddCaminhoProjetoActionPerformed
        JFileChooser chooser = new JFileChooser(jTFCaminhoProjeto.getText());
        chooser.setDialogTitle("Selecione o diretório raiz do PROJETO que deseja abrir");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            jTFCaminhoProjeto.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_jBAddCaminhoProjetoActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        for (HistoricoProjeto hp : listHistoricoProjetos) {
            if (hp.getProjeto().equals(jList1.getSelectedValue().toString())) {
                jTFCaminhoProjeto.setText(hp.getCaminhoProjeto());
                jTFCaminhoProjetoAmaterasu.setText(hp.getCaminhoProjetoAmaterasu());
            }
        }
    }//GEN-LAST:event_jList1MouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAddCaminhoProjeto;
    private javax.swing.JButton jBAddCaminhoProjetoAmaterasu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFCaminhoProjeto;
    private javax.swing.JTextField jTFCaminhoProjetoAmaterasu;
    // End of variables declaration//GEN-END:variables

    @Override
    public void binding() throws AmaterasuException {
        if (jTFCaminhoProjeto.getText().equals("") || jTFCaminhoProjetoAmaterasu.getText().equals("")) {
            throw new AmaterasuException("Campo Obrigatorio: \n*Caminho Projeto \n*Caminho Projeto Amaterasu", false);
        }
        ModelProjeto.i().setCaminhoAmaterasu(jTFCaminhoProjetoAmaterasu.getText());
        ModelProjeto.i().setCaminho(jTFCaminhoProjeto.getText());
    }

    @Override
    public void atualizaPainel() {
    }
}