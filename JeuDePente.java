import java.awt.Color;
import java.time.YearMonth;
import java.util.*;
import java.awt.*;

/**
 * <b>JeuDePente est la classe Principale du jeu</b>
 * <p>Elle instancie les autres et gère le fonctionnement global du jeu</p>
 *
 * 
 * @author Dokarus, GridexX
 */
public class JeuDePente 
{
    /**
     * Instance de la classe Plateau
     */
    static Plateau plateau;
    /**
     * Instance de la classe FenêtreGraphique
     */
    static FenetreGraphique fenetre;
    /**
     * Largeur de la fenêtre
     */
    static int dimensionX;
    /**
     * Hauteur de la fenêtre
     */
    static int dimensionY;
    /**
     * Largeur située entre le bord de la fenêtre et le plateau
     */
    static int decalageX;
    /**
     * Hauteur située entre le bord de la fenêtre et le plateau
     */
    static int decalageY;
    /**
     * Taille d'une case, pour la taille de l'image d'un pion
     */
    static int tailleCase;
    /**
     * Indique si l'on est en partie ou non
     */
    static boolean enJeu;
    /**
     * Indique si une partie a été gagné
     */
    static boolean aGagne;
    /**
     * Indique quel joueur a gagné (true = joueur 1, false = joueur 2)
     */
    static boolean j1aGagne;
    /**
     * Indique quel est le joueur qui joue, à travers sa couleur
     */
    static Color couleurActuelle;
    /**
     * Instance de la classe Joueur
     */
    static Joueur j1, j2;
    /**
     * Instance de la classe Son pour la musique
     */
    static Son musique;
    /**
     * Instance de la classe Son pour les effets sonores
     */
    static Son bruitage;
    /**
     * Volume de la musique
     */
    static int volumeMusique;
    /**
     * Thread permettant l'utilisation de son
     */
    static Thread thMus, thBruit;
    /**
     * Indique quelle image de pion est utilisée parmi la liste d'image de pion disponible dans le thème
     */
    static int imgPionJ1, imgPionJ2;

    /**
     * <p>Instancie toutes les classes, et gère l'intéraction entre les différentes parties de l'application</p>
     * @param arg Non utilisé
     */
    public static void main (String [] arg)
	{
        musique = new Son();
        bruitage = new Son();

        musique.setEstLoopable(true);
        thMus = new Thread(musique);
        thMus.start();
        volumeMusique=10;

        thBruit = new Thread(bruitage);
        thBruit.start();

        dimensionX=1600;
        dimensionY=900;
        decalageX=dimensionX*1/4;
        decalageY=dimensionY*1/18;
        tailleCase=(dimensionX-2*decalageX)/19;
        plateau = new Plateau(19);
        fenetre = new FenetreGraphique("Jeu de Pente", dimensionX, dimensionY);
        
        enJeu=false;
        j1 =  new Joueur(Color.WHITE);
        j2 =  new Joueur(Color.BLACK);
        couleurActuelle = j2.getCouleurJoueur();
    }
    
    /**
     * <p>Fonction lancée lors d'un clique sur le plateau de jeu</p>
     * @param x Coordonnée X du plateau
     * @param y Coordonnée Y du plateau
     */
    public static void clique(int x, int y)
    {
        if((x>=decalageX && x<=dimensionX-decalageX-5) && (y>=decalageY && y<=dimensionY-decalageY-5)) //Clique sur le damier
        {
            if(couleurActuelle.equals(j1.getCouleurJoueur())){ //Joueur 1
                if(plateau.cliquePlateau(j1,conversionX(x),conversionY(y))){
                    if(j1.getPointJoueur()>=5 || j1.getAligne())
                    {
                        partieGagne(Color.WHITE);
                    }
                    couleurActuelle=j2.getCouleurJoueur();
                }
            }
            else{ //Joueur 2
                if(plateau.cliquePlateau(j2,conversionX(x),conversionY(y))){
                    if(j2.getPointJoueur()>=5 || j2.getAligne())
                    {
                        partieGagne(Color.BLACK);
                    }
                    couleurActuelle=j1.getCouleurJoueur();
                }
            }
        }
        else if (x<=dimensionX/20+dimensionX/40 && y<=dimensionX/20+dimensionY/40){
            fenetre.fenetreConfirmation();
        }
        else if(x>=dimensionX-dimensionX/20-dimensionX/40 && y<=dimensionX/20+dimensionY/40){
            fenetre.fenetreParam();
        }
    }

