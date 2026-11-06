package src.main.java.EXPLO;


public class Scanneromnidirectionnel extends Outil {
   
   

    public Scanneromnidirectionnel() {
        super("O", "Scanneromnidirectionnel", "Permet de scanner les salles dans toutes les directions..", 5);
       
    }

 
    public void interaction(Joueur j) {
            Scanneromnidirectionnel scan= new Scanneromnidirectionnel();
            System.out.println("vous venez de recupere un detecteur de mine");
            j.ajouterObjet(scan);
            j.recupere();
            for (int i = 0; i < j.getTaille(); i++) {
                if(j.get(i)==scan){
                System.out.println(" Vous etes deja en possesion de l'outil");
                
                return;}
            }
            // Vérifier si le joueur a assez d'énergie pour utiliser l'outil
        if (j.getEnergie() < (this.getconsoenergie()) ){
            System.out.println("Vous n'avez pas assez d'énergie pour utiliser cet outil.");
            return;
        }
        // Consommer l'énergie de l'outil
        j.diminuerEnergie(j.getEnergie() - (this.getconsoenergie()));
        // Activer l'outil (explorer les salles adjacentes et détecter les mines)
        this.activation(j);
        }
    
        public void activation(Joueur j) {
        // Informer le joueur de la fonctionnalité du scanner et des directions disponibles
        System.out.println("Le scanner omnidirectionnel permet de scanner les salles dans toutes les directions.");
        System.out.println("Directions disponibles :  'haut','bas','gauche','droite'");

        // Demander au joueur de choisir une direction
        Direction direction = null;
        do {
            System.out.println("Entrez une direction : ");
            String directionTexte = JeuDuDemineur.SCANNER.nextLine();
            try {
                direction = Direction.getDirection(directionTexte);
            } catch (IllegalArgumentException e) {
                System.out.println("Direction invalide. Veuillez réessayer.");
            }
        } while (direction == null);

        // Scanner les salles jusqu'à ce que le joueur souhaite arrêter
        boolean continuer = true;
        while (continuer) {
            Position positionJoueur = j.getPosition();
            Plateau plateau = positionJoueur.getPlateau();

            // Scanner les salles dans la direction choisie
            while (true) {
                Position positionVirtuelle = positionJoueur.getSalleSuivante(positionJoueur, direction);

                if (positionVirtuelle == null) {
                    break;
                }

                Salle salle = plateau.getSalle(positionVirtuelle);
                System.out.println("Salle scannée : " + salle.getContenu());
            }

            // Demander au joueur s'il souhaite continuer à scanner
            System.out.println("Voulez-vous continuer à scanner ? (Oui/Non)");
            continuer = JeuDuDemineur.SCANNER.nextLine().equalsIgnoreCase("Oui");
        }

        System.out.println("Le scan est terminé.");
    }

}
    
