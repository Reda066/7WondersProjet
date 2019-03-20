package jeu;
public class Carte {
    
    private Age age;
    private Couleur couleur;
    private String nom;
    private Materiaux cout;

    private int pointsVictoire ;
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
    public Carte(){
        age=null;
        couleur=null;
        nom="";
        pointsVictoire=0;
        cout = null;
    }

    public Carte(Carte c){
        age=c.age;
        couleur=c.couleur;
        nom=c.nom;
        pointsVictoire=c.pointsVictoire;
        cout =c.cout;
    }
    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }
    public Materiaux getCout() {
        return cout;
    }

    public void setCout(Materiaux cout) {
        this.cout = cout;
    }

    public void setPointsVictoire(int pointsVictoire) {
        this.pointsVictoire = pointsVictoire;
    }

    public Couleur getCouleur() {
        return couleur;
    }

}