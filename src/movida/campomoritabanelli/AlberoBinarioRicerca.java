package movida.campomoritabanelli;

public class AlberoBinarioRicerca <K extends Comparable<K>,V> implements Dizionario<K,V>{

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
    //////////////////////////////////////////////////////////////
    @Override
    public void insert(K key, V value) {
        NodoBR tmp=root;
        this.insertBR(tmp,(K)key,(V) value);
    }
    @Override
    public V search(K key) {
        return this.searchBR(this.root,(K) key);
    }

    @Override
    public void delete(K key) {
        this.deleteBR(this.root,key);
    }
    ///////////////////////////////////////////////////////////////

    public V searchBR(NodoBR root, K key) {
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
                    return searchBR(root.getLeftChild(), key);//vai a sinistra
                } else {
                    return searchBR(root.getRightChild(), key);//altrimenti vai a destra
                }
            }
        }
    }

    public void insertBR(NodoBR root, K key, V value){
        NodoBR p = null;
        while(root != null){
            p = root;
            if(root.getKey().compareTo(key) >0){
                root = root.getLeftChild();
            } else {
                root = root.getRightChild();
            }
        }
        NodoBR n = new NodoBR(key,value);
        n.setParent(p);
        if(p == null){
            this.setRoot(n);
        } else {
            if(key.compareTo((K) p.getKey()) <0){
                p.setLeftChild(n);
            } else {
                p.setRightChild(n);
            }
        }
    }

    public void deleteBR(NodoBR root,K key){

    }
}
