package PartA;

import java.util.HashMap;

public class Query {

    HashMap<String,Integer> map_query;
    String text;

    public Query (String text){
    this.text=text;
    this.map_query=new HashMap<>();
    init_mapquery();
    }

    public void init_mapquery(){
        String[] splitted = text.split(" ");
        for (String word: splitted){

            if(map_query.get(word)!=null){
                int count = map_query.get(word);
                map_query.put(word,count+1);
            }
            else{
                map_query.put(word,1);
            }
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HashMap<String, Integer> getMap_query() {

        return map_query;
    }

    public void setMap_query(HashMap<String, Integer> map_query) {
        this.map_query = map_query;
    }
}
