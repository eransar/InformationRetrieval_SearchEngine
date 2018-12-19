//package PartA.Ranking.Functions;
//
//import PartA.Query;
//import PartA.Ranking.RankingInstance;
//import PartA.Ranking.RankingObject;
//import PartA.Searcher;
//
//public class HeaderTest extends ARankerFunction {
//
//    public HeaderTest(RankingObject rankingObject){
//        super(rankingObject);
//
//    }
//    @Override
//    public double calculate() {
//        Query query= Searcher.getQuery();
//        double result =0;
//
//        for(RankingInstance instance : super.getRankingObject().getTerms_data().values()){
//            if(super.getRankingObject().getHEADER().contains(instance.getTerm_name().toLowerCase())){
//                result++;
//            }
//        }
//
//        return result;
//    }
//}
