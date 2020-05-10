package movida.campomoritabanelli;

import java.util.Dictionary;

public class NodoBR <K extends Comparable<K>,V>{
    private K[] keys;
    private NodoBR father;
    private V value;
    private NodoBR left;
    private NodoBR right;

    public NodoBR(K key,V value){//creo un nodo foglia
        this.keys[0]=key;//il nodo foglia avrà solo una chiave,sulla sinistra
        this.father = null;
        this.left = null;
        this.right = null;
        this.value = value;
    }
    
    public V getValue(){return value;}
    
    public void setLeftChild(NodoBR<K,V> v){this.left = v;}
    public void setRightChild(NodoBR<K,V> v){this.right = v;}
    public NodoBR getLeftChild(){return this.left;}
    public NodoBR getRightChild(){return this.right;}
}

/*
    public Nodo getLeftChild(){return this.childs[0];}
    public void setLeftChild(Nodo<K,V> v){this.childs[0]=v;}
    public Nodo getCentralChild(){return this.childs[1];}
    public void setCentralChild(Nodo<K,V> v){this.childs[1]=v;}
    public Nodo getRightChild(){return this.childs[2];}
    public void setRightChild(Nodo<K,V> v){this.childs[2]=v;}
    public Nodo getFather(){return this.father;}
    public void setFather(Nodo<K,V> v){this.father=v;}
    public K getLeftKey(){return this.keys[0];}
    public void setLeftKey(K key){ this.keys[0]=key;}
    public K getCentralKey(){return this.keys[1];}
    public void setCentralKey(K key){ this.keys[1]=key;}
    public V getValue(){return this.value;}
    public void setValue(V value){ this.value=value;}

    public boolean is2Node(){return this.childs.length==2;}
    public boolean is3Node(){return this.childs.length==3;}
    public boolean isLeaf(){//è una foglia se la chiave interna e il valore non sono nulli
        return (this.childs.length==0);
    }

}
*/