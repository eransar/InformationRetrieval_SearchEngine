import java.io.IOException;
import java.text.ParseException;


public class testMain {
    public static void main(String[] args) throws IOException, ParseException {
        float start = System.nanoTime();
        ReadFile rf = new ReadFile("C:\\corpus\\corpus");
        rf.start();
        float end = System.nanoTime();
        System.out.println((end-start)*Math.pow(10,-9)/60);
        System.out.println(rf.parse.terms_size());
    }

}

