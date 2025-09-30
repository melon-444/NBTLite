package com.melon.pixelize.nbt.io;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

import com.melon.pixelize.nbt.NBTElement;

public class GZipNBTReader extends NBTReader {
    final GZIPInputStream gzipIn;

    public GZipNBTReader(GZIPInputStream gzipIn) {
        this.gzipIn = gzipIn;
    }
    
    @Override
    public int read() throws IOException {
        return gzipIn.read();
    }

    public NBTElement<?> readNBTElement() throws IOException{
        return NBTElement.asNBT(gzipIn.readAllBytes());
    }
    
}
