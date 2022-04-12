import java.util.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Random;

/**
  *  
* <b>Theme est la classe contenant les chemins des fichiers nécessaires à l'interface</b>
* 
* <p>Elle contient principalement :</p>
* <ul>
* <li>Les Images pour les différents objets affichés</li>
* <li>Les sons pour la musique de fond et les bruitages</li>
* </ul>
*  <p>En Fonction du thème choisie (parmis les thèmes Classique, Minecraft, Star Wars), la classe Thème cherchera les fichiers dans l'arborescence correspondante</p>

* @author GridexX

*/

class Theme
{
    /** 
     * Le nom du thème (Classique / Minecraft / Star Wars)
     * @see getNom()
     */
    public String nom;

    /** 
     * La liste des images pour les pions du joueur 1
     * @see getImgPionJ1(int index)
     * @see int getNbImgPionsJ1()
     */
    private Vector<Image> listeImgPionsJ1;

    /** 
     * La liste des images pour les pions du joueur 2
     * @see getImgPionJ2(int index)
     * @see int getNbImgPionsJ2()
     */
    private Vector<Image> listeImgPionsJ2;

    /**
     * Indique quel image de pion est utilisé parmi la liste d'image de pion disponible dans le thème
     */
    private int imgPionJ1, imgPionJ2;

    /**
     * L'image des pions du joueur 1
     */
    private Image pionJ1;

    /**
     * L'image des pions du joueur 2
     */
    private Image pionJ2;

    /** 
     * La liste des images affichables en cas de victoire du joueur 1
     * @see getImgVictoireJ1(int index)
     * @see int getNbImgVictoireJ1()
     */
    private Vector<Image> listeImgVictoireJ1;

    /** 
     * La liste des images affichables en cas de victoire du joueur 2
     * @see getImgVictoireJ2(int index)
     * @see int getNbImgVictoireJ2()
     */
    private Vector<Image> listeImgVictoireJ2;

    /** 
     * La liste des images des boutons du menu d'accueil
     * @see Image getMenu(int index)
     */
    private Vector<Image> menu;

    /** 
     * La liste des images des boutons du menu de victoire
     * @see Image getMenuVictoire(int index)
     */
    private Vector<Image> menuVictoire;

    /**
     * L'image de profil du joueur 1
     */
    public Image imgProfilJ1;

    /**
     * L'image de profil du joueur 2
     */
    public Image imgProfilJ2;

    /**
     * L'image du label pour le joueur 1)
     */
    public Image imgLabelJ1; 

    /**
     * L'image du label pour le joueur 2
     */
    public Image imgLabelJ2; 

    /**
     * L'image de victoire affiché quand le joueur 1 gagne
     */
    public Image imgPhraseVictoireJ1;

    /**
     * L'image de victoire affiché quand le joueur 2 gagne)
     */
    public Image imgPhraseVictoireJ2;

    /**
     * L'image de fond de la scène de jeu
     */
    public Image imgFond; 

    /**
     * L'image d'accueil
     */
    public Image imgAccueil; 

    /**
     * L'image pour la jauge de vie du joueur
     */
    public Image imgJaugeJoueur; 

    /**
     * L'image du plateau
     */
    public Image imgPlateau;

    /**
     * L'image pour revenir au menu
     */
    public Image imgRetour;

    /**
     * L'image pour afficher les paramètres
     */
    public Image imgParam;

    /**
     * L'image pour afficher le label vie
     */
    public Image imgVie;
    
    /** 
     * La liste des chemins contenant les sons quand un pion est placé
     * @see String getSonPlacer(int index)
     * @see int getNbSonsPlacer()
     */
    private Vector<String> listeSonsPlacer;

    /** 
     * La liste des chemins contenant les sons quand un pion est mangé
     * @see String getSonManger(int index)
     * @see int getNbSonsManger()
     */
    private Vector<String> listeSonsManger;

    /** 
     * Le chemin contenant la musique de victoire
     */
    public String musiqueVictoire;

