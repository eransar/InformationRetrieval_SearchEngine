public class Document {

    private String DOCNO;
    private String DATE;
    private String HEADER;
    private String TEXT;

    public Document(String docno, String date, String header, String text){
        this.DOCNO=docno;
        this.DATE=date;
        this.HEADER=header;
        this.TEXT=text;
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

    public String getHEADER() {
        return HEADER;
    }

    public void setHEADER(String HEADER) {
        this.HEADER = HEADER;
    }

    public String getTEXT() {
        return TEXT;
    }

    public void setTEXT(String TEXT) {
        this.TEXT = TEXT;
    }

    public String toString(){
        return "Doc No :"+DOCNO+" "+"Date :"+DATE+" "+"Header :"+HEADER+" "+"Text: "+TEXT;
    }
}
