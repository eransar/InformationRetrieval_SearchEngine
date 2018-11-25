package PartA;

import java.util.ArrayList;
import java.util.HashMap;

public class City {

    String country;
    String coin;
    String population_size;
    HashMap<Doc,ArrayList<Integer>> docfrequency;


    public City(String country,String coin,Long population_size)
    {
        docfrequency = new HashMap<>();

    }
    public City(){

    }

}
