# 🏰 Java Procedural Explorer (Dungeon Crawler)

> Un moteur de jeu d'exploration procédurale développé en Java natif, mettant l'accent sur la Programmation Orientée Objet (POO) et la gestion matricielle.

## 📋 Présentation du Projet
Ce projet est une simulation d'exploration en mode console. Le joueur évolue dans un environnement généré aléatoirement (grille 2D), rempli d'obstacles (murs), de pièges (mines) et d'outils à collecter.

L'objectif est de programmer une intelligence artificielle ou de laisser un joueur humain naviguer pour trouver la sortie en gérant ses ressources (énergie, grenades).

## 🛠️ Compétences Techniques Mises en Œuvre
Ce projet démontre la maîtrise des concepts fondamentaux de l'ingénierie logicielle :

* **Programmation Orientée Objet (POO) :**
    * **Héritage & Polymorphisme :** Utilisation d'une classe abstraite `Outil` dont héritent `Scanner`, `Excavatrice`, `DetecteurMines`.
    * **Encapsulation :** Protection des données du `Joueur` (PV, Energie) via des Getters/Setters.
* **Algorithmique :**
    * Gestion de **tableaux multidimensionnels** (`Salle[][]`) pour la carte.
    * Système de **coordonnées et vecteurs** pour les déplacements (`Position`, `Direction`).
    * **Génération Procédurale :** Placement aléatoire des murs et des objets à l'initialisation.
* **Outillage Developer :**
    * Intégration d'un **Mode Debug** pour visualiser la génération de la carte en temps réel.

## 🎮 Fonctionnalités du Jeu
* **Exploration :** Déplacement case par case (Haut, Bas, Gauche, Droite).
* **Destruction :** Système de grenades et d'excavatrices pour détruire les murs et créer des raccourcis.
* **Détection :** Utilisation de scanners (unidirectionnels ou omnidirectionnels) pour repérer les objets à distance.
* **Gestion de ressources :** Le joueur doit gérer son énergie (consommée par les outils) et son stock de grenades.
* **Conditions de Victoire/Défaite :** Trouver la sortie (Gagné) ou tomber sur une mine/plus de ressources (Perdu).

## 🚀 Comment Lancer le Projet

### Prérequis
* JDK 17 ou supérieur.
* Un terminal de commande.

### Installation et Exécution
1.  Compilez les fichiers sources :
    ```bash
    mkdir -p target/classes
    javac -d target/classes src/main/java/EXPLO/*.java
    ```

2.  Lancez le jeu :
    ```bash
    java -cp target/classes src.main.java.EXPLO.ServeurJeu
    ```

Le serveur attend une connexion du client sur le port `5555`.

## 🕹️ Commandes en Jeu
Une fois dans la console, interagissez avec les commandes textuelles :
* `avancer` : Se déplacer (le jeu demandera la direction).
* `utiliser` : Ouvrir l'inventaire pour utiliser un outil (Scanner, Détecteur...).
* `debug` : **(Dev Mode)** Révèle ou cache toute la carte instantanément.
* `quitter` : Fermer le programme.
