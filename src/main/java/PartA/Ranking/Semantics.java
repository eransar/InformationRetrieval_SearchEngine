package PartA.Ranking;

import PartA.Indexer;
import PartA.Query;
import PartA.Searcher;
import javafx.scene.control.Alert;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Semantics {

    private Indexer indexer = Indexer.getInstance();
    private static HashMap<String, ArrayList<String>> map_concepte;
    public Object[] parsed_json;
    private Query query;

    public Semantics(Query q) {
        map_concepte = new HashMap<>();
        query = q;
    }

    public void startConnection() {
        String[] terms = query.getTitle().split(" ");
        for (String termName : terms) {
            String URL = "https://api.datamuse.com/words?ml=" + termName;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(URL).build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Semantic Api");
                alert.setHeaderText("There is no internet connection");
                alert.show();
            }
            JSONParser parser = new JSONParser();
            Object obj = null;
            try {
                try {
                    obj = parser.parse(response.body().string());
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (obj != null) {
                    String word = "";
                    parsed_json = ((JSONArray) obj).toArray();
//                    for (Object s : parsed_json) {
//                        word = (String) ((JSONObject) s).get("word");
//                        map_concepte.put(termName, word);
//                    }
                    int index = 0;
                    map_concepte.put(termName, new ArrayList<>());
                    for (Object s : parsed_json) {
                        word = (String) ((JSONObject) s).get("word");
                        map_concepte.get(termName).add(index, word);
                        if (index == 4)
                            break;
                        index++;
                    }
                }
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static HashMap<String, ArrayList<String>> getMap_concepte() {
        return map_concepte;
    }

    public static void setMap_concepte(HashMap<String, ArrayList<String>> map_concepte) {
        Semantics.map_concepte = map_concepte;
    }
}
