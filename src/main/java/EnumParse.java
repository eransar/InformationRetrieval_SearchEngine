import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Pattern;

public enum EnumParse {

    word{
        public HashSet<String> parse(){
        HashSet<String> toReturn = new HashSet<>();
        String[] text = Parser.getTextWithoutDelimeters();

        return toReturn;
        }
    },
    number{
        public ArrayList<String> first_keywords=getFirstKeyWords();

        public HashSet<String> parse(){
            int i=Parser.getIndex();
            HashSet<String> toReturn = new HashSet<>();
            String[] text = Parser.textWithoutDelimeters;
            boolean fraction=text[i].contains("/");
            boolean fractionAndText =fraction && i+1<text.length && first_keywords.contains(text[i+1]);
            NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
            Number number = null;


            try {
                number = format.parse(text[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double number_term = number.doubleValue();
            //has a keyword after the word
            if(!fraction && i+1 < text.length && first_keywords.contains(text[i+1])){
                //number + value + U.S + Dollar
                if(i+2<text.length && text[i+2].equals("U.S.") && i+3<text.length && text[i+3].equals("Dollars")){
                    switch (text[i+1]){
                        case "million":
                        break;
                        case  "billion": number_term =number_term*1000;
                        break;
                        case "trillion":number_term = number_term*1000000;
                        break;
                    }
                    toReturn.add(convertDouble(number_term)+" "+"M"+" "+"Dollars");
                }
                else{
                    if(!fraction){
                        switch (text[i+1]){
                            case "Thousand":number_term = number_term;
                                toReturn.add(convertDouble(number_term) +"K");
                                break;
                            case "Million": number_term = number_term;
                                toReturn.add(convertDouble(number_term) +"M");
                                break;
                            case "Trillion":
                            case "Billion":number_term = number_term;
                                toReturn.add(convertDouble(number_term) +"B");
                                break;
                            case "percent":
                            case "percentage":
                                toReturn.add(convertDouble(number_term) +"%");
                                break;
                            case "Dollars":
                                toReturn.add(transformNumber(number_term));
                                break;

                        }
                    }


                }
            }
            //Fraction
            else if(i+1<text.length && isFraction(text[i+1])){
                if(number_term < 1000000 && i+2<text.length && text[i+2].equals("Dollars")){
                    toReturn.add(convertDouble(number_term)+" "+text[i+1]+" "+"Dollars");
                    Parser.setIndex(i+1);
                }


                else{
                    toReturn.add(convertDouble(number_term)+" "+text[i+1]);
                }

                return toReturn;

            }
            else if(fraction){
                 if(i+1 < text.length && first_keywords.contains(text[i+1])){
                    toReturn.add(text[i]+" "+text[i+1]);
                }
                else{
                     toReturn.add(text[i]);
                 }

            }
            else if(i+1<text.length && months().containsKey(text[i+1])){
                String month =""+months().get(text[i+1]);
                if(months().get(text[i+1]) < 10){
                    month="0"+months().get(text[i+1]);
                }
                if(number_term<10){
                    toReturn.add(month+"-"+"0"+convertDouble(number_term));
                }
                else{
                    toReturn.add(month+"-"+convertDouble(number_term));
                }
            }
            else{
                    if(!fraction){
                        if(number_term >=1000 && number_term <1000000){
                            toReturn.add(convertDouble(number_term/1000)+"K");
                        }
                        else if (number_term>=1000000 && number_term <1000000000){
                            toReturn.add(convertDouble(number_term/1000000)+"M");
                        }
                        else if(number_term >=1000000000 ){
                            toReturn.add(convertDouble(number_term/1000000000)+"B");
                        }
                        else{
                            toReturn.add(convertDouble(number_term));
                        }
                    }

            }

            return toReturn;
        }




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



        public boolean isFraction(String str) {

            if (str.contains("/")) {
                String separator = "/";
                String[] new_str = str.split(Pattern.quote(separator));
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

    },
    symbol{
        HashSet<String> toReturn = new HashSet<>();
        public ArrayList<String> first_keywords=getFirstKeyWords();
        public HashSet<String> parse(){
            int i=Parser.getIndex();
            String[] text = Parser.getTextWithoutDelimeters();
            NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
            String substring = text[i].substring(1, text[i].length());
            try {
                Number number = format.parse(substring);
                double number_term = number.doubleValue();
                if(i+1<text.length && first_keywords.contains(text[i+1])) {
                    switch (text[i + 1]) {
                        case "million":
                            toReturn.add(convertDouble(number_term) + " " + "M" + " " + "Dollars");
                            break;
                        case "billion":
                            toReturn.add(convertDouble(number_term * 1000) + " " + "M" + " " + "Dollars");
                            break;
                        default:
                            toReturn.add(text[i] + " " + text[i + 1]);
                    }
                }
                else if(number_term<1000000){
                    toReturn.add(substring+" "+"Dollars");
                }
                else
                    toReturn.add(convertDouble(number_term/1000000)+" "+"M"+" "+"Dollars");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return toReturn;
        }
    };

    public String transformNumber(double number) {
        if(number>=1000000){
            number=number/1000000;
            return convertDouble(number)+" "+"M"+" "+"Dollars";
        }
        return number+" "+"Dollars";
    }

    public abstract HashSet<String> parse();
    public String convertDouble(double d){
        String result=""+d;
        return result=result.indexOf(".") < 0 ? result : result.replaceAll("0*$", "").replaceAll("\\.$", "");
    }
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
}
