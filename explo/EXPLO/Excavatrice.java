package EXPLO;

public class Excavatrice extends Outil {

    public Excavatrice() {
        super("E", "Excavatrice", "Détruit un mur sans grenade", 5);
    }

    public void interaction(Joueur j) {
        System.out.println("Vous avez trouvé une Excavatrice !");
        j.recupere();
    }

    public void activation(Joueur j) {
        if (j.getEnergie() < this.getconsoenergie()) {
            System.out.println("Pas assez d'énergie !");
            return;
        }

        System.out.println("Quel mur détruire ? (haut, bas, gauche, droite)");
        Direction dir = Direction.getDirection(JeuDuDemineur.SCANNER.nextLine());
        if (!dir.isValide()) return;

        Position pos = j.getPosition();
        Salle salle = pos.getPlateau().getSalle(pos);

        // Si le mur est déjà détruit
        if (salle.getAcces().contains(dir)) {
            System.out.println("Il n'y a pas de mur ici !");
        } else {
            j.diminuerEnergie(this.getconsoenergie());
            salle.setAcces(dir);
            System.out.println("Le mur a été percé avec l'excavatrice !");
        }
    }
}