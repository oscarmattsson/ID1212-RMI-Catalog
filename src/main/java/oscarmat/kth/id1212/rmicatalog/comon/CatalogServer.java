package oscarmat.kth.id1212.rmicatalog.comon;

import oscarmat.kth.id1212.rmicatalog.server.integration.FileDAO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CatalogServer extends Remote {

    /**
     * Default URI of the server in the RMI registry.
     */
    String SERVER_URI = "CATALOG_SERVER";

    /**
     * Register a new user in the catalog.
     */
    String register(CatalogClient client, UserCredentialsDTO credentials) throws RemoteException;

    /**
     * Unregister an existing user in the catalog.
     */
    String unregister(CatalogClient client) throws RemoteException;

    /**
     * Log in an existing user to the catalog.
     */
    String login(CatalogClient client, UserCredentialsDTO credentials) throws RemoteException;

    /**
     * End the session of a logged in user.
     */
    String logout(CatalogClient client) throws RemoteException;

    /**
     * Upload a file to the server.
     */
    String upload(CatalogClient client, FileDTO file) throws RemoteException;

    /**
     * Download a file from the server.
     */
    String download(CatalogClient client, String filename) throws RemoteException;

    /**
     * Download a file from the server.
     */
    String delete(CatalogClient client, String filename) throws RemoteException;

    /**
     * Tell the server to notify the user when a certain file is accessed.
     */
    String setNotifyAccess(CatalogClient client, String filename) throws RemoteException;

    /**
     * List all files that the user has access to.
     */
    String listFiles(CatalogClient client) throws RemoteException;
}
