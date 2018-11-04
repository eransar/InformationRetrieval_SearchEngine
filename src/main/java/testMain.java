import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;

public class testMain {
    public static void main(String[] args) throws IOException, ParseException {
        ReadFile rf = new ReadFile();
        rf.updateDocList("FT923_5");
        Parse parse = new Parse(rf.docList.get(0));
        parse.ParseDoc();




//        String s="1,000";
//        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
//        Number number = format.parse(s);
//        double d = number.doubleValue();
//        parse.printTerms();

//        Document doc = new Document("1","2","33","$100 billion");
//        parse.setDoc(doc);
//        parse.ParseDocument(doc);

//        Parse parse = new Parse(new Document("1","2","33","May 1994"));
//        parse.ParseDoc();




//        s = s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
//        System.out.println(s);
    }

}
