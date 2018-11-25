package PartA;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CityIndexer {
    private static CityIndexer ourInstance = new CityIndexer();
    public HashMap<String,City> dict_city;
    public Object[] parsed_json;
    public String URL;

    public static CityIndexer getInstance() {
        return ourInstance;
    }

    private CityIndexer() {
        dict_city =new HashMap<>();
        URL="https://restcountries.eu/rest/v2/?fields=name;capital;currencies;population";
    }

    public void addToCityIndexer(Doc doc,int index){

        /* if city not found in the index*/
        if(dict_city.get(doc.getCITY())==null){
            /**
             * Create new City
             * Create new array for the city hashmap
             * Add the index position to the array
             * Add the city to the city indexer
             */
            City OtherCity= new City();
            ArrayList<Integer> positions = new ArrayList<>();
            positions.add(index);
            OtherCity.docfrequency.put(doc,positions);
            dict_city.put(doc.getCITY(),OtherCity);
        }
        else{ /* this means the city is in the hashmap */
            City currentcity= dict_city.get(doc.getCITY());
            /**
             * Check if doc found
             */
            if(currentcity.docfrequency.get(doc)==null){
                ArrayList<Integer> positions = new ArrayList<>();
                positions.add(index);
                currentcity.docfrequency.put(doc,positions);
            }
            else{
                ArrayList<Integer> positions = currentcity.docfrequency.get(doc);
                positions.add(index);
                currentcity.docfrequency.put(doc,positions);
            }
            dict_city.put(doc.getCITY(),currentcity);
        }

    }
    public void startConnection(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(URL).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            try {
                obj = parser.parse(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(obj != null){
                parsed_json= ((JSONArray) obj).toArray();

            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }


}
