package PartA;

import java.util.HashMap;
import java.util.Objects;

public class Doc {
    private String CITY;
    private String DOCNO;
    private String DATE;
//    private String HEADER;
//    private String TEXT;
    private int maxtf;
    private int distinctwords;
    private int position;
    private String file;

    public Doc(String DOCNO,String file, String CITY){
        this.DOCNO=DOCNO;
        this.file=file;
        this.CITY=CITY;
    }

    public Doc(String docno, String date, String header, String text){
        this.DOCNO=docno;
        this.DATE=date;
//        this.HEADER=header;
//        this.TEXT=text;
        this.distinctwords=0;
        this.maxtf=0;

//        this.termLocation=new HashMap<Location,Term>();
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

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getFile() {
        return file;
    }

    public int hashCode(){
        return Objects.hashCode(DOCNO);
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
