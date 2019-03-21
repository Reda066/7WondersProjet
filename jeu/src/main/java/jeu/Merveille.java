package jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Merveille {

    protected String nom;
    protected Face cote;
    protected int etages;
    protected ArrayList<Carte> cartesPose = new ArrayList<Carte>(){};
    public Merveille(String n,Face face,int nb_etages_si_face_A,int nb_etages_si_face_B){
        nom=n;
        cote=face;
        etages = face == Face.A?nb_etages_si_face_A:nb_etages_si_face_B;
    }
    public Merveille(String n,Face face,int nb_etages){
        nom=n;
        cote=face;
        etages = nb_etages;
    }
    public void poserUneCarte(Carte c){
        cartesPose.add(c);
    }

    public ArrayList<Carte> getCartesPose(){
        return cartesPose;
    }

    public String getNom(){
        return nom;
    }
    public Face getFace(){return this.cote;}


}