    /** 
     * Le chemin contenant la musique du menu
     */
    public String musiqueMenu;

    /** 
     * Le chemin contenant la musique de jeu
     */
    public String musiqueFond;

    /**
     * La graine liée au randomisateur pour obtenir des sons/images aléatoires
     * @see int getNbrandom(String fonction)
     */
    private Random rand;

    /**
     * <p>Constructeur de la classe thème</p>
     * @param nomTheme nom du thème
     * @param dX dimension x de la fenêtre
     * @param dY dimension y de la fenêtre
     */
    public Theme(String nomTheme, int dX, int dY)
    {
        nom=nomTheme;
        rand = new Random(System.currentTimeMillis()*hashCode());
        listeImgPionsJ1 = new Vector<Image>();
        listeImgPionsJ2 = new Vector<Image>(); 
        listeImgVictoireJ1 = new Vector<Image>();
        listeImgVictoireJ2 = new Vector<Image>();
        menuVictoire = new Vector<Image>();
        menu = new Vector<Image>();

        listeSonsPlacer = new Vector<String>();
        listeSonsManger = new Vector<String>();

        String listThemes [] = new File("themes/").list();

        boolean aTrouveTheme = false;

        for(int i=0 ; i<listThemes.length && !aTrouveTheme ; ++i)   //Parcourir la liste des dossiers thèmes pour vérifier que celui passé en paramètre existe
        {
            aTrouveTheme = ( nomTheme.compareToIgnoreCase(listThemes[i]) == 0 );       
        }

        if(!aTrouveTheme)
            nom= "classique";

        setupTheme(nomTheme, dX, dY);
        
    }

    /**
     * <p>Fonction random pour renvoyer un nombre random en fonction de la chaine de caractère passé en paramètre</p>
     * @param dossier Le dossier où effectuer l'opération
     * @return Un nombre aléatoire compris entre 0 et le nombre de fichiers dans le dossier passé en paramètre
     */
    public int getNbRandom(String dossier)
    {
        
        int borneSup=0;
        if(dossier.equals("pionsJ1")) borneSup = getNbImgPionsJ1();
        else if(dossier.equals("pionsJ2")) borneSup = getNbImgPionsJ2();
        else if(dossier.equals("victoireJ1")) borneSup = getNbImgVictoireJ1();
        else if(dossier.equals("victoireJ2")) borneSup = getNbImgVictoireJ2();

        else if(dossier.equals("manger")) borneSup = getNbSonsManger();
        else if(dossier.equals("placer")) borneSup = getNbSonsPlacer();
        else System.out.println("Erreur dans la fonction getNbRandom: ");
        return rand.nextInt(borneSup);
    }

