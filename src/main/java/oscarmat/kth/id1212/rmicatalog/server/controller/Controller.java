package oscarmat.kth.id1212.rmicatalog.server.controller;

import oscarmat.kth.id1212.rmicatalog.comon.CatalogClient;
import oscarmat.kth.id1212.rmicatalog.comon.CatalogServer;
import oscarmat.kth.id1212.rmicatalog.comon.FileDTO;
import oscarmat.kth.id1212.rmicatalog.comon.UserCredentialsDTO;
import oscarmat.kth.id1212.rmicatalog.server.integration.DBHandler;
import oscarmat.kth.id1212.rmicatalog.server.model.File;
import oscarmat.kth.id1212.rmicatalog.server.model.User;
import oscarmat.kth.id1212.rmicatalog.server.view.Logger;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Controller extends UnicastRemoteObject implements CatalogServer {

    private final Logger logger;
    private final DBHandler db;

    private Map<CatalogClient, User> loggedInUsers;
    private Map<String, Set<CatalogClient>> fileAccessListeners;

    public Controller(Logger logger, DBHandler db) throws RemoteException {
        loggedInUsers = new HashMap<>();
        fileAccessListeners = new HashMap<>();
        this.logger = logger;
        this.db = db;
    }

    @Override
    public String register(CatalogClient client, UserCredentialsDTO credentials) throws RemoteException {
        User user = new User();
        user.setUsername(credentials.getUsername());
        user.setPassword(credentials.getPassword());
        try {
            db.getUserDAO().persist(user);
            logger.putInfo("Registered new user - " +
                    "Username: " + user.getUsername() + ", " +
                    "Password: " + user.getPassword());
            return "Successfully added user <" +
                    user.getUsername() +
                    "> to the catalog.";
        }
        catch (PersistenceException e) {
            logger.putWarning("Attempted to register new user <" +
                    user.getUsername() +
                    "> but failed.");
            return "Failed to add user: " + e.getMessage();
        }
    }

    @Override
    public String unregister(CatalogClient client) throws RemoteException {
        if(loggedInUsers.get(client) != null) {
            db.getUserDAO().delete(loggedInUsers.get(client));
            String name = loggedInUsers.get(client).getUsername();
            loggedInUsers.remove(client);
            logger.putInfo("Successfully unregistered user <" + name + " from " +
                    "the catalog.");
            return "Successfully removed user <" + name + "> from the catalog." +
                    " You are now logged out.";
        }
        else {
            logger.putWarning("Failed to unregister, user not logged in.");
            return "Error, could not unregister your user because you are not " +
                    "logged in.";
        }
    }

    @Override
    public String login(CatalogClient client, UserCredentialsDTO credentials) throws RemoteException {
        User user = db.getUserDAO().findByUsername(credentials.getUsername());
        if(user == null) {
            logger.putWarning("Failed to log in, no user <" +
                    credentials.getUsername() + "> exists.");
            return "Error, no such user in the database.";
        }
        else if(user.getPassword().equals(credentials.getPassword())) {
            loggedInUsers.put(client, user);
            logger.putInfo("User <" + user.getUsername() + "> logged in.");
            return "You are now logged in as <" + user.getUsername() + ">.";
        }
        else {
            logger.putWarning("Attempt to log in as user <" +
                    user.getUsername() +
                    "> with an invalid password.");
            return "Error, your password was incorrect.";
        }
    }

    @Override
    public String logout(CatalogClient client) throws RemoteException {
        if(loggedInUsers.get(client) != null) {
            String name = loggedInUsers.get(client).getUsername();
            loggedInUsers.remove(client);
            logger.putInfo("User <" + name + "> has logged out.");
            return "You have successfully logged out.";
        }
        else {
            logger.putInfo("Logout attempt from client who is not logged in.");
            return "Error, you are not logged in.";
        }
    }

    @Override
    public String upload(CatalogClient client, FileDTO dto) throws RemoteException {
        if(loggedInUsers.get(client) != null) {
            File file = new File();
            file.setName(dto.getName());
            file.setSize(dto.getSize());
            file.setOwner(loggedInUsers.get(client));
            file.setPublic(dto.isPublic());
            file.setReadonly(dto.isReadonly());
            try {
                File tmp = db.getFileDAO().findByName(dto.getName());
                if(tmp.getOwner().getUsername().equals(
                        loggedInUsers.get(client).getUsername()
                ) || (tmp.isPublic() && !tmp.isReadonly())) {
                    file.setId(tmp.getId());
                    db.getFileDAO().update(file);
                    logger.putInfo("Updated file <" + file.getName() + ">.");
                    notifyFileAccessed(
                            loggedInUsers.get(client).getUsername(),
                            file.getName());
                    return "File already existed, overwrote with new file.";
                }
                else {
                    logger.putWarning("User <" +
                            loggedInUsers.get(client).getUsername() +
                            "> attempted to alter file <" + file.getName() +
                            "> without permissions.");
                    return "Error, this file already exists and you lack " +
                            "permissions to alter it.";
                }
            }
            catch (NoResultException e) {
                db.getFileDAO().persist(file);
                logger.putInfo("User <" +
                        loggedInUsers.get(client).getUsername() +
                        "> uploaded file <" + file.getName() + ">.");
                return "Successfully uploaded file <" + file.getName() + "> " +
                        "to catalog.";
            }
            catch (Exception e) {
                logger.putError("Error uploading file: " + e.getMessage());
                return "Error uploading file.";
            }
        }
        else {
            return "Error, you are not logged in.";
        }
    }

    private void notifyFileAccessed(String username, String filename) throws RemoteException {
        Set<CatalogClient> clients = fileAccessListeners.get(filename);
        if(clients != null) {
            for (CatalogClient client : clients) {
                client.notifyAccessed(username, filename);
            }
        }
    }

    @Override
    public String download(CatalogClient client, String filename) throws RemoteException {
        if(loggedInUsers.get(client) != null) {
            try {
                File file = db.getFileDAO().findByName(filename);
                if (file.getOwner().getUsername().equals(
                        loggedInUsers.get(client).getUsername()
                ) || file.isPublic()) {
                    notifyFileAccessed(
                            loggedInUsers.get(client).getUsername(),
                            file.getName());
                    logger.putInfo("User <" +
                            loggedInUsers.get(client).getUsername() +
                            "> has downloaded file <" + file.getName() + ">.");
                    return "Downloaded file: " + file.toString();
                } else {
                    logger.putWarning("User <" +
                            loggedInUsers.get(client).getUsername() +
                            "> attempted to download file <" + file.getName() +
                            "> without permissions.");
                    return "Error, you lack permissions to download this file.";
                }
            } catch (NoResultException e) {
                logger.putWarning("User <" +
                        loggedInUsers.get(client).getUsername() +
                        "> tried to download nonexistent file.");
                return "Error, no such file exists. Failed to download file.";
            }
        }
        else {
            return "Error, you are not logged in.";
        }
    }

    @Override
    public String delete(CatalogClient client, String filename) throws RemoteException {
        if(loggedInUsers.get(client) != null) {
            try {
                File file = db.getFileDAO().findByName(filename);
                if(file.getOwner().getUsername().equals(
                        loggedInUsers.get(client).getUsername()
                ) || (file.isPublic() && !file.isReadonly())) {
                    db.getFileDAO().delete(file);
                    logger.putInfo("Deleted file <" + file.getName() + ">.");
                    notifyFileAccessed(
                            loggedInUsers.get(client).getUsername(),
                            file.getName());
                    return "Deleted file <" + file.getName() + ">.";
                }
                else {
                    logger.putWarning("User <" +
                            loggedInUsers.get(client).getUsername() +
                            "> attempted to delete file <" + file.getName() +
                            "> without permissions.");
                    return "Error, you lack permissions to delete this file.";
                }
            }
            catch (NoResultException e) {
                logger.putWarning("User <" +
                        loggedInUsers.get(client).getUsername() +
                        "> tried to delete nonexistent file.");
                return "Error, no such file exists. failed to delete file.";
            }
        }
        else {
            return "Error, you are not logged in.";
        }
    }

    @Override
    public String setNotifyAccess(CatalogClient client, String filename) throws RemoteException {
        if(loggedInUsers.get(client) != null) {
            Set<CatalogClient> clients = fileAccessListeners.get(filename);
            if(clients == null) {
                clients = new HashSet<>();
                fileAccessListeners.put(filename, clients);
            }
            clients.add(client);
            return "Started listening for changes on file <" + filename + ">";
        }
        else {
            return "Error, you are not logged in.";
        }
    }

    @Override
    public String listFiles(CatalogClient client) throws RemoteException {
        if(loggedInUsers.get(client) != null) {
            StringBuilder sb = new StringBuilder();
            List<File> ownedFiles = db.getFileDAO().listOwnedFiles(loggedInUsers.get(client));
            sb.append("Your files: ");
            for(File file : ownedFiles) {
                sb.append("\n\t").append(file);
            }
            List<File> publicFiles = db.getFileDAO().listPublicFiles();
            sb.append("\n").append("Public files: ");
            for(File file : publicFiles) {
                sb.append("\n\t").append(file);
            }
            return sb.toString();
        }
        else {
            return "Error, you are not logged in.";
        }
    }
}
