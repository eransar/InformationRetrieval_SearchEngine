package PartA.Ranking.Functions;

import PartA.Indexer;
import PartA.Ranking.RankingObject;

/**
 * Abstract ranking function
 */
public abstract class ARankerFunction implements IRankerFunction {

    private RankingObject rankingObject;


    public ARankerFunction(RankingObject rankingObject){
        this.rankingObject=rankingObject;

    }
    public ARankerFunction(){}

    public RankingObject getRankingObject() {
        return rankingObject;
    }

    public void setRankingObject(RankingObject rankingObject) {
        this.rankingObject = rankingObject;
    }
}
