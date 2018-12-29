package PartA;

import javax.print.attribute.standard.MediaSize;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Query implements Comparable{

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
    }

    public String String_fileQuery(){
        return title+" "+description;
    }

    public void init_mapquery(){
        String[] splitted = text.split(" ");
        for (String word: splitted){

            if(map_query.get(word.toLowerCase())!=null){
                int count = map_query.get(word.toLowerCase());
                map_query.put(word.toLowerCase(),count+1);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query = (Query) o;
        return Objects.equals(numOfQuery, query.numOfQuery);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numOfQuery);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Query) {
            Query OtherQuery = ((Query) o);
            if (Integer.parseInt(this.getNumOfQuery()) > Integer.parseInt(OtherQuery.getNumOfQuery())) {
                return 1;
            }
            else if (Integer.parseInt(this.getNumOfQuery()) < Integer.parseInt(OtherQuery.getNumOfQuery())) {
                return -1;
            }
            else return 0;
        }
        return -5;
    }
}
