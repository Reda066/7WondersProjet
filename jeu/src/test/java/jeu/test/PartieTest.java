package jeu.test;

import com.corundumstudio.socketio.SocketIOServer;
import jeu.*;
import joueur.Joueur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PartieTest {
    SocketIOServer serveur;

    private ArrayList<Participant> joueurs = new ArrayList<Participant>();

    Partie partie;

    Participant  p1, p2, p3;

    Carte c1, c2, c3;

    Merveille m;

    ArrayList<Carte> carte;
    @BeforeEach
    void setUp() {

        this.partie = new Partie(serveur);

        this.m = new Merveille("EPHESOS", Face.A,3);



        this.c1 = new Carte(Age.I, Couleur.BLEU, "Autel", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0));
        this.c2 = new Carte(Age.I, Couleur.VERT, "Officine", 0, new Materiaux(0, 0, 0, 0, 0, 1, 0, 0));
        this.c3 = new Carte(Age.I, Couleur.ROUGE, "Caserne", 0, new Materiaux(0, 0, 0, 0, 1, 0, 0, 0));

        this.carte = new ArrayList<Carte>(7);
        this.carte.add(c1);
        this.carte.add(c2);
        this.carte.add(c3);




    }


    @Test
    void finAgeCourant() {
    }

    @Test
    void finDeTousLesActionsDesJoueurs() {
    }

    @Test
    void ajouter_joueur() {
    }

    @Test
    void decouvrir_voisins() {
    }

    @Test
    void distribuer_merveille() {
    }

    @Test
    void voisin() {
    }

    @Test
    void distribuer_cartes() {
    }

    @Test
    void distribuer_cartes1() {
    }

    @Test
    void donner_gold() {
    }

    @Test
    void commencer() {
    }
}