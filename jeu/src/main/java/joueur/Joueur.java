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

public class Joueur {


  protected ArrayList<Carte> cartesEnMain = new ArrayList<Carte>(){};
  protected String nom = "Non-identifié";
  protected Merveille merveille;
  protected Materiaux materiauxProduite;




  public boolean fins_actions = false;
  final Object attenteDeconnexion = new Object();
  Socket connexion;

  protected Participant voisinDroite;
  protected Participant voisinGauche;

  public Joueur(String nom ,String urlServeur) {

    try {
      IO.Options opts = new IO.Options();
      opts.forceNew = true;
      this.nom = nom;
      connexion = IO.socket(urlServeur, opts);


      System.out.println("[JOUEUR] " + getNom() + " : Préparation des écouteurs");

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
      connexion.on("decouvrirVoisin", new Emitter.Listener() {
        @Override
        public void call(Object... o) {
          Gson gson=new Gson();
          voisinDroite = gson.fromJson(o[0].toString(),Participant.class);
          voisinGauche = gson.fromJson(o[1].toString(),Participant.class);
        }
      });

      connexion.on("ajoutGold", new Emitter.Listener() {
        @Override
        public void call(Object... o) {
          Gson gson=new Gson();
          Materiaux materiaux = gson.fromJson(o[0].toString(), Materiaux.class);
          setMateriauxProduite(materiaux);
        }
      });
      connexion.on("receptionMerveille", new Emitter.Listener() {
        @Override
        public void call(Object... o) {
          Merveille m =new gestionMerveille().getMerveille((String) o[0],o[1]=="A"?Face.A:Face.B);
          setMerveille(m);
        }
      });
      connexion.on("receptionCarte", new Emitter.Listener() {
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
            connexion.emit("poserCarte", carte_choisi.getNom());
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


  public void seConnecter() {
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


  /*public void poserUneCarte(Carte c){
    switch (strategie){
      case GUERRE:
        for (int i = 0; i < cartesEnMain.size(); i++){
          if(this.cartesEnMain.get(i).getCouleur().equals(Couleur.ROUGE)){
            cartesEnMain.remove(i);
            break;
          }
          merveille.poserUneCarte(c);
        }
        break;

      case ALCHIMISTE:
        for (int i = 0; i < cartesEnMain.size(); i++){
          if(this.cartesEnMain.get(i).getCouleur().equals(Couleur.VERT)){
            cartesEnMain.remove(i);
            break;
          }
          merveille.poserUneCarte(c);
        }
        break;

      case BATIMENT:
        for (int i = 0; i < cartesEnMain.size(); i++){
          if(this.cartesEnMain.get(i).getCouleur().equals(Couleur.BLEU)){
            cartesEnMain.remove(i);
            break;
          }
          merveille.poserUneCarte(c);
        }
        break;

    }
    for (int i = 0; i < cartesEnMain.size(); i++) {
      if(cartesEnMain.get(i).equals(c)){
        cartesEnMain.remove(i);
        break;
      }
    }
    merveille.poserUneCarte(c);
  }*/


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

  connexion.emit("defausserCarte", c.getNom());
}
  public void declarerFinActions(){
    fins_actions=true;
    connexion.emit("finAction",true);

  }

  public int Score(){
    int score =0;
    ArrayList<Carte> cartes = merveille.getCartesPose();
    for (int i = 0; i <cartes.size(); i++)
      score+=cartes.get(i).getPointsVictoire();
    return score;
  }


  public String getNom() {
    return nom;
  }


}