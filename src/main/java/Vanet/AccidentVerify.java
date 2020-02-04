package Vanet;
import BC.Block;
import BC.Transaction;
import Utils.Utils;

import java.util.ArrayList;

public class AccidentVerify {
    public static Boolean verify(Block boi, Block pboi){
        if (pboi == null){
            return true;
        }
        int rand = (int)(Math.random()*11);
        if(rand>2){
            Utils.log("start of validation: "+System.currentTimeMillis());
            Transaction local = boi.data;
            Transaction prev_local = pboi.data;
            ArrayList<Double> local_array,prev_local_array;
            local_array = local.getTransaction();
            prev_local_array = prev_local.getTransaction();

            double longitude = local_array.get(0);
            double latitude = local_array.get(1);
            double avgSpeed = local_array.get(2);
            double distance = local_array.get(3);
            //System.out.println("distance: "+(int)distance+" double: "+distance);
            double time = local_array.get(4);
            //double deceleration = local_array.get(4);

            double prev_longitude = prev_local_array.get(0);
            double prev_latitude = prev_local_array.get(1);
            double prev_avgSpeed = prev_local_array.get(2);
            double prev_distance = prev_local_array.get(3);
            double prev_time = prev_local_array.get(4);
            //double prev_deceleration = local_array.get(4);
            //System.out.println("Old data: "+prev_longitude+" "+prev_latitude+" "+prev_avgSpeed+" "+prev_distance);
            //System.out.println("New data: "+longitude+" "+latitude+" "+avgSpeed+" "+distance);

            //using Haversine formula to calculate distance.
            double R,P,A,B,La1,La2,D,Lo1,Lo2,lr1,lr2,dis;
            dis = distance-prev_distance;
            La1 = prev_latitude; La2 = latitude;Lo1=prev_longitude;Lo2=longitude;
            lr1 = Math.toRadians(La1);lr2 = Math.toRadians(La2);
            A = Math.toRadians(La2-La1)/2; B = Math.toRadians(Lo2-Lo1)/2;
            R = 6400*1000;
            P = Math.sqrt(Math.pow(Math.sin(Math.toRadians(A)),2)+Math.cos(Math.toRadians(lr1))*Math.cos(Math.toRadians(lr2))*Math.pow(Math.sin(Math.toRadians(B)),2));
            D = 2*R*Math.asin(P);
            //get the answer and compare it with distance
            Utils.log("validation end time "+System.currentTimeMillis());
            //Utils.log("D: "+(int)D+" distance: "+(int)dis);
            return (int) D == (int) dis;
        }
        else {
            int rand_new = (int)(Math.random()*2);
            return rand_new == 1;
        }

    }
}
