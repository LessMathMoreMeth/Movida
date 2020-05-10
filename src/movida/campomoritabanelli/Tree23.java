package movida.campomoritabanelli;

public class Tree23 <K extends Comparable<K>,V> implements Dizionario {
    private Nodo<K,V> root;

    public Tree23(){
        this.root=null;
    }

    public Nodo getRoot(){
        return this.root;
    }

    public Nodo search(Nodo root,K key) {
        if (root == null) {
            return null;
        } else {
            if (root.isLeaf()) {//se è una foglia
                if (root.getLeftKey().compareTo(key) == 0) {//sono uguali
                    return  root;
                } else {
                    return null;//non c'è la chiave cercata
                }
            } else {
                if (key.compareTo((K) root.getLeftKey()) == -1) {//se è minore della chiave sinistra
                    return search(root.getLeftChild(), key);//vai a sinistra
                } else if (root.getRightChild() != null && key.compareTo((K) root.getCentralKey()) > 0){//se è un 3-nodo  e la chiave è maggiore alla chiave centrale allora vai a destra
                    return search(root.getRightChild(), key);
                } else {
                    return search(root.getCentralChild(), key);//altrimenti vai al centro
                }
            }
        }
    }

    public boolean insert(K key,V value){
        if(this.root==null){
            Nodo leaf=new Nodo(key,value);
            this.root=leaf;
        }else{

        }
    }

}
