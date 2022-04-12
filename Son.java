import javax.sound.sampled.*;
import java.io.*;

/**
 * <b>Son est la classe qui gère tout ce qui est lié à l'audio</b>
 * <p>Elle permet de jouer un son en parallèle du jeu, afin qu'il puisse y avoir sans interrompre la partie</p>
 * <p>Il y a deux types de sons : </p>
 * <ul>
 * <li>La musique qui est jouée en continue</li>
 * <li>Le bruitage qui est joué une seule fois</li>
 * </ul>
 * 
 * @author GridexX
 */
public class Son implements Runnable
{
    /**
     * La taille du buffer pour le son
     */
    private final int BUFFER_SIZE = 128000;

    /**
     * Le fichier audio
     */
    private File soundFile;

    /**
     * Le flux audio
     */
    private AudioInputStream audioStream;

    /**
     * Le format du fichier son
     */
    private AudioFormat audioFormat;

    /**
     * Le flux de données
     */
    private SourceDataLine sourceLine;

    /**
     * Le contrôle du volume
     */
    private FloatControl gain;

    /**
     * La valeur du volume
     * @see int getVolume()
     * @see void setVolume(int _volume)
     */
    private float volume=10.0f;

    /**
     * Le chemin du fichier son
     * @see void setPath(String _soundPath)
     */
    private String soundPath;

    /**
     * Définit le son comme bruitage ou musique
     * @see boolean estLoopable()
     * @see void setEstLoopable(boolean _estLoopable)
     */
    private boolean estBruitage = true;

    /**
     * <p>Fonction appelé au démarrage du thread et qui appelle le constructeur de la classe</p>
     */
    @Override
    public void run() 
    {
        if(soundPath==null)
            if(!estBruitage)
                soundPath="Images/musique_menu.wav";
            else
            soundPath="Images/null.wav";
        playSong();
    }
    /**
     * <p>Fonction qui se charge de l'affectation et de l'instenciation des données membres</p>
     * <p>Se charge de : </p>
     * <ul>
     * <li>Ajuster le volume à n'importe quel moment de la lecture</li>
     * <li>Lire le fichier depuis le début si le chemin change pendant la lecture</li>
     * <li>Lire le son en boucle si est définit comme musique</li>
     * <li></li>
     * </ul> 
     */
    public void playSong()
    {
        try {
            String savePath=soundPath;
            soundFile = new File(soundPath);
            audioStream = AudioSystem.getAudioInputStream(soundFile);
            audioFormat = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
            gain= (FloatControl) sourceLine.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(20.0f * (float) Math.log10( volume / 100.0 ));
            sourceLine.start();
            int nBytesRead = 0;
            byte[] abData = new byte[BUFFER_SIZE];
            while (nBytesRead != -1 && savePath==soundPath) {
                try {
                    nBytesRead = audioStream.read(abData, 0, abData.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (nBytesRead >= 0) {
                    @SuppressWarnings("unused")
                    int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
                }
            }
            sourceLine.drain();
            sourceLine.close();
        }
        catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) 
        {
            throw new RuntimeException(e);
        }
        if(!estBruitage) playSong(); //répétitions du son en boucle si musique de fond
    }

    /**
     * <p>Fonction pour changer le chemin du fichier audio</p>
     * @param _soundPath Le chemin du nouveau fichier audio à lire
     */
    public void setPath(String _soundPath)
    {
        soundPath = _soundPath;
    }

    /**
     * <p>Accesseur du type de son</p>
     * @return Un booléen qui indique si le son est joué en boucle (musique)
     */
    public boolean estLoopable()
    {
        return !estBruitage;
    }
     
    /**
     * <p>Mutateur du type de son</p>
     * @param _estLoopable Le booléen pour définir si le son doit être joué en boucle (musique)
     */
    public void setEstLoopable(boolean _estLoopable)
    {
        estBruitage = ! _estLoopable ;
    }

    /**
     * <p>Accesseur du volume</p>
     * @return Le volume sous la forme d'un entier compris entre 0 (pas de son) et 100 (volume max)
     */
    public int getVolume()
    { return (int)volume;}

    /**
     * <p>Mutateur du volume</p>
     * @param _volume Le volume sous la forme d'un entier compris entre 0 (pas de son) et 50 (volume max)
     */
    public void setVolume(int _volume)
    { volume=_volume; gain.setValue(20.0f * (float) Math.log10( volume / 100.0 )); }


}