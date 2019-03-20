package jeu.gestion;
import jeu.Face;
import jeu.Merveille;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class gestionMerveille {
  public gestionMerveille(){}
  public ArrayList<Merveille> getToutesLesMerveillesPossible(){
    ArrayList<Merveille> merveilles = new ArrayList<Merveille>();
    Random r = new Random();
    merveilles.add(new Merveille("ALEXANDRIA",Face.A,3));
    merveilles.add(new Merveille("BABYLON",Face.A,3));
    merveilles.add(new Merveille("EPHESOS",Face.A,3));
    merveilles.add(new Merveille("GIZAH",Face.A,3));
    merveilles.add(new Merveille("HALIKARNASSOS",Face.A,3));
    merveilles.add(new Merveille("OLYMPIA",Face.A,3));
    merveilles.add(new Merveille("RHÓDOS",Face.A,3));

    merveilles.add(new Merveille("ALEXANDRIA",Face.B,0));
    merveilles.add(new Merveille("BABYLON",Face.B,3));
    merveilles.add(new Merveille("EPHESOS",Face.B,3));
    merveilles.add(new Merveille("GIZAH",Face.B,4));
    merveilles.add(new Merveille("HALIKARNASSOS",Face.B,3));
    merveilles.add(new Merveille("OLYMPIA",Face.B,3));
    merveilles.add(new Merveille("RHODOS",Face.B,2));
    return merveilles;
  }
  public ArrayList<Merveille> getMerveilles(){

    ArrayList<Merveille> merveilles = new ArrayList<Merveille>();
    Random r = new Random();
    merveilles.add(new Merveille("ALEXANDRIA",r.nextInt(2)==0?Face.A:Face.B,3,0));
    merveilles.add(new Merveille("BABYLON",r.nextInt(2)==0?Face.A:Face.B,3,3));
    merveilles.add(new Merveille("EPHESOS",r.nextInt(2)==0?Face.A:Face.B,3,3));
    merveilles.add(new Merveille("GIZAH",r.nextInt(2)==0?Face.A:Face.B,3,4));
    merveilles.add(new Merveille("HALIKARNASSOS",r.nextInt(2)==0?Face.A:Face.B,3,3));
    merveilles.add(new Merveille("OLYMPIA",r.nextInt(2)==0?Face.A:Face.B,3,3));
    merveilles.add(new Merveille("RHODOS",r.nextInt(2)==0?Face.A:Face.B,3,2));

    return merveilles;
  }
  public Merveille getMerveille(String n,Face f){
    ArrayList<Merveille> merveilles = getToutesLesMerveillesPossible();
    for(Merveille m:merveilles)
      if(m.getNom().equals(n) && m.getFace().equals(f))
        return m;
    return null;
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
