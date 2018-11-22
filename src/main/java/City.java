import java.util.ArrayList;
import java.util.HashMap;

public class City {

    String country;
    String coin;
    String population_size;
    HashMap<Doc,ArrayList<Integer>> docfrequency;


    public City(){
        docfrequency = new HashMap<>();
    }

}
