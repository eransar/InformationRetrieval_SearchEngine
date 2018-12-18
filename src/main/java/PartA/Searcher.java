package PartA;

import PartA.Ranking.Ranker;
import PartA.Ranking.RankingInstance;
import PartA.Ranking.RankingObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Searcher {

    private Doc[] arr_docs;
    private ArrayList<String> arr_cities;
    private HashMap<String,Integer> map_docsbm25;
    private HashMap<Doc,Term> map_termsindocs;
    private Query query;
    private Indexer indexer;
    private TreeSet<Pointer> pointers = new TreeSet<>();
    private static Ranker ranker = new Ranker();
    private Parse parse;


    public Searcher(String query){
        this.query=new Query(query);
        this.indexer=Indexer.getInstance();
        this.parse=new Parse(true);
    }
    public ArrayList<Term> parseQuery(){
        return parse.parseQuery(query.text);
    }
    /**
     * Find if the words in the query are in the inverted index and return a pointers to them
     */
    public void getPointers(){
        parse.ParseDoc(new Doc(""),query.getText());
        ArrayList<Term> parsed_content=parse.getQueryTerms();
        for (Term term: parsed_content){
            Pointer currentPointer =indexer.getDictionary().get(term.getName());
            if(currentPointer!=null){
                BuildRankingObjects(term.getName(), currentPointer);
            }
        }
        System.out.println("jjj");
    }

    public static Ranker getRanker() {
        return ranker;
    }

    /**
     * This function takes term and inserts it's data from posting to the ranker
     * @param term_name
     * @param term_pointer
     */
    public void BuildRankingObjects(String term_name , Pointer term_pointer){
        String line=getLine(term_pointer);
        String [] lineArray = lineIntoArray(line);
        for (int i = 0; i <lineArray.length ; i++) {
            String [] tmp = lineArray[i].split(",");
            String DOCNO = tmp[0];
            String docTF = tmp[1];
            String File = tmp[2];
            if(ranker.getMap_ranked_docs().get(DOCNO)!=null){
                RankingObject o = ranker.getMap_ranked_docs().get(DOCNO);
                if(o.getTerms_data().get(term_name)==null){
                    o.getTerms_data().put(term_name,new RankingInstance(term_name,query.getMap_query().get(term_name),Integer.parseInt(docTF)));
                    ranker.getMap_ranked_docs().put(DOCNO,o);
                }
            }
            else{
                RankingObject o = new RankingObject(DOCNO,File,indexer.getDict_docs().get(DOCNO).getLENGTH());
                o.getTerms_data().put(term_name,new RankingInstance(term_name,query.getMap_query().get(term_name),Integer.parseInt(docTF)));
                ranker.getMap_ranked_docs().put(DOCNO,o);
            }
        }
    }


    public String getLine(Pointer term_pointer){
        String line="";
        try (Stream<String> lines = Files.lines(Paths.get(indexer.getPath()+ File.separator+ term_pointer.getFile_name()+".txt"))) {
            line = lines.skip(term_pointer.getLine_number()).findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    /**
     * Splits the line into fields
     * @return
     */
    public String[] lineIntoArray(String line){
        int i=0;
        for (i = 0; i < line.length(); i++) {
            if(line.charAt(i)==' '){
                break;
            }
        }
        line=line.substring(i+2);
        return line.split("\\|");
    }



    public void clear(){

    }

    public ArrayList<String> getLines(){
    return null;
    }




}
