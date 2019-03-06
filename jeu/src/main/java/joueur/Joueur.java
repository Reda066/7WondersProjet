package joueur;

import io.socket.client.IO;
import io.socket.emitter.Emitter;
import jeu.Carte;
import jeu.Merveille;
import jeu.controlleur.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

public class Joueur extends Client {


  protected ArrayList<Carte> cartesEnMain = new ArrayList<Carte>(){};
  protected String nom;
  protected Merveille merveille;

  protected boolean sans_connexion = false;
  protected int rang;
  protected UUID id_affecté;
  public boolean fins_actions = false;

  public int getRang() {
    return rang;
  }
  public void setRang(int rang) {
      this.rang = rang;
  }
  public Joueur (UUID id , int r){
    super();
    sans_connexion=true;
    rang =r;
    id_affecté=id;

  }
  public Joueur(String nom,String url) {
    IO.Options opts = new IO.Options();
    opts.forceNew = true;
    this.nom = nom;
    try {
      connexion = IO.socket(url, opts);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }


    System.out.println("[JOUEUR] " + getNom() + " : Je prépare mes écouteurs");


    // Lors de la création du Joueur on se connecte au serveur
    connexion.on("connect", new Emitter.Listener() {
      @Override
      public void call(Object... data) {}
    });
    connexion.on("reception_merveille", new Emitter.Listener() {
      @Override
      public void call(Object... o) {
        System.out.println("[CLIENT "+id().toString()+"] Réception de la merveille "+o[0]);
        setMerveille(ControlleurMerveille.getMerveilleById((Integer)o[0]));
      }
    });
    connexion.on("reception_cartes", new Emitter.Listener() {
      @Override
      public void call(Object... o) {
        JSONArray ids = (JSONArray) o[0];
        if(nouvellesCartesEntrantes(ids)){
          System.out.println("[CLIENT "+id().toString()+"] Réception des cartes "+ cartesEnMain.toString());
          int index_carte_choisi = 0;
          int carte_id = cartesEnMain.get(index_carte_choisi).getId();
          connexion.emit("jai_pose_une_carte",carte_id);
          poserUneCarte(cartesEnMain.get(index_carte_choisi));

          declarerFinActions();
        }

      }
    });



  }

  public void setMerveille(Merveille merveille) {
    this.merveille = merveille;
  }
  public boolean nouvellesCartesEntrantes(JSONArray ids){
    // Vider la variable cartesEnMain
    cartesEnMain = new ArrayList<Carte>(){};
    for (int i = 0; i < ids.length(); i++) {
      try {
        ajouterCarteEnMain(ControlleurCarte.getCarteById(ids.getInt(i)));
      } catch (JSONException e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;

  }
  public void poserUneCarte(Carte c){

    for (int i = 0; i < cartesEnMain.size(); i++) {
      if(cartesEnMain.get(i).getId() == c.getId()){
          cartesEnMain.remove(i);
        break;
      }
    }
    merveille.poserUneCarte(c);
  }
  public void declarerFinActions(){
    fins_actions=true;
    connexion.emit("jai_fini_mes_actions",true);

  }
  public ArrayList<Carte> getCartesEnMain() {
    return cartesEnMain;
  }
    public int[] getCartesIdEnMain() {

        int ids[]= new int[cartesEnMain.size()];
        for (int i = 0; i < cartesEnMain.size(); i++) {
            ids[i] = cartesEnMain.get(i).getId();
        }
        return ids;
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
      return this.id_affecté;

    return UUID.fromString(this.connexion.id());
  }

  public Merveille setMerveille(int id) {
    return this.merveille = ControlleurMerveille.getMerveilleById(id);
  }
  public void setCartesEnMain(ArrayList<Carte> cartes){
    cartesEnMain = cartes;
  }

//AVANCEMENT CALCUL SCORE A INTEGRER DANS PARTIE POUR L AFFICHER
  public int calculerScore(){
    int score =0;
    ArrayList<Carte> cartes = merveille.getCartesPosé();
    for (int i = 0; i <cartes.size(); i++)
      score+=cartes.get(i).getpoints();
    return score;
  }


}