package oscarmat.kth.id1212.rmicatalog.server.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = User.SELECT_FROM_USERNAME,
                query = "SELECT user FROM User user WHERE username = :username"
        )
})

@Entity
@Table
public class User {

    public static final String SELECT_FROM_USERNAME = "selectFromUsername";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username: " + username + ", password: " + password + "]";
    }
}
