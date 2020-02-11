package Utils;
import BC.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CreateTransaction {
    protected double latitude, longitude,next_latitude,next_longitude;
    protected int distance;
    protected long time;
    protected ArrayList<String> direction;
    protected float acceleration,speed;
    public CreateTransaction (double start_latitude, double start_longitude, float acceleration, float speed, int distance, ArrayList<String> direction){
        this.latitude = start_latitude;
        this.longitude = start_longitude;
        this.acceleration = acceleration;
        this.speed = speed;
        this.distance = distance;
        this.direction = direction;

    }
    public ArrayList<Transaction> createTransaction(int count){
        ArrayList<Transaction> tbr = new ArrayList<>();
        time = System.currentTimeMillis();
        tbr.add(new Transaction(this.latitude,this.longitude,this.speed,this.distance,time,this.acceleration));
        float  new_speed;
        int angle ,time_taken;
        long new_time;
        next_latitude = this.latitude;
        next_longitude = this.longitude;
        new_speed = this.speed;
        double La1, Lo1, total_distance=this.distance;
        ArrayList<Double> coordinates;
        int count_number=1;
        new_time = time;
        for (String dir:direction){
            La1 = next_latitude;
            Lo1 = next_longitude;
                switch (dir) {
                    case "NW":
                        angle = 135;
                        break;
                    case "SW":
                        angle = 225;
                        break;
                    case "NE":
                        angle = 45;
                        break;
                    case "SE":
                        angle = 315;
                        break;
                    case "E":
                        angle = 0;
                        break;
                    case "W":
                        angle = 180;
                    case "N":
                        angle = 90;
                    case "S":
                        angle = 270;
                    default:
                        Utils.log(dir+": default section");
                        angle=0;
                        break;
                }
            coordinates = getPoints(next_latitude,next_longitude,angle, this.distance);
            next_latitude = coordinates.get(0);
            next_longitude = coordinates.get(1);
            //using Haversine formula to calculate distance.
            double R,P,A,B,La2,D,Lo2,lr1,lr2;
            La2 = next_latitude;Lo2=next_longitude;
            lr1 = Math.toRadians(La1);lr2 = Math.toRadians(La2);
            A = Math.toRadians(La2-La1)/2; B = Math.toRadians(Lo2-Lo1)/2;
            R = 6366565;
            P = Math.sqrt(Math.pow(Math.sin(A),2)+Math.cos(lr1)*Math.cos(lr2)*Math.pow(Math.sin(B),2));
            D = 2*R*Math.asin(P);
            Utils.log(Double.valueOf(D).toString());


            //equation to calculate the time required to travel a certain distance D by an accelerating body.
            time_taken = (int)(((Math.sqrt(Math.pow(new_speed,2)+2*this.acceleration*D)-new_speed)/this.acceleration)*1000);
            new_time = new_time + time_taken;
            new_speed = new_speed + this.acceleration*time_taken/1000;


            Utils.log("new speed" + new_speed);
            Utils.log("New time:" + new_time);
            total_distance += D;
            BigDecimal bd = new BigDecimal(Double.toString(total_distance));
            bd = bd.setScale(3, RoundingMode.HALF_UP);
            total_distance = bd.doubleValue();
            Utils.log(Double.valueOf(total_distance).toString());
            tbr.add(new Transaction(next_latitude,next_longitude,new_speed,total_distance,new_time,this.acceleration));
        }
        return tbr;
    }
    public ArrayList<Double> getPoints(double _lat, double _long, double _angle, double _distance){
        ArrayList<Double> points = new ArrayList<>();
        double new_lat, new_long, d_long,R=6366565;
        _lat = Math.toRadians(_lat);
        _long = Math.toRadians(_long);
        _angle = Math.toRadians(_angle);
        _distance = _distance/R;
        new_lat = Math.asin(Math.sin(_lat)*Math.cos(_distance)+Math.cos(_lat)*Math.sin(_distance)*Math.cos(_angle));

        /*Utils.log("old lat value: "+ Double.toString(Math.toDegrees(_lat)));
        Utils.log("lat value: "+ Double.toString(Math.toDegrees(new_lat)));
        */
        new_lat = Math.toDegrees(new_lat);
        BigDecimal bd = new BigDecimal(Double.toString(new_lat));
        bd = bd.setScale(6, RoundingMode.HALF_UP);
        new_lat = bd.doubleValue();

        points.add(new_lat);

        d_long = Math.atan2(Math.sin(_angle)*Math.sin(_distance)*Math.cos(_lat),Math.cos(_distance)-Math.sin(_lat)*Math.sin(new_lat));
        new_long = (_long-d_long+Math.toRadians(180))%(2*Math.toRadians(180))-Math.toRadians(180);
        new_long = Math.toDegrees(new_long);
        BigDecimal bd1 = new BigDecimal(Double.toString(new_long));
        bd1 = bd1.setScale(6, RoundingMode.HALF_UP);
        new_long = bd1.doubleValue();
        points.add(new_long);
        /*Utils.log("old long value: "+ Double.toString(Math.toDegrees(_long)));
        Utils.log("new long value: "+ Double.toString(new_long));
        */
        return points;

    }
}
