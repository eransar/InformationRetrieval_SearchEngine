package PartA.Ranking.Functions;

import PartA.Indexer;
import PartA.Ranking.RankingObject;

/**
 * Function that calculates that header algorithm and returns the ranking.
 */
public class Header extends ARankerFunction {
    Indexer indexer = Indexer.getInstance();
    double result =0;

    public Header(RankingObject rankingObject) {
        super(rankingObject);
    }

    @Override
    /**
     * Ranking by header and return the results
     */
    public double calculate() {
        String s= super.getRankingObject().getHEADER();
        int length =s.length();
        String [] Header = s.split(" ");
        for(String tmp : Header){
            if(super.getRankingObject().getQuery().getMap_query().containsKey(tmp.toLowerCase()) || super.getRankingObject().getQuery().getMap_query().containsKey(tmp.toUpperCase())){
                result = result + 1;
            }
        }
        return result;
    }
}
