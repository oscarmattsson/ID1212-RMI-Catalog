package oscarmat.kth.id1212.rmicatalog.client.startup;

import oscarmat.kth.id1212.rmicatalog.client.controller.Controller;
import oscarmat.kth.id1212.rmicatalog.client.view.ClientCommandView;

import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) {
        try {
            Controller controller = new Controller();
            ClientCommandView view = new ClientCommandView(System.in, System.out, controller);
        }
        catch (RemoteException e) {
            System.err.println("Error creating controller, shutting down application.");
        }
    }

}
