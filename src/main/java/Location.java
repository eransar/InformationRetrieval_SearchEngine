import java.util.HashMap;

public class Location {

    private int row;
    private int position;
    private HashMap<Integer,Integer> location;

    public void setRow(int row) {
        this.row = row;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Location(int row, int position){
        this.row=row;
        this.position=position;

    }
}
