package Miners;

import java.util.ArrayList;

import BC.Block;
import BC.Encryption;
import Utils.Utils;
import Vanet.AccidentVerify;

public class Miner extends Thread {
    public static boolean solutionClaimed = false;
    public static int claimerID = -1;
    public static int candidate = Integer.MIN_VALUE;
    public static ArrayList<Boolean> consensusList = new ArrayList<>();
    public static ArrayList<Boolean> validation = new ArrayList<>();
    public static int minerNum = 0;
    public static String final_nounce;
    public static Block boi,pboi;
    public static int bsize;

    private int difficulty;
    private long prevInfo;

    public int index;
    public String solution;


    public Miner(String minerID, long prevInfo, int difficulty) {
        super(minerID);
        index = minerNum;
        minerNum++;
//        consensusList.add(false);
        solution = "";
        this.prevInfo = prevInfo;
        this.difficulty = difficulty;
    }

    public static boolean numLeading0is(int amount, String hash) {
        boolean result;
        int count = 0;
        for (int i = 0; i < hash.length(); i++) {
            if (hash.charAt(i) == '0') {
                count++;
                //System.out.println("hash at char: "+hash.charAt(i)+" count: "+count);
            } else {
                break;
            }
        }
        //System.out.println("amount at pow: "+amount);
        result = count != amount;

        return result;
    }

    private boolean consensusAchieved()  {
        try {
            Thread.sleep(30*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int true_count = 0;
        int false_count = 0;

        for (Boolean aBoolean : consensusList) {
            if (!aBoolean) {
                false_count++;
            } else {
                true_count++;
            }
        }
        return true_count>false_count;
    }

    // called from inside the class Miner
    private void resetConsensus() {
        // 1, reset the consensusList to all false
        for (int i = 0; i < consensusList.size(); i++) {
            consensusList.set(i, false);
        }
        // 2, reset candidate
        candidate = Integer.MIN_VALUE;
        // 3, reset solutionClaimed to false
        solutionClaimed = false;
        // 4, reset claimerID to -1
        claimerID = -1;
    }

    // called from outside the class Miner
    public static void reset() {
        solutionClaimed = false;
        claimerID = -1;
        candidate = Integer.MIN_VALUE;
        //clear all elements from consensus list and validation list
        for (int i = 0; i < consensusList.size(); i++) {
            consensusList.removeAll(consensusList);
        }
        for (int i = 0; i < validation.size(); i++) {
            validation.removeAll(validation);
        }
        boi = null;
        pboi = null;
        bsize = 0;
        minerNum = 0;
        final_nounce = "";
    }

    @Override
    public void run() {
        System.out.println("Running miner thread: " + this.getName());
        int nounce = Integer.MIN_VALUE;
        // for now, we will set timer to 60s
        // if the validation is finished within 60 seconds it is displayed
        // else it only waits upto 60 seconds and displays whatever is present as validation
        Utils.log("Miner "+this.getName()+" start time: "+System.currentTimeMillis());
        long start_t = System.currentTimeMillis();
        while (numLeading0is(difficulty, Encryption.sha256("" + nounce + prevInfo))) {
            if(System.currentTimeMillis()-start_t > 60*1000){
                // means that the timeout threshold is breached
                // this means this miner is unable to mine so will have no effect on vote
                return;
            }
            nounce++;
            if (nounce == Integer.MAX_VALUE && numLeading0is(difficulty, Encryption.sha256("" + nounce + prevInfo))) {
                prevInfo++;
                nounce = Integer.MIN_VALUE;
            }
        }

        if(claimerID == -1) claimerID = index;
        candidate = nounce;
        validation.add(AccidentVerify.verify(Miner.boi, Miner.pboi));
        Utils.log("Miner "+this.getName()+" end time: "+System.currentTimeMillis());
//            if (solutionClaimed) {
//                // if someone else claims that a solution is found, verify that
//                if (numLeading0is(difficulty, Encryption.sha256("" + candidate + prevInfo))) {
//                    consensusList.set(index, true);
//                } else {
//                    // if this candidate fails the verification
//                    resetConsensus();
//                }
//            } else if (numLeading0is(difficulty, Encryption.sha256("" + nounce + prevInfo))) {
//                // if this miner finds a solution, report to the public, and wait for
//                // verification
//                solutionClaimed = true;
//                consensusList.set(index, true);
//                candidate = nounce;
//                claimerID = index;
//            }
//        }
        final_nounce = "" + candidate + prevInfo;
        System.out.println("Miner" + (this.index + 1) + "(" + this.getName() + ")" + " has approved that Miner"
                + (claimerID + 1) + " came up with the correct solution: " + "\"" + final_nounce + "\"");
    }

}