package com.melon.pixelize;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.zip.GZIPOutputStream;


import com.melon.pixelize.nbt.NBTElement;
import com.melon.pixelize.nbt.NBTObjectBuilder;
import com.melon.pixelize.nbt.io.GZipNBTWriter;

import com.melon.pixelize.utils.ConvertTools;

public class MCMapGenerator {
    static class Test{
        public static void main(String[] args) {
            System.out.println("MCMapGenerator Test");
            try (Scanner sc = new Scanner(System.in)) {
                while (true) {
                    int index = 0;
                    while (Path.of("ignore/map_" +index + ".dat").toFile().exists())
                        index++;
                    System.out.println("Input image path in ignore/ :");
                    byte[] target = ConvertTools.convertImageToMCMapArt(Path.of("ignore/"+sc.nextLine().trim()), 5);
                    NBTElement<?> dat =
                    NBTObjectBuilder.buildCompound()
                            .directCompound(
                            NBTObjectBuilder.buildCompound("data")
                            .ByteArray("colors", target)
                            .String("dimension", "minecraft:overworld")
                            .Boolean("locked", true)
                            .Byte("scale", (byte)0)
                            .Boolean("trackingPosition", false)
                            .Boolean("unlimitedTracking", false)
                            .Int("xCenter", 0)
                            .Int("zCenter",0)
                            .directList(NBTObjectBuilder.buildList("banners")
                            .endList())
                            .endCompound()
                            )
                            .Int("DataVersion", 1343)
                            .end();
                    System.out.println(dat.toString());

                    File f = Path.of("ignore/map_" + index + ".dat").toFile();
                    if (!f.exists())
                        f.createNewFile();
                    GZipNBTWriter writer = new GZipNBTWriter(new GZIPOutputStream(new FileOutputStream(f)));
                    writer.write(dat);
                    writer.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
