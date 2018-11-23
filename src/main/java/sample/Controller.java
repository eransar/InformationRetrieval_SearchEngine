package sample;

import PartA.ReadFile;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Button corpusPath;
    public Button PostingPath;
    public Button StopW;
    public Button Run;
    public Button reset;
    public Button ShowDictionary;
    public TextField corpusField;
    public TextField stopWordsField;
    public TextField PostingField;
    public ReadFile rf;
    public String PathOfCorpus;
    public String StopWordsPath;
    public String PathOfPosting;
    public CheckBox StemmingCheckBox;
    public ChoiceBox<String> language;
    private boolean Steam;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StemmingCheckBox.setSelected(false);
        language.setItems(FXCollections.observableArrayList(
                "Chinese", "English", "עברית", "French", "German","Greek")
        );
        language.setValue("English");

    }

    /** select Corpus BY browser */
    public void openCourpus(ActionEvent event) {
        String path = GetPath();
        if (path != null) {
            PathOfCorpus = path;
            corpusField.setText(path);
        }
    }

    /** Select stopWords by Browser */
    public void openStopWords(ActionEvent event) {
        String path = GetPath();
        if (path != null) {
            StopWordsPath = path;
            stopWordsField.setText(path);
        }
    }

    /** save the path to save posting */
    public void SavePostingPath(ActionEvent event){
        String path = GetPath();
        if (path != null) {
            PathOfPosting = path;
            PostingField.setText(path);
        }
    }

    /** open file by source */
    private String browse() {
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

    /** Get FolderPath To save */
    private String GetPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if(selectedDirectory == null){
            return null;
        }else{
            return selectedDirectory.getAbsolutePath();
        }
    }
    /** To steam or not? */
    public void Tostemming(ActionEvent event){
        if(StemmingCheckBox.isSelected())
            Steam = true;
        else
            Steam= false;
    }

    public void Reset(ActionEvent event){

    }

    public void ShowDictionary(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Dictionary");
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ShowDictionary.fxml"));
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            scene.getStylesheets().add(getClass().getClassLoader().getResource("CssController.css").toExternalForm());
            stage.show();
        } catch (Exception e) {

        }
    }

}
