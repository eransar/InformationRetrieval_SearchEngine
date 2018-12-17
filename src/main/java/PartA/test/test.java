package PartA.test;

import PartA.Query;
import PartA.ReadFile;
import PartA.Searcher;
import PartA.Stemmer;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        ReadFile readFile = new ReadFile("d:\\documents\\users\\eransar\\Downloads\\test","D:\\corpus\\stop_words.txt","D:\\",false);
        readFile.start();
        Searcher s = new Searcher("Once upon a time dad");
        s.getPointers();
    }



}
