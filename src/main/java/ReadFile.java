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



    Elements docno = doc.select("DOCNO");
    Elements date = doc.select("DATE1");
    Elements header = doc.select("HEADER");
    Elements text = doc.select("TEXT");



            /*
               private String CITY;
    private String DOCNO;
    private String DATE;
    private String HEADER;
    private String TEXT;
             */
//
    if(docno.size()!=text.size()){
      System.out.println("different");
      System.out.println(file.toString());
    }
    for (int i = 0; i <docno.size() ; i++) {
      parse.setDoc(new Doc(docno.get(i).text(),text.get(i).text()));
      parse.ParseDoc();
    }
//    parse.setDoc(new Doc(docno.text(),date.text(),header.text(),text.text()));
    parse.ParseDoc();
  }
}
