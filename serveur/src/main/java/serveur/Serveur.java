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
 * attend une connexion, on envoie une question puis on attend une reponse, jusqu'e la decouverte de la bonne reponse
 * le client s'identifie (som, niveau)
 */
public class Serveur {

    SocketIOServer serveur;
    final Object attenteConnexion = new Object();
    private int aTrouve = 42;
    Identification leClient ;

    ArrayList<Coup> coups = new ArrayList<>();


    public Serveur(Configuration config) {
        // creation du serveur
        serveur = new SocketIOServer(config);

        System.out.println("preparation du listener");

        //Connexion
        serveur.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("connexion de "+socketIOClient.getRemoteAddress());

                // on ne s'arrete plus ici
            }
        });

        // reception d'une identification
        serveur.addEventListener("identification", Identification.class, new DataListener<Identification>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identification identification, AckRequest ackRequest) throws Exception {
                System.out.println("Le client est "+identification.getNom());
                leClient = new Identification(identification.getNom(), identification.getNiveau());

                // on enchaine sur une question
                poserUneQuestion(socketIOClient);
            }
        });


        // on attend une reponse
        serveur.addEventListener("reponse", int.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer integer, AckRequest ackRequest) throws Exception {
                System.out.println("La reponse de  "+leClient.getNom()+" est "+integer);
                Coup coup = new Coup(integer, integer > aTrouve);
                if (integer == aTrouve) {
                    System.out.println("le client a trouve ! ");
                    synchronized (attenteConnexion) {
                        attenteConnexion.notify();
                    }
                } else
                {
                    coups.add(coup);
                    System.out.println("le client doit encore cherche ");
                    poserUneQuestion(socketIOClient, coup.isPlusGrand());
                }

            }
        });



    }


    private void demarrer() {

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

        System.out.println("Une connexion est arrivee, on arrete");
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
        serveur.demarrer();


        System.out.println("fin du main");

    }


}