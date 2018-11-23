package sample;

import PartA.ReadFile;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

public class Controller {

    public Button corpusPath;
    public Button PostingPath;
    public Button Run;
    public TextField corpusField;
    public TextField PostingField;
    public ReadFile rf;
    public String StopWordsPath;


    public void openCourpus(ActionEvent event) {
        String path = browse();
        if(path!=null) {
            try {
                rf= new ReadFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openStopWords(ActionEvent event) {
        String path = browse();
        if(path!=null) {
            StopWordsPath= path;
        }
    }




        private String browse(){
            String path;
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file", "*.text"));
            File file = fc.showOpenDialog(null);
            if (file != null) {
                path = file.getPath();
                return path;
            } else {
                System.out.println("problem");
                return null;
            }
        }
    }
