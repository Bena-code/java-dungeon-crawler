package src.main.java.EXPLO;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Mine extends Objet
{
    public void interaction(Joueur j, BufferedReader buffer, PrintWriter out) {
        j.setPerdant(true);
        out.println("BOOUUUUMMM , vous etes tomber sur une Mine !!!!!!");
        out.println("PERDU");
    }
    public void activation(Joueur j, BufferedReader buffer, PrintWriter out) {
        
    }

    public Mine(){
        super("*","Mine");
    }
}
