package movida.campomoritabanelli;

import movida.commons.Person;

//Questa classe sarebbe la scheda identificativa della star,con i vari dati relativi alla sua carriera cinematografica

public class CardStar extends Person {
    private Integer numFilm;

    public CardStar(String name,Integer numFilm) {
        super(name);
        this.numFilm=numFilm;
    }

    public Integer getNumFilm(){
        return this.numFilm;
    }

    public void addFilm(){
        this.numFilm=this.numFilm+1;
    }

    @Override
    public int compareChoice(String field, MyComp o){
        CardStar v=(CardStar)o;
        if(field.equals("name")){
            return this.getName().compareTo(v.getName());
        }else if(field.equals("numFilm")){
            return this.numFilm.compareTo(v.getNumFilm());
        }else{return 0;}
    }

    @Override
    public boolean equals(Object obj) {
        CardStar p=(CardStar) obj;
        return this.nameNormalize().equals(p.nameNormalize());
    }

    @Override
    public int hashCode() {
        return this.nameNormalize().hashCode();
    }

    @Override
    public int compareTo(Object o) {
        CardStar p=(CardStar) o;
        return this.nameNormalize().compareTo(p.nameNormalize());
    }

}
