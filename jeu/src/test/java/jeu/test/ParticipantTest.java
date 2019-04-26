package jeu.test;

import jeu.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {
    Participant participant;
    Carte c1, c2;
    Materiaux materiauxProduite;
    ArrayList<Carte> carteEnMain;

    Merveille m;

    @BeforeEach
    void setUp() {
        this.participant = new Participant("Reda", new UUID(10,5),1);
        this.c1= new Carte(Age.I, Couleur.BLEU, "Autel", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0));
        this.c2 =  new Carte(Age.I, Couleur.BLEU, "Theatre", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0));

        this.carteEnMain = new ArrayList<Carte>();
        this.m = new Merveille("ALEXANDRIA",  Face.A, 3, 0);
        this.participant.cartesEnMain = carteEnMain;

        this.participant.setMateriauxProduite(materiauxProduite);


    }

    @Test
    void Participant(){


        String nom = "Reda";
        assert participant.getNom().equals(nom);


        UUID uuid = new UUID(10,5);
        assert participant.id().equals(uuid);

        System.out.println("------------------TEST CREATION JOUEUR------------------");
        System.out.println("Le joueur "+nom+" est bien créé");
    }

    @Test
    void poserUneCarte() {
        this.participant.merveille = m;
        this.carteEnMain.add(c1);
        this.carteEnMain.add(c2);


        assertEquals(2, this.carteEnMain.size());

        this.participant.poserUneCarte(c1);
        this.m.poserUneCarte(c1);

        assertEquals(1,this.participant.cartesEnMain.size());

        System.out.println("------------------TEST POSER UNE CARTE------------------");
        System.out.println("Les cartes "+c1.toString()+" et "+c2.toString() +" ont bien été posées");

    }

    @Test
    void ajouterCarteEnMain() {
        this.participant.ajouterCarteEnMain(c1);
        this.participant.ajouterCarteEnMain(c2);

        assertEquals(2, this.carteEnMain.size());
        System.out.println("------------------TEST AJOUT CARTE EN MAIN------------------");
        System.out.println("Les cartes "+c1.toString()+" et "+c2.toString() +" ont bien été ajoutées à la main");
    }

    @Test
    void score() {
        this.participant.merveille = m;
        this.carteEnMain.add(c1);
        this.carteEnMain.add(c2);


        assertEquals(2, this.carteEnMain.size());

        this.participant.poserUneCarte(c1);
        this.participant.poserUneCarte(c2);

        assertEquals(4, this.participant.score());

        System.out.println("------------------TEST SCORE------------------");
        System.out.println("Le score est bien attribuée ici il est de : "+this.participant.score());
    }
}