package sispatri.repository;

import java.util.Calendar;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import sispatri.config.HibernateUtil;
import sispatri.model.DepreciacaoProcessamento;

/**
 * Classe responsável por buscar as informações no banco de dados referente
 * à depreciação.
 * 
 * @author augusto
 */
public class DepreciacaoProcessamentoRepository {
    
    /**
     * Busca todas as depreciações.
     * 
     * @return lista de depreciações.
     */
    public static List<DepreciacaoProcessamento> readAll() {
        Query query = HibernateUtil.getSession().createQuery("FROM DepreciacaoProcessamento");
        return query.list();
    }
    
    /**
     * Busca todas as depreciações com base no patrimonio, mês e ano atual.
     * 
     * @param idPatrimonio - id do patrimônio.
     * @return lista de depreciações.
     */
    public static DepreciacaoProcessamento readByPatrimonioAndDateIsNow(int idPatrimonio) {
        Calendar now = Calendar.getInstance();
        
        Query query = HibernateUtil.getSession().createQuery("FROM DepreciacaoProcessamento dp WHERE dp.patrimonio.id = " + idPatrimonio + " AND "
                + " EXTRACT(YEAR FROM dp.dataDepreciacao) = " + now.get(Calendar.YEAR) + " AND EXTRACT(MONTH FROM dp.dataDepreciacao) = " + (now.get(Calendar.MONTH) + 1));
        return (DepreciacaoProcessamento) query.uniqueResult();
    }
    
    /**
     * Busca todas as depreciações.
     * 
     * @param idPatrimonio - id do patrimônio
     * @return lista de depreciações.
     */
    public static DepreciacaoProcessamento getMaxDataDepreciacao(int idPatrimonio) {
        Query query = HibernateUtil.getSession().createQuery("FROM DepreciacaoProcessamento dp WHERE dp.id = " + idPatrimonio + " AND dp.dataDepreciacao IN (SELECT MAX(dp1.dataDepreciacao) FROM DepreciacaoProcessamento dp1)");
        return (DepreciacaoProcessamento) query.uniqueResult();
    }
}
