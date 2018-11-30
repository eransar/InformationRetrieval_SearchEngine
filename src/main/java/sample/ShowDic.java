package sample;

import PartA.Indexer;
import PartA.Pointer;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class ShowDic implements Initializable {


    private ArrayList<String> sortDic;
    public Indexer indexer = Indexer.getInstance();
    public TextArea dic_show;
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        sortDic = indexer.getSortDic();
        StringBuilder stringBuilder = new StringBuilder("");
        if(sortDic !=null) {
            for (String s : sortDic) {
                stringBuilder.append(s+"\n");
            }
            dic_show.setText(stringBuilder.toString());
        }
    }



}
