package PartA.Ranking;

import PartA.*;

import java.util.ArrayList;


public class BM25 {
    private ArrayList<Term> words;
    private String docno;
    private Query query;
    private int docsaverage;
    private int doclength;


    private int b;
    private int k;
    private int M;

    private Indexer indexer;


    public BM25(ArrayList word, String docno, Query query, int doclength, int docsaverage, int b , int k , int M){
        this.words=word;
        this.docno=docno;
        this.query=query;
        this.docsaverage=docsaverage;
        this.b=b;
        this.k=k;
        this.M=M;
        this.indexer=Indexer.getInstance();
        this.doclength=doclength;

    }


    public double calculate(){
        double result=0;
        for (Term word : words){
            int count_wordinquery=countwordinTerm(word);
            int count_wordindoc=word.getDocFrequency().get(docno);
            int word_df=word.getDf();
            int average_doc=indexer.getDocsaverage();
            result += count_wordinquery*
                    (((k+1)*count_wordindoc) /(count_wordindoc+k*(1-b+b*((doclength)/(average_doc)))))* Math.log10((M+1)/(word_df));
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

