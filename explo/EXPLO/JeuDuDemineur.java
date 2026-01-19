package EXPLO;

public class JeuDuDemineur {
    public static void main(String[] args) {
      System.out.println("Bienvenu dans le JEU DU DEMINEUR !!!!!");
      System.out.println("Veuillez entre le nombre de ligne , de colonne et la proportion de vide souhaitez " );
      System.out.println("veuillez entre des nombre de lgne et de colonne impair");
      int nbrlig=Lire.i("nombre de ligne ");
      int nbrcol=Lire.i("nombre de colonne ");
      int nbrvide=Lire.i("nombre de salle vide ");
  
      Categorie 
        grenades = new Categorie(4, CaisseGrenades.class),
        mines = new Categorie(9, Mine.class),
        energie = new Categorie(6, ReserveEnergie.class),
        scannerDirectionnel = new Categorie(2, ScannerUnidirectionnel.class),
        detecteurMine = new Categorie(2, DetecteurMines.class);
        Jeu jeu = new Jeu(nbrlig, nbrcol, nbrvide, grenades, mines, energie, scannerDirectionnel, detecteurMine);
      System.out.println(jeu);
  }        
} 
