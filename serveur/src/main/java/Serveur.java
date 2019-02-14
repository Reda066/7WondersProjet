package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import commun.Coup;
import commun.Identification;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * attend une connexion, on envoie une question puis on attend une r�ponse, jusqu'� la d�couverte de la bonne r�ponse
 * le client s'identifie (som, niveau)
 */
public class Serveur {

    SocketIOServer serveur;
    final Object attenteConnexion = new Object();
    private int �Trouv� = 42;
    Identification leClient ;

    ArrayList<Coup> coups = new ArrayList<>();


    public Serveur(Configuration config) {
        // creation du serveur
        serveur = new SocketIOServer(config);

        // Objet de synchro

        System.out.println("pr�paration du listener");

        // on accept une connexion
        serveur.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("connexion de "+socketIOClient.getRemoteAddress());

                // on ne s'arr�te plus ici
            }
        });

        // r�ception d'une identification
        serveur.addEventListener("identification", Identification.class, new DataListener<Identification>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identification identification, AckRequest ackRequest) throws Exception {
                System.out.println("Le client est "+identification.getNom());
                leClient = new Identification(identification.getNom(), identification.getNiveau());

                // on enchaine sur une question
                poserUneQuestion(socketIOClient);
            }
        });


            // on attend une r�ponse
        serveur.addEventListener("r�ponse", int.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer integer, AckRequest ackRequest) throws Exception {
                System.out.println("La r�ponse de  "+leClient.getNom()+" est "+integer);
                Coup coup = new Coup(integer, integer > �Trouv�);
                if (integer == �Trouv�) {
                    System.out.println("le client a trouv� ! ");
                    synchronized (attenteConnexion) {
                        attenteConnexion.notify();
                    }
                } else
                {
                    coups.add(coup);
                    System.out.println("le client doit encore cherch� ");
                    poserUneQuestion(socketIOClient, coup.isPlusGrand());
                }

            }
        });



    }


    private void d�marrer() {

        serveur.start();

        System.out.println("en attente de connexion");
        synchronized (attenteConnexion) {
            try {
                attenteConnexion.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("erreur dans l'attente");
            }
        }

        System.out.println("Une connexion est arriv�e, on arr�te");
        serveur.stop();

    }


    private void poserUneQuestion(SocketIOClient socketIOClient) {
        socketIOClient.sendEvent("question");
    }

    private void poserUneQuestion(SocketIOClient socketIOClient, boolean plusGrand) {
        socketIOClient.sendEvent("question", plusGrand, coups);
    }



    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);


        Serveur serveur = new Serveur(config);
        serveur.d�marrer();


        System.out.println("fin du main");

    }


}