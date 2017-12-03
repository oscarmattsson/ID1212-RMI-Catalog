package oscarmat.kth.id1212.rmicatalog.server.integration;

import org.hibernate.Session;
import org.hibernate.Transaction;
import oscarmat.kth.id1212.rmicatalog.client.view.CatalogCommandType;
import oscarmat.kth.id1212.rmicatalog.comon.UserCredentials;
import oscarmat.kth.id1212.rmicatalog.server.model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CatalogDAO {

    private Session session;
    private Transaction transaction;

    private final EntityManagerFactory managerFactory;

    public CatalogDAO() {
        managerFactory = Persistence.createEntityManagerFactory("RMICatalogPU");
    }

    public void createUser(UserCredentials credentials) {

    }
}
