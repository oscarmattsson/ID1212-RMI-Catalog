package oscarmat.kth.id1212.cmdview;

import java.util.Map;

/**
 * Represents a parsed input from the command line view.
 * @param <E> Enum set of commands which can be used.
 */
public class Command<E extends Enum<E> & CommandType> {

    private final E type;
    private final Map<String, String> parameters;

    /**
     * Create a new command object.
     * @param type Command type from the supplied enum.
     * @param parameters Parameter key-value pairs.
     */
    public Command(E type, Map<String, String> parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    /**
     * @return The command type.
     */
    public E getType() {
        return type;
    }

    /**
     * @param key Name of the parameter.
     * @return The value of the parameter.
     */
    public String getParameter(String key) {
        return parameters.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Type: ").append(getType()).append("\n");
        for(Map.Entry<String, String> entry : parameters.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
