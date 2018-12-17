package PartA.Ranking;

import PartA.Doc;
import PartA.Term;

import java.util.ArrayList;
import java.util.HashMap;

public class Ranker {
    private HashMap<Doc,ArrayList<Term>> map_docs;
    private HashMap<String,RankingObject> map_ranked_docs; //String is the name of the DOC ( DOCNO)
    private boolean semantics;

    public Ranker(){
        map_docs = new HashMap<>();
        map_ranked_docs = new HashMap<>();
        semantics = false;
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
