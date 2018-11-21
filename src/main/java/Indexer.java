import java.util.HashMap;

public class Indexer {

    private  HashMap<String,String> dictionary;

    public  Indexer(){
        dictionary=new HashMap<String,String>();
    }

    public void addToHashMap(String term_name, String pointer){
        if(!dictionary.containsKey(term_name)){
            dictionary.put(term_name,pointer);
        }
        else{
            StringBuilder stringBuilder = new StringBuilder(dictionary.get(term_name));
            stringBuilder.append(" "+stringBuilder);
            dictionary.put(term_name,stringBuilder.toString());
        }
    }
}
