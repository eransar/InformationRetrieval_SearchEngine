package PartA;


import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

public class Stemmer {

    private SnowballStemmer stemmer = new englishStemmer();

    private static Stemmer ourInstance = new Stemmer();
    //private static SnowballStemmer snowballStemmer = new englishStemmer();
    public static Stemmer getInstance() {
        return ourInstance;
    }

    public void setCurrent(String term_name){
        stemmer.setCurrent(term_name);
    }

    public boolean stem(){
        return stemmer.stem();
    }

    public String getCurrent(){
        return stemmer.getCurrent();
    }

}