    /**
     * <p><Fonction qui instancie chaque donnée membre de la classe</p>
     * @param nomTheme nom du thème
     * @param dimensionX dimension x de la fenêtre
     * @param dimensionY dimension y de la fenêtre
     */
    public void setupTheme(String nomTheme, int dimensionX, int dimensionY)
    {
        nom = nomTheme.toLowerCase();
        nomTheme = nom;

        listeImgPionsJ1.clear();
        listeImgPionsJ2.clear();
        listeImgVictoireJ1.clear();
        listeImgVictoireJ2.clear();
        listeSonsPlacer.clear();
        listeSonsManger.clear();
        menuVictoire.clear();
        menu.clear();
         
        try{
            imgPlateau = ImageIO.read( new File("Images/PlateauJeu.png") );
            imgAccueil = ImageIO.read(new File("Images/accueil.jpg"));
            imgRetour = ImageIO.read(new File("Images/back2.png"));
            imgParam = ImageIO.read(new File("Images/param2.png"));
            imgJaugeJoueur = ImageIO.read(new File("Images/Jauge_Ordinaire.png"));
            imgVie = ImageIO.read(new File("Images/vie.png"));
            imgPhraseVictoireJ1  = ImageIO.read(new File("Images/phraseVictoireJ1.png"));
            imgPhraseVictoireJ2  = ImageIO.read(new File("Images/phraseVictoireJ2.png"));
            imgFond = ImageIO.read( new File("themes/"+nomTheme+"/images/background.jpg") );
            imgProfilJ1 = ImageIO.read( new File("themes/"+nomTheme+"/images/iconeJ1.png") );
            imgLabelJ1 = ImageIO.read(new File("Images/labelJ1.png"));
            imgProfilJ2 = ImageIO.read( new File("themes/"+nomTheme+"/images/iconeJ2.png") );
            imgLabelJ2 = ImageIO.read(new File("Images/labelJ2.png"));

            String rep [] = { "pionsJ1/", "pionsJ2/", "victoireJ1/", "victoireJ2/"};
            String listePathPionsJ1 [] = new File("themes/"+nomTheme+"/images/pionsJ1/").list();
            String listePathPionsJ2 [] = new File("themes/"+nomTheme+"/images/pionsJ2/").list();
            String listePathVictoireJ1 [] = new File("themes/"+nomTheme+"/images/victoireJ1/").list();
            String listePathVictoireJ2 [] = new File("themes/"+nomTheme+"/images/victoireJ2/").list();

            String pathImage [] [] = { listePathPionsJ1, listePathPionsJ2, listePathVictoireJ1, listePathVictoireJ2};

            for( int i=0; i<pathImage.length; ++i)
            {
                for( int j=0; j<pathImage[i].length; ++j)
                {
                    if(i==0) listeImgPionsJ1.add( ImageIO.read( new File("themes/"+nomTheme+"/images/"+rep[i]+pathImage[i][j]) ));
                    else if(i==1) listeImgPionsJ2.add(ImageIO.read( new File("themes/"+nomTheme+"/images/"+rep[i]+pathImage[i][j]) ));
                    else if(i==2) listeImgVictoireJ1.add(ImageIO.read( new File("themes/"+nomTheme+"/images/"+rep[i]+pathImage[i][j]) ));
                    else if(i==3) listeImgVictoireJ2.add(ImageIO.read( new File("themes/"+nomTheme+"/images/"+rep[i]+pathImage[i][j]) ));
                    
                }
            }

            for ( int i=1; i<4; ++i)
            {
                menuVictoire.add( ImageIO.read( new File("Images/menuVictoire" + i + ".png") ));
                menu.add( ImageIO.read( new File("Images/menuVictoire" + i + ".png") ));
            }

            //Changer l'image random pour les pions du joueur 1 et 2
            imgPionJ1=getNbRandom("pionsJ1");    
            imgPionJ2=getNbRandom("pionsJ2");
            pionJ1=getImgPionJ1(imgPionJ1).getScaledInstance(JeuDePente.tailleCase, JeuDePente.tailleCase, Image.SCALE_DEFAULT);
            pionJ2=getImgPionJ2(imgPionJ2).getScaledInstance(JeuDePente.tailleCase, JeuDePente.tailleCase, Image.SCALE_DEFAULT);

        }
        catch(IOException e){
		    e.printStackTrace();
        }

        //pour les sons
        musiqueFond = "themes/"+nomTheme+"/sons/musique_fond.wav"; 
        musiqueMenu = "Images/musique_menu.wav";
        musiqueVictoire = "themes/"+nomTheme+"/sons/victoire.wav";
        String listePathSonsPlacer [] = new File("themes/"+nomTheme+"/sons/placer/").list();
        String listePathSonsManger [] = new File("themes/"+nomTheme+"/sons/manger/").list();

        for( int i=0; i<listePathSonsPlacer.length; ++i)
        {
            listeSonsPlacer.add( "themes/"+nomTheme+"/sons/placer/"+listePathSonsPlacer[i]);
        }
        for( int i=0; i<listePathSonsManger.length; ++i)
        {
            listeSonsManger.add( "themes/"+nomTheme+"/sons/manger/"+listePathSonsManger[i]);
        }

        recadrerImage(dimensionX, dimensionY);
    }

