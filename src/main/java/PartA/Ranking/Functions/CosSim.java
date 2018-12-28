package PartA.Ranking.Functions;

import PartA.Indexer;
import PartA.Ranking.RankingInstance;
import PartA.Ranking.RankingObject;

public class CosSim extends ARankerFunction {

    private int numOfDocs =0;
    private Indexer indexer = Indexer.getInstance();
    public CosSim(RankingObject rankingObject){
        super(rankingObject);
        numOfDocs = indexer.getDict_docs().size();
    }
    @Override
    public double calculate() {
        double weight = super.getRankingObject().getWeight();
        double weight_pow2 = super.getRankingObject().getWeight_pow2();
        double sum_mone=weight;
        double sum_mechane=weight_pow2;
        double count_query=0;



        for (RankingInstance instance : super.getRankingObject().getTerms_data().values()){
           count_query+=Math.pow(instance.getCount_query(),2);
        }
        double result = (weight)/(Math.sqrt(weight_pow2*count_query));
        return result/10;

    }
}
