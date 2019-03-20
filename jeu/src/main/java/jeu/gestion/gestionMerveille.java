package jeu.gestion;
import jeu.Merveille;
import java.util.ArrayList;
import java.util.Collections;

public class gestionMerveille {

  public gestionMerveille(){}

  public static Merveille getMerveilleById(int id)
  {
    switch ( id )
    {
      case 1:  return new Merveille(1,"Le phare de d\'Alexandrie");
      case 2:  return new Merveille(2,"Les jardins suspendus de Babylone");
      case 3:  return new Merveille(3,"Le temple d\'Artémis à Ephèse");
      case 4:  return new Merveille(4,"La statue de Zeus à Olympie");
    }
    return null;
  }



  public ArrayList<Merveille> getMerveilles(){

    ArrayList<Merveille> merveilles = new ArrayList<Merveille>();
    merveilles.add(new Merveille(1,"Le phare de d\'Alexandrie"));
    merveilles.add(new Merveille(2,"Les jardins suspendus de Babylone"));
    merveilles.add(new Merveille(3,"Le temple d\'Artémis à Ephèse"));
    merveilles.add(new Merveille(4,"La statue de Zeus à Olympie"));

    return merveilles;
  }
    public ArrayList<Merveille> getAleaMerveilles(int nb){

        ArrayList<Merveille> merveilles = getMerveilles();
        Collections.shuffle(merveilles);
        merveilles.subList(0, nb-1);
        return merveilles;
    }

    public ArrayList<Merveille> getAleaMerveilles(){

    ArrayList<Merveille> merveilles = getMerveilles();

    Collections.shuffle(merveilles);
    return merveilles;
  }

}
