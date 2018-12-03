package sample;

import PartA.Indexer;
import PartA.Pointer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShowDic implements Initializable {


    private ArrayList<String> sortDic;
    public Indexer indexer = Indexer.getInstance();
    public TextArea dic_show;
    public TreeMap<String,Pointer> treeMap;
    public ListView<String> data;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

            treeMap = indexer.getSortDicTree();
            /*StringBuilder stringBuilder = new StringBuilder("");
            for(Map.Entry<String,Pointer> entry : treeMap.entrySet()) {
                String key = entry.getKey();
                Pointer value = entry.getValue();
                stringBuilder.append(key+" "+value.getTerm_df()+"\n");
            }*/
            for(Map.Entry<String,Pointer> entry : treeMap.entrySet()) {
                data.getItems().add(entry.getKey() + " " + entry.getValue().getTerm_df()+"\n");
            }
            //dic_show.setText(stringBuilder.toString());


        /*String fileName = indexer.getPath() + "dictionary.txt";

        List<String> list = new ArrayList<String>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

            //br returns as stream and convert it into a List
            list = br.lines().collect(Collectors.toList());
            for(String s:list)
                dic_show.setText(s+"\n");

        } catch (IOException e) {
            e.printStackTrace();
        }*/



        /*sortDic = indexer.getSortDic();
        StringBuilder stringBuilder = new StringBuilder("");
        if(sortDic !=null) {
            for (String s : sortDic) {
                stringBuilder.append(s+"\n");
            }
            dic_show.setText(stringBuilder.toString());
        }*/
    }



}
