package com.melon.pixelize;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.zip.GZIPOutputStream;

import org.junit.jupiter.api.Test;

import com.melon.pixelize.nbt.NBTCompound;
import com.melon.pixelize.nbt.NBTElement;
import com.melon.pixelize.nbt.NBTObjectBuilder;
import com.melon.pixelize.nbt.io.NBTWriter;
import com.melon.pixelize.utils.ConvertTools;

public class MapGenTest {
    @Test void MapGen(){
        System.out.println("MCMapGenerator Test");
        System.out.println("work dir: "+Paths.get("").toAbsolutePath());
            try (Scanner sc = new Scanner(System.in)) {
                boolean once = true;
                while (once) {
                    int index = 0;
                    while (Path.of("ignore/map_" +index + ".dat").toFile().exists())
                        index++;
                    System.out.println("Input image path in ignore/ :");
                    String file = "test.png";//sc.nextLine().trim();
                    byte[] target = ConvertTools.convertImageToMCMapArt(Path.of("../ignore/"+file), 5);
                    NBTElement<?> dat =
                    NBTObjectBuilder.buildCompound()
                            .directCompound(
                            (NBTCompound)NBTObjectBuilder.buildCompound("data")
                            .List("banners", new NBTCompound[0])
                            .ByteArray("colors", target)
                            .String("dimension", "minecraft:overworld")
                            .Boolean("locked", true)
                            .Byte("scale", (byte)0)
                            .Boolean("trackingPosition", false)
                            .Boolean("unlimitedTracking", false)
                            .Int("xCenter", 0)
                            .Int("zCenter",0)
                            .end()
                            )
                            .Int("DataVersion", 1343)
                            .end();
                    File f = Path.of("../ignore/map_" + index + ".dat").toFile();
                    if (!f.exists())
                        f.createNewFile();
                    NBTWriter writer = new NBTWriter(new GZIPOutputStream(new FileOutputStream(f)));
                    writer.write(dat);
                    writer.close();
                    once = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                assertTrue(false);
            }
    }
    
}
