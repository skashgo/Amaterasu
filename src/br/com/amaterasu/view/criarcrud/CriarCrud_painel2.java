/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.criarcrud;

import br.com.amaterasu.gerador.GerarCrud;
import br.com.amaterasu.gerador.ManterLog;
import br.com.amaterasu.model.Component;
import br.com.amaterasu.model.CriarCRUDBean;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.Field;
import br.com.amaterasu.util.Format;
import br.com.amaterasu.util.IPainel;
import br.com.amaterasu.util.Table;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Maykon
 */
public class CriarCrud_painel2 extends javax.swing.JPanel implements IPainel {

    private static final Integer CAD = 0;
    private static final Integer ALT = 1;
    private static final Integer GRID = 2;
    private static final Integer FILT = 3;
    private static final Integer VIEW = 4;
    private static final Integer TIPO = 5;
    private static final Integer VAR = 6;
    private static final Integer COMP = 7;
    private static final Integer LABEL = 8;
    private static final Integer LOCAL = 9;

    private boolean next = true;

    public CriarCrud_painel2() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jTFTipo = new javax.swing.JTextField();
        jTFVariavel = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jBExcluirCampo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jCkCadastro = new javax.swing.JCheckBox();
        jCkGrid = new javax.swing.JCheckBox();
        jCkFiltro = new javax.swing.JCheckBox();
        jCkVisualiza = new javax.swing.JCheckBox();
        jCkAltera = new javax.swing.JCheckBox();
        jBAdicionar = new javax.swing.JButton();

