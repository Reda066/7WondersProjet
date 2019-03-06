package joueur;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;


public class Client {

  // Objet de synchro
  final Object attenteDéconnexion = new Object();
  Socket connexion;
  // On le crée ici donc le joueur ? plus besoin de cette class
  public Client(){
    // Joueur sans connexion
  };

  public String getConnexionId() {
    return connexion.id();
  }

  public Client(String urlServeur) {

    try {
      // il fallait rajouter ça enfaite
      IO.Options opts = new IO.Options();
      opts.forceNew = true;

      connexion = IO.socket(urlServeur, opts);


      System.out.println("[CLIENT " + connexion.id() + "] Je prépare mes écouteurs");

      connexion.on("connect", new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
          System.out.println("[CLIENT " + connexion.id() + "] Je suis connecte au serveur");
          //ping_du_client();
        }
      });

      connexion.on("disconnect", new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
          System.out.println("[CLIENT " + connexion.id() + "] Je me deconnecte");
          connexion.disconnect();
          connexion.close();

          synchronized (attenteDéconnexion) {
            attenteDéconnexion.notify();
          }
        }
      });
      /*connexion.on("reception_pong_du_serveur", new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
          System.out.println("[CLIENT " + connexion.id() + "] Je viens de recevoir le pong du serveur");

        }
      });*/


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

    System.out.println("[CLIENT " + connexion.id() + "] En attente de déconnexion");
    synchronized (attenteDéconnexion) {
      try {
        attenteDéconnexion.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
        System.err.println("[CLIENT " + connexion.id() + "] Erreur dans l'attente");
      }
    }
  }

}