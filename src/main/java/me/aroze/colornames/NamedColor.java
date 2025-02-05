package me.aroze.colornames;

import me.aroze.colornames.util.ColorUtil;

public class NamedColor {
    private final String name;

    /** Lightness Component (0-100). */
    private float lightness = 0f;

    /** "a" component (-128-127). */
    private float aComponent = 0f;

    /** "b" component (-128-127). */
    private float bComponent = 0f;

    public NamedColor(String name, String hex) {
        this.name = name;

        String parsedHex = hex;
        if(!parsedHex.startsWith("#")) {
            parsedHex = "#" + parsedHex;
        }

        float[] lab = ColorUtil.hexToLAB(parsedHex);
        this.lightness = lab[0];
        this.aComponent = lab[1];
        this.bComponent = lab[2];
    }

    public NamedColor(String name, int r, int g, int b) {
        this.name = name;

        float[] lab = ColorUtil.rgbToLAB(r, g, b);
        this.lightness = lab[0];
        this.aComponent = lab[1];
        this.bComponent = lab[2];
    }

    public String getName() {
        return name;
    }

    public float getLightness() {
        return lightness;
    }

    public float getAComponent() {
        return aComponent;
    }

    public float getBComponent() {
        return bComponent;
    }
}
