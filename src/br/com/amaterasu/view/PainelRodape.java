/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view;

import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.IPainel;
import br.com.amaterasu.util.ObserverAction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maykon
 */
public class PainelRodape extends javax.swing.JPanel {

    private ObserverAction observerAction;
    private int janelaAtual = 0;
    private int quantidadeJanela;
    private List janelaFinalizavel = new ArrayList();
    private IPainel painel;

    public PainelRodape() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBFinalizar = new javax.swing.JButton();
        jBVoltar = new javax.swing.JButton();
        jBProximo = new javax.swing.JButton();
        jBAjuda = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/PainelRodape"); // NOI18N
        jBFinalizar.setText(bundle.getString("FINALIZAR")); // NOI18N
        jBFinalizar.setEnabled(false);
        jBFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFinalizarActionPerformed(evt);
            }
        });

        jBVoltar.setText(bundle.getString("VOLTAR")); // NOI18N
        jBVoltar.setEnabled(false);
        jBVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVoltarActionPerformed(evt);
            }
        });

        jBProximo.setText(bundle.getString("PROXIMO")); // NOI18N
        jBProximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProximoActionPerformed(evt);
            }
        });

        jBAjuda.setText(bundle.getString("AJUDA")); // NOI18N
        jBAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAjudaActionPerformed(evt);
            }
        });

        jBCancelar.setText(bundle.getString("CANCELAR")); // NOI18N
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addComponent(jBVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBProximo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBAjuda, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBVoltar)
                    .addComponent(jBProximo)
                    .addComponent(jBFinalizar)
                    .addComponent(jBCancelar)
                    .addComponent(jBAjuda))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        observerAction.setAction(IConstants.BOTAO_CANCELAR);
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVoltarActionPerformed
        try {
            painel.binding();
            janelaAtual--;
            if (janelaAtual == 0) {
                jBVoltar.setEnabled(false);
            }
            jBProximo.setEnabled(true);
            if (janelaFinalizavel.contains(janelaAtual + 1)) {
                jBFinalizar.setEnabled(true);
            } else {
                jBFinalizar.setEnabled(false);
            }
            observerAction.setAction(IConstants.BOTAO_VOLTAR);
        } catch (AmaterasuException ex) {
            observerAction.setAction(ex.getMessage());
        }
    }//GEN-LAST:event_jBVoltarActionPerformed

    private void jBProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProximoActionPerformed
        try {
            painel.binding();
            janelaAtual++;
            if (janelaAtual == quantidadeJanela) {
                jBProximo.setEnabled(false);
            }
            jBVoltar.setEnabled(true);
            if (janelaFinalizavel.contains(janelaAtual + 1) || janelaAtual == quantidadeJanela) {
                jBFinalizar.setEnabled(true);
            } else {
                jBFinalizar.setEnabled(false);
            }
            observerAction.setAction(IConstants.BOTAO_PROXIMO);
        } catch (AmaterasuException ex) {
            observerAction.setAction(ex.getMessage());
        }
    }//GEN-LAST:event_jBProximoActionPerformed

    private void jBFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFinalizarActionPerformed
        try {
            painel.binding();
            observerAction.setAction(IConstants.BOTAO_FINALIZAR);
        } catch (AmaterasuException ex) {
            observerAction.setAction(ex.getMessage());
        }
    }//GEN-LAST:event_jBFinalizarActionPerformed

    private void jBAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAjudaActionPerformed
        observerAction.setAction(IConstants.BOTAO_AJUDA);
    }//GEN-LAST:event_jBAjudaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAjuda;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBFinalizar;
    private javax.swing.JButton jBProximo;
    private javax.swing.JButton jBVoltar;
    // End of variables declaration//GEN-END:variables

    public void setObserverAction(ObserverAction observerAction) {
        this.observerAction = observerAction;
    }

    /**
     * Informar a quantidade de paineis que terá esta janela. No mínimo 1
     *
     * @param quantidadePagina
     */
    public void setQuantidadeJanela(int quantidadePagina) {
        this.quantidadeJanela = quantidadePagina - 1;
        if (this.quantidadeJanela < 1) {
            jBProximo.setEnabled(false);
            jBFinalizar.setEnabled(true);
        }
    }

    /**
     * Informar quais paineis pode abilitar o botão Finalizar. (inicia em 1)
     *
     * @param pagina
     */
    public void setJanelaFinalizavel(int... pagina) {
        for (int i : pagina) {
            janelaFinalizavel.add(i);
        }
        if (janelaFinalizavel.contains(1)) {
            jBFinalizar.setEnabled(true);
        }
    }

    public int getJanelaAtual() {
        return this.janelaAtual;
    }

    public void setPainel(IPainel painel) {
        this.painel = painel;
    }
}
