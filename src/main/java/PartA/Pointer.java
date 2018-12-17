package PartA;

import java.io.Serializable;

/**
 * Pointer class of term representation.
 */
public class Pointer implements Serializable , Comparable{
    private String file_name;
    private int line_number;
    private int term_df;

    public Pointer(String file_name, int line_number, int term_df){
        this.file_name=file_name;
        this.line_number=line_number;
        this.term_df=term_df;
    }

    //<editor-fold desc="Getters and Setters">
    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getLine_number() {
        return line_number;
    }

    public void setLine_number(int line_number) {
        this.line_number = line_number;
    }

    public int getTerm_df() {
        return term_df;
    }

    public void setTerm_df(int term_df) {
        this.term_df = term_df;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;

        if ((o instanceof Pointer)){
            Pointer OtherPointer = ((Pointer) o);
            return this.file_name.compareTo(OtherPointer.file_name);
        }
        return -1;
    }
    //</editor-fold>
}
