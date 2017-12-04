package oscarmat.kth.id1212.rmicatalog.server.startup;

import oscarmat.kth.id1212.rmicatalog.server.controller.Controller;
import oscarmat.kth.id1212.rmicatalog.server.integration.DBHandler;
import oscarmat.kth.id1212.rmicatalog.server.integration.UserDAO;
import oscarmat.kth.id1212.rmicatalog.server.model.User;
import oscarmat.kth.id1212.rmicatalog.server.view.Logger;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

public class Server {

    public static void main(String[] args) {

        Logger logger = new Logger(System.out);
        logger.putInfo("Starting server...");

        DBHandler db = new DBHandler();

        try {
            new Server().startRegistry();
            Naming.rebind(Controller.SERVER_URI, new Controller(logger, db));
            logger.putInfo("Server is running.");
        }
        catch (MalformedURLException | RemoteException e) {
            logger.putError("Could not start catalog server. Shutting down.");
        }
    }

    private void startRegistry() throws RemoteException {
        try {
            LocateRegistry.getRegistry().list();
        }
        catch (RemoteException noRegistry) {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }
    }

}
