package com.melon.pixelize.nbt.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import com.melon.pixelize.nbt.NBTElement;

public class NBTReader extends InputStream {
    final GZIPInputStream gzipIn;
    public NBTReader(GZIPInputStream gzipIn) {
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
