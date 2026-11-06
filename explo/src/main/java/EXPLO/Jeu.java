package EXPLO;
public class Jeu {
    private Plateau plateau;
    private Categorie[] listeCategories;
    private int proportionVides;
    private Joueur joueur;

    //modifer et avoir plateau
    public Plateau getPlateau() {
        return this.plateau;
    }
    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    //modifer et avoir categorie
    public Categorie[] getListeCategories() {
        return this.listeCategories;
    }
    public void setListeCategories(Categorie[] listeCategories) {
        this.listeCategories = listeCategories;
    }

    //modifer et avoir proporstion vide
    public int getProportionVides() {
        return this.proportionVides;
    }
    public void setProportionVides(int proportionVides) {
        this.proportionVides = (proportionVides >= 0 && proportionVides <= 100) ? proportionVides : 0;
    }

    // recupere le joueur sur le plateau
    public Joueur getJoueur() {
        return this.getPlateau().getJoueur();
    }

    //methode 
    /**
     * Initialise le jeu.
     */
    public void initJeu() {
        // Initialisation du plateau
       new Plateau( this.plateau.getNbLig(), this.plateau.getNbCol(), proportionVides,  listeCategories , this);
    }
    
    
    public boolean isFini() {
        // Le jeu est fini lorsque le joueur et tomber sur une mine ou qu'il ne peut plus avancer
        return this.joueur.getNombreGrenades()==0 || this.joueur.isPerdant();
    }


    public void joue() {
        // Demander au joueur de taper son pseudo
        System.out.print("Entrez votre pseudo   : ");
        String pseudo = JeuDuDemineur.SCANNER.nextLine();
        // Récupérer le joueur depuis le plateau et définir son nom avec le pseudo saisi
        Joueur joueur = this.plateau.getJoueur();
        
        joueur.setNom(pseudo); // Utilisation du pseudo pour nommer le joueur        
        joueur.ajouterEnergie(10);// le joueur commence avec 10 d'energie 
        joueur.ajouterGrenades(5);// le joueur commence avec 5 grenade
        this.plateau.setVisible(false);// Rendre toutes les salles du plateau non visibles
        
        // Boucle principale du jeu
        while (!isFini()) {
            // Afficher le plateau et la position actuelle du joueur
            System.out.println("Position actuelle : " + joueur.getPosition());
            System.out.println(this.plateau);
            // Demander au joueur l'action à effectuer
            System.out.print("Que voulez-vous faire ? (avancer / utiliser / quitter) : ");
            String action = JeuDuDemineur.SCANNER.nextLine();
            
            // Traiter l'action du joueur
            switch (action.toLowerCase()) {
                case "avancer":
                        // 1. Demander la direction
                        System.out.print("Dans quelle direction voulez-vous avancer ? (haut / bas / gauche / droite) : ");
                        String directionStr = JeuDuDemineur.SCANNER.nextLine();
                        Direction direction = Direction.getDirection(directionStr);
                        
                        // 2. Calculer les positions
                        Position posJoueur = joueur.getPosition();
                        Position posCible = posJoueur.getSalleSuivante(posJoueur, direction);

                        // 3. Vérifier si la cible existe (bord du tableau)
                        if (posCible == null) {
                            System.out.println("Impossible, c'est le bord de la carte !");
                            break;
                        }

                        Salle salleActuelle = this.plateau.getSalle(posJoueur);
                        

                        // 4. Vérifier si le mur est DEJA ouvert
                        // On regarde si la salle actuelle a un accès vers la direction demandée
                        if (salleActuelle.getAcces().contains(direction)) {
                            // Le passage est libre, on avance sans utiliser de grenade
                            joueur.avance(direction);
                        } 
                        else {
                            // 5. Le passage est bloqué : On essaie de détruire le mur
                            System.out.println("Le passage est bloqué par un mur.");
                            
                            if (joueur.getNombreGrenades() > 0) {
                                System.out.print("Voulez-vous utiliser une grenade pour détruire le mur ? (oui/non) : ");
                                String rep = JeuDuDemineur.SCANNER.nextLine();
                                if (rep.equalsIgnoreCase("oui")) {
                                    // On utilise la grenade
                                    joueur.lanceGrenade(direction); // Note: ta méthode lanceGrenade décrémente déjà le compteur
                                    
                                    // On détruit le mur (CORRECTION ICI : on ouvre la direction demandée, pas l'inverse)
                                    salleActuelle.setAcces(direction); 
                                    
                                    System.out.println("BOOM ! Le mur est détruit.");
                                    joueur.avance(direction);
                                } else {
                                    System.out.println("Vous restez sur place.");
                                }
                            } else {
                                System.out.println("Vous n'avez plus de grenades pour détruire ce mur !");
                            }
                        }
                        break;
                    
                //permet d'utiliser un outils qui ce trouve dans les outils
                case "utiliser":
                    
                    // Vérifier si l'inventaire est vide
                    if (joueur.getTaille() == 0) {
                        System.out.println("Vous n'avez aucun outil dans votre inventaire.");
                        break;
                    }
                    // Afficher l'inventaire
                    System.out.println("Liste des outils disponibles :");
                    for (int i = 0; i < joueur.getTaille(); i++) {
                        Outil outil = joueur.get(i);
                        System.out.println(i + 1 + ". " + outil.getInfos()); // Afficher le numéro et le nom de l'outil
                    }
                    // Demander la sélection de l'outil
                    int choixOutil = JeuDuDemineur.lireEntier("Sélectionnez l'outil à utiliser (par son numéro)");
                    // Vérifier la validité de la saisie
                    if (choixOutil < 1 || choixOutil > joueur.getTaille()) {
                        System.out.println("Numéro d'outil invalide. Veuillez réessayer.");
                        break;
                    }
                    // Récupérer l'outil sélectionné
                    Outil outil = joueur.get(choixOutil - 1);
                    outil.activation(joueur);
                    //Utiliser l'outil (les actions spécifiques dépendent du type d'outil)
                   //Remplacer "plateau" par l'objet approprié si nécessaire
                    break;

                case "quitter":
                    // Gérer la sortie du jeu
                    System.out.println("Merci d'avoir joué !");
                    return;
                default:
                    System.out.println("Action invalide !");
                    break;
            }
            Position sortie = new Position(2, 2, this.plateau);
            
            // On compare la position du joueur avec la sortie
            if (joueur.getPosition().equals(sortie)) {
                System.out.println("\n*******************************************************");
                System.out.println(" FÉLICITATIONS ! VOUS AVEZ TROUVÉ LA SORTIE !");
                System.out.println("*******************************************************\n");
                return; // On arrête la méthode joue(), donc le jeu s'arrête.
            }
            System.out.println("\n--------------------------------------------------------------------------------------------");
        }
    }

    //constructeur 
    public Jeu(int nbLig, int nbCol, int proportionVides ,Categorie... listeCategories ) {
        // Créer le plateau
        this.plateau=new Plateau( nbLig, nbCol, proportionVides,  listeCategories , this);

        // Initialiser les autres attributs du jeu
        
        this.setProportionVides(proportionVides);
        this.setListeCategories(listeCategories);
        this.joueur=this.plateau.getJoueur();
        // Déroulement du jeu
        joue();
    }
   

    }
        
