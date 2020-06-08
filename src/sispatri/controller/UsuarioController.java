package sispatri.controller;

import sispatri.repository.UsuarioRepository;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import sispatri.model.Parametro;
import sispatri.model.Usuario;

/**
 * Controller responsável por fazer as principais ações envolvendo o model Usuário.
 * 
 * @author augusto
 */
public class UsuarioController extends BaseController<Usuario> {

    /**
     * Altera a situação do modelo para excluído "E".
     * 
     * @param pCodigo - Código do usuário.
     * @return String
     */
    public String changeSituation(int pCodigo) {
        try {
            Usuario usuario = UsuarioRepository.readId(pCodigo);
            usuario.setStatus("E");
            save(usuario);
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }

    /**
     * Consulta um model a partir do ID.
     * 
     * @param pCodigo - Código do usuário.
     * @return Usuario
     */
    public Usuario getReadId(int pCodigo) {
        try {
            return UsuarioRepository.readId(pCodigo);
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
    public List<Usuario> getReadAllAtivos() {
        try {
            return UsuarioRepository.readAllAtivos();
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }
    
    /**
     * Reseta senha baseada no id do usuário (Não implementado ainda).
     * 
     * @param id - ID do Usuário
     * @return String
     */
    public String resetPassword(int id) {
        try {
            Usuario usuario = getReadId(id);
            resetPassword(usuario);
            save(usuario);
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }
    
    /**
     * Reseta senha baseada no model do usuário (Não implementado ainda).
     * 
     * @param usuario - Modelo do Usuário.
     * @return String
     */
    public String resetPassword(Usuario usuario) {
        try {
            usuario.setSenha(Parametro.SENHA_DEFAULT.name());
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }

    /**
     * Método criado para verificar se dado login (email) já existe.
     * 
     * @param pLogin - Login do usuário (email).
     * @return Boolean
     */
    public Boolean verifyExistsLogin(String pLogin) {
        try {
            return getUserWithLogin(pLogin) != null;
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return false;
    }

    /**
     * Busca o usuário baseado no login dele.
     * 
     * @param pLogin - Login do usuário (email).
     * @return Usuario
     */
    public Usuario getUserWithLogin(String pLogin) {
        try {
            return UsuarioRepository.getUserWithLogin(pLogin);
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }

    /**
     * Valida o login do usuário baseado no login e senha.
     * 
     * @param login - Login do usuário (email).
     * @param senha - Senha do usuário
     * @return Usuario
     */
    public Usuario validaLogin(String login, String senha) {
        try {
            return UsuarioRepository.validaLogin(login, senha);
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
            lTabelaTitulo[2] = "Email";
            lTabelaTitulo[3] = "Situação";

            int lLinha = 0;
            switch (pOption) {
                case 0: {
                    List<Usuario> list = UsuarioRepository.readAll();
                    lTabela = new Object[list.size()][4];
                    for (Usuario c : list) {
                        String situacao = (c.getStatus().equals("A") ? "Ativo" : "Excluído");

                        lTabela[lLinha][0] = c.getId();
                        lTabela[lLinha][1] = c.getNome();
                        lTabela[lLinha][2] = c.getEmail();
                        lTabela[lLinha][3] = situacao;
                        lLinha++;
                    }
                    break;
                }
                case 1: {
                    List<Usuario> list = UsuarioRepository.read(pParam);
                    lTabela = new Object[list.size()][4];
                    for (Usuario c : list) {
                        String situacao = (c.getStatus().equals("A") ? "Ativo" : "Excluído");

                        lTabela[lLinha][0] = c.getId();
                        lTabela[lLinha][1] = c.getNome();
                        lTabela[lLinha][2] = c.getEmail();
                        lTabela[lLinha][3] = situacao;
                        lLinha++;
                    }
                    break;
                }
                default:
                    Usuario c = UsuarioRepository.readId(Integer.parseInt(pParam));
                    if (c == null) {
                        JOptionPane.showMessageDialog(null, "Usuário não encontrada pelo cóodigo: " + pParam);
                    } else {
                        String situacao = (c.getStatus().equals("A") ? "Ativo" : "Excluído");

                        lTabela[lLinha][0] = c.getId();
                        lTabela[lLinha][1] = c.getNome();
                        lTabela[lLinha][2] = c.getEmail();
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
