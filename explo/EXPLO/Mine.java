package EXPLO;

public class Mine extends Objet
{
    public void interaction(Joueur j){
        j.setPerdant(true);
        System.out.println("BOOUUUUMMM , vous etes tomber sur une Mine !!!!!!");
        System.out.println("PERDU");
    }
    public void activation(Joueur j){
        
    }

    public Mine(){
        super("*","Mine");
    }
}


