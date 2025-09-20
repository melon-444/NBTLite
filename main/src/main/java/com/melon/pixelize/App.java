package com.melon.pixelize;

import java.nio.file.Path;

import com.melon.pixelize.utils.ConvertTools;

public class App {


    static final int pixHMost = 64, pixWMost = 64,THREAD_CNT=5;
    static int pixelContains = 10;

    public static void main(String[] args) {
        ConvertTools.convertImageToPixelArt(Path.of("input.png"), Path.of("output.png"), pixHMost, pixWMost, pixelContains);

    }

    
}