package oscarmat.kth.id1212.rmicatalog.server.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = File.LIST_OWNED_FILES,
                query = "SELECT file FROM File file WHERE " +
                        "owner = :owner"
        ),
        @NamedQuery(
                name = File.LIST_PUBLIC_FILES,
                query = "SELECT file FROM File file WHERE " +
                        "isPublic = true"
        ),
        @NamedQuery(
                name = File.SELECT_FROM_NAME,
                query = "SELECT file FROM File file WHERE " +
                        "name = :name"
        )
})

@Entity
@Table
public class File {

    public static final String LIST_OWNED_FILES = "listOwnedFiles";
    public static final String LIST_PUBLIC_FILES = "listPublicFiles";
    public static final String SELECT_FROM_NAME = "selectFromName";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private long size;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User owner;

    @Column(nullable = false)
    private boolean isPublic;

    @Column
    private boolean readonly;

    public File() {
    }

    public File(String name, long size, User owner) {
        this(name, size, owner, false, true);
    }

    public File(String name, long size, User owner, boolean isPublic, boolean readonly) {
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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
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

    @Override
    public String toString() {
        return "File [id=" + id + ", name: " + name + ", size: " + size +
                ", owner: " + owner.toString() + ", public: " + isPublic +
                ", readonly: " + readonly + "]";
    }
}
