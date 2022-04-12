import java.util.Hashtable;
import java.awt.*;

/**
 * <b>Plateau est la classe qui gère toutes les données relatives au Plateau</b>
 * <p>Elle est directement liée à la classe Pion qui modifie ses attributs en fonction  des actions réalisées par les joueurs</p> 
 * @see class Pion
 * 
 * @author Dokarus
*/
public class Plateau 
{
    /**
     * Le nombre de cases du tableau par ligne et par colonne
     */
    final int taille;
    /**
     * La liste de pions disposés sur le plateau
     */
    Hashtable<Integer,Pion> listePion;

    /**
     * <p>Constructeur de la classe PLateau</p>
     * @param t Le nombre de case par ligne et par colonne
     */
    public Plateau(int t)
    {
        listePion = new Hashtable<Integer,Pion>();
        taille = t;
        ajouterPion(Color.WHITE,10,10); //Premier pion posé au milieu
    }

    /**
     * <p>Ajoute un pion dans la liste</p>
     * @param c La couleur du joueur qui a posé le pion
     * @param x La coordonnée x sur le plateau du pion posé
     * @param y La coordonnée y sur le plateau du pion posé
     */
    public void ajouterPion(Color c, int x, int y)
    {
        listePion.put(y*taille+x,new Pion(c,x,y)); //index = y*taille+x 
    }

    /**
     * <p>Supprime un pion parmi la liste de pion</p>
     * @param x La coordonnée x sur le plateau du pion à supprimer
     * @param y La coordonnée y sur le plateau du pion à supprimer
     */
    public void supprimerPion(int x, int y)
    {
        listePion.remove(y*taille+x);
    }

    /**
     * <p>Réagit face au clique de l'un des joueurs</p>
     * <p>Si la case cliquée n'a pas de pion, un nouveau pion est créé, on va ensuite analysé les 8 cases qui l'entoure</p>
     * <p>Plusieurs réactions sont alors possibles : </p>
     * <ul>
     * <li>Si un pion de même couleur est détecté, on regarde le nombre de pions aligné dans le même sens, puis dans l'autre. Si au moins 5 pions sont alignés, le joueur qui a posé le pion gagnera la partie</li>
     * <li>Si un pion de couleur différente est détecté, on vérifie que le pion suivant est aussi un pion différent, et que le pion encore suivant est un pion de la même couleur que le pion posé, si c'est le cas, on mange la paire de pion de oculeur différente</li>
     * </ul>
     * @param joueur Le joueur qui a cliqué
     * @param x La coordonnée x sur le plateau correspondant au clique
     * @param y La coordonnée y sur le plateau correspondant au clique
     * @return True si une action a été effectuée, sinon False
     */
    public boolean cliquePlateau(Joueur joueur, int x, int y) //Renvoie true si un pion à été posé
    { 
        boolean estMange=false;

        if(!listePion.containsKey(y*taille+x))
        {
            ajouterPion(joueur.getCouleurJoueur(),x,y);

            for(int i=-1; i<=1; i++) //Les deux boucles for regardent les 8 cases autour du pion posé
            {
                for(int j=-1; j<=1; j++)
                {
                    if(!(i==0 && j==0) && listePion.containsKey((i+y)*taille+(j+x)))
                    {
                        if(listePion.get((i+y)*taille+(j+x)).couleurPion.equals(joueur.getCouleurJoueur())) //Même couleur
                        {
                            int aligne=2; //2 pions de même couleur aligné
                            boolean memeCouleur=true;
                            while(listePion.containsKey((i*aligne+y)*taille+(j*aligne+x)) && memeCouleur) //On vérifie le nombre de pions de même couleur dans le même sens (vecteur)
                            {
                                if(listePion.get((i*aligne+y)*taille+(j*aligne+x)).couleurPion.equals(joueur.getCouleurJoueur()))
                                {
                                    aligne++;
                                }
                                else
                                {
                                    memeCouleur=false;
                                }
                            }
                            boolean autreSens=listePion.containsKey((-i+y)*taille+(-j+x));
                            memeCouleur=true;
                            if(autreSens) //Si il y a aussi une même couleur dans l'autre sens
                            {
                                int z=1;
                                while(listePion.containsKey((-i*z+y)*taille+(-j*z+x)) && memeCouleur) //On vérifie dans l'autre sens
                                {
                                    if(listePion.get((-i*z+y)*taille+(-j*z+x)).couleurPion.equals(joueur.getCouleurJoueur()))
                                    {
                                        z++;
                                        aligne++;
                                    }
                                    else
                                    {
                                        memeCouleur=false;
                                    }
                                }
                            }
                            if(aligne>=5)
                            {
                                joueur.setAligne(true);
                            }
                        }
                        else if(listePion.containsKey((i*2+y)*taille+(j*2+x)) && listePion.containsKey((i*3+y)*taille+(j*3+x))) //Si couleur différente, on vérifie si il y a deux pions qui suivent
                        {
                            if(!(listePion.get((i*2+y)*taille+(j*2+x)).couleurPion.equals(joueur.getCouleurJoueur())) && listePion.get((i*3+y)*taille+(j*3+x)).couleurPion.equals(joueur.getCouleurJoueur())) //et si c'est encore une couleur différente du pion posé puis une même couleur 
                            {
                                supprimerPion(j+x, i+y); //alors on mange les deux pions différents
                                supprimerPion(j*2+x, i*2+y);
                                joueur.gagneUnPoint();

                                //lecture du son de mangeage
                                JeuDePente.jouerBruitage( JeuDePente.fenetre.getTheme().getSonManger( JeuDePente.fenetre.getTheme().getNbRandom("manger") ) );
                                estMange = true;
                            }
                        }
                    }
                }
            }
            if(!estMange) //lecture du son de plaçage
            JeuDePente.jouerBruitage( JeuDePente.fenetre.getTheme().getSonPlacer( JeuDePente.fenetre.getTheme().getNbRandom("placer") ) );

            return true;
        }
        return false; //Pas d'action
    }

    /**
     * <p>Reset le plateau, en faisant un clear de la liste de pions</p>
     */
    public void resetPlateau()
    {
        listePion.clear();
        ajouterPion(Color.WHITE, 9, 9);
    }

    /**
     * <p>Retourne la liste de pions du plateau</p>
     * @return La liste de pions
     */
    public final Hashtable<Integer,Pion> getListePion(){ 
        return listePion;
    }
    /**
     * <p>Retourne la taille du plateau</p>
     * @return Int
     */
    public final int getTaille(){ 
        return taille;
    }
}
