package oscarmat.kth.id1212.rmicatalog.client.startup;

import oscarmat.kth.id1212.rmicatalog.client.controller.Controller;
import oscarmat.kth.id1212.rmicatalog.client.view.ClientCommandView;

public class Client {

    public static void main(String[] args) {
        Controller controller = new Controller();
        ClientCommandView view = new ClientCommandView(System.in, System.out, controller);
    }

}
