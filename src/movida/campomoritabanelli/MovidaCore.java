package movida.campomoritabanelli;
import movida.commons.*;

import java.io.File;


public class MovidaCore implements IMovidaDB{
    private DBUtils utils;
    Movie[] m;

    public MovidaCore(){
        this.m=null;
        this.utils=new DBUtils();
    }

    public void loadFromFile(File f){
        this.m=this.utils.load(f);
    }

    public void saveToFile(File f){
        if( m!=null && m.length!=0){
            this.utils.save(f,this.m);
        }
    }

    public void clear(){}
    public int countMovies(){return 0;}
    public int countPeople(){return 0;}
    public boolean deleteMovieByTitle(String title){return true;}
    public Movie getMovieByTitle(String title){return null;}
    public Person getPersonByName(String name){return null;}
    public Movie[] getAllMovies(){return null;}
    public Person[] getAllPeople(){return null;}

    public static void main(String[] args) {
        /*MovidaCore m=new MovidaCore();
        m.loadFromFile(new File("esempio-formato-dati.txt"));
        m.saveToFile(new File("output.txt"));*/
        AlberoBinarioRicerca<Integer, Integer> t = new AlberoBinarioRicerca<>();
        t.insert( 9, 9);
        t.insert( 7, 7);
        t.insert( 10, 10);
        t.insert( 4, 4);
        t.insert( 8, 8);
        t.insert( 3, 3);
        t.insert( 6, 6);
        t.insert( 5, 5);
        /*NodoBR test=t.getRoot().getLeftChild().getLeftChild().getRightChild();
        System.out.println(test.getKey()); //6
        System.out.println(t.getRoot().getLeftChild().getKey()); //7
        t.delete(7);
        NodoBR test1=t.getRoot().getLeftChild().getLeftChild().getRightChild();
        System.out.println(test1.getKey());//5
        System.out.println(t.getRoot().getLeftChild().getKey()); //6
        /*
        System.out.println(t.predecessore(test).getKey());
        System.out.println(t.findMax(test).getKey());
        System.out.println(t.findMin(test).getKey());
        */
    }
}
