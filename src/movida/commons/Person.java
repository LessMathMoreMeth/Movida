/*
 * Copyright (C) 2020 - Angelo Di Iorio
 *
 * Progetto Movida.
 * Corso di Algoritmi e Strutture Dati
 * Laurea in Informatica, UniBO, a.a. 2019/2020
 *
 */
package movida.commons;

import movida.campomoritabanelli.MyComp;

/**
 * Classe usata per rappresentare una persona, attore o regista,
 * nell'applicazione Movida.
 *
 * Una persona  identificata in modo univoco dal nome
 * case-insensitive, senza spazi iniziali e finali, senza spazi doppi.
 *
 * Semplificazione: <code>name</code>  usato per memorizzare il nome completo (nome e cognome)
 *
 * La classe pu essere modicata o estesa ma deve implementare il metodo getName().
 * Una persona � identificata in modo univoco dal nome 
 * case-insensitive, senza spazi iniziali e finali, senza spazi doppi. 
 *
 * Semplificazione: <code>name</code> � usato per memorizzare il nome completo (nome e cognome)
 *
 * La classe pu� essere modicata o estesa ma deve implementare il metodo getName().
 *
 */
public class Person implements MyComp {

    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int compareChoice(String field, MyComp o){
        Person v=(Person)o;
        if(field.equals("name")){
            return this.name.compareTo(v.getName());
        }else{
           return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        Person p=(Person) obj;
        return this.getName().equals(p.getName());
    }

    @Override
    public int compareTo(Object o) {
        Person p=(Person) o;
        return this.getName().compareTo(p.getName());
    }
}