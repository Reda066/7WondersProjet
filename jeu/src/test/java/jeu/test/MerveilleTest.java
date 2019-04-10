package jeu.test;

import jeu.*;
import jeu.Merveille;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MerveilleTest {


    ArrayList<Carte> cartesPosee;

    Carte c1, c2;
    Merveille m;

    @BeforeEach
    void setUp() {
        this.cartesPosee = new ArrayList<Carte>();

        this.c1= new Carte(Age.I, Couleur.BLEU, "Autel", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0));
        this.c2 =  new Carte(Age.I, Couleur.BLEU, "Theatre", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0));
        this.m = new Merveille("ALEXANDRIA",  Face.A, 3, 0);

    }
    @Test
    void testmerveille() {

         this.m = new Merveille("ALEXANDRIA",  Face.A, 3, 0);

        Face face = Face.A;
        assert m.getFace().equals(face);


        String nom = "ALEXANDRIA";
        assert m.getNom().equals(nom);
        System.out.println("------------------TEST MERVEILLE------------------");
        System.out.println("La merveille "+m.getNom()+" a bien été créé avec pour face "+m.getFace());

    }

    @Test
    void testposerUneCarte(){

        this.cartesPosee.add(c1);
        this.cartesPosee.add(c2);

        assertEquals(2, this.cartesPosee.size());


        for(Carte c : cartesPosee){
            m.poserUneCarte(c1);
            m.poserUneCarte(c2);
            assertEquals(c1,m.getCartesPose().get(0));
            assertEquals(c2,m.getCartesPose().get(1));

            System.out.println("------------------TEST POSER UNE CARTE------------------");
            System.out.println("Les cartes "+m.getCartesPose().get(0).getNom()+" et "+m.getCartesPose().get(1).getNom()+" ont bien été posées");
        }

    }

}

