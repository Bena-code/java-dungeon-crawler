package EXPLO;

public class UnitéDeDeminage extends Outil {

    public UnitéDeDeminage() {
        super("D", "Unité de Déminage", "Désamorce une mine à distance", 6);
    }

    public void interaction(Joueur j) {
        System.out.println("Vous avez trouvé une Unité de Déminage !");
        j.recupere();
    }

    public void activation(Joueur j) {
        if (j.getEnergie() < this.getconsoenergie()) {
            System.out.println("Pas assez d'énergie !");
            return;
        }

        System.out.println("Direction à déminer ? (haut, bas, gauche, droite)");
        Direction dir = Direction.getDirection(JeuDuDemineur.SCANNER.nextLine());
        if (!dir.isValide()) return;

        j.diminuerEnergie(this.getconsoenergie());

        Position posJoueur = j.getPosition();
        // On récupère la position visée
        Position posCible = posJoueur.getSalleSuivante(posJoueur, dir);

        if (posCible != null) {
            Salle salleCible = posJoueur.getPlateau().getSalle(posCible);
            
            // C'est ici que la magie opère :
            if (salleCible.contientMine()) {
                salleCible.setObjet(null); // ON SUPPRIME LA MINE !
                System.out.println("SUCCÈS ! La mine a été désamorcée.");
            } else {
                System.out.println("Rien à signaler : il n'y avait pas de mine ici.");
            }
        } else {
            System.out.println("Impossible, c'est un mur.");
        }
    }
}