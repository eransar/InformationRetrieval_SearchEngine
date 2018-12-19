package PartA.Ranking;

import PartA.Indexer;
import PartA.Pointer;
import PartA.Term;

import java.util.HashMap;
import java.util.Objects;

public class RankingObject implements Comparable {

    int length;
    String DOCNO;
    String file;
    String CITY;
    double rank;
    double rank_BM25;
    double rank_cossim;
    double rank_header;



    HashMap<String, RankingInstance> terms_data; // String is the name of the term


    public RankingObject(String DOCNO, String file, int length) {
        terms_data = new HashMap<>();
        this.DOCNO = DOCNO;
        this.file = file;
        this.length = length;
    }

    public double getRank_BM25() {
        return rank_BM25;
    }

    public void setRank_BM25(double rank_BM25) {
        this.rank_BM25 = rank_BM25;
    }

    public double getRank_cossim() {
        return rank_cossim;
    }

    public void setRank_cossim(double rank_cossim) {
        this.rank_cossim = rank_cossim;
    }


    public double getRank_header() {
        return rank_header;
    }

    public void setRank_header(double rank_header) {
        this.rank_header = rank_header;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RankingObject rankingObject = (RankingObject) o;
        return this.DOCNO.equals(rankingObject.getDOCNO());
    }

    @Override
    public int hashCode() {

        return Objects.hash(DOCNO);
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
