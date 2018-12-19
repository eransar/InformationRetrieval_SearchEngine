package PartA.test;

import PartA.Query;
import PartA.Ranking.Ranker;
import PartA.ReadFile;
import PartA.Searcher;
import PartA.Stemmer;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        ReadFile readFile = new ReadFile("D:\\corpus\\corpus","D:\\corpus\\corpus\\stop_words.txt","d:\\documents\\users\\eransar\\Downloads\\WithOutStem\\WithOutStem\\",false);
        readFile.start();
//        Searcher s = new Searcher("Falkland petroleum exploration ");
//        s.getPointers();
//        Ranker rank = s.getRanker();
//        rank.calculateBM25();
//        rank.sortSet();
//        int i =5;
    }



}
