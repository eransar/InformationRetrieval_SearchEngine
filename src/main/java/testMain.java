import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class testMain {
    public static void main(String[] args) throws IOException, ParseException {
        ReadFile rf = new ReadFile();
        rf.updateDocList("FT923_5");
        Parser parse = new Parser();
        parse.ParseDocument(rf.docList.get(0));

//        String s="1,000";
//        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
//        Number number = format.parse(s);
//        double d = number.doubleValue();
        parse.printTerms();

//        s = s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
//        System.out.println(s);
    }

    public static  String decimalNumbers(String wordTemp)
    {
        StringBuilder finalWord = new StringBuilder();
        int len = wordTemp.length();
        int index = wordTemp.indexOf(".");
        int corruntLen = len - index;
        if (corruntLen > 3) {
            //String s = Character.toString(wordTemp.charAt(index + 3));
            //System.out.println(wordTemp);
            //if it is a number at the third char after the .
            if (Character.isDigit(wordTemp.charAt(index+3))) {
                int three = Integer.parseInt(""+wordTemp.charAt(index+3));
                if (three > 4) {
                    //String t = wordTemp.charAt(index + 2) + "";
                    int twoAfterDec = wordTemp.charAt(index + 2)-'0';
                    twoAfterDec = twoAfterDec + 1;
                    String number = twoAfterDec + "";
                    finalWord.append(wordTemp.substring(0, index + 2) + number.charAt(0));
                } else {
                    finalWord.append(wordTemp.substring(0, index + 3));
                }
            }
        }
        return finalWord.toString();

    }
}
