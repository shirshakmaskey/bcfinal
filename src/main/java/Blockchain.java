import BC.Block;
import BC.Transaction;

import java.util.ArrayList;
public class Blockchain {
    public static ArrayList<Block> blockchain = new ArrayList<>(); // The blockchain is implemented as an arraylist of Blocks

    public final static int NUM_BLOCKS = 20;


    public static Block createGenesisBlock() {
        return new Block(0, null, "0");
    }

    public static Block createNextBlock(Block prevBlock, Transaction transaction) {
        return new Block(prevBlock.index + 1, transaction, prevBlock.hash);
    }

    public static void printChain() {
        for (int i = 0; i < blockchain.size(); i++) {
            System.out.println(blockchain.get(i));
        }
    }

    public static void main(String args[]) {
        blockchain.add(createGenesisBlock()); // create genesis block and add it to the chain
        // add 20 more blocks to the chain
        for (int i = 0; i < NUM_BLOCKS; i++) {
            Block next = createNextBlock(blockchain.get(i), new Transaction());
            blockchain.add(next);
        }
        printChain();
    }

}