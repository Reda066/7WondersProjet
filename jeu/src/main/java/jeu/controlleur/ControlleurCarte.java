package jeu.controlleur;
import jeu.Age;
import jeu.Carte;
import jeu.Couleur;
import jeu.Materiaux;

import java.util.ArrayList;
import java.util.Collections;

public class ControlleurCarte {
  public ControlleurCarte(){}


  public static Carte getCarteById(int id)
  {
    switch ( id )
    {
      case 1: return new Carte(Age.I, Couleur.BLEU, Materiaux.BOIS,1,"Carte N°1",1);
      case 2: return new Carte(Age.I, Couleur.BLEU,Materiaux.ARGILE,2,"Carte N°2",2);
      case 3: return new Carte(Age.I, Couleur.BLEU,Materiaux.MINERAI,3,"Carte N°3",3);
      case 4: return new Carte(Age.I, Couleur.BLEU,Materiaux.PIERRE,4,"Carte N°4",4);
    }
    return null;
  }
  public ArrayList<Carte> getCartes (int nb_joueurs, Age a){
    ArrayList<Carte> cartes = new ArrayList<Carte>();

    switch(a){
      case I :
        cartes.add(new Carte(Age.I, Couleur.BLEU,Materiaux.BOIS,1,"Carte N°1",1));
        cartes.add(new Carte(Age.I, Couleur.BLEU,Materiaux.ARGILE,2,"Carte N°2",2));
        cartes.add(new Carte(Age.I, Couleur.BLEU,Materiaux.MINERAI,3,"Carte N°3",3));
        cartes.add(new Carte(Age.I, Couleur.BLEU,Materiaux.PIERRE,4,"Carte N°4",4));
      case II :
      case III:

    }
    return cartes;
  }

  public ArrayList<Carte> getAleaCartes(int nb,Age a){
    ArrayList<Carte> cartes = getCartes(nb,a);
    Collections.shuffle(cartes);
    return cartes;
  }


}
