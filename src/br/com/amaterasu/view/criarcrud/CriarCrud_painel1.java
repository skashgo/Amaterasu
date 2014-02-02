/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.criarcrud;

import br.com.amaterasu.gerador.ManterLog;
import br.com.amaterasu.gerador.ManterXML;
import br.com.amaterasu.model.CriarCRUDBean;
import br.com.amaterasu.model.CriarProjetoBean;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.Field;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.IPainel;
import br.com.amaterasu.util.PathFramework;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Maykon
 */
public class CriarCrud_painel1 extends javax.swing.JPanel implements IPainel {

    private List<Field> listFieldsBoolean = new ArrayList<Field>();
    private String[] historico;
    private String cruds = CriarProjetoBean.i().getCaminhoAmaterasu() + IConstants.barra + "CRUDs";

    public CriarCrud_painel1() {
        initComponents();
        String[] modelosCRUD = new File(PathFramework.pathFrameworkModelosCRUD(CriarProjetoBean.i().getModelo())).list();
        if (modelosCRUD != null) {
            jCBModeloCrud.setModel(new DefaultComboBoxModel(modelosCRUD));
        }
        historico = new File(cruds).list();
        if (historico != null) {
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            model.addElement("Selecione");
            for (String s : historico) {
                model.addElement(s);
            }
            jCBHistorico.setModel(model);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jCBModeloCrud = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTFCaminhoClasseBean = new javax.swing.JTextField();
        jBAddCaminhoClasseBean = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTFNomeCasoUso = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jCBCadastro = new javax.swing.JCheckBox();
        jCBAlteracao = new javax.swing.JCheckBox();
        jCBVisualizacao = new javax.swing.JCheckBox();
        jCBFiltro = new javax.swing.JCheckBox();
        jCBExclusaoFisica = new javax.swing.JCheckBox();
        jCBExclusaoLogica = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jCBCampoExclusaoLogica = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jCBGrid = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jRLivre = new javax.swing.JRadioButton();
        jRGrid = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jCBHistorico = new javax.swing.JComboBox();
        jBInfo = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/CriarCrud_painel1"); // NOI18N
        jLabel1.setText(bundle.getString("MODELO_CRUD")); // NOI18N

        jLabel2.setText(bundle.getString("CLASSE_BEAN")); // NOI18N

        jTFCaminhoClasseBean.setEditable(false);

        jBAddCaminhoClasseBean.setText(bundle.getString("ADD")); // NOI18N
        jBAddCaminhoClasseBean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddCaminhoClasseBeanActionPerformed(evt);
            }
        });

        jLabel3.setText(bundle.getString("NOME_CASO_USO")); // NOI18N

        jTFNomeCasoUso.setEditable(false);

        jCBCadastro.setText(bundle.getString("CADASTRO")); // NOI18N

        jCBAlteracao.setText(bundle.getString("ALTERACAO")); // NOI18N

        jCBVisualizacao.setText(bundle.getString("VISUALIZACAO")); // NOI18N

        jCBFiltro.setText(bundle.getString("FILTRO")); // NOI18N

        jCBExclusaoFisica.setText(bundle.getString("EXCLUSAO_FISICA")); // NOI18N
        jCBExclusaoFisica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBExclusaoFisicaActionPerformed(evt);
            }
        });

        jCBExclusaoLogica.setText(bundle.getString("EXCLUSAO_LOGICA")); // NOI18N
        jCBExclusaoLogica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBExclusaoLogicaActionPerformed(evt);
            }
        });

        jLabel4.setText(bundle.getString("CAMPO_EXCLUSAO_LOGICA")); // NOI18N

        jCBCampoExclusaoLogica.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText(bundle.getString("FUNCIONALIDADE_CRUD")); // NOI18N

        jCBGrid.setText(bundle.getString("GRID")); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText(bundle.getString("LAYOUT")); // NOI18N

        buttonGroup1.add(jRLivre);
        jRLivre.setText(bundle.getString("LIVRE")); // NOI18N
        jRLivre.setToolTipText(bundle.getString("DESCRICAO_LIVRE")); // NOI18N

        buttonGroup1.add(jRGrid);
        jRGrid.setSelected(true);
        jRGrid.setText(bundle.getString("GRID")); // NOI18N
        jRGrid.setToolTipText(bundle.getString("DESCRICAO_GRID")); // NOI18N

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel7.setText(bundle.getString("HISTORICO")); // NOI18N

        jCBHistorico.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione..." }));
        jCBHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBHistoricoActionPerformed(evt);
            }
        });

        jBInfo.setText("i");
        jBInfo.setToolTipText("Informação sobre o modelo de Crud");
        jBInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBInfo))
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCBModeloCrud, 0, 267, Short.MAX_VALUE)
                            .addComponent(jTFNomeCasoUso))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTFCaminhoClasseBean)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBAddCaminhoClasseBean))
                            .addComponent(jCBHistorico, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCBVisualizacao)
                                    .addComponent(jCBAlteracao)
                                    .addComponent(jCBCadastro))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCBExclusaoFisica)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCBExclusaoLogica)
                                            .addComponent(jCBFiltro))
                                        .addGap(18, 18, 18)
                                        .addComponent(jCBGrid))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jCBCampoExclusaoLogica, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRLivre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRGrid))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel6)
                                .addGap(31, 31, 31)))
                        .addGap(0, 356, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jCBModeloCrud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTFCaminhoClasseBean, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBAddCaminhoClasseBean)
                    .addComponent(jBInfo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFNomeCasoUso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jCBHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCBCadastro)
                                    .addComponent(jCBFiltro)
                                    .addComponent(jCBGrid))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCBAlteracao)
                                    .addComponent(jCBExclusaoFisica))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCBVisualizacao)
                                    .addComponent(jCBExclusaoLogica))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jCBCampoExclusaoLogica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jRLivre)
                                    .addComponent(jRGrid))))
                        .addGap(0, 1, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBAddCaminhoClasseBeanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddCaminhoClasseBeanActionPerformed
        limpar();
        File file = new File(CriarProjetoBean.i().getCaminhoAmaterasu() + IConstants.barra + "Geral" + IConstants.barra + "caminhoBean.xml");
        String path = "";
        try {
            if (file.exists()) {
                path = (String) ManterXML.readXML(file);
            }
        } catch (AmaterasuException ex) {
            Logger.getLogger(CriarCrud_painel1.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (path.equals("")) {
            path = jTFCaminhoClasseBean.getText();
        }
        if (path.equals("")) {
            path = CriarProjetoBean.i().getCaminho();
        }
        JFileChooser chooser = new JFileChooser(path);
        chooser.setDialogTitle("Selecione a classe BEAN.");
        chooser.setFileFilter(new FileNameExtensionFilter("Classe - Bean", "java"));
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            jTFCaminhoClasseBean.setText(chooser.getSelectedFile().getAbsolutePath());
            try {
                CriarCRUDBean.i().setCaminhoClasseBean(jTFCaminhoClasseBean.getText());
                CriarCRUDBean.i().readBean();
                jCBGrid.setEnabled(true);
                for (Field f : CriarCRUDBean.i().getListFields()) {
                    if (f.getTipo().equals("Boolean") || f.getTipo().equals("boolean")) {
                        listFieldsBoolean.add(f);
                    }
                }
                jCBCampoExclusaoLogica.setModel(new DefaultComboBoxModel(listFieldsBoolean.toArray()));
                jTFNomeCasoUso.setText(new File(jTFCaminhoClasseBean.getText()).getName().replace(".java", ""));
                ManterXML.writeXML(file, new File(jTFCaminhoClasseBean.getText()).getParent());
            } catch (AmaterasuException ex) {
                ManterLog.write(ex);
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }//GEN-LAST:event_jBAddCaminhoClasseBeanActionPerformed

    private void jCBExclusaoLogicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBExclusaoLogicaActionPerformed
        if (jCBExclusaoLogica.isSelected()) {
            jCBExclusaoFisica.setSelected(false);
        }
        jCBCampoExclusaoLogica.setEnabled(jCBExclusaoLogica.isSelected());
    }//GEN-LAST:event_jCBExclusaoLogicaActionPerformed

    private void jCBExclusaoFisicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBExclusaoFisicaActionPerformed
        if (jCBExclusaoFisica.isSelected()) {
            jCBExclusaoLogica.setSelected(false);
            jCBCampoExclusaoLogica.setEnabled(false);
        }
    }//GEN-LAST:event_jCBExclusaoFisicaActionPerformed

    private void jCBHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBHistoricoActionPerformed
        limpar();
        if (jCBHistorico.getSelectedIndex() == 0) {
            return;
        }
        try {
            CriarCRUDBean.i().set((CriarCRUDBean) ManterXML.readXML(new File(cruds + IConstants.barra + historico[jCBHistorico.getSelectedIndex() - 1])));
            listFieldsBoolean.clear();
            for (Field f : CriarCRUDBean.i().getListFields()) {
                if (f.getTipo().equals("Boolean") || f.getTipo().equals("boolean")) {
                    listFieldsBoolean.add(f);
                }
            }
            jCBCampoExclusaoLogica.setModel(new DefaultComboBoxModel(listFieldsBoolean.toArray()));

            jCBModeloCrud.setSelectedItem(CriarCRUDBean.i().getNomeModelo());
            jTFCaminhoClasseBean.setText(CriarCRUDBean.i().getCaminhoClasseBean());
            jTFNomeCasoUso.setText(CriarCRUDBean.i().getNomeCasoUso());
            jCBAlteracao.setSelected(CriarCRUDBean.i().isModoAlteracao());
            jCBCadastro.setSelected(CriarCRUDBean.i().isModoCadastro());
            jCBExclusaoFisica.setSelected(CriarCRUDBean.i().isModoExclusaoFisica());
            jCBExclusaoLogica.setSelected(CriarCRUDBean.i().isModoExclusaoLogica());
            jCBFiltro.setSelected(CriarCRUDBean.i().isModoFiltro());
            jCBVisualizacao.setSelected(CriarCRUDBean.i().isModoVisualizacao());
            jCBGrid.setSelected(CriarCRUDBean.i().isModoGrid());
            jRGrid.setSelected(CriarCRUDBean.i().isLayoutGrid());
            jRLivre.setSelected(!CriarCRUDBean.i().isLayoutGrid());
            if (jCBExclusaoLogica.isSelected()) {
                jCBCampoExclusaoLogica.setSelectedItem(CriarCRUDBean.i().getCampoExclusaoLogica());
                jCBCampoExclusaoLogica.setEnabled(true);
            } else {
                jCBCampoExclusaoLogica.setEnabled(false);
            }

        } catch (AmaterasuException ex) {
            ManterLog.write(ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_jCBHistoricoActionPerformed

    private void jBInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBInfoActionPerformed
        try {
            CriarCRUDBean.i().setNomeModelo(jCBModeloCrud.getSelectedItem().toString());
            JOptionPane.showMessageDialog(null, CriarCRUDBean.i().info());
        } catch (AmaterasuException ex) {
            Logger.getLogger(CriarCrud_painel1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBInfoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBAddCaminhoClasseBean;
    private javax.swing.JButton jBInfo;
    private javax.swing.JCheckBox jCBAlteracao;
    private javax.swing.JCheckBox jCBCadastro;
    private javax.swing.JComboBox jCBCampoExclusaoLogica;
    private javax.swing.JCheckBox jCBExclusaoFisica;
    private javax.swing.JCheckBox jCBExclusaoLogica;
    private javax.swing.JCheckBox jCBFiltro;
    private javax.swing.JCheckBox jCBGrid;
    private javax.swing.JComboBox jCBHistorico;
    private javax.swing.JComboBox jCBModeloCrud;
    private javax.swing.JCheckBox jCBVisualizacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JRadioButton jRGrid;
    private javax.swing.JRadioButton jRLivre;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTFCaminhoClasseBean;
    private javax.swing.JTextField jTFNomeCasoUso;
    // End of variables declaration//GEN-END:variables

    @Override
    public void binding() throws AmaterasuException {
        if (jCBModeloCrud.getSelectedIndex() == -1 || (jTFCaminhoClasseBean.getText().equals("") && jCBHistorico.getSelectedIndex() == 0) || jTFNomeCasoUso.getText().equals("")) {
            throw new AmaterasuException("Campo Obrigatorio: \n*Modelo do CRUD \n*Classe Bean \n*Nome do Caso de Uso", false);
        }
        if (!new File(jTFCaminhoClasseBean.getText()).exists() && jCBHistorico.getSelectedIndex() == 0) {
            throw new AmaterasuException("O caminho da Classe Bean informado é invalido.", false);
        }
        if (jCBExclusaoLogica.isSelected() && jCBCampoExclusaoLogica.getSelectedIndex() == -1) {
            throw new AmaterasuException("Para exclusão lógica é obrigatorio selecionar um Campo para Exclusão Lógica.", false);
        }
        CriarCRUDBean.i().setNomeModelo(jCBModeloCrud.getSelectedItem().toString());
        CriarCRUDBean.i().setCaminhoClasseBean(jTFCaminhoClasseBean.getText());
        CriarCRUDBean.i().setNomeBean(jTFCaminhoClasseBean.getText().substring(jTFCaminhoClasseBean.getText().lastIndexOf(IConstants.barra)).replace(".java", ""));
        CriarCRUDBean.i().setNomeCasoUso(jTFNomeCasoUso.getText());
        CriarCRUDBean.i().setModoAlteracao(jCBAlteracao.isSelected());
        CriarCRUDBean.i().setModoCadastro(jCBCadastro.isSelected());
        CriarCRUDBean.i().setModoExclusaoFisica(jCBExclusaoFisica.isSelected());
        CriarCRUDBean.i().setModoExclusaoLogica(jCBExclusaoLogica.isSelected());
        CriarCRUDBean.i().setModoFiltro(jCBFiltro.isSelected());
        CriarCRUDBean.i().setModoVisualizacao(jCBVisualizacao.isSelected());
        CriarCRUDBean.i().setModoGrid(jCBGrid.isSelected());
        CriarCRUDBean.i().setLayoutGrid(jRGrid.isSelected());
        if (jCBExclusaoLogica.isSelected()) {
            CriarCRUDBean.i().setCampoExclusaoLogica(listFieldsBoolean.get(jCBCampoExclusaoLogica.getSelectedIndex()));
        }
    }

    @Override
    public void atualizaPainel() {
    }

    private void limpar() {
        jCBCampoExclusaoLogica.setModel(new DefaultComboBoxModel());

        jCBModeloCrud.setSelectedItem("");
        jTFCaminhoClasseBean.setText("");
        jTFNomeCasoUso.setText("");
        jCBAlteracao.setSelected(false);
        jCBCadastro.setSelected(false);
        jCBExclusaoFisica.setSelected(false);
        jCBExclusaoLogica.setSelected(false);
        jCBFiltro.setSelected(false);
        jCBVisualizacao.setSelected(false);
        jCBGrid.setSelected(false);
        jRGrid.setSelected(false);
        if (jCBCampoExclusaoLogica.getItemCount() > 0) {
            jCBCampoExclusaoLogica.setSelectedIndex(0);
        }
        jCBCampoExclusaoLogica.setEnabled(false);
    }
}