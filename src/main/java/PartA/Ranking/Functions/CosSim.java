package PartA.Ranking.Functions;

import PartA.Ranking.RankingInstance;
import PartA.Ranking.RankingObject;

public class CosSim extends ARankerFunction {



    public CosSim(RankingObject rankingObject){
        super(rankingObject);

    }
    @Override
    public double calculate() {
        double sum_mone=0;
        double sum_mechane=0;

        for (RankingInstance instance : super.getRankingObject().getTerms_data().values()){
             double doc_weight=instance.getDf()*instance.getCount_doc();
             sum_mone+=doc_weight;
             sum_mechane+=Math.pow(doc_weight,2);

        }

        return ((sum_mone)/Math.sqrt(sum_mechane * getRankingObject().getTerms_data().size()));

    }
}
