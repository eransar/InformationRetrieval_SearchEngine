

import java.io.*;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;


public class Parse {
    private HashSet<String> stopWords;
    private HashMap<String,Term> terms;
    private Doc doc;
    private int lineNumber;
    private int wordPosition;
    private int index;
    private String[] docText;
    private enum wordType {NUMBER, SYMBOL, WORD,NULL;};
    private HashMap <String,Integer> months;
    private HashMap<String,String> replace;



    public Parse() throws IOException {
        this.stopWords=new HashSet<String>();
        this.lineNumber=0;
        this.wordPosition=0;
        this.index=1;
        this.terms=new HashMap<String,Term>();
        this.months=months();
        this.replace=new HashMap<String,String>();
        initStopwords();
        initreplace();
    }
    public Parse(Doc doc) throws IOException {
//        this.termsInfo=new HashMap<Term,HashMap<Doc,Integer>>();
        this.lineNumber=0;
        this.wordPosition=0;
        this.index=1;
        this.terms=new HashMap<String,Term>();
        this.stopWords=new HashSet<String>();
        this.months=months();
        this.replace=new HashMap<String,String>();
        initStopwords();
        initreplace();


    }

    private void initreplace() {
        replace.put(",","");
        replace.put("\n\n"," ");
        replace.put("\\r\\n"," ");
        replace.put("\t"," ");
        replace.put("."+"\n"," ");
        replace.put (".)","");
        replace.put(")"," ");
        replace.put("("," ");
        replace.put(" '","");
        replace.put("' ","");
        replace.put(": "," ");
        replace.put(". \n"," ");
        replace.put(". "," ");
        replace.put(".) "," ");
        replace.put("--"," ");
        replace.put("- "," ");
        replace.put(";"," ");
        replace.put(";\n"," ");
        replace.put("[","");
        replace.put("]","");
        replace.put("'","");
        replace.put("'s","");
        replace.put("-\n","");
        replace.put("\"","");
        replace.put("?","");
        replace.put(".\"","");
        replace.put(".,","");
        replace.put("!","");
        replace.put("\n"," ");
    }

    /**
     * Parsing A document and filling termsInfo HashMap
     */
    public void ParseDoc(Doc doc,String TEXT){
        this.doc=doc;
        TEXT=replaceReplace(TEXT);
        docText=(TEXT.split(" "));
        try {
            startParse();
        } catch (ParseException e) {

        }
//        printTerm();
    }

    private String replaceReplace(String text) {

        StringBuilder sb = new StringBuilder(text);
        for (Map.Entry<String, String> entry : replace.entrySet()) {

            String key = entry.getKey();
            String value = entry.getValue();

            if(text.length()==0 || (text.charAt(0)=='$' && key.equals("$"))){
                continue;
            }
            int start = sb.indexOf(key, 0);
            while (start > -1) {
                int end = start + key.length();
                int nextSearchStart = start + value.length();
                sb.replace(start, end, value);
                start = sb.indexOf(key, nextSearchStart);
            }
        }
        return sb.toString();
    }

