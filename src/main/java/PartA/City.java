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




    public City(String Capital, String country, String coin, Long population_size)
    {
        docfrequency = new HashMap<>();
        this.country=country;
        this.coin=coin;

        this.population_size=population_Pattern(population_size);
        this.name=Capital;

    }
    public City(String name){
        docfrequency = new HashMap<>();
        this.name=name;

    }

    public boolean equals(City c1){
        return c1.name.equals(this.name);
    }

    /**
     * hashcode by the name of the city.
     * @return
     */
    public int hashCode(){
        return Objects.hash(this.name);
    }


    /**
     * Function to format the population.
     * @param population - long input
     * @return
     */
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

    //<editor-fold desc="Getters and Setters">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getPopulation_size() {
        return population_size;
    }

    public void setPopulation_size(String population_size) {
        this.population_size = population_size;
    }

    public HashMap<Doc, ArrayList<Integer>> getDocfrequency() {
        return docfrequency;
    }

    public void setDocfrequency(HashMap<Doc, ArrayList<Integer>> docfrequency) {
        this.docfrequency = docfrequency;
    }
    //</editor-fold>


}
