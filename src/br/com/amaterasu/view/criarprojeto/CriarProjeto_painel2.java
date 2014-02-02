/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.criarprojeto;

import br.com.amaterasu.gerador.ManterTXT;
import br.com.amaterasu.model.ModelProjeto;
import br.com.amaterasu.util.IPainel;
import br.com.amaterasu.util.PathFramework;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Maykon
 */
public class CriarProjeto_painel2 extends javax.swing.JPanel implements IPainel {

    private List<String> tecnologias;

    public CriarProjeto_painel2() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jCBModeloProjeto = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLTecnologia = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jCBPentaho = new javax.swing.JCheckBox();
        jCBJasperReport = new javax.swing.JCheckBox();
        jCBApachePOI = new javax.swing.JCheckBox();

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

        jLTecnologia.setEnabled(false);
        jScrollPane1.setViewportView(jLTecnologia);

        jLabel2.setText(bundle.getString("TECNOLOGIA")); // NOI18N

        jCBPentaho.setText(bundle.getString("PENTAHO")); // NOI18N

        jCBJasperReport.setText(bundle.getString("JASPER_REPORT")); // NOI18N

        jCBApachePOI.setText(bundle.getString("APACHE_POI")); // NOI18N

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
                    .addComponent(jCBModeloProjeto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCBPentaho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBJasperReport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBApachePOI)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCBModeloProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBModeloProjetoActionPerformed
        try {
            tecnologias = ManterTXT.readListLine(new File(PathFramework.pathFrameworkEspecificacao(jCBModeloProjeto.getSelectedItem().toString())));
            DefaultListModel modelList = new DefaultListModel();
            for (String s : tecnologias) {
                modelList.addElement(s);
            }
            jLTecnologia.setModel(modelList);
        } catch (FileNotFoundException ex) {
            jLTecnologia.setModel(new DefaultListModel());
            JOptionPane.showMessageDialog(null, "Não foi encontrado o arquivo especificacao.txt no path:\n" + PathFramework.pathFrameworkEspecificacao(jCBModeloProjeto.getSelectedItem().toString()));
        } catch (IOException ex) {
            jLTecnologia.setModel(new DefaultListModel());
            JOptionPane.showMessageDialog(null, "Não foi possivel ler o arquivo especificacao.txt no path:\n" + PathFramework.pathFrameworkEspecificacao(jCBModeloProjeto.getSelectedItem().toString()));
        }

    }//GEN-LAST:event_jCBModeloProjetoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCBApachePOI;
    private javax.swing.JCheckBox jCBJasperReport;
    private javax.swing.JComboBox jCBModeloProjeto;
    private javax.swing.JCheckBox jCBPentaho;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JList jLTecnologia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void binding() {
        ModelProjeto.i().setModelo(jCBModeloProjeto.getSelectedItem().toString());
        ModelProjeto.i().setJasperReport(jCBJasperReport.isSelected());
        ModelProjeto.i().setPentaho(jCBPentaho.isSelected());
        ModelProjeto.i().setApachePOI(jCBApachePOI.isSelected());
        if (!tecnologias.isEmpty()) {
            ModelProjeto.i().setTecnologia(tecnologias);
        }
    }

    @Override
    public void atualizaPainel() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(new File(PathFramework.pathFramework()).list());
        jCBModeloProjeto.setModel(model);
        if (ModelProjeto.i().getModelo() != null && !ModelProjeto.i().getModelo().equals("")) {
            jCBModeloProjeto.setSelectedItem(ModelProjeto.i().getModelo());
        }
        try {
            tecnologias = ManterTXT.readListLine(new File(PathFramework.pathFrameworkEspecificacao(jCBModeloProjeto.getSelectedItem().toString())));
            DefaultListModel modelList = new DefaultListModel();
            for (String s : tecnologias) {
                modelList.addElement(s);
            }
            jLTecnologia.setModel(modelList);
        } catch (FileNotFoundException ex) {
            jLTecnologia.setModel(new DefaultListModel());
            JOptionPane.showMessageDialog(null, "Não foi encontrado o arquivo especificacao.txt no path:\n" + PathFramework.pathFrameworkEspecificacao(jCBModeloProjeto.getSelectedItem().toString()));
        } catch (IOException ex) {
            jLTecnologia.setModel(new DefaultListModel());
            JOptionPane.showMessageDialog(null, "Não foi possivel ler o arquivo especificacao.txt no path:\n" + PathFramework.pathFrameworkEspecificacao(jCBModeloProjeto.getSelectedItem().toString()));
        }
    }
}
