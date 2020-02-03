import BC.Block;
import BC.Transaction;
import Miners.Miner;
import Utils.Utils;

import java.util.ArrayList;
public class Blockchain {
    public static ArrayList<Block> blockchain = new ArrayList<>();
    // The blockchain is implemented as an ArrayList of Blocks
    // and the block is implemented as ArrayList of transactions.

    public final static int NUM_BLOCKS = 20;
    public final static int MINER_NUM = 5;


    public static Block createGenesisBlock() {
        return new Block(0, null, "0");
    }

    public static Block createNextBlock(Block prevBlock, Transaction transaction) {
        return new Block(prevBlock.index + 1, transaction, prevBlock.hash);
    }

    public static void printChain() {
        for (Block block : blockchain) {
            System.out.println(block);
        }
    }

    public static Block simulate(Transaction t){
        //Create next block in blockchain
        Block next = createNextBlock(blockchain.get(blockchain.size()-1), t);
        Utils.log("created block");
        return next;
    }

    public static void main(String[] args) {
        // dummy transactions list
        ArrayList<Transaction> tlist = new ArrayList<>();
        //Add multiple transaction to a transaction list.
        tlist.add(new Transaction(12.0, 13.25, 100.0f, 120.0f));
        tlist.add(new Transaction(12.0, 13.25, 100.0f, 120.0f));
        tlist.add(new Transaction(12.0, 13.25, 100.0f, 120.0f));
        tlist.add(new Transaction(12.0, 13.25, 100.0f, 120.0f));
        tlist.add(new Transaction(12.0, 13.25, 100.0f, 120.0f));
        // create genesis block and add it to the chain
        blockchain.add(createGenesisBlock());
        //Log all events in the console for better readability
        Utils.log("Added genesis block");
        //Loop through all transactions in the list
        for (Transaction t : tlist) {
            //Simulate blockchain mining using a single block
            Block block = simulate(t);
            Utils.log("initial blockchain size " + blockchain.size());
            //Create an array list of class miner objects.
            ArrayList<Miner> miners = new ArrayList<>();
            Miner.boi = block;
            Miner.reset();
            for (int i = 0; i < MINER_NUM; i++) {
                //create a new miner object from miner class
                Miner miner = new Miner("" + i, 0L, 2);
                //add miner to the miner array list
                miners.add(miner);
                //start the miner thread
                miner.start();
            }

            for (Miner miner : miners) {
                try {
                    //wait for all threads to be executed
                    miner.join();
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted.");
                }
            }

            int true_c = 0, false_c = 0;
            for (Boolean validation : Miner.validation) {
                if (validation) true_c++;
                else false_c++;
            }
            // compares the total validation count between true amd false
            if (true_c > false_c) {
                Utils.log("True/False validation " + true_c + "/" + false_c);
                // this means the accident is validated
                // so we add the block
                blockchain.add(block);
            }
            //if the accident is not validated it is ignored from the blockchain
            //finally publish the blockchain size at the end of the mining
            Utils.log("current blockchain size " + blockchain.size());
        }
    }

}