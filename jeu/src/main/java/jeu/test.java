package jeu;

public class test {
    public static void main (String[] args){

        Carte c = new Carte(Age.I,Couleur.BLEU,Materiaux.BOIS,5,"test",5);
        System.out.println(c.toString());
    }
}
