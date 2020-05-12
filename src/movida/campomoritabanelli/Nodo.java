package movida.campomoritabanelli;

import java.util.ArrayList;
import java.util.Collections;

public class Nodo <K extends Comparable<K>,V>{
    private K keyLeft;
    private K keyCentral;
    private Nodo father;
    private V value;
    private Nodo childLeft;
    private Nodo childCentral;
    private Nodo childRight;
    private Nodo tmp;//nodo temporaneo di inserimento quando si hanno 3 figli

    public Nodo(){//creo un nodo interno
        this.keyLeft=null;
        this.keyCentral=null;
        this.father=null;
        this.childLeft=null;
        this.childCentral=null;
        this.childRight=null;
        this.tmp=null;
        this.value=null;
    }

    public Nodo(K key,V value){//creo un nodo foglia
        this.keyLeft=key;//il nodo foglia avrà solo una chiave,sulla sinistra
        this.keyCentral=null;
        this.father=null;
        this.childLeft=null;
        this.childCentral=null;
        this.childRight=null;
        this.tmp=null;
        this.value=value;
    }

    public Nodo getLeftChild(){return this.childLeft;}
    public void setLeftChild(Nodo<K,V> v){this.childLeft=v;}
    public Nodo getCentralChild(){return this.childCentral;}
    public void setCentralChild(Nodo<K,V> v){this.childCentral=v;}
    public Nodo getRightChild(){return this.childRight;}
    public void setRightChild(Nodo<K,V> v){this.childRight=v;}
    public Nodo getFather(){return this.father;}
    public void setFather(Nodo<K,V> v){this.father=v;}
    public K getLeftKey(){return this.keyLeft;}
    public void setLeftKey(K key){ this.keyLeft=key;}
    public K getCentralKey(){return this.keyCentral;}
    public void setCentralKey(K key){ this.keyCentral=key;}
    public V getValue(){return this.value;}
    public void setValue(V value){ this.value=value;}

    public boolean is2Node(){
        return (this.childLeft!=null && this.childCentral !=null && this.childRight==null);
    }
    public boolean is3Node(){
        return (this.childLeft!=null && this.childCentral !=null && this.childRight!=null);
    }
    public boolean hasOnlyChildsLeafs(){
        boolean flag=false;
        if(childLeft!=null){
            flag=childLeft.isLeaf();
        }
        if(childCentral!=null){
            flag=childCentral.isLeaf();
        }
        if (childRight!=null){
            flag=childRight.isLeaf();
        }
        return flag;
    }

    public boolean isLeaf(){//è una foglia se non ha figli
        return (this.childLeft==null);
    }

    public void add(Nodo v1,Nodo v2){
        K key1= (K) v1.getLeftKey();
        K key2= (K) v2.getLeftKey();
        if(key1.compareTo(key2)<0){
            this.keyLeft=key1;
            this.childLeft=v1;
            this.keyCentral=key2;
            this.childCentral=v2;
        }else{
            this.keyLeft=key2;
            this.childLeft=v2;
            this.keyCentral=key1;
            this.childCentral=v1;
        }
    }

    public boolean has4Child(){
        return (this.childLeft!=null &&this.childCentral!=null &&this.childRight!=null &&this.tmp!=null );
    }

    public void addChild(Nodo v){
        K key=(K)v.getLeftKey();
        if (this.is2Node()){
            if(key.compareTo(this.keyLeft)<0){//se la chiave è la +piccola,swappo tutto sulla destra 
                this.keyCentral=this.keyLeft;
                this.keyLeft=key;
                this.tmp=this.childRight;
                this.childRight=this.childCentral;
                this.childCentral=this.childLeft;
                this.childLeft=v;
            }else if(key.compareTo(this.keyCentral)<0){//se la chiave è più piccola rispetto a quella centrale, swappo a destra dal centro
                this.keyCentral=key;
                this.tmp=this.childRight;
                this.childRight=this.childCentral;
                this.childCentral=v;
            }else{
                this.childRight=v;
            }
        }else{
            this.insert4Child(v,key);
        }
    }

    public Nodo getValidBrother(Nodo v){
        Nodo padre=v.getFather();
        if(padre.getLeftChild()==v){//se è il figlio sinistro
            return padre.getCentralChild();
        }else if(padre.getCentralChild()==v){
            return padre.getLeftChild();
        }else{
            return padre.getCentralChild();
        }
    }

    public void deleteChild(Nodo child){
        if(this.getLeftChild()==child){//se cancello il figlio sinistro
            child.setFather(null);
            this.setLeftChild(this.getCentralChild());//swappo verso sinistra i figli
            this.setCentralChild(this.getRightChild());
            this.setLeftKey(this.getCentralKey());//swappo la chiave centrale a sinistra
        }else if(this.getCentralChild()==child){//se cancello il figlio centrale
            child.setFather(null);
            this.setCentralChild(this.getRightChild());
            this.setCentralKey(null);
        }if(this.getRightChild()==child){//se cancello il figlio destro
            child.setFather(null);
            this.setRightChild(null);
        }
    }

    public void insert4Child(Nodo v,K key){
        if(key.compareTo(this.keyLeft)<0){//se la chiave è la +piccola,swappo tutto sulla destra destra
            this.keyCentral=this.keyLeft;
            this.keyLeft=key;
            this.tmp=this.childRight;
            this.childRight=this.childCentral;
            this.childCentral=this.childLeft;
            this.childLeft=v;
            v.setFather(this);
        }else if(key.compareTo(this.keyCentral)<0){//se la chiave è più piccola rispetto a quella centrale, swappo a destra dal centro
            this.keyCentral=key;
            this.tmp=this.childRight;
            this.childRight=this.childCentral;
            this.childCentral=v;
            v.setFather(this);
        }else{
            if(key.compareTo((K) this.getRightChild().getLeftKey())<0){
                this.tmp=this.childRight;
                this.childRight=v;
                v.setFather(this);
            }else{
                this.tmp=v;
                v.setFather(this);
            }
        }
    }
}
