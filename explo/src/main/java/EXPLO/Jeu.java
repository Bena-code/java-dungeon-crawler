package src.main.java.EXPLO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Jeu {
    private Plateau plateau;
    private Categorie[] listeCategories;
    private int proportionVides;
    private Joueur joueur;

    public Plateau getPlateau() {
        return this.plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public Categorie[] getListeCategories() {
        return this.listeCategories;
    }

    public void setListeCategories(Categorie[] listeCategories) {
        this.listeCategories = listeCategories;
    }

    public int getProportionVides() {
        return this.proportionVides;
    }

    public void setProportionVides(int proportionVides) {
        this.proportionVides = (proportionVides >= 0 && proportionVides <= 100) ? proportionVides : 0;
    }

    public Joueur getJoueur() {
        return this.plateau.getJoueur();
    }

    public void initJeu() {
        this.plateau = new Plateau(
                this.plateau.getNbLig(),
                this.plateau.getNbCol(),
                this.proportionVides,
                this.listeCategories,
                this);
        this.joueur = this.plateau.getJoueur();
    }

    public boolean isFini() {
        return this.joueur.getNombreGrenades() == 0 || this.joueur.isPerdant();
    }

    public void joue(BufferedReader buffer, PrintWriter out) throws IOException {
        String pseudo = ServeurJeu.lireTexte("Entrez votre pseudo :", buffer, out);
        this.joueur = this.plateau.getJoueur();
        this.joueur.setNom(pseudo);
        this.joueur.ajouterEnergie(10);
        this.joueur.ajouterGrenades(5);
        this.plateau.setVisible(false);

        while (!isFini()) {
            out.println("Position actuelle : " + this.joueur.getPosition());
            out.println(this.plateau);

            String action = ServeurJeu.lireTexte(
                    "Que voulez-vous faire ? (avancer / utiliser / quitter)", buffer, out);

            switch (action.toLowerCase()) {
                case "avancer":
                    avancer(buffer, out);
                    break;
                case "utiliser":
                    utiliserOutil(buffer, out);
                    break;
                case "quitter":
                    out.println("Merci d'avoir joué !");
                    return;
                default:
                    out.println("Action invalide !");
                    break;
            }

            Position sortie = new Position(2, 2, this.plateau);
            if (this.joueur.getPosition().equals(sortie)) {
                out.println("\n*******************************************************");
                out.println(" FÉLICITATIONS ! VOUS AVEZ TROUVÉ LA SORTIE !");
                out.println("*******************************************************\n");
                return;
            }
            out.println("\n--------------------------------------------------------------------------------------------");
        }

        if (this.joueur.isPerdant()) {
            out.println("La partie est terminée : vous avez perdu.");
        } else {
            out.println("La partie est terminée : vous n'avez plus de grenades.");
        }
    }

    private void avancer(BufferedReader buffer, PrintWriter out) throws IOException {
        Direction direction = Direction.getDirectionHorizontaleVerticale(buffer, out);
        Position positionActuelle = this.joueur.getPosition();
        Position positionCible = positionActuelle.getSalleSuivante(positionActuelle, direction);

        if (positionCible == null) {
            out.println("Impossible, c'est le bord de la carte !");
            return;
        }

        Salle salleActuelle = this.plateau.getSalle(positionActuelle);
        if (salleActuelle.getAcces().contains(direction)) {
            this.joueur.avance(direction, buffer, out);
            return;
        }

        out.println("Le passage est bloqué par un mur.");
        if (this.joueur.getNombreGrenades() <= 0) {
            out.println("Vous n'avez plus de grenades pour détruire ce mur !");
            return;
        }

        String reponse = ServeurJeu.lireTexte(
                "Voulez-vous utiliser une grenade pour détruire le mur ? (oui/non)",
                buffer,
                out);
        if (reponse.equalsIgnoreCase("oui")) {
            this.joueur.lanceGrenade(direction, buffer, out);
            salleActuelle.setAcces(direction);
            out.println("BOOM ! Le mur est détruit.");
            this.joueur.avance(direction, buffer, out);
        } else {
            out.println("Vous restez sur place.");
        }
    }

    private void utiliserOutil(BufferedReader buffer, PrintWriter out) throws IOException {
        if (this.joueur.getTaille() == 0) {
            out.println("Vous n'avez aucun outil dans votre inventaire.");
            return;
        }

        out.println("Liste des outils disponibles :");
        for (int i = 0; i < this.joueur.getTaille(); i++) {
            out.println((i + 1) + ". " + this.joueur.get(i).getInfos());
        }

        int choix = ServeurJeu.lireEntier(
                "Sélectionnez l'outil à utiliser (par son numéro)", buffer, out);
        if (choix < 1 || choix > this.joueur.getTaille()) {
            out.println("Numéro d'outil invalide.");
            return;
        }

        this.joueur.get(choix - 1).activation(this.joueur, buffer, out);
    }

    public Jeu(int nbLig, int nbCol, int proportionVides, Categorie... listeCategories) {
        this.setProportionVides(proportionVides);
        this.setListeCategories(listeCategories);
        this.plateau = new Plateau(
                nbLig,
                nbCol,
                this.proportionVides,
                this.listeCategories,
                this);
        this.joueur = this.plateau.getJoueur();
    }
}
