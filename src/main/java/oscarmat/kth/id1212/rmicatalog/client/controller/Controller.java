package oscarmat.kth.id1212.rmicatalog.client.controller;

import oscarmat.kth.id1212.rmicatalog.comon.CatalogClient;
import oscarmat.kth.id1212.rmicatalog.comon.CatalogServer;
import oscarmat.kth.id1212.rmicatalog.comon.FileDTO;
import oscarmat.kth.id1212.rmicatalog.comon.UserCredentialsDTO;

import java.io.File;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Handles communication between view and server.
 */
public class Controller {

    private CatalogServer server;
    private final CatalogClient client;

    private boolean connected = false;
    private boolean loggedIn = false;

    private final Map<String, Set<AccessListener>> accessListeners;

    /**
     * Create a new controller.
     */
    public Controller() throws RemoteException{
        accessListeners = new HashMap<>();
        client = new ClientController();
    }

    /**
     * Add a listener to be notified when a file is accessed.
     * @param file File to listen for access on.
     * @param listener The listener to be notified upon access.
     */
    public String addAccessListener(String file, AccessListener listener) throws RemoteException {
        Set<AccessListener> listeners = accessListeners.get(file);
        if(listeners != null) {
            listeners.add(listener);
        }
        else {
            listeners = new HashSet<>();
            listeners.add(listener);
            accessListeners.put(file, listeners);
        }
        return server.setNotifyAccess(client, file);
    }

    /**
     * @return True if the client is connected to the server, otherwise false.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * @return True if the client is logged in to the server, otherwise false.
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Connect to a catalog server.
     * @return True if the connection was successful, otherwise false.
     */
    public boolean connect(String host) {
        try {
            server = (CatalogServer)Naming.lookup("//" + host + "/" + CatalogServer.SERVER_URI);
            connected = true;
            return true;
        }
        catch (Exception e) {
            System.err.println(e.getMessage()); // TODO: Remove debug message
            return false;
        }
    }

    /**
     * Disconnect from the catalog server.
     * @return True if the connection was ended successfully, otherwise false.
     */
    public boolean disconnect() {
        connected = false;
        try {
            UnicastRemoteObject.unexportObject(server, false);
            return true;
        }
        catch (Exception e) {
            System.err.println(e.getMessage()); // TODO: Remove debug message
            return false;
        }
        finally {
            server = null;
        }
    }

    public String login(String username, String password) throws RemoteException {
        UserCredentialsDTO credentials = new UserCredentialsDTO(username, password);
        return server.login(client, credentials);
    }

    public String logout() throws RemoteException {
        return server.logout(client);
    }

    public String register(String username, String password) throws RemoteException {
        UserCredentialsDTO credentials = new UserCredentialsDTO(username, password);
        return server.register(client, credentials);
    }

    public String unregister() throws RemoteException {
        return server.unregister(client);
    }

    public String upload(String filename, boolean isPublic, boolean isReadonly) throws RemoteException {
        File file = new File(filename);
        if(file.exists()) {
            FileDTO dto;
            if(isPublic) {
                dto = new FileDTO(filename, file.length(), isPublic, isReadonly);
            }
            else {
                dto = new FileDTO(filename, file.length());
            }
            return server.upload(client, dto);
        }
        else {
            return "Error, no such file.";
        }
    }

    public String download(String filename) throws RemoteException {
        return server.download(client, filename);
    }

    public String delete(String filename) throws RemoteException {
        return server.delete(client, filename);
    }

    public String list() throws RemoteException {
        return server.listFiles(client);
    }

    /**
     * Represents the client side of the RMI connection.
     */
    private class ClientController extends UnicastRemoteObject implements CatalogClient {

        ClientController() throws RemoteException {
        }

        @Override
        public void notifyAccessed(String user, String file) throws RemoteException {
            Set<AccessListener> listeners = accessListeners.get(file);
            if(listeners != null) {
                for(AccessListener listener : listeners) {
                    listener.notifyAccess(user, file);
                }
            }
        }
    }
}
