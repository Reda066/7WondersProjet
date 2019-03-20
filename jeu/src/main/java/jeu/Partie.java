package jeu;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import joueur.*;
import jeu.gestion.*;

import java.util.*;

public class Partie {
private SocketIOServer serveur;
private Age age = Age.I;
private int nb_tours=0;
private List<Participant> joueurs = new ArrayList<Participant>();
private ArrayList<Carte> defausse = new ArrayList<Carte>();

public List<Participant> getJoueurs() {
    return joueurs;
  }

    private ArrayList<Carte> deck;

    public Partie(SocketIOServer s){
        serveur = s;


        serveur.addEventListener("jai_pose_une_carte", String.class, new DataListener<String>() {
            public void onData(SocketIOClient client, String nom_carte, AckRequest ackRequest) {


              System.out.println("[SERVEUR][PARTIE] "+ get_joueur_by_id(client.getSessionId()).getNom() + " a posé la carte <" +nom_carte+">");

                for (int i = 0; i < joueurs.size(); i++) {
                  if(joueurs.get(i).id().equals(client.getSessionId())){
                    for(Carte c : joueurs.get(i).getCartesEnMain())
                      if(c.getNom().equals(nom_carte)){
                        joueurs.get(i).poserUneCarte(c);
                        return;
                      }
                  }
                }
            }
        });

        serveur.addEventListener("jai_defausser_une_carte", String.class, new DataListener<String>() {
            public void onData(SocketIOClient client, String nom_carte, AckRequest ackRequest) {

                ArrayList <Integer> repet=get_joueur_by_id(client.getSessionId()).getMateriauxProduite().getListeMateriaux();
                System.out.println("[SERVEUR][PARTIE] "+ get_joueur_by_id(client.getSessionId()).getNom() + " a défaussé la carte <" +nom_carte
                        + "> Il a maintenant "
                        +repet.get(0)+" golds, "
                        +repet.get(1)+" pierres, "
                        +repet.get(2)+" bois, "
                        +repet.get(3)+" argile, "
                        +repet.get(4)+" minerai, "
                        +repet.get(5)+" textiles, "
                        +repet.get(6)+" papyrus, "
                        +repet.get(7)+" verres. ");

                for (int i = 0; i < joueurs.size(); i++) {
                    if(joueurs.get(i).id().equals(client.getSessionId())){
                        for(Carte c : joueurs.get(i).getCartesEnMain())
                            if(c.getNom().equals(nom_carte)){
                                defausse.add(c);
                                joueurs.get(i).getCartesEnMain().remove(c);
                                joueurs.get(i).ajouterOr(3);
                                return;
                            }
                    }
                }
            }
        });


      serveur.addEventListener("jai_fini_mes_actions", boolean.class, new DataListener<Boolean>() {
        public void onData(SocketIOClient client, Boolean c, AckRequest ackRequest) throws Exception {
          System.out.println("[SERVEUR][PARTIE] Le client "+get_joueur_by_id(client.getSessionId()).getNom() + " a finis ses actions");
          for (Participant j: joueurs){
              if(j.id().equals(client.getSessionId())){
                j.fins_actions=true;
                break;
              }
          }
          if(finAgeCourant()){
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("[SERVEUR][PARTIE] FIN DE L'AGE "+age);


          }else if (finDeTousLesActionsDesJoueurs()){
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> FIN DU TOUR N°"+nb_tours+" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            nb_tours++;
              ArrayList<Carte> temp =  joueurs.get(joueurs.size()-1).getCartesEnMain();
              for (int i = joueurs.size()-1; i >0 ; i--) {
                distribuer_cartes(joueurs.get(i-1).getCartesEnMain(),joueurs.get(i));
              }
              distribuer_cartes(temp,joueurs.get(0));
          }
          }
      });
    }


    public ArrayList<Carte> getDefausse() {
        return defausse;
    }

