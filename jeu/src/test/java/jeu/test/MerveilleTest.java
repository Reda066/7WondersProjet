package jeu.test;

import jeu.*;
import jeu.Merveille;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MerveilleTest {
    @Test
    void testmerveille() {
        ArrayList<Merveille> merveilles = new ArrayList();
        ArrayList<Carte> cartes = new ArrayList<Carte>();
        cartes.add(new Carte(Age.I, Couleur.BLEU, "Autel", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
        merveilles.add(new Merveille("ALEXANDRIA",  Face.A, 3, 0));
        for (Merveille m : merveilles) {
            assertEquals("ALEXANDRIA",m.getNom());
            for (Carte c : cartes) {
            m.poserUneCarte(c);
                assertEquals(c,m.getCartesPose().get(0));
            }

        }

    }
}
