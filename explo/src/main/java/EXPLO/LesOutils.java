package src.main.java.EXPLO;
import java.util.ArrayList;
public class LesOutils {
   
    private ArrayList<Outil> liste;
    private Joueur proprietaire;

    public int getTaille(){
        return liste.size();
    }
    public Outil get(int i){
        return liste.get(i);
    }
    public void setListe(ArrayList<Outil> liste){
        this.liste = liste;
    }
    public void init(){
        liste = new ArrayList<Outil>(); // Crée une liste d'outils vide
        liste.add(new ScannerUnidirectionnel()); // outil ajouté dès le départ
        liste.add(new DetecteurMines()); // idem
    }

    public void ajoute(Outil o)
    {
        if(this.liste.contains(o)) System.out.println(this.getProprietaire()+" est déjà en possession de "+o);
        else this.liste.add(o);
    }
    
    public Joueur getProprietaire(){
        return this.proprietaire;
    }
    private void setProprietaire(Joueur proprietaire){
        this.proprietaire = proprietaire;}
    
    public LesOutils(Joueur proprietaire)
    {
        this.setProprietaire(proprietaire);
        init();
    }
    public LesOutils(){
        init();
    }

}


