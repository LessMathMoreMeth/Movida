package movida.campomoritabanelli;
import movida.commons.*;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

public class MovidaCore implements IMovidaSearch,IMovidaConfig,IMovidaDB,IMovidaCollaborations {
    private DBUtils utils;
    private Dizionario<String, Movie> movies;
    private Dizionario<String,CardStar> cardStars;
    private Sorting sorter;
    private Graph grafo;

    public MovidaCore() {
        this.grafo = new Graph();
        this.sorter=new HeapSort();
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
            Movie test=this.movies.search(key);
            if( test ==null){
                this.movies.insert(key, movie);
            }else {
                this.movies.delete(key);
                this.movies.insert(key, movie);
            }
            String directorName=movie.getDirector().getName();
            String director=directorName.toLowerCase().trim().replaceAll("\\s", "");;
            CardStar cardDirector=this.cardStars.search((director));
            if(cardDirector==null){
                this.cardStars.insert(director,new CardStar(directorName,0));
            }
            for(Person act: movie.getCast()){
                String actorName=act.getName();
                String actor=actorName.toLowerCase().trim().replaceAll("\\s", "");;
                CardStar cardActor=this.cardStars.search((actor));
                if(cardActor==null){
                    this.cardStars.insert(actor,new CardStar(actorName,1));
                }else{cardActor.addFilm();}
            }
        }
        Movie[] mv=this.movies.values().toArray(new Movie[0]);
        for(Movie mov: mv) {
            this.grafo.extractMovieCollaborations(mov);
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

    ///IMOVIDA SEARCH
    public Movie[] searchMoviesByTitle(String title){
        ArrayList<Movie> ret=new ArrayList<>();
        Movie[] arr=this.movies.values().toArray(new Movie[0]);
        String match=title.toLowerCase().trim().replaceAll("\\s","");
        for (Movie m:arr){
            if(m.titleNormalize().indexOf(match) !=-1){
                ret.add(m);
            }
        }
        return ret.toArray(new Movie[0]);
    }
    public Movie[] searchMostRecentMovies(Integer n){
        Movie[] ret=new Movie[n];
        Movie[] arr=this.movies.values().toArray(new Movie[0]);//casta da arraylist ad array di Movie
        this.sorter.sort("year", arr);
        if(arr.length <=n){
            return arr;
        }else{
            int i=0;
            int j=arr.length-1;
            while(i<n){
                ret[i]=arr[j];
                i++;
                j--;
            }
            return ret;
        }
    }
    public Movie[] searchMoviesInYear(Integer year){
        ArrayList<Movie> ret=new ArrayList<>();
        Movie[] arr=this.movies.values().toArray(new Movie[0]);
        for(Movie m:arr){
            //System.out.println(m.getYear());
            if(m.getYear().equals(year)){
                ret.add(m);
            }
        }
        return ret.toArray(new Movie[0]);
    }
    public Movie[] searchMoviesDirectedBy(String name){
        ArrayList<Movie> ret=new ArrayList<>();
        Movie[] arr=this.movies.values().toArray(new Movie[0]);
        for(Movie m:arr){
            if(m.getDirector().nameNormalize().equals(name.toLowerCase().trim().replaceAll("\\s",""))){
                ret.add(m);
            }
        }
        return ret.toArray(new Movie[0]);
    }
    public Movie[] searchMoviesStarredBy(String name){
        ArrayList<Movie> ret=new ArrayList<>();
        Movie[] arr=this.movies.values().toArray(new Movie[0]);
        String Actor = name.toLowerCase().trim().replaceAll("\\s","");
        for(Movie m:arr){
            Person[] cast = m.getCast();
            for(Person p:cast){
                if(p.nameNormalize().equals(Actor)){
                    ret.add(m);
                    break;
                }
            }
        }
        return ret.toArray(new Movie[0]);
    }
    public Movie[] searchMostVotedMovies(Integer N){
        Movie[] ret=new Movie[N];
        Movie[] arr=this.movies.values().toArray(new Movie[0]);//casta da arraylist ad array di Movie
        this.sorter.sort("votes", arr);
        if(arr.length <=N){
            return arr;
        }else{
            int i=0;
            int j=arr.length-1;
            while(i<N){
                ret[i]=arr[j];
                i++;
                j--;
            }
            return ret;
        }
    }
    public Person[] searchMostActiveActors(Integer N){
        Person[] ret=new Person[N];
        CardStar[] arr=this.cardStars.values().toArray(new CardStar[0]);//casta da arraylist ad array di Movie
        this.sorter.sort("numFilm", arr);
        if(arr.length <=N){
            return arr;
        }else{
            int i=0;
            int j=arr.length-1;
            while(i<N){
                ret[i]=arr[j];
                i++;
                j--;
            }
            return ret;
        }
    }
    ///---

    public static void main(String[] args) {
        MovidaCore m=new MovidaCore();
        m.loadFromFile(new File("esempio-formato-dati.txt")); //PATH LUCA
        //m.loadFromFile(new File("C:\\Users\\Dario\\Desktop\\movida\\Movida\\esempio-formato-dati.txt")); //PATH DARIO
        Person p=new Person("robert De NIro");
        Person[] pers=m.getTeamOf(p);
        for(Person e:pers){
            System.out.println(e.getName());
        }
    }
}


