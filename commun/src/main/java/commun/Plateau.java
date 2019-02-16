package commun;
import java.util.ArrayList;

public class Plateau {


    //Definition des plateaux
    public static final String PLATEAU0 = "Invalid";
    public static final String PLATEAU1 = "Le Colosse des Rhodes";
    public static final String PLATEAU2 = "Le phare d'Alexandrie";
    public static final String PLATEAU3 = "Le temple d'Artèmus à Ephèse";
    public static final String PLATEAU4 = "Les jardins suspendus de Babylone";
    public static final String PLATEAU5 = "La statue de Zeus à Olympie";
    public static final String PLATEAU6 = "Le mausolée d'Halicarnasse";
    public static final String PLATEAU7 = "La grande pyramide de Gizeh";

    public static final int COTE_A = 0;
    public static final int COTE_B = 1;

    protected int COTE;
    protected String nomPlateau;

    public Plateau(String nomPlateau,int COTE) {
        this.nomPlateau=nomPlateau;
        this.COTE = COTE;
    }
}
