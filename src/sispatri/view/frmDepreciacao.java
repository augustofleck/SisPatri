package sispatri.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import sispatri.controller.CategoriaController;
import sispatri.controller.CentroDeCustoController;
import sispatri.controller.DepreciacaoProcessamentoController;
import sispatri.controller.PatrimonioController;
import sispatri.model.Categoria;
import sispatri.model.CentroDeCusto;
import sispatri.model.DepreciacaoProcessamento;
import sispatri.model.Patrimonio;
import sispatri.model.Usuario;
import sispatri.repository.DepreciacaoProcessamentoRepository;

/**
 * Tela da depreciação.
 * @author augusto
 */
public class frmDepreciacao extends javax.swing.JInternalFrame {

    DepreciacaoProcessamentoController depreciacaoProcessamentoController;
    PatrimonioController patrimonioController;
    CategoriaController categoriaController;
    CentroDeCustoController centroDeCustoController;
    
    Usuario usuarioAtual = null;
    
    public frmDepreciacao() {
        initFrmDepreciacao();
    }
    
    public frmDepreciacao(Usuario u) {
        initFrmDepreciacao();
        usuarioAtual = u;
    }
    
    /**
     * Método usado para iniciar a tela de depreciação.
     */
    private void initFrmDepreciacao() {
        initComponents();
        depreciacaoProcessamentoController = new DepreciacaoProcessamentoController();
        patrimonioController = new PatrimonioController();
        categoriaController = new CategoriaController();
        centroDeCustoController = new CentroDeCustoController();
        
        addVariaveisComboBoxTela(cmbCategoria, "CATEGORIA");        
        addVariaveisComboBoxTela(cmbCentroDeCusto, "CENTRODECUSTO");
                
        ajusteListaPatrimonio();
    }
    
