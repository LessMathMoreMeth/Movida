package movida.commons;

import movida.campomoritabanelli.MyComp;


public class Movie implements MyComp {

    private String title;
    private Integer year;
    private Integer votes;
    private Person[] cast;
    private Person director;

    public Movie(String title, Integer year, Integer votes,
                 Person[] cast, Person director) {
        this.title = title;
        this.year = year;
        this.votes = votes;
        this.cast = cast;
        this.director = director;
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getYear() {
        return this.year;
    }

    public Integer getVotes() {
        return this.votes;
    }

    public Person[] getCast() {
        return this.cast;
    }

    public Person getDirector() {
        return this.director;
    }

    public int compareTo(Object movie){
        Movie v=(Movie)movie;
        return this.titleNormalize().compareTo(v.titleNormalize());
    }

    public String titleNormalize(){
        return this.title.toLowerCase().trim().replaceAll("\\s","");
    }

    public int compareChoice(String field, MyComp o){
        Movie v=(Movie)o;
        if(field.equals("year")){
            return this.getYear().compareTo(v.getYear());
        }else{
            return this.getVotes().compareTo(v.getVotes());
        }
    }

    public String stringifyCast(){
        String c="";
        for(int i=0;i<this.cast.length;i++){
            c=c+cast[i]+",";
        }
        return c.substring(0,c.length()-2);//gli levo la virgola finale
    }
}

