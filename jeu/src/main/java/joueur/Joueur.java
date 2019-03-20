package joueur;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import jeu.*;
import jeu.gestion.*;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

public class Joueur {


  protected ArrayList<Carte> cartesEnMain = new ArrayList<Carte>(){};
  protected String nom = "Non-identifié";
  protected Merveille merveille;
  protected Materiaux materiauxProduite;

  protected boolean sans_connexion = false;
  protected int rang;
  protected UUID id_affecte;
  public boolean fins_actions = false;
  // Objet de synchr
  final Object attenteDeconnexion = new Object();
  Socket connexion;

  protected Participant voisin_de_droite;
  protected Participant voisin_de_gauche;


  public int getRang() {
    return rang;
  }

  public void setRang(int rang) {
      this.rang = rang;
  }
  // On le crée ici donc le joueur ? plus besoin de cette class

  public Joueur(String nom ,String urlServeur) {

    try {
      // il fallait rajouter ça enfaite
      IO.Options opts = new IO.Options();
      opts.forceNew = true;
      this.nom = nom;
      connexion = IO.socket(urlServeur, opts);


      System.out.println("[JOUEUR] " + getNom() + " : Je prépare mes écouteurs");

      connexion.on("connect", new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
          connexion.emit("voila_mon_nom",getNom());
        }
      });

      connexion.on("disconnect", new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
          System.out.println("[JOUEUR] "+getNom()+" : Je me suis fait déconnecté");
          connexion.disconnect();
          connexion.close();

          synchronized (attenteDeconnexion) {
            attenteDeconnexion.notify();
          }
        }
      });
      connexion.on("decouvrir_voisins", new Emitter.Listener() {
        @Override
        public void call(Object... o) {
          Gson gson=new Gson();
          voisin_de_droite = gson.fromJson(o[0].toString(),Participant.class);
          voisin_de_gauche = gson.fromJson(o[1].toString(),Participant.class);
        }
      });

      connexion.on("ajout_gold", new Emitter.Listener() {
        @Override
        public void call(Object... o) {
          Gson gson=new Gson();
          Materiaux materiaux = gson.fromJson(o[0].toString(), Materiaux.class);
          setMateriauxProduite(materiaux);
        }
      });
      connexion.on("reception_merveille", new Emitter.Listener() {
        @Override
        public void call(Object... o) {
          Merveille m =new gestionMerveille().getMerveille((String) o[0],o[1]=="A"?Face.A:Face.B);
          setMerveille(m);
        }
      });
      connexion.on("reception_cartes", new Emitter.Listener() {
        @Override
        public void call(Object... o) {
          Boolean posable=false;
          Boolean cout =true;
          Gson gson=new Gson();
          int z=0;
          Type typeCarte = new TypeToken<ArrayList<Carte>>(){}.getType();
          ArrayList<Carte> cartes = gson.fromJson(o[0].toString(), typeCarte);
          cartesEnMain=cartes;

          // Controle du nom de la carte
          int index_carte_choisi= (int)(Math.random() *cartesEnMain.size());
          for(int g=0;g<merveille.getCartesPose().size();g++){
              if(!(cartesEnMain.get(index_carte_choisi).getNom().equals(merveille.getCartesPose().get(g).getNom()))){
                //Contrôle du cout de la carte
                while(z!= getMateriauxProduite().getListeMateriaux().size()){
                  if (cartesEnMain.get(index_carte_choisi).getCout().getListeMateriaux().get(z) > getMateriauxProduite().getListeMateriaux().get(z)) cout=false;
                  // A mettre dans des méthodes car on pourra les réutiliser
                  z++;
                }
                if(cout) {
                  index_carte_choisi = g;
                  posable = true;
                  break;
                }
              }
            index_carte_choisi = (int)Math.random() *cartesEnMain.size();
            }

          Carte carte_choisi = cartesEnMain.get(index_carte_choisi);
          if(posable = true) {
            connexion.emit("jai_pose_une_carte", carte_choisi.getNom());
            poserUneCarte(cartesEnMain.get(index_carte_choisi));
          }
          else {
            defausserUneCarte(carte_choisi);
          }
          declarerFinActions();
        }
      });

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

  }

  /*public void ping_du_client() {
    System.out.println("[CLIENT] J'envoie un ping au serveur ==> [SERVEUR]");
    connexion.emit("reception_ping_du_client", 0);
  }*/

  public void seConnecter() {
    // on se connecte
    connexion.connect();

    System.out.println("[JOUEUR] "+getNom()+" : En attente de déconnexion");
    synchronized (attenteDeconnexion) {
      try {
        attenteDeconnexion.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
        System.err.println("[JOUEUR] "+getNom()+" : Erreur dans l'attente");
      }
    }
  }

    public Materiaux getMateriauxProduite() {
        return materiauxProduite;
    }

  public void setMateriauxProduite(Materiaux materiauxProduite) {
    this.materiauxProduite = materiauxProduite;
  }
  public void ajouterRessourceProduite(Materiaux materiauxProduite) {
    this.materiauxProduite = materiauxProduite;
  }
  public void setMerveille(Merveille merveille) {
    this.merveille = merveille;
  }

  public void poserUneCarte(Carte c){
   for (int i = 0; i < cartesEnMain.size(); i++) {
      if(cartesEnMain.get(i).equals(c)){
          cartesEnMain.remove(i);
        break;
      }
    }
    merveille.poserUneCarte(c);
  }
public void defausserUneCarte(Carte c){
  for (int i = 0; i < cartesEnMain.size(); i++) {
    if(cartesEnMain.get(i).equals(c)){
      cartesEnMain.remove(i);
      break;
    }
  }
  connexion.emit("jai_defausser_une_carte", c.getNom());
}
  public void declarerFinActions(){
    fins_actions=true;
    connexion.emit("jai_fini_mes_actions",true);

  }
  public ArrayList<Carte> getCartesEnMain() {
    return cartesEnMain;
  }

  public void ajouterCarteEnMain(Carte c) {
    this.cartesEnMain.add(c);
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public UUID id() {
    if(this.sans_connexion)
      return this.id_affecte;

    return UUID.fromString(this.connexion.id());
  }

  public void setCartesEnMain(ArrayList<Carte> cartes){
    cartesEnMain = cartes;
  }

  public int calculerScore(){
    int score =0;
    ArrayList<Carte> cartes = merveille.getCartesPose();
    for (int i = 0; i <cartes.size(); i++)
      score+=cartes.get(i).getPointsVictoire();
    return score;
  }

}