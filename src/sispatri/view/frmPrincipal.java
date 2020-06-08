package sispatri.view;

import sispatri.model.Usuario;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.internal.SessionImpl;
import sispatri.config.HibernateUtil;
import static sispatri.config.HibernateUtil.getSession;

/**
 * Tela Principal (Menu).
 * @author augusto
 */
public class frmPrincipal extends javax.swing.JFrame {
    
    Usuario usuarioAtual = null;

    public frmPrincipal(Usuario usuario) {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        usuarioAtual = usuario;

        lblUsuarioLogado.setText(lblUsuarioLogado.getText() + (usuario != null ? usuario.getNome() : "Sem usuário logado"));
    }

    /**
     * Método responsável por abrir e fazer os devidos ajustes na tela.
     * @param pInternalFrame - Tela do tipo JInternalFrame
     */
    public void abrirTela(JInternalFrame pInternalFrame) {
        dkpSistema.add(pInternalFrame);
        setPosition(pInternalFrame);
        pInternalFrame.setVisible(true);
    }
    
    /**
     * Método responsável por centralizar a tela dentro do Principal.
     * @param pInternalFrame - Tela do tipo JInternalFrame
     */
    public static void setPosition(JInternalFrame pInternalFrame) {
        Dimension lDimension = pInternalFrame.getDesktopPane().getSize();
        pInternalFrame.setLocation((lDimension.width - pInternalFrame.getSize().width) / 2, (lDimension.height - pInternalFrame.getSize().height) / 2);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dkpSistema = dkpSistema = new javax.swing.JDesktopPane()
        {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Double width = screenSize.getWidth();
            Double height = screenSize.getHeight();
            Image img = new ImageIcon(getClass().getResource("/sispatri/images/background_desktoppane.png")).getImage();

            Image newimage = img.getScaledInstance(width.intValue(), height.intValue(), Image.SCALE_SMOOTH);

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(newimage, 0, 0, this);
            }
        };
        lblUsuarioLogado = new javax.swing.JLabel();
        mnbPrincipal = new javax.swing.JMenuBar();
        mnuCadastro = new javax.swing.JMenu();
        mniCategoria = new javax.swing.JMenuItem();
        mniCentroDeCusto = new javax.swing.JMenuItem();
        mniUsuario = new javax.swing.JMenuItem();
        mniPatrimonio = new javax.swing.JMenuItem();
        mnuDepreciacao = new javax.swing.JMenu();
        mnuRelatorio = new javax.swing.JMenu();
        mniReportDepreciacaoGeral = new javax.swing.JMenuItem();
        mniReportDepreciacaoCategoria = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SisPatri - Sistema de Controle de Patrimônio");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        dkpSistema.setBackground(new java.awt.Color(255, 255, 255));

        lblUsuarioLogado.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblUsuarioLogado.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioLogado.setText("Usuário: ");

