import BC.Block;
import BC.Transaction;
import Miners.Miner;
import Utils.Utils;

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

    public static Block simulate(Transaction t){
        // add 20 more blocks to the chain
        Block next = createNextBlock(blockchain.get(blockchain.size()-1), t);
        Utils.log("created block");
        return next;
//        blockchain.add(next);
    }

    public static void main(String args[]) {
        // dummy transactions list
        ArrayList<Transaction> tlist = new ArrayList<>();
        tlist.add(new Transaction(12.0, 13.25, 100.0f, 120.0f));
        tlist.add(new Transaction(12.0, 13.25, 100.0f, 120.0f));
        tlist.add(new Transaction(12.0, 13.25, 100.0f, 120.0f));
        tlist.add(new Transaction(12.0, 13.25, 100.0f, 120.0f));
        tlist.add(new Transaction(12.0, 13.25, 100.0f, 120.0f));

        blockchain.add(createGenesisBlock()); // create genesis block and add it to the chain
        Utils.log("Added genesis block");
        for (Transaction t : tlist) {
            Block block = simulate(t);

            Utils.log("initial blockchain size " + blockchain.size());

            ArrayList<Miner> miners = new ArrayList<>();
            Miner.boi = block;
            Miner.reset();
            for (int i = 0; i < 5; i++) {
                Miner miner = new Miner("" + i, 0L, 2);
                miners.add(miner);
                miner.start();
            }

            for (int i = 0; i < miners.size(); i++) {
                try {
                    miners.get(i).join();
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted.");
                }
            }

            int true_c = 0, false_c = 0;
            for (Boolean validation : Miner.validation) {
                if (validation) true_c++;
                else false_c++;
            }

            // we just go ahead with true > false for now
            if (true_c > false_c) {
                Utils.log("True/False validation " + true_c + "/" + false_c);
                // this means the accident is validated
                // so we add the block
                blockchain.add(block);
            }

            Utils.log("current blockchain size " + blockchain.size());
        }
    }

}