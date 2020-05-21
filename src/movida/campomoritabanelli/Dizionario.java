package movida.campomoritabanelli;

import java.util.ArrayList;

public interface Dizionario<K extends Comparable<K>,V> {
     public void insert(K key,V value);
     public V search(K key);
     public void delete(K key);
     public ArrayList<V> values();
     public ArrayList<K> keys();
}
