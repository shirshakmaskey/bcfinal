import java.util.Date;

public class Block {
    public int index;
    public long timestamp;
    public String data;
    public String prev_hash;
    public String hash;

    public Block(int index, String data, String prev_hash) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.data = data;
        this.prev_hash = prev_hash;
        this.hash = getHash();
    }

    private String getHash() {
        return Encryption.sha256(this.index + this.timestamp + this.data + this.prev_hash);
    }

    public String toString() {
        return ("Block #" + this.index + "\n\tmined at: " + this.timestamp + "\n\tData: " + this.data + "\n\tHash: {"
                + this.hash + "}\n");
    }
}
