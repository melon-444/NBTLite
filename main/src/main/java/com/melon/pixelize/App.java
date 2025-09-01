package com.melon.pixelize;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.AWTException;

import java.awt.Robot;

public class App {

    static Robot rbt;
    static {
        try {
            rbt = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    static final int pixHMost = 64, pixWMost = 64,THREAD_CNT=5;
    static int pixelContains = 10;

    volatile static int current;

    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("ignore/input.png"));
            int h = image.getHeight(), w = image.getWidth();
            int pixH = h / pixelContains, pixW = w / pixelContains;
            double hwRate = ((double)pixH / (double)pixW);


            if (pixW > pixWMost||pixH > pixHMost )
                if (hwRate > 1) {
                    pixelContains *= ((double) pixH / (double) pixHMost);
                    pixelContains++;
                    pixH = pixHMost;
                    pixW = (int)((double)pixHMost / hwRate);

                } else {
                    pixelContains *= ((double) pixW / (double) pixWMost);
                    pixelContains++;
                    pixW = pixWMost;
                    pixH = (int)((double)pixWMost * hwRate);

                }
            System.out.println("pixw:" + pixW + " \npixh:" + pixH + " \npixelContains:" + pixelContains);
            h -= h % pixelContains;
            w -= w % pixelContains;// make sure divisible

            int[][][] colorsumRGB = new int[3][pixW][pixH];//0 for r ,1 for g, 2 for b

            ExecutorService pool = Executors.newFixedThreadPool(THREAD_CNT);
            int index = 0;
            
            while(index<THREAD_CNT){
                final int tpixW = pixW;
                final int start = index*pixH/THREAD_CNT;
                final int end = (index+1)*pixH/THREAD_CNT;
                pool.submit(()->{
                    for(int x=start;x<end;x++)
                        for (int j = 0; j < tpixW; j++) {
                            for(int clr:image.getRGB(x*pixelContains, j*pixelContains, pixelContains, pixelContains, null, 0, pixelContains)){
                                for (int type = 0; type < 3; type++)
                                colorsumRGB[type][x][j] +=(clr>>(8*(2-type)))&0xff;
                            }
                            colorsumRGB[0][x][j] /= (pixelContains * pixelContains);
                            colorsumRGB[1][x][j] /= (pixelContains * pixelContains);
                            colorsumRGB[2][x][j] /= (pixelContains * pixelContains);
                        }
                });
                index++;
            }
            pool.shutdown();

            while(!pool.isTerminated()){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            BufferedImage output = new BufferedImage(pixW,  pixH, BufferedImage.TYPE_INT_RGB);
            for(int i=0;i<pixW;i++)
                for(int j=0;j<pixH;j++){
                    Color clr = Color.getColorByRGBVal((colorsumRGB[0][i][j]<<16)|(colorsumRGB[1][i][j]<<8)|colorsumRGB[2][i][j]);
                    output.setRGB(i, j, clr.getRGB());
                }
            
            ImageIO.write(output, "png", new File("ignore/preprocess.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static enum Color {
        black, dark_gray(0x3c3c3c), gray(0x787878), light_gray(0xd2d2d2), white(0xffffff), deep_red(0x600018),
        dark_red(0xa50e1e), red(0xed1c24), orange(0xff7f27), gold(0xf6aa09), yellow(0xf9dd3b), light_yellow(0xfffabc),
        dark_green(0x0eb968), green(0x13e67b), light_green(0x87ff5e), dark_teal(0x0c816e), teal(0x10aea6),
        light_teal(0x13e1be), cyan(0x60f7f2), dark_blue(0x28509e), blue(0x4093e4), indigo(0x6b50f6),
        light_indigo(0x99b1fb), dark_purple(0x780c99), purple(0xaa38b9), light_purple(0xe09ff9), dark_pink(0xcb007a),
        pink(0xec1f80), light_pink(0xf38da9), dark_brown(0x684634), brown(0x95682a), dark_beige(0xd18051),
        beige(0xf8b277), light_beige(0xffc5a5);

        int rgb;

        Color(int color) {
            rgb = color;
        }

        Color() {
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

        public static Color getColorByRGBVal(int color) {
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

            Color candidateColor = Color.black;
            for (Color clr : Color.values()) {
                if (clr == black)
                    continue;
                int nextSimilarIndex = colorDistance(r, g, b, clr);
                if (nextSimilarIndex < similarIndex) {
                    similarIndex = nextSimilarIndex;
                    candidateColor = clr;
                }
            }
            return candidateColor;
        }

        private static int colorDistance(int r, int g, int b, Color clr) {
            int dr = Math.abs(r - clr.getR()), dg = Math.abs(g - clr.getG()), db = Math.abs(b - clr.getB());
            return dr * dr + dg * dg + db * db;
        }
    }
}