package client;

import commun.Coup;
import commun.Identification;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Client {

    Identification moi = new Identification("Michel B", 42);

    Socket connexion;
    int propositionCourante = 50;

    // Objet de synchro
    final Object attenteD�connexion = new Object();

    public Client(String urlServeur) {

        try {
            connexion = IO.socket(urlServeur);

            System.out.println("on s'abonne � la connection / d�connection ");;

            connexion.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(" on est connect� ! et on s'identifie ");

                    // on s'identifie
                    JSONObject id = new JSONObject(moi);
                    connexion.emit("identification", id);

                }
            });

            connexion.on("disconnect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(" !! on est d�connect� !! ");
                    connexion.disconnect();
                    connexion.close();

                    synchronized (attenteD�connexion) {
                        attenteD�connexion.notify();
                    }
                }
            });


            // on recoit une question
            connexion.on("question", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    int pas = 1;
                    System.out.println("on a re�u une question avec "+objects.length+" param�tre(s) ");
                    if (objects.length > 0 ) {
                        System.out.println("la r�ponse pr�c�dente �tait : "+objects[0]);

                        boolean plusGrand = (Boolean)objects[0];
                        // false, c'est plus petit... !! erreur... dans les commit d'avant

                            if (plusGrand)  pas=-1;
                            else pas=+1;


                        System.out.println(objects[1]);

                        // conversion local en ArrayList, juste pour montrer
                        JSONArray tab = (JSONArray) objects[1];
                        ArrayList<Coup> coups = new ArrayList<Coup>();
                        for(int i = 0; i < tab.length(); i++) {

                            try {
                                coups.add(new Coup(tab.getJSONObject(i).getInt("coup"), tab.getJSONObject(i).getBoolean("plusGrand")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        System.out.println(coups);

                    }
                    propositionCourante += pas;
                    System.out.println("on r�pond "+propositionCourante);
                    connexion.emit("r�ponse",  propositionCourante);
                }
            });



        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private void seConnecter() {
        // on se connecte
        connexion.connect();

        System.out.println("en attente de d�connexion");
        synchronized (attenteD�connexion) {
            try {
                attenteD�connexion.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("erreur dans l'attente");
            }
        }
    }

    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Client client = new Client("http://127.0.0.1:10101");
        client.seConnecter();



        System.out.println("fin du main pour le client");

    }

}