        dkpSistema.setLayer(lblUsuarioLogado, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dkpSistemaLayout = new javax.swing.GroupLayout(dkpSistema);
        dkpSistema.setLayout(dkpSistemaLayout);
        dkpSistemaLayout.setHorizontalGroup(
            dkpSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dkpSistemaLayout.createSequentialGroup()
                .addContainerGap(619, Short.MAX_VALUE)
                .addComponent(lblUsuarioLogado)
                .addContainerGap())
        );
        dkpSistemaLayout.setVerticalGroup(
            dkpSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dkpSistemaLayout.createSequentialGroup()
                .addContainerGap(389, Short.MAX_VALUE)
                .addComponent(lblUsuarioLogado)
                .addContainerGap())
        );

        mnbPrincipal.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        mnuCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sispatri/images/add-file.png"))); // NOI18N
        mnuCadastro.setText("Cadastros");
        mnuCadastro.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        mniCategoria.setText("Categoria");
        mniCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCategoriaActionPerformed(evt);
            }
        });
        mnuCadastro.add(mniCategoria);

        mniCentroDeCusto.setText("Centro de Custo");
        mniCentroDeCusto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCentroDeCustoActionPerformed(evt);
            }
        });
        mnuCadastro.add(mniCentroDeCusto);

        mniUsuario.setText("Usuário");
        mniUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniUsuarioActionPerformed(evt);
            }
        });
        mnuCadastro.add(mniUsuario);

        mniPatrimonio.setText("Patrimônio");
        mniPatrimonio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniPatrimonioActionPerformed(evt);
            }
        });
        mnuCadastro.add(mniPatrimonio);

        mnbPrincipal.add(mnuCadastro);

        mnuDepreciacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sispatri/images/financeiro.png"))); // NOI18N
        mnuDepreciacao.setText("Depreciação");
        mnuDepreciacao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mnuDepreciacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuDepreciacaoMouseClicked(evt);
            }
        });
        mnbPrincipal.add(mnuDepreciacao);

        mnuRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sispatri/images/report.png"))); // NOI18N
        mnuRelatorio.setText("Relatórios");
        mnuRelatorio.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        mniReportDepreciacaoGeral.setText("Gerar relatório depreciação geral");
        mniReportDepreciacaoGeral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniReportDepreciacaoGeralActionPerformed(evt);
            }
        });
        mnuRelatorio.add(mniReportDepreciacaoGeral);

        mniReportDepreciacaoCategoria.setText("Gerar relatório depreciação por categoria");
        mniReportDepreciacaoCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniReportDepreciacaoCategoriaActionPerformed(evt);
            }
        });
        mnuRelatorio.add(mniReportDepreciacaoCategoria);

        mnbPrincipal.add(mnuRelatorio);

        setJMenuBar(mnbPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dkpSistema, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dkpSistema, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mniCentroDeCustoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCentroDeCustoActionPerformed
        abrirTela(new frmCentroDeCusto());
    }//GEN-LAST:event_mniCentroDeCustoActionPerformed

    private void mniCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCategoriaActionPerformed
        abrirTela(new frmCategoria());
    }//GEN-LAST:event_mniCategoriaActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void mniUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniUsuarioActionPerformed
        abrirTela(new frmUsuario());
    }//GEN-LAST:event_mniUsuarioActionPerformed

    private void mniPatrimonioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniPatrimonioActionPerformed
        abrirTela(new frmPatrimonio());
    }//GEN-LAST:event_mniPatrimonioActionPerformed

    private void mnuDepreciacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuDepreciacaoMouseClicked
        abrirTela(new frmDepreciacao(usuarioAtual));
    }//GEN-LAST:event_mnuDepreciacaoMouseClicked

    private void mniReportDepreciacaoGeralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniReportDepreciacaoGeralActionPerformed
        try {
            JasperReport relatorio = JasperCompileManager.compileReport(getClass().getResourceAsStream("/sispatri/report/relatorio_depreciacao.jrxml"));
            Map parametros = new HashMap();
            parametros.put("subTitulo", "Relatório Depreciação Geral");

            // Executa relatoio
            JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, ((SessionImpl)getSession()).connection());

            // Exibe resultado em video
            JasperViewer.viewReport(impressao, false);

        } catch (Exception e) {
           e.printStackTrace();
        }
    }//GEN-LAST:event_mniReportDepreciacaoGeralActionPerformed

    private void mniReportDepreciacaoCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniReportDepreciacaoCategoriaActionPerformed
        new frmRelatorioCategoria();
    }//GEN-LAST:event_mniReportDepreciacaoCategoriaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane dkpSistema;
    private javax.swing.JLabel lblUsuarioLogado;
    private javax.swing.JMenuBar mnbPrincipal;
    private javax.swing.JMenuItem mniCategoria;
    private javax.swing.JMenuItem mniCentroDeCusto;
    private javax.swing.JMenuItem mniPatrimonio;
    private javax.swing.JMenuItem mniReportDepreciacaoCategoria;
    private javax.swing.JMenuItem mniReportDepreciacaoGeral;
    private javax.swing.JMenuItem mniUsuario;
    private javax.swing.JMenu mnuCadastro;
    private javax.swing.JMenu mnuDepreciacao;
    private javax.swing.JMenu mnuRelatorio;
    // End of variables declaration//GEN-END:variables
}
