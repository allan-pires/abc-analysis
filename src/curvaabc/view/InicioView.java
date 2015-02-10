/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.view;

import javax.swing.JFrame;
import curvaabc.view.TabelaABCView;
import java.awt.Window;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author doisl_000
 */
public class InicioView extends javax.swing.JFrame {

    /**
     * Creates new form InicioView
     */
    public InicioView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_gerenciar_itens = new javax.swing.JButton();
        button_relatorio1 = new javax.swing.JButton();
        button_grafico = new javax.swing.JButton();
        button_sair = new javax.swing.JButton();
        icon = new javax.swing.JLabel();
        button_configuracoes = new javax.swing.JButton();
        button_relatorio2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        button_gerenciar_itens.setBackground(new java.awt.Color(241, 241, 241));
        button_gerenciar_itens.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        button_gerenciar_itens.setText("Gerenciar itens");
        button_gerenciar_itens.setBorder(button_relatorio1.getBorder());
        button_gerenciar_itens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_gerenciar_itensMouseClicked(evt);
            }
        });
        button_gerenciar_itens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_gerenciar_itensActionPerformed(evt);
            }
        });

        button_relatorio1.setBackground(new java.awt.Color(241, 241, 241));
        button_relatorio1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        button_relatorio1.setText("Exibir planilha sem criticidade");
        button_relatorio1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_relatorio1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button_relatorio1MousePressed(evt);
            }
        });
        button_relatorio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_relatorio1ActionPerformed(evt);
            }
        });

        button_grafico.setBackground(new java.awt.Color(241, 241, 241));
        button_grafico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        button_grafico.setText("Exibir gráfico");
        button_grafico.setBorder(button_relatorio1.getBorder());
        button_grafico.setPreferredSize(new java.awt.Dimension(203, 34));
        button_grafico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_graficoMouseClicked(evt);
            }
        });
        button_grafico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_graficoActionPerformed(evt);
            }
        });

        button_sair.setBackground(new java.awt.Color(241, 241, 241));
        button_sair.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        button_sair.setText("Sair");
        button_sair.setBorder(button_relatorio1.getBorder());
        button_sair.setPreferredSize(new java.awt.Dimension(61, 34));
        button_sair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_sairMouseClicked(evt);
            }
        });

        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/curvaabc/view/curva_abc_logo.png"))); // NOI18N

        button_configuracoes.setBackground(new java.awt.Color(241, 241, 241));
        button_configuracoes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        button_configuracoes.setText("Configurações de classificação");
        button_configuracoes.setBorder(button_relatorio1.getBorder());
        button_configuracoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_configuracoesMouseClicked(evt);
            }
        });
        button_configuracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_configuracoesActionPerformed(evt);
            }
        });

        button_relatorio2.setBackground(new java.awt.Color(241, 241, 241));
        button_relatorio2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        button_relatorio2.setText("Exibir relatório");
        button_relatorio2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_relatorio2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button_relatorio2MousePressed(evt);
            }
        });
        button_relatorio2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_relatorio2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_relatorio2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_configuracoes, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(icon)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(button_relatorio1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button_gerenciar_itens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button_grafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button_sair, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(icon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_gerenciar_itens, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_configuracoes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_relatorio1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_relatorio2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_grafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_sair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_gerenciar_itensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_gerenciar_itensActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_gerenciar_itensActionPerformed

    private void button_graficoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_graficoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_graficoActionPerformed

    private void button_relatorio1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_relatorio1MouseClicked

        try {
            
            TabelaABCView t = new TabelaABCView();
            Window w = new Window(t);
            t.setVisible(true);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_button_relatorio1MouseClicked

    private void button_gerenciar_itensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_gerenciar_itensMouseClicked
            AddProdutoView t = null;
        try {
            t = new AddProdutoView();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        }
            Window w = new Window(t);
            t.setVisible(true);
    }//GEN-LAST:event_button_gerenciar_itensMouseClicked

    private void button_relatorio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_relatorio1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_relatorio1ActionPerformed

    private void button_relatorio1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_relatorio1MousePressed

    }//GEN-LAST:event_button_relatorio1MousePressed

    private void button_graficoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_graficoMouseClicked
        GraficoABCView v = null;
        try {
            v = new GraficoABCView();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        }
        v.setSize(800, 600);
        v.show();
    }//GEN-LAST:event_button_graficoMouseClicked

    private void button_configuracoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_configuracoesMouseClicked
         ConfigurarClassificacaoView v = null;
        try {
            v = new ConfigurarClassificacaoView();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        }
        v.show();
    }//GEN-LAST:event_button_configuracoesMouseClicked

    private void button_configuracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_configuracoesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_configuracoesActionPerformed

    private void button_relatorio2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_relatorio2MouseClicked
        RelatorioView v = null;
        try {
            v = new RelatorioView();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InicioView.class.getName()).log(Level.SEVERE, null, ex);
        }
        v.setSize(800, 400);
        v.show();
    }//GEN-LAST:event_button_relatorio2MouseClicked

    private void button_relatorio2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_relatorio2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_relatorio2MousePressed

    private void button_relatorio2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_relatorio2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_relatorio2ActionPerformed

    private void button_sairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_sairMouseClicked
        InicioView.this.dispose();
    }//GEN-LAST:event_button_sairMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InicioView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InicioView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InicioView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InicioView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InicioView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_configuracoes;
    private javax.swing.JButton button_gerenciar_itens;
    private javax.swing.JButton button_grafico;
    private javax.swing.JButton button_relatorio1;
    private javax.swing.JButton button_relatorio2;
    private javax.swing.JButton button_sair;
    private javax.swing.JLabel icon;
    // End of variables declaration//GEN-END:variables
}
