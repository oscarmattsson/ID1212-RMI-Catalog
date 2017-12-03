package oscarmat.kth.id1212.rmicatalog.client.view;

import oscarmat.kth.id1212.cmdview.CommandType;

public enum CatalogCommandType implements CommandType {
    REGISTER(
            "register -u <username> -p <password>\n",
            "Register a new user in the catalog.\n\t" +
                    "-u Supply unique username to register with.\n\t" +
                    "-p Submit password to be used with login."
    ),
    UNREGISTER(
            "unregister",
            "Unregister the active user. This action requires that you are " +
                    "logged in."
    ),
    LOGIN(
            "login -u <username> -p <password>",
            "Log in to the catalog.\n\t" +
                    "-u Submit username to log in with.\n\t" +
                    "-p Submit password to log in with."
    ),
    LOGOUT(
            "logout",
            "Log out the active user from the catalog. This action requires " +
                    "that you are logged in."
    ),
    UPLOAD(
            "upload -f <file> [-public <permissions>]",
            "Upload a file to the catalog. The file can either be private " +
                    "(default) or public. This action requires that you are " +
                    "logged in.\n\t" +
                    "-f Local path to the file you want to upload.\n\t" +
                    "-public Sets the file as public, meaning it can be listed " +
                    "by other users. Read and write permissions for other users " +
                    "are set as a number which is the sum of permissions where " +
                    "read is 1 and write is 2."
    ),
    DOWNLOAD(
            "download -f <file>",
            "Download a file from the catalog. This action requires that you " +
                    "are logged in and that you have read permissions on the " +
                    "file you are requesting.\n\t" +
                    "-f Name of the file in the catalog."
    ),
    NOTIFY(
            "notify -f <file> [-c]",
            "Tell the server that you want to be notified when other users " +
                    "access one of your public files for the rest of this " +
                    "session. This action requires that you are logged in.\n\t" +
                    "-f Name of your public file that you want notifications " +
                    "from."
    ),
    LIST(
            "list",
            "List your private files and public files from other users in the " +
                    "catalog. This action requires that you are logged in."
    ),
    CONNECT(
            "connect -h <host>",
            "Connect to a catalog server. This action must precede any other " +
                    "operation.\n\t" +
                    "-h Host name or IP address of catalog server."
    ),
    DISCONNECT(
            "disconnect",
            "Disconnect from the current catalog server."
    );

    private String help;
    private String usage;

    CatalogCommandType(String usage, String help) {
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
