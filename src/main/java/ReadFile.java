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
        URL url = getClass().getResource(path);
        File file = new File(url.getPath());
        FileReader fr = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fr);
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        int i=1;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
            stringBuffer.append("\n");
            i++;
        }
        String File = stringBuffer.toString();
        String[] Docs = File.split("<DOC>");
        fr.close();
        for (int j = 1; j < Docs.length ; j++) {
            CreateDocFromString(Docs[j]);
        }

    }

    public void CreateDocFromString(String str){
        String DocNo="";
        String Date="";
        String Header="";
        String Text="";

        DocNo = str.split("<DOCNO>")[1].split("</DOCNO>")[0];
        Date = str.split("<DATE1>")[1].split("</DATE1>")[0];
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
