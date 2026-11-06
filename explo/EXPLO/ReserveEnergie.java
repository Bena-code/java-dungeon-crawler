package EXPLO;



import java.util.Random;

/**
 * Représente une réserve d'énergie dans le jeu.
 */
public class ReserveEnergie extends Objet {
    private final static int ENERGIE_MAX = 20; // Capacité maximale d'énergie que le joueur peut stocker
    private int quantite; // Quantité d'énergie actuellement disponible dans la réserve
    private boolean enSalle;

    public boolean isEnSalle() {
        return enSalle;
    }
    /**
     * Constructeur par défaut. Initialise la réserve avec une quantité d'énergie
     * aléatoire.
     */
    public ReserveEnergie() {
        super("¤", "Réserve d'énergie");
        Random random = new Random();
        this.quantite = random.nextInt(ENERGIE_MAX + 1); // Génère un nombre aléatoire entre 0 et ENERGIE_MAX inclus
    }
    //constructeur pour categorie 
    public ReserveEnergie(int PROBABILITE_SALLE) {
        super("¤", "Réserve d'énergie");
        Random random = new Random();
        this.quantite = random.nextInt(ENERGIE_MAX + 1); // Génère un nombre aléatoire entre 0 et ENERGIE_MAX inclus
        
    }

    /**
     * Retourne la quantité d'énergie disponible dans la réserve.
     * 
     * @return La quantité d'énergie disponible.
     */
    public int getQuantite() {
        return quantite;
    }

    public void activation(Joueur j){ }
    /**
     * Permet à un joueur d'interagir avec la réserve d'énergie en prenant une
     * certaine quantité d'énergie.
     * La quantité d'énergie prise par le joueur ne peut pas dépasser sa capacité
     * maximale.
     * 
     * @param j Le joueur qui interagit avec la réserve d'énergie.
     */
    
    public void interaction(Joueur j) {
        int energieJoueur = j.getEnergie();
        int energieDisponible = getQuantite();

        if (energieDisponible > 0) {
            System.out.println("Il reste " + energieDisponible + " unités d'énergie dans la réserve.");
            System.out.println("Vous avez actuellement " + energieJoueur + " unités d'énergie.");

            // Demander au joueur combien d'énergie il veut prendre
            int energieAPrendre = JeuDuDemineur.lireEntier("Combien d'énergie voulez-vous prendre ?");

            // Vérifier si le joueur peut prendre autant d'énergie
            if (energieAPrendre > energieDisponible) {
                System.out.println("Il n'y a pas assez d'énergie dans la réserve.");
            } else if (energieJoueur + energieAPrendre > ENERGIE_MAX) {
                System.out.println("Vous ne pouvez pas stocker autant d'énergie.");
            } else {
                // Ajouter l'énergie prise au joueur
                j.ajouterEnergie(energieAPrendre);
                // Mettre à jour la quantité d'énergie dans la réserve
                quantite -= energieAPrendre;
                System.out.println("Vous avez pris " + energieAPrendre + " unités d'énergie dans la réserve.");
            }
        } else {
            System.out.println("La réserve d'énergie est vide.");
        }
    }
}
