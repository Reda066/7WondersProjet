package commun;

import java.util.ArrayList;

public class Joueur {
    private String name;
    private int Points;
    private Plateau PlateauJoueur;
    private ArrayList<Materiaux> MateriauxJoueur = new ArrayList<Materiaux>();

    private ArrayList<Carte> CartesJoueur =  new ArrayList<Carte>();
    private int nombreCarte = CartesJoueur.size();

}
