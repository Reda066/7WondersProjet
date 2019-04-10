package jeu.test;
import jeu.Age;
import jeu.Carte;
import jeu.Couleur;
import jeu.Materiaux;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
class carteTest {

  Carte carte;

  @BeforeEach
  void setUp() {
    carte = new Carte(Age.I, Couleur.BLEU, "Autel", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0));
  }
  @Test
  void testcarte() {

  String title = "Autel";
  assert carte.getNom().equals(title);

  int points = 2;
  assert carte.getPointsVictoire() == points;


    System.out.println("------------------TEST CARTE------------------");
    System.out.println("La carte "+carte.getNom()+" a bien été créé avec le nombre de points de victoires suivants : " +carte.getPointsVictoire());
  }
}

