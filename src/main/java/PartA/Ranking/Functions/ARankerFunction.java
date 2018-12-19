package PartA.Ranking.Functions;

import PartA.Indexer;
import PartA.Ranking.RankingObject;

public abstract class ARankerFunction implements IRankerFunction {

    private RankingObject rankingObject;
    private Indexer indexer;

    public ARankerFunction(RankingObject rankingObject){
        this.rankingObject=rankingObject;
        this.indexer=Indexer.getInstance();

    }
    public ARankerFunction(){}

    public RankingObject getRankingObject() {
        return rankingObject;
    }

    public void setRankingObject(RankingObject rankingObject) {
        this.rankingObject = rankingObject;
    }
}
