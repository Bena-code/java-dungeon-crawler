package src.main.java.EXPLO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Excavatrice extends Outil {

    public Excavatrice() {
        super("E", "Excavatrice", "Détruit un mur sans grenade", 5);
    }

    public void interaction(Joueur j, BufferedReader buffer, PrintWriter out) {
        out.println("Vous avez trouvé une Excavatrice !");
        j.recupere();
    }

    public void activation(Joueur j, BufferedReader buffer, PrintWriter out) throws IOException {
        if (j.getEnergie() < this.getconsoenergie()) {
            out.println("Pas assez d'énergie !");
            return;
        }

        Direction dir = Direction.getDirectionHorizontaleVerticale(buffer, out);

        Position pos = j.getPosition();
        Salle salle = pos.getPlateau().getSalle(pos);

        // Si le mur est déjà détruit
        if (salle.getAcces().contains(dir)) {
            out.println("Il n'y a pas de mur ici !");
        } else {
            j.diminuerEnergie(this.getconsoenergie());
            salle.setAcces(dir);
            out.println("Le mur a été percé avec l'excavatrice !");
        }
    }
}