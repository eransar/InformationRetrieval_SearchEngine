import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class Indexer {

    private ConcurrentHashMap<String,String> dictionary;
    private HashSet<String> file_names;

    public  Indexer(){
        dictionary=new ConcurrentHashMap<String,String>();
        file_names=new HashSet();
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


    public void initFiles(String path){

        file_names.add("cities");
        file_names.add("docs");
        file_names.add("numbers");
        file_names.add("symbols");
        for(char alphabet = 'a'; alphabet <='z'; alphabet++ )
        {
            file_names.add(""+alphabet);
        }

        for(String file_name: file_names)
        {
            String path_toWrite =path+"\\"+file_name+".txt";
            File alphabet_file = new File(path_toWrite);
            FileOutputStream is = null;
            try {
                is = new FileOutputStream(alphabet_file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            try {
                w.close();
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
}
