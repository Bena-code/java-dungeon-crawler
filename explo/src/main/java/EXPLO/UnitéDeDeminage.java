package src.main.java.EXPLO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UnitéDeDeminage extends Outil {

    public UnitéDeDeminage() {
        super("D", "Unité de Déminage", "Désamorce une mine à distance", 6);
    }

    public void interaction(Joueur j, BufferedReader buffer, PrintWriter out) {
        out.println("Vous avez trouvé une Unité de Déminage !");
        j.recupere();
    }

    public void activation(Joueur j, BufferedReader buffer, PrintWriter out) throws IOException {
        if (j.getEnergie() < this.getconsoenergie()) {
            out.println("Pas assez d'énergie !");
            return;
        }

        Direction dir = Direction.getDirectionHorizontaleVerticale(buffer, out);

        j.diminuerEnergie(this.getconsoenergie());

        Position posJoueur = j.getPosition();
        // On récupère la position visée
        Position posCible = posJoueur.getSalleSuivante(posJoueur, dir);

        if (posCible != null) {
            Salle salleCible = posJoueur.getPlateau().getSalle(posCible);
            
            // C'est ici que la magie opère :
            if (salleCible.contientMine()) {
                salleCible.setObjet(null); // ON SUPPRIME LA MINE !
                out.println("SUCCÈS ! La mine a été désamorcée.");
            } else {
                out.println("Rien à signaler : il n'y avait pas de mine ici.");
            }
        } else {
            out.println("Impossible, c'est un mur.");
        }
    }
}