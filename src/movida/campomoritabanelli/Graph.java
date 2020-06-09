package movida.campomoritabanelli;

import movida.commons.Collaboration;
import movida.commons.Movie;
import movida.commons.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class Graph {
    private HashMap<Person, ArrayList<Collaboration>> graph;

    Graph(){
        this.graph=new HashMap<Person,ArrayList<Collaboration>>();
    }

    public void stampa(){
        Set<Person> s=this.graph.keySet();
        for(Person p : s){
            System.out.println("ATTORE: "+p.getName());
            ArrayList<Collaboration> colls=this.graph.get(p);
            for( Collaboration c: colls){
                System.out.println("    "+c.getActorA().getName()+" : "+c.getActorB().getName());
            }
        }
    }

    public void extractMovieCollaborations(Movie m){
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
