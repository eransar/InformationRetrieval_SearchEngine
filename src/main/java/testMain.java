import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;

public class testMain {
    public static void main(String[] args) throws IOException, ParseException {

//        ReadFile readFile = new ReadFile();
//        int i=0;
//        File directory = new File("d:\\documents\\users\\eransar\\Downloads\\corpus");
//        File[] fList2 = directory.listFiles();
//        // get all the files from a directory
//        File[] fList = fList2[0].listFiles();
//
//        for (File file : fList) {
//            if (file.isFile()) {
//                readFile.updateDocList(file.getAbsolutePath());
//            } else if (file.isDirectory()) {
//                File file2 = new File(file.getAbsolutePath());
//                File[] fList1 = file2.listFiles();
//                for (File file1 : fList1) {
//                    if (file1.isFile()) {
//                        readFile.updateDocList(file1.getAbsolutePath());
//
//                        System.out.println(file1.getAbsolutePath());
//                    }
//                }
//            }
//        }
        float startTime = System.nanoTime();
        ReadFile rf = new ReadFile();
        rf.updateDocList("D:\\documents\\users\\eransar\\Downloads\\IR\\IR\\src\\main\\resources\\FB396001");
        Parse parse = new Parse(rf.docList.get(0));
        parse.ParseDoc();
        for (int i = 1; i < rf.docList.size() ; i++) {
            parse.setDoc(rf.docList.get(i));
            parse.ParseDoc();
        }
        float endTime = System.nanoTime();

        System.out.println((endTime-startTime)/1000000000);


//        String s="-5 3/4";
//        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
//        Number number = format.parse(s);
//        System.out.println(number.doubleValue());
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

