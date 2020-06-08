package sispatri.controller;

import sispatri.model.Categoria;
import sispatri.repository.CategoriaRepository;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Controller responsável por fazer as principais ações envolvendo o model Categoria.
 * 
 * @author augusto
 */
public class CategoriaController extends BaseController<Categoria> {

    /**
     * Altera a situação do modelo para excluído "E".
     * 
     * @param pCodigo - Código da categoria
     * @return String
     */
    public String changeSituation(int pCodigo) {
        try {
            Categoria categoria = CategoriaRepository.readId(pCodigo);
            categoria.setStatus("E");
            save(categoria);
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }

    /**
     * Consulta um model a partir do ID.
     * 
     * @param pCodigo - Código da categoria
     * @return Categoria
     */
    public Categoria getReadId(int pCodigo) {
        try {
            return CategoriaRepository.readId(pCodigo);
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
    public List<Categoria> getAll() {
        try {
            return CategoriaRepository.readAll();
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
    public List<Categoria> getAlAtivos() {
        try {
            return CategoriaRepository.readAllAtivos();
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
            lTabelaTitulo[2] = "Percentual depreciação mês";
            lTabelaTitulo[3] = "Situação";

            int lLinha = 0;
            switch (pOption) {
                case 0: {
                    List<Categoria> list = CategoriaRepository.readAll();
                    lTabela = new Object[list.size()][4];
                    for (Categoria c : list) {
                        String situacao = (c.getStatus().equals("A") ? "Ativo" : "Excluído");

                        lTabela[lLinha][0] = c.getId();
                        lTabela[lLinha][1] = c.getDescricao();
                        lTabela[lLinha][2] = c.getPercetualDepreciacaoMes();
                        lTabela[lLinha][3] = situacao;
                        lLinha++;
                    }
                    break;
                }
                case 1: {
                    List<Categoria> list = CategoriaRepository.read(pParam);
                    lTabela = new Object[list.size()][4];
                    for (Categoria c : list) {
                        String situacao = (c.getStatus().equals("A") ? "Ativo" : "Excluído");

                        lTabela[lLinha][0] = c.getId();
                        lTabela[lLinha][1] = c.getDescricao();
                        lTabela[lLinha][2] = c.getPercetualDepreciacaoMes();
                        lTabela[lLinha][3] = situacao;
                        lLinha++;
                    }
                    break;
                }
                default:
                    Categoria c = CategoriaRepository.readId(Integer.parseInt(pParam));
                    if (c == null) {
                        JOptionPane.showMessageDialog(null, "Categoria não encontrada pelo cóodigo: " + pParam);
                    } else {
                        String situacao = (c.getStatus().equals("A") ? "Ativo" : "Excluído");

                        lTabela[lLinha][0] = c.getId();
                        lTabela[lLinha][1] = c.getDescricao();
                        lTabela[lLinha][2] = c.getPercetualDepreciacaoMes();
                        lTabela[lLinha][3] = situacao;
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
