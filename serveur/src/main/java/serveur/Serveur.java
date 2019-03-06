package serveur;


import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import jeu.*;


import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * attend une connexion, on envoie une question puis on attend une réponse, jusqu'à la découverte de la bonne réponse
 * le client s'identifie (som, niveau)
 */
public class Serveur {
  SocketIOServer serveur;

  /* TODO: 15/02/2019 [AMELIORATION] Pour une prochaine itération
      Créer une List de parties pour pouvoir simuler autant de parties qu'on veut 😈😈😈
  */
  Partie partie;

  public Serveur(Configuration config) {
    // creation du serveur
    serveur = new SocketIOServer(config);
    partie = new Partie(serveur);

    System.out.println("SERVEUR >  Création des écouteurs d'évenements");
    serveur.addConnectListener(new ConnectListener() {
      public void onConnect(SocketIOClient client) {
        System.out.println("SERVEUR >  Connexion d'un client");
        partie.ajouter_joueur(client.getSessionId());
        if(partie.getJoueurs().size()==4)
          partie.commencer();
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
    config.setPort(10101);


    Serveur serveur = new Serveur(config);
    serveur.démarrer();

  }
  private void démarrer() {
    serveur.start();
    System.out.println("SERVEUR >  Serveur démarré, en attente des clients pour se connecter.");
  }

}