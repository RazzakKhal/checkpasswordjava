

public class TheHashObject {
    private String hash;
    private String salt;
    private String id;

    public TheHashObject() {
    }

    public TheHashObject(String hash, String salt, String id) {
        this.hash = hash;
        this.salt = salt;
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
