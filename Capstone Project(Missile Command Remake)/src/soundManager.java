import javax.sound.sampled.*;
import java.io.File;

public class soundManager {
    static int randomMenuMusic;
    //Clip explosion[] = new Clip[50];
    static Clip[] explosion = new Clip[10];
    static Clip[] groundExplosion = new Clip[10];
    static Clip[] hover = new Clip[10];
    static Clip waitingMusic = getClip("waitingMusic",-12);
    static Clip[] addScore = new Clip[10];
    static final Clip backgroundNoise = getClip("backgroundNoise",-3);
    static final Clip gameOver = getClip("gameOver",0);
    static final Clip click = getClip("click",0);

    public static void initExplosions() {
        for (int i = 0; i < 10; i++) {
            explosion[i] = getClip("regularExplosion",-12);
            groundExplosion[i] = getClip("groundExplosion",-8);
        }
    }
    public static void initHovers(){
        for(int i=0;i<10;i++){
            hover[i] = getClip("click3",-8);
        }
    }
    public static void initAddScore(){
        for(int i=0;i<10;i++){
            addScore[i] = getClip("addScore",-5);
        }
    }
    private static Clip menuMusic=null;
    public static void getRandomMenuMusic() {
            randomMenuMusic = (int) (Math.random() * (5 - 1 + 1) + 1);
            String menuFileName = "music" + randomMenuMusic;
            menuMusic = getClip(menuFileName,-12);
    }
    public static void playHover(){
        for(int i=0;i<10;i++){
            if(!isPlaying(hover[i])){
                play(hover[i],false);
                break;
            }
        }
    }
    public static void playaddScore(){
        for(int i=0;i<10;i++){
            if(!isPlaying(addScore[i])){
                play(addScore[i],false);
                break;
            }
        }
    }
    public static void playGameBackground() {
        final Clip backgroundNoise = getClip("backgroundNoise",-3);
        play(backgroundNoise,true);

    }
    public static void menuMusicPlayNext() {
        if(menuMusic==null){
            getRandomMenuMusic();
            play(menuMusic,false);
        }
        else if (!isPlaying(menuMusic)) {
            getRandomMenuMusic();
            play(menuMusic,false);
        }
    }
    public static boolean isPlaying(Clip clip) {
        return clip.isRunning();

    }
    public static void explode(){
        for(int i=0;i<10;i++){
            if(!isPlaying(explosion[i])){
                play(explosion[i],false);
                break;
            }
        }
    }

    public static void stopMenuMusic(){
        stop(menuMusic);
    }

    public static void play(Clip clip,boolean repeat){ //plays clip
        clip.setFramePosition(0);
        clip.start();
        if (repeat) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.start();
        }
    }
    public static void stop(Clip clip){
        clip.stop();
    } //stops clip

    public static Clip getClip(String filename,int vol){ //handles playing all of our music / sound effects
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream clipStream = AudioSystem.getAudioInputStream(new File("res/"
                    + filename + ".wav"));
            clip.open(clipStream);
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); //these 2 lines controls volume reduction
            volume.setValue(vol);
        } catch (Exception e) { //if cant find clip then show error
            e.printStackTrace();
        }
        return clip;

    }

}
