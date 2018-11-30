package sample;

import PartA.Indexer;
import PartA.Pointer;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class ShowDic implements Initializable {


    private ArrayList<String> sortDic;
    public Indexer indexer = Indexer.getInstance();
    public TextField dic_show;
    @Override
    public void initialize(URL location, ResourceBundle resources) {



        StringBuilder stringBuilder = new StringBuilder("");
        for (String s: sortDic) {
            stringBuilder.append(s+" "+indexer.getDictionary().get(s)+"\n");
        }
        dic_show.setText(stringBuilder.toString());
    }



}
