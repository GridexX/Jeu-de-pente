import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * <b>FenetreGraphique est la classe qui instancie la classe Affichage</b>
 * <p>Elle récupère les actions effectués dans la classe Affichage</p>

 * 
 * @author Dokarus, GridexX
 */
public class FenetreGraphique extends JFrame implements ActionListener, MouseListener
{
    /**
     * Une instance de la classe Affichage
     */
    Affichage affichage;
    
    /**
     * <p>Constructeur de la classe FenetreGraphique</p>
     * @param s Le titre de la fenêtre
     * @param dimensionX La largeur de la fenêtre de jeu
     * @param dimensionY La hauteur de la fenêtre de jeu
     */
    public FenetreGraphique(String s, int dimensionX, int dimensionY)
	{
        super(s);
		setSize(dimensionX,dimensionY+31);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        affichage = new Affichage(dimensionX,dimensionY);
        
        
        setContentPane(affichage); 
        setLayout(null);

        affichage.lancerPartie.setActionCommand("lancerPartie");
        affichage.lancerPartie.addActionListener(this);
        affichage.parametres.setActionCommand("parametres");
        affichage.parametres.addActionListener(this);
        affichage.quitter.setActionCommand("quitter");
        affichage.quitter.addActionListener(this); 

        affichage.recomPartie.setActionCommand("lancerPartie");
        affichage.menu.setActionCommand("menu");
        affichage.quit.setActionCommand("quitter");
        affichage.recomPartie.addActionListener(this);
        affichage.menu.addActionListener(this);
        affichage.quit.addActionListener(this);

        affichage.lancerPartie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                affichage.choixMenu = 0;
                affichage.repaint();
            }
        });

        affichage.parametres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                affichage.choixMenu = 1;
                affichage.repaint();
            }
        });

        affichage.quitter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                affichage.choixMenu = 2;
                affichage.repaint();
            }
        });

        affichage.recomPartie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                affichage.choixMenu = 0;
                affichage.repaint();
            }
        });

        affichage.menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                affichage.choixMenu = 1;
                affichage.repaint();
            }
        });

        affichage.quit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                affichage.choixMenu = 2;
                affichage.repaint();
            }
        });
        
        setVisible(true);
        addMouseListener(this);    
    }

    /**
     * <p>Ouvre la fenêtre de paramètres (avec la fonction afficheParamètres() de Affichage), et réagi selon les choix de l'utilisateur</p>
     */
    public void fenetreParam()
    {
        String [] param = affichage.afficheParametres();
        if(!(param[0].equals("")) && !(param[0].equals(JeuDePente.getTheme().nom))){
            affichage.setupTheme(param[0]);
        }
        if((param[3].equals("true"))){
            JeuDePente.resetPartie();
        }
        if( !(param[1].equals("")) && !(param[2].equals(""))){
        changerTaille( Integer.valueOf(param[1]) , Integer.valueOf(param[2]) );
        }
    }

    /**
     * <p>Ouvre la fenêtre de confirmation de retour au menu (avec la fonction afficheConfirmation() de Affichage)</p>
     */
    public void fenetreConfirmation()
    {
        affichage.afficheConfirmation(true);
    }

    /**
     * <p>Change la taille de la fenêtre de jeu</p>
     * @param sizeX La nouvelle largeur de la fenêtre de jeu
     * @param sizeY La nouvelle hauteur de la fenêtre de jeu
     */
    public void changerTaille(int sizeX, int sizeY)
    {
        setSize(sizeX, sizeY+31);
        affichage.setWidth(sizeX);
        affichage.setHeight(sizeY);
        JeuDePente.dimensionX = sizeX;
        JeuDePente.dimensionY = sizeY;
        JeuDePente.decalageX=sizeX*1/4;
        JeuDePente.decalageY=sizeY*1/18;
        JeuDePente.tailleCase=(sizeX-2*JeuDePente.decalageX)/19;
        affichage.theme.recadrerImage(sizeX, sizeY);
        affichage.setBoutons(sizeX, sizeY);
        affichage.repaint();
    }
    
    /**
     * <p>Fonction réagissant aux différentes actions</p>
     */
    public void actionPerformed(ActionEvent evenement)
    {
        if (evenement.getActionCommand().equals("lancerPartie")){
            JeuDePente.aGagne = false;

            JeuDePente.changerModeDeJeu();
            affichage.remove(affichage.lancerPartie);
            affichage.remove(affichage.parametres);
            affichage.remove(affichage.quitter);

            affichage.remove(affichage.recomPartie);
            affichage.remove(affichage.menu);
            affichage.remove(affichage.quit);
            repaint();
            //JeuDePente.musique.setPath(affichage.theme.getMusiqueFond());
        }
        if (evenement.getActionCommand().equals("parametres")){
            fenetreParam();
        }
        if (evenement.getActionCommand().equals("quitter")){
            System.exit(0);
        }
        if(evenement.getActionCommand().equals("menu")){
            fenetreParam();
        }
    }

    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    /**
     * <p>Fonction réagissant au clique de la souris</p>
     * @param e Evenement de la souris
     */
    public void mouseClicked(MouseEvent e)
    {
        int x=e.getX();
        int y=e.getY();
        if(JeuDePente.getEnJeu()){
            JeuDePente.clique(x-8,y-31);
            affichage.repaint();
        }
    }

    /**
     * <p>Fonction Accesseur pour accéder au Theme de Affichage</p>
     * @return Theme
     */
    public Theme getTheme()
    { return affichage.theme;}
}
