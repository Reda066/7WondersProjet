package commun;

public class Carte {

    protected String nomCarte;
    protected int nbPoint;
    protected Materiaux MateriauxCout;
    protected int color;
    protected int age;


        // Constructeur carte avec le nom et le nb de poitn rapportant la carte


    public Carte(String nomCarte, int nbPoint, Materiaux materiauxCout, int color, int age) {
        this.nomCarte = nomCarte;
        this.nbPoint = nbPoint;
        MateriauxCout = materiauxCout;
        this.color = color;
        this.age = age;
    }

    public String getNomCarte() {
        return nomCarte;
    }

    public int getNbPoint() {
        return nbPoint;
    }

    public Materiaux getMateriauxCout() {
        return MateriauxCout;
    }

    public int getColor() {
        return color;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString(){
            return "[carte -"+getNomCarte()+" -]";
    }

    //Test
    public boolean equals(Object o){
            if ((o !=null) && (o instanceof Carte)){
                return getNomCarte().equals(((Carte) o).getNomCarte());
            }
            else return false;
    }
}

