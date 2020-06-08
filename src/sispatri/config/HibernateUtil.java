package sispatri.config;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static Session session;

    static {
        try {
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Cria uma instância de sessão utilizando o Hibernate caso já não tenha sido criada.
     * 
     * @return Session
     */
    public static Session getSession() {
        if (session == null || !session.isConnected()) {
            session = getSessionFactory().openSession();
        }
        return session;
    }

    /**
     * Fecha a instância de sessão.
     * 
     */
    public static void closeSession() {
        if (session != null && session.isConnected()) {
            session.close();
        }
    }

    /**
     * Inícia uma transação com o Hibernate.
     * 
     * @return Transaction
     */
    public static Transaction getBeginTransaction() {
        return getSession().beginTransaction();
    }
}
