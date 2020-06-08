package sispatri.repository;

import sispatri.config.HibernateUtil;
import sispatri.model.Usuario;
import java.util.List;
import org.hibernate.Query;

/**
 * Classe responsável por buscar as informações no banco de dados referente
 * ao usuário.
 * 
 * @author augusto
 */
public class UsuarioRepository {

    /**
     * Busca todas os usuários.
     * 
     * @return lista de usuários.
     */
    public static List<Usuario> readAll() {
        Query query = HibernateUtil.getSession().createQuery("FROM Usuario");
        return query.list();
    }

    /**
     * Busca todas os usuários ativos.
     * 
     * @return lista de usuários.
     */
    public static List<Usuario> readAllAtivos() {
        Query query = HibernateUtil.getSession().createQuery("FROM Usuario WHERE status = 'A'");
        return query.list();
    }

    /**
     * Busca todas os usuários com base no filtro de texto.
     * 
     * @param pParam - Critério de busca.
     * @return lista de usuários.
     */
    public static List<Usuario> read(String pParam) {
        Query query = HibernateUtil.getSession().createQuery("FROM Usuario WHERE LOWER(nome) LIKE :nomPessoa");
        query.setParameter("nomPessoa", "%" + pParam.toLowerCase() + "%");
        return query.list();
    }

    /**
     * Busca todas os usuários com base no id.
     * 
     * @param pCodigo - id do usuário.
     * @return Usuário.
     */
    public static Usuario readId(int pCodigo) {
        Query query = HibernateUtil.getSession().createQuery("FROM Usuario WHERE id = " + pCodigo);
        return (Usuario) query.uniqueResult();
    }

    /**
     * Busca usuário com base no email e se o mesmo está ativo.
     * 
     * @param email - Email do usuário.
     * @return Usuário.
     */
    public static Usuario getUserWithLogin(String email) {
        Query query = HibernateUtil.getSession().createQuery("FROM Usuario WHERE email = :email AND status = 'A'");
        query.setParameter("email", email);
        return (Usuario) query.uniqueResult();
    }

    /**
     * Busca todas os usuários ativos.
     * 
     * @param email - Email do usuário
     * @param senha - Senha criptografa do usuário.
     * @return lista de usuários.
     */
    public static Usuario validaLogin(String email, String senha) {
        Query query = HibernateUtil.getSession().createQuery("FROM Usuario WHERE email = '" + email + "' AND senha = '" + senha + "'");
        Usuario usuario = (Usuario) query.uniqueResult();
        if (usuario != null) {
            return usuario;
        }
        return null;
    }
}
