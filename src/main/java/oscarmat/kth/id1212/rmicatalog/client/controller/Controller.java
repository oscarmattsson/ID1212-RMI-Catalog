package oscarmat.kth.id1212.rmicatalog.client.controller;

import oscarmat.kth.id1212.rmicatalog.comon.CatalogClient;
import oscarmat.kth.id1212.rmicatalog.comon.CatalogServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Handles communication between view and server.
 */
public class Controller {

    private CatalogServer server;

    private boolean connected = false;
    private boolean loggedIn = false;

    private final Map<String, Set<AccessListener>> accessListeners;

    /**
     * Create a new controller.
     */
    public Controller() {
        accessListeners = new HashMap<>();
    }

    /**
     * Add a listener to be notified when a file is accessed.
     * @param file File to listen for access on.
     * @param listener The listener to be notified upon access.
     */
    public void addAccessListener(String file, AccessListener listener) {
        Set<AccessListener> listeners = accessListeners.get(file);
        if(listeners != null) {
            listeners.add(listener);
        }
        else {
            listeners = new HashSet<>();
            listeners.add(listener);
            accessListeners.put(file, listeners);
        }
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
     * Represents the client side of the RMI connection.
     */
    private class ClientController extends UnicastRemoteObject implements CatalogClient {

        protected ClientController() throws RemoteException {
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
