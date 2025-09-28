package com.melon.pixelize.utils;

public enum WplaceColor implements ColorEnumTemplate {
    black, dark_gray(0x3c3c3c), gray(0x787878), light_gray(0xd2d2d2), white(0xffffff), deep_red(0x600018),
    dark_red(0xa50e1e), red(0xed1c24), orange(0xff7f27), gold(0xf6aa09), yellow(0xf9dd3b), light_yellow(0xfffabc),
    dark_green(0x0eb968), green(0x13e67b), light_green(0x87ff5e), dark_teal(0x0c816e), teal(0x10aea6),
    light_teal(0x13e1be), cyan(0x60f7f2), dark_blue(0x28509e), blue(0x4093e4), indigo(0x6b50f6),
    light_indigo(0x99b1fb), dark_purple(0x780c99), purple(0xaa38b9), light_purple(0xe09ff9), dark_pink(0xcb007a),
    pink(0xec1f80), light_pink(0xf38da9), dark_brown(0x684634), brown(0x95682a), dark_beige(0xd18051),
    beige(0xf8b277), light_beige(0xffc5a5);

    int rgb;

    private WplaceColor(int color) {
        rgb = color;
    }

    private WplaceColor() {
        rgb = 0x000000;// black for default
    }

    public int getR() {
        return (rgb >> 16) & 0xff;
    }

    public int getG() {
        return (rgb >> 8) & 0xff;
    }

    public int getB() {
        return rgb & 0xff;
    }

    public int getRGB() {
        return rgb;
    }

    public static WplaceColor getColorByRGBVal(int color) {
        int r = (color >> 16) & 0xff, g = (color >> 8) & 0xff, b = color & 0xff;

        int similarIndex = r * r + g * g + b * b;
        /*
         * I mean that
         * 
         * int similarIndex =
         * Math.abs(r - Color.black.getR())^2 +
         * Math.abs(g - Color.black.getG())^2 +
         * Math.abs(b - Color.black.getB())^2;
         */

        WplaceColor candidateColor = WplaceColor.black;
        for (WplaceColor clr : WplaceColor.values()) {
            if (clr == black)
                continue;
            int nextSimilarIndex = ColorEnumTemplate.colorDistance(r, g, b, clr);
            if (nextSimilarIndex < similarIndex) {
                similarIndex = nextSimilarIndex;
                candidateColor = clr;
            }
        }
        return candidateColor;
    }
}