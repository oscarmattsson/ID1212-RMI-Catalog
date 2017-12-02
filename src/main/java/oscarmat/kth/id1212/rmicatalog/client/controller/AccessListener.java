package oscarmat.kth.id1212.rmicatalog.client.controller;

/**
 * Notifies a listener when a file is accessed on the server.
 */
public interface AccessListener {

    /**
     * Notify the listener that a file has been accessed.
     * @param user User who accessed the file.
     * @param file File that was accessed.
     */
    public void notifyAccess(String user, String file);

}
