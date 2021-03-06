package movida.campomoritabanelli;

import java.util.Dictionary;

public class NodoBR <K extends Comparable<K>,V>{
    private K key;
    private V value;
    private NodoBR parent;
    private NodoBR childleft;
    private NodoBR childright;

    public NodoBR(K key,V value){//creo un nodo foglia
        this.key = key;
        this.parent = null;
        this.childleft = null;
        this.childright = null;
        this.value = value;
    }
    
    public V getValue(){return this.value;}
    public K getKey(){return this.key;}
    public void setKey(K key){this.key = key;}
    public void setValue(V value){this.value = value;}
    public NodoBR getParent(){return parent;}
    public void setParent(NodoBR<K,V> v){this.parent = v;}
    public void setLeftChild(NodoBR<K,V> v){this.childleft = v;}
    public void setRightChild(NodoBR<K,V> v){this.childright = v;}
    public NodoBR getLeftChild(){return this.childleft;}
    public NodoBR getRightChild(){return this.childright;}

    public boolean isLeaf(){return (childright == null & childleft == null);}

    public void deleteLeaf(NodoBR Node){
            if(this.getLeftChild() == Node){
                this.setLeftChild(null);
        } else if(this.getRightChild() == Node){
                this.setRightChild(null);
            }
    }

    public boolean isRightChild(){
        NodoBR parent = this.parent;
        if(parent.getRightChild() == this){
            return true;
        } else return false;
    }
    public boolean isLeftChild(){
        NodoBR parent = this.parent;
        if(parent.getLeftChild() == this){
            return true;
        } else return false;
    }

    public boolean hasOneChild(){
        if((this.getRightChild() != null && this.getLeftChild() == null) || (this.getRightChild() == null && this.getLeftChild() != null)){
            return true;
        } else return false;
    }

    public NodoBR getChild(){
        if(this.getLeftChild() != null){
            return this.getLeftChild();
        } else return this.getRightChild();
    }

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