import java.awt.*;

/**
 * <b>Joueur est la classe gérant tout ce qui est spécifique au Joueur</b>
 * <p>Elle contient en données membres</p>
 * <ul>
 * <li>La couleur</li>
 * <li>Les points</li>
 * <li>Le booléen si 5 pions sont alignés</li>
 * </ul>

 * 
 * @author Dokarus, GridexX
*/
public class Joueur 
{
    /**
     * La couleur du Joueur (Noir ou Blanc)
     */
    Color couleurJoueur;
    /**
     * Le nombre de points de joueur
     */
    int point;
    /**
     * Booleen indiquant si 5 pions ou plus sont alignés
     */
    boolean aligne;

    /**
     * <p>Constructeur de la classe Joueur</p>
     *  @param c Couleur du joueur (Noir ou Blanc)
     */
    public Joueur(Color c)
    {
        couleurJoueur = c ;
        point = 0 ;
        aligne = false;
    }

    /**
     * <p>Fonction lancé lorsque qu'un joueur gagne un point, c'est à dire lorsque qu'il mange une paire de pions adverse</p>
     */
    public void gagneUnPoint(){
        point++;
    }

    /**
     * <p>Retourne la couleur du joueur</p>
     * @return Color
     */
    public final Color getCouleurJoueur()
    { return couleurJoueur; }
    /**
     * <p>Retourne le nombre de points du joueur</p>
     * @return Int
     */
    public final int getPointJoueur()
    { return point; }
    /**
     * <p>Setteur du nombre de points du joueur</p>
     * @param p Le nombre de points à assigner
     */
    public void setPointJoueur(int p)
    { point=p; }
    /**
     * <p>Retourne un booléen indiquant si il possède 5 pions ou plus alignés</p>
     * @return Boolean
     */
    public final boolean getAligne()
    { return aligne; }
    /**
     * <p>Setteur du booléen Aligne</p>
     * @param b Boolean
     */
    public void setAligne(boolean b)
    { aligne=b; }
}