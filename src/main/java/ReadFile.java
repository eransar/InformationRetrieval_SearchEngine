
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.util.HashSet;

public class ReadFile {

  private HashSet<Doc> docs;
  private String path;
  public Parse parse;
  private int size;
  private int numofDocs;

  public ReadFile(String path) throws IOException {
    this.path = path;
    this.docs = new HashSet<Doc>();
    this.parse = new Parse();
    this.numofDocs=1;
  }

  public void start() throws IOException {
    File input = new File(path);
    File[] corpus = input.listFiles();
    size = corpus.length; //get amount of files
    parse.setStem(false);


    for (int i = 0; i < corpus.length; i++) {
      if (corpus[i].isDirectory()) {//other condition like name ends in html
        for (int j = 0; j < corpus[i].listFiles().length; j++) {
          if(numofDocs == size /10){
          parse.SaveToDisk();
          System.out.println(parse.getPath());
            numofDocs=1;
          }
          jparse(corpus[i].listFiles()[j]);
          numofDocs++;
        }
      } else {
        jparse(corpus[i]);

      }
    }
    if(parse.terms_size()!=0){
      parse.SaveToDisk();
    }
    System.out.println("Number of terms "+parse.getNumofTerm());
  }



  private void jparse(File file) throws IOException {
    Document doc;
    doc = Jsoup.parse(file, "UTF-8");
    String TEXT = "";
    String DOCNO = "";
    String CITY ="";
    Elements doctags = doc.select("DOC");


    for (Element element : doctags) {
      CITY=getCityFromText(element.outerHtml());
      String [] temp_city = CITY.split(" ");
      if(temp_city.length > 2){
        if(CITY.startsWith("   St.")){
          CITY=temp_city[3]+" "+temp_city[4];
        }
        CITY=temp_city[3];
      }
      DOCNO = element.select("DOCNO").text();
      TEXT = element.select("TEXT").text();
      parse.ParseDoc(new Doc(DOCNO, file.getName(),CITY), TEXT);

    }
  }

  public String getCityFromText(String text) {
    String[] temp_text = text.split("\n");
    int start = 0;
    int end = 0;
    int counter=0;

    for (int i = 0; i < temp_text.length ; i++) {
      if (temp_text[i].startsWith(" <f p=\"104\">")) {
        if(!temp_text[i+1].equals(" <text>")){
//          System.out.println(temp_text[i+1]+" ____");
          return temp_text[i+1];
        }

      }
    }
    return "";
  }



}
