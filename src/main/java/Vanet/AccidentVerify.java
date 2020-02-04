package Vanet;
import BC.Block;
import BC.Transaction;

import java.util.ArrayList;

public class AccidentVerify {
    public static Boolean verify(Block boi, Block pboi){
        if (pboi == null){
            return true;
        }
        Transaction local = boi.data;
        Transaction prev_local = pboi.data;
        ArrayList<Double> local_array,prev_local_array;
        local_array = local.getTransaction();
        prev_local_array = prev_local.getTransaction();

        double longitude = local_array.get(0);
        double latitude = local_array.get(1);
        double avgSpeed = local_array.get(2);
        double distance = local_array.get(3);
        //double deceleration = local_array.get(4);

        double prev_longitude = prev_local_array.get(0);
        double prev_latitude = prev_local_array.get(1);
        double prev_avgSpeed = prev_local_array.get(2);
        double prev_distance = prev_local_array.get(3);
        //double prev_deceleration = local_array.get(4);
        System.out.println("Old data: "+prev_longitude+" "+prev_latitude+" "+prev_avgSpeed+" "+prev_distance);
        System.out.println("New data: "+longitude+" "+latitude+" "+avgSpeed+" "+distance);

        //using Haversine formula to calculate distance.
        //double R,P,A,B,La1,La2,D,Lo1,Lo2;
        //La1 = prev_latitude; La2 = latitude;Lo1=prev_longitude;Lo2=longitude;
        //A = (La2-La1)/2; B = (Lo2-Lo1)/2;
        //R = 6400*1000;
        //P = Math.sqrt(Math.pow(Math.sin(A),2)+Math.cos(La1)*Math.cos(La2)*Math.pow(Math.sin(B),2));
        //D = 2*R*Math.asin(P);
        //get the answer and compare it with distance
        //return D == distance;
        //System.out.println("this is local data: "+ local_array.toString());
        return true;
    }
}
