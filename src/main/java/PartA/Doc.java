package PartA;

import java.util.*;

public class Doc {
    private String CITY;
    private String DOCNO;
    private String HEADER;
    private String DATE;
    private String LANGUAGE;
    private int LENGTH;
    private int maxtf;
    private int distinctwords;
    private String file;
    private HashSet<Term> set_entities;
    private TreeSet<Term> treeset_entities;
    private String[] arr_entities;


    public Doc(String DOCNO, String file, String CITY, String HEADER, String DATE, String LANGUAGE){
        this.DOCNO=DOCNO;
        this.file=file;
        this.CITY=CITY;
        this.HEADER=HEADER;
        this.distinctwords=0;
        this.maxtf=0;
        this.DATE=DATE;
        this.LANGUAGE=LANGUAGE;
        this.set_entities=new HashSet<>();
        this.treeset_entities = new TreeSet(new Comparator<Term>() {
            @Override
            public int compare(Term t1, Term t2) {
                        // compare t1 and t2
                        int t1freq=t1.getDocFrequency().get(DOCNO);
                        int t2freq=t2.getDocFrequency().get(DOCNO);
                        if(t1freq > t2freq){
                            return 1;
                        }
                        else if(t1freq==t2freq){
                            return 0;
                        }
                        else{
                            return -1;
                        }
            }
        });
        this.arr_entities=new String[5];

    }
    public void AddtoEntities(Term t){
        set_entities.add(t);
    }

    public void ClearEntitiesSet(){
        set_entities.clear();
    }

    public void init_TreeSet(){
        treeset_entities.addAll(set_entities);
    }
    public void ClearEntitiesTreeSet(){
        treeset_entities.clear();
    }
    public void init_arrEntities(){
        Iterator<Term> it= treeset_entities.descendingIterator();
        for (int i = 0; i < arr_entities.length; i++) {
            if(it.hasNext()){
                arr_entities[i]=it.next().getName();
            }
        }
    }

    public int getLENGTH() {
        return LENGTH;
    }

    public void setLENGTH(int LENGTH) {
        this.LENGTH = LENGTH;
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

