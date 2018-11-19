
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.nio.file.Files;
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


    for (int i = 0; i < corpus.length; i++) {
      if (corpus[i].isDirectory()) {//other condition like name ends in html
        for (int j = 0; j < corpus[i].listFiles().length; j++) {
          if(numofDocs == size /10){
          parse.SaveToDisk();
          System.out.println(parse.getTempfolder());
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
    Elements doctags = doc.select("DOC");

    for (Element element : doctags) {
      DOCNO = element.select("DOCNO").text();
      TEXT = element.select("TEXT").text();
      parse.ParseDoc(new Doc(DOCNO, file.getName()), TEXT);

    }
  }



}