    /**
     * <p>Fonction pour recadrer les images en fonction des dimensions de la fenêtre</p>
     * @param dimensionX dimension x de la fenêtre
     * @param dimensionY dimension y de la fenêtre
     */
    public void recadrerImage(int dimensionX, int dimensionY)
    {
            imgPlateau  = imgPlateau.getScaledInstance(dimensionX, dimensionY, Image.SCALE_DEFAULT);
            imgAccueil = imgAccueil.getScaledInstance(dimensionX, dimensionY, Image.SCALE_DEFAULT);
            imgRetour = imgRetour.getScaledInstance(dimensionX/20, dimensionX/20, Image.SCALE_DEFAULT);
            imgParam = imgParam.getScaledInstance(dimensionX/20, dimensionX/20, Image.SCALE_DEFAULT);
            imgJaugeJoueur = imgJaugeJoueur.getScaledInstance(dimensionX*1/24, dimensionX*1/6, Image.SCALE_DEFAULT);

            menuVictoire.set(0 , menuVictoire.elementAt(0).getScaledInstance(dimensionX*19/100, dimensionY*27/100, Image.SCALE_DEFAULT) );
            menuVictoire.set(1 , menuVictoire.elementAt(1).getScaledInstance(dimensionX*19/100, dimensionY*27/100, Image.SCALE_DEFAULT) );
            menuVictoire.set(2 , menuVictoire.elementAt(2).getScaledInstance(dimensionX*19/100, dimensionY*27/100, Image.SCALE_DEFAULT) );
            menu.set(0 , menu.elementAt(0).getScaledInstance(dimensionX*19/100, dimensionY*27/100, Image.SCALE_DEFAULT) );
            menu.set(1 , menu.elementAt(1).getScaledInstance(dimensionX*19/100, dimensionY*27/100, Image.SCALE_DEFAULT) );
            menu.set(2 , menu.elementAt(2).getScaledInstance(dimensionX*19/100, dimensionY*27/100, Image.SCALE_DEFAULT) );
            
            imgPhraseVictoireJ1  = imgPhraseVictoireJ1.getScaledInstance(dimensionX*75/100, dimensionY*64/1000, Image.SCALE_DEFAULT);
            imgPhraseVictoireJ2  = imgPhraseVictoireJ2.getScaledInstance(dimensionX*75/100, dimensionY*64/1000, Image.SCALE_DEFAULT);
            imgProfilJ1 = imgProfilJ1.getScaledInstance(dimensionX*8/100, dimensionY*14/100, Image.SCALE_DEFAULT);
            imgProfilJ2 = imgProfilJ2.getScaledInstance(dimensionX*8/100, dimensionY*14/100, Image.SCALE_DEFAULT);
            imgVie = imgVie.getScaledInstance(dimensionX/40, dimensionY/40, Image.SCALE_DEFAULT);
            imgFond = imgFond.getScaledInstance(dimensionX, dimensionY, Image.SCALE_DEFAULT);
            imgLabelJ1 = imgLabelJ1.getScaledInstance(dimensionX*1/7, dimensionY*1/20, Image.SCALE_DEFAULT);
            imgLabelJ2 = imgLabelJ2.getScaledInstance(dimensionX*1/7, dimensionY*1/20, Image.SCALE_DEFAULT);
            pionJ1=getImgPionJ1(imgPionJ1).getScaledInstance(JeuDePente.tailleCase, JeuDePente.tailleCase, Image.SCALE_DEFAULT);
            pionJ2=getImgPionJ2(imgPionJ2).getScaledInstance(JeuDePente.tailleCase, JeuDePente.tailleCase, Image.SCALE_DEFAULT);
    }

    /**
     * <p>Fonction Accesseur pour accéder aux images du menu de Victoire</p>
     * @param index numéro de l'image
     * @return l'image du menu spécifiée
     */
    public Image getMenuVictoire(int index)
    { return menuVictoire.elementAt(index);} 

