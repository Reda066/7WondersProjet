package joueur;

import jeu.Carte;
import jeu.Couleur;

public class Batiment extends Joueur {

    public Batiment(String nom, String urlServeur) {
        super(nom, urlServeur);
    }


    public void poserUneCarte(Carte c) {
        for (int i = 0; i < cartesEnMain.size(); i++) {
            if (this.cartesEnMain.get(i).getCouleur().equals(Couleur.BLEU)) {
                cartesEnMain.remove(i);
                break;
            }
            merveille.poserUneCarte(c);
        }
    }

}
