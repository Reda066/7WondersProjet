package lanceur;
import joueur.Joueur;
import serveur.Serveur;

public class Lanceur {

  public static void main(String[] args) {
    final String url = "http://127.0.0.1:9000";

    Thread serveur = new Thread(new Runnable() {
      @Override
      public void run() {
        Serveur.main(null);
      }
    });

    Thread c1 = new Thread(new Runnable() {
      @Override
      public void run() {
        Joueur c = new Joueur("Ciella",url);
        c.seConnecter();
      }
    });
    Thread c2 = new Thread(new Runnable() {
      @Override
      public void run() {
        Joueur c = new Joueur("Ines",url);
        c.seConnecter();
      }
    });
    Thread c3 = new Thread(new Runnable() {
      @Override
      public void run() {
        Joueur c = new Joueur("Reda",url);
        c.seConnecter();
      }
    });

    c1.start();
    c2.start();
    c3.start();


    serveur.start();

  }
}