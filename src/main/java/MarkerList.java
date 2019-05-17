import java.lang.reflect.Array;
import java.util.ArrayList;

public class MarkerList extends ArrayList<Marker> {
    public ArrayList<Integer> indexOfAll(String phonemeGroup) {
        ArrayList<Integer> result = new ArrayList<>();
        int i = this.toString().indexOf(phonemeGroup);
        while (i >= 0) {
            result.add(i);
            i = this.toString().indexOf(phonemeGroup, i+1);
        }
        return result;
    }

    int indexOf(String phonemeGroup) {
        //TODO : return random index using indexOfAll
        return this.toString().indexOf(phonemeGroup);
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Marker m : this)
            s.append(m.phoneme);
        return s.toString();
    }
}
