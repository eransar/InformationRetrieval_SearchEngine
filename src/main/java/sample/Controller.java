package sample;

import PartA.CityIndexer;
import PartA.Indexer;
import PartA.Pointer;
import PartA.ReadFile;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class Controller implements Initializable {

    public Button corpusPath;
    public Button PostingPath;
    public Button StopW;
    public Button run;
    public Button reset;
    public Button LoadDictionary;
    public Button ShowDictionary;
    public TextField corpusField;
    public TextField stopWordsField;
    public TextField PostingField;
    public CheckBox StemmingCheckBox;
    public ChoiceBox<String> language;
    public ReadFile rf;
    private String PathOfCorpus;
    private String StopWordsPath;
    public String PathOfPosting;
    private boolean Steam;
    public Label error;
    private String newPostingPath="";
    private File f;
    public Indexer indexer = Indexer.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StemmingCheckBox.setSelected(false);
        language.setDisable(true);
        language.setItems(FXCollections.observableArrayList(
                "Chinese", "English", "עברית", "French", "German", "Greek")
        );
        language.setValue("English");
        language.setDisable(true);
        error.setVisible(false);
    }

    /**
     * select Corpus BY browser
     */
    public void openCourpus(ActionEvent event) {
        String path = GetPath();
        if (path != null) {
            PathOfCorpus = path;
            corpusField.setText(path);
        }
    }

    /**
     * Select stopWords by Browser
     */
    public void openStopWords(ActionEvent event) {
        String path = browse();
        if (path != null) {
            StopWordsPath = path;
            stopWordsField.setText(path);
        }
    }

    /**
     * save the path to save posting
     */
    public void SavePostingPath(ActionEvent event) {
        String path = GetPath();
        if (path != null) {
            PathOfPosting = path;
            PostingField.setText(path);
        }
    }

    /**
     * open file by source
     */
    private String browse() {
        String path;
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file", "*.txt"));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            path = file.getPath();
            return path;
        } else {
            System.out.println("problem");
            return null;
        }
    }

    /**
     * Get FolderPath To save
     */
    private String GetPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory == null) {
            return null;
        } else {
            return selectedDirectory.getAbsolutePath();
        }
    }

    /**
     * To steam or not?
     */
    public void Tostemming(ActionEvent event) {
        if (StemmingCheckBox.isSelected())
            Steam = true;
        else
            Steam = false;
    }

    public void Reset(ActionEvent event) throws IOException {
        if(!newPostingPath.equals("")){
            FileUtils.cleanDirectory(new File(PathOfPosting));
            indexer.reset();
            CityIndexer.getInstance().reset();
            System.out.println("ddd");
        }
    }

    public void ShowDictionary(ActionEvent event) {
        //if has dictionary or not?
        try {
            if(indexer.getSortDicTree()==null || indexer.getSortDicTree().size()==0){
                LoadDictionary();
            }
            Stage stage = new Stage();
            stage.setTitle("Dictionary");
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ShowDictionary.fxml"));
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            scene.getStylesheets().add(getClass().getClassLoader().getResource("CssController.css").toExternalForm());
            stage.show();
        } catch (Exception e) {
            error.setText("file not found");
        }
    }

    public void LoadDictionary() throws IOException {
        if(PathOfPosting !=null && !PathOfPosting.equals("")){
            indexer.loadDictionary(PathOfPosting,Steam);
        }
        else{
            error.setVisible(true);
            error.setText("please run the\nprogram it first");
        }
    }


    public void SetAll(ActionEvent event) throws IOException {
        newPostingPath = PathOfPosting;
        if (stopWordsField.getText().trim().isEmpty() || corpusField.getText().trim().isEmpty() || PostingField.getText().trim().isEmpty()) {
            error.setText("Pleas fill all the fields");
            error.setVisible(true);
        } else {
            if (Steam) {
                f = new File(PathOfPosting + File.separator+"Stem");
                newPostingPath = PathOfPosting +File.separator+"Stem";
            } else {
                f = new File(PathOfPosting + File.separator+"WithOutStem");
                newPostingPath = PathOfPosting + File.separator+"WithOutStem";
            }
            try {
                if (f.mkdir()) {
                    System.out.println("Directory Created");
                } else {
                    System.out.println("Directory is not created");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            rf = new ReadFile(PathOfCorpus, StopWordsPath, newPostingPath, Steam);
            float start = System.nanoTime();
            rf.start();
            float end = System.nanoTime();
            System.out.println((end - start) * Math.pow(10, -9) / 60);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Indexer Information");
            alert.setContentText("Number of docs: "+rf.getCountDOCs()+"\n"
                                    +"Number of terms: "+indexer.getDictionary().size()+"\n"
                                    +"Time of creating inverted index: "+((end - start) * Math.pow(10, -9) / 60)+ "min");
            alert.show();
            HashSet<String> languages = indexer.getSet_languages();
            language.setItems(FXCollections.observableArrayList(languages));
            language.setDisable(false);


            System.out.println(indexer.getSortDicTree().size());
//            Map<String,Integer> tambal =sortedMap();
//            createCSVFile(tambal);
//            int i = 5;
            CityIndexer.getInstance().findMax();

        }
    }


    public Map<String,Integer> sortedMap(){
        TreeMap<String,Integer> result = new TreeMap();
        for (Map.Entry<String,Pointer> term : indexer.getDictionary().entrySet()){
            result.put(term.getKey(),term.getValue().getTerm_df());
        }


        Map<String,Integer> topTen =
                result.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return topTen;


    }

    public void createCSVFile(Map<String,Integer> map) throws IOException {
        FileWriter out = new FileWriter(newPostingPath+File.separator+"book_new.csv");
        String[] HEADERS = { "term", "df"};
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                .withHeader(HEADERS))) {
            map.forEach((author, title) -> {
                try {
                    printer.printRecord(author, title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
