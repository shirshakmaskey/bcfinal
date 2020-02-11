package BC;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Transaction {
    protected double longitude;
    protected double latitude;
    protected float avgSpeed, acceleration;
    protected double distance;
    protected long time;


    public Transaction(double _lat, double _long, float _avgs, double _distance, long _time, float _acceleration){
        this.longitude = _long;
        this.latitude = _lat;
        this.avgSpeed = _avgs;
        this.distance = _distance;
        this.time = _time;
        this.acceleration = _acceleration;
    }

    public ArrayList<Double> getTransaction(){
        ArrayList<Double> local = new ArrayList<>();
        local.add(this.longitude);
        local.add(this.latitude);
        local.add((double)this.avgSpeed);
        local.add((double)this.distance);
        local.add((double)this.time);
        return local;
    }

    public String getHash(){
        return ""+longitude+""+latitude+""+avgSpeed+""+distance+""+time;
    }

    public String getJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
