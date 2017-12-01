package oscarmat.kth.id1212.rmicatalog.client.view;

import oscarmat.kth.id1212.cmdview.CMDView;
import oscarmat.kth.id1212.cmdview.Command;
import oscarmat.kth.id1212.rmicatalog.client.controller.Controller;

import java.io.InputStream;
import java.io.OutputStream;

public class ConsoleView extends CMDView<CatalogCommandType>{

    private static final String PROMPT = "> ";
    private final Controller controller;

    public ConsoleView(InputStream in, OutputStream out, Controller controller) {
        super(in, out, CatalogCommandType.class);
        this.controller = controller;

        new Thread(this).start();
    }

    @Override
    protected void welcome() {
        StringBuilder sb = new StringBuilder();
        println("Welcome to the catalog!");
        println("To use the catalog you must first register a user using the " +
                "\"register\" command.");
        println("If you have already registered, you may log in using the " +
                "\"login\" command.");
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
}
