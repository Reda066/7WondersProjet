package jeu;

public class Carte {

    private Age age;
    private Couleur couleur;
    private String nom;
    private Materiaux cout;

    private int pointsVictoire;

    public int getPointsVictoire() {
        return pointsVictoire;
    }


    public Carte(Age age, Couleur couleur, String nom, int pointsVictoire, Materiaux cout) {
        this.age = age;
        this.couleur = couleur;
        this.nom = nom;
        this.pointsVictoire = pointsVictoire;
        this.cout = cout;
    }

    public Age getAge() {
        return age;
    }

    public String getNom() {
        return nom;
    }

    public Materiaux getCout() {
        return cout;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public String toString() {
        return "[Nom->" + getNom() + " | Couleur->" + getCouleur() + " | Points->" + getPointsVictoire() + "]";
    }

}