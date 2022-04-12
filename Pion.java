import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * <b>Pion est la classe qui gère toutes les données relatives au pion :</b>
 * 
 * @author Dokarus
*/
public class Pion 
{
    /**
     * La couleur du pion (Noir ou Blanc)
     */
    Color couleurPion;
    /**
     * Les coordonnées x et y du pion, sur le plateau
     */
    int x, y;
    /**
     * <p>Constructeur de la classe Pion</p>
     * @param c Couleur du pion
     * @param _x Coordonnée x du pion sur le plateau
     * @param _y Coordonnée y du pion sur le plateau
     */
    public Pion(Color c, int _x, int _y)
    {
        couleurPion = c ;
        x=_x;
        y=_y;
    }

    /**
     * <p>Retourne de la couleur du pion</p>
     * @return La couleur du pion
     */
    public Color getCouleurPion()
    { return couleurPion; }
    /**
     * <p>Retourne la coordonnée X du pion</p>
     * @return Entier X
     */
    public int getXPion()
    { return x; }
    /**
     * <p>Retourne la coordonnée Y du pion</p>
     * @return Y
     */
    public int getYPion()
    { return y; }
}
