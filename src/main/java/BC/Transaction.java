package BC;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Transaction {
    private Double longitute;
    private Double latitute;
    private Float avgSpeed;
    private Float decelaration;


    public String getHash(){
        return ""+longitute+""+latitute+""+avgSpeed+""+decelaration;
    }

    public String getJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
