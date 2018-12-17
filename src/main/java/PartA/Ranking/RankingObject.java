package PartA.Ranking;

import java.util.HashMap;

public class RankingObject {

    int length;
    String DOCNO;
    String file;
    String CITY;
    int rank;
    HashMap<String,RankingInstance> terms_data; // String is the name of the term


    public RankingObject(String DOCNO, String file, int length){
        terms_data=new HashMap<>();
        this.DOCNO=DOCNO;
        this.file=file;
        this.length=length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDOCNO() {
        return DOCNO;
    }

    public void setDOCNO(String DOCNO) {
        this.DOCNO = DOCNO;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public HashMap<String, RankingInstance> getTerms_data() {
        return terms_data;
    }

    public void setTerms_data(HashMap<String, RankingInstance> terms_data) {
        this.terms_data = terms_data;
    }

    public void clear(){ terms_data=new HashMap<>();}
}
