package com.melon.pixelize;

import java.nio.file.Path;
import java.util.Scanner;

import com.melon.pixelize.utils.ConvertTools;

public class WplacePixelize {

    static int pixHMost = 128, pixWMost = 128, THREAD_CNT = 5;

    static class Test {
        public static void main(String[] args) {
            try (Scanner sc = new Scanner(System.in)) {
                while (true) {
                    int index = 0;
                    while (Path.of("ignore/preprocess" + (index == 0 ? "" : index) + ".png").toFile().exists())
                        index++;
                    System.out.println("Input image path in ignore/:");
                    String file = sc.next().trim();
                    System.out.println("Input pixel Wide :");
                    pixHMost=sc.nextInt();
                    System.out.println("Input pixel Height:");
                    pixWMost=sc.nextInt();


                    ConvertTools.convertImageToPixelArt(Path.of("ignore/"+file),
                            Path.of("ignore/preprocess" + (index == 0 ? "" : index) + ".png"), pixHMost, pixWMost,
                            THREAD_CNT);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}