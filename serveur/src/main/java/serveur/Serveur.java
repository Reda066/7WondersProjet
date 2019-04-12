package serveur;

import joueur.Joueur;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import jeu.*;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Serveur {
  SocketIOServer serveur;
  Partie partie;

  public Serveur(Configuration config) {
    serveur = new SocketIOServer(config);
    partie = new Partie(serveur);

    serveur.addEventListener("nom_client", String.class, new DataListener<String>() {
      public void onData(SocketIOClient client, String nom, AckRequest ackRequest) throws Exception {

        synchronized (Serveur.this) {
          Participant p = new Participant(nom, client.getSessionId(), partie.getJoueurs().size());
          partie.ajouter_joueur(p);
        }
        if(partie.getJoueurs().size()>=3 && partie.getJoueurs().size()<=7)
          partie.commencer();
      }
    });
    System.out.println("[SERVEUR] Initialisation et création des écouteurs d'évenements");
    serveur.addConnectListener(new ConnectListener() {
      public void onConnect(SocketIOClient client) {
        System.out.println("[SERVEUR] Connexion d'un nouveau client.");
      }
    });
  }

  public static final void main(String[] args) {
    try {
      System.setOut(new PrintStream(System.out, true, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    Configuration config = new Configuration();
    config.setHostname("127.0.0.1");
    config.setPort(9000);


    Serveur serveur = new Serveur(config);
    serveur.demarrer();

  }
  private void demarrer() {
    serveur.start();
    System.out.println("[SERVEUR] Le serveur est démarré.");
  }

}