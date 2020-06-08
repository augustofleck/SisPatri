package sispatri.controller;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import sispatri.model.Categoria;
import sispatri.model.CentroDeCusto;
import sispatri.model.Patrimonio;
import sispatri.repository.PatrimonioRepository;

/**
 * Controller responsável por fazer as principais ações envolvendo o model Patrimonio.
 * 
 * @author augusto
 */
public class PatrimonioController extends BaseController<Patrimonio>{
    
    /**
     * Altera a situação do modelo para excluído "E".
     * 
     * @param pCodigo - Código do patrimônio.
     * @return String
     */
    public String changeSituation(int pCodigo) {
        try {
            Patrimonio p = PatrimonioRepository.readId(pCodigo);
            p.setStatus("E");
            save(p);
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }

    /**
     * Consulta um model a partir do ID.
     * 
     * @param pCodigo - Código do patrimônio.
     * @return Patrimonio
     */
    public Patrimonio getReadId(int pCodigo) {
        try {
            return PatrimonioRepository.readId(pCodigo);
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }
    
    /**
     * Método usado para realizar o filtro na tela de depreciações.
     * 
     * @param categoria - Categoria.
     * @param centroDeCusto - CentroDeCusto.
     * @return List
     */
    public List<Patrimonio> readByCategoriaAndCentroDeCusto(Categoria categoria, CentroDeCusto centroDeCusto) {
        try {
            if (categoria != null && centroDeCusto != null) {
                return PatrimonioRepository.readByCategoriaAndCentroDeCusto(categoria.getId(), centroDeCusto.getId());
            } else if (categoria != null) {
                return PatrimonioRepository.readByCategoria(categoria.getId());
            } else if (centroDeCusto != null) {
                return PatrimonioRepository.readByCentroDeCusto(centroDeCusto.getId());
            }
            return PatrimonioRepository.readAllAtivos();
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }

    /**
     * Método que popula a tabela na view.
     * 
     * @param pTabela - Tabela da view a ser populada.
     * @param pOption - Opção a ser executada para população dos dados.
     * @param pParam - Parâmetro de busca caso houver um.
     */
    public void popularTabela(JTable pTabela, int pOption, String pParam) {
        try {
            Object[][] lTabela = null;

            Object[] lTabelaTitulo = new Object[7];
            lTabelaTitulo[0] = "Código";
            lTabelaTitulo[1] = "Nome";
            lTabelaTitulo[2] = "Categoria";
            lTabelaTitulo[3] = "Centro de custo";
            lTabelaTitulo[4] = "Valor original";
            lTabelaTitulo[5] = "Valor atualizado";
            lTabelaTitulo[6] = "Situação";

            int lLinha = 0;
            switch (pOption) {
                case 0: {
                    List<Patrimonio> list = PatrimonioRepository.readAll();
                    lTabela = new Object[list.size()][7];
                    for (Patrimonio p : list) {
                        String situacao = (p.getStatus().equals("A") ? "Ativo" : "Excluído");

                        lTabela[lLinha][0] = p.getId();
                        lTabela[lLinha][1] = p.getNome();
                        lTabela[lLinha][2] = p.getCategoria().getDescricao();
                        lTabela[lLinha][3] = p.getCentroDeCusto().getNome();
                        lTabela[lLinha][4] = p.getValorOriginal();
                        lTabela[lLinha][5] = p.getValorAtualizado();                        
                        lTabela[lLinha][6] = situacao;
                        lLinha++;
                    }
                    break;
                }
                case 1: {
                    List<Patrimonio> list = PatrimonioRepository.read(pParam);
                    lTabela = new Object[list.size()][7];
                    for (Patrimonio p : list) {
                        String situacao = (p.getStatus().equals("A") ? "Ativo" : "Excluído");

                        lTabela[lLinha][0] = p.getId();
                        lTabela[lLinha][1] = p.getNome();
                        lTabela[lLinha][2] = p.getCategoria().getDescricao();
                        lTabela[lLinha][3] = p.getCentroDeCusto().getNome();
                        lTabela[lLinha][4] = p.getValorOriginal();
                        lTabela[lLinha][5] = p.getValorAtualizado();                        
                        lTabela[lLinha][6] = situacao;
                        lLinha++;
                    }
                    break;
                }
                default:
                    Patrimonio p = PatrimonioRepository.readId(Integer.parseInt(pParam));
                    if (p == null) {
                        JOptionPane.showMessageDialog(null, "Categoria não encontrada pelo cóodigo: " + pParam);
                    } else {
                        String situacao = (p.getStatus().equals("A") ? "Ativo" : "Excluído");

                        lTabela[lLinha][0] = p.getId();
                        lTabela[lLinha][1] = p.getNome();
                        lTabela[lLinha][2] = p.getCategoria().getDescricao();
                        lTabela[lLinha][3] = p.getCentroDeCusto().getNome();
                        lTabela[lLinha][4] = p.getValorOriginal();
                        lTabela[lLinha][5] = p.getValorAtualizado();                        
                        lTabela[lLinha][6] = situacao;
                    }
                    break;
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
