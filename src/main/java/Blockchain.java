import BC.Block;
import BC.Transaction;
import Miners.Miner;
import Utils.CreateTransaction;
import Utils.Utils;

import java.util.ArrayList;
public class Blockchain {
    public static ArrayList<Block> blockchain = new ArrayList<>();
    // The blockchain is implemented as an ArrayList of Blocks
    // and the block is implemented as ArrayList of transactions.

    public final static int NUM_BLOCKS = 20;
    public final static int MINER_NUM = 3;


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
        //vary transaction window using the timestamp value and for the distance bound method for
        //calculating the timestamp gap for next transaction
        ArrayList<String> dir= new ArrayList<>();
        dir.add("NE");dir.add("E");dir.add("NE");dir.add("E");
        CreateTransaction create = new CreateTransaction(39.865147,-84.058723,4,10f,40,dir);
        tlist = create.createTransaction(dir.size());
        for (Transaction t:tlist){
            System.out.println(t.getJSON());
        }
        System.exit(0);
        //tlist.add(new Transaction(-84.058723, 39.865147, 10.0f, 20f,1));
        //tlist.add(new Transaction(-84.058773, 39.865197, 7.0f, 1430f,2));
        //tlist.add(new Transaction(-119.839539, 39.550863, 10.0f, 2481f,3));
        //tlist.add(new Transaction(-119.869845, 39.560253, 12.0f, 4924f,4));
        //tlist.add(new Transaction(-119.861845, 39.560356, 100.0f, 4445f,5));
        // create genesis block and add it to the chain
        Utils.log("total Start time: "+System.currentTimeMillis());
        blockchain.add(createGenesisBlock());
        //Log all events in the console for better readability
        Utils.log("Added genesis block");
        //Loop through all transactions in the list
        for (Transaction t : tlist) {
            //Simulate blockchain mining using a single block
            Block block = simulate(t);
            //Utils.log("initial blockchain size " + blockchain.size());
            //Create an array list of class miner objects.
            ArrayList<Miner> miners = new ArrayList<>();
            Miner.reset();
            Miner.bsize = blockchain.size();
            Miner.boi = block;
            if(Miner.bsize == 1){ Miner.pboi = null; } else {Miner.pboi = Miner.pboi = blockchain.get(1);;}
            for (int i = 0; i < MINER_NUM; i++) {
                //create a new miner object from miner class
                Miner miner = new Miner("" + i, 0L, 5);
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
            Utils.log("True/False validation " + true_c + "/" + false_c);
            if (true_c > false_c) {
                // this means the accident is validated
                // so we add the block
                blockchain.add(block);
            }
            //if the accident is not validated it is ignored from the blockchain
            //finally publish the blockchain size at the end of the mining
            Utils.log("current blockchain size " + blockchain.size());
        }
        Utils.log("Total end time: "+System.currentTimeMillis());
        printChain();
    }

}