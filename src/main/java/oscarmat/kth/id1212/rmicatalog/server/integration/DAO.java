package oscarmat.kth.id1212.rmicatalog.server.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

abstract class DAO<T> {

    private final SessionFactory factory;

    private Session session;
    private Transaction transaction;

    DAO(SessionFactory factory) {
        this.factory = factory;
    }

    protected Session openSession() {
        return session = factory.openSession();
    }

    protected void closeSession() {
        session.close();
    }

    protected Session openTransaction() {
        Session session = openSession();
        transaction = session.beginTransaction();
        return session;
    }

    protected void closeTransaction() {
        transaction.commit();
        closeSession();
    }

    public abstract void persist(T entity);
    public abstract void update(T entity);
    public abstract T findById(int id);
    public abstract void delete(T entity);

}
