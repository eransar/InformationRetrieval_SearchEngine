package PartA;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.util.HashSet;

/**
 * Class that reading a file and and splitting it into docs and sending it to the Parser class.
 */
public class ReadFile {

  private HashSet<Doc> docs;
  private String path;
  public Parse parse;
  private int size;
  private int numofDocs;
  private String stopWordsPath;
  private String path_posting;
  private Indexer indexer;
  private boolean steam;
  private int countDOCs=0;

  public ReadFile(){

  }


  public ReadFile(String path,String StopWordsPath,String path_posting,boolean steam) throws IOException {
    this.path = path;
    this.docs = new HashSet<Doc>();
    this.stopWordsPath=StopWordsPath;
    this.parse = new Parse(StopWordsPath,path_posting);
    this.numofDocs=1;
    this.steam = steam;
    this.path_posting=path_posting;
    this.indexer=Indexer.getInstance();
  }


  /**
   * Starting the operation of the ReadFile class.
   * @throws IOException
   */
  public void start() throws IOException {
    indexer.reset();
    indexer.initFiles(path_posting);
    File input = new File(path);
    File[] corpus = input.listFiles();
    size = corpus.length; //get amount of files
    //parse.setStem(true);
    parse.setStem(steam);

    for (int i = 0; i < corpus.length; i++) {
      if (corpus[i].isDirectory()) {//other condition like name ends in html
        for (int j = 0; j < corpus[i].listFiles().length; j++) {


          if(numofDocs == size /10){
          indexer.writeToDisk();


            numofDocs=1;
          }
          jparse(corpus[i].listFiles()[j]);
          numofDocs++;
        }
      } else {
        jparse(corpus[i]);
      }
    }
    if(indexer.getDict_cache().size()!=0) {
      indexer.writeToDisk();
      indexer.writeDocs();
    }
      //marge capital letters to dictionary
      indexer.handleCapitalLetters();
      indexer.updateDocWeight();
//      for (Doc d : indexer.getDict_docs().values()){
//        d.init_TreeSet();
//        d.init_arrEntities();
//      }
      //sort dictionary
      indexer.sortDictionary();
      indexer.WriteDictionary();
      //write cities to disc
      CityIndexer.getInstance().WriteDictionary(path_posting);
      indexer.WriteLanguage();
      indexer.calculateAvg();
      indexer.writeAvgToDisk();
      indexer.WriteCitis();
  }

  public int getCountDOCs() {
    return countDOCs;
  }

  /**
   * Using jsoup to split the file and send it to the parser class
   * @param file
   * @throws IOException
   */
  private void jparse(File file) throws IOException {
    Document doc;
    doc = Jsoup.parse(file, "UTF-8");
    String TEXT = "";
    String DOCNO = "";
    String CITY = "";
    String HEADER = "";
    String DATE = "";
    String LANGUAGE = "";
    Elements doctags = doc.select("DOC");


    for (Element element : doctags) {
      CITY = getCityFromText(element.outerHtml());
      String[] temp_city = CITY.split(" ");
      int cityindex = 0;
      if (temp_city.length > 2) {
        if (CITY.startsWith("   St.")) {
          CITY = new String(temp_city[3] + " " + temp_city[4]);
        } else {
          while (temp_city[cityindex].equals("")) {
            cityindex++;
          }
          CITY = temp_city[cityindex];
        }
      }

      LANGUAGE = getLanguageFromCity(element.outerHtml());
      DOCNO = element.select("DOCNO").text();
      TEXT = element.select("TEXT").text();
      HEADER = element.select("H3").select("TI").text();
      DATE = element.select("DATE1").text();
      countDOCs++;
      parse.ParseDoc(new Doc(DOCNO, file.getName(), CITY, HEADER, DATE, LANGUAGE), TEXT);
      parse.HadleHeader(HEADER);
    }
  }

  /**
   * Returns the City from the FP 104 in the doc
   * @param text
   * @return
   */
  public String getCityFromText(String text) {
    String[] temp_text = text.split("\n");
    int start = 0;
    int end = 0;
    int counter=0;

    for (int i = 0; i < temp_text.length ; i++) {
      if (temp_text[i].startsWith(" <f p=\"104\">") || temp_text[i].startsWith("  <f p=\"104\">")) {
        if(!temp_text[i+1].equals(" <text>")){
          return temp_text[i+1];
        }

      }
    }
    return "";
  }

  /**
   * Returns the language from the FP 105 tag in the doc.
   * @param text
   * @return
   */
  public String getLanguageFromCity(String text) {
    String[] temp_text = text.split("\n");
    int start = 0;
    int end = 0;
    int counter=0;

    for (int i = 0; i < temp_text.length ; i++) {
      if (temp_text[i].startsWith("  <f p=\"105\">")) {
        if(!temp_text[i+1].equals(" <text>")){
          return temp_text[i+1];
        }

      }
    }
    return "";
  }



}
