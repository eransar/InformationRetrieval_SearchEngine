import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

public class Parser {
    private HashSet<String> StopWords;
    private HashSet<String> terms;
    private Document doc;
    private String[] textWithoutDelimeters;


    public Parser(){
        StopWords=new HashSet<String>();
        terms=new HashSet<String>();
    }
    public void ParseDocument(Document doc){
        this.doc=doc;
        String text=doc.getTEXT();
        text=text.replace("."+"\n"," ");
        text=text.replace(System.lineSeparator()," ");
        text=text.replace("\n"," ").replace("\r"," ");
        text=text.replace(", "," ");
        text=text.replace("\t"," ");


        textWithoutDelimeters=text.split(" ");
        ParseThousand();
    }
    public void ParseThousand(){
        String[] delimeters = {",",};
        double d;
        String s= "";
        for (int i = 0; i < textWithoutDelimeters.length; i++) {
            try
            {
                NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
                if(textWithoutDelimeters[i].contains(",") && Character.isDigit(textWithoutDelimeters[i].charAt(0)) && textWithoutDelimeters.length >=5){
                    Number number = format.parse(textWithoutDelimeters[i]);
                     d = number.doubleValue();
                }
                else{
                     d=Double.parseDouble(textWithoutDelimeters[i]);
                }



                //Check for fraction
                if(i+1 < textWithoutDelimeters.length && textWithoutDelimeters[i+1].contains("/")){
                String[] splitted = textWithoutDelimeters[i+1].split("/");
                try
                {
                    double decimal1 = Double.parseDouble(splitted[0]);
                    double decimal2 = Double.parseDouble(splitted[1]);
                    s=""+d;
                    s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                    terms.add(s+" "+textWithoutDelimeters[i+1]);
                }
                catch(NumberFormatException nfe)
                {
                    continue;
                }

                }

                //Thousands
                else if(d >= 1000 && d<1000000){
                    d=d/1000;
                    s=""+d;
                    s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                    terms.add(s+"K");
                }
                else if(i+1 < textWithoutDelimeters.length &&
                        (textWithoutDelimeters[i+1].equals("Thousand") || textWithoutDelimeters[i+1].equals("Thousands"))){
                    s=""+d;
                    s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                    terms.add(s+"K");
                }

                //Millions
                else if(d >= 1000000 && d<1000000000){

                    d=d/1000000;
                    s=""+d;
                    s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                    terms.add(s+"M");
                }
                else if(i+1 < textWithoutDelimeters.length &&
                        (textWithoutDelimeters[i+1].equals("Million") || textWithoutDelimeters[i+1].equals("Millions"))){

                    s=""+d;
                    s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                    terms.add(s+"M");
                }

                //Billions
                else if(d >= 1000000000 ){

                    d=d/1000000000;
                    s=""+d;
                    s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                    terms.add(s+"B");
                }
                else if(i+1 < textWithoutDelimeters.length &&
                        (textWithoutDelimeters[i+1].equals("Billion") || textWithoutDelimeters[i+1].equals("Trillion"))){
                    s=""+d;
                    s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                    terms.add(s+"B");
                }


                //Regular number
                else{
                    s=""+d;
                    s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                    terms.add(""+s);
                }

            }
            catch(NumberFormatException nfe)
            {
                continue;
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


    }

    public void printTerms(){
        Iterator iter = terms.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}


