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
        NodoBR tmp=root;
        return this.searchBR(tmp,(K) key);
    }

    @Override
    public void delete(K key) {
        NodoBR tmp=root;
        this.deleteBR(tmp,key);
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
        NodoBR Node = searchNode(root,key);
        if(Node != null){
            if(Node.isLeaf()){
               NodoBR parent = Node.getParent();
                parent.deleteLeaf(Node);
            }
            if(Node.hasOneChild()){
                NodoBR f = Node.getChild();
                NodoBR padre = Node.getParent();
                if(Node.isRightChild()){
                    padre.setRightChild(f);
                }else {
                    padre.setLeftChild(f);
                }
                f.setParent(padre);
            } else {
                NodoBR predecessore = this.predecessore(Node); // 4
                NodoBR childPredecessore = predecessore.getChild(); // 3
                NodoBR padrePredecessore = predecessore.getParent(); // 7
                if(predecessore.isRightChild()){
                    padrePredecessore.setRightChild(childPredecessore);
                } else {
                    padrePredecessore.setLeftChild(childPredecessore);
                }
                childPredecessore.setParent(padrePredecessore);
                Node.setKey(predecessore.getKey());
                Node.setValue(predecessore.getValue());
            }
        }
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
    public NodoBR searchNode(NodoBR root, K key) {
        while(root!=null){
            if(key.compareTo((K) root.getKey())==0){
                return root;
            }else if(key.compareTo((K)root.getKey())<0){
                root=root.getLeftChild();
            }else{
                root=root.getRightChild();
            }
        }
        return null;
    }

    


}
