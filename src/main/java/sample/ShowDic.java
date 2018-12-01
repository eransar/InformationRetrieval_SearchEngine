package sample;

import PartA.Indexer;
import PartA.Pointer;
import javafx.fxml.Initializable;
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
    public TreeMap<String,Pointer> treeMap = new TreeMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FileInputStream fi = null;
        try {
            fi = new FileInputStream(new File(indexer.getPath()+File.separator+"dictionary.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            treeMap = (TreeMap<String, Pointer>)oi.readObject();
            StringBuilder stringBuilder = new StringBuilder("");
            for(Map.Entry<String,Pointer> entry : treeMap.entrySet()) {
                String key = entry.getKey();
                Pointer value = entry.getValue();
                stringBuilder.append(key+" "+value.getTerm_df()+"\n");
            }
            dic_show.setText(stringBuilder.toString());
            oi.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



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
