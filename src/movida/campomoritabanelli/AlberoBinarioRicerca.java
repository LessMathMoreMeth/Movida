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
        while(root!=null){
            if(key.compareTo((K) root.getKey())==0){
                return (V)root.getValue();
            }else if(key.compareTo((K)root.getKey())<0){
                root=root.getLeftChild();
            }else{
                root=root.getRightChild();
            }
        }
        return null;
    }

    public void insertBR(NodoBR root, K key, V value){
        NodoBR p = null;
        while(root != null){
            p = root;
            if(root.getKey().compareTo(key) <0){
                root = root.getRightChild();
            } else {
                root = root.getLeftChild();
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

    protected NodoBR findMax(NodoBR root){
            while(root != null && root.getRightChild()!= null){
                root = root.getRightChild();
            }
        return root;
    }

    protected NodoBR findMin(NodoBR root){
        while(root != null && root.getLeftChild()!= null){
            root = root.getLeftChild();
        }
        return root;
    }

    protected NodoBR successore(NodoBR root){
        if(root == null) {
            return null;
        }
        if(root.getRightChild() != null){
            return findMin(root.getRightChild());
        } else {
            NodoBR parent = getRoot();
            while(parent != null && root == parent.getRightChild()){
                root = parent;
                parent = parent.getParent();
            }
         return parent;
        }
    }

    protected NodoBR predecessore(NodoBR root){
        if(root == null){
            return null;
        }
        if(root.getLeftChild() != null){
            return findMax(root.getLeftChild());
        } else {
            NodoBR parent = root.getParent();
            while(parent != null && root == parent.getLeftChild()){
                root = parent;
                parent = parent.getParent();
            }
            return parent;
        }
    }

}
