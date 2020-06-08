package sispatri.repository;

import sispatri.config.HibernateUtil;
import sispatri.model.Categoria;
import java.util.List;
import org.hibernate.Query;

/**
 * Classe responsável por buscar as informações no banco de dados referente
 * à categoria.
 * 
 * @author augusto
 */
public class CategoriaRepository {

    /**
     * Busca todas as categorias.
     * 
     * @return lista de categorias.
     */
    public static List<Categoria> readAll() {
        Query query = HibernateUtil.getSession().createQuery("FROM Categoria");
        return query.list();
    }
    
    /**
     * Busca todas as categorias ativas.
     * 
     * @return lista de categorias.
     */
    public static List<Categoria> readAllAtivos() {
        Query query = HibernateUtil.getSession().createQuery("FROM Categoria WHERE status = 'A'");
        return query.list();
    }

    /**
     * Busca todas as categorias com base no filtro de texto.
     * 
     * @param pParam - Critério de busca.
     * @return lista de categorias.
     */
    public static List<Categoria> read(String pParam) {
        Query query = HibernateUtil.getSession().createQuery("FROM Categoria WHERE LOWER(descricao) LIKE :search");
        query.setParameter("search", "%" + pParam.toLowerCase() + "%");
        return query.list();
    }

    /**
     * Busca todas as categorias com base no id.
     * 
     * @param pCodigo - id da Categoria.
     * @return Categoria.
     */
    public static Categoria readId(int pCodigo) {
        Query query = HibernateUtil.getSession().createQuery("FROM Categoria WHERE id = " + pCodigo);
        return (Categoria) query.uniqueResult();
    }
}
