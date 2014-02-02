/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view;

import br.com.amaterasu.model.ModelProjeto;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.ObserverAction;
import br.com.amaterasu.view.abrirprojeto.AbrirProjeto;
import br.com.amaterasu.view.criarcrud.CriarCrud;
import br.com.amaterasu.view.criarprojeto.CriarProjeto;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Maykon
 */
public class Principal extends javax.swing.JFrame implements Observer {

    private ObserverAction observerAction;

    public Principal() {
        initComponents();
        jBFecharProjeto.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPPrincipal = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTFNomeProjeto = new javax.swing.JTextField();
        jTFPackage = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTFDataCriacao = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTFCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jCBPentaho = new javax.swing.JCheckBox();
        jCBJasperReport = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jTFDataUltimaAlteracao = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLTecnologias = new javax.swing.JList();
        jCBApachePOI = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jTFNomeCompleto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTFModelo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTFServidor = new javax.swing.JTextField();
        jBAbrirServidor = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTFPathProjeto = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTFPathAmaterasu = new javax.swing.JTextField();
        jBAbrirProjeto = new javax.swing.JButton();
        jBAbrirAmaterasu = new javax.swing.JButton();
        jPFooter = new javax.swing.JPanel();
        jBFecharProjeto = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMNovo = new javax.swing.JMenu();
        jMICriarProjeto = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMIManterPerfil = new javax.swing.JMenuItem();
        jMICadUsuario = new javax.swing.JMenuItem();
        jMCriarRelatorio = new javax.swing.JMenuItem();
        jMICriarCRUD = new javax.swing.JMenuItem();
        jMAbrir = new javax.swing.JMenu();
        jMIAbrirProjeto = new javax.swing.JMenuItem();
        jMConfigurar = new javax.swing.JMenu();
        jMIJboss = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMIParametrizacao = new javax.swing.JMenuItem();
        jMAjuda = new javax.swing.JMenu();
        jMIAjuda = new javax.swing.JMenuItem();
        jMISobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/Geral"); // NOI18N
        setTitle(bundle.getString("NOME_PROJETO")); // NOI18N
        setMinimumSize(new java.awt.Dimension(800, 500));

        jPPrincipal.setVisible(false);

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/Principal"); // NOI18N
        jLabel1.setText(bundle1.getString("NOME_PROJETO")); // NOI18N

        jLabel2.setText(bundle1.getString("PACOTE")); // NOI18N

        jTFNomeProjeto.setEditable(false);

        jTFPackage.setEditable(false);

        jLabel3.setText(bundle1.getString("DATA_CRIACAO")); // NOI18N

        jTFDataCriacao.setEditable(false);

        jLabel4.setText(bundle1.getString("CLIENTE")); // NOI18N

        jTFCliente.setEditable(false);

        jLabel5.setText(bundle1.getString("TECNOLOGIAS")); // NOI18N

        jCBPentaho.setText(bundle1.getString("PENTAHO")); // NOI18N
        jCBPentaho.setEnabled(false);

        jCBJasperReport.setText(bundle1.getString("JASPERREPORT")); // NOI18N
        jCBJasperReport.setEnabled(false);

        jLabel6.setText(bundle1.getString("DATA_ULTIMA_ALTERACAO")); // NOI18N

        jTFDataUltimaAlteracao.setEditable(false);

        jLTecnologias.setEnabled(false);
        jScrollPane1.setViewportView(jLTecnologias);

        jCBApachePOI.setText("Apache POI");
        jCBApachePOI.setEnabled(false);

        jLabel7.setText(bundle1.getString("NOME_COMPLETO")); // NOI18N

        jTFNomeCompleto.setEditable(false);

        jLabel8.setText(bundle1.getString("MODELO")); // NOI18N

        jTFModelo.setEditable(false);

        jLabel9.setText(bundle1.getString("SERVIDOR")); // NOI18N

        jTFServidor.setEditable(false);

        jBAbrirServidor.setText(bundle1.getString("ABRIR")); // NOI18N
        jBAbrirServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbrirServidorActionPerformed(evt);
            }
        });

        jLabel10.setText(bundle1.getString("PROJETO")); // NOI18N

        jTFPathProjeto.setEditable(false);

        jLabel11.setText(bundle1.getString("AMATERASU")); // NOI18N

        jTFPathAmaterasu.setEditable(false);

        jBAbrirProjeto.setText(bundle1.getString("ABRIR")); // NOI18N
        jBAbrirProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbrirProjetoActionPerformed(evt);
            }
        });

        jBAbrirAmaterasu.setText(bundle1.getString("ABRIR")); // NOI18N
        jBAbrirAmaterasu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbrirAmaterasuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPPrincipalLayout = new javax.swing.GroupLayout(jPPrincipal);
        jPPrincipal.setLayout(jPPrincipalLayout);
        jPPrincipalLayout.setHorizontalGroup(
            jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jCBJasperReport)
                    .addComponent(jCBPentaho)
                    .addComponent(jCBApachePOI))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addComponent(jTFNomeProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7))
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addComponent(jTFDataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))))
                .addGap(18, 18, 18)
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTFNomeCompleto)
                        .addGroup(jPPrincipalLayout.createSequentialGroup()
                            .addComponent(jTFDataUltimaAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jTFCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                        .addComponent(jTFPackage)
                        .addComponent(jTFModelo))
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTFServidor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                            .addComponent(jTFPathProjeto, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFPathAmaterasu, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBAbrirServidor)
                            .addComponent(jBAbrirProjeto)
                            .addComponent(jBAbrirAmaterasu)))))
        );
        jPPrincipalLayout.setVerticalGroup(
            jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTFNomeProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jTFNomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFDataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTFDataUltimaAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jCBPentaho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCBJasperReport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCBApachePOI))
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTFPackage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTFModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTFServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBAbrirServidor))
                        .addGap(18, 18, 18)
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTFPathProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBAbrirProjeto))
                        .addGap(18, 18, 18)
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTFPathAmaterasu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jBAbrirAmaterasu))
                            .addComponent(jLabel11)))
                    .addComponent(jScrollPane1))
                .addGap(72, 72, 72))
        );

        jPFooter.setBackground(new java.awt.Color(255, 255, 255));

        jBFecharProjeto.setText("Fechar Projeto");
        jBFecharProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFecharProjetoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPFooterLayout = new javax.swing.GroupLayout(jPFooter);
        jPFooter.setLayout(jPFooterLayout);
        jPFooterLayout.setHorizontalGroup(
            jPFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPFooterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBFecharProjeto)
                .addContainerGap())
        );
        jPFooterLayout.setVerticalGroup(
            jPFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBFecharProjeto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMNovo.setText(bundle1.getString("NOVO")); // NOI18N

        jMICriarProjeto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMICriarProjeto.setText(bundle1.getString("CRIAR_PROJETO")); // NOI18N
        jMICriarProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMICriarProjetoActionPerformed(evt);
            }
        });
        jMNovo.add(jMICriarProjeto);
        jMNovo.add(jSeparator1);

        jMIManterPerfil.setText(bundle1.getString("MANTER_PERFIS")); // NOI18N
        jMIManterPerfil.setEnabled(false);
        jMNovo.add(jMIManterPerfil);

        jMICadUsuario.setText(bundle1.getString("CADASTRO_USUARIO")); // NOI18N
        jMICadUsuario.setEnabled(false);
        jMNovo.add(jMICadUsuario);

        jMCriarRelatorio.setText("Criar Relatório");
        jMCriarRelatorio.setEnabled(false);
        jMCriarRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMCriarRelatorioActionPerformed(evt);
            }
        });
        jMNovo.add(jMCriarRelatorio);

        jMICriarCRUD.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMICriarCRUD.setText(bundle1.getString("CRIAR_CRUD")); // NOI18N
        jMICriarCRUD.setEnabled(false);
        jMICriarCRUD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMICriarCRUDActionPerformed(evt);
            }
        });
        jMNovo.add(jMICriarCRUD);

        jMenuBar1.add(jMNovo);

        jMAbrir.setText(bundle1.getString("ABRIR")); // NOI18N

        jMIAbrirProjeto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMIAbrirProjeto.setText(bundle1.getString("ABRIR_PROJETO")); // NOI18N
        jMIAbrirProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIAbrirProjetoActionPerformed(evt);
            }
        });
        jMAbrir.add(jMIAbrirProjeto);

        jMenuBar1.add(jMAbrir);

        jMConfigurar.setText(bundle1.getString("CONFIGURAR")); // NOI18N

        jMIJboss.setText(bundle1.getString("SERVIDOR")); // NOI18N
        jMConfigurar.add(jMIJboss);

        jMenuItem1.setText(bundle1.getString("DATA_SOURCE")); // NOI18N
        jMConfigurar.add(jMenuItem1);

        jMenuItem2.setText(bundle1.getString("LOGIN_CONFIG")); // NOI18N
        jMConfigurar.add(jMenuItem2);

        jMIParametrizacao.setText(bundle1.getString("PARAMETRIZACAO")); // NOI18N
        jMConfigurar.add(jMIParametrizacao);

        jMenuBar1.add(jMConfigurar);

        jMAjuda.setText(bundle1.getString("AJUDA")); // NOI18N

        jMIAjuda.setText("Ajuda");
        jMAjuda.add(jMIAjuda);

        jMISobre.setText(bundle1.getString("SOBRE")); // NOI18N
        jMAjuda.add(jMISobre);

        jMenuBar1.add(jMAjuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPFooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPFooter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMICriarCRUDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMICriarCRUDActionPerformed
        new CriarCrud().setVisible(true);
    }//GEN-LAST:event_jMICriarCRUDActionPerformed

    private void jMICriarProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMICriarProjetoActionPerformed
        new CriarProjeto().setVisible(true);
    }//GEN-LAST:event_jMICriarProjetoActionPerformed

    private void jMIAbrirProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIAbrirProjetoActionPerformed
        AbrirProjeto abrirProjeto = new AbrirProjeto();
        observerAction = new ObserverAction();
        observerAction.addObserver(this);
        abrirProjeto.setObserverAction(observerAction);
        abrirProjeto.setVisible(true);
    }//GEN-LAST:event_jMIAbrirProjetoActionPerformed

    private void jBAbrirServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrirServidorActionPerformed
        try {
            String path = ModelProjeto.i().getCaminhoServidor();
            if (!path.equals("")) {
                Runtime.getRuntime().exec("explorer " + path);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir diretorio do servidor.\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jBAbrirServidorActionPerformed

    private void jBAbrirProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrirProjetoActionPerformed
        try {
            String path = ModelProjeto.i().getCaminho();
            if (!path.equals("")) {
                Runtime.getRuntime().exec("explorer " + path);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir diretorio do Projeto.\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jBAbrirProjetoActionPerformed

    private void jBAbrirAmaterasuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrirAmaterasuActionPerformed
        try {
            String path = ModelProjeto.i().getCaminhoAmaterasu();
            if (!path.equals("")) {
                Runtime.getRuntime().exec("explorer " + path);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir diretorio do Projeto Amaterasu.\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jBAbrirAmaterasuActionPerformed

    private void jBFecharProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFecharProjetoActionPerformed
        jMICriarCRUD.setEnabled(false);
        jMICadUsuario.setEnabled(false);
        jMIManterPerfil.setEnabled(false);
        jMCriarRelatorio.setEnabled(false);
        limparInfoProjeto();
        jPPrincipal.setVisible(false);
        jBFecharProjeto.setVisible(false);
        ModelProjeto.fechar();
    }//GEN-LAST:event_jBFecharProjetoActionPerformed

    private void jMCriarRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMCriarRelatorioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMCriarRelatorioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbrirAmaterasu;
    private javax.swing.JButton jBAbrirProjeto;
    private javax.swing.JButton jBAbrirServidor;
    private javax.swing.JButton jBFecharProjeto;
    private javax.swing.JCheckBox jCBApachePOI;
    private javax.swing.JCheckBox jCBJasperReport;
    private javax.swing.JCheckBox jCBPentaho;
    private javax.swing.JList jLTecnologias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMAbrir;
    private javax.swing.JMenu jMAjuda;
    private javax.swing.JMenu jMConfigurar;
    private javax.swing.JMenuItem jMCriarRelatorio;
    private javax.swing.JMenuItem jMIAbrirProjeto;
    private javax.swing.JMenuItem jMIAjuda;
    private javax.swing.JMenuItem jMICadUsuario;
    private javax.swing.JMenuItem jMICriarCRUD;
    private javax.swing.JMenuItem jMICriarProjeto;
    private javax.swing.JMenuItem jMIJboss;
    private javax.swing.JMenuItem jMIManterPerfil;
    private javax.swing.JMenuItem jMIParametrizacao;
    private javax.swing.JMenuItem jMISobre;
    private javax.swing.JMenu jMNovo;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPFooter;
    private javax.swing.JPanel jPPrincipal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextField jTFCliente;
    private javax.swing.JTextField jTFDataCriacao;
    private javax.swing.JTextField jTFDataUltimaAlteracao;
    private javax.swing.JTextField jTFModelo;
    private javax.swing.JTextField jTFNomeCompleto;
    private javax.swing.JTextField jTFNomeProjeto;
    private javax.swing.JTextField jTFPackage;
    private javax.swing.JTextField jTFPathAmaterasu;
    private javax.swing.JTextField jTFPathProjeto;
    private javax.swing.JTextField jTFServidor;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals(IConstants.OPEN_PROJECT)) {
            jMICriarCRUD.setEnabled(true);
            jMICadUsuario.setEnabled(true);
            jMCriarRelatorio.setEnabled(true);
            jMIManterPerfil.setEnabled(true);
            preencherInfoProjeto();
            jPPrincipal.setVisible(true);
            jBFecharProjeto.setVisible(true);
        }
    }

    private void preencherInfoProjeto() {
        jTFNomeProjeto.setText(ModelProjeto.i().getNomeProjeto());
        jTFCliente.setText(ModelProjeto.i().getCliente());
        jTFDataCriacao.setText(ModelProjeto.i().getDataCriacao());
        jTFDataUltimaAlteracao.setText(ModelProjeto.i().getDataUltimaAlteracao());
        jTFPackage.setText(ModelProjeto.i().getPacotePadrao());
        jCBApachePOI.setSelected(ModelProjeto.i().isApachePOI());
        jCBJasperReport.setSelected(ModelProjeto.i().isJasperReport());
        jCBPentaho.setSelected(ModelProjeto.i().isPentaho());
        DefaultListModel model = new DefaultListModel();
        for (String s : ModelProjeto.i().getTecnologia()) {
            model.addElement(s);
        }
        jLTecnologias.setModel(model);
        jTFNomeCompleto.setText(ModelProjeto.i().getNomeCompleto());
        jTFModelo.setText(ModelProjeto.i().getModelo());
        jTFServidor.setText(ModelProjeto.i().getServidor());
        jTFPathProjeto.setText(ModelProjeto.i().getCaminho());
        jTFPathAmaterasu.setText(ModelProjeto.i().getCaminhoAmaterasu());
    }

    private void limparInfoProjeto() {
        jTFNomeProjeto.setText("");
        jTFCliente.setText("");
        jTFDataCriacao.setText("");
        jTFDataUltimaAlteracao.setText("");
        jTFPackage.setText("");
        jCBApachePOI.setSelected(false);
        jCBJasperReport.setSelected(false);
        jCBPentaho.setSelected(false);
        DefaultListModel model = new DefaultListModel();
        jLTecnologias.setModel(model);
        jTFNomeCompleto.setText("");
        jTFModelo.setText("");
        jTFServidor.setText("");
        jTFPathProjeto.setText("");
        jTFPathAmaterasu.setText("");
    }
}