        jLabel3.setText("Adicionar novo campo:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "", "Tipo", "Variavel", "Componente", "Label"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setText("Tipo:");

        jLabel5.setText("Variável:");

        jBExcluirCampo.setText("Excluir");
        jBExcluirCampo.setToolTipText("Exclui linha selecionada da tabela");
        jBExcluirCampo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExcluirCampoActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Selecionar todos");

        jCkCadastro.setText("Cadastro");
        jCkCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCkCadastroActionPerformed(evt);
            }
        });

        jCkGrid.setText("Grid");
        jCkGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCkGridActionPerformed(evt);
            }
        });

        jCkFiltro.setText("Filtro");
        jCkFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCkFiltroActionPerformed(evt);
            }
        });

        jCkVisualiza.setText("Visualiza");
        jCkVisualiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCkVisualizaActionPerformed(evt);
            }
        });

        jCkAltera.setText("Altera");
        jCkAltera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCkAlteraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCkVisualiza)
                    .addComponent(jCkFiltro)
                    .addComponent(jCkGrid)
                    .addComponent(jCkAltera)
                    .addComponent(jCkCadastro)
                    .addComponent(jLabel2))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCkCadastro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCkAltera)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCkGrid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCkFiltro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCkVisualiza)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBAdicionar.setText("Adicionar");
        jBAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAdicionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTFTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFVariavel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBExcluirCampo)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(245, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTFTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jTFVariavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBAdicionar)
                            .addComponent(jBExcluirCampo)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(161, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBExcluirCampoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExcluirCampoActionPerformed
        if (jTable1.getSelectedRow() != -1) {
            try {
                binding();
                CriarCRUDBean.i().getListFields().remove(jTable1.getSelectedRow());
                next = false;
                binding();
                next = true;
                updateTables();
            } catch (AmaterasuException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }//GEN-LAST:event_jBExcluirCampoActionPerformed

    private void jCkCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCkCadastroActionPerformed
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            jTable1.setValueAt(jCkCadastro.isSelected(), i, CAD);
        }
    }//GEN-LAST:event_jCkCadastroActionPerformed

    private void jCkGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCkGridActionPerformed
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            jTable1.setValueAt(jCkGrid.isSelected(), i, GRID);
        }
    }//GEN-LAST:event_jCkGridActionPerformed

    private void jCkFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCkFiltroActionPerformed
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            jTable1.setValueAt(jCkFiltro.isSelected(), i, FILT);
        }
    }//GEN-LAST:event_jCkFiltroActionPerformed

    private void jCkVisualizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCkVisualizaActionPerformed
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            jTable1.setValueAt(jCkVisualiza.isSelected(), i, VIEW);
        }
    }//GEN-LAST:event_jCkVisualizaActionPerformed

    private void jCkAlteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCkAlteraActionPerformed
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            jTable1.setValueAt(jCkAltera.isSelected(), i, ALT);
        }
    }//GEN-LAST:event_jCkAlteraActionPerformed

    private void jBAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAdicionarActionPerformed
        if (!jTFTipo.getText().isEmpty() && !jTFVariavel.getText().isEmpty()) {
            try {
                addField();
                next = false;
                binding();
                updateTables();
                next = true;
                jTFTipo.setText("");
                jTFVariavel.setText("");
            } catch (AmaterasuException ex) {
                ManterLog.write(ex);
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }//GEN-LAST:event_jBAdicionarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAdicionar;
    private javax.swing.JButton jBExcluirCampo;
    private javax.swing.JCheckBox jCkAltera;
    private javax.swing.JCheckBox jCkCadastro;
    private javax.swing.JCheckBox jCkFiltro;
    private javax.swing.JCheckBox jCkGrid;
    private javax.swing.JCheckBox jCkVisualiza;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFTipo;
    private javax.swing.JTextField jTFVariavel;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void binding() throws AmaterasuException {
        boolean valid = false;
        if (next) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                if ((jTable1.getValueAt(i, CAD) != null && (Boolean) jTable1.getValueAt(i, CAD)) || (jTable1.getValueAt(i, ALT) != null && (Boolean) jTable1.getValueAt(i, ALT)) || (jTable1.getValueAt(i, GRID) != null && (Boolean) jTable1.getValueAt(i, GRID)) || (jTable1.getValueAt(i, FILT) != null && (Boolean) jTable1.getValueAt(i, FILT)) || (jTable1.getValueAt(i, VIEW) != null && (Boolean) jTable1.getValueAt(i, VIEW))) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                JOptionPane.showMessageDialog(null, "Nenhum campo foi marcado para ser exibido.");
            }
        }

        Boolean expandirTabela = false;
        //dirBeans = diretorio de beans
        File dirBeans = new File(CriarCRUDBean.i().getCaminhoClasseBean()).getParentFile();
        //percorre todos as linhas da tabela para preencher os objetos com as informações das linhas
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            //se ao menos uma opção de check (estado) estiver checked add objeto
            //valida campos obrigatorios

            Field field = CriarCRUDBean.i().getListFields().get(i);
            field.setShowCadastro((Boolean) jTable1.getValueAt(i, CAD));
            field.setShowAlteracao((Boolean) jTable1.getValueAt(i, ALT));
            field.setShowGrid((Boolean) jTable1.getValueAt(i, GRID));
            field.setShowFiltro((Boolean) jTable1.getValueAt(i, FILT));
            field.setShowVisualizacao((Boolean) jTable1.getValueAt(i, VIEW));
            field.setTipo((String) jTable1.getValueAt(i, TIPO));
            field.setNome(String.valueOf(jTable1.getValueAt(i, VAR)));
            field.setComponente(String.valueOf(jTable1.getValueAt(i, COMP)));
            field.setLabel(String.valueOf(jTable1.getValueAt(i, LABEL)));
            field.setLocal((Boolean) jTable1.getValueAt(i, LOCAL));
            //verifica se o tipo começa com letra maiuscula
            if (Character.isUpperCase(field.getTipo().charAt(0))) {
                //percorre a lista de arquivos beans
                for (File fileBean : dirBeans.listFiles()) {
                    //verifica se o tipo é um novo bean (objeto)
                    if (fileBean.getName().replace(".java", "").equals(field.getTipo())) {
                        field.setBean(true);
                        try {
                            //faz a leitura dos atributos da classe (Bean) e retorna a lista de campos (fields)
                            List<Field> listField = GerarCrud.getFieldsBean(fileBean);
                            if (next) {
                                //se o campo for combobox ou radiobox, selecionar id e label
                                if (field.getComponente().equals(Component.COMBOBOX) || field.getComponente().equals(Component.RADIOBOX)) {
                                    Object[] opcao = new Object[listField.size()];
                                    int j = 0;
                                    for (Field f : listField) {
                                        opcao[j] = f.getNome();
                                        j++;
                                    }
                                    field.setIdCombo((String) JOptionPane.showInputDialog(null, "Escolha o atributo de ID do combobox para o objeto " + field.getTipo(), "Escolha Id", JOptionPane.PLAIN_MESSAGE, null, opcao, opcao[0]));
                                    field.setDescCombo((String) JOptionPane.showInputDialog(null, "Escolha o atributo de DESCRIÇÃO do combobox para o objeto " + field.getTipo(), "Escolha Descrição", JOptionPane.PLAIN_MESSAGE, null, opcao, opcao[0]));
                                }

                                //se for expadir objeto abrir novamente a tabela com os campos do novo bean
                                if (field.getComponente().equals("Expadir Objeto")) {
                                    for (Field f : listField) {
                                        f.setNome(field.getNome().concat(".").concat(f.getNome()));
                                        if (field.isLocal()) {
                                            f.setLocal(true);
                                        }
                                    }
                                    CriarCRUDBean.i().getListFields().addAll(listField);
                                    expandirTabela = true;
                                }
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Erro ao setar valores dos atributos. (bean não esta formatado) \n\n" + ex.getMessage());
                        }
                    }
                }
            }
            //add o campo na lista
            CriarCRUDBean.i().getListFields().set(i, field);
        }
        if (expandirTabela) {
            updateTables();
            throw new AmaterasuException("Expandindo Objeto!", false);
        }
    }

    private void updateTables() {
        JComboBox cb = new JComboBox();
        for (String cc : Component.getComponent()) {
            cb.addItem(cc);
        }
        Table table = new Table();
        table.addColumn("Cadastro", Boolean.class, CriarCRUDBean.i().isModoCadastro());
        table.addColumn("Altera", Boolean.class, CriarCRUDBean.i().isModoAlteracao());
        table.addColumn("Grid", Boolean.class, CriarCRUDBean.i().isModoGrid());
        table.addColumn("Filtro", Boolean.class, CriarCRUDBean.i().isModoFiltro());
        table.addColumn("Visualiza", Boolean.class, CriarCRUDBean.i().isModoVisualizacao());
        table.addColumn("Tipo", String.class, false);
        table.addColumn("Variável", String.class, false);
        table.addColumn("Componente", JComboBox.class, cb, true);
        table.addColumn("Label", String.class, true);
        table.addColumn("Local", Boolean.class, false);

        for (Field f : CriarCRUDBean.i().getListFields()) {
            if (f.getComponente() != null && !f.getComponente().equals("Expadir Objeto")) {
                table.addLineValue(CriarCRUDBean.i().isModoCadastro() ? f.isShowCadastro() : false, CriarCRUDBean.i().isModoAlteracao() ? f.isShowAlteracao() : false, CriarCRUDBean.i().isModoGrid() ? f.isShowGrid() : false, CriarCRUDBean.i().isModoFiltro() ? f.isShowFiltro() : false, CriarCRUDBean.i().isModoVisualizacao() ? f.isShowVisualizacao() : false, f.getTipo(), f.getNome(), f.getComponente(), f.getLabel(), f.isLocal());
            } else if (f.getComponente() == null) {
                table.addLineValue(CriarCRUDBean.i().isModoCadastro() ? f.isShowCadastro() : false, CriarCRUDBean.i().isModoAlteracao() ? f.isShowAlteracao() : false, CriarCRUDBean.i().isModoGrid() ? f.isShowGrid() : false, CriarCRUDBean.i().isModoFiltro() ? f.isShowFiltro() : false, CriarCRUDBean.i().isModoVisualizacao() ? f.isShowVisualizacao() : false, f.getTipo(), f.getNome(), f.getComponente(), f.getLabel(), f.isLocal());
            }
        }
        table.createTable(jTable1);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(8);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(8);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(8);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(8);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(8);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(5);
    }

    private void addField() {
        Field f = new Field();
        f.setLocal(true);
        f.setNome(jTFVariavel.getText());
        f.setTipo(jTFTipo.getText());
        f.setBean(verificaIsBean(f));
        if (f.getTipo().equals("Boolean") || f.getTipo().equals("boolean")) {
            f.setComponente(Component.CHECKBOX);
        } else if (f.getTipo().equals("Date")) {
            f.setComponente(Component.DATA);
        } else {
            f.setComponente(Component.INPUT);
        }
        f.setLabel(Format.maiuscula1(f.getNome()));
        CriarCRUDBean.i().getListFields().add(f);
    }

    private boolean verificaIsBean(Field field) {
        File dirBeans = new File(CriarCRUDBean.i().getCaminhoClasseBean()).getParentFile();
        //verifica se o tipo começa com letra maiuscula
        if (Character.isUpperCase(field.getTipo().charAt(0))) {
            //percorre a lista de arquivos beans
            for (File fileBean : dirBeans.listFiles()) {
                //verifica se o tipo é um novo bean (objeto)
                if (fileBean.getName().replace(".java", "").equals(field.getTipo())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void atualizaPainel() {
        updateTables();
        jCkAltera.setEnabled(CriarCRUDBean.i().isModoAlteracao());
        jCkCadastro.setEnabled(CriarCRUDBean.i().isModoCadastro());
        jCkFiltro.setEnabled(CriarCRUDBean.i().isModoFiltro());
        jCkGrid.setEnabled(CriarCRUDBean.i().isModoGrid());
        jCkVisualiza.setEnabled(CriarCRUDBean.i().isModoVisualizacao());
    }
}
