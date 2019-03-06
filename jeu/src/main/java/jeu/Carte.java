package jeu;
public class Carte {
    
    private Age age;
    private Couleur couleur;
    private int id;
    private String nom;
    private Materiaux materiaux;

    private int points ;


    public Carte(Age age, Couleur couleur,Materiaux materiaux, int id,String nom, int points) {
        this.age = age;
        this.couleur = couleur;
        this.id = id;
        this.nom = nom;
        this.points = points;
        this.materiaux=materiaux;
    }
    public Carte(){
        age=null;
        couleur=null;
        id=0;
        nom="";
        points=0;
    }

    public Carte(Carte c){
        age=c.age;
        couleur=c.couleur;
        id=c.id;
        nom=c.nom;
        points=c.points;
    }
    public Age getAge() {
        return age;
    }
    
    public Couleur getCouleur() {
        return couleur;
    }
    
    public int getId() {
        return id;
    }

    public int getpoints() {
        return points;
    }

    @Override
    public String toString() {
        return  "Age:" + this.age+ " - Couleur: "+this.couleur+" " + "- "+ "MATERIAUX: "+this.materiaux+ " - " + "ID: "+this.id+" - "+ "Nom: "+this.nom+" - "+ "Points: "+this.points;

    }

}