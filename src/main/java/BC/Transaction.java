package BC;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Transaction {
    private Double longitute;
    private Double latitute;
    private Float avgSpeed;
    private Float decelaration;


    public Transaction(){}

    public Transaction(Double _long, Double _lat, Float _avgs, Float _decel){
        this.longitute = _long;
        this.latitute = _lat;
        this.avgSpeed = _avgs;
        this.decelaration = _decel;
    }

    public String getHash(){
        return ""+longitute+""+latitute+""+avgSpeed+""+decelaration;
    }

    public String getJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
