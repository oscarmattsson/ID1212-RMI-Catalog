package oscarmat.kth.id1212.rmicatalog.comon;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CatalogClient extends Remote {

    /**
     * Notify the owner of a file that their file has been accessed.
     * @param user User who accessed the file.
     * @param file File that was accessed.
     */
    void notifyAccessed(String user, String file) throws RemoteException;

}
