package PartA;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Indexer {
    private static Indexer ourInstance = new Indexer();
    private HashMap<String, Term> dict_cache;
    private HashMap<String,Term> dict_capitals;
    private HashMap<String,Integer> dict_files;
    private HashSet<String> file_names;
    private HashSet<String> set_languages;
    private HashSet<Doc> set_docs;
    private ConcurrentHashMap<String,Pointer> dictionary;
    private List<List<String>> list_termsByAlhabet = new ArrayList<List<String>>();
    private ArrayList<String> sortDic;
    private int docsaverage;


    private boolean first_chunk;
    private String path;
    private transient TreeMap<String,Pointer> sortDicTree;
    public static Indexer getInstance() {
        return ourInstance;
    }

    public HashMap<String, Term> getDict_cache() {
        return dict_cache;
    }

    private Indexer(){
        dictionary=new ConcurrentHashMap<String,Pointer>();
        dict_cache=new HashMap<>();
        file_names=new HashSet();
        dict_files=new HashMap<>();
        this.dict_capitals=new HashMap<>();
        this.list_termsByAlhabet = new ArrayList<List<String>>();
        this.first_chunk=true;
        this.set_languages = new HashSet();
        this.set_docs=new HashSet<>();
    }

    /**
     * Reset Indexer data.
     */
    public void reset() {
        dictionary=new ConcurrentHashMap<String,Pointer>();
        dict_cache=new HashMap<>();
        file_names=new HashSet();
        dict_files=new HashMap<>();
        this.dict_capitals=new HashMap<>();
        this.list_termsByAlhabet = new ArrayList<List<String>>();
        this.first_chunk=true;
        sortDicTree= new TreeMap<>();
        sortDic= new ArrayList<>();
        set_languages=new HashSet<>();
        this.set_docs=new HashSet<>();
    }


    public void InitDic(String PathOfPosting) throws IOException {
        File f = new File(PathOfPosting + File.separator + "dictionary.txt");
        String str="";
        BufferedReader reader = new BufferedReader(new FileReader(f));
        List<String> file_dic = new ArrayList<>();
        while ((str = reader.readLine()) != null){
            file_dic.add(str);
        }
        String key="";
        for (String line: file_dic ) {
            //dictionary
            String [] spliteLine = line.split(" ");
            key = spliteLine[0];
            spliteLine = spliteLine[1].split(",");
            Pointer p = new Pointer(spliteLine[0],Integer.parseInt(
                    spliteLine[1]),Integer.parseInt(spliteLine[2]));
            dictionary.put(key,p);
        }
    }

    /**
     * Controling whether this is the first chunk of writing files or not
     * @param first_chunk
     */
    public void setFirst_chunk(boolean first_chunk) {
        this.first_chunk = first_chunk;
    }

    /**
     * Writing first posting files to disk
     * @param path - given path of posting files
     */
    public void initFiles(String path){
        int counter=0;

        for(char alphabet = 'a'; alphabet <='z'; alphabet++ )
        {
            dict_files.put(""+alphabet,counter);
            file_names.add(""+alphabet);
            counter++;
        }

        file_names.add("cities");
        dict_files.put("cities",counter);
        counter++;
        file_names.add("docs");
        dict_files.put("docs",counter);
        counter++;
        file_names.add("numbers");
        dict_files.put("numbers",counter);
        counter++;
        file_names.add("symbols");
        dict_files.put("symbols",counter);
        counter++;
        file_names.add("others");
        dict_files.put("others",counter);
        counter++;
        file_names.add("dictionary");
        dict_files.put("dictionary",counter);

        for(String file_name: file_names)
        {
            String path_toWrite =path+File.separator+file_name+".txt";
            File alphabet_file = new File(path_toWrite);
            try {
                FileWriter fileWriter=new FileWriter(alphabet_file);
                BufferedWriter bf = new BufferedWriter(fileWriter);
                bf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * Find pointer in the dictionary for a given term
     * @param term_name - given term
     * @return pointer of the term
     */
    public  Pointer isExist(String term_name){
        return dictionary.get(term_name);

    }
    public Pointer isExist_Capital(String term_name){
        char first_letter = Character.toLowerCase(term_name.charAt(0));
        StringBuilder check_term = new StringBuilder(first_letter);
        for (int i = 1; i <term_name.length() ; i++) {
            check_term.append(term_name.charAt(i));
        }
        return dictionary.get(check_term);
    }
    public int getLineNumber(String term_name){
        Pointer pointer_data=this.dictionary.get(term_name);
        return pointer_data.getLine_number();

    }

    /**
     * Initialize the sorted dictionary
     */
    public void sortDictionary(){
        sortDicTree = new TreeMap<>(dictionary);
        /*sortDic = new ArrayList<String>(dictionary.keySet());
        Collections.sort(sortDic);
        for (int i = 0; i < sortDic.size(); i++) {
            sortDic.set(i,sortDic.get(i)+" "+dictionary.get(sortDic.get(i)).getTerm_df());
        }*/

    }

    /**
     * Writing the dictionary to the posting directory
     * @throws IOException
     */
    public void WriteDictionary() throws IOException {

        FileOutputStream f = new FileOutputStream(new File(path+File.separator+"dictionary.txt"));
        ObjectOutputStream o = new ObjectOutputStream(f);
        // Write objects to file
        o.writeObject(sortDicTree);
        o.flush();
        o.close();

    }

    /**
     * Loading the dicationary from the posting path with stemm or not
     * @param pathPosting - posting path
     * @param stem - boolean for stemming
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadDictionary(String pathPosting,boolean stem) throws IOException, ClassNotFoundException {
        FileInputStream fi = null;

            if(stem)
                fi = new FileInputStream(new File(pathPosting+File.separator+"Stem"+File.separator+"dictionary.txt"));
            else
                fi = new FileInputStream(new File(pathPosting+File.separator+"WithOutStem"+File.separator+"dictionary.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            sortDicTree = (TreeMap<String, Pointer>)oi.readObject();
            oi.close();

    }


    /**
     * classifying the terms in the cached dictionary into the files they will need to be written in.
     * @param dict
     * @param list_terms
     */
    private void fillAlphabetArrays(HashMap<String,Term> dict, ArrayList<String> list_terms) {
        for (int i = 0; i <31 ; i++) {
            list_termsByAlhabet.add(new ArrayList<String>());
        }
        int place=0;
        for (int i = 0; i < list_terms.size() ; i++) {
            switch ((dict.get(list_terms.get(i)).getType())){
                case "Number":
                    place = dict_files.get("numbers");
                    list_termsByAlhabet.get(place).add(list_terms.get(i));
                    break;
                case "Symbol":
                    place = dict_files.get("symbols");
                    list_termsByAlhabet.get(place).add(list_terms.get(i));
                    break;
                case "City":
                    place = dict_files.get("cities");
                    list_termsByAlhabet.get(place).add(list_terms.get(i));
                    break;
                default:

                    try {
                        place = dict_files.get(""+list_terms.get(i).toLowerCase().charAt(0));
                    } catch (Exception e) {
                        place = dict_files.get("others");
                    }

                    list_termsByAlhabet.get(place).add(list_terms.get(i));
            }

        }
    }



    public void handleCapitalLetters() throws IOException {
        ArrayList<String> capitalTerms = new ArrayList<String>(dict_capitals.keySet()); //create array from list

        fillAlphabetArrays(dict_capitals,capitalTerms);

        //get data from files

        for (String file_name : file_names) {
            if (file_name.equals("dictionary")) {
                continue;
            }
            int place = dict_files.get(file_name);
            File toOpen = new File(path + File.separator+ file_name + ".txt");
            BufferedReader in = new BufferedReader(new FileReader(toOpen));
            String str;
            //opening file and adding it all to array
            List<String> file_content = new ArrayList<String>();
            while ((str = in.readLine()) != null) {
                file_content.add(str);
            }
            in.close();

            file_content = mergeCapitals(list_termsByAlhabet.get(place), file_content, file_name);
            File toWrite = new File(path + File.separator + "\\" + file_name + ".txt");
            FileWriter writer = new FileWriter(toWrite, false);

            for (int i = 0; i < file_content.size(); i++) {
                writer.write(file_content.get(i) + System.lineSeparator());
            }
            writer.close();



//            pool.submit(this);
        }

        dict_capitals.clear();
    }

    public List<String> mergeCapitals(List<String> capitalTerms ,List<String> file_content , String file_name ){

        for (int i = 0; i < capitalTerms.size() ; i++) {
            //find term in lower case first letter form
            char first_letter = Character.toLowerCase(capitalTerms.get(i).charAt(0));
            StringBuilder check_term = new StringBuilder(""+first_letter);
            for (int k = 1; k <capitalTerms.get(i).length() ; k++) {
                check_term.append(capitalTerms.get(i).charAt(k));
            }
            Pointer find_location=isExist(check_term.toString());
            if(find_location==null){
                Term OtherTerm=dict_capitals.get(capitalTerms.get(i));
                StringBuilder termData = new StringBuilder("");
                for (Map.Entry<Doc, Integer> _doc : OtherTerm.getDocFrequency().entrySet()){

                    termData.append("|"+_doc.getKey().getDOCNO()+","+_doc.getValue()+","+_doc.getKey().getFile()); //DOCNO,FrequencyInDoc,File Name of Doc
                }
                file_content.add(OtherTerm.getDf()+" "+termData);
                Pointer OtherPointer = new Pointer(file_name,file_content.size()-1,OtherTerm.getDf());
                if(OtherTerm.getName().charAt(0) >=65 && OtherTerm.getName().charAt(0)<=90){
                    dictionary.put(OtherTerm.getName().toUpperCase(),OtherPointer);
                }
                else{
                    dictionary.put(OtherTerm.getName(),OtherPointer);
                }


            }
            else{
                /**
                 * is exist in the dictionary in lower case in the first letter
                 */
                String lineToChange = null;
                lineToChange = file_content.get(find_location.getLine_number());

                Term OtherTerm = dict_capitals.get(capitalTerms.get(i));
                OtherTerm.setName(check_term.toString());
                String[] currentline = lineToChange.split(" ");
                int currentdf=Integer.parseInt(currentline[0]);
                int chunkdf = OtherTerm.getDf();
                int newdf=currentdf+chunkdf;
                StringBuilder termData = new StringBuilder("");
                for (Map.Entry<Doc, Integer> _doc : OtherTerm.getDocFrequency().entrySet()){
                    termData.append("|"+_doc.getKey().getDOCNO()+","+_doc.getValue()+","+_doc.getKey().getFile());
                }
                file_content.set(find_location.getLine_number(),newdf+" "+currentline[1]+termData);
                Pointer p1 =dictionary.get(OtherTerm.getName());


                dictionary.put(OtherTerm.getName(),new Pointer(file_name,p1.getLine_number(),newdf));

            }

        }
        return file_content;

    }

    public void setPath(String path){
        this.path=path;
    }

    public String getPath() {
        return path;
    }

    public void writeToDisk() throws IOException {
//        if (first_chunk) {
//            initFiles(path);
//            first_chunk = false;
//        }
        ArrayList<String> list_terms = new ArrayList<>(dict_cache.keySet());



        fillAlphabetArrays(dict_cache,list_terms);
        //get data from files

        for (String file_name : file_names)
        {
            if(file_name.equals("dictionary")){
                continue;
            }
            int place = dict_files.get(file_name);
            File toOpen = new File(path+File.separator+file_name+".txt");
            BufferedReader in = new BufferedReader(new FileReader(toOpen));
            String str;
            //opening file and adding it all to array
            List<String> file_content = new ArrayList<String>();
            while((str = in.readLine()) != null){
                file_content.add(str);
            }
            in.close();

            file_content=mergeArrays(list_termsByAlhabet.get(place),file_content, file_name);
            File toWrite = new File(path+File.separator+file_name+".txt");
            FileWriter writer = new FileWriter(toWrite,false);

            for (int i = 0; i < file_content.size() ; i++) {
                writer.write(file_content.get(i)+System.lineSeparator());
            }
            writer.close();
        }
        dict_cache.clear();
        list_termsByAlhabet.clear();
        File p = new File(path);
    }


    /**
     * Helper function to merge the posting files with the dictionary each gradual write.
     * @param chunk_content
     * @param file_content
     * @param filename
     * @return
     */
    private List<String> mergeArrays(List<String> chunk_content , List<String> file_content, String filename) {
        for (int i = 0; i < chunk_content.size() ; i++) {

            Pointer find_location=isExist(chunk_content.get(i));
            if(find_location==null){

                Term OtherTerm= dict_cache.get(chunk_content.get(i));
                StringBuilder termData = new StringBuilder("");
                for (Map.Entry<Doc, Integer> _doc : OtherTerm.getDocFrequency().entrySet()){

                    termData.append("|"+_doc.getKey().getDOCNO()+","+_doc.getValue()+","+_doc.getKey().getFile()); //DOCNO,FrequencyInDoc,File Name of Doc
                }
                file_content.add(OtherTerm.getDf()+" "+termData);
                Pointer OtherPointer = new Pointer(filename,file_content.size()-1,OtherTerm.getDf());
                dictionary.put(OtherTerm.getName(),OtherPointer);
//                indexer.addToHashMap(OtherTerm.getName(),filename+" "+(file_content.size()-1));

            }
            else {
                //find index and change the line

                String lineToChange = null;
                lineToChange = file_content.get(getLineNumber(chunk_content.get(i)));

                Term OtherTerm = dict_cache.get(chunk_content.get(i));
                String[] currentline = lineToChange.split(" ");
                int currentdf=Integer.parseInt(currentline[0]);
                int chunkdf = OtherTerm.getDf();
                int newdf=currentdf+chunkdf;
                StringBuilder termData = new StringBuilder("");
                for (Map.Entry<Doc, Integer> _doc : OtherTerm.getDocFrequency().entrySet()){
                    termData.append("|"+_doc.getKey().getDOCNO()+","+_doc.getValue()+","+_doc.getKey().getFile());
                }
                String s = newdf+" "+currentline[1]+termData;
                file_content.set(getLineNumber(chunk_content.get(i)),newdf+" "+currentline[1]+termData);
                Pointer p1 =dictionary.get(OtherTerm.getName());


                dictionary.put(OtherTerm.getName(),new Pointer(filename,p1.getLine_number(),newdf));

            }
        }
        return file_content;
    }

    /**
     * Writing the docs hashset into file
     */
    public void writeDocs() {

        String filename = path + File.separator + "docs.txt";
        FileWriter fw = null; //the true will append the new data
        try {
            fw = new FileWriter(filename, true);
            for (Doc docs : set_docs) {
                fw.write(docs.getDOCNO() + "|" + docs.getDistinctwords() + "|" + docs.getMaxtf()+System.lineSeparator());
            }
            set_docs = new HashSet<>();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void addtoAvg(int size){
        docsaverage+=size;
    }
    public void calculateAvg(){
        docsaverage=docsaverage/set_docs.size();
    }

    public int getDocsaverage() {
        return docsaverage;
    }

    //<editor-fold desc="Getters and setters">
    public HashMap<String, Term> getDict_capitals() {
        return dict_capitals;
    }

    public HashSet<String> getSet_languages() {
        return set_languages;
    }

    public TreeMap<String, Pointer> getSortDicTree() {
        return sortDicTree;
    }

    public HashSet<Doc> getSet_docs() {
        return set_docs;
    }
    public HashMap<String, Integer> getDict_files() {
        return dict_files;
    }
    public HashSet<String> getFile_names() {
        return file_names;
    }
    public ConcurrentHashMap<String, Pointer> getDictionary() {
        return dictionary;
    }

    public ArrayList<String> getSortDic() {
        return sortDic;
    }

    public void setSortDic(ArrayList<String> sortDic) {
        this.sortDic = sortDic;
    }
    //</editor-fold>
}