    /**
     * <p>Convertie la coordonnée X de la fenêtre en coordonnée du plateau, en prenant compte du décalage et de la taille d'une case</p>
     * @param x La coordonnée X de la fenêtre
     * @return La coordonnée X sur le plateau
     */
    public static int conversionX(int x){ //convertir les coordonnées en index du quadrillage
        return (x-decalageX)/tailleCase;
    }
    /**
     * <p>Convertie la coordonnée Y de la fenêtre en coordonnée du plateau, en prenant compte du décalage et de la taille d'une case</p>
     * @param y La coordonnée Y de la fenêtre
     * @return La coordonnée Y sur le plateau
     */
    public static int conversionY(int y){ //convertir les coordonnées en index du quadrillage
        return (y-decalageY)/tailleCase;
    }

    /**
     * <p>Lancé au moment d'une victoire, elle réitinialise les données de la partie et indique qui a gagné</p>
     * @param c La couleur du joueur gagnant
     */
    public static void partieGagne(Color c)
    {
        resetPartie();
        aGagne = true;
        enJeu = false;
        volumeMusique = musique.getVolume();
        jouerBruitage( getTheme().musiqueVictoire );
        //musique.setVolume(0);
        if(c==Color.WHITE){

            j1aGagne=true;
        }
        else if(c==Color.BLACK){

            j1aGagne=false;
        }
    }

    /**
     * <p>Réitinialise le plateau et les données des joueurs</p>
     */
    public static void resetPartie()
    {
        plateau.resetPlateau();
        j1.setPointJoueur(0);
        j1.setAligne(false);
        j2.setPointJoueur(0);
        j2.setAligne(false);
        fenetre.repaint();
        couleurActuelle=j2.getCouleurJoueur();;
    }

    /**
     * <p>Change le mode de jeu, du menu vers une partie, ou inversement (la partie est réinitialisée à chaque fois qu'on quitte, ou lance une partie)</p>
     */
    public static void changerModeDeJeu()
    {
        enJeu=!enJeu;
        resetPartie();
        musique.setVolume(volumeMusique);
        if(enJeu) changerMusiqueFond( getTheme().musiqueFond );
        else
        changerMusiqueFond( getTheme().musiqueMenu );
    }

    /**
     * <p>Change la musique de fond</p>
     * @param pathMusique Le chemin de la nouvelle musique
     */
    public static void changerMusiqueFond(String pathMusique)
    {
        musique.setPath(pathMusique);
    }

    public static void setVolumeMus(int vol)
    {
        musique.setVolume(vol);
    }

    public static int getVolumeMus()
    { return volumeMusique;}

    /**
     * <p>Joue un effet sonore</p>
     * @param pathBruitage Le chemin du son à jouer
     */
    public static void jouerBruitage(String pathBruitage)
    {
        bruitage.setPath(pathBruitage);
        Thread th = new Thread(bruitage);
        th.start();

    }

    /**
     * <p>Fonction Accesseur pour accéder au booleen enJeu</p>
     * @return enJeu
     */
    public static final boolean getEnJeu(){ 
        return enJeu;
    }
    /**
     * <p>Fonction Accesseur pour accéder à la liste de Pion de Plateau</p>
     * @return La liste de Pion
     */
    public static final Hashtable<Integer,Pion> getListePion(){ 
        return plateau.getListePion();
    }
    /**
     * <p>Fonction Accesseur pour accéder à la taille du plateau</p>
     * @return La taille du plateau
     */
    public static final int getTaille(){ 
        return plateau.getTaille();
    }
    /**
     * <p>Fonction Accesseur pour accéder aux nombres de points du joueur indiqué</p>
     * @param i 1 ou 2
     * @return Le nombre de points du joueur
     */
    public static final int getPoint(int i)
    {
        if(i==1){
            return j1.getPointJoueur(); 
        }
        else if (i==2){
            return j2.getPointJoueur();
        }
        else{
            return 0;
        }
    }

    /**
     * <p>Fonction Accesseur pour accéder au Theme de Affichage, en passant par FenetreGraphique</p>
     * @return Theme
     */
    public static final Theme getTheme()
    { return fenetre.affichage.theme; }
}