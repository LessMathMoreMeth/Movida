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

    public static void main(String[] args){
        /*MovidaCore m=new MovidaCore();
        m.loadFromFile(new File("esempio-formato-dati.txt"));
        m.saveToFile(new File("output.txt"));*/
        Tree23<String,Integer> t=new Tree23<>();
        t.insert(t.getRoot(),"ciao",5);
        t.insert(t.getRoot(),"ciaov",6);
        t.insert(t.getRoot(),"cicco",7);
        System.out.println(t.search(t.getRoot(),"cicco"));
    }
}
