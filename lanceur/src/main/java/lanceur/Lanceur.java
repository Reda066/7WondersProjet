package lanceur;
import joueur.Joueur;
import serveur.Serveur;


public class Lanceur {

  public static void main(String[] args) {
    final String url = "http://127.0.0.1:10101";

    Thread serveur = new Thread(new Runnable() {
      @Override
      public void run() {
        Serveur.main(null);
      }
    });

    Thread p1 = new Thread(new Runnable() {
      @Override
      public void run() {
        Joueur c = new Joueur("Reda",url);
        c.seConnecter();
      }
    });
    Thread p2 = new Thread(new Runnable() {
      @Override
      public void run() {
        Joueur c = new Joueur("Ciella",url);
        c.seConnecter();
      }
    });
    Thread p3 = new Thread(new Runnable() {
      @Override
      public void run() {
        Joueur c = new Joueur("Ines",url);
        c.seConnecter();
      }
    });
    Thread p4 = new Thread(new Runnable() {
      @Override
      public void run() {
        Joueur c = new Joueur("Joueur4",url);
        c.seConnecter();
      }
    });


    p1.start();
    p2.start();
    p3.start();
    p4.start();
    serveur.start();

  }
}