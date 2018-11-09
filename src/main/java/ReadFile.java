import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class ReadFile {

  private HashSet<Doc> docs;
  private String path;
  public Parse parse;
  private int counter;

  public ReadFile(String path) throws IOException {
    this.path = path;
    this.docs = new HashSet<Doc>();
    this.parse=new Parse();
    this.counter=1;
  }

  public void start() throws IOException {
    File input = new File(path);
    File[] corpus = input.listFiles();

    for (int i = 0; i < corpus.length; i++) {
      if (corpus[i].isDirectory()) {//other condition like name ends in html
        for (int j = 0; j < corpus[i].listFiles().length ; j++) {
          jparse(corpus[i].listFiles()[j]);
          System.out.println(counter++);
        }

      }
      else{
        jparse(corpus[i]);

      }
    }


  }

  public HashSet<Doc> getDocs() {
    return docs;
  }

  private void jparse(File file) throws IOException {
    Document doc;
    doc = Jsoup.parse(file, "UTF-8");


    String DOCNO = "";
    String DATE = "";
    String HEADER = "";
    String TEXT = "";

    Elements doctags = doc.select("DOC");
    for (Element jdoc : doctags) {
      DOCNO = jdoc.select("DOCNO").text();
      DATE = jdoc.select("DATE1").text();
      HEADER = jdoc.select("HEADER").text();
      TEXT = jdoc.select("TEXT").text();
      if(DATE.equals("")){
          DATE=jdoc.select("DATE").text();
      }
      if (DOCNO.equals("FT924-11838")) {
        System.out.println(DOCNO);
      }
      parse.setDoc(new Doc(DOCNO,DATE,HEADER,TEXT));
      parse.ParseDoc();
    }

    Elements DOCNOo = doc.select("DOCNO");
//    Elements header = doc.select("HEADER");
    Elements text = doc.select("TEXT");

//    if(DOCNOo.size() != text.size()){
//        System.out.println("YEA");
//    }


            /*
               private String CITY;
    private String DOCNO;
    private String DATE;
    private String HEADER;
    private String TEXT;
             */
//
//    if(doctags.size()!=text.size()){
//      System.out.println("different");
//      System.out.println(file.toString());
//    }
//    for (int i = 0; i <doctags.size() ; i++) {
//      parse.setDoc(new Doc(doctags.get(i).text(),text.get(i).text()));
//      parse.ParseDoc();
//    }
////    parse.setDoc(new Doc(docno.text(),date.text(),header.text(),text.text()));
//    parse.ParseDoc();
//  }
  }
}