    public void initStopwords() throws IOException {
        URL url = getClass().getResource("stop_words.txt");
        File file = new File(url.getPath());
        FileReader fr = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fr);
        StringBuffer stringBuffer = new StringBuffer();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            if(!line.equals(System.lineSeparator())){
                stopWords.add(line);
            }
        }
    }

    private void printTerm() {
        int i=0;
        for (String name: terms.keySet()){
            String key =name.toString();
            System.out.println(i+". "+key);
        i++;
        }
    }

    private void startParse() throws ParseException {
        long startTime;
        for (index = 1; index < docText.length; index++) {
            //if it's a line seperator. increase line number
//            System.out.println("Begin : "+docText[index]);
            if (docText[index].length() == 0 || docText[index].equals("-")) {
                continue;
            }
            else if (stopWords.contains(docText[index]) || stopWords.contains(docText[index].toUpperCase()) || stopWords.contains(docText[index].toLowerCase())) {
                continue;
            }
            else {
                //docText[index] = replaceReplace(docText[index]);
                //System.out.println(docText[index]);
                if (docText[index].length() == 0 || docText[index].equals(" ")) {
                    continue;
                }
                wordType type = identifyDoc(docText[index]); // identifying the word

                if (type == wordType.NUMBER) {
                    parseNumber(docText[index], index);
                    long endTime = System.currentTimeMillis();

                } else if (type == wordType.SYMBOL) {
                    parseSymbol(docText[index], index);
                } else if (type == wordType.WORD) {
                    try {
                        parseWord(index);
                    } catch (ParseException e) {
                        return;
                    }
                }
            }
            wordPosition++;
        }

    }

    private void parseSymbol(String str, int index) {
        Term tempTerm = new Term();
        tempTerm.setType("Symbol");
        ArrayList<String> first_keywords=getFirstKeyWords();
        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        String substring =str.substring(1,str.length());
            try {
                Number number = format.parse(substring);
                double number_term = number.doubleValue();
                if(index+1<docText.length && first_keywords.contains(docText[index+1])) {
                    switch (docText[index + 1]) {
                        case "million":
                            tempTerm.setName(convertDouble(number_term) + " " + "M" + " " + "Dollars");
                            break;
                        case "billion":
                            tempTerm.setName(convertDouble(number_term * 1000) + " " + "M" + " " + "Dollars");
                            break;
                        default:
                            tempTerm.setName(docText[index] + " " + docText[index + 1]);
                    }
                }
                else if(number_term<1000000){
                    tempTerm.setName(substring+" "+"Dollars");
                }
                else
                    tempTerm.setName(convertDouble(number_term/1000000)+" "+"M"+" "+"Dollars");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            handleTerm(tempTerm);

        }

    private void parseWord( int index) throws ParseException {
        Term tempTerm = new Term();
        tempTerm.setType("Word");
        if(stopWords.contains(docText[index])){
            return;
        }
        if (docText[index].contains("-")) {
            tempTerm.setName(docText[index]);
        }
        //month that starts with word example : MAY 19945
        else if(months.containsKey(docText[index]) && isNotOutBound(index+1) && isNumber(docText[index+1])){
            if(months.get(docText[index])<10){
                tempTerm.setName(docText[index+1]+"-"+"0"+months.get(docText[index]));
            }
            else {

                tempTerm.setName(docText[index+1]+"-"+months.get(docText[index]));
            }
        }
        //between number and number
        else if(docText[index].equals("between") && isNotOutBound(index + 1) && isNumber(docText[index + 1])
                && isNotOutBound(index + 2) && docText[index + 2].equals("and") && isNotOutBound(index + 3)
                && isNumber(docText[index + 3]))
                        {
                            tempTerm.setName(docText[index] + " " + docText[index + 1] + " " + docText[index + 2] + " " + docText[index + 3]);
                        }
        else{
            //if word is lowercase - check for uppercase in the first letter in the terms map
            if(testAllLowerCase(docText[index]) &&
                    (terms.containsKey(docText[index].substring(0,1).toUpperCase()+docText[index].substring(1))
                     || terms.containsKey(docText[index].toUpperCase()))){
                if(terms.containsKey(docText[index].substring(0,1).toUpperCase()+docText[index].substring(1))){
                    tempTerm = terms.remove(docText[index].substring(0,1).toUpperCase()+docText[index].substring(1));
                    tempTerm.setName(docText[index]);
                }
                else{
                    tempTerm = terms.remove(docText[index].toUpperCase());

                    tempTerm.setName(docText[index]);
                }

            }
            else if(testAllLowerCase(docText[index]) && terms.containsKey(docText[index].toUpperCase())){
                    return; //dont add lowercase wh
            }

            else{
                tempTerm.setName(docText[index]);
            }
//            else if()
        }

             handleTerm(tempTerm);

    }

    public  boolean testAllLowerCase(String str){
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(c >= 65 && c <= 90) {
                return false;
            }
        }
        return true;
    }
    public boolean isNotOutBound(int i){
        return i < docText.length;
    }




    public wordType identifyDoc(String str) {
            try {
                if (isSymbol(str)) {
//                    System.out.println("Symbol : " + str);
                    return wordType.SYMBOL;
                }
                else if(str.charAt(0)<48 || str.charAt(0)>57 ) {

                    return wordType.WORD;
                }
                else if (isNumber(str)) {
//                    System.out.println("Number : " + str);
                    return wordType.NUMBER;
                }
            } catch (ParseException e) {
//                System.out.println("Word : " + str);
                return wordType.WORD;


            }
            return wordType.WORD;
        }


    public boolean isSymbol (String str) throws ParseException {

        ArrayList<Character> ch = new ArrayList<>();
        ch.add('$');
        if(str.equals("")){
            return false;
        }
        if(ch.contains(str.charAt(0))) {
            NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
            String substring = str.substring(1, str.length());
            Number number = format.parse(substring);
            return true;
        }

        return false;
    }

    private boolean isNumber(String str) throws ParseException {
//        if(str.charAt(0) >=48 && str.charAt(0) <=57){
//            return true;
//        }
//        return false;
        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        Number number = format.parse(str);
        double test=number.doubleValue();
        return true;
    }

    public boolean isFraction(String str) {

        if (str.contains("/")) {
            String separator = "/";
            String[] new_str = str.split(Pattern.quote(separator));
            if(str.length() <=2 || new_str.length<=1){
                return false;
            }
            NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
            try {
                Number first = format.parse(new_str[0]);
                Number second = format.parse(new_str[1]);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
        return false;
    }


    /*
                     Starting Parsing functions
     */
    /**
     * Handles terms of type Number
     * @param str given String for term
     */
    public void parseNumber(String str, int index){
        Term tempTerm = new Term();
        tempTerm.setType("Number");
        ArrayList<String> first_keywords=getFirstKeyWords(); // An Array of listed keywords that can appear after a term
//        Term
        HashSet<String> toReturn = new HashSet<>();
        boolean fraction = str.contains("/");
        boolean fractionAndText = fraction && index + 1 < docText.length && first_keywords.contains(docText[index + 1]);  //???
        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        Number number = null;
            try {
                number = format.parse(docText[index]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double number_term = number.doubleValue();
            //has a keyword after the word
            if (!fraction && index + 1 < docText.length && first_keywords.contains(docText[index + 1])) {
                //number + value + U.S + Dollar
                if (index + 2 < docText.length && docText[index + 2].equals("U.S.") && index + 3 < docText.length && docText[index + 3].equals("Dollars")) {
                    switch (docText[index + 1]) {
                        case "million":
                            break;
                        case "billion":
                            number_term = number_term * 1000;
                            break;
                        case "trillion":
                            number_term = number_term * 1000000;
                            break;
                    }
                    tempTerm.setName(convertDouble(number_term) + " " + "M" + " " + "Dollars");
//                    toReturn.add(convertDouble(number_term) + " " + "M" + " " + "Dollars");
                } else {
                    if (!fraction) {
                        switch (docText[index + 1]) {
                            case "Thousand":
                                number_term = number_term;
                                tempTerm.setName(convertDouble(number_term) + "K");
//                                toReturn.add(convertDouble(number_term) + "K");
                                break;
                            case "Million":
                                number_term = number_term;
                                tempTerm.setName(convertDouble(number_term) + "M");
//                                toReturn.add(convertDouble(number_term) + "M");
                                break;
                            case "Trillion":
                            case "Billion":
                                number_term = number_term;
                                tempTerm.setName(convertDouble(number_term) + "B");
//                                toReturn.add(convertDouble(number_term) + "B");
                                break;
                            case "percent":
                            case "percentage":
                                tempTerm.setName(convertDouble(number_term) + "%");
//                                toReturn.add(convertDouble(number_term) + "%");
                                break;
                            case "Dollars":
                                tempTerm.setName(transformNumber(number_term));
//                                toReturn.add(transformNumber(number_term));
                                break;

                        }
                    }


                }
            }
            //Fraction
            else if (index + 1 < docText.length && isFraction(docText[index + 1])) {
                if (number_term < 1000000 && index + 2 < docText.length && docText[index + 2].equals("Dollars")) {
                    tempTerm.setName(convertDouble(number_term) + " " + docText[index + 1] + " " + "Dollars");
//                    toReturn.add(convertDouble(number_term) + " " + docText[index + 1] + " " + "Dollars");
                    index = index++; //skip the next word in the document :O
//                    Parser.setIndex(index + 1);
                } else {
                    tempTerm.setName(convertDouble(number_term) + " " + docText[index + 1]);
//                    toReturn.add(convertDouble(number_term) + " " + docText[index + 1]);
                }

//                return toReturn; // ??? check why //TODO : Check why !

            } else if (fraction) {
                if (index + 1 < docText.length && first_keywords.contains(docText[index + 1])) {
                    tempTerm.setName(docText[index] + " " + docText[index + 1]);
//                    toReturn.add(docText[index] + " " + docText[index + 1]);
                } else {
                    tempTerm.setName(docText[index]);
//                    toReturn.add(docText[index]);
                }

            } else if (index + 1 < docText.length && months().containsKey(docText[index + 1])) {
                String month = "" + months().get(docText[index + 1]);
                if (months().get(docText[index + 1]) < 10) {
                    month = "0" + months().get(docText[index + 1]);
                }
                if (number_term < 10) {
                    tempTerm.setName(month + "-" + "0" + convertDouble(number_term));
//                    toReturn.add(month + "-" + "0" + convertDouble(number_term));
                } else {
                    tempTerm.setName(month + "-" + convertDouble(number_term));
//                    toReturn.add(month + "-" + convertDouble(number_term));
                }
            } else {
                if (!fraction) {
                    if (number_term >= 1000 && number_term < 1000000) {
                        tempTerm.setName(convertDouble(number_term / 1000) + "K");
//                        toReturn.add(convertDouble(number_term / 1000) + "K");
                    } else if (number_term >= 1000000 && number_term < 1000000000) {
                        tempTerm.setName(convertDouble(number_term / 1000000) + "M");
//                        toReturn.add(convertDouble(number_term / 1000000) + "M");
                    } else if (number_term >= 1000000000) {
                        tempTerm.setName(convertDouble(number_term / 1000000000) + "B");
//                        toReturn.add(convertDouble(number_term / 1000000000) + "B");
                    } else {
                        tempTerm.setName(convertDouble(number_term));
//                        toReturn.add(convertDouble(number_term));
                    }
                }

            }

            handleTerm(tempTerm); //
        }


//    public HashMap<Term, HashMap<Doc, Integer>> getTermsInfo() {
    public HashMap<String, Term> getTerms() {
        return terms;
    }

    //    }
    private void handleTerm(Term toCheck) {

        /*
            if found term not avilable in the term list && not a stop word
                set doc frequency of the term to 1
                increase doc distnict term by 1
                add to terms
                set corpus frequency to 1
                set df to 1
                insert it to the terms with the document found.
                set doc frequency
                set term location in doc
         */
        if (terms.get(toCheck.getName()) == null) {
            //if (!stopWords.contains(toCheck.getName()) && !stopWords.contains(toCheck.getName().toUpperCase()) && !stopWords.contains(toCheck.getName().toLowerCase())) {
                toCheck.getDocFrequency().put(doc, 1);
                doc.setDistinctwords(doc.getDistinctwords() + 1);
                toCheck.setCorpusFrequency(toCheck.getCorpusFrequency() + 1);
                terms.put(toCheck.getName(), toCheck);
//                 System.out.println("New Term : "+toCheck.getName());
            //}
        }
        // if found term already exists in the term list:
            /*
                Get the term from the list
                increase it's corpus frequency
                see if the term has current doc in it's docfreq hashmap
                    if not - add it, increase df,
                    if it does - increase the freq by 1, see if freq > max tf
                add the term back to the hashmap and overwrite the old term
             */
        else {
//             System.out.println(toCheck.getName());
            Term UsedTerm = terms.get(toCheck.getName());
            UsedTerm.setCorpusFrequency(UsedTerm.getCorpusFrequency() + 1);
            if (UsedTerm.getDocFrequency().get(doc) == null) {
                UsedTerm.setDf(UsedTerm.getDf() + 1);
                UsedTerm.getDocFrequency().put(doc, 1);
//                System.out.println("Used Term "+UsedTerm.getName());
            } else {
                UsedTerm.getDocFrequency().put(doc, UsedTerm.getDocFrequency().get(doc) + 1);
                updateDocMaxTf(UsedTerm.getDocFrequency().get(doc));
//                System.out.println("Used Term "+UsedTerm.getName());
            }
        }
    }

    //        return termsInfo;

/*
                Begining of Utilities functions
 */

    public ArrayList<String> getFirstKeyWords(){
        ArrayList<String> keywords=new ArrayList<String>();
        keywords.add("Thousand");
        keywords.add("Million");
        keywords.add("Trillion");
        keywords.add("Billion");
        keywords.add("percent");
        keywords.add("percentage");
        keywords.add("Dollars");
        keywords.add("billion");
        keywords.add("million");
        keywords.add("trillion");
        return keywords;
    }
    public void updateDocMaxTf(int term_tf){
        if(doc.getMaxtf() < term_tf){
            doc.setMaxtf(term_tf);
        }
    }
    /**
     * Convert Double to String without leaving Zero Trails behind
     * @param d given double
     * @return formatted string
     */
    public String convertDouble(double d){
        String result=""+d;
        return result=result.indexOf(".") < 0 ? result : result.replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    /**
     * Helper function to convert double to string with additional parameters
     * @param number given double
     * @return
     */
    public String transformNumber(double number) {
        if(number>=1000000){
            number=number/1000000;
            return convertDouble(number)+" "+"M"+" "+"Dollars";
        }
        return number+" "+"Dollars";
    }

    /**
     * List of all months by names and order
     * @return
     */
    public HashMap<String, Integer> months(){

        HashMap<String,Integer> parse_months = new HashMap<String,Integer>();
        parse_months.put("JAN",1);
        parse_months.put("Jan",1);
        parse_months.put("JANUARY",1);
        parse_months.put("January",1);
        parse_months.put("FEB",2);
        parse_months.put("Feb",2);
        parse_months.put("February",2);
        parse_months.put("FEBRUARY",2);
        parse_months.put("Mar",3);
        parse_months.put("MAR",3);
        parse_months.put("March",3);
        parse_months.put("MARCH",3);
        parse_months.put("Apr",4);
        parse_months.put("APR",4);
        parse_months.put("April",4);
        parse_months.put("APRIL",4);
        parse_months.put("May",5);
        parse_months.put("MAY",5);
        parse_months.put("June",6);
        parse_months.put("JUNE",6);
        parse_months.put("July",7);
        parse_months.put("JULY",7);
        parse_months.put("Aug",8);
        parse_months.put("AUG",8);
        parse_months.put("August",8);
        parse_months.put("AUGUST",8);
        parse_months.put("Sept",9);
        parse_months.put("SEPT",9);
        parse_months.put("September",9);
        parse_months.put("SEPTEMBER",9);
        parse_months.put("Oct",10);
        parse_months.put("OCT",10);
        parse_months.put("October",10);
        parse_months.put("OCTOBER",10);
        parse_months.put("Nov",11);
        parse_months.put("NOV",11);
        parse_months.put("November",11);
        parse_months.put("NOVEMBER",11);
        parse_months.put("Dec",12);
        parse_months.put("DEC",12);
        parse_months.put("December",12);
        parse_months.put("DECEMBER",12);

        return parse_months;
    }

    public int terms_size(){return terms.size();}
    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public Doc getDoc() {

        return doc;
    }
}