    /**
     * <p>Fonction Accesseur pour accéder aux images du menu</p>
     * @param index numéro de l'image
     * @return l'image du menu spécifiée
     */
    public Image getMenu(int index)
    { return menu.elementAt(index);} 

    /**
     * <p>Fonction Accesseur pour obtenir le nombre d'images différentes du pion pour le joueur 1</p>
     * @return le nombre d'images différentes
     */
    public int getNbImgPionsJ1()
    { return listeImgPionsJ1.size();}

    /**
     * <p>Fonction Accesseur pour obtenir le nombre d'images différentes du pion pour le joueur 2</p>
     * @return le nombre d'images différentes
     */
    public int getNbImgPionsJ2()
    { return listeImgPionsJ2.size();}

    /**
     * <p>Fonction Accesseur pour obtenir le nombre d'images différentes de victoire pour le joueur 1</p>
     * @return le nombre d'images différentes
     */
    public int getNbImgVictoireJ1()
    { return listeImgVictoireJ1.size();}

    /**
     * <p>Fonction Accesseur pour obtenir le nombre d'images différentes de victoire pour le joueur 2</p>
     * @return le nombre d'images différentes
     */
    public int getNbImgVictoireJ2()
    { return listeImgVictoireJ2.size();}

    /**
     * <p>Fonction Accesseur pour accéder aux images de pion du joueur 1</p>
     * @param index numéro de l'image
     * @return l'image du pion spécifiée
     */
    public Image getImgPionJ1(int index)
    { return listeImgPionsJ1.elementAt(index);}

    /**
     * <p>Fonction Accesseur pour accéder aux images de pion du joueur 2</p>
     * @param index numéro de l'image
     * @return l'image du pion spécifiée
     */
    public Image getImgPionJ2(int index)
    { return listeImgPionsJ2.elementAt(index);}

    /**
     * <p>Fonction Accesseur pour accéder à l'image de pion du joueur 1</p>
     * @return l'image du pion du joueur 1
     */
    public Image getPionJ1()
    { return pionJ1;}

    /**
     * <p>Fonction Accesseur pour accéder à l'image de pion du joueur 2</p>
     * @return l'image du pion du joueur 2
     */
    public Image getPionJ2()
    { return pionJ2;} 

    /**
     * <p>Fonction Accesseur pour accéder aux images de victoire du joueur 1</p>
     * @param index numéro de l'image
     * @return l'image de victoire spécifiée
     */
    public Image getImgVictoireJ1(int index)
    { return listeImgVictoireJ1.elementAt(index); }

    /**
     * <p>Fonction Accesseur pour accéder aux images de victoire du joueur 2</p>
     * @param index numéro de l'image
     * @return l'image de victoire spécifiée
     */
    public Image getImgVictoireJ2(int index)
    { return listeImgVictoireJ2.elementAt(index);}

    /**
     * <p>Fonction Accesseur pour obtenir le nombre de sons différents en cas de mangeage d'un pion</p>
     * @return le nombre de sons différents
     */
    public int getNbSonsManger()
    { return listeSonsManger.size();}

    /**
     * <p>Fonction Accesseur pour obtenir le nombre de sons différents en cas de plaçage d'un pion</p>
     * @return le nombre de sons différents
     */
    public int getNbSonsPlacer()
    { return listeSonsPlacer.size();}

    /**
     * <p>Fonction Accesseur pour accéder aux sons de mangeage</p>
     * @param index numéro du son
     * @return le chemin du son spécifié
     */
    public String getSonManger(int index)
    { return listeSonsManger.elementAt(index);}

    /**
     * <p>Fonction Accesseur pour accéder aux sons de plaçage</p>
     * @param index numéro du son
     * @return le chemin du son spécifié
     */
    public String getSonPlacer(int index)
    { return listeSonsPlacer.elementAt(index);}
   

}
