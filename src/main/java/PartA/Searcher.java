package PartA;

import java.util.ArrayList;
import java.util.HashMap;

public class Searcher {

    Doc[] arr_docs;
    ArrayList<String> arr_cities;
    HashMap<String,Integer> map_docsbm25;
    HashMap<Doc,Term> map_termsindocs;
    Query query;
    Indexer indexer;
    ArrayList<Pointer> pointers = new ArrayList<>();
    public Searcher(Query query){
        this.query=query;
        this.indexer=Indexer.getInstance();
    }

    public void getPointers(){
        String[] words = query.text.split(" ");
        for (String word : words){
            pointers.add(indexer.getDictionary().get(word));
        }

    }


}
