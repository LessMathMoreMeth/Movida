package movida.campomoritabanelli;

public class Tree23 <K extends Comparable<K>,V> implements Dizionario {
    private Nodo<K,V> root;

    public Tree23(){
        this.root=null;
    }

    public Nodo getRoot(){
        return this.root;
    }

    public void setRoot(Nodo v){
        this.root=v;
    }

    public V search(Nodo root,K key) {
        if (root == null) {
            return null;
        } else {
            if (root.isLeaf()) {//se è una foglia
                if (root.getLeftKey().compareTo(key) == 0) {//sono uguali
                    return (V) root.getValue();
                } else {
                    return null;//non c'è la chiave cercata
                }
            } else {
                if (key.compareTo((K) root.getLeftKey()) <0) {//se è minore della chiave sinistra
                    return search(root.getLeftChild(), key);//vai a sinistra
                } else if (key.compareTo((K) root.getLeftKey())>0 && key.compareTo((K) root.getCentralKey()) <=0){//se  la chiave è maggiore alla chiave sinistra e minore alla chiave centrale allora vai al centro
                    return search(root.getCentralChild(), key);
                } else {
                    return search(root.getRightChild(), key);//altrimenti vai a destra
                }
            }
        }
    }

    public void insert(Nodo root,K key,V value){
        if(root==null){//se l'albero è vuoto
            this.root=new Nodo(key,value);;
        }else if(root.isLeaf()){//se l'albero ha solo un nodo foglia
            Nodo m=new Nodo();
            Nodo v=root;
            this.setRoot(m);
            Nodo l=new Nodo(key,value);
            l.setFather(m);//link col padre
            v.setFather(m);//link col padre
            m.add(v,l);
        }else{//altrimenti è composto
           this.insertHelp(root,key,value);
        }
    }

    public void delete(Nodo root,K key){
        if(root!=null) {//se l'albero non è vuoto
            Nodo v=this.searchDelete(root, key);
            if(v!=null){
                Nodo padre=v.getFather();//prendo il padre
                if(padre.is3Node()){
                    padre.deleteChild(v);
                    this.fixAncientsKeys(padre.getFather(),padre,key);//rifissa a ritroso le chiavi
                }else{
                    padre.deleteChild(v);
                    Nodo w=v.getFather();
                    if(w==this.getRoot()){
                        this.root.deleteChild(v);
                        Nodo tmp=root;
                        this.setRoot(root.getLeftChild());
                        tmp=null;
                    }else {
                        this.merge(v);
                    }
                }
            }
        }
    }

    public void merge(Nodo v){
        Nodo w=v.getFather();
        Nodo l=w.getValidBrother(w);
        if(l.is3Node()){
            w.setLeftChild(l.getRightChild());
            l.setRightChild(null);
            w.deleteChild(v);
        }else{
            w.deleteChild(v);
            l.setRightChild(w.getLeftChild());
            w.setLeftChild(null);
            merge(w);
        }
    }

    public Nodo searchDelete(Nodo root,K key) {
        if (root == null) {
            return null;
        } else {
            if (root.isLeaf()) {//se è una foglia
                if (root.getLeftKey().compareTo(key) == 0) {//sono uguali
                    return root;
                } else {
                    return null;//non c'è la chiave cercata
                }
            } else {
                if (key.compareTo((K) root.getLeftKey()) <0) {//se è minore della chiave sinistra
                    return searchDelete(root.getLeftChild(), key);//vai a sinistra
                } else if (key.compareTo((K) root.getLeftKey())>0 && key.compareTo((K) root.getCentralKey()) <=0){//se  la chiave è maggiore alla chiave sinistra e minore alla chiave centrale allora vai al centro
                    return searchDelete(root.getCentralChild(), key);
                } else {
                    return searchDelete(root.getRightChild(), key);//altrimenti vai a destra
                }
            }
        }
    }


    public void insertHelp(Nodo root,K key,V value){
        Nodo padre=this.find(root,key);
        Nodo v=new Nodo(key,value);
        if(padre.is2Node()){//se ha solo 2 figli
            if (key.compareTo((K) root.getLeftKey()) <0){//nel caso vada come figlio sinistro
                padre.setRightChild(padre.getCentralChild());//swappo il centrale nel destro
                padre.setCentralChild(padre.getLeftChild());//swappo il sinistro nel centrale
                padre.setLeftChild((v));//setto il figlio sinistro
                v.setFather(padre);//linko col padre
                padre.setCentralKey(padre.getLeftKey());//swappo la chiave sinistra nella centrale
                padre.setLeftKey(key);//inserisco come chiave sinistra la chiave appena aggiunta
            }else if (key.compareTo((K) root.getLeftKey())>0 && key.compareTo((K) root.getCentralKey()) <=-1){//nel caso vada come figlio centrale
                padre.setRightChild(padre.getCentralChild());//sposto il figlio centrale in quello destro
                padre.setCentralChild(v);//inserisco il figlio centrale
                v.setFather(padre);//linko col padre
                padre.setCentralKey(key);//cambio la chiave centrale con quella appena inserita
            }else{
                padre.setRightChild(v);
                v.setFather(padre);//linko col padre
                this.fixAncientsKeys(padre.getFather(),padre,key);//rifissa a ritroso le chiavi
            }
        }else{//altrimenti ha già 3 figli
            padre.insert4Child(v,key);//inserisce il 4° figlio,rispettando l'ordine
            this.split(padre);
        }
    }

    public void split(Nodo v){
        Nodo w=new Nodo();
        w.setLeftChild(v.getLeftChild());
        w.setCentralChild(v.getCentralChild());
        w.setLeftKey(v.getLeftKey());
        if(v.getFather()==null){//se sono alla radice
            Nodo med=new Nodo();
            med.setLeftChild(w);
            med.setCentralChild(v);
            med.setLeftKey(w.getLeftKey());
            w.setFather(med);
            v.setFather(med);
        }else{
            v.getFather().addChild(w);
            if(v.getFather().has4Child()){
                this.split(v.getFather());
            }
        }
    }

    public void fixAncientsKeys(Nodo root,Nodo rootChild,K key){
        if (root!=null){//finchè non arrivo alla radice
            if (root.getLeftChild()==rootChild){//se ho aggiornato nel sotto albero sinistro
                if(root.getLeftKey().compareTo(key)<0){//se la chiave sinistra appena inserita è maggiore di quella di un antenato
                    root.setLeftKey(key);//la cambio
                    this.fixAncientsKeys(root.getFather(),root,key);//e vado su
                }
            }else if(root.getCentralChild()==rootChild){//se ho aggiornato al centro
                if(root.getCentralKey().compareTo(key)<0){//se la chiave centrale appena inserita è maggiore di quella di un antenato
                    root.setCentralKey(key);//la cambio
                    this.fixAncientsKeys(root.getFather(),root,key);//e vado su
                }
            }
        }
    }

    public Nodo find(Nodo root, K key){
        if(root.hasOnlyChildsLeafs()){
            return root;
        }else {
            if (key.compareTo((K) root.getLeftKey()) <0) {//se è minore della chiave sinistra
                return this.find(root.getLeftChild(),key);
            }else if((key.compareTo((K) root.getLeftKey())>0 && key.compareTo((K) root.getCentralKey()) <=-1) || root.is2Node()){//se  la chiave è maggiore alla chiave sinistra e minore alla chiave centrale allora vai al centro
                return this.find(root.getCentralChild(),key);
            } else  {
                return this.find(root.getRightChild(),key);
            }
        }
    }
}
