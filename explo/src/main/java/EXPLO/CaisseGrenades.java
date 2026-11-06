package src.main.java.EXPLO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Représente une caisse de grenades dans le jeu.
 * Le joueur peut interagir avec la caisse pour prendre des grenades.
 */
public class CaisseGrenades extends Objet {
    private final static int MAX = 10; // Nombre maximal de grenades dans la caisse
    private int nombreGrenades; // Nombre de grenades dans la caisse
    private boolean enSalle;

    public boolean isEnSalle() {
        return enSalle;
    }
    /**
     * Constructeur avec nombre de grenades spécifié.
     * 
     * @param nbGrenades Le nombre initial de grenades dans la caisse.
     */
    

    /**
     * Constructeur sans paramètres qui génère un nombre aléatoire de grenades.
     */
    public CaisseGrenades() {
        super("§", "Caisse de grenades");
        Random random = new Random();
        this.nombreGrenades = random.nextInt(MAX) + 1; // Génère un nombre aléatoire entre 1 et MAX inclus
        
    }   
    // constructeur pour categorie 
    public CaisseGrenades(int PROBABILITE_SALLE ) {
        super("§", "Caisse de grenades");
        Random random = new Random();
        this.nombreGrenades = random.nextInt(MAX) + 1; // Génère un nombre aléatoire entre 1 et MAX inclus
        this.enSalle = Math.random() * 100 < PROBABILITE_SALLE;
    }   
    public void activation(Joueur j, BufferedReader buffer, PrintWriter out) { }
    /**
     * Permet à un joueur d'interagir avec la caisse de grenades en prenant un
     * certain nombre de grenades.
     * 
     * @param joueur Le joueur qui interagit avec la caisse de grenades.
     */
    
    public void interaction(Joueur joueur, BufferedReader buffer, PrintWriter out) throws IOException {
        
        if (nombreGrenades > 0) {
            out.println("Il y a " + nombreGrenades + " grenades dans la caisse.");
            int grenadesAPrendre = ServeurJeu.lireEntier(
                    "Combien de grenades voulez-vous prendre ?", buffer, out);
            if (grenadesAPrendre <= 0) {
                out.println("Vous devez prendre au moins une grenade.");
            } else if (grenadesAPrendre > nombreGrenades) {
                out.println("Il n'y a pas assez de grenades dans la caisse.");
            } else if (grenadesAPrendre > joueur.getNbGrenadesMax() - joueur.getNombreGrenades()) {
                out.println("Vous n'avez pas assez de place pour prendre autant de grenades.");
            } else {
                joueur.ajouterGrenades(grenadesAPrendre);
                nombreGrenades -= grenadesAPrendre;
                out.println("Vous avez pris " + grenadesAPrendre + " grenades dans la caisse.");
            }
        } else {
            out.println("La caisse de grenades est vide.");
        }
    }
}
