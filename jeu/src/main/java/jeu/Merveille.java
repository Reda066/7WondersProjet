package jeu;


import java.util.ArrayList;
import java.util.Random;

public class Merveille {

    protected String nom;
    protected int id ;
    //protected Face face1, face2;
    protected Face face;

    protected ArrayList<Carte> cartesPosé = new ArrayList<Carte>(){};


    public Merveille(int i , String n){
        nom=n;
        id=i;

        face = getFace();

    }
    public void poserUneCarte(Carte c){
        cartesPosé.add(c);
    }

    public ArrayList<Carte> getCartesPosé(){
        return cartesPosé;
    }

    public String getNom(){
        return nom;
    }

    public int getId(){
        return id;
    }

    public Face getFace(){
        Face f;
        Random r = new Random();
        int i = r.nextInt(Face.values().length);
        f = Face.values()[i];

        return f;
    }



}