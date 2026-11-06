package src.main.java.EXPLO;

/**
 * Permet de gérer une position dans un plateau donné.
 */
public class Position {
    private static char getNumChar(int num) {
        return (char) ('A' + num - 1);
    }

    public static Position get(int num, Plateau p) {
        return new Position(num, p);
    }

    private Plateau plateau;
    private boolean gagner = false;
    private int rang;

    public Plateau getPlateau() { return this.plateau; }
    public void setPlateau(Plateau plateau) { this.plateau = plateau; }

    public int getRang() { return this.rang; }
    public void setRang(int rang) { this.rang = rang; }

    // --- CORRECTION DU BUG DE BORD DE CARTE ---
    public boolean isValide() {
        // CORRECTION IMPORTANTE : on met '<' et non '<='
        // Car si il y a 25 salles, les rangs vont de 0 à 24. Le rang 25 est hors limites.
        return this.getRang() >= 0 && this.getRang() < this.getPlateau().getNbSalles();
    }
    // ------------------------------------------

    public boolean sallevalide(){
        // Même correction ici par sécurité
        return this.getRang() >= 0 && this.getRang() < this.getPlateau().getNbSalles();
    }

    public int getLig() {
        return this.getRang() / this.getPlateau().getNbCol() + 1;
    }

    public char getCharLig() { return Position.getNumChar(this.getLig()); }

    public int getCol() {
        return this.getRang() % this.getPlateau().getNbCol() + 1;
    }

    public char getCharCol() { return Position.getNumChar(this.getCol()); }

    public Position(int rang, Plateau p) {
        this.setRang(rang);
        this.setPlateau(p);
    }

    public Position(int lig, int col, Plateau p) {
        this((lig - 1) * p.getNbCol() + col - 1, p);
    }

    public boolean getGagner(){ return gagner; }
    public void setGagner(boolean b){ gagner = b; }

    public Position getSuivante(Direction p) {
        return new Position(this.getLig() + p.getdLig(), this.getCol() + p.getdCol(), this.getPlateau());
    }

    public Position getSalleSuivante(Position p, Direction d) {
        int lig = p.getLig();
        int col = p.getCol();

        switch (d.quelDir()) {
            case "d": col++; break;
            case "g": col--; break;
            case "h": lig--; break;
            case "b": lig++; break;
        }

        // Vérification des limites
        if (lig < 1 || lig > this.plateau.getNbLig() || col < 1 || col > this.plateau.getNbCol()) {
            return null; // C'est le bord !
        }

        return new Position(lig, col, this.plateau);
    }

    public Direction getDirection(Position positionCible) {
        if(positionCible == null) return null; // Sécurité anti-crash
        
        Position positionJoueur = this.getPlateau().getJoueur().getPosition();
        int dLig = positionCible.getLig() - positionJoueur.getLig();
        int dCol = positionCible.getCol() - positionJoueur.getCol();

        if (dLig == 0) {
            if (dCol > 0) return Direction.DROITE;
            if (dCol < 0) return Direction.GAUCHE;
        } else if (dLig > 0) {
            if (dCol == 0) return Direction.BAS; // CORRECTION : lig > 0 veut dire on descend (BAS)
            if (dCol > 0) return Direction.BAS_DROITE;
            if (dCol < 0) return Direction.BAS_GAUCHE;
        } else if (dLig < 0) {
            if (dCol == 0) return Direction.HAUT; // CORRECTION : lig < 0 veut dire on monte (HAUT)
            if (dCol > 0) return Direction.HAUT_DROITE;
            if (dCol < 0) return Direction.HAUT_GAUCHE;
        }
        return null;
    }

    public Position getSuivante() {
        return new Position(this.getRang() + 1, this.getPlateau());
    }
    
    // HashCode et Equals (Importants pour que Java reconnaisse les positions identiques)
    public int hashCode() {
        return 53 * 5 + this.rang;
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Position autre = (Position) o;
        return this.getRang() == autre.getRang();
    }

    public String toString() {
        return "(" + this.getLig() + "," + this.getCol() + ")";
    }

    public Salle getSalle() {
        return plateau.getSalle(this);
    }
}