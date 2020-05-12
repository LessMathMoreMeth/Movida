package movida.campomoritabanelli;

public class AlberoBinarioRicerca <K extends Comparable<K>,V> implements Dizionario{

    private NodoBR<K,V> root;

    public AlberoBinarioRicerca(){
        this.root=null;
    }

    public NodoBR getRoot(){
        return this.root;
    }

    public void setRoot(NodoBR v){
        this.root=v;
    }

    public V search(NodoBR root,K key) {
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
                if (key.compareTo((K) root.getKey()) <0) {//se è minore della chiave sinistra
                    return search(root.getLeftChild(), key);//vai a sinistra
                } else {
                    return search(root.getRightChild(), key);//altrimenti vai a destra
                }
            }
        }
    }
}
