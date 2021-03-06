package joueur;

import jeu.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BatisseurTest {
    String url = "127.0.0.1:9000";

    Batisseur a;

    Carte c1, c2, c3;

    Merveille m;

    ArrayList<Carte> carte;
    @BeforeEach
    void setUp() {
        this.a = new Batisseur("reda", url);

        this.m = new Merveille("EPHESOS", Face.A,3);

        this.a.merveille = m;

        this.c1 = new Carte(Age.I, Couleur.BLEU, "Autel", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0));
        this.c2 = new Carte(Age.I, Couleur.VERT, "Officine", 0, new Materiaux(0, 0, 0, 0, 0, 1, 0, 0));
        this.c3 = new Carte(Age.I, Couleur.ROUGE, "Caserne", 0, new Materiaux(0, 0, 0, 0, 1, 0, 0, 0));

        this.carte = new ArrayList<Carte>(7);
        this.carte.add(c1);
        this.carte.add(c2);
        this.carte.add(c3);


        this.a.cartesEnMain = carte;

    }


    @Test
    void poserUneCarte() {
        System.out.println("------------------TEST EXISTANCE MAIN NON VIDE------------------");
        assertEquals(3, this.a.cartesEnMain.size());
        System.out.println("La main a bien une taille de "+this.a.cartesEnMain.size());

        this.a.poserUneCarte(c1);

        System.out.println("------------------TEST POSER UNE CARTE BLEU------------------");
        assertEquals(2, this.a.cartesEnMain.size());
        assertNotNull(this.a.merveille.getCartesPose());
        System.out.println("La main a bien une taille de "+this.a.cartesEnMain.size()+" la carte "+c2.toString()+" a bien été posé");
    }
}