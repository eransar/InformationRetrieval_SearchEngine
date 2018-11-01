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
//        parse.ParseDocument(rf.docList.get(0));

//        String s="1,000";
//        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
//        Number number = format.parse(s);
//        double d = number.doubleValue();
//        parse.printTerms();

        Document doc = new Document("1","2","33","320 million U.S. Dollars");
        parse.setDoc(doc);
        parse.ParseDocument(doc);





//        s = s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
//        System.out.println(s);
    }

}
