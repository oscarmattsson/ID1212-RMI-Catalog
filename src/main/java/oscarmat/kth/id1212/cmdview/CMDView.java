package oscarmat.kth.id1212.cmdview;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Represents a command line view using the supplied enum for the set of usable
 * commands. The commands "help" and "stop" are reserved by the CMDView.
 * "help" posts a list of all available commands, override the
 * help method to change this behaviour. "stop" cancels the CMDView.
 * @param <E> Enum set of commands.
 */
public abstract class CMDView<E extends Enum<E> & CommandType> implements Runnable {

    protected enum ReservedCommands implements CommandType {
        HELP (
                "help [-c <command>]",
                "Posts the help section for the application.\n\t" +
                        "-c Posts the help section for a specific command"
        ),
        EXIT (
                "exit",
                "Exits the application."
        );

        private String help;
        private String usage;

        ReservedCommands(String usage, String help) {
            this.usage = usage;
            this.help = help;
        }

        public String getHelp() {
            return help;
        }

        public String getUsage() {
            return usage;
        }
    }

    private static final String PROMPT = "> ";
    private static final String HELP = "help";
    private static final String EXIT = "exit";

    private final Scanner in;
    private final PrintStream out;
    private final CommandParser<E> parser;
    private final CommandParser<ReservedCommands> reservedParser;

    private boolean running;

    private final Class<E> commands;

    /**
     * Create a CMDView instance.
     * @param in Input stream to collect commands from.
     * @param out Output stream to print prompt and results to.
     * @param commands Class for the enum set of commands.
     */
    public CMDView(InputStream in, OutputStream out, Class<E> commands) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
        this.commands = commands;
        parser = new CommandParser<>(commands);
        reservedParser = new CommandParser<>(ReservedCommands.class);
    }

    /**
     * Run the CMDView. This is a blocking operation and should therefore be run
     * in a separate thread if any other layers need to run on top of this view.
     */
    public void run() {
        welcome();
        running = true;
        while (running) {
            out.print(PROMPT);
            String input = in.nextLine();
            if(matchReserved(HELP)) {
                Command help = reservedParser.parse(input);
                printHelp(help);
            }
            else if(matchReserved(EXIT)) {
                stop();
            }
            else {
                try {
                    Command command = parser.parse(input);
                    handleInput(command);
                }
                catch (IllegalArgumentException e) {
                    println("Error: Unknown command. Type \"help\" to get a list of available commands.");
                }
            }
        }
    }

    private boolean matchReserved(String input) {
        return input.toLowerCase().matches("^" + input + ".*");
    }

    private void printHelp(Command help) {
        String command = (String)help.getParameters().get("c");
        if(command != null) {
            if(command.equals(EXIT) || command.equals(HELP)) {
                helpCommand(ReservedCommands.valueOf(command.toUpperCase()));
            }
            else {
                helpCommand(E.valueOf(commands, command.toUpperCase()));
            }
        }
        else {
            help();
        }
    }

    /**
     * Prints a welcome message when the prompt starts to inform the user about
     * how to interact with the view.
     */
    protected abstract void welcome();

    /**
     * Handles input from the command line. Unless the stop method is called
     * from the handleInput method, the application will wait for the next
     * input after returning.
     * @param input Input to be handled.
     */
    protected abstract void handleInput(Command<E> input);

    /**
     * Prints a help section to the command line view with a list of all
     * available commands. Override this method to change the behaviour of the
     * "help" command.
     */
    protected void help() {
        E[] types = commands.getEnumConstants();
        println("Available commands:");
        StringBuilder line = new StringBuilder();
        for(E type : types) {
            line.append(getPrintableEnum(type)).append(" ");
        }
        for(ReservedCommands type : ReservedCommands.values()) {
            line.append(getPrintableEnum(type)).append(" ");
        }
        println(line.toString());
    }

    private String getPrintableEnum(Enum<?> e) {
        return e.toString().toLowerCase();
    }

    /**
     * Prints a help section for a specific command to the command line view.
     * @param type The command type to print the help section for.
     */
    protected void helpCommand(CommandType type) {
        println("Usage: " + type.getUsage());
        println("Description: " + type.getHelp());
    }

    /**
     * Stops the command line view.
     */
     private void stop() {
        running = false;
    }

    /**
     * Prints a line to the output stream.
     * @param output Line to be printed.
     */
    protected final void println(String output) {
        out.println(output);
    }
}
