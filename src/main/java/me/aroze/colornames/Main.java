package me.aroze.colornames;

public class Main {
    public static void main(String[] args) {
        ColorNames colorNames = new ColorNames();
        colorNames.initialize();

        String n = colorNames.closestColorName("#000000");
        System.out.println(n);
    }
}
