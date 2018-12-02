package PartA;

import java.util.HashMap;
import java.util.Objects;

public class Doc {
    private String CITY;
    private String DOCNO;
    private String HEADER;
    private String DATE;
    private int maxtf;
    private int distinctwords;
    private int position;
    private String file;

    public Doc(String DOCNO,String file, String CITY, String HEADER,String DATE){
        this.DOCNO=DOCNO;
        this.file=file;
        this.CITY=CITY;
        this.HEADER=HEADER;
        this.distinctwords=0;
        this.maxtf=0;
        this.DATE=DATE;
    }


    public void AddTermLocation(){

    }

    public void setMaxtf(int maxtf) {
        this.maxtf = maxtf;
    }

    public int getMaxtf() {

        return maxtf;
    }
    //    private HashMap<Location,Term> termLocation;


    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public int getDistinctwords() {
        return distinctwords;
    }

    public void setDistinctwords(int distinctwords) {
        this.distinctwords = distinctwords;
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

    public int hashCode(){
        return Objects.hashCode(DOCNO);
    }
    public boolean equals(Doc d1){
        return d1.DOCNO.equals(this.DOCNO);
    }


}
//    public void setTermLocation(HashMap<Location,Term> termLocation) {
//        this.termLocation = termLocation;
//    }

//    public String getHEADER() {
//        return HEADER;
//    }
//
//    public void setHEADER(String HEADER) {
//        this.HEADER = HEADER;
//    }
//
//    public String getTEXT() {
//        return TEXT;
//    }
//
//    public void setTEXT(String TEXT) {
//        this.TEXT = TEXT;
//    }
//
//    public String toString(){
//        return "Doc No :"+DOCNO+" "+"Date :"+DATE+" "+"Header :"+HEADER+" "+"Text: "+TEXT;
//    }
//}
