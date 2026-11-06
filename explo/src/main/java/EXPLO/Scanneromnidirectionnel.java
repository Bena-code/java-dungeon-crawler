package src.main.java.EXPLO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Scanneromnidirectionnel extends Outil {
   
   

    public Scanneromnidirectionnel() {
        super("O", "Scanneromnidirectionnel", "Permet de scanner les salles dans toutes les directions..", 5);
       
    }

 
    public void interaction(Joueur j, BufferedReader buffer, PrintWriter out) {
        out.println("Vous avez trouvé un scanner omnidirectionnel !");
        j.recupere();
    }
    
    public void activation(Joueur j, BufferedReader buffer, PrintWriter out) throws IOException {
        if (j.getEnergie() < this.getconsoenergie()) {
            out.println("Vous n'avez pas assez d'énergie pour utiliser cet outil.");
            return;
        }

        j.diminuerEnergie(this.getconsoenergie());
        // Informer le joueur de la fonctionnalité du scanner et des directions disponibles
        out.println("Le scanner omnidirectionnel permet de scanner les salles dans toutes les directions.");
        out.println("Directions disponibles :  'haut','bas','gauche','droite'");

        // Demander au joueur de choisir une direction
        Direction direction = Direction.getDirectionHorizontaleVerticale(buffer, out);

        // Scanner les salles jusqu'à ce que le joueur souhaite arrêter
        boolean continuer = true;
        while (continuer) {
            Position positionJoueur = j.getPosition();
            Plateau plateau = positionJoueur.getPlateau();

            // Scanner les salles dans la direction choisie
            Position positionVirtuelle = positionJoueur;
            while ((positionVirtuelle = positionVirtuelle.getSalleSuivante(positionVirtuelle, direction)) != null) {
                Salle salle = plateau.getSalle(positionVirtuelle);
                out.println("Salle scannée : " + salle.getContenu());
            }

            // Demander au joueur s'il souhaite continuer à scanner
            continuer = ServeurJeu.lireTexte("Voulez-vous continuer à scanner ? (Oui/Non)", buffer, out)
                    .equalsIgnoreCase("Oui");
        }

        out.println("Le scan est terminé.");
    }

}
    
