package com.zainic.zainiship.audio;

public class Audio {
    public static Sound PLAYER_SHOOT;
    public static Sound ENEMY_SHOOT;
    public static Sound PLAYER_EXPLOSION;
    public static Sound ENEMY_EXPLOSION;
    public static MusicPlayer MUSIC_GAME;

    public static void init() {
        // paths relative to project root or on classpath
        PLAYER_SHOOT = new Sound("res/sounds/player_shoot.wav");
        PLAYER_EXPLOSION = new Sound("res/sounds/player_explode.wav");
        ENEMY_SHOOT = new Sound("res/sounds/enemy_shoot.wav");
        ENEMY_EXPLOSION = new Sound("res/sounds/enemy_explode.wav");
        MUSIC_GAME = new MusicPlayer("res/sounds/play_music.wav");
        if (MUSIC_GAME != null) {
            MUSIC_GAME.setVolume(1.0f);
        }
        if (PLAYER_SHOOT != null) {
            PLAYER_SHOOT.setVolume(-5.0f);
        }
        if (ENEMY_SHOOT != null) {
            ENEMY_SHOOT.setVolume(-5.0f);
        }
    }
}
