package oscarmat.kth.id1212.rmicatalog.comon;

import java.io.Serializable;

public class File implements Serializable {

    private String name;
    private int size;
    private boolean isPublic;
    private boolean isReadonly;

    public File(String name, int size, boolean isPublic, boolean isReadonly) {
        this.name = name;
        this.size = size;
        this.isPublic = isPublic;
        this.isReadonly = isReadonly;
    }

    public File(String name, int size) {
        this(name, size, false, true);
    }
}
