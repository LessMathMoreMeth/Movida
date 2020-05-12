package movida.campomoritabanelli;

public interface Dizionario<K extends Comparable<K>,V> {
     public void insert(K key,V value);
     public V search(K key);
     public void delete(K key);
}
