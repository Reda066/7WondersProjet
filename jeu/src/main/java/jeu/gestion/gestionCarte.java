package jeu.gestion;
import jeu.Age;
import jeu.Carte;
import jeu.Couleur;
import org.json.JSONArray;
import jeu.Materiaux;

import java.util.ArrayList;
import java.util.Collections;

public class gestionCarte {
  public gestionCarte() {
  }

  public ArrayList<Carte> getCartes(int nb_joueurs, Age a) {
    ArrayList<Carte> cartes = new ArrayList<Carte>() {
    };

    if (a.equals(Age.I)) {
      if (nb_joueurs >= 3) {
        /*-------------------------------------------------------------------------BLEU----------------------------------------------------------------------------*/
          cartes.add(new Carte(Age.I, Couleur.BLEU, "Autel", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
          cartes.add(new Carte(Age.I, Couleur.BLEU, "Bains", 3, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
          cartes.add(new Carte(Age.I, Couleur.BLEU, "Theatre", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
        /*-------------------------------------------------------------------------ROUGE----------------------------------------------------------------------------*/
        cartes.add(new Carte(Age.I, Couleur.ROUGE, "Caserne", 0, new Materiaux(0, 0, 0, 0, 1, 0, 0, 0)));
        cartes.add(new Carte(Age.I, Couleur.ROUGE, "Palissade", 0, new Materiaux(0, 0, 1, 0, 0, 0, 0, 0)));
        cartes.add(new Carte(Age.I, Couleur.ROUGE, "Tour de garde", 0, new Materiaux(0, 0, 0, 1, 0, 0, 0, 0)));
        /*-------------------------------------------------------------------------JAUNE----------------------------------------------------------------------------*/
        cartes.add(new Carte(Age.I, Couleur.JAUNE, "Comptoir Ouest", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
        cartes.add(new Carte(Age.I, Couleur.JAUNE, "Comptoir Est", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
        cartes.add(new Carte(Age.I, Couleur.JAUNE, "Marché", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));

        //VERT
        cartes.add(new Carte(Age.I, Couleur.VERT, "Officine", 0, new Materiaux(0, 0, 0, 0, 0, 1, 0, 0)));
        cartes.add(new Carte(Age.I, Couleur.VERT, "Scriptorium", 0, new Materiaux(0, 0, 0, 0, 0, 0, 1, 0)));
        cartes.add(new Carte(Age.I, Couleur.VERT, "Atelier", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 1)));

        //MARRON
        cartes.add(new Carte(Age.I, Couleur.MARRON, "Chantier", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
        cartes.add(new Carte(Age.I, Couleur.MARRON, "Exploitation forestiere", 1, new Materiaux(1, 0, 0, 0, 0, 0, 0, 0)));
        cartes.add(new Carte(Age.I, Couleur.MARRON, "Fosse argileuse", 3, new Materiaux(1, 0, 0, 0, 0, 0, 0, 0)));
        cartes.add(new Carte(Age.I, Couleur.MARRON, "Bassin argileux", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
        cartes.add(new Carte(Age.I, Couleur.MARRON, "Filon", 1, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
        cartes.add(new Carte(Age.I, Couleur.MARRON, "Cavite", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
        // GRIS
        cartes.add(new Carte(Age.I, Couleur.GRIS, "Verrerie", 5, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
        cartes.add(new Carte(Age.I, Couleur.GRIS, "Presse", 3, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
        cartes.add(new Carte(Age.I, Couleur.GRIS, "Metier à tisser", 1, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));

      }
    }

    return cartes;
  }

  public ArrayList<Carte> getCartes(int nb, Age a, JSONArray cartes) {
    System.out.println(cartes);
    return new ArrayList<Carte>() {
    };
  }

  public ArrayList<Carte> getAleaCartes(int nb, Age a) {
    ArrayList<Carte> cartes = getCartes(nb, a);
    Collections.shuffle(cartes);
    return cartes;
  }

}

