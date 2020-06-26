package movida.campomoritabanelli;
import movida.commons.*;
import java.io.File;


public class MovidaCore implements IMovidaDB,IMovidaCollaborations {
    private DBUtils utils;
    private Dizionario<String, Movie> movies;
    private Graph grafo;

    public MovidaCore() {
        this.grafo = new Graph();
        this.movies = new AlberoBinarioRicerca<>();
        this.utils = new DBUtils();
    }

    public Person[] getDirectCollaboratorsOf(Person actor){
        return this.grafo.getDirectCollaborators(actor);
    }
    public Person[] getTeamOf(Person actor){
        return this.grafo.getTeam(actor);
    }
    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor){
        return this.grafo.maximizeCollaborationsInTheTeam(actor);
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
    }//DA FARE

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
    }//DA FARE
    public Movie[] getAllMovies() {
        return this.movies.values().toArray(new Movie[0]);
    }

    public Person[] getAllPeople() {
        return null;
    }//DA FARE

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
    }
}
