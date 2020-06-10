package movida.campomoritabanelli;
import movida.commons.*;

import java.io.File;
import java.util.*;


public class MovidaCore implements IMovidaDB {
    private DBUtils utils;
    private Dizionario<String, Movie> movies;
    private Graph grafo;

    public MovidaCore() {
        this.grafo = new Graph();
        this.movies = new AlberoBinarioRicerca<>();
        this.utils = new DBUtils();
    }

    public void loadFromFile(File f) {
        Movie[] m = this.utils.load(f);
        for (int i = 0; i < m.length; i++) {
            //"normalizzo" la chiave string,mettendola minuscola,senza spazi bianchi ai "bordi" e "all'interno"
            String key = m[i].getTitle().toLowerCase().trim().replaceAll("\\s", "");
            this.movies.insert(key, m[i]);
            this.grafo.extractMovieCollaborations(m[i]);
        }
    }

    public void saveToFile(File f) {
        Movie[] m = this.movies.values().toArray(new Movie[0]);//lo converto in un array di movie
        if (m.length != 0) {
            this.utils.save(f, m);
        }
    }

    public void clear() {
        this.movies = new AlberoBinarioRicerca<>();
        this.grafo = new Graph();
    }

    public int countMovies() {
        Movie[] m = this.movies.values().toArray(new Movie[0]);
        return m.length;
    }

    public int countPeople() {
        return 0;
    }

    public boolean deleteMovieByTitle(String title) {
        if (this.movies.search(title) != null) {
            this.movies.delete(title);
            return true;
        }
        return false;
    }

    public Movie getMovieByTitle(String title) {
        String key = title.toLowerCase().trim().replaceAll("\\s", "");
        return this.movies.search(key);
    }

    public Person getPersonByName(String name) {
        return null;
    }

    public Movie[] getAllMovies() {
        return this.movies.values().toArray(new Movie[0]);
    }

    public Person[] getAllPeople() {
        return null;
    }

    public void codeSnippets() {
        /*HashMap<String,Integer> map=new HashMap<>();
        map.put("ciao",0);
        System.out.println(map.get("ciao"));
        map.replace("ciao",map.get("ciao")+1);
        System.out.println(map.get("ciao"));*/
        /*for(int i=0;i<v.length;i++){
            System.out.println(v[i].getTitle());
        }*/
        /*PriorityQueue<Integer> p= new PriorityQueue<Integer>();
        p.add(19);
        p.add(10);
        System.out.println(p.peek());
        Integer el=p.poll();
        el=el+10;
        p.add(el);
        System.out.println(p.peek());*/
        //m.grafo.stampa();
        /*AlberoBinarioRicerca<Integer, Integer> t = new AlberoBinarioRicerca<>();
        t.insert( 9, 9);
        t.insert( 7, 7);
        t.insert( 10, 10);
        t.insert( 4, 4);
        t.insert( 8, 8);
        t.insert( 3, 3);
        t.insert( 6, 6);
        t.insert( 5, 5);
        Integer[] f=new Integer[0];
        f= t.values().toArray(f);
        for(int i=0;i<f.length;i++){
            System.out.println(f[i]);
        }
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

    class SortPersons implements Comparator<Person> {
        // Used for sorting in ascending order of
        // name
        public int compare(Person a, Person b) {
            return b.compareTo(a);
        }
    }

    public SortPersons getComp(){
        return new SortPersons();
    }

    public static void main(String[] args) {
        MovidaCore m=new MovidaCore();
        m.loadFromFile(new File("esempio-formato-dati.txt"));
        m.saveToFile(new File("output.txt"));
        //Movie[] v=m.getAllMovies();
        /*Person[] c=m.grafo.getTeam(new Person("harrison ford"));
        for (Person col: c){
            System.out.println("ATTORE B: "+col.getName());
        }*/
        Collaboration[] c=m.grafo.maximizeCollaborationsInTheTeam(new Person("harrison ford"));
        for (Collaboration col: c){
            System.out.println("ATTORE A: "+col.getActorA().getName()+" ATTORE B: "+col.getActorB().getName()+"\n"+"SCORE :"+col.getScore().toString());
        }
        /*PriorityQueue<Person> p=new PriorityQueue<>(m.getComp());
        p.add(new Person("z"));
        p.add(new Person("b"));
        p.add(new Person("a"));
        p.add(new Person("v"));
        System.out.println(p.poll().getName());
        System.out.println(p.poll().getName());*/
    }
}
