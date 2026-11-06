package EXPLO;

import java.util.ArrayList;
import java.util.List;

public class Joueur{

    private String nom;
    private int nombreGrenades=2;
    private int nbGrenadesMax = 10;
    private int energie=5;
    private Position position;
    private boolean perdant;
    private final int EnergieMax = 240;
    private static final String JOUEUR = "@";
    private List<Outil> outils = new ArrayList<>(); // Initialiser la liste d'outils
    

    public String getNom() {
        return this.nom;
    }
    public void setNom(String nom) {
        this.nom=nom;
    }
    // Méthodes pour obtenir et modifier le nombre de grenades du joueur
    public int getNombreGrenades() {
        return this.nombreGrenades;
    }
    public void ajouterGrenades(int grenades) {
        this.nombreGrenades += grenades;
    }
    public int getNbGrenadesMax() {
        return nbGrenadesMax;
    }

    // Méthodes pour obtenir et modifier l'énergie du joueur
    public int getEnergie() {
        return this.energie;
    }
    public void diminuerEnergie(int quantite) {
        this.energie -= quantite;
    }

    // Méthodes pour obtenir et modifier la position du joueur
    public Position getPosition() {
        return this.position;
    }

    private void setPosition(Position position) {
        // Assurez-vous que la nouvelle position est valide
        if (position != null && position.getPlateau() != null && position.getPlateau().getSalle(position) != null) {
            this.position = position;
            // Mettez à jour la salle du joueur
            this.position.getPlateau().getSalle(this.position).entree(this);
        }
    }

    // Méthodes pour obtenir et modifier la collection d'outils du joueur
    public int getTaille(){
        return outils.size();
    }
    public Outil get(int i){
        return outils.get(i);
    }
    public void setListe(ArrayList<Outil> liste){
        this.outils = liste;
    }
  
    // Méthodes pour vérifier si le joueur a perdu
    public boolean isPerdant() {
        return this.perdant;
    }
    //Methode pour arreter le jeu
    public void setPerdant(boolean perdant) {
        this.perdant = perdant;
    }

    /// methode en plus pour le joueur : avancer , ajtbojet, recupere, lancegrenade ,ajouteenergie 

    // Méthode pour avancer dans une direction donnée
    public void avance(Direction direction) {
            // Obtenez la position actuelle du joueur
        Position nouvellePosition = this.getPosition().getSalleSuivante(this.getPosition(),direction);
        // Vérifiez si la nouvelle position est valide
        if (nouvellePosition != null) {
            // Déplacez le joueur vers la nouvelle position
            this.setPosition(nouvellePosition);
        } else {
            // Affichez un message d'erreur si la nouvelle position est invalide
            System.out.println("Déplacement impossible dans cette direction !");
        }
    }

    public void ajouterObjet(Outil outil) {
        outils.add(outil);
    }
    //methode ajt enerrgie 
    public void ajouterEnergie(int quantite) {
        if (energie + quantite <= EnergieMax) {
            energie += quantite;
        } else {
            energie = EnergieMax;
        }
    }
    // Méthodes pour interagir avec les outils et l'environnement
    public void recupere(){
        Salle salleActuelle = this.position.getPlateau().getSalle(this.position);
        if (!salleActuelle.isVide()) {
            Objet objet = salleActuelle.getContenu(); // Récupérer l'objet dans la salle
    
            // Ajouter l'objet à la collection d'outils du joueur (cast si nécessaire)
            this.outils.add((Outil) objet);
    
            salleActuelle.setObjet(null); // Retirer l'objet de la salle
        }
    }

    public void lanceGrenade(Direction direction){
        if (this.nombreGrenades > 0) { // Vérifier si le joueur a des grenades
            Position positionGrenade = this.position.getSuivante(direction); // Obtenir la position de la grenade
            Plateau plateau = this.position.getPlateau();
            if (plateau != null && positionGrenade.isValide()) { // Vérifier si la position de la grenade est valide sur le plateau
                Salle salleGrenade = plateau.getSalle(positionGrenade);
                if (salleGrenade != null && !salleGrenade.isVide()) { // Vérifier si la salle cible contient un objet
                    Objet objetCible = salleGrenade.getContenu();
                    if (objetCible instanceof Mine) { // Vérifier si l'objet cible est une mine
                        ((Mine) objetCible).interaction(this); // Interagir avec la mine pour perdre le jeu
                    }
                }
                this.nombreGrenades--; // Décrémenter le nombre de grenades du joueur
            }
        }
        
    }
   
  

    //constructeur 
    public Joueur(String nom, Position position) {
        this.nom = nom;
        this.position = position;
        this.nombreGrenades = 0;
        this.energie = 0;
    }

    public Joueur(Position position) {
        this.position = position;
        this.nombreGrenades= 0;
        this.energie = 0;
    }

    public Joueur() {
        this.nombreGrenades = 0;
        this.energie = 0;
    }
    //to string 
    public String toString(){
        return JOUEUR;
    }


}