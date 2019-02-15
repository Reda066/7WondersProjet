package commun;

public class Carte {

    String nomCarte;
    int nbPoint;

        // Constructeur carte avec le nom et le nb de poitn rapportant la carte
        public Carte(String nomCarte, int nbPoint){
            this.nomCarte = nomCarte;
            this.nbPoint = nbPoint;
        }

    public String getNomCarte() {
        return nomCarte;
    }

    public int getNbPoint() {
        return nbPoint;
    }


}

