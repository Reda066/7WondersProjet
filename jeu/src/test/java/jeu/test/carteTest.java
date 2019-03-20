package jeu.test;
import jeu.Age;
import jeu.Carte;
import jeu.Couleur;
import jeu.Materiaux;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
class carteTest {


  @Test
  void testcarte() {
    ArrayList<Carte> cartes = new ArrayList<Carte>();
    cartes.add(new Carte(Age.I, Couleur.BLEU, "Autel", 2, new Materiaux(0, 0, 0, 0, 0, 0, 0, 0)));
    int p = 3;
    String a = "Autel";
    for (Carte c : cartes) {
      assertEquals(p, c.getPointsVictoire());
      assertEquals(Couleur.BLEU, c.getCouleur());
      assertEquals(Age.I, c.getAge());
      assertEquals(a, c.getNom());
    }
  }
}