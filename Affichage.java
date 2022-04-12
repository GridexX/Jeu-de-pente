import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

/**
 * <b>Affichage est la classe gérant toute la partie interface graphique et les différentes scènes :</b>
 * <ul>
 * <li>Ecran d'accueil</li>
 * <li>Scène de jeu</li>
 * <li>Menu de Victoire</li>
 * </ul>
 * <p>Elle est directement liée à la classe Thème et utilise ses données pour réaliser l'affichage</p> 
 * @see class Theme
 * <small><p>Diagramme de classe : </p></small>
 * <img src="./doc/doc_diagramme_class_jeu_de_pente.png" alt="Diagramme de classe" />
 * @author Dokarus, GridexX
*/
public class Affichage extends JPanel implements ChangeListener
{
    /**
     * Une instance de la classe Thème, d'où l'on va récupérer les images pour les afficher
     */
    public Theme theme;

    /**
     * Les différents boutons pour l'écran d'accueil et de victoire
     */
    public JButton lancerPartie, parametres, quitter;
    public JButton recomPartie, menu, quit; 

    /**
     * La variable pour indiquer quel bouton du menu est sélectionné
     */
    public int choixMenu=0;

    /**
     * L'entier pour connaitre l'état du jeu et éciter de relancer en boucle le son de victoire
     * @see void afficheVictoire(Graphics g)
     */
    private int enterVictory;

    /**
     * Le numéro d'image de victoire
     */
    private int numImgVictoire;

    /**
     * Largeur et hauteur de la fenêtre
     */
    private int width, height;

    /**
     * <p>Constructeur de la  classe Affichage</p>
     * @param _width La largeur de la fenêtre de jeu
     * @param _height La hauteur de la fenêtre de jeu
     */
    public Affichage(int _width, int _height)
    {
        width = _width;
        height = _height;
        theme = new Theme("classique", width, height);

        lancerPartie = new JButton();
        parametres = new JButton();
        quitter = new JButton();

        setLayout(null);

        recomPartie = new JButton();
        menu = new JButton();
        quit = new JButton();

        //Met les boutons en transparent pour ne garder que leur propriétés 'hover' et 'clique' -> voir le contructeur de FenetreGraphique et JeuDeuPente.clique
        lancerPartie.setOpaque(false);
        recomPartie.setOpaque(false);
        parametres.setOpaque(false);
        menu.setOpaque(false);
        quit.setOpaque(false);
        quitter.setOpaque(false);

        enterVictory=0;
        numImgVictoire=0;

        setBoutons(width, height);
    }

    /**
     * <p>Fonction qui définit la position et la taille des boutons en fonction de la taille de la fenêtre</p>
     * @param w La largeur de la fenêtre de jeu
     * @param h La hauteur de la fenêtre de jeu
     */
    public void setBoutons(int w, int h)
    {
        lancerPartie.setBounds(w*1047/1600, h*278/900, w*389/1600, h*80/900);
        parametres.setBounds(w*1047/1600, h*358/900, w*389/1600, h*80/900);
        quitter.setBounds(w*1047/1600, h*438/900, w*389/1600, h*80/900);
        recomPartie.setBounds(w*3/8, h/2-h/9, w/4, h/9);
        menu.setBounds(w*3/8, h/2, w/4, h/9);
        quit.setBounds(w*3/8, h/2+h/9, w/4, h/9);

    }

    /**
     * <p>Fonction qui définit la scène à afficher </p>
     * @param g La zone graphique àù dessiner les éléments
     */
    public void paint(Graphics g)
	{
            if(JeuDePente.getEnJeu())
            {
                afficheSceneDeJeu(g);
            }
            else
            {
                if(JeuDePente.aGagne){
                    ++enterVictory;
                    afficheVictoire(g);  
                }
                else
                    afficheAccueil(g);
            }
    }

    /**
     * <p>Fonction qui affiche les éléments en cas de victoire d'un joueur</p>
     * @param g La zone graphique àù dessiner les éléments
     */
    public void afficheVictoire(Graphics g)
    {
        Image imgFond;
        Image imgTexteJoueurGagnant;
        Image imgMenu = JeuDePente.getTheme().getMenuVictoire(choixMenu);

        //Si c'est le 1er affichage, on lance le bruitage de victoire, on mute la musique et on définit une image de victoire random 
        if(enterVictory==1){
            JeuDePente.setVolumeMus(0);

            if( JeuDePente.j1aGagne )
            {
                numImgVictoire = theme.getNbRandom("victoireJ1");    
            }   
            else
            {
                numImgVictoire = theme.getNbRandom("victoireJ2");
            }
        }

        //On définit les images en fonction du joueur gagnant
        if( JeuDePente.j1aGagne )
        {
            imgFond = theme.getImgVictoireJ1( numImgVictoire ) ;
            imgTexteJoueurGagnant = theme.imgPhraseVictoireJ1;
        }
        else
        {
            imgFond = JeuDePente.getTheme().getImgVictoireJ2( numImgVictoire ) ;
            imgTexteJoueurGagnant = JeuDePente.getTheme().imgPhraseVictoireJ2;
        }

        //On affiche le tout avec les boutons
        imgFond = imgFond.getScaledInstance(width, height, Image.SCALE_DEFAULT); 
        g.drawImage(imgFond, 0, 0, null);
        g.drawImage(imgTexteJoueurGagnant, width*13/120, height*1/9, null);
        g.drawImage( imgMenu, width*17/40, height*15/40, null);
        add(recomPartie);
        add(menu);
        add(quit);

    }
    
