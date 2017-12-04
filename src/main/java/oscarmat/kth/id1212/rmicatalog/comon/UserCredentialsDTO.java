package oscarmat.kth.id1212.rmicatalog.comon;

import java.io.Serializable;

public class UserCredentialsDTO implements Serializable {

    private String username;
    private String password;

    public UserCredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
