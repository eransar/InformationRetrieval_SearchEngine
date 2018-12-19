package PartA.test;

import PartA.Query;
import PartA.Ranking.Ranker;
import PartA.ReadFile;
import PartA.Searcher;
import PartA.Stemmer;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        ReadFile readFile = new ReadFile("d:\\documents\\users\\eransar\\Downloads\\minicorpus","d:\\documents\\users\\eransar\\Downloads\\smallcorpus\\stop_words.txt","d:\\documents\\users\\eransar\\Downloads\\posting",false);
        readFile.start();
        Searcher s = new Searcher("What information is available on petroleum exploration in \n" +
                "the South Atlantic near the Falkland Islands?");
        s.getPointers();
        Ranker rank = s.getRanker();
        rank.calculate();
        rank.sortSet();
        int i =5;
    }



}
