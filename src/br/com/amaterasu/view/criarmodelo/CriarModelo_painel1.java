/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.criarmodelo;

import br.com.amaterasu.gerador.ManterTXT;
import br.com.amaterasu.model.CriarModeloBean;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.IPainel;
import br.com.amaterasu.util.NodeJtree;
import br.com.amaterasu.util.PathFramework;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Maykon
 */
public class CriarModelo_painel1 extends javax.swing.JPanel implements IPainel {

    private DefaultMutableTreeNode rootNovoModelo = new DefaultMutableTreeNode();
    private List<NodeJtree> nodeJtreeModelo = new ArrayList<NodeJtree>();
    private List<NodeJtree> nodeJtreeNovo = new ArrayList<NodeJtree>();
    private int idModelo = 0;
    private int idNovo = 0;

    public CriarModelo_painel1() {
        initComponents();
        montarArvoreModeloPadrao();
        jTreeNovoModelo.setModel(new DefaultTreeModel(rootNovoModelo));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreeModeloPadrao = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeNovoModelo = new javax.swing.JTree();
        jLabel2 = new javax.swing.JLabel();
        JTFNovoModelo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        JTFPacote = new javax.swing.JTextField();
        jBNovoPacote = new javax.swing.JButton();
        jBRemoverPacote = new javax.swing.JButton();
        jBAdicionarPacote = new javax.swing.JButton();
        JBAdicionarArquivo = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/CriarModelo_painel1"); // NOI18N
        jLabel1.setText(bundle.getString("MODELO_PADRAO")); // NOI18N

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTreeModeloPadrao.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeModeloPadrao.setDragEnabled(true);
        jScrollPane2.setViewportView(jTreeModeloPadrao);

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Novo modelo");
        jTreeNovoModelo.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeNovoModelo.setDragEnabled(true);
        jTreeNovoModelo.setEditable(true);
        jScrollPane1.setViewportView(jTreeNovoModelo);

        jLabel2.setText(bundle.getString("NOME_MODELO")); // NOI18N

        JTFNovoModelo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JTFNovoModeloKeyReleased(evt);
            }
        });

        jLabel3.setText(bundle.getString("PACOTE")); // NOI18N

        JTFPacote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTFPacoteActionPerformed(evt);
            }
        });

        jBNovoPacote.setText(bundle.getString("ADICIONAR")); // NOI18N
        jBNovoPacote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNovoPacoteActionPerformed(evt);
            }
        });

        jBRemoverPacote.setText(bundle.getString("REMOVER")); // NOI18N
        jBRemoverPacote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemoverPacoteActionPerformed(evt);
            }
        });

        jBAdicionarPacote.setText(">>");
        jBAdicionarPacote.setToolTipText(bundle.getString("ADICIONAR_PACOTE")); // NOI18N
        jBAdicionarPacote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAdicionarPacoteActionPerformed(evt);
            }
        });

        JBAdicionarArquivo.setText(">");
        JBAdicionarArquivo.setToolTipText(bundle.getString("ADICIONAR_ARQUIVO")); // NOI18N
        JBAdicionarArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBAdicionarArquivoActionPerformed(evt);
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBAdicionarPacote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JBAdicionarArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JTFNovoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(JTFPacote, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBNovoPacote)
                    .addComponent(jBRemoverPacote))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(JTFNovoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBAdicionarPacote)
                        .addGap(18, 18, 18)
                        .addComponent(JBAdicionarArquivo))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTFPacote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBNovoPacote)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBRemoverPacote)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void JTFNovoModeloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTFNovoModeloKeyReleased
        rootNovoModelo.setUserObject(JTFNovoModelo.getText());
        jTreeNovoModelo.setModel(preencheTree(nodeJtreeNovo, JTFNovoModelo.getText()));
    }//GEN-LAST:event_JTFNovoModeloKeyReleased

    private void jBNovoPacoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNovoPacoteActionPerformed
        NodeJtree node = new NodeJtree();
        node.setId(++idNovo);
        node.setNome(JTFPacote.getText());
        nodeJtreeNovo.add(node);
        jTreeNovoModelo.setModel(preencheTree(nodeJtreeNovo, JTFNovoModelo.getText()));
    }//GEN-LAST:event_jBNovoPacoteActionPerformed

    private void jBRemoverPacoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemoverPacoteActionPerformed
        if (jTreeNovoModelo.getSelectionCount() > 0) {
            DefaultMutableTreeNode node;
            TreePath[] paths = jTreeNovoModelo.getSelectionPaths();
            for (int i = 0; i < paths.length; i++) {
                node = (DefaultMutableTreeNode) (paths[i].getLastPathComponent());
                if (!node.isRoot()) {
                    for (NodeJtree n : nodeJtreeNovo) {
                        if (n.getNome().equals(node.getUserObject())) {
                            nodeJtreeNovo.remove(n);
                            break;
                        }
                        if (n.getList() != null) {
                            for (String s : n.getList()) {
                                if (s.equals(node.getUserObject())) {
                                    n.getList().remove(s);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            jTreeNovoModelo.setModel(preencheTree(nodeJtreeNovo, JTFNovoModelo.getText()));
        }
    }//GEN-LAST:event_jBRemoverPacoteActionPerformed

    private void jBAdicionarPacoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAdicionarPacoteActionPerformed
        if (jTreeModeloPadrao.getSelectionCount() > 0) {
            DefaultMutableTreeNode node;
            TreePath[] paths = jTreeModeloPadrao.getSelectionPaths();
            for (int i = 0; i < paths.length; i++) {
                node = (DefaultMutableTreeNode) (paths[i].getLastPathComponent());
                if (!node.isRoot() && node.getParent().getParent() == null) {
                    NodeJtree nodeJtree = new NodeJtree();
                    nodeJtree.setId(++idNovo);
                    nodeJtree.setNome(node.getUserObject().toString().substring(node.getUserObject().toString().indexOf("-") + 1));
                    nodeJtreeNovo.add(nodeJtree);
                }
            }
        }
        jTreeNovoModelo.setModel(preencheTree(nodeJtreeNovo, JTFNovoModelo.getText()));
    }//GEN-LAST:event_jBAdicionarPacoteActionPerformed

    private void JBAdicionarArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBAdicionarArquivoActionPerformed
        if (jTreeModeloPadrao.getSelectionCount() > 0 && jTreeNovoModelo.getSelectionCount() > 0) {
            TreePath pathNovo = jTreeNovoModelo.getSelectionPath();
            DefaultMutableTreeNode node;
            DefaultMutableTreeNode nodeNovo;
            TreePath[] paths = jTreeModeloPadrao.getSelectionPaths();
            for (int i = 0; i < paths.length; i++) {
                node = (DefaultMutableTreeNode) (paths[i].getLastPathComponent());
                if (!node.isRoot() && node.getParent().getParent() != null) {
                    nodeNovo = (DefaultMutableTreeNode) pathNovo.getLastPathComponent();
                    for (NodeJtree n : nodeJtreeNovo) {
                        if (n.getNome().equals(nodeNovo.getUserObject().toString())) {
                            if (n.getList() == null) {
                                n.setList(new ArrayList<String>());
                            }
                            n.set_id(n.get_id() + 1);
                            n.getList().add(n.getId() + " " + n.get_id() + "-" + node.getUserObject().toString().substring(node.getUserObject().toString().indexOf("-") + 1));
                            break;
                        }
                    }
                }
            }
        }
        jTreeNovoModelo.setModel(preencheTree(nodeJtreeNovo, JTFNovoModelo.getText()));
    }//GEN-LAST:event_JBAdicionarArquivoActionPerformed

    private void JTFPacoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTFPacoteActionPerformed
        jBAdicionarPacoteActionPerformed(evt);
    }//GEN-LAST:event_JTFPacoteActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBAdicionarArquivo;
    private javax.swing.JTextField JTFNovoModelo;
    private javax.swing.JTextField JTFPacote;
    private javax.swing.JButton jBAdicionarPacote;
    private javax.swing.JButton jBNovoPacote;
    private javax.swing.JButton jBRemoverPacote;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree jTreeModeloPadrao;
    private javax.swing.JTree jTreeNovoModelo;
    // End of variables declaration//GEN-END:variables

    @Override
    public void binding() throws AmaterasuException {
        if (JTFNovoModelo.getText().equals("")) {
            throw new AmaterasuException("Campo Obrigatorio: \n*Nome do Modelo", false);
        }
        CriarModeloBean.i().setNomeModelo(JTFNovoModelo.getText());
        CriarModeloBean.i().setArvore(nodeJtreeNovo);
    }

    private void montarArvoreModeloPadrao() {
        try {
            File filePacotesTxt = new File(PathFramework.pathFrameworkPacotes(IConstants.MODELO_PADRAO));
            File fileArquivosModelo = new File(PathFramework.pathFrameworkModelosProjeto(IConstants.MODELO_PADRAO));
            List<String> list = ManterTXT.readListLine(filePacotesTxt);

            for (String row : list) {
                if (!row.startsWith("#")) {
                    NodeJtree node = new NodeJtree();
                    node.setId(++idModelo);
                    node.setNome(row);
                    node.setList(modelTree(node.getId(), new ArrayList<String>(), fileArquivosModelo, row));
                    nodeJtreeModelo.add(node);
                }
            }
            jTreeModeloPadrao.setModel(preencheTree(nodeJtreeModelo, IConstants.MODELO_PADRAO));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CriarModelo_painel1.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CriarModelo_painel1.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<String> modelTree(int id, List<String> list, File file, String path) throws FileNotFoundException, IOException {
        int i = 0;
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                modelTree(id, list, f, path);
            } else {
                String pathArquivo = ManterTXT.readLine(f, 1);
                if (pathArquivo.equals("") || !pathArquivo.contains("#PATH[")) {
                    continue;
                }
                pathArquivo = pathArquivo.substring(pathArquivo.indexOf("#PATH[") + 6, pathArquivo.indexOf("]"));
                pathArquivo = new File(pathArquivo).getParent().replace("\\", "/");
                if (pathArquivo.equals(path)) {
                    list.add(id + " " + ++i + "-" + f.getName());
                }
            }
        }
        return list;
    }

    private DefaultTreeModel preencheTree(List<NodeJtree> nodeJtree, String rootName) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootName);
        for (NodeJtree n : nodeJtree) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(n.getNome());
            if (n.getList() != null) {
                for (String s : n.getList()) {
                    DefaultMutableTreeNode folha = new DefaultMutableTreeNode(s);
                    node.add(folha);
                }
            }
            root.add(node);
        }

        DefaultTreeModel modelPadrao = new DefaultTreeModel(root);
        return modelPadrao;
    }

    @Override
    public void atualizaPainel() {
    }
}