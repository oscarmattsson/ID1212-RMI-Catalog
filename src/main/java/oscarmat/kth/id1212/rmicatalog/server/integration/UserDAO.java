package oscarmat.kth.id1212.rmicatalog.server.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import oscarmat.kth.id1212.rmicatalog.server.model.User;

import javax.persistence.Query;

public class UserDAO extends DAO<User> {

    UserDAO(SessionFactory factory) {
        super(factory);
    }

    @Override
    public void persist(User user) {
        Session session = openTransaction();
        session.persist(user);
        closeTransaction();
    }

    @Override
    public void update(User user) {
        Session session = openTransaction();
        session.update(user);
        closeTransaction();
    }

    @Override
    public User findById(int id) {
        Session session = openSession();
        User user = session.get(User.class, id);
        closeSession();
        return user;
    }

    @Override
    public void delete(User user) {
        Session session = openTransaction();
        session.delete(user);
        closeTransaction();
    }

    public User findByUsername(String username) {
        Session session = openSession();
        Query query = session.getNamedQuery(User.SELECT_FROM_USERNAME)
                .setParameter("username", username);
        User user = (User)query.getSingleResult();
        closeSession();
        return user;
    }

}
