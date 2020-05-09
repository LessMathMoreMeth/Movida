package movida.campomoritabanelli;

import movida.commons.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DBUtils {
   private String[] keysPattern={"Title","Year","Director","Cast","Votes"};

   //funzione di salvataggio di un array di film su file
   public void save(File f,Movie[] movies){
      try {
         FileOutputStream fos = new FileOutputStream(f);
         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
         for(int i=0;i<movies.length;i++){//Per ogni movie appartenente a movies
            bw.write("Title:"+movies[i].getTitle());
            bw.newLine();
            bw.write("Year:"+movies[i].getYear().toString());
            bw.newLine();
            bw.write("Director:"+movies[i].getDirector().getName());
            bw.newLine();
            bw.write("Cast:"+movies[i].stringifyCast());
            bw.newLine();
            bw.write("Votes:"+movies[i].getVotes().toString());
            if(i!=movies.length-1){// se non è l'ultimo Movie,allora metti il separatore
               bw.newLine();
               bw.newLine();
            }
         }
         bw.close();
      }catch (Exception e){System.out.println(e.getMessage());};
   }

   //funzione di caricamento film da un file
   public Movie[] load(File f) {
      String[] rd = new String[5];//contiente le 5 righe di un movie
      ArrayList<Movie> movies = new ArrayList<>();
      try {
         Scanner sc = new Scanner(f);
         while (sc.hasNextLine()) {
            for (int i = 0; i < 5; i++) {//legge 5 righe alla volta,quelle del record Movie
               String line = sc.nextLine();//legge la riga
               this.checkIsValidLineOfRecord(line);//guarda se è valida
               rd[i] = line;//lo inserisce in una struttura grezza del Movie
            }
            Movie v = this.extractMovieFromRecord(rd);//prova a parsarlo
            movies.add(v);
            if (sc.hasNextLine()) {//se non sono alla fine del file
               this.checkIsValidLineSeparator(sc.nextLine());//guardo se il separatore è valido!
            }
         }
      } catch (Exception m) { System.out.println(m.getMessage()); }
      Movie[] mv=new Movie[movies.size()];
      return movies.toArray(mv);
   }

   public void checkIsValidLineOfRecord(String line){//guarda se una linea di un record è del tipo "---:---",altrimenti lancia un errore
      if (line.matches("(.*):(.*)")==false){
         throw new MovidaFileException();
      }
   }

   public void checkIsValidLineSeparator(String line){//guarda se la linea separatrice è tale
      if (line.trim().isEmpty()==false ){
         System.out.println("ATTENZIONE;LA LINEA DI SEPARAZIONE PUÒ ESSERE FATTA DI SPAZI!!!!");
         throw new MovidaFileException();
      }
   }

   public String[] isValidRecord(String[] record){//guarda se i record sono corretti,e nel caso,ritorna i valori "utili" del record
      String[] movieValueFields=new String[5];
      for(int i=0;i<5;i++){
         String[] keyAndValueRd=record[i].split(":");//"creo" il  record "chiave:valore"
         String keyToEvaluate=keyAndValueRd[0];//ottengo il primo pezzo del record "chiave:valore"
         keyToEvaluate=keyToEvaluate.trim();//elimino eventuali spazi interni per il matching
         if(keyToEvaluate.equals(this.keysPattern[i])==false){//guardo se matcha con il suo pattern "assegnato"
            throw new MovidaFileException();
         }
         movieValueFields[i]=keyAndValueRd[1];
      }
      return movieValueFields;
   }

   public Person[] getCastFromNames(String[] names){//dati i nomi del cast,ne ritorna le "persone"
      Person[] cast=new Person[names.length];
      for(int i=0;i<names.length;i++){
         cast[i]=new Person(names[i]);
      }
      return cast;
   }

   public  Movie extractMovieFromRecord(String[] record){
      String[] movieValueFields=this.isValidRecord(record);
      String title=movieValueFields[0];
      Integer year=Integer.parseInt(movieValueFields[1].trim());
      Integer votes=Integer.parseInt(movieValueFields[4].trim());
      String[] castName=movieValueFields[3].split(",");
      Person[] cast=this.getCastFromNames(castName);
      Person director=new Person(movieValueFields[2]);
      return new Movie(title,year,votes,cast,director);
   }
}
