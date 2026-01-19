package EXPLO;

public class DetecteurMines extends Outil {

    public DetecteurMines() {
        super("&", "Détecteur Mines", "Détecte les mines adjacentes", 3);
    }

    // QUAND ON LE RAMASSE
    public void interaction(Joueur j) {
        System.out.println("Vous avez trouvé un Détecteur de Mines !");
        j.recupere();
    }

    // QUAND ON L'UTILISE
    public void activation(Joueur j) {
        if (j.getEnergie() < this.getconsoenergie()) {
            System.out.println("Pas assez d'énergie !");
            return;
        }
        
        j.diminuerEnergie(this.getconsoenergie());

        Position posJoueur = j.getPosition();
        Plateau plateau = posJoueur.getPlateau();
        Direction[] directions = { Direction.DROITE, Direction.GAUCHE, Direction.HAUT, Direction.BAS };
        
        int mines = 0;
        for (Direction dir : directions) {
            Position posVoisine = posJoueur.getSalleSuivante(posJoueur, dir);
            if (posVoisine != null && plateau.getSalle(posVoisine).contientMine()) {
                mines++;
            }
        }

        if (mines > 0) {
            System.out.println("DANGER ! " + mines + " mine(s) autour de vous !");
        } else {
            System.out.println("Aucune mine détectée autour.");
        }
    }
}