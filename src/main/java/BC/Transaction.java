package BC;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Transaction {
    protected double longitude;
    protected double latitude;
    protected float avgSpeed;
    protected float deceleration;


    public Transaction(){}

    public Transaction(double _long, double _lat, float _avgs, float _decel){
        this.longitude = _long;
        this.latitude = _lat;
        this.avgSpeed = _avgs;
        this.deceleration = _decel;
    }

    public ArrayList<Double> getTransaction(){
        ArrayList<Double> local = new ArrayList<>();
        local.add(this.longitude);
        local.add(this.latitude);
        local.add((double)this.avgSpeed);
        local.add((double)this.deceleration);
        return local;
    }

    public String getHash(){
        return ""+longitude+""+latitude+""+avgSpeed+""+deceleration;
    }

    public String getJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
