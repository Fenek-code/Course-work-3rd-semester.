import java.util.*;
import java.util.Map.Entry;

public class TouristJournal {
    private final static String TJOURNAL_FORMAT_STRING = "Страна: %s, стоимость: %d";
    private String name;
    private Map<TouristKey, Integer> journal;

    public TouristJournal() {
        this.name = "";
        this.journal = new TreeMap<TouristKey, Integer>();
    }

    public TouristJournal(String name) {
        this.name = name;
        this.journal = new TreeMap<TouristKey, Integer>();
    }

    public TouristJournal(String name, Comparator comp) {
        this.name = name;
        this.journal = new TreeMap<TouristKey, Integer>(comp);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJournal(Map<TouristKey, Integer> journal) {
        this.journal = journal;
    }

    public String getName() {
        return name;
    }

    public Map<TouristKey, Integer> getJournal() {
        return journal;
    }

    public String toString() {
        return String.format(TJOURNAL_FORMAT_STRING, name, size());
    }

    public boolean addTourist(TouristKey key, Integer n) {
        if (journal.containsKey(key))
            return false;
        journal.put(key, n);
        return true;
    }

    public boolean delTourist(TouristKey key) {
        if (journal.containsKey(key)) {
            journal.remove(key);
            return true;
        } else
            return false;
    }

    public boolean delTourist(int id) {
        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        Iterator<Map.Entry<TouristKey, Integer>> it = setS.iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry<TouristKey, Integer> keyVal = it.next();
            if (keyVal.getKey().getId() == id) {
                it.remove();
                i = i + 1;
            }
        }
        if (i == 0)
            return false;
        return true;
    }

    public int quantityItem() {
        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        Iterator<Map.Entry<TouristKey, Integer>> it = setS.iterator();
        Set<String> quantity = new TreeSet<String>();
        while (it.hasNext()) {
            Map.Entry<TouristKey, Integer> keyVal = it.next();
            quantity.add(keyVal.getKey().getName());
        }
        return (quantity.size());
    }

    public boolean deleteAllT() {
        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        Iterator<Map.Entry<TouristKey, Integer>> it = setS.iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry<TouristKey, Integer> keyVal = it.next();
            if (keyVal.getKey().getId() > 0) {
                it.remove();
                i = i + 1;
            }
        }
        if (i == 0)
            return false;
        return true;
    }

    public TouristJournal delete_condition(int cost) {
        TouristJournal subSpoJou = new TouristJournal();
        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        for (Map.Entry<TouristKey, Integer> keyVal : setS)
            if (keyVal.getKey().getCost() > cost)
                subSpoJou.addTourist(keyVal.getKey(), keyVal.getValue());
        return subSpoJou;
    }

    public boolean delSmallerTourist(int id) {
        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        Iterator<Map.Entry<TouristKey, Integer>> it = setS.iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry<TouristKey, Integer> keyVal = it.next();
            if (keyVal.getKey().getId() < id) {
                it.remove();
                i = i + 1;
            }
        }
        if (i == 0)
            return false;
        return true;
    }

    public boolean updateTourist(TouristKey key, Integer value) {
        if (!journal.containsKey(key))
            return false;
        journal.put(key, value);
        return true;
    }

    public int size() {
        return journal.size();
    }

    public TouristJournal selectTouristData(int id) {//выборка по id 
        TouristJournal subSpoJou = new TouristJournal();
        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        for (Map.Entry<TouristKey, Integer> keyVal : setS)
            if (keyVal.getKey().getId() == id)
                subSpoJou.addTourist(keyVal.getKey(), keyVal.getValue());
        return subSpoJou;
    }

    public TouristJournal selectTeamCodeData(String code) {//выборка по названию страны
        TouristJournal subSpoJou = new TouristJournal();
        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        for (Map.Entry<TouristKey, Integer> keyVal : setS)
            if (keyVal.getKey().getName().equals(code))
                subSpoJou.addTourist(keyVal.getKey(), keyVal.getValue());
        return subSpoJou;
    }

    public TouristJournal selectTouristcostData(int id, int cost) {//выборка по id и стоисости
        TouristJournal subSpoJou = new TouristJournal();
        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        for (Map.Entry<TouristKey, Integer> keyVal : setS)
            if ((keyVal.getKey().getId() == id))
                subSpoJou.addTourist(keyVal.getKey(), keyVal.getValue());
        return subSpoJou;
    }

    public TouristJournal sortScoreDesc() {//сортировка по убыванию цен
        TouristJournal subSpoJou = new TouristJournal("",
                new CompScoreDesc(this.journal));
        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        for (Map.Entry<TouristKey, Integer> keyVal : setS)
            subSpoJou.addTourist(keyVal.getKey(), keyVal.getValue());
        return subSpoJou;
    }

    public TouristJournal sortNameDesc() {//сортировка по названию, убывание.
        TouristJournal subSpoJou = new TouristJournal("",new CompNameDesc());

        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        for (Map.Entry<TouristKey, Integer> keyVal : setS)
            subSpoJou.addTourist(keyVal.getKey(), keyVal.getValue());
        return subSpoJou;
        
    }

    public TouristJournal sortCostDesc() {//сортировка по стоимости, убывание. 
        TouristJournal subSpoJou = new TouristJournal("", new CompCostDesc());
        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        for(Map.Entry<TouristKey, Integer> keyVal: setS)
            subSpoJou.addTourist(keyVal.getKey(), keyVal.getValue());
        return subSpoJou;
    }    
   
    public TouristJournal cheapest_trips() {
        String[] data = new String[0];
        TouristJournal subSpoJou = new TouristJournal("",new CompCostDesc());
        String[][] temp_data = subSpoJou.returnTouristJournalarray();
        
        subSpoJou.deleteAllT();
        int n = 0;
         for (String[] i : temp_data){
             if (data != i)
             subSpoJou.addTourist(new TouristKey(Integer.parseInt(temp_data[n][1]), temp_data[n][2].toString(), Integer.parseInt(temp_data[n][3])), Integer.parseInt(temp_data[n][3]));
            data = i;
         n++;

                }  
            n = 0;     
        return subSpoJou;
    }
    
    public String[][] putTouristJournal(){
        int i = 1;
        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        
        Object[][] array = new String[journal.size()][4];
        for(Map.Entry<TouristKey, Integer> keyK:setS){
            TouristKey key = keyK.getKey();
            
            array[i-1][0] = Integer.toString(i);
            array[i-1][1] = Integer.toString(key.getId());
            array[i-1][2] = key.getName();
            array[i-1][3] = Integer.toString(keyK.getValue());
            i = i+1;
        }
        return (String[][]) array;
    }

    public String[][] returnTouristJournalarray(){
        Set<Map.Entry<TouristKey, Integer>> setS = journal.entrySet();
        int i = 1;
        Object[][] array = new String[journal.size()][4];
        for(Map.Entry<TouristKey, Integer> keyK:setS){
            TouristKey key = keyK.getKey();
            array[i-1][0] = Integer.toString(i);
            array[i-1][1] = Integer.toString(key.getId());
            array[i-1][2] = key.getName();
            array[i-1][3] = Integer.toString(keyK.getValue());
            i = i+1;
        }
        return (String[][]) array;
    }

 
}
