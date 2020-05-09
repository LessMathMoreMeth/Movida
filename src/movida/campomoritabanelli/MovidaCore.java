package movida.campomoritabanelli;
import movida.commons.*;

public class MovidaCore {
    private DBUtils utils;

    public MovidaCore(){
        this.utils=new DBUtils();
    }

    public static void main(String[] args){
        Comparabile m1=new Movie("ciao",1992,33,null,null);
        Comparabile m2=new Movie("ciaos",1993,34,null,null);
        System.out.println(m1.compareChoice("year",m2));
    }
}
