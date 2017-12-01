package oscarmat.kth.id1212.cmdview;

import java.util.HashMap;
import java.util.Map;

/**
 * Parses input from the command line into command objects using the supplied
 * enum for the set of commands.
 * Expects input on the form: <command> [-<parameter> <value> ...]
 * @param <E> Enum set of commands.
 */
public class CommandParser<E extends Enum<E> & CommandType>{

    private static final String SEPARATOR = " ";
    private static final String PARAM_IDENTIFIER = "-";

    private final Class<E> commands;

    /**
     * Create a parser using the given command set.
     * @param commands Enum set of commands to use.
     */
    public CommandParser(Class<E> commands) {
        this.commands = commands;
    }

    /**
     * Parses a catalog command from the user commandline interface.
     * @param command Command to be parsed.
     * @return Parsed command.
     * @throws IllegalArgumentException If the command type is not recognized.
     */
    public Command parse(String command) {
        E type  = getType(getTypeString(command));
        Map<String, String> parameters = getParameters(getParametersString(command));
        return new Command<E>(type, parameters);
    }

    private E getType(String typeString) throws IllegalArgumentException {
        return Enum.valueOf(commands, typeString.toUpperCase());
    }

    private String getTypeString(String command) {
        int cut = command.indexOf(SEPARATOR);
        if(cut != -1) {
            return command.substring(0, command.indexOf(SEPARATOR));
        }
        else {
            return command;
        }
    }

    private Map<String, String> getParameters(String[] parameterStrings) {
        Map<String, String> parameters = new HashMap<String, String>();
        for(String parameter : parameterStrings) {
            String[] items = parameter.split(SEPARATOR);
            String name = items[0];
            String value = "";
            if(items.length > 1) {
                value = items[1];
            }
            parameters.put(name, value);
        }
        return parameters;
    }

    private String[] getParametersString(String command) {
        int cut = command.indexOf(PARAM_IDENTIFIER);
        if(cut != -1) {
            String params = command.substring(cut + 1);
            return params.split(PARAM_IDENTIFIER);
        }
        else {
            return new String[0];
        }
    }
}
