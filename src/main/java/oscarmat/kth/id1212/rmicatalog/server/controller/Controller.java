package oscarmat.kth.id1212.rmicatalog.server.controller;

import oscarmat.kth.id1212.rmicatalog.comon.CatalogServer;
import oscarmat.kth.id1212.rmicatalog.server.view.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Controller extends UnicastRemoteObject implements CatalogServer {

    private final Logger logger;

    public Controller(Logger logger) throws RemoteException {
        this.logger = logger;
    }

    @Override
    public void register() throws RemoteException {
        logger.putInfo("Register user call.");
    }

    @Override
    public void unregister() throws RemoteException {
        logger.putInfo("Unregister user call.");
    }

    @Override
    public void login() throws RemoteException {
        logger.putInfo("Login user call.");
    }

    @Override
    public void logout() throws RemoteException {
        logger.putInfo("Logout user call.");
    }

    @Override
    public void upload() throws RemoteException {
        logger.putInfo("Upload user call.");
    }

    @Override
    public void download() throws RemoteException {
        logger.putInfo("Download user call.");
    }

    @Override
    public void setNotifyAccess() throws RemoteException {
        logger.putInfo("Set notify access user call.");
    }

    @Override
    public void listFiles() throws RemoteException {
        logger.putInfo("List user call.");
    }
}
