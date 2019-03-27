package jeu;

import java.util.ArrayList;

public class Materiaux {

    protected ArrayList<Integer> listeMateriaux = new ArrayList<Integer>(){};

    public Materiaux(int or, int pierre, int bois, int argile, int minerai, int textile, int papyrus, int verre) {
        listeMateriaux.add(or);
        listeMateriaux.add(pierre);
        listeMateriaux.add(bois);
        listeMateriaux.add(argile);
        listeMateriaux.add(minerai);
        listeMateriaux.add(textile);
        listeMateriaux.add(papyrus);
        listeMateriaux.add(verre);
    }

    public ArrayList<Integer> getListeMateriaux() {
        return listeMateriaux;
    }

    public int getOr(){
        return getListeMateriaux().get(0);
    }

   }
