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
  private int counter;

  public ReadFile(String path) throws IOException {
    this.path = path;
    this.docs = new HashSet<Doc>();
    this.parse=new Parse();
    this.counter=1;
    this.parse=new Parse();
  }

  public void start() throws IOException {
    File input = new File(path);
    File[] corpus = input.listFiles();

    for (int i = 0; i < corpus.length; i++) {
      if (corpus[i].isDirectory()) {//other condition like name ends in html
        for (int j = 0; j < corpus[i].listFiles().length; j++) {
          jparse(corpus[i].listFiles()[j]);
        }

      } else {
        jparse(corpus[i]);

      }
    }
  }

  public void FileToDocs(File file) throws IOException {
    FileReader fr = new FileReader(file.getAbsolutePath());
    BufferedReader bufferedReader = new BufferedReader(fr);
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      sb.append(line);
      sb.append("\n");
    }
    String File = sb.toString();
    String[] Docs = File.split("<DOC>");
    fr.close();

//    FillDocsHashSet(Docs);
//    sendToParse();

  }

  public void FillDocsHashSet(String[] docs){
    Document doc;
    String DOCNO;
    String TEXT;
    for (int i = 0; i < docs.length; i++) {
      //parsing file by DOCNO and TEXT
      doc = Jsoup.parse(docs[i]);
      DOCNO=doc.select("DOCNO").text();
      TEXT=doc.select("TEXT").text();
      this.docs.add(new Doc(DOCNO,TEXT));
    }
  }

  public void sendToParse(){
  for (Doc d : docs){
    float start = System.nanoTime();

    parse.setDoc(d);
//    parse.ParseDoc();
    float end = System.nanoTime();
    System.out.println(end-start);
  }
  docs.clear();
  }




















  public HashSet<Doc> getDocs() {
    return docs;
  }

  private void jparse(File file) throws IOException {
    Document doc;
    doc = Jsoup.parse(file, "UTF-8");
    String TEXT = "";
    String DOCNO = "";
    Elements doctags = doc.select("DOC").wrap("DOCNO,TEXT");

    for (  Element element : doctags){
      DOCNO = element.select("DOCNO").text();
      TEXT= element.select("TEXT").text();
      parse.ParseDoc(new Doc(DOCNO,file.getName()),TEXT);

    }

//    SplitFile(doctags.get(0).text());


//      if(DATE.equals("")){
//          DATE=jdoc.select("DATE").text();
//      }
//      parse.setDoc(new Doc(DOCNO,DATE,HEADER,TEXT));
//      parse.ParseDoc();

//    Elements DOCNOo = doc.select("DOCNO");
////    Elements header = doc.select("HEADER");
//    Elements text = doc.select("TEXT");

//  }
  }

  private void SplitFile(String DocText){

    String[] lines=DocText.split("\n");
    StringBuilder content;
    for (int i = 0; i <DocText.length() ; i++) {

    }

  }
//

}
