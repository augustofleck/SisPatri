package sispatri.repository;

import java.util.List;
import org.hibernate.Query;
import sispatri.config.HibernateUtil;
import sispatri.model.Patrimonio;

/**
 * Classe responsável por buscar as informações no banco de dados referente
 * ao patrimônio.
 * 
 * @author augusto
 */
public class PatrimonioRepository {
    
    /**
     * Busca todas os patrimônios.
     * 
     * @return lista de patrimônios.
     */
    public static List<Patrimonio> readAll() {
        Query query = HibernateUtil.getSession().createQuery("FROM Patrimonio");
        return query.list();
    }
    
    /**
     * Busca todas os patrimônios ativos.
     * 
     * @return lista de patrimônios.
     */
    public static List<Patrimonio> readAllAtivos() {
        Query query = HibernateUtil.getSession().createQuery("FROM Patrimonio WHERE status = 'A'");
        return query.list();
    }

    /**
     * Busca todas os patrimônios com base no filtro de texto.
     * 
     * @param pParam - Critério de busca.
     * @return lista de patrimônios.
     */
    public static List<Patrimonio> read(String pParam) {
        Query query = HibernateUtil.getSession().createQuery("FROM Patrimonio WHERE (LOWER(nome) LIKE :search OR LOWER(descricao) LIKE :search)");
        query.setParameter("search", "%" + pParam.toLowerCase() + "%");
        return query.list();
    }

    /**
     * Busca todas os patrimônios com base no id.
     * 
     * @param pCodigo - id do patrimônio.
     * @return Patrimonio.
     */
    public static Patrimonio readId(int pCodigo) {
        Query query = HibernateUtil.getSession().createQuery("FROM Patrimonio WHERE id = " + pCodigo);
        return (Patrimonio) query.uniqueResult();
    }
    
    /**
     * Busca todas os patrimônios com base na categoria e no centro de custo.
     * 
     * @param idCategoria - id da categoria.
     * @param idCentroDeCusto - id do centro de custo.
     * @return Patrimonio.
     */
    public static List<Patrimonio> readByCategoriaAndCentroDeCusto(int idCategoria, int idCentroDeCusto) {
        Query query = HibernateUtil.getSession().createQuery("FROM Patrimonio WHERE categoria.id = " + idCategoria + " AND centroDeCusto.id = " + idCentroDeCusto);
        return query.list();
    }
    
    /**
     * Busca todas os patrimônios com base na categoria.
     * 
     * @param pCodigo - id da categoria.
     * @return Patrimonio.
     */
    public static List<Patrimonio> readByCategoria(int pCodigo) {
        Query query = HibernateUtil.getSession().createQuery("FROM Patrimonio WHERE categoria.id = " + pCodigo);
        return query.list();
    }
    
    /**
     * Busca todas os patrimônios com base no centro de custo.
     * 
     * @param pCodigo - id do centro de custo.
     * @return Patrimonio.
     */
    public static List<Patrimonio> readByCentroDeCusto(int pCodigo) {
        Query query = HibernateUtil.getSession().createQuery("FROM Patrimonio WHERE centroDeCusto.id = " + pCodigo);
        return query.list();
    }
}
