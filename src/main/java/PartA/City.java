package PartA;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class City {
    String name;
    String country;
    String coin;
    String population_size;
    HashMap<Doc,ArrayList<Integer>> docfrequency;


    public City(String Capital, String country,String coin,Long population_size)
    {
        docfrequency = new HashMap<>();
        this.country=country;
        this.coin=coin;
        this.population_size=population_Pattern(population_size);
        this.name="";

    }
    public City(String name){
        docfrequency = new HashMap<>();
        this.name=name;

    }

    public boolean equals(City c1){
        return c1.name.equals(this.name);
    }


    public int hashCode(){
        return Objects.hash(this.name);
    }

    public String population_Pattern(long population){
        String result = "";
        double d =population;
        DecimalFormat df = new DecimalFormat("#.00");
         if (d>1000000000)
        {
            d= d / 1000000000;
            result=df.format(d)+"B";
            return result;
        }
        else if(d>1000000){
            d= d / 1000000;
            result=df.format(d)+"M";
            return result;
        }

        else if(d>1000){
             d= d / 1000;
             result=df.format(d)+"K";
             return result;
        }
        return df.format(d);
    }


}
