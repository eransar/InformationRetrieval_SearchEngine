package PartA.Ranking;

import PartA.Pointer;

import java.util.HashMap;

public class RankingObject implements Comparable {

    int length;
    String DOCNO;
    String file;
    String CITY;
    double rank;
    HashMap<String, RankingInstance> terms_data; // String is the name of the term


    public RankingObject(String DOCNO, String file, int length) {
        terms_data = new HashMap<>();
        this.DOCNO = DOCNO;
        this.file = file;
        this.length = length;
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

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public HashMap<String, RankingInstance> getTerms_data() {
        return terms_data;
    }

    public void setTerms_data(HashMap<String, RankingInstance> terms_data) {
        this.terms_data = terms_data;
    }

    public void clear() {
        terms_data = new HashMap<>();
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;

        if ((o instanceof RankingObject)) {
            RankingObject OtherRakingObject = ((RankingObject) o);
            if (this.rank > OtherRakingObject.rank) {
                return -1;
            } else {
                return 1;
            }
        }
        return -1;
    }
}
