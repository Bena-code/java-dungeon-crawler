package EXPLO;
import java.util.ArrayList;

public class Salle {
    private boolean visible;
    private Position position;
    private Plateau plateau;
    private Objet objet;
    private ArrayList<Direction> acces;

    //methode rend true si la salle est visible
    public boolean isVisible() {
        return this.visible || this.getPlateau().isVisible();
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    //donne la position de la salle et la modifie
    public Position getPosition() {
        return this.position;
    }
    private void setPosition(Position position) {
        this.position = position;
    }
    //rend le plateau de jeu
    public Plateau getPlateau() {
        return this.plateau;
    }
    private void setPlateau(Plateau p) {
        this.plateau = p;
    }
    public void setObjet(Objet objet) {
        this.objet = objet;
    }
    public Objet getObjet() {
        return this.objet;
    }

    /**
     * Crée un accès vers la salle contiguë dans la direction donnée… si l'accès
     * n'existe pas déjà et si la salle contiguë existe
     * L'accès inverse est aussi ajouté (direction inverse ajoutée à la liste
     * d'accès de la salle contiguë)
     * 
     * @param d La direction : haut, bas, gauche, droite
     */
    public void setAcces(Direction d) {
        Salle voisine = getVoisine(d);
        if (voisine != null && !acces.contains(d)) {
            acces.add(d);
            voisine.acces.add(d.getInverse());
        }
    }
    /**
     * Récupérer la liste des directions accessibles depuis cette salle.
     *
     * @return Une ArrayList contenant les directions accessibles.
     */
    public ArrayList<Direction> getAcces() {
        return this.acces;
    }
    /*
     * initialise l'arrayliste pour l'acces au salle
     */
    private void initAcces() {
        this.acces = new ArrayList<Direction>(); // Création d'une liste d'accès vide
    }
     /**
     * Restitue la salle contiguë dans la direction donnée… ou null si on sort du
     * plateau
     */
    public Salle getVoisine(Direction d) {
        Position posSuivante = this.getPosition().getSuivante(d);
        if (posSuivante.isValide()) {
            return this.getPlateau().getSalle(posSuivante);
        } else {
            return null;
        }
    }
    // verifier si i y a un objet dans la salle
    public Objet getContenu(){
        return this.objet;
    }
    // return true si aucun objet est dans la salle
    public boolean isVide() {
        return this.getContenu() == null;
    }
    // verifier si il y a une mine dans la salle
    public boolean contientMine(){
        return getContenu().getNature().equals("Mine");
    }
    // verifie si la salle contient un objet si non declanche l'interraction entre le joueur et l'objet
    public void entree(Joueur j) {
        if (!this.isVide()) {
            this.objet.interaction(j);
        }
    }

    //methode tostring
    public String getSymbole() {
        return isVide() ? " " : this.getContenu().toString();
    }
    public String toString(boolean isVisible) {
        if (this.isVisible())
            return this.getSymbole();
        else
            return "S"; // salle non visible
    }

    //constructeur 
    public Salle(Position pos, Plateau p, Objet o) {
        this.setPosition(pos);
        this.setPlateau(p);
        this.setObjet(o);
        this.initAcces();
        this.setVisible(false);
    }
    public Salle(Position pos,Categorie g,Plateau p){
        this.setPosition(pos);
        this.setPlateau(p);
    }

    public Salle(Position pos, Plateau p) {
        this(pos, p, null);
    }

}
