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
import java.util.HashSet;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Searcher {


    protected static Query query;
    private Indexer indexer;
    private TreeSet<Pointer> pointers = new TreeSet<>();
    private static Ranker ranker;
    private Parse parse;
    private HashSet<String> hashSet_citisByUser;
    private String query_text;
    private TreeSet<RankingObject> map_results;
    private boolean stemming;

    public Searcher(String query, HashSet<String> hashSet_citisByUser,boolean stemming){
        this.hashSet_citisByUser =hashSet_citisByUser;
        ranker = new Ranker(hashSet_citisByUser);
        this.query_text=query;
        this.indexer=Indexer.getInstance();
        this.parse=new Parse(true);
        this.map_results=new TreeSet<RankingObject>();
        this.stemming=false;

    }

    public Searcher(Query query, HashSet<String> hashSet_citisByUser,boolean stemming){
        this.hashSet_citisByUser =hashSet_citisByUser;
        this.indexer=Indexer.getInstance();
        this.parse=new Parse(true);
        this.map_results=new TreeSet<RankingObject>();
        this.query_text=query.getText();
        ranker = new Ranker(hashSet_citisByUser);
        this.stemming=false;


    }

    public Searcher(String query,boolean stemming){
        this.hashSet_citisByUser=new HashSet<>();
        ranker = new Ranker(hashSet_citisByUser);
        this.query_text=query;
        this.indexer=Indexer.getInstance();
        this.parse=new Parse(true);
        this.map_results=new TreeSet<RankingObject>();
        this.stemming=false;
    }

    public ArrayList<Term> parseQuery(){
        return parse.parseQuery(query.text);
    }
    /**
     * Find if the words in the query are in the inverted index and return a pointers to them
     */
    public void getPointers(){
        ArrayList<Term> parsed_content=sendtoParse(query_text,stemming);
        query=buildQuery(parsed_content);

        for (Term term: parsed_content){
            Pointer currentPointer =indexer.getDictionary().get(term.getName());
            if(currentPointer!=null){
                BuildRankingObjects(term.getName(), currentPointer);
            }
        }
    }
    public ArrayList<Term> sendtoParse(String text, boolean stemming){
        parse.ParseDoc(new Doc(""),text);
        return parse.getQueryTerms(stemming);
    }

    public Query buildQuery(ArrayList<Term> parsed_content){
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < parsed_content.size(); i++) {
            if(i==0){
                sb.append(parsed_content.get(i));
            }
            else{
                sb.append(" "+parsed_content.get(i));
            }
        }
        query=new Query(sb.toString());
        return query;
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
        String line=indexer.getLine(term_pointer);
        String [] lineArray = lineIntoArray(line);
        for (int i = 0; i <lineArray.length ; i++) {
            String [] tmp = lineArray[i].split(",");
            String DOCNO = tmp[0];
            String docTF = tmp[1];
            String File = tmp[2];
            if(ranker.getMap_ranked_docs().get(DOCNO)!=null){
                RankingObject o = ranker.getMap_ranked_docs().get(DOCNO);
                if(o.getTerms_data().get(term_name)==null){
                    o.getTerms_data().put(term_name,new RankingInstance(term_name,query.getMap_query().get(term_name.toLowerCase()),Integer.parseInt(docTF)));
                    ranker.getMap_ranked_docs().put(DOCNO,o);
                }
            }
            else{
                Doc currentdoc=indexer.getDict_docs().get(DOCNO);
                String [] ss =currentdoc.getArr_entities();
                RankingObject o = new RankingObject(DOCNO,File,currentdoc.getLENGTH(),currentdoc.getWeight(),currentdoc.getWeight_pow2(),ss,query);
                o.getTerms_data().put(term_name,new RankingInstance(term_name,query.getMap_query().get(term_name.toLowerCase()),Integer.parseInt(docTF)));
                ranker.getMap_ranked_docs().put(DOCNO,o);
            }
        }
    }



//    public String getLine(Pointer term_pointer){
//        String line="";
//        try (Stream<String> lines = Files.lines(Paths.get(indexer.getPath()+ File.separator+ term_pointer.getFile_name()+".txt"))) {
//            line = lines.skip(term_pointer.getLine_number()).findFirst().get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return line;
//    }

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

    public static Query getQuery(){
        return query;
    }

    public ArrayList<String> getLines(){
    return null;
    }




}
