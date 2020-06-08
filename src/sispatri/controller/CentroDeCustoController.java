package sispatri.controller;

import sispatri.model.CentroDeCusto;
import sispatri.repository.CentroDeCustoRepository;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Controller responsável por fazer as principais ações envolvendo o model CentroDeCusto.
 * 
 * @author augusto
 */
public class CentroDeCustoController extends BaseController<CentroDeCusto> {

    /**
     * Altera a situação do modelo para excluído "E".
     * 
     * @param pCodigo - Código do centro de custo.
     * @return String
     */
    public String changeSituation(int pCodigo) {
        try {
            CentroDeCusto centroDeCusto = CentroDeCustoRepository.readId(pCodigo);
            centroDeCusto.setStatus("E");
            save(centroDeCusto);
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }

    /**
     * Consulta um model a partir do ID.
     * 
     * @param pCodigo - Código do centro de custo.
     * @return CentroDeCusto
     */
    public CentroDeCusto getReadId(int pCodigo) {
        try {
            return CentroDeCustoRepository.readId(pCodigo);
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }
    
    /**
     * Busca todos do modelo.
     * 
     * @return List
     */
    public List<CentroDeCusto> getAll() {
        try {
            return CentroDeCustoRepository.readAll();
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }
    
    /**
     * Busca todos do modelo que estão ativos.
     * 
     * @return List
     */
    public List<CentroDeCusto> getAllAtivos() {
        try {
            return CentroDeCustoRepository.readAllAtivos();
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

            Object[] lTabelaTitulo = new Object[4];
            lTabelaTitulo[0] = "Código";
            lTabelaTitulo[1] = "Nome";
            lTabelaTitulo[2] = "Código Externo";
            lTabelaTitulo[3] = "Situação";

            int lLinha = 0;
            switch (pOption) {
                case 0: {
                    List<CentroDeCusto> list = CentroDeCustoRepository.readAll();
                    lTabela = new Object[list.size()][4];
                    for (CentroDeCusto centroDeCusto : list) {
                        String situacao = (centroDeCusto.getStatus().equals("A") ? "Ativo" : "Excluído");

                        lTabela[lLinha][0] = centroDeCusto.getId();
                        lTabela[lLinha][1] = centroDeCusto.getNome();
                        lTabela[lLinha][2] = centroDeCusto.getCodigo();
                        lTabela[lLinha][3] = situacao;
                        lLinha++;
                    }
                    break;
                }
                case 1: {
                    List<CentroDeCusto> list = CentroDeCustoRepository.read(pParam);
                    lTabela = new Object[list.size()][4];
                    for (CentroDeCusto centroDeCusto : list) {
                        String situacao = (centroDeCusto.getStatus().equals("A") ? "Ativo" : "Excluído");

                        lTabela[lLinha][0] = centroDeCusto.getId();
                        lTabela[lLinha][1] = centroDeCusto.getNome();
                        lTabela[lLinha][2] = centroDeCusto.getCodigo();
                        lTabela[lLinha][3] = situacao;
                    }
                    break;
                }
                default:
                    CentroDeCusto centroDeCusto = CentroDeCustoRepository.readId(Integer.parseInt(pParam));
                    if (centroDeCusto == null) {
                        JOptionPane.showMessageDialog(null, "Centro de custo não encontrado pelo código: " + pParam);
                    } else {
                        String situacao = (centroDeCusto.getStatus().equals("A") ? "Ativo" : "Excluído");

                        lTabela[lLinha][0] = centroDeCusto.getId();
                        lTabela[lLinha][1] = centroDeCusto.getNome();
                        lTabela[lLinha][2] = centroDeCusto.getCodigo();
                        lTabela[lLinha][3] = situacao;
                        lLinha++;
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
                        lColumn.setPreferredWidth(180);
                        lColumn.setCellRenderer(lLeft);
                        break;
                    case 2:
                        lColumn.setPreferredWidth(130);
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
