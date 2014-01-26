/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.view.criarcrud;

import br.com.amaterasu.model.CriarCRUDBean;
import br.com.amaterasu.util.AmaterasuException;
import br.com.amaterasu.util.IConstants;
import br.com.amaterasu.util.IPainel;
import br.com.amaterasu.util.IPainelRodape;
import br.com.amaterasu.util.ObserverAction;
import br.com.amaterasu.view.text.Txt;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Maykon
 */
public class CriarCrud extends javax.swing.JFrame implements IPainelRodape {

    private ObserverAction observerAction;
    private List<IPainel> paineis = new ArrayList<IPainel>();

    public CriarCrud() {
        initComponents();
        //------------configuração de cabeçalho e rodapé-------------------//
        painelCabecalho1.setTitulo(Txt.getTxt(this.getClass().getSimpleName(), "TITULO_CABECALHO"));
        painelCabecalho1.setDescricao(Txt.getTxt(this.getClass().getSimpleName(), "DESCRICAO_CABECALHO"));
        //------------configuração de lista de paineis-------------------//
        paineis.add(new CriarCrud_painel1());
        paineis.add(new CriarCrud_painel2());
        paineis.add(new CriarCrud_painel3());
        //------------configuração de ações do rodapé-------------------//
        observerAction = new ObserverAction();
        observerAction.addObserver(this);
        painelRodape1.setObserverAction(observerAction);
        painelRodape1.setQuantidadeJanela(paineis.size());
        painelRodape1.setPainel(paineis.get(painelRodape1.getJanelaAtual()));
//        painelRodape1.setJanelaFinalizavel(2, 3);
        //------------apresentando o primeiro painel-------------------//
        jPConteudo.setLayout(new BorderLayout());
        jPConteudo.add((JPanel) paineis.get(painelRodape1.getJanelaAtual()), BorderLayout.NORTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelRodape1 = new br.com.amaterasu.view.PainelRodape();
        painelCabecalho1 = new br.com.amaterasu.view.PainelCabecalho();
        jPConteudo = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/amaterasu/view/text/Geral"); // NOI18N
        setTitle(bundle.getString("NOME_PROJETO")); // NOI18N

        javax.swing.GroupLayout jPConteudoLayout = new javax.swing.GroupLayout(jPConteudo);
        jPConteudo.setLayout(jPConteudoLayout);
        jPConteudoLayout.setHorizontalGroup(
            jPConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPConteudoLayout.setVerticalGroup(
            jPConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelRodape1, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
            .addComponent(painelCabecalho1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPConteudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(painelCabecalho1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPConteudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelRodape1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPConteudo;
    private br.com.amaterasu.view.PainelCabecalho painelCabecalho1;
    private br.com.amaterasu.view.PainelRodape painelRodape1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void doVoltar() {
        atualizaJanela();
    }

    @Override
    public void doProximo() {
        atualizaJanela();
    }

    @Override
    public void doFinalizar() {
        try {
            painelRodape1.setPainel(paineis.get(painelRodape1.getJanelaAtual()));
            CriarCRUDBean.i().save();
            JOptionPane.showMessageDialog(null, "CRUD criado com sucesso!");
            dispose();
        } catch (AmaterasuException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public void doCancelar() {
        this.dispose();
    }

    @Override
    public void doAjuda() {
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals(IConstants.BOTAO_VOLTAR)) {
            doVoltar();
        } else if (arg.equals(IConstants.BOTAO_PROXIMO)) {
            doProximo();
        } else if (arg.equals(IConstants.BOTAO_FINALIZAR)) {
            doFinalizar();
        } else if (arg.equals(IConstants.BOTAO_CANCELAR)) {
            doCancelar();
        } else if (arg.equals(IConstants.BOTAO_AJUDA)) {
            doAjuda();
        } else {
            JOptionPane.showMessageDialog(null, arg);
        }
    }

    public void atualizaJanela() {
        paineis.get(painelRodape1.getJanelaAtual()).atualizaPainel();
        jPConteudo.removeAll();
        jPConteudo.add((JPanel) paineis.get(painelRodape1.getJanelaAtual()), BorderLayout.NORTH);
        jPConteudo.repaint();
        jPConteudo.validate();
        painelRodape1.setPainel(paineis.get(painelRodape1.getJanelaAtual()));
    }
}
