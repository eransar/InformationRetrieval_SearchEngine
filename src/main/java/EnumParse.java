import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
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
            else if(i+1<text.length && (months().contains(text[i+1].substring(0,1).toUpperCase()+text[i+1].substring(1)) || months().contains(text[i+1].toUpperCase()))){

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
        public ArrayList<String> months(){
            ArrayList<String> keywords=new ArrayList<String>();
            keywords.add("january");
            keywords.add("february");
            keywords.add("march");
            keywords.add("april");
            keywords.add("may");
            keywords.add("june");
            keywords.add("july");
            keywords.add("august");
            keywords.add("september");
            keywords.add("october");
            keywords.add("november");
            keywords.add("december");
            return keywords;
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
