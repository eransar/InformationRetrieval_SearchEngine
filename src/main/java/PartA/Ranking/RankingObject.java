package PartA.Ranking;

import PartA.Indexer;
import PartA.Query;
import PartA.Ranking.Functions.Header;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * A Class that represents a ranking object that holds a document data and ranking
 */
public class RankingObject implements Comparable {
    Indexer indexer = Indexer.getInstance();
    int length;
    Query query;
    String DOCNO;
    String HEADER="";
    String file;
    String CITY;
    double rank;
    double rank_BM25;
    double rank_cossim;
    double rank_header;
    double weight;
    double weight_pow2;
    String[] entities;
    double rank_entities;


    HashMap<String, RankingInstance> terms_data; // String is the name of the term





    public RankingObject(String DOCNO, String file, int length, double weight, double weight_pow2,String [] entities_arr, Query query) {
        terms_data = new HashMap<>();
        this.DOCNO = DOCNO;
        this.query = query;
        this.HEADER = indexer.getDict_docs().get(DOCNO).getHEADER();
        this.file = file;
        this.length = length;
        this.weight = weight;
        this.weight_pow2 = weight_pow2;
        this.entities = new String[5];
        for (int i = 0; i < this.entities.length; i++) {
            this.entities[i] = entities_arr[i];
            if(entities[i] != null && query.getMap_query().get(this.entities[i].toLowerCase()) !=null){
                if(query.getMap_query().get(this.entities[i].toLowerCase() ) > 1){
                    rank_entities*=2;
                }
                else{
                    rank_entities++;
                }

            }

        }
    }



    public boolean equals(Object o) {
        if (this == o) return true;
        RankingObject rankingObject = (RankingObject) o;
        return this.DOCNO.equals(rankingObject.getDOCNO());
    }

    @Override
    public int hashCode() {

        return Objects.hash(DOCNO);
    }
    @Override
    /***
     * Comparing by ranking
     */
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

    public double getWeight_pow2() {
        return weight_pow2;
    }

    public void setWeight_pow2(float weight_pow2) {
        this.weight_pow2 = weight_pow2;
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }


    public String getHEADER() {
        return HEADER;
    }

    public void setHEADER(String HEADER) {
        this.HEADER = HEADER;
    }
    public double getRank_entities() {
        return rank_entities;
    }

    public void setRank_entities(double rank_entities) {
        this.rank_entities = rank_entities;
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
    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }


    public double getRank_header() {
        return rank_header;
    }

    public void setRank_header(double rank_header) {
        this.rank_header = rank_header;
    }

}
