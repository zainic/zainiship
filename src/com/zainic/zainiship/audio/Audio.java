package com.zainic.zainiship.audio;

public class Audio {
    public static Sound SHOOT;
    public static Sound EXPLOSION;
    public static Sound ENEMY_EXPLOSION;
    public static MusicPlayer MUSIC_GAME;

    public static void init() {
        // paths relative to project root or on classpath
        SHOOT = new Sound("res/sounds/ship_shoot.wav");
        EXPLOSION = new Sound("res/sounds/ship_explode.wav");
        ENEMY_EXPLOSION = new Sound("res/sounds/enemy_explode.wav");
        MUSIC_GAME = new MusicPlayer("res/sounds/play_music.wav");
        if (MUSIC_GAME != null) {
            MUSIC_GAME.setVolume(-3.0f);
        }
    }
}
