package joueur;

import jeu.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {
    String url = "127.0.0.1:9000";

    Joueur joueur;

    Carte c1, c2, c3;

    Merveille m;

    ArrayList<Carte> carte;

    @BeforeEach
    void setUp() {

        this.joueur = new Joueur("reda", url);

        this.m = new Merveille("EPHESOS", Face.A,3);

        this.joueur.merveille = m;

        this.c1 = new Carte(Age.I, Couleur.BLEU, "Autel", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0));
        this.c2 = new Carte(Age.I, Couleur.VERT, "Officine", 0, new Materiaux(0, 0, 0, 0, 0, 1, 0, 0));
        this.c3 = new Carte(Age.I, Couleur.ROUGE, "Caserne", 0, new Materiaux(0, 0, 0, 0, 1, 0, 0, 0));

        this.carte = new ArrayList<Carte>(7);
        this.carte.add(c1);
        this.carte.add(c2);
        this.carte.add(c3);


        this.joueur.cartesEnMain = carte;

    }

    @Test
    void poserUneCarte() {
        System.out.println("before test : la taille de la main " + this.joueur.cartesEnMain.size());

        this.joueur.poserUneCarte(c1);
        assertEquals(2, this.joueur.cartesEnMain.size());
        assertEquals(1, this.joueur.merveille.getCartesPose().size());



    }


}