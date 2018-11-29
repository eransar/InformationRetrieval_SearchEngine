package PartA;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CityIndexer {
    private static CityIndexer ourInstance = new CityIndexer();
    public HashMap<String,City> dict_cache;
    public HashMap<String,String> dict_city;
    public Object[] parsed_json;
    public String URL;

    public static CityIndexer getInstance() {
        return ourInstance;
    }

    private CityIndexer() {
        dict_city=new HashMap<>();
        dict_cache =new HashMap<>();
        URL="https://restcountries.eu/rest/v2/?fields=name;capital;currencies;population";

    }

    public void addToCityIndexer(Doc doc,int index){

        /* if city not found in the index*/
        if(dict_cache.get(doc.getCITY())==null){
            /**
             * Create new City
             * Create new array for the city hashmap
             * Add the index position to the array
             * Add the city to the city indexer
             */
            City OtherCity= new City(doc.getCITY());
            ArrayList<Integer> positions = new ArrayList<>();
            positions.add(index);
            OtherCity.docfrequency.put(doc,positions);
            dict_cache.put(doc.getCITY(),OtherCity);
        }
        else{ /* this means the city is in the hashmap */
            City currentcity= dict_cache.get(doc.getCITY());
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
            dict_cache.put(doc.getCITY(),currentcity);
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
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(obj != null){
                String capital="",country="",code="";
                Long population=0L;
                parsed_json= ((JSONArray) obj).toArray();
                for(Object s: parsed_json){
                    capital = (String)((JSONObject)s).get("capital");
                    country = (String)((JSONObject)s).get("name");
                    JSONArray jsonArray = (JSONArray)(((JSONObject)s).get("currencies"));
                    for(Object ss : jsonArray){
                        code = (String)((JSONObject)ss).get("code");
                    }
                    population = (Long)((JSONObject)s).get("population");
                    dict_cache.put(capital,new City(capital,country,code,population));
                }

            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    public void WriteDictionary(String postingPath ) throws IOException {
        File toWrite=new File(postingPath+File.separator+"cities.txt");
        FileWriter writer = null;
        writer = new FileWriter(toWrite,false);
        int linenumber = 1;

        for(Map.Entry<String, City > capitals : dict_cache.entrySet()){
            if(!capitals.getValue().name.equals("")){
                StringBuilder doc_content = new StringBuilder("");
                int counter=0;
                int size=capitals.getValue().docfrequency.size();
                for(Map.Entry<Doc, ArrayList<Integer>> doc : capitals.getValue().docfrequency.entrySet()){

                    doc_content.append(doc.getKey().getDOCNO()+","+doc.getKey().getFile()+",");
                    for (int i = 0; i < doc.getValue().size() ; i++) {
                        if(i==doc.getValue().size()-1){
                            doc_content.append(i);
                        }
                        else{
                            doc_content.append(i+",");
                        }
                        if(counter!=size-1){
                            doc_content.append("|");
                        }
                        counter++;
                    }
                }
                writer.write(capitals.getValue().country+"|"+capitals.getValue().population_size+"|"+capitals.getValue().coin+" "+doc_content+System.lineSeparator());
                dict_city.put(capitals.getKey(),""+linenumber);
                linenumber++;
            }

        }
        writer.close();
    }


}
