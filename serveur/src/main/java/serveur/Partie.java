package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import jeu.Age;
import jeu.Carte;
import jeu.Face;
import jeu.Merveille;
import jeu.gestion.*;
import joueur.Joueur;

import java.util.*;

public class Partie {
    private SocketIOServer serveur;
    private Age age = Age.I;
    private int nb_tours = 0;
    private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
    private ArrayList<Carte> defausse = new ArrayList<Carte>();

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    private ArrayList<Carte> deck;

    public Partie(SocketIOServer s) {
        serveur = s;

// Ajout d'un événement poser une carte
        serveur.addEventListener("poserCarte", String.class, new DataListener<String>() {
            public void onData(SocketIOClient client, String nom_carte, AckRequest ackRequest) {


                System.out.println("SERVEUR && PARTIE >  " + getJoueurById(client.getSessionId()).getNom() + " a posé la carte '" + nom_carte + "'");

                for (int i = 0; i < joueurs.size(); i++) {
                    if (joueurs.get(i).id().equals(client.getSessionId())) {
                        for (Carte c : joueurs.get(i).getCartesEnMain())
                            if (c.getNom().equals(nom_carte)) {
                                joueurs.get(i).poserUneCarte(c);
                                return;
                            }
                    }
                }
            }
        });
// Ajout d'un événement defausser carte
        serveur.addEventListener("defausserCarte", String.class, new DataListener<String>() {
            public void onData(SocketIOClient client, String nom_carte, AckRequest ackRequest) {

                ArrayList<Integer> repet = getJoueurById(client.getSessionId()).getMateriauxProduite().getListeMateriaux();
                System.out.println("SERVEUR && PARTIE >  " + getJoueurById(client.getSessionId()).getNom() + " a défaussé la carte <" + nom_carte
                        + "> Il a maintenant "
                        + repet.get(0) + " golds, "
                        + repet.get(1) + " pierres, "
                        + repet.get(2) + " bois, "
                        + repet.get(3) + " argile, "
                        + repet.get(4) + " minerai, "
                        + repet.get(5) + " textiles, "
                        + repet.get(6) + " papyrus, "
                        + repet.get(7) + " verres. ");

                for (int i = 0; i < joueurs.size(); i++) {
                    if (joueurs.get(i).id().equals(client.getSessionId())) {
                        for (Carte c : joueurs.get(i).getCartesEnMain())
                            if (c.getNom().equals(nom_carte)) {
                                defausse.add(c);
                                joueurs.get(i).getCartesEnMain().remove(c);
                                joueurs.get(i).ajouterOr(3);
                                return;
                            }
                    }
                }
            }
        });

// Ajout d'un événement fin d'action
        serveur.addEventListener("finAction", boolean.class, new DataListener<Boolean>() {
            public void onData(SocketIOClient client, Boolean c, AckRequest ackRequest) throws Exception {
                System.out.println("SERVEUR && PARTIE >  " + getJoueurById(client.getSessionId()).getNom() + " a terminé son tour");
                for (Joueur j : joueurs) {
                    if (j.id().equals(client.getSessionId())) {
                        j.fins_actions = true;
                        break;
                    }
                }
                //Déclaration de la fin de l age si le nombre de carte en main = 0
                if (finAge()) {
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println("SERVEUR && PARTIE >  FIN DE L'AGE " + age);
                    afficherScores();

                    // le jeu continue si le nb de carte en main != 0
                } else if (finActions()) {
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> FIN DU TOUR N°" + nb_tours + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    afficherScoresParTour();
                    carteEnMain();
                    System.out.println("-----------------------------------------------------------------------");

                    nb_tours++;
                    ArrayList<Carte> temp = joueurs.get(joueurs.size() - 1).getCartesEnMain();
                    for (int i = joueurs.size() - 1; i > 0; i--) {
                        distribuerCartes(joueurs.get(i - 1).getCartesEnMain(), joueurs.get(i));
                    }
                    distribuerCartes(temp, joueurs.get(0));
                }
            }
        });
    }


