package com.zainic.zainiship.audio;

import javax.sound.sampled.*;
import java.io.*;

public class Sound {
    private AudioFormat format;
    private byte[] audioData;
    private float defaultGain = 0f; // dB
    private Clip loopClip;

    public Sound(String path) {
        try (AudioInputStream ais = getAudioInputStream(path)) {
            if (ais == null) return;
            format = ais.getFormat();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int read;
            while ((read = ais.read(buf)) != -1) {
                baos.write(buf, 0, read);
            }
            audioData = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AudioInputStream getAudioInputStream(String path) throws IOException, UnsupportedAudioFileException {
        // Try file system first
        try {
            File f = new File(path);
            if (f.exists()) {
                return AudioSystem.getAudioInputStream(f);
            }
        } catch (Exception ignored) {}

        // Try classpath resource
        InputStream is = Sound.class.getClassLoader().getResourceAsStream(path);
        if (is != null) {
            return AudioSystem.getAudioInputStream(new BufferedInputStream(is));
        }
        return null;
    }

    // Play this sound; allows overlapping by creating a fresh Clip per play
    public void play() {
        if (audioData == null || format == null) return;
        try {
            AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(audioData), format, audioData.length / format.getFrameSize());
            Clip c = AudioSystem.getClip();
            c.open(ais);
            applyGain(c, defaultGain);
            c.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP || event.getType() == LineEvent.Type.CLOSE) {
                    c.close();
                }
            });
            c.start();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    // Loop this sound continuously (keeps a dedicated clip so it can be stopped)
    public void loop() {
        if (audioData == null || format == null) return;
        stopLoop();
        try {
            AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(audioData), format, audioData.length / format.getFrameSize());
            loopClip = AudioSystem.getClip();
            loopClip.open(ais);
            applyGain(loopClip, defaultGain);
            loopClip.loop(Clip.LOOP_CONTINUOUSLY);
            loopClip.start();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void stopLoop() {
        if (loopClip != null) {
            loopClip.stop();
            loopClip.close();
            loopClip = null;
        }
    }

    public void stop() {
        stopLoop();
    }

    public void setVolume(float db) {
        defaultGain = db;
        if (loopClip != null) applyGain(loopClip, db);
    }

    private void applyGain(Clip c, float db) {
        try {
            if (c.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl ctrl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
                ctrl.setValue(db);
            }
        } catch (IllegalArgumentException ignored) {}
    }

    public void close() {
        stopLoop();
        audioData = null;
        format = null;
    }
}
