package oscarmat.kth.id1212.rmicatalog.server.integration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import oscarmat.kth.id1212.rmicatalog.server.model.File;
import oscarmat.kth.id1212.rmicatalog.server.model.User;

/**
 * Container for all DAO objects.
 */
public class DBHandler {

    private final UserDAO userDAO;
    private final FileDAO fileDAO;

    public DBHandler() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(File.class)
                .configure();
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
        SessionFactory factory = configuration.buildSessionFactory(builder.build());
        this.userDAO = new UserDAO(factory);
        this.fileDAO = new FileDAO(factory);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public FileDAO getFileDAO() {
        return fileDAO;
    }
}