    // Affichage du score à la fin de l'age
    public void afficherScores() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("SERVEUR && PARTIE >  LES SCORES SONT : ");
        int max_index = 0;
        for (int i = 0; i < joueurs.size(); i++) {
            Joueur j = joueurs.get(i);
            if (j.score() >= joueurs.get(max_index).score())
                max_index = i;
            System.out.println(j.getNom() + " : " + j.score() + " points et de " + j.getMateriauxProduite().getOr() + " or");
        }
        System.out.println("Le joueur gagnant est  " + joueurs.get(max_index).getNom() + " avec " + joueurs.get(max_index).score() + " Points");

    }

    //Affichage du score à la fin de chaque tour
    public void afficherScoresParTour() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("SERVEUR && PARTIE >  LES SCORES SONT : ");
        System.out.println("-----------------------------------------------------------------------");
        int max_index = 0;
        for (int i = 0; i < joueurs.size(); i++) {
            Joueur j = joueurs.get(i);
            if (j.score() >= joueurs.get(max_index).score())
                max_index = i;
            System.out.println(j.getNom() + " : " + j.score() + " points et de " + j.getMateriauxProduite().getOr() + " or");
        }

    }

    // se situer à la fin de l'age lorsqu'il n'y a plus de carte en main
    public boolean finAge() {
        for (Joueur j : joueurs) {
            if (j.getCartesEnMain().size() != 0 || !j.fins_actions)
                return false;
        }
        return true;
    }

    // se situer si les joueurs ont fini leurs actions
    public boolean finActions() {
        for (Joueur j : joueurs) {
            if (!j.fins_actions)
                return false;
        }
        return true;
    }

    // ajout de joueur en tant que joueur
    public void ajouterJoueur(Joueur p) {
        System.out.println("PARTIE >  " + p.getNom() + " a été ajouté a la partie");
        joueurs.add(p);
    }

    public Joueur getJoueurById(UUID id) {
        for (Joueur j : joueurs)
            if (j.id().equals(id))
                return j;

        return null;
    }

    public void decouvrir_voisins(){
        for(Joueur p : joueurs){
            serveur.getClient(p.id()).sendEvent("decouvrir_voisins",p.voisinDroite(joueurs),p.voisinGauche(joueurs));
        }
    }


    public void distribuerMerveille() {
        ArrayList<Merveille> merveilles = new gestionMerveille().getRandomMerveille(joueurs.size());
        Face face;
        if (Math.random() * 1 == 0) face = Face.A;
        else face = Face.B;
        for (int i = 0; i < joueurs.size(); i++) {
            joueurs.get(i).setMerveille(merveilles.get(i));
            serveur.getClient(joueurs.get(i).id()).sendEvent("receptionMerveille", merveilles.get(i).getNom(), merveilles.get(i).getFace());
            System.out.println("SERVEUR && PARTIE > Le joueur " + joueurs.get(i).getNom() + " reçoit la merveille '" + merveilles.get(i).getNom() + "' avec pour face " + merveilles.get(i).cote);

        }
    }

      public boolean distribuerCartes(ArrayList<Carte> cartes, Joueur j) {
        if (cartes.size() <= 0)
            return true;
        j.fins_actions = false;
        j.setCartesEnMain(cartes);

        System.out.println("SERVEUR && PARTIE >  Envoi des cartes au joueur " + j.getNom());
        serveur.getClient(j.id()).sendEvent("receptionCarte", j.getCartesEnMain());

        decouvrir_voisins();
        return true;

    }

    public void distribuerCartes() {
        nb_tours++;
        ArrayList<Carte> cartes = new gestionCarte().getRandomCarte(joueurs.size(), age);
        int index = 0;
        for (Carte c : cartes) {
            joueurs.get(index).ajouterCarteEnMain(c);
            index++;
            if (index == joueurs.size())
                index = 0;
        }

        for (Joueur j : joueurs) {
            System.out.println("SERVEUR && PARTIE > Envoi des cartes au joueur " + j.getNom());

            serveur.getClient(j.id()).sendEvent("receptionCarte", j.getCartesEnMain());

        }


    }




    public void donnerOr() {
        for (Joueur j : joueurs) {
            j.getMateriauxProduite().getListeMateriaux().set(0, j.getMateriauxProduite().getListeMateriaux().get(0).intValue() + 3);
            System.out.println("SERVEUR && PARTIE >  Le joueur " + j.getNom() + " reçoit 3 golds");
            serveur.getClient(j.id()).sendEvent("ajoutOr", j.getMateriauxProduite());
        }
    }

    public void carteEnMain() {
        System.out.println("SERVEUR && PARTIE >  Distribution des cartes aux joueurs");
        for (Joueur p : joueurs) {
            System.out.println("SERVEUR && PARTIE >  " + p.getNom() + " obtiendra les cartes suivantes :  " + p.getCartesEnMain());
            serveur.getClient(p.id()).sendEvent("reception_cartes", p.getCartesEnMain(), p.voisinDroite(joueurs), p.voisinGauche(joueurs));
        }
    }

    public void commencer() {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> Démarrage de la partie <<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        distribuerMerveille();
        distribuerCartes();
        carteEnMain();
        donnerOr();
        serveur.getBroadcastOperations().sendEvent("debut_partie");
        System.out.println("SERVEUR && PARTIE >  La partie commence !");
        System.out.println("________________________ DEBUT DU TOUR N°1 ________________________");

    }
}
