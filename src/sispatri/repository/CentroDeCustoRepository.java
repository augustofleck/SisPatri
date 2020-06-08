package sispatri.repository;

import sispatri.config.HibernateUtil;
import sispatri.model.CentroDeCusto;
import java.util.List;
import org.hibernate.Query;

/**
 * Classe responsável por buscar as informações no banco de dados referente
 * ao centro de custo.
 * 
 * @author augusto
 */
public class CentroDeCustoRepository {

    /**
     * Busca todas os centro de custos.
     * 
     * @return lista de centro de custos.
     */
    public static List<CentroDeCusto> readAll() {
        Query query = HibernateUtil.getSession().createQuery("FROM CentroDeCusto");
        return query.list();
    }
    
    /**
     * Busca todas os centro de custos ativos.
     * 
     * @return lista de centro de custos.
     */
    public static List<CentroDeCusto> readAllAtivos() {
        Query query = HibernateUtil.getSession().createQuery("FROM CentroDeCusto WHERE status = 'A'");
        return query.list();
    }

    /**
     * Busca todas os centro de custos com base no filtro de texto.
     * 
     * @param pParam - Critério de busca.
     * @return lista de centro de custos.
     */
    public static List<CentroDeCusto> read(String pParam) {
        Query query = HibernateUtil.getSession().createQuery("FROM CentroDeCusto WHERE LOWER(nome) LIKE :search");
        query.setParameter("search", "%" + pParam.toLowerCase() + "%");
        return query.list();
    }

    /**
     * Busca todas os centro de custos com base no id.
     * 
     * @param pCodigo - id do centro de custo.
     * @return Centro de custo.
     */
    public static CentroDeCusto readId(int pCodigo) {
        Query query = HibernateUtil.getSession().createQuery("FROM CentroDeCusto WHERE id = " + pCodigo);
        return (CentroDeCusto) query.uniqueResult();
    }
}