    /**
     * <p>Fonction qui affiche la scène de jeu</p>
     * @param g La zone graphique àù dessiner les éléments
     */
    public void afficheSceneDeJeu(Graphics g)
	{
        //JeuDePente.changerMusiqueFond( theme.musiqueFond );

        //On remet le volume de la musique initial et on réactualise le thème pour obtenir des nouvelles images randoms
        if( enterVictory>0)
        {
            enterVictory=0;
            setupTheme(theme.nom);
        }

        g.drawImage(theme.imgFond, 0, 0, null);
        g.drawImage(theme.imgProfilJ1, width*118/1600, height*180/900, null);
        g.drawImage(theme.imgProfilJ2, width*1340/1600, height*180/900, null);
        g.drawImage(theme.imgLabelJ1, width*1/24, height*2/5, null);
        g.drawImage(theme.imgLabelJ2, width*1/24+width*3/4+10, height*2/5, null);
        g.drawImage(theme.imgRetour, width/50, height/40, null);
        g.drawImage(theme.imgParam, width-width/13, height/40, null);
        g.drawImage(theme.imgPlateau, 0, 0, null);

        g.setColor(new Color(255,255,255));
        for(int i=1; i<=5-JeuDePente.getPoint(2); i++)//Affichage de la jauge de vie du joueur 1 (en fonction des points du joueur 2)
        {
            g.fillRect(width*1/10-10, height/2+((5-i)*(width/6)/5), width*1/24, (width/6)/5);
        }
        for(int i=1; i<=5-JeuDePente.getPoint(1); i++)//Affichage de la jauge de points du joueur 2
        {
            g.fillRect(width*1/10+10+width*3/4, height/2+((5-i)*(width/6)/5), width*1/24, (width/6)/5);
        }
        g.drawImage(theme.imgJaugeJoueur, width*1/10-10, height/2, null);
        g.drawImage(theme.imgJaugeJoueur, width*1/10+10+width*3/4, height/2, null);
        g.drawImage(theme.imgVie, width*1/9-15, height*4/5, null);
        g.drawImage(theme.imgVie, width*1/9+5+width*3/4, height*4/5, null);

        for (Enumeration<Pion> e = JeuDePente.getListePion().elements(); e.hasMoreElements();) //Affichage des pions
        {          
            Pion p = e.nextElement();
            if(p.getCouleurPion()==Color.WHITE){
                g.drawImage(theme.getPionJ1(), JeuDePente.decalageX+p.getXPion()*JeuDePente.tailleCase+1, JeuDePente.decalageY+p.getYPion()*JeuDePente.tailleCase, null);
            }
            else{
                g.drawImage(theme.getPionJ2(), JeuDePente.decalageX+p.getXPion()*JeuDePente.tailleCase+1, JeuDePente.decalageY+p.getYPion()*JeuDePente.tailleCase, null);
            }
        }
    }

    /**
     * <p>Fonction qui affiche l'écran d'accueil</p>
     * @param g La zone graphique àù dessiner les éléments
     */
    public void afficheAccueil(Graphics g)
    {
        if( enterVictory>0)
        {
            enterVictory=0;
        }
        //JeuDePente.changerMusiqueFond( theme.musiqueMenu );

        Image imgMenu = JeuDePente.getTheme().getMenu(choixMenu);
        g.drawImage(theme.imgAccueil, 0, 0, null);
        g.drawImage(imgMenu, width*27/40, height*37/120, null);
        add(lancerPartie);
        add(parametres);
        add(quitter);
    }

    /**
     * <p>Fonction qui affiche un Warning de confirmation</p>
     */
    public void afficheConfirmation(boolean vaMenu)
    {
        String msgWarning [][] = { { "Voulez-vous vraiment retourner au menu ? L'avancement de la partie sera perdu", "Retourner au menu" } ,
                                   { "Voulez-vous vraiment réinitialiser la partie ? L'avancement de la partie sera perdu", "Réinitialiser la partie" } } ;
        
        int numPhrase=1;    

        if(vaMenu)
            numPhrase=0;  

        String textesBoutons[]={ "Continuer", "Annuler",};
        int retour = JOptionPane.showOptionDialog(null,
                msgWarning[numPhrase][0],
                msgWarning[numPhrase][1],
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null,
                textesBoutons, textesBoutons[0]);
        if( retour==JOptionPane.YES_OPTION && vaMenu)
        {
            JeuDePente.changerModeDeJeu();
        }
    }

