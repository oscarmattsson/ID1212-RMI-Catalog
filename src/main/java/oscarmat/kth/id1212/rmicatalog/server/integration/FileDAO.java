package oscarmat.kth.id1212.rmicatalog.server.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import oscarmat.kth.id1212.rmicatalog.server.model.File;
import oscarmat.kth.id1212.rmicatalog.server.model.User;

import javax.persistence.Query;
import java.util.List;

public class FileDAO extends DAO<File> {

    public FileDAO(SessionFactory factory) {
        super(factory);
    }

    @Override
    public void persist(File file) {
        Session session = openTransaction();
        session.persist(file);
        closeTransaction();
    }

    @Override
    public void update(File file) {
        Session session = openTransaction();
        session.update(file);
        closeTransaction();
    }

    @Override
    public File findById(int id) {
        Session session = openSession();
        File file = session.find(File.class, id);
        closeSession();
        return file;
    }

    @Override
    public void delete(File file) {
        Session session = openTransaction();
        session.delete(file);
        closeTransaction();
    }

    public List<File> listOwnedFiles(User owner) {
        Session session = openSession();
        Query query = session.getNamedQuery(File.LIST_OWNED_FILES)
                .setParameter("owner", owner);
        List<File> files = query.getResultList();
        closeSession();
        return files;
    }

    public List<File> listPublicFiles() {
        Session session = openSession();
        Query query = session.getNamedQuery(File.LIST_PUBLIC_FILES);
        List<File> files = query.getResultList();
        closeSession();
        return files;
    }

    public File findByName(String name) {
        Session session = openSession();
        Query query = session.getNamedQuery(File.SELECT_FROM_NAME)
                .setParameter("name", name);
        File file = (File)query.getSingleResult();
        return file;
    }
}
