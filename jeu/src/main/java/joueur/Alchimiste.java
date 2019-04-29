package joueur;

import jeu.Carte;
import jeu.Couleur;

public class Alchimiste extends Joueur {

    public Alchimiste(String nom, String urlServeur) {
        super(nom, urlServeur);
    }

    public void poserUneCarte(Carte c) {
        for (int i = 0; i < cartesEnMain.size(); i++) {
            if (this.cartesEnMain.get(i).getCouleur().equals(Couleur.VERT)) {
                cartesEnMain.remove(i);
                break;
            }
            merveille.poserUneCarte(c);
        }


    }

}
