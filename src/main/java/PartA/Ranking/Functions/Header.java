package PartA.Ranking.Functions;

import PartA.Indexer;
import PartA.Ranking.RankingObject;

public class Header extends ARankerFunction {
    Indexer indexer = Indexer.getInstance();
    double result =0;

    public Header(RankingObject rankingObject) {
        super(rankingObject);
    }

    @Override
    public double calculate() {
        String s= super.getRankingObject().getHEADER();
        int lenth =s.length();
        String [] Header = s.split(" ");
        for(String tmp : Header){
            if(super.getRankingObject().getQuery().getMap_query().containsKey(tmp)){
                result = result + 1;
            }
        }

        return result/(double)lenth;
    }
}
