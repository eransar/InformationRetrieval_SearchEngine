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

            //has a keyword after the word
            if(i+1 < text.length && first_keywords.contains(text[i+1])){

            }
            else{
                NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);

                try {
                    Number number = format.parse(text[i]);
                    double number_term = number.doubleValue();

                    if(number_term >=1000 && number_term <100000){
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


                } catch (ParseException e) {
                    e.printStackTrace();
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
    },
    symbol{
        HashSet<String> toReturn = new HashSet<>();
        public HashSet<String> parse(int i){
            String[] text = Parser.getTextWithoutDelimeters();
            return toReturn;
        }
    };

    public abstract HashSet<String> parse(int i);
    public String convertDouble(double d){
        String result=""+d;
        return result=result.indexOf(".") < 0 ? result : result.replaceAll("0*$", "").replaceAll("\\.$", "");
    }
}
