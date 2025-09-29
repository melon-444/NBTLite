package com.melon.pixelize;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.melon.pixelize.utils.ConvertTools;

public class WplacePixelizeTest {

    static int pixHMost = 128, pixWMost = 128, THREAD_CNT = 5;

    @Test void WplaceTest() {
        System.out.println("Wplace Gen Test");
        System.out.println("work dir: "+Paths.get("").toAbsolutePath());
        try (Scanner sc = new Scanner(System.in)) {
            boolean once = true;
                while (once) {
                    int index = 0;
                    while (Path.of("ignore/preprocess" + (index == 0 ? "" : index) + ".png").toFile().exists())
                        index++;
                    System.out.println("Input image path in ignore/:");
                    String file = "test.png";//sc.next().trim();
                    System.out.println("Input pixel Wide :");
                    pixHMost= 128;//sc.nextInt();
                    System.out.println("Input pixel Height:");
                    pixWMost= 128;//sc.nextInt();

                    ConvertTools.convertImageToPixelArt(Path.of("ignore/"+file),
                            Path.of("ignore/preprocess" + (index == 0 ? "" : index) + ".png"), pixHMost, pixWMost,
                            THREAD_CNT);
                    once = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                assertTrue(false);
            }
    }   
}
