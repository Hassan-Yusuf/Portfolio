package com.company;

import javax.sound.sampled.*;
import java.io.File;

public class soundManager {
    final static Clip music = getClip("music",-12);
    final static Clip gameMusic = getClip("theme2",-12); //our different music clips / sound effect clips
    final static Clip levelUp = getClip("levelup",0);
    final static Clip gameOver = getClip("theme3",-17);


    public static void play(Clip clip){ //plays clip
        clip.setFramePosition(0);
        clip.start();
    }
    public static void stop(Clip clip){
        clip.stop();
    } //stops clip
    public static void shoot(){
        final Clip laserShot = getClip("laser2",-10); //creates bullet sound instance
        play(laserShot);
    }
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
