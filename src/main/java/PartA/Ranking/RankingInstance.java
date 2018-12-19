package PartA.Ranking;

import PartA.Indexer;

public class RankingInstance {
    String term_name;
    int count_query;
    int count_doc;
    int df;


    public String getTerm_name() {
        return term_name;
    }

    public int getDf() {
        return df;
    }

    public void setDf(int df) {
        this.df = df;
    }

    public RankingInstance(String term_name, int count_query, int  count_doc){
        this.count_query=count_query;
        this.count_doc=count_doc;
        this.term_name=term_name;
        this.df= Indexer.getInstance().getDictionary().get(term_name).getTerm_df();


    }

    public int getCount_query() {
        return count_query;
    }

    public void setCount_query(int count_query) {
        this.count_query = count_query;
    }

    public int getCount_doc() {
        return count_doc;
    }

    public void setCount_doc(int count_doc) {
        this.count_doc = count_doc;
    }
}
