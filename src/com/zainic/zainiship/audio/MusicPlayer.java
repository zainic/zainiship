package com.zainic.zainiship.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MusicPlayer {
    private Clip clip;

    public MusicPlayer(String path) {
        try {
            AudioInputStream ais = getAudioInputStream(path);
            if (ais == null) return;
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private AudioInputStream getAudioInputStream(String path) throws IOException, UnsupportedAudioFileException {
        try {
            File f = new File(path);
            if (f.exists()) {
                return AudioSystem.getAudioInputStream(f);
            }
        } catch (Exception ignored) {}
        InputStream is = MusicPlayer.class.getClassLoader().getResourceAsStream(path);
        if (is != null) {
            return AudioSystem.getAudioInputStream(is);
        }
        return null;
    }

    public void playLoop() {
        if (clip == null) return;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    public void stop() {
        if (clip == null) return;
        clip.stop();
        clip.setFramePosition(0);
    }

    public void setVolume(float db) {
        if (clip == null) return;
        try {
            FloatControl ctrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            ctrl.setValue(db);
        } catch (IllegalArgumentException ignored) {}
    }
}
