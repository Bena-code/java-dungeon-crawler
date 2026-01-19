package EXPLO;
import java.util.ArrayList;

public class Plateau {
    private static final String BORD = "░";
    public static boolean VISIBLE = false; // mode déboggage : si true, toutes les salles doivent afficher leur contenu
    private Jeu jeu;
    private boolean visible;
    private Salle[][] grille;
    private Joueur joueur;
    
    

    //methode pour voir les parametre de jeu et les donner au plateau
    public Jeu getJeu() {
        return this.jeu;
    }
    //methode pour retourner la visibilite de la salle initialement a false
    public boolean isVisible() {
        return this.visible || Plateau.VISIBLE;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    //methode pour voir le nombre de salle , voir une salle a une position donner et une salle avec @param lig et col 
    public int getNbSalles() {
        return this.getNbCol() * this.getNbLig();
    }
    public int getnb(){
        return this.getNbCol()-1*this.getNbLig();
    }
    public Salle getSalle(Position p) {
        if (p.isValide())
            return this.grille[p.getLig() - 1][p.getCol() - 1];
        else
            return null;
    }
    public Salle getSalle(int lig, int col) {
        return this.getSalle(new Position(lig, col, this));
    }

    //methode pour voir le joueur ,changer des parametre du joueur et voir la postion du joueur 
    public Joueur getJoueur() {
        return this.joueur;
    }
   
    public Position getPosJoueur() {
        return this.getJoueur().getPosition();
    }

        
    //methode pour voir le nombre de ligne et colonne du plateau de jeu 
    public int getNbLig() {
        return this.grille.length;
    }
    public int getNbCol() {
        return this.grille[0].length;
    }

    // methode :
    /**
     * Place une salle à une position donnée (Les positions en Lig et Col commencent à 1)
     * 
     * @param s Une salle
     */
    public void placeSalle(Salle s) {
        Position p = s.getPosition();
        if (!p.isValide())
            throw new RuntimeException("Affectation de la salle à une position non valide");
        this.grille[p.getLig() - 1][p.getCol() - 1] = s;
    }
        /**
     * Crée une salle contenant un objet référencé par o (vide si o = null) et
     * l'affecte au plateau
     * 
     * @param p la position dans le plateau
     * @param o l'objet que la salle doit contenir
     */
    public void setNouvelleSalle(Position p, Objet o) {
        placeSalle(new Salle(p, this,o));
    }
    public void setNouvelleSalle(Position p) {
        setNouvelleSalle(p, null);
    }
  
  
    public Plateau(int nbLig, int nbCol, int proportionVides, Categorie[] listeCategories, Jeu jeu) {
        // Crée un joueur et initialise le plateau selon les spécifications du jeu (Gros boulot)
        this.jeu = jeu;
        this.visible = false;
        this.grille = new Salle[nbLig][nbCol];
    
        // Bordure du plateau
        BORD.repeat(nbCol + 2);
    
        // Remplissage du plateau avec des salles
        for (int lig = 0; lig < this.getNbLig(); lig++) {
            BORD.repeat(0);
            for (int col = 0; col < this.getNbCol(); col++) {
                this.grille[lig][col] = new Salle(new Position(lig + 1, col + 1, this), this);
            }
            BORD.repeat(0);
        }
    
        BORD.repeat(nbCol + 2);
    
        
        // Création du joueur au centre du plateau
        Position positionInitiale = new Position((nbLig / 2)+1 , (nbCol / 2)+1 , this);
        this.joueur = new Joueur(positionInitiale);
        placeSalle(new Salle(positionInitiale, this));
    
        int nbSalles = this.getNbSalles();
        int nbVides = (proportionVides * nbSalles) / 100;
        ArrayList<Position> positions = new ArrayList<>();
    
        // Ajout de toutes les positions possibles
        for (int lig = 1; lig <= this.getNbLig(); lig++) {
            for (int col = 1; col <= this.getNbCol(); col++) {
                positions.add(new Position(lig, col, this));
            }
        }
    
        // Ajout des salles vides
        for (int i = 0; i < nbVides; i++) {
            int index = (int) (Math.random() * positions.size());
            Position positionVide = positions.remove(index);
            this.placeSalle(new Salle(positionVide,this));
        }
        
        for (Position p : positions) {
            Categorie categorieAleatoire = listeCategories[(int) (Math.random() * listeCategories.length)];
            Objet objet = categorieAleatoire.getNouveau();
        
            // Vérifier le type de catégorie directement (en supposant des classes de catégorie spécifiques)
           
        
            if (objet != null) {
                this.setNouvelleSalle(p, objet);
            }
        }

        this.grille[1][1].getPosition().setGagner(true);
    }
   
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int ajustement = 0; // Ajustement pour la position du joueur
        // Vérifier si le joueur est arrivé à la sortie
        Position sortie = new Position(2, this);

        if (joueur.getPosition().equals(sortie)) {
        builder.append("\n*** Félicitations ! Vous avez trouvé la sortie et gagné la partie ! ***\n");
    }
        // Bordure supérieure du plateau
        builder.append(BORD.repeat(ajustement + getNbCol() + 2)).append("\n"); // Ajout du saut de ligne après la bordure supérieure
    
        // Itération sur les lignes du plateau
        for (int ligne = 0; ligne < getNbLig(); ligne++) {
            builder.append(BORD).append("");
    
            // Itération sur les colonnes du plateau
            for (int col = 0; col < getNbCol(); col++) {
                Salle salle = grille[ligne][col];
                
                // Vérifier si la cellule actuelle est la position du joueur
                if (ligne+1 == joueur.getPosition().getLig() && col+1 == joueur.getPosition().getCol()) {
                    builder.append(joueur.toString()); // Remplacer le contenu de la salle par l'indicateur du joueur
                } else {
                    // Vérifier si la salle a été visitée par le joueur
                   Direction directionJoueur = joueur.getPosition().getDirection(getPosJoueur().getSuivante()); // Obtenir la direction du joueur
                    // Vérifier si l'accès de la salle contient la direction du joueur
                    if (salle.getAcces() != null && salle.getAcces().contains(directionJoueur)) {
                        builder.append(salle.toString(true)); // Afficher la salle comme visible
                        
                      } else {
                        builder.append(salle.toString(false)); // Afficher la salle comme invisible
                      }
                }
            }

            builder.append(BORD).append("").append("\n"); // Ajout du saut de ligne après chaque ligne
        }
    
        // Bordure inférieure du plateau
        builder.append(BORD.repeat(getNbCol() + ajustement + 2)).append("\n");
    
        // Informations sur le joueur
        builder.append("\n========================================\n");
        builder.append(" JOUEUR : ").append(joueur.getNom()).append("\n");
        builder.append(" POSITION : ").append(joueur.getPosition()).append("\n");
        builder.append(" ----------------------------------------\n");
        builder.append(" ENERGIE : ").append(joueur.getEnergie()).append(" / 240\n");
        builder.append(" GRENADES : ").append(joueur.getNombreGrenades()).append("\n");
        builder.append(" OUTILS : ").append(joueur.getTaille()).append("\n");
        builder.append("========================================\n");

        if (joueur.getPosition().equals(sortie)) {
        builder.append("\n*** Félicitations ! Vous avez trouvé la sortie et gagné la partie ! ***\n");
    }
        // Retourne la chaîne formatée
        return builder.toString();
    }

}
