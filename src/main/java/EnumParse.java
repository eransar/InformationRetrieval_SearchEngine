import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public enum EnumParse {

    word{
        public HashSet<String> parse(int i){
        HashSet<String> toReturn = new HashSet<>();
        String[] text = Parser.getTextWithoutDelimeters();

        return toReturn;
        }
    },
    number{
        public ArrayList<String> first_keywords=getFirstKeyWords();

        public HashSet<String> parse(int i){
            HashSet<String> toReturn = new HashSet<>();
            String[] text = Parser.getTextWithoutDelimeters();
            NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
            Number number = null;
            try {
                number = format.parse(text[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double number_term = number.doubleValue();
            //has a keyword after the word
            if(i+1 < text.length && first_keywords.contains(text[i+1])){
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
                    switch (text[i+1]){
                        case "Thousand":number_term = number_term/1000;
                            toReturn.add(convertDouble(number_term) +"K");
                        break;
                        case "Million": number_term = number_term/1000000;
                            toReturn.add(convertDouble(number_term) +"M");
                            break;
                        case "Billion":number_term = number_term/1000000000;
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
            else if(i+1<text.length && months().containsKey(text[i+1])){
                if(number_term<10)
                    toReturn.add("0"+months().get(text[i+1])+"-"+number_term);
                else
                    toReturn.add(months().get(text[i+1])+"-"+number_term);
            }
            else{
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

            return toReturn;
        }

        public ArrayList<String> getFirstKeyWords(){
            ArrayList<String> keywords=new ArrayList<String>();
            keywords.add("Thousand");
            keywords.add("Million");
            keywords.add("Trillion");
            keywords.add("percent");
            keywords.add("percentage");
            keywords.add("Dollars");
            keywords.add("billion");
            keywords.add("million");
            keywords.add("trillion");
            return keywords;
        }
        public HashMap<String, Integer> months(){

            HashMap<String,Integer> parse_months = new HashMap<String,Integer>();

            parse_months.put("JANUARY",1);
            parse_months.put("January",1);
            parse_months.put("February",2);
            parse_months.put("FEBRUARY",2);
            parse_months.put("March",3);
            parse_months.put("MARCH",3);
            parse_months.put("April",4);
            parse_months.put("APRIL",4);
            parse_months.put("June",6);
            parse_months.put("JUNE",6);
            parse_months.put("August",8);
            parse_months.put("AUGUST",8);
            parse_months.put("July",7);
            parse_months.put("JULY",7);
            parse_months.put("September",9);
            parse_months.put("SEPTEMBER",9);
            parse_months.put("October",10);
            parse_months.put("OCTOBER",10);
            parse_months.put("November",11);
            parse_months.put("NOVEMBER",11);
            parse_months.put("December",12);
            parse_months.put("DECEMBER",12);

            return parse_months;
        }
    },
    symbol{
        HashSet<String> toReturn = new HashSet<>();
        public HashSet<String> parse(int i){
            String[] text = Parser.getTextWithoutDelimeters();
            return toReturn;
        }
    };

    public String transformNumber(double number) {
        if(number>1000000){
            return number/1000000+" "+"M"+" "+"Dollars";
        }
        return number+" "+"Dollars";
    }

    public abstract HashSet<String> parse(int i);
    public String convertDouble(double d){
        String result=""+d;
        return result=result.indexOf(".") < 0 ? result : result.replaceAll("0*$", "").replaceAll("\\.$", "");
    }
}
