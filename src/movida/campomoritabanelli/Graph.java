package movida.campomoritabanelli;

import movida.commons.Collaboration;
import movida.commons.Movie;
import movida.commons.Person;

import java.util.*;

public class Graph {
    private HashMap<Person, ArrayList<Collaboration>> graph;

    Graph(){
        this.graph=new HashMap<Person,ArrayList<Collaboration>>();
    }

    public Person[] getDirectCollaborators(Person actor){
        ArrayList<Collaboration> colls=this.graph.get(actor);//ottieni la lista di collaborazioni dell'attore
        Person[] p=new Person[colls.size()];//crea un array di ugual grandezza
        int i=0;
        for(Collaboration c : colls){//lo riempio con i nomi degli attore COLLABORANTI
            p[i]=c.getActorB();
            i++;
        }
        return p;
    }

    public Person[] getTeam(Person actor){//sostanzialmente è una visita in ampiezza
        HashSet<Person> mark = new HashSet<>();//tiene traccia delle persone già visitate
        ArrayList<Person> team = new ArrayList<>();//sarà il team dell'attore
        ArrayDeque<Person> q= new ArrayDeque<>();//coda in cui tengo le persone da visitare
        mark.add(actor);
        team.add(actor);
        q.add(actor);
        while (q.isEmpty()==false){
            Person u=q.poll();
            for(Collaboration c: this.graph.get(u)){
                Person v=c.getActorB();
                if(mark.contains(v)==false){
                    mark.add(v);
                    team.add(v);
                    q.add(v);
                }
            }
        }
        Person[] array= new Person[team.size()];
        return team.toArray(array);
    }

    //questa classe interna,mi dovrebbe tenere ordinati, nella priorityQueue, le collaborazioni
    //in ordine DECRESCENTE in base allo score della collaborazione
    class SortCollaborations implements Comparator<Collaboration> {
        public int compare(Collaboration a, Collaboration b) {
            return b.getScore().compareTo(a.getScore());
        }
    }
    ///////////////////////////////


    // è un problema di Maximum Spanning Tree
    public Collaboration[] maximizeCollaborationsInTheTeam(Person actor){
        HashSet<Person> mark=new HashSet<>();//insieme degli attori già marcati
        ArrayList<Collaboration> collabs=new ArrayList<>();//lista delle collaborazioni nel MST
        PriorityQueue<Collaboration> q=new PriorityQueue<Collaboration>(new SortCollaborations());//contiene le collaborazioni da valutare
        for(Collaboration c: this.graph.get(actor)){//in questo ciclo,inserisco
            q.add(c);                               //le collaborazioni dell'attore iniziale
        }
        while (!q.isEmpty()){
            Collaboration e=q.poll();//prendo il massimo nella coda,la collaborazione con più score
            // la collaborazione può essere vista come un arco (u,v) di costo w(score)
            if(!mark.contains(e.getActorB())){//se l'attore collaborante( nell'arco (u,v) è v!!) non è marcato
                mark.add(e.getActorA());//aggiungo i due attori del team ai visitati(li marco!)
                mark.add(e.getActorB());//nb: actorA sarà semprè già nel mark,non succede niente in questo caso!
                collabs.add(e);//aggiungi la collaborazione a quelle del MST
                for(Collaboration c:this.graph.get(e.getActorB())){//per ogni arco uscente dell'attore appena marcato(quello collaborante)
                    if(!mark.contains(c.getActorB())){//se l'arco incide su un nodo non ancora marcato
                        q.add(c);
                    }
                }
            }
        }
        return collabs.toArray(new Collaboration[0]);
    }

    public void extractMovieCollaborations(Movie m){//estrae le varie collaborazioni da un film
        for(Person actorInExam: m.getCast()){
            if (this.graph.containsKey(actorInExam)==false){//se l'attore non è ancora stato aggiunto
                this.graph.put(actorInExam,new ArrayList<>());//aggiungilo
            }
            for (Person actor: m.getCast()){//per ogni persona del cast del film
                if(!actorInExam.equals(actor)){//che sia diversa dall'attore in esame
                    ArrayList<Collaboration> colls=this.graph.get(actorInExam);//ottieni le collaborazioni dell'attore
                    Collaboration c= new Collaboration(actorInExam,actor);//crea una nuova collaborazione
                    if(colls.contains(c)){//se contiene già la collaborazione
                        int index=colls.indexOf(c);//valla a prendere e aggiungici il film
                        colls.get(index).addMovie(m);//aggiungo il movie alla collaborazione esistente
                    }else{
                        c.addMovie(m);//inserisci il film alla nuova collaborazione
                        colls.add(c);//aggiungila alle collaborazioni
                    }
                }
            }
        }
    }
}
