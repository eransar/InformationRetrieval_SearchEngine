import java.util.HashMap;
import java.util.Objects;

public class Term {
    private String name;
    private int df;
    private int corpusFrequency;
    private HashMap<Document,Integer> docFrequency;

    public void setDocFrequency(HashMap<Document, Integer> docFrequency) {
        this.docFrequency = docFrequency;
    }

    public HashMap<Document, Integer> getDocFrequency() {
        return docFrequency;
    }

    public Term(Term t){
        this.name=t.name;

        this.df=t.df;
        this.corpusFrequency=t.corpusFrequency;
        this.docFrequency=null;


    }

    public int getCorpusFrequency() {
        return corpusFrequency;
    }

    public void setCorpusFrequency(int corpusFrequency) {
        this.corpusFrequency = corpusFrequency;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDf() {
        return df;
    }

    public void setDf(int df) {
        this.df = df;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }

    private int tf;


    public Term(){
        this.name="";
        this.df=0;
        this.tf=0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return Objects.equals(name, term.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
