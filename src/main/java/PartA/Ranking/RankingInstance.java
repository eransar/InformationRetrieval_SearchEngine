package PartA.Ranking;

public class RankingInstance {
    int count_query;
    int count_doc;


    public RankingInstance(int count_query, int  count_doc){
        this.count_query=count_query;
        this.count_doc=count_doc;
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
