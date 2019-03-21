package joueur;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import jeu.Carte;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import java.util.concurrent.TimeUnit;


public class Client {


  final Object attenteDeconnexion = new Object();
  Socket connexion;

  public Client(String urlServeur) {

    try {
      IO.Options opts = new IO.Options();
      opts.forceNew = true;

      connexion = IO.socket(urlServeur, opts);


      System.out.println("[CLIENT " + connexion.id() + "] Je prépare mes écouteurs");

      connexion.on("connect", new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
        }
      });

      connexion.on("disconnect", new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
          System.out.println("[CLIENT " + connexion.id() + "] Je me suis fait déconnecté");
          connexion.disconnect();
          connexion.close();

          synchronized (attenteDeconnexion) {
            attenteDeconnexion.notify();
          }
        }
      });

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

  }


}