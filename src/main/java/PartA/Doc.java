package PartA;

import java.io.Serializable;
import java.util.*;

public class Doc implements Serializable {
    private String CITY;
    private String DOCNO;
    private String HEADER;
    private String DATE;
    private String LANGUAGE;
    private int LENGTH;
    private int maxtf;
    private int distinctwords;
    private String file;
    private HashMap<String,Term> map_entities;
    private TreeSet<Term> treeset_entities;
    private String[] arr_entities;
    private double weight;
    private double weight_pow2;
    private static final long serialVersionUID = 1113799434508676095L;



    public Doc(String DOCNO, String file, String CITY, String HEADER, String DATE, String LANGUAGE){
        this.DOCNO=DOCNO;
        this.file=file;
        this.CITY=CITY;
        this.HEADER=HEADER;
        this.distinctwords=0;
        this.maxtf=0;
        this.DATE=DATE;
        this.LANGUAGE=LANGUAGE;
        this.map_entities =new HashMap<>();
        this.treeset_entities = new TreeSet<Term>(new DocComperator() {});
        this.arr_entities=new String[5];
        this.weight=0.0;
        this.weight_pow2=0.0;

    }

    public Doc(String DOCNO){
        this.DOCNO=DOCNO;

    }

    public void AddtoEntities(Term t){
        map_entities.put(t.getName(),t);
    }

    public void ClearEntitiesSet(){
        map_entities.clear();
    }

    public String[] getArr_entities() {
        return arr_entities;
    }

    public void setArr_entities(String[] arr_entities) {
        this.arr_entities = arr_entities;
    }

    public void init_TreeSet(){
        for (Term t: map_entities.values()) {
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
                Term t = it.next();
                arr_entities[i]=t.getName()+"  " + t.getTf();
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

    public void addWeight(double w){
        weight+=w;
        weight_pow2+= w*w;
    }

    //    private HashMap<Location,Term> termLocation;

    public void addEntity(Term term){
        Term tmp = new Term(term);
        tmp.setName(tmp.getName().toUpperCase());
        if(map_entities.get(tmp.getName())==null) {
            tmp.setTf(1);
            map_entities.put(tmp.getName(), tmp);
        }
        else {
            Term t = map_entities.get(tmp.getName());
            int num = t.getTf();
            t.setTf(t.getTf()+1);
            //map_entities.get(t.getName()).getDocFrequency().put(this,num+1);
            map_entities.put(t.getName(),t);
        }
    }

    public void removeEntity(String term_name){
        map_entities.remove(term_name);
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public HashMap<String,Term> getMap_entities() {
        return map_entities;
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

    public String getHEADER() {
        return HEADER;
    }

    public void setHEADER(String HEADER) {
        this.HEADER = HEADER;
    }
    public double getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public double getWeight_pow2() {
        return weight_pow2;
    }

    public void setWeight_pow2(float weight_pow2) {
        this.weight_pow2 = weight_pow2;
    }

    //</editor-fold>
}