    /**
     * Realiza as configurações inicias da tabela de Patrimonio como permitir
     * seleção de múltiplos campos.
     */
    private void ajusteListaPatrimonio() {
        tblPatrimonio.setRowSelectionAllowed(true);
        tblPatrimonio.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        tblPatrimonio.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                btnDepreciacaoSelecionados.setEnabled(true);
            }
        });
    }
    
    /**
     * Adiciona os valores do banco de dados no combo.
     * @param comboBox - JComboBox.
     * @param combo - "CATEGORIA" ou "CENTRODECUSTO".
     */
    private void addVariaveisComboBoxTela(javax.swing.JComboBox comboBox, String combo) {
        switch (combo) {
            case "CATEGORIA":
                List<Categoria> list = categoriaController.getAlAtivos();
                if (!list.isEmpty()) {
                    List<String> listString = new ArrayList<>();
                    listString.add("Sem filtro");
                    for (Categoria a : list) {
                        listString.add(a.getId() + " " + a.getDescricao()+ " - Depre:" + a.getPercetualDepreciacaoMes());
                    }
                    comboBox.setModel(new DefaultComboBoxModel(listString.toArray()));
                    String split[] = comboBox.getSelectedItem().toString().split(" ");
                }
                break;
            case "CENTRODECUSTO":
                List<CentroDeCusto> list1 = centroDeCustoController.getAllAtivos();
                if (!list1.isEmpty()) {
                    List<String> listString = new ArrayList<>();
                    listString.add("Sem filtro");
                    for (CentroDeCusto a : list1) {
                        listString.add(a.getId() + " " + a.getNome());
                    }
                    comboBox.setModel(new DefaultComboBoxModel(listString.toArray()));
                    String split[] = comboBox.getSelectedItem().toString().split(" ");
                }
                break;
        }
    }
    
    /**
     * Atualiza a lista de Patrimonios com base nos filtros selecionas.
     */
    private void atualizaLista() {
        String strCat = cmbCategoria.getSelectedItem().toString();
        String strCentro = cmbCentroDeCusto.getSelectedItem().toString();

        Categoria categoria = null;
        CentroDeCusto centroDeCusto = null;
        
        if (!strCat.equals("") && !strCat.equals("Sem filtro")) {
            categoria = categoriaController.getReadId(Integer.parseInt(strCat.split(" ")[0]));
        }
        if (!strCentro.equals("") && !strCentro.equals("Sem filtro")) {
            centroDeCusto = centroDeCustoController.getReadId(Integer.parseInt(strCentro.split(" ")[0]));
        }
        
        depreciacaoProcessamentoController.popularTabelaDepreciacao(tblPatrimonio, patrimonioController.readByCategoriaAndCentroDeCusto(categoria, centroDeCusto));
        
        if (tblPatrimonio.getModel().getRowCount() > 0) {
                    btnDepreciacao.setEnabled(true);
                    return;
                }
        btnDepreciacao.setEnabled(false);
    }
    
    /**
     * Gera a depreciação dos itens.
     * @param tipo - TODOS: Gera de todos os itens filtrados.
     *               SELECIONADOS: Gera de todos os itens filtrados selecionados.
     */
    private void gerarDepreciacao(String tipo) {
        Object[] options = {"Sim", "Não"};
        int escolha = JOptionPane.showOptionDialog(null, "Você tem certeza que gostaria de gerar depreciação para todos os patrimônios na lista?", "Gerando depreciação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (escolha == 0) {
            int rows;
            
            if (tipo.equals("TODOS")) {
                rows = tblPatrimonio.getRowCount();
            } else {
                rows = tblPatrimonio.getSelectedRows().length;
            }
            
            List<DepreciacaoProcessamento> depreciacaoProcessamento = new ArrayList<DepreciacaoProcessamento>();
            Date now = new Date();
            for (int i = 0; i < rows; i++) {
                int index = i;
                if (!tipo.equals("TODOS")) {
                    index = tblPatrimonio.getSelectedRows()[i];
                }
                
                Patrimonio p = patrimonioController.getReadId(Integer.parseInt(tblPatrimonio.getModel().getValueAt(index, 0).toString()));
                if (p == null)
                    continue;
                if (depreciacaoProcessamentoController.readByPatrimonioAndDateIsNow(p) != null) {
                    continue;
                    // Já foi gerado depreciação para esse mês
                }
                
                DepreciacaoProcessamento dp = new DepreciacaoProcessamento();
                dp.setDataDepreciacao(now);
                dp.setPatrimonio(p);
                dp.setUsuario(usuarioAtual);
                
                depreciacaoProcessamento.add(dp);
                
                // Atualiza o valor do patrimônio
                p.setValorAtualizado(p.calcularDepreciacao());
                patrimonioController.save(p);
            }
            
            depreciacaoProcessamentoController.saveAll(depreciacaoProcessamento);

            JOptionPane.showMessageDialog(this, "Geração de depreciação concluída com sucesso!");
            atualizaLista();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbpPermissao = new javax.swing.JTabbedPane();
        pnlCadastro = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        scpBotoes = new javax.swing.JScrollPane();
        tblPatrimonio = new javax.swing.JTable();
        lblTela = new javax.swing.JLabel();
        cmbCategoria = new javax.swing.JComboBox<>();
        cmbCentroDeCusto = new javax.swing.JComboBox<>();
        lblTela1 = new javax.swing.JLabel();
        btnPesquisa = new javax.swing.JButton();
        pnlHeader = new javax.swing.JPanel();
        btnDepreciacao = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        btnDepreciacaoSelecionados = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tbpPermissao.setBackground(new java.awt.Color(255, 255, 255));
        tbpPermissao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbpPermissao.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tbpPermissaoStateChanged(evt);
            }
        });

        pnlCadastro.setBackground(new java.awt.Color(255, 255, 255));
        pnlCadastro.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Cadastro de Depreciação ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Patrimônios ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblPatrimonio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        tblPatrimonio.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scpBotoes.setViewportView(tblPatrimonio);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scpBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(scpBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        lblTela.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTela.setForeground(new java.awt.Color(102, 102, 102));
        lblTela.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTela.setText("<html>Categoria<font color='red'><b>*</b></font>:</html>");

        cmbCategoria.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        cmbCentroDeCusto.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        lblTela1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTela1.setForeground(new java.awt.Color(102, 102, 102));
        lblTela1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTela1.setText("<html>Centro de custo<font color='red'><b>*</b></font>:</html>");

        btnPesquisa.setBackground(new java.awt.Color(12, 91, 160));
        btnPesquisa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPesquisa.setForeground(new java.awt.Color(255, 255, 255));
        btnPesquisa.setText("Filtrar");
        btnPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCadastroLayout = new javax.swing.GroupLayout(pnlCadastro);
        pnlCadastro.setLayout(pnlCadastroLayout);
        pnlCadastroLayout.setHorizontalGroup(
            pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCadastroLayout.createSequentialGroup()
                        .addComponent(lblTela, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCadastroLayout.createSequentialGroup()
                        .addComponent(lblTela1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbCentroDeCusto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCadastroLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlCadastroLayout.setVerticalGroup(
            pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTela1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCentroDeCusto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnPesquisa)
                .addGap(4, 4, 4)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbpPermissao.addTab("Adicionar", pnlCadastro);

        btnDepreciacao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDepreciacao.setForeground(new java.awt.Color(12, 91, 160));
        btnDepreciacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sispatri/images/payment.png"))); // NOI18N
        btnDepreciacao.setText("Gerar depreciação");
        btnDepreciacao.setEnabled(false);
        btnDepreciacao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDepreciacao.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDepreciacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepreciacaoActionPerformed(evt);
            }
        });

        btnFechar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnFechar.setForeground(new java.awt.Color(12, 91, 160));
        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sispatri/images/close.png"))); // NOI18N
        btnFechar.setText("Fechar");
        btnFechar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFechar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        btnDepreciacaoSelecionados.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDepreciacaoSelecionados.setForeground(new java.awt.Color(12, 91, 160));
        btnDepreciacaoSelecionados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sispatri/images/payment.png"))); // NOI18N
        btnDepreciacaoSelecionados.setText("Gerar depreciação selecionado(s)");
        btnDepreciacaoSelecionados.setEnabled(false);
        btnDepreciacaoSelecionados.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDepreciacaoSelecionados.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDepreciacaoSelecionados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepreciacaoSelecionadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDepreciacaoSelecionados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDepreciacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFechar)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDepreciacao)
            .addComponent(btnFechar)
            .addComponent(btnDepreciacaoSelecionados)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbpPermissao))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbpPermissao)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbpPermissaoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tbpPermissaoStateChanged

    }//GEN-LAST:event_tbpPermissaoStateChanged

    private void btnDepreciacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepreciacaoActionPerformed
        gerarDepreciacao("TODOS");
    }//GEN-LAST:event_btnDepreciacaoActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaActionPerformed
        atualizaLista();
    }//GEN-LAST:event_btnPesquisaActionPerformed

    private void btnDepreciacaoSelecionadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepreciacaoSelecionadosActionPerformed
        gerarDepreciacao("SELECIONADOS");
    }//GEN-LAST:event_btnDepreciacaoSelecionadosActionPerformed

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
            java.util.logging.Logger.getLogger(frmDepreciacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmDepreciacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmDepreciacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmDepreciacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmDepreciacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDepreciacao;
    private javax.swing.JButton btnDepreciacaoSelecionados;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnPesquisa;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbCentroDeCusto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTela;
    private javax.swing.JLabel lblTela1;
    private javax.swing.JPanel pnlCadastro;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JScrollPane scpBotoes;
    private javax.swing.JTable tblPatrimonio;
    private javax.swing.JTabbedPane tbpPermissao;
    // End of variables declaration//GEN-END:variables
}
