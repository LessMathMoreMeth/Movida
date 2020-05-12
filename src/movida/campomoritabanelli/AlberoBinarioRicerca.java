package movida.campomoritabanelli;

public class AlberoBinarioRicerca <K extends Comparable<K>,V> implements Dizionario {

    private NodoBR<K, V> root;

    public AlberoBinarioRicerca() {
        this.root = null;
    }

    public NodoBR getRoot() {
        return this.root;
    }

    public void setRoot(NodoBR v) {
        this.root = v;
    }

    public V search(NodoBR root, K key) {
        if (root == null) {
            return null;
        } else {
            if (root.isLeaf()) {//se è una foglia
                if (root.getKey().compareTo(key) == 0) {//sono uguali
                    return (V) root.getValue();
                } else {
                    return null;
                }
            } else {
                if (key.compareTo((K) root.getKey()) < 0) {//se è minore della chiave sinistra
                    return search(root.getLeftChild(), key);//vai a sinistra
                } else {
                    return search(root.getRightChild(), key);//altrimenti vai a destra
                }
            }
        }
    }

    public void insert(NodoBR nodo, K key, V value) {
        if (root == null) {
            this.root = new NodoBR(key, value);
        } else if (root.isLeaf()) {
            if(root.getKey().compareTo(key) <=0){
                root.setLeftChild(nodo);
            } else {
                root.setRightChild(nodo);
            }
        } else {
            if (key.compareTo((K) root.getKey()) <=0){
                NodoBR m = root.getLeftChild();
                insert(m,key,value);
            } else {
                NodoBR m = root.getRightChild();
                insert(m, key, value);
            }
        }
    }
}
