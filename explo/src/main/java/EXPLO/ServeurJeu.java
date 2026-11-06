package src.main.java.EXPLO;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurJeu {
    public static void main(String[] args) {
        int port = 5555;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur Java démarré sur le port " + port);
            System.out.println("En attente du joueur...");

            try (Socket clientSocket = serverSocket.accept();
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                System.out.println("Joueur connecté depuis : " + clientSocket.getInetAddress());
                out.println("=========================================");
                out.println("BIENVENUE DANS LE DEMINEUR (RESEAU)");
                out.println("=========================================");

                Categorie grenades = new Categorie(4, CaisseGrenades.class);
                Categorie mines = new Categorie(9, Mine.class);
                Categorie energie = new Categorie(6, ReserveEnergie.class);
                Categorie scannerDirectionnel = new Categorie(2, ScannerUnidirectionnel.class);
                Categorie detecteurMine = new Categorie(2, DetecteurMines.class);

                Jeu jeu = new Jeu(9, 9, 20, grenades, mines, energie,
                        scannerDirectionnel, detecteurMine);
                jeu.joue(buffer, out);
                out.flush();
                if (out.checkError()) {
                    throw new IOException("Impossible d'envoyer la réponse finale au joueur.");
                }
                System.out.println("Fin de la partie, fermeture du serveur.");
            }
        } catch (IOException e) {
            System.err.println("Erreur réseau : " + e.getMessage());
        }
    }

    public static String lireLigne(BufferedReader buffer) throws IOException {
        String ligne = buffer.readLine();
        if (ligne == null) {
            throw new EOFException("Le joueur a fermé la connexion.");
        }
        return ligne.trim();
    }

    public static String lireTexte(String prompt, BufferedReader buffer, PrintWriter out) throws IOException {
        while (true) {
            envoyer(prompt, out);
            String texte = lireLigne(buffer);
            if (!texte.isEmpty()) {
                return texte;
            }
            envoyer("La saisie ne peut pas être vide.", out);
        }
    }

    public static int lireEntier(String prompt, BufferedReader buffer, PrintWriter out) throws IOException {
        while (true) {
            String texte = lireTexte(prompt, buffer, out);
            try {
                return Integer.parseInt(texte);
            } catch (NumberFormatException e) {
                envoyer("Veuillez saisir un nombre entier valide.", out);
            }
        }
    }

    private static void envoyer(String message, PrintWriter out) throws IOException {
        out.println(message);
        out.flush();
        if (out.checkError()) {
            throw new IOException("Impossible d'envoyer un message au joueur.");
        }
    }
}