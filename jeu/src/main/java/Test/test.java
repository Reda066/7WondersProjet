package Test;

import jeu.Age;
import jeu.Carte;
import jeu.Couleur;
import jeu.Materiaux;

public class test {
    public static void main (String[] args){

        Carte c = new Carte(Age.I, Couleur.GRIS, "Metier à tisser", 1, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0));
        System.out.println(c.toString());
    }
}
