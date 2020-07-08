INFORMAZIONI SUL GRUPPO:
Dario Campomori
Luca Tabanelli

ISTRUZIONI:
Inserire i sorgenti tutti sotto la stessa cartella.
ATTENZIONE: I file ove verranno loadati o salvati i dati devono ESSERE NELLO STESSO CLASSPATH dei sorgenti

DESCRIZIONE:
Nb: Per classe Person si intende la SCHEDA DEI DATI ANAGRAFICI di tale persona.
MovidaCore usa come attributi 5 campi:
	-DbUtils è una classe che permette l'interfacciamento con i file,estrazione dei film o salvataggio dei film , SU FILE(secondo un pattern 			preimpostato)
	-Dizionario movies contiene l'associazione tra nome del film(NORMALIZZATO) e il relativo film
	-Dizionario cardStars contiene l'associazione tra nome della star e la relativa SCHEDA CINEMATOGRAFICA(classe cardStar) della star
	-Sorter è l'algoritmo attualmente selezionato di ordinamento
	-Graph è il grafo(liste di adiacenza) contenente coppie persona e lista di collaborazioni dirette della persona
Esistono poi 3 interfacce da noi implementate:
-Dizionario: espone i metodi base di un dizionario("unifica" i tipi ABR e Alberi2-3)
-Sorting:espone il metodo sort,("unifica" i tipi InsertionSort e HeapSort)
-MyComp:espone il metodo compareChoice,che permette di comparare due oggetti in base a un determinato campo(utili per sorting,per riordinare in base 		ad un campo),oltre ad esporre il metodo compareTo,dato che estende l'interfaccia Comparable.

Per prima cosa,dopo aver istanziato la classe MovidaCore,si effettua il loadfromFile(),in cui,usando il dbUtils,si ricaverà in primis un array di Movie.Successivamente,per ogni movie,si riempie il dizionario movies e cardStars,TENENDO CONTO DI EVENTUALI DOPPIONI.
Alla fine del ciclo,si riempie il Graph(grafo delle collaborazioni),ciclando sui values del dizionario movies,chiamando il metodo extractCollaborations di Graph,sul movie in esame.

