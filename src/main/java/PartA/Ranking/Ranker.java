package PartA.Ranking;

import PartA.*;
import PartA.Ranking.Functions.BM25;
import PartA.Ranking.Functions.CosSim;
import PartA.Ranking.Functions.Header;

import java.io.*;
import java.util.*;

public class Ranker {
    private HashMap<Doc, ArrayList<Term>> map_docs;
    private HashMap<String, RankingObject> map_ranked_docs; //String is the name of the DOC ( DOCNO)
    private TreeSet<RankingObject> sorted_rankingobject;
    private boolean semantics;
    private HashSet<String> set_citiesChoosen;



    public Ranker(HashSet<String> set_citiesChoosen) {
        this.set_citiesChoosen = set_citiesChoosen;
        map_docs = new HashMap<>();
        map_ranked_docs = new HashMap<>();
        semantics = false;
        this.sorted_rankingobject = new TreeSet<>();


    }

    public TreeSet<RankingObject> getSorted_rankingobject() {
        return sorted_rankingobject;
    }

    public void setSorted_rankingobject(TreeSet<RankingObject> sorted_rankingobject) {
        this.sorted_rankingobject = sorted_rankingobject;
    }

    public HashSet<String> getSet_citiesChoosen() {
        return set_citiesChoosen;
    }

    public void setSet_citiesChoosen(HashSet<String> set_citiesChoosen) {
        this.set_citiesChoosen = set_citiesChoosen;
    }


    public void calculateBM25() {
        for (RankingObject rank : map_ranked_docs.values()) {
            BM25 bm25 = new BM25(rank);
            rank.setRank_BM25(bm25.calculate());
        }
    }

    public void calculateCosSim(){
        for (RankingObject rank : map_ranked_docs.values()) {
            CosSim cosSim = new CosSim(rank);
            rank.setRank_cossim(cosSim.calculate());
        }
    }

    public void calculateHeader() {
        for (RankingObject rank : map_ranked_docs.values()) {
            Header header = new Header(rank);
            rank.setRank_header(header.calculate());
        }
    }

    public void calculate() {
        calculateBM25();
        calculateCosSim();
        calculateHeader();
//        calculateHeaderTest();
        for (RankingObject rank : map_ranked_docs.values()) {
            rank.setRank(0.5*rank.getRank_BM25()+0.2*rank.getRank_entities() +0.3*rank.getRank_header() );
        }
    }

    public void writeResults(String filepath){
        StringBuilder sb = new StringBuilder("");
        int i=0;
        for (RankingObject rank :sorted_rankingobject){
            if(i==50)
                break;
            sb.append(Searcher.getQuery().getNumOfQuery()+" 0 "+rank.getDOCNO()+" 1 42.38 mt"+System.lineSeparator());
            i++;
        }
        try {
            File f=new File(filepath+ File.separator+"results.txt");
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, false)));
            out.write(sb.toString());
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    /**
     * sort the relevent docs by rank
     */
    public void sortSet() {
        //remove the unnecessary docs by not chosen citis.
        citiesFilter();
        sorted_rankingobject.addAll(map_ranked_docs.values());
    }

    /**
     * filter the docs by cities
     */
    private void citiesFilter() {
        ArrayList<String> list = new ArrayList<>();
        if(set_citiesChoosen != null && set_citiesChoosen.size()>0) {
            for (String s : map_ranked_docs.keySet()) {
                String city = Indexer.getInstance().getDict_docs().get(s).getCITY();
                if (!set_citiesChoosen.contains(city)) {
                    list.add(s);
                }
            }
            for(String s : list)
                map_ranked_docs.remove(s);
        }
    }

    public HashMap<Doc, ArrayList<Term>> getMap_docs() {
        return map_docs;
    }

    public void setMap_docs(HashMap<Doc, ArrayList<Term>> map_docs) {
        this.map_docs = map_docs;
    }

    public HashMap<String, RankingObject> getMap_ranked_docs() {
        return map_ranked_docs;
    }

    public void setMap_ranked_docs(HashMap<String, RankingObject> map_ranked_docs) {
        this.map_ranked_docs = map_ranked_docs;
    }

    public boolean isSemantics() {
        return semantics;
    }

    public void setSemantics(boolean semantics) {

        this.semantics = semantics;
    }
}
