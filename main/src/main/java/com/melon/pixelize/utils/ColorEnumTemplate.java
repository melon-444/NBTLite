package com.melon.pixelize.utils;

public interface ColorEnumTemplate {

        public int getR();
        public int getG();
        public int getB();

        public int getRGB();

        public static ColorEnumTemplate getColorByRGBVal(int color){ 
            return null;
        }

        public static int colorDistance(int r, int g, int b, ColorEnumTemplate clr) {
            int dr = Math.abs(r - clr.getR()), dg = Math.abs(g - clr.getG()), db = Math.abs(b - clr.getB());
            return dr * dr + dg * dg + db * db;
        }
}
