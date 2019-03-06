package Test;

import jeu.Age;
import jeu.Carte;
import jeu.Couleur;
import jeu.Materiaux;

import java.util.ArrayList;

public class Cartetest {
    void testcarte() {
        ArrayList<Carte> cartes = new ArrayList<Carte>();
        cartes.add(new Carte(Age.I, Couleur.BLEU, Materiaux.MINERAI,1,"f",2));
        int p = 2;
        String a = "Batiment";
        for (Carte c : cartes) {
            assertEquals(p, c.getpoints());

        }
    }

    private void assertEquals(int p, int getpoints) {
    }
}
