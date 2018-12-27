package PartA;

import java.util.HashMap;

public class Query {

    private String numOfQuery="";
    private String title="";
    private String description="";
    private String narr="";
    HashMap<String,Integer> map_query;
    String text;

    public Query (String text) {
        this.text = text;
        this.map_query = new HashMap<>();
        init_mapquery();
    }
    public Query(String numOfQuery, String title,String description,String narr){
        this.numOfQuery = numOfQuery;
        this.title = title;
        this.description = description;
        this.narr = narr;
        this.map_query = new HashMap<>();
        this.text = String_fileQuery();
        init_mapquery();
    }

    public String String_fileQuery(){
        return title+" "+description+" "+narr;
    }

    public void init_mapquery(){
        String[] splitted = text.split(" ");
        for (String word: splitted){

            if(map_query.get(word)!=null){
                int count = map_query.get(word);
                map_query.put(word,count+1);
            }
            else{
                map_query.put(word.toLowerCase(),1);
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

    public String getNumOfQuery() {
        return numOfQuery;
    }

    public void setNumOfQuery(String numOfQuery) {
        this.numOfQuery = numOfQuery;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNarr() {
        return narr;
    }

    public void setNarr(String narr) {
        this.narr = narr;
    }
}
