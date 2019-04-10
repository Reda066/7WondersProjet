package jeu.gestion;
import jeu.Age;
import jeu.Carte;
import jeu.Couleur;
import jeu.Materiaux;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

//C'est ici qu'on créé les cartes en fonction de l'age pour l'instant nous disposons seulement de l'age 1

public class gestionCarte {

  public ArrayList<Carte> getCartes(int nb_joueurs, Age a) {
    ArrayList<Carte> cartes = new ArrayList<Carte>() {
    };

    if (a.equals(Age.I)) {

        if (nb_joueurs >= 3) {
            //BLEU
            cartes.add(new Carte(Age.I, Couleur.BLEU, "Autel", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
            cartes.add(new Carte(Age.I, Couleur.BLEU, "Bains", 3, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
            cartes.add(new Carte(Age.I, Couleur.BLEU, "Theatre", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));

            //ROUGE
            cartes.add(new Carte(Age.I, Couleur.ROUGE, "Caserne", 0, new Materiaux(0, 0, 0, 0, 1, 0, 0, 0)));
            cartes.add(new Carte(Age.I, Couleur.ROUGE, "Palissade", 0, new Materiaux(0, 0, 1, 0, 0, 0, 0, 0)));
            cartes.add(new Carte(Age.I, Couleur.ROUGE, "Tour de garde", 0, new Materiaux(0, 0, 0, 1, 0, 0, 0, 0)));

            //JAUNE
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
      if (nb_joueurs >= 4) {

          //BLEU
          cartes.add(new Carte(Age.I, Couleur.BLEU, "Preteur sur gages", 3, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));

          //ROUGE
          cartes.add(new Carte(Age.I, Couleur.ROUGE, "Tour de garde", 0, new Materiaux(0, 0, 0, 1, 0, 0, 0, 0)));

          //JAUNE
          cartes.add(new Carte(Age.I, Couleur.JAUNE, "Taverne", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));

          //VERT
          cartes.add(new Carte(Age.I, Couleur.VERT, "Scriptorium", 0, new Materiaux(0, 0, 0, 0, 0, 0, 1, 0)));

          //MARRON
          cartes.add(new Carte(Age.I, Couleur.MARRON, "Chantier", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
          cartes.add(new Carte(Age.I, Couleur.MARRON, "Filon", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
          cartes.add(new Carte(Age.I, Couleur.MARRON, "Excavation", 0, new Materiaux(1, 0, 0, 0, 0, 0, 0, 0)));

      }

      if (nb_joueurs >= 5) {
          //BLEU
          cartes.add(new Carte(Age.I, Couleur.BLEU, "Autel", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
          //ROUGE
          cartes.add(new Carte(Age.I, Couleur.ROUGE, "Caserne", 0, new Materiaux(0, 0, 0, 0, 1, 0, 0, 0)));

          //JAUNE
          cartes.add(new Carte(Age.I, Couleur.JAUNE, "Taverne", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));

          //VERT
          cartes.add(new Carte(Age.I, Couleur.VERT, "Officine", 0, new Materiaux(0, 0, 0, 0, 0, 1, 0, 0)));

          //MARRON
          cartes.add(new Carte(Age.I, Couleur.MARRON, "Cavite", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
          cartes.add(new Carte(Age.I, Couleur.MARRON, "Bassin argileux", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
          cartes.add(new Carte(Age.I, Couleur.MARRON, "Gisement", 0, new Materiaux(1, 0, 0, 0, 0, 0, 0, 0)));
      }


    //Prochaines itérations
      if (a.equals(Age.II)) {
          if (nb_joueurs >= 3) {
              ///BLEU
              cartes.add(new Carte(Age.II, Couleur.BLEU, "Temple", 3, new Materiaux(0, 0, 1, 1, 0, 0, 0, 1)));
              cartes.add(new Carte(Age.II, Couleur.BLEU, "Tribunal", 4, new Materiaux(0, 0, 0, 2, 0, 1, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.BLEU, "Statue", 4, new Materiaux(0, 0, 1, 0, 2, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.BLEU, "Aqueduc", 5, new Materiaux(0, 3, 0, 0, 0, 0, 0, 0)));

              //ROUGE
              cartes.add(new Carte(Age.II, Couleur.ROUGE, "Muraille", 0, new Materiaux(0, 3, 0, 0, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.ROUGE, "Ecuries", 0, new Materiaux(0, 0, 1, 1, 1, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.ROUGE, "Champs de tir", 0, new Materiaux(0, 0, 2, 0, 1, 0, 0, 0)));


              //JAUNE
              cartes.add(new Carte(Age.II, Couleur.JAUNE, "Forum", 0, new Materiaux(0, 0, 0, 2, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.JAUNE, "Vignoble", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.JAUNE, "Caravanserail", 0, new Materiaux(0, 0, 2, 0, 0, 0, 0, 0)));

              //VERT
              cartes.add(new Carte(Age.II, Couleur.VERT, "Dispensaire", 0, new Materiaux(0, 0, 0, 0, 2, 0, 0, 1)));
              cartes.add(new Carte(Age.II, Couleur.VERT, "Laboratoire", 0, new Materiaux(0, 0, 0, 2, 0, 0, 1, 0)));
              cartes.add(new Carte(Age.II, Couleur.VERT, "Bibliotheque", 0, new Materiaux(0, 2, 0, 0, 0, 1, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.VERT, "Ecole", 0, new Materiaux(0, 0, 1, 0, 0, 0, 1, 0)));

              //MARRON
              cartes.add(new Carte(Age.II, Couleur.MARRON, "Carriere", 0, new Materiaux(1, 0, 0, 0, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.MARRON, "Scierie", 0, new Materiaux(1, 0, 0, 0, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.MARRON, "Fonderie", 0, new Materiaux(1, 0, 0, 0, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.MARRON, "Briqueterie", 0, new Materiaux(1, 0, 0, 0, 0, 0, 0, 0)));
              //GRIS
              cartes.add(new Carte(Age.II, Couleur.GRIS, "Verrerie", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.GRIS, "Presse", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.GRIS, "Metier à tisser", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));

          }

          if (nb_joueurs >= 4) {
              //ROUGE
              cartes.add(new Carte(Age.II, Couleur.ROUGE, "Place d'armes", 0, new Materiaux(0, 0, 1, 0, 2, 0, 0, 0)));

              //JAUNE
              cartes.add(new Carte(Age.II, Couleur.JAUNE, "Bazar", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));

              //VERT
              cartes.add(new Carte(Age.II, Couleur.VERT, "Dispensaire", 0, new Materiaux(0, 0, 0, 0, 2, 0, 0, 1)));

              //MARRON
              cartes.add(new Carte(Age.II, Couleur.MARRON, "Carriere", 0, new Materiaux(1, 0, 0, 0, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.MARRON, "Scierie", 0, new Materiaux(1, 0, 0, 0, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.MARRON, "Fonderie", 0, new Materiaux(1, 0, 0, 0, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.MARRON, "Briqueterie", 0, new Materiaux(1, 0, 0, 0, 0, 0, 0, 0)));


          }
          if (nb_joueurs >= 5) {

              //BLEU
              cartes.add(new Carte(Age.II, Couleur.BLEU, "Tribunal", 4, new Materiaux(0, 0, 0, 2, 0, 1, 0, 0)));

              //ROUGE
              cartes.add(new Carte(Age.II, Couleur.ROUGE, "Ecuries", 0, new Materiaux(0, 0, 1, 1, 1, 0, 0, 0)));

              //JAUNE

              cartes.add(new Carte(Age.II, Couleur.JAUNE, "Caravanserail", 0, new Materiaux(0, 0, 2, 0, 0, 0, 0, 0)));

              //VERT

              cartes.add(new Carte(Age.II, Couleur.VERT, "Laboratoire", 0, new Materiaux(0, 0, 0, 2, 0, 0, 1, 0)));

              //GRIS
              cartes.add(new Carte(Age.II, Couleur.GRIS, "Verrerie", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.GRIS, "Presse", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
              cartes.add(new Carte(Age.II, Couleur.GRIS, "Metier à tisser", 0, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));

          }
      }

    return cartes;
  }

// obtention des cartes de façon aléatoire grace à la methode shuffle
  public ArrayList<Carte> getAleaCartes(int nb, Age a) {
    ArrayList<Carte> cartes = getCartes(nb, a);
    Collections.shuffle(cartes);
    return cartes;
  }

}

