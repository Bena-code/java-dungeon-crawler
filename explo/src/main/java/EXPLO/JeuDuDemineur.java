package src.main.java.EXPLO;

import java.util.Scanner;

public class JeuDuDemineur {
    static final Scanner SCANNER = new Scanner(System.in);

    static int lireEntier(String invite) {
        while (true) {
            System.out.print(invite + " : ");
            try {
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("La valeur saisie n'est pas un entier ; Recommencez !");
            }
        }
    }

    public static void main(String[] args) {
      System.out.println("Bienvenu dans le JEU DU DEMINEUR !!!!!");
      System.out.println("Veuillez entre le nombre de ligne , de colonne et la proportion de vide souhaitez " );
      System.out.println("veuillez entre des nombre de lgne et de colonne impair");
      int nbrlig=lireEntier("nombre de ligne ");
      int nbrcol=lireEntier("nombre de colonne ");
      int nbrvide=lireEntier("nombre de salle vide ");
  
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
