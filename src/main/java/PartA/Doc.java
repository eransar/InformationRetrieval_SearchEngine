package PartA;

import java.util.HashMap;
import java.util.Objects;

public class Doc {
    private String CITY;
    private String DOCNO;
    private String HEADER;
    private String DATE;
    private String LANGUAGE;
    private int maxtf;
    private int distinctwords;
    private String file;

    public Doc(String DOCNO, String file, String CITY, String HEADER, String DATE, String LANGUAGE){
        this.DOCNO=DOCNO;
        this.file=file;
        this.CITY=CITY;
        this.HEADER=HEADER;
        this.distinctwords=0;
        this.maxtf=0;
        this.DATE=DATE;
        this.LANGUAGE=LANGUAGE;

    }



    //<editor-fold desc="Getters and Setters">
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


    public String getLANGUAGE() {
        return LANGUAGE;
    }
    //</editor-fold>
}

