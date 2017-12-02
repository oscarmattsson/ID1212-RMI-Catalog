package oscarmat.kth.id1212.rmicatalog.comon;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CatalogServer extends Remote {

    /**
     * Default URI of the server in the RMI registry.
     */
    public static final String SERVER_URI = "CATALOG_SERVER";

    /**
     * Register a new user in the catalog.
     */
    void register() throws RemoteException;

    /**
     * Unregister an existing user in the catalog.
     */
    void unregister() throws RemoteException;

    /**
     * Log in an existing user to the catalog.
     */
    void login() throws RemoteException;

    /**
     * End the session of a logged in user.
     */
    void logout() throws RemoteException;

    /**
     * Upload a file to the server.
     */
    void upload() throws RemoteException;

    /**
     * Download a file from the server.
     */
    void download() throws RemoteException;

    /**
     * Tell the server to notify the user when a certain file is accessed.
     * @throws RemoteException
     */
    void setNotifyAccess() throws RemoteException;

    /**
     * List all files that the user has access to.
     * @throws RemoteException
     */
    void listFiles() throws RemoteException;
}
