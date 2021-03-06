package oscarmat.kth.id1212.rmicatalog.client.view;

import oscarmat.kth.id1212.cmdview.CMDView;
import oscarmat.kth.id1212.cmdview.Command;
import oscarmat.kth.id1212.rmicatalog.client.controller.AccessListener;
import oscarmat.kth.id1212.rmicatalog.client.controller.Controller;
import oscarmat.kth.id1212.rmicatalog.comon.CatalogServer;

import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;

public class ClientCommandView extends CMDView<CatalogCommandType> implements AccessListener{

    private final Controller controller;

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
        println("connect -h 127.0.0.1");
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
                connect(command);
                break;
            case DISCONNECT:
                disconnect(command);
                break;
            case REGISTER:
                register(command);
                break;
            case UNREGISTER:
                unregister(command);
                break;
            case LOGIN:
                login(command);
                break;
            case LOGOUT:
                logout(command);
                break;
            case DOWNLOAD:
                download(command);
                break;
            case UPLOAD:
                upload(command);
                break;
            case DELETE:
                delete(command);
                break;
            case NOTIFY:
                notify(command);
                break;
            case LIST:
                list(command);
                break;
            default:
                System.err.println("Unimplemented command.");
        }
    }

    private void connect(Command command) {
        String host = command.getParameter("h");
        if(host == null) {
            println("Missing host parameter.");
            println(CatalogCommandType.CONNECT.getUsage());
        }
        else {
            if (controller.connect(host)) {
                println("To use the catalog you must first register a user using the " +
                        "\"register\" command.");
                println("If you have already registered, you may log in using the " +
                        "\"login\" command.");
            } else {
                println("Error connecting to server, please verify your " +
                        "information and try again.");
            }
        }
    }

    private void disconnect(Command command) {
        if(controller.disconnect()) {
            println("Sucessfully disconnected from server.");
        }
        else {
            println("Error disconnecting from server, discarding connection.");
        }
    }

    private void register(Command command) {
        try {
            String username = command.getParameter("u");
            String password = command.getParameter("p");
            if(username == null || password == null) {
                println("Missing parameter.");
                println("Usage: " + CatalogCommandType.REGISTER.getUsage());
            }
            else {
                println(controller.register(username, password));
            }
        }
        catch (RemoteException e) {
            println(e.getMessage());
        }
    }

    private void unregister(Command command) {
        try {
            println(controller.unregister());
        }
        catch (RemoteException e) {
            println(e.getMessage());
        }
    }

    private void login(Command command) {
        try {
            String username = command.getParameter("u");
            String password = command.getParameter("p");
            if(username == null || password == null) {
                println("Missing parameter.");
                println("Usage: " + CatalogCommandType.LOGIN.getUsage());
            }
            else {
                println(controller.login(username, password));
            }
        }
        catch (RemoteException e) {
            println(e.getMessage());
        }
    }

    private void logout(Command command) {
        try {
            println(controller.logout());
        }
        catch (RemoteException e) {
            println(e.getMessage());
        }
    }

    private void upload(Command command) {
        try {
            String file = command.getParameter("f");
            boolean isPublic = command.getParameter("public") != null;
            boolean isReadonly = command.getParameter("readonly") != null;
            if(file == null) {
                println("Missing parameter.");
                println("Usage: " + CatalogCommandType.UPLOAD.getUsage());
            }
            else {
                println(controller.upload(file, isPublic, isReadonly));
            }
        }
        catch (RemoteException e) {
            println(e.getMessage());
        }
    }

    private void download(Command command) {
        try {
            String file = command.getParameter("f");
            if(file == null) {
                println("Missing parameter");
                println("Usage: " + CatalogCommandType.DOWNLOAD.getUsage());
            }
            else {
                println(controller.download(file));
            }
        }
        catch (RemoteException e) {
            println(e.getMessage());
        }
    }

    private void delete(Command command) {
        try {
            String file = command.getParameter("f");
            if(file == null) {
                println("Missing parameter");
                println("Usage: " + CatalogCommandType.DELETE.getUsage());
            }
            else {
                println(controller.delete(file));
            }
        }
        catch (RemoteException e) {
            println(e.getMessage());
        }
    }

    private void notify(Command command) {
        try {
            String file = command.getParameter("f");
            if(file == null) {
                println("Missing parameter");
                println("Usage: " + CatalogCommandType.NOTIFY.getUsage());
            }
            else {
                println(controller.addAccessListener(file, this));
            }
        }
        catch (RemoteException e) {
            println(e.getMessage());
        }
    }

    private void list(Command command) {
        try {
            println(controller.list());
        }
        catch (RemoteException e) {
            println(e.getMessage());
        }
    }

    @Override
    public void notifyAccess(String user, String file) {
        println(user + " accessed the file: " + file);
    }
}
