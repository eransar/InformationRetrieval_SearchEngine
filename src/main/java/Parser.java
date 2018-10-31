import java.io.*;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

public class Parser {
    private HashSet<String> stopWords;
    private HashSet<String> terms;
    private Document doc;

    public static String[] getTextWithoutDelimeters() {
        return textWithoutDelimeters;
    }

    private static String[] textWithoutDelimeters;


    public Parser() throws IOException {
        stopWords=new HashSet<String>();
//        initializestopwords();
        terms=new HashSet<String>();
    }

    private void initializestopwords() throws IOException {
        URL url = getClass().getResource("stop_words.txt");
        File file = new File(url.getPath());
        FileReader fr = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fr);
        StringBuffer stringBuffer = new StringBuffer();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stopWords.add(line);
        }
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
        identifyDoc();

//        ParseWords();
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }



    public void identifyDoc(){
        EnumParse enumParse;
        for (int i = 0; i <textWithoutDelimeters.length ; i++) {
            try {
                if(isSymbol(i)){
                    enumParse = EnumParse.symbol;
                    terms.addAll(enumParse.parse(i));

                    System.out.println("Symbol : "+textWithoutDelimeters[i]);
                }
                else if (isNumber(i)){
                    enumParse = EnumParse.number;
                    terms.addAll(enumParse.parse(i));
                }
            } catch (ParseException e) {
                enumParse = EnumParse.word;
                System.out.println("Word : "+textWithoutDelimeters[i]);

            }
        }
    }

    private boolean isSymbol(int i) throws ParseException {

        ArrayList<Character> ch = new ArrayList<>();
        ch.add('$');
        if(ch.contains(textWithoutDelimeters[i].charAt(0))) {
            NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
            String substring = textWithoutDelimeters[i].substring(1, textWithoutDelimeters[i].length());
            Number number = format.parse(substring);
            return true;
        }
            return false;
    }

    private boolean isNumber(int i) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);

        Number number = format.parse(textWithoutDelimeters[i]);
        return true;
    }


    private void ParseWords(int i) {

    //TODO : 6%





    }

    public void ParseNumbers(){
        String[] delimeters = {",",};
        double d;
        String s= "";
        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        for (int i = 0; i < textWithoutDelimeters.length; i++) {
            try
            {

                //numbers with $$ dollar symbol
                if(textWithoutDelimeters[i].equals("")){
                    continue;
                }
                else if(textWithoutDelimeters[i].charAt(0)=='$'){
                    String substring=textWithoutDelimeters[i].substring(1,textWithoutDelimeters[i].length());
                    Number number = format.parse(substring);
                    double dollar_number = number.doubleValue();


                    if(dollar_number >= 1000000){
                        if(i+1 < textWithoutDelimeters.length &&
                                (textWithoutDelimeters[i+1].equals("Dollars"))){
                            dollar_number=dollar_number/1000000;
                            s=""+dollar_number;
                            s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                            terms.add(s+" "+"M"+" "+textWithoutDelimeters[i+1]);
                            continue;
                        }
                        else{
                            dollar_number=dollar_number/1000000;
                            s=""+dollar_number;
                            s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                            terms.add(s+" "+"M"+" "+"Dollars");
                            continue;
                        }

                    }

                    else{
                        s=""+dollar_number;
                        s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                        terms.add(s+" "+textWithoutDelimeters[i+1]);
                    }
                }

                //numbers with ',' comma

                else if(textWithoutDelimeters[i].contains(",") && Character.isDigit(textWithoutDelimeters[i].charAt(0)) && textWithoutDelimeters.length >=5){
                    Number number = format.parse(textWithoutDelimeters[i]);
                     d = number.doubleValue();
                }
                else{
                     d=Double.parseDouble(textWithoutDelimeters[i]);
                }

                Number number = format.parse(textWithoutDelimeters[i]);
                d = number.doubleValue();

                //Check for fraction
                if(i+1 < textWithoutDelimeters.length && textWithoutDelimeters[i+1].contains("/")){
                String[] splitted = textWithoutDelimeters[i+1].split("/");
                try
                {

                    double decimal1 = Double.parseDouble(splitted[0]);
                    double decimal2 = Double.parseDouble(splitted[1]);
                    s=""+d;
                    s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                    if(i+2 < textWithoutDelimeters.length && textWithoutDelimeters[i+2].equals("Dollars")){
                        terms.add(s+" "+textWithoutDelimeters[i+1]+" "+textWithoutDelimeters[i+2]);
                    }
                    else{
                        terms.add(s+" "+textWithoutDelimeters[i+1]);
                    }
                }
                catch(NumberFormatException nfe)
                {
                    ParseWords(i);
                    continue;
                }

                }
                //                 percent
                else if(i+1 < textWithoutDelimeters.length &&
                        (textWithoutDelimeters[i+1].equals("percent") || textWithoutDelimeters[i+1].equals("percentage"))){
                    s=""+d;
                    s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                    terms.add(s+"%");
                }
                //Dollars
                else if(i+1 < textWithoutDelimeters.length &&
                        (textWithoutDelimeters[i+1].equals("Dollars"))){


                    if(d >=1000000 ){
                        d=d/1000000;
                        s=""+d;
                        s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                        terms.add(s+" "+"M"+" "+textWithoutDelimeters[i+1]);
                    }
                    else{
                        s=""+d;
                        s=s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                        terms.add(s+" "+"M"+" "+textWithoutDelimeters[i+1]);
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
                ParseWords(i);
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


