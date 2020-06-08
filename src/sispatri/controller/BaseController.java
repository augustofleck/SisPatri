package sispatri.controller;

import sispatri.config.HibernateUtil;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Classe abstrata criada para lidar com os saves, deletes de cada controller.
 * 
 * @author augusto
 * @param <T> Model
 */
public abstract class BaseController<T> implements IBaseController<T> {

    /**
     * Salva um objeto do tipo T Model.
     * 
     * @param pT - Modelo
     * @return String
     */
    @Override
    public String save(T pT) {
        try {
            Transaction transaction = HibernateUtil.getBeginTransaction();

            HibernateUtil.getSession().saveOrUpdate(pT);

            transaction.commit();
        } catch (Exception ex) {
            HibernateUtil.closeSession();
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }

    /**
     * Salva uma lista de objetos do tipo T Model.
     * 
     * @param pT - Modelo
     * @return String
     */
    @Override
    public String saveAll(List<T> pT) {
        try {
            pT.forEach((object) -> {
                save(object);
            });
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }

    /**
     * Deleta um objeto do tipo T Model.
     * 
     * @param pT - Modelo
     * @return String
     */
    @Override
    public String delete(T pT) {
        try {
            Transaction transaction = HibernateUtil.getBeginTransaction();

            HibernateUtil.getSession().delete(pT);

            transaction.commit();
        } catch (Exception ex) {
            HibernateUtil.closeSession();
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }

    /**
     * Deleta uma lista de objetos do tipo T Model.
     * 
     * @param pT - Modelo
     * @return String
     */
    @Override
    public String deleteAll(List<T> pT) {
        try {
            pT.forEach((object) -> {
                delete(object);
            });
        } catch (Exception ex) {
            LoggerController.log(this.getClass(), ex);
        }
        return null;
    }
}
