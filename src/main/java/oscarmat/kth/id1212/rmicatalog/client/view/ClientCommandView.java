package oscarmat.kth.id1212.rmicatalog.client.view;

import oscarmat.kth.id1212.cmdview.CMDView;
import oscarmat.kth.id1212.cmdview.Command;
import oscarmat.kth.id1212.rmicatalog.client.controller.AccessListener;
import oscarmat.kth.id1212.rmicatalog.client.controller.Controller;
import oscarmat.kth.id1212.rmicatalog.comon.CatalogServer;

import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.registry.Registry;

public class ClientCommandView extends CMDView<CatalogCommandType> implements AccessListener{

    private static final String PROMPT = "> ";
    private final Controller controller;

    private boolean isConnected = false;
    private boolean isLoggedIn = false;

    private CatalogServer server;

    public ClientCommandView(InputStream in, OutputStream out, Controller controller) {
        super(in, out, CatalogCommandType.class);
        this.controller = controller;

        new Thread(this).start();
    }

    @Override
    protected void welcome() {
        StringBuilder sb = new StringBuilder();
        println("To connect to a catalog, use the \"connect\" command.");
        println("For example:");
        println("connect -h 127.0.0.1 -p " + Registry.REGISTRY_PORT);
        println("For more information, you can use the \"help\" command at any " +
                "time.");
        println("Type \"exit\" to exit the application.");
    }

    @Override
    protected void help() {
        super.help();
    }

    @Override
    protected void handleInput(Command<CatalogCommandType> command) {
        switch (command.getType()) {
            case CONNECT:
                println("To use the catalog you must first register a user using the " +
                        "\"register\" command.");
                println("If you have already registered, you may log in using the " +
                    "\"login\" command.");
                break;
            case REGISTER:
                break;
            case UNREGISTER:
                break;
            case LOGIN:
                break;
            case LOGOUT:
                break;
            case DOWNLOAD:
                break;
            case UPLOAD:
                break;
            case NOTIFY:
                break;
        }
    }

    @Override
    public void notifyAccess(String user, String file) {
        println(user + " accessed the file: " + file);
    }
}