    public boolean finAgeCourant(){
      for (Participant j: joueurs){
        if(j.getCartesEnMain().size()!=0 || !j.fins_actions)
          return false;
      }
      return true;
    }
    public boolean finDeTousLesActionsDesJoueurs(){
      for (Participant j: joueurs){
        if(!j.fins_actions)
          return false;
      }
      return true;
    }
    public void ajouter_joueur(Participant p){
        System.out.println("[PARTIE] "+p.getNom()+" a été ajouté a la partie");
        joueurs.add(p);
    }
    public Participant get_joueur_by_id(UUID id){
        for (Participant j:joueurs)
            if(j.id().equals(id))
                return j;

        return null;
    }

    public void retirer_joueur(Joueur j){
        joueurs.remove(j);
    }

  public void decouvrir_voisins(){
    for(Participant p : joueurs){
      p.voisin_de_droite=voisin(p.getRang(),false);
      p.voisin_de_gauche=voisin(p.getRang(),true);
      serveur.getClient(p.id()).sendEvent("decouvrir_voisins",voisin(p.getRang(),false),voisin(p.getRang(),true));
    }
  }
    public void distribuer_merveille(){
        ArrayList<Merveille> merveilles = new gestionMerveille().getAleaMerveilles(joueurs.size());
        Face face;
        if(Math.random()*1==0) face=Face.A;
        else face=Face.B;
        for (int i=0; i< joueurs.size();i++){
            joueurs.get(i).setMerveille(merveilles.get(i));
            serveur.getClient(joueurs.get(i).id()).sendEvent("reception_merveille",merveilles.get(i).getNom(),merveilles.get(i).getFace());
            System.out.println("[SERVEUR][PARTIE] Affectation de la merveille <"+merveilles.get(i).getNom() +"> Face "+merveilles.get(i).cote+ " au joueur nommé "+joueurs.get(i).getNom());
            // emit au serveur au joueur x la merveille y
        }
    }

  public Participant voisin(int rang_joueur_courant,boolean de_gauche){
      if(de_gauche)
        return joueurs.get(rang_joueur_courant+1>=(joueurs.size()-1)?0:rang_joueur_courant+1);
        else
      return joueurs.get(rang_joueur_courant-1<0?joueurs.size()-1:rang_joueur_courant-1);
  }
    public boolean distribuer_cartes(ArrayList<Carte> cartes,Participant j){
      if(cartes.size()<=0)
        return true;
      j.fins_actions=false;
      j.setCartesEnMain(cartes);
      System.out.println("[SERVEUR][PARTIE] Envoi des cartes au joueur "+j.getRang());
      serveur.getClient(j.id()).sendEvent("reception_cartes",j.getCartesEnMain());
      decouvrir_voisins();
      return true;
    }
    public void distribuer_cartes(){
        nb_tours++;
        ArrayList<Carte> cartes = new gestionCarte().getAleaCartes(joueurs.size(),age);
        int index=0;
        for( Carte c : cartes){
            joueurs.get(index).ajouterCarteEnMain(c);
            index++;
            if(index == joueurs.size())
                index=0;
        }

        for(Participant j : joueurs){
          System.out.println("[SERVEUR][PARTIE] Envoi des cartes au joueur"+j.getRang());
           serveur.getClient(j.id()).sendEvent("reception_cartes",j.getCartesEnMain());
        }
      decouvrir_voisins();

    }

    public void donner_gold(){
        for(Participant j : joueurs){
            j.getMateriauxProduite().getListeMateriaux().set(0, j.getMateriauxProduite().getListeMateriaux().get(0).intValue() + 3);
            System.out.println("[SERVEUR][PARTIE] Ajout des 3 golds comme initialisation au joueur "+j.getNom());
            serveur.getClient(j.id()).sendEvent("ajout_gold",j.getMateriauxProduite());
        }
    }

    public void ajouterGold(Participant j, int a){
        j.getMateriauxProduite().getListeMateriaux().set(0, j.getMateriauxProduite().getListeMateriaux().get(0).intValue() + a);
        serveur.getClient(j.id()).sendEvent("ajout_gold",j.getMateriauxProduite());
    }
    public void commencer(){

      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> Démarrage de la partie <<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
      distribuer_merveille();
      distribuer_cartes();

      donner_gold();

      serveur.getBroadcastOperations().sendEvent("debut_partie");
      System.out.println("[SERVEUR][PARTIE] La partie commence !");

    }
}
