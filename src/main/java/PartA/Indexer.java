package PartA;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Indexer implements Runnable {
    private static Indexer ourInstance = new Indexer();
    private ConcurrentHashMap<String,Pointer> dictionary;
    private HashSet<String> file_names;
    private HashMap<String,Integer> dict_files;
    private ArrayList<String> sortDic;

    public static Indexer getInstance() {
        return ourInstance;
    }

    private Indexer(){
        dictionary=new ConcurrentHashMap<String,Pointer>();
        file_names=new HashSet();
        dict_files=new HashMap<>();
    }

    public void reset(){
        Indexer.getInstance().dictionary.clear();
    }

    public HashMap<String, Integer> getDict_files() {
        return dict_files;
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
            String path_toWrite =path+"\\"+file_name+".txt";
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
    public HashSet<String> getFile_names() {
        return file_names;
    }
    public synchronized Pointer isExist(String term_name){
        if((term_name.charAt(0) >=65 && term_name.charAt(0)<=90) ||(term_name.charAt(0) >=97 && term_name.charAt(0)<=122)){
            // is lower case
//            if(term_name.charAt(0) >=97 && term_name.charAt(0)<=122){ // first char is lower case
//                if(dictionary.contains(term_name.substring(0,1).toUpperCase()+term_name.substring(1))){
//                    Pointer Otherpointer = dictionary.remove(term_name.substring(0,1).toUpperCase()+term_name.substring(1));
//                    dictionary.put(term_name,Otherpointer);
//                    return Otherpointer;
//                }
//                else if(dictionary.contains(term_name.toUpperCase())){
//                    Pointer Otherpointer = dictionary.remove(term_name.toUpperCase());
//                    dictionary.put(term_name,Otherpointer);
//                    return Otherpointer;
//
//                }
//                else if(dictionary.contains(term_name.toLowerCase())){
//                    return dictionary.get(term_name.toLowerCase());
//                }
//
//            }
        }
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

    public ConcurrentHashMap<String, Pointer> getDictionary() {
        return dictionary;
    }

    public ArrayList<String> getSortDic() {
        return sortDic;
    }

    public void setSortDic(ArrayList<String> sortDic) {
        this.sortDic = sortDic;
    }

    public void printTofile(String path) throws IOException {
        File f = new File(path+File.separator+dictionary+".txt");
        FileWriter writer = new FileWriter(f);
        BufferedWriter bf = new BufferedWriter(writer);



        for (Map.Entry<String,Pointer> dict_data: dictionary.entrySet()){
            writer.write(dict_data.getKey()+" "+dict_data.getValue().getFile_name()+"|"+dict_data.getValue().getLine_number()+"|"+dict_data.getValue().getTerm_df()+System.lineSeparator());
        }
        writer.close();
    }

    public void sortDictionary(){
        sortDic = new ArrayList<String>(dictionary.keySet());
        Collections.sort(sortDic);
        for (int i = 0; i < sortDic.size(); i++) {
            sortDic.set(i,sortDic.get(i)+" "+dictionary.get(sortDic.get(i)).getTerm_df());
        }
    }


    @Override
    public void run() {

    }
}
