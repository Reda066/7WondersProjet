package jeu;

import java.util.ArrayList;
import java.util.UUID;

public class Participant {


  public ArrayList<Carte> cartesEnMain = new ArrayList<Carte>(){};

  protected String nom = "Inconnu";

  public Merveille merveille;

  protected Materiaux materiauxProduite;

  protected int rang;

  protected UUID id;

  protected Participant voisinDroite;
  protected Participant voisinGauche;

  public boolean fins_actions=false;

  public int getRang() {
    return rang;
  }

  public Participant (String nom , UUID id , int r){
    this.nom=nom;
    rang =r;
    this.id=id;
    materiauxProduite = new Materiaux(0,0,0,0,0,0,0,0);
  }

  public Materiaux getMateriauxProduite() {
    return materiauxProduite;
  }
  public void setMateriauxProduite(Materiaux m ){ this.materiauxProduite = m;}


  public void setMerveille(Merveille merveille) {
    this.merveille = merveille;
  }
  public Merveille getMerveille(){return this.merveille;}

  public void poserUneCarte(Carte c){
    for (int i = 0; i < cartesEnMain.size(); i++) {
      if(cartesEnMain.get(i).equals(c)){
        cartesEnMain.remove(i);
      }
    }
    merveille.poserUneCarte(c);
  }

  public ArrayList<Carte> getCartesEnMain() {
    return cartesEnMain;
  }

  public void ajouterCarteEnMain(Carte c) {
    this.cartesEnMain.add(c);
  }

  public String getNom() {
    return nom;
  }


  public UUID id() {
    return this.id;
  }

  public void setCartesEnMain(ArrayList<Carte> cartes){
    cartesEnMain = cartes;
  }

  public void ajouterOr(int nb){
    getMateriauxProduite().getListeMateriaux().set(0, getMateriauxProduite().getListeMateriaux().get(0).intValue() + nb);


  }

  public int score(){
    int score =0;
    ArrayList<Carte> cartes = merveille.getCartesPose();
    for (int i = 0; i <cartes.size(); i++){
      score += cartes.get(i).getPointsVictoire();
    }

    return score;
  }
}