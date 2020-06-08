package sispatri.controller;

import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import sispatri.model.DepreciacaoProcessamento;
import sispatri.model.Patrimonio;
import sispatri.repository.DepreciacaoProcessamentoRepository;
import sispatri.support.Formatacao;

/**
 * Controller responsável por fazer as principais ações envolvendo o model DepreciacaoProcessamento.
 * 
 * @author augusto
 */
public class DepreciacaoProcessamentoController extends BaseController<DepreciacaoProcessamento> {
    
    /**
     * Busca a DepreciacaoProcessamento que contém o Patrimonio e a data (mês e
     * ano) que é igual à atual.
     * 
     * @param p - Modelo Patrimonio
     * @return DepreciacaoProcessamento
     */
    public DepreciacaoProcessamento readByPatrimonioAndDateIsNow(Patrimonio p) {
        return DepreciacaoProcessamentoRepository.readByPatrimonioAndDateIsNow(p.getId());
    }
    
    /**
     * Método que popula a tabela na view.
     * 
     * @param pTabela - Tabela da view a ser populada.
     * @param list - Lista de patrimonios.
     */
    public void popularTabelaDepreciacao(JTable pTabela, List<Patrimonio> list) {
        try {
            Object[][] lTabela = null;

            Object[] lTabelaTitulo = new Object[7];
            lTabelaTitulo[0] = "Código";
            lTabelaTitulo[1] = "Nome";
            lTabelaTitulo[2] = "Categoria";
            lTabelaTitulo[3] = "Centro de custo";
            lTabelaTitulo[4] = "Valor atualizado";
            lTabelaTitulo[5] = "% depreciação";
            lTabelaTitulo[6] = "Última depreciação";

            int lLinha = 0;
            lTabela = new Object[list.size()][7];
            for (Patrimonio p : list) {
                DepreciacaoProcessamento depreciacaoProcessamento = DepreciacaoProcessamentoRepository.getMaxDataDepreciacao(p.getId());
                
                lTabela[lLinha][0] = p.getId();
                lTabela[lLinha][1] = p.getNome();
                lTabela[lLinha][2] = p.getCategoria().getDescricao();
                lTabela[lLinha][3] = p.getCentroDeCusto().getNome();
                lTabela[lLinha][4] = p.getValorAtualizado() != null ? p.getValorAtualizado().setScale(2) : "Sem depreciação";
                lTabela[lLinha][5] = p.getCategoria().getPercetualDepreciacaoMes();                        
                lTabela[lLinha][6] = depreciacaoProcessamento == null ? "Sem depreciação" : Formatacao.ajustaDataDMA(depreciacaoProcessamento.getDataDepreciacao());
                lLinha++;
            }

            pTabela.setModel(new DefaultTableModel(lTabela, lTabelaTitulo) {
                @Override
                public boolean isCellEditable(int pRow, int pColumn) {
                    return false;
                }

                @Override
                public Class getColumnClass(int pColumn) {
                    if (pColumn == 2) {
                    }
                    return Object.class;
                }
            });

            pTabela.setSelectionMode(0);

            TableColumn lColumn = null;

            DefaultTableCellRenderer lLeft = new DefaultTableCellRenderer();
            DefaultTableCellRenderer lCenter = new DefaultTableCellRenderer();
            DefaultTableCellRenderer lRight = new DefaultTableCellRenderer();

            lLeft.setHorizontalAlignment(SwingConstants.LEFT);
            lCenter.setHorizontalAlignment(SwingConstants.CENTER);
            lRight.setHorizontalAlignment(SwingConstants.RIGHT);

            for (int i = 0; i < pTabela.getColumnCount(); i++) {
                lColumn = pTabela.getColumnModel().getColumn(i);
                switch (i) {
                    case 0:
                        lColumn.setPreferredWidth(30);
                        lColumn.setCellRenderer(lRight);
                        break;
                    case 1:
                        lColumn.setPreferredWidth(120);
                        lColumn.setCellRenderer(lLeft);
                        break;
                    case 2:
                        lColumn.setPreferredWidth(150);
                        lColumn.setCellRenderer(lLeft);
                        break;
                    case 3:
                        lColumn.setPreferredWidth(60);
                        lColumn.setCellRenderer(lCenter);
                        break;
                }
            }
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
    }
    
}
