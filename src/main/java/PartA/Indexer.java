package PartA;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Indexer implements Runnable {

    private ConcurrentHashMap<String,String> dictionary;
    private HashSet<String> file_names;
    private HashMap<String,Integer> dict_files;

    public  Indexer(){
        dictionary=new ConcurrentHashMap<String,String>();
        file_names=new HashSet();
        dict_files=new HashMap<>();
    }

    public void addToHashMap(String term_name, String pointer){
        if(!dictionary.containsKey(term_name)){
            dictionary.put(term_name,pointer);
        }
        else{
            StringBuilder stringBuilder = new StringBuilder(dictionary.get(term_name));
            stringBuilder.append(" "+stringBuilder);
            dictionary.put(term_name,stringBuilder.toString());
        }
    }


    public HashMap<String, Integer> getDict_files() {
        return dict_files;
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
    public synchronized String isExist(String term_name){
        return dictionary.get(term_name);

    }
    public int getLineNumber(String term_name){
        String dict_data=this.dictionary.get(term_name);
        String line_number = dict_data.split(" ")[1];

        return Integer.parseInt(line_number);

    }

    public ConcurrentHashMap<String, String> getDictionary() {
        return dictionary;
    }

    public void printTofile(String path) throws IOException {
        File f = new File(path+File.separator+dictionary+".txt");
        FileWriter writer = new FileWriter(f);
        BufferedWriter bf = new BufferedWriter(writer);



        for (Map.Entry<String,String> str: dictionary.entrySet()){
            writer.write(str.getKey()+" "+str.getValue()+System.lineSeparator());
        }
        writer.close();
    }

    @Override
    public void run() {

    }
}
