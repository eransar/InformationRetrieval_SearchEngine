package PartA;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Class that represents a query file in our program.
 */
public class QueryFile {
    private Document doc;
    private File querysFile;
    private LinkedHashMap<String, String> dict_replaceWords;
    private ArrayList<Query> queryArrayList;

    public QueryFile(File querysFile) {
        this.querysFile = querysFile;
        queryArrayList = new ArrayList<>();
        dict_replaceWords = new LinkedHashMap<>();
        init_replace();
    }

    /**
     * Parsing query file with jsoup
     * @throws IOException
     */
    public void parse() throws IOException {
        Document query;
        query = Jsoup.parse(querysFile, "UTF-8");
        ArrayList<String> list = new ArrayList<>();
        Elements queryTags = query.select("top");
        for (Element element : queryTags) {
            String title = element.select("title").text();
            String[] num = element.select("num").text().split("Number: ");
            String numOfQuery = num[1].split(" ")[0];
            String[] description_array = (element.select("desc").text().split("Description:"))[1].split("Narrative: ");
            String description = description_array[0];
            String narr = description_array[1];
            Query query1 = new Query(numOfQuery,title,description,narr);
            queryArrayList.add(query1);
        }
    }

    private void init_replace() {
        dict_replaceWords.put("\n", " ");
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public File getQuerysFile() {
        return querysFile;
    }

    public void setQuerysFile(File querysFile) {
        this.querysFile = querysFile;
    }

    public LinkedHashMap<String, String> getDict_replaceWords() {
        return dict_replaceWords;
    }

    public void setDict_replaceWords(LinkedHashMap<String, String> dict_replaceWords) {
        this.dict_replaceWords = dict_replaceWords;
    }

    public ArrayList<Query> getQueryArrayList() {
        return queryArrayList;
    }

    public void setQueryArrayList(ArrayList<Query> queryArrayList) {
        this.queryArrayList = queryArrayList;
    }
}
