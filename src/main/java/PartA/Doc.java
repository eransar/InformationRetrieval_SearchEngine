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
        this.treeset_entities = new TreeSet<Term>(new Comparator<Term>() {
            @Override
            public int compare(Term t1, Term t2) {
                        // compare t1 and t2
                        int t1freq=1;
                        for (Doc d : t1.getDocFrequency().keySet()){
                            t1freq=t1.getDocFrequency().get(d);
                        }
                         int t2freq=1;

                        for (Doc d : t2.getDocFrequency().keySet()){
                            t2freq=t2.getDocFrequency().get(d);
                        }

                        if(t1freq > t2freq){
                            return 1;
                        }
                        else{
                            return -1;
                        }
            }
        });
        this.arr_entities=new String[5];

    }

    public Doc(String DOCNO){
        this.DOCNO=DOCNO;

    }
    public void AddtoEntities(Term t){
        set_entities.add(t);
    }

    public void ClearEntitiesSet(){
        set_entities.clear();
    }

    public void init_TreeSet(){
        for (Term t:
             set_entities) {
            treeset_entities.add(t);
        }
    }
    public void add_TreeSet(Term t){
        if(treeset_entities.size()>5){
            Iterator<Term> it =treeset_entities.iterator();
            Term currentTerm =it.next();
            if(currentTerm.getDocFrequency().get(this) < t.getDocFrequency().get(this)){
                it.remove();
                treeset_entities.add(t);
            }
        }
        else{
            treeset_entities.add(t);
        }
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

    public HashSet<Term> getSet_entities() {
        return set_entities;
    }

    public TreeSet<Term> getTreeset_entities() {
        return treeset_entities;
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

