package movida.campomoritabanelli;
import movida.commons.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class MovidaCore implements IMovidaConfig,IMovidaDB,IMovidaCollaborations {
    private DBUtils utils;
    private Dizionario<String, Movie> movies;
    private Dizionario<String,CardStar> cardStars;
    private Sorting sorter;
    private Graph grafo;

    public MovidaCore() {
        this.grafo = new Graph();
        this.sorter=new InsertionSort();
        this.movies = new AlberoBinarioRicerca<>();
        this.cardStars=new AlberoBinarioRicerca<>();
        this.utils = new DBUtils();
    }

    ///IMOVIDA COLLABORATIONS
    public Person[] getDirectCollaboratorsOf(Person actor){
        return this.grafo.getDirectCollaborators(actor);
    }
    public Person[] getTeamOf(Person actor){
        return this.grafo.getTeam(actor);
    }
    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor){
        return this.grafo.maximizeCollaborationsInTheTeam(actor);
    }
    ////-----

    ////IMOVIDA DB
    public void loadFromFile(File f) {
        Movie[] m = this.utils.load(f);
        for (Movie movie: m) {
            //"normalizzo" la chiave string,mettendola minuscola,senza spazi bianchi ai "bordi" e "all'interno"
            String key = movie.getTitle().toLowerCase().trim().replaceAll("\\s", "");
            this.movies.insert(key, movie);
            this.grafo.extractMovieCollaborations(movie);
            String director=movie.getDirector().getName().toLowerCase().trim().replaceAll("\\s", "");;
            CardStar cardDirector=this.cardStars.search((director));
            if(cardDirector==null){
                this.cardStars.insert(director,new CardStar(director,0));
            }
            for(Person actor: movie.getCast()){
                String actorName=actor.getName().toLowerCase().trim().replaceAll("\\s", "");;
                CardStar cardActor=this.cardStars.search((actorName));
                if(cardActor==null){
                    this.cardStars.insert(actorName,new CardStar(actorName,1));
                }else{cardActor.addFilm();}
            }
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
        Person[] p=this.cardStars.values().toArray(new Person[0]);
        return p.length;
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
        String key = name.toLowerCase().trim().replaceAll("\\s", "");
        return this.cardStars.search(key);
    }
    public Movie[] getAllMovies() {
        return this.movies.values().toArray(new Movie[0]);
    }
    public Person[] getAllPeople() {
        return this.cardStars.values().toArray(new Person[0]);
    }
    ///-------

    ///IMOVIDA CONFIG
    public boolean setMap(MapImplementation m){
        if(m==MapImplementation.ABR && this.movies instanceof Tree23){//se voglio settare ABR e attualmente è alberi2-3
            this.movies=new AlberoBinarioRicerca<>();
            this.cardStars=new AlberoBinarioRicerca<>();
            return true;
        }else if(m==MapImplementation.Alberi23 && this.movies instanceof AlberoBinarioRicerca){//caso speculare
            this.movies=new Tree23<>();
            this.cardStars=new Tree23<>();
            return true;
        }else{//altrimenti se non è una implementazione fatta oppure c'è già quel setting,non faccio nulla
            return false;
        }
    }
    public boolean setSort(SortingAlgorithm a){
        if(a==SortingAlgorithm.InsertionSort && this.sorter instanceof HeapSort){//analogo con mapImplementation
            this.sorter=new InsertionSort();
            return true;
        }else if(a==SortingAlgorithm.HeapSort && this.movies instanceof InsertionSort){//caso speculare
            this.sorter=new HeapSort();
            return true;
        }else{//altrimenti se non è una implementazione fatta oppure c'è già quel setting,non faccio nulla
            return false;
        }
    }

    public Movie searchMostRecentMovies(int n){
        MyComp
        this.sorter.sort("year", this.movies.values());
    }




    ///---

    public static void main(String[] args) {
        MovidaCore m=new MovidaCore();
        m.loadFromFile(new File("esempio-formato-dati.txt"));
        //System.out.println(m.getPersonByName("Brian de palMa"));





    }
}


