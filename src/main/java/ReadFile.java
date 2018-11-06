import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFile {

    ArrayList<Document> docList = new ArrayList<Document>();


    public ReadFile(){
        docList=new ArrayList<Document>();
    }

    public void updateDocList(String path) throws IOException {
        File file = new File(path);
        FileReader fr = new FileReader(file.getAbsolutePath());
        BufferedReader bufferedReader = new BufferedReader(fr);
        StringBuffer stringBuffer = new StringBuffer();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
            int i=8;
            stringBuffer.append("\n");
        }
        String File = stringBuffer.toString();
        String[] Docs = File.split("<DOC>");
        fr.close();
        for (int j = 1; j < Docs.length ; j++) {
            CreateDocFromString(Docs[j]);
        }
        try {
            Parse p = new Parse(docList.get(0));
            p.ParseDoc();
            for (int i = 1; i<docList.size(); i++){
                p.setDoc(docList.get(i));
                p.ParseDoc();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void CreateDocFromString(String str){
        String DocNo="";
        String Date="";
        String Header="";
        String Text="";

        DocNo = str.split("<DOCNO>")[1].split("</DOCNO>")[0];
        Date = str.split("<DATE1>|<DATE>")[1].split("</DATE1>|</DATE>")[0];
        Text = str.split("<TEXT>")[1].split("</TEXT>")[0];
        Document document = new Document(DocNo,Date,Header,Text);
        docList.add(document);
    }

//    public void CreateDocFromString(String str){
//        String DocNo="";
//        String Date="";
//        String Header="";
//        String Text="";
//
//        int DocNostartPosition = str.indexOf("[DOCNO]") + "[DOCNO]".length();
//        int DocNoendPosition = str.indexOf("[/DOCNO]", DocNostartPosition);
//        DocNo = str.substring(DocNostartPosition, DocNoendPosition);
//
//        System.out.println(DocNo);
//
//
//
//
//
//
////        System.out.println(str);
//
//        docList.add(new Document(DocNo,Date,Header,Text));
//    }



}
