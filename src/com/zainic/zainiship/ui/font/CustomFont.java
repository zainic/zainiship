package com.zainic.zainiship.ui.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class CustomFont {

    private final String family;
    private final float size;

    public static CustomFont orbitron25 = new CustomFont("Orbitron", 20f);
    public static CustomFont orbitron20 = new CustomFont("Orbitron", 20f);
    public static CustomFont orbitron15 = new CustomFont("Orbitron", 15f);

    public CustomFont(String family, float size) {
        this.family = family;
        this.size = size;
    }

    public Font get(String variant) {
        String path = "/fonts/" + family + "/" + family + "-" + variant + ".ttf";

        try (InputStream input = CustomFont.class.getResourceAsStream(path)) {
            if (input == null) {
                throw new IllegalArgumentException("Font not found: " + path);
            }

            return Font.createFont(Font.TRUETYPE_FONT, input).deriveFont(size);
        } catch (IOException | FontFormatException exception) {
            throw new IllegalStateException("Could not load font: " + path, exception);
        }
    }
}