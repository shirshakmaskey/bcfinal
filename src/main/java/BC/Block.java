package BC;

import java.util.Date;

public class Block {
    public int index;
    public long timestamp;
    public Transaction data;
    public String prev_hash;
    public String hash;

    public Block(int index, Transaction data, String prev_hash) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.data = data;
        this.prev_hash = prev_hash;
        this.hash = getHash();
    }

    private String getHash() {
        return Encryption.sha256(this.index + this.timestamp + (this.data == null? "":this.data.getHash()) + this.prev_hash);
    }

    public String toString() {
        return ("BC.Block #" + this.index + "\n\tmined at: " + this.timestamp + "\n\tData: " + (this.data == null?"{}":this.data.getJSON()) + "\n\tHash: {"
                + this.hash + "}\n");
    }
}
