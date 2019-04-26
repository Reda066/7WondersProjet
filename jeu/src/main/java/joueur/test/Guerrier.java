package joueur.test;

import jeu.Carte;
import jeu.Couleur;

public class Guerrier extends Joueur {
    public Guerrier(String nom,String urlServeur) {
        super(nom, urlServeur);
    }

    public void poserUneCarte(Carte c){
        for (int i = 0; i < cartesEnMain.size(); i++){
            if(this.cartesEnMain.get(i).getCouleur().equals(Couleur.ROUGE)){
                cartesEnMain.remove(i);
                break;
            }
            merveille.poserUneCarte(c);
        }
    }
}
