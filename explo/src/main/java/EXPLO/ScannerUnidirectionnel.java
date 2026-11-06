package src.main.java.EXPLO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ScannerUnidirectionnel extends Outil {



    public ScannerUnidirectionnel() {
        super("1", "Scanner unidrectionnel", "Regarde les salles dans une direction.", 2);
    }

    // INTERACTION = QUAND ON MARCHE DESSUS (On le ramasse juste)
    public void interaction(Joueur j, BufferedReader buffer, PrintWriter out) {
        out.println("Vous avez trouvé un " + this.getNature() + " !");
        j.recupere(); 
    }

    // ACTIVATION = QUAND ON L'UTILISE DEPUIS L'INVENTAIRE (On paye l'énergie et on scanne)
    public void activation(Joueur j, BufferedReader buffer, PrintWriter out) throws IOException {
        // 1. Vérifier l'énergie
        if (j.getEnergie() < this.getconsoenergie()) {
            out.println("Pas assez d'énergie !");
            return;
        }

        // 2. Demander la direction (Code déplacé ici !)
        Direction dir = Direction.getDirectionHorizontaleVerticale(buffer, out);

        // 3. Consommer l'énergie
        j.diminuerEnergie(this.getconsoenergie());

        // 4. La logique du scan
        Position pos = j.getPosition();
        Plateau plateau = pos.getPlateau();
        int distance = 0;
        
        while (true) {
            pos = pos.getSalleSuivante(pos, dir);
            if (pos == null) break; // Mur du bord de map
            
            distance++;
            Salle salle = plateau.getSalle(pos);
            
            // Si on trouve quelque chose qui n'est pas vide
            if (!salle.isVide()) {
                out.println("Scanner : Objet détecté à " + distance + " cases !");
                return; // On s'arrête au premier objet trouvé
            }
        }
        out.println("Scanner : Rien détecté dans cette direction.");
    }
}