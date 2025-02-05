package me.aroze.colornames.util;

public class ColorUtil {
    private ColorUtil() { }

    public static float[] hexToLAB(String hex) {
        int rgb = Integer.parseInt(hex.substring(1), 16);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        return rgbToLAB(r, g, b);
    }

    public static float[] rgbToLAB(int r, int g, int b) {
        float[] xyz = rgbToXYZ(r, g, b);
        return xyzToLAB(xyz[0], xyz[1], xyz[2]);
    }

    private static float[] rgbToXYZ(int r, int g, int b) {
        float rr = pivotRGB(r / 255f);
        float gg = pivotRGB(g / 255f);
        float bb = pivotRGB(b / 255f);

        float x = (rr * 0.4124564f + gg * 0.3575761f + bb * 0.1804375f) * 100f;
        float y = (rr * 0.2126729f + gg * 0.7151522f + bb * 0.0721750f) * 100f;
        float z = (rr * 0.0193339f + gg * 0.1191920f + bb * 0.9503041f) * 100f;

        return new float[]{x, y, z};
    }

    private static float[] xyzToLAB(float x, float y, float z) {
        x /= 95.047f;
        y /= 100.000f;
        z /= 108.883f;

        x = pivotXYZ(x);
        y = pivotXYZ(y);
        z = pivotXYZ(z);

        float l = (116f * y) - 16f;
        float a = 500f * (x - y);
        float b = 200f * (y - z);

        return new float[]{l, a, b};
    }

    private static float pivotRGB(float value) {
        return (value > 0.04045f) ? (float)Math.pow((value + 0.055f) / 1.055f, 2.4f) : value / 12.92f;
    }

    private static float pivotXYZ(float value) {
        return (value > 0.008856f) ? (float)Math.pow(value, 1f / 3f) : (7.787f * value) + (16f / 116f);
    }
}