package PartA;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static PartA.newParser.q5;


public class testMain {
    public  static void main(String[] args) throws IOException, ParseException {


        newReadFile rf = new newReadFile("d:\\documents\\users\\eransar\\Downloads\\corpus\\corpus", "d:\\documents\\users\\eransar\\Downloads\\aa.txt","d:\\documents\\users\\eransar\\Downloads\\temp\\WithOutStem",false);
        rf.start();
        Map<String,Integer> sortedMap = abc();
        TreeMap<String,Integer> b=new TreeMap<>();
        b.putAll(abc());

        int i = 5;






       /* float start = System.nanoTime();
        ReadFile rf = new ReadFile("d:\\documents\\users\\eransar\\Downloads\\corpus\\corpus");
        rf.start();
        float end = System.nanoTime();
        System.out.println((end-start)*Math.pow(10,-9)/60);
        System.out.println(rf.parse.terms_size());
*/
//        Parse parse = new Parse();
//        parse.initFiles();

        /**
         * change line text
         */
//        float start = System.nanoTime();
//        File path = new File("C:\\Users\\eransar\\AppData\\Local\\Temp\\0.txt");
//        List<String> fileContent = new ArrayList<>(Files.readAllLines(path.toPath(), StandardCharsets.UTF_8));
//        String old = fileContent.get(12);
//        String merge = "4  ERAN-59584,5,FB496183 ERAN-3333,5,FB496179";
//        int old_index=findSpaceIndex(old);
//        int merge_index=findSpaceIndex(old);
//        int df_sum = Integer.parseInt(old.substring(0,old_index)) + Integer.parseInt(merge.substring(0,merge_index));
//        StringBuilder str = new StringBuilder(df_sum+old.substring(old_index,old.length())+" "+merge.substring(merge_index,merge.length()));
//        fileContent.set(12,str.toString());
//        Files.write(path.toPath(),fileContent,StandardCharsets.UTF_8);
//        float end = System.nanoTime();
//        System.out.println((end-start)*Math.pow(10,-9)/60);
        //find the first space



        //3  FBIS4-49984,1,FB496187 FBIS4-47629,2,FB496179




//        Files.write(path.toPath(), fileContent, StandardCharsets.UTF_8);



//



//        ReadFile rf = new ReadFile("d:\\documents\\users\\eransar\\Downloads\\corpus\\FB396012");
//        rf.start();

    }

    private static void changehashmap(HashMap<String, String> h1) {
        h1.put("b","b");

    }

    public static Map<String,Integer> abc(){
        TreeMap result = q5;
        Map<String,Integer> topTen =
                q5.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                        .limit(10)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return topTen;
    }
    private static void print(HashMap<String,String> h1){
        for(Map.Entry<String, String> t : h1.entrySet()){
            System.out.println(t.getKey()+" "+t.getValue());
        }
    }

//    public static int findSpaceIndex(String str){
//        for (int i = 0; i < str.length(); i++) {
//            if(str.charAt(i) ==' '){
//                return i;
//            }
//
//        }
//        return -1;
//    }

}






