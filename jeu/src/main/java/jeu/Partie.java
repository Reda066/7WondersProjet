package jeu;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import joueur.*;
import jeu.controlleur.*;

import java.util.*;

public class Partie {
    // Liste des joueurs
    // Distribution de merveilles,deck,golds
    // Attendre que tout le monde est posé sa carte puis passer au prochain tour donc relancer la fonction jusqu'a ce qu'il ne reste plus que une carte dans les deck
    // Compter les points
    // Victoire fin de partie
    private SocketIOServer serveur;
    private Age age = Age.I;

  public List<Joueur> getJoueurs() {
    return joueurs;
  }

  private List<Joueur> joueurs = new ArrayList<Joueur>();
    private ArrayList<Carte> deck;

    public Partie(SocketIOServer s){
        System.out.println("Serveur && Partie > Création des listeners d'evenements");
        serveur = s;

        // Ajout d'un événement qui s'appelle pose_carte
        serveur.addEventListener("jai_pose_une_carte", int.class, new DataListener<Integer>() {
            public void onData(SocketIOClient client, Integer c, AckRequest ackRequest) throws Exception {
                System.out.println("Serveur && Partie > "+client.getSessionId() + " pose la carte " + c);

                for (int i = 0; i < joueurs.size(); i++) {
                  if(joueurs.get(i).id().equals(client.getSessionId())){
                    joueurs.get(i).poserUneCarte(ControlleurCarte.getCarteById(c));
                    return;
                  }
              }
            }
        });

    }

        public void ajouter_joueur(UUID id ){

        Joueur j = new Joueur(id,joueurs.size());
        System.out.println("Partie > Ajout du joueur "+j.getRang());
        joueurs.add(j);
    }

    public void retirer_joueur(Joueur j){
        joueurs.remove(j);
    }

    // Donne les merveilles de maniière aléatoire dès le début du jeu à un joueur
    public boolean distribuer_merveille(){
        ArrayList<Merveille> merveilles = new ControlleurMerveille().getAleaMerveilles(joueurs.size());
        if(merveilles==null) return false;
        for (int i=0; i< joueurs.size();i++){
            joueurs.get(i).setMerveille(merveilles.get(i));
            serveur.getClient(joueurs.get(i).id()).sendEvent("reception_merveille",merveilles.get(i).id);
        }
        return true;
    }


    // Permet de donner à chaque joueur son packet de carte en main

    public boolean distribuer_cartes(ArrayList<Carte> cartes,Joueur j){
      if(cartes.size()<=0)
        return true;
      j.fins_actions=false;
      j.setCartesEnMain(cartes);
        serveur.getClient(j.id()).sendEvent("reception_cartes",j.getCartesIdEnMain());
      return true;
    }


    public boolean distribuer_cartes(){

        ArrayList<Carte> cartes = new ControlleurCarte().getAleaCartes(joueurs.size(),age);
        int tabId [];
        int index=0;
        if(cartes==null) return false;
        for( Carte c : cartes){
            joueurs.get(index).ajouterCarteEnMain(c);
            index++;
            if(index == joueurs.size())
                index=0;
        }

        for(Joueur j : joueurs){
           serveur.getClient(j.id()).sendEvent("reception_cartes",j.getCartesIdEnMain());
        }
        return true;
    }

    public void commencer(){
      /*
      * Il faut s'assurer que les joueurs ont bien recu leurs merveilles avant de pouvoir distribuer les cartes
      * Ceci est un check en carton (provisoire), il sera remplacé avec les accusé de réception ackResponse pour suivre la réception des merveilles/cartes chez les joueurs
      */
      if(distribuer_merveille()){
        if(distribuer_cartes()) {
          serveur.getBroadcastOperations().sendEvent("debut_partie");
          System.out.println("Serveur && Partie > La partie commence !");
        }
      }
    }
}