    /**
     * <p>Fonction qui affiche les différents paramètres de jeu</p>
     */
    public String [] afficheParametres()
    {
        String param[]= { "", "", "", ""};
        JPanel paramPanel = new JPanel(new GridLayout(0, 2));

        //Paramètres du thème
        JLabel labelTheme = new JLabel("Theme de la partie");
        String s1[] = { "Classique", "Minecraft", "Star-Wars" };
        JComboBox<String> themeComboBox = new JComboBox<>(s1);

        for(int i=0; i<s1.length; ++i)    //Pour afficher dans le menu le nom du thème sélectionné
        {
            if( s1[i].equalsIgnoreCase(theme.nom))      
                themeComboBox.setSelectedIndex(i);
        }

        paramPanel.add(labelTheme);
        paramPanel.add(themeComboBox);

        //Paramètre de la résolution d'affichage
        JLabel labelRes = new JLabel("Résolution");
        String s2[] = { "960x540",  "1366x768",  "1600x900", "1920x1080", "4096x2304"};
        JComboBox<String> resComboBox = new JComboBox<>(s2);
        int res[][] = { {960,  1366, 1600, 1920, 4096} ,
                        {540,  768, 900, 1080, 2304 } } ;
        String resActuelle = width + "x" + height ; 
        
        for(int i=0; i<s2.length; ++i)
        {
            if( s2[i].equalsIgnoreCase(resActuelle) )    //Pour afficher la résolution courante
                resComboBox.setSelectedIndex(i);
        }

        paramPanel.add(labelRes);
        paramPanel.add(resComboBox);

        //Slider pour ajuster le volume de la musique
        JLabel labelVolume = new JLabel("Volume de la musique");
        JSlider sliderVolume = new JSlider(JSlider.HORIZONTAL,0, 50, JeuDePente.musique.getVolume());
        sliderVolume.setName("sliderMusique");
        sliderVolume.addChangeListener(this);
        paramPanel.add(labelVolume);
        paramPanel.add(sliderVolume);

        //Slider pour ajuster le volume des effects sonores
        JLabel labelVolumeB = new JLabel("Volume des effets sonores");
        JSlider sliderVolumeB = new JSlider(JSlider.HORIZONTAL,0, 50, JeuDePente.bruitage.getVolume());
        sliderVolumeB.setName("sliderBruitages");
        sliderVolumeB.addChangeListener(this);
        paramPanel.add(labelVolumeB);
        paramPanel.add(sliderVolumeB);

        //Checkbox pour réinitialiser la partie
        JLabel labelReinitialise = new JLabel("Reinitialiser la partie ?  ");
        Checkbox checkReinitialiser = new Checkbox("", false);
        if(JeuDePente.getEnJeu()){
            paramPanel.add(labelReinitialise);
            paramPanel.add(checkReinitialiser);
        }
        String textesBoutons[]={ "Confirmer", "Annuler",};
        int retour = JOptionPane.showOptionDialog(null,
                     paramPanel,
                     "Parametres de jeu",
                     JOptionPane.YES_NO_OPTION,
                     JOptionPane.PLAIN_MESSAGE, null,
                     textesBoutons, textesBoutons[0]
            );

        if( retour==JOptionPane.YES_OPTION)  //Renvoie des paramètres à la fenêtre graphique
        {
            param[0] = (String) themeComboBox.getSelectedItem();    //nom du thème 
            param[1] = String.valueOf( res[0][resComboBox.getSelectedIndex()] );    //Largeur de l'affichage
            param[2] = String.valueOf( res[1][resComboBox.getSelectedIndex()] );    //Hauteur de l'affichage

            if(checkReinitialiser.getState())    //Checkbox de réinitialisation de la partie
            {
                param[3] = "true";
                afficheConfirmation(false);
            }
            else
            {
                param[3] = "false";
            }
        }
        return param;
    }

    /**
     * <p>Fonction qui récupère les valeurs des sliders et ajuste les volumes en fonction</p>
     * @param e L'évènement associé aux sliders
     */
    public void stateChanged(ChangeEvent e)
    {
        JSlider source = (JSlider)e.getSource();
        if(!source.getValueIsAdjusting())
        {
            int volume = (int)source.getValue();
            if ( source.getName().equals("sliderMusique")) 
   
                JeuDePente.musique.setVolume(volume);
            else
                JeuDePente.bruitage.setVolume(volume);
        };

    }

    /**
     * <p>Fonction qui redéfinit le thème à n'importe quelle moment de la partie</p>
     * @param nomTheme
     */
    public void setupTheme(String nomTheme)
    {
        theme.setupTheme(nomTheme, width, height);     
    }
    /**
     * Mutateur de la largeur de la classe Affichage
     * @param _width la nouvelle largeur
     */
    public void setWidth(int _width)
    { width = _width; }

    /**
     * Mutateur de la hauteur de la classe Affichage
     * @param _height la nouvelle hauteur
     */
    public void setHeight(int _height)
    { height = _height; }
}


