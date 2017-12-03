package oscarmat.kth.id1212.rmicatalog.server.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "selectAllFiles",
                query = "SELECT file FROM File file"
        )
})

@Entity
@Table
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int size;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User owner;

    @Column(nullable = false)
    private boolean isPublic;

    @Column
    private boolean readonly;

    public File() {
    }

    public File(String name, int size, User owner) {
        this(name, size, owner, false, true);
    }

    public File(String name, int size, User owner, boolean isPublic, boolean readonly) {
        this.name = name;
        this.size = size;
        this.owner = owner;
        this.isPublic = isPublic;
        this.readonly = readonly;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
}
