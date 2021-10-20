import java.util.LinkedList;
import java.util.List;

public class FIP {
    private List<Integer> codes = new LinkedList<>();
    private List<Integer> positions = new LinkedList<>();

    public void addFIP(Integer code, Integer position){
        codes.add(code);
        positions.add(position);
    }

    @Override
    public String toString() {
        String text = "";
        for (int i = 0; i <codes.size() ; i++) {
            text+= codes.get(i) + " " + positions.get(i) + "\n";
        }
        return text;
    }
}
