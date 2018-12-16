package PartA.test;

import PartA.Stemmer;

public class test {
    public static void main(String[] args) {
        Stemmer ste = Stemmer.getInstance();
        String w1 = "ENGLISH";
        String w2 = "UNiverses";
        String w3 = "PEOPLES";

        ste.setCurrent(w1);
        ste.stem();
        System.out.println(ste.getCurrent());

        ste.setCurrent(w2);
        ste.stem();
        System.out.println(ste.getCurrent());

        ste.setCurrent(w3);
        ste.stem();
        System.out.println(ste.getCurrent());
    }



}
