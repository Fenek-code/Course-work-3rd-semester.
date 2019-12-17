import java.util.Comparator;

public class CompNameDesc implements Comparator<TouristKey> {
    public int compare(TouristKey key1, TouristKey key2){
        String code1 = key1.getName();
        String code2 = key2.getName();
        
        if(code1.compareTo(code2) < 0) return 1;
        if(code1.compareTo(code2) > 0) return -1;
        return 0;
    }
}

