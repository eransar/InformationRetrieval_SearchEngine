package PartA;


import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

/**
 * Stemmer class in charge of analyzing the terms and decreasing the dictionary by removing the suffix for terms
 */
public class Stemmer {

    private SnowballStemmer stemmer = new englishStemmer();

    private static Stemmer ourInstance = new Stemmer();
    //private static SnowballStemmer snowballStemmer = new englishStemmer();
    public static Stemmer getInstance() {
        return ourInstance;
    }


    public boolean stem(){
        return stemmer.stem();
    }

    //<editor-fold desc="Getter and Setter">
    public void setCurrent(String term_name){
        stemmer.setCurrent(term_name);
    }

    public String getCurrent(){
        return stemmer.getCurrent();
    }
    //</editor-fold>

}
