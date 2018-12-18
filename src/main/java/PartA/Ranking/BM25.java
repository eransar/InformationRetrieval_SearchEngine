package PartA.Ranking;

import PartA.*;

import java.util.ArrayList;


public class BM25 {
    private ArrayList<Term> words;
    private String docno;
    private Query query;


    private final double b = 0.75;
    private final double k = 1.2;
    private int M ;
    private RankingObject rankingObject;
    private Indexer indexer;


    public BM25(RankingObject rankingObject){
        this.indexer=Indexer.getInstance();
        this.M = indexer.getDict_docs().size();
        this.rankingObject = rankingObject;
    }


    public double calculate(){
        double result=0;
        for (RankingInstance in : rankingObject.getTerms_data().values()){
            int count_wordinquery=in.getCount_query();
            int count_wordindoc=in.getCount_doc();
            int word_df= (indexer.getDictionary().get(in.getTerm_name()).getTerm_df());
            int average_doc=indexer.getDocsaverage();
            result += count_wordinquery*
                    (((k+1)*count_wordindoc) /(count_wordindoc+k*(1-b+b*((rankingObject.length)/(average_doc)))))* Math.log10((M+1)/(word_df));
        }
        return result;
    }

    public int countwordinTerm(Term word){
        if(query.getMap_query().containsKey(word)){
            return query.getMap_query().get(word);
        }
        return 0;
    }
}

