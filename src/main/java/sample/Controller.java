package sample;

import PartA.*;
import PartA.Ranking.Ranker;
import PartA.Ranking.RankingObject;
import PartA.Ranking.Semantics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.IndexedCheckModel;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
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
    ////////////////////////////////////
    public CheckComboBox CheckComboBox_Citis;
    public Button load_Q_file;
    public TextField Q_text;
    public Button run_Q;
    ////////////////////////////////////
    public ReadFile rf;
    private String PathOfCorpus="";
    private String StopWordsPath="";
    public String PathOfPosting="";
    private boolean Steam;
    public Label error;
    private String newPostingPath = "";
    private File f;
    public Indexer indexer = Indexer.getInstance();
    public ArrayList<String> citisNames;
    public ListView<Node> listView_docs;
    private boolean semantic=false;
    public CheckBox check_Semantic;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StemmingCheckBox.setSelected(false);
        language.setDisable(true);
        CheckComboBox_Citis.setDisable(true);
        language.setItems(FXCollections.observableArrayList(
                "Language")
        );
        language.setValue("Language");
        language.setDisable(true);
        error.setVisible(false);
        citisNames = new ArrayList<>();
    }

    /**
     * select Corpus BY browser
     */
    public void openCourpus(ActionEvent event) {
        String path = GetPath();
        if (path != null) {
            PathOfCorpus = path;
            corpusField.setText(path);
            File tmp = new File(PathOfCorpus + File.separator + "stop_words");
            if (tmp.exists() && !tmp.isDirectory())
                StopWordsPath = PathOfCorpus + File.separator + "stop_words";
            else {
                tmp = new File(PathOfCorpus + File.separator + "stop_words.txt");
                if (tmp.exists() && !tmp.isDirectory())
                    StopWordsPath = PathOfCorpus + File.separator + "stop_words.txt";
                else
                    error.setText("there are not stopWord file\nYou can init manually");
            }

        }
        stopWordsField.setText(StopWordsPath);
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

    /**
     * reset the system - cleaning memory
     * @param event
     * @throws IOException
     */
    public void Reset(ActionEvent event) throws IOException {
        if (!newPostingPath.equals("")) {
            FileUtils.deleteDirectory(new File(PathOfPosting + File.separator + "Stem"));
            FileUtils.deleteDirectory(new File(PathOfPosting + File.separator + "WithOutStem"));
            indexer.reset();
            CityIndexer.getInstance().reset();
            language.getItems().clear();
            language.setItems(FXCollections.observableArrayList(
                    "Language")
            );
            language.setValue("Language");
            newPostingPath="";
            indexer.setLoad(false);
        }
    }

    /**
     * show the Dictionary in listView
     * @param event
     */
    public void ShowDictionary(ActionEvent event) {
        //if has dictionary or not?
        try {
            if (indexer.getSortDicTree() == null || indexer.getSortDicTree().size() == 0) {
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
            error.setText("file not found\nyou may need to run it first");
        }
    }

    /**
     * load the Dictionary ,Language from disc if its not load
     */
    public void LoadDictionary()  {
        if (PathOfPosting != null && !PathOfPosting.equals("")) {
            try {
                indexer.setLoad(true);
                indexer.setStem(Steam);
                indexer.loadDocs(PathOfPosting,Steam);
                indexer.loadDictionary(PathOfPosting, Steam);
                indexer.loadLanguage(PathOfPosting, Steam);
                indexer.loadAvgFromDisk(PathOfPosting,Steam);
                indexer.loadCitis(PathOfPosting,Steam);
                indexer.setPath(PathOfPosting);
                languageChoosieBox();
                CitisCheckComboBox();
            } catch (Exception e) {
                e.printStackTrace();
                error.setVisible(true);
                error.setText("please run the\nprogram it first");
            }
        } else {
            error.setVisible(true);
            error.setText("please run the\nprogram it first");
        }
    }

    /**
     * running func
     * @param event
     * @throws IOException
     * @throws IOException
     */
    public void SetAll(ActionEvent event) throws IOException {
        indexer.setLoad(false);
        newPostingPath = PathOfPosting;
        if (stopWordsField.getText().trim().isEmpty() || corpusField.getText().trim().isEmpty() || PostingField.getText().trim().isEmpty()) {
            error.setText("Pleas fill all the fields");
            error.setVisible(true);
        } else {
            if (Steam) {
                f = new File(PathOfPosting + File.separator + "Stem");
                newPostingPath = PathOfPosting + File.separator + "Stem";
            } else {
                f = new File(PathOfPosting + File.separator + "WithOutStem");
                newPostingPath = PathOfPosting + File.separator + "WithOutStem";
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
            alert.setContentText("Number of docs: " + rf.getCountDOCs() + "\n"
                    + "Number of terms: " + indexer.getDictionary().size() + "\n"
                    + "Time of creating inverted index: " + ((end - start) * Math.pow(10, -9) ) + " sec");
            alert.show();
            languageChoosieBox();
            CitisCheckComboBox();
        }
    }

    private void languageChoosieBox() {
        HashSet<String> languages = indexer.getSet_languages();
        language.setItems(FXCollections.observableArrayList(languages));
        language.setValue(languages.iterator().next());
        language.setDisable(false);
    }


    /**
     * build list of citis for the user to search for
     */
    private void CitisCheckComboBox() {
        for(String S : CityIndexer.getInstance().dict_city.keySet()){
            citisNames.add(S);
        }
        citisNames.sort(new Comparator<String>() {
            @Override
                public int compare(String obj1, String obj2) {
                    return obj1.compareTo(obj2);
                }

        });
        ObservableList<String> Citis = FXCollections.observableArrayList(citisNames);
        CheckComboBox_Citis.setDisable(false);
        CheckComboBox_Citis.getItems().setAll(Citis);
    }

    public void runQuery(ActionEvent event) {
        String query = "";
        if (Q_text.getText() != null && !Q_text.getText().equals("")) {
            HashSet<String> set_CitisByUser = new HashSet<>();
            ObservableList<Integer> indexOfCheckComboBox_Citis = CheckComboBox_Citis.getCheckModel().getCheckedIndices();
            for (Integer i : indexOfCheckComboBox_Citis) {
                set_CitisByUser.add((String) CheckComboBox_Citis.getItems().get(i));
            }
            query = Q_text.getText();
            Searcher searcher = new Searcher(query, set_CitisByUser);
            searcher.getPointers();
            Semantics semantics = new Semantics();
            Ranker ranker = searcher.getRanker();
            ranker.calculate();
            Searcher searcher1;
            Ranker ranker1;
            if (semantic) {
                semantics.startConnection();
                String query1 = "";
                for (Map.Entry<String, String> d : Semantics.getMap_concepte().entrySet()) {
                    query1 = query1 + " " + d.getValue();
                }
                searcher1 = new Searcher(query1, set_CitisByUser);
                searcher1.getPointers();
                ranker1 = searcher.getRanker();
                ranker1.calculate();
                margeRank(ranker,ranker1);
            }
            ranker.sortSet();
            DisplayDocs(ranker);
            ranker.writeResults();
        }
    }

    private void margeRank(Ranker r1,Ranker r2) {
        for (Map.Entry<String, RankingObject> d : r2.getMap_ranked_docs().entrySet()){
            if(r1.getMap_ranked_docs().containsKey(d.getKey())){
                double rank1 = r1.getMap_ranked_docs().get(d.getKey()).getRank();
                double rank2 = r2.getMap_ranked_docs().get(d.getKey()).getRank();
                r1.getMap_ranked_docs().get(d.getKey()).setRank(rank1 + 0.5*rank2);
            }
        }
    }

    public void Semantic_Choose(ActionEvent event){
        if(check_Semantic.isSelected())
            semantic = true;
        else
            semantic = false;
    }

    /**
     * show Entities for specific doc;
     * @param event
     */
    private void OnClickEntities(ActionEvent event){
        Button b = ((Button)event.getSource());
        int i = Integer.parseInt(b.getId());
        //Entities data structers
    }

    /**
     * display list of 50 relevant docs
     * @param ranker
     */
    private void DisplayDocs(Ranker ranker) {
        listView_docs.getItems().clear();
        int i = 0;
        for(RankingObject r : ranker.getSorted_rankingobject()){
            if(i>=50)
                break;
            HBox hBox = new HBox();
            hBox.resize(526,267/4);
            Button button = new Button();
            button.setText("Show Entities");
            button.resize(61,31);
            button.setId(""+i);
            button.setOnAction(this::OnClickEntities);
            Label docName = new Label();
            docName.setText("   " + r.getDOCNO());
            System.out.println(r.getDOCNO());
            hBox.getChildren().add(button);
            hBox.getChildren().add(docName);
            listView_docs.getItems().add(hBox);
            i++;
            //System.out.println(r.getDOCNO());
        }
    }

    /**
     * use query text file
     * @param event
     * @throws FileNotFoundException
     */
    public void BrowseQuery(ActionEvent event) throws FileNotFoundException {
        String path = browse();
        if(path!=null) {
            File f = new File(path);
            FileReader fr = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fr);
            //צריך לפרסר ולשלוח לsearch
        }
    }


}


